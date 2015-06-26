/*
 * Created on Jul 11, 2006
 * 
 */

package com.cartmatic.estore.core.listener;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;

import com.cartmatic.estore.cart.util.ShoppingCartUtil;
import com.cartmatic.estore.core.util.ContextUtil;
import com.cartmatic.estore.webapp.util.RequestUtil;

/**
 * @author Ryan
 * modify by huangwenmin 2008-10-31
 * modify by Ryan Liu 2010-11-24
 */
public class LogoutHandler extends org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler 
{
	private final static transient Log	logger	= LogFactory.getLog(LogoutHandler.class);

	public void logout(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication) 
	{
		if (logger.isDebugEnabled())
			logger.debug("processing logout ...");
		// set a flag to indicate Auditer this is a logout event
		//request.getSession().setAttribute("IS_LOGOUT", "true");		
		super.logout(request, response, authentication);
		
		if (ContextUtil.isStoreFront()) {
			ShoppingCartUtil.getInstance().removeShoppingcartCookie(request, response);
		}
		RequestUtil.removeUserCookies(response, request.getContextPath());
	}
}
