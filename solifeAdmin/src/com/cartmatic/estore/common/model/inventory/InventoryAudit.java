package com.cartmatic.estore.common.model.inventory;

import com.cartmatic.estore.common.model.inventory.base.InventoryAuditTbl;

/**
 * Model class for InventoryAudit. Add not database mapped fileds in this class.
 */
public class InventoryAudit extends InventoryAuditTbl {

  	/**
	 * Default Empty Constructor for class InventoryAudit
	 */
	public InventoryAudit () {
		super();
	}
	
	/**
	 * 定义实体的业务名取值； inventoryAuditName
	 * 必须手工完成这个部分，否则编译不通过。
	 */
	public String getInventoryAuditName () {
		if (inventoryAuditId == null)
	        return "";
	    else
			//返回一个指定有业务意义的属性值;
			//如：product的VO就用product.productName
	        return this.inventoryAuditId.toString();
	}
	
	/**
	 * Default Key Fields Constructor for class InventoryAudit
	 */
	public InventoryAudit (
		 Integer in_inventoryAuditId
		) {
		super (
		  in_inventoryAuditId
		);
	}

}
