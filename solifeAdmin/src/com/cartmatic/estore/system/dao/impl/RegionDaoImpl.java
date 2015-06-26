package com.cartmatic.estore.system.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;

import com.cartmatic.estore.system.dao.RegionDao;
import com.cartmatic.estore.common.model.system.Region;
import com.cartmatic.estore.core.dao.impl.HibernateGenericDaoImpl;

/**
 * Dao implementation for Region.
*/
public class RegionDaoImpl extends HibernateGenericDaoImpl<Region> implements RegionDao {
	private final String REGION_TYPE_CUSTOM="custom";
	private final String REGION_TYPE_SYSTEM="system";
	
	@Override
	public Region[] findMatchRegions(String countryName, String stateName,String cityName) {
		Region[] regionArray=new Region[3];
		if(StringUtils.isNotBlank(countryName)&&StringUtils.isNotBlank(stateName)&&StringUtils.isNotBlank(cityName)){//三者非空
			String hql = "from Region r3 where r3.regionName=? and " +
					"r3.parentRegionId in (select r2.regionId from Region r2 where r2.regionName=? " +
					"and r2.parentRegionId in (select r1.regionId from Region r1 where r1.regionName =?))";
			//StringBuffer sql=new StringBuffer("from Region r3")
			//.append(" where r3.regionName = '").append(cityName).append("'")
			//.append(" and r3.parentRegionId in (").append("select r2.regionId from Region r2 where r2.regionName ="+"'"+stateName+"' and r2.parentRegionId in ("+"select r1.regionId from Region r1 where r1.regionName ='"+countryName+"'"+")").append(")");
			//List<Region> cities=super.find(sql.toString());
			List<Region> cities=findByHql(hql,new Object[]{cityName, stateName, countryName});
			if(cities.size()>0){
				regionArray[2]=cities.get(0);
				regionArray[1]=getById(regionArray[2].getParentRegionId());
				if(regionArray[1]!=null){
					regionArray[0]=getById(regionArray[1].getParentRegionId());
				}
			}
		}
		if(regionArray[2]==null&&StringUtils.isNotEmpty(countryName)&&StringUtils.isNotEmpty(stateName)){//二者非空
			String hql ="from com.cartmatic.estore.common.model.system.Region r2 where r2.regionName = ? and r2.parentRegionId in " +
					"(select r1.regionId from com.cartmatic.estore.common.model.system.Region r1 where r1.regionName =?)" ;
//			StringBuffer sql=new StringBuffer("from Region r2")
//			.append(" where r2.regionName = '").append(stateName).append("'")
//			.append(" and r2.parentRegionId in (").append("select r1.regionId from Region r1 where r1.regionName ="+"'").append(countryName).append("'").append(")");
			List<Region> states=findByHql(hql, new Object[]{stateName, countryName});
			if (states.size()>0) {
				regionArray[1]=states.get(0);
				regionArray[0]=getById(regionArray[1].getParentRegionId());
			}
		}
		if(regionArray[1]==null&&StringUtils.isNotEmpty(countryName)){//国家非空
			String hql = "from Region r1 where r1.regionName = ?";
//			StringBuffer sql=new StringBuffer("from Region r1")
//			.append(" where r1.regionName = '").append(countryName).append("'");
			List<Region> countries=findByHql(hql, new Object[]{countryName});
			if (countries.size()>0) {
				regionArray[0]=countries.get(0);
			}
		}
		return regionArray;
	}

	@Override
	public List<Region> getRegionByParentId(Integer id, Integer regionType) {
		String sql="from Region r where r.parentRegionId=? and r.regionType=? order by r.regionName";
		List<Region>regionList=findByHql(sql, new Object[]{id,regionType});
		return regionList;
	}

	@Override
	public List<Region> getActiveRegionByType(Integer type) {
		String sql="from Region r where r.regionType=? and status=? order by r.regionName";
		List<Region>regionList=findByHql(sql, new Object[]{type,1});
		return regionList;
	}

	@Override
	public List<Region> getRegionIgnoreType(Integer... types) {
		String sql="from Region r ";
		List<Integer> params=new ArrayList<Integer>();
		if(types!=null){
			for (int i = 0; i < types.length; i++) {
				Integer integer = types[i];
				if(i==0)
					sql+=" where r.regionType<>?";
				else
					sql+=" and r.regionType<>?";
				params.add(integer);
			}
		}
		sql+=" order by r.regionName";
		List<Region>regionList=findByHql(sql,params.toArray());
		return regionList;
	}


	@Override
	public List<Region> findRegionByIds(Set<Integer> regionIdSet) {
		String regionIds=regionIdSet.toString();
		String sql="from Region r where r.regionId in ("+regionIds.substring(regionIds.indexOf('[')+1,regionIds.toString().lastIndexOf(']'))+")";
		return findByHql(sql);
	}

	@Override
	public List<Region> getChildRegions(Integer parentRegionId, boolean isRoot,String regionType) {
		String hql="from Region r where r.parentRegionId="+parentRegionId+(isRoot?" and r.status=1":"");
		if(REGION_TYPE_SYSTEM.equalsIgnoreCase(regionType)){
			hql+=" and r.regionType!=4";
		}else if(REGION_TYPE_CUSTOM.equalsIgnoreCase(regionType)){
			hql="from Region r where"+(isRoot?" r.status=1":" r.parentRegionId="+parentRegionId)+" and r.regionType=4";
		}
		hql+=" order by r.regionName";
		List<Region> list=findByHql(hql);
		return list;
	}

	@Override
	public boolean haveChildRegion(Integer parentRegionId) {
		int childCount=((Long) findUnique("select count(*) from Region where parentRegionId=?" , new Object[]{parentRegionId})).intValue();
		return childCount>0;
	}
	
	public Region getCountryByName(String name)
	{
		String hql = "from Region r where r.regionName = ? and r.regionType=1";
		List<Region> list=findByHql(hql, name);
		if (list == null  || list.isEmpty())
			return null;
		return list.get(0);
	}

}
