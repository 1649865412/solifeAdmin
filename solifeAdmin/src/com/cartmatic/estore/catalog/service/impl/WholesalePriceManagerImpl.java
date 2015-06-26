package com.cartmatic.estore.catalog.service.impl;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.cartmatic.estore.catalog.dao.WholesalePriceDao;
import com.cartmatic.estore.catalog.service.WholesalePriceManager;
import com.cartmatic.estore.common.model.catalog.WholesalePrice;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;


/**
 * Manager implementation for WholesalePrice, responsible for business processing, and communicate between web and persistence layer.
 */
public class WholesalePriceManagerImpl extends GenericManagerImpl<WholesalePrice> implements WholesalePriceManager {

	private WholesalePriceDao wholesalePriceDao = null;

	/**
	 * @param wholesalePriceDao
	 *            the wholesalePriceDao to set
	 */
	public void setWholesalePriceDao(WholesalePriceDao wholesalePriceDao) {
		this.wholesalePriceDao = wholesalePriceDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		dao = wholesalePriceDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete(java.lang.Object)
	 */
	@Override
	protected void onDelete(WholesalePrice entity) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave(java.lang.Object)
	 */
	@Override
	protected void onSave(WholesalePrice entity) {

	}

	public void saveWholesalePrices(Integer productSkuId,String... s_wholesalePrices) {
		if(s_wholesalePrices==null){
			s_wholesalePrices=new String[]{};
		}
		Map<Integer, BigDecimal>wholesalePriceMap=new LinkedHashMap<Integer, BigDecimal>();
		for (String s_wholesalePrice : s_wholesalePrices) {
			String temp_s_wholesalePrice[]=s_wholesalePrice.split("-");
			Integer minQuanatity=null;
			BigDecimal price=null;
			try {
				minQuanatity=Integer.valueOf(temp_s_wholesalePrice[0]);
				price=new BigDecimal(temp_s_wholesalePrice[1]);
			} catch (Exception e) {
				continue;
			}
			wholesalePriceMap.put(minQuanatity, price);
		}
		Set<Integer>wholesalePriceMap_keySet=wholesalePriceMap.keySet();
		
		//查找sku所有批发价
		List<WholesalePrice> wholesalePrices=findByProperty("productSku.productSkuId",productSkuId);
		
		//删除相应的批发价
		for (WholesalePrice wholesalePrice : wholesalePrices) {
			if(!wholesalePriceMap_keySet.contains(wholesalePrice.getMinQuantity().intValue())){
				delete(wholesalePrice);
			} 
		}
		//增加/更新批发价
		for (Integer wholesalePriceMap_Quanatity : wholesalePriceMap_keySet) {
			WholesalePrice tempWholesalePrice=null;
			//以批发数量决定是否更新还是新增
			for (WholesalePrice wholesalePrice : wholesalePrices) {
				if(wholesalePrice.getMinQuantity().intValue()==wholesalePriceMap_Quanatity.intValue()){
					tempWholesalePrice=wholesalePrice;
					break;
				}
			}
			if(tempWholesalePrice==null){
				tempWholesalePrice=new WholesalePrice();
				tempWholesalePrice.setMinQuantity(wholesalePriceMap_Quanatity);
				tempWholesalePrice.setProductSkuId(productSkuId);
			}
			tempWholesalePrice.setPrice(wholesalePriceMap.get(wholesalePriceMap_Quanatity));
			save(tempWholesalePrice);
		}
	}
	
	public WholesalePrice getSalePriceBySkuIdByMinQuantity(Integer productSkuId,Integer minQuantity){
		return wholesalePriceDao.getSalePriceBySkuIdByMinQuantity(productSkuId,minQuantity);
	}
}
