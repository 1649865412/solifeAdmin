/**
 * 
 */

package com.cartmatic.estore.core.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.access.AccessDeniedException;

import com.cartmatic.estore.core.dao.GenericDao;
import com.cartmatic.estore.core.model.BaseObject;
import com.cartmatic.estore.core.search.SearchCriteria;
import com.cartmatic.estore.core.search.SearchCriteriaBuilder;
import com.cartmatic.estore.core.service.GenericManager;
import com.cartmatic.estore.core.util.GenericsUtils;
import com.cartmatic.estore.core.util.StringUtil;
import com.cartmatic.estore.webapp.util.RequestContext;

/**
 * Manager/Service一定要基于接口编程，这样可以很方便的注入其他Bean，而且又不会与实现太过耦合。
 * <P>
 * 子类可按需要实现onDelete，onSave，initManager等方法
 * <P>
 * 需要权限检查的可以实现checkRight；由于采用先读后保存或删除的策略，所以检查都是在读到后立刻进行，在写之前不再检查。
 * 
 * @author Ryan
 * 
 * @param <T>
 */
@SuppressWarnings("unchecked")
public abstract class GenericManagerImpl<T> implements GenericManager<T>,
		InitializingBean {
	protected final static StringBuilder formatMsg(String msgWithFormat,
			Object... args) {
		return StringUtil.format(msgWithFormat, args);
	}

	/**
	 * Manager和DAO之间的接口比较特殊，一般DAO只是对应一个Manager，不需要被多个Manager共享不基于接口编程。如果需要使用基类提供的以外的数据库访问方法，实现自己的dao并注入。
	 */
	protected GenericDao<T>			dao		= null;

	public Class<T>					entityClass;

	protected final transient Log	logger	= LogFactory.getLog(getClass());

	/**
	 * 在构造函数中将泛型T.class赋给entityClass.
	 */
	public GenericManagerImpl() {
		entityClass = GenericsUtils.getSuperClassGenricType(getClass());
	}

	public final void afterPropertiesSet() throws IllegalArgumentException,
			BeanInitializationException {
		initManager();
		if (dao == null) {
			logger.info("Generic dao is not set. Please set it in the initManager method if database access is desired!");
		}
	}

	/**
	 * 检查当前用户/请求是否具有相应的权限，如果没有则抛出ADE异常，后续的框架处理会转向403.jsp。子类需要覆盖hasRight才能生效。
	 * 
	 * @param entity
	 * @return
	 * @throws AccessDeniedException
	 */
	protected final T checkRight(T entity) throws AccessDeniedException {
		if (!hasRight(entity)) {
			String errMsg = formatMsg(
					"User %1 try to access entity (%2 - %3) but has no access right!",
					RequestContext.getCurrentUserId(),
					entity.getClass().getSimpleName(),
					entity instanceof BaseObject ? ((BaseObject) entity)
							.getId() : entity).toString();
			throw new AccessDeniedException(errMsg);
		}
		return entity;
	}

	/*
	 * 如果需要对删除的数据进行一些检查、处理，重载此方法。
	 * 
	 */
	protected void delete(T entity) {
		onDelete(entity);
		this.dao.delete(entity);
	}
	
	public void evict(T entity) {
        this.dao.evict(entity);
    }
	
	public void merge(T entity) {
        this.dao.merge(entity);
    }

	public final void deleteAllByIds(String[] ids) {
		for (int i = 0; i < ids.length; i++) {
			deleteById(Integer.valueOf(ids[i]));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManager#deleteById(java.io.Serializable)
	 */
	public final T deleteById(Serializable id) {
		T entity = loadById(id);
		delete(entity);
		return entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManager#findByProperty(java.lang.String,
	 *      java.lang.Object)
	 */
	public final List<T> findByProperty(String propertyName, Object value) {
		return this.dao.findByProperty(propertyName, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManager#findByPropertyOrdered(java.lang.String,
	 *      java.lang.Object, java.lang.String, boolean)
	 */
	public final List<T> findByPropertyOrdered(String propertyName,
			Object value, String orderBy, boolean isAsc) {
		return this.dao.findByPropertyOrdered(propertyName, value, orderBy,
				isAsc);
	}	

	public void flush() {
		dao.flush();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManager#getAll()
	 */
	public List<T> getAll() {
		return this.dao.getAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManager#getAllOrdered(java.lang.String,
	 *      boolean)
	 */
	public List<T> getAllOrdered(String orderBy, boolean isAsc) {
		return this.dao.getAllOrdered(orderBy, isAsc);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManager#getById(java.io.Serializable)
	 */
	public final T getById(Serializable id) {
		return checkRight(this.dao.getById(id));
	}

	public SearchCriteriaBuilder getSearchCriteriaBuilder(String filterName) {
		return dao.getSearchCriteriaBuilder(filterName);
	}

	/**
	 * 检查当前用户/请求是否具有相应的权限。框架不提供实现，子类需要的时候应该覆盖并提供实现。由checkRight使用。
	 * 
	 * @param entity
	 * @return
	 */
	protected boolean hasRight(T entity) {
		return true;
	}

	/**
	 * 子类需要重载这个方法来实现初始化，包括设置dao
	 * 
	 */
	protected abstract void initManager();

	protected final boolean isEmpty(final String str) {
		return str == null || str.trim().length() == 0;
	}

	protected final boolean isTrue(final Object value) {
		return (Boolean.TRUE.equals(value) || Short.valueOf("1").equals(value) || "true"
				.equals(value));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManager#loadById(java.io.Serializable)
	 */
	public final T loadById(final Serializable id) {
		return checkRight(this.dao.loadById(id));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.GenericManager#loadForUpdate(java.io.Serializable)
	 */
	public T loadForUpdate(final Serializable id) {
		return checkRight(dao.loadForUpdate(id));
	}

	/**
	 * 如果需要对删除的数据进行一些检查、处理，实现此方法。可抛出ApplicationException。
	 * 
	 * @param entity
	 */
	protected abstract void onDelete(T entity);

	/**
	 * 保存之前，对数据进行一些检查、转换、关联、其他处理等。可抛出ApplicationException。
	 * 
	 * @param entity
	 */
	protected abstract void onSave(T entity);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManager#save(java.lang.Object)
	 */
	public void save(T entity) {
		/** 新增的时候很难检查，更新的时候已经在load的时候检查，而在程序里面一般不会再修改id，所以应该是安全的，需要再检查的时候在onSave里面检查 */
		onSave(entity);
		this.dao.save(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManager#saveOrUpdateAll(java.util.Collection)
	 */
	public final void saveOrUpdateAll(Collection<T> col) {
		for (Iterator iter = col.iterator(); iter.hasNext();) {
			save((T) iter.next());
		}
	}

	/**
	 * 要求数据要先转换为正确的类型。
	 * 
	 * @param multiUpdateModel
	 */
	public final void saveOrUpdateAll(
			Map<Integer, Map<String, Object>> multiUpdateModel) {
		Iterator<Integer> iter = multiUpdateModel.keySet().iterator();
		try {
			while (iter.hasNext()) {
				Integer id = iter.next();
				Map<String, Object> entityModel = multiUpdateModel.get(id);
				T entity = loadById(id);
				Iterator<String> setterIter = entityModel.keySet().iterator();
				while (setterIter.hasNext()) {
					String methodName = setterIter.next();
					Object value = entityModel.get(methodName);
                    
					String upperF = String.valueOf(methodName.charAt(0)).toUpperCase();
					methodName = methodName.replaceFirst(upperF.toLowerCase(), upperF);
					
					entityClass.getMethod("set"+methodName,
							new Class[] { value.getClass() }).invoke(entity,
							new Object[] { value });
				}
			}
		} catch (Throwable e) {
			logger.error(formatMsg(
					"Unexpected multi update error. Processing Data: %1",
					multiUpdateModel));
			throw new RuntimeException("Unexpected multi update error.", e);
		}
	}

	public List searchByCriteria(SearchCriteria sc) {
		return dao.searchByCriteria(sc);
	}
}
