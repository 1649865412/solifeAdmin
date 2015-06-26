package com.cartmatic.estore.system.dao;

import java.util.List;

import com.cartmatic.estore.common.model.system.AppUser;
import com.cartmatic.estore.core.dao.GenericDao;
/**
 * Dao interface for AppUser.
 */
public interface AppUserDao extends GenericDao<AppUser> {
	public boolean isCustomerExist(String username);
	public boolean isAdminExist(String username);
	public AppUser getUserByName(String username);
	
	public List<AppUser> getAllAppAdmin();
	
}