package com.cartmatic.estore.core.view;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.RedirectView;

/**
 * 默认使用301永久重定向来处理请求。
 * 扩展Spring MVC原来的RedirectView.
 * 
 * @author Liu Jiayang
 */
public class RedirectView301 extends RedirectView {

	
	/**
	 * Create a new RedirectView with the given URL.
	 * <p>The given URL will be considered as relative to the web server,
	 * not as relative to the current ServletContext.
	 * @param url the URL to redirect to
	 * @see #RedirectView(String, boolean)
	 */
	public RedirectView301(String url) {
		super(url);
	}

	/**
	 * Create a new RedirectView with the given URL.
	 * @param url the URL to redirect to
	 * @param contextRelative whether to interpret the given URL as
	 * relative to the current ServletContext
	 */
	public RedirectView301(String url, boolean contextRelative) {
		super(url,contextRelative);
	}

	/**
	 * Send a permanent redirect back to the HTTP client
	 * @param request current HTTP request (allows for reacting to request method)
	 * @param response current HTTP response (for sending response headers)
	 * @param targetUrl the target URL to redirect to
	 * @param http10Compatible whether to stay compatible with HTTP 1.0 clients
	 * @throws IOException if thrown by response methods
	 */
	@Override
	protected void sendRedirect(
			HttpServletRequest request, HttpServletResponse response, String targetUrl, boolean http10Compatible)
			throws IOException {

		//if (http10Compatible) {
			// Always send status code 302.
			//response.sendRedirect(response.encodeRedirectURL(targetUrl));
		//}
		//else {
			// Correct HTTP status code is 301, in particular for POST requests.
			response.setStatus(301);
			response.setHeader("Location", response.encodeRedirectURL(targetUrl));
		//}
	}
}
