package com.cartmatic.estore.common.model.catalog;

import com.cartmatic.estore.common.model.catalog.base.ProductSkuOptionValueTbl;

/**
 * Model class for ProductSkuOptionValue. Add not database mapped fileds in this class.
 */
public class ProductSkuOptionValue extends ProductSkuOptionValueTbl {
	

  	/**
	 * Default Empty Constructor for class ProductSkuOptionValue
	 */
	public ProductSkuOptionValue () {
		super();
	}
	
	/**
	 * 定义实体的业务名取值； productSkuOptionValueName
	 * 必须手工完成这个部分，否则编译不通过。
	 */
	public String getProductSkuOptionValueName () {
		if (productSkuOptionValueId == null)
	        return "";
	    else
			//返回一个指定有业务意义的属性值;
			//如：product的VO就用product.productName
	        return this.productSkuOptionValueId.toString();
	}
	
	/**
	 * Default Key Fields Constructor for class ProductSkuOptionValue
	 */
	public ProductSkuOptionValue (
		 Integer in_productSkuOptionValueId
		) {
		super (
		  in_productSkuOptionValueId
		);
	}
	


}
