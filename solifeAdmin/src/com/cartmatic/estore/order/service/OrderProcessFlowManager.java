/**
 * 
 */
package com.cartmatic.estore.order.service;

import java.io.Serializable;
import java.math.BigDecimal;

import com.cartmatic.estore.common.model.order.OrderPayment;
import com.cartmatic.estore.common.model.order.OrderPickList;
import com.cartmatic.estore.common.model.order.OrderReturn;
import com.cartmatic.estore.common.model.order.OrderSettlement;
import com.cartmatic.estore.common.model.order.OrderShipment;
import com.cartmatic.estore.common.model.order.SalesOrder;
import com.cartmatic.estore.common.model.system.AppUser;

/**
 * 订单流程处理接口
 * 
 * @author pengzhirong
 */
public interface OrderProcessFlowManager {
	
	/**
	 * 确认发货项可备货
	 * @param orderShipmentId
	 * @param curUserId 当前用户ID
	 * @return 1 成功
	 * 		   0 参数异常
	 * 		  -1 业务异常，未加锁或订单已被他人加锁或发货项状态不在"库存已分配"状态
	 */
	public int doConfirmShipment(Serializable orderShipmentId, AppUser curUser);
	
	/**
	 * 确认发货项可备货
	 * @param orderShipment
	 * @return 1 成功
	 * 		  -1 业务异常, 未加锁或订单已被他人加锁或发货项状态不在"库存已分配"状态
	 */
	public int doConfirmShipmentByRobot(OrderShipment orderShipment);
	
	/**
	 * 取消发货项
	 * @param orderShipmentId
	 * @param curUser 当前用户
	 * @return 1 成功
	 * 		   0 参数异常
	 * 		  -1 业务异常，未加锁或订单已被他人加锁或发货项已被创建备货单
	 */
	public int doCancelShipment(Serializable orderShipmentId, AppUser curUser);
	
	/**
	 * 完成发货，记录发货项状态，更新订单状态，更新所在备货单激活状态，扣款，审计
	 * @param shipmentNo
	 * @param trackingNo
	 * @param carrierName 快递公司
	 * @param curUser 当前用户
	 * @param shippingCostPaid 实际支付的运费
	 */
	public void doCompletShipping(Serializable shipmentNo, String carrierName, String trackingNo, AppUser curUser, BigDecimal shippingCostPaid);
	
	
	/**
	 * 创建备货单
	 * @param orderShipmentIdVersions 发货项ID_版本号
	 * @param curUser
	 * @return 新创建的备货单
	 */
	public OrderPickList createPickList(String [] orderShipmentIdVersions, AppUser curUser);
	
	/**
	 * 取消订单
	 * @param salesOrderId
	 * @param curUser 当前用户
	 * @return 1 成功
	 * 		   0 参数异常
	 * 		  -1 业务异常，未加锁或订单已被他人加锁或订单已部分发货或完成
	 */
	public int doCancelOrder(Serializable salesOrderId, AppUser curUser);
	
	/**
	 * 取消订单
	 * @param salesOrder
	 * @param curUser
	 * @return
	 */
	public int doCancelOrder(SalesOrder salesOrder, AppUser curUser);
	
	/**
	 * 暂停订单处理
	 * @param salesOrderId
	 * @param curUser 当前用户
	 * @return 1 成功
	 * 		   0 参数异常
	 * 		  -1 业务异常，订单已被其他人加锁或订单已完成
	 */
	public int doHoldOrder(Serializable salesOrderId, AppUser curUser);
	
	/**
	 * 恢复订单处理
	 * @param salesOrderId
	 * @param curUser 当前用户
	 * @return 1 成功
	 * 		   0 参数异常
	 * 		  -1 业务异常，订单不处在HOLD状态或订单已完成
	 */
	public int doResumeOrder(Serializable salesOrderId, AppUser curUser);
	
	/**
	 * 手工支付
	 * @param orderPayment
	 * @return	 1 - 成功
	 *  		 0 - 参数异常
	 * 		    -1 - 业务异常，支付金额大于订单未付金额或支付金额小于等于0。
	 *   		-2 - 业务异常，换货订单须等待原货退回后才可以支付！
	 */
	public int createPaymentStoreManager(OrderPayment orderPayment);
	public int createPaymentStoreFront(OrderPayment orderPayment);
	/**
	 * 退货款项充入换货订单中
	 * @param orderReturn
	 * @param exchangeOrder
	 */
	public void payExchangeOrder(OrderReturn orderReturn, SalesOrder exchangeOrder);
	
}
