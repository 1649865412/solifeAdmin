package com.cartmatic.estore.system.service.impl;

import java.util.List;
import java.util.Set;

import com.cartmatic.estore.common.model.system.Region;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;
import com.cartmatic.estore.system.dao.RegionDao;
import com.cartmatic.estore.system.service.RegionManager;


/**
 * Manager implementation for Region, responsible for business processing, and communicate between web and persistence layer.
 */
public class RegionManagerImpl extends GenericManagerImpl<Region> implements RegionManager {

	private RegionDao regionDao = null;

	/**
	 * @param regionDao
	 *            the regionDao to set
	 */
	public void setRegionDao(RegionDao regionDao) {
		this.regionDao = regionDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		dao = regionDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete()
	 */
	@Override
	protected void onDelete(Region entity) {
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave()
	 */
	@Override
	protected void onSave(Region entity) {
	
	}

	@Override
	public Region[] findMatchRegions(String countryName, String stateName,
			String cityName) {
		return regionDao.findMatchRegions(countryName, stateName, cityName);
	}

	@Override
	public List<Region> getAllRegion() {
		return regionDao.getAllOrdered("regionName",true);
	}

	@Override
	public List<Region> getRegionByParentId(Integer id, Integer regionType) {
		return regionDao.getRegionByParentId(id, regionType);
	}

	@Override
	public List<Region> getActiveRegionByType(Integer type) {
		return regionDao.getActiveRegionByType(type);
	}

	@Override
	public List<Region> getRegionIgnoreType(Integer... types) {
		return regionDao.getRegionIgnoreType(types);
	}

	@Override
	public Region getRegionByCode(String code) {
		return regionDao.findUniqueByProperty("regionCode", code);
	}

	@Override
	public List<Region> getSubRegion(Integer regionId) {
		return regionDao.findByProperty("parentRegionId", regionId);
	}
	

	@Override
	public List<Region> findRegionByIds(Set<Integer> regionIdSet) {
		return regionDao.findRegionByIds(regionIdSet);
	}

	@Override
	public List<Region> getChildRegions(Integer parentRegionId, boolean isRoot,String regionType) {
		return regionDao.getChildRegions(parentRegionId, isRoot, regionType);
	}

	@Override
	public boolean haveChildRegion(Integer parentRegionId) {
		return regionDao.haveChildRegion(parentRegionId);
	}
	
	public Region getCountryByName(String name)
	{
		return regionDao.getCountryByName(name);
	}

/*	@Override
	public List<Region> getRegionByType(Integer type) {
		return regionDao.findByPropertyOrdered("regionType", type, "regionName", true);
	}*/

}
