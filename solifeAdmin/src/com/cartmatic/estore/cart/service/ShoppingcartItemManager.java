package com.cartmatic.estore.cart.service;

import java.util.List;

import com.cartmatic.estore.common.model.cart.ShoppingcartItem;
import com.cartmatic.estore.common.model.cart.ShoppingcartItemGc;
import com.cartmatic.estore.common.model.cart.ShoppingcartItemStat;
import com.cartmatic.estore.common.model.catalog.ProductSku;
import com.cartmatic.estore.core.search.SearchCriteria;
import com.cartmatic.estore.core.service.GenericManager;

/**
 * Manager interface for ShoppingcartItem, responsible for business processing, and communicate between web and persistence layer.
 *
 */
public interface ShoppingcartItemManager extends GenericManager<ShoppingcartItem> {

	/**
	 * 更新购物车/收藏夹中商品的数量
	 * @param item
	 * @param quantity
	 * @return
	 */
	public ShoppingcartItem updateQuantity(ShoppingcartItem item, Integer quantity);
	
	/**
	 * 初始化一个item
	 * 该方法不将Item进行持久化操作
	 * @param productsku
	 *        item的商品是产品类型
	 * @return
	 */
	public ShoppingcartItem newShoppingcartItem(ProductSku productsku, Integer quantity, String accessoryIds, short isSaved);
	
	/**
	 * 初始化一个item
	 * 该方法不将Item进行持久化操作
	 * @param productsku
	 *        item的商品是产品类型
	 * @return
	 */
	public ShoppingcartItem newShoppingcartItem(ProductSku productsku, Integer quantity, String accessoryIds,String customMade, short isSaved);
	
	/**
	 * 初始化一个item
	 * 该方法不将Item进行持久化操作
	 * @param gc
	 *        Item的商品是礼券类型
	 * @return
	 */
	public ShoppingcartItem newShoppingcartItem(ShoppingcartItemGc gc);
	
	/**
	 * 通过ShoppingcartItemGc的ID加载item
	 * @param gcId
	 * @return
	 */
	public ShoppingcartItem getShoppingcartItemByGcId(Integer gcId);
	
	/**
	 * 检查cartItem的单价,是否有特价,批发价,和附件价等.
	 * @param productsku
	 * @param item
	 * @param quantity
	 */
	public void updateItemPrice(ProductSku productsku, ShoppingcartItem item, Integer quantity );
	
	public List<ShoppingcartItemStat> searchInShoppingcartStat(SearchCriteria sc);
	
	public List<ShoppingcartItemStat> searchInSavedStat(SearchCriteria sc);
}
