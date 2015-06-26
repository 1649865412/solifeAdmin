/*
 * Created on Nov 4, 2006
 * 
 */

package com.cartmatic.estore.core.util;

import java.util.Date;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import com.opensymphony.oscache.base.Cache;
import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.web.ServletCacheAdministrator;

/**
 * Util for application scope cache.
 * 
 * @author Ryan
 * 
 */
public final class CacheUtil {
	private final static CacheUtil	instance	= new CacheUtil();

	private static final Log		logger		= LogFactory
														.getLog(CacheUtil.class);

	public final static CacheUtil getInstance() {
		return instance;
	}

	Cache						appScopeCache;

	CacheManager				cacheManager	= null;

	net.sf.ehcache.Cache		loginErrorCache	= null;

	ServletCacheAdministrator	sca				= null;

	ServletContext				servletContext;

	public void clearAllDataCaches() {
		cacheManager.clearAll();
		logger.debug("DataCache reloaded [OK].");
	}

	public void clearAllPageCache() {
		if (sca != null) {
			sca.flushAll();
			logger.debug("PageCache reloaded [OK].");
		}
	}

	public void clearAllServerSideCaches() {
		clearAllDataCaches();
		clearAllPageCache();
	}

	public Object getAppCache(String cacheKey) {
		Object cachedObj = null;
		try {
			cachedObj = appScopeCache.getFromCache(cacheKey);
		} catch (NeedsRefreshException e) {
			appScopeCache.cancelUpdate(cacheKey);
		}
		return cachedObj;
	}

	public Object getLoginErrorCache(String cacheKey) {
		Element element = loginErrorCache.get(cacheKey);
		return element == null ? null : element.getValue();
	}

	public void initEhCache(CacheManager in_cacheManager) {
		Assert.notNull(in_cacheManager);
		cacheManager = in_cacheManager;
		loginErrorCache = cacheManager.getCache("LoginErrorCache");
	}

	public void initOsCache(ServletContext in_servletContext) {
		Assert.notNull(in_servletContext);

		// load oscache.properties in a customized location
		Properties props = new Properties();
		try {
			props
					.load(in_servletContext
							.getResourceAsStream("/WEB-INF/classes/conf/oscache.properties"));
		} catch (Throwable e) {
			throw new RuntimeException("Initialize OsCache error!", e);
		}

		sca = ServletCacheAdministrator.getInstance(in_servletContext, props);
		servletContext = in_servletContext;
		appScopeCache = sca.getAppScopeCache(in_servletContext);
	}

	public void reloadPageCache() {
		appScopeCache.flushAll(new Date());
		logger.debug("PageCache reloaded [OK]:" );
	}

	public void setLoginErrorCache(String cacheKey, Object objToCache) {
		Assert.notNull(objToCache);

		Element element = new Element(cacheKey, objToCache);
		loginErrorCache.put(element);
	}
}
