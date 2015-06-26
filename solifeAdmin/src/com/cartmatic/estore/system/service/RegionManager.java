package com.cartmatic.estore.system.service;

import java.util.List;
import java.util.Set;

import com.cartmatic.estore.common.model.system.Region;
import com.cartmatic.estore.core.service.GenericManager;

/**
 * Manager interface for Region, responsible for business processing, and communicate between web and persistence layer.
 *
 */
public interface RegionManager extends GenericManager<Region> {
	/**
     * 根据国家名，省，城市名，返回相应的一组地区（长度为3的数组，依次为国家，省，城市）
     * @param countryName
     * @param stateName
     * @param cityName
     * @return
     */
	public Region[] findMatchRegions(String countryName,String stateName,String cityName);
	
	/**
	 * 查找所有区域
	 * @return
	 */
	public List<Region> getAllRegion();
	/**
	 * 查找指定父区域的所有子区域
	 * @param id
	 * @param regionType
	 * @return
	 */
	public List<Region> getRegionByParentId(Integer id, Integer regionType);
	
	/**
	 * 查找指定类型的所有激活区域
	 * @param type
	 * @return
	 */
	public List<Region> getActiveRegionByType(Integer type);
	
	/**
	 * 查找忽略指定类型的区域
	 * @param types
	 * @return
	 */
	public List<Region> getRegionIgnoreType(Integer...types);
	
	/**
	 * 根据code查找区域
	 * @param code
	 * @return
	 */
	public Region getRegionByCode(String code);
	
	public List<Region> getSubRegion(Integer regionId);
	
	/**
	 * 查找指定类型的所有区域
	 * @param type
	 * @return
	 */
//	public List<Region> getRegionByType(Integer type);
	
	public List<Region> findRegionByIds(Set<Integer> regionIdSet);
	
	public List<Region> getChildRegions(Integer parentRegionId,boolean isRoot,String regionType);
	
	public boolean haveChildRegion(Integer parentRegionId);
	
	public Region getCountryByName(String name);
}
