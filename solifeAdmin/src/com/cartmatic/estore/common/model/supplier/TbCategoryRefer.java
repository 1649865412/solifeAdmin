package com.cartmatic.estore.common.model.supplier;

import com.cartmatic.estore.common.model.supplier.base.TbCategoryReferTbl;

/**
 * Model class for TbCategoryRefer. Add not database mapped fileds in this class.
 */
public class TbCategoryRefer extends TbCategoryReferTbl {

  	/**
	 * Default Empty Constructor for class TbCategoryRefer
	 */
	public TbCategoryRefer () {
		super();
	}
	
	/**
	 * 定义实体的业务名取值； tbCategoryReferName
	 * 必须手工完成这个部分，否则编译不通过。
	 */
	public String getTbCategoryReferName () {
		if (tbCategoryReferId == null)
	        return "";
	    else
			//返回一个指定有业务意义的属性值;
			//如：product的VO就用product.productName
	        return this.tbCategoryName;
	}
	
	/**
	 * Default Key Fields Constructor for class TbCategoryRefer
	 */
	public TbCategoryRefer (
		 Integer in_tbCategoryReferId
		) {
		super (
		  in_tbCategoryReferId
		);
	}

}
