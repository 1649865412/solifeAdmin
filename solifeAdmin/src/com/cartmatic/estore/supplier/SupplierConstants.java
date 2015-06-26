package com.cartmatic.estore.supplier;

public class SupplierConstants {
	public final static Integer DEFAULT_SUPPLIER_ID = Integer.valueOf("-1");
	
	public final static Short PO_STATUS_PROCESSING = Short.valueOf("1");
	public final static Short PO_STATUS_SHIPED = Short.valueOf("2");
	public final static Short PO_STATUS_COMPLETED = Short.valueOf("3");
	public final static Short PO_STATUS_CANCELED = Short.valueOf("4");
	
	public final static Short PO_ITEM_STATUS_NEW = Short.valueOf("0");
	public final static Short PO_ITEM_STATUS_PROCESSING = Short.valueOf("1");
	public final static Short PO_ITEM_STATUS_COMPLETED = Short.valueOf("2");
}
