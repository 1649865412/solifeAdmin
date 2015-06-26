
package com.cartmatic.estore.webapp.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.common.helper.ConfigUtil;
import com.cartmatic.estore.core.model.AppUser;
import com.cartmatic.estore.core.util.StringUtil;

/**
 * Convenience class for setting and retrieving cookies.
 */
public final class RequestUtil {
	private transient static Log	logger			= LogFactory
															.getLog(RequestUtil.class);

	private static int				urlPrefixIdx	= 0;

	/**
	 * Convenience method for deleting a cookie by name
	 * 
	 * @param response
	 *            the current web response
	 * @param cookie
	 *            the cookie to delete
	 * @param path
	 *            the path on which the cookie was set (i.e. /appfuse)
	 */
	public static void deleteCookie(HttpServletResponse response,
			Cookie cookie, String path) {
		if (cookie != null) {
			// Delete the cookie by setting its maximum age to zero
			cookie.setMaxAge(0);
			cookie.setPath(path);
			response.addCookie(cookie);
		}
	}

	/**
	 * Convenience method to get the application's URL based on request
	 * variables.
	 */
	public static String getAppURL(HttpServletRequest request) {
		StringBuffer url = new StringBuffer();
		int port = request.getServerPort();
		if (port < 0) {
			port = 80; // Work around java.net.URL bug
		}
		String scheme = request.getScheme();
		url.append(scheme);
		url.append("://");
		url.append(request.getServerName());
		if ((scheme.equals("http") && (port != 80))
				|| (scheme.equals("https") && (port != 443))) {
			url.append(':');
			url.append(port);
		}
		url.append(request.getContextPath());
		return url.toString();
	}

	/**
	 * Convenience method to get a cookie by name
	 * 
	 * @param request
	 *            the current request
	 * @param name
	 *            the name of the cookie to find
	 * 
	 * @return the cookie (if found), null if not found
	 */
	public static Cookie getCookie(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		Cookie returnCookie = null;

		if (cookies == null) {
			return returnCookie;
		}

		for (int i = 0; i < cookies.length; i++) {
			Cookie thisCookie = cookies[i];
			if (thisCookie.getName().equals(name)) {
				// cookies with no value do me no good!
				if (!thisCookie.getValue().equals("")) {
					try {
						thisCookie.setValue(URLDecoder.decode(thisCookie.getValue(),"UTF-8"));
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}					
					returnCookie = thisCookie;
					break;
				}
			}
		}		
		return returnCookie;
	}

	public final static StringBuilder getErrorInfoFromRequest(
			HttpServletRequest request, boolean isInfoEnabled) {
		StringBuilder sb = new StringBuilder();
		String errorUrl = getErrorUrl(request);
		sb.append(StringUtil.format(
				"Error processing url: %1, Referrer: %2, Error message: %3.\n",
				errorUrl, request.getHeader("REFERER"), request
						.getAttribute("javax.servlet.error.message")));

		Throwable ex = (Throwable) request.getAttribute("exception");
		if (ex == null) {
			ex = (Throwable) request
					.getAttribute("javax.servlet.error.exception");
		}

		if (ex != null) {
			sb.append(StringUtil.format("Exception stack trace: \n", ex));
		}

		if (isInfoEnabled) {
			sb.append(getRequestInfo(request));
		}
		return sb;
	}

	public final static String getErrorUrl(HttpServletRequest request) {
		String errorUrl = (String) request
				.getAttribute("javax.servlet.error.request_uri");
		if (errorUrl == null) {
			errorUrl = (String) request
					.getAttribute("javax.servlet.forward.request_uri");
		}
		if (errorUrl == null) {
			errorUrl = (String) request
					.getAttribute("javax.servlet.include.request_uri");
		}
		if (errorUrl == null) {
			errorUrl = request.getRequestURL().toString();
		}
		return errorUrl;
	}

	public static Cookie getEStoreCookie(HttpServletRequest request) {
		return getCookie(request, Constants.COOKIE_NAME);
	}

	public static final String getFullRequestUrl(HttpServletRequest req) {
		return (req.getQueryString() == null ? req.getRequestURL() : req
				.getRequestURL().append("?").append(req.getQueryString()))
				.toString();
	}

	public static Integer getInteger(HttpServletRequest request,
			String paramName) {
		String id = request.getParameter(paramName);
		if (id != null && !id.equals("")) {
			try {
				return new Integer(id);
			} catch (Exception e) {
				return null;
			}
		}
		return null;
	}

	public static Boolean getBoolean(HttpServletRequest request,
			String paramName) {
		String id = request.getParameter(paramName);
		if (id != null && !id.equals("")) {
			try {
				return new Boolean(id);
			} catch (Exception e) {
				return Boolean.FALSE;
			}
		}
		return Boolean.FALSE;
	}
	
	public static Integer[] getIntegerArray(HttpServletRequest request,
			String paramName) {
		Integer[] iIds = null;
		String[] strIds = request.getParameterValues(paramName);
		if (strIds != null && strIds.length > 0) {
			iIds = new Integer[strIds.length];
			for (int i = 0; i < strIds.length; i++) {
				iIds[i] = new Integer(strIds[i]);
			}
		}
		return iIds;
	}
	
	public static Integer[] getIntegerArrayNullSafe(HttpServletRequest request,
			String paramName) {
		Integer[] iIds =getIntegerArray(request, paramName);
		if(iIds==null){
			iIds=new Integer[]{};
		}
		return iIds;  
	}
	
	public static String[] getParameterValuesNullSafe(HttpServletRequest request,
			String paramName) {
		String[] strIds = request.getParameterValues(paramName);
		if(strIds==null){
			strIds=new String[]{};
		}
		return strIds;  
	}

	public static String getParameterNullSafe(HttpServletRequest request,
			String paramName) {
		String ret = request.getParameter(paramName);
		if (ret == null) {
			ret = "";
		}
		return ret;
	}

	public static String getRequestedPageName(final HttpServletRequest req,
			final String defaultViewName) {
		int idx2 = req.getRequestURI().lastIndexOf(".");
		if (idx2 > 0) {// request a specified page
			return req.getRequestURI().substring(getUrlPrefixIdx(req), idx2);
		} else {// request a directory, a servlet or something else
			String uri = req.getRequestURI().substring(getUrlPrefixIdx(req));
			if (!uri.endsWith("/")) {
				uri = uri + "/" + defaultViewName;
			} else {
				uri = uri + defaultViewName;
			}
			return uri;
		}
	}

	public final static StringBuilder getRequestInfo(HttpServletRequest request) {
		StringBuilder sb = new StringBuilder();
		String requestUrl = request.getRequestURL().toString();

		sb
				.append(StringUtil
						.format(
								"##########Start of debuging request and session data for url: %1.\n",
								requestUrl));
		sb.append("!!!!!!!!!!Debuging request.getParameterNames()!!!!!!!!!!\n");
		java.util.Enumeration enumeration = request.getParameterNames();
		while (enumeration.hasMoreElements()) {
			String paramName = (String) enumeration.nextElement();
			sb.append(StringUtil.format("Request Parameter - %1 = %2.\n",
					paramName, request.getParameter(paramName)));
		}

		sb.append("!!!!!!!!!!Debuging request.getAttributeNames()!!!!!!!!!!\n");
		enumeration = request.getAttributeNames();
		while (enumeration.hasMoreElements()) {
			String attrName = (String) enumeration.nextElement();
			if (!attrName.endsWith("exception")) {
				sb.append(StringUtil.format("Request Attribute - %1 = %2.\n",
						attrName, request.getAttribute(attrName)));
			}
		}

		sb.append("!!!!!!!!!!Debuging request.getHeaderNames()!!!!!!!!!!\n");
		enumeration = request.getHeaderNames();
		while (enumeration.hasMoreElements()) {
			String headerName = (String) enumeration.nextElement();
			sb.append(StringUtil.format("Request Header - %1 = %2.\n",
					headerName, request.getHeader(headerName)));
		}

		HttpSession session = request.getSession(false);
		if (session != null) {
			sb
					.append("!!!!!!!!!!Debuging session.getAttributeNames()!!!!!!!!!!\n");
			enumeration = session.getAttributeNames();
			while (enumeration.hasMoreElements()) {
				String attrName = (String) enumeration.nextElement();
				sb.append(StringUtil.format("Session Attribute - %1 = %2.\n",
						attrName, session.getAttribute(attrName)));
			}
			sb
					.append(StringUtil
							.format(
									"##########End of debuging request and session data for url: %1.\n",
									requestUrl));
		}
		return sb;
	}

	public static Map getRequestMap(final HttpServletRequest req) {
		Map params = new HashMap();
		Enumeration emu = req.getParameterNames();
		while (emu.hasMoreElements()) {
			String key = (String) emu.nextElement();
			String[] values = req.getParameterValues(key);
			if (values != null) {
				if (values.length == 1) {
					params.put(key, values[0]);
				} else {
					params.put(key, values);
				}
			}
		}

		return Collections.unmodifiableMap(params);
	}

	public static int getUrlPrefixIdx(final HttpServletRequest req) {
		if (urlPrefixIdx == 0) {
			String contextPath = req.getContextPath();
			int idx1 = req.getRequestURI().indexOf(contextPath);
			urlPrefixIdx = idx1 + contextPath.length();
		}

		return urlPrefixIdx;
	}
	
	/**
	 * 判断当前的uri是否选择器
	 * @param req
	 * @return
	 */
	public static boolean isSelectorUri(final HttpServletRequest req)
	{
	    return req.getRequestURI().startsWith(req.getContextPath()+"/selector/");
	}

	public static void removeUserCookies(HttpServletResponse response,
			String ctxPath) {
		setCookie(response, "UID", "-2", ctxPath);
		// setCookie(response, "UNAME", "", ctxPath);
	}

	/**
	 * Convenience method to set a cookie
	 * 
	 * @param response
	 * @param name
	 * @param value
	 * @param path
	 */
	public static void setCookie(HttpServletResponse response, String name,
			String value, String path) {
		setCookie(response, name, value, path, ConfigUtil.getInstance()
				.getCookieMaxAge() * 24 * 60 * 60);
	}

	public static void setCookie(HttpServletResponse response, String name,
			String value, String path, int maxAge) {
		if (logger.isDebugEnabled()) {
			logger
					.debug("Setting cookie '" + name + "' on path '" + path
							+ "'");
		}
		try {
			value = URLEncoder.encode(value,"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Cookie cookie = new Cookie(name, value);
		cookie.setSecure(false);
		cookie.setPath( (path==null || "".equals(path)?"/":path) );
		cookie.setMaxAge(maxAge); // 30 days
		response.addCookie(cookie);
	}

	//不进行设置过期时间，使其过期时间自动设置为session的连线时间.
	public static void setUserInfoCookie(HttpServletResponse response, AppUser appuser, String ctxPath) {
		com.cartmatic.estore.common.model.system.AppUser user=(com.cartmatic.estore.common.model.system.AppUser)appuser;
		setCookie(response, "UID", appuser.getAppuserId().toString(), ctxPath);
		setCookie(response, "UNAME", StringUtils.isNotBlank(user.getFirstname())?user.getFirstname():user.getEmail(), ctxPath);
		setCookie(response, "UEMAIL", appuser.getEmail(), ctxPath);
	}

	
	
	private RequestUtil() {
	}
}
