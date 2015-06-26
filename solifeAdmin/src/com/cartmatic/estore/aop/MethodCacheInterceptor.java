/*
 * Copyright 2002-2004 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
/*
 * Modified by Legend on Sep 20, 2006
 * 
 */

package com.cartmatic.estore.aop;

import java.io.Serializable;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.cartmatic.estore.common.helper.ConfigUtil;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

/**
 * No more provide a parameter to enable/disable method cache. instead, remove
 * the bean from spring xml to disable cache.
 * 
 * @author Ryan
 * 
 */
public class MethodCacheInterceptor implements MethodInterceptor,
		InitializingBean {

	private Cache	cache;

	/**
	 * Checks if required attributes are provided.
	 */
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(cache,
				"A cache is required. Use setCache(Cache) to provide one.");
	}

	/**
	 * creates cache key: targetName.methodName.argument0.argument1...
	 */
	private String getCacheKey(String targetName, String methodName, Object[] arguments) 
	{
		StringBuffer sb = new StringBuffer();
		sb.append(targetName).append(".").append(methodName);
		if ((arguments != null) && (arguments.length != 0)) {
			for (int i = 0; i < arguments.length; i++) {
				sb.append(".").append(arguments[i]);
			}
		}

		return sb.toString();
	}

	/**
	 * main method caches method result if method is configured for caching
	 * method results must be serializable
	 */
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Object result;
		if (!ConfigUtil.getInstance().getIsServerSideDataCacheEnabled())
		{
			//开发模式下,不使用方法级别的cache
			result = invocation.proceed();
		}
		else
		{
			String targetName = invocation.getThis().getClass().getName();
			String methodName = invocation.getMethod().getName();
			Object[] arguments = invocation.getArguments();
			String cacheKey = getCacheKey(targetName, methodName, arguments);
			Element element = cache.get(cacheKey);
			if (element == null) {
				// call target/sub-interceptor
				result = invocation.proceed();
				if (result != null) {
					//cache method result
					element = new Element(cacheKey, (Serializable) result);
					cache.put(element);
				}
			} else {
				result = element.getValue();
			}
		}
		return result;
	}

	/**
	 * sets cache name to be used
	 */
	public void setCache(Cache cache) {
		this.cache = cache;
	}
}
