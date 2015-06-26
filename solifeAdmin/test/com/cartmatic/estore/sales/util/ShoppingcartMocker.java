
package com.cartmatic.estore.sales.util;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.common.model.cart.Shoppingcart;
import com.cartmatic.estore.common.model.cart.ShoppingcartItem;
import com.cartmatic.estore.common.model.cart.ShoppingcartPromotion;
import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.common.model.catalog.ProductSku;

public class ShoppingcartMocker {
	private static final Log	logger	= LogFactory
												.getLog(ShoppingcartMocker.class);

	public static Shoppingcart getData() {
		Shoppingcart cart = new Shoppingcart();
		Set<ShoppingcartItem> cartItems = new HashSet<ShoppingcartItem>();
		ShoppingcartItem cartItem;
		ProductSku sku;
		Product product;
		// 1---------------------------
		cartItem = new ShoppingcartItem();
		sku = new ProductSku();
		sku.setProductSkuId(1);
		sku.setProductSkuCode("nokia-n800");
		product = new Product();
		product.setProductId(1);
		product.setBrandId(2);
		sku.setProduct(product);
		cartItem.setProductSku(sku);
		cartItem.setPrice(new BigDecimal(5000));
		cartItem.setQuantity(2);
		cartItem.setItemType(Constants.ITEM_TYPE_PRODUCT);
		cartItem.setIsSaved(ShoppingcartItem.ISSAVED_NO);
		cartItem.setIsOnSale(ShoppingcartItem.ISONSALE_NO);
		cartItem.setIsWholesale(ShoppingcartItem.ISWHOLESALE_NO);
		cartItems.add(cartItem);
		// 2---------------------------
		cartItem = new ShoppingcartItem();
		sku = new ProductSku();
		sku.setProductSkuId(2);
		sku.setProductSkuCode("anycall900");
		product = new Product();
		product.setProductId(2);
		product.setBrandId(2);
		sku.setProduct(product);
		cartItem.setProductSku(sku);
		cartItem.setPrice(new BigDecimal(6000));
		cartItem.setQuantity(5);
		cartItem.setItemType(Constants.ITEM_TYPE_PRODUCT);
		cartItem.setIsSaved(ShoppingcartItem.ISSAVED_NO);
		cartItem.setIsOnSale(ShoppingcartItem.ISONSALE_NO);
		cartItem.setIsWholesale(ShoppingcartItem.ISWHOLESALE_NO);
		cartItems.add(cartItem);

		// 3---------------------------
		cartItem = new ShoppingcartItem();
		sku = new ProductSku();
		sku.setProductSkuId(3);
		sku.setProductSkuCode("koka_blue");
		product = new Product();
		product.setProductId(3);
		product.setBrandId(1);
		sku.setProduct(product);
		cartItem.setProductSku(sku);
		cartItem.setPrice(new BigDecimal(2500));
		cartItem.setQuantity(2);
		cartItem.setItemType(Constants.ITEM_TYPE_PRODUCT);
		cartItem.setIsSaved(ShoppingcartItem.ISSAVED_NO);
		cartItem.setIsOnSale(ShoppingcartItem.ISONSALE_NO);
		cartItem.setIsWholesale(ShoppingcartItem.ISWHOLESALE_NO);
		cartItems.add(cartItem);

		// 4---------------------------
		cartItem = new ShoppingcartItem();
		sku = new ProductSku();
		sku.setProductSkuId(4);
		sku.setProductSkuCode("koka_red");
		product = new Product();
		product.setProductId(3);
		product.setBrandId(1);
		sku.setProduct(product);
		cartItem.setProductSku(sku);
		cartItem.setPrice(new BigDecimal(3500));
		cartItem.setDiscountPrice(new BigDecimal(3200));
		cartItem.setQuantity(1);
		cartItem.setItemType(Constants.ITEM_TYPE_PRODUCT);
		cartItem.setIsSaved(ShoppingcartItem.ISSAVED_NO);
		cartItem.setIsOnSale(ShoppingcartItem.ISONSALE_YES);
		cartItem.setIsWholesale(ShoppingcartItem.ISWHOLESALE_NO);
		cartItems.add(cartItem);

		// 5---------------------------
		cartItem = new ShoppingcartItem();
		sku = new ProductSku();
		sku.setProductSkuId(5);
		sku.setProductSkuCode("vedio_large");
		product = new Product();
		product.setProductId(4);
		sku.setProduct(product);
		cartItem.setProductSku(sku);
		cartItem.setPrice(new BigDecimal(2000));
		cartItem.setDiscountPrice(new BigDecimal(1000));
		cartItem.setQuantity(1);
		cartItem.setItemType(Constants.ITEM_TYPE_PRODUCT);
		cartItem.setIsSaved(ShoppingcartItem.ISSAVED_NO);
		cartItem.setIsOnSale(ShoppingcartItem.ISONSALE_YES);
		cartItem.setIsWholesale(ShoppingcartItem.ISWHOLESALE_NO);
		cartItems.add(cartItem);

		cart.setShoppingCartItems(cartItems);
		// *********************************
		for (ShoppingcartItem o : cart.getShoppingCartItems()) {
			if (o.getIsSaved() == ShoppingcartItem.ISSAVED_NO) {
				//cart.setBuyNowItemsCount(cart.getBuyNowItemsCount()
				//		+ o.getQuantity());
			} else {
			//	cart.setBuyLaterItemsCount(cart.getBuyLaterItemsCount()
			//			+ o.getQuantity());
			}
		}

		return cart;
	}

	public static Shoppingcart getSingleData() {
		Shoppingcart cart = new Shoppingcart();
		Set<ShoppingcartItem> cartItems = new HashSet<ShoppingcartItem>();
		ShoppingcartItem cartItem;
		ProductSku sku;
		Product product;
		// 1---------------------------
		cartItem = new ShoppingcartItem();
		sku = new ProductSku();
		sku.setProductSkuId(1);
		sku.setProductSkuCode("nokia-n800");
		product = new Product();
		product.setProductId(1);
		product.setBrandId(2);
		sku.setProduct(product);
		cartItem.setProductSku(sku);
		cartItem.setPrice(new BigDecimal(5000));
		cartItem.setQuantity(2);
		cartItem.setItemType(Constants.ITEM_TYPE_PRODUCT);
		cartItem.setIsSaved(ShoppingcartItem.ISSAVED_NO);
		cartItem.setIsOnSale(ShoppingcartItem.ISONSALE_NO);
		cartItem.setIsWholesale(ShoppingcartItem.ISWHOLESALE_NO);
		cart.addShoppingCartItem(cartItem);
		return cart;
	}

	public static void print(Shoppingcart cart) {

		StringBuffer sb = new StringBuffer(200).append("\n");
		for (ShoppingcartItem item : cart.getShoppingCartItems()) {
			sb.append("[skuId-" + item.getProductSku().getProductSkuId() + "]");
			sb.append("[skuCode-" + item.getProductSku().getProductSkuCode()
					+ "]");
			sb
					.append("[productId-" + item.getProductSku().getProductId()
							+ "]");
			sb.append("[price-" + item.getPrice() + "]");
			sb.append("[discountPrice-" + item.getDiscountPrice() + "]");
			sb.append("[quantity-" + item.getQuantity() + "]");
			sb.append("[discountQuantity-" + item.getDiscountQuantity() + "]");
			sb.append("[itemDiscountAmount-" + item.getItemDiscountAmount()
					+ "]");
			sb.append("[itemType-" + item.getItemType() + "]");
			sb.append("[isSaved-" + item.getIsSaved() + "]");
			sb.append("[isOnSale-" + item.getIsOnSale() + "]");
			sb.append("[isWholesale-" + item.getIsWholesale() + "]");
			sb.append("[total-" + item.getTotal() + "]");
			sb.append("\n");

		}
		sb.append("[cartTotal-" + cart.getTotal() + "]");
		sb.append("[cartSubtotal-" + cart.getSubtotal() + "]");
		sb.append("[itemsCount-" + cart.getItemsCount() + "]");
		sb.append("[buyNowItemsCount-" + cart.getBuyNowItemsCount() + "]");
		sb.append("[buyLaterItemsCount-" + cart.getBuyLaterItemsCount() + "]");
		sb.append("\n");
		sb.append("[cartDiscountAmount-" + cart.getCartDiscountAmount() + "]");
		sb.append("[cartDiscountAmountRAM-" + cart.getCartDiscountAmountRAM()
				+ "]");
		sb.append("[shippingDiscountInfo-" + cart.getShippingDiscountInfo()
				+ "]");
		sb.append("[shippingDiscountInfoRAM-"
				+ cart.getShippingDiscountInfoRAM() + "]");
		sb.append("\n");
		sb.append("[gainedPoint-" + cart.getGainedPoint() + "]");
		sb.append("[gainedPointRAM-" + cart.getGainedPointRAM() + "]");
		sb.append("[gainedCouponType-" + cart.getGainedCouponTypeId() + "]");
		sb.append("[gainedCouponTypeRAM-" + cart.getGainedCouponTypeIdRAM()
				+ "]");
		sb.append("[usedCouponTypeNo-" + cart.getUsedCouponNo() + "]");
		sb.append("\n");

		for (ShoppingcartPromotion promo : cart.getShoppingCartPromotions()) {
			sb.append("[promoId-" + promo.getPromoRuleId() + "]");
			sb.append("[promoName-" + promo.getPromotionName() + "]");
			sb.append("\n");
		}
		logger.info(sb.toString());
		return;
	}
}
