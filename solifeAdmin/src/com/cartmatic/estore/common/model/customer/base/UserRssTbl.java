package com.cartmatic.estore.common.model.customer.base;

import java.io.Serializable;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.common.model.customer.Customer;
import com.cartmatic.estore.common.model.system.Store;
import com.cartmatic.estore.core.model.BaseObject;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * Favorite Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class UserRssTbl extends BaseObject implements Serializable {

	private Integer id;
	
	private String userName;
	
	private String email;
	
	private Integer status;
	
	
	public UserRssTbl()
	{
		super();
	}
	

	public UserRssTbl(Integer id)
	{
		super();
		this.id = id;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}