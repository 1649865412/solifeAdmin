package com.cartmatic.estore.system.dao;

import java.util.List;

import com.cartmatic.estore.common.model.system.ShippingRate;
import com.cartmatic.estore.core.dao.GenericDao;
/**
 * Dao interface for ShippingRate.
 */
public interface ShippingRateDao extends GenericDao<ShippingRate> {
	
	List<ShippingRate> findShippingRatesOrderByShippingMethod(Integer countryId,Integer stated, Integer cityId);
	
	//查找所有运输
	List<ShippingRate> findAllShippingRate();
	
}