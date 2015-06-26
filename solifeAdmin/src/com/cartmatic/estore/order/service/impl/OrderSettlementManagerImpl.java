package com.cartmatic.estore.order.service.impl;

import java.math.BigDecimal;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.common.model.order.OrderSettlement;
import com.cartmatic.estore.common.model.order.OrderShipment;
import com.cartmatic.estore.common.model.order.SalesOrder;
import com.cartmatic.estore.common.model.system.AppUser;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;
import com.cartmatic.estore.order.dao.OrderSettlementDao;
import com.cartmatic.estore.order.service.OrderSettlementManager;


/**
 * Manager implementation for OrderSettlement, responsible for business processing, and communicate between web and persistence layer.
 */
public class OrderSettlementManagerImpl extends GenericManagerImpl<OrderSettlement> implements OrderSettlementManager {

	private OrderSettlementDao orderSettlementDao = null;
	
	/**
	 * @param orderSettlementDao
	 *            the orderSettlementDao to set
	 */
	public void setOrderSettlementDao(OrderSettlementDao orderSettlementDao) {
		this.orderSettlementDao = orderSettlementDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		dao = orderSettlementDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete(java.lang.Object)
	 */
	@Override
	protected void onDelete(OrderSettlement entity) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave(java.lang.Object)
	 */
	@Override
	protected void onSave(OrderSettlement entity) {

	}
	
	/**
	 * 货到付款订单创建物流对帐单
	 * @param salesOrder
	 * @param orderShipment
	 * @param settlementAmount 应收款
	 * @param curUser
	 */
	public void createOrderSettlement(SalesOrder salesOrder, OrderShipment orderShipment, BigDecimal settlementAmount, AppUser curUser){
		OrderSettlement settlement = new OrderSettlement();
		if(curUser!=null)
			settlement.setAddedBy(curUser.getUsername());
		settlement.setCarrierName(orderShipment.getCarrierName());
		settlement.setIsComplete(Constants.FLAG_FALSE);
		settlement.setOrderId(salesOrder.getSalesOrderId());
		settlement.setOrderNo(salesOrder.getOrderNo());
		settlement.setOriginalTotal(orderShipment.getTotalAfterTax());
		settlement.setSettlementAmount(settlementAmount);
		settlement.setShipmentNo(orderShipment.getShipmentNo());
		settlement.setTrackingNo(orderShipment.getTrackingNo());
		settlement.setShipmentId(orderShipment.getOrderShipmentId());
		
		this.save(settlement);
	}
	
	public void doCompleteSettlement(java.io.Serializable orderSettlementId, String ip, AppUser appUser){
		OrderSettlement orderSettlement = this.getById(new Integer(orderSettlementId.toString()));
		if(orderSettlement!=null && orderSettlement.getIsComplete().byteValue()==Constants.FLAG_FALSE.byteValue()){
			orderSettlement.setIsComplete(Constants.FLAG_TRUE);
			this.save(orderSettlement);
		}
	}

}
