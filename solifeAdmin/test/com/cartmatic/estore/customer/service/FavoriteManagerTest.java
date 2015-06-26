package com.cartmatic.estore.customer.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.cartmatic.estore.common.model.customer.Favorite;
import com.cartmatic.estore.framework.test.BaseTransactionalTestCase;

public class FavoriteManagerTest extends BaseTransactionalTestCase
{
	@Autowired
	private FavoriteManager favoriteManager;
	
	@Test
	public void testLoad()
	{
		Favorite f = favoriteManager.loadFavorite(1,10, 7);
		System.out.println(f);
	}
	
	@Test
	@Rollback(false)
	public void testAdd()
	{
		Favorite f = new Favorite();
		f.setCustomerId(10);
		f.setProductId(5);
		favoriteManager.save(f);
		System.out.println(f);
	}
}
