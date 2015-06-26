package com.cartmatic.estore.core.listener;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;

import com.cartmatic.estore.common.helper.AppAuditHelper;
import com.cartmatic.estore.customer.service.ShopPointHistoryManager;
import com.cartmatic.estore.webapp.util.RequestContext;
import com.cartmatic.estore.webapp.util.RequestUtil;

/**
 * 参考SavedRequestAwareAuthenticationSuccessHandler
 * 
 * TODO在这个handler类,实现postloginController的功能.
 * @author Administrator
 *
 */
public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler
{
	private ShopPointHistoryManager	shopPointHistoryManager;
	private RequestCache requestCache = new HttpSessionRequestCache();
	
	private void addLoginedFlag(HttpServletRequest req, HttpServletResponse resp) {
		// add logined flag in cookie
		RequestUtil.setUserInfoCookie(resp, RequestContext.getCurrentUser(), (req).getContextPath());
	}
	
	@Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws ServletException, IOException {
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		if (savedRequest == null) {
            super.onAuthenticationSuccess(request, response, authentication);

            return;
        }

        if (isAlwaysUseDefaultTargetUrl() || StringUtils.hasText(request.getParameter(getTargetUrlParameter()))) {
            requestCache.removeRequest(request, response);
            super.onAuthenticationSuccess(request, response, authentication);

            return;
        }

        clearAuthenticationAttributes(request);

        // Use the DefaultSavedRequest URL
        String targetUrl = savedRequest.getRedirectUrl();
        logger.debug("Redirecting to DefaultSavedRequest Url: " + targetUrl);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
        
		
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
	public void setRequestCache(RequestCache requestCache) {
        this.requestCache = requestCache;
    }
}
