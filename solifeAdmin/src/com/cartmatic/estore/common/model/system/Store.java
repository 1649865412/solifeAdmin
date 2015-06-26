package com.cartmatic.estore.common.model.system;

import java.util.HashMap;
import java.util.Map;

import com.cartmatic.estore.common.model.system.base.StoreTbl;

/**
 * Model class for Store. Add not database mapped fileds in this class.
 */
public class Store extends StoreTbl {

	private String phone;
	private String email;
	private String defaultAnalytics;
	private String spareAnalytics;
	private String extraMeta1;
	private String extraMeta2;
	private String extraMeta3;
	private String domain;
	private String keyWords;
	private String title;
	private String description;
	private String footerDescription;
	private String emailSender;
	private boolean isTrackOrder;
	private boolean isTrackCheckout;
	private Integer categoryListPerSize;
	private String mediaUrlPath;
	private String emailSiteUrl;
	private String emailSiteName;

	private Map<String, String> searchAttribute;
	private Map<String, String> searchSkuOption;
	private String productCategoryDisMaxRequestHandler;
	private String searchProductDisMaxRequestHandler;
	private String urlBuilderClass;
	
  	public String getEmailSender() {
		return emailSender;
	}

	public void setEmailSender(String emailSender) {
		this.emailSender = emailSender;
	}

	public boolean getIsTrackOrder() {
		return isTrackOrder;
	}

	public void setIsTrackOrder(boolean isTrackOrder) {
		this.isTrackOrder = isTrackOrder;
	}

	public boolean getIsTrackCheckout() {
		return isTrackCheckout;
	}

	public void setIsTrackCheckout(boolean isTrackCheckout) {
		this.isTrackCheckout = isTrackCheckout;
	}

	public String getDefaultAnalytics() {
		return defaultAnalytics;
	}

	public void setDefaultAnalytics(String defaultAnalytics) {
		this.defaultAnalytics = defaultAnalytics;
	}

	public String getSpareAnalytics() {
		return spareAnalytics;
	}

	public void setSpareAnalytics(String spareAnalytics) {
		this.spareAnalytics = spareAnalytics;
	}

	public String getExtraMeta1() {
		return extraMeta1;
	}

	public void setExtraMeta1(String extraMeta1) {
		this.extraMeta1 = extraMeta1;
	}

	public String getExtraMeta2() {
		return extraMeta2;
	}

	public void setExtraMeta2(String extraMeta2) {
		this.extraMeta2 = extraMeta2;
	}

	public String getExtraMeta3() {
		return extraMeta3;
	}

	public void setExtraMeta3(String extraMeta3) {
		this.extraMeta3 = extraMeta3;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFooterDescription() {
		return footerDescription;
	}

	public void setFooterDescription(String footerDescription) {
		this.footerDescription = footerDescription;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Default Empty Constructor for class Store
	 */
	public Store () {
		super();
	}
	
	/**
	 * 定义实体的业务名取值； storeName
	 * 必须手工完成这个部分，否则编译不通过。
	 */
	public String getStoreName () {
		if (storeId == null)
	        return "";
	    else
			//返回一个指定有业务意义的属性值;
			//如：product的VO就用product.productName
	        return this.name;
	}
	
	/**
	 * Default Key Fields Constructor for class Store
	 */
	public Store (
		 Integer in_storeId
		) {
		super (
		  in_storeId
		);
	}

	public Integer getCategoryListPerSize() {
		return categoryListPerSize;
	}

	public void setCategoryListPerSize(Integer categoryListPerSize) {
		this.categoryListPerSize = categoryListPerSize;
	}

	public Map<String, String> getSearchAttribute() {
		return searchAttribute;
	}

	public void setSearchAttribute(Map<String, String> searchAttribute) {
		this.searchAttribute = searchAttribute;
	}
	
	public Map<String, String> getSearchSkuOption() {
		return searchSkuOption;
	}

	public void setSearchSkuOption(Map<String, String> searchSkuOption) {
		this.searchSkuOption = searchSkuOption;
	}

	public String getMediaUrlPath() {
		return mediaUrlPath;
	}

	public void setMediaUrlPath(String mediaUrlPath) {
		this.mediaUrlPath = mediaUrlPath;
	}

	public String getProductCategoryDisMaxRequestHandler() {
		return productCategoryDisMaxRequestHandler;
	}

	public void setProductCategoryDisMaxRequestHandler(String productCategoryDisMaxRequestHandler) {
		this.productCategoryDisMaxRequestHandler = productCategoryDisMaxRequestHandler;
	}

	public String getSearchProductDisMaxRequestHandler() {
		return searchProductDisMaxRequestHandler;
	}

	public void setSearchProductDisMaxRequestHandler(String searchProductDisMaxRequestHandler) {
		this.searchProductDisMaxRequestHandler = searchProductDisMaxRequestHandler;
	}

	public String getUrlBuilderClass() {
		return urlBuilderClass;
	}

	public void setUrlBuilderClass(String urlBuilderClass) {
		this.urlBuilderClass = urlBuilderClass;
	}

	public String getEmailSiteUrl() {
		return emailSiteUrl;
	}

	public void setEmailSiteUrl(String EmailSiteUrl) {
		this.emailSiteUrl = EmailSiteUrl;
	}

	public String getEmailSiteName() {
		return emailSiteName;
	}

	public void setEmailSiteName(String emailSiteName) {
		this.emailSiteName = emailSiteName;
	}
}