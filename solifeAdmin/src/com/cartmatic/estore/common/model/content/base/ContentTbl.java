
package com.cartmatic.estore.common.model.content.base;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.cartmatic.estore.common.model.attribute.ContentAttrValue;
import com.cartmatic.estore.common.model.catalog.Category;
import com.cartmatic.estore.common.model.system.Store;

/**
 * Content Base Java Bean, base class for the model, mapped directly to database
 * table
 * 
 * Avoid changing this file if not necessary, will be overwritten.
 * 
 * TODO: add class/table comments
 */
public class ContentTbl extends com.cartmatic.estore.core.model.BaseObject
		implements Serializable {

	private static final long								serialVersionUID	= 9090740950261370857L;
	protected Integer										contentId;
	protected String										contentTitle;
	protected Short											status;
	protected String										contentBody;
	protected java.util.Date								publishTime;
	protected java.util.Date								expiredTime;
	protected Short											sortOrder;
	protected String										metaKeyword;
	protected String										metaDescription;
	protected Date											createTime;
	protected Date											updateTime;
	protected Integer										createBy;
	protected Integer										updateBy;
	protected Integer										version;
	protected String										contentCode;
	private com.cartmatic.estore.common.model.catalog.Category	category;
	protected Store store;

	protected Set<ContentAttrValue>							contentAttrValues	= new HashSet<ContentAttrValue>();

	/**
	 * Default Empty Constructor for class Content
	 */
	public ContentTbl() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class Content
	 */
	public ContentTbl(Integer in_contentId) {
		this.setContentId(in_contentId);
	}

	/**
	 * 
	 * @return Long
	 * @hibernate.id column="contentId" type="java.lang.Long"
	 *               generator-class="native"
	 */
	public Integer getContentId() {
		return this.contentId;
	}

	/**
	 * Set the contentId
	 */
	public void setContentId(Integer aValue) {
		this.contentId = aValue;
	}

	/**
	 * 	 * @return Integer
	 */
	public Integer getCategoryId() {
		return this.getCategory()==null?null:this.getCategory().getCategoryId();
	}
	
	/**
	 * Set the promoRuleId
	 */	
	public void setCategoryId(Integer aValue) {
	    if (aValue==null) {
	    	category = null;
	    } else {
	    	category = new Category(aValue);
	    	category.setVersion(new Integer(0));//set a version to cheat hibernate only
	    }
	}	

	/**
	 * 
	 * @return String
	 * @hibernate.property column="titleKey" type="java.lang.String" length="32"
	 *                     not-null="true" unique="false"
	 */
	public String getContentTitle() {
		return this.contentTitle;
	}

	/**
	 * Set the titleKey
	 * 
	 * @spring.validator type="required"
	 */
	public void setContentTitle(String aValue) {
		this.contentTitle = aValue;
	}

	/**
	 * *
	 * 
	 * @return Short
	 * @hibernate.property column="status" type="java.lang.Short" length="5"
	 *                     not-null="true" unique="false"
	 */
	public Short getStatus() {
		return this.status;
	}

	/**
	 * Set the status
	 * 
	 * @spring.validator type="required"
	 */
	public void setStatus(Short aValue) {
		this.status = aValue;
	}

	/**
	 * *
	 * 
	 * @return String
	 * @hibernate.property column="contentBody" type="java.lang.String"
	 *                     length="65535" not-null="false" unique="false"
	 */
	public String getContentBody() {
		return this.contentBody;
	}

	/**
	 * Set the contentBody
	 */
	public void setContentBody(String aValue) {
		this.contentBody = aValue;
		;
	}

	/**
	 * 
	 * @return java.util.Date
	 * @hibernate.property column="publishTime" type="java.util.Date"
	 *                     length="19" not-null="true" unique="false"
	 */
	public java.util.Date getPublishTime() {
		return this.publishTime;
	}

	/**
	 * Set the issueTime
	 */
	public void setPublishTime(java.util.Date aValue) {
		this.publishTime = aValue;
	}

	/**
	 * 
	 * @return java.util.Date
	 * @hibernate.property column="expiredTime" type="java.util.Date"
	 *                     length="19" not-null="false" unique="false"
	 */
	public java.util.Date getExpiredTime() {
		return this.expiredTime;
	}

	/**
	 * Set the expireTime
	 */
	public void setExpiredTime(java.util.Date aValue) {
		this.expiredTime = aValue;
	}

	/**
	 * 
	 * @return Short
	 * @hibernate.property column="sortOrder" type="java.lang.Short" length="6"
	 *                     not-null="false" unique="false"
	 */
	public Short getSortOrder() {
		return this.sortOrder;
	}

	/**
	 * Set the sortOrder
	 */
	public void setSortOrder(Short aValue) {
		this.sortOrder = aValue;
	}

	public String getMetaKeyword() {
		return metaKeyword;
	}

	public void setMetaKeyword(String metaKeyword) {
		this.metaKeyword = metaKeyword;
	}

	public String getMetaDescription() {
		return metaDescription;
	}

	public void setMetaDescription(String metaDescription) {
		this.metaDescription = metaDescription;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof ContentTbl)) {
			return false;
		}
		ContentTbl rhs = (ContentTbl) object;
		return new EqualsBuilder().append(this.contentId, rhs.contentId).append(
						this.contentTitle, rhs.contentTitle).append(
						this.status, rhs.status).append(this.contentBody,
						rhs.contentBody).append(this.publishTime,
						rhs.publishTime).append(this.contentCode,
						rhs.contentCode).append(this.expiredTime,
								rhs.expiredTime).append(this.sortOrder, rhs.sortOrder)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(
						this.contentTitle).append(this.status).append(
						this.contentBody).append(this.publishTime).append(
						this.expiredTime).append(this.sortOrder).append(this.contentCode).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("contentId", this.contentId).append("titleKey",
						this.contentTitle).append("display", this.status)
				.append("contentValue", this.contentBody).append("issueTime",
						this.publishTime)
				.append("expireTime", this.expiredTime).append("isTop",
						this.sortOrder).toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "contentId";
	}

	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return contentId;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Set<ContentAttrValue> getContentAttrValues() {
		return contentAttrValues;
	}

	public void setContentAttrValues(Set<ContentAttrValue> contentAttrValues) {
		this.contentAttrValues = contentAttrValues;
	}

	/**
	 * *
	 * 
	 * @return java.util.Date
	 * @hibernate.property column="createTime" type="java.util.Date" length="0"
	 *                     not-null="true" unique="false"
	 */
	public java.util.Date getCreateTime() {
		return this.createTime;
	}

	/**
	 * Set the createTime
	 * 
	 * @spring.validator type="required"
	 */
	public void setCreateTime(java.util.Date aValue) {
		this.createTime = aValue;
	}

	/**
	 * *
	 * 
	 * @return java.util.Date
	 * @hibernate.property column="updateTime" type="java.util.Date" length="0"
	 *                     not-null="true" unique="false"
	 */
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}

	/**
	 * Set the updateTime
	 * 
	 * @spring.validator type="required"
	 */
	public void setUpdateTime(java.util.Date aValue) {
		this.updateTime = aValue;
	}

	/**
	 * *
	 * 
	 * @return Integer
	 * @hibernate.property column="createBy" type="java.lang.Integer"
	 *                     length="10" not-null="false" unique="false"
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
	 * *
	 * 
	 * @return Integer
	 * @hibernate.property column="updateBy" type="java.lang.Integer"
	 *                     length="10" not-null="false" unique="false"
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
	 * *
	 * 
	 * @return Integer
	 * @hibernate.property column="version" type="java.lang.Integer" length="10"
	 *                     not-null="true" unique="false"
	 */
	public Integer getVersion() {
		return this.version;
	}

	/**
	 * Set the version
	 * 
	 * @spring.validator type="required"
	 */
	public void setVersion(Integer aValue) {
		this.version = aValue;
	}

	public String getContentCode() {
		return contentCode;
	}

	public void setContentCode(String contentCode) {
		this.contentCode = contentCode;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}
	
	/**
	 * 	 * @return Integer
	 */
	public Integer getStoreId() {
		return this.getStore()==null?null:this.getStore().getStoreId();
	}
	
	/**
	 * Set the storeId
	 */	
	public void setStoreId(Integer aValue) {
	    if (aValue==null) {
	    	store = null;
	    } else {
	    	store = new Store(aValue);
	    	store.setVersion(new Integer(0));//set a version to cheat hibernate only
	    }
	}
}