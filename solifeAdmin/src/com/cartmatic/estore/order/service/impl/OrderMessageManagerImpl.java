package com.cartmatic.estore.order.service.impl;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.common.model.order.OrderMessage;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;
import com.cartmatic.estore.order.dao.OrderMessageDao;
import com.cartmatic.estore.order.service.OrderMessageManager;


/**
 * Manager implementation for OrderMessage, responsible for business processing, and communicate between web and persistence layer.
 */
public class OrderMessageManagerImpl extends GenericManagerImpl<OrderMessage> implements OrderMessageManager {

	private OrderMessageDao orderMessageDao = null;

	public void addByAdmin(Integer salesOrderId, String orderNo, Integer customerId, String subject, String message)
	{
		OrderMessage obj = new OrderMessage();
		obj.setSalesOrderId(salesOrderId);
		obj.setOrderNo(orderNo);
		obj.setCustomerId(customerId);
		obj.setSubject(subject);
		obj.setMessage(message);
		obj.setStatus(Constants.STATUS_NOT_PUBLISHED);
		this.save(obj);
	}
	
	public int getCountCustomerMsgs(Integer customerId)
	{
		return orderMessageDao.getCountCustomerMsgs(customerId);
	}
	
	/**
	 * @param orderMessageDao
	 *            the orderMessageDao to set
	 */
	public void setOrderMessageDao(OrderMessageDao orderMessageDao) {
		this.orderMessageDao = orderMessageDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		dao = orderMessageDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete()
	 */
	@Override
	protected void onDelete(OrderMessage entity) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave()
	 */
	@Override
	protected void onSave(OrderMessage entity) {

	}

}
