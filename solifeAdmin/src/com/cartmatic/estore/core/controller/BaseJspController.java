/*
 * Created on Apr 21, 2006
 * 
 */

package com.cartmatic.estore.core.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.cartmatic.estore.webapp.util.RequestUtil;

/**
 * @author Ryan
 * 
 */
public class BaseJspController extends BaseStoreFrontController {

	/**
	 * @see GenericController.defaultAction
	 */
	public ModelAndView defaultAction(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException {
		String page = RequestUtil.getRequestedPageName(req, "index");
		logger
				.debug("URL is not mapped to a controller, forward to page directly: "
						+ page);

		ModelAndView mav = getModelAndView(page);
		if (mav == null) {
			logger.error(new StringBuilder(
					"Invalid request, or template not found for url: [")
					.append(req.getRequestURI()).append("].").toString());
			try {
				resp.sendError(404, "Invalid request, or template not found!");
			} catch (IOException e) {
				logger.error("Unexpected sendError error.", e);
			}
			return null;
		}
		return mav;
	}
}
