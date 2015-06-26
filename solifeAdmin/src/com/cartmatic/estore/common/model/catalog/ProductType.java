package com.cartmatic.estore.common.model.catalog;

import com.cartmatic.estore.common.model.catalog.base.ProductTypeTbl;

/**
 * Model class for ProductType. Add not database mapped fileds in this class.
 */
public class ProductType extends ProductTypeTbl {

  	/**
	 * Default Empty Constructor for class ProductType
	 */
	public ProductType () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class ProductType
	 */
	public ProductType (
		 Integer in_productTypeId
		) {
		super (
		  in_productTypeId
		);
	}

}
