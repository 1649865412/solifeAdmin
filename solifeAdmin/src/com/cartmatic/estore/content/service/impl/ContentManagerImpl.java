
package com.cartmatic.estore.content.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import com.cartmatic.estore.common.model.attribute.AttributeValue;
import com.cartmatic.estore.common.model.catalog.Category;
import com.cartmatic.estore.common.model.content.Content;
import com.cartmatic.estore.common.service.AttributeService;
import com.cartmatic.estore.content.dao.ContentDao;
import com.cartmatic.estore.content.model.ContentSearchCriteria;
import com.cartmatic.estore.content.service.ContentManager;
import com.cartmatic.estore.core.search.SearchCriteria;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;

/**
 * Manager implementation for Coupon, responsible for business processing, and
 * communicate between web and persistence layer.
 */
public class ContentManagerImpl extends GenericManagerImpl<Content> implements
		ContentManager {

	private ContentDao	contentDao	= null;

	private AttributeService attributeService=null;
	
	public void setAttributeService(AttributeService attributeService) {
		this.attributeService = attributeService;
	}
	/**
	 * @param contentDao
	 *            the contentDao to set
	 */
	public void setContentDao(ContentDao contentDao) {
		this.contentDao = contentDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		dao = contentDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete(java.lang.Object)
	 */
	@Override
	protected void onDelete(Content entity) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave(java.lang.Object)
	 */
	@Override
	protected void onSave(Content content) {
		if(StringUtils.isBlank(content.getContentCode())){
			content.setContentCode(Math.abs(UUID.randomUUID().getMostSignificantBits())+"");
		}
	}
	
	public Integer getContentCountByCategory(Integer categoryId) {
		return contentDao.getContentCountByCategory(categoryId);
	}
	
	public List<Content> searchContents(SearchCriteria searchCriteria,
			ContentSearchCriteria contentSearchCriteria) {
		return contentDao.searchContents(searchCriteria, contentSearchCriteria);
	}
	
	public List<Content> searchContentsForFront(SearchCriteria searchCriteria,
			ContentSearchCriteria contentSearchCriteria) {
		return contentDao.searchContentsForFront(searchCriteria, contentSearchCriteria);
	}

	public Content getUniqueContentForFront(ContentSearchCriteria _contentSearchCriteria){
		return contentDao.getUniqueContentForFront(_contentSearchCriteria);
	}

	public void setState(Content _content) {
		Content content = _content; 
		Date now = new Date();
		int i = now.compareTo(content.getPublishTime());
		int j;
		if (content.getExpiredTime() != null) {
			j = now.compareTo(content.getExpiredTime());
		} else {
			j = -1;
		}
		
		if(content.getStatus() == 1 ){
			if ((i > 0) && (j < 0)) {
				content.setState(String.valueOf(Content.STATE_DOING));
			} else if ((i < 0) && (j < 0)) {
				content.setState(String.valueOf(Content.STATE_FUTURE));
			} else {
				content.setState(String.valueOf(Content.STATE_PAST));
			}	
		}else{
			content.setState(String.valueOf(Content.STATE_INVALID));
		}
		
		return;
	}

	public List<Content> searchContentByCategory(Category category, Integer firstResult, Integer maxResults) {
		return contentDao.searchContentByCategory(category, firstResult, maxResults);
	}

	public Content getContentByCode(Integer storeId,String code) {
		return contentDao.getContentByCode(storeId, code);
	}

	public void saveContentAction(Content content, List<AttributeValue> attributeValues)
	{		
		save(content);
		//更新目录自定义属性
		if(attributeValues!=null){
			attributeService.saveOrUpdateAttributeValue(attributeValues, content);
		}
	}
}
