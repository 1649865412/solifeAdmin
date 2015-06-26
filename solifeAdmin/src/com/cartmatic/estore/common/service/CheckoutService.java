package com.cartmatic.estore.common.service;

import java.math.BigDecimal;
import java.util.List;

import com.cartmatic.estore.common.model.cart.Shoppingcart;
import com.cartmatic.estore.common.model.sales.GiftCertificate;
import com.cartmatic.estore.common.model.system.ShippingRate;
import com.cartmatic.estore.common.model.system.Wrap;

public interface CheckoutService {
	/**
	 * 根据提供的国家、省份、城市的region表中的id，返回所有满足条件的运输方式
	 * @param countryId 国家id
	 * @param stated 省id
	 * @param cityId 城市id
	 * @return
	 */
	public List<ShippingRate> getRegionShippingRates(Integer countryId, Integer stated, Integer cityId);
	
	public ShippingRate getShippingRateById(Integer id);
	
	/**
	 * 根据费率的id和商品重量，返回运输费用
	 * @param Integer 费率id(ShippingRate.shippingRateId)
	 * @param weight 商品重量
	 * @param itemCount 商品的数量
	 * @return
	 */
	public BigDecimal getShippingExpence(Integer shippingRateId,BigDecimal weight, Integer itemCount);
	
	
	/**
	 * 查询所有当前系统定义有的包装方式
	 * @return
	 */
	public List<Wrap> getSystemWraps();
	
	public Wrap getWrapById(Integer wrapId);
	
	public void caculateTaxes(Shoppingcart shoppingcart,Integer countryId,Integer stated, Integer cityId);
	
	public GiftCertificate getGiftCertificateByNo(String no);
}
