package com.cartmatic.estore.customer.dao;

import java.util.List;

import com.cartmatic.estore.common.model.customer.Feedback;
import com.cartmatic.estore.core.dao.GenericDao;
/**
 * Dao interface for Feedback.
 */
public interface FeedbackDao extends GenericDao<Feedback> {
	public List<Feedback> getLatestFeedbacks(Integer maxCount);
	
	public List<Feedback> getAllReplysByThreadId(Integer threadId);
}