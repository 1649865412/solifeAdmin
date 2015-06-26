/*
 * Created on Jul 10, 2006
 * 
 */

package com.cartmatic.estore.core.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.Assert;

/**
 * @author Ryan
 * 
 */
public final class UrlUtil {
	/**
	 * 所有非英文和数字的字符串都会过滤掉.
	 */
	//private static Pattern p_not_code = Pattern.compile("[^\\w]+");
	
	public final static String appendParamToUrl(final String url,
			final String paramName, final String paramValue) {
		Assert.hasText(paramName);
		//Assert.hasText(paramValue);
		String pvalue = paramValue;
		try {
			pvalue = URLEncoder.encode(paramValue, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		StringBuilder sb = new StringBuilder(url);
		if (url.indexOf("?") == -1) {
			sb.append("?");
		} else if (!url.endsWith("&")) {
			sb.append("&");
		}
		sb.append(paramName).append("=").append(pvalue);
		return sb.toString();
	}

	private static String buildFullRequestUrl(String scheme, String serverName,
			int serverPort, String contextPath, String requestUrl,
			String servletPath, String requestURI, String pathInfo,
			String queryString) {
		boolean includePort = true;
		if ("http".equals(scheme.toLowerCase()) && (serverPort == 80)) {
			includePort = false;
		}
		if ("https".equals(scheme.toLowerCase()) && (serverPort == 443)) {
			includePort = false;
		}
		return scheme
				+ "://"
				+ serverName
				+ ((includePort) ? (":" + serverPort) : "")
				+ contextPath
				+ buildRequestUrl(servletPath, requestURI, contextPath,
						pathInfo, queryString);
	}

	private static String buildRequestUrl(String servletPath,
			String requestURI, String contextPath, String pathInfo,
			String queryString) {
		String uri = servletPath;

		if (uri == null) {
			uri = requestURI;
			uri = uri.substring(contextPath.length());
		}
		return uri + ((pathInfo == null) ? "" : pathInfo)
				+ ((queryString == null) ? "" : ("?" + queryString));
	}

	public static String formatUrl(String url) {
		return formatUrl(new StringBuilder(url)).toString();
	}

	public static StringBuilder formatUrl(StringBuilder url) {
		int idx = 0;
		while ((idx = url.indexOf("//")) >= 0) {
			url.deleteCharAt(idx);
		}
		// idx = url.length()-1;
		// if (url.charAt(idx)=='/') {
		// url.deleteCharAt(idx);
		// }
		return url;
	}

	public static String getDomainPath(HttpServletRequest request) {
		String fullDomain = request.getScheme() + "://"
				+ request.getHeader("host") + request.getContextPath();
		return fullDomain;
	}

	/**
	 * 取到当前请求的全路径
	 * 
	 * @param r
	 * @return
	 */
	public static String getFullRequestUrl(HttpServletRequest r) {
		return buildFullRequestUrl(r.getScheme(), r.getServerName(), r
				.getServerPort(), r.getContextPath(), r.getRequestURL()
				.toString(), r.getServletPath(), r.getRequestURI(), r
				.getPathInfo(), r.getQueryString());
	}

	/**
	 * reference getParamFromUrl(String uri,String paramName)
	 * 
	 * @param request
	 * @param paramName
	 * @return
	 */
	public final static String getParamFromUrl(
			final HttpServletRequest request, String paramName) {
		return getParamFromUrl(request.getRequestURI(), paramName);
	}

	/**
	 * only for *.html Sample:uri="/index=path1.2.3=page4.html";
	 * getParamFromUrl(uri,"path") will returns "1.2.3";
	 * getParamFromUrl(uri,"page") will returns "4";
	 * 
	 * @param uri
	 * @param paramName
	 * @return
	 */
	public final static String getParamFromUrl(String uri, String paramName) {
		int indexStart = uri.indexOf("=" + paramName);
		if (indexStart == -1) {
			return null;
		}
		String rs = uri.substring(indexStart + paramName.length() + 1);
		int indexEnd = rs.indexOf("=");
		if (indexEnd == -1) {
			if (uri.contains("_dy")) {
				indexEnd = rs.indexOf("_dy.html");
			} else {
				indexEnd = rs.indexOf(".html");
			}
		}
		if (indexEnd == -1) {
			return null;
		}
		rs = rs.substring(0, indexEnd);
		return rs;
	}

	public static String getParentUrl(final String url) {
		int idx = 0;
		while (true) {
			int nextIdx = url.indexOf("/", idx + 1);
			if (nextIdx > idx) {
				idx = nextIdx;
			} else {
				break;
			}
		}
		return url.substring(0, idx) + "/index.html";
	}

	public static String getRequestServletPath(HttpServletRequest r) {
		return buildRequestUrl(r.getServletPath(), r.getRequestURI(), r
				.getContextPath(), r.getPathInfo(), r.getQueryString());
	}

	public final static String getUrlFromRequest(HttpServletRequest request) {
		String url = request.getServletPath();
		Enumeration e = request.getParameterNames();
		while (e.hasMoreElements()) {
			String name = (String) e.nextElement();
			String value = request.getParameter(name);
			url = appendParamToUrl(url, name, value);
		}
		return url;
	}

	/*public final static boolean isUrlValid(String strUrl) {
		try {
			URL url = new URL(strUrl);
			return true;
		} catch (MalformedURLException e) {
			return false;
		}

	}*/

	public static void main(String[] args) {
		System.out
				.println(UrlUtil
						.replaceParamInUrl(
								"http://localhost:8080/StoreAdmin/system/systemLanguage.html?systemLanguageId=82&doAction=edit&from=list",
								"systemLanguageId", "1234"));
		String url = "http://localhost:8080/StoreAdmin.html";
		url = UrlUtil.appendParamToUrl(url, "hello", "343.034");
//		url = UrlUtil.appendParamToUrl(url, "hello1", "343.0 34");
		url = UrlUtil.appendParamToUrl(url, "hello2", "http://www.abc.com/ab.html?3=4&abc");
		url = UrlUtil.replaceParamInUrl(url, "hello1", "3aaaa");
		System.out.println(url);
	}

	public static String parsePageName(final String url) {
		int idx2 = url.lastIndexOf(".");
		if (idx2 > 0) {// request a specified page
			return url.substring(url.lastIndexOf('/') + 1, idx2);
		} else {
			return "Page_name_cannot_be_parsed.";
		}
	}

	public final static String removeParamFromUrl(final String url,
			final String paramName) {
		Assert.hasText(paramName);

		StringBuilder sb = new StringBuilder(url);
		int idx = sb.indexOf("?");
		if (idx > 0) {
			int idx1 = sb.indexOf(paramName, idx);
			if (idx1 > 0) {
				int idx2 = sb.indexOf("&", idx1);
				if (idx2 == -1) {
					idx2 = sb.length();
				} else {
					idx2++;
				}
				return sb.delete(idx1, idx2).toString();
			}
		}

		return url;
	}

	public final static String replaceParamInUrl(final String url,
			final String paramName, final String paramValue) {
		return appendParamToUrl(removeParamFromUrl(url, paramName), paramName,
				paramValue);
	}
	
	/**
	 * 主要是针对code处理
	 * @param uri
	 * @return
	 */
	public final static String formatUriPart(String uri) {
//		uri=uri.replaceAll(" ","-");
//		uri=uri.replaceAll("%","-");
//		uri=uri.replaceAll("#","-");
//		uri=uri.replaceAll("\\?","-");
//		uri=uri.replaceAll("&","-");
//		uri=uri.replaceAll("/","-");
//		uri=uri.replaceAll(";","-");
//		uri=uri.replaceAll(":","-");
//		uri=uri.replaceAll("!","-");
//		uri=uri.replaceAll("@","-");
//		uri=uri.replaceAll("\"","-");
//		uri=uri.replaceAll("\\+","-");
//		uri=uri.replaceAll("\\.","-");
//		uri=uri.replaceAll("\\(","-");
//		uri=uri.replaceAll("\\)","-");
		/*while(uri.indexOf("--")!=-1){
			uri=uri.replaceAll("--","-");
		}*/
//		return p_not_code.matcher(uri).replaceAll("-");
		try {
			uri=URLFormat.format(uri);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return uri;
	}
	
	/*
	public final static String formatUri(String uri) {
		uri=uri.replaceAll(" ","-");
		uri=uri.replaceAll("#","-");
		uri=uri.replaceAll("\\?","-");
		//while(uri.indexOf("--")!=-1){
		//	uri=uri.replaceAll("--","-");
		//}
		return uri;
	}*/

	private UrlUtil() {
	}
	
	public static Map<String, String[]> getParamsMap(String queryString) {
		Map<String, String[]> paramsMap = new HashMap<String, String[]>();

		if (queryString != null && queryString.length() > 0)
		{
			int ampersandIndex, lastAmpersandIndex = 0;
			String subStr, param, value;
			String[] paramPair, values, newValues;
			do
			{
				ampersandIndex = queryString.indexOf('&', lastAmpersandIndex) + 1;
				if (ampersandIndex > 0)
				{
					subStr = queryString.substring(lastAmpersandIndex, ampersandIndex - 1);
					lastAmpersandIndex = ampersandIndex;
				}
				else
				{
					subStr = queryString.substring(lastAmpersandIndex);
				}
				paramPair = subStr.split("=");
				param = paramPair[0];
				value = paramPair.length == 1 ? "" : paramPair[1];
				try
				{
					value = URLDecoder.decode(value, "utf-8");
				}
				catch (UnsupportedEncodingException ignored)
				{
				}
				if (paramsMap.containsKey(param))
				{
					values = paramsMap.get(param);
					int len = values.length;
					newValues = new String[len + 1];
					System.arraycopy(values, 0, newValues, 0, len);
					newValues[len] = value;
				}
				else
				{
					newValues = new String[] { value };
				}
				paramsMap.put(param, newValues);
			}
			while (ampersandIndex > 0);
		}
		return paramsMap;
	}
	
	public static  String mapToQuery(Map<String, String[]> map) {
		 StringBuilder sb = new StringBuilder();
	        for (Map.Entry<String, String[]> entry : map.entrySet()) {
	            if (sb.length() > 0) {
	                sb.append("&");
	            }
	            
            	String values[]=entry.getValue();
            	if(values!=null&&values.length>0){
            		for (String value : values)
					{
            			if(value!=null){
            				try{
                				sb.append(String.format("%s=%s",URLEncoder.encode(entry.getKey(), "UTF-8"),URLEncoder.encode(value, "UTF-8")));
    						}
    	    				catch (UnsupportedEncodingException e)
    	    				{
    	    					e.printStackTrace();
    	    				}
            			}
					}
            	}
				
	        }
	        return sb.toString();   
	}
}
