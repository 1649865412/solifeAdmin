package com.cartmatic.estore.common.model.other.base;

import java.io.Serializable;
import com.cartmatic.estore.Constants;
import com.cartmatic.estore.core.model.BaseObject;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * PatchExecuteHistory Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class PatchExecuteHistoryTbl extends BaseObject implements Serializable {

    protected Integer patchExecuteHistoryId;
	protected String transactionUid;
	protected String fileName;
	protected String fileType;
	protected Short flag;
	protected String resultDescription;
	protected String fromVersion;
	protected String toVersion;
	protected java.util.Date executeTime;
	protected Integer version;


	/**
	 * Default Empty Constructor for class PatchExecuteHistory
	 */
	public PatchExecuteHistoryTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class PatchExecuteHistory
	 */
	public PatchExecuteHistoryTbl (
		 Integer in_patchExecuteHistoryId
        ) {
		this.setPatchExecuteHistoryId(in_patchExecuteHistoryId);
    }

    

	/**
	 * 	 * @return Integer
     * @hibernate.id column="patchExecuteHistoryId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getPatchExecuteHistoryId() {
		return this.patchExecuteHistoryId;
	}
	
	/**
	 * Set the patchExecuteHistoryId
	 */	
	public void setPatchExecuteHistoryId(Integer aValue) {
		this.patchExecuteHistoryId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="transactionUid" type="java.lang.String" length="32" not-null="false" unique="false"
	 */
	public String getTransactionUid() {
		return this.transactionUid;
	}
	
	/**
	 * Set the transactionUid
	 */	
	public void setTransactionUid(String aValue) {
		this.transactionUid = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="fileName" type="java.lang.String" length="64" not-null="true" unique="false"
	 */
	public String getFileName() {
		return this.fileName;
	}
	
	/**
	 * Set the fileName
	 * @spring.validator type="required"
	 */	
	public void setFileName(String aValue) {
		this.fileName = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="fileType" type="java.lang.String" length="64" not-null="true" unique="false"
	 */
	public String getFileType() {
		return this.fileType;
	}
	
	/**
	 * Set the fileType
	 * @spring.validator type="required"
	 */	
	public void setFileType(String aValue) {
		this.fileType = aValue;
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="flag" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getFlag() {
		return this.flag;
	}
	
	/**
	 * Set the flag
	 * @spring.validator type="required"
	 */	
	public void setFlag(Short aValue) {
		this.flag = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="resultDescription" type="java.lang.String" length="256" not-null="false" unique="false"
	 */
	public String getResultDescription() {
		return this.resultDescription;
	}
	
	/**
	 * Set the resultDescription
	 */	
	public void setResultDescription(String aValue) {
		this.resultDescription = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="fromVersion" type="java.lang.String" length="32" not-null="true" unique="false"
	 */
	public String getFromVersion() {
		return this.fromVersion;
	}
	
	/**
	 * Set the fromVersion
	 * @spring.validator type="required"
	 */	
	public void setFromVersion(String aValue) {
		this.fromVersion = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="toVersion" type="java.lang.String" length="32" not-null="true" unique="false"
	 */
	public String getToVersion() {
		return this.toVersion;
	}
	
	/**
	 * Set the toVersion
	 * @spring.validator type="required"
	 */	
	public void setToVersion(String aValue) {
		this.toVersion = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="executeTime" type="java.util.Date" length="0" not-null="false" unique="false"
	 */
	public java.util.Date getExecuteTime() {
		return this.executeTime;
	}
	
	/**
	 * Set the executeTime
	 */	
	public void setExecuteTime(java.util.Date aValue) {
		this.executeTime = aValue;
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

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof PatchExecuteHistoryTbl)) {
			return false;
		}
		PatchExecuteHistoryTbl rhs = (PatchExecuteHistoryTbl) object;
		return new EqualsBuilder()
				.append(this.patchExecuteHistoryId, rhs.patchExecuteHistoryId)
				.append(this.transactionUid, rhs.transactionUid)
				.append(this.fileName, rhs.fileName)
				.append(this.fileType, rhs.fileType)
				.append(this.flag, rhs.flag)
				.append(this.resultDescription, rhs.resultDescription)
				.append(this.fromVersion, rhs.fromVersion)
				.append(this.toVersion, rhs.toVersion)
				.append(this.executeTime, rhs.executeTime)
				.append(this.version, rhs.version)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.patchExecuteHistoryId) 
				.append(this.transactionUid) 
				.append(this.fileName) 
				.append(this.fileType) 
				.append(this.flag) 
				.append(this.resultDescription) 
				.append(this.fromVersion) 
				.append(this.toVersion) 
				.append(this.executeTime) 
				.append(this.version) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("patchExecuteHistoryId", this.patchExecuteHistoryId) 
				.append("transactionUid", this.transactionUid) 
				.append("fileName", this.fileName) 
				.append("fileType", this.fileType) 
				.append("flag", this.flag) 
				.append("resultDescription", this.resultDescription) 
				.append("fromVersion", this.fromVersion) 
				.append("toVersion", this.toVersion) 
				.append("executeTime", this.executeTime) 
				.append("version", this.version) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "patchExecuteHistoryId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return patchExecuteHistoryId;
	}

}