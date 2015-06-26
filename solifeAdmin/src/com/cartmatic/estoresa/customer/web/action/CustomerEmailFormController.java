package com.cartmatic.estoresa.customer.web.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.cartmatic.estore.common.helper.ConfigUtil;
import com.cartmatic.estore.common.model.customer.CustomerEmail;
import com.cartmatic.estore.common.model.system.AppUser;
import com.cartmatic.estore.core.controller.BaseController;
import com.cartmatic.estore.core.model.Message;
import com.cartmatic.estore.core.view.MailEngine;
import com.cartmatic.estore.system.service.AppUserManager;
import com.cartmatic.estore.webapp.util.RequestUtil;

public class CustomerEmailFormController extends BaseController {
    
	private AppUserManager appUserManager=null;
	
	protected MailEngine mailEngine = null;
	
    public ModelAndView defaultAction(HttpServletRequest request, HttpServletResponse resp) throws ServletException 
    {
		String page = RequestUtil.getRequestedPageName(request, "index");
		CustomerEmail email=new CustomerEmail();
		String appuserId=request.getParameter("appuserId");    	
    	if(appuserId!=null){
    	    AppUser appUser=appUserManager.getById(new Integer(appuserId));
    	    email.setReceivers(appUser.getEmail());
    	}
    	ModelAndView mv = new ModelAndView(page);
    	mv.addObject("customerEmail", email);
		return mv;
	}
    
	//public ModelAndView cancelForm(HttpServletRequest request, HttpServletResponse response) throws Exception
	//{
	//	return new ModelAndView(new RedirectView(successView));
	//}
    
    
    public ModelAndView send(HttpServletRequest request, HttpServletResponse response) throws Exception 
    {
        if (logger.isDebugEnabled()) {
        	logger.debug("entering 'CustomerEmailController--onSubmit' method...");
        }
        
        CustomerEmail customerEmail = new CustomerEmail();
        this.bindAndValidate(request, customerEmail);
        
        String[]mailTos=customerEmail.getReceivers().split("[,]");        
        Map map=new HashMap();
        map.put("customerEmail",customerEmail);
        //map.put("content",customerEmail.getContent());
        String appuserId=request.getParameter("appuserId");       
        if(appuserId!=null&&!appuserId.trim().equals("")){
           AppUser appUser=appUserManager.getById(new Integer(appuserId));
           map.put("username" , appUser.getUsername());
        }
        
        String from=ConfigUtil.getInstance().getDefaultSystemEmail();
        
        mailEngine.sendSimpleTemplateMail("customer/feedbackEmail.vm",map,null,from,mailTos);
        saveMessage(Message.info("customer.mail.success"));        
        return new ModelAndView(new RedirectView(successView));

    }
  
    
    /*protected CustomerEmail formBackingObject(HttpServletRequest request)
            throws Exception {
    	
        CustomerEmail email=new CustomerEmail();
        //getBinder().bind(request, email, "customerEmail");
        
    	
    	//request.setAttribute("customerEmail",email);
    	//email.setContent(request.getParameter("content"));
    	//email.setTitle(request.getParameter("title"));
        return email;
    }*/    
    
    public void setAppUserManager(AppUserManager appUserManager) {
        this.appUserManager = appUserManager;
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
	
	public void setMailEngine(MailEngine mailEngine)
    {
        this.mailEngine = mailEngine;
    }
}