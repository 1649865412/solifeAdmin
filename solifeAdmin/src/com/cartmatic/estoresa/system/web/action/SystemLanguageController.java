package com.cartmatic.estoresa.system.web.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.BindException;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.common.model.system.SystemLanguage;
import com.cartmatic.estore.core.controller.GenericController;
import com.cartmatic.estore.system.service.SystemLanguageManager;

/**
 * 管理SystemLanguage的Controller。使用doAction映射执行那个方法。支持Form和List两大类的模式。
 * 
 * @author Ryan
 * 
 */
public class SystemLanguageController extends GenericController<SystemLanguage> {
	private SystemLanguageManager systemLanguageManager = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController#getEntityName(java.lang.Object)
	 */
	@Override
	protected String getEntityName(SystemLanguage entity) {
		return getMessage(entity.getLanguageNameKey());
	}

	public void setSystemLanguageManager(SystemLanguageManager manager) {
		systemLanguageManager = manager;
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
		Map<Integer, Map<String, Object>> multiSaveModel = null;

		String[] ids = request.getParameterValues("multiIds");
		if (ids != null && ids.length > 0) {
			multiSaveModel = new HashMap<Integer, Map<String, Object>>();
			for (String element : ids) {
				String status = request.getParameter("status_" + element);
				// 转换或缺省值设置
				if (status == null || "".equals(status)) {
					status = "0";
				}
				Map<String, Object> entityModel = new HashMap<String, Object>();
				entityModel.put("status", new Short(status));
				multiSaveModel.put(new Integer(element), entityModel);
			}
		}

		return multiSaveModel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.BaseController#initController()
	 */
	@Override
	protected void initController() throws Exception {
		// 设置范型基类的mgr，无法通过自动检测设置
		mgr = systemLanguageManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController#onSave(javax.servlet.http.HttpServletRequest,
	 *      java.lang.Object, org.springframework.validation.BindException)
	 */
	@Override
	protected void onSave(HttpServletRequest request, SystemLanguage entity, BindException errors) {
		// 一些判断、转换、其他处理等
		String status = request.getParameter("status");

		entity.setStatus(status == null ? Constants.STATUS_NOT_PUBLISHED : Constants.STATUS_ACTIVE);

		if (systemLanguageManager.isExistLocaleCode(entity)) {
			errors.rejectValue("localeCode", "systemLanguage.localeCode.exists");
		}
	}

}
