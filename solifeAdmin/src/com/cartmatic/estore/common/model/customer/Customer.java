package com.cartmatic.estore.common.model.customer;

import net.sf.json.JSONObject;

import com.cartmatic.estore.common.model.customer.base.CustomerTbl;

/**
 * Model - Business object
 * This file won't get overwritten.
 */
public class Customer extends CustomerTbl {
	
	private String inviteCustomer;

  	/**
	 * Default Empty Constructor for class Customer
	 */
	public Customer () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class Customer
	 */
	public Customer (
		 Integer in_customerId
		) {
		super (
		  in_customerId
		);
	}
	
	public boolean getIsSupplier(){
		return getSupplierId()!=null;
	}
	
	/**
	 * 构建简单的Json对象主要用于选择器
	 * @return
	 */
	public String getJsonObject(){
		JSONObject jsonSupplier=new JSONObject();
		jsonSupplier.put("appuserId",this.appuserId);
		jsonSupplier.put("username",this.username);
		jsonSupplier.put("email", this.email);
		jsonSupplier.put("firstname", this.firstname);
		jsonSupplier.put("lastname", this.lastname);
		if(this.supplier!=null)
			jsonSupplier.put("supplierId", this.supplier.getSupplierId());
		return jsonSupplier.toString();
	}

	public String getInviteCustomer() {
		return inviteCustomer;
	}

	public void setInviteCustomer(String inviteCustomer) {
		this.inviteCustomer = inviteCustomer;
	} 
	
}
