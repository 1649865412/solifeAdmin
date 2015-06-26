package com.cartmatic.estore.order.dao;

import com.cartmatic.estore.common.model.order.OrderMessage;
import com.cartmatic.estore.core.dao.GenericDao;
/**
 * Dao interface for OrderMessage.
 */
public interface OrderMessageDao extends GenericDao<OrderMessage> {
	public int getCountCustomerMsgs(Integer customerId);
}