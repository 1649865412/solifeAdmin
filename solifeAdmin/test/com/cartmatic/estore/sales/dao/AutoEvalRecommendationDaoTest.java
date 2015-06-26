package com.cartmatic.estore.sales.dao;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.framework.test.BaseTransactionalTestCase;

public class AutoEvalRecommendationDaoTest extends BaseTransactionalTestCase {
	@Autowired
    private AutoEvalRecommendationDao autoEvalRecommendationDao = null;



//	public void testGetBuyCountProductsBySourceId(){
//		long startTimeInMilli = System.currentTimeMillis();
//		long startTimeInNano = System.nanoTime();
//		List<Product> list = autoEvalRecommendationDao.getBuyCountProductsBySourceId(6, 0, 3);
//		long endTimeInMilli = System.currentTimeMillis();
//		long endTimeInNano = System.nanoTime();
//
//		logger.info(new StringBuffer().append("ur~~~ it cost time ")
//				.append(endTimeInMilli - startTimeInMilli).append(
//						" milliseconds").toString());
//		logger.info(new StringBuffer().append("ur~~~ it cost time ")
//				.append(endTimeInNano - startTimeInNano).append(
//						" nanoseconds").toString());
//		for(Product product : list){
//			logger.info("productId:productBuyCount===="+product.getProductId()+":"+product.getProductStat().getBuyCount());
//		}
//	}
	
	@Test
	public void testGetNewProductsBySourceId(){
		/*long startTimeInMilli = System.currentTimeMillis();
		long startTimeInNano = System.nanoTime();
		List<Product> list = autoEvalRecommendationDao.getNewProductsBySourceId(6, 0, 7);
		long endTimeInMilli = System.currentTimeMillis();
		long endTimeInNano = System.nanoTime();

		logger.info(new StringBuffer().append("ur~~~ it cost time ")
				.append(endTimeInMilli - startTimeInMilli).append(
						" milliseconds").toString());
		logger.info(new StringBuffer().append("ur~~~ it cost time ")
				.append(endTimeInNano - startTimeInNano).append(
						" nanoseconds").toString());
		logger.info("size~~~~"+list.size());
		for(Product product : list){
			logger.info("productId:publishTime===="+product.getProductId()+":"+product.getPublishTime());
			logger.info("name%%%%%%%%"+product.getProductName());
		}*/
	}
	
//	public void testGetSimilarPriceProductsBySourceId(){
//		List<Product> list = autoEvalRecommendationDao.getSimilarPriceProductsBySourceId(6,0,2);
//		for(Product product : list){
//			logger.info("productId:price===="+product.getProductId()+":"+product.getDefaultProductSku().getPrice());
//		}
//	}
//	
   
   
    
   

}