package com.cartmatic.estore.common.model.content;


import com.cartmatic.estore.common.model.content.base.ContentTbl;



/**
 * Model - Business object
 * This file won't get overwritten.
 */
public class Content extends ContentTbl implements Comparable<Content>{
	public static final int STATE_INVALID = 0; //非激活 
	public static final int STATE_DOING = 1;  //发布中
	public static final int STATE_FUTURE = 2; //将要发布
	public static final int STATE_PAST = 3; //已过时
	
	private static final long	serialVersionUID	= 950237961776819499L;
	
	private Integer viewType;
	private String state;
	/**
	 * 前台访问路径
	 */
	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getViewType() {
		return viewType;
	}

	public void setViewType(Integer viewType) {
		this.viewType = viewType;
	}
	
	

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	/**
	 * Default Empty Constructor for class Content
	 */
	public Content () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class Content
	 */
	public Content (
		 Integer in_contentId
		) {
		super (
		  in_contentId
		);
	}
	
	/**
	 * 定义实体的业务名取值； contentName
	 * 必须手工完成这个部分，否则编译不通过。
	 */
	public String getContentName () {
		if (contentId == null)
	        return "";
	    else
			//返回一个指定有业务意义的属性值;
			//如：product的VO就用product.productName
	        return this.contentTitle;
	}

	public int compareTo(Content o) {
		Content that = o;
		if(this.getSortOrder().intValue()>that.getSortOrder().intValue()){
				return -1;
		}else if(this.getSortOrder().equals(that.getSortOrder())){
			if(this.getPublishTime().getTime()>that.getPublishTime().getTime())
				return -1;
			else
				return 1;
		}else{
			if(this.getPublishTime().getTime()>that.getPublishTime().getTime())
				return -1;
			else
				return 1;
		}
	}



}
