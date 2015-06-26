package com.cartmatic.estore.common.model.catalog.base;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.cartmatic.estore.common.model.system.Store;
import com.cartmatic.estore.core.model.BaseObject;

/**
 * ProductStat Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class ProductStatTbl extends BaseObject implements Serializable {

    protected Integer productStatId;
	protected Integer reviewCount = 0;
	protected java.math.BigDecimal averageRate;
	protected Integer buyCount = 0;
	protected Integer favoriteCount = 0;
	protected Integer version;
	protected com.cartmatic.estore.common.model.catalog.Product product;

	protected Store store;
	
	/**
	 * Default Empty Constructor for class ProductStat
	 */
	public ProductStatTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class ProductStat
	 */
	public ProductStatTbl (
		 Integer in_productStatId
        ) {
		this.setProductStatId(in_productStatId);
    }

	
	public com.cartmatic.estore.common.model.catalog.Product getProduct () {
		return product;
	}	
	
	public void setProduct (com.cartmatic.estore.common.model.catalog.Product in_product) {
		this.product = in_product;
	}
    

	/**
	 * 	 * @return Integer
     * @hibernate.id column="productStatId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getProductStatId() {
		return this.productStatId;
	}
	
	/**
	 * Set the productStatId
	 */	
	public void setProductStatId(Integer aValue) {
		this.productStatId = aValue;
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
	 * 产品总评论数	 * @return Integer
	 * @hibernate.property column="reviewCount" type="java.lang.Integer" length="10" not-null="true" unique="false"
	 */
	public Integer getReviewCount() {
		return this.reviewCount;
	}
	
	/**
	 * Set the reviewCount
	 * @spring.validator type="required"
	 */	
	public void setReviewCount(Integer aValue) {
		this.reviewCount = aValue;
	}	

	/**
	 * 产品评论平均分值	 * @return java.math.BigDecimal
	 * @hibernate.property column="averageRate" type="java.math.BigDecimal" length="12" not-null="true" unique="false"
	 */
	public java.math.BigDecimal getAverageRate() {
		return this.averageRate;
	}
	
	/**
	 * Set the averageRate
	 * @spring.validator type="required"
	 */	
	public void setAverageRate(java.math.BigDecimal aValue) {
		this.averageRate = aValue;
	}	

	/**
	 * 该产品销售数量	 * @return Integer
	 * @hibernate.property column="buyCount" type="java.lang.Integer" length="10" not-null="true" unique="false"
	 */
	public Integer getBuyCount() {
		return this.buyCount;
	}
	
	/**
	 * Set the buyCount
	 * @spring.validator type="required"
	 */	
	public void setBuyCount(Integer aValue) {
		this.buyCount = aValue;
	}	

	
	
	public Integer getFavoriteCount() {
		return favoriteCount;
	}

	public void setFavoriteCount(Integer favoriteCount) {
		this.favoriteCount = favoriteCount;
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
		if (!(object instanceof ProductStatTbl)) {
			return false;
		}
		ProductStatTbl rhs = (ProductStatTbl) object;
		return new EqualsBuilder()
				.append(this.productStatId, rhs.productStatId)
						.append(this.reviewCount, rhs.reviewCount)
				.append(this.averageRate, rhs.averageRate)
				.append(this.buyCount, rhs.buyCount)
				.append(this.version, rhs.version)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.productStatId) 
						.append(this.reviewCount) 
				.append(this.averageRate) 
				.append(this.buyCount) 
				.append(this.version) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("productStatId", this.productStatId) 
						.append("reviewCount", this.reviewCount) 
				.append("averageRate", this.averageRate) 
				.append("buyCount", this.buyCount) 
				.append("version", this.version) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "productStatId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return productStatId;
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
}