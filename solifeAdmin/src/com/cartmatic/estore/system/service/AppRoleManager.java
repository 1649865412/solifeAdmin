package com.cartmatic.estore.system.service;

import com.cartmatic.estore.common.model.system.AppRole;
import com.cartmatic.estore.core.service.GenericManager;

/**
 * Manager interface for AppRole, responsible for business processing, and communicate between web and persistence layer.
 *
 */
public interface AppRoleManager extends GenericManager<AppRole> {
	public AppRole getAdminRole();
	
	public boolean getIsRoleNameExist(String roleName);
	/**
	 * 会删除或增加关联的resource
	 * @param appRole
	 */
	public void saveAppRole(AppRole appRole);
}
