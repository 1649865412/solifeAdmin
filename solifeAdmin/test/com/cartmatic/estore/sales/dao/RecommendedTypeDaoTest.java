package com.cartmatic.estore.sales.dao;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import com.cartmatic.estore.common.model.sales.RecommendedType;
import com.cartmatic.estore.framework.test.BaseTransactionalTestCase;

public class RecommendedTypeDaoTest extends BaseTransactionalTestCase{
	@Autowired
    private RecommendedTypeDao dao = null;
	
    private RecommendedType recommendedType = null;
   
    @Test
    public void testGetRecommendedTypeInvalid() throws Exception {
        try {
            recommendedType = dao.getById(new Integer(0));
            Assert.fail("'badrecommendedTypename' found in database, failing test...");
        } catch (DataAccessException d) {
        	Assert.assertTrue(d != null);
        }
    }

    @Test
     public void testAddAndRemoveRecommendedType() throws Exception {
        recommendedType = new RecommendedType();
        recommendedType.setTypeName("typename9");
        recommendedType.setVersion(null);
        recommendedType.setAutoEval((new Short((new Long(9)).shortValue())));
        recommendedType.setRuleCode(new Integer(9));
        recommendedType.setStatus((new Short((new Long(9)).shortValue())));
        recommendedType.setIsSystem((new Short((new Long(9)).shortValue())));
        dao.save(recommendedType);
        Assert.assertEquals("typename9", recommendedType.getTypeName());
		Integer newKeyValue = recommendedType.getRecommendedTypeId();
        dao.delete(recommendedType);

        try {
            recommendedType = dao.getById(newKeyValue);
            Assert.fail("saveRecommendedType didn't throw DataAccessException");
        } catch (DataAccessException d) {
        	Assert.assertNotNull(d);
        }
    }
    
    @Test
    public void testGetRecommendedType() throws Exception {
        recommendedType = dao.getById(new Integer(1));
        Assert.assertNotNull(recommendedType);
    }
    @Test
    public void testGetRecommendedTypeList() throws Exception {
        List results = dao.getAll();

        Assert.assertTrue(results.size() > 0);
         
    }
    @Test
    public void testUpdateRecommendedType() throws Exception {
        recommendedType = dao.getById(new Integer(1));
        recommendedType.setTypeName("typename8");

        dao.save(recommendedType);

        Assert.assertEquals("typename8", recommendedType.getTypeName());
        
    }

}