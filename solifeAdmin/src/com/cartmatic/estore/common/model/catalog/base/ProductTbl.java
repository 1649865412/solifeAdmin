package com.cartmatic.estore.common.model.catalog.base;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.cartmatic.estore.common.model.attribute.ProductAttrValue;
import com.cartmatic.estore.common.model.catalog.Accessory;
import com.cartmatic.estore.common.model.catalog.ProductCategory;
import com.cartmatic.estore.common.model.catalog.ProductMedia;
import com.cartmatic.estore.common.model.catalog.ProductMediaUp;
import com.cartmatic.estore.common.model.catalog.ProductReview;
import com.cartmatic.estore.common.model.catalog.ProductSku;
import com.cartmatic.estore.common.model.catalog.ProductStat;
import com.cartmatic.estore.common.model.supplier.Supplier;
import com.cartmatic.estore.core.model.BaseObject;

/**
 * Product Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class ProductTbl extends BaseObject implements Serializable {
    protected Integer productId;
    protected Integer defaultSupplierProductId;
	protected String productName;
	protected String productCode;
	protected java.util.Date planStartTime;
	protected java.util.Date planEndTime;
	protected java.util.Date publishTime;
	protected String url;
	protected String title;
	protected String metaKeyword;
	protected String metaDescription;
	protected Short isAllowReview;
	/**
	 * 1=普通产品 2=变种产品 3=产品包
	 */
	protected Short productKind;

	protected java.util.Date expectedReleaseDate;
	protected Short availabilityRule;
	protected Integer preOrBackOrderLimit;
	protected Integer minOrderQuantity;
	protected Integer membershipId;
	protected java.util.Date createTime;
	protected java.util.Date updateTime;
	protected Integer createBy;
	protected Integer updateBy;
	protected String templatePath;
	protected Integer version;
	protected Short status;
	/**
	 * 辅助性属性（产品原状态）
	 */
	private Short origStatus;
	/**
	 * 辅助性属性（产品原销售规则）
	 */
	private Short origAvailabilityRule;
	private String extra1;
	
	protected Supplier supplier;
	protected com.cartmatic.estore.common.model.catalog.Brand brand;
	protected com.cartmatic.estore.common.model.catalog.ProductType productType;
	protected com.cartmatic.estore.common.model.catalog.ProductDescription productDescription;
	protected com.cartmatic.estore.common.model.catalog.ProductSku defaultProductSku;
	
	protected com.cartmatic.estore.common.model.catalog.ProductHandDraw productHandDraw;

	protected java.util.Set<ProductAttrValue> productAttrValues = new java.util.HashSet<ProductAttrValue>();
	protected java.util.Set<ProductStat> productStats = new java.util.HashSet<ProductStat>();
	protected java.util.Set<ProductCategory> productCategorys = new java.util.HashSet<ProductCategory>();
	protected java.util.Set<ProductMedia> productMedias = new java.util.HashSet<ProductMedia>();
	protected java.util.Set<ProductReview> productReviews = new java.util.HashSet<ProductReview>();
	protected java.util.Set<ProductSku> productSkus = new java.util.HashSet<ProductSku>();
	protected java.util.Set<Accessory> accessories = new java.util.HashSet<Accessory>();
	
	protected java.util.Set<ProductMediaUp> productMediasUp = new java.util.HashSet<ProductMediaUp>();
	
	/**
	 * Default Empty Constructor for class Product
	 */
	public ProductTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class Product
	 */
	public ProductTbl (
		 Integer in_productId
        ) {
		this.setProductId(in_productId);
    }
	
	public com.cartmatic.estore.common.model.catalog.Brand getBrand () {
		return brand;
	}	
	
	public void setBrand (com.cartmatic.estore.common.model.catalog.Brand in_brand) {
		this.brand = in_brand;
	}
	
	public com.cartmatic.estore.common.model.catalog.ProductType getProductType () {
		return productType;
	}	
	
	public void setProductType (com.cartmatic.estore.common.model.catalog.ProductType in_productType) {
		this.productType = in_productType;
	}
	
	public com.cartmatic.estore.common.model.catalog.ProductDescription getProductDescription () {
		return productDescription;
	}	
	
	public void setProductDescription (com.cartmatic.estore.common.model.catalog.ProductDescription in_productDescription) {
		this.productDescription = in_productDescription;
	}
	
	public Integer getDefaultSupplierProductId() {
		return defaultSupplierProductId;
	}

	public void setDefaultSupplierProductId(Integer defaultSupplierProductId) {
		this.defaultSupplierProductId = defaultSupplierProductId;
	}

	public com.cartmatic.estore.common.model.catalog.ProductSku getDefaultProductSku() {
		return defaultProductSku;
	}

	public void setDefaultProductSku(
			com.cartmatic.estore.common.model.catalog.ProductSku defaultProductSku) {
		this.defaultProductSku = defaultProductSku;
	}

	public java.util.Set<ProductAttrValue> getProductAttrValues () {
		return productAttrValues;
	}	
	
	public void setProductAttrValues (java.util.Set<ProductAttrValue> in_productAttrValues) {
		this.productAttrValues = in_productAttrValues;
	}

	public java.util.Set<ProductStat> getProductStats () {
		return productStats;
	}	
	
	public void setProductStats (java.util.Set<ProductStat> in_productStats) {
		this.productStats = in_productStats;
	}

	public java.util.Set<ProductCategory> getProductCategorys () {
		return productCategorys;
	}	
	
	public void setProductCategorys (java.util.Set<ProductCategory> in_productCategorys) {
		this.productCategorys = in_productCategorys;
	}

	public java.util.Set<ProductMedia> getProductMedias () {
		return productMedias;
	}	
	
	public void setProductMedias (java.util.Set<ProductMedia> in_productMedias) {
		this.productMedias = in_productMedias;
	}

	public java.util.Set<ProductReview> getProductReviews () {
		return productReviews;
	}	
	
	public void setProductReviews (java.util.Set<ProductReview> in_productReviews) {
		this.productReviews = in_productReviews;
	}

	public java.util.Set<ProductSku> getProductSkus () {
		return productSkus;
	}	
	
	public void setProductSkus (java.util.Set<ProductSku> in_productSkus) {
		this.productSkus = in_productSkus;
	}
    

	public java.util.Set<Accessory> getAccessories() {
		return accessories;
	}

	public void setAccessories(java.util.Set<Accessory> accessories) {
		this.accessories = accessories;
	}

	/**
	 * 	 * @return Integer
     * @hibernate.id column="productId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getProductId() {
		return this.productId;
	}
	
	/**
	 * Set the productId
	 */	
	public void setProductId(Integer aValue) {
		this.productId = aValue;
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
	 * 	 * @return Integer
	 */
	public Integer getDefaultProductSkuId() {
		return this.getDefaultProductSku()==null?null:this.getDefaultProductSku().getProductSkuId();
	}
	
	/**
	 * Set the defaultProductSkuId
	 */	
	public void setDefaultProductSkuId(Integer aValue) {
	    if (aValue==null) {
	    	defaultProductSku = null;
	    }  else {
	    	defaultProductSku = new com.cartmatic.estore.common.model.catalog.ProductSku(aValue);
	    	defaultProductSku.setVersion(new Integer(0));//set a version to cheat hibernate only
	    }
	}	

	/**
	 * 	 * @return Integer
	 */
	public Integer getBrandId() {
		return this.getBrand()==null?null:this.getBrand().getBrandId();
	}
	
	/**
	 * Set the brandId
	 */	
	public void setBrandId(Integer aValue) {
	    if (aValue==null) {
	    	brand = null;
	    } else {
			brand = new com.cartmatic.estore.common.model.catalog.Brand(aValue);
			brand.setVersion(new Integer(0));//set a version to cheat hibernate only
	    }
	}	


	/**
	 * 	 * @return Integer
	 */
	public Integer getProductDescriptionId() {
		return this.getProductDescription()==null?null:this.getProductDescription().getProductDescriptionId();
	}
	
	/**
	 * Set the productDescriptionId
	 */	
	public void setProductDescriptionId(Integer aValue) {
	    if (aValue==null) {
	    	productDescription = null;
	    } else {
	    	productDescription = new com.cartmatic.estore.common.model.catalog.ProductDescription(aValue);
	        productDescription.setVersion(new Integer(0));//set a version to cheat hibernate only
	    }
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="productName" type="java.lang.String" length="128" not-null="true" unique="false"
	 */
	public String getProductName() {
		return this.productName;
	}
	
	/**
	 * Set the productName
	 * @spring.validator type="required"
	 */	
	public void setProductName(String aValue) {
		this.productName = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="productCode" type="java.lang.String" length="32" not-null="true" unique="false"
	 */
	public String getProductCode() {
		return this.productCode;
	}
	
	/**
	 * Set the productCode
	 * @spring.validator type="required"
	 */	
	public void setProductCode(String aValue) {
		this.productCode = aValue;
	}	

	/**
	 * 产品计划上架时间	 * @return java.util.Date
	 * @hibernate.property column="planStartTime" type="java.util.Date" length="0" not-null="true" unique="false"
	 */
	public java.util.Date getPlanStartTime() {
		return this.planStartTime;
	}
	
	/**
	 * Set the planStartTime
	 * @spring.validator type="required"
	 */	
	public void setPlanStartTime(java.util.Date aValue) {
		this.planStartTime = aValue;
	}	

	/**
	 * 产品计划下架时间	 * @return java.util.Date
	 * @hibernate.property column="planEndTime" type="java.util.Date" length="0" not-null="false" unique="false"
	 */
	public java.util.Date getPlanEndTime() {
		return this.planEndTime;
	}
	
	/**
	 * Set the planEndTime
	 */	
	public void setPlanEndTime(java.util.Date aValue) {
		this.planEndTime = aValue;
	}	

	/**
	 * 发布时间	 * @return java.util.Date
	 * @hibernate.property column="publishTime" type="java.util.Date" length="0" not-null="false" unique="false"
	 */
	public java.util.Date getPublishTime() {
		return this.publishTime;
	}
	
	/**
	 * Set the publishTime
	 */	
	public void setPublishTime(java.util.Date aValue) {
		this.publishTime = aValue;
	}	

	/**
	 * SEO前台浏览url	 * @return String
	 * @hibernate.property column="url" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getUrl() {
		return this.url;
	}
	
	/**
	 * Set the url
	 */	
	public void setUrl(String aValue) {
		this.url = aValue;
	}	

	/**
	 * seo title	 * @return String
	 * @hibernate.property column="title" type="java.lang.String" length="128" not-null="false" unique="false"
	 */
	public String getTitle() {
		return this.title;
	}
	
	/**
	 * Set the title
	 */	
	public void setTitle(String aValue) {
		this.title = aValue;
	}	

	/**
	 * seo	 * @return String
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
	 * seo	 * @return String
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
	 * 是否接受评论	 * @return Short
	 * @hibernate.property column="isAllowReview" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
	public Short getIsAllowReview() {
		return this.isAllowReview;
	}
	
	/**
	 * Set the isAllowReview
	 */	
	public void setIsAllowReview(Short aValue) {
		this.isAllowReview = aValue;
	}	

	/**
	 * 预订时，预计的到货日期	 * @return java.util.Date
	 * @hibernate.property column="expectedReleaseDate" type="java.util.Date" length="0" not-null="false" unique="false"
	 */
	public java.util.Date getExpectedReleaseDate() {
		return this.expectedReleaseDate;
	}
	
	/**
	 * Set the expectedReleaseDate
	 */	
	public void setExpectedReleaseDate(java.util.Date aValue) {
		this.expectedReleaseDate = aValue;
	}	

	/**
	 * 销售规则，有库存才可以购买、预订、缺货销售、永远可售。	 * @return Short
	 * @hibernate.property column="availabilityRule" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getAvailabilityRule() {
		return this.availabilityRule;
	}
	
	/**
	 * Set the availabilityRule
	 * @spring.validator type="required"
	 */	
	public void setAvailabilityRule(Short aValue) {
		this.availabilityRule = aValue;
		if(origAvailabilityRule==null)
			origAvailabilityRule=aValue;
	}	

	/**
	 * 可预订的产品的最大数量	 * @return Integer
	 * @hibernate.property column="preOrBackOrderLimit" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getPreOrBackOrderLimit() {
		return this.preOrBackOrderLimit;
	}
	
	/**
	 * Set the preOrBackOrderLimit
	 */	
	public void setPreOrBackOrderLimit(Integer aValue) {
		this.preOrBackOrderLimit = aValue;
	}	

	/**
	 * 最少订购量	 * @return Integer
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
	 * 	 * @return Integer
	 * @hibernate.property column="membershipId" type="java.lang.Integer" length="10" not-null="true" unique="false"
	 */
	public Integer getMembershipId() {
		return this.membershipId;
	}
	
	/**
	 * Set the membershipId
	 * @spring.validator type="required"
	 */	
	public void setMembershipId(Integer aValue) {
		this.membershipId = aValue;
	}	


	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="createTime" type="java.util.Date" length="0" not-null="true" unique="false"
	 */
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	
	/**
	 * Set the createTime
	 * @spring.validator type="required"
	 */	
	public void setCreateTime(java.util.Date aValue) {
		this.createTime = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="updateTime" type="java.util.Date" length="0" not-null="true" unique="false"
	 */
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}
	
	/**
	 * Set the updateTime
	 * @spring.validator type="required"
	 */	
	public void setUpdateTime(java.util.Date aValue) {
		this.updateTime = aValue;
	}	

	/**
	 * 显示模版	 * @return String
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
	 * 0=草稿
            1=激活
            2=非激活
            3=已删除
            6=待删除	 * @return Short
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
		if(origStatus==null)
			origStatus=aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof ProductTbl)) {
			return false;
		}
		ProductTbl rhs = (ProductTbl) object;
		return new EqualsBuilder()
				.append(this.productId, rhs.productId)
														.append(this.productName, rhs.productName)
				.append(this.productCode, rhs.productCode)
				.append(this.planStartTime, rhs.planStartTime)
				.append(this.planEndTime, rhs.planEndTime)
				.append(this.publishTime, rhs.publishTime)
				.append(this.url, rhs.url)
				.append(this.title, rhs.title)
				.append(this.metaKeyword, rhs.metaKeyword)
				.append(this.defaultSupplierProductId, rhs.defaultSupplierProductId)
				.append(this.metaDescription, rhs.metaDescription)
				.append(this.isAllowReview, rhs.isAllowReview)
				.append(this.expectedReleaseDate, rhs.expectedReleaseDate)
				.append(this.availabilityRule, rhs.availabilityRule)
				.append(this.preOrBackOrderLimit, rhs.preOrBackOrderLimit)
				.append(this.minOrderQuantity, rhs.minOrderQuantity)
				.append(this.membershipId, rhs.membershipId)
				.append(this.productKind, rhs.productKind)
				.append(this.createTime, rhs.createTime)
				.append(this.updateTime, rhs.updateTime)
				.append(this.templatePath, rhs.templatePath)
				.append(this.version, rhs.version)
				.append(this.status, rhs.status)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.productId) 
				.append(this.productName) 
				.append(this.productCode) 
				.append(this.planStartTime) 
				.append(this.planEndTime) 
				.append(this.publishTime) 
				.append(this.defaultSupplierProductId)
				.append(this.url) 
				.append(this.title) 
				.append(this.metaKeyword) 
				.append(this.metaDescription) 
				.append(this.isAllowReview) 
				.append(this.expectedReleaseDate) 
				.append(this.availabilityRule) 
				.append(this.preOrBackOrderLimit) 
				.append(this.minOrderQuantity) 
				.append(this.membershipId) 
				.append(this.productKind) 
				.append(this.createTime) 
				.append(this.updateTime) 
				.append(this.templatePath) 
				.append(this.version) 
				.append(this.status) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("productId", this.productId) 
														.append("productName", this.productName) 
				.append("productCode", this.productCode) 
				.append("planStartTime", this.planStartTime) 
				.append("planEndTime", this.planEndTime) 
				.append("publishTime", this.publishTime) 
				.append("url", this.url) 
				.append("title", this.title) 
				.append("defaultSupplierProductId", this.defaultSupplierProductId)
				.append("metaKeyword", this.metaKeyword) 
				.append("metaDescription", this.metaDescription) 
				.append("isAllowReview", this.isAllowReview) 
				.append("expectedReleaseDate", this.expectedReleaseDate) 
				.append("availabilityRule", this.availabilityRule) 
				.append("preOrBackOrderLimit", this.preOrBackOrderLimit) 
				.append("minOrderQuantity", this.minOrderQuantity) 
				.append("membershipId", this.membershipId) 
				.append("productKind", this.productKind) 
				.append("createTime", this.createTime) 
				.append("updateTime", this.updateTime) 
				.append("templatePath", this.templatePath) 
				.append("version", this.version) 
				.append("status", this.status) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "productId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return productId;
	}

	/**
	 * 1=普通产品 2=变种产品 3=产品包
	 * @return
	 */
	public Short getProductKind() {
		return productKind;
	}

	public void setProductKind(Short productKind) {
		this.productKind = productKind;
	}

	public Short getOrigStatus() {
		return origStatus;
	}

	public String getExtra1() {
		return extra1;
	}

	public void setExtra1(String extra1) {
		this.extra1 = extra1;
	}

	public Short getOrigAvailabilityRule() {
		return origAvailabilityRule;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
	
	/**
	 * 	 * @return Integer
	 */
	public Integer getSupplierId() {
		return this.getSupplier()==null?null:this.getSupplier().getSupplierId();
	}
	
	/**
	 * Set the supplierId
	 */	
	public void setSupplierId(Integer aValue) {
	    if (aValue==null) {
	    	supplier = null;
	    } else {
	    	supplier = new Supplier(aValue);
	    	supplier.setVersion(new Integer(0));//set a version to cheat hibernate only
	    }
	}
	
	public void setCreateBy(Integer createBy) {
		this.createBy=createBy;
	}

	public void setUpdateBy(Integer updateBy) {
		this.updateBy=updateBy;
	}


	public Integer getCreateBy() {
		return createBy;
	}

	public Integer getUpdateBy() {
		return updateBy;
	}

	public com.cartmatic.estore.common.model.catalog.ProductHandDraw getProductHandDraw() {
		return productHandDraw;
	}

	public void setProductHandDraw(com.cartmatic.estore.common.model.catalog.ProductHandDraw productHandDraw) {
		this.productHandDraw = productHandDraw;
	}

	public java.util.Set<ProductMediaUp> getProductMediasUp() {
		return productMediasUp;
	}

	public void setProductMediasUp(java.util.Set<ProductMediaUp> productMediasUp) {
		this.productMediasUp = productMediasUp;
	}

}