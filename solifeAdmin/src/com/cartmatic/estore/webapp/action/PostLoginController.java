/*
 * Created on Jun 16, 2006
 */

package com.cartmatic.estore.webapp.action;

import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.servlet.ModelAndView;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.cart.util.ShoppingCartUtil;
import com.cartmatic.estore.common.helper.AppAuditHelper;
import com.cartmatic.estore.common.helper.ConfigUtil;
import com.cartmatic.estore.common.model.customer.Customer;
import com.cartmatic.estore.common.model.system.AppUser;
import com.cartmatic.estore.core.controller.BaseController;
import com.cartmatic.estore.core.util.ContextUtil;
import com.cartmatic.estore.customer.CustomerConstants;
import com.cartmatic.estore.customer.service.ShopPointHistoryManager;
import com.cartmatic.estore.system.service.AppUserManager;
import com.cartmatic.estore.webapp.util.RequestContext;
import com.cartmatic.estore.webapp.util.RequestUtil;

/**
 * @author NULL
 * 
 * modify descript:修改登陆成功之后加载/合并购物车的流程
 */
public class PostLoginController extends BaseController 
{
	
	private ShopPointHistoryManager	shopPointHistoryManager;
	
	private AppUserManager appUserManager=null;
	
	public void setAppUserManager(AppUserManager appUserManager) {
		this.appUserManager = appUserManager;
	}

	private void addLoginedFlag(HttpServletRequest req, HttpServletResponse resp) {
		// add logined flag in cookie
		RequestUtil.setUserInfoCookie(resp, RequestContext.getCurrentUser(), (req).getContextPath());
	}

	
	public ModelAndView defaultAction(HttpServletRequest req,HttpServletResponse resp) throws ServletException {
		HttpSession session = req.getSession(true);
		String targetUrl = getTargetUrl(req, session);
		// set login time
		//session.setAttribute("LoginInTime", new java.util.Date());
		AppUser appUser = (AppUser) RequestContext.getCurrentUser();
		// merge cookie and shoppingcart after signin
		if (ContextUtil.isStoreFront()) 
		{
			// make sure that the amdin use cant login front page here.
			if (appUser.isContainAdminRole()) {
				try {
					resp.sendRedirect(req.getContextPath() + "/admin/index.html");
					return null;
				} catch (Throwable ex) {
					logger.error("Cannot sendRedirect to target page after logined: /admin/index.html",
								ex);
				}
			}
			ShoppingCartUtil.getInstance().setShoppingcartInfo(req, resp, (Customer) appUser);
			//setMembership(req);
			// add shop point history for current user.
			// only add a shop point record for current user in a same day.
			if (!shopPointHistoryManager.getIsLoginToday(RequestContext.getCurrentUserId())) 
			{
				shopPointHistoryManager.saveNewShopPointHistory(CustomerConstants.ShopPoint_Type_Login, (Customer) appUser);
			}
		    //Object toCe = session.getAttribute(CheckoutConstants.TOCHECKOUT);
			//if(toCe!=null){
			//	boolean toCheckout = Boolean.valueOf(toCe.toString());
			//	if(toCheckout){
			//		session.removeAttribute(CheckoutConstants.TOCHECKOUT);
			//		targetUrl = "checkout/login.html";
			//	}
			//}
		} 
		else 
		{
			// checking the system parameter cofiguration when the login user
			// hold the admin role
			if (appUser.isContainAdminRole()
					&& ConfigUtil.getInstance().checkInvalidSystemConfigs())
			{
				logger.info("There are invalid system configs, forwarding to system config page...");
				targetUrl = "system/systemConfig.html";
			}
			//只有后台用户longin时才审计
			setAudit(req, targetUrl);
		}
		
		addLoginedFlag(req, resp);
		//记录最后登录的时间
		AppUser appUser2=appUserManager.getById(appUser.getAppuserId());
		appUser2.setLastLoginTime(new Date());
		appUserManager.save(appUser2);
		try {
			resp.sendRedirect(resp.encodeRedirectURL(targetUrl));
			return null;
		} catch (Throwable e) {
			logger.error("Cannot sendRedirect to target page after logined: "
					+ targetUrl, e);
		}
		return null;
	}

	private String getTargetUrl(HttpServletRequest req, HttpSession session) {
		// retrieve saved requested url (saved by Spring Security)
		String targetUrl = null;
		// retrieve saved requested url (saved by Spring Security)
		if (session != null) {
			SavedRequest sq = (SavedRequest) session.getAttribute(WebAttributes.SAVED_REQUEST);
			if (sq != null) {
				targetUrl = sq.getRedirectUrl();
				session.removeAttribute(WebAttributes.SAVED_REQUEST);
			}
		}

		// if come from checkout , redirect checkout flow
		session.removeAttribute(Constants.FROM_CHECKOUT);
		if (session.getAttribute(Constants.CHECKOUT_TARGET_URL) != null) 
		{
			targetUrl = session.getAttribute(Constants.CHECKOUT_TARGET_URL).toString();
			session.removeAttribute(Constants.CHECKOUT_TARGET_URL);
		}
		// if no requested url, set to use default url
		if (targetUrl == null || "".equals(targetUrl)) {
			if (ContextUtil.isStoreFront())
				targetUrl = req.getContextPath() + "/myaccount/account.html";
			else
				targetUrl = req.getContextPath() + "/";
		}
		return targetUrl;
	}

	private void setAudit(HttpServletRequest req, String targetUrl) {
		// Audit login success action.
		String actionName = "true".equals(req.getParameter("isRememberMe")) ? 
		                AppAuditHelper.ACTION_REMEBER_ME_LOGIN : AppAuditHelper.ACTION_LOGIN;
		AppAuditHelper.getInstance().doAuditAction(actionName, 
		                req.getRemoteAddr(),
		                AppAuditHelper.RESULT_SUCCESS, 
		                targetUrl);
	}

	public void setShopPointHistoryManager(
			ShopPointHistoryManager shopPointHistoryManager) {
		this.shopPointHistoryManager = shopPointHistoryManager;
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