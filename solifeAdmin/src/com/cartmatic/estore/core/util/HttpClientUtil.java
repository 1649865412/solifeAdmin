
package com.cartmatic.estore.core.util;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.HttpVersion;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.cookie.CookieSpec;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public final class HttpClientUtil {

	private static final Log	logger		= LogFactory
													.getLog(HttpClientUtil.class);

	static final String			LOGIN_URL_1	= "/login.html";

	static final String			LOGIN_URL_2	= "/j_security_check";

	static final String			LOGOUT_URL	= "/j_acegi_logout";

	/**
	 * 
	 * @param protocol
	 *            http or https
	 * @param host
	 * @param port
	 * @param url
	 * @param userName
	 * @param password
	 * @param params
	 * @param simplifiedLoginMode
	 *            set to true to bypass login form page and redirecting page
	 * @throws IOException
	 */
	public static void invokeHttpUrl(String strUrl, String userName,
			String password, Map params, boolean simplifiedLoginMode)
			throws IOException {
		URL url = new URL(strUrl);
		// create and init HttpClient
		HttpClient client = new HttpClient();
		client.getHostConfiguration().setHost(url.getHost(), url.getPort(),
				url.getProtocol());
		client.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);

		// login before executing the url if username is spcified
		if (userName != null) {
			CookieSpec cookiespec = null;

			if (!simplifiedLoginMode) {
				// get login page
				GetMethod authget = new GetMethod(url.getPath() + LOGIN_URL_1);
				client.executeMethod(authget);
				authget.releaseConnection();
				if (logger.isDebugEnabled()) {
					logger.debug("Getting login form page result: "
							+ authget.getStatusLine().toString());
					// See if we got any cookies
					cookiespec = CookiePolicy.getDefaultSpec();
					Cookie[] initcookies = cookiespec.match(url.getHost(), url
							.getPort(), "/", false, client.getState()
							.getCookies());
					logger.debug("Initial set of cookies:");
					if (initcookies.length == 0) {
						logger.debug("No cookies.");
					} else {
						for (int i = 0; i < initcookies.length; i++) {
							logger.debug("- " + initcookies[i].toString());
						}
					}
				}
			}

			// perform login, using post mode
			PostMethod authpost = new PostMethod(url.getPath() + LOGIN_URL_2);
			// Prepare login parameters
			NameValuePair prmUserName = new NameValuePair("j_username",
					userName);
			NameValuePair prmPassword = new NameValuePair("j_password",
					password);
			authpost.setRequestBody(new NameValuePair[] { prmUserName,
					prmPassword });

			client.executeMethod(authpost);
			logger.info("Login result: " + authpost.getStatusLine().toString());
			authpost.releaseConnection();
			if (!simplifiedLoginMode && logger.isDebugEnabled()) {
				// See if we got any cookies
				// The only way of telling whether logon succeeded is
				// by finding a session cookie
				Cookie[] logoncookies = cookiespec.match(url.getHost(), url
						.getPort(), "/", false, client.getState().getCookies());
				logger.debug("Login cookies:");
				if (logoncookies.length == 0) {
					logger.debug("No cookies.");
				} else {
					for (int i = 0; i < logoncookies.length; i++) {
						logger.debug("- " + logoncookies[i].toString());
					}
				}
			}

			if (!simplifiedLoginMode) {
				// Usually a successful form-based login results in a redicrect
				// to another url
				int statuscode = authpost.getStatusCode();
				if ((statuscode == HttpStatus.SC_MOVED_TEMPORARILY)
						|| (statuscode == HttpStatus.SC_MOVED_PERMANENTLY)
						|| (statuscode == HttpStatus.SC_SEE_OTHER)
						|| (statuscode == HttpStatus.SC_TEMPORARY_REDIRECT)) {
					Header header = authpost.getResponseHeader("location");
					if (header != null) {
						String newuri = header.getValue();
						if ((newuri == null) || (newuri.equals(""))) {
							newuri = "/";
						}
						logger.debug("Redirect target: " + newuri);
						GetMethod redirect = new GetMethod(newuri);

						client.executeMethod(redirect);
						logger.debug("Redirect result: "
								+ redirect.getStatusLine().toString());
						// release any connection resources used by the method
						redirect.releaseConnection();
					} else {
						logger.warn("Invalid redirect");
					}
				}
			}
		}

		// execute the targeted url
		if (params == null) {
			// if params is null, use get mode
			GetMethod getUrl = new GetMethod(url.getPath() + url);
			client.executeMethod(getUrl);
			getUrl.releaseConnection();
			if (getUrl.getStatusCode() != HttpStatus.SC_OK) {
				logger.error("Http page call error. Url: " + url
						+ " | Status: " + getUrl.getStatusLine().toString());
				throw new HttpException("Http page call error: " + url);
			} else {
				logger.info("Http page call completed [OK], url: " + url
						+ " | Status: " + getUrl.getStatusLine().toString());
			}
		} else {
			// if params is NOT null, use post mode
			PostMethod postMethod = new PostMethod(strUrl);
			// Prepare parameters
			List paramList = new ArrayList();
			for (Iterator iter = params.keySet().iterator(); iter.hasNext();) {
				String paramName = (String) iter.next();
				NameValuePair nameValuePair = new NameValuePair(paramName,
						(String) params.get(paramName));
				paramList.add(nameValuePair);
			}
			postMethod.setRequestBody((NameValuePair[]) paramList
					.toArray(new NameValuePair[] {}));

			client.executeMethod(postMethod);
			postMethod.releaseConnection();
			if (postMethod.getStatusCode() != HttpStatus.SC_OK) {
				logger
						.error("Http page call error. Url: " + url
								+ " | Status: "
								+ postMethod.getStatusLine().toString());
				throw new HttpException("Http page call error: " + url);
			} else {
				logger
						.info("Http page call completed [OK], url: " + url
								+ " | Status: "
								+ postMethod.getStatusLine().toString());
			}
		}

		// logout after executing the url
		if (userName != null) {
			// logout
			GetMethod logout = new GetMethod(url.getPath() + LOGOUT_URL);
			client.executeMethod(logout);
			logout.releaseConnection();
			logger.debug("Logout result: " + logout.getStatusLine().toString());
		}
	}

	/**
	 * Execute a http url request. Login first is userName is not null. Use post
	 * mode if params is not null, or else use default get mode. Simplefied for
	 * call store front pages.
	 * 
	 * @param url
	 *            the target url to call
	 * @param userName
	 *            required if login is required
	 * @param password
	 * @param params
	 *            required
	 * @throws IOException
	 */
	public static void invokeStoreFrontPage(String url, String userName,
			String password, Map params) throws IOException {
		invokeHttpUrl(url, userName, password, params, true);
	}

	public static void main(String[] args) throws Exception {
		// HttpClientUtil.invokeStoreFrontPage("/StoreAdmin/index.html",
		// "test4", "test4", new HashMap());
		testHttpUrl("http://www.google.com");
		// testHttpUrl("http://www.17k.com/html/bookAbout.htm?bid=168");

		// debug localhost ip and host name
		// InetAddress localAddr = InetAddress.getLocalHost();
		// System.out.println(localAddr.getHostAddress());
		// System.out.println(localAddr.getHostName());
	}

	public static void testHttpUrl(String fullUrl) throws IOException {
		URL url = new URL(fullUrl);
		// create and init HttpClient
		HttpClient httpClient = new HttpClient();
		httpClient.getHostConfiguration().setHost(url.getHost(),
				url.getPort() == -1 ? 80 : url.getPort(), url.getProtocol());
		httpClient.getParams().setCookiePolicy(
				CookiePolicy.BROWSER_COMPATIBILITY);
		httpClient.getParams().setParameter("http.protocol.version",
				HttpVersion.HTTP_1_1);
		httpClient.getParams().setParameter("http.socket.timeout",
				new Integer(600000));
		httpClient.getParams().setParameter("http.protocol.content-charset",
				"UTF-8");
		logger.warn("Getting page: " + fullUrl);
		if (logger.isDebugEnabled()) {
			logger.debug("Getting page: " + fullUrl);
		}
		CookieSpec cookiespec = null;
		GetMethod getMethod = new GetMethod(url.getPath());

		SimpleDateFormat sdf = new SimpleDateFormat(
				"EEE, dd MMM yyyy HH:mm:ss z");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		Date testDate = new Date();
		// 60 secs ago
		testDate.setTime(testDate.getTime() - 1000 * 60);
		// getMethod.addRequestHeader("If-Modified-Since",sdf.format(testDate));
		// getMethod.addRequestHeader("If-Modified-Since",
		// "Thu, 19 Oct 2006 00:41:44 GMT");
		// getMethod.addRequestHeader("Pragma", "no-cache");
		getMethod.addRequestHeader("Content-Type", "text/html; charset=UTF-8");
		getMethod
				.addRequestHeader(
						"Accept",
						"text/xml,application/xml,application/xhtml+xml,text/html;q=0.9,text/plain;q=0.8,image/png,*/*;q=0.5");
		getMethod.addRequestHeader("Accept-Language", "zh-cn,en-us;q=0.5");
		getMethod.addRequestHeader("Accept-Encoding", "gzip, deflate");
		getMethod.addRequestHeader("Accept-Charset", "UTF-8;q=0.7");
		getMethod.addRequestHeader("Referer", "http://www.test.com");

		httpClient.executeMethod(getMethod);

		// if (logger.isDebugEnabled()) {
		logger
				.debug("Get page result: "
						+ getMethod.getStatusLine().toString());
		// See if we got any cookies
		cookiespec = CookiePolicy.getDefaultSpec();
		Cookie[] cookies = cookiespec.match(url.getHost(),
				url.getPort() == -1 ? 80 : url.getPort(), "/", false,
				httpClient.getState().getCookies());
		logger.debug("Set of cookies:");
		if (cookies.length == 0) {
			logger.debug("No cookies.");
		} else {
			for (int i = 0; i < cookies.length; i++) {
				logger.debug("- " + cookies[i].toString());
			}
		}

		logger.debug("**************************************************");
		logger.debug("Request headers:");
		Header[] headers = getMethod.getRequestHeaders();
		for (int i = 0; i < headers.length; i++) {
			Header header = headers[i];
			logger.debug(header.getName() + ": " + header.getValue());
		}

		logger.debug("**************************************************");
		logger.debug("Response headers:");
		headers = getMethod.getResponseHeaders();
		for (int i = 0; i < headers.length; i++) {
			Header header = headers[i];
			logger.debug(header.getName() + ": " + header.getValue());
		}

		logger.debug("**************************************************");
		logger.debug(getMethod.getResponseCharSet());
		// logger.debug(getMethod.getResponseBodyAsString());
		InputStream in = getMethod.getResponseBodyAsStream();
		byte[] buf = new byte[8096];
		OutputStream out = new BufferedOutputStream(new FileOutputStream(
				"c:/test.html"));
		while (in.read(buf) > 0) {
			out.write(buf);
		}
		in.close();
		out.close();
		// }
		getMethod.releaseConnection();
	}

	public HttpClientUtil() {
		super();
	}
}
