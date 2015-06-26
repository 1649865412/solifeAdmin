package com.cartmatic.estore.system.dao;

import java.util.List;

import com.cartmatic.estore.common.model.system.RegionItem;
import com.cartmatic.estore.core.dao.GenericDao;
/**
 * Dao interface for RegionItem.
 */
public interface RegionItemDao extends GenericDao<RegionItem> {
	List<RegionItem> findByRegionId(List<Integer> regionIds,List<Integer> itemsIds);
	
	/**
	 * 根据区域Id查找子区域RegionItem
	 * @param regionId
	 * @return
	 */
	public List<RegionItem> getRegionItemsByRegionId(Integer regionId);
	
	/**
	 * 检查RegionItem是否已经存在
	 * @param regionId
	 * @param itemId
	 * @return
	 */
	public boolean isExistRegionItemForRegionAndItem(Integer regionId,Integer itemId);
}