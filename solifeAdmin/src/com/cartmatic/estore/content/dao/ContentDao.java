package com.cartmatic.estore.content.dao;

import java.util.List;

import com.cartmatic.estore.common.model.catalog.Category;
import com.cartmatic.estore.common.model.content.Content;
import com.cartmatic.estore.content.model.ContentSearchCriteria;
import com.cartmatic.estore.core.dao.GenericDao;
import com.cartmatic.estore.core.search.SearchCriteria;
/**
 * Dao interface for Content.
 */
/**
 * @author Administrator
 *
 */
public interface ContentDao extends GenericDao<Content> {
	
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
	
	/**
	 * 根据目录查找相应的文章
	 * @param category
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	public List<Content> searchContentByCategory(Category category,Integer firstResult, Integer maxResults);
	
	public Content getContentByCode(Integer storeId,String code);
	

}