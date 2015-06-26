package com.cartmatic.estore.common.model.system;

import com.cartmatic.estore.common.model.system.base.PaymentHistoryTbl;

/**
 * Model class for PaymentHistory. Add not database mapped fileds in this class.
 */
public class PaymentHistory extends PaymentHistoryTbl {
	/**
     * indicate the payment hisotry 's flag field. 
     */
    public static final Short FLAG_SUCCESS=new Short((short)1);
    public static final Short FLAG_PENDING=new Short((short)-1);
    public static final Short FLAG_FAIL=new Short((short)0);
    
    /**
     * indicate the payment hisotry 's isBrowsed field. 
     */
    public static final Short BROWSED=new Short((short)1);
    public static final Short UNBROWSED=new Short((short)0);
    
  	/**
	 * Default Empty Constructor for class PaymentHistory
	 */
	public PaymentHistory () {
		super();
	}
	
	/**
	 * 定义实体的业务名取值； paymentHistoryName
	 * 必须手工完成这个部分，否则编译不通过。
	 */
	public String getPaymentHistoryName () {
		if (paymentHistoryId == null)
	        return "";
	    else
			//返回一个指定有业务意义的属性值;
			//如：product的VO就用product.productName
	        return this.getPaymentHistoryId()+"";
	}
	
	/**
	 * Default Key Fields Constructor for class PaymentHistory
	 */
	public PaymentHistory (
		 Integer in_paymentHistoryId
		) {
		super (
		  in_paymentHistoryId
		);
	}

}
