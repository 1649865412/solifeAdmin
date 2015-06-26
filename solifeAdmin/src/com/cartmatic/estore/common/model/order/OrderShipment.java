package com.cartmatic.estore.common.model.order;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.catalog.CatalogConstants;
import com.cartmatic.estore.common.model.customer.Address;
import com.cartmatic.estore.common.model.order.base.OrderShipmentTbl;
import com.cartmatic.estore.order.OrderConstants;
import com.cartmatic.estore.webapp.util.RequestContext;

/**
 * Model class for OrderShipment. Add not database mapped fileds in this class.
 */
public class OrderShipment extends OrderShipmentTbl {

  	/**
	 * Default Empty Constructor for class OrderShipment
	 */
	public OrderShipment () {
		super();
	}
	
	/**
	 * 定义实体的业务名取值； orderShipmentName
	 * 必须手工完成这个部分，否则编译不通过。
	 */
	public String getOrderShipmentName () {
		if (orderShipmentId == null)
	        return "";
	    else
			//返回一个指定有业务意义的属性值;
			//如：product的VO就用product.productName
	        return this.shipmentNo;
	}
	
	/**
	 * Default Key Fields Constructor for class OrderShipment
	 */
	public OrderShipment (
		 Integer in_orderShipmentId
		) {
		super (
		  in_orderShipmentId
		);
	}
	
	/**可选运输地址*/
	private List<Address> shippingAddresses = null;

	public List<Address> getShippingAddresses() {
		return shippingAddresses;
	}

	public void setShippingAddresses(List<Address> shippingAddresses) {
		this.shippingAddresses = shippingAddresses;
	}
	
	public void setItemSubTotalWithFloat(float aValue) {
		this.setItemSubTotal(new BigDecimal(aValue).setScale(2, BigDecimal.ROUND_HALF_UP));
	}
	
	protected String customerFirstname;
	protected String customerLastname;

	public String getCustomerFirstname() {
		return customerFirstname;
	}

	public void setCustomerFirstname(String customerFirstname) {
		this.customerFirstname = customerFirstname;
	}

	public String getCustomerLastname() {
		return customerLastname;
	}

	public void setCustomerLastname(String customerLastname) {
		this.customerLastname = customerLastname;
	}
	
	public void updateStatusForReallocated(){
		if(this.getStatus().shortValue()==OrderConstants.SHIPMENT_STATUS_AWAITING_INVENTORY.shortValue()||this.getStatus().shortValue()==OrderConstants.SHIPMENT_STATUS_INVENTORY_ASSIGNED.shortValue()){
			boolean hasOneAwaiting = false;
			Iterator<OrderSku> i = this.getOrderSkus().iterator();
			while(i.hasNext()){
				OrderSku sku = i.next();
				if(sku.getAllocatedQuantity()<sku.getQuantity()){
					hasOneAwaiting = true;
					break;
				}
			}
			if(hasOneAwaiting){
				this.setStatus(OrderConstants.SHIPMENT_STATUS_AWAITING_INVENTORY);
			}else{
				this.setStatus(OrderConstants.SHIPMENT_STATUS_INVENTORY_ASSIGNED);
			}
		}
	}
	
	public OrderShipment clone(){
		OrderShipment orderShipment = new OrderShipment(); 
		orderShipment.setCarrierName(this.getCarrierName());
		orderShipment.setCreateBy(RequestContext.getCurrentUserId());
		orderShipment.setCreateTime(new Date());
		orderShipment.setDiscountAmount(new BigDecimal(0));
		
		orderShipment.setItemSubTotalWithFloat(0);
		orderShipment.setShipmentNo(null);
		
		orderShipment.setStatus(OrderConstants.SHIPMENT_STATUS_AWAITING_INVENTORY);
		orderShipment.setShippingRateId(this.getShippingRateId());
		orderShipment.setShippingCost(this.getShippingCost());
		
		//税率
		orderShipment.setItemTax(new BigDecimal(0));
		orderShipment.setShippingTax(new BigDecimal(0));
		orderShipment.setHasRobotReviewed(Constants.FLAG_FALSE);
		orderShipment.setUpdateTime(orderShipment.getCreateTime());
		orderShipment.setSalesOrderId(null);
		return orderShipment;
	}
	
	public String getOrdinalNum(){
		String shipmentNo = this.getShipmentNo();
		return shipmentNo.substring(shipmentNo.indexOf("-")+1);
	}
	
	public BigDecimal getTotalBeforeTax(){
		return this.getItemSubTotal()
					.add(getShippingCost())
					.add(getDiscountAmount().negate())
					.add(getWrapTotal());
	}
	
	public BigDecimal getTotalAfterTax(){
		return getTotalBeforeTax()
				.add(getItemTax())
				.add(getShippingTax());
	}
	
	
	/**包装费总计*/
	public BigDecimal getWrapTotal(){
		if(this.getWrapUnitPrice()==null) 
			return BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
		
//		BigDecimal wrapTotal = BigDecimal.ZERO;
//		Iterator<OrderSku> i = this.getOrderSkus().iterator();
//		while(i.hasNext()){
//			OrderSku orderSku = i.next();
//			wrapTotal = wrapTotal.add( 
//					( getWrapUnitPrice().multiply( BigDecimal.valueOf(orderSku.getQuantity())) )
//							);
//		}
		
		return getWrapUnitPrice();
	}
	
	//运输方式
	private Map<String, String> shippingMethods = new HashMap<String, String>();

	public Map<String, String> getShippingMethods() {
		return shippingMethods;
	}

	public void setShippingMethods(Map<String, String> shippingMethods) {
		this.shippingMethods = shippingMethods;
	}
	
	public void addShippingMethod(String id, String name){
		if(id!=null && name!=null)
			this.shippingMethods.put(id, name);
	}
	
	public void delete(OrderSku orderSku){
		Set<OrderSku> orderSkus = this.getOrderSkus();
		for(OrderSku original: orderSkus){
			if(original.getOrderSkuId().intValue()==orderSku.getOrderSkuId().intValue()){
				orderSkus.remove(original);
				break;
			}
		}
	}
	
	public void addOrderSku(OrderSku orderSku){
		this.getOrderSkus().add(orderSku);
	}
	
	public String getOrderAddressString(){
		if(orderAddress==null) return "";
		
		return orderAddress.getCountry().trim()+orderAddress.getState().trim()
				+ (orderAddress.getCity() != null? orderAddress.getCity().trim() : "")
				+(orderAddress.getAddress1()!=null?orderAddress.getAddress1().trim():"")
				+(orderAddress.getAddress2()!=null?orderAddress.getAddress2().trim():"");
	}
	
	/**
	 * 发货项中是否有物理产品
	 * @return
	 */
	public boolean hasPhysicalSku(){
		Iterator<OrderSku> i = this.getOrderSkus().iterator();
		while(i.hasNext()){
			OrderSku orderSku = i.next();
			if(orderSku.getItemType().shortValue()==Constants.ITEM_TYPE_PRODUCT
					&& orderSku.getProductSku().getSkuKind().shortValue()==CatalogConstants.SKU_KIND_ENTITY.shortValue())
				return true;
		}
		return false;
	}
	
	/**发货项是否全是数字产品，礼券、服务、点卡、下载*/
	public boolean getIsAllDigitalItem(){
		return !this.hasPhysicalSku();
	}
	
	//================================流程操作条件
	/**
	 * 是否允许创建备货单
	 * @return true - 允许
	 * @return false - 不允许
	 */
	public boolean getCreatePickListPermission(){
		Short isOnHold = this.getSalesOrder().getIsOnHold();
		if(isOnHold!=null && isOnHold!=Constants.FLAG_TRUE.shortValue()	&& this.getStatus().shortValue()==OrderConstants.SHIPMENT_STATUS_PICKING_AVAILABLE.shortValue())
			return true;
		
		return false;
	}
	
	public BigDecimal getTotal()
	{
		return this.getItemSubTotal().add(this.getShippingCost()).add(this.getWrapTotal()).add(this.getItemTax()).subtract(this.getDiscountAmount());
	}
	
}
