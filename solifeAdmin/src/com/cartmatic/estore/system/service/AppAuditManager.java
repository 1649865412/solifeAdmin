package com.cartmatic.estore.system.service;

import java.util.Date;
import java.util.List;

import com.cartmatic.estore.common.model.system.AppAudit;
import com.cartmatic.estore.core.service.GenericManager;

/**
 * Manager interface for AppAudit, responsible for business processing, and communicate between web and persistence layer.
 *
 */
public interface AppAuditManager extends GenericManager<AppAudit> {
	public void doAuditAction(String actionName, Object procObj,Object procResult, String requestUrl);
	
	public void doAuditAction(String actionName, Object procObj,Object procResult, String requestUrl, Integer userId, Date procTime);
	
	public List<Integer> findUserIdsbyName(String name);
}
