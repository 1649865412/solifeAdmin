package com.cartmatic.estore.content.dao;

import java.util.List;

import com.cartmatic.estore.common.model.content.Advertisement;
import com.cartmatic.estore.core.dao.GenericDao;
/**
 * Dao interface for Advertisement.
 */
public interface AdvertisementDao extends GenericDao<Advertisement> {
	public List<Advertisement> getAllsForFront(Integer positionId);
}