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
public class ProductHandDrawTbl extends BaseObject implements Serializable {

	protected Integer productHandDrawId;
	protected String mediaUrl;

	/**
	 * Default Empty Constructor for class ProductMedia
	 */
	public ProductHandDrawTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class ProductMedia
	 */
	public ProductHandDrawTbl (
		 Integer in_productMediaId
        ) {
		this.setProductHandDrawId(in_productMediaId);
    }


	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof ProductHandDrawTbl)) {
			return false;
		}
		ProductHandDrawTbl rhs = (ProductHandDrawTbl) object;
		return new EqualsBuilder()
				.append(this.productHandDrawId, rhs.productHandDrawId)
				.append(this.mediaUrl, rhs.mediaUrl)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.productHandDrawId) 
				.append(this.mediaUrl) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("productHandDrawId", this.productHandDrawId) 
				.append("mediaUrl", this.mediaUrl) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "productHandDrawId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return productHandDrawId;
	}

	public Integer getProductHandDrawId() {
		return productHandDrawId;
	}

	public void setProductHandDrawId(Integer productHandDrawId) {
		this.productHandDrawId = productHandDrawId;
	}

	public String getMediaUrl() {
		return mediaUrl;
	}

	public void setMediaUrl(String mediaUrl) {
		this.mediaUrl = mediaUrl;
	}
}