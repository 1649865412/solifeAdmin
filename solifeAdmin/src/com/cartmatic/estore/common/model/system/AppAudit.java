package com.cartmatic.estore.common.model.system;

import com.cartmatic.estore.common.model.system.base.AppAuditTbl;

/**
 * Model class for AppAudit. Add not database mapped fileds in this class.
 */
public class AppAudit extends AppAuditTbl {

  	/**
	 * Default Empty Constructor for class AppAudit
	 */
	public AppAudit () {
		super();
	}
	
	/**
	 * 定义实体的业务名取值； appAuditName
	 * 必须手工完成这个部分，否则编译不通过。
	 */
	public String getAppAuditName () {
		if (appAuditId == null)
	        return "";
	    else
			//返回一个指定有业务意义的属性值;
			//如：product的VO就用product.productName
	        return this.appAuditId+"";
	}
	
	/**
	 * Default Key Fields Constructor for class AppAudit
	 */
	public AppAudit (
		 Integer in_appAuditId
		) {
		super (
		  in_appAuditId
		);
	}

}
