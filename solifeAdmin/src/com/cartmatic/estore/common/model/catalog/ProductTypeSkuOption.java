package com.cartmatic.estore.common.model.catalog;

import com.cartmatic.estore.common.model.catalog.base.ProductTypeSkuOptionTbl;

/**
 * Model class for ProductTypeSkuOption. Add not database mapped fileds in this class.
 */
public class ProductTypeSkuOption extends ProductTypeSkuOptionTbl {

  	/**
	 * Default Empty Constructor for class ProductTypeSkuOption
	 */
	public ProductTypeSkuOption () {
		super();
	}
	
	/**
	 * 定义实体的业务名取值； productTypeSkuOptionName
	 * 必须手工完成这个部分，否则编译不通过。
	 */
	public String getProductTypeSkuOptionName () {
		if (productTypeSkuOptionId == null)
	        return "";
	    else
			//返回一个指定有业务意义的属性值;
			//如：product的VO就用product.productName
	        return this.productTypeSkuOptionId.toString();
	}
	
	/**
	 * Default Key Fields Constructor for class ProductTypeSkuOption
	 */
	public ProductTypeSkuOption (
		 Integer in_productTypeSkuOptionId
		) {
		super (
		  in_productTypeSkuOptionId
		);
	}

}
