package com.cartmatic.estore.common.model.system;

import com.cartmatic.estore.common.model.system.base.RoleResTbl;

/**
 * Model class for RoleRes. Add not database mapped fileds in this class.
 */
public class RoleRes extends RoleResTbl {

  	/**
	 * Default Empty Constructor for class RoleRes
	 */
	public RoleRes () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class RoleRes
	 */
	public RoleRes (
		 Integer in_roleResId
		) {
		super (
		  in_roleResId
		);
	}

}
