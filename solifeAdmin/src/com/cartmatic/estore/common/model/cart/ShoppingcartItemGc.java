package com.cartmatic.estore.common.model.cart;

import com.cartmatic.estore.common.model.cart.base.ShoppingcartItemGcTbl;

/**
 * Model - Business object  
 * ShoppingcartItemGc Object
 */
public class ShoppingcartItemGc extends ShoppingcartItemGcTbl implements Cloneable{

  	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	
	public ShoppingcartItemGc () {
		super();
	}
	

	/**
	 * Default Key Fields Constructor for class ShoppingcartItemGc
	 */
	public ShoppingcartItemGc (
		 Integer in_shoppingcartItemGcId
		) {
		super (
			in_shoppingcartItemGcId
		);
	}

    
}
