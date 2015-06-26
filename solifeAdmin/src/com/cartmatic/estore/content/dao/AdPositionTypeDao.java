package com.cartmatic.estore.content.dao;

import com.cartmatic.estore.common.model.content.AdPositionType;
import com.cartmatic.estore.core.dao.GenericDao;
/**
 * Dao interface for AdPositionType.
 */
public interface AdPositionTypeDao extends GenericDao<AdPositionType> {
	public AdPositionType getAdPositionTypeByName(Integer storeId,String positionName);
}