/**
 * 
 */
package com.cartmatic.estore.order.service.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.cartmatic.estore.common.model.catalog.ProductSku;
import com.cartmatic.estore.common.model.order.OrderAudit;
import com.cartmatic.estore.common.model.order.OrderPayment;
import com.cartmatic.estore.common.model.order.OrderReturn;
import com.cartmatic.estore.common.model.order.OrderShipment;
import com.cartmatic.estore.common.model.order.SalesOrder;
import com.cartmatic.estore.common.model.system.AppUser;
import com.cartmatic.estore.core.util.ContextUtil;
import com.cartmatic.estore.core.util.I18nUtil;
import com.cartmatic.estore.order.OrderConstants;
import com.cartmatic.estore.order.dao.OrderAuditDao;
import com.cartmatic.estore.order.service.OrderAuditHelper;
import com.cartmatic.estore.webapp.util.RequestContext;

/**
 * 订单日志审计工具类
 * 
 * 对订单主要数据的变化和流程操作进行记录
 * 
 * @author pengzhirong
 */
public class OrderAuditHelperImpl implements OrderAuditHelper {
	private OrderAuditDao orderAuditDao=null;
	
	/**
	 * 审计确认可备货
	 * @param salesOrder
	 * @param orderShipment
	 * @param curUser
	 */
	public void logConfirmShipment(SalesOrder salesOrder, OrderShipment orderShipment, AppUser curUser){
		String detail =curUser.getUsername()+this.getMessage("order.process.confirmShipment", orderShipment.getShipmentNo());
		OrderAudit orderAudit = createOrderAuditBySystem(salesOrder.getSalesOrderId(), detail);
		salesOrder.addOrderAudit(orderAudit);
	}
	
	/**
	 * 审计确认可备货，订单智能审核
	 * @param salesOrder
	 * @param orderShipment
	 */
	public void logConfirmShipmentByRobot(SalesOrder salesOrder, OrderShipment orderShipment){
		String detail = OrderConstants.SYS_ADDED_BY+this.getMessage("order.process.confirmShipment", orderShipment.getShipmentNo());
		OrderAudit orderAudit = createOrderAuditBySystem(salesOrder.getSalesOrderId(), detail);
		salesOrder.addOrderAudit(orderAudit);
	}
	
	/**
	 * 审计创建备货单
	 * @param salesOrder
	 * @param orderShipment
	 * @param curUser 创建人
	 */
	public void logCreatePickList(SalesOrder salesOrder, OrderShipment orderShipment, AppUser curUser){
		String detail = this.getMessage("order.process.createPickList", curUser.getUsername(), orderShipment.getShipmentNo());
		OrderAudit orderAudit = createOrderAuditBySystem(salesOrder.getSalesOrderId(), detail);
		salesOrder.addOrderAudit(orderAudit);
		
	}
	
	/**
	 * 审计取消发货项
	 * @param salesOrder
	 * @param orderShipment
	 * @param curUser
	 */
	public void logCancelShipment(SalesOrder salesOrder, OrderShipment orderShipment, AppUser curUser){
		String detail = curUser.getUsername()+ this.getMessage("order.process.cancelShipment", orderShipment.getShipmentNo());
		OrderAudit orderAudit = createOrderAuditBySystem(orderShipment.getSalesOrderId(), detail);
		salesOrder.addOrderAudit(orderAudit);
	}
	
	/**
	 * 审计完成发货项
	 * @param salesOrder
	 * @param orderShipment
	 * @param curUser
	 */
	public void logCompleteShipment(SalesOrder salesOrder, OrderShipment orderShipment, AppUser curUser){
		String detail = this.getMessage("order.process.completeShipment", curUser.getUsername(), orderShipment.getShipmentNo());
		OrderAudit orderAudit = createOrderAuditBySystem(orderShipment.getSalesOrderId(), detail);
		salesOrder.addOrderAudit(orderAudit);
	}
	
	/**
	 * 审计完成发货项，订单智能审核触发
	 * @param salesOrder
	 * @param orderShipment
	 */
	public void logCompleteShipment(SalesOrder salesOrder, OrderShipment orderShipment){
		String detail = this.getMessage("order.process.completeShipment", OrderConstants.SYS_ADDED_BY, orderShipment.getShipmentNo());
		OrderAudit orderAudit = createOrderAuditBySystem(orderShipment.getSalesOrderId(), detail);
		salesOrder.addOrderAudit(orderAudit);
	}
	
	/**
	 * 审计取消订单
	 * @param salesOrder
	 * @param curUser
	 */
	public void logCancelOrder(SalesOrder salesOrder, AppUser curUser){
		String detail = this.getMessage("order.process.cancelOrder", curUser.getUsername());
		OrderAudit orderAudit = createOrderAuditBySystem(salesOrder.getSalesOrderId(), detail);
		salesOrder.addOrderAudit(orderAudit);
	}
	
	/**
	 * 审计暂停处理订单
	 * @param salesOrder
	 * @param curUser
	 */
	public void logHoldOrder(SalesOrder salesOrder, AppUser curUser){
		String detail = this.getMessage("order.process.holdOrder", curUser.getUsername());
		OrderAudit orderAudit = createOrderAuditBySystem(salesOrder.getSalesOrderId(), detail);
		salesOrder.addOrderAudit(orderAudit);
	}
	
	
	/**
	 * 审计恢复订单处理
	 * @param salesOrder
	 * @param curUser
	 */
	public void logResumeOrder(SalesOrder salesOrder, AppUser curUser){
		String detail = this.getMessage("order.process.resumeOrder", curUser.getUsername());
		OrderAudit orderAudit = createOrderAuditBySystem(salesOrder.getSalesOrderId(), detail);
		salesOrder.addOrderAudit(orderAudit);
	}
	
	/**
	 * 审计添加商品到指定的发货项
	 * @param salesOrder
	 * @param orderShipment 
	 * @param productSku
	 * @param curUser
	 */
	public void logAddItem(SalesOrder salesOrder, OrderShipment orderShipment, ProductSku productSku, AppUser curUser){
		String detail = this.getMessage("order.process.addItem", curUser.getUsername(), productSku.getProductSkuCode(), orderShipment.getShipmentNo());
		OrderAudit orderAudit = createOrderAuditBySystem(salesOrder.getSalesOrderId(), detail);
		salesOrder.addOrderAudit(orderAudit);
	}
	
	/**
	 * 审计从发货项中移除商品
	 * @param salesOrder
	 * @param orderShipment 
	 * @param productSku
	 * @param curUser
	 */
	public void logRemoveItem(SalesOrder salesOrder, OrderShipment orderShipment, ProductSku productSku, AppUser curUser){
		String detail = this.getMessage("order.process.removeItem", curUser.getUsername(), orderShipment.getShipmentNo(), productSku.getProductSkuCode());
		OrderAudit orderAudit = createOrderAuditBySystem(salesOrder.getSalesOrderId(), detail);
		salesOrder.addOrderAudit(orderAudit);
	}
	
	/**
	 * 审计移动商品到指定的发货项
	 * @param salesOrder
	 * @param fromShipment
	 * @param toShipment
	 * @param productSku
	 * @param isNewShipment 是否移动到新发货项
	 * @param curUser
	 */
	public void logMoveItem(SalesOrder salesOrder, OrderShipment fromShipment, OrderShipment toShipment, ProductSku productSku, boolean isNewShipment, AppUser curUser){
		String msgKey = null;
		if(isNewShipment)
			msgKey = "order.process.moveItem2";
		else
			msgKey = "order.process.moveItem1";
		String detail = this.getMessage(msgKey, curUser.getUsername(), fromShipment.getShipmentNo(), productSku.getProductSkuCode(), toShipment.getShipmentNo());
		OrderAudit orderAudit = createOrderAuditBySystem(salesOrder.getSalesOrderId(), detail);
		salesOrder.addOrderAudit(orderAudit);
	}
	
	/**
	 * 审计创建退货
	 * @param salesOrder
	 * @param orderShipment
	 * @param orderReturn
	 * @param curUser
	 */
	public void logCreateReturn(SalesOrder salesOrder, OrderShipment orderShipment, OrderReturn orderReturn, AppUser curUser){
		String detail = this.getMessage("order.return.createReturn",curUser.getUsername(),orderReturn.getRmaNo());
		OrderAudit orderAudit = createOrderAuditBySystem(salesOrder.getSalesOrderId(), detail);
		salesOrder.addOrderAudit(orderAudit);
	}
	/**
	 * 审计创建换货
	 * @param salesOrder
	 * @param orderShipment
	 * @param orderReturn
	 * @param curUser
	 */
	public void logCreateExchange(SalesOrder salesOrder, OrderShipment orderShipment, OrderReturn orderReturn, SalesOrder exchangeOrder, AppUser curUser){
		String detail = this.getMessage("order.return.createExchange",curUser.getUsername(),orderReturn.getRmaNo(),exchangeOrder.getOrderNo());
		OrderAudit orderAudit = createOrderAuditBySystem(salesOrder.getSalesOrderId(), detail);
		salesOrder.addOrderAudit(orderAudit);
	}
	
	private OrderAudit createOrderAuditBySystem(Serializable salesOrderId, String detail){
		return this.createOrderAudit(salesOrderId, detail, OrderConstants.AUDIT_TANSACTION_TYPE_SYS, OrderConstants.SYS_ADDED_BY);
	}
	
	private OrderAudit createOrderAudit(Serializable salesOrderId, String detail, String type, String addedBy){
		
		OrderAudit orderAudit = new OrderAudit();
		orderAudit.setTransactionType(type);
		orderAudit.setAddedBy(addedBy);
		orderAudit.setCreateTime(new Date());
		orderAudit.setDetail(detail);
		orderAudit.setSalesOrderId(new Integer(salesOrderId.toString()));
		
		return orderAudit;
	}
	
	private String getMessage(String key, Object... params){
		return ContextUtil.getSpringContext().getMessage(key, params, null);
	}

	public void logHandworkPayment(SalesOrder salesOrder, OrderPayment orderPayment, int rtn) {
		String transactionType=this.getMessage("salesOrder.paymentType_"+orderPayment.getTransactionType())+" "+
							I18nUtil.getInstance().getMessage("orderPayment.createPaymentResult_"+rtn);
		String detail = RequestContext.getCurrentUserName() + this.getMessage("orderPayment.action.createHandworkPayment", 
				orderPayment.getPaymentAmount(),transactionType);
		OrderAudit orderAudit = createOrderAuditBySystem(salesOrder.getSalesOrderId(), detail);
		orderAuditDao.save(orderAudit);
	}

	public void logCustomerPayment(SalesOrder salesOrder, OrderPayment orderPayment, int rtn) {
		String payMentResult=I18nUtil.getInstance().getMessage("orderPayment.createPaymentResult_"+rtn);
		String detail =I18nUtil.getInstance().getMessage("orderPayment.action.createCustomerPayment",new Object[]{ orderPayment.getPaymentAmount(),orderPayment.getPaymentGatewayName(),payMentResult});
		OrderAudit orderAudit = createOrderAuditBySystem(salesOrder.getSalesOrderId(), detail);
		orderAuditDao.save(orderAudit);
	}
	
	public void logUpdateTrackingNoAndCostPaid(SalesOrder salesOrder,OrderShipment orderShipment, String trackingNo_old,BigDecimal shippingCostPaid_old, AppUser curUser) {
		String detail = this.getMessage("salesOrder.update.trackingNoAndCostPaid", curUser.getUsername(), trackingNo_old,shippingCostPaid_old,orderShipment.getTrackingNo(),orderShipment.getShippingCostPaid());
		OrderAudit orderAudit = createOrderAuditBySystem(orderShipment.getSalesOrderId(), detail);
		salesOrder.addOrderAudit(orderAudit);
	}

	public void setOrderAuditDao(OrderAuditDao orderAuditDao) {
		this.orderAuditDao = orderAuditDao;
	}

	public void logCancelOrderByRobot(SalesOrder salesOrder, Integer expireDate) {
		String detail =this.getMessage("order.process.cancelOrderByRobot", expireDate);
		OrderAudit orderAudit = createOrderAuditBySystem(salesOrder.getSalesOrderId(), detail);
		salesOrder.addOrderAudit(orderAudit);
	}
	
}
