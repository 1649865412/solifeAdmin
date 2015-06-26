package com.cartmatic.estore.common.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.SolrRequest.METHOD;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.common.helper.CatalogHelper;
import com.cartmatic.estore.common.helper.ConfigUtil;
import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.common.model.catalog.SearchProductModel;
import com.cartmatic.estore.common.model.system.Store;
import com.cartmatic.estore.common.service.SolrService;
import com.cartmatic.estore.core.exception.ApplicationException;
import com.cartmatic.estore.textsearch.SearchConstants;
import com.cartmatic.estore.textsearch.model.SearchResult;
import com.cartmatic.estore.textsearch.query.QueryHelper;

public class SolrServiceImpl implements SolrService
{
    private Map<String, SolrServer> solrServers = new HashMap<String, SolrServer>();
    protected final transient Log logger = LogFactory.getLog(getClass());
    private String serverUrl = null;
    
    public void init()
    {
        // 实例时初始化coreServer
        solrServers.put(SearchConstants.CORE_NAME_PRODUCT, createHttpSolrServer(SearchConstants.CORE_NAME_PRODUCT));
        solrServers.put(SearchConstants.CORE_NAME_SALESORDER, createHttpSolrServer(SearchConstants.CORE_NAME_SALESORDER));
        solrServers.put(SearchConstants.CORE_NAME_CONTENT, createHttpSolrServer(SearchConstants.CORE_NAME_CONTENT));
        solrServers.put(SearchConstants.CORE_NAME_ADMIN, createHttpSolrServer(null));
    }
    
    /**
     * 查询并设置分页信息.
     * @param request
     * @param categoryId
     * @return
     */
    public SearchResult queryProductByCatalog(HttpServletRequest request, Integer categoryId,Integer defaultPageSize) 
    {
    	SearchResult searchResult=new SearchResult();
    	SolrQuery query = QueryHelper.buildCatalogNav(request, categoryId,defaultPageSize);
    	try {
			QueryResponse queryResponse = getSolrServer(SearchConstants.CORE_NAME_PRODUCT).query(query);
			SolrDocumentList datas = queryResponse.getResults();
			QueryHelper.setPageInfo(request, query, datas);
	    	List<Integer> rs = new ArrayList<Integer>();
			for (SolrDocument data : datas)
			{
				rs.add(Integer.valueOf(data.getFieldValue("id").toString()));
			}
			searchResult.setResultList(rs);
			searchResult.setFacetMap(QueryHelper.getFacetQueryMap(queryResponse));
		} catch (SolrServerException e) {
			logger.error(e);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return searchResult;
    	
    }
    
    /**
     * 产品搜索
     * @param request
     * @return
     */
    public SearchResult queryProductBySearch(HttpServletRequest request,Integer defaultPageSize) 
    {
    	SearchResult searchResult=new SearchResult();
    	SolrQuery query = QueryHelper.buildCatalogQuery(request,defaultPageSize);
    	try {
			QueryResponse queryResponse = getSolrServer(SearchConstants.CORE_NAME_PRODUCT).query(query);
			SolrDocumentList datas = queryResponse.getResults();
			QueryHelper.setPageInfo(request, query, datas);
	    	List<Integer> rs = new ArrayList<Integer>();
			for (SolrDocument data : datas)
			{
				rs.add(Integer.valueOf(data.getFieldValue("id").toString()));
			}
			searchResult.setResultList(rs);
			searchResult.setFacetMap(QueryHelper.getFacetQueryMap(queryResponse));
		} catch (SolrServerException e) {
			logger.error(e);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return searchResult;
    	
    }

    /**
     * 通过core名来获得coreServer.
     * 
     * @param core
     * @return
     */
    public SolrServer getSolrServer(String core)
    {
        if (SearchConstants.CORE_NAME_PRODUCT.equals(core))
        {
            return solrServers.get(core);
        }
        else if (SearchConstants.CORE_NAME_SALESORDER.equals(core))
        {
            return solrServers.get(core);
        }
        else if (SearchConstants.CORE_NAME_CONTENT.equals(core))
        {
            return solrServers.get(core);
        }
        else if (SearchConstants.CORE_NAME_ADMIN.equals(core))
        {
        	return solrServers.get(core);
        }
        logger.warn("SolrServer don't support this core which name is [" + core + "] and return null.");
        return null;
    }

    /**
     * 刷新
     * @param solrserver
     * @param flag 是否需要优化index
     * @throws ApplicationException
     */
    public void flushChanges(SolrServer solrserver, boolean flag) throws ApplicationException
    {
        try
        {
            if (flag)
            {
                solrserver.optimize();
            }
            solrserver.commit();
            //UpdateRequest updaterequest = new UpdateRequest();
            //updaterequest.setAction(org.apache.solr.client.solrj.request.UpdateRequest.ACTION.COMMIT, false, false);
            //updaterequest.process(solrserver);
        }
        catch (SolrServerException e)
        {
        	e.printStackTrace();
            throw new ApplicationException("flushChanges Error ", e);
        }
        catch (IOException ioe)
        {
        	ioe.printStackTrace();
            throw new ApplicationException("flushChanges Error ", ioe);
        }
    }

    /**
     * 只使用httpSolr的客户端
     * 
     * @param s
     * @return
     */
    private SolrServer createHttpSolrServer(String s)
    {
        String url = serverUrl;
        if (s != null)
        	url = url + "/" + s;
        CommonsHttpSolrServer server = null;
        // CoreContainer container = new CoreContainer();
        try
        {
            server = new CommonsHttpSolrServer(url);
            server.setSoTimeout(5000); // socket read timeout 等待服务器返回数据超的时间
            server.setConnectionTimeout(1000); //等待建立连接的超时时间
            server.setConnectionManagerTimeout(1000L);//等待HttpConnectionManager从连接池中返回空闲连接的超时时间
            server.setDefaultMaxConnectionsPerHost(400);//每台主机的最大并行链接数
            server.setMaxTotalConnections(500);//客户端总并行链接最大数
            server.setFollowRedirects(false); // defaults to false
            // allowCompression defaults to false.
            // Server side must support gzip or deflate for this to have any
            // effect.
            server.setAllowCompression(true);
            server.setMaxRetries(1); // defaults to 0. > 1 not recommended.
        }
        catch (Exception e)
        {
            logger.error(e);
        }
        return server;
    }
    
    
    public SearchResult queryProductByCategory(HttpServletRequest request,Integer categoryId,Integer pageSize,Integer pageNo,String sort) 
    {
    	CatalogHelper catalogHelper=CatalogHelper.getInstance();
    	Store store=ConfigUtil.getInstance().getStore();
    	SearchResult searchResult=new SearchResult();
    	SolrQuery query = QueryHelper.buildProductArray(categoryId,pageSize,pageNo,sort);
    	try {
			QueryResponse queryResponse = getSolrServer(SearchConstants.CORE_NAME_PRODUCT).query(query);
			SolrDocumentList datas = queryResponse.getResults();
			QueryHelper.setPageInfo(request, query, datas);
	    	List<SearchProductModel> rs = new ArrayList<SearchProductModel>();
			for (SolrDocument data : datas)
			{
				SearchProductModel product=new SearchProductModel();
				product.setProductId(Integer.valueOf(data.getFieldValue("id").toString()));
				product.setProductName((String)data.getFieldValue("productName"));
				product.setUrl((String)data.getFieldValue("url"));
				product.setImage((String)data.getFieldValue("image"));
				product.setPrice(new BigDecimal(((Float)data.getFieldValue("price")).toString()));
				//设置产品实际访问URL
				Product tempProduct=catalogHelper.getProductById(product.getProductId());
				product.setUrl(CatalogHelper.getInstance().getProductUrl(tempProduct,store.getCatalogId(),categoryId));
				rs.add(product);
			}
			searchResult.setResultList(rs);
			
		} catch (SolrServerException e) {
			logger.error(e);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return searchResult;
    	
    }
    
    public void setServerUrl(String avalue)
    {
    	serverUrl = avalue;
    }

	public SearchResult queryBuyCountProductsByCategoryId(Integer storeId,Integer categoryId,final int firstResult, final int maxResults) {
		//必须指定目录
    	SearchResult searchResult=new SearchResult();
		SolrQuery query =new SolrQuery("+(parentCategoryIds:"+categoryId+") + (displayable:true)");
		
		query.addSortField(storeId+"_salesCount_i", SolrQuery.ORDER.desc);
		query.setStart(firstResult);
		if (maxResults != -1)
			query.setRows(maxResults);
    	
    	try {
			QueryResponse queryResponse = getSolrServer(SearchConstants.CORE_NAME_PRODUCT).query(query);
			SolrDocumentList datas = queryResponse.getResults();
			List<Integer> rs = new ArrayList<Integer>();
			for (SolrDocument data : datas)
			{
				rs.add(Integer.valueOf(data.getFieldValue("id").toString()));
			}
			searchResult.setResultList(rs);
		} catch (SolrServerException e) {
			logger.error(e);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return searchResult;
	}
	
	public SearchResult queryFavoireCountProductsByCategoryId(Integer storeId,Integer categoryId,final int firstResult, final int maxResults) {
		//必须指定目录
    	SearchResult searchResult=new SearchResult();
		SolrQuery query =new SolrQuery("+(parentCategoryIds:"+categoryId+") + (displayable:true)");
		
		query.addSortField(storeId+"_favoriteCount_i", SolrQuery.ORDER.desc);
		query.setStart(firstResult);
		if (maxResults != -1)
			query.setRows(maxResults);
    	
    	try {
			QueryResponse queryResponse = getSolrServer(SearchConstants.CORE_NAME_PRODUCT).query(query);
			SolrDocumentList datas = queryResponse.getResults();
			List<Integer> rs = new ArrayList<Integer>();
			for (SolrDocument data : datas)
			{
				rs.add(Integer.valueOf(data.getFieldValue("id").toString()));
			}
			searchResult.setResultList(rs);
		} catch (SolrServerException e) {
			logger.error(e);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return searchResult;
	}

	public SearchResult queryNewProductsByCategoryId(Integer categoryId,int firstResult, int maxResults) {
		//必须指定目录
		SearchResult searchResult=new SearchResult();
		SolrQuery query = new SolrQuery("+(parentCategoryIds:"+categoryId+") + (displayable:true)");
		query.addSortField("createTime", SolrQuery.ORDER.desc);
		query.setStart(firstResult);
		if (maxResults != -1)
			query.setRows(maxResults);
    	List<Integer> rs = new ArrayList<Integer>();
    	try {
			QueryResponse queryResponse = getSolrServer(SearchConstants.CORE_NAME_PRODUCT).query(query);
			SolrDocumentList datas = queryResponse.getResults();
			for (SolrDocument data : datas)
			{
				rs.add(Integer.valueOf(data.getFieldValue("id").toString()));
			}
			searchResult.setResultList(rs);
		} catch (SolrServerException e) {
			logger.error(e);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return searchResult;
	}
	
	public SearchResult queryLastModifiedProductsBySourceId(Integer categoryId,int firstResult, int maxResults) {
		//必须指定目录
		SearchResult searchResult=new SearchResult();
		SolrQuery query = new SolrQuery("+(parentCategoryIds:"+categoryId+") + (displayable:true)");
		query.addSortField("updateTime", SolrQuery.ORDER.desc);
		query.setStart(firstResult);
		if (maxResults != -1)
			query.setRows(maxResults);
    	List<Integer> rs = new ArrayList<Integer>();
    	try {
			QueryResponse queryResponse = getSolrServer(SearchConstants.CORE_NAME_PRODUCT).query(query);
			SolrDocumentList datas = queryResponse.getResults();
			for (SolrDocument data : datas)
			{
				rs.add(Integer.valueOf(data.getFieldValue("id").toString()));
			}
			searchResult.setResultList(rs);
		} catch (SolrServerException e) {
			logger.error(e);
			e.printStackTrace();
		}		
		return searchResult;
	}
	
	public SearchResult querySameBrandProductsByProductId(Integer categoryId,Integer brandId,Integer productId,int firstResult, int maxResults) {
		SearchResult searchResult=new SearchResult();
		StringBuffer sql = new StringBuffer("");
		if(categoryId != null){
			sql.append("+ (parentCategoryIds:"+categoryId+")");
		}
		if(brandId != null){
			sql.append("+ (brandId:"+brandId+")");
		}
//		SolrQuery query = new SolrQuery("+ (parentCategoryIds:"+categoryId+") + (brandId:"+brandId+") + (displayable:true)");
		SolrQuery query = new SolrQuery(sql.append("+ (displayable:true)").toString());
		query.addFilterQuery("-id:"+productId);
		query.addSortField("id", SolrQuery.ORDER.desc);
		query.setStart(firstResult);
		if (maxResults != -1)
			query.setRows(maxResults);
    	try {
			QueryResponse queryResponse = getSolrServer(SearchConstants.CORE_NAME_PRODUCT).query(query);
			SolrDocumentList datas = queryResponse.getResults();
	    	List<Integer> rs = new ArrayList<Integer>();
			for (SolrDocument data : datas)
			{
				rs.add(Integer.valueOf(data.getFieldValue("id").toString()));
			}
			searchResult.setResultList(rs);
		} catch (SolrServerException e) {
			logger.error(e);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return searchResult;
	}

	public SearchResult querySimilarPriceProductsByProductId(Integer categoryId,BigDecimal fromPrice, BigDecimal toPrice, Integer productId,
			int firstResult, int maxResults) {
		SearchResult searchResult=new SearchResult();
		SolrQuery query = new SolrQuery("+ (parentCategoryIds:"+categoryId+")+(price:["+fromPrice+" TO "+toPrice+"]) + (displayable:true)");
		query.addFilterQuery("-id:"+productId);
		query.addSortField("id", SolrQuery.ORDER.desc);
		query.setStart(firstResult);
		if (maxResults != -1)
			query.setRows(maxResults);
    	try {
			QueryResponse queryResponse = getSolrServer(SearchConstants.CORE_NAME_PRODUCT).query(query);
			SolrDocumentList datas = queryResponse.getResults();
	    	List<Integer> rs = new ArrayList<Integer>();
			for (SolrDocument data : datas)
			{
				rs.add(Integer.valueOf(data.getFieldValue("id").toString()));
			}
			searchResult.setResultList(rs);
		} catch (SolrServerException e) {
			logger.error(e);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return searchResult;
	}
	
	public SearchResult queryAllContentByCategory(HttpServletRequest request,Integer defaultPageSize)
	{
		SearchResult searchResult=new SearchResult();
    	SolrQuery query = QueryHelper.buildContentQuery(request,defaultPageSize);
    	try {
			QueryResponse queryResponse = getSolrServer(SearchConstants.CORE_NAME_CONTENT).query(query);
			SolrDocumentList datas = queryResponse.getResults();
			QueryHelper.setPageInfo(request, query, datas);
	    	List<Integer> rs = new ArrayList<Integer>();
			for (SolrDocument data : datas)
			{				
				rs.add(Integer.valueOf(data.getFieldValue("id").toString()));
			}
			searchResult.setResultList(rs);
			
		} catch (SolrServerException e) {
			logger.error(e);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return searchResult;
	}
}
