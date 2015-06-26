package com.cartmatic.estore.common.model.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.common.model.order.base.OrderSkuTbl;
import com.cartmatic.estore.common.model.supplier.PurchaseOrderItem;
import com.cartmatic.estore.order.OrderConstants;
import com.cartmatic.estore.supplier.SupplierConstants;

/**
 * Model class for OrderSku. Add not database mapped fileds in this class.
 */
public class OrderSku extends OrderSkuTbl {
	
   private BigDecimal total;
   
   /**
	 * 原来购物的数量（辅助处理）
	 */
	private Integer origQty;

  	public Integer getOrigQty() {
  		return origQty;
	}
	
	public void setOrigQty(Integer origQty) {
		this.origQty = origQty;
	}

	public BigDecimal getTotal() {
	return total;
  	}

  	public void setTotal(BigDecimal total) {
	this.total = total;
  	}

	/**
	 * Default Empty Constructor for class OrderSku
	 */
	public OrderSku () {
		super();
	}
	
	/**
	 * 定义实体的业务名取值； orderSkuName
	 * 必须手工完成这个部分，否则编译不通过。
	 */
	public String getOrderSkuName () {
		if (orderSkuId == null)
	        return "";
	    else
			//返回一个指定有业务意义的属性值;
			//如：product的VO就用product.productName
	        return this.orderSkuId.toString();
	}
	
	/**
	 * Default Key Fields Constructor for class OrderSku
	 */
	public OrderSku (
		 Integer in_orderSkuId
		) {
		super (
		  in_orderSkuId
		);
	}
	
	private Integer returnableQuantity = 0;

	public Integer getReturnableQuantity() {
		return returnableQuantity;
	}

	public void setReturnableQuantity(Integer returnableQuantity) {
		this.returnableQuantity = returnableQuantity;
	}
	
	/** 要退回的数量*/
	private String returnQuantity;

	public String getReturnQuantity() {
		return returnQuantity;
	}

	public void setReturnQuantity(String returnQuantity) {
		this.returnQuantity = returnQuantity;
	}
	
	public OrderSku clone(Integer quantity, Integer allocatedQuantity4New){
		OrderSku orderSku = new OrderSku();
		orderSku.setAllocatedQuantity(allocatedQuantity4New>0?allocatedQuantity4New:0);
		orderSku.setPrice(this.getPrice());
		orderSku.setDiscountPrice(this.getDiscountPrice());
		orderSku.setItemDiscountAmount(new BigDecimal(0));
		orderSku.setItemType(this.getItemType());
		orderSku.setProductName(this.getProductName());
		orderSku.setProductSku(this.getProductSku());
		orderSku.setProductId(this.getProductId());
		orderSku.setProductSkuCode(this.getProductSkuCode());
		orderSku.setQuantity(quantity);
		orderSku.setIsOnSale(this.getIsOnSale());
		orderSku.setIsWholesale(this.getIsWholesale());
		orderSku.setDisplaySkuOptions(this.getDisplaySkuOptions());
		return orderSku;
	}
	
	/**
	 * 商品项（orderSku）金额小计
	 * @return
	 */
	public BigDecimal getSubTotalAmount(){
		return this.getDiscountPrice().multiply(BigDecimal.valueOf(this.getQuantity())).subtract(this.getItemDiscountAmount().negate());
	}
	
	public List<String> getOptions(){
		List<String> opts = new ArrayList<String>();
		String[] optArray = this.getDisplaySkuOptions().split("###");
		for(String opt: optArray)
			opts.add(opt);
		return opts;
	}
	
	public boolean getIsProduct(){
		return this.getItemType().shortValue()==Constants.ITEM_TYPE_PRODUCT.shortValue();
	}
	
	/**
	 * 本sku是否能被编辑
	 * 订单处理过程中,正在采购中和备货中是不能编辑的
	 * @return
	 */
	public boolean getCanEdit()
	{
		Set<PurchaseOrderItem> pis = this.getPurchaseOrderItems();
		//有采购记录
		if (pis != null && pis.size() > 0)
		{
			for (PurchaseOrderItem pi : pis)
			{
				//采购中 (未采购完)
				if (! pi.getStatus().equals(SupplierConstants.PO_ITEM_STATUS_COMPLETED))
				{
					return false;
				}
			}
		}
		//备货中
		if (this.getOrderShipment().getStatus().equals(OrderConstants.SHIPMENT_STATUS_PICKING))
		return false;
		
		return true;
	}
	
	/**
	 * 是事没有采购记录
	 * @return
	 */
	public boolean getIsNotPurchase()
	{
		Set<PurchaseOrderItem> pis = this.getPurchaseOrderItems();
		//有采购记录
		if (pis != null && pis.size() > 0)
		{
			return false;
		}
		return true;
	}
}
