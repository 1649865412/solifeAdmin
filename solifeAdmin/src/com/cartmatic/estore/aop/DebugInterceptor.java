/*
 * Created on Jul 7, 2006
 * 
 */

package com.cartmatic.estore.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cartmatic.estore.webapp.util.RequestContext;

/**
 * @author Ryan
 * 
 */
public class DebugInterceptor implements MethodInterceptor {
	protected final transient Log	logger	= LogFactory.getLog(getClass());

	/**
	 * Return a description for the given method invocation.
	 * 
	 * @param invocation
	 *            the invocation to describe
	 * @return the description
	 */
	protected String getInvocationDescription(MethodInvocation invocation) {
		return "method '" + invocation.getMethod().getName() + "' of class ["
				+ invocation.getThis().getClass().getName() + "]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept.MethodInvocation)
	 */
	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		String invocationDescription = getInvocationDescription(methodInvocation);
		long t = System.currentTimeMillis();
		StringBuffer buf = new StringBuffer();
		buf.append("\n##Entering " + invocationDescription);
		buf.append("\n##User:").append(RequestContext.getCurrentUserId());
		buf.append("\n##RequestURL:").append(
				RequestContext.getCurrentRequestUrl());
		buf.append("\n##Parameters:");
		if (methodInvocation.getArguments() != null) {
			for (int i = 0; i < methodInvocation.getArguments().length; i++) {
				Object param = methodInvocation.getArguments()[i];
				buf.append("\n").append(param);
			}
		}

		Object returnObj = null;
		try {
			returnObj = methodInvocation.proceed();
			buf.append("\n##Returned Result:").append(returnObj);
		} catch (Throwable e) {
			buf.append("\n##Error Occurred!").append(e.getMessage());
			throw e;
		} finally {
			buf.append("\n##Process Time (in ms):").append(
					System.currentTimeMillis() - t);
			buf.append("\n##Exiting " + invocationDescription);
			logger.debug(buf.toString());
		}
		return returnObj;
	}
}
