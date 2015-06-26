package com.cartmatic.estore.common.model.catalog;

import com.cartmatic.estore.common.model.catalog.base.BrandTbl;

/**
 * Model class for Brand. Add not database mapped fileds in this class.
 */
public class Brand extends BrandTbl {

  	/**
	 * Default Empty Constructor for class Brand
	 */
	public Brand () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class Brand
	 */
	public Brand (
		 Integer in_brandId
		) {
		super (
		  in_brandId
		);
	}

}
