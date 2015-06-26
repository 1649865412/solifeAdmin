package com.cartmatic.estore.common.model.catalog;

import com.cartmatic.estore.common.model.catalog.base.ProductPackageItemTbl;

/**
 * Model class for ProductPackageItem. Add not database mapped fileds in this class.
 */
public class ProductPackageItem extends ProductPackageItemTbl {

  	/**
	 * Default Empty Constructor for class ProductPackageItem
	 */
	public ProductPackageItem () {
		super();
	}
	
	/**
	 * 定义实体的业务名取值； productPackageItemName
	 * 必须手工完成这个部分，否则编译不通过。
	 */
	public String getProductPackageItemName () {
		if (productPackageItemId == null)
	        return "";
	    else
			//返回一个指定有业务意义的属性值;
			//如：product的VO就用product.productName
	        return this.productPackageItemId.toString();
	}
	
	/**
	 * Default Key Fields Constructor for class ProductPackageItem
	 */
	public ProductPackageItem (
		 Integer in_productPackageItemId
		) {
		super (
		  in_productPackageItemId
		);
	}

}
