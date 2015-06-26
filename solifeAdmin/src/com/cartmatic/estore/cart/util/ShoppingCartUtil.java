package com.cartmatic.estore.cart.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cartmatic.estore.cart.CheckoutConstants;
import com.cartmatic.estore.cart.dao.ShoppingcartDao;
import com.cartmatic.estore.common.helper.ConfigUtil;
import com.cartmatic.estore.common.model.cart.Shoppingcart;
import com.cartmatic.estore.common.model.customer.Customer;
import com.cartmatic.estore.common.service.ShoppingcartService;
import com.cartmatic.estore.webapp.util.RequestContext;
import com.cartmatic.estore.webapp.util.RequestUtil;

public class ShoppingCartUtil {

	private final static ShoppingCartUtil shoppingCartUtil = new ShoppingCartUtil();
	private ShoppingcartDao shoppingcartDao;
	
	private ShoppingcartService shoppingcartService;
	
	public void setShoppingcartService(ShoppingcartService shoppingcartService) {
		this.shoppingcartService = shoppingcartService;
	}
	
	public void setShoppingcartDao(ShoppingcartDao shoppingcartDao) {
		this.shoppingcartDao = shoppingcartDao;
	}

	/**
	 * 
	 * @param request
	 * @param response
	 */
	
	public static synchronized ShoppingCartUtil  getInstance(){
		return shoppingCartUtil;
	}
	public void removeShoppingcartCookie(HttpServletRequest request,
			HttpServletResponse response) {
		RequestUtil.setCookie(response, CheckoutConstants.SHOPPINGCART_COOKIE, "",request.getContextPath());
		RequestUtil.setCookie(response, CheckoutConstants.SHOPPINGCART_PRICE_COOKIE, "0", request.getContextPath());
		RequestUtil.setCookie(response, CheckoutConstants.C_ITEMCOUNT_COOKIE, "0", request.getContextPath());
		RequestUtil.setCookie(response, CheckoutConstants.F_ITEMCOUNT_COOKIE, "0", request.getContextPath());
		RequestUtil.setCookie(response, CheckoutConstants.SHOPPINGCAT_ITEMCOUT_COOKIE, "0", request.getContextPath());
	}
	
	/**
	 * <h3>获取当前用户的购物车</h3>
	 * <p>由于使用了acegi架构，用户登陆后，即使在持久层配置了自动加载购物车，但是依然还是无法得到用户的购物车，即，不能
	 * 使用customer.getShoppingcart()的方法得到购物车。因此，提供此方法来获得当前用户的购物车</p>
	 * @return
	 */
	public Shoppingcart getCurrentUserShoppingcart(){
		Customer customer = (Customer) RequestContext.getCurrentUser();		
		return getCurrentUserShoppingcart(customer);
	}	
	public Shoppingcart getCurrentUserShoppingcart(Customer customer)
	{
		Shoppingcart cart = shoppingcartDao.getShoppingcartByCustomer(customer, ConfigUtil.getInstance().getStore());
		return cart;
	}
	

    /**
     * 合并购物车信息
     * @param req
     * @param resp
     */
	public void setShoppingcartInfo(HttpServletRequest req, HttpServletResponse resp, Customer customer) {
		//Customer customer = (Customer) RequestContext.getCurrentUser();
	    Shoppingcart cartDb = ShoppingCartUtil.getInstance().getCurrentUserShoppingcart(customer);
	    Cookie cookie = RequestUtil.getCookie(req,CheckoutConstants.SHOPPINGCART_COOKIE);
	    String cartCookieUuid = "";
	    if(cookie!=null){
	    	cartCookieUuid = cookie.getValue();
	    }
	    
	    if(cartDb!=null){
	    	if(!cartCookieUuid.equals("")){
	    		shoppingcartService.doUniteShoppingcarts(cartDb.getUuid(), cartCookieUuid, req, resp);
	    		shoppingcartService.refreshShoppingcart(cartDb.getUuid(), req, resp);
	    	}
	    	else{
	    		shoppingcartService.refreshShoppingcart(cartDb.getUuid(), req, resp);
	    	}
	    }
	    else if(!cartCookieUuid.equals("")){
	        Shoppingcart cartCookie = shoppingcartService.loadShoppingcartByUuid(cartCookieUuid);
	        if(cartCookie!=null&&cartCookie.getCustomerId()==null){
	        	cartCookie.setCustomerId(customer.getAppuserId());
	            shoppingcartService.refreshShoppingcart(cartCookieUuid, req, resp);
	        }
	        else if(cartCookie!=null&&cartCookie.getCustomerId()!=null){//cookie中的购物车不是刚刚登陆的此用户
	        	ShoppingCartUtil.getInstance().removeShoppingcartCookie(req, resp);
	        }
	    }
	    else{
	    	Shoppingcart newCart = shoppingcartService.newShoppingcart(customer);
	    	shoppingcartService.refreshShoppingcart(newCart.getUuid(), req, resp);
	    }
	}
	
}
