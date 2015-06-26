package com.cartmatic.estore.common.model.system;

import com.cartmatic.estore.common.model.system.base.SystemVersionTbl;

/**
 * Model class for SystemVersion. Add not database mapped fileds in this class.
 */
public class SystemVersion extends SystemVersionTbl {

  	/**
	 * Default Empty Constructor for class SystemVersion
	 */
	public SystemVersion () {
		super();
	}
	
	/**
	 * 定义实体的业务名取值； systemVersionName
	 * 必须手工完成这个部分，否则编译不通过。
	 */
	public String getSystemVersionName () {
		if (systemVersionId == null)
	        return "";
	    else
			//返回一个指定有业务意义的属性值;
			//如：product的VO就用product.productName
	        return this.licenseKey;
	}
	
	/**
	 * Default Key Fields Constructor for class SystemVersion
	 */
	public SystemVersion (
		 Integer in_systemVersionId
		) {
		super (
		  in_systemVersionId
		);
	}

}
