package com.cartmatic.estore.common.model.system.base;

import java.io.Serializable;
import com.cartmatic.estore.Constants;
import com.cartmatic.estore.core.model.BaseObject;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * TaxRate Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class TaxRateTbl extends BaseObject implements Serializable {

    protected Integer taxRateId;
	protected String formula;
	protected java.math.BigDecimal rateValue;
	protected Short rateType;
	protected Integer version;
	protected com.cartmatic.estore.common.model.system.Tax tax;
	protected com.cartmatic.estore.common.model.catalog.ProductType productType;
	protected com.cartmatic.estore.common.model.system.Region region;


	/**
	 * Default Empty Constructor for class TaxRate
	 */
	public TaxRateTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class TaxRate
	 */
	public TaxRateTbl (
		 Integer in_taxRateId
        ) {
		this.setTaxRateId(in_taxRateId);
    }

	
	public com.cartmatic.estore.common.model.system.Tax getTax () {
		return tax;
	}	
	
	public void setTax (com.cartmatic.estore.common.model.system.Tax in_tax) {
		this.tax = in_tax;
	}
	
	public com.cartmatic.estore.common.model.catalog.ProductType getProductType () {
		return productType;
	}	
	
	public void setProductType (com.cartmatic.estore.common.model.catalog.ProductType in_productType) {
		this.productType = in_productType;
	}
	
	public com.cartmatic.estore.common.model.system.Region getRegion () {
		return region;
	}	
	
	public void setRegion (com.cartmatic.estore.common.model.system.Region in_region) {
		this.region = in_region;
	}
    

	/**
	 * 	 * @return Integer
     * @hibernate.id column="taxRateId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getTaxRateId() {
		return this.taxRateId;
	}
	
	/**
	 * Set the taxRateId
	 */	
	public void setTaxRateId(Integer aValue) {
		this.taxRateId = aValue;
	}	

	/**
	 * 	 * @return Integer
	 */
	public Integer getTaxId() {
		return this.getTax()==null?null:this.getTax().getTaxId();
	}
	
	/**
	 * Set the taxId
	 */	
	public void setTaxId(Integer aValue) {
	    if (aValue==null) {
	    	tax = null;
	    } else {
	        tax = new com.cartmatic.estore.common.model.system.Tax(aValue);
	        tax.setVersion(new Integer(0));//set a version to cheat hibernate only
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
	 */
	public Integer getProductTypeId() {
		return this.getProductType()==null?null:this.getProductType().getProductTypeId();
	}
	
	/**
	 * Set the productTypeId
	 */	
	public void setProductTypeId(Integer aValue) {
	    if (aValue==null) {
	    	productType = null;
	    } else {
	        productType = new com.cartmatic.estore.common.model.catalog.ProductType(aValue);
	        productType.setVersion(new Integer(0));//set a version to cheat hibernate only
	    }
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="formula" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getFormula() {
		return this.formula;
	}
	
	/**
	 * Set the formula
	 */	
	public void setFormula(String aValue) {
		this.formula = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="rateValue" type="java.math.BigDecimal" length="12" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getRateValue() {
		return this.rateValue;
	}
	
	/**
	 * Set the rateValue
	 */	
	public void setRateValue(java.math.BigDecimal aValue) {
		this.rateValue = aValue;
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="rateType" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
	public Short getRateType() {
		return this.rateType;
	}
	
	/**
	 * Set the rateType
	 */	
	public void setRateType(Short aValue) {
		this.rateType = aValue;
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
		if (!(object instanceof TaxRateTbl)) {
			return false;
		}
		TaxRateTbl rhs = (TaxRateTbl) object;
		return new EqualsBuilder()
				.append(this.taxRateId, rhs.taxRateId)
										.append(this.formula, rhs.formula)
				.append(this.rateValue, rhs.rateValue)
				.append(this.rateType, rhs.rateType)
				.append(this.version, rhs.version)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.taxRateId) 
										.append(this.formula) 
				.append(this.rateValue) 
				.append(this.rateType) 
				.append(this.version) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("taxRateId", this.taxRateId) 
										.append("formula", this.formula) 
				.append("rateValue", this.rateValue) 
				.append("rateType", this.rateType) 
				.append("version", this.version) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "taxRateId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return taxRateId;
	}

}