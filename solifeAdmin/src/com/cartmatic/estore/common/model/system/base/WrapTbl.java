package com.cartmatic.estore.common.model.system.base;

import java.io.Serializable;
import com.cartmatic.estore.Constants;
import com.cartmatic.estore.core.model.BaseObject;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * Wrap Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class WrapTbl extends BaseObject implements Serializable {

    protected Integer wrapId;
	protected java.math.BigDecimal defaultPrice;
	protected String wrapName;
	protected String description;
	protected String imageSrc;
	protected Integer version;


	/**
	 * Default Empty Constructor for class Wrap
	 */
	public WrapTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class Wrap
	 */
	public WrapTbl (
		 Integer in_wrapId
        ) {
		this.setWrapId(in_wrapId);
    }

    

	/**
	 * 	 * @return Integer
     * @hibernate.id column="wrapId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getWrapId() {
		return this.wrapId;
	}
	
	/**
	 * Set the wrapId
	 */	
	public void setWrapId(Integer aValue) {
		this.wrapId = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="defaultPrice" type="java.math.BigDecimal" length="12" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getDefaultPrice() {
		return this.defaultPrice;
	}
	
	/**
	 * Set the defaultPrice
	 */	
	public void setDefaultPrice(java.math.BigDecimal aValue) {
		this.defaultPrice = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="wrapName" type="java.lang.String" length="64" not-null="false" unique="false"
	 */
	public String getWrapName() {
		return this.wrapName;
	}
	
	/**
	 * Set the wrapName
	 */	
	public void setWrapName(String aValue) {
		this.wrapName = aValue;
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
	 * 	 * @return String
	 * @hibernate.property column="imageSrc" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getImageSrc() {
		return this.imageSrc;
	}
	
	/**
	 * Set the imageSrc
	 */	
	public void setImageSrc(String aValue) {
		this.imageSrc = aValue;
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
		if (!(object instanceof WrapTbl)) {
			return false;
		}
		WrapTbl rhs = (WrapTbl) object;
		return new EqualsBuilder()
				.append(this.wrapId, rhs.wrapId)
				.append(this.defaultPrice, rhs.defaultPrice)
				.append(this.wrapName, rhs.wrapName)
				.append(this.description, rhs.description)
				.append(this.imageSrc, rhs.imageSrc)
				.append(this.version, rhs.version)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.wrapId) 
				.append(this.defaultPrice) 
				.append(this.wrapName) 
				.append(this.description) 
				.append(this.imageSrc) 
				.append(this.version) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("wrapId", this.wrapId) 
				.append("defaultPrice", this.defaultPrice) 
				.append("wrapName", this.wrapName) 
				.append("description", this.description) 
				.append("imageSrc", this.imageSrc) 
				.append("version", this.version) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "wrapId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return wrapId;
	}

}