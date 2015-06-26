package com.cartmatic.estore.common.model.sales.base;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.cartmatic.estore.common.model.sales.AlsoBuy;

public class AlsoBuyTbl extends com.cartmatic.estore.core.model.BaseObject implements Serializable {

	private static final long	serialVersionUID	= -3990285656563018852L;
	protected Integer alsoBuyId;
	protected Integer productId;
	protected Integer times;
	protected Integer version;
	protected java.util.Date	createTime;
	protected java.util.Date	updateTime;

	protected com.cartmatic.estore.common.model.catalog.Product alsoProduct;


	/**
	 * Default Empty Constructor for class AlsoBuy
	 */
	public AlsoBuyTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class AlsoBuy
	 */
	public AlsoBuyTbl (
		 Integer in_alsoBuyId
        ) {
		this.setAlsoBuyId(in_alsoBuyId);
    }

	
	

	public com.cartmatic.estore.common.model.catalog.Product getAlsoProduct() {
		return alsoProduct;
	}

	public void setAlsoProduct(
			com.cartmatic.estore.common.model.catalog.Product alsoProduct) {
		this.alsoProduct = alsoProduct;
	}

	/**
	 * 
	 * @return Integer
     * @hibernate.id column="alsoBuyId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getAlsoBuyId() {
		return this.alsoBuyId;
	}
	
	/**
	 * Set the alsoBuyId
	 */	
	public void setAlsoBuyId(Integer aValue) {
		this.alsoBuyId = aValue;
	}	

	/**
	 * 
	 * @return Integer
	 * @hibernate.property column="productId" type="java.lang.Integer" length="11" not-null="true" unique="false"
	 */
	public Integer getProductId() {
		return this.productId;
	}
	
	/**
	 * Set the productId
	 * @spring.validator type="required"
	 */	
	public void setProductId(Integer aValue) {
		this.productId = aValue;
	}	

	/**
	 * 
	 * @return Integer
	 */
	public Integer getAlsoProductId() {
		return this.getAlsoProduct()==null?null:this.getAlsoProduct().getProductId();
	}
	
	/**
	 * Set the alsoProductId
	 */	
	public void setAlsoProductId(Integer aValue) {
	    if (aValue==null) {
	    	alsoProduct = null;
	    } else if (alsoProduct == null) {
	    	alsoProduct = new com.cartmatic.estore.common.model.catalog.Product(aValue);
	    	alsoProduct.setVersion(new Integer(0));//set a version to cheat hibernate only
	    } else {
	    	alsoProduct.setProductId(aValue);
	    }
	}	

	/**
	 * how many times this product has been purchased
	 * @return Integer
	 * @hibernate.property column="times" type="java.lang.Integer" length="11" not-null="false" unique="false"
	 */
	public Integer getTimes() {
		return this.times;
	}
	
	/**
	 * Set the times
	 */	
	public void setTimes(Integer aValue) {
		this.times = aValue;
	}	
	
	/**
	 * *
	 * 
	 * @return java.util.Date
	 * @hibernate.property column="createTime" type="java.util.Date" length="0"
	 *                     not-null="true" unique="false"
	 */
	public java.util.Date getCreateTime() {
		return this.createTime;
	}

	/**
	 * Set the createTime
	 * 
	 * @spring.validator type="required"
	 */
	public void setCreateTime(java.util.Date aValue) {
		this.createTime = aValue;
	}

	/**
	 * *
	 * 
	 * @return java.util.Date
	 * @hibernate.property column="updateTime" type="java.util.Date" length="0"
	 *                     not-null="true" unique="false"
	 */
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}

	/**
	 * Set the updateTime
	 * 
	 * @spring.validator type="required"
	 */
	public void setUpdateTime(java.util.Date aValue) {
		this.updateTime = aValue;
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
		if (!(object instanceof AlsoBuy)) {
			return false;
		}
		AlsoBuy rhs = (AlsoBuy) object;
		return new EqualsBuilder()
				.append(this.alsoBuyId, rhs.alsoBuyId)
				.append(this.productId, rhs.productId)
						.append(this.times, rhs.times)
				.append(this.version, rhs.version)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.alsoBuyId) 
				.append(this.productId) 
						.append(this.times) 
				.append(this.version) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("alsoBuyId", this.alsoBuyId) 
				.append("productId", this.productId) 
						.append("times", this.times) 
				.append("version", this.version) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "alsoBuyId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return alsoBuyId;
	}

}