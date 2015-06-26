package com.cartmatic.estore.common.model.system.base;

import java.io.Serializable;
import java.util.HashMap;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.common.model.system.Store;
import com.cartmatic.estore.core.model.BaseObject;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * PaymentMethod Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class PaymentMethodTbl extends BaseObject implements Serializable {

    protected Integer paymentMethodId;
	protected String paymentMethodName;
	protected String paymentMethodCode;
	protected String paymentMethodDetail;
	protected Short paymentMethodType;
	protected String paymentMethodIcon;
	protected String protocol;
	protected String processorFile;
	protected String configFile;
	protected HashMap<String, String> configData;
	protected String testModel;
	protected Short isCod;
	protected Integer sortOrder;
	protected Short status;
	protected Integer version;
	protected java.util.Set<Store> stores = new java.util.HashSet<Store>();

	/**
	 * Default Empty Constructor for class PaymentMethod
	 */
	public PaymentMethodTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class PaymentMethod
	 */
	public PaymentMethodTbl (
		 Integer in_paymentMethodId
        ) {
		this.setPaymentMethodId(in_paymentMethodId);
    }

    

	/**
	 * 	 * @return Integer
     * @hibernate.id column="paymentMethodId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getPaymentMethodId() {
		return this.paymentMethodId;
	}
	
	/**
	 * Set the paymentMethodId
	 */	
	public void setPaymentMethodId(Integer aValue) {
		this.paymentMethodId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="paymentMethodName" type="java.lang.String" length="64" not-null="true" unique="false"
	 */
	public String getPaymentMethodName() {
		return this.paymentMethodName;
	}
	
	/**
	 * Set the paymentMethodName
	 * @spring.validator type="required"
	 */	
	public void setPaymentMethodName(String aValue) {
		this.paymentMethodName = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="paymentMethodCode" type="java.lang.String" length="32" not-null="true" unique="false"
	 */
	public String getPaymentMethodCode() {
		return this.paymentMethodCode;
	}
	
	/**
	 * Set the paymentMethodCode
	 * @spring.validator type="required"
	 */	
	public void setPaymentMethodCode(String aValue) {
		this.paymentMethodCode = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="paymentMethodDetail" type="java.lang.String" length="2048" not-null="false" unique="false"
	 */
	public String getPaymentMethodDetail() {
		return this.paymentMethodDetail;
	}
	
	/**
	 * Set the paymentMethodDetail
	 */	
	public void setPaymentMethodDetail(String aValue) {
		this.paymentMethodDetail = aValue;
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="paymentMethodType" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
	public Short getPaymentMethodType() {
		return this.paymentMethodType;
	}
	
	/**
	 * Set the paymentMethodType
	 */	
	public void setPaymentMethodType(Short aValue) {
		this.paymentMethodType = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="paymentMethodIcon" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getPaymentMethodIcon() {
		return this.paymentMethodIcon;
	}
	
	/**
	 * Set the paymentMethodIcon
	 */	
	public void setPaymentMethodIcon(String aValue) {
		this.paymentMethodIcon = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="protocol" type="java.lang.String" length="8" not-null="false" unique="false"
	 */
	public String getProtocol() {
		return this.protocol;
	}
	
	/**
	 * Set the protocol
	 */	
	public void setProtocol(String aValue) {
		this.protocol = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="processorFile" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getProcessorFile() {
		return this.processorFile;
	}
	
	/**
	 * Set the processorFile
	 */	
	public void setProcessorFile(String aValue) {
		this.processorFile = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="configFile" type="java.lang.String" length="256" not-null="false" unique="false"
	 */
	public String getConfigFile() {
		return this.configFile;
	}
	
	/**
	 * Set the configFile
	 */	
	public void setConfigFile(String aValue) {
		this.configFile = aValue;
	}	

	public HashMap<String, String> getConfigData() {
		return configData;
	}

	public void setConfigData(HashMap<String, String> configData) {
		this.configData = configData;
	}

	/**
	 * 	 * @return String
	 * @hibernate.property column="testModel" type="java.lang.String" length="10" not-null="false" unique="false"
	 */
	public String getTestModel() {
		return this.testModel;
	}
	
	/**
	 * Set the testModel
	 */	
	public void setTestModel(String aValue) {
		this.testModel = aValue;
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="isCod" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getIsCod() {
		return this.isCod;
	}
	
	/**
	 * Set the isCod
	 * @spring.validator type="required"
	 */	
	public void setIsCod(Short aValue) {
		this.isCod = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="sortOrder" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getSortOrder() {
		return this.sortOrder;
	}
	
	/**
	 * Set the sortOrder
	 */	
	public void setSortOrder(Integer aValue) {
		this.sortOrder = aValue;
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
	 * @hibernate.property column="version" type="java.lang.Integer" length="10" not-null="true" unique="false"
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

	public java.util.Set<Store> getStores() {
		return stores;
	}

	public void setStores(java.util.Set<Store> stores) {
		this.stores = stores;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof PaymentMethodTbl)) {
			return false;
		}
		PaymentMethodTbl rhs = (PaymentMethodTbl) object;
		return new EqualsBuilder()
				.append(this.paymentMethodId, rhs.paymentMethodId)
				.append(this.paymentMethodName, rhs.paymentMethodName)
				.append(this.paymentMethodCode, rhs.paymentMethodCode)
				.append(this.paymentMethodDetail, rhs.paymentMethodDetail)
				.append(this.paymentMethodType, rhs.paymentMethodType)
				.append(this.paymentMethodIcon, rhs.paymentMethodIcon)
				.append(this.protocol, rhs.protocol)
				.append(this.processorFile, rhs.processorFile)
				.append(this.configFile, rhs.configFile)
				.append(this.configData, rhs.configData)
				.append(this.testModel, rhs.testModel)
				.append(this.isCod, rhs.isCod)
				.append(this.sortOrder, rhs.sortOrder)
				.append(this.status, rhs.status)
				.append(this.version, rhs.version)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.paymentMethodId) 
				.append(this.paymentMethodName) 
				.append(this.paymentMethodCode) 
				.append(this.paymentMethodDetail) 
				.append(this.paymentMethodType) 
				.append(this.paymentMethodIcon) 
				.append(this.protocol) 
				.append(this.processorFile) 
				.append(this.configFile) 
				.append(this.configData) 
				.append(this.testModel) 
				.append(this.isCod) 
				.append(this.sortOrder) 
				.append(this.status) 
				.append(this.version) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("paymentMethodId", this.paymentMethodId) 
				.append("paymentMethodName", this.paymentMethodName) 
				.append("paymentMethodCode", this.paymentMethodCode) 
				.append("paymentMethodDetail", this.paymentMethodDetail) 
				.append("paymentMethodType", this.paymentMethodType) 
				.append("paymentMethodIcon", this.paymentMethodIcon) 
				.append("protocol", this.protocol) 
				.append("processorFile", this.processorFile) 
				.append("configFile", this.configFile) 
				.append("configData", this.configData) 
				.append("testModel", this.testModel) 
				.append("isCod", this.isCod) 
				.append("sortOrder", this.sortOrder) 
				.append("status", this.status) 
				.append("version", this.version) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "paymentMethodId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return paymentMethodId;
	}

}