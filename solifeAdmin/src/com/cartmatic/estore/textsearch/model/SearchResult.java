package com.cartmatic.estore.textsearch.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SearchResult {
	/**
	 * 搜索结果集
	 */
	private List<Object> resultList=null;
	
	/**
	 * 层面统计
	 */
	private Map<String, CMFacetField> facetMap=null;


	/**
	 * 不返回Null
	 * @return
	 */
	public List getResultList() {
		if (resultList == null)
			return new ArrayList();
		return resultList;
	}

	public void setResultList(List resultList) {
		this.resultList = resultList;
	}

	public Map<String, CMFacetField> getFacetMap() {
		return facetMap;
	}

	public void setFacetMap(Map<String, CMFacetField> facetMap) {
		this.facetMap = facetMap;
	}

	
	/**
	 * 搜索匹配结果数量
	 */
//	private Long numFound;
	
	
	
	
}
