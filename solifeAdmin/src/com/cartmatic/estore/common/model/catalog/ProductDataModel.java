package com.cartmatic.estore.common.model.catalog;

import java.util.List;

import com.cartmatic.estore.common.model.attribute.AttributeValue;

public class ProductDataModel {
	private Product product;
	
	private String fullDescription;
	
	private String shortDescription;
	
	private String imageDescription;
	
	private Integer []mainCategoryIds;
	
	private Integer []categoryIds;
	
	private String productMediaIds[];
	
	private String mediaUrls[];
	
	private String mediaUrls_d[];
	
	private String productMediaTypes[];
	
	private String mediaDescription[];
	
	private String mediaProductSkus[];
	
	private String productMedia_deleteds[];
	
	private ProductSku defaultProductSku;
	
	private String wholesalePrices[];
	
	private Integer[]productPackageItemIds;
	                                   
	private Integer[]productPackageQuantity;
	
	private boolean onlySaveProductInfo;
	
	private Integer defaultSupplierId;
	
	private Integer []supplierIds;

	/**
	 * 用于保存更新自定义属性
	 */
	private List<AttributeValue> attributeValues;
	
	/**
	 * 产品附件
	 */
	private Integer[]accessoryIds;
	
	//供应商产品创建在线产品
	private Short productKind;
	
	//供应商产品创建在线产品
	private Integer productTypeId;
	
	//手绘图Id
	private String handDrawId;
	//手绘图Url
	private String handDrawUrl;
	
	//---------------------------产品详情	上方所需额外图	start-------------------------------
	private String productMediaUpIds[];
	
	private String mediaUrlsUp[];
	
	private String productMediaTypesUp[];
	
	private String mediaDescriptionUp[];
	
	private String productMedia_deletedsUp[];

	//---------------------------产品详情	上方所需额外图	end-------------------------------
	
	public Integer[] getAccessoryIds() {
		return accessoryIds;
	}

	public void setAccessoryIds(Integer[] accessoryIds) {
		this.accessoryIds = accessoryIds;
	}

	public boolean isOnlySaveProductInfo() {
		return onlySaveProductInfo;
	}

	public void setOnlySaveProductInfo(boolean onlySaveProductInfo) {
		this.onlySaveProductInfo = onlySaveProductInfo;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getFullDescription() {
		return fullDescription;
	}

	public void setFullDescription(String fullDescription) {
		this.fullDescription = fullDescription;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public Integer[] getMainCategoryIds() {
		return mainCategoryIds;
	}

	public void setMainCategoryIds(Integer[] mainCategoryIds) {
		this.mainCategoryIds = mainCategoryIds;
	}

	public Integer[] getCategoryIds() {
		return categoryIds;
	}

	public void setCategoryIds(Integer[] categoryIds) {
		this.categoryIds = categoryIds;
	}

	public String[] getProductMediaIds() {
		return productMediaIds;
	}

	public void setProductMediaIds(String[] productMediaIds) {
		this.productMediaIds = productMediaIds;
	}

	public String[] getMediaUrls() {
		return mediaUrls;
	}

	public void setMediaUrls(String[] mediaUrls) {
		this.mediaUrls = mediaUrls;
	}

	public String[] getProductMediaTypes() {
		return productMediaTypes;
	}

	public void setProductMediaTypes(String[] productMediaTypes) {
		this.productMediaTypes = productMediaTypes;
	}

	public String[] getMediaDescription() {
		return mediaDescription;
	}

	public void setMediaDescription(String[] mediaDescription) {
		this.mediaDescription = mediaDescription;
	}

	public ProductSku getDefaultProductSku() {
		return defaultProductSku;
	}

	public void setDefaultProductSku(ProductSku defaultProductSku) {
		this.defaultProductSku = defaultProductSku;
	}

	public String[] getWholesalePrices() {
		return wholesalePrices;
	}

	public void setWholesalePrices(String[] wholesalePrices) {
		this.wholesalePrices = wholesalePrices;
	}

	public Integer[] getProductPackageItemIds() {
		return productPackageItemIds;
	}

	public void setProductPackageItemIds(Integer[] productPackageItemIds) {
		this.productPackageItemIds = productPackageItemIds;
	}

	public Integer[] getProductPackageQuantity() {
		return productPackageQuantity;
	}

	public void setProductPackageQuantity(Integer[] productPackageQuantity) {
		this.productPackageQuantity = productPackageQuantity;
	}

	public void setAttributeValues(List<AttributeValue> attributeValues) {
		this.attributeValues = attributeValues;
	}

	public List<AttributeValue> getAttributeValues() {
		return attributeValues;
	}

	public String[] getProductMedia_deleteds() {
		return productMedia_deleteds;
	}

	public void setProductMedia_deleteds(String[] productMedia_deleteds) {
		this.productMedia_deleteds = productMedia_deleteds;
	}

	public Integer getDefaultSupplierId() {
		return defaultSupplierId;
	}

	public void setDefaultSupplierId(Integer defaultSupplierId) {
		this.defaultSupplierId = defaultSupplierId;
	}

	public Integer[] getSupplierIds() {
		return supplierIds;
	}

	public void setSupplierIds(Integer[] supplierIds) {
		this.supplierIds = supplierIds;
	}

	public Short getProductKind() {
		return productKind;
	}

	public void setProductKind(Short productKind) {
		this.productKind = productKind;
	}

	public Integer getProductTypeId() {
		return productTypeId;
	}

	public void setProductTypeId(Integer productTypeId) {
		this.productTypeId = productTypeId;
	}

	public String getImageDescription() {
		return imageDescription;
	}

	public void setImageDescription(String imageDescription) {
		this.imageDescription = imageDescription;
	}

	public String[] getMediaUrls_d() {
		return mediaUrls_d;
	}

	public void setMediaUrls_d(String[] mediaUrlsD) {
		mediaUrls_d = mediaUrlsD;
	}

	public String getHandDrawUrl() {
		return handDrawUrl;
	}

	public void setHandDrawUrl(String handDrawUrl) {
		this.handDrawUrl = handDrawUrl;
	}

	public String getHandDrawId() {
		return handDrawId;
	}

	public void setHandDrawId(String handDrawId) {
		this.handDrawId = handDrawId;
	}

	public String[] getProductMediaUpIds() {
		return productMediaUpIds;
	}

	public void setProductMediaUpIds(String[] productMediaUpIds) {
		this.productMediaUpIds = productMediaUpIds;
	}

	public String[] getMediaUrlsUp() {
		return mediaUrlsUp;
	}

	public void setMediaUrlsUp(String[] mediaUrlsUp) {
		this.mediaUrlsUp = mediaUrlsUp;
	}

	public String[] getProductMediaTypesUp() {
		return productMediaTypesUp;
	}

	public void setProductMediaTypesUp(String[] productMediaTypesUp) {
		this.productMediaTypesUp = productMediaTypesUp;
	}

	public String[] getMediaDescriptionUp() {
		return mediaDescriptionUp;
	}

	public void setMediaDescriptionUp(String[] mediaDescriptionUp) {
		this.mediaDescriptionUp = mediaDescriptionUp;
	}

	public String[] getProductMedia_deletedsUp() {
		return productMedia_deletedsUp;
	}

	public void setProductMedia_deletedsUp(String[] productMediaDeletedsUp) {
		productMedia_deletedsUp = productMediaDeletedsUp;
	}

	public String[] getMediaProductSkus() {
		return mediaProductSkus;
	}

	public void setMediaProductSkus(String[] mediaProductSkus) {
		this.mediaProductSkus = mediaProductSkus;
	}
	
}
