package com.cartmatic.estore.cart.model;

import java.math.BigDecimal;

public class AddToCartResult {
	/**
	 * 添加购物车结果，成功/失败.true/false
	 */
	private Short result;
	
	/**
	 * 添加购物车返回信息
	 */
	private String message;
	
	/**
	 * 购物车产品数量
	 */
	private Integer cartItemQty;
	
	/**
	 * 购物车总价格
	 */
	private BigDecimal total;
	
	/**
	 * 最后添加到购物车的产品
	 */
	private String lastAddToCartItemName;
	/**
	 * 最后添加到产品的价格
	 */
	private BigDecimal lastAddToCartItemPrice;
	/**
	 * 最后添加到购物车的数量
	 */
	private Integer lastAddToCartItemQty;
	/**
	 * 最后添加到购物车的图片
	 */
	private String lastAddToCartItemImage;
	public Short getResult() {
		return result;
	}
	public void setResult(Short result) {
		this.result = result;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Integer getCartItemQty() {
		return cartItemQty;
	}
	public void setCartItemQty(Integer cartItemQty) {
		this.cartItemQty = cartItemQty;
	}
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	public String getLastAddToCartItemName() {
		return lastAddToCartItemName;
	}
	public void setLastAddToCartItemName(String lastAddToCartItemName) {
		this.lastAddToCartItemName = lastAddToCartItemName;
	}
	public BigDecimal getLastAddToCartItemPrice() {
		return lastAddToCartItemPrice;
	}
	public void setLastAddToCartItemPrice(BigDecimal lastAddToCartItemPrice) {
		this.lastAddToCartItemPrice = lastAddToCartItemPrice;
	}
	public Integer getLastAddToCartItemQty() {
		return lastAddToCartItemQty;
	}
	public void setLastAddToCartItemQty(Integer lastAddToCartItemQty) {
		this.lastAddToCartItemQty = lastAddToCartItemQty;
	}
	public String getLastAddToCartItemImage() {
		return lastAddToCartItemImage;
	}
	public void setLastAddToCartItemImage(String lastAddToCartItemImage) {
		this.lastAddToCartItemImage = lastAddToCartItemImage;
	}
	

	
	
}
