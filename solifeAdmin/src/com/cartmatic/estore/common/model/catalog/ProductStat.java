package com.cartmatic.estore.common.model.catalog;

import com.cartmatic.estore.common.model.catalog.base.ProductStatTbl;

/**
 * Model class for ProductStat. Add not database mapped fileds in this class.
 */
public class ProductStat extends ProductStatTbl {

  	/**
	 * Default Empty Constructor for class ProductStat
	 */
	public ProductStat () {
		super();
	}
	
	/**
	 * 定义实体的业务名取值； productStatName
	 * 必须手工完成这个部分，否则编译不通过。
	 */
	public String getProductStatName () {
		if (productStatId == null)
	        return "";
	    else
			//返回一个指定有业务意义的属性值;
			//如：product的VO就用product.productName
	        return this.productStatId.toString();
	}
	
	/**
	 * Default Key Fields Constructor for class ProductStat
	 */
	public ProductStat (
		 Integer in_productStatId
		) {
		super (
		  in_productStatId
		);
	}

}
