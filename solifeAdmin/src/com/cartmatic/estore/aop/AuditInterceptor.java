/*
 * Created on Jul 7, 2006
 * 
 */

package com.cartmatic.estore.aop;

import java.util.Collection;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.cartmatic.estore.common.helper.AppAuditHelper;
import com.cartmatic.estore.common.model.system.AuditInfo;
import com.cartmatic.estore.common.model.system.Auditable;
import com.cartmatic.estore.core.model.BaseObject;
import com.cartmatic.estore.core.util.UrlUtil;
import com.cartmatic.estore.webapp.util.RequestContext;

/**
 * @author Ryan
 * 
 */
public class AuditInterceptor implements MethodInterceptor {
	//private AppAuditManager	appAuditManager;

	/*
	 * <pre> Audit very simple action, only audit actionName, procObj,
	 * SUCCESS/FAILED, requestUrl, etc. Use AuditHelper instead to audit more
	 * detailed info. </pre>
	 * 
	 * @see org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept.MethodInvocation)
	 */
	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		// logger.debug("entering MethodInterceptor ......
		// "+methodInvocation.getClass().getName()+"."+methodInvocation.getMethod().getName());
		String result = AppAuditHelper.RESULT_SUCCESS;
		try {
			return methodInvocation.proceed();
		} catch (Throwable e) {
			result = AppAuditHelper.RESULT_FAILED;
			throw e;
		} finally {

			Object[] args = methodInvocation.getArguments();
			String actionName = methodInvocation.getMethod().getName();
			String requestUrl = RequestContext.getCurrentRequestUrl();

			// holds the detailed audit info
			StringBuffer buf = new StringBuffer();

			if (args != null) {
				if (args.length == 1) {
					Object entity = args[0];
					if (entity instanceof Auditable) {
						// An auditable entity, use detailed info from
						// getAuditInfo
						AuditInfo auditInfo = ((Auditable) entity)
								.getAuditInfo(actionName);
						if (auditInfo != null) {
							if (auditInfo.getActionNameMappings() != null
									&& auditInfo.getActionNameMappings().get(
											actionName) != null) {
								actionName = auditInfo.getActionNameMappings()
										.get(actionName);
							}

							if (requestUrl != null) {
								if (auditInfo.getSupplementUrl() != null) {
									if (requestUrl.indexOf("?") == -1) {
										requestUrl = requestUrl + "?";
									} else {
										requestUrl = requestUrl + "&";
									}
									requestUrl = requestUrl
											+ auditInfo.getSupplementUrl();
								} else {
									requestUrl = UrlUtil.appendParamToUrl(
											requestUrl, ((BaseObject) entity)
													.getFirstKeyColumnName(),
											"" + ((BaseObject) entity).getId());
								}
							}

							if (auditInfo.getEntityType() != null) {
								buf.append("[").append(
										auditInfo.getEntityType())
										.append("]: ");
							}
							buf.append(auditInfo.getEntityAuditInfo());

						}
					} else if (entity instanceof Collection) {
						Collection col = (Collection) entity;
						buf.append("Bulk update: " + col.size() + " "
								+ col.toString());
					} else {
						// usually is a BaseObject or some other POJO, apply
						// default seetings to get entity info
						buf.append(entity);
					}
				} else {
					// more than one arguments
					buf.append("Multiple arguments: " + args);
				}
			}// else no more detailed info need to audit

			if (buf.length() > 1000) {
				buf.delete(1000, buf.length());
				buf.append("...");
			}

			if (buf.length() > 10) {
			    AppAuditHelper.getInstance().doAuditAction(actionName, 
			                    buf.toString(),
			                    result, requestUrl);
				//appAuditManager.doAuditAction(actionName, buf.toString(),
				//		result, requestUrl);
			}

			// logger.debug("leaving MethodInterceptor ...... "
			// +methodInvocation.getClass().getName()+"."+methodInvocation.getMethod().getName());
		}

	}

//	public void setAppAuditManager(AppAuditManager appAuditManager) {
//		this.appAuditManager = appAuditManager;
//	}

}
