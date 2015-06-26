/**
 * 
 */

package com.cartmatic.estore.other.web.action;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.cartmatic.estore.core.controller.BaseStoreFrontController;

/**
 * @author Ryan
 * 
 */
public class RecentViewController extends BaseStoreFrontController {

	public ModelAndView auditUserRecentViews(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException {
		return null;
	}

	/*
	 * 说明：以前的实现中因为模板包的原因前台和后台是分开不同的实现的。现在合并。
	 * 
	 * @see com.cartmatic.estore.webapp.action.BaseListController#defaultAction(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public ModelAndView defaultAction(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException {
		return getModelAndView("recentView", null);
	}
}
