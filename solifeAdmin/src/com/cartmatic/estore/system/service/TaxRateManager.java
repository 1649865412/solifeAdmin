package com.cartmatic.estore.system.service;

import java.util.List;

import com.cartmatic.estore.common.model.system.TaxRate;
import com.cartmatic.estore.core.service.GenericManager;

/**
 * Manager interface for TaxRate, responsible for business processing, and communicate between web and persistence layer.
 *
 */
public interface TaxRateManager extends GenericManager<TaxRate> {
	List<TaxRate> findByRegionIds(List<Integer> regionIdList,Integer productTypeId);
	
	public List<TaxRate> findByTaxProductTypeRegion(Integer taxId,Integer productTypeId,Integer regionId);
}
