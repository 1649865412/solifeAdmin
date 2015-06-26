package com.cartmatic.estore.order.dao.impl;

import com.cartmatic.estore.common.model.order.OrderMessage;
import com.cartmatic.estore.core.dao.impl.HibernateGenericDaoImpl;
import com.cartmatic.estore.order.dao.OrderMessageDao;

/**
 * Dao implementation for OrderMessage.
*/
public class OrderMessageDaoImpl extends HibernateGenericDaoImpl<OrderMessage> implements OrderMessageDao {

	public int getCountCustomerMsgs(Integer customerId)
	{
		String hql = "FROM OrderMessage where customerId = ? and status=0 and createBy != ?";
		Long count = this.countByHql(hql, new Object[]{customerId, customerId});
		return count.intValue();
	}
}
