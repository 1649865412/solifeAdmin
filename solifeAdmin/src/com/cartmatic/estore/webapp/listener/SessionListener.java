/*
 * Created on Jul 11, 2006
 * 
 */

package com.cartmatic.estore.webapp.listener;

import java.util.Enumeration;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;

/**
 * Perform the following tasks: 1.Logging or auditing (login,logout,timeout)
 * 2.Count active user, etc 3.Persist user click stream to db when
 * logout/timeout
 * 
 * @author Ryan
 * 
 */
public class SessionListener implements HttpSessionListener {
	private final static transient Log	logger = LogFactory.getLog(SessionListener.class);

	final static AuthenticationTrustResolver resolver = new AuthenticationTrustResolverImpl();

	//private AppAuditManager						appAuditManager;

//	protected AppAuditManager getAppAuditManager() {
//		if (appAuditManager == null) {
//			appAuditManager = (AppAuditManager) RequestContext.getSpringBeanById("appAuditManager");
//		}
//		return appAuditManager;
//	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSessionListener#sessionCreated(javax.servlet.http.HttpSessionEvent)
	 */
	public void sessionCreated(HttpSessionEvent event) {
		/**
		 * Login auditing already be processed in PostLoginController and
		 * RemembermeFilter. Session get created before Spring Security take action.
		 */
	}

	/*
	 * Note, logout is handled in LogoutHandler first, timeout will come
	 * directly here
	 * 
	 * @see javax.servlet.http.HttpSessionListener#sessionDestroyed(javax.servlet.http.HttpSessionEvent)
	 */
	public void sessionDestroyed(HttpSessionEvent event) {
		if (logger.isDebugEnabled()) {
			StringBuilder sb = new StringBuilder("Remained Session Attributes:\n");
			Enumeration enums = event.getSession().getAttributeNames();
			while (enums.hasMoreElements()) {
				sb.append(enums.nextElement()).append("\n");
			}
			logger.debug(sb.toString());
		}
	}
}
