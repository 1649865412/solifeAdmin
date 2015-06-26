package com.cartmatic.estore.common.model.order.base;

import java.io.Serializable;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.core.model.BaseObject;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * SalesOrderGeoip Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class SalesOrderGeoipTbl extends BaseObject implements Serializable {

    protected Integer salesOrderGeoipId;
	protected String orderNo;
	protected String customerIp;
	protected String lon;
	protected String lat;
	protected Short actionType;
	protected String city;
	protected String state;
	protected String country;
	protected java.util.Date createTime;


	/**
	 * Default Empty Constructor for class SalesOrderGeoip
	 */
	public SalesOrderGeoipTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class SalesOrderGeoip
	 */
	public SalesOrderGeoipTbl (
		 Integer in_salesOrderGeoipId
        ) {
		this.setSalesOrderGeoipId(in_salesOrderGeoipId);
    }

    

	/**
	 * 	 * @return Integer
     * @hibernate.id column="salesOrderGeoipId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getSalesOrderGeoipId() {
		return this.salesOrderGeoipId;
	}
	
	/**
	 * Set the salesOrderGeoipId
	 */	
	public void setSalesOrderGeoipId(Integer aValue) {
		this.salesOrderGeoipId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="orderNo" type="java.lang.String" length="20" not-null="true" unique="false"
	 */
	public String getOrderNo() {
		return this.orderNo;
	}
	
	/**
	 * Set the orderNo
	 * @spring.validator type="required"
	 */	
	public void setOrderNo(String aValue) {
		this.orderNo = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="customerIp" type="java.lang.String" length="64" not-null="false" unique="false"
	 */
	public String getCustomerIp() {
		return this.customerIp;
	}
	
	/**
	 * Set the customerIp
	 */	
	public void setCustomerIp(String aValue) {
		this.customerIp = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="lon" type="java.lang.String" length="32" not-null="false" unique="false"
	 */
	public String getLon() {
		return this.lon;
	}
	
	/**
	 * Set the lon
	 */	
	public void setLon(String aValue) {
		this.lon = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="lat" type="java.lang.String" length="32" not-null="false" unique="false"
	 */
	public String getLat() {
		return this.lat;
	}
	
	/**
	 * Set the lat
	 */	
	public void setLat(String aValue) {
		this.lat = aValue;
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="actionType" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getActionType() {
		return this.actionType;
	}
	
	/**
	 * Set the actionType
	 * @spring.validator type="required"
	 */	
	public void setActionType(Short aValue) {
		this.actionType = aValue;
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
	
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SalesOrderGeoipTbl)) {
			return false;
		}
		SalesOrderGeoipTbl rhs = (SalesOrderGeoipTbl) object;
		return new EqualsBuilder()
				.append(this.salesOrderGeoipId, rhs.salesOrderGeoipId)
				.append(this.orderNo, rhs.orderNo)
				.append(this.customerIp, rhs.customerIp)
				.append(this.lon, rhs.lon)
				.append(this.lat, rhs.lat)
				.append(this.actionType, rhs.actionType)
				.append(this.createTime, rhs.createTime)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.salesOrderGeoipId) 
				.append(this.orderNo) 
				.append(this.customerIp) 
				.append(this.lon) 
				.append(this.lat) 
				.append(this.actionType) 
				.append(this.createTime) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("salesOrderGeoipId", this.salesOrderGeoipId) 
				.append("orderNo", this.orderNo) 
				.append("customerIp", this.customerIp) 
				.append("lon", this.lon) 
				.append("lat", this.lat) 
				.append("actionType", this.actionType) 
				.append("createTime", this.createTime) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "salesOrderGeoipId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return salesOrderGeoipId;
	}

}