package com.cartmatic.estore.catalog.service.impl;

import java.util.List;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.catalog.dao.SkuOptionDao;
import com.cartmatic.estore.catalog.service.ProductSkuManager;
import com.cartmatic.estore.catalog.service.SkuOptionManager;
import com.cartmatic.estore.common.model.catalog.SkuOption;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;


/**
 * Manager implementation for SkuOption, responsible for business processing, and communicate between web and persistence layer.
 */
public class SkuOptionManagerImpl extends GenericManagerImpl<SkuOption> implements SkuOptionManager {
	ProductSkuManager productSkuManager=null;

	private SkuOptionDao skuOptionDao = null;

	/**
	 * @param skuOptionDao
	 *            the skuOptionDao to set
	 */
	public void setSkuOptionDao(SkuOptionDao skuOptionDao) {
		this.skuOptionDao = skuOptionDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		dao = skuOptionDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete(java.lang.Object)
	 */
	@Override
	protected void onDelete(SkuOption entity) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave(java.lang.Object)
	 */
	@Override
	protected void onSave(SkuOption entity) {

	}


	public List<SkuOption> findSkuOptionsByProductType(Integer productTypeId) {
		return skuOptionDao.findSkuOptionsByProductType(productTypeId);
	}

	public List<SkuOption> findActiveSkuOptionsByProductType(
			Integer productTypeId) {
		return skuOptionDao.findActiveSkuOptionsByProductType(productTypeId);
	}

	public void setProductSkuManager(ProductSkuManager productSkuManager) {
		this.productSkuManager = productSkuManager;
	}

	public List<SkuOption> findActiveSkuOptionsExcludeRefProductType(Integer productTypeId) {
		List<SkuOption> allActiveSkuOptions=skuOptionDao.findByProperty("status", Constants.STATUS_ACTIVE);
		List<SkuOption> productTypeActiveSkuOptions=findActiveSkuOptionsByProductType(productTypeId);
		//清除与该产品类型关联的Sku属性选项
		for (SkuOption skuOption : productTypeActiveSkuOptions) {
			if(allActiveSkuOptions.contains(skuOption)){
				allActiveSkuOptions.remove(skuOption);
			}
		}
		return allActiveSkuOptions;
	}

	public SkuOption getSkuOptionByCode(String skuOptionCode) {
		SkuOption skuOption=dao.findUniqueByProperty("skuOptionCode",skuOptionCode);
		return skuOption;
	}

	

}
