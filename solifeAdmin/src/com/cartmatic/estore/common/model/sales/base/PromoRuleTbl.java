
package com.cartmatic.estore.common.model.sales.base;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.cartmatic.estore.common.model.catalog.Catalog;
import com.cartmatic.estore.common.model.sales.Coupon;
import com.cartmatic.estore.common.model.sales.PromoRuleElement;
import com.cartmatic.estore.common.model.system.Store;
import com.cartmatic.estore.core.model.BaseObject;

/**
 * PromoRule Base Java Bean, base class for the model, mapped directly to
 * database table
 * 
 * Avoid changing this file if not necessary, will be overwritten.
 * 
 */
public class PromoRuleTbl extends BaseObject implements Serializable {

	
	private static final long	serialVersionUID	= -8378461511636754787L;
	protected Integer			promoRuleId;
	protected String			name;
	protected String			description;
	protected Integer			priority;
	protected Integer			availableCount;
	protected Short				enableDiscountAgain;
	protected java.util.Date	startTime;
	protected java.util.Date	endTime;
	protected String			promoType;
	
	protected Short triggerType;
	
	protected Short				status;
	protected Short				eligibilityOperator;
	protected Short				conditionOperator;
	protected java.util.Date	createTime;
	protected java.util.Date	updateTime;
	protected Integer			createBy;
	protected Integer			updateBy;
	protected Integer			version;

	protected java.util.Set<Coupon>		coupons				= new java.util.HashSet<Coupon>	();
	protected java.util.Set<PromoRuleElement>		promoRuleElements	= new java.util.HashSet<PromoRuleElement>();


	protected Catalog catalog;
	protected Store store;
	
	/**
	 * Default Empty Constructor for class PromoRule
	 */
	public PromoRuleTbl() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class PromoRule
	 */
	public PromoRuleTbl(Integer in_promoRuleId) {
		this.setPromoRuleId(in_promoRuleId);
	}

	public java.util.Set<Coupon> getCoupons() {
		return coupons;
	}

	public void setCoupons(java.util.Set<Coupon> in_coupons) {
		this.coupons = in_coupons;
	}

	public java.util.Set<PromoRuleElement> getPromoRuleElements() {
		return promoRuleElements;
	}

	public void setPromoRuleElements(java.util.Set<PromoRuleElement> _promoRuleElements) {
		this.promoRuleElements = _promoRuleElements;
	}
	
	public void addPromoRuleElement(PromoRuleElement _promoRuleElement){
		this.promoRuleElements.add(_promoRuleElement);
	}
	
	public void removePromoRuleElement(PromoRuleElement _promoRuleElement){
		this.promoRuleElements.remove(_promoRuleElement);
	}

	/**
	 * *
	 * 
	 * @return Integer
	 * @hibernate.id column="promoRuleId" type="java.lang.Integer"
	 *               generator-class="native"
	 */
	public Integer getPromoRuleId() {
		return this.promoRuleId;
	}

	/**
	 * Set the promoRuleId
	 */
	public void setPromoRuleId(Integer aValue) {
		this.promoRuleId = aValue;
	}

	/**
	 * *
	 * 
	 * @return String
	 * @hibernate.property column="name" type="java.lang.String" length="64"
	 *                     not-null="true" unique="false"
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Set the name
	 * 
	 * @spring.validator type="required"
	 */
	public void setName(String aValue) {
		this.name = aValue;
	}

	/**
	 * *
	 * 
	 * @return String
	 * @hibernate.property column="description" type="java.lang.String"
	 *                     length="65535" not-null="false" unique="false"
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
	 * *
	 * 
	 * @return Integer
	 * @hibernate.property column="priority" type="java.lang.Integer"
	 *                     length="10" not-null="true" unique="false"
	 */
	public Integer getPriority() {
		return this.priority;
	}

	/**
	 * Set the priority
	 * 
	 * @spring.validator type="required"
	 */
	public void setPriority(Integer aValue) {
		this.priority = aValue;
	}
	
	
	/**
	 * *
	 * 
	 * @return Integer
	 * @hibernate.property column="availableCount" type="java.lang.Integer"
	 *                     length="10" not-null="false" unique="false"
	 */
	public Integer getAvailableCount() {
		return availableCount;
	}
	/**
	 * Set the availableCount
	 * 
	 */
	public void setAvailableCount(Integer availableCount) {
		this.availableCount = availableCount;
	}

	/**
	 * *
	 * 
	 * @return Short
	 * @hibernate.property column="enableDiscountAgain" type="java.lang.Short"
	 *                     length="5" not-null="true" unique="false"
	 */
	public Short getEnableDiscountAgain() {
		return this.enableDiscountAgain;
	}

	/**
	 * Set the enableDiscountAgain
	 * 
	 * @spring.validator type="required"
	 */
	public void setEnableDiscountAgain(Short aValue) {
		this.enableDiscountAgain = aValue;
	}

	/**
	 * *
	 * 
	 * @return java.util.Date
	 * @hibernate.property column="startTime" type="java.util.Date" length="0"
	 *                     not-null="true" unique="false"
	 */
	public java.util.Date getStartTime() {
		return this.startTime;
	}

	/**
	 * Set the startTime
	 * 
	 * @spring.validator type="required"
	 */
	public void setStartTime(java.util.Date aValue) {
		this.startTime = aValue;
	}

	/**
	 * *
	 * 
	 * @return java.util.Date
	 * @hibernate.property column="endTime" type="java.util.Date" length="0"
	 *                     not-null="false" unique="false"
	 */
	public java.util.Date getEndTime() {
		return this.endTime;
	}

	/**
	 * Set the endTime
	 */
	public void setEndTime(java.util.Date aValue) {
		this.endTime = aValue;
	}

	/**
	 * *
	 * 
	 * @return String
	 * @hibernate.property column="promoType" type="java.lang.String"
	 *                     length="32" not-null="true" unique="false"
	 */
	public String getPromoType() {
		return this.promoType;
	}

	/**
	 * Set the promoType
	 * 
	 * @spring.validator type="required"
	 */
	public void setPromoType(String aValue) {
		this.promoType = aValue;
	}

	/**
	 * *
	 * 
	 * @return Short
	 * @hibernate.property column="status" type="java.lang.Short" length="5"
	 *                     not-null="true" unique="false"
	 */
	public Short getStatus() {
		return this.status;
	}

	/**
	 * Set the status
	 * 
	 * @spring.validator type="required"
	 */
	public void setStatus(Short aValue) {
		this.status = aValue;
	}
	
	/**
	 * *
	 * 
	 * @return Short
	 * @hibernate.property column="eligibilityOperator" type="java.lang.Short" length="5"
	 *                     not-null="true" unique="false"
	 */

	public Short getEligibilityOperator() {
		return eligibilityOperator;
	}

	/**
	 * Set the eligibilityOperator
	 * 
	 * @spring.validator type="required"
	 */
	public void setEligibilityOperator(Short eligibilityOperator) {
		this.eligibilityOperator = eligibilityOperator;
	}
	/**
	 * *
	 * 
	 * @return Short
	 * @hibernate.property column="conditionOperator" type="java.lang.Short" length="5"
	 *                     not-null="true" unique="false"
	 */
	public Short getConditionOperator() {
		return conditionOperator;
	}

	/**
	 * Set the conditionOperator
	 * 
	 * @spring.validator type="required"
	 */
	public void setConditionOperator(Short conditionOperator) {
		this.conditionOperator = conditionOperator;
	}

	/**
	 * *
	 * 
	 * @return java.util.Date
	 * @hibernate.property column="createTime" type="java.util.Date" length="0"
	 *                     not-null="true" unique="false"
	 */
	public java.util.Date getCreateTime() {
		return this.createTime;
	}

	/**
	 * Set the createTime
	 * 
	 * @spring.validator type="required"
	 */
	public void setCreateTime(java.util.Date aValue) {
		this.createTime = aValue;
	}

	/**
	 * *
	 * 
	 * @return java.util.Date
	 * @hibernate.property column="updateTime" type="java.util.Date" length="0"
	 *                     not-null="true" unique="false"
	 */
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}

	/**
	 * Set the updateTime
	 * 
	 * @spring.validator type="required"
	 */
	public void setUpdateTime(java.util.Date aValue) {
		this.updateTime = aValue;
	}

	/**
	 * *
	 * 
	 * @return Integer
	 * @hibernate.property column="createBy" type="java.lang.Integer"
	 *                     length="10" not-null="false" unique="false"
	 */
	public Integer getCreateBy() {
		return this.createBy;
	}

	/**
	 * Set the createBy
	 */
	public void setCreateBy(Integer aValue) {
		this.createBy = aValue;
	}

	/**
	 * *
	 * 
	 * @return Integer
	 * @hibernate.property column="updateBy" type="java.lang.Integer"
	 *                     length="10" not-null="false" unique="false"
	 */
	public Integer getUpdateBy() {
		return this.updateBy;
	}

	/**
	 * Set the updateBy
	 */
	public void setUpdateBy(Integer aValue) {
		this.updateBy = aValue;
	}

	/**
	 * *
	 * 
	 * @return Integer
	 * @hibernate.property column="version" type="java.lang.Integer" length="10"
	 *                     not-null="true" unique="false"
	 */
	public Integer getVersion() {
		return this.version;
	}

	/**
	 * Set the version
	 * 
	 * @spring.validator type="required"
	 */
	public void setVersion(Integer aValue) {
		this.version = aValue;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof PromoRuleTbl)) {
			return false;
		}
		PromoRuleTbl rhs = (PromoRuleTbl) object;
		return new EqualsBuilder().append(this.promoRuleId, rhs.promoRuleId)
				.append(this.name, rhs.name).append(this.description,
						rhs.description).append(this.priority, rhs.priority)
				.append(this.enableDiscountAgain, rhs.enableDiscountAgain)
				.append(this.startTime, rhs.startTime).append(this.endTime,
						rhs.endTime).append(this.promoType, rhs.promoType)
				.append(this.status, rhs.status).append(this.createTime,
						rhs.createTime).append(this.updateTime, rhs.updateTime)
				.append(this.createBy, rhs.createBy).append(this.updateBy,
						rhs.updateBy).append(this.version, rhs.version)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(
				this.promoRuleId).append(this.name).append(this.description)
				.append(this.priority).append(this.enableDiscountAgain).append(
						this.startTime).append(this.endTime).append(
						this.promoType).append(this.status).append(
						this.createTime).append(this.updateTime).append(
						this.createBy).append(this.updateBy).append(
						this.version).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("promoRuleId", this.promoRuleId).append("name",
						this.name).append("description", this.description)
				.append("priority", this.priority).append(
						"enableDiscountAgain", this.enableDiscountAgain)
				.append("startTime", this.startTime).append("endTime",
						this.endTime).append("promoType", this.promoType)
				.append("status", this.status).append("createTime",
						this.createTime).append("updateTime", this.updateTime)
				.append("createBy", this.createBy).append("updateBy",
						this.updateBy).append("version", this.version)
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "promoRuleId";
	}

	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return promoRuleId;
	}

	public Catalog getCatalog() {
		return catalog;
	}

	public void setCatalog(Catalog catalog) {
		this.catalog = catalog;
	}
	
	/**
	 * 	 * @return Integer
	 */
	public Integer getCatalogId() {
		return this.getCatalog()==null?null:this.getCatalog().getCatalogId();
	}
	
	/**
	 * Set the catalogId
	 */	
	public void setCatalogId(Integer aValue) {
	    if (aValue==null) {
	    	catalog = null;
	    } else {
	    	catalog = new Catalog(aValue);
	    	catalog.setVersion(new Integer(0));//set a version to cheat hibernate only
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

	public Short getTriggerType() {
		return triggerType;
	}

	public void setTriggerType(Short triggerType) {
		this.triggerType = triggerType;
	}
}