package com.cartmatic.estore.catalog.service;

import com.cartmatic.estore.common.model.catalog.WholesalePrice;
import com.cartmatic.estore.core.service.GenericManager;

/**
 * Manager interface for WholesalePrice, responsible for business processing, and communicate between web and persistence layer.
 *
 */
public interface WholesalePriceManager extends GenericManager<WholesalePrice> {

	/**
	 * 更新sku所有的批发价
	 * @param productSkuId
	 * @param s_wholesalePrices [2-100,5-80]批发价以“批发数量-售价”组合
	 */
	public void saveWholesalePrices(Integer productSkuId,String... s_wholesalePrices);
	/**hqm
	 * 根据sku id和最少批发数量查找相应的批发价实体
	 * @param productSkuId
	 * @param minQuantity
	 */
	public WholesalePrice getSalePriceBySkuIdByMinQuantity(Integer productSkuId,Integer minQuantity);
}
 