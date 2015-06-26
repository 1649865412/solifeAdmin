package com.cartmatic.estore.system.dao.impl;

import com.cartmatic.estore.common.model.system.RoleRes;
import com.cartmatic.estore.core.dao.impl.HibernateGenericDaoImpl;
import com.cartmatic.estore.system.dao.RoleResDao;

/**
 * Dao implementation for RoleRes.
*/
public class RoleResDaoImpl extends HibernateGenericDaoImpl<RoleRes> implements RoleResDao {

	public void deleteByRoleId(Integer id)
	{
		String hqlDelete = "delete RoleRes where appRoleId = ?";
        Object[] val = {id};
        this.getHibernateTemplate().bulkUpdate(hqlDelete,val);
	}
}
