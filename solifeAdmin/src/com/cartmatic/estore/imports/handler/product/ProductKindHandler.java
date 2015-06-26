
package com.cartmatic.estore.imports.handler.product;
import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.imports.handler.ColumnHandler;
import com.cartmatic.estore.imports.handler.basic.ColumnBasicHandler;
import com.cartmatic.estore.imports.model.Column;
import com.cartmatic.estore.imports.model.ImportModel;

public class ProductKindHandler extends ColumnBasicHandler implements
		ColumnHandler {
	private Logger logger = Logger.getLogger(ProductKindHandler.class);
	


	public void setProperty(ImportModel importModel,Column column) throws Exception {
		Product product=(Product)importModel.getTarget();
		Short origProductKind=product.getProductKind();
		//目前只判断是否存在两个价格来决定是否变种
		List<String> values = column.getValues();
		int count=0;
		for (String value : values) {
			if (StringUtils.isNotBlank(value)) {
				try {
					if(new BigDecimal(value).doubleValue()>0)
						count++;
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}
		Short productKind=null;
		if(count>1){
			productKind=new Short("2");
		}else{
			productKind=new Short("1");
		}
		if(product.getId()==null){
			product.setProductKind(productKind);
		}else{
			if(column.isSupportUpdate()){
				product.setProductKind(productKind);
			}else if(productKind.intValue()!=origProductKind){
				logger.warn("本条数据为更新，productKind不支持更新操作。"+column);
			}
		}
	}
}
