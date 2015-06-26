package com.cartmatic.estore.common.model.system;

import com.cartmatic.estore.common.model.system.base.AdminInfoTbl;

/**
 * Model class for AdminInfo. Add not database mapped fileds in this class.
 */
public class AdminInfo extends AdminInfoTbl {

  	/**
	 * Default Empty Constructor for class AdminInfo
	 */
	public AdminInfo () {
		super();
	}
	
	/**
	 * 定义实体的业务名取值； adminInfoName
	 * 必须手工完成这个部分，否则编译不通过。
	 */
	public String getAdminInfoName () {
		if (adminInfoId == null)
	        return "";
	    else
			//返回一个指定有业务意义的属性值;
			//如：product的VO就用product.productName
	        return this.adminInfoId+"";
	}
	
	/**
	 * Default Key Fields Constructor for class AdminInfo
	 */
	public AdminInfo (
		 Integer in_adminInfoId
		) {
		super (
		  in_adminInfoId
		);
	}

}
