
package com.cartmatic.estore.common.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.cartmatic.estore.framework.test.BaseTransactionalTestCase;
import com.cartmatic.estore.sales.model.EmailModel;

public class PromoServiceTest extends BaseTransactionalTestCase {

	private static final Log	logger	= LogFactory
												.getLog(PromoServiceTest.class);

	@Autowired
	private PromoService		promoService;

	

	// public void testAddCatalogPromoInfo() {
	// logger.debug("\n/*********************************************\n********catalog
	// Promo\n*********************************************/\n");
	// try {
	//
	// Collection<ProductSku> data = ProductSkuMocker.getData();
	// ProductSkuMocker.print(data);
	//
	// long startTimeInMilli = System.currentTimeMillis();
	// long startTimeInNano = System.nanoTime();
	//
	// PRuleEngine engine = new PRuleEngine(PRuleManager
	// .getCatalogPromotionRules());
	// // PRuleEngine engine = new PRuleEngine(getData());
	// data = promoService.appendPromoInfo(data);
	// long endTimeInMilli = System.currentTimeMillis();
	// long endTimeInNano = System.nanoTime();
	//
	// logger.info(new StringBuffer().append("ur~~~ it cost time ")
	// .append(endTimeInMilli - startTimeInMilli).append(
	// " milliseconds").toString());
	// logger.info(new StringBuffer().append("ur~~~ it cost time ")
	// .append(endTimeInNano - startTimeInNano).append(
	// " nanoseconds").toString());
	// ProductSkuMocker.print(data);
	// logger.info("---");
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	//
	// }
	//
	// public void testAddCartPromoInfo() {
	// logger.debug("\n/*********************************************\n********cart
	// Promo\n*********************************************/\n");
	//		
	// try {
	//
	// Shoppingcart data = ShoppingcartMocker.getData();
	// ShoppingcartMocker.print(data);
	// // it will cost about 16ms to load the manager from spring
	// Customer user = new Customer();
	// Membership membership = new Membership();
	// // membership.setMembershipId(4);
	// // user.setMembership(membership);
	// user.setAppuserId(3);
	// user.setMembershipId(4);
	//
	// long startTimeInMilli = System.currentTimeMillis();
	// long startTimeInNano = System.nanoTime();
	// data = promoService.appendPromoInfo(data, (AppUser)user);
	//
	// long endTimeInMilli = System.currentTimeMillis();
	// long endTimeInNano = System.nanoTime();
	//
	// logger.info(new StringBuffer().append("ur~~~ it cost time ")
	// .append(endTimeInMilli - startTimeInMilli).append(
	// " milliseconds").toString());
	// logger.info(new StringBuffer().append("ur~~~ it cost time ")
	// .append(endTimeInNano - startTimeInNano).append(
	// " nanoseconds").toString());
	// ShoppingcartMocker.print(data);
	// ShoppingcartItem cartItem ;
	// ProductSku sku ;
	// Product product;
	// cartItem = new ShoppingcartItem();
	// sku = new ProductSku();
	// sku.setProductSkuId(6);
	//			
	// sku.setProductSkuCode("vedio_middle");
	// product = new Product();
	// product.setProductId(4);
	//			
	// product.setBrandId(2);
	// sku.setProduct(product);
	// cartItem.setProductSku(sku);
	// cartItem.setPrice(new BigDecimal(2500));
	// cartItem.setQuantity(2);
	// cartItem.setItemType(ShoppingcartItem.ITEMTYPE_PRODUCT);
	// cartItem.setIsSaved(ShoppingcartItem.ISSAVED_NO);
	// cartItem.setIsOnSale(ShoppingcartItem.ISONSALE_NO);
	// cartItem.setIsWholesale(ShoppingcartItem.ISWHOLESALE_NO);
	// data.addShoppingCartItem(cartItem);
	//			
	// data = promoService.appendPromoInfo(data, user);
	// ShoppingcartMocker.print(data);
	//			
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	//
	// }
	//
	// public void testAddCouponPromoInfo() {
	//
	// try {
	//
	// Shoppingcart data = ShoppingcartMocker.getData();
	// ShoppingcartMocker.print(data);
	// // it will cost about 16ms to load the manager from spring
	// Customer user = new Customer();
	// Membership membership = new Membership();
	// // membership.setMembershipId(4);
	// // user.setMembership(membership);
	// user.setAppuserId(3);
	// user.setMembershipId(4);
	//
	// Coupon coupon = new Coupon();
	// coupon.setCouponNo("a838491219709535");
	//
	// long startTimeInMilli = System.currentTimeMillis();
	// long startTimeInNano = System.nanoTime();
	// data = promoService.appendPromoInfo(data, user);
	//
	// long endTimeInMilli = System.currentTimeMillis();
	// long endTimeInNano = System.nanoTime();
	//
	// logger.info(new StringBuffer().append("ur~~~ it cost time ")
	// .append(endTimeInMilli - startTimeInMilli).append(
	// " milliseconds").toString());
	// logger.info(new StringBuffer().append("ur~~~ it cost time ")
	// .append(endTimeInNano - startTimeInNano).append(
	// " nanoseconds").toString());
	// ShoppingcartMocker.print(data);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	//
	// }
	//
	//	
	//
	// public void testGetShippingFee() {
	// String shippingDiscountInfo = ",1-%0.1,2-$300.50,1-$10,1-%0.2";
	// BigDecimal shippingFee = promoService.getShippingFee(
	// shippingDiscountInfo, 1, new BigDecimal("120"));
	// logger.info("shippingfee=" + shippingFee);
	// }
	// public void testCheckCoupon(){
	// short status = promoService.checkCoupon("a838491219709535");
	// logger.info("status===="+status);
	// }
	// public void testUseCoupon(){
	// short status = promoService.useCoupon("a838491219709535");
	// logger.info("status===="+status);
	// }
//
//	public void testMockAddCatalogPromoInfo() {
//		logger
//				.debug("\n/*********************************************\n********catalogPromo\n*********************************************/\n");
//		try {
//
//			Collection<ProductSku> data = ProductSkuMocker.getData();
//			ProductSkuMocker.print(data);
//			for (ProductSku sku : data) {
//				long startTimeInMilli = System.currentTimeMillis();
//				long startTimeInNano = System.nanoTime();
//
//				PRuleEngine engine = new PRuleEngine(PRuleManager
//						.getCatalogPromotionRules());
//				// PRuleEngine engine = new PRuleEngine(getData());
//
//				Collection<ProductSku> results;
//				results = promoService.getPromoInfoUsedInProductModule(sku);
//				long endTimeInMilli = System.currentTimeMillis();
//				long endTimeInNano = System.nanoTime();
//
//				logger.info(new StringBuffer().append("ur~~~ it cost time ")
//						.append(endTimeInMilli - startTimeInMilli).append(
//								" milliseconds").toString());
//				logger.info(new StringBuffer().append("ur~~~ it cost time ")
//						.append(endTimeInNano - startTimeInNano).append(
//								" nanoseconds").toString());
//				ProductSkuMocker.print(results);
//				logger.info("---");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}
	@Test
	public void testDoSendCoupon(){
		EmailModel emailModel = new EmailModel();
		emailModel.setFirstName("he");
		emailModel.setLastName("ming");
		emailModel.setEmail("heqingming@cartmatic.com");		
		promoService.doSendCoupon(73, emailModel);
	}

}
