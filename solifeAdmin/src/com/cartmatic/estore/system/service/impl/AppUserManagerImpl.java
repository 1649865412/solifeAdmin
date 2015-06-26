package com.cartmatic.estore.system.service.impl;

import java.util.List;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.common.model.system.AppUser;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;
import com.cartmatic.estore.system.dao.AppUserDao;
import com.cartmatic.estore.system.service.AppUserManager;


/**
 * Manager implementation for AppUser, responsible for business processing, and communicate between web and persistence layer.
 */
public class AppUserManagerImpl extends GenericManagerImpl<AppUser> implements AppUserManager {

	private AppUserDao appUserDao = null;

	/**
	 * @param appUserDao
	 *            the appUserDao to set
	 */
	public void setAppUserDao(AppUserDao appUserDao) {
		this.appUserDao = appUserDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		dao = appUserDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete()
	 */
	@Override
	protected void onDelete(AppUser entity) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave()
	 */
	@Override
	protected void onSave(AppUser entity) {

	}

	public boolean isAdminExist(String username) {
		return appUserDao.isAdminExist(username);
	}

	public boolean isCustomerExist(String username) {
		return appUserDao.isCustomerExist(username);
	}

	public boolean isEmailExist(AppUser appUser) {
		List<AppUser> appUserList=appUserDao.findByProperty("email", appUser.getEmail());
		if(appUserList.size()==0){
			return false;
		}else if((appUser.getAppuserId()==null&&appUserList.size()>0)||appUserList.size()>1){
			return true;
		}else{
			return appUser.getAppuserId().intValue()!=appUserList.get(0).getAppuserId().intValue();
		}
	}

	
	public AppUser getUserByName(String username) {
		return appUserDao.getUserByName(username);
	}

	public List<AppUser> getAllAppAdmin() {
		return appUserDao.getAllAppAdmin();
	}

	public boolean getIsPasswordRight(Integer appuserId, String oldPassword) {
		AppUser appUser=getById(appuserId);
		if(appUser.getPassword().equals(oldPassword)){
			return true;
		}else{
			return false;
		}
	}

	public void saveModifyPassword(Integer appuserId, String newPassword) {
		AppUser appUser=getById(appuserId);
		appUser.setPassword(newPassword);
		save(appUser);
	}
	
	public void saveModifyPassword(String email, String newPassword) {
		AppUser appUser=appUserDao.findUniqueByProperty("email", email);
		appUser.setPassword(newPassword);
		save(appUser);
	}


	public void doActiceUserStatusByEmail(String email) {
		AppUser appUser=appUserDao.findUniqueByProperty("email", email);
		appUser.setStatus(Constants.STATUS_ACTIVE);
		save(appUser);
	}

	public boolean isEmailExist(String email) {
		long count =appUserDao.countByProperty("email", email);
		return count>0;
	}



}
