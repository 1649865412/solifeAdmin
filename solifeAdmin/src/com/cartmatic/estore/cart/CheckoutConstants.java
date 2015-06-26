package com.cartmatic.estore.cart;

public class CheckoutConstants {
    //---------------------- checkout constants ------------------------
	//---- checkout status Constants
	
	/**
	 * 通过CHECKOUT_ON来判断checkout过程中session的过期问题
	 */
	public final static String CHECKOUT_ON = "CHECKOUT_ON";

	public static final String	SHOPPINGCART_COOKIE		= "shoppingcartInCookie";
	public static final String SHOPPINGCAT_ITEMCOUT_COOKIE = "totalItemNo";
	public static final String SHOPPINGCART_PRICE_COOKIE = "cartTotalPrice";
	public static final Short	SHOPPINGCART_STATUS_ACTIVATE	= new Short((short) 1);
	public static final Short	SHOPPINGCART_STATUS_DELETED		= new Short((short) 3);
    
	public static final String F_ITEMCOUNT_COOKIE ="favoriteitemNo";
	public static final String C_ITEMCOUNT_COOKIE ="cartitemNo";
	
	public static final String CANNOTMOVE_C_NOINVENTORY = "NOINVENTORY";
	public static final String CANNOTMOVE_C_NOINVENTORY_NOMORE = "NOMOREINVENTORY";
	
	public static final String TOCHECKOUT = "toCheckoutPage";
	public static final String ANONYMOUS = "isanonymous";
	public static final String EMAIL = "userEmail";
}

