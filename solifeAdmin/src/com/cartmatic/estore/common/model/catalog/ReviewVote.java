package com.cartmatic.estore.common.model.catalog;

import com.cartmatic.estore.common.model.catalog.base.ReviewVoteTbl;

/**
 * Model class for ReviewVote. Add not database mapped fileds in this class.
 */
public class ReviewVote extends ReviewVoteTbl {

  	/**
	 * Default Empty Constructor for class ReviewVote
	 */
	public ReviewVote () {
		super();
	}
	
	/**
	 * 定义实体的业务名取值； reviewVoteName
	 * 必须手工完成这个部分，否则编译不通过。
	 */
	public String getReviewVoteName () {
		if (reviewVoteId == null)
	        return "";
	    else
			//返回一个指定有业务意义的属性值;
			//如：product的VO就用product.productName
	        return this.reviewVoteId.toString();
	}
	
	/**
	 * Default Key Fields Constructor for class ReviewVote
	 */
	public ReviewVote (
		 Integer in_reviewVoteId
		) {
		super (
		  in_reviewVoteId
		);
	}

}
