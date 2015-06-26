package com.cartmatic.estore.common.model.catalog;

import com.cartmatic.estore.common.model.catalog.base.WholesalePriceTbl;

/**
 * Model class for WholesalePrice. Add not database mapped fileds in this class.
 */
public class WholesalePrice extends WholesalePriceTbl {

  	/**
	 * Default Empty Constructor for class WholesalePrice
	 */
	public WholesalePrice () {
		super();
	}
	
	/**
	 * 定义实体的业务名取值； wholesalePriceName
	 * 必须手工完成这个部分，否则编译不通过。
	 */
	public String getWholesalePriceName () {
		if (wholesalePriceId == null)
	        return "";
	    else
			//返回一个指定有业务意义的属性值;
			//如：product的VO就用product.productName
	        return this.wholesalePriceId.toString();
	}
	
	/**
	 * Default Key Fields Constructor for class WholesalePrice
	 */
	public WholesalePrice (
		 Integer in_wholesalePriceId
		) {
		super (
		  in_wholesalePriceId
		);
	}

}
