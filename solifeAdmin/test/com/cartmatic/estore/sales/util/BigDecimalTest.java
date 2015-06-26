
package com.cartmatic.estore.sales.util;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.common.model.catalog.ProductSku;

public class BigDecimalTest {
	private static final Log	logger	= LogFactory
												.getLog(BigDecimalTest.class);

	@Test
	public void testScaleAndRound() {
		BigDecimal val = new BigDecimal("1234.5678");
		BigDecimal valT;
		logger.debug(val);
		valT = val.setScale(2, BigDecimal.ROUND_HALF_UP);
		logger.debug(valT);
		valT = val.round(new MathContext(2, RoundingMode.HALF_UP));
		logger.debug(valT);
		

	}
	@Test
	public void testCloneable(){
		ProductSku sku = new ProductSku();
		Product product = new Product();
		product.setMetaDescription("abc");
		sku.setPrice(new BigDecimal("12"));
		sku.setProduct(product);
		ProductSku sku2 = (ProductSku)sku.clone();
		Product product2 = (Product)product.clone();
		product2.setMetaDescription("cba");
		sku2.setPrice(new BigDecimal("21"));
		sku2.setProduct(product2);
		logger.debug("sku price=="+sku.getPrice());
		logger.debug("sku2 price=="+sku2.getPrice());
		logger.debug("product desc=="+sku.getProduct().getMetaDescription());
		logger.debug("product2 desc=="+sku2.getProduct().getMetaDescription());
	}
}
