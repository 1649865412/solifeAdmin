/*
 * Created on Jul 11, 2006
 * 
 */

package com.cartmatic.estore.core.listener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.common.helper.AppAuditHelper;
import com.cartmatic.estore.common.model.system.AppUser;

/**
 * @author Ryan
 * 
 */
public class AuthenticationListener implements ApplicationListener {
	private final static transient Log	logger	= LogFactory
														.getLog(AuthenticationListener.class);

	//AppAuditManager						appAuditManager;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.context.ApplicationListener#onApplicationEvent(org.springframework.context.ApplicationEvent)
	 */
	public void onApplicationEvent(ApplicationEvent event) {
		if (event instanceof AbstractAuthenticationEvent) {
			try {
				AbstractAuthenticationEvent authEvent = (AbstractAuthenticationEvent) event;

				if (event instanceof AuthenticationSuccessEvent) {
				    AppAuditHelper.getInstance().doAuditAction(AppAuditHelper.ACTION_LOGIN, 
				                    event.toString(),
				                    AppAuditHelper.RESULT_SUCCESS,
				                    null,
				                    ((AppUser) authEvent.getAuthentication().getPrincipal()).getAppuserId());
//					appAuditManager.doAuditAction("Login", event.toString(),
//							Constants.AUDIT_SUCCESS, null, ((AppUser) authEvent
//									.getAuthentication().getPrincipal())
//									.getAppuserId(), null);
				} else if (event instanceof AbstractAuthenticationFailureEvent) {
				    AppAuditHelper.getInstance().doAuditAction(AppAuditHelper.ACTION_LOGIN, 
                                    event.toString(),
                                    AppAuditHelper.RESULT_FAILED,
                                    null,
                                    Constants.USERID_ANONYMOUS);
//					appAuditManager.doAuditAction("Login", event.toString(),
//							Constants.AUDIT_FAILED, null,
//							Constants.USERID_ANONYMOUS, null);
				}

			} catch (Throwable e) {
				logger.error("Cannot audit Login event:" + event.toString(), e);
			}
		}

	}

//	public void setAppAuditManager(AppAuditManager appAuditManager) {
//		this.appAuditManager = appAuditManager;
//	}
}
