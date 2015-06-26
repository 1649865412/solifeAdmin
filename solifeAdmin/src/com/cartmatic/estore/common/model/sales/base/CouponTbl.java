package com.cartmatic.estore.common.model.sales.base;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.cartmatic.estore.common.model.sales.Coupon;
import com.cartmatic.estore.common.model.system.AppUser;
import com.cartmatic.estore.core.model.BaseObject;

/**
 * Coupon Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * coupon
 */
public class CouponTbl extends BaseObject implements Serializable {

	private static final long	serialVersionUID	= -2639809204782343658L;
	protected Integer couponId;
	protected String couponNo;
	protected Short isSent;
	protected String sentEmail;
	protected Integer remainedTimes;
	protected Short status;
	protected Integer version;
	protected com.cartmatic.estore.common.model.sales.PromoRule promoRule;
	
	protected Set<AppUser> userCoupons = new HashSet<AppUser>();


	/**
	 * Default Empty Constructor for class Coupon
	 */
	public CouponTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class Coupon
	 */
	public CouponTbl (
		 Integer in_couponId
        ) {
		this.setCouponId(in_couponId);
    }

	
	public com.cartmatic.estore.common.model.sales.PromoRule getPromoRule () {
		return promoRule;
	}	
	
	public void setPromoRule (com.cartmatic.estore.common.model.sales.PromoRule in_promoRule) {
		this.promoRule = in_promoRule;
	}
    

	/**
	 * 	 * @return Integer
     * @hibernate.id column="couponId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getCouponId() {
		return this.couponId;
	}
	
	/**
	 * Set the couponId
	 */	
	public void setCouponId(Integer aValue) {
		this.couponId = aValue;
	}	

	/**
	 * 	 * @return Integer
	 */
	public Integer getPromoRuleId() {
		return this.getPromoRule()==null?null:this.getPromoRule().getPromoRuleId();
	}
	
	/**
	 * Set the promoRuleId
	 */	
	public void setPromoRuleId(Integer aValue) {
	    if (aValue==null) {
	    	promoRule = null;
	    } else {
	        promoRule = new com.cartmatic.estore.common.model.sales.PromoRule(aValue);
	        promoRule.setVersion(new Integer(0));//set a version to cheat hibernate only
	    }
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="couponNo" type="java.lang.String" length="16" not-null="true" unique="false"
	 */
	public String getCouponNo() {
		return this.couponNo;
	}
	
	/**
	 * Set the couponNo
	 * @spring.validator type="required"
	 */	
	public void setCouponNo(String aValue) {
		this.couponNo = aValue;
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="isSent" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
	public Short getIsSent() {
		return this.isSent;
	}
	
	/**
	 * Set the isSent
	 */	
	public void setIsSent(Short aValue) {
		this.isSent = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="remainedTimes" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getRemainedTimes() {
		return this.remainedTimes;
	}
	
	/**
	 * Set the remainedTimes
	 */	
	public void setRemainedTimes(Integer aValue) {
		this.remainedTimes = aValue;
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
		if (!(object instanceof CouponTbl)) {
			return false;
		}
		CouponTbl rhs = (CouponTbl) object;
		return new EqualsBuilder()
				.append(this.couponId, rhs.couponId)
						.append(this.couponNo, rhs.couponNo)
				.append(this.isSent, rhs.isSent)
				.append(this.remainedTimes, rhs.remainedTimes)
				.append(this.status, rhs.status)
				.append(this.version, rhs.version)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.couponId) 
						.append(this.couponNo) 
				.append(this.isSent) 
				.append(this.remainedTimes) 
				.append(this.status) 
				.append(this.version) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("couponId", this.couponId) 
						.append("couponNo", this.couponNo) 
				.append("isSent", this.isSent) 
				.append("remainedTimes", this.remainedTimes) 
				.append("status", this.status) 
				.append("version", this.version) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "couponId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return couponId;
	}

	public String getSentEmail() {
		return sentEmail;
	}

	public void setSentEmail(String sentEmail) {
		this.sentEmail = sentEmail;
	}

	public Set<AppUser> getUserCoupons() {
		return userCoupons;
	}

	public void setUserCoupons(Set<AppUser> userCoupons) {
		this.userCoupons = userCoupons;
	}

}