package com.cartmatic.estore.common.model.catalog;

import com.cartmatic.estore.common.model.catalog.base.ProductDescriptionTbl;

/**
 * Model class for ProductDescription. Add not database mapped fileds in this class.
 */
public class ProductDescription extends ProductDescriptionTbl {

  	/**
	 * Default Empty Constructor for class ProductDescription
	 */
	public ProductDescription () {
		super();
	}
	
	/**
	 * 定义实体的业务名取值； productDescriptionName
	 * 必须手工完成这个部分，否则编译不通过。
	 */
	public String getProductDescriptionName () {
		if (productDescriptionId == null)
	        return "";
	    else
			//返回一个指定有业务意义的属性值;
			//如：product的VO就用product.productName
	        return this.productDescriptionId.toString();
	}
	
	/**
	 * Default Key Fields Constructor for class ProductDescription
	 */
	public ProductDescription (
		 Integer in_productDescriptionId
		) {
		super (
		  in_productDescriptionId
		);
	}

}
