package com.cartmatic.estore.system.dao.impl;

import java.util.List;

import com.cartmatic.estore.common.model.system.SystemLanguage;
import com.cartmatic.estore.core.dao.impl.HibernateGenericDaoImpl;
import com.cartmatic.estore.system.dao.SystemLanguageDao;

/**
 * SystemLanguage Data Access Object (DAO) implementation. Developer introduced interfaces should be declared here.
 */
public class SystemLanguageDaoImpl extends HibernateGenericDaoImpl<SystemLanguage> implements SystemLanguageDao {
	public List getActives() {
		return findByFilter("active");
	}

	public SystemLanguage getDefaultLanguage() {
		List result = findByHql("from SystemLanguage where isDefault=1");
		if (result.size() < 1) {
			throw new RuntimeException("The default language is not set!!");
		}

		return (SystemLanguage) result.get(0);
	}

}