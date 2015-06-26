package com.cartmatic.estore.common.service;

import java.math.BigDecimal;
import java.util.List;

import com.cartmatic.estore.common.model.system.PaymentMethod;

/**
 * 
 * @author huangwm210
 *
 */
public interface PaymentMethodService {

	/**
	 * 当isVirtual=true时，返回所有的只支持先付款后发货的付款方式，适用于购物车有虚拟产品的情况
	 * 当isVirtual=false时，返回所有的付款方式，适用于购物车没有虚拟产品的情况
	 * @param isVirtual
	 * @return
	 */
	public List<PaymentMethod> getPaymentMethodByCart(boolean isVirtual);
	
	public PaymentMethod getPaymentById(Integer id);
	
	public void savePaymentHistoryForDemo(String orderNo,BigDecimal amount,Integer paymentMethodId);
	
	public PaymentMethod getPaymentMethodByCode(String code);
}
