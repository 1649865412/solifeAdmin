
package com.cartmatic.estore.imports.handler.sku;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.cartmatic.estore.catalog.service.ProductSkuManager;
import com.cartmatic.estore.common.model.catalog.ProductSku;
import com.cartmatic.estore.imports.handler.ColumnHandler;
import com.cartmatic.estore.imports.handler.basic.ColumnBasicHandler;
import com.cartmatic.estore.imports.model.Column;
import com.cartmatic.estore.imports.model.ImportModel;

/**
 * value(productCode)不能为空,默认值无效,根据该productCode查找Product,如果存在该product的就update否则add
 * new product
 * productCode应该放到最先处理，其具体是新增产品还是更新产品
 * @author kedou
 * 
 */
public class SkuProductSkuCodeHandler extends ColumnBasicHandler implements
		ColumnHandler {
	private ProductSkuManager	productSkuManager	= null;
	private Logger logger = Logger.getLogger(SkuProductSkuCodeHandler.class);
	

	public void setProperty(ImportModel importModel,Column column) throws Exception {
		ProductSku productSku=(ProductSku)importModel.getTarget();
		String value = column.getValue();
		if (StringUtils.isNotEmpty(value)) {
			ProductSku productSku2=productSkuManager.getProductSkuByProductSkuCode(value);
			if(productSku2!=null){
				productSku=productSku2;
				productSku.getProduct().getProductCode();
				importModel.setTarget(productSku);
				if(productSku.getProductSkuOptionValues()!=null){
					productSku.getProductSkuOptionValues().size();
				}
				importModel.setUpdate(true);
				if(logger.isInfoEnabled()){
					logger.info("本条数据为更新SKU信息，SKU具体数据："+productSku);
				}
				//提前加载延迟对象
				if(productSku2.getProduct()!=null)
					if(productSku2.getProduct().getDefaultProductSku()!=null)
						productSku2.getProduct().getDefaultProductSku().getStatus();
				
			}else{
				productSku.setProductSkuCode(value);
				if(logger.isInfoEnabled()){
					logger.info("本条数据为新增SKU");
				}
			}
			importModel.setResult("1");
		} else {
			logger.warn("没有指定相应的产品编码");
			importModel.setResult("-1");
		}
	}


	public void setProductSkuManager(ProductSkuManager productSkuManager) {
		this.productSkuManager = productSkuManager;
	}
}
