package com.cartmatic.estore.exception;

import com.cartmatic.estore.catalog.model.BulkField;

public class BulkUpdateException extends Exception{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7103519262145766487L;
	
	private BulkField bulkField;
	
	private String msg;

	public BulkUpdateException(BulkField bulkField,String msg) {
		super();
		this.bulkField=bulkField;
		this.msg=msg;
	}

	public BulkField getBulkField() {
		return bulkField;
	}

	public String getMsg() {
		return msg;
	}

}
