package com.cartmatic.estore.common.service.impl;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cartmatic.estore.cart.CheckoutConstants;
import com.cartmatic.estore.cart.service.ShoppingcartItemGcManager;
import com.cartmatic.estore.cart.service.ShoppingcartManager;
import com.cartmatic.estore.common.model.cart.Shoppingcart;
import com.cartmatic.estore.common.model.cart.ShoppingcartItem;
import com.cartmatic.estore.common.model.cart.ShoppingcartItemGc;
import com.cartmatic.estore.common.model.customer.Customer;
import com.cartmatic.estore.common.service.ShoppingcartService;
import com.cartmatic.estore.webapp.util.RequestUtil;

/**
 * @author huangwenmin 2008-10-31
 */
public class ShoppingcartServiceImpl implements ShoppingcartService {
	private ShoppingcartManager shoppingcartManager = null;

	public Shoppingcart loadShoppingcartByUuid(String cartUuid) {
		// TODO Auto-generated method stub
		Shoppingcart  cart = shoppingcartManager.loadShoppingcartByUuid(cartUuid);
		return cart;
	}

	public Shoppingcart refreshShoppingcart(String cartUuid, HttpServletRequest requset, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return shoppingcartManager.refreshCart(cartUuid, requset, response);
	}

	public Shoppingcart newShoppingcart(Customer customer) {
		// TODO Auto-generated method stub
		Shoppingcart cart = shoppingcartManager.initShoppingcart(customer);
		return cart;
	}

	public void setShoppingcartManager(ShoppingcartManager shoppingcartManager) {
		this.shoppingcartManager = shoppingcartManager;
	}

	public void doUniteShoppingcarts(String cartDBUuid, String cartCookieUuid,
			HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		this.shoppingcartManager.doUniteShoppingcarts(cartDBUuid, cartCookieUuid, request, response);
	}

	public Shoppingcart loadCookieCart(HttpServletRequest request) {
		// TODO Auto-generated method stub
		Cookie c = RequestUtil.getCookie(request, CheckoutConstants.SHOPPINGCART_COOKIE);
		String uuid = null;
		Shoppingcart cart = null;
		if(c!=null){
			uuid = c.getValue();
			cart = loadShoppingcartByUuid(uuid);
		}
		return cart;
	}

	public void clearShoppingcart(String uuid,HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		shoppingcartManager.doClearShoppingcart(uuid, request, response);
	}

	public void addGcToCart(ShoppingcartItemGc sgc, HttpServletRequest request,
			HttpServletResponse response) {
		String cartUuid = null;
		Cookie c = RequestUtil.getCookie(request, CheckoutConstants.SHOPPINGCART_COOKIE);
		if(c!=null){
		    cartUuid = c.getValue();
		}
		shoppingcartManager.addGcToCart(cartUuid, sgc, request, response);
	}

	public boolean checkInventory(ShoppingcartItem item, String skuCode) {
		return shoppingcartManager.checkItemInventory(item, skuCode);
	}
}
