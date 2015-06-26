package com.cartmatic.estore.content.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cartmatic.estore.common.helper.ConfigUtil;
import com.cartmatic.estore.common.model.content.AdPositionType;
import com.cartmatic.estore.common.model.system.Store;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;
import com.cartmatic.estore.content.service.AdPositionTypeManager;
import com.cartmatic.estore.content.dao.AdPositionTypeDao;
import com.cartmatic.estore.webapp.util.RequestContext;


/**
 * Manager implementation for AdPositionType, responsible for business processing, and communicate between web and persistence layer.
 */
public class AdPositionTypeManagerImpl extends GenericManagerImpl<AdPositionType> implements AdPositionTypeManager {

	private AdPositionTypeDao adPositionTypeDao = null;

	/**
	 * @param adPositionTypeDao
	 *            the adPositionTypeDao to set
	 */
	public void setAdPositionTypeDao(AdPositionTypeDao adPositionTypeDao) {
		this.adPositionTypeDao = adPositionTypeDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		dao = adPositionTypeDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete()
	 */
	@Override
	protected void onDelete(AdPositionType entity) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave()
	 */
	@Override
	protected void onSave(AdPositionType entity) {

	}

	@Override
	public AdPositionType getAdPositionTypeByName(Integer storeId,String positionName) {
		return adPositionTypeDao.findUniqueByProperty("positionName", positionName);
	}

	@Override
	public Map<String, AdPositionType> getAllsForFront(String storeCode) {
		Map<String, AdPositionType> result = new HashMap<String, AdPositionType>();
		Store store = ConfigUtil.getInstance().getStore(storeCode);
		List<AdPositionType> adPositionTypeList = this.findByProperty("store", store);
		for (AdPositionType adPositionType : adPositionTypeList) {
			adPositionTypeDao.evict(adPositionType);
			result.put(adPositionType.getPositionName(),adPositionType);
		}
		logger.debug("no cache......AdPositionType");
		return result;
	}

}
