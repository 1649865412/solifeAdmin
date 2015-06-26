package com.cartmatic.estore.imports.handler.product;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.cartmatic.estore.catalog.service.ProductTypeManager;
import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.common.model.catalog.ProductType;
import com.cartmatic.estore.imports.handler.ColumnHandler;
import com.cartmatic.estore.imports.handler.basic.ColumnBasicHandler;
import com.cartmatic.estore.imports.model.Column;
import com.cartmatic.estore.imports.model.ImportModel;

/**
 * 更新产品时不处理产品类型
 * @author Administrator
 *
 */
public class ProductTypeHandler extends ColumnBasicHandler implements ColumnHandler {
	private Logger logger = Logger.getLogger(ProductTypeHandler.class);
	ProductTypeManager productTypeManager=null;
	
	public void setProductTypeManager(ProductTypeManager productTypeManager) {
		this.productTypeManager = productTypeManager;
	}

	public void setProperty(ImportModel importModel,Column column) throws Exception {
		Product product=(Product)importModel.getTarget();
		if(importModel.isUpdate()){
			//更新产品时不处理产品类型
			importModel.setResult("0");
			if(StringUtils.isNotEmpty(column.getValue())&&(!product.getProductType().getProductTypeName().equals(column.getValue()))){
				logger.info("当前处理的更新产品操作，不对产品类型做更新处理！系统产品类型["+product.getProductType().getProductTypeName()+"],导入文件指定的产品类型["+column.getValue()+"]");
			}
			return;
		}
		ProductType productType=null;
		String value=column.getValue();
		if(StringUtils.isNotEmpty(value)){
			productType=productTypeManager.getProductTypeByName(value);
		}
		if(productType==null&&StringUtils.isNotEmpty(column.getDefaultValue())){
			productType=productTypeManager.getProductTypeByName(column.getDefaultValue());
		}
		if(productType!=null){
			product.setProductType(productType);
			importModel.setResult("1");
		}else{
			logger.warn("没有找到指定的产品类型["+column.getValue()+"]！");
			setDefaultResult(importModel, column);
		}
	}

}
