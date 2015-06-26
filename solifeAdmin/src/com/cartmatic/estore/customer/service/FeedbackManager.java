package com.cartmatic.estore.customer.service;

import java.util.List;

import com.cartmatic.estore.common.model.customer.Feedback;
import com.cartmatic.estore.core.service.GenericManager;

/**
 * Manager interface for Feedback, responsible for business processing, and communicate between web and persistence layer.
 *
 */
public interface FeedbackManager extends GenericManager<Feedback> {
	public List<Feedback> getLatestFeedbacks(Integer maxCount);
	/**
	 * get all thread feedbacks of a customer
	 * @return List
	 */
	
	public List<Feedback> getAllReplysByThreadId(Integer threadId);
}
