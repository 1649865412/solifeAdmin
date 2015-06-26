package com.cartmatic.estore.common.model.sales.base;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.cartmatic.estore.common.model.sales.PromoRuleParameter;
import com.cartmatic.estore.core.model.BaseObject;

/**
 * PromoRuleElement Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 */
public class PromoRuleElementTbl extends BaseObject implements Serializable{

 
	private static final long	serialVersionUID	= 6088702765051415010L;
	protected Integer promoRuleElementId;
	protected String kind;
	protected String type;
	protected Integer sort;
	protected com.cartmatic.estore.common.model.sales.PromoRule promoRule;

	protected java.util.Set<PromoRuleParameter> promoRuleParameters = new java.util.HashSet<PromoRuleParameter>();

	/**
	 * Default Empty Constructor for class PromoRuleElement
	 */
	public PromoRuleElementTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class PromoRuleElement
	 */
	public PromoRuleElementTbl (
		 Integer in_promoRuleElementId
        ) {
		this.setPromoRuleElementId(in_promoRuleElementId);
    }

	
	public com.cartmatic.estore.common.model.sales.PromoRule getPromoRule () {
		return promoRule;
	}	
	
	public void setPromoRule (com.cartmatic.estore.common.model.sales.PromoRule in_promoRule) {
		this.promoRule = in_promoRule;
	}

	public java.util.Set<PromoRuleParameter> getPromoRuleParameters () {
		return promoRuleParameters;
	}	
	
	public void setPromoRuleParameters (java.util.Set<PromoRuleParameter> in_promoRuleParameters) {
		this.promoRuleParameters = in_promoRuleParameters;
	}
    

	/**
	 * 	 * @return Integer
     * @hibernate.id column="promoRuleElementId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getPromoRuleElementId() {
		return this.promoRuleElementId;
	}
	
	/**
	 * Set the promoRuleElementId
	 */	
	public void setPromoRuleElementId(Integer aValue) {
		this.promoRuleElementId = aValue;
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
	 * @hibernate.property column="kind" type="java.lang.String" length="16" not-null="true" unique="false"
	 */
	public String getKind() {
		return this.kind;
	}
	
	/**
	 * Set the kind
	 * @spring.validator type="required"
	 */	
	public void setKind(String aValue) {
		this.kind = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="type" type="java.lang.String" length="64" not-null="true" unique="false"
	 */
	public String getType() {
		return this.type;
	}
	
	/**
	 * Set the type
	 * @spring.validator type="required"
	 */	
	public void setType(String aValue) {
		this.type = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="sort" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getSort() {
		return this.sort;
	}
	
	/**
	 * Set the sort
	 */	
	public void setSort(Integer aValue) {
		this.sort = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof PromoRuleElementTbl)) {
			return false;
		}
		PromoRuleElementTbl rhs = (PromoRuleElementTbl) object;
		return new EqualsBuilder()
				.append(this.promoRuleElementId, rhs.promoRuleElementId)
						.append(this.kind, rhs.kind)
				.append(this.type, rhs.type)
				.append(this.sort, rhs.sort)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.promoRuleElementId) 
						.append(this.kind) 
				.append(this.type) 
				.append(this.sort) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("promoRuleElementId", this.promoRuleElementId) 
						.append("kind", this.kind) 
				.append("type", this.type) 
				.append("sort", this.sort) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "promoRuleElementId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return promoRuleElementId;
	}
	
	


}