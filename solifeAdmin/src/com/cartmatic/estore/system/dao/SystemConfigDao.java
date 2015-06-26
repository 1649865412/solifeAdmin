package com.cartmatic.estore.system.dao;

import java.util.List;

import com.cartmatic.estore.common.model.system.SystemConfig;
import com.cartmatic.estore.core.dao.GenericDao;
/**
 * Dao interface for SystemConfig.
 */
public interface SystemConfigDao extends GenericDao<SystemConfig> {
	public List<String> findConfigKeyItemsByCategory(String category);
	public SystemConfig getConfigByKey(String configKey, Integer storeId);
	public List<SystemConfig> findSystemConfigByCategory(String category);
}