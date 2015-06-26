package com.cartmatic.estore.activity.dao;

import com.cartmatic.estore.common.model.activity.UserAward;
import com.cartmatic.estore.common.model.attribute.Attribute;
import com.cartmatic.estore.core.dao.GenericDao;

public interface UserAwardDao extends GenericDao<UserAward>
{

	public UserAward getUserAward(Integer userId);
	
}
