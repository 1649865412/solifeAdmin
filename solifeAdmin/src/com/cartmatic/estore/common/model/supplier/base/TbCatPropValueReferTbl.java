package com.cartmatic.estore.common.model.supplier.base;

import java.io.Serializable;
import com.cartmatic.estore.Constants;
import com.cartmatic.estore.core.model.BaseObject;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * TbCatPropValueRefer Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class TbCatPropValueReferTbl extends BaseObject implements Serializable {

    protected Integer tbCatPropValueReferId;
	protected Long tbCatPropId;
	protected Long tbCatPropValueId;
	protected String tbCatPropValueName;
	protected com.cartmatic.estore.common.model.supplier.TbCatPropRefer tbCatPropRefer;
	protected com.cartmatic.estore.common.model.catalog.Accessory accessory;


	/**
	 * Default Empty Constructor for class TbCatPropValueRefer
	 */
	public TbCatPropValueReferTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class TbCatPropValueRefer
	 */
	public TbCatPropValueReferTbl (
		 Integer in_tbCatPropValueReferId
        ) {
		this.setTbCatPropValueReferId(in_tbCatPropValueReferId);
    }

	
	public com.cartmatic.estore.common.model.supplier.TbCatPropRefer getTbCatPropRefer () {
		return tbCatPropRefer;
	}	
	
	public void setTbCatPropRefer (com.cartmatic.estore.common.model.supplier.TbCatPropRefer in_tbCatPropRefer) {
		this.tbCatPropRefer = in_tbCatPropRefer;
	}
	
	public com.cartmatic.estore.common.model.catalog.Accessory getAccessory () {
		return accessory;
	}	
	
	public void setAccessory (com.cartmatic.estore.common.model.catalog.Accessory in_accessory) {
		this.accessory = in_accessory;
	}
    

	/**
	 * 	 * @return Integer
     * @hibernate.id column="tbCatPropValueReferId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getTbCatPropValueReferId() {
		return this.tbCatPropValueReferId;
	}
	
	/**
	 * Set the tbCatPropValueReferId
	 */	
	public void setTbCatPropValueReferId(Integer aValue) {
		this.tbCatPropValueReferId = aValue;
	}	

	/**
	 * 	 * @return Integer
	 */
	public Integer getAccessoryId() {
		return this.getAccessory()==null?null:this.getAccessory().getAccessoryId();
	}
	
	/**
	 * Set the accessoryId
	 */	
	public void setAccessoryId(Integer aValue) {
	    if (aValue==null) {
	    	accessory = null;
	    } else {
	        accessory = new com.cartmatic.estore.common.model.catalog.Accessory(aValue);
	        accessory.setVersion(new Integer(0));//set a version to cheat hibernate only
	    }
	}	

	/**
	 * 	 * @return Integer
	 */
	public Integer getTbCatPropReferId() {
		return this.getTbCatPropRefer()==null?null:this.getTbCatPropRefer().getTbCatPropReferId();
	}
	
	/**
	 * Set the tbCatPropReferId
	 */	
	public void setTbCatPropReferId(Integer aValue) {
	    if (aValue==null) {
	    	tbCatPropRefer = null;
	    } else {
	        tbCatPropRefer = new com.cartmatic.estore.common.model.supplier.TbCatPropRefer(aValue);
	        tbCatPropRefer.setVersion(new Integer(0));//set a version to cheat hibernate only
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
	 * 	 * @return Long
	 * @hibernate.property column="tbCatPropValueId" type="java.lang.Long" length="19" not-null="true" unique="false"
	 */
	public Long getTbCatPropValueId() {
		return this.tbCatPropValueId;
	}
	
	/**
	 * Set the tbCatPropValueId
	 * @spring.validator type="required"
	 */	
	public void setTbCatPropValueId(Long aValue) {
		this.tbCatPropValueId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="tbCatPropValueName" type="java.lang.String" length="256" not-null="false" unique="false"
	 */
	public String getTbCatPropValueName() {
		return this.tbCatPropValueName;
	}
	
	/**
	 * Set the tbCatPropValueName
	 */	
	public void setTbCatPropValueName(String aValue) {
		this.tbCatPropValueName = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof TbCatPropValueReferTbl)) {
			return false;
		}
		TbCatPropValueReferTbl rhs = (TbCatPropValueReferTbl) object;
		return new EqualsBuilder()
				.append(this.tbCatPropValueReferId, rhs.tbCatPropValueReferId)
								.append(this.tbCatPropId, rhs.tbCatPropId)
				.append(this.tbCatPropValueId, rhs.tbCatPropValueId)
				.append(this.tbCatPropValueName, rhs.tbCatPropValueName)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.tbCatPropValueReferId) 
								.append(this.tbCatPropId) 
				.append(this.tbCatPropValueId) 
				.append(this.tbCatPropValueName) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("tbCatPropValueReferId", this.tbCatPropValueReferId) 
								.append("tbCatPropId", this.tbCatPropId) 
				.append("tbCatPropValueId", this.tbCatPropValueId) 
				.append("tbCatPropValueName", this.tbCatPropValueName) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "tbCatPropValueReferId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return tbCatPropValueReferId;
	}

}