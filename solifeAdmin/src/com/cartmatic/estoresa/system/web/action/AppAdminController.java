
package com.cartmatic.estoresa.system.web.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.common.model.system.AppAdmin;
import com.cartmatic.estore.common.model.system.AppRole;
import com.cartmatic.estore.common.model.system.AppUser;
import com.cartmatic.estore.core.controller.GenericController;
import com.cartmatic.estore.core.exception.ApplicationException;
import com.cartmatic.estore.core.model.Message;
import com.cartmatic.estore.system.service.AppAdminManager;
import com.cartmatic.estore.system.service.AppRoleManager;
import com.cartmatic.estore.system.service.AppUserManager;
import com.cartmatic.estore.webapp.util.RequestContext;

public class AppAdminController extends GenericController<AppAdmin> {
	private AppAdminManager	appAdminManager	= null;
	private AppRoleManager	appRoleManager	= null;
	private AppUserManager	appUserManager	= null;
	private PasswordEncoder	passwordEncoder;

	public void setPasswordEncoder(PasswordEncoder avalue) {
		passwordEncoder = avalue;
	}

	public void setAppAdminManager(AppAdminManager appAdminManager) {
		this.appAdminManager = appAdminManager;
	}

	public void setAppRoleManager(AppRoleManager appRoleManager) {
		this.appRoleManager = appRoleManager;
	}

	public void setAppUserManager(AppUserManager appUserManager) {
		this.appUserManager = appUserManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController
	 * getEntityName(java.lang.Object)
	 */
	@Override
	protected String getEntityName(AppAdmin entity) {
		return entity.getAppUserName();
	}

	/**
	 * 构造批量更新所需的model。
	 * <P>
	 * 本来这方法对大部分情况也是可以自动分析和转换的，但考虑到比较复杂和难以灵活（验证、缺省值、I18N等、Status转换等），暂时要求各模块自己实现
	 * 。要求数据要先转换为正确的类型。
	 * 
	 * @param request
	 * @return
	 */
	@Override
	protected Map<Integer, Map<String, Object>> getMultiSaveModel(
			HttpServletRequest request) {
		String[] multiIds = request.getParameterValues("multiIds");
		Map<Integer, Map<String, Object>> map = new HashMap<Integer, Map<String, Object>>();
		if (multiIds != null && multiIds.length != 0) {
			for (int i = 0; i < multiIds.length; i++) {
				Integer id = Integer.valueOf(multiIds[i]);
				String status = request.getParameter("status_" + id);
				if (StringUtils.isBlank(status)) {
					status = "2";
				}
				Map<String, Object> entityModel = new HashMap<String, Object>();
				entityModel.put("status", Short.valueOf(status));
				map.put(id, entityModel);
			}
		}
		return map;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.BaseController#initController()
	 */
	@Override
	protected void initController() throws Exception {
		mgr = appAdminManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController
	 * onSave(javax.servlet.http.HttpServletRequest, java.lang.Object,
	 * org.springframework.validation.BindException)
	 */
	@Override
	protected void onSave(HttpServletRequest request, AppAdmin appAdmin, BindException errors) 
	{
		if (isEntityNew(request)) 
		{
			if (StringUtils.isBlank(appAdmin.getNewPassword())) 
			{
				errors.rejectValue("newPassword", "appAdmin.newPassword.required");
			}
			if (appUserManager.isAdminExist(appAdmin.getUsername())) 
			{
				Object[] params = { appAdmin.getUsername() };
				errors.rejectValue("username", "appUser.username.isExist", params, "the user name has exist!");
			}
			else if (appUserManager.isEmailExist(appAdmin)) 
			{
				errors.rejectValue("email", "appUser.email.isExist", "the email has exist!");
			}
			// set user type for admin user
			appAdmin.setUserType(AppUser.USER_TYPE_ADMIN);
			// set unlock staus
			appAdmin.setIsLocked(AppUser.CONST_UNLOCKED);
			// set user status to unactive
			appAdmin.setStatus(Constants.STATUS_ACTIVE);
			appAdmin.setCreateBy(RequestContext.getCurrentUserId());
			appAdmin.setCreateTime(new Date());
			appAdmin.setUpdateTime(new Date());
			appAdmin.setDeleted(Constants.MARK_NOT_DELETED);
			appAdmin.setPassword(passwordEncoder.encodePassword(appAdmin.getNewPassword(), null));
			appAdmin.setUpdateBy(RequestContext.getCurrentUserId());
		} else {
			// check existence of the user's email
			boolean isEmailExist = appUserManager.isEmailExist(appAdmin);
			if (isEmailExist) {
				errors.rejectValue("email", "appUser.email.isExist",
						"the email has use by others");
			}
			if (StringUtils.isNotBlank(appAdmin.getNewPassword()) && !errors.hasErrors()) {
				String newPassword = passwordEncoder.encodePassword(appAdmin.getNewPassword(), null);
				appAdmin.setPassword(newPassword);
			}
		}

		// set the roles for current appadmin
		appAdmin.getUserRoles().clear();
		String[] appRoleIds = request.getParameterValues("appRoleIds");
		if (appRoleIds != null) {
			for (int i = 0; i < appRoleIds.length; i++) {
				AppRole role = appRoleManager
						.getById(new Integer(appRoleIds[i]));
				appAdmin.getUserRoles().add(role);
			}
		}
		// if the current user is admin user,then set the admin role.
		if (Constants.DEFAULT_ADMIN_USER_NAME.equals(appAdmin.getUsername())) {
			AppRole adminRole = appRoleManager.getAdminRole();
			if (!appAdmin.getUserRoles().contains(adminRole)) {
				appAdmin.getUserRoles().add(adminRole);
			}
		}
	}

	@Override
	protected void onShowForm(HttpServletRequest request, ModelAndView mv) {
		List<AppRole> allRoles = appRoleManager.getAll();
		// for unit tests - so we can grab an object to save
		request.setAttribute("allRoles", allRoles);
	}

	@Override
	public ModelAndView delete(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 取得Form对应的Model。因为要先保存其名称，否则可能会连I18N数据也已经删除了；另外可以用于出错的时候恢复显示Form页面
		AppAdmin entity = formBackingObject(request);
		try {
			// 删除并设置信息
			String entityId = request.getParameter(entityIdName);

			if (!StringUtils.isEmpty(entityId)) {
				AppAdmin appAdmin = appAdminManager.getById(new Integer(entityId));
				if (!appAdmin.getUsername().equalsIgnoreCase("admin")) {
					// 先构造提示信息，否则信息对应的记录可能也会被删除。
					String entityName = getEntityName(entity);
					entity = mgr.deleteById(new Integer(entityId));
					saveMessage(Message.info("common.deleted",
							getEntityTypeMessage(), entityName));

				} else {
					saveMessage(Message.error("appAdmin.admin.canNotDelete"));
				}
			} else {
				saveMessage(Message.error("errors.invalid.delete.id", entityId));
			}
		} catch (ApplicationException e) {
			// 为了出错的时候可以恢复表单显示，构造errors，但是设置标记跳过绑定和验证
			request.setAttribute("SUPPRESS_BINDING", Boolean.TRUE);
			request.setAttribute("SUPPRESS_VALIDATION", Boolean.TRUE);
			BindException errors = null;
			ServletRequestDataBinder binder = bindAndValidate(request, entity);
			errors = new BindException(binder.getBindingResult());
			handleApplicationException(errors, e);
			return showForm(request, errors);
		}

		return getModelAndView(cancelFormView, null);
	}

}
