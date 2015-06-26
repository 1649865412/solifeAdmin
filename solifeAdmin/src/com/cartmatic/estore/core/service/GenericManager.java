/**
 * 
 */

package com.cartmatic.estore.core.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.cartmatic.estore.core.search.SearchCriteria;
import com.cartmatic.estore.core.search.SearchCriteriaBuilder;

/**
 * 不涉及具体实现的基于范型的Manager接口。Manager/Service一定要基于接口编程，这样可以很方便的注入其他Bean，而且又不会与实现太过耦合。
 * 
 * @author Ryan
 * 
 * @param <T>
 */
public interface GenericManager<T> {

	/**
	 * 缺省只是支持id数据类型为Integer的，一般直接从Web层次取得的是String。
	 * 
	 * @param ids
	 */
	public void deleteAllByIds(String[] ids);

	/**
	 * @param id
	 * @see com.cartmatic.estore.core.dao.GenericDao#deleteById(java.io.Serializable)
	 */
	public T deleteById(Serializable id);

	/**
	 * 
	 * @param entity
	 */
	public void evict(T entity);
	/**
	 * 
	 * @param entity
	 */
	public void merge(T entity);
	/**
	 * @param propertyName
	 * @param value
	 * @return
	 * @see com.cartmatic.estore.core.dao.GenericDao#findByProperty(java.lang.String,
	 *      java.lang.Object)
	 */
	public List<T> findByProperty(String propertyName, Object value);

	/**
	 * @param propertyName
	 * @param value
	 * @param orderBy
	 * @param isAsc
	 * @return
	 * @see com.cartmatic.estore.core.dao.GenericDao#findByPropertyOrdered(java.lang.String,
	 *      java.lang.Object, java.lang.String, boolean)
	 */
	public List<T> findByPropertyOrdered(String propertyName, Object value,
			String orderBy, boolean isAsc);

	public void flush();

	/**
	 * @return
	 * @see com.cartmatic.estore.core.dao.GenericDao#getAll()
	 */
	public List<T> getAll();

	/**
	 * @param orderBy
	 * @param isAsc
	 * @return
	 * @see com.cartmatic.estore.core.dao.GenericDao#getAllOrdered(java.lang.String,
	 *      boolean)
	 */
	public List<T> getAllOrdered(String orderBy, boolean isAsc);

	/**
	 * @param id
	 * @return
	 * @see com.cartmatic.estore.core.dao.GenericDao#getById(java.io.Serializable)
	 */
	public T getById(Serializable id);

	/**
	 * 返回SearchCriteriaBuilder实现（需要指定Filter的名称），用于构造新的SearchCriteria
	 * 
	 * @param filterName
	 * @return
	 */
	public SearchCriteriaBuilder getSearchCriteriaBuilder(String filterName);

	/**
	 * @param id
	 * @return
	 * @see com.cartmatic.estore.core.dao.GenericDao#loadById(java.io.Serializable)
	 */
	public T loadById(Serializable id);

	/**
	 * 为解决保存时一个session多个事务问题（先load后save；如果是单个事务内，并不需要显式加锁）提供的加锁的load方法，一般在表单页的saveXXXAction开始读数据的时候用。
	 * 
	 * @param id
	 * @return
	 */
	public T loadForUpdate(Serializable id);

	/**
	 * 保存数据。难点是关联处理、事务控制、版本控制、数据类型转换等。框架无法自动处理，所以子类需要在onSave方法处理。
	 * 
	 * @param entity
	 * @see com.cartmatic.estore.core.dao.GenericDao#save(Object)
	 */
	public void save(T entity);

	/**
	 * 建议使用更安全的saveOrUpdateAll(Map<Serializable, Map<String, Object>>
	 * multiUpdateModel);
	 * 
	 * @param col
	 * @see com.cartmatic.estore.core.dao.GenericDao#saveOrUpdateAll(Collection)
	 */
	@Deprecated
	public void saveOrUpdateAll(Collection<T> col);

	/**
	 * 比较安全的批量更新方法，Web层只是传入需要更新的id和属性对，但不能支持复杂的关联处理、验证等。要求数据要先转换为正确的类型。
	 * 
	 * @param multiUpdateModel
	 */
	public void saveOrUpdateAll(
			Map<Integer, Map<String, Object>> multiUpdateModel);

	/**
	 * 根据SearchCriteria进行搜索，注意传入的SearchCriteria会被更新
	 * 
	 * @param sc
	 * @return
	 */
	public List searchByCriteria(SearchCriteria sc);
}