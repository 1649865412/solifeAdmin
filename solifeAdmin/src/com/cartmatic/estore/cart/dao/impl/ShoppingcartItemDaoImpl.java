package com.cartmatic.estore.cart.dao.impl;

import java.util.List;

import com.cartmatic.estore.cart.dao.ShoppingcartItemDao;
import com.cartmatic.estore.common.model.cart.ShoppingcartItem;
import com.cartmatic.estore.common.model.cart.ShoppingcartItemStat;
import com.cartmatic.estore.core.dao.impl.HibernateGenericDaoImpl;
import com.cartmatic.estore.core.search.SearchCriteria;

/**
 * Dao implementation for ShoppingcartItem.
*/
public class ShoppingcartItemDaoImpl extends HibernateGenericDaoImpl<ShoppingcartItem> implements ShoppingcartItemDao {

	public ShoppingcartItem getItemByGcId(Integer gcId) {
		String hql = "from ShoppingcartItem i where i.shoppingcartItemGc.shoppingcartItemGcId=?";
		ShoppingcartItem item = (ShoppingcartItem)findUnique(hql, gcId);
		return item;
	}

	/**
	 * 查询出所有shoppingCartItem的统计数据,由多到少排序.
	 * @param sc
	 * @return
	 */
	public final List<ShoppingcartItemStat> searchInShoppingcartStat(SearchCriteria sc) 
	{
		String hql = "from ShoppingcartItemStat s where s.itemType=1 and s.isSaved=0 order by s.quantity desc";
		sc.setHql(hql);
		return searchByCriteria(sc);		
	}
	
	/**
	 * 查询出所有收藏的Item的统计数据,由多到少排序.
	 * @param sc
	 * @return
	 */
	public final List<ShoppingcartItemStat> searchInSavedStat(SearchCriteria sc)
	{
		String hql = "from ShoppingcartItemStat s where s.itemType=1 and s.isSaved=1 order by s.quantity desc";
		sc.setHql(hql);
		return searchByCriteria(sc);
	}
}
