/**
 * 
 */
package com.cartmatic.estore.order.service.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.catalog.CatalogConstants;
import com.cartmatic.estore.common.model.content.SystemMessage;
import com.cartmatic.estore.common.model.order.OrderPayment;
import com.cartmatic.estore.common.model.order.OrderPickList;
import com.cartmatic.estore.common.model.order.OrderReturn;
import com.cartmatic.estore.common.model.order.OrderShipment;
import com.cartmatic.estore.common.model.order.OrderSku;
import com.cartmatic.estore.common.model.order.SalesOrder;
import com.cartmatic.estore.common.model.order.SalesOrderGeoip;
import com.cartmatic.estore.common.model.sales.GiftCertificate;
import com.cartmatic.estore.common.model.system.AppUser;
import com.cartmatic.estore.common.model.system.SystemQueue;
import com.cartmatic.estore.common.service.GiftCertificateService;
import com.cartmatic.estore.common.service.InventoryService;
import com.cartmatic.estore.common.service.ProductService;
import com.cartmatic.estore.common.service.PromoService;
import com.cartmatic.estore.common.service.RecommendedService;
import com.cartmatic.estore.content.service.SystemMessageManager;
import com.cartmatic.estore.core.util.ContextUtil;
import com.cartmatic.estore.customer.CustomerConstants;
import com.cartmatic.estore.customer.service.CustomerManager;
import com.cartmatic.estore.customer.service.ShopPointHistoryManager;
import com.cartmatic.estore.exception.OutOfStockException;
import com.cartmatic.estore.order.OrderConstants;
import com.cartmatic.estore.order.service.OrderAuditHelper;
import com.cartmatic.estore.order.service.OrderPickListManager;
import com.cartmatic.estore.order.service.OrderProcessFlowManager;
import com.cartmatic.estore.order.service.OrderSettlementManager;
import com.cartmatic.estore.order.service.SalesOrderManager;
import com.cartmatic.estore.sales.model.EmailModel;
import com.cartmatic.estore.supplier.service.PurchaseOrderItemManager;
import com.cartmatic.estore.system.model.GeoIpCollectorQueue;
import com.cartmatic.estore.system.service.SystemQueueManager;
import com.cartmatic.estore.textsearch.SearchConstants;
import com.cartmatic.estore.webapp.event.IndexNotifyEvent;

/**
 * @author pengzhirong
 */
public class OrderProcessFlowManagerImpl implements OrderProcessFlowManager, InitializingBean {
	
	protected final transient Log	logger	= LogFactory.getLog(getClass());
	
	private SalesOrderManager salesOrderManager = null;
	
	private OrderPickListManager orderPickListManager = null;
	
	private OrderAuditHelper orderAuditHelper = null;
	
	private OrderSettlementManager orderSettlementManager = null;
	
	private ShopPointHistoryManager shopPointHistoryManager = null;
	
	private GiftCertificateService giftCertificateService = null;
	
	private InventoryService inventoryService = null;
	
	private ProductService productService = null;
	
	private RecommendedService recommendedService = null;
	
	private SystemMessageManager systemMessageManager = null;
	
	private SystemQueueManager systemQueueManager=null;
	
	private PurchaseOrderItemManager purchaseOrderItemManager=null;
	
	private CustomerManager customerManager = null;
	
	public void setSystemMessageManager(SystemMessageManager systemMessageManager) {
		this.systemMessageManager = systemMessageManager;
	}
	
	public void setPurchaseOrderItemManager(
			PurchaseOrderItemManager purchaseOrderItemManager) {
		this.purchaseOrderItemManager = purchaseOrderItemManager;
	}

	public void setRecommendedService(RecommendedService recommendedService) {
		this.recommendedService = recommendedService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	public void setShopPointHistoryManager(
			ShopPointHistoryManager shopPointHistoryManager) {
		this.shopPointHistoryManager = shopPointHistoryManager;
	}

	public void setInventoryService(InventoryService inventoryService) {
		this.inventoryService = inventoryService;
	}
	
    public void setGiftCertificateService(GiftCertificateService avalue) {
		this.giftCertificateService = avalue;
	}

	public void setOrderSettlementManager(
			OrderSettlementManager orderSettlementManager) {
		this.orderSettlementManager = orderSettlementManager;
	}

	public void setOrderAuditHelper(OrderAuditHelper orderAuditHelper) {
		this.orderAuditHelper = orderAuditHelper;
	}

	public void setOrderPickListManager(OrderPickListManager orderPickListManager) {
		this.orderPickListManager = orderPickListManager;
	}


	public void setSalesOrderManager(SalesOrderManager salesOrderManager) {
		this.salesOrderManager = salesOrderManager;
	}

	/**
	 * 确认发货项可备货
	 * @param orderShipmentId
	 * @param curUserId 当前用户ID
	 * @return 1 成功
	 * 		   0 参数异常
	 * 		  -1 业务异常, 未加锁或订单已被他人加锁或发货项状态不在"库存已分配"状态
	 */
	public int doConfirmShipment(Serializable orderShipmentId, AppUser curUser){
		if(orderShipmentId==null || curUser==null)
			return 0;
		
		OrderShipment orderShipment = salesOrderManager.getOrderShipmentById(orderShipmentId);
		SalesOrder salesOrder = orderShipment.getSalesOrder();
		
		if(salesOrder.getIsLocked().byteValue()!=Constants.FLAG_TRUE.byteValue() 
				|| curUser.getAppuserId().intValue()!=salesOrder.getLockedBy().intValue())
			return -1;
		
		//如果发货项当前状态不是库存已分配，则直接返回
		if(orderShipment.getStatus().shortValue()!=OrderConstants.SHIPMENT_STATUS_INVENTORY_ASSIGNED.shortValue())
			return -1;		
		//审计
		orderAuditHelper.logConfirmShipment(salesOrder, orderShipment, curUser);		
		confirmShipment(salesOrder, orderShipment);		
		return 1;
	}
	
	/**
	 * 确认发货项可备货
	 * @param orderShipment
	 * @return 1 成功
	 * 		  -1 业务异常, 未加锁或订单已被他人加锁或发货项状态不在"库存已分配"状态
	 */
	public int doConfirmShipmentByRobot(OrderShipment orderShipment){
		SalesOrder salesOrder = orderShipment.getSalesOrder();
		//如果发货项当前状态不是库存已分配，则直接返回
		if(orderShipment.getStatus().shortValue()!=OrderConstants.SHIPMENT_STATUS_INVENTORY_ASSIGNED.shortValue())
			return -1;		
		//审计
		orderAuditHelper.logConfirmShipmentByRobot(salesOrder, orderShipment);		
		confirmShipment(salesOrder, orderShipment);		
		return 1;
	}
	
	private void confirmShipment(SalesOrder salesOrder, OrderShipment orderShipment){
		//款到发货订单
		if(!salesOrder.isCod()){
			OrderPayment orderPayment = salesOrderManager.getLatestOrderPayment(salesOrder.getSalesOrderId());
			if(orderPayment!=null && salesOrder.getPaymentStatus().shortValue() == OrderConstants.PAYMENT_STATUS_PAID.shortValue())//salesOrder.getActualBalance(orderPayment.getBalance()).compareTo(orderShipment.getTotalAfterTax())>=0)
				orderShipment.setStatus(OrderConstants.SHIPMENT_STATUS_PICKING_AVAILABLE);
			else
				//等待支付
				orderShipment.setStatus(OrderConstants.SHIPMENT_STATUS_AWAITING_PAYMENT);
		}else{
			orderShipment.setStatus(OrderConstants.SHIPMENT_STATUS_PICKING_AVAILABLE);
		}
		
		//处理数字产品
		handleDigitalProduct(salesOrder, orderShipment);
		
		salesOrderManager.save(orderShipment);
		//unlock and save
		salesOrderManager.unlock(salesOrder);
		
		//系统消息提示
		if(salesOrder.getCustomerId()!=null)
			systemMessageManager.addSystemMessage(this.getMessage("salesOrder.message.confirm", orderShipment.getShipmentNo()), SystemMessage.MSG_TYPE_ORDER, (AppUser)salesOrder.getCustomer());
		
		//TODO  发送确认邮件
	}
	
	/**
	 * 处理数字产品
	 * @param salesOrder
	 * @param orderShipment
	 */
	private void handleDigitalProduct(SalesOrder salesOrder, OrderShipment orderShipment){
		if(!orderShipment.hasPhysicalSku() && orderShipment.getStatus().shortValue()==OrderConstants.SHIPMENT_STATUS_PICKING_AVAILABLE.shortValue()){
			//审计
			orderAuditHelper.logCompleteShipment(salesOrder, orderShipment);
			
			orderShipment.setStatus(OrderConstants.SHIPMENT_STATUS_SHIPPED);
			//扣除货款,更新订单状态
			payOrderShipment(salesOrder, orderShipment);
			salesOrder.updateOrderStatusByShipments();
			//数字产品处理,发送礼券
			sendDigitalProduct(salesOrder.getOrderNo(), orderShipment);
			
			//发送积分
			sendShopPoint(salesOrder);
			
			//发送优惠券
			sendCoupon(salesOrder);
		}
	}
	
	/**
	 * 发送数字产品
	 * @param orderNo
	 * @param orderShipment
	 */
	private void sendDigitalProduct(String orderNo, OrderShipment orderShipment){
		Set<OrderSku> orderSkus = orderShipment.getOrderSkus();
		for(OrderSku orderSku: orderSkus){
			if(orderSku.getItemType().shortValue()==Constants.ITEM_TYPE_GC.shortValue()){
				GiftCertificate gc = orderSku.getGiftCertificate();
				if(gc.getIsSentByEmail()==GiftCertificate.SENTBYEMAIL_YES.shortValue())
				    giftCertificateService.doSendGiftCertificate(gc, orderNo);
			}
		}
	}
	
	/**
	 * 只有在发货项发货时才扣款，不管是货到付款还是款到发货
	 * @param salesOrder
	 * @param orderShipment
	 */
	private void payOrderShipment(SalesOrder salesOrder, OrderShipment orderShipment){
		BigDecimal totalAfterTax = orderShipment.getTotalAfterTax();
		OrderPayment newOrderPayment = populateOrderPayment(salesOrder.getSalesOrderId(), OrderConstants.TRANSACTION_TYPE_SHIP, totalAfterTax);
		OrderPayment latestOrderPayment = salesOrderManager.getLatestOrderPayment(salesOrder.getSalesOrderId());
		if(latestOrderPayment!=null)
			newOrderPayment.setBalance(latestOrderPayment.getBalance().add(totalAfterTax.negate()));
		else
			newOrderPayment.setBalance(totalAfterTax.negate());
		salesOrder.getOrderPayments().add(newOrderPayment);
	}
	
	private OrderPayment populateOrderPayment(Integer salesOrderId, Short transactionType, BigDecimal paymentAmount){
		OrderPayment newOrderPayment = new OrderPayment();
		newOrderPayment.setSalesOrderId(salesOrderId);
		newOrderPayment.setTransactionType(transactionType);
		newOrderPayment.setPaymentAmount(paymentAmount);
		newOrderPayment.setAddedBy(OrderConstants.SYS_ADDED_BY);
		newOrderPayment.setCreateTime(new Date());
		
		return newOrderPayment;
	}
	
	public void payExchangeOrder(OrderReturn orderReturn, SalesOrder exchangeOrder){
		BigDecimal returnTotalAmount = orderReturn.getReturnTotalAmount();
		if(returnTotalAmount.compareTo(BigDecimal.ZERO)==1){
			OrderPayment newOrderPayment = populateOrderPayment(exchangeOrder.getSalesOrderId(), OrderConstants.TRANSACTION_TYPE_RETURN, returnTotalAmount);
			newOrderPayment.setBalance(returnTotalAmount);
			exchangeOrder.getOrderPayments().add(newOrderPayment);
			exchangeOrder.setPaidAmount(returnTotalAmount);
			exchangeOrder.setPaymentStatus((returnTotalAmount.compareTo(exchangeOrder.getTotalAmount())==-1)?OrderConstants.PAYMENT_STATUS_PARTIALLY_PAID:OrderConstants.PAYMENT_STATUS_PAID);
		}
	}
	
	/**
	 * 取消发货项
	 * @param orderShipmentId
	 * @param curUser 当前用户
	 * @return 1 成功
	 * 		   0 参数异常
	 * 		  -1 业务异常，未加锁或订单已被他人加锁或发货项已被创建备货单
	 */
	public int doCancelShipment(Serializable orderShipmentId, AppUser curUser){
		if(orderShipmentId==null || curUser==null)
			return 0;
		
		OrderShipment orderShipment = salesOrderManager.getOrderShipmentById(orderShipmentId);
		SalesOrder salesOrder = orderShipment.getSalesOrder();
		
		if(salesOrder.getIsLocked().byteValue()!=Constants.FLAG_TRUE.byteValue()  
				|| curUser.getAppuserId().intValue()!=salesOrder.getLockedBy().intValue())
			return -1;
		//如果发货项当前状态已经是待备货
		if(orderShipment.getStatus().shortValue()>OrderConstants.SHIPMENT_STATUS_PICKING_AVAILABLE.shortValue())
			return -1;
		//审计
		orderAuditHelper.logCancelShipment(salesOrder, orderShipment, curUser);
		
		orderShipment.setStatus(OrderConstants.STATUS_CANCELLED);
		salesOrderManager.save(orderShipment);
		
		Set<OrderSku> orderSkus = orderShipment.getOrderSkus();
		//取消已分配的库存&删除待采购单
		for(OrderSku orderSku:orderSkus){
			//删除待采购单
			purchaseOrderItemManager.deleteByOrderSkuId(orderSku.getOrderSkuId());
			
			if(orderSku.getItemType().shortValue()!=Constants.ITEM_TYPE_PRODUCT.shortValue())
				continue;
			//无库存销售的不需处理
			if(orderSku.getProductSku().getProduct().getAvailabilityRule().intValue()==CatalogConstants.PRODUCT_AVAILABILITY_NOT_IN_STOCK_SELL.intValue()){
				continue;
			}
			if(orderSku.getAllocatedQuantity()>0){
				inventoryService.doCancelAllocate(salesOrder, orderSku.getProductSku(), orderSku.getAllocatedQuantity());
			}
			//[库存分配触发调整]
			//释放预订库存
			int preOrderQty = orderSku.getAllocatedPreQty();
			if(preOrderQty>0){
				inventoryService.doCancelAllocatePreOrBackOrderedQty(orderSku.getProductSku(), preOrderQty);
				orderSku.setAllocatedPreQty(0);
			}
			orderSku.setAllocatedQuantity(0);
			salesOrderManager.save(orderSku);
		}
		
		salesOrder.updateOrderStatusByShipments();
		//更新总金额，支付状态
		salesOrder.updateTotalAmount();
		salesOrder.updatePaymentStatus();
		
		//更新等待支付的发货项
		if(!salesOrder.isCod()){
			Set<OrderShipment> oss = salesOrder.getOrderShipments();
			for(OrderShipment os : oss){
				if(os.getStatus().shortValue()==OrderConstants.SHIPMENT_STATUS_AWAITING_PAYMENT.shortValue())
					confirmShipment(salesOrder, os);
			}
		}
		//unlock and save
		salesOrderManager.unlock(salesOrder);
		
		return 1;
	}
	
	/**
	 * 创建备货单
	 * @param orderShipmentIdVersions 发货项ID_版本号
	 * @param curUser
	 * @return 新创建的备货单
	 */
	public OrderPickList createPickList(String [] orderShipmentIdVersions, AppUser curUser){
		OrderPickList orderPickList = new OrderPickList();
		orderPickList.setCreateBy(curUser.getAppuserId());
		orderPickList.setCreateTime(new Date());
		orderPickList.setIsActive(Constants.FLAG_TRUE);
		
		orderPickListManager.save(orderPickList);
		
		for(String idVersion: orderShipmentIdVersions){
			String[] temp = idVersion.split("_");
			String id = temp[0];
			//乐观锁
			String vesion = temp[1];
			
			OrderShipment orderShipment = salesOrderManager.getOrderShipmentById(id);
			//订单被暂停或已取消
			if(!orderShipment.getCreatePickListPermission()){
				orderPickListManager.delete(orderPickList);
				return null;
			}
			
			orderShipment.setStatus(OrderConstants.SHIPMENT_STATUS_PICKING);
			orderShipment.setOrderPickList(orderPickList);
			//orderShipment.setVersion(new Integer(vesion)+1);
			salesOrderManager.save(orderShipment);
			
			//审计谁为发货项备货
			SalesOrder salesOrder = orderShipment.getSalesOrder();
			orderAuditHelper.logCreatePickList(salesOrder, orderShipment, curUser);
			
			//系统消息提示
			if(salesOrder.getCustomerId()!=null)
				systemMessageManager.addSystemMessage(this.getMessage("salesOrder.message.picking", orderShipment.getShipmentNo()), SystemMessage.MSG_TYPE_ORDER, (AppUser)salesOrder.getCustomer());
		}
		
		return orderPickList;
	}
	
	/**
	 * 完成发货，记录发货项状态，更新订单状态，更新所在备货单激活状态，扣款，审计
	 * @param shipmentNo
	 * @param trackingNo
	 * @param curUser 当前用户
	 */
	public void doCompletShipping(Serializable shipmentNo, String carrierName, String trackingNo, AppUser curUser, BigDecimal shippingCostPaid){
		OrderShipment orderShipment = salesOrderManager.getOrderShipmentByShipmentNo(shipmentNo);
		if(orderShipment==null 
				||orderShipment.getStatus().shortValue()!=OrderConstants.SHIPMENT_STATUS_PICKING.shortValue()) 
			return;
		
		SalesOrder salesOrder = orderShipment.getSalesOrder();
		//审计
		orderAuditHelper.logCompleteShipment(salesOrder, orderShipment, curUser);
		
		orderShipment.setStatus(OrderConstants.SHIPMENT_STATUS_SHIPPED);
		orderShipment.setTrackingNo(trackingNo);
		orderShipment.setCarrierName(carrierName);
		orderShipment.setShippingCostPaid(shippingCostPaid);
		orderShipment.setDeliverTime(new Date());
		salesOrderManager.save(orderShipment);
		
		//通知索引更新
		IndexNotifyEvent event = new IndexNotifyEvent(SearchConstants.CORE_NAME_SALESORDER, SearchConstants.UPDATE_TYPE.UPDATE); 
		event.setIds(Arrays.asList(orderShipment.getId()));
		ContextUtil.getInstance().fireApplicationEvent(event);		
		
		Set<OrderSku> orderSkus = orderShipment.getOrderSkus();
		
		List<Integer> pkeys = new ArrayList<Integer>();
		for(OrderSku orderSku:orderSkus){
			if(orderSku.getItemType().shortValue()==Constants.ITEM_TYPE_PRODUCT.shortValue()){
				//无库存销售的不需处理
				if(orderSku.getProductSku().getProduct().getAvailabilityRule().intValue()!=CatalogConstants.PRODUCT_AVAILABILITY_NOT_IN_STOCK_SELL.intValue()){
					//库存出库
					inventoryService.doReleaseStock(salesOrder, orderSku.getProductSku(), orderSku.getAllocatedQuantity());
					productService.doAddProductBuyCount(salesOrder.getStoreId(),orderSku.getProductId(), orderSku.getAllocatedQuantity());
				}
				pkeys.add(orderSku.getProductId());
				
			}else if(orderSku.getItemType().shortValue()==Constants.ITEM_TYPE_GC.shortValue())
				//如果有礼券则发送
			    giftCertificateService.doSendGiftCertificate(orderSku.getGiftCertificate(), salesOrder.getOrderNo());
		}
		
		recommendedService.saveAlsoBuy(pkeys);
		
		//支付、生成结算记录
		if(salesOrder.isCod())
			this.completePayment4Cod(orderShipment, curUser);
		
		//扣款
		payOrderShipment(salesOrder, orderShipment);
		//设置订单状态
		salesOrder.updateOrderStatusByShipments();
		salesOrderManager.save(salesOrder);
		
		OrderPickList orderPickList = orderShipment.getOrderPickList();
		orderPickList.getOrderPickListId();
		orderPickListManager.doSetActive(orderPickList.getOrderPickListId());
		
		//发送积分
		sendShopPoint(salesOrder);
		
		//发送优惠券
		sendCoupon(salesOrder);
		
		//已发货通知
		salesOrderManager.sendShipmentNotificationEmail(salesOrder,orderShipment);	
		
		//系统消息提示
		if(salesOrder.getCustomerId()!=null)
			systemMessageManager.addSystemMessage(this.getMessage("salesOrder.message.shipping", orderShipment.getShipmentNo()), SystemMessage.MSG_TYPE_ORDER, (AppUser)salesOrder.getCustomer());
	
	}
	
	private void sendShopPoint(SalesOrder salesOrder){
		//当为匿名下单的插件赠送积分数据
		if(salesOrder.getCustomerId()!=null &&salesOrder.getCustomerId()!=Constants.USERID_ANONYMOUS.intValue()&& salesOrder.getGainedPoint()!=null 
				&&salesOrder.getGainedPoint().intValue()>0)
			shopPointHistoryManager.saveNewShopPointHistory(CustomerConstants.ShopPoint_Type_Shopping, salesOrder.getCustomer(), salesOrder.getGainedPoint());
	}
	
	private void sendCoupon(SalesOrder salesOrder){
		if(salesOrder.getGainedCouponTypeId()!=null){
			EmailModel emailModel = new EmailModel();
			emailModel.setEmail(salesOrder.getCustomerEmail());
			emailModel.setFirstName(salesOrder.getCustomerFirstname());
			emailModel.setLastName(salesOrder.getCustomerLastname());
			PromoService promoService = (PromoService)ContextUtil.getSpringContext().getBean("promoService");
			promoService.doSendCoupon(salesOrder.getGainedCouponTypeId(), emailModel);
		}
	}
	
	/**
	 * 取消订单
	 * @param salesOrderId
	 * @param curUser 当前用户
	 * @return 1 成功
	 * 		   0 参数异常
	 * 		  -1 业务异常,未加锁或订单已被他人加锁或订单已部分发货或完成
	 */
	public int doCancelOrder(Serializable salesOrderId, AppUser curUser){
		if(salesOrderId==null || curUser==null)
			return 0;
		
		SalesOrder salesOrder = salesOrderManager.getForViewById(salesOrderId);
		
		return this.doCancelOrder(salesOrder, curUser);
	}
	
	/**
	 * 取消订单
	 * @param salesOrder
	 * @param curUser
	 * @return
	 */
	public int doCancelOrder(SalesOrder salesOrder, AppUser curUser){
		
		if(!salesOrder.getCancelOrderPermission())
			return -1;
		
		//审计
		orderAuditHelper.logCancelOrder(salesOrder, curUser);
		
		salesOrder.setOrderStatus(OrderConstants.STATUS_CANCELLED);
		Set<OrderShipment> oss = salesOrder.getOrderShipments();
		
		for(OrderShipment orderShipment: oss){
			orderShipment.setStatus(OrderConstants.STATUS_CANCELLED);
			//取消备货单
			orderPickListManager.removeFromPickList(orderShipment.getOrderShipmentId());
			Set<OrderSku> orderSkus = orderShipment.getOrderSkus();
			//取消已分配的库存&删除待采购单
			for(OrderSku orderSku:orderSkus){
				//删除待采购单
				purchaseOrderItemManager.deleteByOrderSkuId(orderSku.getOrderSkuId());
				
				if(orderSku.getItemType().shortValue()==Constants.ITEM_TYPE_PRODUCT.shortValue()){
					//无库存销售的不需处理
					if(orderSku.getProductSku().getProduct().getAvailabilityRule().intValue()==CatalogConstants.PRODUCT_AVAILABILITY_NOT_IN_STOCK_SELL.intValue()){
						continue;
					}
					if(orderSku.getAllocatedQuantity()>0){
						inventoryService.doCancelAllocate(salesOrder, orderSku.getProductSku(), orderSku.getAllocatedQuantity());
					}
					//[库存分配触发调整]
					//释放预订库存
					int preOrderQty = orderSku.getAllocatedPreQty();
					if(preOrderQty>0){
						inventoryService.doCancelAllocatePreOrBackOrderedQty(orderSku.getProductSku(), preOrderQty);
						orderSku.setAllocatedPreQty(0);
					}
					orderSku.setAllocatedQuantity(0);
					salesOrderManager.save(orderSku);
				}
			}
		}
		
		//如果前台客户申请取消订单
		if(salesOrder.getIsOnHold().shortValue()==Constants.FLAG_TRUE.shortValue()
				&& salesOrder.getIsHoldByCustomer().shortValue()==Constants.FLAG_TRUE.shortValue()){
			salesOrder.setIsOnHold(Constants.FLAG_FALSE);
			salesOrder.setIsHoldByCustomer(null);
			if(salesOrder.getCustomerId()!=null)
				systemMessageManager.addSystemMessage(this.getMessage("salesOrder.approvalCancelling", salesOrder.getOrderNo()), SystemMessage.MSG_TYPE_ORDER, (AppUser)salesOrder.getCustomer());
		}
		
		//unlock and save
		salesOrderManager.unlock(salesOrder);
		
		return 1;
	}
	
	/**
	 * 暂停订单处理
	 * @param salesOrderId
	 * @param curUser 当前用户
	 * @return 1 成功
	 * 		   0 参数异常
	 * 		  -1 业务异常,订单已被其他人加锁或订单已完成
	 */
	public int doHoldOrder(Serializable salesOrderId, AppUser curUser){
		if(salesOrderId==null || curUser==null)
			return 0;
		
		SalesOrder salesOrder = salesOrderManager.getById(new Integer(salesOrderId.toString()));
		if((salesOrder.getIsLocked().byteValue()==Constants.FLAG_TRUE.byteValue() && salesOrder.getLockedBy().intValue()!=curUser.getAppuserId().intValue())
				|| salesOrder.getOrderStatus().shortValue()==OrderConstants.ORDER_STATUS_COMPLETE.shortValue())
			return -1;		
		//审计
		orderAuditHelper.logHoldOrder(salesOrder, curUser);		
		salesOrder.setIsOnHold(Constants.FLAG_TRUE);		
		//unlock and save
		salesOrderManager.unlock(salesOrder);
		
		return 1;
	}
	
	/**
	 * 恢复订单处理
	 * @param salesOrderId
	 * @param curUser 当前用户
	 * @return 1 成功
	 * 		   0 参数异常
	 * 		  -1 业务异常,订单不处在HOLD状态或订单已完成
	 */
	public int doResumeOrder(Serializable salesOrderId, AppUser curUser){
		if(salesOrderId==null || curUser==null)
			return 0;
		
		SalesOrder salesOrder = salesOrderManager.getById(new Integer(salesOrderId.toString()));
		
		if(salesOrder.getIsOnHold().byteValue()!=Constants.FLAG_TRUE.byteValue()  
				|| salesOrder.getOrderStatus().shortValue()==OrderConstants.ORDER_STATUS_COMPLETE.shortValue())
			return -1;
		
		//审计
		orderAuditHelper.logResumeOrder(salesOrder, curUser);
		salesOrder.setIsOnHold(Constants.FLAG_FALSE);
		
		//如果前台客户申请取消订单
		if(salesOrder.getIsHoldByCustomer().shortValue()==Constants.FLAG_TRUE.shortValue()){
			salesOrder.setIsHoldByCustomer(null);
			if(salesOrder.getCustomerId()!=null)
				systemMessageManager.addSystemMessage(this.getMessage("salesOrder.refuseCancelling", salesOrder.getOrderNo()), SystemMessage.MSG_TYPE_ORDER, (AppUser)salesOrder.getCustomer());
		}
		//unlock and save
		salesOrderManager.unlock(salesOrder);
		
		return 1;
	}
	
	/**
	 * 手工支付
	 * @param orderPayment
	 * @return	 1 - 成功
	 *  		 0 - 参数异常
	 * 		    -1 - 业务异常，支付金额大于订单未付金额。
	 * 			-2 - 业务异常，换货订单须等待原货退回后才可以支付！
	 */
	public int createPaymentStoreManager(OrderPayment orderPayment){
		SalesOrder salesOrder = salesOrderManager.getSalesOrderByOrderNo(orderPayment.getOrderNo());
		int rtn = createPayment(orderPayment, salesOrder);
		if(salesOrder==null){
			return rtn;
		}
		orderAuditHelper.logHandworkPayment(salesOrder, orderPayment, rtn);
		//check the balance, if current order is paid then set it to ship.
		if (OrderConstants.PAYMENT_STATUS_PAID.equals(salesOrder.getPaymentStatus()) && !salesOrder.isCod())
		{
			/*自动审核
			Set<OrderShipment> oss = salesOrder.getOrderShipments();
			for(OrderShipment orderShipment :oss)
			{
				int result = doConfirmShipmentByRobot(orderShipment);
				if (logger.isDebugEnabled())
				{
					logger.debug("doConfirmShipmentByRobot:"+ result);
				}				
			}			*/
		}
		return rtn;
	}
	
	/**
	 * 
	 * @param orderPayment
	 * @return
	 */
	public int createPaymentStoreFront(OrderPayment orderPayment){
		SalesOrder salesOrder = salesOrderManager.getSalesOrderByOrderNo(orderPayment.getOrderNo());
		int rtn = createPayment(orderPayment, salesOrder);
		orderAuditHelper.logCustomerPayment(salesOrder, orderPayment,rtn);
		return rtn;
	}
	
	private int createPayment(OrderPayment orderPayment, SalesOrder salesOrder){
		if(orderPayment==null||salesOrder==null
				||orderPayment.getTransactionType()==null
				||orderPayment.getPaymentAmount()==null
				||orderPayment.getAddedBy()==null
				||orderPayment.getOrderNo()==null)
			return 0;
		
		//SalesOrder salesOrder = salesOrderManager.getSalesOrderByOrderNo(orderPayment.getOrderNo());
		//if(salesOrder==null) return 0;
		
		if(salesOrder.getOrderStatus().shortValue()==OrderConstants.ORDER_STATUS_AWAITING_RETURN.shortValue()){
			return -2;
		}
		
		BigDecimal totalAmount = salesOrder.getTotalAmount();
		BigDecimal paidAmount = salesOrder.getPaidAmount();
		BigDecimal paymentAmount = orderPayment.getPaymentAmount();
		BigDecimal curPaidAmount = paymentAmount.add(paidAmount);
		//后台手工支付时，支付限额
		if(curPaidAmount.compareTo(totalAmount)==1 )
			return -1;
		
		OrderPayment latestOrderPayment = salesOrderManager.getLatestOrderPayment(salesOrder.getSalesOrderId());
		BigDecimal curBalance = paymentAmount;
		if (latestOrderPayment!=null)
			curBalance = curBalance.add(latestOrderPayment.getBalance());
			
		orderPayment.setBalance(curBalance);		
		orderPayment.setCreateTime(new Date());
		orderPayment.setSalesOrderId(salesOrder.getSalesOrderId());
		salesOrderManager.save(orderPayment);		
		salesOrder.setPaidAmount(curPaidAmount);
		
		//如果订单是款到发货订单，则检查有无等待支付的发货项
		if(!salesOrder.isCod()){
			//实际余额，除去已占用金额的发货项
			BigDecimal actualBalance = salesOrder.getActualBalance(curBalance);			
			Set<OrderShipment> orderShipments = salesOrder.getOrderShipments();
			for(OrderShipment orderShipment: orderShipments){
				BigDecimal totalAfterTax = orderShipment.getTotalAfterTax();
				if(orderShipment.getStatus().shortValue()==OrderConstants.SHIPMENT_STATUS_AWAITING_PAYMENT.shortValue()
						&& actualBalance.compareTo(totalAfterTax)>=0){
					orderShipment.setStatus(OrderConstants.SHIPMENT_STATUS_PICKING_AVAILABLE);
					actualBalance = actualBalance.add(totalAfterTax.negate());
				}
				//处理数字产品
				handleDigitalProduct(salesOrder, orderShipment);
			}			
		}
		if(curPaidAmount.compareTo(BigDecimal.ZERO)==1)
		{
			salesOrder.setPaymentStatus((curPaidAmount.compareTo(salesOrder.getTotalAmount())==-1) ? OrderConstants.PAYMENT_STATUS_PARTIALLY_PAID : OrderConstants.PAYMENT_STATUS_PAID);
			//订单金额已全部支付时，添加一个队列，根据IP查找地址及经纬度
			if(salesOrder.getPaymentStatus().intValue()==OrderConstants.PAYMENT_STATUS_PAID.intValue()){
				
				try {
					//订单已全额支付的需分配库存[库存分配触发调整]
					salesOrderManager.doAllocateQty4Order(salesOrder);
					
					//订单已全额支付的为用户发送优惠劵
					if(salesOrder.getGainedCouponTypeId() != null){
						customerManager.addCoupon(salesOrder.getGainedCouponTypeId(), salesOrder.getCustomerId());
					}
				} catch (OutOfStockException e) {
					e.printStackTrace();
					//TODO分配库存时可能为出错。后台请求分配库存的已不会抛出缺货异常
					logger.warn("支付成功后获取库存分配出错！"+e.getMessage()+salesOrder);
				}
				
				GeoIpCollectorQueue geoIpCollectorQueue=new GeoIpCollectorQueue();
				geoIpCollectorQueue.setOrderNO(salesOrder.getOrderNo());
				geoIpCollectorQueue.setCustomerIp(salesOrder.getIpAddress());
				geoIpCollectorQueue.setActionType(SalesOrderGeoip.PLACE_ORDER);
				SystemQueue queue = new SystemQueue();
				queue.setTitle(salesOrder.getIpAddress());
				queue.setQueueType(SystemQueue.TYPE_GEOIP);
				queue.setTargetEntiy(geoIpCollectorQueue);
				queue.setNextRetryTime(new Date());
				systemQueueManager.save(queue);
			}
		}
		//unlock and save
		salesOrderManager.unlock(salesOrder);
		return 1;
	}
	
	
	/**
	 * 记录货到付款发货项已支付，支付方式为货到付款
	 * @param orderShipment
	 * @param ip 可以为null
	 * @param appUser
	 */
	private void completePayment4Cod(OrderShipment orderShipment, AppUser curUser){
		
		SalesOrder salesOrder = orderShipment.getSalesOrder();
		if(!salesOrder.isCod() || salesOrder.getPaymentStatus().shortValue()==OrderConstants.PAYMENT_STATUS_PAID.shortValue()) return;
		
		OrderPayment orderPayment = new OrderPayment();
		orderPayment.setOrderNo(salesOrder.getOrderNo());
		orderPayment.setTransactionType(OrderConstants.TRANSACTION_TYPE_COD);
		
		BigDecimal shipmentTotalAfterTax = orderShipment.getTotalAfterTax();
		OrderPayment latestOrderPayment = salesOrderManager.getLatestOrderPayment(salesOrder.getSalesOrderId());
		BigDecimal balance = latestOrderPayment==null?BigDecimal.ZERO:latestOrderPayment.getBalance();
		if(balance.compareTo(shipmentTotalAfterTax)>=0)
			return;
		else
			//支付当前发货项的未付款
			orderPayment.setPaymentAmount(shipmentTotalAfterTax.add(balance.negate()));
		
		orderPayment.setAddedBy(OrderConstants.SYS_ADDED_BY);
		
		this.createPaymentStoreManager(orderPayment);
		
		//有实体产品则生成结算记录
		if(orderShipment.hasPhysicalSku())
			orderSettlementManager.createOrderSettlement(salesOrder, orderShipment, orderPayment.getPaymentAmount(), curUser);
	}
	
	private String getMessage(String key, Object... params){
		return ContextUtil.getSpringContext().getMessage(key, params, null);
	}
	
	public void afterPropertiesSet() throws Exception {
		
	}

	public void setSystemQueueManager(SystemQueueManager systemQueueManager) {
		this.systemQueueManager = systemQueueManager;
	}

	public CustomerManager getCustomerManager() {
		return customerManager;
	}

	public void setCustomerManager(CustomerManager customerManager) {
		this.customerManager = customerManager;
	}

}
