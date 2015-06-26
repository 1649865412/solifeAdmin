package com.cartmatic.estore.catalog.service;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.cartmatic.estore.common.model.catalog.Brand;
import com.cartmatic.estore.framework.test.BaseTransactionalTestCase;

public class BrandManagerTest extends BaseTransactionalTestCase
{
	@Autowired
    private BrandManager brandManager;
    
	@Test
	//@Rollback(false)
    public void testFillBrandData()
    {
        for (int i = 0; i < 20; i++)
        {
            Brand vo = new Brand();
            vo.setBrandCode("code"+i);
            vo.setBrandName("name"+i);
            brandManager.save(vo);
        }
        assertTrue(true);
        //Assert.verify(condition)
        //this.setComplete();
    }
    
    
}
