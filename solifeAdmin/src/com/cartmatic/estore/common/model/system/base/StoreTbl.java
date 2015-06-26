package com.cartmatic.estore.common.model.system.base;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.cartmatic.estore.common.model.catalog.Category;
import com.cartmatic.estore.core.model.BaseObject;

/**
 * Store Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class StoreTbl extends BaseObject implements Serializable {

    protected Integer storeId;
	protected String name;
	protected String code;
	protected String siteUrl;
	protected String description;
	protected Short status;
	protected Integer version;
	protected com.cartmatic.estore.common.model.catalog.Catalog catalog;
	//对应的根目录(内容)
	private Category category;

	protected java.util.Set appUsers = new java.util.HashSet();
	protected java.util.Set categorys = new java.util.HashSet();
	protected java.util.Set contents = new java.util.HashSet();
	protected java.util.Set productReviews = new java.util.HashSet();
	protected java.util.Set productStats = new java.util.HashSet();
	protected java.util.Set promoRules = new java.util.HashSet();
	protected java.util.Set salesOrders = new java.util.HashSet();
	protected java.util.Set shoppingcarts = new java.util.HashSet();
	protected java.util.Set shippingMethods = new java.util.HashSet();
	protected java.util.Set paymentMethods = new java.util.HashSet();
	protected java.util.Set systemConfigs = new java.util.HashSet();
	/**
	 * Default Empty Constructor for class Store
	 */
	public StoreTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class Store
	 */
	public StoreTbl (
		 Integer in_storeId
        ) {
		this.setStoreId(in_storeId);
    }

	
	public com.cartmatic.estore.common.model.catalog.Catalog getCatalog () {
		return catalog;
	}	
	
	public void setCatalog (com.cartmatic.estore.common.model.catalog.Catalog in_catalog) {
		this.catalog = in_catalog;
	}
	
	

	public java.util.Set getAppUsers () {
		return appUsers;
	}	
	
	public void setAppUsers (java.util.Set in_appUsers) {
		this.appUsers = in_appUsers;
	}

	public java.util.Set getCategorys () {
		return categorys;
	}	
	
	public void setCategorys (java.util.Set in_categorys) {
		this.categorys = in_categorys;
	}

	public java.util.Set getContents () {
		return contents;
	}	
	
	public void setContents (java.util.Set in_contents) {
		this.contents = in_contents;
	}

	public java.util.Set getProductReviews () {
		return productReviews;
	}	
	
	public void setProductReviews (java.util.Set in_productReviews) {
		this.productReviews = in_productReviews;
	}

	public java.util.Set getProductStats () {
		return productStats;
	}	
	
	public void setProductStats (java.util.Set in_productStats) {
		this.productStats = in_productStats;
	}

	public java.util.Set getPromoRules () {
		return promoRules;
	}	
	
	public void setPromoRules (java.util.Set in_promoRules) {
		this.promoRules = in_promoRules;
	}

	public java.util.Set getSalesOrders () {
		return salesOrders;
	}	
	
	public void setSalesOrders (java.util.Set in_salesOrders) {
		this.salesOrders = in_salesOrders;
	}

	public java.util.Set getShoppingcarts () {
		return shoppingcarts;
	}	
	
	public void setShoppingcarts (java.util.Set in_shoppingcarts) {
		this.shoppingcarts = in_shoppingcarts;
	}
    
	public java.util.Set getShippingMethods() {
		return shippingMethods;
	}

	public void setShippingMethods(java.util.Set shippingMethods) {
		this.shippingMethods = shippingMethods;
	}

	public java.util.Set getPaymentMethods() {
		return paymentMethods;
	}

	public void setPaymentMethods(java.util.Set paymentMethods) {
		this.paymentMethods = paymentMethods;
	}

	public java.util.Set getSystemConfigs() {
		return systemConfigs;
	}

	public void setSystemConfigs(java.util.Set systemConfigs) {
		this.systemConfigs = systemConfigs;
	}

	/**
	 * 	 * @return Integer
     * @hibernate.id column="storeId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getStoreId() {
		return this.storeId;
	}
	
	/**
	 * Set the storeId
	 */	
	public void setStoreId(Integer aValue) {
		this.storeId = aValue;
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
	        catalog = new com.cartmatic.estore.common.model.catalog.Catalog(aValue);
	        catalog.setVersion(new Integer(0));//set a version to cheat hibernate only
	    }
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
	 * @hibernate.property column="siteUrl" type="java.lang.String" length="255" not-null="true" unique="false"
	 */
	public String getSiteUrl() {
		return this.siteUrl;
	}
	
	/**
	 * Set the siteUrl
	 * @spring.validator type="required"
	 */	
	public void setSiteUrl(String aValue) {
		this.siteUrl = aValue;
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
		if (!(object instanceof StoreTbl)) {
			return false;
		}
		StoreTbl rhs = (StoreTbl) object;
		return new EqualsBuilder()
				.append(this.storeId, rhs.storeId)
						.append(this.name, rhs.name)
				.append(this.code, rhs.code)
				.append(this.siteUrl, rhs.siteUrl)
				.append(this.description, rhs.description)
				.append(this.status, rhs.status)
				.append(this.version, rhs.version)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.storeId) 
						.append(this.name) 
				.append(this.code) 
				.append(this.siteUrl) 
				.append(this.description) 
				.append(this.status) 
				.append(this.version) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("storeId", this.storeId) 
						.append("name", this.name) 
				.append("code", this.code) 
				.append("siteUrl", this.siteUrl) 
				.append("description", this.description) 
				.append("status", this.status) 
				.append("version", this.version) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "storeId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return storeId;
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

}