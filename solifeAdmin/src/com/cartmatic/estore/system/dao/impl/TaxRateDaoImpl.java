package com.cartmatic.estore.system.dao.impl;

import java.util.List;

import com.cartmatic.estore.system.dao.TaxRateDao;
import com.cartmatic.estore.common.model.system.TaxRate;
import com.cartmatic.estore.core.dao.impl.HibernateGenericDaoImpl;

/**
 * Dao implementation for TaxRate.
*/
public class TaxRateDaoImpl extends HibernateGenericDaoImpl<TaxRate> implements TaxRateDao {

	@Override
	public List<TaxRate> findByRegionIds(List<Integer> regionIdList,Integer productTypeId) {
		StringBuffer sql=new StringBuffer("from TaxRate t");
		if(regionIdList.size()>0){
			StringBuffer sb=new StringBuffer();
			for (Integer regionId : regionIdList) {
				sb.append(",").append(regionId);
			}
			sb.setCharAt(0, '(');
			sb.append(')');
			sql.append(" where t.productType.productTypeId="+productTypeId);
			sql.append(" and (t.region.regionId in ").append(sb).append(" or t.region.regionId in (select ri.region.regionId from RegionItem ri where ri.item.regionId in ").append(sb).append("))");
		}
		return findByHql(sql.toString());
	}

	@Override
	public List<TaxRate> findByTaxProductTypeRegion(Integer taxId,Integer productTypeId, Integer regionId) {
		return findByHql("from TaxRate t where t.tax.taxId=? and t.productType.productTypeId=? and t.region.regionId=?",new Object[]{taxId,productTypeId,regionId});
	}

}
