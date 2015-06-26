package com.cartmatic.estore.common.model.supplier;

import com.cartmatic.estore.common.model.supplier.base.PurchaseOrderItemTbl;

/**
 * Model class for PurchaseOrderItem. Add not database mapped fileds in this class.
 */
public class PurchaseOrderItem extends PurchaseOrderItemTbl {

  	/**
	 * Default Empty Constructor for class PurchaseOrderItem
	 */
	public PurchaseOrderItem () {
		super();
	}
	
	/**
	 * 定义实体的业务名取值； purchaseOrderItemName
	 * 必须手工完成这个部分，否则编译不通过。
	 */
	public String getPurchaseOrderItemName () {
		if (purchaseOrderItemId == null)
	        return "";
	    else
			//返回一个指定有业务意义的属性值;
			//如：product的VO就用product.productName
	        return this.productName;
	}
	
	/**
	 * Default Key Fields Constructor for class PurchaseOrderItem
	 */
	public PurchaseOrderItem (
		 Integer in_purchaseOrderItemId
		) {
		super (
		  in_purchaseOrderItemId
		);
	}

}
