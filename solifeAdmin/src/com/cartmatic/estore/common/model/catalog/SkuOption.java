package com.cartmatic.estore.common.model.catalog;

import com.cartmatic.estore.common.model.catalog.base.SkuOptionTbl;

/**
 * Model class for SkuOption. Add not database mapped fileds in this class.
 */
public class SkuOption extends SkuOptionTbl {
	/**
	 * 本选项属性是否与产品类型有关联，默认是
	 */
	private boolean refProductType=true;

  	/**
	 * Default Empty Constructor for class SkuOption
	 */
	public SkuOption () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class SkuOption
	 */
	public SkuOption (
		 Integer in_skuOptionId
		) {
		super (
		  in_skuOptionId
		);
	}

	public boolean isRefProductType() {
		return refProductType;
	}

	public void setRefProductType(boolean refProductType) {
		this.refProductType = refProductType;
	}




}
