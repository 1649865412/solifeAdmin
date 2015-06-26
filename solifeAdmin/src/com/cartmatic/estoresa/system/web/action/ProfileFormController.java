package com.cartmatic.estoresa.system.web.action;

import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.common.model.system.AdminInfo;
import com.cartmatic.estore.common.model.system.AppAdmin;
import com.cartmatic.estore.common.model.system.AppUser;
import com.cartmatic.estore.core.model.Message;
import com.cartmatic.estore.core.view.AjaxView;
import com.cartmatic.estore.system.service.AppAdminManager;
import com.cartmatic.estore.system.service.AppUserManager;
import com.cartmatic.estore.webapp.util.RequestContext;
import com.cartmatic.estore.webapp.util.RequestUtil;

public class ProfileFormController extends com.cartmatic.estore.core.controller.BaseController {
	//private static final String	AppAdmin_EDIT_KEY	= "appAdminForm";
    private AppAdminManager appAdminManager = null;
    
    private AppUserManager appUserManager=null;
    
	public void setAppUserManager(AppUserManager appUserManager) {
		this.appUserManager = appUserManager;
	}
    public void setAppAdminManager(AppAdminManager appAdminManager) {
        this.appAdminManager =appAdminManager;
    }
    
    public ModelAndView defaultAction(HttpServletRequest request, HttpServletResponse resp) throws ServletException 
    {
    	removeNavFromSearchCriteria(request);
    	String page = RequestUtil.getRequestedPageName(request, "index");
    	AppUser appUser=(AppUser)RequestContext.getCurrentUser();
        AppAdmin appAdmin=null;
        try{
            appAdmin=appAdminManager.getById(appUser.getAppuserId());
        }
        catch(Exception ex){
            appAdmin = new AppAdmin();
        }
        if(appAdmin.getAdminInfo()==null){
        	appAdmin.setAdminInfo(new AdminInfo());
        }
        ModelAndView mv = new ModelAndView(page);
    	mv.addObject("appAdmin", appAdmin);
		return mv;
    }
    
    /**
     * 保存
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView save(HttpServletRequest request, HttpServletResponse response) throws Exception 
    {
        if (logger.isDebugEnabled()) {
        	logger.debug("entering 'onSubmit' method...");
        }
        
        AppAdmin appAdmin = new AppAdmin();
        BindException errors = null;
		errors = new BindException(bindAndValidate(request, appAdmin).getBindingResult());
        //String success = getSuccessView();
		// -----------------------------------------------------
    	appAdmin.setUpdateBy(RequestContext.getCurrentUserId());    	
    	appAdmin.setUpdateTime(new Date());
 
		 boolean isEmailExist=false;
        //check existence of the user's email
        isEmailExist=appUserManager.isEmailExist(appAdmin);
        if(isEmailExist){
            errors.rejectValue("email","appUser.email.isExist","the email has use by others");
            return new ModelAndView(formView,errors.getModel());
	    }
        
        AppAdmin destAdmin=appAdminManager.getById(RequestContext.getCurrentUserId());
        setProfileInfo(appAdmin,destAdmin);
        appAdminManager.save(destAdmin);    	
        //String key = (6isNew) ? "appAdmin.added" : "appAdmin.updated";
        saveMessage(Message.info("appAdmin.updated", appAdmin.getFirstname()+" "+ appAdmin.getLastname()));   
       
        return new ModelAndView(new RedirectView(successView));
    }
    
 
    
    private AppAdmin formBackingObject(HttpServletRequest request)
            throws Exception {
        //get current user 
        AppUser appUser=(AppUser)RequestContext.getCurrentUser();
        AppAdmin appAdmin=null;
        try{
            appAdmin=appAdminManager.getById(appUser.getAppuserId());
        }
        catch(Exception ex){
            appAdmin = new AppAdmin();
        }
        return appAdmin;
    }
    
    /**
     * 
     * @param source
     * @param dest
     */
    private void setProfileInfo(AppAdmin source,AppAdmin dest){
        dest.setTitle(source.getTitle());
        dest.setFirstname(source.getFirstname());
        dest.setLastname(source.getLastname());
        dest.setEmail(source.getEmail());
        dest.setTelephone(source.getTelephone());
        dest.setFax(source.getFax());
        dest.setUserPosition(source.getUserPosition());
        dest.setAdminInfo(source.getAdminInfo());
    }
	@Override
	protected Map<Integer, Map<String, Object>> getMultiSaveModel(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	protected void initController() throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	
	public ModelAndView updatePagingSetting(HttpServletRequest request,HttpServletResponse response) throws Exception {
		AjaxView ajaxView=new AjaxView(response);
		try
		{
			AppUser appUser=(AppUser)RequestContext.getCurrentUser();
	        AppAdmin appAdmin=appAdminManager.getById(appUser.getAppuserId());
	        AdminInfo adminInfo= appAdmin.getAdminInfo();
	        if(adminInfo==null){
	        	adminInfo=new AdminInfo();
	        	appAdmin.setAdminInfo(adminInfo);
	        }
	        Integer pagingSetting=ServletRequestUtils.getIntParameter(request, "count", 10);
	        adminInfo.setPagingSetting(pagingSetting);
	        appAdminManager.save(appAdmin);
	        request.getSession(true).setAttribute(Constants.KEY_ADMIN_INFO,adminInfo);
			ajaxView.setMsg(getMessage("adminInfo.pagingSetting.updated",pagingSetting));
		}
		catch (Exception e)
		{
			ajaxView.setStatus(new Short("-500"));
			ajaxView.setMsg(getMessage("system.error.msg"));
			e.printStackTrace();
		}
		return ajaxView;
	}
	
	public ModelAndView updateProductViewSetting(HttpServletRequest request,HttpServletResponse response) throws Exception {
		AjaxView ajaxView=new AjaxView(response);
		try
		{
			AppUser appUser=(AppUser)RequestContext.getCurrentUser();
	        AppAdmin appAdmin=appAdminManager.getById(appUser.getAppuserId());
	        AdminInfo adminInfo= appAdmin.getAdminInfo();
	        if(adminInfo==null){
	        	adminInfo=new AdminInfo();
	        	appAdmin.setAdminInfo(adminInfo);
	        }
	        String productView = ServletRequestUtils.getStringParameter(request, "productView", "0");
	        adminInfo.setProductViewSetting(Short.valueOf(productView));
	        appAdminManager.save(appAdmin);
	        request.getSession(true).setAttribute(Constants.KEY_ADMIN_INFO,adminInfo);
			ajaxView.setMsg(getMessage("adminInfo.productView.updated"));
		}
		catch (Exception e)
		{
			ajaxView.setStatus(new Short("-500"));
			ajaxView.setMsg(getMessage("system.error.msg"));
			e.printStackTrace();
		}
		return ajaxView;
	}
}
