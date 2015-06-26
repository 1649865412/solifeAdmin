package com.cartmatic.estore.common.model.catalog;

import com.cartmatic.estore.common.model.catalog.base.ProductRateItemTbl;

/**
 * Model class for ProductRateItem. Add not database mapped fileds in this class.
 */
public class ProductRateItem extends ProductRateItemTbl {

  	/**
	 * Default Empty Constructor for class ProductRateItem
	 */
	public ProductRateItem () {
		super();
	}
	
	/**
	 * 定义实体的业务名取值； productRateItemName
	 * 必须手工完成这个部分，否则编译不通过。
	 */
	public String getProductRateItemName () {
		if (productRateItemId == null)
	        return "";
	    else
			//返回一个指定有业务意义的属性值;
			//如：product的VO就用product.productName
	        return this.productRateItemId.toString();
	}
	
	/**
	 * Default Key Fields Constructor for class ProductRateItem
	 */
	public ProductRateItem (
		 Integer in_productRateItemId
		) {
		super (
		  in_productRateItemId
		);
	}

}
