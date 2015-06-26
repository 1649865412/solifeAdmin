package com.cartmatic.estore.system.dao.impl;

import java.util.List;

import com.cartmatic.estore.system.dao.RegionItemDao;
import com.cartmatic.estore.common.model.system.RegionItem;
import com.cartmatic.estore.core.dao.impl.HibernateGenericDaoImpl;

/**
 * Dao implementation for RegionItem.
*/
public class RegionItemDaoImpl extends HibernateGenericDaoImpl<RegionItem> implements RegionItemDao {

	@Override
	public boolean isExistRegionItemForRegionAndItem(Integer regionId,Integer itemId) {
		String hql="from RegionItem ri where ri.region.regionId=? and ri.item.regionId=?";
		Long count=countByHql( hql, new Object[]{regionId,itemId});
		return count>0;
	}

	@Override
	public List<RegionItem> getRegionItemsByRegionId(Integer regionId) {
		String hql="from RegionItem ri where ri.region.regionId=? order by ri.item.regionName";
		List<RegionItem>regionItemList=findByHql(hql,regionId);
		return regionItemList;
	}

	@Override
	public List<RegionItem> findByRegionId(List<Integer> regionIds,List<Integer> itemsIds) {
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<regionIds.size();i++){
			sb.append(",").append(regionIds.get(i));
		}
		sb.setCharAt(0, '(');
		sb.append(')');
		String hql=" from RegionItem ri where ri.region.regionId in "+sb;
		if(itemsIds.size()>0){
			sb=new StringBuffer();
			for(int i=0;i<itemsIds.size();i++){
				sb.append(",").append(itemsIds.get(i));
			}
			sb.setCharAt(0, '(');
			sb.append(')');
			hql=hql+" and ri.item.regionId in "+sb;
		}
		List<RegionItem> results=findByHql(hql);
		return results;
	}
}
