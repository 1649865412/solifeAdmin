package com.cartmatic.estore.customer.dao.impl;

import java.util.List;

import com.cartmatic.estore.common.model.customer.Feedback;
import com.cartmatic.estore.core.dao.impl.HibernateGenericDaoImpl;
import com.cartmatic.estore.core.model.PagingBean;
import com.cartmatic.estore.customer.dao.FeedbackDao;

/**
 * Dao implementation for Feedback.
*/
public class FeedbackDaoImpl extends HibernateGenericDaoImpl<Feedback> implements FeedbackDao {

	@SuppressWarnings({ "unchecked", "null" })
	public List<Feedback> getLatestFeedbacks(Integer maxCount) {
		String query=" from Feedback order by createTime desc";
		if(maxCount==null&&maxCount<1)
			maxCount=10;
		List<Feedback> feedbacklist=find(query, 0, maxCount);
		return feedbacklist;
	}
	
	public List<Feedback> getAllReplysByThreadId(Integer threadId) {
		return findByHql("from Feedback where threadId=? order by createTime asc",threadId);
	}

}
