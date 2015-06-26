package com.cartmatic.estore.system.dao;

import com.cartmatic.estore.common.model.system.RoleRes;
import com.cartmatic.estore.core.dao.GenericDao;
/**
 * Dao interface for RoleRes.
 */
public interface RoleResDao extends GenericDao<RoleRes> {
	public void deleteByRoleId(Integer id);
}