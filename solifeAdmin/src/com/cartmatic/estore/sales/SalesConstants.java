
package com.cartmatic.estore.sales;

import java.math.BigDecimal;

public class SalesConstants {

	public static enum RECOMMENDED_SOURCE_TYPE {
		PRODUCT, CATEGORY
	};

	// 优惠券样式 数字
	public static final int			COUPON_NO_STYLE_NUMBER					= 1;
	// 优惠券样式 英文字符
	public static final int			COUPON_NO_STYLE_STRING					= 2;

	public static final int			MAX_GEN_TIMES							= 2000;
	public static final int			MAX_GEN_TRY_TIMES						= 100;

	public static final String		RECOMMENDED_TYPE_SOURCE_PRODUCT			= "PRODUCT";
	public static final String		RECOMMENDED_TYPE_SOURCE_CATEGORY		= "CATEGORY";

	public static final int			DEFAULT_RECOMMENDED_PRODUCT_COUNT		= 10;
	// 推荐规则
	public static final int			RECOMMENDED_RULE_CODE_NONE				= 0;			// 无自动推荐规则
	public static final int			RECOMMENDED_RULE_CODE_BUY_COUNT			= 1;			// 目录内商品购买数量推荐规则（适用于商品目录）
	
	public static final int			RECOMMENDED_RULE_CODE_FAVORIATE			= 2;			// 目录收藏存推荐规则（适用于商品目录）
	public static final int			RECOMMENDED_RULE_CODE_NEW				= 3;			// 目录内商品发布时间推荐规则（适用于商品目录）
	public static final int			RECOMMENDED_RULE_CODE_ALSO_BUY			= 4;			// 商品购买关联(AlsoBuy)推荐规则（适用于商品）
	public static final int			RECOMMENDED_RULE_CODE_SIMILAR_PRODUCT	= 5;			// 相似商品推荐规则（适用于商品
	public static final int			RECOMMENDED_RULE_CODE_SIMILAR_PRICE		= 6;			// 同商品主目录内的价格相近的商品推荐规则（适用于商品）
	public static final int			RECOMMENDED_RULE_CODE_SAME_BRAND		= 7;			// 同商品主目录内的相同品牌的商品推荐规则（适用于商品）
	public static final int			RECOMMENDED_RULE_CODE_SIMILAR_CODE		= 8;			// 同商品主目录内的相同品牌的商品推荐规则（适用于商品）
	// 推荐规则排序
	public static final int			RECOMMENDED_RULE_SORT_ASC				= 1;
	public static final int			RECOMMENDED_RULE_SORT_DESC				= -1;

	public static final BigDecimal	RECOMMENDED_SIMILAR_PRICE_FLOOR_LIMIT	= new BigDecimal(
																					"0.8");
	public static final BigDecimal	RECOMMENDED_SIMILAR_PRICE_UPPER_LIMIT	= new BigDecimal(
																					"1.2");

}
