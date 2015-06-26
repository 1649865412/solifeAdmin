package com.cartmatic.estore.catalog.dao;

import org.springframework.beans.factory.annotation.Autowired;

import com.cartmatic.estore.framework.test.BaseTransactionalTestCase;

public class CategoryDAOTest extends BaseTransactionalTestCase {
	
	@Autowired
	private CategoryDao categoryDao = null;
    
   
        
    /*
    public void testGetCategoryInvalid() throws Exception {
        try {
            category = dao.getCategory(new Integer(1));
            fail("'badcategoryname' found in database, failing test...");
        } catch (DataAccessException d) {
            assertTrue(d != null);
        }
    }

    
    public void testGetCategory() throws Exception {
        category = dao.getCategory(new Integer(1));

        assertNotNull(category);
        assertEquals("categoryName1", category.getcategoryName());
    }
    
    public void testGetCategoryList() throws Exception {
        List results = dao.getCategorys();

        assertTrue(results.size() > 0);
         
    }

    public void testUpdateCategory() throws Exception {
        category = dao.getCategory(new Integer(1));
        category.setcategoryName("categoryName8");

        dao.saveCategory(category);

        assertEquals("categoryName8", category.getcategoryName());
        
        // verify that violation occurs when adding new category
        // with same categorycategoryId
        
        category.setVersion(null);

        try {
            dao.saveCategory(category);
            fail("saveCategory didn't throw DataIntegrityViolationException");
        } catch (DataIntegrityViolationException e) {
            assertNotNull(e);
            logger.debug("expected exception: " + e.getMessage());
        }
    }
*/
    public void testGetAllCategorys(){
//    	List list=dao.getAllCategorys(true);
//    	logger.debug(list.size());
//    	assertTrue(list.size() > 0);
    }
    

}