/*
 * Created on Sep 4, 2006
 * 
 */

package com.cartmatic.estore.core.filter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.FilterConfig;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cartmatic.estore.common.helper.ConfigUtil;
import com.cartmatic.estore.core.util.CacheUtil;
import com.cartmatic.estore.core.util.StringUtil;
import com.cartmatic.estore.webapp.util.RequestContext;
import com.opensymphony.oscache.base.Cache;
import com.opensymphony.oscache.web.ServletCacheAdministrator;

/**
 * @author Ryan
 * 
 */
public class CacheFilter extends
		com.opensymphony.oscache.web.filter.CacheFilter {

	private static final Log	logger	= LogFactory.getLog(CacheFilter.class);

	/**
	 * Only specified uris defined here will be cached. Note: we assume
	 * CacheFilter will take effect after The UrlRewriteFilter, so don't put the
	 * 'zh_CN' like context in uris.
	 */
	//public String[]	cachableUris;
	
	private Map<String,ArrayList<Pattern>> matchPatterns = new HashMap<String,ArrayList<Pattern>>();  

	/**
	 * set the storeCode for generate key.
	 */
	public String createCacheKey(HttpServletRequest httpRequest, ServletCacheAdministrator scAdmin, Cache cache) 
	{
        return scAdmin.generateEntryKey(null, httpRequest, super.getCacheScope(), RequestContext.getCurrentStoreCode());
    }
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.oscache.web.filter.CacheFilter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig config) {
		// use customized initilization first to load out oscache.properties
		CacheUtil.getInstance().initOsCache(config.getServletContext());
		super.init(config);
		onConfigChanged();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * Customized cache logic: Donot cache post requests. Infact, we only cache
	 * defined urls.
	 * 
	 * @see com.opensymphony.oscache.web.filter.CacheFilter#isCacheable(javax.servlet.ServletRequest)
	 */
	public boolean isCacheable(ServletRequest request) {
		boolean cachable = false;
		if (getTime() > 0) {
			/**
			 * some AJAX application use post to get the page, need to specify a
			 * cache=true parameter to enable cache. Known url includes:
			 * /sales/showRecommended, /content/adPublish.html.
			 */
			if ("true".equals(request.getParameter("cache"))) 
			{
				cachable = true;
			}
			else if (request.getParameter("noCache") == null && (request instanceof HttpServletRequest)) 
			{
				HttpServletRequest req = (HttpServletRequest) request;
				if (!req.isRequestedSessionIdFromURL() && req.getMethod().equalsIgnoreCase("GET")) 
				{
					ArrayList<Pattern> patterns = matchPatterns.get(RequestContext.getCurrentStoreCode());
					for (Pattern p : patterns) 
					{
						if (p.matcher(req.getRequestURI()).matches()) 
						{
							cachable = true;
							break;
						}
					}
				}
			}
		}

		return cachable;
	}

	public void onConfigChanged() {
		logger.info("Updating server side page cache time settings from: "
					+ getTime() + " to: "
					+ ConfigUtil.getInstance().getServerSidePageCacheSeconds());
		setTime(ConfigUtil.getInstance().getServerSidePageCacheSeconds());
		logger.info("Enable server side page caching? " + (getTime() > 0));
		logger.info("Updating browser side page cache time settings from: " + getCacheControlMaxAge() + " to: " + ConfigUtil.getInstance().getBrowserSidePageCacheSeconds());
		setCacheControlMaxAge(ConfigUtil.getInstance().getBrowserSidePageCacheSeconds());
		//logger.warn("Updating cachable urls: " + ConfigUtil.getInstance().getCachableUrls());
		String[] cachableUris = StringUtil.toArrayByDel(ConfigUtil.getInstance().getCachableUrls(), ",");
		Collection<String> stores = ConfigUtil.getInstance().getAllStoreCodes();
		for (String code: stores)
		{
			ArrayList<Pattern> patterns = matchPatterns.get(code);
			if (patterns == null)
			{
				patterns = new ArrayList<Pattern>(); 
				matchPatterns.put(code, patterns);
			}
			if (cachableUris != null)
			{
				for (String uri : cachableUris)
				{
					uri = "^"+uri.replace("*", ".*");
					Pattern p = Pattern.compile(uri);
					patterns.add(p);
					logger.info("URI cache match pattern :"+uri);
				}
			}
		}
	}
}
