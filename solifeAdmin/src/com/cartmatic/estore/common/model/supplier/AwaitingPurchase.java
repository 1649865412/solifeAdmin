package com.cartmatic.estore.common.model.supplier;

import com.cartmatic.estore.common.model.supplier.base.AwaitingPurchaseTbl;

/**
 * Model class for AwaitingPurchase. Add not database mapped fileds in this class.
 */
public class AwaitingPurchase extends AwaitingPurchaseTbl {

  	/**
	 * Default Empty Constructor for class AwaitingPurchase
	 */
	public AwaitingPurchase () {
		super();
	}
	
	/**
	 * 定义实体的业务名取值； awaitingPurchaseName
	 * 必须手工完成这个部分，否则编译不通过。
	 */
	public String getAwaitingPurchaseName () {
		if (supplier == null)
	        return "";
	    else
			//返回一个指定有业务意义的属性值;
			//如：product的VO就用product.productName
	        return this.supplier.getSupplierName();
	}
	
	/**
	 * Default Key Fields Constructor for class AwaitingPurchase
	 */
	public AwaitingPurchase (
		 Integer in_supplierId
		) {
		super (
		  in_supplierId
		);
	}

}
