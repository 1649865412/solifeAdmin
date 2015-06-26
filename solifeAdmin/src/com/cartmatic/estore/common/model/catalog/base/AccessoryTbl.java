package com.cartmatic.estore.common.model.catalog.base;

import java.io.Serializable;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.core.model.BaseObject;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * Accessory Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class AccessoryTbl extends BaseObject implements Serializable {

    protected Integer accessoryId;
	protected String accessoryName;
	protected java.math.BigDecimal price;
	protected Integer sortOrder;
	protected Integer version;
	protected com.cartmatic.estore.common.model.catalog.AccessoryGroup accessoryGroup;

	protected java.util.Set<Product> products = new java.util.HashSet<Product>();

	/**
	 * Default Empty Constructor for class Accessory
	 */
	public AccessoryTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class Accessory
	 */
	public AccessoryTbl (
		 Integer in_accessoryId
        ) {
		this.setAccessoryId(in_accessoryId);
    }

	
	public com.cartmatic.estore.common.model.catalog.AccessoryGroup getAccessoryGroup () {
		return accessoryGroup;
	}	
	
	public void setAccessoryGroup (com.cartmatic.estore.common.model.catalog.AccessoryGroup in_accessoryGroup) {
		this.accessoryGroup = in_accessoryGroup;
	}

	public java.util.Set<Product> getProducts() {
		return products;
	}

	public void setProducts(java.util.Set<Product> products) {
		this.products = products;
	}

	/**
	 * 	 * @return Integer
     * @hibernate.id column="accessoryId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getAccessoryId() {
		return this.accessoryId;
	}
	
	/**
	 * Set the accessoryId
	 */	
	public void setAccessoryId(Integer aValue) {
		this.accessoryId = aValue;
	}	

	/**
	 * 	 * @return Integer
	 */
	public Integer getAccessoryGroupId() {
		return this.getAccessoryGroup()==null?null:this.getAccessoryGroup().getAccessoryGroupId();
	}
	
	/**
	 * Set the accessoryGroupId
	 */	
	public void setAccessoryGroupId(Integer aValue) {
	    if (aValue==null) {
	    	accessoryGroup = null;
	    } else {
	        accessoryGroup = new com.cartmatic.estore.common.model.catalog.AccessoryGroup(aValue);
	        accessoryGroup.setVersion(new Integer(0));//set a version to cheat hibernate only
	    }
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="accessoryName" type="java.lang.String" length="32" not-null="true" unique="false"
	 */
	public String getAccessoryName() {
		return this.accessoryName;
	}
	
	/**
	 * Set the accessoryName
	 * @spring.validator type="required"
	 */	
	public void setAccessoryName(String aValue) {
		this.accessoryName = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="price" type="java.math.BigDecimal" length="12" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getPrice() {
		return this.price;
	}
	
	/**
	 * Set the price
	 */	
	public void setPrice(java.math.BigDecimal aValue) {
		this.price = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="sortOrder" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getSortOrder() {
		return this.sortOrder;
	}
	
	/**
	 * Set the sortOrder
	 */	
	public void setSortOrder(Integer aValue) {
		this.sortOrder = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="version" type="java.lang.Integer" length="10" not-null="true" unique="false"
	 */
	public Integer getVersion() {
		return this.version;
	}
	
	/**
	 * Set the version
	 * @spring.validator type="required"
	 */	
	public void setVersion(Integer aValue) {
		this.version = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof AccessoryTbl)) {
			return false;
		}
		AccessoryTbl rhs = (AccessoryTbl) object;
		return new EqualsBuilder()
				.append(this.accessoryId, rhs.accessoryId)
						.append(this.accessoryName, rhs.accessoryName)
				.append(this.price, rhs.price)
				.append(this.sortOrder, rhs.sortOrder)
				.append(this.version, rhs.version)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.accessoryId) 
						.append(this.accessoryName) 
				.append(this.price) 
				.append(this.sortOrder) 
				.append(this.version) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("accessoryId", this.accessoryId) 
						.append("accessoryName", this.accessoryName) 
				.append("price", this.price) 
				.append("sortOrder", this.sortOrder) 
				.append("version", this.version) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "accessoryId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return accessoryId;
	}

}