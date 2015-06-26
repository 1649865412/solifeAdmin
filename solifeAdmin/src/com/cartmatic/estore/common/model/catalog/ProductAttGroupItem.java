package com.cartmatic.estore.common.model.catalog;

import com.cartmatic.estore.common.model.attribute.ProductAttrValue;
import com.cartmatic.estore.common.model.catalog.base.ProductAttGroupItemTbl;

/**
 * Model class for ProductAttGroupItem. Add not database mapped fileds in this class.
 */
public class ProductAttGroupItem extends ProductAttGroupItemTbl {
	/**
	 * 保存产品的自定义属性值
	 */

  	/**
	 * Default Empty Constructor for class ProductAttGroupItem
	 */
	public ProductAttGroupItem () {
		super();
	}
	
	/**
	 * 定义实体的业务名取值； productAttGroupItemName
	 * 必须手工完成这个部分，否则编译不通过。
	 */
	public String getProductAttGroupItemName () {
		if (productAttGroupItemId == null)
	        return "";
	    else
			//返回一个指定有业务意义的属性值; 
			//如：product的VO就用product.productName
	        return this.productAttGroupItemId.toString();
	}
	
	/**
	 * Default Key Fields Constructor for class ProductAttGroupItem
	 */
	public ProductAttGroupItem (
		 Integer in_productAttGroupItemId
		) {
		super (
		  in_productAttGroupItemId
		);
	}

}
