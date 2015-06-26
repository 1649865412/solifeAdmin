package com.cartmatic.estore.system.service;

import java.math.BigDecimal;
import java.util.List;

import com.cartmatic.estore.common.model.system.PaymentHistory;
import com.cartmatic.estore.core.service.GenericManager;

/**
 * Manager interface for PaymentHistory, responsible for business processing, and communicate between web and persistence layer.
 *
 */
public interface PaymentHistoryManager extends GenericManager<PaymentHistory> {
	public void savePaymentHistoryForDemo(String orderNo,BigDecimal amount,Integer paymentMethodId);
	
	/**
	 * get all success and is first browsed payment records
	 * @return
	 */
	public List<PaymentHistory> getAllSuccessUnBrowsed();
	
	/**
	 * 用于检查支付历史记录是否来自支付网关的重复数据，
	 * 也即，可能支付网关会对同一次的支付会发送多次的返回数量
	 * @param orderNo
	 * @param paymentMethodId
	 * @param amount
	 * @param flag
	 * @return
	 */
	public boolean getIsExistPaymentHistory(String orderNo,Integer paymentMethodId,BigDecimal amount,Short flag);
}
