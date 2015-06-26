package com.cartmatic.estore.activity.service;

import com.cartmatic.estore.common.model.activity.UserAward;
import com.cartmatic.estore.core.service.GenericManager;

public interface UserAwardService extends GenericManager<UserAward>
{
	
	public UserAward getUserAward(Integer userId);

}
