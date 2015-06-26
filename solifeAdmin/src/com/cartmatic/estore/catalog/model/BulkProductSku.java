package com.cartmatic.estore.catalog.model;

import java.util.HashMap;
import java.util.Map;

import com.cartmatic.estore.common.model.catalog.ProductSku;

public class BulkProductSku {
	private ProductSku productSku;
	private Integer productSkuId;
	private Map<String,BulkField> skuCommAttrs=new HashMap<String, BulkField>();
	

	public Integer getProductSkuId() {
		return productSkuId;
	}

	public void setProductSkuId(Integer productSkuId) {
		this.productSkuId = productSkuId;
	}

	public Map<String, BulkField> getSkuCommAttrs() {
		return skuCommAttrs;
	}

	public void setSkuCommAttrs(Map<String, BulkField> skuCommAttrs) {
		this.skuCommAttrs = skuCommAttrs;
	}

	public ProductSku getProductSku() {
		return productSku;
	}

	public void setProductSku(ProductSku productSku) {
		this.productSku = productSku;
	}

}
