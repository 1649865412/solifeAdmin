package com.cartmatic.estore.common.model.attribute;

import com.cartmatic.estore.common.model.attribute.base.CategoryAttrValueTbl;

/**
 * Model class for CategoryAttrValue. Add not database mapped fileds in this class.
 */
public class CategoryAttrValue extends CategoryAttrValueTbl {

  	/**
	 * Default Empty Constructor for class CategoryAttrValue
	 */
	public CategoryAttrValue () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class CategoryAttrValue
	 */
	public CategoryAttrValue (
		 Integer in_categoryAttrValueId
		) {
		super (
		  in_categoryAttrValueId
		);
	}

}
