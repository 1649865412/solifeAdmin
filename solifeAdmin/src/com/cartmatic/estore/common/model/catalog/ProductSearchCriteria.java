package com.cartmatic.estore.common.model.catalog;

import java.math.BigDecimal;
import java.util.List;

public class ProductSearchCriteria {
	/**
	 * 产品名称
	 */
	private String name;
	/**
	 * 商品分类Id
	 */
	private Integer categoryId;
	/**
	 * 商品目录Id
	 */
	private Integer catalogId;
	
	/**
	 * 是否显示虚拟目录下的产品
	 * 0表示显示所有
	 * 1只显示虚拟目录下的产品
	 * 2只显示实体目录下的产品
	 */
	private Integer virtual;
	
	private String productCode;
	private String skuCode;
	private Integer brandId;
	private Integer supplierId;
	private Integer productTypeId;
	/**
	 * 产品状态，多个的以,分隔
	 */
	private String productStatus;
	/**
	 * 不包含的产品状态，多个的以,分隔
	 */
	private String excludeProductStatus;
	/**
	 * Sku状态
	 */
	private Short skuStatus;
	private String productKind;
	/**
	 * 排序
	 */
	private String sort;
	private String categoryPath;
	/**
	 * 价格大于
	 */
	private BigDecimal fromPrice;
	/**
	 * 价格少于
	 */
	private BigDecimal toPrice;
	/**
	 * 销售规则
	 */
	private String availabilityRule;
	/**
	 * 价格从x到y之间，x-y两值用中划线分隔
	 */
	private String fromToPrice;
	
	/**
	 * 搜索产品名时是否包含skuCode
	 */
	private boolean searchNameInSkuCode=false;
	
	public boolean isSearchNameInSkuCode() {
		return searchNameInSkuCode;
	}
	public void setSearchNameInSkuCode(boolean searchNameInSkuCode) {
		this.searchNameInSkuCode = searchNameInSkuCode;
	}
	public String getFromToPrice() {
		return fromToPrice;
	}
	public void setFromToPrice(String fromToPrice) {
		String tempPrice[]=fromToPrice.split("-");
		fromPrice=new BigDecimal(tempPrice[0]);
		toPrice=new BigDecimal(tempPrice[1]);
		this.fromToPrice = fromToPrice;
	}
	private List<SearchAttributeModel> searchAttributeList=null;

	
	public List<SearchAttributeModel> getSearchAttributeList() {
		return searchAttributeList;
	}
	public void setSearchAttributeList(
			List<SearchAttributeModel> searchAttributeList) {
		this.searchAttributeList = searchAttributeList;
	}
	public BigDecimal getFromPrice() {
		return fromPrice;
	}
	public void setFromPrice(BigDecimal fromPrice) {
		this.fromPrice = fromPrice;
	}
	public BigDecimal getToPrice() {
		return toPrice;
	}
	public void setToPrice(BigDecimal toPrice) {
		this.toPrice = toPrice;
	}
	public String getCategoryPath() {
		return categoryPath;
	}
	public void setCategoryPath(String categoryPath) {
		this.categoryPath = categoryPath;
	}
	public String getProductKind() {
		return productKind;
	}
	public void setProductKind(String productKind) {
		this.productKind = productKind;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
		if(this.name!=null)
			this.name=this.name.trim();
	}
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
		if(this.productCode!=null)
			this.productCode=this.productCode.trim();
	}
	public String getSkuCode() {
		return skuCode;
	}
	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
		if(this.skuCode!=null)
			this.skuCode=this.skuCode.trim();
	}
	public Integer getBrandId() {
		return brandId;
	}
	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}
	
	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}
	public Integer getSupplierId() {
		return supplierId;
	}
	public Integer getProductTypeId() {
		return productTypeId;
	}
	public void setProductTypeId(Integer productTypeId) {
		this.productTypeId = productTypeId;
	}
	public String getProductStatus() {
		return productStatus;
	}
	public void setProductStatus(String productStatus) {
		this.productStatus = productStatus;
	}
	public String getExcludeProductStatus() {
		return excludeProductStatus;
	}
	public void setExcludeProductStatus(String excludeProductStatus) {
		this.excludeProductStatus = excludeProductStatus;
	}
	public Short getSkuStatus() {
		return skuStatus;
	}
	public void setSkuStatus(Short skuStatus) {
		this.skuStatus = skuStatus;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getAvailabilityRule() {
		return availabilityRule;
	}
	public void setAvailabilityRule(String availabilityRule) {
		this.availabilityRule = availabilityRule;
	}
	public Integer getCatalogId() {
		return catalogId;
	}
	public void setCatalogId(Integer catalogId) {
		this.catalogId = catalogId;
	}
	public Integer getVirtual() {
		return virtual;
	}
	public void setVirtual(Integer virtual) {
		this.virtual = virtual;
	}
}