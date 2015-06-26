package com.cartmatic.estore.common.model.system;

import com.cartmatic.estore.common.model.system.base.PaymentMethodTbl;

/**
 * Model class for PaymentMethod. Add not database mapped fileds in this class.
 */
public class PaymentMethod extends PaymentMethodTbl implements Comparable<PaymentMethod> {
    public static final Short PAYMENT_METHOD_TYPE_ONLINE=new Short("1");
    
    public static final Short PAYMENT_METHOD_TYPE_OFFLINE=new Short("2");

  	/**
	 * Default Empty Constructor for class PaymentMethod
	 */
	public PaymentMethod () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class PaymentMethod
	 */
	public PaymentMethod (
		 Integer in_paymentMethodId
		) {
		super (
		  in_paymentMethodId
		);
	}

	@Override
	public int compareTo(PaymentMethod o) {
		if(this.sortOrder!=null&&o.sortOrder!=null){
			return this.sortOrder.compareTo(o.sortOrder);
		}else if(this.sortOrder==null&&o.sortOrder!=null){
			return -1;
		}else if(this.sortOrder!=null&&o.sortOrder==null){
			return 1;
		}else{
			return this.paymentMethodId.compareTo(o.paymentMethodId);
		}
	}
	
	

}
