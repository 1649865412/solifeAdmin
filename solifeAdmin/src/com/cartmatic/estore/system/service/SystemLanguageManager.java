package com.cartmatic.estore.system.service;

import java.util.List;

import com.cartmatic.estore.common.model.system.SystemLanguage;
import com.cartmatic.estore.core.service.GenericManager;

/**
 * SystemLanguage Business Delegate (Proxy) Interface to handle communication between web and persistence layer.
 * Developer introduced interfaces should be declared here. 
 * 
 */
public interface SystemLanguageManager extends GenericManager<SystemLanguage> {

	public SystemLanguage getDefaultLanguage();

	public boolean isExistLocaleCode(SystemLanguage l);

	public List getActives();
}
