/**
 * 
 */

package com.cartmatic.estore.core.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import com.cartmatic.estore.core.search.SearchCriteria;
import com.cartmatic.estore.core.search.SearchCriteriaBuilder;

/**
 * 不涉及具体实现的基于范型的DAO接口。也会提供一些常用的不限制使用范型的接口。 注意事项及使用指引：
 * <P>
 * 涉及具体实现（如Hibernate）的方法不应该公开，例如Criteria、Hql等，这些应该是在内部处理完，把结果返回给使用者。否则会导致使用者（如Manager）有很多Hql等。
 * <P>
 * 其他：与Manager/Service相比，dao其实可以不基于接口编程，因为一般都是mgr与dao一一对应，不共享，而我们也不打算支持多种数据库访问策略。
 * 
 * @author Ryan
 * 
 * @param <T>
 */
public interface GenericDao<T> {

	public long countByProperty(String propertyName, Object value);

	/**
	 * 删除对象。
	 */
	public void delete(T entity);

	public void evict(T entity);
	
	public void merge(T entity);
	/**
	 * 根据属性名和属性值查询对象.
	 * 
	 * @return 符合条件的对象列表
	 */
	public List<T> findByProperty(String propertyName, Object value);

	/**
	 * 根据属性名和属性值查询对象,带排序参数.
	 */
	public List<T> findByPropertyOrdered(String propertyName, Object value,
			String orderBy, boolean isAsc);

	/**
	 * 根据属性名和属性值查询唯一对象。
	 * 
	 * @return 符合条件的唯一对象 or null if not found.
	 */
	public T findUniqueByProperty(String propertyName, Object value);

	public void flush();

	/**
	 * 获取全部对象。
	 */
	public List<T> getAll();

	/**
	 * 获取全部对象,带排序字段与升降序参数。
	 */
	public List<T> getAllOrdered(String orderBy, boolean isAsc);

	/**
	 * 根据ID获取对象。如果对象不存在，返回null。
	 */
	public T getById(Serializable id);

	/**
	 * 主要给TestCase使用
	 * 
	 * @return
	 */
	public T getSampleEntity();

	/**
	 * 返回SearchCriteriaBuilder实现（需要指定Filter的名称），用于构造新的SearchCriteria
	 * 
	 * @param filterName
	 * @return
	 */
	public SearchCriteriaBuilder getSearchCriteriaBuilder(String filterName);

	/**
	 * 判断对象某些属性的值在数据库中是否唯一。
	 * 
	 * @param uniquePropertyNames
	 *            在POJO里不能重复的属性列表,以逗号分割 如"name,loginid,password"
	 */
	public boolean isUnique(T entity, String... uniquePropertyNames);

	/**
	 * 根据ID获取对象。如果对象不存在，抛出异常。
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
	 * 保存或创建对象。
	 */
	public void save(T entity);

	/**
	 * Save or update all instances in the collection.
	 * 
	 * @param col
	 *            A collections of instances to be saved or updated
	 */
	public void saveOrUpdateAll(Collection<T> col);

	/**
	 * 根据SearchCriteria进行搜索，注意传入的SearchCriteria会被更新
	 * 
	 * @param sc
	 * @return
	 */
	public List searchByCriteria(SearchCriteria sc);
}