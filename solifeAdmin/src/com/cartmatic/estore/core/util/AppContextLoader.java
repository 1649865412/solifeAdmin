/*
 * Created on Jan 16, 2008
 * 
 */

package com.cartmatic.estore.core.util;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;

public interface AppContextLoader {

	public void cleanup();

	/**
	 * TODO, need to change when multiple company need to be supported
	 * 
	 */
	//public void reloadCompanyInfo();

	/**
	 * 系统配置不是直接以Map的方式放在ServletContext中，放的是ConfigUtil；后者对系统配置进行包装；令到对系统配置的访问非常方便，举例：appConfig.rememberMeEnabled；并对系统配置自动进行缓存
	 * 
	 */
	public void reloadConfig();

	public void reloadContext();

	@Deprecated
	//public void reloadLanguage();

	public void reloadMembership();

	public void reloadCurrency();

	public void reloadPageCache();

	public void setServletContext(ServletContext servletContext);

	public void setSpringContext(ApplicationContext springContext);
	public void clearAllServerSideCaches();
}