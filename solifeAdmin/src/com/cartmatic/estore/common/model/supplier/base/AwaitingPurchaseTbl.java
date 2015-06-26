package com.cartmatic.estore.common.model.supplier.base;

import java.io.Serializable;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.common.model.supplier.Supplier;
import com.cartmatic.estore.core.model.BaseObject;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * AwaitingPurchase Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class AwaitingPurchaseTbl extends BaseObject implements Serializable {

    //protected Integer supplierId;
	protected Integer quantity;
	protected Supplier supplier;

	/**
	 * Default Empty Constructor for class AwaitingPurchase
	 */
	public AwaitingPurchaseTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class AwaitingPurchase
	 */
	public AwaitingPurchaseTbl (
		 Integer in_supplierId
        ) {
		this.setSupplierId(in_supplierId);
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

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="quantity" type="java.lang.Integer" length="10" not-null="true" unique="false"
	 */
	public Integer getQuantity() {
		return this.quantity;
	}
	
	/**
	 * Set the quantity
	 * @spring.validator type="required"
	 */	
	public void setQuantity(Integer aValue) {
		this.quantity = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof AwaitingPurchaseTbl)) {
			return false;
		}
		AwaitingPurchaseTbl rhs = (AwaitingPurchaseTbl) object;
		return new EqualsBuilder()
				.append(this.supplier.supplierId, rhs.supplier.supplierId)
				.append(this.quantity, rhs.quantity)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.supplier.supplierId) 
				.append(this.quantity) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("supplierId", this.supplier.supplierId) 
				.append("quantity", this.quantity) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "supplierId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return supplier.supplierId;
	}

}