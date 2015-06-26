package com.cartmatic.estore.catalog.util;

import java.util.HashMap;
import java.util.Map;

import com.cartmatic.estore.common.helper.ConfigUtil;
import com.cartmatic.estore.common.model.catalog.Category;
import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.common.model.content.Content;
import com.cartmatic.estore.common.model.system.Store;

public class UrlBuilderUtil implements  UrlBuilder{
	final static UrlBuilderUtil	urlBuilderUtil	= new UrlBuilderUtil();
	
	private static Map<String,UrlBuilder> urlBuilders=new HashMap<String, UrlBuilder>();
	
	private UrlBuilderUtil(){
	}
	
	public static UrlBuilderUtil getInstance(){
		return urlBuilderUtil;
	}
	
	private UrlBuilder getUrlBuilder(){
		Store store=ConfigUtil.getInstance().getStore();
		UrlBuilder urlBuilder=urlBuilders.get(store.getCode());
		if(urlBuilder==null){
			try
			{
				urlBuilder=(UrlBuilder)Class.forName(store.getUrlBuilderClass()).newInstance();
			}catch (Exception e){
				e.printStackTrace();
			}
			if(urlBuilder==null){
				urlBuilder = new DefaultUrlBuilder();
			}
			if(urlBuilder!=null){
				urlBuilders.put(store.getCode(), urlBuilder);
			}
		}
		return urlBuilder;
	}

	public Category getContentCategoryByUri(String uri) {
		return getUrlBuilder().getContentCategoryByUri(uri);
	}

	public String getContentCategoryUrl(Category category) {
		return getUrlBuilder().getContentCategoryUrl(category);
	}

	public Category getProductCategoryByUri(String uri) {
		return getUrlBuilder().getProductCategoryByUri(uri);
	}

	public String getProductCategoryUrl(Category category) {
		return getUrlBuilder().getProductCategoryUrl(category);
	}

	@Override
	public String getProductUrl(Product product,Integer catalogId, Integer categoryId) {
		return getUrlBuilder().getProductUrl(product,catalogId, categoryId);
	}

	@Override
	public String getContentUrl(Content content) {
		return getUrlBuilder().getContentUrl(content);
	}

	@Override
	public Integer[] getIdsByProductUrl(String uri) {
		return getUrlBuilder().getIdsByProductUrl(uri);
	}

	@Override
	public Integer getIdByContentUri(String uri) {
		return getUrlBuilder().getIdByContentUri(uri);
	}
}
