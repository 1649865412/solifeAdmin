package com.cartmatic.estore.common.model.content;

import com.cartmatic.estore.common.model.content.base.SystemMessageTbl;

/**
 * Model class for SystemMessage. Add not database mapped fileds in this class.
 */
public class SystemMessage extends SystemMessageTbl {
    /**
     * constant variable for message type.
     */
    public final static Short MSG_TYPE_OTHER=new Short((short)0);
    public final static Short MSG_TYPE_ORDER=new Short((short)1);
    public final static Short MSG_TYPE_PRICE=new Short((short)2);
    public final static Short MSG_TYPE_PROMOTION=new Short((short)3);
    public final static Short MSG_TYPE_STORE=new Short((short)4);
	

  	/**
	 * Default Empty Constructor for class SystemMessage
	 */
	public SystemMessage () {
		super();
	}
	
	/**
	 * 定义实体的业务名取值； systemMessageName
	 * 必须手工完成这个部分，否则编译不通过。
	 */
	public String getSystemMessageName () {
		if (systemMessageId == null)
	        return "";
	    else
			//返回一个指定有业务意义的属性值;
			//如：product的VO就用product.productName
	        return this.systemMessageId+"";
	}
	
	/**
	 * Default Key Fields Constructor for class SystemMessage
	 */
	public SystemMessage (
		 Integer in_systemMessageId
		) {
		super (
		  in_systemMessageId
		);
	}

}
