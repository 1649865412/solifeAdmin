package com.cartmatic.estore.common.model.sales.base;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.cartmatic.estore.common.model.sales.RecommendedProduct;

/**
 * RecommendedProduct Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * When customer is browsing products, system will recommend ot
 */
public class RecommendedProductTbl extends com.cartmatic.estore.core.model.BaseObject implements Serializable {

  
	private static final long	serialVersionUID	= -5576521534033808382L;
	protected Integer recommendedProductId; 
	protected Integer sourceId;   //要关联到的商品id或者目录id
	protected Short status;   //是否激活
	protected Integer sortOrder; //排序值
	protected java.util.Date startTime; //开始时间
	protected java.util.Date expireTime; //结束时间
	protected com.cartmatic.estore.common.model.sales.RecommendedType recommendedType; 
	protected com.cartmatic.estore.common.model.catalog.Product product;
	protected Integer version;
	
    public Integer getSourceId()
    {
        return sourceId;
    }
    public void setSourceId(Integer sourceId)
    {
        this.sourceId = sourceId;
    }
    public java.util.Date getExpireTime()
    {
        return expireTime;
    }
    public void setExpireTime(java.util.Date expireTime)
    {
        this.expireTime = expireTime;
    }
    public java.util.Date getStartTime()
    {
        return startTime;
    }
    public void setStartTime(java.util.Date startTime)
    {
        this.startTime = startTime;
    }
	/**
	 * Default Empty Constructor for class RecommendedProduct
	 */
	public RecommendedProductTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class RecommendedProduct
	 */
	public RecommendedProductTbl (
		 Integer in_recommendedProductId
        ) {
		this.setRecommendedProductId(in_recommendedProductId);
    }

	
	public com.cartmatic.estore.common.model.sales.RecommendedType getRecommendedType () {
		return recommendedType;
	}	
	
	public void setRecommendedType (com.cartmatic.estore.common.model.sales.RecommendedType in_recommendedType) {
		this.recommendedType = in_recommendedType;
	}
	
	public com.cartmatic.estore.common.model.catalog.Product getProduct () {
		return product;
	}	
	
	public void setProduct (com.cartmatic.estore.common.model.catalog.Product in_product) {
		this.product = in_product;
	}
    

	/**
	 * 
	 * @return Integer
     * @hibernate.id column="recommendedProductId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getRecommendedProductId() {
		return this.recommendedProductId;
	}
	
	/**
	 * Set the recommendedProductId
	 */	
	public void setRecommendedProductId(Integer aValue) {
		this.recommendedProductId = aValue;
	}	

	/**
	 * 
	 * @return Integer
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
	    } else if (product == null) {
	        product = new com.cartmatic.estore.common.model.catalog.Product(aValue);
	        product.setVersion(new Integer(0));//set a version to cheat hibernate only
	    } else {
			product.setProductId(aValue);
	    }
	}	

	/**
	 * 
	 * @return Integer
	 */
	public Integer getRecommendedTypeId() {
		return this.getRecommendedType()==null?null:this.getRecommendedType().getRecommendedTypeId();
	}
	
	/**
	 * Set the recommendedTypeId
	 */	
	public void setRecommendedTypeId(Integer aValue) {
	    if (aValue==null) {
	    	recommendedType = null;
	    } else if (recommendedType == null) {
	        recommendedType = new com.cartmatic.estore.common.model.sales.RecommendedType(aValue);
	        recommendedType.setVersion(new Integer(0));//set a version to cheat hibernate only
	    } else {
			recommendedType.setRecommendedTypeId(aValue);
	    }
	}	

	/**
	 * 
	 * @return Short
	 * @hibernate.property column="status" type="java.lang.Short" length="6" not-null="false" unique="false"
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
	 * 
	 * @return Integer
	 * @hibernate.property column="sortOrder" type="java.lang.Integer" length="11" not-null="false" unique="false"
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
	 * 
	 * @return Integer
	 * @hibernate.property column="version" type="java.lang.Integer" length="11" not-null="true" unique="false"
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
		if (!(object instanceof RecommendedProduct)) {
			return false;
		}
		RecommendedProduct rhs = (RecommendedProduct) object;
		return new EqualsBuilder()
				.append(this.recommendedProductId, rhs.recommendedProductId)
				.append(this.status, rhs.status)
				.append(this.sortOrder, rhs.sortOrder)
				.append(this.sourceId, rhs.sourceId) 
				.append(this.version, rhs.version)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.recommendedProductId) 
				.append(this.status) 
				.append(this.sortOrder) 
				.append(this.sourceId) 
				.append(this.version) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("recommendedProductId", this.recommendedProductId) 
				.append("status", this.status) 
				.append("sortOrder", this.sortOrder) 
				.append("sourceId", this.sourceId) 
				.append("version", this.version) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "recommendedProductId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return recommendedProductId;
	}

}