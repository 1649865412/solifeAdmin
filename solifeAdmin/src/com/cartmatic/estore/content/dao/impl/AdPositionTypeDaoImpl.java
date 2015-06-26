package com.cartmatic.estore.content.dao.impl;

import com.cartmatic.estore.content.dao.AdPositionTypeDao;
import com.cartmatic.estore.common.model.content.AdPositionType;
import com.cartmatic.estore.core.dao.impl.HibernateGenericDaoImpl;

/**
 * Dao implementation for AdPositionType.
*/
public class AdPositionTypeDaoImpl extends HibernateGenericDaoImpl<AdPositionType> implements AdPositionTypeDao {

	@Override
	public AdPositionType getAdPositionTypeByName(Integer storeId, String positionName) {
		return (AdPositionType) findUnique("from AdPositionType apt where apt.store.storeId=? and apt.positionName=?", storeId,positionName);
	}

}
