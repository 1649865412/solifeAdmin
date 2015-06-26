/*
 * Created on Aug 29, 2006
 * 
 */

package com.cartmatic.estore.core.util;

import java.io.File;
import java.util.Enumeration;

import javax.servlet.ServletContext;

import net.sf.ehcache.CacheManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.intercept.RunAsUserToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.util.Assert;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.common.helper.ConfigUtil;
import com.cartmatic.estore.common.model.system.AppUser;
import com.cartmatic.estore.core.event.ApplicationEvent;
import com.cartmatic.estore.sales.engine.PRuleManager;
import com.cartmatic.estore.system.service.AppEventManager;

/**
 * 对整个应用环境的集中管理，包括初始化，刷新，和访问。
 * 
 * @author Ryan
 * 
 */
public final class ContextUtil 
{
	private final static ContextUtil instance = new ContextUtil();

	private AppContextLoader appContextLoader = null;

    private AppEventManager appEventManager;
    private PRuleManager pruleManager; 
    private boolean isStoreFront = false;
    private boolean isSearchServer = false;
    private boolean isWebService = false;

    private final Log logger = LogFactory.getLog(ContextUtil.class);

    private ServletContext servletContext = null;

    private ApplicationContext springContext = null;

    private ContextUtil() {}
    
	public final static ContextUtil getInstance() {
		return instance;
	}

	public static ServletContext getServletContext() {
		return getInstance().servletContext;
	}

	public static Object getSpringBeanById(String beanId) {
		return getInstance().springContext.getBean(beanId);
	}

	public static ApplicationContext getSpringContext() {
		return getInstance().springContext;
	}

	public static boolean isStoreFront() {
		return getInstance().isStoreFront;
	}
	
	public static boolean isSearchServer() {
		return getInstance().isSearchServer;
	}
	
	public static boolean isWebService() {
		return getInstance().isWebService;
	}

	private void autoStartScheduler() {
		if (ConfigUtil.getInstance().getIsAutoStartScheduler()) {
			startScheduler();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.webapp.util.AppContextLoader#cleanup()
	 */
	public void cleanup() {
		debugContext();
		if (appContextLoader != null) {
			appContextLoader.cleanup();
		}
		this.appEventManager = null;
		this.servletContext = null;
		this.springContext = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.webapp.util.AppContextLoader#debugContext()
	 */
	public void debugContext() {
		logger.info(System.getProperties());
		logger.info(System.getenv());
		if (springContext != null) {
			logger.info(springContext.toString());
		}

		if (servletContext != null) 
		{
			logger.info("!!!!!!!!!!Debuging ServletContext.getAttributeNames()!!!!!!!!!!");
			Enumeration enumeration = servletContext.getAttributeNames();
			while (enumeration.hasMoreElements()) 
			{
				String name = (String) enumeration.nextElement();
				logger.info("ServletContext Attributes - " + name + ": ["
						+ servletContext.getAttribute(name) + "].");
			}
			logger.info("!!!!!!!!!!Debuging ServletContext.getInitParameterNames()!!!!!!!!!!");
			enumeration = servletContext.getInitParameterNames();
			while (enumeration.hasMoreElements()) 
			{
				String name = (String) enumeration.nextElement();
				logger.info("ServletContext InitParameter - " + name + ": ["
						+ servletContext.getInitParameter(name) + "].");
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.webapp.util.AppContextLoader#fireApplicationEvent(com.cartmatic.estore.core.event.ApplicationEvent)
	 */
	public void fireApplicationEvent(ApplicationEvent event) {
	    appEventManager.fireApplicationEvent(event);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.webapp.util.AppContextLoader#getCacheManager()
	 */
	public CacheManager getCacheManager() {
		return (CacheManager) springContext.getBean("cacheManager");
	}

	private Scheduler getScheduler() {
		return (Scheduler) springContext.getBean("scheduler");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.webapp.util.AppContextLoader#getSessionFactory()
	 */
	public SessionFactory getSessionFactory() {
		return (SessionFactory) springContext.getBean("sessionFactory");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.webapp.util.AppContextLoader#getStartupTime()
	 */
	public long getStartupTime() {
		return this.springContext.getStartupDate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.webapp.util.AppContextLoader#handleApplicationEvent(com.cartmatic.estore.core.event.ApplicationEvent)
	 */
	/*public void handleApplicationEvent(ApplicationEvent event) {
		this.applicationEventHandler.handleApplicationEvent(event);
	}*/

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.webapp.util.AppContextLoader#init(javax.servlet.ServletContext)
	 */
	public void init(ServletContext servletContext) {
		this.servletContext = servletContext;
		this.springContext = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
		this.isStoreFront = (servletContext.getInitParameter("isFront") != null);
		this.isSearchServer = (servletContext.getInitParameter("isSearchServer") != null);
		this.isWebService = (servletContext.getInitParameter("isWebService") != null);

		// register a RunAs system user
		SecurityContext sci = new SecurityContextImpl();
		AppUser systemUser = new AppUser();
		systemUser.setAppuserId(Constants.USERID_SYSTEM);
		systemUser.setUsername("system");
		sci.setAuthentication(new RunAsUserToken("SystemUser", systemUser, null, new GrantedAuthority[]{}, null));
		SecurityContextHolder.setContext(sci);

		if (!isStoreFront && !isSearchServer && !isWebService) 
		{
			((ConfigRegistry) getSpringBeanById("configRegistry")).registerConfigs();
		}
		if (isSearchServer)
		{
			//setup the solrHome dir.
			System.setProperty("solr.solr.home", ConfigUtil.getInstance().getAssetsPath()+"/solrHome");
		}
		ConfigUtil.getInstance().setIsStoreFront(isStoreFront);
		ConfigUtil.getInstance().setWebAppRootPath(servletContext.getRealPath("/"));

		appEventManager = (AppEventManager) springContext.getBean("appEventManager");
		Assert.notNull(appEventManager,	"appEventManager not found!");
		pruleManager = (PRuleManager)springContext.getBean("pruleManager");
		
		pruleManager.init();
		
		initAppContextLoader();

		initVelocity();

		autoStartScheduler();
	}

	protected void initAppContextLoader() {
		this.appContextLoader = (AppContextLoader) getSpringBeanById("appContextLoader");
		appContextLoader.setServletContext(servletContext);
		appContextLoader.setSpringContext(springContext);
		appContextLoader.reloadContext();
	}

	/**
	 * 需要根据系统配置来创建和初始化VelocityEngine，所以这里使用定制的初始化过程
	 * 
	 */
	private void initVelocity() {
		VelocityUtil velocityUtil = ((VelocityUtil) getSpringBeanById("velocityUtil"));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.webapp.util.AppContextLoader#isFileExists(java.lang.String)
	 */
	public boolean isFileExists(String relativeFilePath) {
		return new File(servletContext.getRealPath(relativeFilePath)).exists();
	}

	//public void reloadMenu() {
	//	appContextLoader.reloadMenu();
	//}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.webapp.util.AppContextLoader#shutdownScheduler()
	 */
	public void shutdownScheduler() {
		try {
			getScheduler().shutdown();
		} catch (SchedulerException e) {
			logger.error("Shutdown Scheduler Error!", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.webapp.util.AppContextLoader#startScheduler()
	 */
	public void startScheduler() {
		try {
			SchedulerUtil.startupScheduler(getScheduler());
		} catch (SchedulerException e) {
			throw new RuntimeException("Start Scheduler Error!", e);
		}

		/*
		 * try { if (!getScheduler().isStarted()) { getScheduler().start(); } }
		 * catch (SchedulerException e) { throw new RuntimeException("Start
		 * Scheduler Error!", e); }
		 */
	}
}
