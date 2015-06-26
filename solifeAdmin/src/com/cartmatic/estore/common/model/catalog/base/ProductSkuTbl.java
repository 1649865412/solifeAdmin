package com.cartmatic.estore.common.model.catalog.base;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.cartmatic.estore.common.model.catalog.ProductMedia;
import com.cartmatic.estore.common.model.catalog.ProductPackageItem;
import com.cartmatic.estore.common.model.catalog.ProductSkuOptionValue;
import com.cartmatic.estore.common.model.catalog.ProductStat;
import com.cartmatic.estore.common.model.catalog.WholesalePrice;
import com.cartmatic.estore.common.model.inventory.Inventory;
import com.cartmatic.estore.core.model.BaseObject;

/**
 * ProductSku Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * 1=������\r\n2=���������
 */
public class ProductSkuTbl extends BaseObject implements Serializable {

    protected Integer productSkuId;
	protected String productSkuCode;
	protected BigDecimal weight;
	protected BigDecimal length;
	protected BigDecimal width;
	protected BigDecimal height;
	protected String image;
	protected Short skuKind;
	protected BigDecimal price;
	protected BigDecimal costPrice;
	protected BigDecimal salePrice;
	protected BigDecimal listPrice;
	protected java.util.Date createTime;
	protected java.util.Date updateTime;
	protected Integer version;
	protected Short status;
	protected com.cartmatic.estore.common.model.catalog.Product product;

	protected java.util.Set<Inventory> inventorys = new java.util.HashSet<Inventory>();
	protected java.util.Set productPackageItems = new java.util.HashSet();
	protected java.util.Set<ProductSkuOptionValue> productSkuOptionValues = new java.util.HashSet<ProductSkuOptionValue>();
	
	protected Set<ProductMedia> productMedias ;
	
	protected ProductStat productStat;
	/**
	 * 按minQuantity排序，由低到高
	 */
	protected java.util.Set<WholesalePrice> wholesalePrices = new java.util.HashSet<WholesalePrice>();
	private Short origStatus;

	
	/**
	 * Default Empty Constructor for class ProductSku
	 */
	public ProductSkuTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class ProductSku
	 */
	public ProductSkuTbl (
		 Integer in_productSkuId
        ) {
		this.setProductSkuId(in_productSkuId);
    }

	
	public com.cartmatic.estore.common.model.catalog.Product getProduct () {
		return product;
	}	
	
	public void setProduct (com.cartmatic.estore.common.model.catalog.Product in_product) {
		this.product = in_product;
	}

	public java.util.Set<Inventory> getInventorys () {
		return inventorys;
	}	
	
	public void setInventorys (java.util.Set<Inventory> in_inventorys) {
		this.inventorys = in_inventorys;
	}

	public java.util.Set<ProductPackageItem> getProductPackageItems () {
		return productPackageItems;
	}	
	
	public void setProductPackageItems (java.util.Set<ProductPackageItem> in_productPackageItems) {
		this.productPackageItems = in_productPackageItems;
	}

	public java.util.Set<ProductSkuOptionValue> getProductSkuOptionValues () {
		return productSkuOptionValues;
	}	
	
	public void setProductSkuOptionValues (java.util.Set<ProductSkuOptionValue> in_productSkuOptionValues) {
		this.productSkuOptionValues = in_productSkuOptionValues;
	}

	public java.util.Set<WholesalePrice> getWholesalePrices () {
		return wholesalePrices;
	}	
	
	public void setWholesalePrices (java.util.Set<WholesalePrice> in_wholesalePrices) {
		this.wholesalePrices = in_wholesalePrices;
	}
    

	/**
	 * 	 * @return Integer
     * @hibernate.id column="productSkuId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getProductSkuId() {
		return this.productSkuId;
	}
	
	/**
	 * Set the productSkuId
	 */	
	public void setProductSkuId(Integer aValue) {
		this.productSkuId = aValue;
	}	

	/**
	 * 	 * @return Integer
	 */
	public Integer getProductId() {
		return this.getProduct()==null?null:this.getProduct().getProductId();
	}
	
	/**
	 * Set the productId
	 */	
	public void setProductId(Integer aValue) {
	    if (aValue==null) {
	    	product = null;
	    } else {
	    	product = new com.cartmatic.estore.common.model.catalog.Product(aValue);
	        product.setVersion(new Integer(0));//set a version to cheat hibernate only
	    }
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="productSkuCode" type="java.lang.String" length="32" not-null="true" unique="false"
	 */
	public String getProductSkuCode() {
		return this.productSkuCode;
	}
	
	/**
	 * Set the productSkuCode
	 * @spring.validator type="required"
	 */	
	public void setProductSkuCode(String aValue) {
		this.productSkuCode = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="weight" type="java.math.BigDecimal" length="12" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getWeight() {
		return this.weight;
	}
	
	/**
	 * Set the weight
	 */	
	public void setWeight(java.math.BigDecimal aValue) {
		this.weight = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="length" type="java.math.BigDecimal" length="12" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getLength() {
		return this.length;
	}
	
	/**
	 * Set the length
	 */	
	public void setLength(java.math.BigDecimal aValue) {
		this.length = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="width" type="java.math.BigDecimal" length="12" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getWidth() {
		return this.width;
	}
	
	/**
	 * Set the width
	 */	
	public void setWidth(java.math.BigDecimal aValue) {
		this.width = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="height" type="java.math.BigDecimal" length="12" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getHeight() {
		return this.height;
	}
	
	/**
	 * Set the height
	 */	
	public void setHeight(java.math.BigDecimal aValue) {
		this.height = aValue;
	}	

	/**
	 * 产品图片	 * @return String
	 * @hibernate.property column="image" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getImage() {
		return this.image;
	}
	
	/**
	 * Set the image
	 */	
	public void setImage(String aValue) {
		this.image = aValue;
	}	

	/**
	 * 产品性质,决定是否可运输等
            1=实体
            2=服务
            3=下载
            4=点卡	 * @return Short
	 * @hibernate.property column="skuKind" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getSkuKind() {
		return this.skuKind;
	}
	
	/**
	 * Set the skuKind
	 * @spring.validator type="required"
	 */	
	public void setSkuKind(Short aValue) {
		this.skuKind = aValue;
	}	

	/**
	 * 售价	 * @return java.math.BigDecimal
	 * @hibernate.property column="price" type="java.math.BigDecimal" length="12" not-null="true" unique="false"
	 */
	public java.math.BigDecimal getPrice() {
		return this.price;
	}
	
	/**
	 * Set the price
	 * @spring.validator type="required"
	 */	
	public void setPrice(java.math.BigDecimal aValue) {
		this.price = aValue;
	}	

	public BigDecimal getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(BigDecimal costPrice) {
		this.costPrice = costPrice;
	}

	/**
	 * 特价	 * @return java.math.BigDecimal
	 * @hibernate.property column="salePrice" type="java.math.BigDecimal" length="12" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getSalePrice() {
		return this.salePrice;
	}
	
	/**
	 * Set the salePrice
	 */	
	public void setSalePrice(java.math.BigDecimal aValue) {
		this.salePrice = aValue;
	}	

	/**
	 * 市场价格	 * @return java.math.BigDecimal
	 * @hibernate.property column="listPrice" type="java.math.BigDecimal" length="12" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getListPrice() {
		return this.listPrice;
	}
	
	/**
	 * Set the listPrice
	 */	
	public void setListPrice(java.math.BigDecimal aValue) {
		this.listPrice = aValue;
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
		if(origStatus==null)
			origStatus=aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof ProductSkuTbl)) {
			return false;
		}
		ProductSkuTbl rhs = (ProductSkuTbl) object;
		return new EqualsBuilder()
				.append(this.productSkuId, rhs.productSkuId)
						.append(this.productSkuCode, rhs.productSkuCode)
				.append(this.weight, rhs.weight)
				.append(this.length, rhs.length)
				.append(this.width, rhs.width)
				.append(this.height, rhs.height)
				.append(this.image, rhs.image)
				.append(this.skuKind, rhs.skuKind)
				.append(this.price, rhs.price)
				.append(this.salePrice, rhs.salePrice)
				.append(this.listPrice, rhs.listPrice)
				.append(this.createTime, rhs.createTime)
				.append(this.updateTime, rhs.updateTime)
				.append(this.version, rhs.version)
				.append(this.status, rhs.status)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.productSkuId) 
						.append(this.productSkuCode) 
				.append(this.weight) 
				.append(this.length) 
				.append(this.width) 
				.append(this.height) 
				.append(this.image) 
				.append(this.skuKind) 
				.append(this.price) 
				.append(this.salePrice) 
				.append(this.listPrice) 
				.append(this.createTime) 
				.append(this.updateTime) 
				.append(this.version) 
				.append(this.status) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("productSkuId", this.productSkuId) 
						.append("productSkuCode", this.productSkuCode) 
				.append("weight", this.weight) 
				.append("length", this.length) 
				.append("width", this.width) 
				.append("height", this.height) 
				.append("image", this.image) 
				.append("skuKind", this.skuKind) 
				.append("price", this.price) 
				.append("salePrice", this.salePrice) 
				.append("listPrice", this.listPrice) 
				.append("createTime", this.createTime) 
				.append("updateTime", this.updateTime) 
				.append("version", this.version) 
				.append("status", this.status) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "productSkuId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return productSkuId;
	}

	public ProductStat getProductStat() {
		return productStat;
	}

	public void setProductStat(ProductStat productStat) {
		this.productStat = productStat;
	}

	public Short getOrigStatus() {
		return origStatus;
	}

	public Set<ProductMedia> getProductMedias() {
		return productMedias;
	}

	public void setProductMedias(Set<ProductMedia> productMedias) {
		this.productMedias = productMedias;
	}
}