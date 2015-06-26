package com.cartmatic.estore.common.model.cart;

import com.cartmatic.estore.common.model.catalog.ProductSku;

/**
 * 记录shoppingcartItem的统计信息
 * 
 * @author Administrator
 *
 */
public class ShoppingcartItemStat {

	protected Short isSaved;
	protected Short itemType;
	protected Integer productSkuId;
	/**
	 * 数量
	 */
	private Long quantity;
	/**
	 * productSku
	 */
	private ProductSku productSku;
	
	public Integer getProductSkuId() {
		return productSkuId;
	}
	public void setProductSkuId(Integer productSkuId) {
		this.productSkuId = productSkuId;
	}
	public Short getIsSaved() {
		return isSaved;
	}
	public void setIsSaved(Short isSaved) {
		this.isSaved = isSaved;
	}
	public Short getItemType() {
		return itemType;
	}
	public void setItemType(Short itemType) {
		this.itemType = itemType;
	}
	public Long getQuantity() {
		return quantity;
	}
	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}
	public ProductSku getProductSku() {
		return productSku;
	}
	public void setProductSku(ProductSku productSku) {
		this.productSku = productSku;
	}
	
	
}
