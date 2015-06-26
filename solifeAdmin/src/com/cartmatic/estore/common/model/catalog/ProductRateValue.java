package com.cartmatic.estore.common.model.catalog;

import com.cartmatic.estore.common.model.catalog.base.ProductRateValueTbl;

/**
 * Model class for ProductRateValue. Add not database mapped fileds in this class.
 */
public class ProductRateValue extends ProductRateValueTbl {

  	/**
	 * Default Empty Constructor for class ProductRateValue
	 */
	public ProductRateValue () {
		super();
	}
	
	/**
	 * 定义实体的业务名取值； productRateValueName
	 * 必须手工完成这个部分，否则编译不通过。
	 */
	public String getProductRateValueName () {
		if (productRateValueId == null)
	        return "";
	    else
			//返回一个指定有业务意义的属性值;
			//如：product的VO就用product.productName
	        return this.productRateValueId.toString();
	}
	
	/**
	 * Default Key Fields Constructor for class ProductRateValue
	 */
	public ProductRateValue (
		 Integer in_productRateValueId
		) {
		super (
		  in_productRateValueId
		);
	}

}
