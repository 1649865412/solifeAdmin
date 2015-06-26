package com.cartmatic.estore.common.model.catalog.base;

import java.io.Serializable;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.core.model.BaseObject;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * ProductDescription Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class ProductDescriptionTbl extends BaseObject implements Serializable {

    protected Integer productDescriptionId;
	protected String shortDescription;
	protected String fullDescription;
	protected String imageDescription;

	/**
	 * Default Empty Constructor for class ProductDescription
	 */
	public ProductDescriptionTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class ProductDescription
	 */
	public ProductDescriptionTbl (
		 Integer in_productDescriptionId
        ) {
		this.setProductDescriptionId(in_productDescriptionId);
    }

	/**
	 * 	 * @return Integer
     * @hibernate.id column="productDescriptionId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getProductDescriptionId() {
		return this.productDescriptionId;
	}
	
	/**
	 * Set the productDescriptionId
	 */	
	public void setProductDescriptionId(Integer aValue) {
		this.productDescriptionId = aValue;
	}	

	/**
	 * 产品短描述	 * @return String
	 * @hibernate.property column="shortDescription" type="java.lang.String" length="512" not-null="false" unique="false"
	 */
	public String getShortDescription() {
		return this.shortDescription;
	}
	
	/**
	 * Set the shortDescription
	 */	
	public void setShortDescription(String aValue) {
		this.shortDescription = aValue;
	}	

	/**
	 * 产品详细描述（详细页面）	 * @return String
	 * @hibernate.property column="fullDescription" type="java.lang.String" length="65535" not-null="false" unique="false"
	 */
	public String getFullDescription() {
		return this.fullDescription;
	}
	
	/**
	 * Set the fullDescription
	 */	
	public void setFullDescription(String aValue) {
		this.fullDescription = aValue;
	}	

	public String getImageDescription() {
		return imageDescription;
	}

	public void setImageDescription(String imageDescription) {
		this.imageDescription = imageDescription;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof ProductDescriptionTbl)) {
			return false;
		}
		ProductDescriptionTbl rhs = (ProductDescriptionTbl) object;
		return new EqualsBuilder()
				.append(this.productDescriptionId, rhs.productDescriptionId)
				.append(this.shortDescription, rhs.shortDescription)
				.append(this.fullDescription, rhs.fullDescription)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.productDescriptionId) 
				.append(this.shortDescription) 
				.append(this.fullDescription) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("productDescriptionId", this.productDescriptionId) 
				.append("shortDescription", this.shortDescription) 
				.append("fullDescription", this.fullDescription) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "productDescriptionId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return productDescriptionId;
	}

}