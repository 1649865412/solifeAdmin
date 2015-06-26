/**
 * 
 */

package com.cartmatic.estore.core.util;

import java.io.File;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.LogManager;
import org.apache.log4j.PropertyConfigurator;

import com.cartmatic.estore.Constants;

/**
 * @author Ryan
 */
public final class Log4jUtil {
	private static boolean	initialized	= false;

	public static void initLogging(ServletContext servletContext) {
		if (initialized) {
			return;
		}
		initialized = true;
		String webAppName;
		String webAppRootPath;
		if (servletContext != null) {
			webAppName = servletContext.getServletContextName();
			webAppRootPath = servletContext.getRealPath("/");
		} else {
			File webRoot = new File("web");
			webAppRootPath = webRoot.getAbsolutePath();
			webAppName = webRoot.getAbsoluteFile().getParentFile().getName();
		}
		String logPath = webAppRootPath + "/WEB-INF/logs/";
		String location = webAppRootPath + Constants.LOG4J_CONFIG_LOCATION;

		System.setProperty("application", webAppName);
		System.setProperty("logs", logPath);

		// load Log4J property configurations
		try {
			String liveConfigLocation = location.replaceFirst(".properties",
					"_user.properties");
			File liveConfigFile = new File(liveConfigLocation);
			if (liveConfigFile.exists()) {
				PropertyConfigurator.configure(liveConfigLocation);
				Log logger = LogFactory.getLog(Log4jUtil.class);
				logger.info("Initialized Log4J from [" + liveConfigLocation
						+ "], using live settings.");
			} else {
				PropertyConfigurator.configureAndWatch(location, 10000);
				Log logger = LogFactory.getLog(Log4jUtil.class);
				logger.info("Initialized Log4J from [" + location
						+ "], dynamic configurations is enabled.");
			}
		} catch (Throwable e) {
			throw new RuntimeException("Initialize Log4J error!", e);
		}
	}

	public static void shutdownLogging() {
		Log logger = LogFactory.getLog(Log4jUtil.class);
		logger.info("Shutting down Log4J ......");
		LogManager.shutdown();
	}

}
