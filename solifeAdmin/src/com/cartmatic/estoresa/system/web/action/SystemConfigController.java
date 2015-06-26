package com.cartmatic.estoresa.system.web.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import com.cartmatic.estore.core.controller.GenericController;
import com.cartmatic.estore.core.model.BaseObject;
import com.cartmatic.estore.core.model.Message;
import com.cartmatic.estore.core.util.StringUtil;
import com.cartmatic.estore.core.util.UrlUtil;
import com.cartmatic.estore.common.helper.ConfigRegistryImpl;
import com.cartmatic.estore.common.helper.ConfigUtil;
import com.cartmatic.estore.common.model.system.Store;
import com.cartmatic.estore.common.model.system.SystemConfig;
import com.cartmatic.estore.system.service.StoreManager;
import com.cartmatic.estore.system.service.SystemConfigManager;

public class SystemConfigController extends GenericController<SystemConfig> {
    private SystemConfigManager systemConfigManager = null;

    private StoreManager storeManager;
    
    public void setSystemConfigManager(SystemConfigManager inMgr) {
        this.systemConfigManager = inMgr;
    }
    public void setStoreManager(StoreManager avalue)
    {
    	this.storeManager = avalue;
    }
    

	@Override
	public ModelAndView defaultAction(HttpServletRequest request,HttpServletResponse response) {
		String catalog = this.getCatalog(request);
		this.doPublicBind(request);
		List<SystemConfig>systemConfigList=systemConfigManager.findSystemConfigByCategory(catalog);
		if ("store".equals(catalog))
		{
			return new ModelAndView("system/systemConfigList", "storeList",storeManager.getAll());
		}
		else
			return new ModelAndView("system/systemConfigList", "systemConfigList",systemConfigList );
	}
	
	private void doPublicBind(HttpServletRequest request) {
		request.setAttribute("catalogs", ConfigUtil.getInstance().getConfigCategorys().split(","));
		request.setAttribute("teams", systemConfigManager.findConfigKeyItemsByCategory(this.getCatalog(request)));

	}
	
	private String getCatalog(HttpServletRequest request) {
		String model = request.getParameter("configCategory");
		if (StringUtils.isBlank(model))
			model = "system";
		request.setAttribute("configCategory", model);
		return model;
	}
	

	private final Object lockObj = new Object();

	@Override
	public ModelAndView multiSave(HttpServletRequest request,HttpServletResponse response) {
		List<String> results = new ArrayList<String>();
		List<SystemConfig>systemConfigList=systemConfigManager.findSystemConfigByCategory(getCatalog(request));
		synchronized (lockObj) {
			for (int i = 0; i < systemConfigList.size(); i++) {
				SystemConfig systemConfig = (SystemConfig) systemConfigList.get(i);

				String configValue = request.getParameter("configValue_" + systemConfig.getSystemConfigId().toString());
				
				if (systemConfig.getDataType() != null) {
					if (systemConfig.getConfigType().intValue() == 3) {
						if (configValue == null || "".equals(configValue))
							configValue = "false";
					}
					if (configValue != null) {
						configValue = configValue.trim();
						if (!configValue.equals(systemConfig.getConfigValue())) {
							if (systemConfig.getDataType().equals(ConfigRegistryImpl.CONF_DATA_TYPE_NUMBER)) {
								if (StringUtil.isNumber(configValue)) {
									systemConfig.setConfigValue(configValue);
									systemConfigManager.save(systemConfig);
									// add the updated object to results
									results.add(systemConfig.getConfigKey());
								}
							} else {
								systemConfig.setConfigValue(configValue);
								systemConfigManager.save(systemConfig);
								// add the updated object to results
								results.add(systemConfig.getConfigKey());
							}
						}
					}
				}
			}
		}
		
		// save message
		StringBuffer sb = new StringBuffer();
		if (results.size() > 3) {
			sb.append(results.size() + getMessage("common.items"));
		} else if (results.size() > 0) {
			for (String result : results) {
				sb.append(",[").append(getMessage("conf.name." + result)).append("]");
			}
			
			if(sb.length()>0){
				sb.deleteCharAt(0);
			}
		}
		if (results.size() > 0) {
			saveMessage(Message.info("systemConfig.updated",  sb.toString()));
		}else{
			saveMessage(Message.info("systemConfig.nothing.toUpdate"));
		}
		this.doPublicBind(request);
		ConfigUtil.getInstance().checkInvalidSystemConfigs();
		return getRedirectView(UrlUtil.appendParamToUrl(mappedUrl, "configCategory", request.getParameter("configCategory")));
	}
	
	
	public ModelAndView clearAllServerSideCache(HttpServletRequest request,HttpServletResponse response) throws ServletException {
		systemConfigManager.clearAllServerSideCaches();
		saveMessage(Message.info("systemConfig.refreshRerverCache"));
		return getRedirectView(UrlUtil.appendParamToUrl(mappedUrl, "configCategory", request.getParameter("configCategory")));
	}



	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController getEntityName(java.lang.Object)
	 */
	@Override
	protected String getEntityName(SystemConfig entity) {
		return entity.getSystemConfigName();
	}

	/**
	 * 构造批量更新所需的model。
	 * <P>
	 * 本来这方法对大部分情况也是可以自动分析和转换的，但考虑到比较复杂和难以灵活（验证、缺省值、I18N等、Status转换等），暂时要求各模块自己实现。要求数据要先转换为正确的类型。
	 * 
	 * @param request
	 * @return
	 */
	@Override
	protected Map<Integer, Map<String, Object>> getMultiSaveModel(HttpServletRequest request) {
		//FIXME
		throw new RuntimeException("Not implemented yet!");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.BaseController#initController()
	 */
	@Override
	protected void initController() throws Exception {
		mgr = systemConfigManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController onSave(javax.servlet.http.HttpServletRequest,
	 *      java.lang.Object, org.springframework.validation.BindException)
	 */
	@Override
	protected void onSave(HttpServletRequest request, SystemConfig entity, BindException errors) {
	}

}
