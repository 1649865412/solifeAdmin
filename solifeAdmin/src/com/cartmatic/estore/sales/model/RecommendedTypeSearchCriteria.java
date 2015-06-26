package com.cartmatic.estore.sales.model;

public class RecommendedTypeSearchCriteria {
	private String newSearch;
	protected Short status; //是否激活
	protected Short isApplyToProduct; //与商品相关
	protected Short isApplyToCategory; //与目录相关
	
	public String getNewSearch() {
		return newSearch;
	}
	public void setNewSearch(String newSearch) {
		this.newSearch = newSearch;
	}
	public Short getStatus() {
		return status;
	}
	public void setStatus(Short status) {
		this.status = status;
	}
	public Short getIsApplyToProduct() {
		return isApplyToProduct;
	}
	public void setIsApplyToProduct(Short isApplyToProduct) {
		this.isApplyToProduct = isApplyToProduct;
	}
	public Short getIsApplyToCategory() {
		return isApplyToCategory;
	}
	public void setIsApplyToCategory(Short isApplyToCategory) {
		this.isApplyToCategory = isApplyToCategory;
	}
	
	
	
	
}
