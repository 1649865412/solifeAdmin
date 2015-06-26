package com.cartmatic.estore.order.service.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.catalog.CatalogConstants;
import com.cartmatic.estore.common.helper.ConfigUtil;
import com.cartmatic.estore.common.model.catalog.ProductSku;
import com.cartmatic.estore.common.model.customer.Address;
import com.cartmatic.estore.common.model.order.OrderAddress;
import com.cartmatic.estore.common.model.order.OrderAudit;
import com.cartmatic.estore.common.model.order.OrderPayment;
import com.cartmatic.estore.common.model.order.OrderPromotion;
import com.cartmatic.estore.common.model.order.OrderShipment;
import com.cartmatic.estore.common.model.order.OrderSku;
import com.cartmatic.estore.common.model.order.SalesOrder;
import com.cartmatic.estore.common.model.system.AppUser;
import com.cartmatic.estore.common.model.system.PaymentMethod;
import com.cartmatic.estore.common.model.system.Region;
import com.cartmatic.estore.common.model.system.ShippingRate;
import com.cartmatic.estore.common.model.system.TaxRate;
import com.cartmatic.estore.common.model.system.Wrap;
import com.cartmatic.estore.common.service.CheckoutService;
import com.cartmatic.estore.common.service.InventoryService;
import com.cartmatic.estore.common.service.PaymentMethodService;
import com.cartmatic.estore.common.service.ProductService;
import com.cartmatic.estore.common.service.SupplierService;
import com.cartmatic.estore.core.search.SearchCriteria;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;
import com.cartmatic.estore.core.util.ContextUtil;
import com.cartmatic.estore.core.view.MailEngine;
import com.cartmatic.estore.customer.service.AddressManager;
import com.cartmatic.estore.exception.OutOfStockException;
import com.cartmatic.estore.order.OrderConstants;
import com.cartmatic.estore.order.dao.OrderAddressDao;
import com.cartmatic.estore.order.dao.OrderAuditDao;
import com.cartmatic.estore.order.dao.OrderPaymentDao;
import com.cartmatic.estore.order.dao.OrderPromotionDao;
import com.cartmatic.estore.order.dao.OrderShipmentDao;
import com.cartmatic.estore.order.dao.OrderSkuDao;
import com.cartmatic.estore.order.dao.SalesOrderDao;
import com.cartmatic.estore.order.service.OrderAuditHelper;
import com.cartmatic.estore.order.service.SalesOrderManager;
import com.cartmatic.estore.order.util.OrderUtil;
import com.cartmatic.estore.order.vo.OrderFilter;
import com.cartmatic.estore.system.service.AppUserManager;
import com.cartmatic.estore.system.service.RegionManager;
import com.cartmatic.estore.system.service.TaxRateManager;
import com.cartmatic.estore.system.service.WrapManager;
import com.cartmatic.estore.webapp.util.RequestContext;


/**
 * Manager implementation for SalesOrder, responsible for business processing, and communicate between web and persistence layer.
 */
public class SalesOrderManagerImpl extends GenericManagerImpl<SalesOrder> implements SalesOrderManager {

	private SalesOrderDao salesOrderDao = null;	
	private OrderShipmentDao orderShipmentDao = null;	
	private OrderAddressDao orderAddressDao = null;	
	private OrderSkuDao orderSkuDao = null;	
	private OrderAuditDao orderAuditDao = null;	
	private AppUserManager appUserManager = null;	
	private OrderAuditHelper orderAuditHelper = null;	
	private ProductService productService = null;	
	private OrderPaymentDao orderPaymentDao = null;
	private OrderPromotionDao orderPromotionDao = null;	
	private InventoryService inventoryService = null;
	private MailEngine mailEngine = null;	
	private WrapManager wrapManager = null;	
	private AddressManager addressManager = null;	
	private TaxRateManager taxRateManager = null;	
	private CheckoutService checkoutService = null;	
	private RegionManager regionManager = null;
	private SupplierService supplierService=null;
	private PaymentMethodService paymentMethodService=null;
	
	public void setSupplierService(SupplierService supplierService) {
		this.supplierService = supplierService;
	}

	public void setPaymentMethodService(PaymentMethodService paymentMethodService) {
		this.paymentMethodService = paymentMethodService;
	}

	public void setRegionManager(RegionManager regionManager) {
		this.regionManager = regionManager;
	}

	public void setCheckoutService(CheckoutService avalue) {
		this.checkoutService = avalue;
	}

	public TaxRateManager getTaxRateManager() {
		return taxRateManager;
	}

	public void setTaxRateManager(TaxRateManager taxRateManager) {
		this.taxRateManager = taxRateManager;
	}

	public void setAddressManager(AddressManager addressManager) {
		this.addressManager = addressManager;
	}
    
	public void setWrapManager(WrapManager wrapManager) {
		this.wrapManager = wrapManager;
	}
	
	public void setMailEngine(MailEngine mailEngine) {
		this.mailEngine = mailEngine;
	}

	public void setInventoryService(InventoryService inventoryService) {
		this.inventoryService = inventoryService;
	}

	public void setOrderPromotionDao(OrderPromotionDao orderPromotionDao) {
		this.orderPromotionDao = orderPromotionDao;
	}

	public void setOrderPaymentDao(OrderPaymentDao orderPaymentDao) {
		this.orderPaymentDao = orderPaymentDao;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	public void setOrderAuditHelper(OrderAuditHelper orderAuditHelper) {
		this.orderAuditHelper = orderAuditHelper;
	}

	public void setOrderAuditDao(OrderAuditDao orderAuditDao) {
		this.orderAuditDao = orderAuditDao;
	}

	public void setOrderSkuDao(OrderSkuDao orderSkuDao) {
		this.orderSkuDao = orderSkuDao;
	}
	
	public void setOrderAddressDao(OrderAddressDao orderAddressDao) {
		this.orderAddressDao = orderAddressDao;
	}

	public void setAppUserManager(AppUserManager appUserManager) {
		this.appUserManager = appUserManager;
	}

	public void setOrderShipmentDao(OrderShipmentDao orderShipmentDao) {
		this.orderShipmentDao = orderShipmentDao;
	}

	/**
	 * @param salesOrderDao
	 *            the salesOrderDao to set
	 */
	public void setSalesOrderDao(SalesOrderDao salesOrderDao) {
		this.salesOrderDao = salesOrderDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		dao = salesOrderDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete(java.lang.Object)
	 */
	@Override
	protected void onDelete(SalesOrder entity) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave(java.lang.Object)
	 */
	@Override
	protected void onSave(SalesOrder entity) {

	}
	
	
	/**
	 * 通过会员ID与订单编号获取订单
	 * @param orderNo 订单编号
	 * @param userId 前台会员ID
	 * @return
	 */
	public SalesOrder getSalesOrder(Integer storeId,String orderNo, Serializable userId){
		Assert.notNull(storeId);
		Assert.notNull(orderNo);
		Assert.notNull(userId);
		return salesOrderDao.getSalesOrder(storeId,orderNo, userId);
	}
	
	/**
	 * 通过会员Email与订单编号获取订单
	 * @param orderNo 订单编号
	 * @param userId 前台会员ID
	 * @return
	 */
	public SalesOrder getSalesOrder(String orderNo, String email)
	{
		Assert.notNull(orderNo);
		Assert.notNull(email);
		return salesOrderDao.getSalesOrder(orderNo, email);
	}
	
	/**
	 * 通过会员ID与订单ID获取订单
	 * @param salesOrderId 订单ID
	 * @param userId 前台会员ID
	 * @return
	 */
	public SalesOrder getSalesOrder(Integer storeId,Serializable salesOrderId, Serializable userId){
		Assert.notNull(salesOrderId);
		Assert.notNull(userId);
		
		return salesOrderDao.getSalesOrder(storeId,salesOrderId, userId);
	}

	public List<OrderShipment> getAllShipmentsByOrderId(Serializable salesOrderId){
		Assert.notNull(salesOrderId);
		return orderShipmentDao.getAllShipmentsByOrderId(new Integer(salesOrderId.toString()));
	}
	
	public Integer countShipments4Picking(){
		return orderShipmentDao.countShipments4Picking();
	}
	
	/**
	 * 获取待备货的发货项
	 * @return
	 */
	public List<OrderShipment> getShipments4Picking(){
		return orderShipmentDao.getShipments4Picking();
	}
	
	public SalesOrder doGetAndLockById(Serializable salesOrderId){
		Assert.notNull(salesOrderId);
		
		AppUser appUser = (AppUser) RequestContext.getCurrentUser();
		SalesOrder salesOrder = lockSalesOrder(new Integer(salesOrderId.toString()), appUser.getAppuserId());
		
		this.setLockAttr(salesOrder, appUser);
		
		return salesOrder;
	}
	
	public SalesOrder getForViewById(Serializable salesOrderId){
		Assert.notNull(salesOrderId);
		
		AppUser appUser = (AppUser) RequestContext.getCurrentUser();
		SalesOrder salesOrder = this.getById(new Integer(salesOrderId.toString()));
		
		this.setLockAttr(salesOrder, appUser);
		
		return salesOrder;
	}
	
	/**
	 * 保存发货项
	 * @param OrderShipment
	 */
	public void save(OrderShipment orderShipment){
		orderShipmentDao.save(orderShipment);
	}
	
	/**
	 * 保存订单地址
	 * @param orderAddress
	 */
	public void save(OrderAddress orderAddress){
		orderAddressDao.save(orderAddress);
	}
	
	public void save(OrderSku orderSku){
		orderSkuDao.save(orderSku);
	}
	
	/**
	 * 保存订单备注审计
	 * @param orderAudit
	 */
	public void save(OrderAudit orderAudit){
		orderAuditDao.save(orderAudit);
	}
	
	public void save(OrderPayment orderPayment){
		orderPaymentDao.save(orderPayment);
	}
	
	public void save(OrderPromotion orderPromotion){
		orderPromotionDao.save(orderPromotion);
	}
	
	public OrderShipment getOrderShipmentById(Serializable orderShipmentId){
		Assert.notNull(orderShipmentId);
		return orderShipmentDao.getById(new Integer(orderShipmentId.toString()));
	}
	
	public OrderAddress getOrderAddressById(Serializable orderAddressId){
		Assert.notNull(orderAddressId);
		return orderAddressDao.getById(new Integer(orderAddressId.toString()));
	}
	
	public OrderSku getOrderSkuById(Serializable orderSkuId){
		Assert.notNull(orderSkuId);
		return orderSkuDao.getById(new Integer(orderSkuId.toString()));
	}
	
	public OrderShipment getOrderShipmentByShipmentNo(Serializable shipmentNo){
		if(shipmentNo==null) return null;
		OrderShipment orderShipment = orderShipmentDao.findUniqueByProperty("shipmentNo", ((String)shipmentNo).trim());
		if(orderShipment!=null && orderShipment.getStatus().intValue()==OrderConstants.SHIPMENT_STATUS_PICKING.intValue()){
			return orderShipment;
		}
		
		return null;
	}
	
	/**
	 *  获取指定订单下的未确认备货的发货项的ID与编码
	 * @param salesOrderId
	 * @return
	 */
	public Map<Integer, String> getShipments4MoveItemById(Serializable salesOrderId, Serializable orderShipmentId){
		
		return orderShipmentDao.getForMoveItemByOrderId(new Integer(salesOrderId.toString()), new Integer(orderShipmentId.toString()));
	}
	
	/**
	 * 添加商品到指定的发货项中
	 * @param orderShipmentId
	 * @param productSkuId
	 * @param curUser
	 * @return 1 - 成功
	 * 		   0 - 参数异常
	 * 		  -1 - 业务异常，订单未被当前用户锁住或被暂停处理或发货项已确订可备货
	 * 		  -2 - 业务异常，该产品可售数量（包括可预订数量）为零，不可销售！
	 * @throws OutOfStockException 
	 */
	public int addItem(Serializable orderShipmentId, Serializable productSkuId, AppUser curUser) throws OutOfStockException{
		if(orderShipmentId==null || productSkuId==null)
			return 0;
		
		OrderShipment orderShipment = this.getOrderShipmentById(orderShipmentId);
		SalesOrder salesOrder = orderShipment.getSalesOrder();
		
		if(salesOrder.getIsLocked().byteValue()!=Constants.FLAG_TRUE.byteValue()  
				|| curUser.getAppuserId().intValue()!=salesOrder.getLockedBy().intValue()
				|| salesOrder.getIsOnHold().byteValue()==Constants.FLAG_TRUE.byteValue()
				|| (orderShipment.getStatus().shortValue()!=OrderConstants.SHIPMENT_STATUS_INVENTORY_ASSIGNED.shortValue()
						&& orderShipment.getStatus().shortValue()!=OrderConstants.SHIPMENT_STATUS_AWAITING_INVENTORY.shortValue()))
			return -1;
		
		ProductSku productSku = productService.getProductSku(new Integer(productSkuId.toString()));
		
		Set<OrderSku> orderSkus = orderShipment.getOrderSkus();
		
		//默认新增时，只增加一个数量
		int newOrderSkuQty = 1;
		//库存分配,移动后面，确定已全额支付完毕了才分配。[库存分配触发调整]
//		Integer[] newAllocatedQuantity = inventoryService.doAllocateProductSku(salesOrder, productSku, newOrderSkuQty);
		
		//审计
		orderAuditHelper.logAddItem(salesOrder, orderShipment, productSku, curUser);
		
		//sku能否合并
		boolean merged = false;
		for(OrderSku orderSku : orderSkus){
			if(productSku.getProductSkuId().intValue()==orderSku.getProductSkuId().intValue()){
				orderSku.setQuantity(orderSku.getQuantity()+newOrderSkuQty);
//				orderSku.setAllocatedQuantity(newAllocatedQuantity[0]+orderSku.getAllocatedQuantity());
				merged = true;
				break;
			}
		}
		//新增SKU
		if(!merged){
			OrderSku orderSku = getOrderSku(productSku, 0);
//			orderSku.setAllocatedQuantity(newAllocatedQuantity[0]);
			orderSku.setOrderShipmentId(orderShipment.getOrderShipmentId());
			orderShipment.getOrderSkus().add(orderSku);
		}
		
		float itemSubTotal = 0;
		boolean hasAnyOneAwaiting = false;
		for(OrderSku orderSku : orderSkus){
			if(!hasAnyOneAwaiting && orderSku.getQuantity()-orderSku.getAllocatedQuantity()>0)
				hasAnyOneAwaiting=true;
			//款项重新汇总
			itemSubTotal += orderSku.getQuantity() * orderSku.getDiscountPrice().floatValue();
		}
		orderShipment.setItemSubTotalWithFloat(itemSubTotal);
		//税费、运输费
		this.caculateTaxesAndShippingCost(orderShipment, false);
		this.save(orderShipment);
		
		//注意顺序
		salesOrder.updateTotalAmount();
		salesOrder.updatePaymentStatus();
		this.save(salesOrder);
		
		//库存分配,确定已全额支付完毕了才分配。[库存分配触发调整]
		if(salesOrder.getPaymentStatus().intValue()==OrderConstants.PAYMENT_STATUS_PAID){
			doAllocateQty4Order(salesOrder);
		}
		return 1;
	}
	
	private OrderSku getOrderSku(ProductSku productSku, int allocatedQuantity){
		OrderSku orderSku = new OrderSku();
		orderSku.setAllocatedQuantity(allocatedQuantity);
		orderSku.setDiscountPrice(productSku.getPrice());
		orderSku.setItemType(productSku.getSkuKind());
		orderSku.setProductName(productSku.getProduct().getProductName());
		orderSku.setProductSkuId(productSku.getProductSkuId());
		orderSku.setProductId(productSku.getProductId());
		orderSku.setProductSkuCode(productSku.getProductSkuCode());
		orderSku.setQuantity(1);
		orderSku.setProductSku(productSku);
		orderSku.setPrice(productSku.getPrice());
		
		//TODO 是否特价是否批发价
		orderSku.setIsOnSale(new Short("0"));
		orderSku.setIsWholesale(new Short("0"));
		orderSku.setItemDiscountAmount(new BigDecimal(0));
		orderSku.setDisplaySkuOptions(productSku.getOrderSkuDisplayOption());
		
		return orderSku;
	}
	
	/**
	 * 移动商品
	 */
	public OrderShipment doMoveItem(Serializable orderSkuId, Serializable toOrderShipmentId, Integer quantity){
		//商品(sku)最终移动到的发货项
		OrderShipment finalOrderShipment=null;
		Assert.notNull(orderSkuId);
		Assert.notNull(quantity);
		
		if(quantity==0)
			return finalOrderShipment;
		OrderSku orderSku = orderSkuDao.getById(new Integer(orderSkuId.toString()));
		OrderShipment from =  orderSku.getOrderShipment();
		SalesOrder salesOrder = from.getSalesOrder();
		
		Integer finalQty = orderSku.getQuantity()-quantity;
		if(finalQty<0 || (finalQty==0 && from.getOrderSkus().size()==1))
			return finalOrderShipment;
		
		OrderSku newOrderSku = null;
		//更新原orderSku
		if(finalQty==0){
			//该商品全部移走
			newOrderSku = orderSku;
			newOrderSku.setOrderShipment(null);
			from.delete(orderSku);
		}else{
			//当前已分配－更改后的商品数>0，说明当前orderSku为已分配状态
			int allocatedQuantity4New =orderSku.getAllocatedQuantity()-finalQty;
			if(allocatedQuantity4New>0)
				orderSku.setAllocatedQuantity(orderSku.getAllocatedQuantity()-allocatedQuantity4New);
			orderSku.setQuantity(finalQty);
			
			newOrderSku = orderSku.clone(quantity, allocatedQuantity4New);
			
			orderSkuDao.save(orderSku);
		}
		
		//计算原发货项总额
		float minusAmout = orderSku.getDiscountPrice().floatValue();
		minusAmout = minusAmout*quantity;
		float itemSubTotal = from.getItemSubTotal().floatValue();
		itemSubTotal -= minusAmout;
		from.setItemSubTotalWithFloat(itemSubTotal);
		//更新原发货项状态  --当发货时，分拆订单的，原发货项的状态应该不需要更新 by kedou 090914
		if(from.getStatus().shortValue()!=OrderConstants.SHIPMENT_STATUS_PICKING.shortValue()){
			from.updateStatusForReallocated();
		}
		//this.caculateTaxesAndShippingCost(from, false);  移动item不需要重新计算运费 by Ryan 20100528
		orderShipmentDao.save(from);
		
		AppUser appUser = (AppUser) RequestContext.getCurrentUser();
		if(toOrderShipmentId!=null){
			OrderShipment to =  orderShipmentDao.getById(new Integer(toOrderShipmentId.toString()));
			//更新总金额
			itemSubTotal = to.getItemSubTotal().floatValue();
			itemSubTotal += minusAmout;
			to.setItemSubTotalWithFloat(itemSubTotal);
			boolean merged = false;
			//sku合并
			Iterator<OrderSku> i = to.getOrderSkus().iterator();
			while(i.hasNext()){
				OrderSku temp = i.next();
				if(temp.getProductSkuId().intValue()==newOrderSku.getProductSkuId().intValue()){
					temp.setQuantity(temp.getQuantity()+newOrderSku.getQuantity());
					temp.setAllocatedQuantity(temp.getAllocatedQuantity()+newOrderSku.getAllocatedQuantity());
					orderSkuDao.save(temp);
					merged = true;
					break;
				}
					
			}
			//在发货项上新增sku
			if(!merged){
				newOrderSku.setOrderShipment(to);
				to.getOrderSkus().add(newOrderSku);
				orderSkuDao.save(newOrderSku);
			}else{
				if(newOrderSku.getOrderSkuId()!=null)
					orderSkuDao.delete(newOrderSku);
			}
			//更新目的发货项状态
			to.updateStatusForReallocated();
			//this.caculateTaxesAndShippingCost(to, false);  移动item不需要重新计算运费 by Ryan 20100528
			orderShipmentDao.save(to);
			//审计
			orderAuditHelper.logMoveItem(salesOrder, from, to, orderSku.getProductSku(), true, appUser);
			finalOrderShipment=to;
		}else{
			//保存新发货项地址
			OrderAddress newOrderAddress = null;
			if(from.getOrderAddress()!=null){
				newOrderAddress = from.getOrderAddress().clone();
				orderAddressDao.save(newOrderAddress);
			}
			//创建新发货项
			OrderShipment newOrderShipment = this.clone(from, newOrderSku);
			newOrderShipment.setOrderAddress(newOrderAddress);
			newOrderShipment.addOrderSku(orderSku);
			//this.caculateTaxesAndShippingCost(newOrderShipment, false);  移动item不需要重新计算运费 by Ryan 20100528
			orderShipmentDao.save(newOrderShipment);
			
			newOrderSku.setOrderShipment(newOrderShipment);
			orderSkuDao.save(newOrderSku);
			
			salesOrder.getOrderShipments().add(newOrderShipment);
			
			//审计
			orderAuditHelper.logMoveItem(salesOrder, from, newOrderShipment, orderSku.getProductSku(), true, appUser);
			
			finalOrderShipment=newOrderShipment;
		}
		
		salesOrder.updateTotalAmount();
		this.save(salesOrder);
		return finalOrderShipment;
	}
	
	/**
	 *	移除发货项中的商品
	 * @param orderSkuId
	 */
	public void doRemoveItem(Serializable orderSkuId){
		Assert.notNull(orderSkuId);
		//计算发货项总额
		OrderSku orderSku = orderSkuDao.getById(new Integer(orderSkuId.toString()));
		if(orderSku==null) return;
		
		BigDecimal minusAmount = orderSku.getDiscountPrice()
									.multiply(BigDecimal.valueOf(orderSku.getQuantity()))
									.add(orderSku.getItemDiscountAmount().negate());
		OrderShipment orderShipment = orderSku.getOrderShipment();
		orderShipment.setItemSubTotal(orderShipment.getItemSubTotal().add(minusAmount.negate()));
		orderShipmentDao.save(orderShipment);
		orderShipment.delete(orderSku);
		orderSku.setOrderShipment(null);
		orderSkuDao.delete(orderSku);
		
		//状态更新
		orderShipment.updateStatusForReallocated();
		this.caculateTaxesAndShippingCost(orderShipment, false);
		orderShipmentDao.save(orderShipment);
		
		SalesOrder salesOrder = orderShipment.getSalesOrder();
		//注意顺序
		salesOrder.updateTotalAmount();
		salesOrder.updatePaymentStatus();
		this.save(salesOrder);
		//更新库存
		//无库存销售的不需处理
		if(orderSku.getProductSku().getProduct().getAvailabilityRule().intValue()!=CatalogConstants.PRODUCT_AVAILABILITY_NOT_IN_STOCK_SELL.intValue()){
			inventoryService.doCancelAllocate(salesOrder, orderSku.getProductSku(), orderSku.getAllocatedQuantity());
			//[库存分配触发调整]
			//释放预订库存
			int preOrderQty = orderSku.getAllocatedPreQty();
			if(preOrderQty>0){
				inventoryService.doCancelAllocatePreOrBackOrderedQty(orderSku.getProductSku(), preOrderQty);
				orderSku.setAllocatedPreQty(0);
			}
		}
		
		
		AppUser appUser = (AppUser) RequestContext.getCurrentUser();
		orderAuditHelper.logRemoveItem(salesOrder, orderShipment, orderSku.getProductSku(), appUser);
	}
	
	/**
	 * 管理员添加备注
	 * @param salesOrderId
	 * @param detail
	 */
	public void addNote(Serializable salesOrderId, String detail){
		this.addOrderAudit(OrderConstants.AUDIT_TANSACTION_TYPE_ADMIN, salesOrderId, detail);
	}
	
	/**
	 * 编辑备注
	 * @param orderAuditId
	 * @param detail
	 */
	public void editNote(Serializable orderAuditId, String detail){
		Assert.notNull(orderAuditId);
		Assert.notNull(detail);
		
		OrderAudit orderAudit = this.orderAuditDao.getById(new Integer(orderAuditId.toString()));
		if(orderAudit.getTransactionType().equals(OrderConstants.AUDIT_TANSACTION_TYPE_ADMIN)){
			orderAudit.setDetail(detail);
			orderAuditDao.save(orderAudit);
		}
		
	}
	
	/**
	 * 保存订单所有可编辑的信息
	 * @param params 页面表单参数
	 * @param out_exceptionMsg 业务异常信息
	 * @return 1 成功
	 * 		  -1 异常
	 *        -2 某商品可销售(包括预订)量不能满足新增订购数量
	 * @throws OutOfStockException 
	 */
	public int saveAll(Map params, StringBuilder out_exceptionMsg) throws OutOfStockException{
		String salesOrderId = OrderUtil.getParameter(params, "salesOrderId");
		if(StringUtils.isEmpty(salesOrderId))
			return -1;
		
		SalesOrder salesOrder = this.getById(new Integer(salesOrderId.toString()));
		Iterator<OrderShipment> orderShipments = salesOrder.getOrderShipments().iterator();
		while(orderShipments.hasNext()){
			OrderShipment orderShipment = orderShipments.next();
			//不可编辑
			if(orderShipment.getStatus().shortValue()>20)
				continue;
			Integer orderShipmentId = orderShipment.getOrderShipmentId();
			
			String shippingAddressId = OrderUtil.getParameter(params, "shippingAddressId" + orderShipmentId);
			//修改运输地址
			if(shippingAddressId!=null && !"0".equals(shippingAddressId)){
				Address address = addressManager.getById(new Integer(shippingAddressId));
				OrderAddress shippingAddress = orderShipment.getOrderAddress();
				shippingAddress.setCountry(address.getCountryName());
				shippingAddress.setState(address.getStateName());
				shippingAddress.setCity(address.getCityName());
				shippingAddress.setAddress1(address.getAddress());
				shippingAddress.setAddress2(address.getAddress2());
			}
			//运输方式
			String shippingRateId = OrderUtil.getParameter(params, "shippingRateId" + orderShipmentId);
			if(shippingRateId!=null){
				ShippingRate shippingRate = checkoutService.getShippingRateById(new Integer(shippingRateId));
				orderShipment.setShippingRate(shippingRate);
				orderShipment.setCarrierName(shippingRate.getShippingMethod().getShippingMethodName());
			}
			
			//包装
			String wrapId = OrderUtil.getParameter(params, "wrapId" + orderShipmentId);
			float wrapUnitPrice = 0;
			if(!StringUtils.isEmpty(wrapId)){
				//未更改包装项
				if("0".equals(wrapId)){
					wrapUnitPrice = orderShipment.getWrapUnitPrice()==null?0:orderShipment.getWrapUnitPrice().floatValue();
				}else{
					//不需要包装
					if("-1".equals(wrapId)){
						wrapUnitPrice = 0;
						orderShipment.setWrapName(null);
						orderShipment.setWrapUnitPrice(new BigDecimal(0));
					}else{
						Wrap wrap = wrapManager.getById(new Integer(wrapId));
						orderShipment.setWrapName(wrap.getWrapName());
						orderShipment.setWrapUnitPrice(wrap.getDefaultPrice());
						
						wrapUnitPrice = wrap.getDefaultPrice().floatValue();
					}
				}
			}
			
			Iterator<OrderSku> i = orderShipment.getOrderSkus().iterator();
			float itemSubTotal = 0;
			while(i.hasNext()){
				OrderSku orderSku = i.next();
				Integer orderSkuId = orderSku.getOrderSkuId();
				//获取页面参数值
				String quantity = OrderUtil.getParameter(params, "quantity" + orderShipmentId + orderSkuId);
				String discountPrice = OrderUtil.getParameter(params, "discountPrice" + orderShipmentId + orderSkuId);
				String itemDiscountAmount = OrderUtil.getParameter(params, "itemDiscountAmount" + orderShipmentId + orderSkuId);
				if(quantity==null || discountPrice==null || itemDiscountAmount==null) continue;
				
				Integer quantityInteger = new Integer(quantity);
				
				//款项重新汇总
				itemSubTotal += quantityInteger * Float.parseFloat(discountPrice) - Float.parseFloat(itemDiscountAmount);
				
				orderSku.setOrigQty(orderSku.getQuantity());
				orderSku.setQuantity(quantityInteger);
				orderSku.setDiscountPrice(new BigDecimal(discountPrice).setScale(2, BigDecimal.ROUND_HALF_UP));
				orderSku.setItemDiscountAmount(new BigDecimal(itemDiscountAmount).setScale(2, BigDecimal.ROUND_HALF_UP));
				this.save(orderSku);
			}
			String discountAmount = OrderUtil.getParameter(params, "discountAmount" + orderShipmentId);	
			orderShipment.setItemSubTotalWithFloat(itemSubTotal);
			orderShipment.setDiscountAmount(new BigDecimal(discountAmount).setScale(2, BigDecimal.ROUND_HALF_UP));
			orderShipment.setStatus(OrderConstants.SHIPMENT_STATUS_AWAITING_INVENTORY);
			//税费、运输费
			this.caculateTaxesAndShippingCost(orderShipment, false);
			this.save(orderShipment);
		}

		//注意顺序
		salesOrder.updateTotalAmount();
		salesOrder.updatePaymentStatus();
		
		this.save(salesOrder);
		
		
		adjustAllocateQty(salesOrder);
		
		return 1;
	}
	
	private void adjustAllocateQty(SalesOrder salesOrder)throws OutOfStockException{
		//调整计算出价格得到支付状态后再调整分配库存[库存分配触发调整]
		if(salesOrder.getPaymentStatus().intValue()==OrderConstants.PAYMENT_STATUS_PAID){
			Iterator<OrderShipment> orderShipments = salesOrder.getOrderShipments().iterator();
			while(orderShipments.hasNext()){
				OrderShipment orderShipment = orderShipments.next();
				//不可编辑
				if(orderShipment.getStatus().shortValue()>20)
					continue;
				Integer orderShipmentId = orderShipment.getOrderShipmentId();
				Iterator<OrderSku> i = orderShipment.getOrderSkus().iterator();
				boolean hasOneAwaiting = false;
				while(i.hasNext()){
					OrderSku orderSku = i.next();
					//无库存销售不需分配库存
					if(orderSku.getProductSku().getProduct().getAvailabilityRule().intValue()==CatalogConstants.PRODUCT_AVAILABILITY_NOT_IN_STOCK_SELL.intValue()){
						if(!hasOneAwaiting && orderSku.getQuantity()-orderSku.getAllocatedQuantity()>0)
							hasOneAwaiting=true;
						continue;
					}
					//获取页面参数值
					Integer quantityInteger = orderSku.getQuantity();
					//数量发生改变时分配库存
					int increaseQty = quantityInteger-orderSku.getOrigQty();
					if(increaseQty>0){
						//当为需要增加库存分配的订单状态必须为全额支付完毕
						if(salesOrder.getPaymentStatus().intValue()==OrderConstants.PAYMENT_STATUS_PAID){
							Integer[] allocatedQuantityFromInventory;
							try {
								inventoryService.doCancelAllocatePreOrBackOrderedQty(orderSku.getProductSku(), orderSku.getAllocatedPreQty());
								allocatedQuantityFromInventory = inventoryService.doAllocateProductSku(salesOrder, orderSku.getProductSku(), increaseQty);
							} catch (OutOfStockException e) {
//								out_exceptionMsg.append(this.getMessage("order.shipment.tip.notEnough", orderSku.getProductSku().getProductSkuCode(),increaseQty, e.getCanAllocateAvailableQty()+e.getCanAllocatePreOrBackOrderQty()));
								throw e;
							}
							orderSku.setAllocatedQuantity(allocatedQuantityFromInventory[0]+orderSku.getAllocatedQuantity());
							orderSku.setAllocatedPreQty(allocatedQuantityFromInventory[1]);
						}
					}else if(increaseQty<0){
						//如果已分配的库存比当前订购数量要大，则退回多出的分配数量
						//存在的缺货数量
						Integer outOfStockQty;
						Short productAvailabilityRule=orderSku.getProductSku().getProduct().getAvailabilityRule();
						if(productAvailabilityRule.intValue()==CatalogConstants.PRODUCT_AVAILABILITY_BACK_ORDER||productAvailabilityRule.intValue()==CatalogConstants.PRODUCT_AVAILABILITY_PRE_ORDER){
							outOfStockQty=orderSku.getOrigQty()-orderSku.getAllocatedQuantity()-orderSku.getAllocatedPreQty();
						}else{
							outOfStockQty=orderSku.getOrigQty()-orderSku.getAllocatedQuantity();
						}
						//calcelqty本次取消数量
						Integer cancelQty=orderSku.getOrigQty()-quantityInteger;
						cancelQty=cancelQty-outOfStockQty;
						if(cancelQty>0){
							Integer cancelEntityQty=cancelQty-orderSku.getAllocatedPreQty();
							/*Integer cancelPreQty=cancelQty-cancelEntityQty;
							
							//取消已分配的预订数量
							if(cancelPreQty>0){
								orderSku.setAllocatedPreQty(orderSku.getAllocatedPreQty()-cancelPreQty);
								this.save(orderSku);
								inventoryService.doCancelAllocatePreOrBackOrderedQty(orderSku.getProductSku(), cancelPreQty);
							}*/
							
							//取消已分配的实体数量
							if(cancelEntityQty>0){
								orderSku.setAllocatedQuantity(orderSku.getAllocatedQuantity()-cancelQty);
								this.save(orderSku);
								inventoryService.doCancelAllocate(salesOrder, orderSku.getProductSku(), cancelQty);
							}
						}
					}
					//检查是否需要分配库存
					int waitAllocatedQty=orderSku.getQuantity()-orderSku.getAllocatedQuantity();
					if(waitAllocatedQty>0){
						Integer[] allocatedQuantityFromInventory;
						try {
							inventoryService.doCancelAllocatePreOrBackOrderedQty(orderSku.getProductSku(), orderSku.getAllocatedPreQty());
							allocatedQuantityFromInventory = inventoryService.doAllocateProductSku(salesOrder, orderSku.getProductSku(), waitAllocatedQty);
						} catch (OutOfStockException e) {
//							out_exceptionMsg.append(this.getMessage("order.shipment.tip.notEnough", orderSku.getProductSku().getProductSkuCode(),increaseQty, e.getCanAllocateAvailableQty()+e.getCanAllocatePreOrBackOrderQty()));
							throw e;
						}
						orderSku.setAllocatedQuantity(allocatedQuantityFromInventory[0]+orderSku.getAllocatedQuantity());
						orderSku.setAllocatedPreQty(allocatedQuantityFromInventory[1]);
					}
					if(!hasOneAwaiting && quantityInteger-orderSku.getAllocatedQuantity()>0)
						hasOneAwaiting=true;
					
					this.save(orderSku);
				}
				if(hasOneAwaiting)
					orderShipment.setStatus(OrderConstants.SHIPMENT_STATUS_AWAITING_INVENTORY);
				else
					orderShipment.setStatus(OrderConstants.SHIPMENT_STATUS_INVENTORY_ASSIGNED);
//				throw new  RuntimeException("test"); 
			}
		}
	}
	
	private String getMessage(String key, Object... params){
		return ContextUtil.getSpringContext().getMessage(key, params, null);
	}
	
	/**
	 * 添加备注审计
	 * @param type
	 * @param salesOrderId
	 * @param detail
	 */
	private void addOrderAudit(String type, Serializable salesOrderId, String detail){
		Assert.notNull(salesOrderId);
		Assert.notNull(detail);
		
		AppUser appUser = (AppUser) RequestContext.getCurrentUser();
		OrderAudit orderAudit = new OrderAudit();
		orderAudit.setAddedBy(appUser.getUsername());
		orderAudit.setCreateTime(new Date());
		orderAudit.setDetail(detail);
		orderAudit.setSalesOrderId(new Integer(salesOrderId.toString()));
		orderAudit.setTransactionType(type);
		orderAuditDao.save(orderAudit);
	}
	
	public void unlock(SalesOrder salesOrder){
		salesOrder.setIsLocked(Constants.FLAG_FALSE);
		salesOrder.setLockedBy(null);
		this.save(salesOrder);
	}
	
	
	private OrderShipment clone(OrderShipment from, OrderSku newOrderSku){
		OrderShipment orderShipment = new OrderShipment(); 
		orderShipment.setCarrierName(from.getCarrierName());
		orderShipment.setShippingRateId(from.getShippingRateId());
		AppUser appUser = (AppUser) RequestContext.getCurrentUser();
		orderShipment.setCreateBy(appUser.getAppuserId());
		orderShipment.setCreateTime(new Date());
		orderShipment.setDiscountAmount(new BigDecimal(0));
		
		int quantity = newOrderSku.getQuantity();
		float discountPrice = newOrderSku.getDiscountPrice().floatValue();
		float itemDiscountAmount = newOrderSku.getItemDiscountAmount().floatValue();
		
		orderShipment.setItemSubTotalWithFloat(discountPrice*quantity - itemDiscountAmount);
		orderShipment.setShipmentNo(orderShipmentDao.getNextShipmentNo(from.getSalesOrderId()));
		
		orderShipment.setStatus(newOrderSku.getAllocatedQuantity()-newOrderSku.getQuantity()==0?OrderConstants.SHIPMENT_STATUS_INVENTORY_ASSIGNED
				:OrderConstants.SHIPMENT_STATUS_AWAITING_INVENTORY);
		//运费不要Clone.
		orderShipment.setShippingCost(BigDecimal.ZERO);		
		orderShipment.setItemTax(BigDecimal.ZERO);
		orderShipment.setShippingTax(new BigDecimal(0));
		orderShipment.setHasRobotReviewed(Constants.FLAG_FALSE);
		orderShipment.setUpdateTime(orderShipment.getCreateTime());
		orderShipment.setSalesOrderId(from.getSalesOrderId());
		return orderShipment;
	}
	
		
	/**
	 * 锁住订单，如果已被锁，则直接返回
	 * @param orderId
	 * @param userId
	 * @return
	 */
	private synchronized SalesOrder lockSalesOrder(Integer orderId, Integer userId){
		SalesOrder so = this.getById(orderId);
		if(so!=null && so.getIsOnHold().shortValue()!=Constants.FLAG_TRUE.shortValue() && (so.getIsLocked()==null || so.getIsLocked().byteValue()==0) 
				&& (so.getIsOnHold()!=null && so.getIsOnHold().shortValue()!=Constants.FLAG_TRUE.shortValue())){
			so.setIsLocked(Constants.FLAG_TRUE);
			so.setLockedBy(userId);
			this.save(so);
		}
		return so;
	}
	
	/**
	 * 设置加锁权限及加锁相关信息
	 * @param salesOrder
	 * @param cuurrentUser
	 */
	private void setLockAttr(SalesOrder salesOrder, AppUser cuurrentUser){
		if( salesOrder.getIsLocked().byteValue()==Constants.FLAG_TRUE.byteValue() ){
			if(cuurrentUser.getAppuserId().intValue()==salesOrder.getLockedBy().intValue()){
				salesOrder.setLockedByMyself(true);
			}
			salesOrder.setLockedByUserName(appUserManager.getById( salesOrder.getLockedBy()).getUsername());
		}
	}
	
	public long countCustomerOrder(Serializable userId){
		Assert.notNull(userId);
		return salesOrderDao.countCustomerOrder(new Integer(userId.toString()), false);
	}
	
	
	/**
	 * 获取最近的支付记录
	 * @param salesOrderId
	 * @return
	 */
	public OrderPayment getLatestOrderPayment(Serializable salesOrderId){
		Assert.notNull(salesOrderId);
		return orderPaymentDao.getLatestOrderPayment(new Integer(salesOrderId.toString()));
	}
	
	/**
	 * 搜索提示功能，返回指定前缀的前N条记录
	 * @param prefix
	 * @param pageSize
	 * @return
	 */
	public List<String> autoCompleteShipmentNo(String prefix, Integer pageSize){
		return orderShipmentDao.autoCompleteShipmentNo(prefix, pageSize);
	}
	
	/**
	 * 获取指定商品的订单SKU
	 * @param productSkuId
	 * @return
	 */
	public List<OrderSku> getOrderSkuAwaitingInventoryByProductSkuId(Integer productSkuId){
		return orderSkuDao.getOrderSkuAwaitingInventoryByProductSkuId(productSkuId);
	}
	
	public String getMaxOrderNo(){
		return salesOrderDao.getMaxOrderNo();
	}
	
	public void sendNotificationEmail(Short mailType,SalesOrder salesOrder){
		if(logger.isDebugEnabled()){
			logger.debug("==============send the email to notice the customer=============");
		}
		
		String from=ConfigUtil.getInstance().getStore(salesOrder.getStore().getCode()).getEmailSender();
		Map<String, Object> map=new HashMap<String, Object>(); 
		map.put("storeFrontSiteUrl", ConfigUtil.getInstance().getStore().getSiteUrl());
		map.put("salesOrder", salesOrder);
		//需要判断线上还是线下支付
		PaymentMethod paymentMethod = null;
		if (salesOrder.getPaymentMethodId() != null) //默认使用paypal
		{
			paymentMethod = paymentMethodService.getPaymentById(salesOrder.getPaymentMethodId());			
		}
		else
		{
			paymentMethod = paymentMethodService.getPaymentMethodByCode("paypal");
		}
		map.put("paymentMethod", paymentMethod);
		
		String templateName="";
		
		switch(mailType){
			case OrderConstants.MAIL_TYPE_PLACE_ORDER:
				templateName="placeOrder.vm";
				break;
		/*	case OrderConstants.MAIL_TYPE_SHIPPING:
				templateName="shipping.vm";
				break;*/
			case OrderConstants.MAIL_TYPE_RECONFIRM:
				templateName="reConfirmOrder.vm";
				break;	
		}
		try{
			mailEngine.sendSimpleTemplateMail("/order/"+templateName, map, null, from, new String[]{salesOrder.getCustomerEmail()});
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void sendShipmentNotificationEmail(SalesOrder salesOrder,OrderShipment orderShipment) {
		if(logger.isDebugEnabled()){
			logger.debug("==============send the email to shipment notice the customer=============");
		}
		
		String from = ConfigUtil.getInstance().getStore(salesOrder.getStore().getCode()).getEmailSender();
		
		Map<String, Object> map=new HashMap<String, Object>(); 
		map.put("storeFrontSiteUrl", ConfigUtil.getInstance().getStore().getSiteUrl());
		map.put("salesOrder", salesOrder);
		map.put("orderShipment", orderShipment);
		
		try{
			mailEngine.sendSimpleTemplateMail("/order/shipping.vm", map, null, from, new String[]{salesOrder.getCustomerEmail()});
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	
	/**
	 * 计算发货项税费与运输费，当前只计算商品税与运输费
	 * @param orderShipment
	 * @param evict 供controller调用，仅起计算作用
	 */
	public void caculateTaxesAndShippingCost(OrderShipment orderShipment, boolean evict){
		if(evict)
			salesOrderDao.clearAll();
		if (!ConfigUtil.getInstance().getIsRecalculateShippingAndTaxAfterModified())
		{
			return;
		}
		List<Integer> regionIdList = new ArrayList<Integer>(3);
		OrderAddress oa = orderShipment.getOrderAddress();
		if(oa==null) return;
		//根据发货地址获取ID
		Integer countryId=null,stateId=null,cityId = null;
		Region[] regions = regionManager.findMatchRegions(oa.getCountry(), oa.getState(), oa.getCity());
		if(regions!=null && regions.length>0){
			countryId = regions[0].getRegionId();
			regionIdList.add(countryId);
			
			if(regions.length>1&&regions[1]!=null){
				stateId = regions[1].getRegionId();
				regionIdList.add(stateId);
			}
			if(regions.length>2&&regions[2]!=null){
				cityId = regions[2].getRegionId();
				regionIdList.add(cityId);
			}
		}
		if(regionIdList.size()==0) {
			if(logger.isDebugEnabled())
				logger.debug("there should be one not null value of countryId,stated,cityId!");
			return;
		}

		HashMap<Integer,List<TaxRate>> productTaxRateMap = new HashMap<Integer,List<TaxRate>>();
		BigDecimal taxTotal = BigDecimal.ZERO;
		BigDecimal weight = BigDecimal.ZERO;
		Integer itemCount = 0;
		for (Iterator iterator = orderShipment.getOrderSkus().iterator(); iterator.hasNext();) {
			OrderSku orderSku = (OrderSku) iterator.next();
			caculateTaxes(orderSku, regionIdList, productTaxRateMap);
			if(orderSku.getTaxName()!=null && logger.isInfoEnabled())
				logger.info(orderShipment.getShipmentNo() + " has been applied tax:" + orderSku.getTaxName() + "; Tax total:" +orderSku.getTax().toPlainString());
			taxTotal = taxTotal.add(orderSku.getTax());
			if(!orderSku.getItemType().equals(Constants.ITEM_TYPE_PRODUCT))
				continue;
			itemCount += orderSku.getQuantity();
			BigDecimal skuWeight = orderSku.getWeight();
			if(skuWeight!=null)
				weight = weight.add(skuWeight.multiply(BigDecimal.valueOf(orderSku.getQuantity())));
		}
		orderShipment.setItemTax(taxTotal.setScale(2, BigDecimal.ROUND_HALF_UP));
		
		//计算运输费
		if(orderShipment.getShippingRateId()!=null)
			orderShipment.setShippingCost(checkoutService.getShippingExpence(orderShipment.getShippingRateId(), weight, itemCount));
	}
	
	
	/**
	 * 计算单个商品的税费
	 * @param orderSku
	 * @param regionIdList
	 * @param productTaxRateMap
	 */
	public void caculateTaxes(OrderSku orderSku, List<Integer> regionIdList, HashMap<Integer,List<TaxRate>> productTaxRateMap){
		BigDecimal tax = new BigDecimal(0);
		Integer productTypeId=orderSku.getProductSku().getProduct().getProductTypeId();
		if(productTypeId==null)
			return;
		if(productTaxRateMap==null)
			productTaxRateMap = new HashMap<Integer,List<TaxRate>>();
		List<TaxRate> taxRateList=productTaxRateMap.get(productTypeId);
		if(taxRateList==null){
			taxRateList=taxRateManager.findByRegionIds(regionIdList, productTypeId);
			productTaxRateMap.put(productTypeId, taxRateList);
		}
		StringBuilder taxName =new StringBuilder();
		if(taxRateList.size()>0){
			BigDecimal itemTotal = orderSku.getDiscountPrice()
									.multiply(BigDecimal.valueOf(orderSku.getQuantity()))
									.add(orderSku.getItemDiscountAmount().negate());
			for (TaxRate taxRate : taxRateList) {
				tax = tax.add(itemTotal.multiply(taxRate.getRateValue()).divide(BigDecimal.valueOf(100)));
				taxName.append(taxRate.getTax().getTaxName()).append(",");//多个税率间用逗号分隔
			}
			taxName.deleteCharAt(taxName.length()-1);
		}
		orderSku.setTax(tax);
		orderSku.setTaxName(taxName.toString());
	}
	
	/**
	 * 获取订单，用于智能审核
	 * @param sc
	 * @return
	 */
	public List<OrderShipment> getOrderShipment4RobotReview(SearchCriteria sc){
		return orderShipmentDao.getOrderShipment4RobotReview(sc);
	}
	
	/**
	 * 是否有类同的发货项
	 * @param salesOrder
	 * @param orderShipment
	 * @param shippingAddress
	 * @return
	 */
	//public boolean hasSimilarOrderShipment(SalesOrder salesOrder, OrderShipment orderShipment, OrderAddress shippingAddress){
	//	return orderShipmentDao.hasSimilarOrderShipment(salesOrder, orderShipment, shippingAddress);
	//}
	
	public SalesOrder getSalesOrderByOrderNo(String orderNo){
		return this.salesOrderDao.findUniqueByProperty("orderNo", orderNo);
	}
	
	/**
	 * 列出指定会员的所有订单
	 * @param userId 前台会员ID
	 * @return
	 */
	public List<SalesOrder> getSalesOrderByUserId(Serializable userId){
		Assert.notNull(userId);
		Integer customerId = new Integer(userId.toString());
		return this.salesOrderDao.findByPropertyOrdered("customer.appuserId", customerId, "salesOrderId", false);
	}

	public List<SalesOrder> getSalesOrder(List<String> orderNos) {
		return salesOrderDao.getSalesOrder(orderNos);
	}


	public OrderShipment updateTrackingNoAndCostPaid(Integer orderShipmentId,String trackingNo,BigDecimal shippingCostPaid) throws Exception{
		OrderShipment orderShipment = getOrderShipmentById(orderShipmentId);
		String trackingNo_old=orderShipment.getTrackingNo();
		BigDecimal shippingCostPaid_old=orderShipment.getShippingCostPaid();
		if (shippingCostPaid_old == null)
		{
			shippingCostPaid_old = BigDecimal.ZERO;
		}
		orderShipment.setTrackingNo(trackingNo);
		orderShipment.setShippingCostPaid(shippingCostPaid);
		save(orderShipment);
		if((!orderShipment.getTrackingNo().equals(trackingNo_old))||(orderShipment.getShippingCostPaid().compareTo(shippingCostPaid_old)!=0)){
			//审计
			orderAuditHelper.logUpdateTrackingNoAndCostPaid(orderShipment.getSalesOrder(), orderShipment, trackingNo_old, shippingCostPaid_old, (AppUser) RequestContext.getCurrentUser());
		}
		return orderShipment;
	}
	
	public void doAllocateQty4Order(SalesOrder salesOrder) throws OutOfStockException{
		Iterator<OrderShipment> orderShipments = salesOrder.getOrderShipments().iterator();
		while(orderShipments.hasNext()){
			OrderShipment orderShipment = orderShipments.next();
			Iterator<OrderSku> i = orderShipment.getOrderSkus().iterator();
			boolean hasOneAwaiting = false;
			while(i.hasNext()){
				OrderSku orderSku = i.next();
				if(orderSku.getProductSku().getProduct().getAvailabilityRule().intValue()==CatalogConstants.PRODUCT_AVAILABILITY_NOT_IN_STOCK_SELL.intValue()){
//					if(!hasOneAwaiting && orderSku.getQuantity()-orderSku.getAllocatedQuantity()>0)
//						hasOneAwaiting=true;
					if(orderSku.getQuantity()-orderSku.getAllocatedQuantity()>0){
						supplierService.createPurchaseOrderItem(orderSku);
						hasOneAwaiting=true;
					}
					continue;
				}else{
					//数量发生改变时分配库存
					int increaseQty = orderSku.getQuantity()-orderSku.getAllocatedQuantity();
					if(increaseQty>0){
						if(salesOrder.getPaymentStatus().intValue()==OrderConstants.PAYMENT_STATUS_PAID){
							Integer[] allocatedQuantityFromInventory;
							try {
								inventoryService.doCancelAllocatePreOrBackOrderedQty(orderSku.getProductSku(), orderSku.getAllocatedPreQty());
								allocatedQuantityFromInventory = inventoryService.doAllocateProductSku(salesOrder, orderSku.getProductSku(), increaseQty);
							} catch (OutOfStockException e) {
//								out_exceptionMsg.append(this.getMessage("order.shipment.tip.notEnough", orderSku.getProductSku().getProductSkuCode(),increaseQty, e.getCanAllocateAvailableQty()+e.getCanAllocatePreOrBackOrderQty()));
								throw e;
							}
							orderSku.setAllocatedQuantity(allocatedQuantityFromInventory[0]+orderSku.getAllocatedQuantity());
							orderSku.setAllocatedPreQty(allocatedQuantityFromInventory[1]);
						}
					}else if(increaseQty<0){
						throw new RuntimeException("支付时触发的库存调整，正常情况下只增加分配。（减少库存分配的是调整时立刻执行）");
					}
					if(!hasOneAwaiting && (orderSku.getQuantity()-orderSku.getAllocatedQuantity())>0)
						hasOneAwaiting=true;
					save(orderSku);
				}
			}
			if(hasOneAwaiting)
				orderShipment.setStatus(OrderConstants.SHIPMENT_STATUS_AWAITING_INVENTORY);
			else
				if(!salesOrder.isCod()){
					orderShipment.setStatus(OrderConstants.SHIPMENT_STATUS_INVENTORY_ASSIGNED);
				}else{
					orderShipment.setStatus(OrderConstants.SHIPMENT_STATUS_SHIPPED);
				}
//				orderShipment.setStatus(OrderConstants.SHIPMENT_STATUS_INVENTORY_ASSIGNED);
		}
	}

	public void cancelExpireSalesOrder() {
		Integer expireDate=ConfigUtil.getInstance().getOrderCancelDays();
		boolean flag = ConfigUtil.getInstance().getOrderCancelIncludeCod();
		expireDate=Math.abs(expireDate);
		List<SalesOrder>salesOrderList=salesOrderDao.findExpiredSalesOrder(expireDate);
		for (SalesOrder salesOrder : salesOrderList) {
			if(!flag && salesOrder.isCod())
				continue;
			salesOrder.setOrderStatus(OrderConstants.STATUS_CANCELLED);
			save(salesOrder);
			orderAuditHelper.logCancelOrderByRobot(salesOrder, expireDate);
		}
	}

	@Override
	public List<SalesOrder> getSalesOrder(Integer userId, Serializable paymentStatus, Date begin, Date end) {
		// TODO Auto-generated method stub
		return this.salesOrderDao.getSalesOrder(userId, paymentStatus, begin, end);
	}
	
}
