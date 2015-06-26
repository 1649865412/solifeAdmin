package com.cartmatic.estore.common.model.system;

import com.cartmatic.estore.common.model.system.base.RegionItemTbl;

/**
 * Model class for RegionItem. Add not database mapped fileds in this class.
 */
public class RegionItem extends RegionItemTbl {
	
	/**
	 * Default Empty Constructor for class RegionItem
	 */
	private String itemName;

  	/**
	 * Default Empty Constructor for class RegionItem
	 */
	public RegionItem () {
		super();
	}
	
	/**
	 * 定义实体的业务名取值； regionItemName
	 * 必须手工完成这个部分，否则编译不通过。
	 */
	public String getRegionItemName () {
		if (regionItemId == null)
	        return "";
	    else
			//返回一个指定有业务意义的属性值;
			//如：product的VO就用product.productName
	        return this.getRegionItemId()+"";
	}
	
	/**
	 * Default Key Fields Constructor for class RegionItem
	 */
	public RegionItem (
		 Integer in_regionItemId
		) {
		super (
		  in_regionItemId
		);
	}
	
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

}
