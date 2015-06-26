package com.cartmatic.estore.common.model.system;

import com.cartmatic.estore.common.model.system.base.WrapTbl;

/**
 * Model class for Wrap. Add not database mapped fileds in this class.
 */
public class Wrap extends WrapTbl {

  	/**
	 * Default Empty Constructor for class Wrap
	 */
	public Wrap () {
		super();
	}
	
	
	/**
	 * Default Key Fields Constructor for class Wrap
	 */
	public Wrap (
		 Integer in_wrapId
		) {
		super (
		  in_wrapId
		);
	}

}
