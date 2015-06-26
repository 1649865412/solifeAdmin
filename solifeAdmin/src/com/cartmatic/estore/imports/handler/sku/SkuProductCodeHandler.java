
package com.cartmatic.estore.imports.handler.sku;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.common.model.catalog.ProductSku;
import com.cartmatic.estore.common.service.ProductService;
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
public class SkuProductCodeHandler extends ColumnBasicHandler implements
		ColumnHandler {
	private ProductService	productService	= null;
	private Logger logger = Logger.getLogger(SkuProductCodeHandler.class);
	
	

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	public void setProperty(ImportModel importModel,Column column) throws Exception {
		ProductSku productSku=(ProductSku)importModel.getTarget();
		String value = column.getValue();
		if(StringUtils.isEmpty(value)){
			logger.warn("指定的产品编码为空");
			importModel.setResult("-1");
			return;
		}
		if(productSku.getId()!=null&&StringUtils.isNotEmpty(value)&&value.equals(productSku.getProduct().getProductCode())){
			importModel.setResult("1");
			return;
		}
		if(productSku.getId()==null){
			Product product=productService.getProductByProductCode(value);
			if(product==null){
				logger.warn("指定的产品编码没有找到指定的产品，code:"+value);
				importModel.setResult("-1");
				return;
			}else{
				productSku.setProduct(product);
				try {
					if(product.getDefaultProductSku()!=null)
						product.getDefaultProductSku().getProductSkuCode();
				} catch (Exception e) {
					product.setDefaultProductSku(null);
				}
				importModel.setResult("1");
				return;
			}
		}
		logger.warn("本处理为更新Sku信息，但系统Sku的产品编码与导入文件指定的产品编码不一致，无法处理！导入文件指定产品编码："+value+"；系统产品编码："+productSku.getProduct().getProductCode());
		importModel.setResult("-1");
		return;
	}
}
