package com.cartmatic.estore.common.model.order;

import com.cartmatic.estore.common.model.order.base.OrderMessageTbl;

/**
 * Model class for OrderMessage. Add not database mapped fileds in this class.
 */
public class OrderMessage extends OrderMessageTbl {

  	/**
	 * Default Empty Constructor for class OrderMessage
	 */
	public OrderMessage () {
		super();
	}
	
	/**
	 * 定义实体的业务名取值； orderMessageName
	 * 必须手工完成这个部分，否则编译不通过。
	 */
	public String getOrderMessageName () {
		if (orderMessageId == null)
	        return "";
	    else
			//返回一个指定有业务意义的属性值;
			//如：product的VO就用product.productName
	        return this.subject;
	}
	
	/**
	 * Default Key Fields Constructor for class OrderMessage
	 */
	public OrderMessage (
		 Integer in_orderMessageId
		) {
		super (
		  in_orderMessageId
		);
	}

}
