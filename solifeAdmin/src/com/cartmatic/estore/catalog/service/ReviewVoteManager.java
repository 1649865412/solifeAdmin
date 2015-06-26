package com.cartmatic.estore.catalog.service;

import com.cartmatic.estore.common.model.catalog.ReviewVote;
import com.cartmatic.estore.core.service.GenericManager;

/**
 * Manager interface for ReviewVote, responsible for business processing, and communicate between web and persistence layer.
 *
 */
public interface ReviewVoteManager extends GenericManager<ReviewVote> {
	/**
	 * 检查是否已经投票了
	 * @param productReviewId
	 * @param customerId
	 * @return
	 */
	public boolean voteAlready(Integer productReviewId, Integer customerId);
}
