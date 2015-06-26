/**
 * 
 */
package com.cartmatic.estore.common.service.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.catalog.CatalogConstants;
import com.cartmatic.estore.common.helper.ConfigUtil;
import com.cartmatic.estore.common.model.order.OrderAddress;
import com.cartmatic.estore.common.model.order.OrderAudit;
import com.cartmatic.estore.common.model.order.OrderPayment;
import com.cartmatic.estore.common.model.order.OrderPromotion;
import com.cartmatic.estore.common.model.order.OrderShipment;
import com.cartmatic.estore.common.model.order.OrderSku;
import com.cartmatic.estore.common.model.order.SalesOrder;
import com.cartmatic.estore.common.model.sales.GiftCertificate;
import com.cartmatic.estore.common.model.system.PaymentHistory;
import com.cartmatic.estore.common.service.InventoryService;
import com.cartmatic.estore.common.service.OrderService;
import com.cartmatic.estore.common.service.ProductService;
import com.cartmatic.estore.common.service.SupplierService;
import com.cartmatic.estore.common.util.DateUtil;
import com.cartmatic.estore.core.util.StringUtil;
import com.cartmatic.estore.core.view.MailEngine;
import com.cartmatic.estore.exception.OutOfStockException;
import com.cartmatic.estore.order.OrderConstants;
import com.cartmatic.estore.order.service.OrderAuditHelper;
import com.cartmatic.estore.order.service.OrderNoGenerator;
import com.cartmatic.estore.order.service.OrderProcessFlowManager;
import com.cartmatic.estore.order.service.SalesOrderManager;
import com.cartmatic.estore.sales.service.GiftCertificateManager;
import com.cartmatic.estore.webapp.util.RequestContext;

/**
 * @author pengzhirong
 */
public class OrderServiceImpl implements OrderService, InitializingBean {

	protected final transient Log	logger	= LogFactory.getLog(getClass());	
	private SalesOrderManager salesOrderManager = null;
	private InventoryService inventoryService = null;
	private ProductService productService = null;	
	private OrderNoGenerator orderNoGenerator = null;	
	private OrderProcessFlowManager orderProcessFlowManager = null;
	private MailEngine mailEngine;
	private GiftCertificateManager	giftCertificateManager;
	private OrderAuditHelper orderAuditHelper=null;
	private SupplierService supplierService=null;
	
	public void setSupplierService(SupplierService supplierService) {
		this.supplierService = supplierService;
	}

	public void setMailEngine(MailEngine avalue)
	{
		mailEngine = avalue;
	}
	
	public void setOrderProcessFlowManager(
			OrderProcessFlowManager orderProcessFlowManager) {
		this.orderProcessFlowManager = orderProcessFlowManager;
	}

	public void setOrderNoGenerator(OrderNoGenerator orderNoGenerator) {
		this.orderNoGenerator = orderNoGenerator;
	}
	
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	public void setInventoryService(InventoryService inventoryService) {
		this.inventoryService = inventoryService;
	}

	public void setSalesOrderManager(SalesOrderManager salesOrderManager) {
		this.salesOrderManager = salesOrderManager;
	}
	
	public void setGiftCertificateManager(GiftCertificateManager avalue)
	{
		giftCertificateManager = avalue;
	}
	
	public boolean isCustomerOrderNumZero(Serializable userId) {
		long num = salesOrderManager.countCustomerOrder(userId);
		return num==0?true:false;
	}
	
	/**
	 * 下订单
	 * @param billingAddress 帐单发票地址 可为null（全为礼券的订单）
	 * @param shippingAddress 运输地址 可为null（全为礼券的订单）
	 * @param salesOrder 订单基本信息
	 * @param orderShipment 发货项
	 * @param orderSkus 订单商品项集合
	 * @param orderPromotions 订单商品项应用的促销集合  可为null
	 * @param orderPayments 礼券或积分支付  可为null
	 * @param note 备注 可为null
	 * @throws OutOfStockException 
	 */
	public void doPlaceOrder(OrderAddress billingAddress, OrderAddress shippingAddress, 
			SalesOrder salesOrder, OrderShipment orderShipment, 
			Set<OrderSku> orderSkus, Set<OrderPromotion> orderPromotions, Set<OrderPayment> orderPayments, String note) throws OutOfStockException{
		Assert.notNull(salesOrder);
		Assert.notNull(orderShipment);
		Assert.notNull(orderSkus);
		
		boolean isExchangeOrder = salesOrder.getIsExchangeOrder().shortValue()==Constants.FLAG_TRUE.shortValue();
		
		//支付此处只能是礼券和积分支付
		if(orderPayments!=null && orderPayments.size()>0 && !isExchangeOrder)
			for(OrderPayment orderPayment: orderPayments){
				short transactionType = orderPayment.getTransactionType().shortValue();
				if(transactionType!=OrderConstants.TRANSACTION_TYPE_GIFT_CERT.shortValue() 
						&& transactionType!=OrderConstants.TRANSACTION_TYPE_SHOP_POINT.shortValue())
					return;
			}
		
		if(billingAddress!=null)
			salesOrderManager.save(billingAddress);
		if(shippingAddress!=null)
			salesOrderManager.save(shippingAddress);
		
//		if (!orderNoGenerator.isInit()) {
//			String maxOrderNo = salesOrderManager.getMaxOrderNo();
//			orderNoGenerator.init(maxOrderNo);
//		}
		salesOrder.setOrderNo(orderNoGenerator.generateOrderNo());
		
		salesOrder.setOrderAddress(billingAddress);
		if(salesOrder.getOrderStatus()==null)
			salesOrder.setOrderStatus(OrderConstants.ORDER_STATUS_IN_PROGRESS);
		salesOrder.setPaymentStatus(OrderConstants.PAYMENT_STATUS_UNPAID);
		salesOrderManager.save(salesOrder);
		
		orderShipment.setSalesOrderId(salesOrder.getSalesOrderId());
		//第一个发货项编号
		orderShipment.setShipmentNo(salesOrder.getOrderNo()+"-01");
		orderShipment.setStatus(OrderConstants.SHIPMENT_STATUS_AWAITING_INVENTORY);
		orderShipment.setOrderAddress(shippingAddress);
		if(orderShipment.getShippingCost()==null)
			orderShipment.setShippingCost(new BigDecimal(0));
		if(orderShipment.getWrapUnitPrice()==null)
			orderShipment.setWrapUnitPrice(new BigDecimal(0));
		
		salesOrderManager.save(orderShipment);
		
		//如果是礼券+实体商品的混合形式则拆单，拆成两个发货项
		OrderAddress billingAddress1 = null;
		OrderShipment eShipment = null;
		boolean hasAllItemType = false;
		if(!isExchangeOrder && orderSkus.size()>1){
			boolean hasDigitalType = false;
			boolean hasPhysicalType = false;
			for(OrderSku orderSku: orderSkus){
				if(orderSku.getItemType().shortValue()==Constants.ITEM_TYPE_GC.shortValue()){
					hasDigitalType = true;
				}else if(orderSku.getItemType().shortValue()==Constants.ITEM_TYPE_PRODUCT.shortValue()){
					short skuKind = orderSku.getProductSku().getSkuKind().shortValue();
					if(skuKind==CatalogConstants.SKU_KIND_ENTITY.shortValue())
						hasPhysicalType = true;
					else
						hasDigitalType = true;
				}
			}
			if(hasPhysicalType && hasDigitalType){
				hasAllItemType = true;
				billingAddress1 = billingAddress.clone();
				eShipment = orderShipment.clone();
				eShipment.setShippingCost(BigDecimal.ZERO.setScale(2));
				eShipment.setShipmentNo(salesOrder.getOrderNo()+"-02");
				eShipment.setStatus(OrderConstants.SHIPMENT_STATUS_INVENTORY_ASSIGNED);
				eShipment.setSalesOrder(salesOrder);
				salesOrderManager.save(eShipment);
			}
		}
		
		BigDecimal itemTax1 = BigDecimal.ZERO;
		BigDecimal itemTax2 = BigDecimal.ZERO;
		BigDecimal itemSubTotal1 = BigDecimal.ZERO;
		BigDecimal itemSubTotal2 = BigDecimal.ZERO;
		for(OrderSku orderSku: orderSkus){
			//分配库存（移动后面，当确定订单已全额支付了，才分配库存）[库存分配触发调整]
			/*if(orderSku.getItemType().shortValue()==Constants.ITEM_TYPE_PRODUCT.shortValue()){
				Integer[] allcatedQty = inventoryService.doAllocateProductSku(salesOrder, productService.getProductSkuByProductSkuCode(orderSku.getProductSkuCode()), orderSku.getQuantity());
				orderSku.setAllocatedQuantity(allcatedQty[0]);
			}*/
			
			if((orderSku.getItemType().shortValue()==Constants.ITEM_TYPE_PRODUCT.shortValue() && orderSku.getProductSku().getSkuKind().shortValue()==CatalogConstants.SKU_KIND_ENTITY.shortValue())
					|| !hasAllItemType){
				if(orderSku.getTax()!=null)
					itemTax1 = itemTax1.add(orderSku.getTax());
				itemSubTotal1 = itemSubTotal1.add(orderSku.getSubTotalAmount());
				
				orderSku.setOrderShipment(orderShipment);
				orderShipment.getOrderSkus().add(orderSku);
			}else{
				if(orderSku.getTax()!=null)
					itemTax2 = itemTax2.add(orderSku.getTax());
				itemSubTotal2 = itemSubTotal2.add(orderSku.getSubTotalAmount());
				
				orderSku.setOrderShipment(eShipment);
				eShipment.getOrderSkus().add(orderSku);
			}
			
			
		}
		orderShipment.setItemTax(itemTax1.setScale(2, BigDecimal.ROUND_HALF_UP));
		orderShipment.setItemSubTotal(itemSubTotal1.setScale(2, BigDecimal.ROUND_HALF_UP));
		//更新发货项状态
		
		orderShipment.updateStatusForReallocated();
		salesOrderManager.save(orderShipment);
		salesOrder.getOrderShipments().add(orderShipment);
		
		if(hasAllItemType){
			eShipment.setItemTax(itemTax2.setScale(2, BigDecimal.ROUND_HALF_UP));
			eShipment.setItemSubTotal(itemSubTotal2.setScale(2, BigDecimal.ROUND_HALF_UP));
			eShipment.setShippingCost(BigDecimal.ZERO.setScale(2));
			//更新发货项状态
			orderShipment.updateStatusForReallocated();
			salesOrderManager.save(eShipment);
			salesOrder.getOrderShipments().add(eShipment);
		}
		
		if(salesOrder.getIsCod().intValue() == new Short("1")){
			orderShipment.setStatus(OrderConstants.SHIPMENT_STATUS_INVENTORY_ASSIGNED);
			salesOrderManager.save(orderShipment);
		}
		
		if(orderPromotions!=null){
			for(OrderPromotion orderPromotion:orderPromotions){
				orderPromotion.setSalesOrder(salesOrder);
				salesOrderManager.save(orderPromotion);
			}
		}
		
		//支付
		if(orderPayments!=null && orderPayments.size()>0){
			for(OrderPayment orderPayment: orderPayments){
				orderPayment.setSalesOrder(salesOrder);
				//不需要调用createPayment方法.
				//orderProcessFlowManager.createPaymentStoreFront(orderPayment);
				salesOrder.getOrderPayments().add(orderPayment);
			}
			//更新支付状态
			salesOrder.updatePaymentStatus();
			salesOrderManager.save(salesOrder);
		}
		
		//下单时如果已全额支付完的马上分配库存。（礼券支付）[库存分配触发调整]
		if(salesOrder.getPaymentStatus().intValue()==OrderConstants.PAYMENT_STATUS_PAID || salesOrder.getIsCod().intValue() == new Short("1")){
			for(OrderSku orderSku: orderSkus){
				//分配库存
				if(orderSku.getItemType().shortValue()==Constants.ITEM_TYPE_PRODUCT.shortValue()){
					//无库存销售不需分配库存
					if(orderSku.getProductSku().getProduct().getAvailabilityRule().intValue()==CatalogConstants.PRODUCT_AVAILABILITY_NOT_IN_STOCK_SELL.intValue()){
//						continue;
						supplierService.createPurchaseOrderItem(orderSku);
					}else{
						Integer[] allcatedQty = inventoryService.doAllocateProductSku(salesOrder, productService.getProductSkuByProductSkuCode(orderSku.getProductSkuCode()), orderSku.getQuantity());
						orderSku.setAllocatedQuantity(allcatedQty[0]);
						orderSku.setAllocatedPreQty(allcatedQty[1]);
					}
				}
			}
		}
		
		
		//备注
		if(!isExchangeOrder)
			this.saveNote(salesOrder, note);
		
		salesOrderManager.sendNotificationEmail(OrderConstants.MAIL_TYPE_PLACE_ORDER, salesOrder);
		
	}
	
	private void saveNote(SalesOrder salesOrder, String note){
		if(note!=null && !"".equals(note.trim())){
			OrderAudit orderAudit = new OrderAudit();
			orderAudit.setTransactionType(OrderConstants.AUDIT_TANSACTION_TYPE_CUSTOMER);
			String name=salesOrder.getCustomerFirstname()+" "+salesOrder.getCustomerLastname();
			if(name.length()>64){
				name=name.substring(0,63);
			}
			orderAudit.setAddedBy(name);
			orderAudit.setCreateTime(salesOrder.getCreateTime());
			if(note.length()>1024){
				note=note.substring(0,1023);
			}
			orderAudit.setDetail(note);
			orderAudit.setSalesOrderId(salesOrder.getSalesOrderId()	);
			salesOrderManager.save(orderAudit);
		}
	}
	
	/**
	 * 为预订订单分配库存，产品入库时调用
	 * @param productSkuId
	 * @return
	 */
	public void doAllocation4PreOrBackOrder(Integer productSkuId){
		Assert.notNull(productSkuId);
		
		List<OrderSku> orderSkus = salesOrderManager.getOrderSkuAwaitingInventoryByProductSkuId(productSkuId);
		for(OrderSku orderSku:orderSkus){
			SalesOrder salesOrder=orderSku.getOrderShipment().getSalesOrder();
			if(salesOrder.getPaymentStatus().intValue()==OrderConstants.PAYMENT_STATUS_PAID){
				//无库存销售的不需分配
				if(orderSku.getProductSku().getProduct().getAvailabilityRule().intValue()==CatalogConstants.PRODUCT_AVAILABILITY_NOT_IN_STOCK_SELL.intValue()){
					continue;
				}
				//[库存分配触发调整]
				int needQty = orderSku.getAllocatedPreQty();
				Integer allocatedQty = inventoryService.doReAllocateStockForPreSKU(orderSku.getOrderShipment().getSalesOrder(), orderSku.getProductSku(), needQty);
				orderSku.setAllocatedQuantity(orderSku.getAllocatedQuantity()+allocatedQty);
				orderSku.setAllocatedPreQty(needQty-allocatedQty);
				if(allocatedQty>0&&allocatedQty-needQty>=0){
					OrderShipment orderShipment = orderSku.getOrderShipment();
					orderShipment.updateStatusForReallocated();
					salesOrderManager.save(orderShipment);
				}
				salesOrderManager.save(orderSku);
			}
		}
	}
	
	
	/**
	 * 列出指定会员的所有订单
	 * @param userId 前台会员ID
	 * @return
	 */
	public List<SalesOrder> getSalesOrderByUserId(Serializable userId){
		return salesOrderManager.getSalesOrderByUserId(userId);
	}
	
	
	
	/**
	 * 通过会员ID与订单编号获取订单
	 * @param orderNo 订单编号
	 * @param userId 前台会员ID
	 * @return
	 */
	public SalesOrder getSalesOrder(Integer storeId,String orderNo, Serializable userId){
		return salesOrderManager.getSalesOrder(storeId,orderNo, userId);
	}
	
	/**
	 * 通过会员ID与订单ID获取订单
	 * @param salesOrderId 订单ID
	 * @param userId 前台会员ID
	 * @return
	 */
	public SalesOrder getSalesOrder(Integer storeId,Serializable salesOrderId, Serializable userId){
		return salesOrderManager.getSalesOrder(storeId,salesOrderId, userId);
	}
	
	/**
	 * 前台根据订单号和email 查询订单
	 * @return
	 */
	public SalesOrder getSalesOrder(String orderNo, String customerEmail)
	{
		return salesOrderManager.getSalesOrder(orderNo, customerEmail);
	}
	
	/**
	 * 前台根据订单号查询订单
	 * @return
	 */
	public SalesOrder getSalesOrderByOrderNo(String orderNo)
	{
		return salesOrderManager.getSalesOrderByOrderNo(orderNo);
	}
	
	/**
	 * 前台 申请取消订单
	 * @param salesOrderId 订单ID
	 * @param reasonType 取消原因类型
	 * @param note 前台会员申请取消时所填备注信息
	 * @param userId 前台会员ID
	 */
	public void doCancelOrder(Integer storeId,Serializable salesOrderId, Short reasonType, String note, Serializable userId){
		SalesOrder salesOrder = salesOrderManager.getSalesOrder(storeId,salesOrderId, userId);
		if(salesOrder==null || !salesOrder.getCancelOrderPermission4Customer()) return;
		
		salesOrder.setIsOnHold(Constants.FLAG_TRUE);
		salesOrder.setIsHoldByCustomer(Constants.FLAG_TRUE);
		salesOrder.setIsLocked(Constants.FLAG_FALSE);
		salesOrder.setLockedBy(null);
		
		//salesOrderManager.save(salesOrder);
		//if(reasonType==null || reasonType.shortValue()!=OrderConstants.ORDER_CANCELTYPE_OTHER){
		//保存由后台手工审核
			salesOrderManager.save(salesOrder);
		//}else{
			//直接取消; 20090327 by Ryan 取消直接取消订单,所有前台取消订单都必须经后台管理人员操作.
		//	orderProcessFlowManager.doCancelOrder(salesOrder, (AppUser)RequestContext.getCurrentUser());
		//}
		
		//备注
		this.saveNote(salesOrder, note);
	}
	
	/**
	 * 前台 支付回调接口，在线支付成功时调用
	 * @param orderNo 订单编号
	 * @param paymentAmount 支付金额
	 * @param paymentMethodName 支付网关的名称
	 * @param ipAddress 用户的IP地址，注意这个字段最长64位
	 */
	public void doCreatePayment(Serializable orderNo, BigDecimal paymentAmount, String paymentMethodName, String ipAddress){
		OrderPayment orderPayment = new OrderPayment();
		orderPayment.setTransactionType(OrderConstants.TRANSACTION_TYPE_ONLINE);
		orderPayment.setPaymentAmount(paymentAmount);
		orderPayment.setIpAddress(ipAddress);
		orderPayment.setAddedBy(OrderConstants.SYS_ADDED_BY);
		orderPayment.setOrderNo(orderNo.toString());
		orderPayment.setPaymentGatewayName(paymentMethodName);
		int rtn = orderProcessFlowManager.createPaymentStoreFront(orderPayment);
		//前台支付，添加审计信息
		//SalesOrder salesOrder = salesOrderManager.getSalesOrderByOrderNo(orderPayment.getOrderNo());
		
		if(rtn!=1){
			if(logger.isErrorEnabled())
				logger.error("doCreatePayment: create orderPayment(pay online) failed! orderNo="+orderNo);
		}
	}

	public void sendPaymentResult(PaymentHistory ph, String orderNo){
		if (logger.isDebugEnabled())
			logger.debug("MailTemplateSpecifyService sendPaymentResult, orderNo="+ orderNo);
		SalesOrder salesOrder = this.salesOrderManager.getSalesOrderByOrderNo(orderNo);
		if (salesOrder == null) {
			return;
		}
		
		String mailFrom = ConfigUtil.getInstance().getStore(salesOrder.getStore().getCode()).getEmailSender();
		String mailTo = salesOrder.getCustomerEmail();
		Map dataMap = new HashMap();
		dataMap.put("salesOrder", salesOrder);
		dataMap.put("paymentHistory", ph);
		mailEngine.sendSimpleTemplateMail("order/paymentSuccess.vm", dataMap, null, mailFrom, mailTo);				
		this.sendPaymentResultToStore(null, orderNo);
	}
	
	public void sendPaymentResultToStore(PaymentHistory ph, String orderNo) {
		if (logger.isDebugEnabled())
			logger.debug("MailTemplateSpecifyService sendPaymentResult, orderNo="+ orderNo);
		SalesOrder salesOrder = this.salesOrderManager.getSalesOrderByOrderNo(orderNo);
		if (salesOrder == null) {
			return;
		}
		
		String mailFrom = ConfigUtil.getInstance().getStore(salesOrder.getStore().getCode()).getEmailSender();
		String mailTo = ConfigUtil.getInstance().getMailCustomer();
		Map dataMap = new HashMap();
		dataMap.put("salesOrder", salesOrder);
//		dataMap.put("paymentHistory", ph);
		mailEngine.sendSimpleTemplateMail("order/paymentSuccessToStore.vm", dataMap, null, mailFrom, mailTo);
	}
	
	/**
	 * 对已经下单的订单,进行使用[订单专用礼券]和[普通礼券]
	 * 
	 * @param salesOrder
	 * @param gcNo
	 * @return
	 */
	public boolean doUseSalesOrderGiftCertificate(SalesOrder salesOrder, String gcNo, String ipAddress)
	{
		if (StringUtil.isEmpty(gcNo) || salesOrder == null){
			return false;
		}
		//判断是否订单专用礼券，如果礼券号以订单规则开头的，就是订单专用礼券
		String orderNoPreFix=ConfigUtil.getInstance().getOrderNoPrefix();
		String orderNoSuffix=ConfigUtil.getInstance().getOrderNoSuffix();
		Pattern pattern=Pattern.compile("^"+orderNoPreFix+"\\d{5}"+orderNoSuffix+".*");
		if(pattern.matcher(gcNo).matches()){
			if(!gcNo.startsWith(salesOrder.getOrderNo())){
				return false;
			}
		}
		GiftCertificate gc = giftCertificateManager.getGiftCertificate(gcNo);
		if (gc == null)
			return false;
		BigDecimal shouldPay = salesOrder.getShouldPay();
		BigDecimal gcAmt = gc.getRemainedAmt();
		short state = giftCertificateManager.getState(gc);
		if (state == GiftCertificate.STATE_AVAILABLE) 
		{
			if (gcAmt.compareTo(BigDecimal.ZERO) >0 && shouldPay.compareTo(BigDecimal.ZERO) > 0)
			{
				BigDecimal paymentAmount = BigDecimal.ZERO;
				if (shouldPay.compareTo(gcAmt) >= 0)
				{
					gc.setRemainedAmt(gc.getRemainedAmt().add(gcAmt.negate()));
					paymentAmount = gcAmt;					
				}
				else
				{
					gc.setRemainedAmt(gc.getRemainedAmt().add(shouldPay.negate()));
					paymentAmount = shouldPay;					
				}				
				OrderPayment orderPayment = new OrderPayment();
				orderPayment.setGiftCertificateNo(gcNo);
				orderPayment.setTransactionType(OrderConstants.TRANSACTION_TYPE_GIFT_CERT);
				orderPayment.setPaymentAmount(paymentAmount);
				orderPayment.setIpAddress(ipAddress);
				orderPayment.setAddedBy(RequestContext.getCurrentUserName());
				orderPayment.setOrderNo(salesOrder.getOrderNo());				
				orderProcessFlowManager.createPaymentStoreFront(orderPayment);				
				return true;
			}
		}
		return false;
	}

	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void setOrderAuditHelper(OrderAuditHelper orderAuditHelper) {
		this.orderAuditHelper = orderAuditHelper;
	}

	public void updateStatusForReallocated(OrderSku orderSku) {
		OrderShipment orderShipment=orderSku.getOrderShipment();
		orderShipment.updateStatusForReallocated();
		salesOrderManager.save(orderShipment);
	}
}