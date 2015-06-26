package com.cartmatic.estore.common.model.customer;

import com.cartmatic.estore.common.model.customer.base.WishlistTbl;

/**
 * Model class for Wishlist. Add not database mapped fileds in this class.
 */
public class Wishlist extends WishlistTbl {
    public static final Integer SHARED_LEVEL_ANY=new Integer(0);
    public static final Integer SHARED_LEVEL_OWNER=new Integer(1);
    public static final Integer SHaRED_LEVEL_SOMEONE=new Integer(2);
    
    public static final Short WISH_LIST_TYPE_NORMAL=new Short("0");
    public static final Short WISH_LIST_TYPE_EVENT=new Short("1");
    
    public static final Short NOT_DEFAULT_LIST=new Short("0");
    public static final Short DEFAULT_LIST=new Short("1");
    
    public static final String WISHLIST_DEFAULT_TITLE="Wish List";

  	/**
	 * Default Empty Constructor for class Wishlist
	 */
	public Wishlist () {
		super();
	}
	
	/**
	 * 定义实体的业务名取值； wishlistName
	 * 必须手工完成这个部分，否则编译不通过。
	 */
	public String getWishlistName () {
		if (wishListId == null)
	        return "";
	    else
			//返回一个指定有业务意义的属性值;
			//如：product的VO就用product.productName
	        return this.wishListTitle;
	}
	
	/**
	 * Default Key Fields Constructor for class Wishlist
	 */
	public Wishlist (
		 Integer in_wishListId
		) {
		super (
		  in_wishListId
		);
	}

}
