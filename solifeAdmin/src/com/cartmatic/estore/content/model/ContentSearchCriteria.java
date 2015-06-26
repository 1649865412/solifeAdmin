
package com.cartmatic.estore.content.model;

import java.util.Date;

public class ContentSearchCriteria {
	private Integer	categoryId;
	private Integer	contentId;
	private String	contentTitle;
	private Short	status;
	private String	contentBody;
	private Date	publishTime;
	private Date	expiredTime;
	private Short	sortOrder;
	private String	metaKeyword;
	private String	metaDescription;
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public Integer getContentId() {
		return contentId;
	}
	public void setContentId(Integer contentId) {
		this.contentId = contentId;
	}
	public String getContentTitle() {
		return contentTitle;
	}
	public void setContentTitle(String contentTitle) {
		if(contentTitle!=null)
			contentTitle=contentTitle.trim();
		this.contentTitle = contentTitle;
	}
	public Short getStatus() {
		return status;
	}
	public void setStatus(Short status) {
		this.status = status;
	}
	public String getContentBody() {
		return contentBody;
	}
	public void setContentBody(String contentBody) {
		this.contentBody = contentBody;
	}
	public Date getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}
	public Date getExpiredTime() {
		return expiredTime;
	}
	public void setExpiredTime(Date expiredTime) {
		this.expiredTime = expiredTime;
	}
	public Short getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(Short sortOrder) {
		this.sortOrder = sortOrder;
	}
	public String getMetaKeyword() {
		return metaKeyword;
	}
	public void setMetaKeyword(String metaKeyword) {
		this.metaKeyword = metaKeyword;
	}
	public String getMetaDescription() {
		return metaDescription;
	}
	public void setMetaDescription(String metaDescription) {
		this.metaDescription = metaDescription;
	}

	

}
