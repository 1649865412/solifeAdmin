package com.cartmatic.estore.catalog.dao.impl;

import com.cartmatic.estore.catalog.dao.ReviewVoteDao;
import com.cartmatic.estore.common.model.catalog.ReviewVote;
import com.cartmatic.estore.core.dao.impl.HibernateGenericDaoImpl;

/**
 * Dao implementation for ReviewVote.
*/
public class ReviewVoteDaoImpl extends HibernateGenericDaoImpl<ReviewVote> implements ReviewVoteDao {

	public boolean voteAlready(Integer productReviewId, Integer customerId) {
		String hql="from ReviewVote rv where rv.productReview.productReviewId=? and rv.appUser.appuserId=?";
		long count=countByHql(hql, new Object[]{productReviewId,customerId});
		return count>0;
	}

}
