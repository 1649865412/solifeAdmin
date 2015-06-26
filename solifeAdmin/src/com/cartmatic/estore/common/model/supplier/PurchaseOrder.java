package com.cartmatic.estore.common.model.supplier;

import com.cartmatic.estore.common.model.supplier.base.PurchaseOrderTbl;

/**
 * Model class for PurchaseOrder. Add not database mapped fileds in this class.
 */
public class PurchaseOrder extends PurchaseOrderTbl {

  	/**
	 * Default Empty Constructor for class PurchaseOrder
	 */
	public PurchaseOrder () {
		super();
	}
	
	/**
	 * 定义实体的业务名取值； purchaseOrderName
	 * 必须手工完成这个部分，否则编译不通过。
	 */
	public String getPurchaseOrderName () {
		if (purchaseOrderId == null)
	        return "";
	    else
			//返回一个指定有业务意义的属性值;
			//如：product的VO就用product.productName
	        return this.purchaseOrderNo;
	}
	
	/**
	 * Default Key Fields Constructor for class PurchaseOrder
	 */
	public PurchaseOrder (
		 Integer in_purchaseOrderId
		) {
		super (
		  in_purchaseOrderId
		);
	}

}
