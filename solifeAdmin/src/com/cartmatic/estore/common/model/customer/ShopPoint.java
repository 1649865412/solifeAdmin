package com.cartmatic.estore.common.model.customer;

import com.cartmatic.estore.common.model.customer.base.ShopPointTbl;

/**
 * Model class for ShopPoint. Add not database mapped fileds in this class.
 */
public class ShopPoint extends ShopPointTbl {

  	/**
	 * Default Empty Constructor for class ShopPoint
	 */
	public ShopPoint () {
		super();
	}
	
	/**
	 * 定义实体的业务名取值； shopPointName
	 * 必须手工完成这个部分，否则编译不通过。
	 */
	public String getShopPointName () {
		if (shopPointId == null)
	        return "";
	    else
			//返回一个指定有业务意义的属性值;
			//如：product的VO就用product.productName
	        return this.shopPointId.toString();
	}
	
	/**
	 * Default Key Fields Constructor for class ShopPoint
	 */
	public ShopPoint (
		 Integer in_shopPointId
		) {
		super (
		  in_shopPointId
		);
	}

}
