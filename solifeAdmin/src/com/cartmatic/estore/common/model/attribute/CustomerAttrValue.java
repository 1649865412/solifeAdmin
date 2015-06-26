package com.cartmatic.estore.common.model.attribute;

import com.cartmatic.estore.common.model.attribute.base.CustomerAttrValueTbl;

/**
 * Model class for CustomerAttrValue. Add not database mapped fileds in this class.
 */
public class CustomerAttrValue extends CustomerAttrValueTbl {

  	/**
	 * Default Empty Constructor for class CustomerAttrValue
	 */
	public CustomerAttrValue () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class CustomerAttrValue
	 */
	public CustomerAttrValue (
		 Integer in_customerAttrValueId
		) {
		super (
		  in_customerAttrValueId
		);
	}

}
