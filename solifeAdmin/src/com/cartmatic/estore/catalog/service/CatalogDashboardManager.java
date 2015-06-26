package com.cartmatic.estore.catalog.service;

import java.util.Map;

import com.cartmatic.estore.core.search.SearchCriteria;

public interface CatalogDashboardManager {
	
	/**
	 * 获取产品仪表板数据
	 * @return
	 */
	public Map<String,Long> getCatalogDashboardData();
	
	
	/**
	 * 获取最近的评论信息
	 * @return
	 */
	public SearchCriteria findLatestProductReview(SearchCriteria sc);
}
