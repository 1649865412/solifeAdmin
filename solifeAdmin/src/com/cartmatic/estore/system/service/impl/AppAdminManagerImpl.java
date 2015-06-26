package com.cartmatic.estore.system.service.impl;

import java.util.UUID;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.common.helper.AppAuditHelper;
import com.cartmatic.estore.common.model.system.AdminInfo;
import com.cartmatic.estore.common.model.system.AppAdmin;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;
import com.cartmatic.estore.system.dao.AppAdminDao;
import com.cartmatic.estore.system.service.AdminInfoManager;
import com.cartmatic.estore.system.service.AppAdminManager;
import com.cartmatic.estore.webapp.util.RequestContext;


/**
 * AppAdmin Business Delegate (Proxy) implementation to handle communication between web and
 * persistence layer. Implementation of AppAdminManager interface.
 * Developer introduced interfaces should be declared here. Won't get overwritten.
 */
public class AppAdminManagerImpl extends GenericManagerImpl<AppAdmin> implements AppAdminManager {
	private AppAdminDao appAdminDao=null;
	private AdminInfoManager adminInfoManager=null;
	
	public void setAppAdminDao(AppAdminDao appAdminDao) {
		this.appAdminDao = appAdminDao;
	}

	public void setAdminInfoManager(AdminInfoManager adminInfoManager) {
		this.adminInfoManager = adminInfoManager;
	}

	@Override
	protected void initManager() {
		dao = appAdminDao;
	}

	@Override
	protected void onDelete(AppAdmin entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onSave(AppAdmin entity) {
		AdminInfo adminInfo=entity.getAdminInfo();
		if(adminInfo==null||adminInfo.getAdminInfoId()==null){
			adminInfoManager.save(adminInfo);
		}
	}

	@Override
	protected void delete(AppAdmin appAdmin) {
		//逻辑删除客户，删除前改userName及用户名
        String userName=appAdmin.getUsername();
        String email=appAdmin.getEmail();
        long uuid= UUID.randomUUID().getLeastSignificantBits();
        uuid=Math.abs(uuid);
        appAdmin.setUsername(uuid+"");
        appAdmin.setEmail(uuid+"");
        appAdmin.setDeleted(Constants.FLAG_TRUE);
        save(appAdmin);
        StringBuffer prObj=new StringBuffer("delete manager,ID:");
        prObj.append(appAdmin.getId());
        prObj.append(",userName:");
        prObj.append(userName);
        prObj.append(",email:");
        prObj.append(email);
        prObj.append(",new email:");
        prObj.append(appAdmin.getEmail());
        prObj.append(",new userName:");
        prObj.append(appAdmin.getUsername());
        AppAuditHelper.getInstance().doAuditAction(AppAuditHelper.ACTION_DEL_CUSTOMER,prObj.toString(), AppAuditHelper.RESULT_SUCCESS, null, RequestContext.getCurrentUserId());
	}

}
