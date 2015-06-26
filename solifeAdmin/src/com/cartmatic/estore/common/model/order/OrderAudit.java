package com.cartmatic.estore.common.model.order;

import com.cartmatic.estore.common.model.order.base.OrderAuditTbl;

/**
 * Model class for OrderAudit. Add not database mapped fileds in this class.
 */
public class OrderAudit extends OrderAuditTbl {

  	/**
	 * Default Empty Constructor for class OrderAudit
	 */
	public OrderAudit () {
		super();
	}
	
	/**
	 * 定义实体的业务名取值； orderAuditName
	 * 必须手工完成这个部分，否则编译不通过。
	 */
	public String getOrderAuditName () {
		if (orderAuditId == null)
	        return "";
	    else
			//返回一个指定有业务意义的属性值;
			//如：product的VO就用product.productName
	        return this.orderAuditId.toString();
	}
	
	/**
	 * Default Key Fields Constructor for class OrderAudit
	 */
	public OrderAudit (
		 Integer in_orderAuditId
		) {
		super (
		  in_orderAuditId
		);
	}

}
