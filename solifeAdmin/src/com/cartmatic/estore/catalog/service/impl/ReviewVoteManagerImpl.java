package com.cartmatic.estore.catalog.service.impl;

import com.cartmatic.estore.catalog.dao.ReviewVoteDao;
import com.cartmatic.estore.catalog.service.ReviewVoteManager;
import com.cartmatic.estore.common.model.catalog.ReviewVote;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;


/**
 * Manager implementation for ReviewVote, responsible for business processing, and communicate between web and persistence layer.
 */
public class ReviewVoteManagerImpl extends GenericManagerImpl<ReviewVote> implements ReviewVoteManager {

	private ReviewVoteDao reviewVoteDao = null;

	/**
	 * @param reviewVoteDao
	 *            the reviewVoteDao to set
	 */
	public void setReviewVoteDao(ReviewVoteDao reviewVoteDao) {
		this.reviewVoteDao = reviewVoteDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		dao = reviewVoteDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete(java.lang.Object)
	 */
	@Override
	protected void onDelete(ReviewVote entity) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave(java.lang.Object)
	 */
	@Override
	protected void onSave(ReviewVote entity) {

	}

	public boolean voteAlready(Integer productReviewId, Integer customerId) {
		return reviewVoteDao.voteAlready(productReviewId, customerId);
	}

}
