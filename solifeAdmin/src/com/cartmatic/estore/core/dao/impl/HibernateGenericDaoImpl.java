
package com.cartmatic.estore.core.dao.impl;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.ArrayUtils;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import com.cartmatic.estore.core.dao.GenericDao;
import com.cartmatic.estore.core.model.BaseObject;
import com.cartmatic.estore.core.util.GenericsUtils;
import com.cartmatic.estore.webapp.util.RequestContext;

/**
 * Hibernate实现的泛型基类,继承Spring的<code>HibernateDaoSupport</code>,支持MVC的Controller＋Manager＋DAO设计，基于接口的设计。
 * <P>
 * 为单个Entity对象提供CRUD操作,提供分页函数和若干便捷查询方法，并对返回值作了泛型类型转换。
 * <P>
 * 在SpringSide的基础上进行优化，把部分public的方法修改为protected，避免在Manager直接创建Query等，Controller、Manager层尽量避免和Hibernate绑定。
 * <P>
 * 用法：子类需要在类定义时指定所管理Entity的Class,实现该Entity特有数据库访问方法；一般不需要重载构造函数。
 * 
 * <pre>
 * public class UserDao extends HibernateGenericDaoImpl&lt;User&gt; {
 * }
 * </pre>
 * 
 * @author Ryan
 * 
 * @param <T>
 * 
 * @see HibernateDaoSupport
 * @see GenericDao
 */
@SuppressWarnings("unchecked")
public abstract class HibernateGenericDaoImpl<T> extends HibernateDaoSupportExt
		implements GenericDao<T> {
	protected Class<T>	entityClass;				// DAO所管理的Entity类型.

	private boolean		hasCreateBy		= false;

	private boolean		hasCreateTime	= false;

	private boolean		hasUpdateBy		= false;

	private boolean		hasUpdateTime	= false;

	private boolean		hasVersion		= false;

	protected String	idName			= null;

	/**
	 * 在构造函数中将泛型T.class自动赋给entityClass.
	 */
	public HibernateGenericDaoImpl() {
		entityClass = GenericsUtils.getSuperClassGenricType(getClass());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.dao.GenericDao#countByProperty(java.lang.String,
	 *      java.lang.Object)
	 */
	public final long countByProperty(String propertyName, Object value) {
		Assert.hasText(propertyName);
		return (Integer)createCriteria(Restrictions.eq(propertyName, value)).setProjection(Projections.rowCount()).uniqueResult();
	}

	/**
	 * 创建Criteria对象.
	 * 
	 * @param criterions
	 *            可变的Restrictions条件列表
	 */
	protected final Criteria createCriteria(Criterion... criterions) {
		Criteria criteria = getSession().createCriteria(entityClass);
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		return criteria;
	}

	/**
	 * 创建Criteria对象，带排序字段与升降序字段.
	 * 
	 * @see #createCriteria(Class,Criterion[])
	 */
	protected final Criteria createCriteria(String orderBy, boolean isAsc,
			Criterion... criterions) {
		Assert.hasText(orderBy);

		Criteria criteria = createCriteria(criterions);

		if (isAsc) {
			criteria.addOrder(Order.asc(orderBy));
		} else {
			criteria.addOrder(Order.desc(orderBy));
		}

		return criteria;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.dao.impl.GenericDao#delete(java.lang.Object)
	 */
	public void delete(T entity) {
		getHibernateTemplate().delete(entity);
		logger.info(formatMsg("Deleted an entity. ", entity));
	}
	
	public void evict(T entity) {
        getHibernateTemplate().evict(entity);
    }
	
	public void merge(T entity) {
        getHibernateTemplate().merge(entity);
    }

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.dao.impl.GenericDao#findBy(java.lang.String,
	 *      java.lang.Object)
	 */
	public final List<T> findByProperty(String propertyName, Object value) {
		Assert.hasText(propertyName);
		return createCriteria(Restrictions.eq(propertyName, value)).list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.dao.impl.GenericDao#findBy(java.lang.String,
	 *      java.lang.Object, java.lang.String, boolean)
	 */
	public final List<T> findByPropertyOrdered(String propertyName,
			Object value, String orderBy, boolean isAsc) {
		Assert.hasText(propertyName);
		Assert.hasText(orderBy);
		return createCriteria(orderBy, isAsc,
				Restrictions.eq(propertyName, value)).list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.dao.impl.GenericDao#findUniqueBy(java.lang.String,
	 *      java.lang.Object)
	 */
	public final T findUniqueByProperty(String propertyName, Object value) {
		Assert.hasText(propertyName);
		return (T) createCriteria(Restrictions.eq(propertyName, value))
				.uniqueResult();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.dao.impl.GenericDao#getAll()
	 */
	public List<T> getAll() {
		return getAllOrdered(idName, false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.dao.impl.GenericDao#getAllOrdered(java.lang.String,
	 *      boolean)
	 */
	public List<T> getAllOrdered(String orderBy, boolean isAsc) {
		Assert.hasText(orderBy);
		if (isAsc) {
			return getHibernateTemplate().findByCriteria(
					DetachedCriteria.forClass(entityClass).addOrder(
							Order.asc(orderBy)));
		} else {
			return getHibernateTemplate().findByCriteria(
					DetachedCriteria.forClass(entityClass).addOrder(
							Order.desc(orderBy)));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.dao.impl.GenericDao#get(java.io.Serializable)
	 */
	public final T getById(Serializable id) {
		T entity = (T) getHibernateTemplate().get(entityClass, id);
		if (entity == null) {
			logger.info(formatMsg(
					"Requested entity not found, class:%1, id:%2.", entityClass
							.getSimpleName(), id));
		}
		return entity;
	}

	public String getDefaultAllHql() {
		StringBuilder sb = new StringBuilder("select s from ").append(
				entityClass.getSimpleName()).append(" s");
		try {
			if (entityClass.getMethod("getDeleted", (Class[]) null) != null) {
				sb.append(" where deleted=").append(0).append(" ");
			}
		} catch (Throwable e) {
			// Method not found, do nothing
		}
		try {
			Method getIdColumnMethod = entityClass.getMethod(
					"getFirstKeyColumnName", (Class[]) null);
			sb.append(" order by ").append(
					getIdColumnMethod.invoke(entityClass.newInstance()));
			sb.append(" desc ");
		} catch (Throwable e) {
			// Method not found, do nothing
		}
		return sb.toString();
	}

	/**
	 * 取得entityClass。如果要支持JDK1.4（不支持泛型），重载此函数达到相同效果。
	 */
	protected Class<T> getEntityClass() {
		return entityClass;
	}

	/*
	 * 取得对象的主键值,辅助函数。因为在BaseObject已经提供了方法，所以这里不准备作为接口提供，只是保留。
	 * 
	 * @see com.cartmatic.estore.core.dao.impl.GenericDao#getId(java.lang.Class,
	 *      java.lang.Object)
	 */
	protected Serializable getId(Object entity) throws NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {
		Assert.notNull(entity);
		return (Serializable) PropertyUtils.getProperty(entity, getIdName());
	}

	/*
	 * 取得对象的主键名,辅助函数。
	 * 
	 * @see com.cartmatic.estore.core.dao.impl.GenericDao#getIdName(java.lang.Class)
	 */
	protected String getIdName() {
		return idName;
	}

	public T getSampleEntity() {
		final String queryString = getDefaultAllHql();
		return (T) getHibernateTemplate().executeWithNativeSession(new HibernateCallback() {
			public Object doInHibernate(Session session) {
				Query queryObject = session.createQuery(queryString);

				queryObject.setFirstResult(0);
				queryObject.setMaxResults(1);
				queryObject.setFetchSize(1);

				List<T> results = queryObject.list();

				if (results.size() == 1) {
					T entity = results.get(0);
					session.evict(entity);
					return entity;
				}
				return null;
			}
		});

	}

	/*
	 * 这里可以设置defaultHql（必须是不带参数的）,各个FilterHql等，以及其他初始化。
	 * 
	 * @see org.springframework.dao.support.DaoSupport#initDao()
	 */
	@Override
	protected final void initDao() throws Exception {
		super.initDao();
		ClassMetadata meta = getSessionFactory().getClassMetadata(entityClass);
		Assert.notNull(meta, "Class " + entityClass
				+ " not define in hibernate session factory.");
		idName = meta.getIdentifierPropertyName();
		Assert.hasText(idName, entityClass.getSimpleName()
				+ " has no identifier property define.");
		String[] propertyNames = meta.getPropertyNames();
		hasVersion = meta.isVersioned();
		hasCreateBy = ArrayUtils.contains(propertyNames, "createBy");
		hasCreateTime = ArrayUtils.contains(propertyNames, "createTime");
		hasUpdateBy = ArrayUtils.contains(propertyNames, "updateBy");
		hasUpdateTime = ArrayUtils.contains(propertyNames, "updateTime");
		if (filterHqlMap.get("default") == null) {
			filterHqlMap.put("default", getDefaultAllHql());
		}
		initEntityDao();
	}

	protected void initEntityDao() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.dao.impl.GenericDao#isUnique(java.lang.Object,
	 *      java.lang.String)
	 */
	public boolean isUnique(final T entity, final String... uniquePropertyNames) {
		Assert.notNull(uniquePropertyNames);
		Criteria criteria = createCriteria().setProjection(
				Projections.rowCount());
		try {
			// 循环加入唯一列
			for (String name : uniquePropertyNames) {
				criteria.add(Restrictions.eq(name, PropertyUtils.getProperty(
						entity, name)));
			}

			// 以下代码为了如果是update的情况,排除entity自身.

			// 取得entity的主键值
			Serializable id = getId(entity);

			// 如果id!=null,说明对象已存在,该操作为update,加入排除自身的判断
			if (id != null) {
				criteria.add(Restrictions.not(Restrictions.eq(idName, id)));
			}
		} catch (Exception e) {
			ReflectionUtils.handleReflectionException(e);
		}
		return (Integer) criteria.uniqueResult() == 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.dao.impl.GenericDao#load(java.io.Serializable)
	 */
	public final T loadById(Serializable id) {
		return (T) getHibernateTemplate().load(entityClass, id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.dao.GenericDao#loadForUpdate(java.io.Serializable)
	 */
	public T loadForUpdate(Serializable id) {
		return (T) getHibernateTemplate().load(entityClass, id,
				LockMode.UPGRADE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.dao.impl.GenericDao#save(java.lang.Object)
	 */
	public void save(T entity) {
		if (!(entity instanceof BaseObject)) {
			getHibernateTemplate().saveOrUpdate(entity);
			return;
		}

		BaseObject baseObj = (BaseObject) entity;
		if (hasUpdateBy) {
			baseObj.setUpdateBy(RequestContext.getCurrentUserId());
		}
		if (hasUpdateTime) {
			baseObj.setUpdateTime(new Date());
		}
		if (baseObj.getId() == null) {// is create
			if (hasCreateBy) {
				baseObj.setCreateBy(RequestContext.getCurrentUserId());
			}
			if (hasCreateTime) {
				baseObj.setCreateTime(new Date());
			}
		} else {// is update
			if (hasVersion) {
				// 缺省是忽略版本，真正需要版本控制的子类要在初始化方法设置hasVersion为假跳过，并自己控制版本。
				/**
				 * if (getHibernateTemplate().contains(baseObj)) {
				 * logger.warn("found this in session already:" + baseObj);
				 * getHibernateTemplate().evict(baseObj); }
				 * 
				 * BaseObject persistedInstance = (BaseObject)
				 * loadForUpdate(baseObj .getId());
				 * baseObj.setVersion(persistedInstance.getVersion());
				 * getHibernateTemplate().evict(persistedInstance);
				 */
			}
		}

		getHibernateTemplate().saveOrUpdate(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.dao.GenericDao#saveOrUpdateAll(java.util.Collection)
	 */
	public void saveOrUpdateAll(Collection<T> col) {
		getHibernateTemplate().saveOrUpdateAll(col);
	}
}