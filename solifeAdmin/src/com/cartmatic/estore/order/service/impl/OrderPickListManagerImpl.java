package com.cartmatic.estore.order.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import org.springframework.util.Assert;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.common.model.order.OrderPickList;
import com.cartmatic.estore.common.model.order.OrderShipment;
import com.cartmatic.estore.common.model.order.OrderSku;
import com.cartmatic.estore.common.model.system.AppUser;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;
import com.cartmatic.estore.order.OrderConstants;
import com.cartmatic.estore.order.dao.OrderPickListDao;
import com.cartmatic.estore.order.dao.OrderShipmentDao;
import com.cartmatic.estore.order.service.OrderPickListManager;
import com.cartmatic.estore.order.service.SalesOrderManager;
import com.cartmatic.estore.webapp.util.RequestContext;


/**
 * Manager implementation for OrderPickList, responsible for business processing, and communicate between web and persistence layer.
 */
public class OrderPickListManagerImpl extends GenericManagerImpl<OrderPickList> implements OrderPickListManager {

	private OrderPickListDao orderPickListDao = null;
	private OrderShipmentDao orderShipmentDao = null;
	//private SalesOrderManager salesOrderManager = null;
	
    

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		dao = orderPickListDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete(java.lang.Object)
	 */
	@Override
	protected void onDelete(OrderPickList entity) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave(java.lang.Object)
	 */
	@Override
	protected void onSave(OrderPickList entity) {

	}
	
	public List<OrderPickList> getActivePickLists(Integer createdBy){
		return orderPickListDao.getActivePickLists(createdBy);
	}
	
	public List<OrderPickList> getInActivePickLists(Integer fetchSize, Integer createdBy){
		return orderPickListDao.getInActivePickLists(fetchSize, createdBy);
	}
	
	public void removeFromPickList(Serializable orderShipmentId){
		Assert.notNull(orderShipmentId);
		
		OrderShipment orderShipment = orderShipmentDao.getById(new Integer(orderShipmentId.toString()));
		OrderPickList orderPickList = orderShipment.getOrderPickList();
		if(orderPickList==null) return;
		
		AppUser appUser = (AppUser) RequestContext.getCurrentUser();
		if( (orderShipment.getStatus().shortValue()==OrderConstants.SHIPMENT_STATUS_PICKING.shortValue()
						||orderShipment.getStatus().shortValue()==OrderConstants.STATUS_CANCELLED.shortValue())){
			if(orderShipment.getStatus().shortValue()==OrderConstants.SHIPMENT_STATUS_PICKING.shortValue())
				orderShipment.setStatus(OrderConstants.SHIPMENT_STATUS_PICKING_AVAILABLE);
			orderShipment.setOrderPickList(null);
			orderShipmentDao.save(orderShipment);
			
			doSetActive(orderPickList.getOrderPickListId());
		}
	}
	
	public void doSetActive(Serializable orderPickListId){
		OrderPickList orderPickList = this.getById(orderPickListId);
		Set<OrderShipment> set = orderPickList.getOrderShipments();
		
		if(set==null || set.size()==0){
			this.delete(orderPickList);
			return;
		}
		
		boolean isActive = false;
		for(OrderShipment orderShipment:set){
			if(orderShipment.getStatus().shortValue()==OrderConstants.SHIPMENT_STATUS_PICKING.shortValue()){
				isActive = true;
				break;
			}
		}
		if(isActive)
			orderPickList.setIsActive(Constants.FLAG_TRUE);
		else
			orderPickList.setIsActive(Constants.FLAG_FALSE);
		
		this.save(orderPickList);
	}
	
	public void delete(OrderPickList orderPickList){
		this.orderPickListDao.delete(orderPickList);
	}
	
	public List<OrderSku> getRecentShipments(int limit)
	{
		return orderShipmentDao.getRecentShipments(limit);
	}
	
	//setter ....
	public void setOrderShipmentDao(OrderShipmentDao avalue) {
		this.orderShipmentDao = avalue;
	}
	/**
	 * @param orderPickListDao
	 *            the orderPickListDao to set
	 */
	public void setOrderPickListDao(OrderPickListDao orderPickListDao) {
		this.orderPickListDao = orderPickListDao;
	}
}
