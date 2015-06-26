package com.cartmatic.estore.common.model.catalog;

import com.cartmatic.estore.common.model.catalog.base.AccessoryTbl;

/**
 * Model class for Accessory. Add not database mapped fileds in this class.
 */
public class Accessory extends AccessoryTbl {

  	/**
	 * Default Empty Constructor for class Accessory
	 */
	public Accessory () {
		super();
	}
	

	/**
	 * Default Key Fields Constructor for class Accessory
	 */
	public Accessory (
		 Integer in_accessoryId
		) {
		super (
		  in_accessoryId
		);
	}

}
