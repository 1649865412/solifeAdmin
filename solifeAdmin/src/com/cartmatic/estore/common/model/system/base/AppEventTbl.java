package com.cartmatic.estore.common.model.system.base;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * AppEvent Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class AppEventTbl extends com.cartmatic.estore.core.model.BaseObject implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -6194966052298039412L;
	
	private Integer appEventId;
	private Serializable appEvent;
	private Date updateTime;


	/**
	 * Default Empty Constructor for class AppEvent
	 */
	public AppEventTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class AppEvent
	 */
	public AppEventTbl (
		 Integer in_appEventId
        ) {
		this.setAppEventId(in_appEventId);
    }

    

	/**
	 * 
	 * @return Integer
     * @hibernate.id column="appEventId" type="java.lang.Integer" generator-class="$prop.generatorClass"
	 */
	public Integer getAppEventId() {
		return this.appEventId;
	}
	
	/**
	 * Set the appEventId
	 */	
	public void setAppEventId(Integer aValue) {
		this.appEventId = aValue;
	}	

	/**
	 * 
	 * @return Serializable
	 * @hibernate.property column="appEvent" type="java.lang.String" length="65535" not-null="true" unique="false"
	 */
	public Serializable getAppEvent() {
		return this.appEvent;
	}
	
	/**
	 * Set the appEvent
	 * @spring.validator type="required"
	 */	
	public void setAppEvent(Serializable aValue) {
		this.appEvent = aValue;
	}	

	/**
	 * 
	 * @return java.util.Date
	 * @hibernate.property column="updateTime" type="java.lang.String" length="45" not-null="true" unique="false"
	 */
	public Date getUpdateTime() {
		return this.updateTime;
	}
	
	/**
	 * Set the updateTime
	 * @spring.validator type="required"
	 */	
	public void setUpdateTime(Date aValue) {
		this.updateTime = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof AppEventTbl)) {
			return false;
		}
		AppEventTbl rhs = (AppEventTbl) object;
		return new EqualsBuilder()
				.append(this.appEventId, rhs.appEventId)
				.append(this.appEvent, rhs.appEvent)
				.append(this.updateTime, rhs.updateTime)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.appEventId) 
				.append(this.appEvent) 
				.append(this.updateTime) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("appEventId", this.appEventId) 
				.append("appEvent", this.appEvent) 
				.append("updateTime", this.updateTime) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "appEventId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return appEventId;
	}

}