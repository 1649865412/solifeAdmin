package com.cartmatic.estore.activity.service.impl;

import com.cartmatic.estore.activity.dao.AwardDao;
import com.cartmatic.estore.activity.service.AwardService;
import com.cartmatic.estore.common.model.activity.Award;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;

public class AwardServiceImpl extends GenericManagerImpl<Award> implements AwardService
{
	private AwardDao awardDao;

	@Override
	protected void initManager() {
		// TODO Auto-generated method stub
		dao = awardDao;
	}

	@Override
	protected void onDelete(Award entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onSave(Award entity) {
		// TODO Auto-generated method stub
		
	}

	public AwardDao getAwardDao() {
		return awardDao;
	}

	public void setAwardDao(AwardDao awardDao) {
		this.awardDao = awardDao;
	}

}
