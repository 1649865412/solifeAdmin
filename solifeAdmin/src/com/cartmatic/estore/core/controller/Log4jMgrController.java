
package com.cartmatic.estore.core.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.web.servlet.ModelAndView;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.core.model.Message;
import com.cartmatic.estore.core.util.ContextUtil;
import com.cartmatic.estore.core.view.DownloadView;

public class Log4jMgrController extends BaseController {
	private final String	DEBUG				= "DEBUG";

	private String			defaultView			= "system/log4jMgr";

	private final String	ERROR				= "ERROR";

	private final String	EXTRA_LOG4J_CONFIG	= "extra_log4j_config";

	private final String	FATAL				= "FATAL";

	private final String	INFO				= "INFO";

	private final String	OFF					= "OFF";

	private final String	PARAM_BEG			= "log4j.logger";

	private final String	WARN				= "WARN";

	/**
	 * Add a new config into log4j, and save the properties in ctx.
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ModelAndView addExtraConfig(HttpServletRequest request,
			HttpServletResponse response) throws ServletException {
		String newPattern = request.getParameter("newPattern");
		if (!empty(newPattern)) {
			newPattern = newPattern.trim();
			saveExtraConfigFromCtx(newPattern, request.getParameter("newLevel"));
			saveMessage(Message.info("log4jMgr.addExtraConfig.succeed", newPattern));
		}
		return getRedirectView(successView);
	}

	public ModelAndView defaultAction(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException {
		if (logger.isDebugEnabled()) {
			logger.debug("entering 'Log4jMgrController defaultAction.....");
		}
		Properties configProp = getConfigProp();
		List loggers = getConfigLoggers(configProp);
		ModelAndView mv = new ModelAndView(defaultView);
		mv.addObject("configProp", configProp);
		mv.addObject("currentProp", getCurrentProp(loggers));
		mv.addObject("loggerList", loggers);
		mv.addObject("extraLoggerList", getExtraConfigLoggers());
		return mv;
	}

	public ModelAndView downloadLog(HttpServletRequest request,
			HttpServletResponse response) throws ServletException {
		ModelAndView mv = new ModelAndView(new DownloadView());
		String logFile = "/WEB-INF/logs/"
				+ ContextUtil.getServletContext().getServletContextName()
				+ ".log";
		mv.addObject("uriPath", logFile);
		return mv;
	}

	/**
	 * Get loggers list form log4j.properties All setting is start with
	 * "log4j.logger". sample: log4j.logger.com.opensymphony.oscache
	 * log4j.logger.*
	 * 
	 * @param pp
	 *            log4j.properties
	 * @return
	 */
	private List getConfigLoggers(Properties pp) {
		List rs = new ArrayList();
		Iterator it = pp.keySet().iterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			if (key.startsWith("log4j.logger")) {
				rs.add(key);
			}
		}
		java.util.Collections.sort(rs);
		return rs;
	}

	/**
	 * Get config properties form log4j.properties
	 * 
	 * @return
	 * @throws ServletException
	 */
	private Properties getConfigProp() throws ServletException {
		String path = ContextUtil.getServletContext().getRealPath(
				Constants.LOG4J_CONFIG_LOCATION);
		Properties rs = null;
		try {
			rs = new Properties();
			rs.load(new FileInputStream(path));
		} catch (IOException e) {
			logger.error(e);
			throw new ServletException(e);
		}
		return rs;
	}

	/**
	 * get current setting
	 * 
	 * @param loggers
	 * @return
	 */
	private Properties getCurrentProp(List loggers) {
		Properties rs = new Properties();
		for (int i = 0; i < loggers.size(); i++) {
			String pattern = ((String) loggers.get(i)).substring(PARAM_BEG
					.length() + 1);
			Logger lg = org.apache.log4j.LogManager.getLogger(pattern);
			rs.put(loggers.get(i), lg.getLevel());
		}
		return rs;
	}

	private Properties getExtraConfigFromCtx() {
		Properties rs = (Properties) ContextUtil.getServletContext()
				.getAttribute(EXTRA_LOG4J_CONFIG);
		return rs;
	}

	private List getExtraConfigLoggers() {
		Properties pp = (Properties) ContextUtil.getServletContext()
				.getAttribute(EXTRA_LOG4J_CONFIG);
		List rs = new ArrayList();
		if (pp == null || pp.isEmpty()) {
			return rs;
		}
		Iterator it = pp.keySet().iterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			rs.add(key);
		}
		java.util.Collections.sort(rs);
		return rs;
	}

	/*
	 * private void removeExtraConfigFromCtx(String pattern) { Properties rs =
	 * getExtraConfigFromCtx(); if (rs == null || rs.isEmpty()) { return; }
	 * rs.remove(pattern); }
	 */

	public ModelAndView save(HttpServletRequest request,
			HttpServletResponse response) throws ServletException {
		if (logger.isDebugEnabled()) {
			logger.debug("entering 'Log4jMgrController save' method...");
		}
		Properties configProp = getConfigProp();
		List loggers = getConfigLoggers(configProp);
		for (int i = 0; i < loggers.size(); i++) {
			String key = (String) loggers.get(i);
			String value = request.getParameter(key);
			String pattern = key.substring(PARAM_BEG.length() + 1);
			setLogger(pattern, value); // set into log4j
		}
		saveMessage(Message.info("log4jMgr.save.succeed"));
		return getRedirectView(successView);
	}

	/**
	 * mutil save
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ModelAndView mutilSaveExtraConfig(HttpServletRequest request,
			HttpServletResponse response) throws ServletException {
		List list = getExtraConfigLoggers();
		if (list != null && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				String level = request.getParameter("extra_" + list.get(i));
				saveExtraConfigFromCtx((String) list.get(i), level);
			}
		}
		saveMessage(Message.info("log4jMgr.mutilSaveExtraConfig.succeed"));
		return getRedirectView(successView);
	}

	public ModelAndView resetAll(HttpServletRequest request,
			HttpServletResponse response) throws ServletException {
		if (logger.isDebugEnabled()) {
			logger.debug("entering 'Log4jMgrController resetAll' method...");
		}
		String path = ContextUtil.getServletContext().getRealPath(Constants.LOG4J_CONFIG_LOCATION);
		// reset all configure
		PropertyConfigurator.configure(path);
		// clean extra config
		ContextUtil.getServletContext().removeAttribute(EXTRA_LOG4J_CONFIG);
		saveMessage(Message.info("log4jMgr.reloadAll.succeed"));
		return getRedirectView(successView);
	}

	private void saveExtraConfigFromCtx(String pattern, String level) {
		Properties rs = getExtraConfigFromCtx();
		if (rs == null || rs.isEmpty()) {
			rs = new Properties();
			ContextUtil.getServletContext()
					.setAttribute(EXTRA_LOG4J_CONFIG, rs);
		}
		rs.put(pattern, level);
		setLogger(pattern, level);
	}

	public void setDefaultView(String avalue) {
		this.defaultView = avalue;
	}

	/**
	 * set logger into log4j,runtime.
	 * 
	 * @param paramKey
	 * @param level
	 */
	private void setLogger(String pattern, String level) {
		// String pattern = paramKey.substring(PARAM_BEG.length() + 1);
		Logger lg = org.apache.log4j.LogManager.getLogger(pattern);
		if (DEBUG.equals(level)) {
			lg.setLevel(org.apache.log4j.Level.DEBUG);
		} else if (INFO.equals(level)) {
			lg.setLevel(org.apache.log4j.Level.INFO);
		} else if (WARN.equals(level)) {
			lg.setLevel(org.apache.log4j.Level.WARN);
		} else if (ERROR.equals(level)) {
			lg.setLevel(org.apache.log4j.Level.ERROR);
		} else if (FATAL.equals(level)) {
			lg.setLevel(org.apache.log4j.Level.FATAL);
		} else if (OFF.equals(level)) {
			lg.setLevel(org.apache.log4j.Level.OFF);
		}
	}

	@Override
	protected Map<Integer, Map<String, Object>> getMultiSaveModel(
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void initController() throws Exception {
		// TODO Auto-generated method stub
		
	}
}
