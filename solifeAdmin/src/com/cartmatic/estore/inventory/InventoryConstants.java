package com.cartmatic.estore.inventory;

public class InventoryConstants {
	
	/**
	 * 操作类型,调整Sku库存数量（添加库存、减少库存）（后台库存操作）
	 */
	public static final Short	INVENTORY_AUDIT_EVENT_TYPE_STOCK_ADJUSTMENT=new Short("1");
	
	/**
	 * 操作类型,Sku库存数量分配
	 */
	public static final Short	INVENTORY_AUDIT_EVENT_TYPE_STOCK_ALLOCATE=new Short("2");
	
	/**
	 * 操作类型,取消某Sku库存分配
	 */
	public static final Short	INVENTORY_AUDIT_EVENT_TYPE_STOCK_DEALLOCATE=new Short("3");
	
	/**
	 * 操作类型,释放sku库存（真正发货）
	 */
	public static final Short	INVENTORY_AUDIT_EVENT_TYPE_STOCK_RELEASE=new Short("4");
	
	/**
	 * 操作者，系统触发操作
	 */
	public static final String	INVENTORY_AUDIT_EVENT_HANDLER_SYSTEM="System";
	
	/**
	 * 操作者，前台触发操作
	 */
	public static final String	INVENTORY_AUDIT_EVENT_HANDLER_STOREFRONT="StoreFront";
	
	
}
