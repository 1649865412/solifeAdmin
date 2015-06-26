/**
 * 
 */
package com.cartmatic.estore.order.service;

import java.math.BigDecimal;

import com.cartmatic.estore.common.model.catalog.ProductSku;
import com.cartmatic.estore.common.model.order.OrderAudit;
import com.cartmatic.estore.common.model.order.OrderPayment;
import com.cartmatic.estore.common.model.order.OrderReturn;
import com.cartmatic.estore.common.model.order.OrderShipment;
import com.cartmatic.estore.common.model.order.SalesOrder;
import com.cartmatic.estore.common.model.system.AppUser;

/**
 * 订单日志审计工具类接口
 * 
 * 对订单主要数据的变化和流程操作进行记录
 * 
 * @author pengzhirong
 */
public interface OrderAuditHelper {
	
	/**
	 * 审计确认可备货
	 * @param salesOrder
	 * @param orderShipment
	 * @param curUser
	 */
	public void logConfirmShipment(SalesOrder salesOrder, OrderShipment orderShipment, AppUser curUser);
	
	/**
	 * 审计确认可备货，订单智能审核
	 * @param salesOrder
	 * @param orderShipment
	 */
	public void logConfirmShipmentByRobot(SalesOrder salesOrder, OrderShipment orderShipment);
	
	/**
	 * 审计创建备货单
	 * @param salesOrder
	 * @param orderShipment
	 * @param curUser 创建人
	 */
	public void logCreatePickList(SalesOrder salesOrder, OrderShipment orderShipment, AppUser curUser);
	
	/**
	 * 审计取消发货项
	 * @param salesOrder
	 * @param orderShipment
	 * @param curUser
	 */
	public void logCancelShipment(SalesOrder salesOrder, OrderShipment orderShipment, AppUser curUser);
	
	/**
	 * 审计完成发货项
	 * @param salesOrder
	 * @param orderShipment
	 * @param curUser
	 */
	public void logCompleteShipment(SalesOrder salesOrder, OrderShipment orderShipment, AppUser curUser);
	
	/**
	 * 审计完成发货项，订单智能审核触发
	 * @param salesOrder
	 * @param orderShipment
	 */
	public void logCompleteShipment(SalesOrder salesOrder, OrderShipment orderShipment);
	
	/**
	 * 审计取消订单
	 * @param salesOrder
	 * @param curUser
	 */
	public void logCancelOrder(SalesOrder salesOrder, AppUser curUser);
	
	/**
	 * 审计暂停处理订单
	 * @param salesOrder
	 * @param curUser
	 */
	public void logHoldOrder(SalesOrder salesOrder, AppUser curUser);
	
	
	/**
	 * 审计恢复订单处理
	 * @param salesOrder
	 * @param curUser
	 */
	public void logResumeOrder(SalesOrder salesOrder, AppUser curUser);
	
	/**
	 * 审计添加商品到指定的发货项
	 * @param salesOrder
	 * @param orderShipment 
	 * @param productSku
	 * @param curUser
	 */
	public void logAddItem(SalesOrder salesOrder, OrderShipment orderShipment, ProductSku productSku, AppUser curUser);
	
	/**
	 * 审计从发货项中移除商品
	 * @param salesOrder
	 * @param orderShipment 
	 * @param productSku
	 * @param curUser
	 */
	public void logRemoveItem(SalesOrder salesOrder, OrderShipment orderShipment, ProductSku productSku, AppUser curUser);
	
	/**
	 * 审计移动商品到指定的发货项
	 * @param salesOrder
	 * @param fromShipment
	 * @param toShipment
	 * @param productSku
	 * @param isNewShipment
	 * @param curUser
	 */
	public void logMoveItem(SalesOrder salesOrder, OrderShipment fromShipment, OrderShipment toShipment, ProductSku productSku, boolean isNewShipment, AppUser curUser);
	
	/**
	 * 审计创建退货
	 * @param salesOrder
	 * @param orderShipment
	 * @param orderReturn
	 * @param curUser
	 */
	public void logCreateReturn(SalesOrder salesOrder, OrderShipment orderShipment, OrderReturn orderReturn, AppUser curUser);
	
	/**
	 * 审计创建换货
	 * @param salesOrder
	 * @param orderShipment
	 * @param orderReturn
	 * @param curUser
	 */
	public void logCreateExchange(SalesOrder salesOrder, OrderShipment orderShipment, OrderReturn orderReturn, SalesOrder exchangeOrder, AppUser curUser);
	
	/**
	 * 审计手工支付(主要是后台)
	 * @param salesOrder
	 * @param orderPayment
	 * @param appUser
	 */
	public void logHandworkPayment(SalesOrder salesOrder, OrderPayment orderPayment, int rtn);
	/**
	 *  审计客户支付(主要是前台)
	 * @param salesOrder
	 * @param orderPayment
	 * @param payResultInfo 支付返回信息
	 */
	public void logCustomerPayment(SalesOrder salesOrder, OrderPayment orderPayment,int rtn);
	
	/**
	 * 审计管理员保存更新物流跟踪号,运输成本
	 * @param salesOrder
	 * @param orderShipment
	 * @param trackingNo_old
	 * @param shippingCostPaid_old
	 * @param curUser
	 */
	public void logUpdateTrackingNoAndCostPaid(SalesOrder salesOrder, OrderShipment orderShipment, String trackingNo_old,BigDecimal shippingCostPaid_old,AppUser curUser);
	
	
	/**
	 * 订单超过X天未支付的，自动取消
	 * @param salesOrder
	 * @param expireDate
	 */
	public void logCancelOrderByRobot(SalesOrder salesOrder, Integer expireDate);
}
