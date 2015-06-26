package com.cartmatic.estore.common.model.customer.base;

import java.io.Serializable;
import java.util.HashSet;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.cartmatic.estore.common.model.attribute.CustomerAttrValue;
import com.cartmatic.estore.common.model.cart.Shoppingcart;
import com.cartmatic.estore.common.model.customer.Customer;
import com.cartmatic.estore.common.model.customer.Favorite;
import com.cartmatic.estore.common.model.supplier.Supplier;
import com.cartmatic.estore.common.model.system.AppUser;
import com.cartmatic.estore.common.model.system.Store;

/**
 * Customer Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 */
public class CustomerTbl extends AppUser implements Serializable {
 
	protected String passwordHint;
	protected String passwordHintAnswer;
	protected String customerPosition;
	protected java.util.Date registerTime;
	protected Integer totalPoints;
	protected String note;

	protected String registerIpAddress;
	
	protected com.cartmatic.estore.common.model.customer.Membership membership;
	
	private java.util.Set<CustomerAttrValue> customerAttrValues = new HashSet<CustomerAttrValue>();
	protected java.util.Set<Favorite> favorites = new java.util.HashSet<Favorite>();
	
	protected Supplier supplier;
	
	protected Store store;
	/**
	 * 	 * @return Integer
	 */
	public Integer getMembershipId() {
		return this.getMembership()==null?null:this.getMembership().getMembershipId();
	}
	
	/**
	 * Set the membershipId
	 */	
	public void setMembershipId(Integer aValue) {
	    if (aValue==null) {
	    	membership = null;
	    } else {
	        membership = new com.cartmatic.estore.common.model.customer.Membership(aValue);
	        membership.setVersion(new Integer(0));//set a version to cheat hibernate only
	    }
	}	
	
	public com.cartmatic.estore.common.model.customer.Membership getMembership () {
		return membership;
	}	
	
	public void setMembership (com.cartmatic.estore.common.model.customer.Membership in_membership) {
		this.membership = in_membership;
	}
	public java.util.Set<CustomerAttrValue> getCustomerAttrValues() {
		return customerAttrValues;
	}

	public void setCustomerAttrValues(java.util.Set<CustomerAttrValue> customerAttrValues) {
		this.customerAttrValues = customerAttrValues;
	} 

	
	public java.util.Set<Favorite> getFavorites() {
		return favorites;
	}

	public void setFavorites(java.util.Set<Favorite> favorites) {
		this.favorites = favorites;
	}

	/**
	 * Default Empty Constructor for class Customer
	 */
	public CustomerTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class Customer
	 */
	public CustomerTbl (
		 Integer in_customerId
        ) {
		this.setCustomerId(in_customerId);
    }

	/**
	 * @return Integer
     * @hibernate.id column="customerId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getCustomerId() {
		return this.appuserId;
	}
	
	/**
	 * Set the customerId
	 */	
	public void setCustomerId(Integer aValue) {
		this.appuserId = aValue;
	}	

	

	/**
	 * @return String
	 * @hibernate.property column="passwordHint" type="java.lang.String" length="128" not-null="false" unique="false"
	 */
	public String getPasswordHint() {
		return this.passwordHint;
	}
	
	/**
	 * Set the passwordHint
	 */	
	public void setPasswordHint(String aValue) {
		this.passwordHint = aValue;
	}	

	/**
	 * @return String
	 * @hibernate.property column="passwordHintAnswer" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getPasswordHintAnswer() {
		return this.passwordHintAnswer;
	}
	
	/**
	 * Set the passwordHintAnswer
	 */	
	public void setPasswordHintAnswer(String aValue) {
		this.passwordHintAnswer = aValue;
	}	

	/**
	 * @return String
	 * @hibernate.property column="customerPosition" type="java.lang.String" length="128" not-null="false" unique="false"
	 */
	public String getCustomerPosition() {
		return this.customerPosition;
	}
	
	/**
	 * Set the customerPosition
	 */	
	public void setCustomerPosition(String aValue) {
		this.customerPosition = aValue;
	}	

	/**
	 * @return java.util.Date
	 * @hibernate.property column="registerTime" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getRegisterTime() {
		return this.registerTime;
	}
	
	/**
	 * Set the registerTime
	 */	
	public void setRegisterTime(java.util.Date aValue) {
		this.registerTime = aValue;
	}	

    /**
     * return the note for current customer
     * @return
     */
    public String getNote() {
        return note;
    }
    /**
     * set not for current customer.
     * @param note
     */
    public void setNote(String note) {
        this.note = note;
    }
    
	public void setRegisterIpAddress(String registerIpAddress) {
		this.registerIpAddress = registerIpAddress;
	}
	
	public String getRegisterIpAddress() {
		return registerIpAddress;
	}

	/**
     * @see java.lang.Object#equals(Object)
     */
	public boolean equals(Object object) {
		if (!(object instanceof Customer)) {
			return false;
		}
		Customer rhs = (Customer) object;
		return new EqualsBuilder()
				.append(this.appuserId, rhs.appuserId)
						.append(this.passwordHint, rhs.passwordHint)
				.append(this.passwordHintAnswer, rhs.passwordHintAnswer)
				.append(this.customerPosition, rhs.customerPosition)
				.append(this.registerTime, rhs.registerTime)
				.append(this.version, rhs.version)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.appuserId) 
						.append(this.passwordHint) 
				.append(this.passwordHintAnswer) 
				.append(this.customerPosition) 
				.append(this.registerTime) 
				.append(this.version) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("customerId", this.appuserId) 
						.append("passwordHint", this.passwordHint) 
				.append("passwordHintAnswer", this.passwordHintAnswer) 
				.append("customerPosition", this.customerPosition) 
				.append("registerTime", this.registerTime) 
				.append("version", this.version) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "customerId";
	}
	
    public Integer getTotalPoints() {
        return totalPoints;
    }
    public void setTotalPoints(Integer totalPoints) {
        this.totalPoints = totalPoints;
    }
    
    public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
    
    /**
	 * 	 * @return Integer
	 */
	public Integer getSupplierId() {
		return this.getSupplier()==null?null:this.getSupplier().getSupplierId();
	}
	
	/**
	 * Set the supplierId
	 */	
	public void setSupplierId(Integer aValue) {
	    if (aValue==null) {
	    	supplier = null;
	    } else {
	    	supplier = new Supplier(aValue);
	    	supplier.setVersion(new Integer(0));//set a version to cheat hibernate only
	    }
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