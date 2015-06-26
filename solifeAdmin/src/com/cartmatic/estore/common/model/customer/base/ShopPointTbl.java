package com.cartmatic.estore.common.model.customer.base;

import java.io.Serializable;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.common.model.customer.Customer;
import com.cartmatic.estore.core.model.BaseObject;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * ShopPoint Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class ShopPointTbl extends BaseObject implements Serializable {

    protected Integer shopPointId;
	protected Integer total;
	protected Integer gainedTotal;
	protected Integer usedTotal;
	protected java.util.Date updateTime;
	protected Customer customer;


	/**
	 * Default Empty Constructor for class ShopPoint
	 */
	public ShopPointTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class ShopPoint
	 */
	public ShopPointTbl (
		 Integer in_shopPointId
        ) {
		this.setShopPointId(in_shopPointId);
    }

	
	public com.cartmatic.estore.common.model.customer.Customer getCustomer () {
		return customer;
	}	
	
	public void setCustomer (com.cartmatic.estore.common.model.customer.Customer in_customer) {
		this.customer = in_customer;
	}

	/**
	 * 	 * @return Integer
     * @hibernate.id column="shopPointId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getShopPointId() {
		return this.shopPointId;
	}
	
	/**
	 * Set the shopPointId
	 */	
	public void setShopPointId(Integer aValue) {
		this.shopPointId = aValue;
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
	 * 	 * @return Integer
	 * @hibernate.property column="total" type="java.lang.Integer" length="10" not-null="true" unique="false"
	 */
	public Integer getTotal() {
		return this.total;
	}
	
	/**
	 * Set the total
	 * @spring.validator type="required"
	 */	
	public void setTotal(Integer aValue) {
		this.total = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="gainedTotal" type="java.lang.Integer" length="10" not-null="true" unique="false"
	 */
	public Integer getGainedTotal() {
		return this.gainedTotal;
	}
	
	/**
	 * Set the gainedTotal
	 * @spring.validator type="required"
	 */	
	public void setGainedTotal(Integer aValue) {
		this.gainedTotal = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="usedTotal" type="java.lang.Integer" length="10" not-null="true" unique="false"
	 */
	public Integer getUsedTotal() {
		return this.usedTotal;
	}
	
	/**
	 * Set the usedTotal
	 * @spring.validator type="required"
	 */	
	public void setUsedTotal(Integer aValue) {
		this.usedTotal = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="updateTime" type="java.util.Date" length="0" not-null="false" unique="false"
	 */
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}
	
	/**
	 * Set the updateTime
	 */	
	public void setUpdateTime(java.util.Date aValue) {
		this.updateTime = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof ShopPointTbl)) {
			return false;
		}
		ShopPointTbl rhs = (ShopPointTbl) object;
		return new EqualsBuilder()
				.append(this.shopPointId, rhs.shopPointId)
						.append(this.total, rhs.total)
				.append(this.gainedTotal, rhs.gainedTotal)
				.append(this.usedTotal, rhs.usedTotal)
				.append(this.updateTime, rhs.updateTime)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.shopPointId) 
						.append(this.total) 
				.append(this.gainedTotal) 
				.append(this.usedTotal) 
				.append(this.updateTime) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("shopPointId", this.shopPointId) 
						.append("total", this.total) 
				.append("gainedTotal", this.gainedTotal) 
				.append("usedTotal", this.usedTotal) 
				.append("updateTime", this.updateTime) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "shopPointId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return shopPointId;
	}

}