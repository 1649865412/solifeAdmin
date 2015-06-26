package com.cartmatic.estore.common.model.sales.base;

import java.io.Serializable;
import java.util.Comparator;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.cartmatic.estore.core.model.BaseObject;

/**
 * PromoRuleParameter Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 */
public class PromoRuleParameterTbl extends BaseObject implements Serializable ,Comparator<PromoRuleParameterTbl>{

    
	private static final long	serialVersionUID	= -3883910303454979002L;
	protected Integer promoRuleParameterId;
	protected String paramName;
	protected String paramValue;
	protected Short isExcluded;
	protected String excludedType;
	protected com.cartmatic.estore.common.model.sales.PromoRuleElement promoRuleElement;


	/**
	 * Default Empty Constructor for class PromoRuleParameter
	 */
	public PromoRuleParameterTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class PromoRuleParameter
	 */
	public PromoRuleParameterTbl (
		 Integer in_promoRuleParameterId
        ) {
		this.setPromoRuleParameterId(in_promoRuleParameterId);
    }

	
	public com.cartmatic.estore.common.model.sales.PromoRuleElement getPromoRuleElement () {
		return promoRuleElement;
	}	
	
	public void setPromoRuleElement (com.cartmatic.estore.common.model.sales.PromoRuleElement in_promoRuleElement) {
		this.promoRuleElement = in_promoRuleElement;
	}
    

	/**
	 * 	 * @return Integer
     * @hibernate.id column="promoRuleParameterId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getPromoRuleParameterId() {
		return this.promoRuleParameterId;
	}
	
	/**
	 * Set the promoRuleParameterId
	 */	
	public void setPromoRuleParameterId(Integer aValue) {
		this.promoRuleParameterId = aValue;
	}	

	/**
	 * 	 * @return Integer
	 */
	public Integer getPromoRuleElementId() {
		return this.getPromoRuleElement()==null?null:this.getPromoRuleElement().getPromoRuleElementId();
	}
	
	/**
	 * Set the promoRuleElementId
	 */	
	public void setPromoRuleElementId(Integer aValue) {
	    if (aValue==null) {
	    	promoRuleElement = null;
	    } else {
	        promoRuleElement = new com.cartmatic.estore.common.model.sales.PromoRuleElement(aValue);
	        promoRuleElement.setVersion(new Integer(0));//set a version to cheat hibernate only
	    }
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="paramName" type="java.lang.String" length="32" not-null="true" unique="false"
	 */
	public String getParamName() {
		return this.paramName;
	}
	
	/**
	 * Set the paramName
	 * @spring.validator type="required"
	 */	
	public void setParamName(String aValue) {
		this.paramName = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="paramValue" type="java.lang.String" length="64" not-null="true" unique="false"
	 */
	public String getParamValue() {
		return this.paramValue;
	}
	
	/**
	 * Set the paramValue
	 * @spring.validator type="required"
	 */	
	public void setParamValue(String aValue) {
		this.paramValue = aValue;
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="isExcluded" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getIsExcluded() {
		return this.isExcluded;
	}
	
	/**
	 * Set the isExcluded
	 * @spring.validator type="required"
	 */	
	public void setIsExcluded(Short aValue) {
		this.isExcluded = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="excludedType" type="java.lang.String" length="16" not-null="true" unique="false"
	 */
	public String getExcludedType() {
		return this.excludedType;
	}
	
	/**
	 * Set the excludedType
	 */	
	public void setExcludedType(String aValue) {
		this.excludedType = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof PromoRuleParameterTbl)) {
			return false;
		}
		PromoRuleParameterTbl rhs = (PromoRuleParameterTbl) object;
		return new EqualsBuilder()
				.append(this.promoRuleParameterId, rhs.promoRuleParameterId)
						.append(this.paramName, rhs.paramName)
				.append(this.paramValue, rhs.paramValue)
				.append(this.isExcluded, rhs.isExcluded)
				.append(this.excludedType, rhs.excludedType)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.promoRuleParameterId) 
						.append(this.paramName) 
				.append(this.paramValue) 
				.append(this.isExcluded) 
				.append(this.excludedType) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("promoRuleParameterId", this.promoRuleParameterId) 
						.append("paramName", this.paramName) 
				.append("paramValue", this.paramValue) 
				.append("isExcluded", this.isExcluded) 
				.append("excludedType", this.excludedType) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "promoRuleParameterId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return promoRuleParameterId;
	}
	
	public int compare(PromoRuleParameterTbl o1, PromoRuleParameterTbl o2){
        PromoRuleParameterTbl s1=(PromoRuleParameterTbl)o1;
        PromoRuleParameterTbl s2=(PromoRuleParameterTbl)o2;
        if(s1.equals(s2)){
            return 0;
        }
        else if(s1.getPromoRuleParameterId().compareTo(s2.getPromoRuleParameterId()) > 0){
            return 1;
        }
        else{
            return -1;
        }
    
    }

}