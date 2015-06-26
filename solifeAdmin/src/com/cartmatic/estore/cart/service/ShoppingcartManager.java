
package com.cartmatic.estore.cart.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cartmatic.estore.common.model.cart.Shoppingcart;
import com.cartmatic.estore.common.model.cart.ShoppingcartItem;
import com.cartmatic.estore.common.model.cart.ShoppingcartItemGc;
import com.cartmatic.estore.common.model.catalog.ProductSku;
import com.cartmatic.estore.common.model.customer.Customer;
import com.cartmatic.estore.core.service.GenericManager;
import com.cartmatic.estore.exception.OutOfStockException;

/**
 * 
 * @author huangwenmin 2008-10-23
 *
 */
public interface ShoppingcartManager extends GenericManager<Shoppingcart> {
	
	/**
	 * 使用Uuid从数据库中获取购物车
	 * @param uuid
	 * @return
	 */
	public Shoppingcart loadShoppingcartByUuid(String uuid);
	


	/**
	 * 合并两辆购物车。
	 * <h3>一般情况下，此方法用于匿名用户登陆后进行cookie中的购物和他原先存储在数据库中的购物车合并操作</h3>
	 * 合并之后，将会删除登陆之前那辆匿名购物车，返回<h3>合并之后的购物车</h3>
	 * @param cartDB
	 *        用户之前在数据库中购物车
	 * @param cartCookie
	 *        匿名用户在登陆之前由于发生“添加商品到购物车/收藏夹”而产生的购物车
	 * @return
	 */
	public Shoppingcart doUniteShoppingcarts(String cartDBUuid, String cartCookieUuid, HttpServletRequest request, HttpServletResponse response);

	/**
	 * 从购物车里面删除一个商品
	 * 
	 * @param cart
	 * @param shoppingcartItemId
	 */
	public Shoppingcart doDeleteItem(String cartUuid, String shoppingcartItemId, HttpServletRequest request, HttpServletResponse response);

	/**
	 * 将购物车内的收藏夹的商品移到购物车（现在购买）
	 * 
	 * @param request
	 * @param response
	 * @param shoppingcartItemId
	 */
	public boolean doMoveFavoriteItemToCart(String cartUuid, Integer itemId,  HttpServletRequest request, HttpServletResponse response);
	

	/**
	 * 删除所有过期的购物车，过期周期在系统参数设置
	 */
	public void deleteExpirationShoppingcarts();

	/**
	 * 将商品放入购物车中
	 * <h3>此时，商品的isSaved=0</h3>
	 * @param cart
	 *        购物车
	 * @param product
	 *        商品
	 * @return
	 */
	public Shoppingcart addProductToCart(String cartUuid, String productsku, Integer quantity, String accessoryIds, HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	
	/**
	 * 将商品放入购物车中
	 * <h3>此时，商品的isSaved=0</h3>
	 * @param cart
	 *        购物车
	 * @param product
	 *        商品
	 * @return
	 */
	public Shoppingcart addProductToCart(String cartUuid, String productsku, Integer quantity, String accessoryIds,String customMade, HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	/**
	 * 将商品及该商品的附属产品放入购物车
	 * 这样的情况一般都出现在某个商品有附加商品，比如相机的相机架，售后服务等。
	 * @param cart
	 * @param parent
	 *        商品
	 * @param children
	 *        某商品的附加商品
	 * @param isSaved
	 *        true 放入收藏夹；false 放入购物车
	 * @return
	 */
	public Shoppingcart addMultiProductToCart(String cartUuid, String parentSku, String[] childrenSku,Integer quantity, String accessoryIds, short isSaved, HttpServletRequest request, HttpServletResponse response) throws Exception;

	
	/**
	 * 添加产品到购物车,仅供ws使用
	 * @param cart
	 * @param sku 
	 * @param quantity
	 * @param accessoryIds 
	 * @param customMade
	 * @return
	 * @throws Exception
	 */ 
	public Shoppingcart addProductToCart4WS(Shoppingcart cart, ProductSku sku, Integer quantity, String accessoryIds,String customMade) throws Exception;
	
	/**
	 * 将商品放入收藏夹中
	 * <h3>此时，商品的isSaved=1</h3>
	 * @param cart
	 *        购物车
	 * @param product
	 *        某商品
	 */
	public Shoppingcart addProductToFavorite(String cartUuid, String productsku, Integer quantity, String accessoryIds, HttpServletRequest request, HttpServletResponse response) ;
	
	/**
	 * 将礼券放入购物车中
	 * @param sgc
	 * @param request
	 * @param response
	 * @return
	 */
	public Shoppingcart addGcToCart(String cartUuid,ShoppingcartItemGc sgc, HttpServletRequest request, HttpServletResponse response);
	/**
	 * 将购物车中的item放入收藏夹
	 * @param cart 
	 *        购物车
	 * @param cartItem
	 *        要移动的该购物车中的商品
	 */
	public void doMoveCartItemToFavorite(String cartUuid, Integer itemId, HttpServletRequest request, HttpServletResponse response);
	
	/**
	 * 清空购物车，在情况的同时，可以将购物车的商品放入收藏夹。
	 * @param cart
	 *        要清空的购物车
	 */
	public Shoppingcart doClearShoppingcart(String cartUuid, HttpServletRequest request, HttpServletResponse response);
	
	/**
	 * 清空收藏夹中的所有商品
	 * @param cartUuid
	 * @param request
	 * @param response
	 * @return
	 */
	public Shoppingcart doClearFavorite(String cartUuid, HttpServletRequest request, HttpServletResponse response);
	
	
	/**
	 * 用skuNew更新skuOld，当只改变数量时，skuNew=skuOld
	 * @param cartUuid
	 *        购物车uuid
	 * @param skuOld
	 *        需要更新的产品
	 * @param skuNew
	 *        新产品
	 * @param newQuantity
	 *        数量，此数字将会替换原来的item数量
	 * @param request
	 * @param response
	 * @return
	 */
	public boolean updateShoppingcartItem(String itemId, String skuNew, Integer newQuantity,HttpServletRequest request, HttpServletResponse response);
	
	/**
	 * 初始化一辆新的购物车
	 * @param customer
	 * @return
	 */
	public Shoppingcart initShoppingcart(Customer customer);
	
	/**
	 * 刷新购物车，此方法会将最新数据写入cookie中
	 * @param cartUuid
	 * @param request
	 * @param response
	 * @return
	 */
	public Shoppingcart refreshCart(String cartUuid,HttpServletRequest request, HttpServletResponse response);
	

	/**
	 * 使用优惠券
	 * @param no
	 * @param uuid
	 */
	public short doUseCoupon(String no,String uuid);
	
	/**
	 * 不使用优惠券
	 * @param uuid
	 */
	public void doNotUseCoupon(String uuid);
	
	/**
	 * 移除购物车内的所有促销信息
	 * @param cart
	 */
	public void doNotUsePromo(Shoppingcart cart);
	
	/**
	 * 检查Item的库存库存情况
	 * @param item
	 * @param skuCode
	 * @return
	 */
	public boolean checkItemInventory(ShoppingcartItem item, String skuCode);
	
	/**
	 * 检查购物车是否含有没有库存的产品
	 * @return
	 */
	public boolean doCheckCartOutOfStock(Shoppingcart cart);
}
