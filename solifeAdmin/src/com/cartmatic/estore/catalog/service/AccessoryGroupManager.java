package com.cartmatic.estore.catalog.service;

import java.util.List;

import com.cartmatic.estore.common.model.catalog.AccessoryGroup;
import com.cartmatic.estore.core.service.GenericManager;

/**
 * Manager interface for AccessoryGroup, responsible for business processing, and communicate between web and persistence layer.
 *
 */
public interface AccessoryGroupManager extends GenericManager<AccessoryGroup> {
	public List<AccessoryGroup> findAllAccessoryGroup();
	public AccessoryGroup getAccessoryGroupByCode(String code);
}
