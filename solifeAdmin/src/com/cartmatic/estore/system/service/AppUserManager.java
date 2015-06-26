package com.cartmatic.estore.system.service;

import java.util.List;

import com.cartmatic.estore.common.model.system.AppUser;
import com.cartmatic.estore.core.service.GenericManager;

/**
 * Manager interface for AppUser, responsible for business processing, and communicate between web and persistence layer.
 *
 */
public interface AppUserManager extends GenericManager<AppUser> {
	public boolean isCustomerExist(String username);
	public boolean isAdminExist(String username);
	public boolean isEmailExist(AppUser appUser);
	public boolean isEmailExist(String email);
	public AppUser getUserByName(String username);
	public List<AppUser> getAllAppAdmin();
	public boolean getIsPasswordRight(Integer appuserId,String oldPassword);
	public void saveModifyPassword(Integer appuserId, String newPassword);
	public void saveModifyPassword(String email, String newPassword);
	public void doActiceUserStatusByEmail(String email);
}
