package com.cartmatic.estore.common.model.catalog;

import java.util.ArrayList;
import java.util.List;

import com.cartmatic.estore.common.model.attribute.ProductAttrValue;
import com.cartmatic.estore.common.model.catalog.base.ProductAttGroupTbl;

/**
 * Model class for ProductAttGroup. Add not database mapped fileds in this class.
 */
public class ProductAttGroup extends ProductAttGroupTbl {
	
	/**
	 * 辅助前台显示自定义属性
	 */
	private List<ProductAttrValue> productAttrValues;

  	/**
	 * Default Empty Constructor for class ProductAttGroup
	 */
	public ProductAttGroup () {
		super();
	}
	

	/**
	 * Default Key Fields Constructor for class ProductAttGroup
	 */
	public ProductAttGroup (
		 Integer in_productAttGroupId
		) {
		super (
		  in_productAttGroupId 
		);
	}


	public List<ProductAttrValue> getProductAttrValues() {
		return productAttrValues;
	}


	public void setProductAttrValues(List<ProductAttrValue> productAttrValues) {
		this.productAttrValues = productAttrValues;
	}

}
