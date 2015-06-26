package com.cartmatic.estore.system.service;

import java.math.BigDecimal;
import java.util.List;

import com.cartmatic.estore.common.model.system.ShippingRate;
import com.cartmatic.estore.core.service.GenericManager;

/**
 * Manager interface for ShippingRate, responsible for business processing, and communicate between web and persistence layer.
 *
 */
public interface ShippingRateManager extends GenericManager<ShippingRate> {
	/**
	 * m1 华南
	 * m1 广州
	 * m1 中国
	 * m2 广州
	 * m2 广东
	 * 条件：广州--->返回：m1 广州,m2 广州
	 * 条件：广东--->返回：m1 华南,m2 广东
	 * 条件：中国--->返回：m1 中国
	 * @param countryId
	 * @param stated
	 * @param cityId
	 * @return
	 */
	public List<ShippingRate> findShippingRateByRegionIds(Integer countryId,Integer statedId, Integer cityId);
	
	/**
	 * 根据 国家 省 城市名字 返回完成匹配的ShippingRate列表
	 * 
	 * @param countryName 国家名字
	 * @param stateName 省名字
	 * @param cityName 城市名字
	 * @return
	 */
	public List<ShippingRate> findShippingRateByRegionNames(String countryName, String stateName, String cityName);
	
	public BigDecimal getShippingExpence(Integer shippingRateId,BigDecimal weight, Integer itemCount);
	
	//查找所有运输
	public List<ShippingRate> findAllShippingRate();
}
