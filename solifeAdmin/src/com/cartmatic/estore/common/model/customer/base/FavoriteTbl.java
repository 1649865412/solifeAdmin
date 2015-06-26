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
public class FavoriteTbl extends BaseObject implements Serializable {

    protected Integer favoriteId;
	protected java.util.Date createTime;
	protected Integer version;
	protected Customer customer;
	protected Product product;
	protected Store store;


	/**
	 * Default Empty Constructor for class Favorite
	 */
	public FavoriteTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class Favorite
	 */
	public FavoriteTbl (
		 Integer in_favoriteId
        ) {
		this.setFavoriteId(in_favoriteId);
    }

	
	public com.cartmatic.estore.common.model.customer.Customer getCustomer () {
		return customer;
	}	
	
	public void setCustomer (com.cartmatic.estore.common.model.customer.Customer in_customer) {
		this.customer = in_customer;
	}
    
	public Product getProduct() {
		return product;
	}	
	
	public void setProduct(Product avalue) {
		this.product = avalue;
	}

	/**
	 * 	 * @return Integer
     * @hibernate.id column="favoriteId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getFavoriteId() {
		return this.favoriteId;
	}
	
	/**
	 * Set the favoriteId
	 */	
	public void setFavoriteId(Integer aValue) {
		this.favoriteId = aValue;
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
	 */
	public Integer getProductId() {
		return this.getProduct()==null?null:this.getProduct().getProductId();
	}
	
	/**
	 * Set the customerId
	 */	
	public void setProductId(Integer aValue) {
	    if (aValue==null) {
	    	product = null;
	    } else {
	    	product = new Product(aValue);
	    	product.setVersion(new Integer(0));//set a version to cheat hibernate only
	    }
	}	

	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="createTime" type="java.util.Date" length="0" not-null="true" unique="false"
	 */
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	
	/**
	 * Set the createTime
	 * @spring.validator type="required"
	 */	
	public void setCreateTime(java.util.Date aValue) {
		this.createTime = aValue;
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
		if (!(object instanceof FavoriteTbl)) {
			return false;
		}
		FavoriteTbl rhs = (FavoriteTbl) object;
		return new EqualsBuilder()
				.append(this.favoriteId, rhs.favoriteId)
				.append(this.createTime, rhs.createTime)
				.append(this.version, rhs.version)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.favoriteId) 
				.append(this.createTime) 
				.append(this.version) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("favoriteId", this.favoriteId) 
				.append("createTime", this.createTime) 
				.append("version", this.version) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "favoriteId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return favoriteId;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}
	
	/**
	 * 	 * @return Integer
	 */
	public Integer getStoreId() {
		return this.getStore()==null?null:this.getStore().getStoreId();
	}
	
	/**
	 * Set the storeId
	 */	
	public void setStoreId(Integer aValue) {
	    if (aValue==null) {
	    	store = null;
	    } else {
	    	store = new Store(aValue);
	    	store.setVersion(new Integer(0));//set a version to cheat hibernate only
	    }
	}
}