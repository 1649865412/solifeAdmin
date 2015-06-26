package com.cartmatic.estore.system.dao.impl;

import java.util.List;

import com.cartmatic.estore.system.dao.AppAuditDao;
import com.cartmatic.estore.common.model.system.AppAudit;
import com.cartmatic.estore.core.dao.impl.HibernateGenericDaoImpl;

/**
 * Dao implementation for AppAudit.
*/
public class AppAuditDaoImpl extends HibernateGenericDaoImpl<AppAudit> implements AppAuditDao {
	@Override
	public List<Integer> findUserIdsbyName(String userName) {
		return findByHql("select a.appuserId from AppUser a where a.firstname like '%" + userName + "%' or a.lastname like '%" + userName +"%' or concat( a.firstname,' ',a.lastname) like '%"+userName+"%'");
	}

}
