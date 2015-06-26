package com.cartmatic.estore.system.model;

import java.io.Serializable;

public class GeoIpCollectorQueue implements Serializable {

	private static final long	serialVersionUID	= -2996696022501930764L;
	private String orderNO;
	private  String customerIp;
	/**
	 *0: PLACE ORDER
	 *1: GOTOPAY
	 *2: SHIPPING ADDRESS
	 *3: BILLING ADDRESS
	 *与SalesOrderGeoip.actionType一致
	 */
	private Short actionType;
	public String getOrderNO() {
		return orderNO;
	}
	public void setOrderNO(String orderNO) {
		this.orderNO = orderNO;
	}
	public String getCustomerIp() {
		return customerIp;
	}
	public void setCustomerIp(String customerIp) {
		this.customerIp = customerIp;
	}
	public Short getActionType() {
		return actionType;
	}
	public void setActionType(Short actionType) {
		this.actionType = actionType;
	}
	
	
	
}
