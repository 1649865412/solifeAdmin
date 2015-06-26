package com.cartmatic.estoresa.system.web.action;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;

import com.cartmatic.estore.core.controller.GenericController;
import com.cartmatic.estore.core.exception.ApplicationException;
import com.cartmatic.estore.core.model.BaseObject;
import com.cartmatic.estore.core.model.Message;
import com.cartmatic.estore.core.security.RequestMapFactoryBean;
import com.cartmatic.estore.common.model.system.AppResource;
import com.cartmatic.estore.common.model.system.AppRole;
import com.cartmatic.estore.common.model.system.RoleRes;
import com.cartmatic.estore.system.service.AppRoleManager;
import com.cartmatic.estore.system.service.AppResourceManager;
import com.cartmatic.estore.system.service.RoleResManager;

public class AppRoleController extends GenericController<AppRole> {
	private RoleResManager roleResManager=null;
    private AppRoleManager appRoleManager = null;
    
    private AppResourceManager appResourceManager;

    public void setAppRoleManager(AppRoleManager inMgr) {
        this.appRoleManager = inMgr;
    }

	public void setAppResourceManager(AppResourceManager appResourceManager) {
		this.appResourceManager = appResourceManager;
	}



	public void setRoleResManager(RoleResManager roleResManager) {
		this.roleResManager = roleResManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController getEntityName(java.lang.Object)
	 */
	@Override
	protected String getEntityName(AppRole entity) {
		return entity.getAppRoleName();
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
		mgr = appRoleManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController onSave(javax.servlet.http.HttpServletRequest,
	 *      java.lang.Object, org.springframework.validation.BindException)
	 */
	@Override
	protected void onSave(HttpServletRequest request, AppRole appRole, BindException errors) {
		if(isEntityNew(request)){
			boolean isRoleNameExist=appRoleManager.getIsRoleNameExist(appRole.getRoleName());
	    	if(isRoleNameExist){
	    		Object[]params={appRole.getRoleName()};
	    		errors.rejectValue("roleName","appRole.roleName.exist",params,"the role name has exist!");
	    	}
		}
		
		if(!errors.hasErrors()){
			Set<RoleRes> rsSet = new HashSet<RoleRes>();
			String[] resourceIds=request.getParameterValues("ckResourceId");
	    	//其他resource
			if(resourceIds!=null)
			{
				for(int i=0; i<resourceIds.length; i++)
				{
				    /*
				     * 读取mask,记录本角色在对应的资源上有些什么权限.由于是利用二进制来表达,
				     * 所以mask值只要简单相加就可以了.
				     * 例如:save:1 , edit:2, delete:4, other:8
				     * 全选的 mask = 1+2+4+8 
				     */
				    String[] masks=request.getParameterValues("mask_"+resourceIds[i]);
				    int maskCount = 0;
				    if (masks != null)
				    for (String mask : masks)
				    {
				        maskCount += Integer.parseInt(mask);
				    }
					AppResource rs=appResourceManager.getById(new Integer(resourceIds[i]));
					RoleRes roleRes = new RoleRes();
	        		roleRes.setAppResource(rs);
	        		roleRes.setAppRole(appRole);
	        		roleRes.setPermissionMask(maskCount);
					rsSet.add(roleRes);
				}
			}
			appRole.setRoleRess(rsSet);
		}
	}
	
	

	@Override
	public ModelAndView save(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 取得Form对应的Model
		AppRole entity = formBackingObject(request);

		BindException errors = null;
		try {
			ServletRequestDataBinder binder = bindAndValidate(request, entity);
			errors = new BindException(binder.getBindingResult());
			// 传给子类的实现，后者可以继续验证和加入错误到errors
			onSave(request, entity, errors);
			if (!errors.hasErrors()) {// 里面如果出错应该抛出异常
				appRoleManager.saveAppRole(entity);
				String msgKey = (isEntityNew(request)) ? "common.added": "common.updated";
				saveMessage(Message.info(msgKey, getEntityTypeMessage(),getEntityName(entity)));
				//刷新当前系统的source
				ApplicationContext appContext=WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
	        	RequestMapFactoryBean dbSource = appContext.getBean(RequestMapFactoryBean.class);
	    		dbSource.reload();
			}
		} catch (ApplicationException e) {
			handleApplicationException(errors, e);
		}

		ModelAndView mav;
		if (errors.hasErrors()) {
			mav = showForm(request, errors);
		} else if (successView != null) {
			mav = getModelAndView(successView, errors.getModel());
		} else {
			mav = getRedirectToActionView("edit", ((BaseObject) entity).getId()
					.toString());
		}
		return mav;
	}

	@Override
	protected void onShowForm(HttpServletRequest request, ModelAndView mv) {
		List<AppResource> allResources=appResourceManager.getAllResources();
		request.setAttribute("allResource", allResources);
	}
	
	

}
