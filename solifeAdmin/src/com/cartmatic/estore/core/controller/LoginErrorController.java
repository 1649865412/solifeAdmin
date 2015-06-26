/*
 * Created on Jun 16, 2006
 * 
 */

package com.cartmatic.estore.core.controller;

import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.servlet.ModelAndView;

import com.cartmatic.estore.common.helper.AppAuditHelper;
import com.cartmatic.estore.core.util.ContextUtil;

/**
 * @author Ryan
 * 
 */
public class LoginErrorController extends BaseController {

	
	public ModelAndView defaultAction(HttpServletRequest req,HttpServletResponse resp) throws ServletException {
		// login error, audit event then forward to error page
		Exception e = (Exception) req.getSession().getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		if (e != null) {
			String failedAccount = null;
			if (e instanceof AuthenticationException) {
				try {
					failedAccount = ((AuthenticationException) e)
							.getAuthentication().getPrincipal().toString();
				} catch (Throwable t) {
					logger.error("Error retrieving login failure info from Spring Security AuthenticationException",
								t);
					failedAccount = "ERROR Parsing Login Name";
				}
			}

			String targetUrl = null;
			// retrieve saved requested url (saved by Spring Security)
			HttpSession session = req.getSession();
			if (session != null) {
				SavedRequest sq = (SavedRequest) session
						.getAttribute(WebAttributes.SAVED_REQUEST);
				if (sq != null) {
					targetUrl = sq.getRedirectUrl();
				}
			}

			// if no requested url, set to use default url
			if (targetUrl == null || "".equals(targetUrl)) {
				targetUrl = req.getContextPath() + "/";
			}

			// Audit login failure event.
			AppAuditHelper.getInstance().doAuditAction(AppAuditHelper.ACTION_LOGIN, 
			                req.getRemoteAddr(), AppAuditHelper.RESULT_FAILED, targetUrl);
//			appAuditManager.doAuditAction("Login", failedAccount,
//					Constants.AUDIT_FAILED, targetUrl);

			// redirect to requested url or default url
			try {

				if (ContextUtil.isStoreFront()) {
					req.getRequestDispatcher("/signup.html?error=true").forward(req, resp);
				} else {
					req.getRequestDispatcher("login.html?error=true").forward(req, resp);
				}
			} catch (Throwable ex) {
				logger.error("Cannot forward to error login page: " + targetUrl, ex);
			}
		}
		return null;
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
