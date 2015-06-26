package com.cartmatic.estore.activity.service.impl;

import com.cartmatic.estore.activity.dao.UserAwardDao;
import com.cartmatic.estore.activity.service.UserAwardService;
import com.cartmatic.estore.common.model.activity.UserAward;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;

public class UserAwardServiceImpl extends GenericManagerImpl<UserAward> implements UserAwardService
{
	private UserAwardDao userAwardDao;
	
	public UserAward getUserAward(Integer userId) {
		return this.userAwardDao.getUserAward(userId);
	}

	@Override
	protected void initManager() {
		// TODO Auto-generated method stub
		dao = userAwardDao;
	}

	@Override
	protected void onDelete(UserAward entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onSave(UserAward entity) {
		// TODO Auto-generated method stub
		
	}

	public UserAwardDao getUserAwardDao() {
		return userAwardDao;
	}

	public void setUserAwardDao(UserAwardDao userAwardDao) {
		this.userAwardDao = userAwardDao;
	}


}
