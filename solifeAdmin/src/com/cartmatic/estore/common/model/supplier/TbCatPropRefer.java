package com.cartmatic.estore.common.model.supplier;

import com.cartmatic.estore.common.model.supplier.base.TbCatPropReferTbl;

/**
 * Model class for TbCatPropRefer. Add not database mapped fileds in this class.
 */
public class TbCatPropRefer extends TbCatPropReferTbl {

  	/**
	 * Default Empty Constructor for class TbCatPropRefer
	 */
	public TbCatPropRefer () {
		super();
	}
	
	/**
	 * 定义实体的业务名取值； tbCatPropReferName
	 * 必须手工完成这个部分，否则编译不通过。
	 */
	public String getTbCatPropReferName () {
		if (tbCatPropReferId == null)
	        return "";
	    else
			//返回一个指定有业务意义的属性值;
			//如：product的VO就用product.productName
	        return this.tbCatPropName;
	}
	
	/**
	 * Default Key Fields Constructor for class TbCatPropRefer
	 */
	public TbCatPropRefer (
		 Integer in_tbCatPropReferId
		) {
		super (
		  in_tbCatPropReferId
		);
	}

}
