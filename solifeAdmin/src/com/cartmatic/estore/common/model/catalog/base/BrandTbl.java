package com.cartmatic.estore.common.model.catalog.base;

import java.io.Serializable;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.core.model.BaseObject;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * Brand Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class BrandTbl extends BaseObject implements Serializable {

    protected Integer brandId;
	protected String brandName;
	protected String brandCode;
	protected Integer sortOrder;
	protected String designer;
	protected String website;
	protected String story;;
	protected String logo;
	protected String pic;
	protected String pic2;
	protected String icon;
	protected String countryCode;
	protected Integer version;
	
	protected java.util.Set products = new java.util.HashSet();

	/**
	 * Default Empty Constructor for class Brand
	 */
	public BrandTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class Brand
	 */
	public BrandTbl (
		 Integer in_brandId
        ) {
		this.setBrandId(in_brandId);
    }


	public java.util.Set getProducts () {
		return products;
	}	
	
	public void setProducts (java.util.Set in_products) {
		this.products = in_products;
	}
    

	/**
	 * 	 * @return Integer
     * @hibernate.id column="brandId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getBrandId() {
		return this.brandId;
	}
	
	/**
	 * Set the brandId
	 */	
	public void setBrandId(Integer aValue) {
		this.brandId = aValue;
	}	

	/**
	 * 品牌名称	 * @return String
	 * @hibernate.property column="brandName" type="java.lang.String" length="64" not-null="true" unique="false"
	 */
	public String getBrandName() {
		return this.brandName;
	}
	
	/**
	 * Set the brandName
	 * @spring.validator type="required"
	 */	
	public void setBrandName(String aValue) {
		this.brandName = aValue;
	}	

	/**
	 * 品牌编码	 * @return String
	 * @hibernate.property column="brandCode" type="java.lang.String" length="32" not-null="true" unique="false"
	 */
	public String getBrandCode() {
		return this.brandCode;
	}
	
	/**
	 * Set the brandCode
	 * @spring.validator type="required"
	 */	
	public void setBrandCode(String aValue) {
		this.brandCode = aValue;
	}	

	public String getDesigner() {
		return designer;
	}

	public void setDesigner(String designer) {
		this.designer = designer;
	}

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="sortOrder" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getSortOrder() {
		return this.sortOrder;
	}
	
	/**
	 * Set the sortOrder
	 */	
	public void setSortOrder(Integer aValue) {
		this.sortOrder = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="website" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getWebsite() {
		return this.website;
	}
	
	/**
	 * Set the website
	 */	
	public void setWebsite(String aValue) {
		this.website = aValue;
	}	
	
	public String getStory() {
		return story;
	}

	public void setStory(String story) {
		this.story = story;
	}

	/**
	 * 	 * @return String
	 * @hibernate.property column="logo" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getLogo() {
		return this.logo;
	}
	
	/**
	 * Set the logo
	 */	
	public void setLogo(String aValue) {
		this.logo = aValue;
	}	

	
	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getPic2() {
		return pic2;
	}

	public void setPic2(String pic2) {
		this.pic2 = pic2;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
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
		if (!(object instanceof BrandTbl)) {
			return false;
		}
		BrandTbl rhs = (BrandTbl) object;
		return new EqualsBuilder()
				.append(this.brandId, rhs.brandId)
				.append(this.brandName, rhs.brandName)
				.append(this.brandCode, rhs.brandCode)
				.append(this.sortOrder, rhs.sortOrder)
				.append(this.website, rhs.website)
				.append(this.logo, rhs.logo)
				.append(this.version, rhs.version)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.brandId) 
				.append(this.brandName) 
				.append(this.brandCode) 
				.append(this.sortOrder) 
				.append(this.website) 
				.append(this.logo) 
				.append(this.version) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("brandId", this.brandId) 
				.append("brandName", this.brandName) 
				.append("brandCode", this.brandCode) 
				.append("sortOrder", this.sortOrder) 
				.append("website", this.website) 
				.append("logo", this.logo) 
				.append("version", this.version) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "brandId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return brandId;
	}

}