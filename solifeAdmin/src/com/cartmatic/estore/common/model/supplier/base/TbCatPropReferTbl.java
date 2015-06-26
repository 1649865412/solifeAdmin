package com.cartmatic.estore.common.model.supplier.base;

import java.io.Serializable;
import com.cartmatic.estore.Constants;
import com.cartmatic.estore.core.model.BaseObject;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * TbCatPropRefer Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class TbCatPropReferTbl extends BaseObject implements Serializable {

    protected Integer tbCatPropReferId;
	protected Long tbCatPropId;
	protected String tbCatPropName;
	protected com.cartmatic.estore.common.model.catalog.AccessoryGroup accessoryGroup;

	protected java.util.Set tbCatPropValueRefers = new java.util.HashSet();

	/**
	 * Default Empty Constructor for class TbCatPropRefer
	 */
	public TbCatPropReferTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class TbCatPropRefer
	 */
	public TbCatPropReferTbl (
		 Integer in_tbCatPropReferId
        ) {
		this.setTbCatPropReferId(in_tbCatPropReferId);
    }

	
	public com.cartmatic.estore.common.model.catalog.AccessoryGroup getAccessoryGroup () {
		return accessoryGroup;
	}	
	
	public void setAccessoryGroup (com.cartmatic.estore.common.model.catalog.AccessoryGroup in_accessoryGroup) {
		this.accessoryGroup = in_accessoryGroup;
	}

	public java.util.Set getTbCatPropValueRefers () {
		return tbCatPropValueRefers;
	}	
	
	public void setTbCatPropValueRefers (java.util.Set in_tbCatPropValueRefers) {
		this.tbCatPropValueRefers = in_tbCatPropValueRefers;
	}
    

	/**
	 * 	 * @return Integer
     * @hibernate.id column="tbCatPropReferId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getTbCatPropReferId() {
		return this.tbCatPropReferId;
	}
	
	/**
	 * Set the tbCatPropReferId
	 */	
	public void setTbCatPropReferId(Integer aValue) {
		this.tbCatPropReferId = aValue;
	}	

	/**
	 * 	 * @return Integer
	 */
	public Integer getAccessoryGroupId() {
		return this.getAccessoryGroup()==null?null:this.getAccessoryGroup().getAccessoryGroupId();
	}
	
	/**
	 * Set the accessoryGroupId
	 */	
	public void setAccessoryGroupId(Integer aValue) {
	    if (aValue==null) {
	    	accessoryGroup = null;
	    } else {
	        accessoryGroup = new com.cartmatic.estore.common.model.catalog.AccessoryGroup(aValue);
	        accessoryGroup.setVersion(new Integer(0));//set a version to cheat hibernate only
	    }
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="tbCatPropId" type="java.lang.Long" length="19" not-null="true" unique="false"
	 */
	public Long getTbCatPropId() {
		return this.tbCatPropId;
	}
	
	/**
	 * Set the tbCatPropId
	 * @spring.validator type="required"
	 */	
	public void setTbCatPropId(Long aValue) {
		this.tbCatPropId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="tbCatPropName" type="java.lang.String" length="128" not-null="false" unique="false"
	 */
	public String getTbCatPropName() {
		return this.tbCatPropName;
	}
	
	/**
	 * Set the tbCatPropName
	 */	
	public void setTbCatPropName(String aValue) {
		this.tbCatPropName = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof TbCatPropReferTbl)) {
			return false;
		}
		TbCatPropReferTbl rhs = (TbCatPropReferTbl) object;
		return new EqualsBuilder()
				.append(this.tbCatPropReferId, rhs.tbCatPropReferId)
						.append(this.tbCatPropId, rhs.tbCatPropId)
				.append(this.tbCatPropName, rhs.tbCatPropName)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.tbCatPropReferId) 
						.append(this.tbCatPropId) 
				.append(this.tbCatPropName) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("tbCatPropReferId", this.tbCatPropReferId) 
						.append("tbCatPropId", this.tbCatPropId) 
				.append("tbCatPropName", this.tbCatPropName) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "tbCatPropReferId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return tbCatPropReferId;
	}

}