package com.cartmatic.estore.imports.handler.product;

import org.apache.log4j.Logger;

import com.cartmatic.estore.catalog.CatalogConstants;
import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.imports.handler.ColumnHandler;
import com.cartmatic.estore.imports.handler.basic.ColumnBasicHandler;
import com.cartmatic.estore.imports.model.Column;
import com.cartmatic.estore.imports.model.ImportModel;

public class ProductAvailabilityRuleHandler extends ColumnBasicHandler implements ColumnHandler {
	private Logger logger = Logger.getLogger(ProductAvailabilityRuleHandler.class);

	public void setProperty(ImportModel importModel, Column column) throws Exception {
		Product product = (Product) importModel.getTarget();
		// 当为更新产品时，如果产品原来的销售规则已经是非无限库存（有库存管理）时，不能设置更新为无限库存
		if (product.getId() != null && product.getAvailabilityRule().intValue() != CatalogConstants.PRODUCT_AVAILABILITY_ALWAYS_SELL.intValue() && column.getValue().equals(CatalogConstants.PRODUCT_AVAILABILITY_ALWAYS_SELL.toString())) {
			logger.warn("本产品原来的销售规则是非无限库存，即有库存管理，现在不能更新设置为无限库存（不接受库存管理）。");
			setDefaultResult(importModel, column);
			return;
		}
		// 当为更新产品时，如果产品原来的销售规则已经是无限库存（没有库存管理）时，不能设置更新为非无限库存
		if (product.getId() != null && product.getAvailabilityRule().intValue() == CatalogConstants.PRODUCT_AVAILABILITY_ALWAYS_SELL.intValue() && !column.getValue().equals(product.getAvailabilityRule().toString())) {
			logger.warn("本产品原来的销售规则是无限库存，即没有库存管理，现在不能更新设置为非无限库存。");
			setDefaultResult(importModel, column);
			return;
		}
		// 当为更新产品时，如果产品原来的销售规则已经是非无限库存（有库存管理）时，不能设置更新为无限库存
		if (product.getId() != null && product.getAvailabilityRule().intValue() != CatalogConstants.PRODUCT_AVAILABILITY_NOT_IN_STOCK_SELL.intValue() && column.getValue().equals(CatalogConstants.PRODUCT_AVAILABILITY_ALWAYS_SELL.toString())) {
			logger.warn("本产品原来的销售规则是非无库存销售，即有系统库存管理，现在不能更新设置为无库存销售（不接受库存管理）。");
			setDefaultResult(importModel, column);
			return;
		}
		// 当为更新产品时，如果产品原来的销售规则已经是无限库存（没有库存管理）时，不能设置更新为非无限库存
		if (product.getId() != null && product.getAvailabilityRule().intValue() == CatalogConstants.PRODUCT_AVAILABILITY_NOT_IN_STOCK_SELL.intValue() && !column.getValue().equals(product.getAvailabilityRule().toString())) {
			logger.warn("本产品原来的销售规则是无库存销售，即没有系统库存管理，现在不能更新设置为非无库存销售。");
			setDefaultResult(importModel, column);
			return;
		}
		try {
			product.setAvailabilityRule(new Short(column.getValue()));
		} catch (Exception e) {
			logger.error("错误信息：" + e.getMessage() + ">>>" + e);
			setDefaultResult(importModel, column);
		}

	}

}
