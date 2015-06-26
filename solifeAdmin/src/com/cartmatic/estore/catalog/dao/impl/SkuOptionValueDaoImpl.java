package com.cartmatic.estore.catalog.dao.impl;

import java.util.List;

import com.cartmatic.estore.catalog.dao.SkuOptionValueDao;
import com.cartmatic.estore.common.model.catalog.SkuOptionValue;
import com.cartmatic.estore.core.dao.impl.HibernateGenericDaoImpl;

/**
 * Dao implementation for SkuOptionValue.
*/
public class SkuOptionValueDaoImpl extends HibernateGenericDaoImpl<SkuOptionValue> implements SkuOptionValueDao {

	@SuppressWarnings("unchecked")
	public List<SkuOptionValue> findSkuOptionValueByOptionCodeAndValueName(
			String optionCode, String valueName) {
		String hql="select sov from SkuOptionValue sov where sov.skuOption.skuOptionCode=? and sov.skuOptionValueName=?";
		List<SkuOptionValue>result =findByHql(hql, new Object[]{optionCode,valueName});
		return result;
	}

	@SuppressWarnings("unchecked")
	public List<SkuOptionValue> findSkuOptionValueByOptionNameAndValueName(
			String optionName, String valueName) {
		String hql="select sov from SkuOptionValue sov where sov.skuOption.skuOptionName=? and sov.skuOptionValueName=?";
		List<SkuOptionValue>result =findByHql(hql, new Object[]{optionName,valueName});
		return result;
	}

}
