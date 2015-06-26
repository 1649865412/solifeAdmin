package com.cartmatic.estore.common.model.customer;

import com.cartmatic.estore.common.model.customer.base.FeedbackTbl;

/**
 * Model class for Feedback. Add not database mapped fileds in this class.
 */
public class Feedback extends FeedbackTbl {
	
    public static final Short STATUS_OPENED=new Short("0");
    public static final Short STATUS_CLOSED=new Short("1");

  	/**
	 * Default Empty Constructor for class Feedback
	 */
	public Feedback () {
		super();
	}
	
	/**
	 * 定义实体的业务名取值； feedbackName
	 * 必须手工完成这个部分，否则编译不通过。
	 */
	public String getFeedbackName () {
		if (feedbackId == null)
	        return "";
	    else
			//返回一个指定有业务意义的属性值;
			//如：product的VO就用product.productName
	        return this.subject;
	}
	
	/**
	 * Default Key Fields Constructor for class Feedback
	 */
	public Feedback (
		 Integer in_feedbackId
		) {
		super (
		  in_feedbackId
		);
	}

}
