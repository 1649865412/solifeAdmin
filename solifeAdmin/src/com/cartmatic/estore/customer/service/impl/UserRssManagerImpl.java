package com.cartmatic.estore.customer.service.impl;

import com.cartmatic.estore.common.model.customer.UserRss;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;
import com.cartmatic.estore.customer.dao.UserRssDao;
import com.cartmatic.estore.customer.service.UserRssManager;


/**
 * Manager implementation for Favorite, responsible for business processing, and communicate between web and persistence layer.
 */
public class UserRssManagerImpl extends GenericManagerImpl<UserRss> implements UserRssManager {

	private UserRssDao userRssDao = null;
	

	public UserRssDao getUserRssDao() {
		return userRssDao;
	}

	public void setUserRssDao(UserRssDao userRssDao) {
		this.userRssDao = userRssDao;
	}

	@Override
	protected void initManager() {
		// TODO Auto-generated method stub
		dao = userRssDao;
	}

	@Override
	protected void onDelete(UserRss entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onSave(UserRss entity) {
		// TODO Auto-generated method stub
		
	}
}
