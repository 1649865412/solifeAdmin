package com.cartmatic.estore.system.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.cartmatic.estore.common.helper.ConfigUtil;
import com.cartmatic.estore.common.model.system.Region;
import com.cartmatic.estore.common.model.system.RegionItem;
import com.cartmatic.estore.common.model.system.ShippingRate;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;
import com.cartmatic.estore.system.service.RegionItemManager;
import com.cartmatic.estore.system.service.RegionManager;
import com.cartmatic.estore.system.service.ShippingRateManager;
import com.cartmatic.estore.system.dao.ShippingRateDao;


/**
 * Manager implementation for ShippingRate, responsible for business processing, and communicate between web and persistence layer.
 */
public class ShippingRateManagerImpl extends GenericManagerImpl<ShippingRate> implements ShippingRateManager {
	private ShippingRateDao shippingRateDao = null;
	private RegionItemManager regionItemManager=null;
	private RegionManager regionManager=null;
	
	public void setRegionManager(RegionManager regionManager) {
		this.regionManager = regionManager;
	}

	/**
	 * @param shippingRateDao
	 *            the shippingRateDao to set
	 */
	public void setShippingRateDao(ShippingRateDao shippingRateDao) {
		this.shippingRateDao = shippingRateDao;
	}

	public void setRegionItemManager(RegionItemManager regionItemManager) {
		this.regionItemManager = regionItemManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		dao = shippingRateDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete()
	 */
	@Override
	protected void onDelete(ShippingRate entity) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave()
	 */
	@Override
	protected void onSave(ShippingRate entity) {

	}

	@Override
	public List<ShippingRate> findShippingRateByRegionIds(Integer countryId,Integer stateId, Integer cityId) {
		List<ShippingRate> shippingRates = shippingRateDao.findShippingRatesOrderByShippingMethod(countryId, stateId, cityId);
		processShippingRate(shippingRates, countryId, stateId, cityId);
		return shippingRates;
	}

	private void processShippingRate(List<ShippingRate> shippingRates,Integer countryId, Integer stateId, Integer cityId) {
		Integer customRegionType=4;
		if(shippingRates.size()>0){
			ShippingRate preShippingRate=shippingRates.get(0);//前一个ShippingRate
			for (int i = 1; i < shippingRates.size(); i++) {
				ShippingRate currentShippingRate=shippingRates.get(i);
				if(currentShippingRate.getShippingMethodId().equals(preShippingRate.getShippingMethodId())){//getShippingMethodId()!=null
					if(preShippingRate.getRegionId().equals(cityId)){//取匹配城市
						shippingRates.remove(i--);
					}else if(currentShippingRate.getRegionId().equals(cityId)){//取匹配城市
						shippingRates.remove(i-1);i--;
					}else if(preShippingRate.getRegion().getRegionType().equals(customRegionType)||currentShippingRate.getRegion().getRegionType().equals(customRegionType)){//自定义区域
						List<Integer> regionIds=new ArrayList<Integer>();
						List<Integer> itemsIds=new ArrayList<Integer>();
						regionIds.add(preShippingRate.getRegionId());
						regionIds.add(currentShippingRate.getRegionId());
						if(cityId!=null) itemsIds.add(cityId);
						if(stateId!=null) itemsIds.add(stateId);
						boolean removeOne=false;//匹配到后则移除另一
						if(regionIds.size()>0){
							List<RegionItem> regionItems=regionItemManager.findByRegionId(regionIds, itemsIds);
							for(int cnt=0;cnt<2&&!removeOne&&regionItems.size()>0;cnt++){
								Integer toCompareRegonId=cnt==0?cityId:stateId;//先匹配城市再省
								if(toCompareRegonId==null) continue;
								for (RegionItem regionItem:regionItems) {
									if(toCompareRegonId.equals(regionItem.getItemId())){
										if(regionItem.getRegionId().equals(preShippingRate.getRegionId())){
											shippingRates.remove(i--);
											removeOne=true;
											break;
										}else if(regionItem.getRegionId().equals(currentShippingRate.getRegionId())){
											shippingRates.remove(i-1);i--;
											removeOne=true;
											break;
										}
									}
								}
							}
						}
						if(!removeOne) shippingRates.remove(i--);
					}else if(preShippingRate.getRegionId().equals(stateId)){//取匹配省
						shippingRates.remove(i--);
					}else if(currentShippingRate.getRegionId().equals(cityId)){//取匹配省
						shippingRates.remove(i-1);
					}else {
						shippingRates.remove(i--);
					}
				}
				preShippingRate=currentShippingRate;
			}
		}
		
	}

	@Override
	public List<ShippingRate> findShippingRateByRegionNames(String countryName,String stateName, String cityName) {
		List<Region> regionList=new ArrayList<Region>();//findByRegionName.........................
		List<Integer> regionIds=new ArrayList<Integer>();
		for (Region region : regionList) {
			regionIds.add(region.getRegionId());
		}
		Integer countryId=null;
		Integer stateId=null;
		Integer cityId=null;
		List<ShippingRate> shippingRates=new ArrayList<ShippingRate>();
		if(StringUtils.isNotBlank(countryName)){//国家非空，设置其ID
			Region[] regions=regionManager.findMatchRegions(countryName, stateName, cityName);
			if(regions[0]!=null){
				countryId=regions[0].getRegionId();
				if(regions[1]!=null){//省非空，设置其ID
					stateId=regions[1].getRegionId();
					if(regions[2]!=null){//城市非空，设置其ID
						cityId=regions[2].getRegionId();
					}
				}
				shippingRates = shippingRateDao.findShippingRatesOrderByShippingMethod(countryId,stateId,cityId);
				processShippingRate(shippingRates, countryId, stateId, cityId);
			}
		}
		return shippingRates;
	}
	
	/**
	 * 计算运费
	 */
	@Override
	public BigDecimal getShippingExpence(Integer shippingRateId,BigDecimal weight, Integer itemCount) {
		BigDecimal sum=new BigDecimal(0);
		if(shippingRateId!=null){
			ShippingRate shippingRate=getById(shippingRateId);
			if(shippingRate!=null)
			{
				if (2==shippingRate.getBaseOn()) //按件计算
				{
					sum = shippingRate.getItemPerRate().multiply(new BigDecimal(itemCount));
					sum = sum.setScale(2, BigDecimal.ROUND_HALF_UP);
				}
				else if (0 == shippingRate.getBaseOn())  //按重量计算
				{
					sum=shippingRate.getBasePrice();
					if(weight!=null&&shippingRate.getBaseWeight()!=null)
					{
						//单位增量或费率为空(0)时，没额外费用---只有基本费用getBasePrice()
						//计费公式：总运费 = 基本运费 + [（商品重量 - 起价重量）÷ 单位增量] × 费率
						if(shippingRate.getIncreaseUnit() != null
							&&shippingRate.getIncreaseUnit().compareTo(BigDecimal.ZERO) > 0
							&&shippingRate.getWeightPerRate() != null
							&&shippingRate.getWeightPerRate().compareTo(BigDecimal.ZERO) > 0)
						{
							String weightUnit = ConfigUtil.getInstance().getWeightUnit();
							BigDecimal newWeigth=weight;
							if(!weightUnit.equalsIgnoreCase(shippingRate.getMetricUnitCode()))
							{//商品重与快递计费单位不同时,转换商品重量
								if("g".equalsIgnoreCase(weightUnit)
										&&"kg".equalsIgnoreCase(shippingRate.getMetricUnitCode()))
								{
									newWeigth=newWeigth.divide(BigDecimal.valueOf(1000));
								}
								else if("kg".equalsIgnoreCase(weightUnit)
										&&"g".equalsIgnoreCase(shippingRate.getMetricUnitCode()))
								{
									newWeigth=newWeigth.multiply(BigDecimal.valueOf(1000));
								}
							}
							if(newWeigth.compareTo(shippingRate.getBaseWeight()) > 0)
							{//超重
								sum=sum.add(newWeigth.subtract(shippingRate.getBaseWeight()).divide(shippingRate.getIncreaseUnit(),0,BigDecimal.ROUND_CEILING).multiply(shippingRate.getWeightPerRate()));
							}
						}
					}
				}
			}
		}		
		return sum;
	}

	@Override
	public List<ShippingRate> findAllShippingRate() {
		// TODO Auto-generated method stub
		return this.shippingRateDao.findAllShippingRate();
	}

}
