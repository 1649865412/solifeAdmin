package com.cartmatic.estore.common.model.content;

import com.cartmatic.estore.common.model.content.base.AdvertisementTbl;

/**
 * Model class for Advertisement. Add not database mapped fileds in this class.
 */
public class Advertisement extends AdvertisementTbl {

  	/**
	 * Default Empty Constructor for class Advertisement
	 */
	public Advertisement () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class Advertisement
	 */
	public Advertisement (
		 Integer in_advertisementId
		) {
		super (
		  in_advertisementId
		);
	}

}
