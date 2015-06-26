
package com.cartmatic.estore.system.service.impl;

import java.util.List;

import com.cartmatic.estore.common.model.system.SystemLanguage;
import com.cartmatic.estore.core.exception.ApplicationException;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;
import com.cartmatic.estore.system.dao.SystemLanguageDao;
import com.cartmatic.estore.system.service.SystemLanguageManager;

/**
 * SystemLanguage Business Delegate (Proxy) implementation to handle
 * communication between web and persistence layer. Implementation of
 * SystemLanguageManager interface. Developer introduced interfaces should be
 * declared here.
 */
public class SystemLanguageManagerImpl extends
		GenericManagerImpl<SystemLanguage> implements SystemLanguageManager {
	// 对非基类的方法的引用应该通过这个变量来调用
	private SystemLanguageDao	systemLanguageDao	= null;

	public List getActives() {
		return systemLanguageDao.getActives();
	}

	public SystemLanguage getDefaultLanguage() {
		return systemLanguageDao.getDefaultLanguage();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		// 设置范型基类的dao
		dao = systemLanguageDao;
	}

	/**
	 * new if exists false. update if id != selfId false.
	 * 
	 */
	public boolean isExistLocaleCode(SystemLanguage l) {
		return !dao.isUnique(l, "localeCode");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete(java.lang.Object)
	 */
	@Override
	protected void onDelete(SystemLanguage entity) {
		if (isTrue(entity.getIsDefault())) {
			throw new ApplicationException("Cannot delete a default language.",
					"CAN_NOT_DELETE_DEFAULT_ENTITY", entity.getLanguageName());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave(java.lang.Object)
	 */
	@Override
	protected void onSave(SystemLanguage entity) {
	}

	/**
	 * @param systemLanguageDao
	 *            the systemLanguageDao to set
	 */
	public void setSystemLanguageDao(SystemLanguageDao systemLanguageDao) {
		this.systemLanguageDao = systemLanguageDao;
	}

}
