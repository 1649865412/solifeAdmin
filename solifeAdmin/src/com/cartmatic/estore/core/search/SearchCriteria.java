/**
 * 
 */

package com.cartmatic.estore.core.search;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.Criteria;
import org.springframework.util.Assert;

/**
 * 目标：统一的搜索条件。集成搜索条件（参数名和值）、分页、排序、搜索保存和重用等功能。
 * <P>
 * 使用流程：
 * <ol>
 * <li>在Controller用SearchCriteriaBuilder提供的方法（有分页、Hql或Criteria、参数等几种组合方案）创建SC，对于ExportAll也应该在这层判断</li>
 * <li>Manager层只是负责在Controller和Dao层之间传输SearchCriteria，一般不需特别处理</li>
 * <li>Dao根据SearchCriteria进行查询，并设置查询结果列表，以及总记录数；所以传入的SC会被更新再回传</li>
 * <li>JSP显示和处理SearchCriteria，例如显示结果，绑定分页信息，恢复查询条件等；Controller层也可进行一些额外的处理</li>
 * <li>Filter对应的HQL也可支持参数。支持多个Filter。所有搜索都基于Filter，缺省是default，但也可只使用一个Filter，这样相当于不使用Filter。</li>
 * </ol>
 * 其他说明:重构后，基于Filter，对应的HQL只能在Dao编写，不得再出现在Controller。
 * 
 * @author Ryan
 * 
 */
public class SearchCriteria implements Serializable {

	public final static String				PRM_CURRENT_PAGE_NO	= "PrmPageNo";

	public final static String				PRM_ITEMS_PER_PAGE	= "PrmItemsPerPage";
	
	public final static String	PRM_FILTER_NAME		= "SRH@filter";

	private static final long	serialVersionUID	= -6898898403105146143L;

	private int					addParamIdx			= 0;

	private Criteria			criteria;

	private int					fetchSize;		// 每页的记录数，用来覆盖pageSize，在某些情况下进行优化。

	private String				hql;

	private boolean				isSaveSearchResultsAllowed	= false;

	private int					pageCount;	// 总页数

	private int					pageNo;		// 当前页码

	private int					pageSize;	// 每页的记录数，为0的时候表示不分页

	// 储存页面传入的参数，可以用来重现－编辑等，这样保存的SC与新的都通过统一方式操作
	private Map<String, Object>	paramMap			= null;

	private Object[]			paramValues			= null;

	private String				savedUri;
	
	List<SearchFilter>			searchFilters		= new ArrayList<SearchFilter>();

	//private List<Serializable>	searchResultIds;

	private int					startIdx;			// 当前页第一条数据在List中的位置,从0开始

	private long				totalCount;			// 总记录数

	/**
	 * 利用hibernate的criteria来进行查询.
	 * @param in_criteria
	 * @param in_paramValues
	 * @param in_pageNo
	 * @param in_pageSize
	 * @param in_paramMap
	 * @return
	 */
	public final static SearchCriteria getCriteriaPagingInstance(
                    Criteria in_criteria, 
                    Object[] in_paramValues, 
                    int in_pageNo,
                    int in_pageSize, 
                    Map<String, Object> in_paramMap) 
    {
        SearchCriteria sc = new SearchCriteria(null, in_paramValues, in_pageNo, in_pageSize, in_paramMap);
        sc.criteria = in_criteria;
        return sc;
    }

    public final static SearchCriteria getHqlPagingInstance(
                    String in_hql,
                    Object[] in_paramValues,
                    int in_pageNo,
                    int in_pageSize,
                    Map<String, Object> in_paramMap) 
    {
    	SearchCriteria sc = new SearchCriteria(in_hql, in_paramValues==null?new Object[0]:in_paramValues,in_pageNo, in_pageSize, in_paramMap);
        return sc;
    }
	/**
	 * 缺省的构造方法。
	 * 
	 * @param in_pageNo
	 *            请求的页码
	 * @param in_pageSize
	 *            本页容量
	 */
	private SearchCriteria(String in_hql, Object[] in_paramValues,
			int in_pageNo, int in_pageSize, Map<String, Object> in_paramMap) {
		Assert.isTrue(in_pageNo > 0 && in_pageSize >= 0,
				"pageNo must >0 and pageSize must >= 0!");
		this.hql = in_hql;
		this.pageSize = in_pageSize;
		this.pageNo = in_pageNo;
		startIdx = (pageSize == 0) ? 0 : (in_pageNo - 1) * in_pageSize;
		this.paramValues = in_paramValues;
		this.paramMap = in_paramMap;
	}

	/**
	 * 注意：新的参数会添加到数组的最后。所以调用的顺序应该和hql里面参数的顺序相同。
	 * 大部分参数的值是根据request动态设置的，但是对于filter里面的参数，可以通过这方法设置。 *
	 * 
	 * @param paramValue
	 */
	public void addParamValue(Object paramValue) {
		if (addParamIdx >= 0) {
			paramValues = ArrayUtils.add(paramValues, addParamIdx, paramValue);
			addParamIdx++;
		}
	}
	
	public void removeParamValues() {
		paramValues = ArrayUtils.EMPTY_OBJECT_ARRAY;
		addParamIdx = 0;
	}

	/**
	 * 修改页码，不改变搜索条件，因此可以重用
	 * 
	 */
	public void changePaging(int newPageNo, int newPageSize) {
		pageNo = newPageNo;
		pageSize = newPageSize;
	}

	/**
	 * @return the criteria
	 */
	public Criteria getCriteria() {
		return this.criteria;
	}

	/**
	 * 本次查询应该取多少条记录，主要是为记录数等于2页的时候进行优化，这时候不分页
	 * 
	 * @return
	 */
	public int getFetchSize() {
		return fetchSize;
	}

	/**
	 * @return the hql
	 */
	public String getHql() {
		return this.hql;
	}

	public int getPageNo() {
		return pageNo;
	}

	/**
	 * 取每页数据容量.
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @return the paramMap
	 */
	public Map<String, Object> getParam() {
		return this.paramMap;
	}

	/**
	 * @return the paramValues
	 */
	public Object[] getParamValues() {
		return this.paramValues;
	}

	public String getSavedUri() {
		return savedUri;
	}

	/**
	 * @return the isSaveSearchResultsAllowed
	 */
	public boolean getSaveSearchResultsAllowed() {
		return this.isSaveSearchResultsAllowed;
	}

	public List<SearchFilter> getSearchFilters() {
		return searchFilters;
	}

	/**
	 * @return the searchResultIds
	 */
//	public List<Serializable> getSearchResultIds() {
//		return this.searchResultIds;
//	}

	/**
	 * 0 base, starting from 0
	 * 
	 * @return
	 */
	public int getStartIdx() {
		return startIdx;
	}

	/**
	 * 取总记录数.
	 */
	public long getTotalCount() {
		return this.totalCount;
	}

	/**
	 * 总页数
	 * 
	 * @return
	 */
	public int getTotalPageCount() {
		return pageCount;
	}

	public void lockParamValues() {
		addParamIdx = -1;
	}

	/**
	 * Calculate and then cleanup
	 * 
	 */
	private void recalc() {
		pageCount = (int) Math.ceil((double) totalCount / (double) pageSize);
			startIdx = (pageNo - 1) * pageSize;
			fetchSize = pageSize;
	}

	/**
	 * 可以在搜索后的回调，保存搜索的结果，用于下一页等。
	 * 
	 * @param searchResult
	 */
//	public void saveSearchResultIds(List<BaseObject> searchResult) {
//		searchResultIds = new ArrayList<Serializable>();
//		for (Iterator iter = searchResult.iterator(); iter.hasNext();) {
//			BaseObject obj = (BaseObject) iter.next();
//			searchResultIds.add(obj.getId());
//		}
//	}

	public void setSavedUri(String savedUri) {
		this.savedUri = savedUri;
	}

	/**
	 * @param isSaveSearchResultsAllowed
	 *            the isSaveSearchResultsAllowed to set
	 */
	public void setSaveSearchResultsAllowed(boolean isSaveSearchResultsAllowed) {
		this.isSaveSearchResultsAllowed = isSaveSearchResultsAllowed;
	}

	/**
	 * 设置在页面显示的Filter。已知问题：无法过滤，所以只是支持很简单的情况，复杂的时候不应依赖Filter。
	 * 
	 * @param searchFilters
	 */
	public void setSearchFilters(List<SearchFilter> searchFilters) {
		this.searchFilters = searchFilters;
	}

	/**
	 * 一般是传SC给dao后由dao更新
	 * 
	 * @param in_totalCount
	 */
	public void setTotalCount(long in_totalCount) {
		this.totalCount = in_totalCount;
		recalc();
	}
	
	/**
	 * 在创建了SearchCriteria后,还有可能按需设置hql.
	 * 但如自己编写hql查询,利用SearchCriteria来进行分页查询.
	 * @param avalue
	 */
	public void setHql(String avalue)
	{
	    this.hql = avalue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this).append("hql", this.hql).append(
				"pageNo", this.pageNo).append("pageSize", this.pageSize)
				.append("startIdx", this.startIdx).append("paramValues",
						this.paramValues).append("paramMap", this.paramMap)
				.append("Results pre-count", this.totalCount).toString();
	}

}
