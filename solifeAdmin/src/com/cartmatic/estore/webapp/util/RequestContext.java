
package com.cartmatic.estore.webapp.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.WebInvocationPrivilegeEvaluator;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.common.helper.ConfigUtil;
import com.cartmatic.estore.common.model.system.AdminInfo;
import com.cartmatic.estore.common.model.system.AppAdmin;
import com.cartmatic.estore.core.model.AppUser;
import com.cartmatic.estore.core.model.Message;
import com.cartmatic.estore.core.util.ContextUtil;
import com.cartmatic.estore.system.service.AppAdminManager;
import com.opensymphony.oscache.util.StringUtil;

/**
 * 
 * 与Web运行时环境绑定（线程HttpServletRequest绑定），对于不是Web环境时（后台任务、单元测试），通过缺省值进行兼容。
 * 
 * @author Ryan
 * 
 */
public final class RequestContext 
{
	private static ThreadLocal<HttpServletRequest>	currentHttpRequestHolder	= new ThreadLocal<HttpServletRequest>();
	// Default is anonymous user, whose id is -2
	private final static Integer defaultUserId = Constants.USERID_ANONYMOUS;

	private final static String	defaultUserName	= "ANONYMOUS User";

	private static final Log logger = LogFactory.getLog(RequestContext.class);

	final static AuthenticationTrustResolver resolver = new AuthenticationTrustResolverImpl();

	private final static String	systemUserName = "SYSTEM";

	public static void clearCurrentHttpRequest() {
		currentHttpRequestHolder.set(null);
	}

	public static HttpServletRequest getCurrentHttpRequest() {
		return currentHttpRequestHolder.get();
	}

	private static HttpSession getCurrentHttpSession() {
		return getCurrentHttpRequest() == null ? null : getCurrentHttpRequest()
				.getSession(true);
	}

	public static ServletContext getServletContext() {
        return ContextUtil.getServletContext();
    }
	/**
	 * 
	 * @return
	 */
	public static String getCurrentRequestUrl() {
		HttpServletRequest request = getCurrentHttpRequest();
		if (request != null) {
			return RequestUtil.getFullRequestUrl(request);
		}
		return null;
	}

	/**
	 * 
	 * @return
	 */
	public static String getCurrentTemplatePath()
	{
		return "/WEB-INF/templates/" + getCurrentStoreCode();
	}
	
	public static String getDefaultTemplatePath()
	{
		return "/WEB-INF/templates/" + Constants.STORE_DEFAULT_CODE;
	}
	
	public static String getCurrentStoreCode()
	{
		if (getCurrentHttpRequest() == null)
			return Constants.STORE_DEFAULT_CODE;
		String storeCode = ConfigUtil.getInstance().getStoreCode(getCurrentHttpRequest().getServerName());
		if (StringUtil.isEmpty(storeCode))
			storeCode = Constants.STORE_DEFAULT_CODE;
		return storeCode;
	}
	
	/**
	 * 通过Acegi的SecurityContextHolder获取当前用户，所以必须在Acegi的上下文环境内才能返回正确的用户，而且一般在web请求环境内。
	 * 
	 * @return
	 */
	public static AppUser getCurrentUser() {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		if (securityContext != null) {
			Authentication auth = securityContext.getAuthentication();
			if (auth != null) {
				Object principal = auth.getPrincipal();
				if (principal instanceof AppUser) {
					return (AppUser) principal;
				}
			} else if (logger.isDebugEnabled()) {
				logger.debug("WARN: SecurityContext not found in SecurityContextHolder. Not in a Spring Security context?");
			}
		}

		return null;
	}
	
	public static boolean authorizeUsingUrlCheck(String url) 
	{
		SecurityContext securityContext = SecurityContextHolder.getContext();
		if (securityContext != null) 
		{
			Authentication auth = securityContext.getAuthentication();
			try
			{
				return getPrivilegeEvaluator().isAllowed(getCurrentHttpRequest().getContextPath(), url, null, auth);
			}
			catch (JspException e)
			{
				// TODO Auto-generated catch block
				logger.warn(e);
				e.printStackTrace();
			}
		}
		return false;
	}
	
	private static WebInvocationPrivilegeEvaluator getPrivilegeEvaluator() throws JspException {
        //ServletContext servletContext = pageContext.getServletContext();
        ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(ContextUtil.getServletContext());
        Map<String, WebInvocationPrivilegeEvaluator> wipes = ctx.getBeansOfType(WebInvocationPrivilegeEvaluator.class);
        if (wipes.size() == 0) {
            throw new JspException("No visible WebInvocationPrivilegeEvaluator instance could be found in the application " +
                    "context. There must be at least one in order to support the use of URL access checks in 'authorize' tags.");
        }
        return (WebInvocationPrivilegeEvaluator) wipes.values().toArray()[0];
    }

	public static Integer getCurrentUserId() {
		return getCurrentUser() != null ? getCurrentUser().getAppuserId()
				: defaultUserId;
	}

	public static Integer getCurrentUserIdDefaultSystem() {
		return getCurrentUser() != null ? getCurrentUser().getAppuserId()
				: Constants.USERID_SYSTEM;
	}

	public static String getCurrentUserName() {
		return getCurrentUser() != null ? getCurrentUser().getFullName()
				: defaultUserName;
	}

	public static String getCurrentUserNameDefaultSystem() {
		return getCurrentUser() != null ? getCurrentUser().getFullName()
				: systemUserName;
	}

	public static String getRelativeTemplatePath(String templatePath) 
	{
		//return new StringBuilder(Constants.PAGES_PREFIX).append(templatePath).append(".jsp").toString();
		return new StringBuilder(getCurrentTemplatePath()).append(Constants.PAGES_PREFIX).append(templatePath).append(".jsp").toString();
	}

	public static int getSessionMessageCount() {
		List<String> messages = (List<String>) getCurrentHttpSession()
				.getAttribute("messages");
		return messages == null ? 0 : messages.size();
	}

	public static boolean isAnonymousUser() {
		return getCurrentUser() == null;
	}

	/**
	 * TODO, move to RequestUtil
	 * 
	 * @param request
	 * @param msg
	 */
	public static void saveSessionMessage(HttpServletRequest request, Message message) {
		HttpSession session = request.getSession(true);
		List<Message> messages = (List<Message>) session.getAttribute("messages");

		if (messages == null) {
			messages = new ArrayList<Message>();
		} else {
			session.removeAttribute("messages");
		}

		messages.add(0, message);
		session.setAttribute("messages", messages);
	}

	public static void saveSessionMessage(Message message) {
		saveSessionMessage(getCurrentHttpRequest(), message);
	}

	/**
	 * 
	 * @param requestUrl
	 */
	public static void setCurrentHttpRequest(HttpServletRequest request) {
		if (currentHttpRequestHolder.get() != null) {
			logger.error("Duplicate call to setCurrentHttpRequest, same request? "
						+ request.equals(currentHttpRequestHolder.get()
						+ " URL is:" + request.getRequestURL()));
		} else {
			currentHttpRequestHolder.set(request);
		}
	}
	
	public static Object getSpringBeanById(String beanId) {
        return ContextUtil.getSpringBeanById(beanId);
    }
	
	/*public static ApplicationContext getSpringContext() {
        return ContextUtil.getSpringContext() ;
    }
	
	public static boolean isStoreFront() {
        return ContextUtil.isStoreFront();
    }*/
	
	public static AdminInfo getAdminInfo() 
	{
		HttpSession session = getCurrentHttpRequest().getSession(true);
		AdminInfo adminInfo = (AdminInfo) session.getAttribute(Constants.KEY_ADMIN_INFO);
		if(adminInfo==null)
		{
			AppAdminManager appAdminManager=(AppAdminManager)ContextUtil.getSpringBeanById("appAdminManager");
			AppAdmin appAdmin=appAdminManager.getById(getCurrentUserId());
			if(appAdmin!=null&&appAdmin.getAdminInfo()!=null&&appAdmin.getAdminInfo().getPagingSetting()!=null)
			{
				adminInfo = appAdmin.getAdminInfo();
				session.setAttribute(Constants.KEY_ADMIN_INFO, adminInfo);
			}
		}
		if (adminInfo == null)
			adminInfo = new AdminInfo();
        return adminInfo;
    }
	
	
}
