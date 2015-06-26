package com.cartmatic.estore.system.dao;

import java.util.List;

import com.cartmatic.estore.common.model.system.TaxRate;
import com.cartmatic.estore.core.dao.GenericDao;
/**
 * Dao interface for TaxRate.
 */
public interface TaxRateDao extends GenericDao<TaxRate> {
	public List<TaxRate> findByRegionIds(List<Integer> regionIdList,Integer productTypeId);
	
	public List<TaxRate> findByTaxProductTypeRegion(Integer taxId,Integer productTypeId,Integer regionId);
}