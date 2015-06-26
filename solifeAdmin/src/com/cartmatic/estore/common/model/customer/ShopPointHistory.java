package com.cartmatic.estore.common.model.customer;

import com.cartmatic.estore.common.model.customer.base.ShopPointHistoryTbl;

/**
 * Model class for ShopPointHistory. Add not database mapped fileds in this class.
 */
public class ShopPointHistory extends ShopPointHistoryTbl {
	public static final Short SHOPPOINT_TYPE_CUSTOMERREGISTER = 10;
	public static final Short SHOPPOINT_TYPE_LOGIN = 20;
	public static final Short SHOPPOINT_TYPE_GIVE = 30;
	public static final Short SHOPPOINT_TYPE_SUGGESTION = 40;
	public static final Short SHOPPOINT_TYPE_INTRODUCENEW = 50;
	public static final Short SHOPPOINT_TYPE_PRODUCTREVIEW = 60;
	public static final Short SHOPPOINT_TYPE_PAY = 70;
	public static final Short SHOPPOINT_TYPE_ORDERCANCEL = 80;
	public static final Short SHOPPOINT_TYPE_OTHER = 90;
	public static final Short SHOPPOINT_TYPE_GIFTCERTIFICATE = 100;
	public static final Short SHOPPOINT_TYPE_BUY = 110;
  	/**
	 * Default Empty Constructor for class ShopPointHistory
	 */
	public ShopPointHistory () {
		super();
	}
	
	/**
	 * 定义实体的业务名取值； shopPointHistoryName
	 * 必须手工完成这个部分，否则编译不通过。
	 */
	public String getShopPointHistoryName () {
		if (shopPointHistoryId == null)
	        return "";
	    else
			//返回一个指定有业务意义的属性值;
			//如：product的VO就用product.productName
	        return this.shopPointHistoryId.toString();
	}
	
	/**
	 * Default Key Fields Constructor for class ShopPointHistory
	 */
	public ShopPointHistory (
		 Integer in_shopPointHistoryId
		) {
		super (
		  in_shopPointHistoryId
		);
	}

}
