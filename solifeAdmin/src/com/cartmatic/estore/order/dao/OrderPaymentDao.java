package com.cartmatic.estore.order.dao;

import com.cartmatic.estore.common.model.order.OrderPayment;
import com.cartmatic.estore.core.dao.GenericDao;
/**
 * Dao interface for OrderPayment.
 */
public interface OrderPaymentDao extends GenericDao<OrderPayment> {
	
	/**
	 * 获取取后一条订单支付记录
	 * @param salesOrderId
	 * @return
	 */
	public OrderPayment getLatestOrderPayment(Integer salesOrderId);
}