package com.cartmatic.estore.order.service;

import com.cartmatic.estore.common.model.order.OrderMessage;
import com.cartmatic.estore.core.service.GenericManager;

/**
 * Manager interface for OrderMessage, responsible for business processing, and communicate between web and persistence layer.
 *
 */
public interface OrderMessageManager extends GenericManager<OrderMessage> {
	public void addByAdmin(Integer salesOrderId, String orderNo, Integer customerId, String subject, String message);
	/**
	 * 获得指定客户的messages的记录数.
	 * @param customerId
	 * @return
	 */
	public int getCountCustomerMsgs(Integer customerId);
}
