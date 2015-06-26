package com.cartmatic.estore.common.model.attribute.base;

import java.io.Serializable;
import java.util.Date;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.common.model.attribute.BaseAttributeValue;
import com.cartmatic.estore.core.model.BaseObject;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * CustomerAttrValue Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class CustomerAttrValueTbl extends BaseAttributeValue implements Serializable {

    protected Integer customerAttrValueId;
	protected com.cartmatic.estore.common.model.customer.Customer customer;


	/**
	 * Default Empty Constructor for class CustomerAttrValue
	 */
	public CustomerAttrValueTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class CustomerAttrValue
	 */
	public CustomerAttrValueTbl (
		 Integer in_customerAttrValueId
        ) {
		this.setCustomerAttrValueId(in_customerAttrValueId);
    }

	
	public com.cartmatic.estore.common.model.customer.Customer getCustomer () {
		return customer;
	}	
	
	public void setCustomer (com.cartmatic.estore.common.model.customer.Customer in_customer) {
		this.customer = in_customer;
	}
	
	/**
	 * 	 * @return Integer
     * @hibernate.id column="customerAttrValueId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getCustomerAttrValueId() {
		return this.customerAttrValueId;
	}
	
	/**
	 * Set the customerAttrValueId
	 */	
	public void setCustomerAttrValueId(Integer aValue) {
		this.customerAttrValueId = aValue;
	}	

	/**
	 * 	 * @return Integer
	 */
	public Integer getAttributeId() {
		return this.getAttribute()==null?null:this.getAttribute().getAttributeId();
	}
	
	/**
	 * Set the attributeId
	 */	
	public void setAttributeId(Integer aValue) {
	    if (aValue==null) {
	    	attribute = null;
	    } else if (attribute == null) {
	        attribute = new com.cartmatic.estore.common.model.attribute.Attribute(aValue);
	        attribute.setVersion(new Integer(0));//set a version to cheat hibernate only
	    } else {
			attribute.setAttributeId(aValue);
	    }
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
	    } else if (customer == null) {
	        customer = new com.cartmatic.estore.common.model.customer.Customer(aValue);
	        customer.setVersion(new Integer(0));//set a version to cheat hibernate only
	    } else {
			customer.setCustomerId(aValue);
	    }
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof CustomerAttrValueTbl)) {
			return false;
		}
		CustomerAttrValueTbl rhs = (CustomerAttrValueTbl) object;
		return new EqualsBuilder()
				.append(this.customerAttrValueId, rhs.customerAttrValueId)
								.append(this.shortTextValue, rhs.shortTextValue)
				.append(this.longTextValue, rhs.longTextValue)
				.append(this.intValue, rhs.intValue)
				.append(this.decimalValue, rhs.decimalValue)
				.append(this.booleanValue, rhs.booleanValue)
				.append(this.attributeDataType, rhs.attributeDataType)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.customerAttrValueId) 
								.append(this.shortTextValue) 
				.append(this.longTextValue) 
				.append(this.intValue) 
				.append(this.decimalValue) 
				.append(this.booleanValue) 
				.append(this.attributeDataType) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("customerAttrValueId", this.customerAttrValueId) 
								.append("shortTextValue", this.shortTextValue) 
				.append("longTextValue", this.longTextValue) 
				.append("intValue", this.intValue) 
				.append("decimalValue", this.decimalValue) 
				.append("booleanValue", this.booleanValue) 
				.append("attributeDateType", this.attributeDataType) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "customerAttrValueId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return customerAttrValueId;
	}

}