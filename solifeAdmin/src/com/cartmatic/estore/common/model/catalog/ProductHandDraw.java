package com.cartmatic.estore.common.model.catalog;

import com.cartmatic.estore.common.model.catalog.base.ProductHandDrawTbl;

/**
 * Model class for ProductMedia. Add not database mapped fileds in this class.
 */
public class ProductHandDraw extends ProductHandDrawTbl {

	/**
	 * Default Empty Constructor for class ProductMedia
	 */
	public ProductHandDraw () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class ProductMedia
	 */
	public ProductHandDraw (
		 Integer in_productMediaId
		) {
		super (
		  in_productMediaId
		);
	}

}
