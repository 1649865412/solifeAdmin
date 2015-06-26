package com.cartmatic.estore.system.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.common.model.system.AppRole;
import com.cartmatic.estore.common.model.system.RoleRes;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;
import com.cartmatic.estore.system.service.AppRoleManager;
import com.cartmatic.estore.system.service.RoleResManager;
import com.cartmatic.estore.system.dao.AppRoleDao;


/**
 * Manager implementation for AppRole, responsible for business processing, and communicate between web and persistence layer.
 */
public class AppRoleManagerImpl extends GenericManagerImpl<AppRole> implements AppRoleManager {
	private RoleResManager roleResManager=null;
	
	private AppRoleDao appRoleDao = null;

	/**
	 * @param appRoleDao
	 *            the appRoleDao to set
	 */
	public void setAppRoleDao(AppRoleDao appRoleDao) {
		this.appRoleDao = appRoleDao;
	}

	public void setRoleResManager(RoleResManager roleResManager) {
		this.roleResManager = roleResManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		dao = appRoleDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete()
	 */
	@Override
	protected void onDelete(AppRole entity) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave()
	 */
	@Override
	protected void onSave(AppRole entity) {

	}

	@Override
	public AppRole getAdminRole() {
		return getByRoleName(Constants.DEFAULT_ADMIN_ROLE_NAME);
	}
	
	
	private AppRole getByRoleName(String roleName){
		return appRoleDao.findUniqueByProperty("roleName", roleName);
	}

	@Override
	public boolean getIsRoleNameExist(String roleName) {
		return appRoleDao.countByProperty("roleName", roleName)>0;
	}

	@Override
	public void saveAppRole(AppRole appRole) {
		//如果不是新增的话，要先删除原来的与resource的关联，再重新添加。
		/*roleResManager.deleteByRoleId(appRole.getRoleId());
		roleResManager.flush();*/
		Set<RoleRes> roleRess=appRole.getRoleRess();
		Set<RoleRes> willRemoveRoleRess=new HashSet<RoleRes>();
		if(appRole.getAppRoleId()!=null){
			appRole.setRoleRess(null);
			List<RoleRes> appRoleList=roleResManager.findByProperty("appRole.appRoleId", appRole.getAppRoleId());
			for (RoleRes roleRes : appRoleList) {
				boolean delete=true;
				for (RoleRes roleRes2 : roleRess) {
					if(roleRes.getAppResourceId().intValue()==roleRes2.getAppResourceId().intValue()&&roleRes.getPermissionMask()!=null&&roleRes.getPermissionMask().intValue()==roleRes2.getPermissionMask().intValue()){
						delete=false;
						willRemoveRoleRess.add(roleRes2);
						break;
					}
				}
				if(delete){
					roleResManager.deleteById(roleRes.getRoleResId());
				}
			}
			roleRess.removeAll(willRemoveRoleRess);
			for (RoleRes roleRes : roleRess) {
		
				roleResManager.save(roleRes);
			}
		}
		save(appRole);
	}

}
