package com.cartmatic.estore.common.model.catalog.base;

import java.io.Serializable;

import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.common.model.catalog.ProductAttGroup;
import com.cartmatic.estore.common.model.catalog.ProductRateItem;
import com.cartmatic.estore.common.model.catalog.ProductTypeSkuOption;
import com.cartmatic.estore.core.model.BaseObject;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * ProductType Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class ProductTypeTbl extends BaseObject implements Serializable {
	protected Integer productTypeId;
	protected String productTypeName;
	protected String productTypeDescription;
	protected String templatePath;
	protected Integer minOrderQuantity;
	protected Short status;
	protected Integer version; 
	private int ioi=0;

	protected java.util.Set<ProductAttGroup> productAttGroups = new java.util.HashSet<ProductAttGroup>();
	protected java.util.Set<Product> products = new java.util.HashSet<Product>();
	protected java.util.Set<ProductRateItem> productRateItems = new java.util.HashSet<ProductRateItem>();
	protected java.util.Set<ProductTypeSkuOption> productTypeSkuOptions = new java.util.HashSet<ProductTypeSkuOption> ();

	/**
	 * Default Empty Constructor for class ProductType
	 */
	public ProductTypeTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class ProductType
	 */
	public ProductTypeTbl (
		 Integer in_productTypeId
        ) {
		this.setProductTypeId(in_productTypeId);
    }


	public java.util.Set<ProductAttGroup> getProductAttGroups () {
		return productAttGroups;
	}	
	
	public void setProductAttGroups (java.util.Set<ProductAttGroup> in_productAttGroups) {
		this.productAttGroups = in_productAttGroups;
	}

	public java.util.Set<Product> getProducts () {
		return products;
	}	
	
	public void setProducts (java.util.Set<Product> in_products) {
		this.products = in_products;
	}

	public java.util.Set<ProductRateItem> getProductRateItems () {
		return productRateItems;
	}	
	
	public void setProductRateItems (java.util.Set<ProductRateItem> in_productRateItems) {
		this.productRateItems = in_productRateItems;
	}

	public java.util.Set<ProductTypeSkuOption> getProductTypeSkuOptions () {
		return productTypeSkuOptions;
	}	
	
	public void setProductTypeSkuOptions (java.util.Set<ProductTypeSkuOption> in_productTypeSkuOptions) {
		this.productTypeSkuOptions = in_productTypeSkuOptions;
	}
    

	/**
	 * 	 * @return Integer
     * @hibernate.id column="productTypeId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getProductTypeId() {
		return this.productTypeId;
	}
	
	/**
	 * Set the productTypeId
	 */	
	public void setProductTypeId(Integer aValue) {
		this.productTypeId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="productTypeName" type="java.lang.String" length="32" not-null="true" unique="false"
	 */
	public String getProductTypeName() {
		return this.productTypeName;
	}
	
	/**
	 * Set the productTypeName
	 * @spring.validator type="required"
	 */	
	public void setProductTypeName(String aValue) {
		this.productTypeName = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="productTypeDescription" type="java.lang.String" length="128" not-null="false" unique="false"
	 */
	public String getProductTypeDescription() {
		return this.productTypeDescription;
	}
	
	/**
	 * Set the productTypeDescription
	 */	
	public void setProductTypeDescription(String aValue) {
		this.productTypeDescription = aValue;
	}	

	/**
	 * 添加产品时前台产品默认显示模板	 * @return String
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
	 * 添加产品时默认的最小订购量	 * @return Integer
	 * @hibernate.property column="minOrderQuantity" type="java.lang.Integer" length="10" not-null="true" unique="false"
	 */
	public Integer getMinOrderQuantity() {
		return this.minOrderQuantity;
	}
	
	/**
	 * Set the minOrderQuantity
	 * @spring.validator type="required"
	 */	
	public void setMinOrderQuantity(Integer aValue) {
		this.minOrderQuantity = aValue;
	}	

	/**
	 * 1=激活
            2=非激活	 * @return Short
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
		if (!(object instanceof ProductTypeTbl)) {
			return false;
		}
		ProductTypeTbl rhs = (ProductTypeTbl) object;
		return new EqualsBuilder()
				.append(this.productTypeId, rhs.productTypeId)
				.append(this.productTypeName, rhs.productTypeName)
				.append(this.productTypeDescription, rhs.productTypeDescription)
				.append(this.templatePath, rhs.templatePath)
				.append(this.minOrderQuantity, rhs.minOrderQuantity)
				.append(this.status, rhs.status)
				.append(this.version, rhs.version)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.productTypeId) 
				.append(this.productTypeName) 
				.append(this.productTypeDescription) 
				.append(this.templatePath) 
				.append(this.minOrderQuantity) 
				.append(this.status) 
				.append(this.version) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("productTypeId", this.productTypeId) 
				.append("productTypeName", this.productTypeName) 
				.append("productTypeDescription", this.productTypeDescription) 
				.append("templatePath", this.templatePath) 
				.append("minOrderQuantity", this.minOrderQuantity) 
				.append("status", this.status) 
				.append("version", this.version) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "productTypeId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return productTypeId;
	}

}