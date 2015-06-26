/*
 * create date:2006-6-12
 */

package com.cartmatic.estore.cart.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cartmatic.estore.exception.GiftCertificateStateException;
import com.cartmatic.estore.exception.OutOfStockException;


public interface CheckoutManager {

	/**
	 * 下单
	 * 需要进行最后一次的库存检查。
	 * 当返回"ok"表示下单成功；
	 * 当返回不是"ok"时，返回的字符串的格式将按以下输出：productName1:msg1###productName2:msg2
	 * @param request
	 * @param response
	 */
	public void doPlaceOrder(HttpServletRequest request,
			HttpServletResponse response) throws OutOfStockException, GiftCertificateStateException;

}
