package com.cartmatic.estore.system.service.impl;

import java.util.List;

import com.cartmatic.estore.common.model.system.AppResource;
import com.cartmatic.estore.core.security.ResourceMapping;
import com.cartmatic.estore.core.security.ResourceMappingProvider;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;
import com.cartmatic.estore.system.dao.AppResourceDao;
import com.cartmatic.estore.system.service.AppResourceManager;


/**
 * Manager implementation for AppResource, responsible for business processing, and communicate between web and persistence layer.
 */
public class AppResourceManagerImpl extends GenericManagerImpl<AppResource> implements AppResourceManager,ResourceMappingProvider {
	private AppResourceDao appResourceDao = null;

	/**
	 * @param appResourceDao
	 *            the appResourceDao to set
	 */
	public void setAppResourceDao(AppResourceDao appResourceDao) {
		this.appResourceDao = appResourceDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		dao = appResourceDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete()
	 */
	@Override
	protected void onDelete(AppResource entity) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave()
	 */
	@Override
	protected void onSave(AppResource entity) {

	}
	
	/**
	 * get all resource and it's roles
	 */
	@Override
	public ResourceMapping[] getResourceMappings() {
		
		if(logger.isDebugEnabled()){
			logger.debug("enter the ResourceManagerImpl getResourceMappings method =====");
		}
		//get all resource.
		List<AppResource> allResources=getAllResources();		
		ResourceMapping[] resMapping=new ResourceMapping[allResources.size()];
		
		for(int i=0;i<allResources.size();i++){
			resMapping[i]=new ResourceMapping();
			AppResource resource=(AppResource)allResources.get(i);
			resMapping[i].setPermission(resource.getResPermission());
			resMapping[i].setResourcePath(resource.getResourceString());			
			//重构后,是直接从resource中得到roleNames;  -- old:Set roleSet=resource.getAppRoles();
			resMapping[i].setRecipients(resource.getAuthorities());
		}
		return resMapping;
	}
	
	@Override
	public List<AppResource> getAllResources() {
		return dao.getAllOrdered("resourceString", false);
	}
	
}
