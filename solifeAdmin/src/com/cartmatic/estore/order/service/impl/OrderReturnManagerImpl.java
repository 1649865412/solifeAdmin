package com.cartmatic.estore.order.service.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.common.helper.ConfigUtil;
import com.cartmatic.estore.common.model.order.OrderAddress;
import com.cartmatic.estore.common.model.order.OrderReturn;
import com.cartmatic.estore.common.model.order.OrderReturnSku;
import com.cartmatic.estore.common.model.order.OrderShipment;
import com.cartmatic.estore.common.model.order.OrderSku;
import com.cartmatic.estore.common.model.order.SalesOrder;
import com.cartmatic.estore.common.model.system.AppUser;
import com.cartmatic.estore.common.service.OrderService;
import com.cartmatic.estore.common.service.ProductService;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;
import com.cartmatic.estore.exception.OutOfStockException;
import com.cartmatic.estore.order.OrderConstants;
import com.cartmatic.estore.order.dao.OrderReturnDao;
import com.cartmatic.estore.order.dao.OrderReturnSkuDao;
import com.cartmatic.estore.order.service.OrderAuditHelper;
import com.cartmatic.estore.order.service.OrderProcessFlowManager;
import com.cartmatic.estore.order.service.OrderReturnManager;
import com.cartmatic.estore.order.service.SalesOrderManager;
import com.cartmatic.estore.order.util.OrderUtil;


/**
 * Manager implementation for OrderReturn, responsible for business processing, and communicate between web and persistence layer.
 */
public class OrderReturnManagerImpl extends GenericManagerImpl<OrderReturn> implements OrderReturnManager {

	private OrderReturnDao orderReturnDao = null;
	
	private OrderReturnSkuDao orderReturnSkuDao = null;

	private SalesOrderManager salesOrderManager = null;
	
	private OrderAuditHelper orderAuditHelper = null;
	
	private OrderService orderService = null;
	
	private OrderProcessFlowManager orderProcessFlowManager = null;
	
	private ProductService productService = null;
	
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}
	
	public void setOrderProcessFlowManager(
			OrderProcessFlowManager orderProcessFlowManager) {
		this.orderProcessFlowManager = orderProcessFlowManager;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public void setOrderAuditHelper(OrderAuditHelper orderAuditHelper) {
		this.orderAuditHelper = orderAuditHelper;
	}
	
    public void setOrderReturnSkuDao(OrderReturnSkuDao orderReturnSkuDao) {
		this.orderReturnSkuDao = orderReturnSkuDao;
	}

	public void setSalesOrderManager(SalesOrderManager salesOrderManager) {
		this.salesOrderManager = salesOrderManager;
	}
	    
	/**
	 * @param orderReturnDao
	 *            the orderReturnDao to set
	 */
	public void setOrderReturnDao(OrderReturnDao orderReturnDao) {
		this.orderReturnDao = orderReturnDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		dao = orderReturnDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete(java.lang.Object)
	 */
	@Override
	protected void onDelete(OrderReturn entity) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave(java.lang.Object)
	 */
	@Override
	protected void onSave(OrderReturn entity) {

	}
	
	/**
	 * 创建退货
	 * @param params 页面表单参数
	 * @param curUser
	 * @return 1 成功
	 * 		   0 参数异常	
	 * 		  -1 业务异常
	 */
	public int createReturn(Map params, AppUser curUser){
		String orderShipmentId = OrderUtil.getParameter(params, "orderShipmentId");
		String lessRestockAmount = OrderUtil.getParameter(params, "lessRestockAmount");
		String note = OrderUtil.getParameter(params, "note");
		
		if(orderShipmentId==null) return 0;
		
		OrderShipment orderShipment = salesOrderManager.getOrderShipmentById(new Integer(orderShipmentId));
		SalesOrder salesOrder = orderShipment.getSalesOrder();
		Iterator<OrderSku> i = orderShipment.getOrderSkus().iterator();
		
		OrderReturn orderReturn = createteOrderReturn(salesOrder.getSalesOrderId(), OrderConstants.RETURN_TYPE_RETURN, Constants.FLAG_TRUE, 
										new BigDecimal(lessRestockAmount).setScale(2, BigDecimal.ROUND_HALF_UP), note, curUser.getAppuserId());
		int returnSkuCount = 0;
		BigDecimal itemSubTotal = BigDecimal.ZERO;
		BigDecimal itemTax = BigDecimal.ZERO;
		while(i.hasNext()){
			OrderSku orderSku = i.next();
			Integer orderSkuId = orderSku.getOrderSkuId();
			//获取页面参数值
			String quantity = OrderUtil.getParameter(params, "returnQuantity" + orderSkuId);
			String reasonType =  OrderUtil.getParameter(params, "reasonType" + orderSkuId);
			Integer quantityInteger = Integer.parseInt(quantity);
			if(quantity==null || "0".equals(quantity))
				continue;
			BigDecimal itemReturmAmount = OrderReturn.getItemReturmAmount(orderSku.getDiscountPrice(), orderSku.getItemDiscountAmount(), orderSku.getQuantity(), quantityInteger);
			
			itemSubTotal = itemSubTotal.add(itemReturmAmount);
			itemTax = itemTax.add( orderSku.getTax().multiply( BigDecimal.valueOf(quantityInteger).setScale(2).divide(BigDecimal.valueOf(orderSku.getQuantity()), RoundingMode.HALF_UP)) );
			
			createOrderReturnSku(orderReturn, orderSku, quantityInteger, Short.valueOf(reasonType), itemReturmAmount);
			returnSkuCount++;
		}
		if(returnSkuCount==0)
			//如果没有退项货被创建，抛出异常，事务回滚
			throw new RuntimeException("no OrderReturnSku created!");
		
		orderReturn.setItemSubTotal(itemSubTotal.setScale(2, BigDecimal.ROUND_HALF_UP));
		orderReturn.setItemTax(itemTax.setScale(2, BigDecimal.ROUND_HALF_UP));
		orderReturn.setShippingCost(orderShipment.getShippingCost());
		orderReturn.setDiscountAmount(orderShipment.getDiscountAmount());
		orderReturn.setShippingTax(orderShipment.getShippingTax());
		
		this.save(orderReturn);
		
		orderAuditHelper.logCreateReturn(salesOrder, orderShipment, orderReturn, curUser);
		salesOrderManager.save(salesOrder);
		
		return 1;
	}
	
	private OrderReturnSku createOrderReturnSku(OrderReturn orderReturn,  OrderSku orderSku, Integer quantityInteger, Short reasonType, BigDecimal itemReturmAmount){
		OrderReturnSku ors = new OrderReturnSku();
		ors.setOrderReturn(orderReturn);
		ors.setOrderSku(orderSku);
		ors.setQuantity(quantityInteger);
		ors.setReasonType(reasonType);
		ors.setReturnAmount(itemReturmAmount.setScale(2, BigDecimal.ROUND_HALF_UP));
		ors.setReceivedQuantity(0);
		orderReturnSkuDao.save(ors);
		
		return ors;
	}
	
	private OrderReturn createteOrderReturn(Integer salesOrderId, Short returnType, Short isPhysicalReturn, BigDecimal lessRestockAmount, String note, Integer curUserId){
		OrderReturn orderReturn = new OrderReturn();
		orderReturn.setCreateBy(curUserId);
		orderReturn.setIsPhysicalReturn(isPhysicalReturn);
		orderReturn.setLessRestockAmount(lessRestockAmount);
		orderReturn.setNote(note);
		orderReturn.setReturnType(returnType);
		orderReturn.setSalesOrderId(salesOrderId);
		orderReturn.setStatus(OrderConstants.RETURN_STATUS_AWAITING_RETURN);
		orderReturn.setRmaNo("");
		
		orderReturn.setItemSubTotal(BigDecimal.ZERO);
		orderReturn.setShippingCost(BigDecimal.ZERO);
		orderReturn.setDiscountAmount(BigDecimal.ZERO);
		orderReturn.setItemTax(BigDecimal.ZERO);
		orderReturn.setShippingTax(BigDecimal.ZERO);
		
		this.save(orderReturn);
		orderReturn.setRmaNo(orderReturn.getOrderReturnId().toString());
		
		return orderReturn;
	}
	
	/**
	 * 编辑退货
	 * @param params 页面表单参数
	 * @param curUser
	 * @return 1 成功
	 * 		   0 参数异常	
	 * 		  -1 业务异常 编辑必须在未收到原货之前
	 */
	public int editReturn(Map params, AppUser curUser){
		String orderReturnId = OrderUtil.getParameter(params, "orderReturnId");
		String lessRestockAmount = OrderUtil.getParameter(params, "lessRestockAmount");
		String note = OrderUtil.getParameter(params, "note");
		if(orderReturnId==null) return 0;
		
		OrderReturn orderReturn = orderReturnDao.getById(new Integer(orderReturnId));
		
		//计算该退换货下的商品项的可退换数量
		Set<OrderReturnSku> orderReturnSkus = orderReturn.getOrderReturnSkus();
		BigDecimal itemSubTotal = BigDecimal.ZERO;
		BigDecimal itemTax = BigDecimal.ZERO;
		for(OrderReturnSku orderReturnSku:orderReturnSkus){
			OrderSku orderSku = orderReturnSku.getOrderSku();
			
			String quantity = OrderUtil.getParameter(params, "returnQuantity" + orderSku.getOrderSkuId());
			String reasonType =  OrderUtil.getParameter(params, "reasonType" + orderSku.getOrderSkuId());
			Integer quantityInteger = Integer.parseInt(quantity);
			//取消商品项退换
			if(quantity==null || "0".equals(quantity))
				continue;
			orderReturnSku.setQuantity(quantityInteger);
			orderReturnSku.setReasonType(new Short(reasonType));
			
			BigDecimal itemReturmAmount = OrderReturn.getItemReturmAmount(orderSku.getDiscountPrice(), orderSku.getItemDiscountAmount(), orderSku.getQuantity(), quantityInteger);

			itemSubTotal = itemSubTotal.add(itemReturmAmount);
			itemTax = itemTax.add( orderSku.getTax());
		}
		orderReturn.setNote(note);
		orderReturn.setItemSubTotal(itemSubTotal.setScale(2, BigDecimal.ROUND_HALF_UP));
		orderReturn.setItemTax(itemTax.setScale(2, BigDecimal.ROUND_HALF_UP));
		orderReturn.setLessRestockAmount(new BigDecimal(lessRestockAmount).setScale(2, BigDecimal.ROUND_HALF_UP));
		orderReturnDao.evict(orderReturn);
		orderReturn.setVersion(new Integer(OrderUtil.getParameter(params, "version")));
		orderReturnDao.save(orderReturn);
		
		return 1;
	}
	
	/**
	 * 取消退换货
	 * @param orderReturnId
	 * @param curUser
	 * @return 1 成功
	 * 		   0 参数异常	
	 * 		  -1 业务异常 
	 */
	public int doCancelReturn(Serializable orderReturnId, AppUser curUser){
		if(orderReturnId==null) return 0;
		OrderReturn orderReturn = orderReturnDao.getById(new Integer(orderReturnId.toString()));
		orderReturn.setStatus(OrderConstants.RETURN_STATUS_CANCELLED);
		this.save(orderReturn);
		
		//取消换货订单
		if(orderReturn.isExchange()){
			orderProcessFlowManager.doCancelOrder(orderReturn.getExchangeOrderId(), curUser);
		}
		
		return 1;
	}
	
	/**
	 * 完成退换货
	 * @param params 页面表单参数
	 * @param curUser
	 * @return 1 成功
	 * 		   0 参数异常	
	 * 		  -1 业务异常
	 */
	public int doCompleteReturn(Map params, AppUser curUser){
		String orderReturnId = OrderUtil.getParameter(params, "orderReturnId");
		
		OrderReturn orderReturn = orderReturnDao.getById(new Integer(orderReturnId.toString()));
		if(orderReturn==null) return 0;
		
		String lessRestockAmount = OrderUtil.getParameter(params, "lessRestockAmount");
		String note = OrderUtil.getParameter(params, "note");
		orderReturn.setLessRestockAmount(new BigDecimal(lessRestockAmount).setScale(2, BigDecimal.ROUND_HALF_UP));
		orderReturn.setNote(note);
		
		orderReturn.setStatus(OrderConstants.RETURN_STATUS_COMPLETE);
		
		orderReturnDao.evict(orderReturn);
		orderReturn.setVersion(new Integer(OrderUtil.getParameter(params, "version")));
		orderReturnDao.save(orderReturn);
		
		if(orderReturn.isExchange()){
			//换货订单处理
			SalesOrder salesOrder = salesOrderManager.getById(orderReturn.getExchangeOrderId());
			salesOrder.setOrderStatus(OrderConstants.ORDER_STATUS_IN_PROGRESS);
			//退款计入换货订单帐户中
			orderProcessFlowManager.payExchangeOrder(orderReturn, salesOrder);
			salesOrderManager.save(salesOrder);
		}
		//@TODO 单向，购买数量暂不减少
		/*Set<OrderReturnSku> orderReturnSkus = orderReturn.getOrderReturnSkus();
		for(OrderReturnSku orderReturnSku: orderReturnSkus){
			productService.doSubtractProductBuyCount(orderReturnSku.getOrderSku().getProductId(), orderReturnSku.getReceivedQuantity());
		}*/
		
		return 1;
		
	}
	
	/**
	 * 收到顾客退回的商品
	 * @param params
	 * @param curUser
	 * @return 1 成功
	 * 		   0 参数异常	
	 * 		  -1 业务异常
	 */
	public int doReceiveReturn(Map params, AppUser curUser){
		String orderReturnId = OrderUtil.getParameter(params, "orderReturnId");
		if(orderReturnId==null) return 0;
		
		OrderReturn orderReturn = orderReturnDao.getById(new Integer(orderReturnId.toString()));
		if(orderReturn.getStatus().shortValue()==OrderConstants.RETURN_STATUS_AWAITING_COMPLETION)
			return -1;
		Set<OrderReturnSku> orderReturnSkus = orderReturn.getOrderReturnSkus();
		
		for(OrderReturnSku orderReturnSku: orderReturnSkus){
			String receivedQuantity = OrderUtil.getParameter(params, "receivedQuantity"+orderReturnSku.getOrderReturnSkuId());
			String receivedStatus = OrderUtil.getParameter(params, "receivedStatus"+orderReturnSku.getOrderReturnSkuId());
			Integer receivedQuantityInteger = new Integer(receivedQuantity);
			
			orderReturnSku.setReceivedQuantity(receivedQuantityInteger);
			orderReturnSku.setReceivedStatus(new Short(receivedStatus));
			
		}
		String note = OrderUtil.getParameter(params, "note");
		orderReturn.setNote(note);
		orderReturn.setReceivedBy(curUser.getAppuserId());
		
		orderReturn.setStatus(OrderConstants.RETURN_STATUS_AWAITING_COMPLETION);
		this.save(orderReturn);
		
		return 1;
	}
	
	/**
	 * 创建换货，先退货后生成换货订单
	 * @param params 页面表单参数
	 * @param curUser
	 * @param ipAddressFromCurUser
	 * @return 1 成功
	 * 		   0 参数异常	
	 * 		  -1 业务异常
	 * @throws OutOfStockException 
	 */
	public int createExchange(Map params, AppUser curUser, String ipAddressFromCurUser) throws OutOfStockException{
		//创建退货
		String orderShipmentId = OrderUtil.getParameter(params, "orderShipmentId");
		String lessRestockAmount = OrderUtil.getParameter(params, "lessRestockAmount");
		String note = OrderUtil.getParameter(params, "note");
		
		if(orderShipmentId==null) return 0;
		
		OrderShipment orderShipment = salesOrderManager.getOrderShipmentById(new Integer(orderShipmentId));
		SalesOrder salesOrder = orderShipment.getSalesOrder();
		Iterator<OrderSku> i = orderShipment.getOrderSkus().iterator();
		
		
		OrderReturn orderReturn = createteOrderReturn(salesOrder.getSalesOrderId(), OrderConstants.RETURN_TYPE_EXCHANGE, Constants.FLAG_TRUE, 
				new BigDecimal(lessRestockAmount).setScale(2, BigDecimal.ROUND_HALF_UP), note, curUser.getAppuserId());
		
		int returnSkuCount = 0;
		Set<OrderSku> exchangeOrderSkus = new HashSet<OrderSku>();
		BigDecimal itemSubTotal = BigDecimal.ZERO;
		BigDecimal itemTax = BigDecimal.ZERO;
		while(i.hasNext()){
			OrderSku orderSku = i.next();
			Integer orderSkuId = orderSku.getOrderSkuId();
			//获取页面参数值
			String quantity = OrderUtil.getParameter(params, "returnQuantity" + orderSkuId);
			String reasonType =  OrderUtil.getParameter(params, "reasonType" + orderSkuId);
			Integer quantityInteger = Integer.parseInt(quantity);
			if(quantity==null || "0".equals(quantity))
				continue;
			OrderSku exchangeOrderSku = orderSku.clone(quantityInteger, 0);
			exchangeOrderSku.setTax(orderSku.getTax());
			exchangeOrderSku.setTaxName(orderSku.getTaxName());
			exchangeOrderSkus.add(exchangeOrderSku);
			
			BigDecimal itemReturmAmount = OrderReturn.getItemReturmAmount(orderSku.getDiscountPrice(), orderSku.getItemDiscountAmount(), orderSku.getQuantity(), quantityInteger);

			itemSubTotal = itemSubTotal.add(itemReturmAmount);
			itemTax = itemTax.add( orderSku.getTax() );
			
			createOrderReturnSku(orderReturn, orderSku, quantityInteger, Short.valueOf(reasonType), itemReturmAmount);
			returnSkuCount++;
		}
		if(returnSkuCount==0)
			//如果没有退项货被创建，抛出异常，事务回滚
			throw new RuntimeException("no OrderReturnSku created!");
		
		orderReturn.setItemSubTotal(itemSubTotal.setScale(2, BigDecimal.ROUND_HALF_UP));
		orderReturn.setItemTax(itemTax.setScale(2, BigDecimal.ROUND_HALF_UP));
		//计算运费与运费税
		orderReturn.setShippingCost(orderShipment.getShippingCost());
		orderReturn.setShippingTax(orderShipment.getShippingTax());
		
		//=================================创建换货订单=============================
		OrderAddress billingAddress = null;
		OrderAddress shippingAddress = null;
		
		if(salesOrder.getOrderAddress()!=null)
			billingAddress = salesOrder.getOrderAddress().clone(); 
		if(orderShipment.getOrderAddress()!=null)
			shippingAddress = orderShipment.getOrderAddress().clone();
		
		OrderShipment exchangeOrderShipment = orderShipment.clone();
		exchangeOrderShipment.setItemSubTotal(itemSubTotal);
		
		BigDecimal totalAmount = orderReturn.getItemSubTotal().add(orderReturn.getItemTax())
									.add(orderReturn.getShippingCost()).add(orderReturn.getShippingTax());
		SalesOrder exchangeOrder = salesOrder.clone(Constants.FLAG_TRUE, OrderConstants.ORDER_STATUS_AWAITING_RETURN, OrderConstants.PAYMENT_STATUS_UNPAID, totalAmount);
		exchangeOrder.setOriginalOrderId(salesOrder.getSalesOrderId());
		exchangeOrder.setPaidAmount(new BigDecimal(0));
		exchangeOrder.setIpAddress(ipAddressFromCurUser);

		exchangeOrder.setStore(salesOrder.getStore());
		//创建换货订单
		orderService.doPlaceOrder(billingAddress, shippingAddress, exchangeOrder, exchangeOrderShipment, exchangeOrderSkus, null, null, null);
		
		orderReturn.setExchangeOrderId(exchangeOrder.getSalesOrderId());
		this.save(orderReturn);
		
		return 1;
	}
	
}
