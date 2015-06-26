package com.cartmatic.estore.sales.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.cartmatic.estore.catalog.service.ProductManager;
import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.common.service.SolrService;
import com.cartmatic.estore.core.dao.impl.HibernateGenericDaoImpl;
import com.cartmatic.estore.sales.SalesConstants;
import com.cartmatic.estore.sales.dao.AutoEvalRecommendationDao;
import com.cartmatic.estore.textsearch.model.SearchResult;

public class AutoEvalRecommendationDaoFromSServerImpl extends HibernateGenericDaoImpl<Product> implements AutoEvalRecommendationDao{
	private SolrService solrService=null;
	
	private ProductManager productManager=null;
	
	public void setSolrService(SolrService solrService) {
		this.solrService = solrService;
	}

	public List<Product> getBuyCountProductsByCategoryId(final Integer storeId,Integer categoryId, int firstResult, int maxResults) {
		List<Product> productList= new ArrayList<Product>();
		SearchResult searchResult=solrService.queryBuyCountProductsByCategoryId(storeId,categoryId, firstResult, maxResults);
		List<Integer> solrRsp = (List<Integer>)searchResult.getResultList();
		for (Integer id : solrRsp)
		{
			Product product=productManager.getById(id); 
			if(product!=null)
				productList.add(product);
		}
		return productList;
	}
	
	public List<Product> getFavoriteCountProductsByCategoryId(final Integer storeId,Integer categoryId, int firstResult, int maxResults) {
		List<Product> productList= new ArrayList<Product>();
		SearchResult searchResult=solrService.queryFavoireCountProductsByCategoryId(storeId,categoryId, firstResult, maxResults);
		List<Integer> solrRsp = (List<Integer>)searchResult.getResultList();
		for (Integer id : solrRsp)
		{
			Product product=productManager.getById(id); 
			if(product!=null)
				productList.add(product);
		}
		return productList;
	}

	public void setProductManager(ProductManager productManager) {
		this.productManager = productManager;
	}

	public List<Product> getNewProductsByCategoryId(Integer categoryId,int firstResult, int maxResults) {
		List<Product> productList= new ArrayList<Product>();
		SearchResult searchResult = solrService.queryNewProductsByCategoryId(categoryId, firstResult, maxResults);
		List<Integer> solrRsp = (List<Integer>)searchResult.getResultList();
		for (Integer id : solrRsp)
		{
			Product product=productManager.getById(id); 
			if(product!=null)
				productList.add(product);
		}
		return productList;
	}

	public List<Product> getSameBrandProductsByProductId(Integer categoryId,Integer productId,int firstResult, int maxResults) {
		Product product=productManager.getById(productId);
		List<Product> productList= new ArrayList<Product>();
		SearchResult searchResult=solrService.querySameBrandProductsByProductId(categoryId,product.getBrandId(),productId, firstResult, maxResults);
		List<Integer> solrRsp = (List<Integer>)searchResult.getResultList();
		for (Integer id : solrRsp)
		{
			Product temp_product=productManager.getById(id); 
			if(temp_product!=null)
				productList.add(temp_product);
		}
		return productList;
	} 

	public List<Product> getSimilarPriceProductsByProductId(Integer categoryId,Integer productId,int firstResult, int maxResults) {
		List<Product> productList= new ArrayList<Product>();
		Product product=productManager.getById(productId);
		if(product==null)
			return productList;
		final BigDecimal fromPrice = product.getDefaultProductSku().getPrice().multiply(SalesConstants.RECOMMENDED_SIMILAR_PRICE_FLOOR_LIMIT);
		final BigDecimal toPrice = product.getDefaultProductSku().getPrice().multiply(SalesConstants.RECOMMENDED_SIMILAR_PRICE_UPPER_LIMIT);
		SearchResult searchResult=solrService.querySimilarPriceProductsByProductId(categoryId,fromPrice, toPrice, product.getProductId(), firstResult, maxResults);
		List<Integer> solrRsp = (List<Integer>)searchResult.getResultList();
		for (Integer id : solrRsp)
		{
			Product temp_product=productManager.getById(id); 
			if(temp_product!=null)
				productList.add(temp_product);
		}
		return productList;
	}

	public List<Product> getSimilarCodeProductsBySourceId(Integer sourceId,
			int firstResult, int maxResults) {
		// TODO Auto-generated method stub
		List<Product> productList= new ArrayList<Product>();
		return productList;
	}
}
