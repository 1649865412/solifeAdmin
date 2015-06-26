package com.cartmatic.estore.catalog.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cartmatic.estore.common.model.catalog.Product;

public class BulkProduct {
	private Product product;
	private Integer productId;
	private Map<String,BulkField> prodCommAttrs=new HashMap<String, BulkField>();
	private Map<String,BulkField> prodAttrs=new HashMap<String, BulkField>();
	private List<BulkProductSku> productSkus=new ArrayList<BulkProductSku>();

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Map<String, BulkField> getProdCommAttrs() {
		return prodCommAttrs;
	}

	public void setProdCommAttrs(Map<String, BulkField> prodCommAttrs) {
		this.prodCommAttrs = prodCommAttrs;
	}

	public Map<String, BulkField> getProdAttrs() {
		return prodAttrs;
	}

	public void setProdAttrs(Map<String, BulkField> prodAttrs) {
		this.prodAttrs = prodAttrs;
	}

	public List<BulkProductSku> getProductSkus() {
		return productSkus;
	}

	public void setProductSkus(List<BulkProductSku> productSkus) {
		this.productSkus = productSkus;
	}



	
}
