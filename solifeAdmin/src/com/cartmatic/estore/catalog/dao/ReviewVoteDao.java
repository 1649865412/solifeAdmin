package com.cartmatic.estore.catalog.dao;

import com.cartmatic.estore.common.model.catalog.ReviewVote;
import com.cartmatic.estore.core.dao.GenericDao;
/**
 * Dao interface for ReviewVote.
 */
public interface ReviewVoteDao extends GenericDao<ReviewVote> {
	/**
	 * 检查是否已经投票了
	 * @param productReviewId
	 * @param customerId
	 * @return
	 */
	public boolean voteAlready(Integer productReviewId, Integer customerId);
}