package com.cartmatic.estore.system.service;

import java.util.List;

import com.cartmatic.estore.common.model.system.AppResource;
import com.cartmatic.estore.core.service.GenericManager;


/**
 * Manager interface for AppResource, responsible for business processing, and communicate between web and persistence layer.
 *
 */
public interface AppResourceManager extends GenericManager<AppResource> {
	public List<AppResource> getAllResources();
}
