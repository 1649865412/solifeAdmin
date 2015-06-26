package com.cartmatic.estore.common.model.content.base;

import java.io.Serializable;
import com.cartmatic.estore.Constants;
import com.cartmatic.estore.core.model.BaseObject;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * Advertisement Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class AdvertisementTbl extends BaseObject implements Serializable {

    protected Integer advertisementId;
	protected String advertisementName;
	protected Short contentType;
	protected String advertisementDetail;
	protected String mediaPath;
	protected String url;
	protected String redirectUrl;
	protected String target;
	
	protected java.util.Date startPublishTime;
	protected java.util.Date endPublishTime;
	protected Integer sortOrder;
	protected Integer createBy;
	protected Integer updateBy;
	protected java.util.Date createTime;
	protected java.util.Date updateTime;
	protected Short isInclude;
	protected Integer version;
	protected com.cartmatic.estore.common.model.content.AdPositionType adPositionType;

	protected java.util.Set productAdvertisements = new java.util.HashSet();

	/**
	 * Default Empty Constructor for class Advertisement
	 */
	public AdvertisementTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class Advertisement
	 */
	public AdvertisementTbl (
		 Integer in_advertisementId
        ) {
		this.setAdvertisementId(in_advertisementId);
    }

	
	public com.cartmatic.estore.common.model.content.AdPositionType getAdPositionType () {
		return adPositionType;
	}	
	
	public void setAdPositionType (com.cartmatic.estore.common.model.content.AdPositionType in_adPositionType) {
		this.adPositionType = in_adPositionType;
	}

	public java.util.Set getProductAdvertisements () {
		return productAdvertisements;
	}	
	
	public void setProductAdvertisements (java.util.Set in_productAdvertisements) {
		this.productAdvertisements = in_productAdvertisements;
	}
    

	/**
	 * 	 * @return Integer
     * @hibernate.id column="advertisementId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getAdvertisementId() {
		return this.advertisementId;
	}
	
	/**
	 * Set the advertisementId
	 */	
	public void setAdvertisementId(Integer aValue) {
		this.advertisementId = aValue;
	}	

	/**
	 * 	 * @return Integer
	 */
	public Integer getAdPositionTypeId() {
		return this.getAdPositionType()==null?null:this.getAdPositionType().getAdPositionTypeId();
	}
	
	/**
	 * Set the adPositionTypeId
	 */	
	public void setAdPositionTypeId(Integer aValue) {
	    if (aValue==null) {
	    	adPositionType = null;
	    } else {
	        adPositionType = new com.cartmatic.estore.common.model.content.AdPositionType(aValue);
	        adPositionType.setVersion(new Integer(0));//set a version to cheat hibernate only
	    }
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="advertisementName" type="java.lang.String" length="128" not-null="true" unique="false"
	 */
	public String getAdvertisementName() {
		return this.advertisementName;
	}
	
	/**
	 * Set the advertisementName
	 * @spring.validator type="required"
	 */	
	public void setAdvertisementName(String aValue) {
		this.advertisementName = aValue;
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="contentType" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getContentType() {
		return this.contentType;
	}
	
	/**
	 * Set the contentType
	 * @spring.validator type="required"
	 */	
	public void setContentType(Short aValue) {
		this.contentType = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="advertisementDetail" type="java.lang.String" length="512" not-null="false" unique="false"
	 */
	public String getAdvertisementDetail() {
		return this.advertisementDetail;
	}
	
	/**
	 * Set the advertisementDetail
	 */	
	public void setAdvertisementDetail(String aValue) {
		this.advertisementDetail = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="mediaPath" type="java.lang.String" length="128" not-null="false" unique="false"
	 */
	public String getMediaPath() {
		return this.mediaPath;
	}
	
	/**
	 * Set the mediaPath
	 */	
	public void setMediaPath(String aValue) {
		this.mediaPath = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="url" type="java.lang.String" length="255" not-null="true" unique="false"
	 */
	public String getUrl() {
		return this.url;
	}
	
	/**
	 * Set the url
	 * @spring.validator type="required"
	 */	
	public void setUrl(String aValue) {
		this.url = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="redirectUrl" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getRedirectUrl() {
		return this.redirectUrl;
	}
	
	/**
	 * Set the redirectUrl
	 */	
	public void setRedirectUrl(String aValue) {
		this.redirectUrl = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="target" type="java.lang.String" length="32" not-null="false" unique="false"
	 */
	public String getTarget() {
		return this.target;
	}
	
	/**
	 * Set the target
	 */	
	public void setTarget(String aValue) {
		this.target = aValue;
	}	

	
	
	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="startPublishTime" type="java.util.Date" length="0" not-null="false" unique="false"
	 */
	public java.util.Date getStartPublishTime() {
		return this.startPublishTime;
	}
	
	/**
	 * Set the startPublishTime
	 */	
	public void setStartPublishTime(java.util.Date aValue) {
		this.startPublishTime = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="endPublishTime" type="java.util.Date" length="0" not-null="false" unique="false"
	 */
	public java.util.Date getEndPublishTime() {
		return this.endPublishTime;
	}
	
	/**
	 * Set the endPublishTime
	 */	
	public void setEndPublishTime(java.util.Date aValue) {
		this.endPublishTime = aValue;
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
	 * 	 * @return Integer
	 * @hibernate.property column="createBy" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getCreateBy() {
		return this.createBy;
	}
	
	/**
	 * Set the createBy
	 */	
	public void setCreateBy(Integer aValue) {
		this.createBy = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="updateBy" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getUpdateBy() {
		return this.updateBy;
	}
	
	/**
	 * Set the updateBy
	 */	
	public void setUpdateBy(Integer aValue) {
		this.updateBy = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="createTime" type="java.util.Date" length="0" not-null="false" unique="false"
	 */
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	
	/**
	 * Set the createTime
	 */	
	public void setCreateTime(java.util.Date aValue) {
		this.createTime = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="updateTime" type="java.util.Date" length="0" not-null="false" unique="false"
	 */
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}
	
	/**
	 * Set the updateTime
	 */	
	public void setUpdateTime(java.util.Date aValue) {
		this.updateTime = aValue;
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="isInclude" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
	public Short getIsInclude() {
		return this.isInclude;
	}
	
	/**
	 * Set the isInclude
	 */	
	public void setIsInclude(Short aValue) {
		this.isInclude = aValue;
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
		if (!(object instanceof AdvertisementTbl)) {
			return false;
		}
		AdvertisementTbl rhs = (AdvertisementTbl) object;
		return new EqualsBuilder()
				.append(this.advertisementId, rhs.advertisementId)
						.append(this.advertisementName, rhs.advertisementName)
				.append(this.contentType, rhs.contentType)
				.append(this.advertisementDetail, rhs.advertisementDetail)
				.append(this.mediaPath, rhs.mediaPath)
				.append(this.url, rhs.url)
				.append(this.redirectUrl, rhs.redirectUrl)
				.append(this.target, rhs.target)
				.append(this.startPublishTime, rhs.startPublishTime)
				.append(this.endPublishTime, rhs.endPublishTime)
				.append(this.sortOrder, rhs.sortOrder)
				.append(this.createBy, rhs.createBy)
				.append(this.updateBy, rhs.updateBy)
				.append(this.createTime, rhs.createTime)
				.append(this.updateTime, rhs.updateTime)
				.append(this.isInclude, rhs.isInclude)
				.append(this.version, rhs.version)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.advertisementId) 
						.append(this.advertisementName) 
				.append(this.contentType) 
				.append(this.advertisementDetail) 
				.append(this.mediaPath) 
				.append(this.url) 
				.append(this.redirectUrl) 
				.append(this.target) 
				.append(this.startPublishTime) 
				.append(this.endPublishTime) 
				.append(this.sortOrder) 
				.append(this.createBy) 
				.append(this.updateBy) 
				.append(this.createTime) 
				.append(this.updateTime) 
				.append(this.isInclude) 
				.append(this.version) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("advertisementId", this.advertisementId) 
						.append("advertisementName", this.advertisementName) 
				.append("contentType", this.contentType) 
				.append("advertisementDetail", this.advertisementDetail) 
				.append("mediaPath", this.mediaPath) 
				.append("url", this.url) 
				.append("redirectUrl", this.redirectUrl) 
				.append("target", this.target) 
				.append("startPublishTime", this.startPublishTime) 
				.append("endPublishTime", this.endPublishTime) 
				.append("sortOrder", this.sortOrder) 
				.append("createBy", this.createBy) 
				.append("updateBy", this.updateBy) 
				.append("createTime", this.createTime) 
				.append("updateTime", this.updateTime) 
				.append("isInclude", this.isInclude) 
				.append("version", this.version) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "advertisementId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return advertisementId;
	}

}