package com.cartmatic.estore.common.model.system;

import com.cartmatic.estore.common.model.system.base.TaxTbl;

/**
 * Model - Business object
 * This file won't get overwritten.
 */
public class Tax extends TaxTbl {
	
  	/**
	 * Default Empty Constructor for class Tax
	 */
	public Tax () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class Tax
	 */
	public Tax (
		 Integer in_taxId
		) {
		super (
		  in_taxId
		);
	}

}
