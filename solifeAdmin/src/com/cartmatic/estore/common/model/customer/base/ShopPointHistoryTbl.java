package com.cartmatic.estore.common.model.customer.base;

import java.io.Serializable;

import com.cartmatic.estore.common.model.customer.Customer;
import com.cartmatic.estore.core.model.BaseObject;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * ShopPointHistory Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class ShopPointHistoryTbl extends BaseObject implements Serializable {

    protected Integer shopPointHistoryId;
	protected Short shopPointType;
	protected String description;
	protected Integer amount;
	protected java.util.Date createTime;
	protected Customer customer;


	/**
	 * Default Empty Constructor for class ShopPointHistory
	 */
	public ShopPointHistoryTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class ShopPointHistory
	 */
	public ShopPointHistoryTbl (
		 Integer in_shopPointHistoryId
        ) {
		this.setShopPointHistoryId(in_shopPointHistoryId);
    }

	public com.cartmatic.estore.common.model.customer.Customer getCustomer () {
		return customer;
	}	
	
	public void setCustomer (com.cartmatic.estore.common.model.customer.Customer in_customer) {
		this.customer = in_customer;
	}

	/**
	 * 	 * @return Integer
     * @hibernate.id column="shopPointHistoryId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getShopPointHistoryId() {
		return this.shopPointHistoryId;
	}
	
	/**
	 * Set the shopPointHistoryId
	 */	
	public void setShopPointHistoryId(Integer aValue) {
		this.shopPointHistoryId = aValue;
	}	

	/**
	 * 	 * @return Integer
	 */
	public Integer getCustomerId() {
		return this.getCustomer()==null?null:this.getCustomer().getCustomerId();
	}
	
	/**
	 * Set the customerId
	 */	
	public void setCustomerId(Integer aValue) {
	    if (aValue==null) {
	    	customer = null;
	    } else {
	    	customer = new Customer(aValue);
	    	customer.setVersion(new Integer(0));//set a version to cheat hibernate only
	    }
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="shopPointType" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getShopPointType() {
		return this.shopPointType;
	}
	
	/**
	 * Set the shopPointType
	 * @spring.validator type="required"
	 */	
	public void setShopPointType(Short aValue) {
		this.shopPointType = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="description" type="java.lang.String" length="128" not-null="false" unique="false"
	 */
	public String getDescription() {
		return this.description;
	}
	
	/**
	 * Set the description
	 */	
	public void setDescription(String aValue) {
		this.description = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="amount" type="java.lang.Integer" length="10" not-null="true" unique="false"
	 */
	public Integer getAmount() {
		return this.amount;
	}
	
	/**
	 * Set the amount
	 * @spring.validator type="required"
	 */	
	public void setAmount(Integer aValue) {
		this.amount = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="createTime" type="java.util.Date" length="0" not-null="false" unique="false"
	 */
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	
	/**
	 * Set the createTime
	 */	
	public void setCreateTime(java.util.Date aValue) {
		this.createTime = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof ShopPointHistoryTbl)) {
			return false;
		}
		ShopPointHistoryTbl rhs = (ShopPointHistoryTbl) object;
		return new EqualsBuilder()
				.append(this.shopPointHistoryId, rhs.shopPointHistoryId)
						.append(this.shopPointType, rhs.shopPointType)
				.append(this.description, rhs.description)
				.append(this.amount, rhs.amount)
				.append(this.createTime, rhs.createTime)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.shopPointHistoryId) 
						.append(this.shopPointType) 
				.append(this.description) 
				.append(this.amount) 
				.append(this.createTime) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("shopPointHistoryId", this.shopPointHistoryId) 
						.append("shopPointType", this.shopPointType) 
				.append("description", this.description) 
				.append("amount", this.amount) 
				.append("createTime", this.createTime) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "shopPointHistoryId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return shopPointHistoryId;
	}

}