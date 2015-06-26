
package com.cartmatic.estore.content.service;

import java.util.List;

import com.cartmatic.estore.common.model.attribute.AttributeValue;
import com.cartmatic.estore.common.model.catalog.Category;
import com.cartmatic.estore.common.model.content.Content;
import com.cartmatic.estore.content.model.ContentSearchCriteria;
import com.cartmatic.estore.core.search.SearchCriteria;
import com.cartmatic.estore.core.service.GenericManager;

/**
 * Manager interface for Coupon, responsible for business processing, and
 * communicate between web and persistence layer.
 * 
 */
public interface ContentManager extends GenericManager<Content> {
	//根据目录ID获得文章内容的数目
	public Integer getContentCountByCategory(Integer categoryId);
	//后台搜索文章内容
	public List<Content> searchContents(SearchCriteria searchCriteria,
			ContentSearchCriteria contentSearchCriteria);
	//前台搜索文章内容
	public List<Content> searchContentsForFront(SearchCriteria searchCriteria,
			ContentSearchCriteria contentSearchCriteria);
	//前台根据目录获得所属唯一一篇文章内容，若无返回null
	public Content getUniqueContentForFront(ContentSearchCriteria _contentSearchCriteria);
	//根据content的status、pulishTime、expireTime设置state，主要方便显示
	public void setState(Content _content);
	
	/**
	 * 根据目录查找相应的文章
	 * @param category
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	public List<Content> searchContentByCategory(Category category,Integer firstResult, Integer maxResults); 
	
	/**
	 * 根据code获取内容
	 * @param code
	 * @return
	 */
	public Content getContentByCode(Integer storeId,String code);
	
	public void saveContentAction(Content content, List<AttributeValue> attributeValues);
}
