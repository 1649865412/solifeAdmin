package com.cartmatic.estore.common.model.attribute;

import com.cartmatic.estore.common.model.attribute.base.AttributeTbl;

/**
 * Model class for Attribute. Add not database mapped fileds in this class.
 */
public class Attribute extends AttributeTbl {

  	/**
	 * Default Empty Constructor for class Attribute
	 */
	public Attribute () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class Attribute
	 */
	public Attribute (
		 Integer in_attributeId
		) {
		super (
		  in_attributeId
		);
	}

}
