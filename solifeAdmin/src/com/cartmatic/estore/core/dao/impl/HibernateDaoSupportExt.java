/**
 * 
 */

package com.cartmatic.estore.core.dao.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.impl.CriteriaImpl;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.util.Assert;

import com.cartmatic.estore.core.model.PagingBean;
import com.cartmatic.estore.core.search.SearchCriteria;
import com.cartmatic.estore.core.search.SearchCriteriaBuilder;
import com.cartmatic.estore.core.search.SearchFilter;
import com.cartmatic.estore.core.util.BeanUtils;
import com.cartmatic.estore.core.util.HibernateUtils;
import com.cartmatic.estore.core.util.StringUtil;

/**
 * 主要定义不支持范型的方法、常用的方法。
 * 
 * @author Ryan
 * 
 */
@SuppressWarnings("unchecked")
public class HibernateDaoSupportExt extends HibernateDaoSupport {

	public final static String formatMsg(String msgWithFormat, Object... args) {
		return StringUtil.formatNoBracket(msgWithFormat, args).toString();
	}

	Map<String, String>	filterHqlMap	= new HashMap<String, String>();

	List<SearchFilter>	searchFilters	= new ArrayList<SearchFilter>();

	/*
	 * 清空会话缓存
	 * 
	 * @see com.cartmatic.estore.core.dao.impl.GenericDao#clear()
	 */
	public final void clear() {
		getHibernateTemplate().clear();
	}

	/**
	 * 不支持Group By和UNION。只是简单的取FROM和ORDER BY之间的语句（去除select和order子句）。
	 * 
	 * @param hql
	 * @param paramValues
	 * @return
	 */
	public final Long countByHql(final String hql, final Object... paramValues) {
		if(hql.toLowerCase().indexOf("distinct")!=-1 || hql.toLowerCase().indexOf(" group by")!=-1){
			String sql=HibernateUtils.hqlToSql(hql, this.getSession());
			SQLQuery sqlQuery=this.getSession().createSQLQuery("select count(*) from ("+sql+") tempTable");
			if (paramValues != null) { 
				for (int i = 0; i < paramValues.length; i++) {
					sqlQuery.setParameter(i, paramValues[i]);
				}
			}
			BigInteger count=(BigInteger)sqlQuery.uniqueResult();
			return count.longValue();
		}
		return (Long) findUnique("select count(*) " + getCountAllQl(hql),
				paramValues);
	}

	protected final int countQlParameters(final String ql,
			final Object... paramValues) {
		int count = 0;
		int idx = ql.indexOf('?');
		while (idx > 0) {
			count++;
			idx = ql.indexOf('?', idx + 1);
		}
		if (count != paramValues.length) {
			logger
					.warn(formatMsg(
							"Parameters not matched (when counting, it's ok). Expected=[%1], found=[%2]. Ql=[%3]. paramValues=[%4].",
							count, paramValues.length, ql, paramValues));
		}
		return count;
	}

	/**
	 * 
	 * @param queryString
	 * @param firstResult
	 *            从0计起
	 * @param maxResults
	 * @param paramValues
	 * @return
	 */
	protected final List find(final String queryString, final int firstResult,
			final int maxResults, final Object... paramValues) {
		return (List) getHibernateTemplate().executeWithNativeSession(new HibernateCallback() {
			public Object doInHibernate(Session session) {
				Query queryObject = session.createQuery(queryString);
				// queryObject.setReadOnly(true);
				if (maxResults > 0) {
					queryObject.setFirstResult(firstResult);
					queryObject.setMaxResults(maxResults);
					queryObject.setFetchSize(maxResults);
				}
				SessionFactoryUtils.applyTransactionTimeout(queryObject,
						getSessionFactory());
				if (paramValues != null) {
					for (int i = 0; i < countQlParameters(queryString,
							paramValues); i++) {
						queryObject.setParameter(i, paramValues[i]);
					}
				}

				return queryObject.list();
			}
		});
	}

	public final List findByFilter(final String filtername,
			final Object... paramValues) {
		Assert.hasText(filtername);
		return getHibernateTemplate().find(getFilterHqlByName(filtername),
				paramValues);
	}

	/**
	 * 根据ql(query language, 如sql/hql...)查询，可以有0..n个参数
	 * 
	 * @param paramValues
	 *            可变参数
	 */
	public final List findByHql(final String ql, final Object... paramValues) {
		Assert.hasText(ql);
		return getHibernateTemplate().find(ql, paramValues);
	}

	public final List findByHqlPaged(final String queryString,
			final int firstResult, final int maxResults,
			final Object[] paramValues) {
		return (List) getHibernateTemplate().executeWithNativeSession(new HibernateCallback() {
			public Object doInHibernate(Session session) {
				Query queryObject = session.createQuery(queryString);

				if (maxResults > 0) {
					queryObject.setFirstResult(firstResult);
					queryObject.setMaxResults(maxResults);
					queryObject.setFetchSize(maxResults);
				}
				SessionFactoryUtils.applyTransactionTimeout(queryObject,
						getSessionFactory());
				if (paramValues != null) {
					for (int i = 0; i < countQlParameters(queryString,
							paramValues); i++) {
						queryObject.setParameter(i, paramValues[i]);
					}
				}

				return queryObject.list();
			}
		});
	}

	public final Object findUnique(final String queryString,
			final Object... paramValues) {
		try {
			return getHibernateTemplate().executeWithNativeSession(new HibernateCallback() {
				public Object doInHibernate(Session session) {
					Query queryObject = session.createQuery(queryString);
					SessionFactoryUtils.applyTransactionTimeout(queryObject,
							session.getSessionFactory());
					if (paramValues != null) {
						for (int i = 0; i < countQlParameters(queryString,
								paramValues); i++) {
							queryObject.setParameter(i, paramValues[i]);
						}
					}
					return queryObject.uniqueResult();
				}
			});
		} catch (Throwable e) {
			throw new RuntimeException(formatMsg(
					"Query error. Hql=%1, paramValues=%2", queryString,
					paramValues), e);
		}
	}

	/*
	 * 刷新会话、提交更新数据。要实现MVC设计，以及基于接口设计，这方法就不应该提供。所以这里只是保留。刷新应该通过其他方法来控制，例如事务，或HibernateUtil。
	 * 
	 * @see com.cartmatic.estore.core.dao.impl.GenericDao#flush()
	 */
	public final void flush() {
		getHibernateTemplate().flush();
	}

	/**
	 * 不支持Group By和UNION。只是简单的取FROM和ORDER BY之间的语句（去除select和order子句）。
	 * 
	 * @param ql
	 * @return
	 */
	public final String getCountAllQl(final String ql) {
		String formatedHql = ql.toUpperCase();
		int idx = formatedHql.indexOf("FROM ");
		Assert.isTrue(idx != -1, " hql : " + ql + " must has a keyword 'from'");
		int idx2 = formatedHql.indexOf(" ORDER BY ");
		if (idx2 == -1) {
			idx2 = ql.length();
		}
		return ql.substring(idx, idx2);
	}

	/**
	 * 取得Filter对应的Hql。Hql在dao的配置文件设置。
	 * 
	 * @param filterName
	 * @return
	 */
	protected final String getFilterHqlByName(String filterName) {
		Assert.notNull(filterName);
		String hql = filterHqlMap.get(filterName);
		Assert.notNull(hql, formatMsg("Filter hql cannot be found for: %1.",
				filterName));
		return hql;
	}

	public SearchCriteriaBuilder getSearchCriteriaBuilder(String filterName) {
		return SearchCriteriaBuilder.getInstance(filterName,
				getFilterHqlByName(filterName));
	}

	public final void initialize(Object obj) {
		Hibernate.initialize(obj);
	}

	protected final void lock(Object obj, LockMode mode) {
		getHibernateTemplate().lock(obj, mode);
	}

	public final long queryForLong(String ql, Object... paramValues) {
		return (Long) findByHql(ql, paramValues).get(0);
	}

	/**
	 * 注意：sc会被更新，以回传总记录数，并更新分页信息等。根据Filter来确定基本的HQL，并支持动态条件和参数。
	 * 
	 * @param sc
	 * @return
	 */
	public final List searchByCriteria(SearchCriteria sc) {
		List searchResult = null;
		if (sc.getHql() != null) {
			String hql = sc.getHql();
			// 用于页面输出，用户可以选择Filter
			sc.setSearchFilters(searchFilters);

			try {
				long count = countByHql(hql, sc.getParamValues()).intValue();
				sc.setTotalCount(count);
				if (count == 0){
					searchResult = new ArrayList();
				}else{
				    searchResult = findByHqlPaged(hql, sc.getStartIdx(), sc
		                            .getFetchSize(), sc.getParamValues());
				}
			} catch (Throwable e) {
				throw new RuntimeException(formatMsg("Query error! [%1]", sc),
						e);
			}
		} else if (sc.getCriteria() != null) {
			// FIXME 未测试
			Criteria criteria = sc.getCriteria();
			Assert.isTrue(sc.getPageNo() >= 1, "pageNo should start from 1");
			CriteriaImpl impl = (CriteriaImpl) criteria;

			// 先把Projection和OrderBy条件取出来,清空两者来执行Count操作
			Projection projection = impl.getProjection();
			List<CriteriaImpl.OrderEntry> orderEntries;
			try {
				orderEntries = (List) BeanUtils.forceGetProperty(impl,
						"orderEntries");
				BeanUtils.forceSetProperty(impl, "orderEntries",
						new ArrayList());
			} catch (Exception e) {
				throw new InternalError(
						" Runtime Exception impossibility throw ");
			}

			// 执行查询
			long count = (Long) criteria.setProjection(Projections.rowCount())
					.uniqueResult();

			// 将之前的Projection和OrderBy条件重新设回去
			criteria.setProjection(projection);
			if (projection == null) {
				criteria
						.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
			}

			try {
				BeanUtils.forceSetProperty(impl, "orderEntries", orderEntries);
			} catch (Exception e) {
				throw new InternalError(
						" Runtime Exception impossibility throw ");
			}

			sc.setTotalCount(count);

			// 返回分页对象
			if (count == 0) {
				searchResult = new ArrayList();
			} else {
				searchResult = criteria.setFirstResult(sc.getStartIdx())
						.setMaxResults(sc.getFetchSize()).list();
			}
		}

		if (logger.isDebugEnabled()) {
			logger
					.debug(formatMsg(
							"Search Criteria Details: [%1]. Search result count: [%2].",
							sc, searchResult.size()));
		}
//  20081110 不需要在sc中记录查询结果，这个作用不大。 维护也麻烦，容易有bug，现在取消这个模式。
//		if (sc.getSaveSearchResultsAllowed() && searchResult != null
//				&& searchResult.size() > 0) {
//			sc.saveSearchResultIds(searchResult);
//		}

		return searchResult;
	}

	public void setSearchFilters(List<String> searchFilterDefinitions) {
		for (Iterator iter = searchFilterDefinitions.iterator(); iter.hasNext();) {
			String filterDefinition = (String) iter.next();
			String[] parsedValues = StringUtil.toArrayByDel(filterDefinition,";;");
			SearchFilter filter = new SearchFilter();
			filter.setFilterName(parsedValues[0]);
			if (parsedValues.length > 2) {
				filter.setAllow(parsedValues[2]);
			}
			//最后一项的配置，如果是"hidden"这个关键字则设置这个filter为hidden.
			//前台的jsp不可见这个filter。
			if (!"hidden".equals(parsedValues[parsedValues.length -1]))
			{
			    //filter.setHidden(true);
			    searchFilters.add(filter);
			}
			filterHqlMap.put(filter.getFilterName(), parsedValues[1]);
		}
	}
	

	/**
	 * 针对对需要分页,但又没有controller的情况.
	 * 如某些分页的定时任务.
	 * @param queryString
	 * @param pb
	 * @param paramValues
	 * @return
	 */
	public List find(final String queryString, PagingBean pb,final Object... paramValues){
		if (pb != null) {
			int count = countByHql(queryString, paramValues).intValue();
			pb.setNumberOfItems(count);
			if (count == 0) {
				return new ArrayList();
			} else {
				return find(queryString, pb.getFirstResult(), pb.getMaxResults(),paramValues);
			}
		}
		return find(queryString, 0, 0,paramValues);
	}

}
