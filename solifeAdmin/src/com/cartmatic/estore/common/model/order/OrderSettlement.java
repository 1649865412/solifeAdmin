package com.cartmatic.estore.common.model.order;

import com.cartmatic.estore.common.model.order.base.OrderSettlementTbl;

/**
 * Model class for OrderSettlement. Add not database mapped fileds in this class.
 */
public class OrderSettlement extends OrderSettlementTbl {

  	/**
	 * Default Empty Constructor for class OrderSettlement
	 */
	public OrderSettlement () {
		super();
	}
	
	/**
	 * 定义实体的业务名取值； orderSettlementName
	 * 必须手工完成这个部分，否则编译不通过。
	 */
	public String getOrderSettlementName () {
		if (orderSettlementId == null)
	        return "";
	    else
			//返回一个指定有业务意义的属性值;
			//如：product的VO就用product.productName
	        return this.shipmentNo;
	}
	
	/**
	 * Default Key Fields Constructor for class OrderSettlement
	 */
	public OrderSettlement (
		 Integer in_orderSettlementId
		) {
		super (
		  in_orderSettlementId
		);
	}

}
