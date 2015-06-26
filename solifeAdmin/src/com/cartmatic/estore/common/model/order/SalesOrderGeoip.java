package com.cartmatic.estore.common.model.order;

import com.cartmatic.estore.common.model.order.base.SalesOrderGeoipTbl;

/**
 * Model class for SalesOrderGeoip. Add not database mapped fileds in this class.
 */
public class SalesOrderGeoip extends SalesOrderGeoipTbl {
	public static final Short PLACE_ORDER =0;
	public static final Short GOTOPAY =1;
	public static final Short SHIPPING_ADDRESS =2;
	public static final Short BILLING_ADDRESS =3;

  	/**
	 * Default Empty Constructor for class SalesOrderGeoip
	 */
	public SalesOrderGeoip () {
		super();
	}
	
	/**
	 * 定义实体的业务名取值； salesOrderGeoipName
	 * 必须手工完成这个部分，否则编译不通过。
	 */
	public String getSalesOrderGeoipName () {
		if (salesOrderGeoipId == null)
	        return "";
	    else
			//返回一个指定有业务意义的属性值;
			//如：product的VO就用product.productName
	        return this.orderNo;
	}
	
	/**
	 * Default Key Fields Constructor for class SalesOrderGeoip
	 */
	public SalesOrderGeoip (
		 Integer in_salesOrderGeoipId
		) {
		super (
		  in_salesOrderGeoipId
		);
	}

}
