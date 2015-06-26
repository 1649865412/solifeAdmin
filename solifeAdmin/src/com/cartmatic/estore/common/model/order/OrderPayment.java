package com.cartmatic.estore.common.model.order;

import java.math.BigDecimal;

import com.cartmatic.estore.common.model.order.base.OrderPaymentTbl;

/**
 * Model class for OrderPayment. Add not database mapped fileds in this class.
 */
public class OrderPayment extends OrderPaymentTbl {

  	/**
	 * Default Empty Constructor for class OrderPayment
	 */
	public OrderPayment () {
		super();
	}
	
	/**
	 * 定义实体的业务名取值； orderPaymentName
	 * 必须手工完成这个部分，否则编译不通过。
	 */
	public String getOrderPaymentName () {
		if (orderPaymentId == null)
	        return "";
	    else
			//返回一个指定有业务意义的属性值;
			//如：product的VO就用product.productName
	        return this.orderPaymentId.toString();
	}
	
	/**
	 * Default Key Fields Constructor for class OrderPayment
	 */
	public OrderPayment (
		 Integer in_orderPaymentId
		) {
		super (
		  in_orderPaymentId
		);
	}
	
	public void setBalanceWithFloat(float aValue) {
		this.setBalance(new BigDecimal(aValue).setScale(2, BigDecimal.ROUND_HALF_UP));
	}
	
	private String orderNo;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
}
