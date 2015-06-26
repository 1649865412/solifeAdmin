package com.cartmatic.estore.content.dao.impl;

import java.util.Date;
import java.util.List;

import com.cartmatic.estore.content.dao.AdvertisementDao;
import com.cartmatic.estore.common.model.content.Advertisement;
import com.cartmatic.estore.core.dao.impl.HibernateGenericDaoImpl;

/**
 * Dao implementation for Advertisement.
*/
public class AdvertisementDaoImpl extends HibernateGenericDaoImpl<Advertisement> implements AdvertisementDao {

	@Override
	public List<Advertisement> getAllsForFront(Integer positionId) {
		String hql="from Advertisement a where a.adPositionType.adPositionTypeId=? and a.endPublishTime >=? and a.startPublishTime <= ? order by sortOrder";
		return findByHql(hql, new Object[]{positionId,new Date(),new Date()});
	}

}
