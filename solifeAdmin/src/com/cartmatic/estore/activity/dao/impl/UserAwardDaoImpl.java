package com.cartmatic.estore.activity.dao.impl;

import com.cartmatic.estore.activity.dao.UserAwardDao;
import com.cartmatic.estore.common.model.activity.UserAward;
import com.cartmatic.estore.core.dao.impl.HibernateGenericDaoImpl;

public class UserAwardDaoImpl extends HibernateGenericDaoImpl<UserAward> implements UserAwardDao
{
	
	public UserAward getUserAward(Integer userId) {
		// TODO Auto-generated method stub
		String hql = "from UserAward a where a.customer.appuserId = ?";
		Object ob = super.findUnique(hql, userId);
		return (UserAward)ob;
	}

}
