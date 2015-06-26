package com.cartmatic.estore.common.model.content;

import com.cartmatic.estore.common.model.content.base.AdPositionTypeTbl;

/**
 * Model class for AdPositionType. Add not database mapped fileds in this class.
 */
public class AdPositionType extends AdPositionTypeTbl {

  	/**
	 * Default Empty Constructor for class AdPositionType
	 */
	public AdPositionType () {
		super();
	}
	
	/**
	 * 定义实体的业务名取值； adPositionTypeName
	 * 必须手工完成这个部分，否则编译不通过。
	 */
	public String getAdPositionTypeName () {
		if (adPositionTypeId == null)
	        return "";
	    else
			//返回一个指定有业务意义的属性值;
			//如：product的VO就用product.productName
	        return this.positionName;
	}
	
	/**
	 * Default Key Fields Constructor for class AdPositionType
	 */
	public AdPositionType (
		 Integer in_adPositionTypeId
		) {
		super (
		  in_adPositionTypeId
		);
	}

}
