package com.cartmatic.estore.system.service;

import com.cartmatic.estore.common.model.system.SystemVersion;
import com.cartmatic.estore.core.service.GenericManager;

/**
 * Manager interface for SystemVersion, responsible for business processing, and communicate between web and persistence layer.
 *
 */
public interface SystemVersionManager extends GenericManager<SystemVersion> {
	public SystemVersion getSystemVersion();
}
