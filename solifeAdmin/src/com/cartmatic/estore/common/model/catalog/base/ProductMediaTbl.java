package com.cartmatic.estore.common.model.catalog.base;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.cartmatic.estore.common.model.catalog.ProductSku;
import com.cartmatic.estore.core.model.BaseObject;

/**
 * ProductMedia Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class ProductMediaTbl extends BaseObject implements Serializable {

    protected Integer productMediaId;
	protected Short mediaType;
	protected String mediaUrl;
	protected String mediaUrlLarge;
	protected String mediaDescription;
	protected Integer sortOrder;
	protected com.cartmatic.estore.common.model.catalog.Product product;
	
	protected ProductSku productSku;


	/**
	 * Default Empty Constructor for class ProductMedia
	 */
	public ProductMediaTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class ProductMedia
	 */
	public ProductMediaTbl (
		 Integer in_productMediaId
        ) {
		this.setProductMediaId(in_productMediaId);
    }

	
	public com.cartmatic.estore.common.model.catalog.Product getProduct () {
		return product;
	}	
	
	public void setProduct (com.cartmatic.estore.common.model.catalog.Product in_product) {
		this.product = in_product;
	}
    

	/**
	 * 	 * @return Integer
     * @hibernate.id column="productMediaId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getProductMediaId() {
		return this.productMediaId;
	}
	
	/**
	 * Set the productMediaId
	 */	
	public void setProductMediaId(Integer aValue) {
		this.productMediaId = aValue;
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
	    }else {
	    	product = new com.cartmatic.estore.common.model.catalog.Product(aValue);
	        product.setVersion(new Integer(0));//set a version to cheat hibernate only
	    }
	}	
	
	/**
	 * set the productMedia productSkuId
	 * @param id
	 */
	public void setProductSkuId(Integer id){
		if(id == null){
			productSku = null;
		}else{
			productSku = new ProductSku(id);
			productSku.setVersion(0);
		}
	}

	/**
	 * 产品媒体类型，产品更多图片、产品多媒体、产品附件；	 * @return Short
	 * @hibernate.property column="mediaType" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getMediaType() {
		return this.mediaType;
	}
	
	/**
	 * Set the mediaType
	 * @spring.validator type="required"
	 */	
	public void setMediaType(Short aValue) {
		this.mediaType = aValue;
	}	

	/**
	 * 媒体路径	 * @return String
	 * @hibernate.property column="mediaUrl" type="java.lang.String" length="255" not-null="true" unique="false"
	 */
	public String getMediaUrl() {
		return this.mediaUrl;
	}
	
	/**
	 * Set the mediaUrl
	 * @spring.validator type="required"
	 */	
	public void setMediaUrl(String aValue) {
		this.mediaUrl = aValue;
	}	

	/**
	 * 产品媒体描述	 * @return String
	 * @hibernate.property column="mediaDescription" type="java.lang.String" length="256" not-null="false" unique="false"
	 */
	public String getMediaDescription() {
		return this.mediaDescription;
	}
	
	/**
	 * Set the mediaDescription
	 */	
	public void setMediaDescription(String aValue) {
		this.mediaDescription = aValue;
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
		if (!(object instanceof ProductMediaTbl)) {
			return false;
		}
		ProductMediaTbl rhs = (ProductMediaTbl) object;
		return new EqualsBuilder()
				.append(this.productMediaId, rhs.productMediaId)
						.append(this.mediaType, rhs.mediaType)
				.append(this.mediaUrl, rhs.mediaUrl)
				.append(this.mediaUrlLarge, rhs.mediaUrlLarge)
				.append(this.mediaDescription, rhs.mediaDescription)
				.append(this.sortOrder, rhs.sortOrder)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.productMediaId) 
						.append(this.mediaType) 
				.append(this.mediaUrl) 
				.append(this.mediaUrlLarge)
				.append(this.mediaDescription) 
				.append(this.sortOrder) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("productMediaId", this.productMediaId) 
						.append("mediaType", this.mediaType) 
				.append("mediaUrl", this.mediaUrl) 
				.append("mediaUrlLarge",this.mediaUrlLarge)
				.append("mediaDescription", this.mediaDescription) 
				.append("sortOrder", this.sortOrder) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "productMediaId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return productMediaId;
	}

	public String getMediaUrlLarge() {
		return mediaUrlLarge;
	}

	public void setMediaUrlLarge(String mediaUrlLarge) {
		this.mediaUrlLarge = mediaUrlLarge;
	}

	public ProductSku getProductSku() {
		return productSku;
	}

	public void setProductSku(ProductSku productSku) {
		this.productSku = productSku;
	}

}