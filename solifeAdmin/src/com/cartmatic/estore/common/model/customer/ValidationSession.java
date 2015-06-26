package com.cartmatic.estore.common.model.customer;

import com.cartmatic.estore.common.model.customer.base.ValidationSessionTbl;

/**
 * Model class for ValidationSession. Add not database mapped fileds in this class.
 */
public class ValidationSession extends ValidationSessionTbl {
    public static final Short TYPE_PASSWORD_RECOVER=new Short((short)0);
    public static final Short TYPE_CUSTOMER_ACTIVE=new Short((short)1);
	
  	/**
	 * Default Empty Constructor for class ValidationSession
	 */
	public ValidationSession () {
		super();
	}
	
	/**
	 * 定义实体的业务名取值； validationSessionName
	 * 必须手工完成这个部分，否则编译不通过。
	 */
	public String getValidationSessionName () {
		if (validationSessionId == null)
	        return "";
	    else
			//返回一个指定有业务意义的属性值;
			//如：product的VO就用product.productName
	        return this.validationSessionId.toString();
	}
	
	/**
	 * Default Key Fields Constructor for class ValidationSession
	 */
	public ValidationSession (
		 Integer in_validationSessionId
		) {
		super (
		  in_validationSessionId
		);
	}

}
