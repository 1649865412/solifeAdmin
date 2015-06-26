package com.cartmatic.estore.common.model.catalog.base;

import java.io.Serializable;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.core.model.BaseObject;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * ReviewVote Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class ReviewVoteTbl extends BaseObject implements Serializable {

    protected Integer reviewVoteId;
	protected com.cartmatic.estore.common.model.catalog.ProductReview productReview;
	protected com.cartmatic.estore.common.model.system.AppUser appUser;


	/**
	 * Default Empty Constructor for class ReviewVote
	 */
	public ReviewVoteTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class ReviewVote
	 */
	public ReviewVoteTbl (
		 Integer in_reviewVoteId
        ) {
		this.setReviewVoteId(in_reviewVoteId);
    }

	
	public com.cartmatic.estore.common.model.catalog.ProductReview getProductReview () {
		return productReview;
	}	
	
	public void setProductReview (com.cartmatic.estore.common.model.catalog.ProductReview in_productReview) {
		this.productReview = in_productReview;
	}
	
	public com.cartmatic.estore.common.model.system.AppUser getAppUser () {
		return appUser;
	}	
	
	public void setAppUser (com.cartmatic.estore.common.model.system.AppUser in_appUser) {
		this.appUser = in_appUser;
	}
    

	/**
	 * 	 * @return Integer
     * @hibernate.id column="reviewVoteId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getReviewVoteId() {
		return this.reviewVoteId;
	}
	
	/**
	 * Set the reviewVoteId
	 */	
	public void setReviewVoteId(Integer aValue) {
		this.reviewVoteId = aValue;
	}	

	/**
	 * 	 * @return Integer
	 */
	public Integer getProductReviewId() {
		return this.getProductReview()==null?null:this.getProductReview().getProductReviewId();
	}
	
	/**
	 * Set the productReviewId
	 */	
	public void setProductReviewId(Integer aValue) {
	    if (aValue==null) {
	    	productReview = null;
	    } else {
	    	productReview = new com.cartmatic.estore.common.model.catalog.ProductReview(aValue);
	        productReview.setVersion(new Integer(0));//set a version to cheat hibernate only
	    }
	}	

	/**
	 * 	 * @return Integer
	 */
	public Integer getCustomerId() {
		return this.getAppUser()==null?null:this.getAppUser().getAppuserId();
	}
	
	/**
	 * Set the customerId
	 */	
	public void setCustomerId(Integer aValue) {
	    if (aValue==null) {
	    	appUser = null;
	    } else {
	    	appUser = new com.cartmatic.estore.common.model.system.AppUser(aValue);
	        appUser.setVersion(new Integer(0));//set a version to cheat hibernate only
	    }
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof ReviewVoteTbl)) {
			return false;
		}
		ReviewVoteTbl rhs = (ReviewVoteTbl) object;
		return new EqualsBuilder()
				.append(this.reviewVoteId, rhs.reviewVoteId)
								.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.reviewVoteId) 
								.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("reviewVoteId", this.reviewVoteId) 
								.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "reviewVoteId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return reviewVoteId;
	}

}