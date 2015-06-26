package com.cartmatic.estore.cart.dao;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;

import com.cartmatic.estore.common.model.cart.ShoppingcartItemStat;
import com.cartmatic.estore.framework.test.BaseTransactionalTestCase;

public class ShoppingcartItemDaoTest extends BaseTransactionalTestCase{
	@Autowired
	private ShoppingcartItemDao dao = null;
	
	@Test
	public void testSearchInShoppingcartStat()
	{
		MockHttpServletRequest request = new MockHttpServletRequest();
		List<ShoppingcartItemStat> rs = dao.searchInShoppingcartStat(dao.getSearchCriteriaBuilder("default").buildSearchCriteria(request, 10));
		for (int i=0; i < rs.size(); i++)
		{
			ShoppingcartItemStat obj = rs.get(i);
			System.out.println(obj.getQuantity()+" "+obj.getProductSku().getProduct().getProductName());
		}
	}
	
	@Test
	public void testSearchInSavedcartStat()
	{
		MockHttpServletRequest request = new MockHttpServletRequest();
		List<ShoppingcartItemStat> rs = dao.searchInSavedStat(dao.getSearchCriteriaBuilder("default").buildSearchCriteria(request, 10));
		for (int i=0; i < rs.size(); i++)
		{
			ShoppingcartItemStat obj = rs.get(i);
			System.out.println(obj.getQuantity()+" "+obj.getProductSku().getProduct().getProductName());
		}
	}
	
	
}
