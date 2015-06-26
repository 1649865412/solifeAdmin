package com.cartmatic.estore.common.model.catalog.base;

import java.io.Serializable;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.common.model.system.Store;
import com.cartmatic.estore.core.model.BaseObject;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * ProductReview Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class ProductReviewTbl extends BaseObject implements Serializable {

    protected Integer productReviewId;
	protected String customerName;
	protected String subject;
	protected String message;
	protected String remoteIp;
	protected Integer givenPoint;
	protected java.util.Date givenTime;
	protected Integer rate;
	protected Integer usefulCount;
	protected Integer unusefulCount;
	protected Integer reviewId;
	protected Short hasReply;
	protected java.util.Date createTime;
	protected java.util.Date updateTime;
	protected Short status;
	protected com.cartmatic.estore.common.model.catalog.Product product;
	/**
	 * 回复者，当前台发表评论时，就为客户，后台为管理员
	 */
	protected com.cartmatic.estore.common.model.system.AppUser reviewUser;
	/**
	 * 赠送者
	 */
	protected com.cartmatic.estore.common.model.system.AppUser grantor;
	

	protected java.util.Set productRateValues = new java.util.HashSet();
	protected java.util.Set reviewVotes = new java.util.HashSet();

	protected Store store;

	/**
	 * Default Empty Constructor for class ProductReview
	 */
	public ProductReviewTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class ProductReview
	 */
	public ProductReviewTbl (
		 Integer in_productReviewId
        ) {
		this.setProductReviewId(in_productReviewId);
    }

	
	public com.cartmatic.estore.common.model.catalog.Product getProduct () {
		return product;
	}	
	
	public void setProduct (com.cartmatic.estore.common.model.catalog.Product in_product) {
		this.product = in_product;
	}
	

	public com.cartmatic.estore.common.model.system.AppUser getReviewUser() {
		return reviewUser;
	}

	public void setReviewUser(com.cartmatic.estore.common.model.system.AppUser reviewUser) {
		this.reviewUser = reviewUser;
	}


	public java.util.Set getProductRateValues () {
		return productRateValues;
	}	
	
	public void setProductRateValues (java.util.Set in_productRateValues) {
		this.productRateValues = in_productRateValues;
	}

	public java.util.Set getReviewVotes () {
		return reviewVotes;
	}	
	
	public void setReviewVotes (java.util.Set in_reviewVotes) {
		this.reviewVotes = in_reviewVotes;
	}
    

	/**
	 * 	 * @return Integer
     * @hibernate.id column="productReviewId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getProductReviewId() {
		return this.productReviewId;
	}
	
	/**
	 * Set the productReviewId
	 */	
	public void setProductReviewId(Integer aValue) {
		this.productReviewId = aValue;
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
	 * 后台回复评论的管理员Id	 * @return Integer
	 */
	public Integer getReviewUserId() {
		return this.getReviewUser()==null?null:this.getReviewUser().getAppuserId();
	}
	
	/**
	 * Set the managerId
	 */	
	public void setReviewUserId(Integer aValue) {
	    if (aValue==null) {
	    	reviewUser = null;
	    } else {
	    	reviewUser = new com.cartmatic.estore.common.model.system.AppUser(aValue);
	    	reviewUser.setVersion(new Integer(0));//set a version to cheat hibernate only
	    }
	}	
	


	/**
	 * 未登录时发表评论的名称	 * @return String
	 * @hibernate.property column="customerName" type="java.lang.String" length="64" not-null="false" unique="false"
	 */
	public String getCustomerName() {
		return this.customerName;
	}
	
	/**
	 * Set the customerName
	 */	
	public void setCustomerName(String aValue) {
		this.customerName = aValue;
	}	

	/**
	 * 发表评论的主题	 * @return String
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
	 * 评论内容（消息）	 * @return String
	 * @hibernate.property column="message" type="java.lang.String" length="2048" not-null="true" unique="false"
	 */
	public String getMessage() {
		return this.message;
	}
	
	/**
	 * Set the message
	 * @spring.validator type="required"
	 */	
	public void setMessage(String aValue) {
		this.message = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="remoteIp" type="java.lang.String" length="32" not-null="true" unique="false"
	 */
	public String getRemoteIp() {
		return this.remoteIp;
	}
	
	/**
	 * Set the remoteIp
	 * @spring.validator type="required"
	 */	
	public void setRemoteIp(String aValue) {
		this.remoteIp = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="givenPoint" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getGivenPoint() {
		return this.givenPoint;
	}
	
	/**
	 * Set the givenPoint
	 */	
	public void setGivenPoint(Integer aValue) {
		this.givenPoint = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="givenTime" type="java.util.Date" length="0" not-null="false" unique="false"
	 */
	public java.util.Date getGivenTime() {
		return this.givenTime;
	}
	
	/**
	 * Set the givenTime
	 */	
	public void setGivenTime(java.util.Date aValue) {
		this.givenTime = aValue;
	}	

	/**
	 * 该产品的整体评分	 * @return Integer
	 * @hibernate.property column="rate" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getRate() {
		return this.rate;
	}
	
	/**
	 * Set the rate
	 */	
	public void setRate(Integer aValue) {
		this.rate = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="usefulCount" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getUsefulCount() {
		return this.usefulCount;
	}
	
	/**
	 * Set the usefulCount
	 */	
	public void setUsefulCount(Integer aValue) {
		this.usefulCount = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="unusefulCount" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getUnusefulCount() {
		return this.unusefulCount;
	}
	
	/**
	 * Set the unusefulCount
	 */	
	public void setUnusefulCount(Integer aValue) {
		this.unusefulCount = aValue;
	}	

	/**
	 * 作为评论回复时，所指向的评论Id	 * @return Integer
	 * @hibernate.property column="reviewId" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getReviewId() {
		return this.reviewId;
	}
	
	/**
	 * Set the reviewId
	 */	
	public void setReviewId(Integer aValue) {
		this.reviewId = aValue;
	}	

	/**
	 * 该评论是否已回复	 * @return Short
	 * @hibernate.property column="hasReply" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
	public Short getHasReply() {
		return this.hasReply;
	}
	
	/**
	 * Set the hasReply
	 */	
	public void setHasReply(Short aValue) {
		this.hasReply = aValue;
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
	 * 用于标识评论是否已审核
            1=激活
            2=非激活
            	 * @return Short
	 * @hibernate.property column="status" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getStatus() {
		return this.status;
	}
	
	/**
	 * Set the status
	 * @spring.validator type="required"
	 */	
	public void setStatus(Short aValue) {
		this.status = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof ProductReviewTbl)) {
			return false;
		}
		ProductReviewTbl rhs = (ProductReviewTbl) object;
		return new EqualsBuilder()
				.append(this.productReviewId, rhs.productReviewId)
				.append(this.customerName, rhs.customerName)
				.append(this.subject, rhs.subject)
				.append(this.message, rhs.message)
				.append(this.remoteIp, rhs.remoteIp)
				.append(this.givenPoint, rhs.givenPoint)
				.append(this.givenTime, rhs.givenTime)
				.append(this.rate, rhs.rate)
				.append(this.usefulCount, rhs.usefulCount)
				.append(this.unusefulCount, rhs.unusefulCount)
				.append(this.reviewId, rhs.reviewId)
				.append(this.hasReply, rhs.hasReply)
				.append(this.createTime, rhs.createTime)
				.append(this.updateTime, rhs.updateTime)
				.append(this.status, rhs.status)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.productReviewId) 
				.append(this.customerName) 
				.append(this.subject) 
				.append(this.message) 
				.append(this.remoteIp) 
				.append(this.givenPoint) 
				.append(this.givenTime) 
				.append(this.rate) 
				.append(this.usefulCount) 
				.append(this.unusefulCount) 
				.append(this.reviewId) 
				.append(this.hasReply) 
				.append(this.createTime) 
				.append(this.updateTime) 
				.append(this.status) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("productReviewId", this.productReviewId) 
				.append("customerName", this.customerName) 
				.append("subject", this.subject) 
				.append("message", this.message) 
				.append("remoteIp", this.remoteIp) 
				.append("givenPoint", this.givenPoint) 
				.append("givenTime", this.givenTime) 
				.append("rate", this.rate) 
				.append("usefulCount", this.usefulCount) 
				.append("unusefulCount", this.unusefulCount) 
				.append("reviewId", this.reviewId) 
				.append("hasReply", this.hasReply) 
				.append("createTime", this.createTime) 
				.append("updateTime", this.updateTime) 
				.append("status", this.status) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "productReviewId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return productReviewId;
	}

	public void setGrantor(com.cartmatic.estore.common.model.system.AppUser grantor) {
		this.grantor = grantor;
	}

	public com.cartmatic.estore.common.model.system.AppUser getGrantor() {
		return grantor;
	}
	
	public Integer getGrantorId() {
		return this.getGrantor()==null?null:this.getGrantor().getAppuserId();
	}
	
	/**
	 * Set the managerId
	 */	
	public void setGrantorId(Integer aValue) {
	    if (aValue==null) {
	    	grantor = null;
	    } else {
	    	grantor = new com.cartmatic.estore.common.model.system.AppUser(aValue);
	    	grantor.setVersion(new Integer(0));//set a version to cheat hibernate only
	    }
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