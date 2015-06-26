package com.cartmatic.estore.common.model.catalog.base;

import java.io.Serializable;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.core.model.BaseObject;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * AccessoryGroup Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class AccessoryGroupTbl extends BaseObject implements Serializable {

    protected Integer accessoryGroupId;
	protected String groupName;
	protected String groupCode;
	protected String groupDesc;

	protected java.util.Set accessorys = new java.util.HashSet();

	/**
	 * Default Empty Constructor for class AccessoryGroup
	 */
	public AccessoryGroupTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class AccessoryGroup
	 */
	public AccessoryGroupTbl (
		 Integer in_accessoryGroupId
        ) {
		this.setAccessoryGroupId(in_accessoryGroupId);
    }


	public java.util.Set getAccessorys () {
		return accessorys;
	}	
	
	public void setAccessorys (java.util.Set in_accessorys) {
		this.accessorys = in_accessorys;
	}
    

	/**
	 * 	 * @return Integer
     * @hibernate.id column="accessoryGroupId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getAccessoryGroupId() {
		return this.accessoryGroupId;
	}
	
	/**
	 * Set the accessoryGroupId
	 */	
	public void setAccessoryGroupId(Integer aValue) {
		this.accessoryGroupId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="groupName" type="java.lang.String" length="32" not-null="true" unique="false"
	 */
	public String getGroupName() {
		return this.groupName;
	}
	
	/**
	 * Set the groupName
	 * @spring.validator type="required"
	 */	
	public void setGroupName(String aValue) {
		this.groupName = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="groupDesc" type="java.lang.String" length="64" not-null="false" unique="false"
	 */
	public String getGroupDesc() {
		return this.groupDesc;
	}
	
	/**
	 * Set the groupDesc
	 */	
	public void setGroupDesc(String aValue) {
		this.groupDesc = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof AccessoryGroupTbl)) {
			return false;
		}
		AccessoryGroupTbl rhs = (AccessoryGroupTbl) object;
		return new EqualsBuilder()
				.append(this.accessoryGroupId, rhs.accessoryGroupId)
				.append(this.groupName, rhs.groupName)
				.append(this.groupCode, rhs.groupCode)
				.append(this.groupDesc, rhs.groupDesc)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.accessoryGroupId) 
				.append(this.groupName) 
				.append(this.groupCode) 
				.append(this.groupDesc) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("accessoryGroupId", this.accessoryGroupId) 
				.append("groupName", this.groupName) 
				.append("groupCode", this.groupCode) 
				.append("groupDesc", this.groupDesc) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "accessoryGroupId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return accessoryGroupId;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
}