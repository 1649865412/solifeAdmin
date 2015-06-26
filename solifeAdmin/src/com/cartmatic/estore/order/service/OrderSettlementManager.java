package com.cartmatic.estore.order.service;

import java.math.BigDecimal;

import com.cartmatic.estore.common.model.order.OrderSettlement;
import com.cartmatic.estore.common.model.order.OrderShipment;
import com.cartmatic.estore.common.model.order.SalesOrder;
import com.cartmatic.estore.common.model.system.AppUser;
import com.cartmatic.estore.core.service.GenericManager;

/**
 * Manager interface for OrderSettlement, responsible for business processing, and communicate between web and persistence layer.
 *
 */
public interface OrderSettlementManager extends GenericManager<OrderSettlement> {
	
	/**
	 * 货到付款订单创建物流对帐单
	 * @param salesOrder
	 * @param orderShipment
	 * @param settlementAmount 应收款
	 * @param curUser
	 */
	public void createOrderSettlement(SalesOrder salesOrder, OrderShipment orderShipment, BigDecimal settlementAmount, AppUser curUser);
	
	/**
	 * 完成结算
	 * @param orderSettlementId
	 * @param ip 可为null
	 * @param appUser
	 */
	public void doCompleteSettlement(java.io.Serializable orderSettlementId, String ip, AppUser appUser);
		
}
