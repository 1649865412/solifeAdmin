package com.cartmatic.estore.system.service;

import com.cartmatic.estore.common.model.system.RoleRes;
import com.cartmatic.estore.core.service.GenericManager;

/**
 * Manager interface for RoleRes, responsible for business processing, and communicate between web and persistence layer.
 *
 */
public interface RoleResManager extends GenericManager<RoleRes> {

	public void deleteByRoleId(Integer id);
}
