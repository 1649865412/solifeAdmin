package com.cartmatic.estore.system.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.cartmatic.estore.system.dao.ShippingRateDao;
import com.cartmatic.estore.common.model.system.ShippingRate;
import com.cartmatic.estore.core.dao.impl.HibernateGenericDaoImpl;

/**
 * Dao implementation for ShippingRate.
*/
public class ShippingRateDaoImpl extends HibernateGenericDaoImpl<ShippingRate> implements ShippingRateDao {

	@Override
	public List<ShippingRate> findShippingRatesOrderByShippingMethod(
			Integer countryId, Integer stated, Integer cityId) {
		List<Integer> regionIds=new ArrayList<Integer>();
		if(countryId!=null) regionIds.add(countryId);
		if(stated!=null) regionIds.add(stated);
		if(cityId!=null) regionIds.add(cityId);
		return this.findShippingRatesOrderByShippingMethod(regionIds);
	}

	private List<ShippingRate> findShippingRatesOrderByShippingMethod(List<Integer> regionIds) {
		if(regionIds.size()==0) return new ArrayList<ShippingRate>();
		StringBuffer sql=new StringBuffer("from ShippingRate s");
		StringBuffer sb=new StringBuffer();
		for (Integer regionId : regionIds) {
			sb.append(",").append(regionId);
		}
		if(sb.length()>0){
			sb.setCharAt(0, '(');
			sb.append(')');
			//sql.append(" where s.region.regionId in ").append(sb).append(" or s.region.regionId in (select ri.region.regionId from RegionItem ri where ri.itemId in ").append(sb).append(')');
			sql.append(" where (s.region.regionId in ").append(sb).append(" or s.region.regionId in (select ri.region.regionId from RegionItem ri where ri.item.regionId in ").append(sb).append("))");
			sql.append(" and s.deleted=0 and s.shippingMethod.status=1");
			sql.append(" and s.shippingMethod.deleted=0 and s.shippingMethod.carrier.deleted=0 and s.shippingMethod.carrier.status=1");
			sql.append(" order by s.shippingMethod.shippingMethodId,s.shippingRateId");
		}
		return findByHql(sql.toString());
	}

	@Override
	public List<ShippingRate> findAllShippingRate() {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer("from ShippingRate sr");
		return findByHql(sql.toString());
	}
	
	

}
