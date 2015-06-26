/*
 * create by ycm on 2006-05-19
 */

package com.cartmatic.estore.webapp.util;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.common.model.cart.Shoppingcart;
import com.cartmatic.estore.common.model.order.SalesOrder;

/**
 * 
 */
public class SessionUtil {
	public final static String	BOOL_FROMCHECKOUT	= "fromCheckout";

	/**
	 * all attributes save in session must be set a constant String in here the
	 * constant format is [Type]_[Name]
	 */

	protected final static Log	logger	= LogFactory.getLog(SessionUtil.class);
	public final static String	OBJ_CHECKOUTMODEL	= "checkoutModel";
	public final static String	OBJ_MINICART		= "minicart";
	public final static String	OBJ_SHOPPINGCART	= "shoppingcart";

	private static Object getAttribute(HttpSession _session, String sKey) {
		if (_session == null) {
			return null;
		}
		return _session.getAttribute(sKey);
	}


	public static void setAttribute(HttpSession _session, String sKey,
			Object obj) {
		if (_session != null) {
			_session.setAttribute(sKey, obj);
		}
	}


	public static void setCurrentGroupKey(HttpSession session,
			String currentGroupKey) {
		session.setAttribute(Constants.CURRENT_GROUP_KEY, currentGroupKey);
	}

	public static void setNewProductId(HttpSession _session, Set newProductIds) {
//		SessionUtil.setAttribute(_session,
//				Constants.Shoppingcart_NEW_PRODUCT_IDS, newProductIds);
	}


	public static void setShoppingcart(HttpSession _session,
			Shoppingcart shoppingcart) {
		SessionUtil.setAttribute(_session, SessionUtil.OBJ_SHOPPINGCART,
				shoppingcart);
	}

	private SessionUtil() {
	}

}
