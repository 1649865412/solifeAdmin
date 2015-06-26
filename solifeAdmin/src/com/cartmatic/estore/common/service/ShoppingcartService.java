/*
 * create date:2006-10-10
 */
package com.cartmatic.estore.common.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cartmatic.estore.common.model.cart.Shoppingcart;
import com.cartmatic.estore.common.model.cart.ShoppingcartItem;
import com.cartmatic.estore.common.model.cart.ShoppingcartItemGc;
import com.cartmatic.estore.common.model.customer.Customer;

/**
 * @author huang wenmin
 * 2008-10-23
 */
public interface ShoppingcartService {

	/**
	 * 获取某个客户的购物车
	 * @param customer
	 * @return
	 */
	public Shoppingcart loadShoppingcartByUuid(String cartUuid);
	
	/**
	 * 从cookie中获取shoppingcart
	 * @param reqeust
	 * @return
	 */
	public Shoppingcart loadCookieCart(HttpServletRequest request);
	/**
	 * 刷新购物车,并返回刷新后的购物车
	 * @param cartUuid
	 *        购物车的uuid
	 * @return
	 */
	public Shoppingcart refreshShoppingcart(String cartUuid, HttpServletRequest request, HttpServletResponse response);
	
	
	/**
	 * 出事一辆新的购物车
	 * @return
	 */
	public Shoppingcart newShoppingcart(Customer customer);
	
	/**
	 * 合并购物车
	 * 将某用户数据库中的购物车和cookie中的购物车合并，吃两辆购物车可能是同一个购物车，但是此逻辑放在shoppingcarManager类进行处理
	 * 一般情况下，此方法是在PostLoginController中进行引用 
	 * @param cartDb
	 * @param cartCookie
	 * @param request
	 * @param response
	 */
	public void doUniteShoppingcarts(String cartDb, String cartCookie, HttpServletRequest request, HttpServletResponse response);
	
	/**
	 * 清空购物车中的商品，并刷新cookie
	 * @param uuid
	 */
	public void clearShoppingcart(String uuid,HttpServletRequest request, HttpServletResponse response);
	
	/**
	 * 将礼券加入购物车
	 * @param sgc
	 * @param request
	 * @param response
	 */
	public void addGcToCart(ShoppingcartItemGc sgc,HttpServletRequest request, HttpServletResponse response);
	
	/**
	 * 检查Item的库存情况,假如库存正常返回"ok"
	 * @param request
	 * @param response
	 */
	public boolean checkInventory(ShoppingcartItem item, String skuCode);
	
}
