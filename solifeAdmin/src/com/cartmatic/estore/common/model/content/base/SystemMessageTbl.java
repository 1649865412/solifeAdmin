package com.cartmatic.estore.common.model.content.base;

import java.io.Serializable;
import com.cartmatic.estore.Constants;
import com.cartmatic.estore.core.model.BaseObject;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * SystemMessage Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class SystemMessageTbl extends BaseObject implements Serializable {

    protected Integer systemMessageId;
	protected String messageHtml;
	protected Short messageType;
	protected Short status;
	protected java.util.Date createTime;
	protected com.cartmatic.estore.common.model.system.AppUser appUser;


	/**
	 * Default Empty Constructor for class SystemMessage
	 */
	public SystemMessageTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class SystemMessage
	 */
	public SystemMessageTbl (
		 Integer in_systemMessageId
        ) {
		this.setSystemMessageId(in_systemMessageId);
    }

	
	public com.cartmatic.estore.common.model.system.AppUser getAppUser () {
		return appUser;
	}	
	
	public void setAppUser (com.cartmatic.estore.common.model.system.AppUser in_appUser) {
		this.appUser = in_appUser;
	}
    

	/**
	 * 	 * @return Integer
     * @hibernate.id column="systemMessageId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getSystemMessageId() {
		return this.systemMessageId;
	}
	
	/**
	 * Set the systemMessageId
	 */	
	public void setSystemMessageId(Integer aValue) {
		this.systemMessageId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="messageHtml" type="java.lang.String" length="512" not-null="true" unique="false"
	 */
	public String getMessageHtml() {
		return this.messageHtml;
	}
	
	/**
	 * Set the messageHtml
	 * @spring.validator type="required"
	 */	
	public void setMessageHtml(String aValue) {
		this.messageHtml = aValue;
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="messageType" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
	public Short getMessageType() {
		return this.messageType;
	}
	
	/**
	 * Set the messageType
	 */	
	public void setMessageType(Short aValue) {
		this.messageType = aValue;
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="status" type="java.lang.Short" length="5" not-null="false" unique="false"
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
	 * 	 * @return Integer
	 */
	public Integer getAppuserId() {
		return this.getAppUser()==null?null:this.getAppUser().getAppuserId();
	}
	
	/**
	 * Set the appuserId
	 */	
	public void setAppuserId(Integer aValue) {
	    if (aValue==null) {
	    	appUser = null;
	    } else {
	        appUser = new com.cartmatic.estore.common.model.system.AppUser(aValue);
	        appUser.setVersion(new Integer(0));//set a version to cheat hibernate only
	    }
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="createTime" type="java.util.Date" length="0" not-null="true" unique="false"
	 */
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	
	/**
	 * Set the createTime
	 * @spring.validator type="required"
	 */	
	public void setCreateTime(java.util.Date aValue) {
		this.createTime = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SystemMessageTbl)) {
			return false;
		}
		SystemMessageTbl rhs = (SystemMessageTbl) object;
		return new EqualsBuilder()
				.append(this.systemMessageId, rhs.systemMessageId)
				.append(this.messageHtml, rhs.messageHtml)
				.append(this.messageType, rhs.messageType)
				.append(this.status, rhs.status)
						.append(this.createTime, rhs.createTime)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.systemMessageId) 
				.append(this.messageHtml) 
				.append(this.messageType) 
				.append(this.status) 
						.append(this.createTime) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("systemMessageId", this.systemMessageId) 
				.append("messageHtml", this.messageHtml) 
				.append("messageType", this.messageType) 
				.append("status", this.status) 
						.append("createTime", this.createTime) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "systemMessageId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return systemMessageId;
	}

}