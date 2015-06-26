package com.cartmatic.estore.common.model.system;

import com.cartmatic.estore.common.model.system.base.ShippingMethodTbl;

/**
 * Model class for ShippingMethod. Add not database mapped fileds in this class.
 */
public class ShippingMethod extends ShippingMethodTbl {

  	/**
	 * Default Empty Constructor for class ShippingMethod
	 */
	public ShippingMethod () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class ShippingMethod
	 */
	public ShippingMethod (
		 Integer in_shippingMethodId
		) {
		super (
		  in_shippingMethodId
		);
	}

}
