package com.cartmatic.estore.common.model.system;

import com.cartmatic.estore.common.model.system.base.SystemQueueTbl;
import com.cartmatic.estore.common.util.DateUtil;

/**
 * Model class for SystemQueue. Add not database mapped fileds in this class.
 */
public class SystemQueue extends SystemQueueTbl {
		
	public final static short	STATUS_CANCELLED	= 9;

	public final static short	STATUS_NOT_SEND		= 0;

	public final static short	STATUS_SENDING		= 1;

	public final static short	STATUS_SENT			= 2;
	
	/**
	 * 队列类型是发送email
	 */
	public final static Short TYPE_EMAIL = Short.valueOf("1");
	/**
	 * 队列类型是查询IP Location
	 */
	public final static Short TYPE_GEOIP = Short.valueOf("2");
	
	
	/**
	 * 队列类型是图片重构
	 */
	public final static Short TYPE_IMAGE_REBUILD = Short.valueOf("3");
	
	/**
	 * 队列类型是清理没有被引用的图片
	 */
	public final static Short TYPE_IMAGE_CLEAN = Short.valueOf("4");
	
  	/**
	 * Default Empty Constructor for class SystemQueue
	 */
	public SystemQueue () {
		super();
		queueStatus = 0;
		execTimes = 0;
		errorMsg = "";
	}
	
	/**
	 * 定义实体的业务名取值； systemQueueName
	 * 必须手工完成这个部分，否则编译不通过。
	 */
	public String getSystemQueueName () {
		if (systemQueueId == null)
	        return "";
	    else
			//返回一个指定有业务意义的属性值;
			//如：product的VO就用product.productName
	        return this.title;
	}
	
	/**
	 * Default Key Fields Constructor for class SystemQueue
	 */
	public SystemQueue (
		 Integer in_systemQueueId
		) {
		super (
		  in_systemQueueId
		);
	}
	
	public void appendErrorMsg(String errMsg) {
		StringBuilder sb = new StringBuilder(errorMsg);
		sb.append(DateUtil.getNowStr()).append(" - ");
		sb.append(errMsg);
		sb.append("<br/>");
		String result = sb.toString();
		if (sb.length() > 1024)
		{
			int len = sb.length();
			result = sb.substring(len - 1024, sb.length());
		}
		errorMsg = result;
	}

}
