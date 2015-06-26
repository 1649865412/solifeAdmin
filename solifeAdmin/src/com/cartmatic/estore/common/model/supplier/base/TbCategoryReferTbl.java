package com.cartmatic.estore.common.model.supplier.base;

import java.io.Serializable;
import com.cartmatic.estore.Constants;
import com.cartmatic.estore.core.model.BaseObject;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * TbCategoryRefer Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class TbCategoryReferTbl extends BaseObject implements Serializable {

    protected Integer tbCategoryReferId;
	protected Long tbCategoryId;
	protected String tbCategoryName;
	protected com.cartmatic.estore.common.model.catalog.Category category;


	/**
	 * Default Empty Constructor for class TbCategoryRefer
	 */
	public TbCategoryReferTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class TbCategoryRefer
	 */
	public TbCategoryReferTbl (
		 Integer in_tbCategoryReferId
        ) {
		this.setTbCategoryReferId(in_tbCategoryReferId);
    }

	
	public com.cartmatic.estore.common.model.catalog.Category getCategory () {
		return category;
	}	
	
	public void setCategory (com.cartmatic.estore.common.model.catalog.Category in_category) {
		this.category = in_category;
	}
    

	/**
	 * 	 * @return Integer
     * @hibernate.id column="tbCategoryReferId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getTbCategoryReferId() {
		return this.tbCategoryReferId;
	}
	
	/**
	 * Set the tbCategoryReferId
	 */	
	public void setTbCategoryReferId(Integer aValue) {
		this.tbCategoryReferId = aValue;
	}	

	/**
	 * 	 * @return Integer
	 */
	public Integer getCategoryId() {
		return this.getCategory()==null?null:this.getCategory().getCategoryId();
	}
	
	/**
	 * Set the categoryId
	 */	
	public void setCategoryId(Integer aValue) {
	    if (aValue==null) {
	    	category = null;
	    } else {
	        category = new com.cartmatic.estore.common.model.catalog.Category(aValue);
	        category.setVersion(new Integer(0));//set a version to cheat hibernate only
	    }
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="tbCategoryId" type="java.lang.Long" length="19" not-null="true" unique="false"
	 */
	public Long getTbCategoryId() {
		return this.tbCategoryId;
	}
	
	/**
	 * Set the tbCategoryId
	 * @spring.validator type="required"
	 */	
	public void setTbCategoryId(Long aValue) {
		this.tbCategoryId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="tbCategoryName" type="java.lang.String" length="256" not-null="false" unique="false"
	 */
	public String getTbCategoryName() {
		return this.tbCategoryName;
	}
	
	/**
	 * Set the tbCategoryName
	 */	
	public void setTbCategoryName(String aValue) {
		this.tbCategoryName = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof TbCategoryReferTbl)) {
			return false;
		}
		TbCategoryReferTbl rhs = (TbCategoryReferTbl) object;
		return new EqualsBuilder()
				.append(this.tbCategoryReferId, rhs.tbCategoryReferId)
						.append(this.tbCategoryId, rhs.tbCategoryId)
				.append(this.tbCategoryName, rhs.tbCategoryName)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.tbCategoryReferId) 
						.append(this.tbCategoryId) 
				.append(this.tbCategoryName) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("tbCategoryReferId", this.tbCategoryReferId) 
						.append("tbCategoryId", this.tbCategoryId) 
				.append("tbCategoryName", this.tbCategoryName) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "tbCategoryReferId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return tbCategoryReferId;
	}

}