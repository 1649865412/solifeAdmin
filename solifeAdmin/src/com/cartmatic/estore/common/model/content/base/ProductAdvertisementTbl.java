package com.cartmatic.estore.common.model.content.base;

import java.io.Serializable;
import com.cartmatic.estore.Constants;
import com.cartmatic.estore.core.model.BaseObject;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * ProductAdvertisement Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class ProductAdvertisementTbl extends BaseObject implements Serializable {

    protected Integer productAdvertisementId;
	protected Integer version;
	protected com.cartmatic.estore.common.model.content.Advertisement advertisement;
	protected com.cartmatic.estore.common.model.catalog.Category category;


	/**
	 * Default Empty Constructor for class ProductAdvertisement
	 */
	public ProductAdvertisementTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class ProductAdvertisement
	 */
	public ProductAdvertisementTbl (
		 Integer in_productAdvertisementId
        ) {
		this.setProductAdvertisementId(in_productAdvertisementId);
    }

	
	public com.cartmatic.estore.common.model.content.Advertisement getAdvertisement () {
		return advertisement;
	}	
	
	public void setAdvertisement (com.cartmatic.estore.common.model.content.Advertisement in_advertisement) {
		this.advertisement = in_advertisement;
	}
	
	public com.cartmatic.estore.common.model.catalog.Category getCategory () {
		return category;
	}	
	
	public void setCategory (com.cartmatic.estore.common.model.catalog.Category in_category) {
		this.category = in_category;
	}
    

	/**
	 * 	 * @return Integer
     * @hibernate.id column="productAdvertisementId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getProductAdvertisementId() {
		return this.productAdvertisementId;
	}
	
	/**
	 * Set the productAdvertisementId
	 */	
	public void setProductAdvertisementId(Integer aValue) {
		this.productAdvertisementId = aValue;
	}	

	/**
	 * 	 * @return Integer
	 */
	public Integer getAdvertisementId() {
		return this.getAdvertisement()==null?null:this.getAdvertisement().getAdvertisementId();
	}
	
	/**
	 * Set the advertisementId
	 */	
	public void setAdvertisementId(Integer aValue) {
	    if (aValue==null) {
	    	advertisement = null;
	    } else {
	        advertisement = new com.cartmatic.estore.common.model.content.Advertisement(aValue);
	        advertisement.setVersion(new Integer(0));//set a version to cheat hibernate only
	    }
	}	

	/**
	 * 	 * @return Integer
	 */
	public Integer getCategoryId() {
		return this.getCategory()==null?null:this.getCategory().getCategoryId();
	}
	
	/**
	 * Set the categoryId
	 */	
	public void setCategoryId(Integer aValue) {
	    if (aValue==null) {
	    	category = null;
	    } else {
	        category = new com.cartmatic.estore.common.model.catalog.Category(aValue);
	        category.setVersion(new Integer(0));//set a version to cheat hibernate only
	    }
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
		if (!(object instanceof ProductAdvertisementTbl)) {
			return false;
		}
		ProductAdvertisementTbl rhs = (ProductAdvertisementTbl) object;
		return new EqualsBuilder()
				.append(this.productAdvertisementId, rhs.productAdvertisementId)
				.append(this.version, rhs.version)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.productAdvertisementId) 
				.append(this.version) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("productAdvertisementId", this.productAdvertisementId) 
				.append("version", this.version) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "productAdvertisementId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return productAdvertisementId;
	}

}