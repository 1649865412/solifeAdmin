/*
 * create date:2006-6-30
 */
package com.cartmatic.estore.sales.model;

/**
 * @author ChaoMin Yan
 * 2006-6-30
 * TODO 
 */
public class OrderStatus{
	private String statusNameKey;
	private String status;
	private String checked="0";
	public OrderStatus(String nameKey,String status){
		this.statusNameKey=nameKey;
		this.status=status;
	}
	
	/**
	 * @return statusNameKey
	 */
	public String getStatusNameKey() {
		return statusNameKey;
	}
	/**
	 * @param statusNameKey setter statusNameKey
	 */
	public void setStatusNameKey(String statusNameKey) {
		this.statusNameKey = statusNameKey;
	}
	/**
	 * @return checked
	 */
	public String getChecked() {
		return checked;
	}
	/**
	 * @param checked setter checked
	 */
	public void setChecked(String checked) {
		this.checked = checked;
	}
	/**
	 * @return status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status setter status
	 */
	public void setStatus(String status) {
		this.status = status;
	}
}