package com.cartmatic.estore.catalog;

public class CatalogConstants {
	/**
	 * 产品更多图片
	 */
	public static final Short	PRODUCT_MEDIA_TYPE_MORE_IMAGE=new Short("0");
	
	/**
	 * 产品附件
	 */
	public static final Short	PRODUCT_MEDIA_TYPE_ACCESSORIES=new Short("1");
	
	/**
	 * 产品多媒体
	 */
	public static final Short	PRODUCT_MEDIA_TYPE_MULTIMEDIA=new Short("2");
	
	/**
	 * 有库存才可以购买
	 */
	public static final Short	PRODUCT_AVAILABILITY_ONLY_IN_STOCK=new Short("1");
	
	/**
	 * 预订
	 */
	public static final Short	PRODUCT_AVAILABILITY_PRE_ORDER=new Short("2");
	
	/**
	 * 缺货销售
	 */
	public static final Short	PRODUCT_AVAILABILITY_BACK_ORDER=new Short("3");
	
	/**
	 * 永远可售
	 */
	public static final Short	PRODUCT_AVAILABILITY_ALWAYS_SELL=new Short("4");
	
	/**
	 * 无库存销售
	 */
	public static final Short	PRODUCT_AVAILABILITY_NOT_IN_STOCK_SELL=new Short("5");
	

	/**
	 * 实体
	 */
	public static final Short	SKU_KIND_ENTITY=new Short("1");
	
	/**
	 * 服务
	 */
	public static final Short	SKU_KIND_SERVICE=new Short("2");
	
	/**
	 * 软件（下载）
	 */
	public static final Short	SKU_KIND_SOFTWARE=new Short("3");
	

	/**
	 * 卡类产品
	 */
	public static final Short	SKU_KIND_CARD=new Short("4");
	
	/**
	 * 普通产品
	 */
	public static final Short	PRODUCT_KIND_COMMON=new Short("1");

	/**
	 * 变种产品（即带SKU Option）
	 */
	public static final Short	PRODUCT_KIND_VARIATION=new Short("2");

	/**
	 * 产品包
	 */
	public static final Short	PRODUCT_KIND_PACKAGE=new Short("3");
}
