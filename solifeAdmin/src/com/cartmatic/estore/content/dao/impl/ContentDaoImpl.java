
package com.cartmatic.estore.content.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cartmatic.estore.common.model.catalog.Category;
import com.cartmatic.estore.common.model.content.Content;
import com.cartmatic.estore.content.dao.ContentDao;
import com.cartmatic.estore.content.model.ContentSearchCriteria;
import com.cartmatic.estore.core.dao.impl.HibernateGenericDaoImpl;
import com.cartmatic.estore.core.search.SearchCriteria;

/**
 * Dao implementation for Coupon.
 */
public class ContentDaoImpl extends HibernateGenericDaoImpl<Content> implements
		ContentDao {
	
	public Integer getContentCountByCategory(Integer categoryId) {
		String hql="select count(c.contentId) from Content c where c.category.categoryId=?";
		Long result=countByHql(hql, categoryId);
		return result.intValue();
	}
	public List<Content> searchContents(SearchCriteria _searchCriteria,
			ContentSearchCriteria _contentSearchCriteria){
		SearchCriteria searchCriteria = _searchCriteria;
		ContentSearchCriteria contentSearchCriteria = _contentSearchCriteria;
		List<Object> paramList=new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append("from Content content where 1=1");
		if(null != contentSearchCriteria.getCategoryId()){
			sb.append(" and  content.category.id = "+contentSearchCriteria.getCategoryId());
		}
		if(null != contentSearchCriteria.getContentTitle()){
			sb.append(" and content.contentTitle like ? ");
			paramList.add("%"+contentSearchCriteria.getContentTitle()+"%");
		}
		sb.append(" order by contentId desc");
		searchCriteria.setHql(sb.toString());
		searchCriteria.removeParamValues();
		for (Object object : paramList) {
			searchCriteria.addParamValue(object);
		}
		List<Content> list=searchByCriteria(searchCriteria);
		return list;
	}
	
	public List<Content> searchContentsForFront(SearchCriteria _searchCriteria,
			ContentSearchCriteria _contentSearchCriteria){
		SearchCriteria searchCriteria = _searchCriteria;
		ContentSearchCriteria contentSearchCriteria = _contentSearchCriteria;
		StringBuffer sb = new StringBuffer();
		sb.append("from Content where 1=1 ");
		if(null != contentSearchCriteria.getCategoryId()){
			sb.append(" and  category.id = "+contentSearchCriteria.getCategoryId());
		}
		
		String format = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		String now = dateFormat.format(new Date());
		sb.append(" and publishTime < '"+now+"'");
		sb.append(" and ('"+ now +"' <  expiredTime  or expiredTime is null)");
		sb.append(" and status =1 ");
		sb.append(" order by sortOrder asc,createTime desc");
		searchCriteria.setHql(sb.toString());
		List<Content> list=searchByCriteria(searchCriteria);
		return list;
	}
	
	public List<Content> searchContentByCategory(Category category,Integer firstResult, Integer maxResults){
		Date now=new Date();
		String hql="from Content where category.id=? and status =1 and publishTime< ? and (expiredTime > ? or expiredTime is null) order by sortOrder asc,createTime desc";
		return find(hql, firstResult, maxResults, new Object[]{category.getId(),now,now});
	}
	
	public Content getUniqueContentForFront(ContentSearchCriteria _contentSearchCriteria){
		ContentSearchCriteria contentSearchCriteria = _contentSearchCriteria;
		StringBuffer sb = new StringBuffer();
		sb.append("from Content where 1=1 ");
		if(null != contentSearchCriteria.getCategoryId()){
			sb.append(" and  category.id = "+contentSearchCriteria.getCategoryId());
		}
		String format = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		String now = dateFormat.format(new Date());
		sb.append(" and publishTime < '"+now+"'");
		sb.append(" and ('"+ now +"' <  expiredTime  or expiredTime is null)");
		sb.append(" and status =1 ");
		
		Long size = (Long)findUnique("select count(*) "+sb.toString());
		if(1 == size){
			return (Content)findUnique(sb.toString());
		}else{
			return null;
		}
		
		
	}
	@Override
	public Content getContentByCode(Integer storeId, String code) {
		return (Content) findUnique("from Content where store.storeId=? and contentCode =?", storeId,code);
	}
	
}
