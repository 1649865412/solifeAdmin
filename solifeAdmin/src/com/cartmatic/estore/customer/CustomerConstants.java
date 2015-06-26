package com.cartmatic.estore.customer;

public class CustomerConstants {
	// anonymous membership level
	public static final Integer	MEMBERSHIP_LEVEL_ANONYMOUS	= new Integer(-2);
	// base membership level
	public static final Integer	MEMBERSHIP_LEVEL_BASE	= new Integer(1);
	 
	public static final Short	ShopPoint_Type_Buy_Product	= new Short((short) 70);
	public static final Short	ShopPoint_Type_Cancel_Order	= new Short((short) 80);
	public static final Short	ShopPoint_Type_Feedback	= new Short((short) 40);
	public static final Short	ShopPoint_Type_Introduce	= new Short((short) 50);
	public static final Short	ShopPoint_Type_Login	= new Short((short) 20);
	public static final Short	ShopPoint_Type_Others	= new Short((short) 90);
	public static final Short	ShopPoint_Type_ProductComment	= new Short((short) 60);
	// added for the shop point history
	public static final Short	ShopPoint_Type_Registration	= new Short((short) 10);
	public static final Short	ShopPoint_Type_Shopping	= new Short((short) 30);
}
