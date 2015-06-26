package com.cartmatic.estore.common.model.customer;

import com.cartmatic.estore.common.helper.CatalogHelper;
import com.cartmatic.estore.common.model.customer.base.WishlistItemTbl;

/**
 * Model class for WishlistItem. Add not database mapped fileds in this class.
 */
public class WishlistItem extends WishlistItemTbl {

	public String getProductUrl() {
		String url = CatalogHelper.getInstance().getProductUrl(this.product,null, null);
		return url;
	}

	/**
	 * Default Empty Constructor for class WishlistItem
	 */
	public WishlistItem() {
		super();
	}

	/**
	 * 定义实体的业务名取值； wishlistItemName 必须手工完成这个部分，否则编译不通过。
	 */
	public String getWishlistItemName() {
		if (wishlistItemId == null)
			return "";
		else
			// 返回一个指定有业务意义的属性值;
			// 如：product的VO就用product.productName
			return this.wishlistItemId.toString();
	}

	/**
	 * Default Key Fields Constructor for class WishlistItem
	 */
	public WishlistItem(Integer in_wishlistItemId) {
		super(in_wishlistItemId);
	}

}
