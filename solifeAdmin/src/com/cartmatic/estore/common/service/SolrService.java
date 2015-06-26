package com.cartmatic.estore.common.service;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import org.apache.solr.client.solrj.SolrServer;

import com.cartmatic.estore.textsearch.model.SearchResult;

public interface SolrService
{
	/**
	 * 支接查询产品.用于前台根据当前目录来显示商品列表.
	 * 
	 * @param query
	 * @return
	 */
	public SearchResult queryProductByCatalog(HttpServletRequest request, Integer categoryId,Integer defaultPageSize);
	
	/**
	 * 前台对产品进行搜索。
	 * @param request
	 * @return
	 */
	public SearchResult queryProductBySearch(HttpServletRequest request,Integer defaultPageSize);
	
    public SolrServer getSolrServer(String core);
    
    public void flushChanges(SolrServer solrserver, boolean flag);
    
    /**
     * 产品详细页面，查找同目录产品
     * @param request
     * @return
     */ 
    public SearchResult queryProductByCategory(HttpServletRequest request,Integer categoryId,Integer pageSize,Integer pageNo,String sort);
    
    /**
     * 自动评估 根据[源Id=categoryId]获得特定范围的推荐产品(不能重复,而且必须是激活的),采用的推荐规则是：购买数量排序
     * @param sourceId
     * @param firstResult
     * @param maxResults
     * @return
     */
    public SearchResult queryBuyCountProductsByCategoryId(final Integer storeId,final Integer categoryId,final int firstResult, final int maxResults);
    public SearchResult queryFavoireCountProductsByCategoryId(final Integer storeId,final Integer categoryId,final int firstResult, final int maxResults);
    /**
     * 自动评估 根据[源Id=categoryId]获得特定范围的推荐产品(不能重复,而且必须是激活的),采用的推荐规则是：新产品，发布时间排序
     * @param sourceId
     * @param firstResult
     * @param maxResults
     * @return
     */
    public SearchResult queryNewProductsByCategoryId(final Integer categoryId,final int firstResult, final int maxResults);
    
    /**
     * 查找最近修改的产品
     * @param categoryId 多店必须指定相应的目录
     * @param firstResult
     * @param maxResults
     * @return
     */
    public SearchResult queryLastModifiedProductsBySourceId(Integer categoryId,int firstResult, int maxResults);
    
    /**
     * 自动评估 根据[源Id=brandId]获得特定范围的推荐产品(不能重复,而且必须是激活的,并且不包含productId产品),采用的推荐规则是：相同品牌
     * @param categoryId 需要指定catalog默认的目录
     * @param brandId
     * @param productId
     * @param firstResult
     * @param maxResults
     * @return
     */
    public SearchResult querySameBrandProductsByProductId(Integer categoryId,Integer brandId,Integer productId,int firstResult, int maxResults);

    /**
     * 根据[从价格fromPrice 到 价格toPrice]获得特定范围的推荐产品(不能重复,而且必须是激活的,并且不包含productId产品),采用的推荐规则是：相似价格(默认SKU)
     * @param categoryId 需要指定catalog默认的目录
     * @param fromPrice
     * @param toPrice
     * @param productId
     * @param firstResult
     * @param maxResults
     * @return
     */
    public SearchResult querySimilarPriceProductsByProductId(Integer categoryId,BigDecimal fromPrice,BigDecimal toPrice,Integer productId,int firstResult, int maxResults);
    
    /**
     * 查询帮助内容
     * @param request
     * @param categoryId
     * @return
     */
    public SearchResult queryAllContentByCategory(HttpServletRequest request,Integer defaultPageSize);
}
