package com.cartmatic.estore.cart.dao;

import java.util.List;

import com.cartmatic.estore.common.model.cart.ShoppingcartItem;
import com.cartmatic.estore.common.model.cart.ShoppingcartItemStat;
import com.cartmatic.estore.core.dao.GenericDao;
import com.cartmatic.estore.core.search.SearchCriteria;
/**
 * Dao interface for ShoppingcartItem.
 */
public interface ShoppingcartItemDao extends GenericDao<ShoppingcartItem> {

	/**
	 * 通过ShoppingcartItemGc的id加载Item
	 * @param gcId
	 * @return
	 */
	public ShoppingcartItem getItemByGcId(Integer gcId);
	
	public List<ShoppingcartItemStat> searchInShoppingcartStat(SearchCriteria sc);
	
	public List<ShoppingcartItemStat> searchInSavedStat(SearchCriteria sc);
	
}