package com.cartmatic.estore.common.model.system.base;

import java.io.Serializable;
import com.cartmatic.estore.Constants;
import com.cartmatic.estore.core.model.BaseObject;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * ShippingRate Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class ShippingRateTbl extends BaseObject implements Serializable {

    protected Integer shippingRateId;
	protected Integer baseOn;
	protected Short isFlat;
	protected Short isRoundUpValue;
	protected java.math.BigDecimal basePrice;
	protected java.math.BigDecimal maxWeight;
	protected java.math.BigDecimal maxVolume;
	protected java.math.BigDecimal baseWeight;
	protected java.math.BigDecimal baseVolume;
	protected java.math.BigDecimal weightPerRate;
	protected java.math.BigDecimal volumePerRate;
	protected java.math.BigDecimal volumePerFee;
	protected java.math.BigDecimal increaseUnit;
	protected String metricUnitCode;
	protected Integer maxItem;
	protected java.math.BigDecimal itemPerRate;
	protected String description;
	protected Short deleted = Constants.MARK_NOT_DELETED;
	protected Integer version;
	protected java.math.BigDecimal minWeight;
	protected com.cartmatic.estore.common.model.system.Region region;
	protected com.cartmatic.estore.common.model.system.ShippingMethod shippingMethod;


	/**
	 * Default Empty Constructor for class ShippingRate
	 */
	public ShippingRateTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class ShippingRate
	 */
	public ShippingRateTbl (
		 Integer in_shippingRateId
        ) {
		this.setShippingRateId(in_shippingRateId);
    }

	
	public com.cartmatic.estore.common.model.system.Region getRegion () {
		return region;
	}	
	
	public void setRegion (com.cartmatic.estore.common.model.system.Region in_region) {
		this.region = in_region;
	}
	
	public com.cartmatic.estore.common.model.system.ShippingMethod getShippingMethod () {
		return shippingMethod;
	}	
	
	public void setShippingMethod (com.cartmatic.estore.common.model.system.ShippingMethod in_shippingMethod) {
		this.shippingMethod = in_shippingMethod;
	}
    

	/**
	 * 	 * @return Integer
     * @hibernate.id column="shippingRateId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getShippingRateId() {
		return this.shippingRateId;
	}
	
	/**
	 * Set the shippingRateId
	 */	
	public void setShippingRateId(Integer aValue) {
		this.shippingRateId = aValue;
	}	

	/**
	 * 	 * @return Integer
	 */
	public Integer getShippingMethodId() {
		return this.getShippingMethod()==null?null:this.getShippingMethod().getShippingMethodId();
	}
	
	/**
	 * Set the shippingMethodId
	 */	
	public void setShippingMethodId(Integer aValue) {
	    if (aValue==null) {
	    	shippingMethod = null;
	    } else {
	        shippingMethod = new com.cartmatic.estore.common.model.system.ShippingMethod(aValue);
	        shippingMethod.setVersion(new Integer(0));//set a version to cheat hibernate only
	    }
	}	

	/**
	 * 	 * @return Integer
	 */
	public Integer getRegionId() {
		return this.getRegion()==null?null:this.getRegion().getRegionId();
	}
	
	/**
	 * Set the regionId
	 */	
	public void setRegionId(Integer aValue) {
	    if (aValue==null) {
	    	region = null;
	    } else {
	        region = new com.cartmatic.estore.common.model.system.Region(aValue);
	        region.setVersion(new Integer(0));//set a version to cheat hibernate only
	    }
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="baseOn" type="java.lang.Integer" length="10" not-null="true" unique="false"
	 */
	public Integer getBaseOn() {
		return this.baseOn;
	}
	
	/**
	 * Set the baseOn
	 * @spring.validator type="required"
	 */	
	public void setBaseOn(Integer aValue) {
		this.baseOn = aValue;
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="isFlat" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	/**
	 * @ TODO不懂为什么这样赋值,编辑页面不存在本属性
	 * @return Short
	 * @hibernate.property column="isFlat" type="java.lang.Short" length="6" not-null="true" unique="false"
	 */
	public Short getIsFlat() {
		return this.isFlat==null?new Short("1"):this.isFlat;
	}
	
	/**
	 * @ TODO不懂为什么这样赋值,编辑页面不存在本属性
	 * Set the isFlat
	 * @spring.validator type="required"
	 */	
	public void setIsFlat(Short aValue) {
		if(aValue==null)
			 aValue=new Short("1");
		this.isFlat = aValue;
	}

	/**
	 * 	 * @return Short
	 * @hibernate.property column="isRoundUpValue" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
	public Short getIsRoundUpValue() {
		return this.isRoundUpValue;
	}
	
	/**
	 * Set the isRoundUpValue
	 */	
	public void setIsRoundUpValue(Short aValue) {
		this.isRoundUpValue = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="basePrice" type="java.math.BigDecimal" length="12" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getBasePrice() {
		return this.basePrice;
	}
	
	/**
	 * Set the basePrice
	 */	
	public void setBasePrice(java.math.BigDecimal aValue) {
		this.basePrice = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="maxWeight" type="java.math.BigDecimal" length="12" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getMaxWeight() {
		return this.maxWeight;
	}
	
	/**
	 * Set the maxWeight
	 */	
	public void setMaxWeight(java.math.BigDecimal aValue) {
		this.maxWeight = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="maxVolume" type="java.math.BigDecimal" length="12" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getMaxVolume() {
		return this.maxVolume;
	}
	
	/**
	 * Set the maxVolume
	 */	
	public void setMaxVolume(java.math.BigDecimal aValue) {
		this.maxVolume = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="baseWeight" type="java.math.BigDecimal" length="12" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getBaseWeight() {
		return this.baseWeight;
	}
	
	/**
	 * Set the baseWeight
	 */	
	public void setBaseWeight(java.math.BigDecimal aValue) {
		this.baseWeight = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="baseVolume" type="java.math.BigDecimal" length="12" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getBaseVolume() {
		return this.baseVolume;
	}
	
	/**
	 * Set the baseVolume
	 */	
	public void setBaseVolume(java.math.BigDecimal aValue) {
		this.baseVolume = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="weightPerRate" type="java.math.BigDecimal" length="12" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getWeightPerRate() {
		return this.weightPerRate;
	}
	
	/**
	 * Set the weightPerRate
	 */	
	public void setWeightPerRate(java.math.BigDecimal aValue) {
		this.weightPerRate = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="volumePerRate" type="java.math.BigDecimal" length="12" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getVolumePerRate() {
		return this.volumePerRate;
	}
	
	/**
	 * Set the volumePerRate
	 */	
	public void setVolumePerRate(java.math.BigDecimal aValue) {
		this.volumePerRate = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="volumePerFee" type="java.math.BigDecimal" length="12" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getVolumePerFee() {
		return this.volumePerFee;
	}
	
	/**
	 * Set the volumePerFee
	 */	
	public void setVolumePerFee(java.math.BigDecimal aValue) {
		this.volumePerFee = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="increaseUnit" type="java.math.BigDecimal" length="12" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getIncreaseUnit() {
		return this.increaseUnit;
	}
	
	/**
	 * Set the increaseUnit
	 */	
	public void setIncreaseUnit(java.math.BigDecimal aValue) {
		this.increaseUnit = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="metricUnitCode" type="java.lang.String" length="32" not-null="false" unique="false"
	 */
	public String getMetricUnitCode() {
		return this.metricUnitCode;
	}
	
	/**
	 * Set the metricUnitCode
	 */	
	public void setMetricUnitCode(String aValue) {
		this.metricUnitCode = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="maxItem" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getMaxItem() {
		return this.maxItem;
	}
	
	/**
	 * Set the maxItem
	 */	
	public void setMaxItem(Integer aValue) {
		this.maxItem = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="itemPerRate" type="java.math.BigDecimal" length="12" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getItemPerRate() {
		return this.itemPerRate;
	}
	
	/**
	 * Set the itemPerRate
	 */	
	public void setItemPerRate(java.math.BigDecimal aValue) {
		this.itemPerRate = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="description" type="java.lang.String" length="1024" not-null="false" unique="false"
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

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="minWeight" type="java.math.BigDecimal" length="12" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getMinWeight() {
		return this.minWeight;
	}
	
	/**
	 * Set the minWeight
	 */	
	public void setMinWeight(java.math.BigDecimal aValue) {
		this.minWeight = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof ShippingRateTbl)) {
			return false;
		}
		ShippingRateTbl rhs = (ShippingRateTbl) object;
		return new EqualsBuilder()
				.append(this.shippingRateId, rhs.shippingRateId)
								.append(this.baseOn, rhs.baseOn)
				.append(this.isFlat, rhs.isFlat)
				.append(this.isRoundUpValue, rhs.isRoundUpValue)
				.append(this.basePrice, rhs.basePrice)
				.append(this.maxWeight, rhs.maxWeight)
				.append(this.maxVolume, rhs.maxVolume)
				.append(this.baseWeight, rhs.baseWeight)
				.append(this.baseVolume, rhs.baseVolume)
				.append(this.weightPerRate, rhs.weightPerRate)
				.append(this.volumePerRate, rhs.volumePerRate)
				.append(this.volumePerFee, rhs.volumePerFee)
				.append(this.increaseUnit, rhs.increaseUnit)
				.append(this.metricUnitCode, rhs.metricUnitCode)
				.append(this.maxItem, rhs.maxItem)
				.append(this.itemPerRate, rhs.itemPerRate)
				.append(this.description, rhs.description)
				.append(this.deleted, rhs.deleted)
				.append(this.version, rhs.version)
				.append(this.minWeight, rhs.minWeight)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.shippingRateId) 
								.append(this.baseOn) 
				.append(this.isFlat) 
				.append(this.isRoundUpValue) 
				.append(this.basePrice) 
				.append(this.maxWeight) 
				.append(this.maxVolume) 
				.append(this.baseWeight) 
				.append(this.baseVolume) 
				.append(this.weightPerRate) 
				.append(this.volumePerRate) 
				.append(this.volumePerFee) 
				.append(this.increaseUnit) 
				.append(this.metricUnitCode) 
				.append(this.maxItem) 
				.append(this.itemPerRate) 
				.append(this.description) 
				.append(this.deleted) 
				.append(this.version) 
				.append(this.minWeight) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("shippingRateId", this.shippingRateId) 
								.append("baseOn", this.baseOn) 
				.append("isFlat", this.isFlat) 
				.append("isRoundUpValue", this.isRoundUpValue) 
				.append("basePrice", this.basePrice) 
				.append("maxWeight", this.maxWeight) 
				.append("maxVolume", this.maxVolume) 
				.append("baseWeight", this.baseWeight) 
				.append("baseVolume", this.baseVolume) 
				.append("weightPerRate", this.weightPerRate) 
				.append("volumePerRate", this.volumePerRate) 
				.append("volumePerFee", this.volumePerFee) 
				.append("increaseUnit", this.increaseUnit) 
				.append("metricUnitCode", this.metricUnitCode) 
				.append("maxItem", this.maxItem) 
				.append("itemPerRate", this.itemPerRate) 
				.append("description", this.description) 
				.append("deleted", this.deleted) 
				.append("version", this.version) 
				.append("minWeight", this.minWeight) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "shippingRateId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return shippingRateId;
	}

}