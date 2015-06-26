package com.cartmatic.estore.catalog.model;


public class BulkProductModel {
	private String productIds;
	private String skuIds;
	private String prodCommAttrs[];
	private String skuCommAttrs[];
	private String prodAttrs[];
	
	public String getProductIds() {
		return productIds;
	}
	public void setProductIds(String productIds) {
		this.productIds = productIds;
	}
	public String getSkuIds() {
		return skuIds;
	}
	public void setSkuIds(String skuIds) {
		this.skuIds = skuIds;
	}
	public String[] getProdCommAttrs() {
		return prodCommAttrs;
	}

	public void setProdCommAttrs(String[] prodCommAttrs) {
		this.prodCommAttrs = prodCommAttrs;
	}
	public String[] getSkuCommAttrs() {
		return skuCommAttrs;
	}
	public void setSkuCommAttrs(String[] skuCommAttrs) {
		this.skuCommAttrs = skuCommAttrs;
	}
	public String[] getProdAttrs() {
		return prodAttrs;
	}
	public void setProdAttrs(String[] prodAttrs) {
		this.prodAttrs = prodAttrs;
	}
	
	
}
