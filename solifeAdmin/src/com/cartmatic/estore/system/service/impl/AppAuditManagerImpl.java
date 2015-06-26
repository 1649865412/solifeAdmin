package com.cartmatic.estore.system.service.impl;

import java.util.Date;
import java.util.List;

import com.cartmatic.estore.common.model.system.AppAudit;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;
import com.cartmatic.estore.system.service.AppAuditManager;
import com.cartmatic.estore.system.dao.AppAuditDao;
import com.cartmatic.estore.webapp.util.RequestContext;


/**
 * Manager implementation for AppAudit, responsible for business processing, and communicate between web and persistence layer.
 */
public class AppAuditManagerImpl extends GenericManagerImpl<AppAudit> implements AppAuditManager {

	private AppAuditDao appAuditDao = null;

	/**
	 * @param appAuditDao
	 *            the appAuditDao to set
	 */
	public void setAppAuditDao(AppAuditDao appAuditDao) {
		this.appAuditDao = appAuditDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		dao = appAuditDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete()
	 */
	@Override
	protected void onDelete(AppAudit entity) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave()
	 */
	@Override
	protected void onSave(AppAudit entity) {

	}

	@Override
	public void doAuditAction(String actionName, Object procObj,Object procResult, String requestUrl) {
		doAuditAction(actionName, procObj, procResult, RequestContext.getCurrentRequestUrl(), null, null);
	}

	@Override
	public void doAuditAction(String actionName, Object procObj,Object procResult, String requestUrl, Integer userId, Date procTime) {
		AppAudit audit = new AppAudit();
		audit.setActionName(actionName);
		if (procObj != null) {
			audit.setProcObj(procObj.toString());
		}
		if (procResult != null) {
			audit.setProcResult(procResult.toString());
		}
		audit.setProcUserId(userId == null ? RequestContext.getCurrentUserId()
				: userId);
		audit.setRequestUrl(requestUrl);
		audit.setProcTime(procTime == null ? new Date() : procTime);
		save(audit);
		evict(audit);
	}

	@Override
	public List<Integer> findUserIdsbyName(String name) {
		return appAuditDao.findUserIdsbyName(name);
	}

}
