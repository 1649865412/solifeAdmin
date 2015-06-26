package com.cartmatic.estore.common.model.catalog.base;

import java.io.Serializable;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.core.model.BaseObject;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * ProductMedia Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class ProductMediaUpTbl extends BaseObject implements Serializable {

    protected Integer productMediaUpId;
	protected Short mediaType;
	protected String mediaUrl;
	protected String mediaDescription;
	protected Integer sortOrder;
	protected com.cartmatic.estore.common.model.catalog.Product product;


	/**
	 * Default Empty Constructor for class ProductMedia
	 */
	public ProductMediaUpTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class ProductMedia
	 */
	public ProductMediaUpTbl (
		 Integer in_productMediaId
        ) {
		this.setProductMediaUpId(in_productMediaId);
    }

	
	public com.cartmatic.estore.common.model.catalog.Product getProduct () {
		return product;
	}	
	
	public void setProduct (com.cartmatic.estore.common.model.catalog.Product in_product) {
		this.product = in_product;
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

	public Integer getProductMediaUpId() {
		return productMediaUpId;
	}

	public void setProductMediaUpId(Integer productMediaUpId) {
		this.productMediaUpId = productMediaUpId;
	}

	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		if (!(o instanceof ProductMediaTbl)) {
			return false;
		}
		ProductMediaUpTbl rhs = (ProductMediaUpTbl) o;
		return new EqualsBuilder()
				.append(this.productMediaUpId, rhs.productMediaUpId)
						.append(this.mediaType, rhs.mediaType)
				.append(this.mediaUrl, rhs.mediaUrl)
				.append(this.mediaDescription, rhs.mediaDescription)
				.append(this.sortOrder, rhs.sortOrder)
				.isEquals();
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.productMediaUpId) 
				.append(this.mediaType) 
		.append(this.mediaUrl) 
		.append(this.mediaDescription) 
		.append(this.sortOrder) 
		.toHashCode();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return new ToStringBuilder(this)
		.append("productMediaUpId", this.productMediaUpId) 
				.append("mediaType", this.mediaType) 
		.append("mediaUrl", this.mediaUrl) 
		.append("mediaDescription", this.mediaDescription) 
		.append("sortOrder", this.sortOrder) 
		.toString();
	}	
	
	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "productMediaUpId";
	}
	

}