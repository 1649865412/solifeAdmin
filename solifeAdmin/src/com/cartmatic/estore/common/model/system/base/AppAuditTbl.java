package com.cartmatic.estore.common.model.system.base;

import java.io.Serializable;
import com.cartmatic.estore.Constants;
import com.cartmatic.estore.core.model.BaseObject;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * AppAudit Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class AppAuditTbl extends BaseObject implements Serializable {

    protected Integer appAuditId;
	protected Integer procUserId;
	protected String procObj;
	protected String actionName;
	protected String procResult;
	protected String requestUrl;
	protected java.util.Date procTime;


	/**
	 * Default Empty Constructor for class AppAudit
	 */
	public AppAuditTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class AppAudit
	 */
	public AppAuditTbl (
		 Integer in_appAuditId
        ) {
		this.setAppAuditId(in_appAuditId);
    }

    

	/**
	 * 	 * @return Integer
     * @hibernate.id column="appAuditId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getAppAuditId() {
		return this.appAuditId;
	}
	
	/**
	 * Set the appAuditId
	 */	
	public void setAppAuditId(Integer aValue) {
		this.appAuditId = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="procUserId" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getProcUserId() {
		return this.procUserId;
	}
	
	/**
	 * Set the procUserId
	 */	
	public void setProcUserId(Integer aValue) {
		this.procUserId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="procObj" type="java.lang.String" length="1024" not-null="false" unique="false"
	 */
	public String getProcObj() {
		return this.procObj;
	}
	
	/**
	 * Set the procObj
	 */	
	public void setProcObj(String aValue) {
		this.procObj = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="actionName" type="java.lang.String" length="64" not-null="true" unique="false"
	 */
	public String getActionName() {
		return this.actionName;
	}
	
	/**
	 * Set the actionName
	 * @spring.validator type="required"
	 */	
	public void setActionName(String aValue) {
		this.actionName = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="procResult" type="java.lang.String" length="1024" not-null="false" unique="false"
	 */
	public String getProcResult() {
		return this.procResult;
	}
	
	/**
	 * Set the procResult
	 */	
	public void setProcResult(String aValue) {
		this.procResult = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="requestUrl" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getRequestUrl() {
		return this.requestUrl;
	}
	
	/**
	 * Set the requestUrl
	 */	
	public void setRequestUrl(String aValue) {
		this.requestUrl = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="procTime" type="java.util.Date" length="0" not-null="true" unique="false"
	 */
	public java.util.Date getProcTime() {
		return this.procTime;
	}
	
	/**
	 * Set the procTime
	 * @spring.validator type="required"
	 */	
	public void setProcTime(java.util.Date aValue) {
		this.procTime = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof AppAuditTbl)) {
			return false;
		}
		AppAuditTbl rhs = (AppAuditTbl) object;
		return new EqualsBuilder()
				.append(this.appAuditId, rhs.appAuditId)
				.append(this.procUserId, rhs.procUserId)
				.append(this.procObj, rhs.procObj)
				.append(this.actionName, rhs.actionName)
				.append(this.procResult, rhs.procResult)
				.append(this.requestUrl, rhs.requestUrl)
				.append(this.procTime, rhs.procTime)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.appAuditId) 
				.append(this.procUserId) 
				.append(this.procObj) 
				.append(this.actionName) 
				.append(this.procResult) 
				.append(this.requestUrl) 
				.append(this.procTime) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("appAuditId", this.appAuditId) 
				.append("procUserId", this.procUserId) 
				.append("procObj", this.procObj) 
				.append("actionName", this.actionName) 
				.append("procResult", this.procResult) 
				.append("requestUrl", this.requestUrl) 
				.append("procTime", this.procTime) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "appAuditId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return appAuditId;
	}

}