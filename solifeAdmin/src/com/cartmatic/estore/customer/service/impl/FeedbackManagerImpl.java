package com.cartmatic.estore.customer.service.impl;

import java.util.List;

import com.cartmatic.estore.common.model.customer.Feedback;
import com.cartmatic.estore.core.model.PagingBean;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;
import com.cartmatic.estore.customer.dao.FeedbackDao;
import com.cartmatic.estore.customer.service.FeedbackManager;


/**
 * Manager implementation for Feedback, responsible for business processing, and communicate between web and persistence layer.
 */
public class FeedbackManagerImpl extends GenericManagerImpl<Feedback> implements FeedbackManager {

	private FeedbackDao feedbackDao = null;

	/**
	 * @param feedbackDao
	 *            the feedbackDao to set
	 */
	public void setFeedbackDao(FeedbackDao feedbackDao) {
		this.feedbackDao = feedbackDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		dao = feedbackDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete()
	 */
	@Override
	protected void onDelete(Feedback entity) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave()
	 */
	@Override
	protected void onSave(Feedback entity) {

	}

	public List<Feedback> getLatestFeedbacks(Integer maxCount) {
		return feedbackDao.getLatestFeedbacks(maxCount);
	}

	public List<Feedback> getAllReplysByThreadId(Integer threadId) {
		return feedbackDao.getAllReplysByThreadId(threadId);
	}

}
