
package com.cartmatic.estoresa.system.web.action;

import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.cartmatic.estore.common.model.system.AppUser;
import com.cartmatic.estore.common.model.system.ModifyPasswordModel;
import com.cartmatic.estore.core.controller.BaseController;
import com.cartmatic.estore.core.model.Message;
import com.cartmatic.estore.system.service.AppUserManager;
import com.cartmatic.estore.webapp.util.RequestContext;
import com.cartmatic.estore.webapp.util.RequestUtil;

/**
 * 
 * @author Ryan Modify password form for user
 */
public class ModifyPasswordFormController extends BaseController {

	private AppUserManager	appUserManager	= null;
	
	private String successView;
	
	private PasswordEncoder passwordEncoder;
	
	public ModelAndView defaultAction(HttpServletRequest request, HttpServletResponse resp) throws ServletException 
    {
		removeNavFromSearchCriteria(request);
		String page = RequestUtil.getRequestedPageName(request, "index");
    	ModelAndView mv = new ModelAndView(page);
    	mv.addObject("modifyPasswordModel", formBackingObject(request));
		return mv;
	}
	
	protected ModifyPasswordModel formBackingObject(HttpServletRequest request){
		AppUser appuser = (AppUser) RequestContext.getCurrentUser();
		ModifyPasswordModel passwordModel = new ModifyPasswordModel();
		passwordModel.setAppuserId(appuser.getAppuserId());		
		return passwordModel;
	}

	
	public ModelAndView save(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		
		ModifyPasswordModel passwordModel = new ModifyPasswordModel();
		//ServletRequestDataBinder binder = this.;
		//binder.bind(request, passwordModel, "modifyPasswordModel");
		BindException errors = null;
		errors = new BindException(bindAndValidate(request, passwordModel).getBindingResult());
		if (errors.hasErrors())
		{
			return showForm(request, errors);
		}
		String oldPassword = passwordEncoder.encodePassword(passwordModel.getOldPassword(), null);
		String newPassword = passwordEncoder.encodePassword(passwordModel.getNewPassword(), null);

		passwordModel.setOldPassword(oldPassword);
		passwordModel.setNewPassword(newPassword);

		// check the old password is right
		boolean isOldPasswordRight = appUserManager.getIsPasswordRight(passwordModel.getAppuserId(), passwordModel.getOldPassword());
		if (!isOldPasswordRight) {
			errors.rejectValue("oldPassword", "appUser.oldPassword.error","Your old password is wrong.");
			return showForm(request, errors);
			//return new ModelAndView(RequestUtil.getRequestedPageName(request, getDefaultViewName()), errors.getModel());
		}

		// modify password
		appUserManager.saveModifyPassword(passwordModel.getAppuserId(),passwordModel.getNewPassword());
		saveMessage(Message.info("appUser.password.modified"));
		return new ModelAndView(new RedirectView(successView));
	}

	public void setSuccessView(String avalue)
    {
    	successView = avalue;
    }
	
	public void setAppUserManager(AppUserManager appUserManager) {
		this.appUserManager = appUserManager;
	}
	
	public void setPasswordEncoder(PasswordEncoder avalue)
    {
    	passwordEncoder = avalue;
    }

	@Override
	protected Map<Integer, Map<String, Object>> getMultiSaveModel(
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void initController() throws Exception {
		// TODO Auto-generated method stub
		
	}

}
