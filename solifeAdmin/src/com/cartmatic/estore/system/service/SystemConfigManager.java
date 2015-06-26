package com.cartmatic.estore.system.service;

import java.util.List;
import java.util.Map;

import com.cartmatic.estore.common.model.system.SystemConfig;
import com.cartmatic.estore.core.service.GenericManager;

/**
 * Manager interface for SystemConfig, responsible for business processing, and communicate between web and persistence layer.
 *
 */
public interface SystemConfigManager extends GenericManager<SystemConfig> {
	public SystemConfig getConfigByKey(String configKey);
	
	public SystemConfig getConfigByKey(String configKey, Integer storeId);
	
	//public List<SystemConfig> getStoreConfigsByKey(String configKey);
	
	public Map<String, String> getConfigAsMap();
	
	public List<String> findConfigKeyItemsByCategory(String category);
	
	public List<SystemConfig> findSystemConfigByCategory(String category);
	
	public void clearAllServerSideCaches();
}
