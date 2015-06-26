package com.cartmatic.estore.common.model.customer;

import com.cartmatic.estore.common.model.customer.base.TextMessageTbl;

/**
 * Model class for TextMessage. Add not database mapped fileds in this class.
 */
public class TextMessage extends TextMessageTbl {

  	/**
	 * Default Empty Constructor for class TextMessage
	 */
	public TextMessage () {
		super();
	}
	
	/**
	 * 定义实体的业务名取值； textMessageName
	 * 必须手工完成这个部分，否则编译不通过。
	 */
	public String getTextMessageName () {
		if (textMessageId == null)
	        return "";
	    else
			//返回一个指定有业务意义的属性值;
			//如：product的VO就用product.productName
	        return this.email;
	}
	
	/**
	 * Default Key Fields Constructor for class TextMessage
	 */
	public TextMessage (
		 Integer in_textMessageId
		) {
		super (
		  in_textMessageId
		);
	}

}
