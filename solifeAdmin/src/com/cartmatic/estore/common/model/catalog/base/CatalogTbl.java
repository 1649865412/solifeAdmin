package com.cartmatic.estore.common.model.catalog.base;

import java.io.Serializable;
import com.cartmatic.estore.Constants;
import com.cartmatic.estore.common.model.catalog.Catalog;
import com.cartmatic.estore.common.model.catalog.Category;
import com.cartmatic.estore.core.model.BaseObject;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * Catalog Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class CatalogTbl extends BaseObject implements Serializable {

    protected Integer catalogId;
	protected String name;
	protected String code;
	protected String description;
	protected Short isVirtual;
	protected Short status;
	protected Integer version;
	protected Short availabilityRule;
	
	//对应的根商品分类
	private Category category;

	protected java.util.Set categorys = new java.util.HashSet();
	protected java.util.Set promoRules = new java.util.HashSet();
	protected java.util.Set stores = new java.util.HashSet();

	/**
	 * Default Empty Constructor for class Catalog
	 */
	public CatalogTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class Catalog
	 */
	public CatalogTbl (
		 Integer in_catalogId
        ) {
		this.setCatalogId(in_catalogId);
    }


	public java.util.Set getCategorys () {
		return categorys;
	}	
	
	public void setCategorys (java.util.Set in_categorys) {
		this.categorys = in_categorys;
	}

	public java.util.Set getPromoRules () {
		return promoRules;
	}	
	
	public void setPromoRules (java.util.Set in_promoRules) {
		this.promoRules = in_promoRules;
	}

	public java.util.Set getStores () {
		return stores;
	}	
	
	public void setStores (java.util.Set in_stores) {
		this.stores = in_stores;
	}
    

	/**
	 * 	 * @return Integer
     * @hibernate.id column="catalogId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getCatalogId() {
		return this.catalogId;
	}
	
	/**
	 * Set the catalogId
	 */	
	public void setCatalogId(Integer aValue) {
		this.catalogId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="name" type="java.lang.String" length="32" not-null="true" unique="false"
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Set the name
	 * @spring.validator type="required"
	 */	
	public void setName(String aValue) {
		this.name = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="code" type="java.lang.String" length="32" not-null="true" unique="false"
	 */
	public String getCode() {
		return this.code;
	}
	
	/**
	 * Set the code
	 * @spring.validator type="required"
	 */	
	public void setCode(String aValue) {
		this.code = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="description" type="java.lang.String" length="65535" not-null="false" unique="false"
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
	 * @hibernate.property column="isVirtual" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getIsVirtual() {
		return this.isVirtual;
	}
	
	/**
	 * Set the isVirtual
	 * @spring.validator type="required"
	 */	
	public void setIsVirtual(Short aValue) {
		this.isVirtual = aValue;
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="status" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getStatus() {
		return this.status;
	}
	
	/**
	 * Set the status
	 * @spring.validator type="required"
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
		if (!(object instanceof CatalogTbl)) {
			return false;
		}
		CatalogTbl rhs = (CatalogTbl) object;
		return new EqualsBuilder()
				.append(this.catalogId, rhs.catalogId)
				.append(this.name, rhs.name)
				.append(this.code, rhs.code)
				.append(this.description, rhs.description)
				.append(this.isVirtual, rhs.isVirtual)
				.append(this.status, rhs.status)
				.append(this.version, rhs.version)
				.append(this.availabilityRule, rhs.availabilityRule)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.catalogId) 
				.append(this.name) 
				.append(this.code) 
				.append(this.description) 
				.append(this.isVirtual) 
				.append(this.status) 
				.append(this.version) 
				.append(this.availabilityRule) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("catalogId", this.catalogId) 
				.append("name", this.name) 
				.append("code", this.code) 
				.append("description", this.description) 
				.append("isVirtual", this.isVirtual) 
				.append("status", this.status) 
				.append("version", this.version) 
				.append("availabilityRule", this.availabilityRule)
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "catalogId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return catalogId;
	}
	
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	/**
	 * 	 * @return Integer
	 */
	public Integer getCategoryId() {
		return this.getCategory()==null?null:this.getCategory().getCategoryId();
	}
	
	/**
	 * Set the catalogId
	 */	
	public void setCategoryId(Integer aValue) {
	    if (aValue==null) {
	    	category = null;
	    } else {
	    	category = new Category(aValue);
	    	category.setVersion(new Integer(0));//set a version to cheat hibernate only
	    }
	}

	public Short getAvailabilityRule() {
		return availabilityRule;
	}

	public void setAvailabilityRule(Short availabilityRule) {
		this.availabilityRule = availabilityRule;
	}

}