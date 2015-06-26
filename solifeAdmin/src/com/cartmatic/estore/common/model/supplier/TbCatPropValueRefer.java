package com.cartmatic.estore.common.model.supplier;

import com.cartmatic.estore.common.model.supplier.base.TbCatPropValueReferTbl;

/**
 * Model class for TbCatPropValueRefer. Add not database mapped fileds in this class.
 */
public class TbCatPropValueRefer extends TbCatPropValueReferTbl {

  	/**
	 * Default Empty Constructor for class TbCatPropValueRefer
	 */
	public TbCatPropValueRefer () {
		super();
	}
	
	/**
	 * 定义实体的业务名取值； tbCatPropValueReferName
	 * 必须手工完成这个部分，否则编译不通过。
	 */
	public String getTbCatPropValueReferName () {
		if (tbCatPropValueReferId == null)
	        return "";
	    else
			//返回一个指定有业务意义的属性值;
			//如：product的VO就用product.productName
	        return this.tbCatPropValueName;
	}
	
	/**
	 * Default Key Fields Constructor for class TbCatPropValueRefer
	 */
	public TbCatPropValueRefer (
		 Integer in_tbCatPropValueReferId
		) {
		super (
		  in_tbCatPropValueReferId
		);
	}

}
