package com.cartmatic.estore.common.model.attribute;

import com.cartmatic.estore.common.model.attribute.base.ProductAttrValueTbl;

/**
 * Model class for ProductAttrValue. Add not database mapped fileds in this class.
 */
public class ProductAttrValue extends ProductAttrValueTbl {

  	/**
	 * Default Empty Constructor for class ProductAttrValue
	 */
	public ProductAttrValue () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class ProductAttrValue
	 */
	public ProductAttrValue (
		 Integer in_productAttrValueId
		) {
		super (
		  in_productAttrValueId
		);
	}

}
