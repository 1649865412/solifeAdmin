package com.cartmatic.estore.common.model.customer.base;

import java.io.Serializable;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.core.model.BaseObject;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * Feedback Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class FeedbackTbl extends BaseObject implements Serializable {

    protected Integer feedbackId;
	protected String title;
	protected String firstName;
	protected String lastName;
	protected String subject;
	protected String content;
	protected Short status;
	protected Short replyType;
	protected Short feedbackType;
	protected Integer priority;
	protected String email;
	protected String telephone;
	protected String fax;
	protected Integer threadId;
	protected java.util.Date updateTime;
	protected java.util.Date createTime;
	protected Short givenShopPointAction;
	protected Integer version;
	protected com.cartmatic.estore.common.model.system.AppUser appUser;
	protected com.cartmatic.estore.common.model.catalog.Product product;


	/**
	 * Default Empty Constructor for class Feedback
	 */
	public FeedbackTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class Feedback
	 */
	public FeedbackTbl (
		 Integer in_feedbackId
        ) {
		this.setFeedbackId(in_feedbackId);
    }

	
	public com.cartmatic.estore.common.model.system.AppUser getAppUser () {
		return appUser;
	}	
	
	public void setAppUser (com.cartmatic.estore.common.model.system.AppUser in_appUser) {
		this.appUser = in_appUser;
	}
	
	public com.cartmatic.estore.common.model.catalog.Product getProduct () {
		return product;
	}	
	
	public void setProduct (com.cartmatic.estore.common.model.catalog.Product in_product) {
		this.product = in_product;
	}
    

	/**
	 * 	 * @return Integer
     * @hibernate.id column="feedbackId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getFeedbackId() {
		return this.feedbackId;
	}
	
	/**
	 * Set the feedbackId
	 */	
	public void setFeedbackId(Integer aValue) {
		this.feedbackId = aValue;
	}	

	/**
	 * 	 * @return Integer
	 */
	public Integer getProductId() {
		return this.getProduct()==null?null:this.getProduct().getProductId();
	}
	
	/**
	 * Set the productId
	 */	
	public void setProductId(Integer aValue) {
	    if (aValue==null) {
	    	product = null;
	    } else {
	        product = new com.cartmatic.estore.common.model.catalog.Product(aValue);
	        product.setVersion(new Integer(0));//set a version to cheat hibernate only
	    }
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
	 * 	 * @return String
	 * @hibernate.property column="title" type="java.lang.String" length="8" not-null="false" unique="false"
	 */
	public String getTitle() {
		return this.title;
	}
	
	/**
	 * Set the title
	 */	
	public void setTitle(String aValue) {
		this.title = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="firstName" type="java.lang.String" length="32" not-null="false" unique="false"
	 */
	public String getFirstName() {
		return this.firstName;
	}
	
	/**
	 * Set the firstName
	 */	
	public void setFirstName(String aValue) {
		this.firstName = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="lastName" type="java.lang.String" length="32" not-null="false" unique="false"
	 */
	public String getLastName() {
		return this.lastName;
	}
	
	/**
	 * Set the lastName
	 */	
	public void setLastName(String aValue) {
		this.lastName = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="subject" type="java.lang.String" length="128" not-null="false" unique="false"
	 */
	public String getSubject() {
		return this.subject;
	}
	
	/**
	 * Set the subject
	 */	
	public void setSubject(String aValue) {
		this.subject = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="content" type="java.lang.String" length="512" not-null="false" unique="false"
	 */
	public String getContent() {
		return this.content;
	}
	
	/**
	 * Set the content
	 */	
	public void setContent(String aValue) {
		this.content = aValue;
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
	 * 	 * @return Short
	 * @hibernate.property column="replyType" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
	public Short getReplyType() {
		return this.replyType;
	}
	
	/**
	 * Set the replyType
	 */	
	public void setReplyType(Short aValue) {
		this.replyType = aValue;
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="feedbackType" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
	public Short getFeedbackType() {
		return this.feedbackType;
	}
	
	/**
	 * Set the feedbackType
	 */	
	public void setFeedbackType(Short aValue) {
		this.feedbackType = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="priority" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getPriority() {
		return this.priority;
	}
	
	/**
	 * Set the priority
	 */	
	public void setPriority(Integer aValue) {
		this.priority = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="email" type="java.lang.String" length="64" not-null="false" unique="false"
	 */
	public String getEmail() {
		return this.email;
	}
	
	/**
	 * Set the email
	 */	
	public void setEmail(String aValue) {
		this.email = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="telephone" type="java.lang.String" length="32" not-null="false" unique="false"
	 */
	public String getTelephone() {
		return this.telephone;
	}
	
	/**
	 * Set the telephone
	 */	
	public void setTelephone(String aValue) {
		this.telephone = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="fax" type="java.lang.String" length="32" not-null="false" unique="false"
	 */
	public String getFax() {
		return this.fax;
	}
	
	/**
	 * Set the fax
	 */	
	public void setFax(String aValue) {
		this.fax = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="threadId" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getThreadId() {
		return this.threadId;
	}
	
	/**
	 * Set the threadId
	 */	
	public void setThreadId(Integer aValue) {
		this.threadId = aValue;
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
	 * 	 * @return Short
	 * @hibernate.property column="givenShopPointAction" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
	public Short getGivenShopPointAction() {
		return this.givenShopPointAction;
	}
	
	/**
	 * Set the givenShopPointAction
	 */	
	public void setGivenShopPointAction(Short aValue) {
		this.givenShopPointAction = aValue;
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
		if (!(object instanceof FeedbackTbl)) {
			return false;
		}
		FeedbackTbl rhs = (FeedbackTbl) object;
		return new EqualsBuilder()
				.append(this.feedbackId, rhs.feedbackId)
								.append(this.title, rhs.title)
				.append(this.firstName, rhs.firstName)
				.append(this.lastName, rhs.lastName)
				.append(this.subject, rhs.subject)
				.append(this.content, rhs.content)
				.append(this.status, rhs.status)
				.append(this.replyType, rhs.replyType)
				.append(this.feedbackType, rhs.feedbackType)
				.append(this.priority, rhs.priority)
				.append(this.email, rhs.email)
				.append(this.telephone, rhs.telephone)
				.append(this.fax, rhs.fax)
				.append(this.threadId, rhs.threadId)
				.append(this.updateTime, rhs.updateTime)
				.append(this.createTime, rhs.createTime)
				.append(this.givenShopPointAction, rhs.givenShopPointAction)
				.append(this.version, rhs.version)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.feedbackId) 
								.append(this.title) 
				.append(this.firstName) 
				.append(this.lastName) 
				.append(this.subject) 
				.append(this.content) 
				.append(this.status) 
				.append(this.replyType) 
				.append(this.feedbackType) 
				.append(this.priority) 
				.append(this.email) 
				.append(this.telephone) 
				.append(this.fax) 
				.append(this.threadId) 
				.append(this.updateTime) 
				.append(this.createTime) 
				.append(this.givenShopPointAction) 
				.append(this.version) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("feedbackId", this.feedbackId) 
								.append("title", this.title) 
				.append("firstName", this.firstName) 
				.append("lastName", this.lastName) 
				.append("subject", this.subject) 
				.append("content", this.content) 
				.append("status", this.status) 
				.append("replyType", this.replyType) 
				.append("feedbackType", this.feedbackType) 
				.append("priority", this.priority) 
				.append("email", this.email) 
				.append("telephone", this.telephone) 
				.append("fax", this.fax) 
				.append("threadId", this.threadId) 
				.append("updateTime", this.updateTime) 
				.append("createTime", this.createTime) 
				.append("givenShopPointAction", this.givenShopPointAction) 
				.append("version", this.version) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "feedbackId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return feedbackId;
	}

}