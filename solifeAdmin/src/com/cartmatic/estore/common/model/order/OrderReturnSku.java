package com.cartmatic.estore.common.model.order;

import com.cartmatic.estore.common.model.order.base.OrderReturnSkuTbl;

/**
 * Model class for OrderReturnSku. Add not database mapped fileds in this class.
 */
public class OrderReturnSku extends OrderReturnSkuTbl {

  	/**
	 * Default Empty Constructor for class OrderReturnSku
	 */
	public OrderReturnSku () {
		super();
	}
	
	/**
	 * 定义实体的业务名取值； orderReturnSkuName
	 * 必须手工完成这个部分，否则编译不通过。
	 */
	public String getOrderReturnSkuName () {
		if (orderReturnSkuId == null)
	        return "";
	    else
			//返回一个指定有业务意义的属性值;
			//如：product的VO就用product.productName
	        return this.orderReturnSkuId.toString();
	}
	
	/**
	 * Default Key Fields Constructor for class OrderReturnSku
	 */
	public OrderReturnSku (
		 Integer in_orderReturnSkuId
		) {
		super (
		  in_orderReturnSkuId
		);
	}
	/** 该退换商品当前可退换的数量*/
	private Integer returnableQuantity = 0;

	public Integer getReturnableQuantity() {
		return returnableQuantity;
	}

	public void setReturnableQuantity(Integer returnableQuantity) {
		this.returnableQuantity = returnableQuantity;
	}
}
