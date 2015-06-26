package com.cartmatic.estore.common.model.inventory;

import com.cartmatic.estore.common.model.inventory.base.InventoryTbl;

/**
 * Model class for Inventory. Add not database mapped fileds in this class.
 */
public class Inventory extends InventoryTbl {
	
	private Integer availableQuantity;
	
	private Short adjustmentType;

  	public Integer getAvailableQuantity() {
		return this.quantityOnHand-reservedQuantity-allocatedQuantity;
	}

	/**
	 * Default Empty Constructor for class Inventory
	 */
	public Inventory () {
		super();
	}
	
	/**
	 * 定义实体的业务名取值； inventoryName
	 * 必须手工完成这个部分，否则编译不通过。
	 */
	public String getInventoryName () {
		if (inventoryId == null)
	        return "";
	    else
			//返回一个指定有业务意义的属性值;
			//如：product的VO就用product.productName
	        return this.inventoryId.toString();
	}
	
	/**
	 * Default Key Fields Constructor for class Inventory
	 */
	public Inventory (
		 Integer in_inventoryId
		) {
		super (
		  in_inventoryId
		);
	}

}
