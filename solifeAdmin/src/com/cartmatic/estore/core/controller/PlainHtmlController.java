/**
 * 
 */

package com.cartmatic.estore.core.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.cartmatic.estore.common.util.FileUtil;
import com.cartmatic.estore.webapp.util.RequestUtil;

/**
 * @author Ryan
 * 
 */
public class PlainHtmlController extends AbstractController {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.mvc.AbstractController#handleRequestInternal(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String page = RequestUtil.getRequestedPageName(request, "") + ".html";
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().write(
				FileUtil.readFileToString(request.getSession(true)
						.getServletContext().getRealPath(page)));
		return null;
	}

}
