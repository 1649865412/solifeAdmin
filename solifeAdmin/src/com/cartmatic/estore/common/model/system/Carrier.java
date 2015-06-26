package com.cartmatic.estore.common.model.system;

import com.cartmatic.estore.common.model.system.base.CarrierTbl;

/**
 * Model class for Carrier. Add not database mapped fileds in this class.
 */
public class Carrier extends CarrierTbl {

  	/**
	 * Default Empty Constructor for class Carrier
	 */
	public Carrier () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class Carrier
	 */
	public Carrier (
		 Integer in_carrierId
		) {
		super (
		  in_carrierId
		);
	}

}
