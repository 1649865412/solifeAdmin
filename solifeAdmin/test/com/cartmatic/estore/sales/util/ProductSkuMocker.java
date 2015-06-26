
package com.cartmatic.estore.sales.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.common.model.catalog.ProductSku;

public class ProductSkuMocker {
	private static final Log	logger	= LogFactory
												.getLog(ProductSkuMocker.class);

	public static Collection<ProductSku> getData() {
		Collection<ProductSku> list = new ArrayList<ProductSku>();
		ProductSku sku;
		Product product;
		// 1---------------------------

		sku = new ProductSku();
		sku.setProductSkuId(1);
		sku.setProductSkuCode("nokia-n800");
		product = new Product();
		product.setProductId(1);
		product.setBrandId(2);
		sku.setPrice(new BigDecimal("5000"));
		sku.setProduct(product);
		list.add(sku);
		// 2---------------------------
		sku = new ProductSku();
		sku.setProductSkuId(2);
		sku.setProductSkuCode("anycall900");
		product = new Product();
		product.setProductId(2);
		product.setBrandId(2);
		sku.setPrice(new BigDecimal("6000"));
		sku.setProduct(product);
		list.add(sku);

		// 3---------------------------
		sku = new ProductSku();
		sku.setProductSkuId(3);
		sku.setProductSkuCode("koka_blue");
		product = new Product();
		product.setProductId(3);
		product.setBrandId(1);
		sku.setPrice(new BigDecimal("2500"));
		sku.setProduct(product);
		list.add(sku);

		// 4---------------------------
		sku = new ProductSku();
		sku.setProductSkuId(4);
		sku.setProductSkuCode("koka_red");
		product = new Product();
		product.setProductId(3);
		product.setBrandId(1);
		sku.setPrice(new BigDecimal("3500"));
//		sku.setSalePrice(new BigDecimal("3200"));
		sku.setProduct(product);
		list.add(sku);

		// 5---------------------------
		sku = new ProductSku();
		sku.setProductSkuId(5);
		sku.setProductSkuCode("vedio_large");
		product = new Product();
		product.setProductId(4);
		sku.setPrice(new BigDecimal("2000"));
//		sku.setSalePrice(new BigDecimal("1000"));
		product.setBrandId(2);
		sku.setProduct(product);
		list.add(sku);

		// *********************************
		

		return list;
	}

	public static void print(Collection<ProductSku> list) {

		StringBuffer sb = new StringBuffer(200).append("\n");
		for (ProductSku sku : list) {
			sb.append("[skuId-" + sku.getProductSkuId() + "]");
			sb.append("[skuCode-" + sku.getProductSkuCode() + "]");
			sb.append("[productId-" + sku.getProductId() + "]");
			sb.append("[price-" + sku.getPrice() + "]");
			sb.append("[salePrice-" + sku.getSalePrice() + "]");
			sb.append("[discountPrice-" + sku.getDiscountPrice() + "]");
			sb.append("[discountPriceRAM-" + sku.getDiscountPriceRAM() + "]");
			sb.append("[prule-" + (null != sku.getPrule()?(sku.getPrule().getRuleName()):"NULL") + "]");
			sb.append("\n");
		}
		logger.info(sb.toString());
		return;
	}
}
