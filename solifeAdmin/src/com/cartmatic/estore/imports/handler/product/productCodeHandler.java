
package com.cartmatic.estore.imports.handler.product;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.common.service.ProductService;
import com.cartmatic.estore.imports.handler.ColumnHandler;
import com.cartmatic.estore.imports.handler.basic.ColumnBasicHandler;
import com.cartmatic.estore.imports.model.Column;
import com.cartmatic.estore.imports.model.ImportModel;

public class productCodeHandler extends ColumnBasicHandler implements
		ColumnHandler {
	private ProductService	productService	= null;
	private Logger logger = Logger.getLogger(productCodeHandler.class);
	
	

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	public void setProperty(ImportModel importModel,Column column) throws Exception {
		Product product=(Product)importModel.getTarget();
		String value = column.getValue();
		if (StringUtils.isNotEmpty(value)) {
			Product product2 = productService.getProductByProductCode(value);
			if (product2 != null) {
				product = product2;
				importModel.setTarget(product);
				importModel.setUpdate(true);
				//将相关的延迟加载对象预先读取
				if(product.getProductCategorys()!=null){
					product.getProductCategorys().size();
				}
				if(product.getProductDescription()!=null){
					product.getProductDescription().getFullDescription();
				}
				if(product.getProductMedias()!=null){
					product.getProductMedias().size();
				}
				if(product.getProductType().getProductAttGroups()!=null){
					product.getProductType().getProductAttGroups().size();
				}
				if(logger.isInfoEnabled()){
					logger.info("本条数据为更新产品信息，产品具体数据："+product);
				}
			} else {
				product.setProductCode(value);
				if(logger.isInfoEnabled()){
					logger.info("本条数据为新增产品");
				}
			}
			importModel.setResult("1");
		} else {
			logger.warn("没有指定相应的产品编码");
			importModel.setResult("-1");
		}
	}
}
