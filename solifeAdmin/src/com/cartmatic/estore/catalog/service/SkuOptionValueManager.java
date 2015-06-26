package com.cartmatic.estore.catalog.service;

import java.util.List;

import com.cartmatic.estore.common.model.catalog.SkuOptionValue;
import com.cartmatic.estore.core.service.GenericManager;

/**
 * Manager interface for SkuOptionValue, responsible for business processing, and communicate between web and persistence layer.
 *
 */
public interface SkuOptionValueManager extends GenericManager<SkuOptionValue> {
	/**
	 * 根据option Name和option value ，查找SkuOptionValue
	 * @param optionName
	 * @param valueName
	 * @return
	 */
	public List<SkuOptionValue> findSkuOptionValueByOptionNameAndValueName(String optionName,String valueName);
	
	/**
	 * 根据option Code和option value ，查找SkuOptionValue
	 * @param optionCode
	 * @param valueName
	 * @return
	 */
	public List<SkuOptionValue> findSkuOptionValueByOptionCodeAndValueName(String optionCode,String valueName);
}
