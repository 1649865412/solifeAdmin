package com.cartmatic.estore.system.dao;

import java.util.List;

import com.cartmatic.estore.common.model.system.SystemLanguage;
import com.cartmatic.estore.core.dao.GenericDao;
/**
 * SystemLanguage Data Access Object (DAO) interface.
 * Developer introduced interfaces should be declared here.
 */
public interface SystemLanguageDao extends GenericDao<SystemLanguage> {

	public List getActives();

	public SystemLanguage getDefaultLanguage();

}
