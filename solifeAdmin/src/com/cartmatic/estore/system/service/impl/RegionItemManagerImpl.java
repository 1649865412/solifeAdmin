package com.cartmatic.estore.system.service.impl;

import java.util.List;

import com.cartmatic.estore.common.model.system.RegionItem;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;
import com.cartmatic.estore.system.service.RegionItemManager;
import com.cartmatic.estore.system.dao.RegionItemDao;


/**
 * Manager implementation for RegionItem, responsible for business processing, and communicate between web and persistence layer.
 */
public class RegionItemManagerImpl extends GenericManagerImpl<RegionItem> implements RegionItemManager {

	private RegionItemDao regionItemDao = null;

	/**
	 * @param regionItemDao
	 *            the regionItemDao to set
	 */
	public void setRegionItemDao(RegionItemDao regionItemDao) {
		this.regionItemDao = regionItemDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		dao = regionItemDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete()
	 */
	@Override
	protected void onDelete(RegionItem entity) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave()
	 */
	@Override
	protected void onSave(RegionItem entity) {

	}

	@Override
	public boolean isExistRegionItemForRegionAndItem(Integer regionId,Integer itemId) {
		return regionItemDao.isExistRegionItemForRegionAndItem(regionId, itemId);
	}

	@Override
	public List<RegionItem> getRegionItemsByRegionId(Integer regionId) {
		return regionItemDao.getRegionItemsByRegionId(regionId);
	}

	@Override
	public List<RegionItem> findByRegionId(List<Integer> regionIds,List<Integer> itemsIds) {
		return regionItemDao.findByRegionId(regionIds, itemsIds);
	}

}
