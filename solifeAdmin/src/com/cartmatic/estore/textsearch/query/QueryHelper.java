package com.cartmatic.estore.textsearch.query;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.client.solrj.response.RangeFacet;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.FacetParams;
import org.springframework.web.bind.ServletRequestUtils;

import com.cartmatic.estore.common.helper.ConfigUtil;
import com.cartmatic.estore.common.model.system.Store;
import com.cartmatic.estore.textsearch.model.CMFacetField;
import com.opensymphony.oscache.util.StringUtil;

public class QueryHelper {

	private static Pattern catalog_page_pattern = Pattern.compile("^.*_pn\\d{0,}.html");
	
	/**
	 * s1 = new arrival.
	 * s2 = Bestselling.
	 * s3 = Item Name.
	 * s4 = Price(Low to high).
	 * s5 = Price(High to low).
	 */
	private static final String[] SORT_PARAM = {"s1", "s2", "s3", "s4", "s5"};
	
	/**
	 * 默认1页显示24个items
	 */
	private static final int defaultRows = 24;
	/**
	 * 一页不能超过200
	 */
	private static final int maxRows = 200;
	
	/**
	 * 产品目录上的商品列表的查询
	 * @param request
	 * @param categoryId
	 * @return
	 */
	public static SolrQuery buildCatalogNav(HttpServletRequest request, Integer categoryId,Integer defaultPageSize)
	{
		Store store=ConfigUtil.getInstance().getStore();
		// 根据url分析当前页数.
		SolrQuery query = new SolrQuery("+(parentCategoryIds:"+categoryId+") + (displayable:true)");
		
		searchProductCondition(query, request);
		
		query.add("qt", store.getProductCategoryDisMaxRequestHandler());
		//添加排序参数
		appendSortParam(request, query);
		int pageNo = getPageNoByUri(request.getRequestURI());
		int pageSize = getPageSize(request, defaultPageSize);
		appendPagingParam(query, pageNo, pageSize);
		return query;
	}
	
	private static void searchProductCondition(SolrQuery query,HttpServletRequest request){
		String brandId=request.getParameter("brandId");
		if(StringUtils.isNotBlank(brandId)){
			try{
				request.setAttribute("brandId", new Integer(request.getParameter("brandId").trim()));
				query.add("fq", "brandId:"+request.getParameter("brandId").trim());
			}catch (Exception e){
			}
		}
		
		Double minPrice=null;
		if(request.getParameter("minPrice")!=null){
			try{
				minPrice=new Double(request.getParameter("minPrice").trim());
				request.setAttribute("minPrice", request.getParameter("minPrice").trim());
			}catch (Exception e){
			}
		}
		
		Double maxPrice=null;
		if(request.getParameter("maxPrice")!=null){
			try{
				maxPrice=new Double(request.getParameter("maxPrice").trim());
				request.setAttribute("maxPrice", request.getParameter("maxPrice").trim());
			}catch (Exception e){
			}
		}
		
		if(minPrice!=null&&maxPrice!=null){
			query.add("fq", "price:["+minPrice.doubleValue()+" TO "+maxPrice.doubleValue()+"]");
		}else if(minPrice!=null){
			query.add("fq", "price:["+minPrice.doubleValue()+" TO *]");
		}else if(maxPrice!=null){
			query.add("fq", "price:[* TO "+maxPrice.doubleValue()+"]");
		}
		
		Store store=ConfigUtil.getInstance().getStore();
		Map<String,String>searchAttributeMap=store.getSearchAttribute();
		Set<String> params=searchAttributeMap.keySet();
		for (String param : params){
			String values[]=request.getParameterValues(param);
			if(values!=null){
				StringBuffer qValueBuff=new StringBuffer();
				if(values.length>1){
					qValueBuff.append("(");
				}
				for (int i = 0; i < values.length; i++)
				{
					if(i!=0){
						qValueBuff.append(" OR ");
					}
					qValueBuff.append("\""+values[i].trim()+"\"");
				}
				if(values.length>1){
					qValueBuff.append(")");
				}
				query.add("fq", searchAttributeMap.get(param)+":"+qValueBuff.toString().trim());
			}
		}
		
		Map<String,String>searchSkuOptionMap=store.getSearchSkuOption();
		params=searchSkuOptionMap.keySet();
		for (String param : params){
			String values[]=request.getParameterValues(param);
			if(values!=null){
				StringBuffer qValueBuff=new StringBuffer();
				if(values.length>1){
					qValueBuff.append("(");
				}
				for (int i = 0; i < values.length; i++)
				{
					if(i!=0){
						qValueBuff.append(" OR ");
					}
					qValueBuff.append("\""+values[i].trim()+"\"");
				}
				if(values.length>1){
					qValueBuff.append(")");
				}
				query.add("fq", searchSkuOptionMap.get(param)+":"+qValueBuff.toString().trim());
			}
		}
	}
	
	/**
	 * 在产品详细页面上,查出相同目录的其他产品的列表(产品陈列).
	 * 
	 * @param request 询查条件有pageNo-当前页; rows-一页显示多少items.
	 * @param categoryId
	 * @return
	 */
	public static SolrQuery buildProductArray(Integer categoryId,Integer pageSize,Integer pageNo,String sort)
	{
		Store store=ConfigUtil.getInstance().getStore();
		// 根据url分析当前页数.
		SolrQuery query = new SolrQuery("+(parentCategoryIds:"+categoryId+") + (displayable:true)");
		
		query.add("qt", store.getProductCategoryDisMaxRequestHandler());
		//添加排序参数
		appendSort(sort, query);
		appendPagingParam(query, pageNo, pageSize);
		return query;
	}
	
	
	/**
	 * 用于产品查询
	 * @param request
	 * @param categoryId
	 * @return
	 */
	public static SolrQuery buildCatalogQuery(HttpServletRequest request,Integer defaultPageSize)
	{
		Store store=ConfigUtil.getInstance().getStore();
		// 根据request分析当前页数.
		SolrQuery query = new SolrQuery(request.getParameter("q"));
		query.add("qt", store.getSearchProductDisMaxRequestHandler());
		searchProductCondition(query, request);
		//增加对目录的层面统计
		/*query.setFacet(true);
		query.addFacetField("parentCategoryIds");
		query.setFacetMinCount(1);
		query.setFacetSort(FacetParams.FACET_SORT_COUNT);*/
		// 添加排序
		appendSortParam(request, query);

		Integer cat=ServletRequestUtils.getIntParameter(request, "cat", ConfigUtil.getInstance().getStore().getCatalog().getCategoryId());
		query.add("fq", "parentCategoryIds:"+cat);
		
		int pageNo = ServletRequestUtils.getIntParameter(request, "pageNo", 1);
		int pageSize = getPageSize(request, defaultPageSize);
		appendPagingParam(query, pageNo, pageSize);
		
		return query;
	}
	
	/**
	 * 用于内容查询
	 * @param request
	 * @param categoryId
	 * @return
	 */
	public static SolrQuery buildContentQuery(HttpServletRequest request,Integer defaultPageSize)
	{
		// 根据request分析当前页数.
		SolrQuery query = new SolrQuery(request.getParameter("cq"));
		query.add("qt", "edismax");
		if (!StringUtil.isEmpty(request.getParameter("cat")))
		{
			query.add("fq", "parentCategoryIds:"+request.getParameter("cat"));
		}		
//		query.add("fq", "expiredTime:[NOW TO *]");
		int pageNo = ServletRequestUtils.getIntParameter(request, "pageNo", 1);
		int pageSize = getPageSize(request, defaultPageSize);
		appendPagingParam(query, pageNo, pageSize);		
		return query;
	}
	
	/**
	 * 根据uri得到当前页数
	 * @param uri
	 * @return
	 */
	private static Integer getPageNoByUri(String uri)
	{
		Matcher matcher=catalog_page_pattern.matcher(uri);
		if(matcher.matches()){
			return Integer.parseInt(uri.substring(uri.indexOf("_pn")+"_pn".length(),uri.indexOf(".html")));
		}else{
			return 1;
		}
	}
	
	private static Integer getPageSize(HttpServletRequest request,Integer defaultPageSize)
	{
		int rows = defaultRows;
		if(defaultPageSize!=null){
			rows=defaultPageSize;
		}
		if (!StringUtil.isEmpty(request.getParameter("rows")))
		{
			try{
				rows = new Integer(request.getParameter("rows"));
				if (rows > maxRows)
				{
					rows = maxRows;
				}
			}catch (NumberFormatException e){
				//do nothing
			}
		}
		return rows;
	}
	
	/**
	 * 设置分页信息，为jsp显示分页做准备
	 * @param request
	 * @param queryResponse
	 */
	public static void setPageInfo(HttpServletRequest request, SolrQuery query, SolrDocumentList result)
	{
		QueryCriteria qc = new QueryCriteria();
		
		int rows = defaultRows;
		long start = result.getStart();
		long count = result.getNumFound();
		qc.setTotalCount(count);
		if (query.getRows() != null)
		{
			rows = query.getRows();
		}
		qc.setRows(rows);
		int pageNo = (int) (start/rows);
		//0是第一页
		pageNo++;
		qc.setPageNo(pageNo);
		request.setAttribute("sc", qc);
	}
	
	/**
	 * 添加排序参数
	 * @param request
	 * @param query
	 */
	private static void appendSort(String sortby, SolrQuery query)
	{
		//default is new arrival.
		String sort = SORT_PARAM[0];
		if (StringUtils.isNotBlank(sortby))
		{
			for (String param : SORT_PARAM)  //检查sort参数是否在指定的参数范围内.
			{
				if (sortby.equals(param))
				{
					sort = sortby;
					break;
				}
			}
		}
		if (SORT_PARAM[0].equals(sort))
			query.addSortField("createTime", SolrQuery.ORDER.desc);
		else if (SORT_PARAM[1].equals(sort))
			query.addSortField("_salesCount_i", SolrQuery.ORDER.desc);
		else if (SORT_PARAM[2].equals(sort))
			query.addSortField("productName_s", SolrQuery.ORDER.asc);
		else if (SORT_PARAM[3].equals(sort))
			query.addSortField("price", SolrQuery.ORDER.asc);
		else if (SORT_PARAM[4].equals(sort))
			query.addSortField("price", SolrQuery.ORDER.desc);
	}
	
	private static void appendSortParam(HttpServletRequest request, SolrQuery query)
	{
		appendSort(request.getParameter("sort"), query);
	}
	
	/**
	 * 添加页数,显示个数
	 * @param request
	 * @param query
	 */
	private static void appendPagingParam(SolrQuery query,Integer pageNo,Integer pageSize)
	{
		query.setRows(pageSize);
		query.setStart((pageNo - 1)* pageSize);
	}
	
	public static Map<String, CMFacetField> getFacetQueryMap(QueryResponse queryResponse){
		Map<String, CMFacetField> facetMap=new HashMap<String, CMFacetField>();
		List<FacetField> facets = queryResponse.getFacetFields();
		if(facets!=null){
			for (FacetField facet : facets) {
				CMFacetField cmFacetField=facetMap.get(facet.getName());
				if(cmFacetField==null){
					cmFacetField=new CMFacetField(facet.getName(),null);
					facetMap.put(facet.getName(), cmFacetField);
				}
				List<Count> counts=facet.getValues();
				if(counts!=null){
					for (Count count : counts) {
						cmFacetField.add(count.getName(), count.getCount());
					}
				}
			}
		}
		
		List<RangeFacet> rangeFacetNums=queryResponse.getFacetRanges();
		if(rangeFacetNums!=null){
			for (RangeFacet rangeFacet : rangeFacetNums)
			{
				CMFacetField cmFacetField=facetMap.get(rangeFacet.getName());
				if(cmFacetField==null){
					cmFacetField=new CMFacetField(rangeFacet.getName(),rangeFacet.getStart(),rangeFacet.getEnd(),rangeFacet.getBefore(),rangeFacet.getAfter());
					facetMap.put(rangeFacet.getName(), cmFacetField);
				}
				List<RangeFacet.Count> counts=rangeFacet.getCounts();
				if(counts!=null){
					for (RangeFacet.Count count : counts) {
						cmFacetField.add(new BigDecimal(count.getValue()), new BigDecimal(count.getValue()).add(new BigDecimal(rangeFacet.getGap().toString())), new Long(count.getCount()));
					}
				}
			}
		}
		return facetMap;
	}
}
