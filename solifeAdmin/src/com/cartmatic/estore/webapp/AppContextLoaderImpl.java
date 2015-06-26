/*
 * Created on Jan 16, 2008
 * 
 */

package com.cartmatic.estore.webapp;

import java.util.Iterator;

import javax.servlet.ServletContext;

import net.sf.ehcache.CacheManager;
import net.sf.navigator.menu.MenuRepository;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.RememberMeAuthenticationProvider;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.common.helper.ConfigUtil;
import com.cartmatic.estore.common.model.customer.Membership;
import com.cartmatic.estore.core.util.AppContextLoader;
import com.cartmatic.estore.core.util.CacheUtil;
import com.cartmatic.estore.core.util.ContextUtil;
import com.cartmatic.estore.core.view.MailEngine;
import com.cartmatic.estore.customer.service.MembershipManager;
import com.cartmatic.estore.system.service.CurrencyManager;

public class AppContextLoaderImpl implements AppContextLoader {
	//private java.util.Timer		contextTimer	= null;

	//private String				licenseKey		= null;

	private final Log			logger			= LogFactory
														.getLog(AppContextLoaderImpl.class);

	private ServletContext		servletContext	= null;

	private ApplicationContext	springContext	= null;

	public void cleanup() {
		//if (this.contextTimer != null) {
		//	this.contextTimer.cancel();
		//	this.contextTimer = null;
		//}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.webapp.util.AppContextLoader#reloadCompanyInfo()
	 */
	//public void reloadCompanyInfo() {
	//	CompanyInfoManager companyInfoManager = (CompanyInfoManager) springContext.getBean("companyInfoManager");
	//	CompanyInfo companyInfo =companyInfoManager.getDefaultCompany();
	//	servletContext.setAttribute("companyInfo",companyInfo!=null?companyInfo:new CompanyInfo());

	//}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.webapp.util.AppContextLoader#reloadConfig()
	 */
	public void reloadConfig() {
		// 从Spring Security 的bean配置读取是否允许记住我自动登录功能
		try {
			boolean rememberMeEnabled = false;
			ProviderManager provider = (ProviderManager) springContext.getBean("authenticationManager");
			
			for (Iterator it = provider.getProviders().iterator(); it.hasNext();) {
				AuthenticationProvider p = (AuthenticationProvider) it.next();
				if (p instanceof RememberMeAuthenticationProvider) {
					rememberMeEnabled = true;
				}
			}
			ConfigUtil.getInstance().setRememberMeEnabled(rememberMeEnabled);
		} catch (NoSuchBeanDefinitionException n) {
			logger.warn("NoSuchBeanDefinitionException, ignore.");
		}

		ConfigUtil.getInstance().checkConfigAtStartup();
		servletContext.setAttribute(Constants.CONFIG, ConfigUtil.getInstance());
		logger.debug("Config reloaded [OK].");
	}

	/**
	 * Will be called by reloadController, can reload application context.
	 * 
	 * @param context
	 */
	public void reloadContext() {
		logger.info("Start reloading application context...");

		// check license
		//SystemVersionManager svm = (SystemVersionManager) springContext.getBean("systemVersionManager");
		//if (svm.getSystemVersion() == null) {
		//	throw new LicenseNotFoundException();
		//}
		//licenseKey = svm.getSystemVersion().getLicenseKey();

		/*
		 * 屏蔽check Licence
		 if (ContextUtil.isStoreFront() && contextTimer == null) {
			long __delay = 13 * 60 * 60 * 1000;
			// long __delay = 13;
			long __period = 57 * 60 * 60 * 1000;
			// long __period = 300*1000;
			contextTimer = new java.util.Timer();
			contextTimer.schedule(new java.util.TimerTask() {
				@Override
				public void run() {
					try {
						URL url = new URL(
								"http://www.cartmatic.com/onlineCheck.html?licenseKey="
										+ licenseKey);
						// URL url = new
						// URL("http://192.168.16.226:8280/onlineCheck.html?licenseKey="+licenseKey);
						HttpURLConnection connection = (HttpURLConnection) url
								.openConnection();
						String chReq = connection
								.getHeaderField("cartmaticLicense");
						connection.disconnect();
						SystemConfigManager confMgr = (SystemConfigManager) springContext
								.getBean("systemConfigManager");
						String alert = confMgr.getConfigAsMap().get(
								"IsAlertLicense");
						if ("false".equals(chReq))// Returns false. it is
						// going to alert current
						// system.
						{
							if (alert != null && alert.equals("false")) {
								confMgr.saveConfigByKey("IsAlertLicense",
										"true");// alert it.
							}
						} else // Returns true. Checking passed.
						{
							if (alert != null && alert.equals("true")) {
								confMgr.saveConfigByKey("IsAlertLicense",
										"false");// Don't alert it.
							}
						}
					} catch (Exception e) {
						// e.printStackTrace();
						// do nothing.
					}
				}
			}, __delay, __period);
			// todo check online.
		}*/

		// check license end

		// when reloading, config should be the first, other modules depend on
		// it
		reloadConfig();
		reloadMenu();
		reloadCurrency();
		//reloadLanguage();
		reloadMembership();
		reloadMailEngine();
		//reloadCompanyInfo();
		// reloadAllI18nMessages();
		// add by csx deploy the workflow. TODO here 2006-06-20

		CacheUtil.getInstance().initEhCache((CacheManager) ContextUtil.getSpringBeanById(("cacheManager")));

		if (logger.isDebugEnabled()) {
			ContextUtil.getInstance().debugContext();
		}

		logger.info("Reloading application context completed [OK].");
	}
	

	public void reloadMailEngine()
	{
		MailEngine mgr = (MailEngine) springContext.getBean("mailEngine");
		mgr.init();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.webapp.util.AppContextLoader#reloadMembership()
	 */
	public void reloadMembership() {
		// load default membership to context
		MembershipManager membershipManager = (MembershipManager) springContext.getBean("membershipManager");
		Membership membership = membershipManager.getAnonymousMembership();
		servletContext.setAttribute(Constants.MEMBERSHIP_ANONYMOUS, membership);
		if (logger.isDebugEnabled()) {
			logger.debug("Membership reloaded [OK]. membership=" + membership);
		}
	}

	/**
	 * 载入币种,只有前台才需要.
	 */
	public void reloadCurrency()
	{
		if (ContextUtil.isStoreFront()) {
			CurrencyManager mgr = (CurrencyManager) springContext.getBean("currencyManager");
			servletContext.setAttribute(Constants.ENABLE_CURRENCYS, mgr.loadEnableCurrency());
			if (logger.isDebugEnabled()) {
				logger.debug("ENABLE_CURRENCYS reloaded [OK].");
			}
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.webapp.util.AppContextLoader#reloadMenu()
	 */
	public void reloadMenu() {
		// Setting up MenuRepository
		if (logger.isDebugEnabled()) {
			logger.debug("Starting struts-menu initialization");
		}

		try {
			if (ContextUtil.isStoreFront() || ContextUtil.isSearchServer() || ContextUtil.isWebService()) {
				//CategoryManager categoryManager = (CategoryManager) springContext.getBean("categoryManager");
				//TODO 将所有目录放在application (目录应该没有用上)
				/*ColumnRepository productColumnRepository = categoryManager
						.getColumnRespositoryByCode("product");
				servletContext.setAttribute("productColumn", productColumnRepository);*/
			} else {
				String menuConfig = "/WEB-INF/classes/conf/menu-config.xml";
				MenuRepository repository = new MenuRepository();
				repository.setLoadParam(menuConfig);
				repository.setServletContext(servletContext);
				repository.load();
				servletContext.setAttribute(MenuRepository.MENU_REPOSITORY_KEY,
						repository);
			}
			if (logger.isDebugEnabled()) {
				logger.debug("Struts-menu initialization [OK].");
			}
		} catch (Exception lre) {
			logger.fatal("Failure initializing Struts-menu: "
					+ lre.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 只有前台才需要清pageCache.
	 * @see com.cartmatic.estore.webapp.util.AppContextLoader#reloadPageCacheByGroup(java.lang.String)
	 */
	public void reloadPageCache() {
		if (ContextUtil.isStoreFront()) {
			CacheUtil.getInstance().reloadPageCache();
		}
	}
	
	public void clearAllServerSideCaches() {
		CacheUtil.getInstance().clearAllServerSideCaches();
		reloadMailEngine();
	}

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	public void setSpringContext(ApplicationContext springContext) {
		this.springContext = springContext;
	}	

}
