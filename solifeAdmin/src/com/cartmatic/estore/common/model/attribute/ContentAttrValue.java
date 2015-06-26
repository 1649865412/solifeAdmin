package com.cartmatic.estore.common.model.attribute;

import com.cartmatic.estore.common.model.attribute.base.ContentAttrValueTbl;

/**
 * Model class for ContentAttrValue. Add not database mapped fileds in this class.
 */
public class ContentAttrValue extends ContentAttrValueTbl {

  	/**
	 * Default Empty Constructor for class ContentAttrValue
	 */
	public ContentAttrValue () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class ContentAttrValue
	 */
	public ContentAttrValue (
		 Integer in_contentAttrValueId
		) {
		super (
		  in_contentAttrValueId
		);
	}

}
