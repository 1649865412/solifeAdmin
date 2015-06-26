package com.cartmatic.estore.cart.model;

public class CheckoutPagingModel {
	public static final String SESSION_KEY = "Current_Checkout_Paging_Model";
	/**
	 * 1为购物车
	 * 2账号登陆
	 * 3地址薄
	 * 4支付
	 * 5完成
	 */
	private Integer currentStep;
	private Integer shippingAddressId;
	/**
	 * 估算运费国家
	 */
	private Integer countryId;
	
	
	public Integer getCountryId() {
		return countryId;
	}
	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}
	public Integer getCurrentStep() {
		return currentStep;
	}
	public void setCurrentStep(Integer currentStep) {
		this.currentStep = currentStep;
	}
	public Integer getShippingAddressId() {
		return shippingAddressId;
	}
	public void setShippingAddressId(Integer shippingAddressId) {
		this.shippingAddressId = shippingAddressId;
	}

	
}
