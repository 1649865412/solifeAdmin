package com.cartmatic.estore.common.model.system;

import com.cartmatic.estore.common.model.system.base.AppEventTbl;

/**
 * Model class for AppEvent. Add not database mapped fileds in this class.
 */
public class AppEvent extends AppEventTbl {

  	/**
	 * Default Empty Constructor for class AppEvent
	 */
	public AppEvent () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class AppEvent
	 */
	public AppEvent (
		 Integer in_appEventId
		) {
		super (
		  in_appEventId
		);
	}

}
