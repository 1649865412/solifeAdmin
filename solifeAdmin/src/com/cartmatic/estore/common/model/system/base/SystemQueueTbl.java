package com.cartmatic.estore.common.model.system.base;

import java.io.Serializable;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.core.model.BaseObject;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * SystemQueue Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class SystemQueueTbl extends BaseObject implements Serializable {

    protected Integer systemQueueId;
	protected String title;
	protected Short queueType;
	protected Short execTimes;
	protected Serializable targetEntiy;
	protected String errorMsg;
	protected Short queueStatus;
	protected java.util.Date createTime;
	protected java.util.Date updateTime;
	protected java.util.Date nextRetryTime;

	/**
	 * Default Empty Constructor for class SystemQueue
	 */
	public SystemQueueTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class SystemQueue
	 */
	public SystemQueueTbl (
		 Integer in_systemQueueId
        ) {
		this.setSystemQueueId(in_systemQueueId);
    }

    

	/**
	 * 	 * @return Integer
     * @hibernate.id column="systemQueueId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getSystemQueueId() {
		return this.systemQueueId;
	}
	
	/**
	 * Set the systemQueueId
	 */	
	public void setSystemQueueId(Integer aValue) {
		this.systemQueueId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="title" type="java.lang.String" length="256" not-null="true" unique="false"
	 */
	public String getTitle() {
		return this.title;
	}
	
	/**
	 * Set the title
	 * @spring.validator type="required"
	 */	
	public void setTitle(String aValue) {
		this.title = aValue;
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="queueType" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getQueueType() {
		return this.queueType;
	}
	
	/**
	 * Set the queueType
	 * @spring.validator type="required"
	 */	
	public void setQueueType(Short aValue) {
		this.queueType = aValue;
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="execTimes" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getExecTimes() {
		return this.execTimes;
	}
	
	/**
	 * Set the execTimes
	 * @spring.validator type="required"
	 */	
	public void setExecTimes(Short aValue) {
		this.execTimes = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="targetEntiy" type="java.lang.String" length="65535" not-null="true" unique="false"
	 */
	public Serializable getTargetEntiy() {
		return this.targetEntiy;
	}
	
	/**
	 * Set the targetEntiy
	 * @spring.validator type="required"
	 */	
	public void setTargetEntiy(Serializable aValue) {
		this.targetEntiy = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="errorMsg" type="java.lang.String" length="1024" not-null="false" unique="false"
	 */
	public String getErrorMsg() {
		return this.errorMsg;
	}
	
	/**
	 * Set the errorMsg
	 */	
	public void setErrorMsg(String aValue) {
		this.errorMsg = aValue;
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="queueStatus" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getQueueStatus() {
		return this.queueStatus;
	}
	
	/**
	 * Set the queueStatus
	 * @spring.validator type="required"
	 */	
	public void setQueueStatus(Short aValue) {
		this.queueStatus = aValue;
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
	 * 	 * @return java.util.Date
	 * @hibernate.property column="updateTime" type="java.util.Date" length="0" not-null="true" unique="false"
	 */
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}
	
	/**
	 * Set the updateTime
	 * @spring.validator type="required"
	 */	
	public void setUpdateTime(java.util.Date aValue) {
		this.updateTime = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SystemQueueTbl)) {
			return false;
		}
		SystemQueueTbl rhs = (SystemQueueTbl) object;
		return new EqualsBuilder()
				.append(this.systemQueueId, rhs.systemQueueId)
				.append(this.title, rhs.title)
				.append(this.queueType, rhs.queueType)
				.append(this.execTimes, rhs.execTimes)
				.append(this.targetEntiy, rhs.targetEntiy)
				.append(this.errorMsg, rhs.errorMsg)
				.append(this.queueStatus, rhs.queueStatus)
				.append(this.createTime, rhs.createTime)
				.append(this.updateTime, rhs.updateTime)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.systemQueueId) 
				.append(this.title) 
				.append(this.queueType) 
				.append(this.execTimes) 
				.append(this.targetEntiy) 
				.append(this.errorMsg) 
				.append(this.queueStatus) 
				.append(this.createTime) 
				.append(this.updateTime) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("systemQueueId", this.systemQueueId) 
				.append("title", this.title) 
				.append("queueType", this.queueType) 
				.append("execTimes", this.execTimes) 
				.append("targetEntiy", this.targetEntiy) 
				.append("errorMsg", this.errorMsg) 
				.append("queueStatus", this.queueStatus) 
				.append("createTime", this.createTime) 
				.append("updateTime", this.updateTime) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "systemQueueId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return systemQueueId;
	}

	public java.util.Date getNextRetryTime() {
		return nextRetryTime;
	}

	public void setNextRetryTime(java.util.Date nextRetryTime) {
		this.nextRetryTime = nextRetryTime;
	}
	

}