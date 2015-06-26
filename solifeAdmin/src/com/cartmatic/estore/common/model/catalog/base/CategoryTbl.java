package com.cartmatic.estore.common.model.catalog.base;

import java.io.Serializable;
import java.util.HashSet;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.cartmatic.estore.common.model.catalog.Catalog;
import com.cartmatic.estore.common.model.catalog.Category;
import com.cartmatic.estore.common.model.system.Store;
import com.cartmatic.estore.core.model.BaseObject;

/**
 * Category Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class CategoryTbl extends BaseObject implements Serializable {

    protected Integer categoryId;
	protected String categoryName;
	protected String categoryDescription;
	protected String categoryCode;
	protected String metaKeyword;
	protected String metaDescription;
	protected Short status;
	protected Integer createBy;
	protected Integer updateBy;
	protected java.util.Date createTime;
	protected java.util.Date updateTime;
	protected String templatePath;
	protected String categoryPath;
	protected String imageUrl;
	protected Integer version;
	protected Short categoryType;
	protected Short isLinkCategory;
	protected Integer sortOrder;
	protected String linkUrl;
	protected com.cartmatic.estore.common.model.catalog.Category category;

	protected java.util.Set contents = new java.util.HashSet();
	protected java.util.Set categorys = new java.util.HashSet();
	protected java.util.Set productCategorys = new java.util.HashSet();
	protected java.util.Set taxRates = new java.util.HashSet();
	
	protected java.util.Set categoryAttrValues = new HashSet();
	

	protected Catalog catalog;
	protected Store store;
	protected com.cartmatic.estore.common.model.catalog.Category linkedCategory;

	/**
	 * Default Empty Constructor for class Category
	 */
	public CategoryTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class Category
	 */
	public CategoryTbl (Integer in_categoryId) {
		this.setCategoryId(in_categoryId);
    }

	
	public com.cartmatic.estore.common.model.catalog.Category getCategory () {
		return category;
	}	
	
	public void setCategory (com.cartmatic.estore.common.model.catalog.Category in_category) {
		this.category = in_category;
	}

	public java.util.Set getContents() {
		return contents;
	}

	public void setContents(java.util.Set contents) {
		this.contents = contents;
	}
	
	public java.util.Set getCategorys () {
		return categorys;
	}	
	
	public void setCategorys (java.util.Set in_categorys) {
		this.categorys = in_categorys;
	}


	public java.util.Set getProductCategorys () {
		return productCategorys;
	}	
	
	public void setProductCategorys (java.util.Set in_productCategorys) {
		this.productCategorys = in_productCategorys;
	}

	public java.util.Set getTaxRates () {
		return taxRates;
	}	
	
	public void setTaxRates (java.util.Set in_taxRates) {
		this.taxRates = in_taxRates;
	}
    

	/**
	 * 	 * @return Integer
     * @hibernate.id column="categoryId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getCategoryId() {
		return this.categoryId;
	}
	
	/**
	 * Set the categoryId
	 */	
	public void setCategoryId(Integer aValue) {
		this.categoryId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="categoryName" type="java.lang.String" length="128" not-null="true" unique="false"
	 */
	public String getCategoryName() {
		return this.categoryName;
	}
	
	/**
	 * Set the categoryName
	 * @spring.validator type="required"
	 */	
	public void setCategoryName(String aValue) {
		this.categoryName = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="categoryDescription" type="java.lang.String" length="65535" not-null="false" unique="false"
	 */
	public String getCategoryDescription() {
		return this.categoryDescription;
	}
	
	/**
	 * Set the categoryDescription
	 */	
	public void setCategoryDescription(String aValue) {
		this.categoryDescription = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="categoryCode" type="java.lang.String" length="32" not-null="true" unique="false"
	 */
	public String getCategoryCode() {
		return this.categoryCode;
	}
	
	/**
	 * Set the categoryCode
	 * @spring.validator type="required"
	 */	
	public void setCategoryCode(String aValue) {
		this.categoryCode = aValue;
	}	


	
	/**
	 * 	 * @return String
	 * @hibernate.property column="metaKeyword" type="java.lang.String" length="256" not-null="false" unique="false"
	 */
	public String getMetaKeyword() {
		return this.metaKeyword;
	}
	
	/**
	 * Set the metaKeyword
	 */	
	public void setMetaKeyword(String aValue) {
		this.metaKeyword = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="metaDescription" type="java.lang.String" length="256" not-null="false" unique="false"
	 */
	public String getMetaDescription() {
		return this.metaDescription;
	}
	
	/**
	 * Set the metaDescription
	 */	
	public void setMetaDescription(String aValue) {
		this.metaDescription = aValue;
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
	 * @hibernate.property column="createBy" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getCreateBy() {
		return this.createBy;
	}
	
	/**
	 * Set the createBy
	 */	
	public void setCreateBy(Integer aValue) {
		this.createBy = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="updateBy" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getUpdateBy() {
		return this.updateBy;
	}
	
	/**
	 * Set the updateBy
	 */	
	public void setUpdateBy(Integer aValue) {
		this.updateBy = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="createTime" type="java.util.Date" length="0" not-null="false" unique="false"
	 */
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	
	/**
	 * Set the createTime
	 */	
	public void setCreateTime(java.util.Date aValue) {
		this.createTime = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="updateTime" type="java.util.Date" length="0" not-null="false" unique="false"
	 */
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}
	
	/**
	 * Set the updateTime
	 */	
	public void setUpdateTime(java.util.Date aValue) {
		this.updateTime = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="templatePath" type="java.lang.String" length="128" not-null="false" unique="false"
	 */
	public String getTemplatePath() {
		return this.templatePath;
	}
	
	/**
	 * Set the templatePath
	 */	
	public void setTemplatePath(String aValue) {
		this.templatePath = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="categoryPath" type="java.lang.String" length="32" not-null="true" unique="false"
	 */
	public String getCategoryPath() {
		return this.categoryPath;
	}
	
	/**
	 * Set the categoryPath
	 * @spring.validator type="required"
	 */	
	public void setCategoryPath(String aValue) {
		this.categoryPath = aValue;
	}	


	/**
	 * 	 * @return String
	 * @hibernate.property column="imageUrl" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getImageUrl() {
		return this.imageUrl;
	}
	
	/**
	 * Set the imageUrl
	 */	
	public void setImageUrl(String aValue) {
		this.imageUrl = aValue;
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
	 * 	 * @return Integer
	 */
	public Integer getParentId() {
		return this.getCategory()==null?null:this.getCategory().getCategoryId();
	}
	
	/**
	 * Set the parentId
	 */	
	public void setParentId(Integer aValue) {
	    if (aValue==null) {
	    	category = null;
	    } else {
	    	category = new com.cartmatic.estore.common.model.catalog.Category(aValue);
	        category.setVersion(new Integer(0));//set a version to cheat hibernate only
	    }
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="categoryType" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getCategoryType() {
		return this.categoryType;
	}
	
	/**
	 * Set the categoryType
	 * @spring.validator type="required"
	 */	
	public void setCategoryType(Short aValue) {
		this.categoryType = aValue;
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="isLinkCategory" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
	public Short getIsLinkCategory() {
		return this.isLinkCategory;
	}
	
	/**
	 * Set the isLinkCategory
	 */	
	public void setIsLinkCategory(Short aValue) {
		this.isLinkCategory = aValue;
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
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof CategoryTbl)) {
			return false;
		}
		CategoryTbl rhs = (CategoryTbl) object;
		return new EqualsBuilder()
				.append(this.categoryId, rhs.categoryId)
				.append(this.categoryName, rhs.categoryName)
				.append(this.categoryDescription, rhs.categoryDescription)
				.append(this.categoryCode, rhs.categoryCode)
				.append(this.metaKeyword, rhs.metaKeyword)
				.append(this.metaDescription, rhs.metaDescription)
				.append(this.status, rhs.status)
				.append(this.createBy, rhs.createBy)
				.append(this.updateBy, rhs.updateBy)
				.append(this.createTime, rhs.createTime)
				.append(this.updateTime, rhs.updateTime)
				.append(this.templatePath, rhs.templatePath)
				.append(this.categoryPath, rhs.categoryPath)
				.append(this.imageUrl, rhs.imageUrl)
				.append(this.version, rhs.version)
						.append(this.categoryType, rhs.categoryType)
				.append(this.isLinkCategory, rhs.isLinkCategory)
				.append(this.sortOrder, rhs.sortOrder)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.categoryId) 
				.append(this.categoryName) 
				.append(this.categoryDescription) 
				.append(this.categoryCode) 
				.append(this.metaKeyword) 
				.append(this.metaDescription) 
				.append(this.status) 
				.append(this.createBy) 
				.append(this.updateBy) 
				.append(this.createTime) 
				.append(this.updateTime) 
				.append(this.templatePath) 
				.append(this.categoryPath) 
				.append(this.imageUrl) 
				.append(this.version) 
						.append(this.categoryType) 
				.append(this.isLinkCategory) 
				.append(this.sortOrder) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("categoryId", this.categoryId) 
				.append("categoryName", this.categoryName) 
				.append("categoryDescription", this.categoryDescription) 
				.append("categoryCode", this.categoryCode) 
				.append("metaKeyword", this.metaKeyword) 
				.append("metaDescription", this.metaDescription) 
				.append("status", this.status) 
				.append("createBy", this.createBy) 
				.append("updateBy", this.updateBy) 
				.append("createTime", this.createTime) 
				.append("updateTime", this.updateTime) 
				.append("templatePath", this.templatePath) 
				.append("categoryPath", this.categoryPath) 
				.append("imageUrl", this.imageUrl) 
				.append("version", this.version) 
						.append("categoryType", this.categoryType) 
				.append("isLinkCategory", this.isLinkCategory) 
				.append("sortOrder", this.sortOrder) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "categoryId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return categoryId;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public java.util.Set getCategoryAttrValues() {
		return categoryAttrValues;
	}

	public void setCategoryAttrValues(java.util.Set categoryAttrValues) {
		this.categoryAttrValues = categoryAttrValues;
	}

	public Catalog getCatalog() {
		return catalog;
	}

	public void setCatalog(Catalog catalog) {
		this.catalog = catalog;
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
	    	catalog = new Catalog(aValue);
	    	catalog.setVersion(new Integer(0));//set a version to cheat hibernate only
	    }
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}
	
	/**
	 * 	 * @return Integer
	 */
	public Integer getStoreId() {
		return this.getStore()==null?null:this.getStore().getStoreId();
	}
	
	/**
	 * Set the storeId
	 */	
	public void setStoreId(Integer aValue) {
	    if (aValue==null) {
	    	store = null;
	    } else {
	    	store = new Store(aValue);
	    	store.setVersion(new Integer(0));//set a version to cheat hibernate only
	    }
	}

	public com.cartmatic.estore.common.model.catalog.Category getLinkedCategory() {
		return linkedCategory;
	}

	public void setLinkedCategory(com.cartmatic.estore.common.model.catalog.Category linkedCategory) {
		this.linkedCategory = linkedCategory;
	}
	
	/**
	 * 	 * @return Integer
	 */
	public Integer getLinkedCategoryId() {
		return this.getLinkedCategory()==null?null:this.getLinkedCategory().getCategoryId();
	}
	
	/**
	 * Set the storeId
	 */	
	public void setLinkedCategoryId(Integer aValue) {
	    if (aValue==null) {
	    	linkedCategory = null;
	    } else {
	    	linkedCategory = new Category(aValue);
	    	linkedCategory.setVersion(new Integer(0));//set a version to cheat hibernate only
	    }
	}
}