package com.cartmatic.estore.common.model.system.base;

import java.io.Serializable;
import com.cartmatic.estore.Constants;
import com.cartmatic.estore.common.model.system.Store;
import com.cartmatic.estore.core.model.BaseObject;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * ShippingMethod Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class ShippingMethodTbl extends BaseObject implements Serializable {

    protected Integer shippingMethodId;
	protected String shippingMethodName;
	protected String shippingMethodDetail;
	protected Short isDomestic;
	protected String deliveryTime;
	protected Short status;
	protected Short isCod;
	protected Short deleted = Constants.MARK_NOT_DELETED;
	protected Integer version;
	protected com.cartmatic.estore.common.model.system.Carrier carrier;

	protected java.util.Set shippingRates = new java.util.HashSet();
	protected java.util.Set<Store> stores = new java.util.HashSet<Store>();
	
	/**
	 * Default Empty Constructor for class ShippingMethod
	 */
	public ShippingMethodTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class ShippingMethod
	 */
	public ShippingMethodTbl (
		 Integer in_shippingMethodId
        ) {
		this.setShippingMethodId(in_shippingMethodId);
    }

	
	public com.cartmatic.estore.common.model.system.Carrier getCarrier () {
		return carrier;
	}	
	
	public void setCarrier (com.cartmatic.estore.common.model.system.Carrier in_carrier) {
		this.carrier = in_carrier;
	}

	public java.util.Set getShippingRates () {
		return shippingRates;
	}	
	
	public void setShippingRates (java.util.Set in_shippingRates) {
		this.shippingRates = in_shippingRates;
	}
    

	/**
	 * 	 * @return Integer
     * @hibernate.id column="shippingMethodId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getShippingMethodId() {
		return this.shippingMethodId;
	}
	
	/**
	 * Set the shippingMethodId
	 */	
	public void setShippingMethodId(Integer aValue) {
		this.shippingMethodId = aValue;
	}	

	/**
	 * 	 * @return Integer
	 */
	public Integer getCarrierId() {
		return this.getCarrier()==null?null:this.getCarrier().getCarrierId();
	}
	
	/**
	 * Set the carrierId
	 */	
	public void setCarrierId(Integer aValue) {
	    if (aValue==null) {
	    	carrier = null;
	    } else {
	        carrier = new com.cartmatic.estore.common.model.system.Carrier(aValue);
	        carrier.setVersion(new Integer(0));//set a version to cheat hibernate only
	    }
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="shippingMethodName" type="java.lang.String" length="64" not-null="true" unique="false"
	 */
	public String getShippingMethodName() {
		return this.shippingMethodName;
	}
	
	/**
	 * Set the shippingMethodName
	 * @spring.validator type="required"
	 */	
	public void setShippingMethodName(String aValue) {
		this.shippingMethodName = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="shippingMethodDetail" type="java.lang.String" length="1024" not-null="false" unique="false"
	 */
	public String getShippingMethodDetail() {
		return this.shippingMethodDetail;
	}
	
	/**
	 * Set the shippingMethodDetail
	 */	
	public void setShippingMethodDetail(String aValue) {
		this.shippingMethodDetail = aValue;
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="isDomestic" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
	public Short getIsDomestic() {
		return this.isDomestic;
	}
	
	/**
	 * Set the isDomestic
	 */	
	public void setIsDomestic(Short aValue) {
		this.isDomestic = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="deliveryTime" type="java.lang.String" length="256" not-null="false" unique="false"
	 */
	public String getDeliveryTime() {
		return this.deliveryTime;
	}
	
	/**
	 * Set the deliveryTime
	 */	
	public void setDeliveryTime(String aValue) {
		this.deliveryTime = aValue;
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="status" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
	public Short getStatus() {
		return this.status;
	}
	
	/**
	 * Set the status
	 */	
	public void setStatus(Short aValue) {
		this.status = aValue;
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="isCod" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
	public Short getIsCod() {
		return this.isCod;
	}
	
	/**
	 * Set the isCod
	 */	
	public void setIsCod(Short aValue) {
		this.isCod = aValue;
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="deleted" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getDeleted() {
		return this.deleted;
	}
	
	/**
	 * Set the deleted
	 * @spring.validator type="required"
	 */	
	public void setDeleted(Short aValue) {
		this.deleted = aValue;
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

	public java.util.Set<Store> getStores() {
		return stores;
	}

	public void setStores(java.util.Set<Store> stores) {
		this.stores = stores;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof ShippingMethodTbl)) {
			return false;
		}
		ShippingMethodTbl rhs = (ShippingMethodTbl) object;
		return new EqualsBuilder()
				.append(this.shippingMethodId, rhs.shippingMethodId)
						.append(this.shippingMethodName, rhs.shippingMethodName)
				.append(this.shippingMethodDetail, rhs.shippingMethodDetail)
				.append(this.isDomestic, rhs.isDomestic)
				.append(this.deliveryTime, rhs.deliveryTime)
				.append(this.status, rhs.status)
				.append(this.isCod, rhs.isCod)
				.append(this.deleted, rhs.deleted)
				.append(this.version, rhs.version)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.shippingMethodId) 
						.append(this.shippingMethodName) 
				.append(this.shippingMethodDetail) 
				.append(this.isDomestic) 
				.append(this.deliveryTime) 
				.append(this.status) 
				.append(this.isCod) 
				.append(this.deleted) 
				.append(this.version) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("shippingMethodId", this.shippingMethodId) 
						.append("shippingMethodName", this.shippingMethodName) 
				.append("shippingMethodDetail", this.shippingMethodDetail) 
				.append("isDomestic", this.isDomestic) 
				.append("deliveryTime", this.deliveryTime) 
				.append("status", this.status) 
				.append("isCod", this.isCod) 
				.append("deleted", this.deleted) 
				.append("version", this.version) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "shippingMethodId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return shippingMethodId;
	}

}