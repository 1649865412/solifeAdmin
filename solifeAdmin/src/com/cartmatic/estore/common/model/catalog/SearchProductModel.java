package com.cartmatic.estore.common.model.catalog;

import java.math.BigDecimal;

public class SearchProductModel {
	private Integer productId;
	private String productName;
	/**
	 * 产品实际访问URL,非产品属性url
	 */
	private String url;
	private String image;
	private BigDecimal price;
	
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	/**
	 * @return 产品实际访问URL,非产品属性url
	 */
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
}
