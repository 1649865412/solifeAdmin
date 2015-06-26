package com.cartmatic.estore.system.dao;

import java.util.List;

import com.cartmatic.estore.common.model.system.AppAudit;
import com.cartmatic.estore.core.dao.GenericDao;
/**
 * Dao interface for AppAudit.
 */
public interface AppAuditDao extends GenericDao<AppAudit> {
	public List<Integer> findUserIdsbyName(String name);
}