package com.cartmatic.estore.system.dao;

import java.math.BigDecimal;
import java.util.List;

import com.cartmatic.estore.common.model.system.PaymentHistory;
import com.cartmatic.estore.core.dao.GenericDao;
/**
 * Dao interface for PaymentHistory.
 */
public interface PaymentHistoryDao extends GenericDao<PaymentHistory> {
	 /**
     *  get all payment history by flag and isbrowsed field
     * @param flag
     * @param isBrowsed
     * @return
     */
    public List<PaymentHistory> getAllByFlagIsBrowsed(Short flag,Short isBrowsed);
    
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