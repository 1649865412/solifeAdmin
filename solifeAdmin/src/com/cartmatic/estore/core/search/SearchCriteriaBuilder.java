
package com.cartmatic.estore.core.search;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;
import org.springframework.web.bind.ServletRequestUtils;

import com.cartmatic.estore.common.util.DateConverter;

/**
 * 从Request自动构造SearchCriteria的工具类，目前支持HQL，可以扩展支持Criteria。
 * <P>
 * 由于需要支持Filter，HQL无法只是根据Request就确定下来，需要在DAO获得Filter的Hql后才能最终确定，因此需要先用Filter取得Hql，再根据Request创建。
 * 
 * @author Ryan
 * 
 */
public class SearchCriteriaBuilder {
	private final static transient Log	logger	= LogFactory
														.getLog(SearchCriteria.class);

	public final static SearchCriteriaBuilder getInstance(String filterName,
			String filterHql) {
		SearchCriteriaBuilder hqlBuilder = new SearchCriteriaBuilder();
		hqlBuilder.initBaseHql(filterHql);
		hqlBuilder.filterName = filterName;
		return hqlBuilder;
	}

	private StringBuffer		bufWhere				= new StringBuffer();

	/**
	 * 当用户没有指定排序字段的时候缺省的排序字段
	 */
	private String				defaultOrderByClause	= null;

	private String				filterName				= "default";

	/**
	 * 用于主表以及固定的连接表
	 */
	private String				fromClause				= "";

	/**
	 * 用于动态连接表，如I18N
	 */
	private String				fromClause2				= "";

	private String				hql;

	private String				orderByClause			= "";

	// 储存页面传入的参数及其原来的值，可以用来重现－编辑等，这样保存的SC与新的都通过统一方式操作
	private Map<String, Object>	paramMap				= new LinkedHashMap<String, Object>();

	// 储存分析/转换过的值，目的是传给Hql/Criteria用；除了储存Where子句的参数，也储存Order
	// by子句的参数，因此不能和paramMap合并。
	private List<Object>		params					= new ArrayList<Object>();

	private String				selectClause			= "";

	private String				whereClause				= "";

	/*
	 * add a search criteria/filter column will parse parameter name, which
	 * should be in format: COL$columnName$dataType$operator$joinCondition
	 * 
	 */
	private void addFilterColumn(final String paramName, final String paramValue) {
		if (paramValue != null && !"".equals(paramValue.trim())) {
			// 储存原始的参数名和其值
			paramMap.put(paramName, paramValue);

			StringTokenizer st = new StringTokenizer(paramName, "@");
			st.nextToken();// is "COL@", skip only;

			/**
			 * filter column name, format: entity.columnName; entity is optional
			 */
			String columnName = st.nextToken();

			// data type processing
			String dataType = "String";// default
			if (st.hasMoreTokens()) {
				dataType = st.nextToken();// supported data type, Integer,
			}
			Object convertedValue = null;
			if ("String".equals(dataType)) {
				convertedValue = paramValue;
			} else if ("Integer".equals(dataType)) {
				convertedValue = new Integer(paramValue);
			} else if ("Short".equals(dataType)) {// Short Type
				convertedValue = new Short(paramValue);
			} else if ("Date".equals(dataType)) {
				DateConverter dateConverter = new DateConverter();
				convertedValue = dateConverter.convert(Date.class, paramValue);
			}else if ("Date_Begin".equals(dataType)) {
				DateConverter dateConverter = new DateConverter();
				convertedValue = dateConverter.convert(Timestamp.class, paramValue+" 00:00:00");
			}else if ("Date_End".equals(dataType)) {
				DateConverter dateConverter = new DateConverter();
				convertedValue = dateConverter.convert(Timestamp.class, paramValue+" 23:59:59");
			} else if ("BigDecimal".equals(dataType)) {
				convertedValue = new BigDecimal(paramValue);
			} else {// data type not supported, use String
				logger
						.warn("Found datatype not supported [check syntax needed]:"
								+ dataType);
				convertedValue = paramValue;
			}

			/**
			 * Operator processing. EQUAL (=),NOT (not equal, <>),LIKE,GTE
			 * (greater than or equal, >=),GT (>),STE ( <=),ST ( <) Note:
			 * between is not supported, but can use x>10 and x <20 instead. to
			 * use 2 identical columns, add a mark to make them different;like:
			 * COL@LocaleCode@EQL@Integer@1, and COL@LocaleCode@EQL@Integer@2
			 */
			String operator = " = ?";// default
			if (st.hasMoreTokens()) {
				String operatorMark = st.nextToken();
				if ("LIKE".equals(operatorMark)) {
					// data type must be String
					operator = " like ?";
					if (paramValue.indexOf("%") == -1) {
						convertedValue = "%" + paramValue + "%";
					} else {
						convertedValue = paramValue;
					}
				}else if ("RLIKE".equals(operatorMark)) {
					// data type must be String
					operator = " like ?";
					convertedValue = paramValue+"%";;
				}else if ("LLIKE".equals(operatorMark)) {
					// data type must be String
					operator = " like ?";
					convertedValue = "%"+paramValue;;
				} else if ("NOT".equals(operatorMark)) {
					operator = " <> ?";
				} else if ("GTE".equals(operatorMark)) {
					operator = " >= ?";
				} else if ("STE".equals(operatorMark)) {
					operator = " <= ?";
				} else if ("GT".equals(operatorMark)) {
					operator = " > ?";
				} else if ("LTE".equals(operatorMark)) {
					operator = " <= ?";
				} else if ("LT".equals(operatorMark)) {
					operator = " < ?";
				}
				// else use default: = ?
			}
			// TODO:加入对JOIN的支持，用JC:开头
			// if (st.hasMoreTokens()), ignore, but can be used to make
			// identical column be processed more than 1 times

			params.add(convertedValue);

			// 把条件添加到Where子句
			appendWhereCondition(columnName, operator);
		} // else user donot specify a value, no further processing
	}

	private void appendWhereCondition(String column, String operator) {
		if (bufWhere.length() == 0) {
			bufWhere.append("where ");
		} else {
			bufWhere.append(" and ");
		}
		bufWhere.append(column).append(operator);
	}

	/**
	 * 
	 * @param request
	 * @param defaultHql
	 * @return
	 */
	public SearchCriteria buildSearchCriteria(HttpServletRequest request,
			int defaultPageSize) {

		int pageNo = ServletRequestUtils.getIntParameter(request, "PrmPageNo",1);
		if (pageNo < 1) {
			pageNo = 1;
		}
		int pageSize = ServletRequestUtils.getIntParameter(request, "PrmItemsPerPage", defaultPageSize);
		// 某些情况下需要取得全部数据，例如数据导出的时候，用NoPaging控制
		if (request.getParameter("NoPaging") != null) {
			pageSize = 0;
		}

		// 对于ShowAll，应作为一个filter的特例处理，例如disallow所有条件
		// 处理用户输入的动态查询条件，可选（可以没有动态的查询条件）
		Map params = request.getParameterMap();
		for (Iterator iter = params.keySet().iterator(); iter.hasNext();) {
			String paramName = (String) iter.next();
			String paramValue = ServletRequestUtils.getStringParameter(request, paramName,"").trim();
			//request.getParameter(paramName);

			if (paramName.startsWith("COL@")) {
				// is a search criteria/filter column
				// parse parameter, should be in format:
				// COL$columnName$dataType$operator$(reserved)
				// hqlBuilder里面并会储存request里面的参数名/值对
				addFilterColumn(paramName, paramValue);
			} else {
				paramMap.put(paramName, paramValue);
			}
		}

		// 保存以用于页面重新绑定，如果上面已经保存了会覆盖，下同
		paramMap.put(SearchCriteria.PRM_FILTER_NAME, this.filterName);

		// order by clause, optional
		String orderBy = request.getParameter("SRH@orderBy");
		if (orderBy != null && !"".equals(orderBy)) {
			// hqlBuilder里面并会储存SRH@orderBy参数和值；如果要支持多个字段排序，SRH@orderDirection应该为null
			setOrderBy(orderBy, request.getParameter("SRH@orderDirection"));
		}

		// 需要页面重新绑定的时候，需要保存paramMap
		SearchCriteria sc = com.cartmatic.estore.core.search.SearchCriteria
				.getHqlPagingInstance(getHql(), getParamValues(), pageNo,
						pageSize, paramMap);

		return sc;
	}

	private String getHql() {
		if (hql == null) {
			StringBuffer buf = new StringBuffer();
			buf.append(selectClause);
			buf.append(fromClause).append(fromClause2).append(" ");
			buf.append(getWhereClause()).append(" ");
			buf.append(getOrderByClause());
			hql = StringUtils.replace(buf.toString(), "  ", " ");
		}
		return hql;
	}

	private String getOrderByClause() {
		if (orderByClause.length() == 0) {
			if (defaultOrderByClause != null) {
				orderByClause = " order by " + defaultOrderByClause;
			}
		}

		return orderByClause;
	}

	private Object[] getParamValues() {
		return params == null ? new Object[0] : params.toArray();
	}

	private String getWhereClause() {
		if (whereClause.length() == 0) {
			whereClause = bufWhere.toString();
			bufWhere = null;
		}

		return whereClause;
	}

	/**
	 * 设置基本的Hql，用来初始化；并可以作为缺省的Hql（当没有参数的时候）
	 * 
	 * @param in_hql
	 */
	private void initBaseHql(String in_hql) {

		String in_hql2 = in_hql.toUpperCase();
		Assert
				.doesNotContain(
						in_hql2,
						"GROUP BY",
						"Group by is not supported in Search Criteria Default Implementation. Implement your own!");
		Assert
				.doesNotContain(
						in_hql2,
						" UNION ",
						"UNION is not supported in Search Criteria Default Implementation. Implement your own!");

		int idxSelect = in_hql2.indexOf("SELECT ");
		int idxFrom = in_hql2.indexOf("FROM ");
		int idxWhere = in_hql2.indexOf(" WHERE ");
		int idxOrderBy = in_hql2.indexOf(" ORDER BY ");

		Assert.isTrue(idxFrom > -1, "HQL must have FROM keyword!");

		if (idxSelect >= 0) {
			selectClause = in_hql.substring(idxSelect, idxFrom);
		}

		int idxEndOfFrom = (idxWhere > 0) ? idxWhere
				: (idxOrderBy > 0 ? idxOrderBy : in_hql.length());
		fromClause = in_hql.substring(idxFrom, idxEndOfFrom);

		if (idxWhere > 0) {
			// 缺省的Where子句，而且即使有用户输入的查询条件，本条件也会添加上去。
			String defaultWhereClause = in_hql.substring(idxWhere,
					idxOrderBy > 0 ? idxOrderBy : in_hql.length());
			bufWhere.append(defaultWhereClause);
		}

		if (idxOrderBy > 0) {
			defaultOrderByClause = in_hql.substring(idxOrderBy
					+ " ORDER BY ".length(), in_hql.length());
		}

		Assert
				.hasText(fromClause,
						"Search criteria base hql invalid, from clause cannot be empty.");
	}

	/**
	 * 说明：试过用order by
	 * ?参数控制，但不生效，所以这里不使用参数。如果要支持多个字段排序，SRH@orderDirection应该为null。
	 * 
	 * @param orderByColumns
	 */
	private void setOrderBy(String orderByColumn, String direction) {
		StringBuilder sb = new StringBuilder(" order by ")
				.append(orderByColumn);
		if (direction != null) {
			sb.append(" ").append(direction).toString();
		}
		this.orderByClause = sb.toString();
		// 页面可以使用这个保存的参数重新绑定
		paramMap.put("SRH@orderBy", orderByColumn);
		paramMap.put("SRH@orderDirection", direction);
	}
}
