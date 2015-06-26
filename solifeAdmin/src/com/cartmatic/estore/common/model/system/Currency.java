package com.cartmatic.estore.common.model.system;

import com.cartmatic.estore.common.model.system.base.CurrencyTbl;

/**
 * Model class for Currency. Add not database mapped fileds in this class.
 */
public class Currency extends CurrencyTbl {

  	/**
	 * Default Empty Constructor for class Currency
	 */
	public Currency () {
		super();
	}
	
	
	/**
	 * Default Key Fields Constructor for class Currency
	 */
	public Currency (
		 Integer in_currencyId
		) {
		super (
		  in_currencyId
		);
	}

}
