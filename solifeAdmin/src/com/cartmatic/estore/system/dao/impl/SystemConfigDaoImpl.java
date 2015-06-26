package com.cartmatic.estore.system.dao.impl;

import java.util.List;

import com.cartmatic.estore.system.dao.SystemConfigDao;
import com.cartmatic.estore.common.model.system.SystemConfig;
import com.cartmatic.estore.core.dao.impl.HibernateGenericDaoImpl;

/**
 * Dao implementation for SystemConfig.
*/
public class SystemConfigDaoImpl extends HibernateGenericDaoImpl<SystemConfig> implements SystemConfigDao {

	@Override
	public List<String> findConfigKeyItemsByCategory(String category) {
		return findByHql("select s.configKey from SystemConfig s where s.category like ?","%"+category+"%");
	}
	
	public SystemConfig getConfigByKey(String configKey, Integer storeId)
	{
		String hql = "from SystemConfig s where s.configKey=? and s.store.storeId=?";
		return (SystemConfig) this.findUnique(hql, new Object[]{configKey, storeId});
	}
	@Override
	public List<SystemConfig> findSystemConfigByCategory(String category) {
		return findByHql("from SystemConfig s where s.isSystem <>1 and s.category like ?","%"+category+"%");
	}

}
