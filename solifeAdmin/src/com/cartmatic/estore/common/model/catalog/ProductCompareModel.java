package com.cartmatic.estore.common.model.catalog;

import java.util.List;
import java.util.Map;

import com.cartmatic.estore.common.model.attribute.Attribute;
import com.cartmatic.estore.common.model.attribute.ProductAttrValue;

public class ProductCompareModel {
	/**
	 * 比较的产品 
	 */
	private List<Product> productList;
	
	/**
	 * 检查非必填字段是否可以显示，即非全null或空
	 */
	private boolean showImage=false; 
	
	private boolean showBrand=false;
	
	private boolean showWeight=false;
	
	private boolean showWholesalePrice=false;
	
	private boolean showShortDescription=false;
	
	private boolean showMinOrderQuantity=false;
	
	private boolean showProviderName=false;
	
	/**
	 * 产品自定义属性
	 */
	private Map<Attribute,List<ProductAttrValue>> productAttribute;
	
	/**
	 * 产品变种属性
	 */
	private Map<SkuOption,List<List<SkuOptionValue>>> productSkuOptionValue;

	public List<Product> getProductList() {
		return productList;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}

	public boolean isShowImage() {
		return showImage;
	}

	public void setShowImage(boolean showImage) {
		this.showImage = showImage;
	}

	public boolean isShowBrand() {
		return showBrand;
	}

	public void setShowBrand(boolean showBrand) {
		this.showBrand = showBrand;
	}

	public boolean isShowWeight() {
		return showWeight;
	}

	public void setShowWeight(boolean showWeight) {
		this.showWeight = showWeight;
	}

	public boolean isShowWholesalePrice() {
		return showWholesalePrice;
	}

	public void setShowWholesalePrice(boolean showWholesalePrice) {
		this.showWholesalePrice = showWholesalePrice;
	}

	public boolean isShowShortDescription() {
		return showShortDescription;
	}

	public void setShowShortDescription(boolean showShortDescription) {
		this.showShortDescription = showShortDescription;
	}

	public Map<Attribute, List<ProductAttrValue>> getProductAttribute() {
		return productAttribute;
	}

	public void setProductAttribute(
			Map<Attribute, List<ProductAttrValue>> productAttribute) {
		this.productAttribute = productAttribute;
	}

	public Map<SkuOption, List<List<SkuOptionValue>>> getProductSkuOptionValue() {
		return productSkuOptionValue;
	}

	public void setProductSkuOptionValue(
			Map<SkuOption, List<List<SkuOptionValue>>> productSkuOptionValue) {
		this.productSkuOptionValue = productSkuOptionValue;
	}

	public void setShowMinOrderQuantity(boolean showMinOrderQuantity) {
		this.showMinOrderQuantity = showMinOrderQuantity;
	}

	public void setShowProviderName(boolean showProviderName) {
		this.showProviderName = showProviderName;
	}

	public boolean isShowMinOrderQuantity() {
		return showMinOrderQuantity;
	}

	public boolean isShowProviderName() {
		return showProviderName;
	}
}
