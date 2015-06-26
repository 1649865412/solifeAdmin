package com.cartmatic.estore.common.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.common.model.cart.Shoppingcart;
import com.cartmatic.estore.common.model.cart.ShoppingcartItem;
import com.cartmatic.estore.common.model.sales.GiftCertificate;
import com.cartmatic.estore.common.model.system.ShippingRate;
import com.cartmatic.estore.common.model.system.TaxRate;
import com.cartmatic.estore.common.model.system.Wrap;
import com.cartmatic.estore.common.service.CheckoutService;
import com.cartmatic.estore.sales.service.GiftCertificateManager;
import com.cartmatic.estore.system.service.ShippingRateManager;
import com.cartmatic.estore.system.service.TaxRateManager;
import com.cartmatic.estore.system.service.WrapManager;

public class CheckoutServiceImpl implements CheckoutService {
	private ShippingRateManager shippingRateManager;
	private TaxRateManager taxRateManager;
	private WrapManager wrapManager;
	private GiftCertificateManager certificateManager;
	private static final Log logger = LogFactory.getLog(CheckoutServiceImpl.class);
	public void setCertificateManager(GiftCertificateManager certificateManager) {
		this.certificateManager = certificateManager;
	}
	/**
	 * 根据提供的国家、省份、城市的region表中的id，返回所有满足条件的运输方式
	 * @param countryId
	 * @param stated
	 * @param cityId
	 * @return
	 */
	public List<ShippingRate> getRegionShippingRates(Integer countryId,Integer stated, Integer cityId) {
		List<ShippingRate> shippingRateList=shippingRateManager.findShippingRateByRegionIds(countryId, stated, cityId);
		//提前加载延迟对象
		for(ShippingRate s : shippingRateList){
			s.getShippingMethod().getShippingMethodName();
		}
		return shippingRateList;
	}
	/**
	 * 根据费率的id和商品重量，返回运输费用
	 * @param Integer 费率id(ShippingRate.shippingRateId)
	 * @param weight 商品重量
	 * @return
	 */
	public BigDecimal getShippingExpence(Integer shippingRateId,BigDecimal weight, Integer itemCount) {
		return shippingRateManager.getShippingExpence(shippingRateId, weight, itemCount);
	}
	
	/**
	 * 查询所有当前系统定义有的包装方式
	 * @return
	 */
	public List<Wrap> getSystemWraps() {
		return wrapManager.getWrapsAllDesc();
	}
	public void setShippingRateManager(ShippingRateManager shippingRateManager) {
		this.shippingRateManager = shippingRateManager;
	}
	public void setWrapManager(WrapManager wrapManager) {
		this.wrapManager = wrapManager;
	}
	public Wrap getWrapById(Integer wrapId) {
		return wrapManager.getById(wrapId);
	}
	
	public void caculateTaxes(Shoppingcart shoppingcart,Integer countryId,Integer stated, Integer cityId){
		List<Integer> regionIdList = new ArrayList<Integer>(3);
		if(countryId!=null) regionIdList.add(countryId);
		if(stated!=null) regionIdList.add(stated);
		if(cityId!=null) regionIdList.add(cityId);
		if(regionIdList.size()==0) {
			logger.error("there should be one not null value of countryId,stated,cityId!");
			//throw new Exception("there should be one not null value of countryId,stated,cityId!");
		}
		Set<ShoppingcartItem> shoppingCartItems=shoppingcart.getCartItems();
		if(shoppingCartItems.isEmpty()) {
			logger.info("shoppingcart is empty!");
			//throw new Exception("shoppingcart is empty!");
		}
		
		HashMap<Integer,List<TaxRate>> productTaxRateMap=new HashMap<Integer,List<TaxRate>>();;//{productTypeId,List<TaxRate>}
		for (Iterator iterator = shoppingCartItems.iterator(); iterator.hasNext();) {
			ShoppingcartItem shoppingcartItem = (ShoppingcartItem) iterator.next();
			if(!shoppingcartItem.getItemType().equals(Constants.ITEM_TYPE_PRODUCT))continue;
			Integer productTypeId=shoppingcartItem.getProductSku().getProduct().getProductTypeId();//可能有null pointer导常
			List<TaxRate> taxRateList=productTaxRateMap.get(productTypeId);
			if(taxRateList==null){
				taxRateList=taxRateManager.findByRegionIds(regionIdList, productTypeId);
				productTaxRateMap.put(productTypeId, taxRateList);
			}
			if(taxRateList.size()>0){
				BigDecimal tax=new BigDecimal(0);
				StringBuffer taxName=new StringBuffer();
				BigDecimal itemPrices=shoppingcartItem.getDiscountPrice().multiply(BigDecimal.valueOf(shoppingcartItem.getQuantity()));//shoppingcartItem折后价总和
				for (TaxRate taxRate : taxRateList) {//
					tax=tax.add(itemPrices.multiply(taxRate.getRateValue()).divide(BigDecimal.valueOf(100)));
					taxName.append(taxRate.getTax().getTaxName()).append(",");//多个税率间用逗号分隔
				}
				taxName.deleteCharAt(taxName.length()-1);
				shoppingcartItem.setTax(tax.setScale(2,BigDecimal.ROUND_HALF_UP));
				shoppingcartItem.setTaxName(taxName.toString());
			}
		}
	}
	public void setTaxRateManager(TaxRateManager taxRateManager) {
		this.taxRateManager = taxRateManager;
	}
	public ShippingRate getShippingRateById(Integer id) {
		return shippingRateManager.getById(id);
	}
	public GiftCertificate getGiftCertificateByNo(String no) {
		GiftCertificate g = certificateManager.getGiftCertificate(no);
		return g;
	}
}