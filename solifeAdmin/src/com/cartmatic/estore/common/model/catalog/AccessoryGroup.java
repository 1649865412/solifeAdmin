package com.cartmatic.estore.common.model.catalog;

import com.cartmatic.estore.common.model.catalog.base.AccessoryGroupTbl;

/**
 * Model class for AccessoryGroup. Add not database mapped fileds in this class.
 */
public class AccessoryGroup extends AccessoryGroupTbl {

  	/**
	 * Default Empty Constructor for class AccessoryGroup
	 */
	public AccessoryGroup () {
		super();
	}
	
	/**
	 * 定义实体的业务名取值； accessoryGroupName
	 * 必须手工完成这个部分，否则编译不通过。
	 */
	public String getAccessoryGroupName () {
		if (accessoryGroupId == null)
	        return "";
	    else
			//返回一个指定有业务意义的属性值;
			//如：product的VO就用product.productName
	        return this.groupName;
	}
	
	/**
	 * Default Key Fields Constructor for class AccessoryGroup
	 */
	public AccessoryGroup (
		 Integer in_accessoryGroupId
		) {
		super (
		  in_accessoryGroupId
		);
	}

}
