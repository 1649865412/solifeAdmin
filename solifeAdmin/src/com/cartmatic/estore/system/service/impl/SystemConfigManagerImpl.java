package com.cartmatic.estore.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cartmatic.estore.common.model.system.SystemConfig;
import com.cartmatic.estore.core.event.ConfigChangedEvent;
import com.cartmatic.estore.core.event.RefreshContextEvent;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;
import com.cartmatic.estore.core.util.ContextUtil;
import com.cartmatic.estore.system.dao.SystemConfigDao;
import com.cartmatic.estore.system.service.SystemConfigManager;


/**
 * Manager implementation for SystemConfig, responsible for business processing, and communicate between web and persistence layer.
 */
public class SystemConfigManagerImpl extends GenericManagerImpl<SystemConfig> implements SystemConfigManager {

	private SystemConfigDao systemConfigDao = null;
	
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		dao = systemConfigDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete()
	 */
	@Override
	protected void onDelete(SystemConfig entity) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave()
	 */
	@Override
	protected void onSave(SystemConfig entity) {

	}

	@Override
	public void save(SystemConfig systemConfig) {
		super.save(systemConfig);
		if (systemConfig.getIsValueChanged()) {
			ContextUtil.getInstance().fireApplicationEvent(new ConfigChangedEvent(systemConfig.getConfigKey(), systemConfig.getConfigValue()));
		}
	}
	
	@Override
	public SystemConfig getConfigByKey(String configKey) {
		return systemConfigDao.findUniqueByProperty("configKey",configKey);
	}
	
	public SystemConfig getConfigByKey(String configKey, Integer storeId)
	{
		return systemConfigDao.getConfigByKey(configKey,storeId);
	}
	
	/*public List<SystemConfig> getStoreConfigsByKey(String configKey)
	{
		return systemConfigDao.findByProperty("configKey", configKey);
	}*/

	@Override
	public Map<String, String> getConfigAsMap() {
		Map<String, String> newConfigMap = new HashMap<String,String>();
		List<SystemConfig> systemConfigList = systemConfigDao.getAll();
		for (SystemConfig systemConfig : systemConfigList) {
			if (systemConfig.getStore() == null)
				newConfigMap.put(systemConfig.getConfigKey(), systemConfig.getConfigValue());
			else
				newConfigMap.put(systemConfig.getStore().getCode()+"_"+systemConfig.getConfigKey(), systemConfig.getConfigValue());
		}
		return newConfigMap;
	}

	@Override
	public List<String> findConfigKeyItemsByCategory(String category) {
		List<String> results = new ArrayList<String>();
		List<String> configKeyItemList = systemConfigDao.findConfigKeyItemsByCategory(category);
		for (String configKey: configKeyItemList) {
			String[] temps=configKey.split("[.]");
			String team = null;
			if (temps.length > 2){
				team = temps[1];
			}else{
				team = category;
			}
			if (!results.contains(team))
				results.add(team);
		}
		return results;
	}

	@Override
	public List<SystemConfig> findSystemConfigByCategory(String category) {
		return systemConfigDao.findSystemConfigByCategory(category);
	}

	@Override
	public void clearAllServerSideCaches() {
		ContextUtil.getInstance().fireApplicationEvent(new RefreshContextEvent(RefreshContextEvent.CATEGORY_SERVER_SIDE_CACHE));
	}
	/**
	 * @param systemConfigDao
	 *            the systemConfigDao to set
	 */
	public void setSystemConfigDao(SystemConfigDao systemConfigDao) {
		this.systemConfigDao = systemConfigDao;
	}
	

}
