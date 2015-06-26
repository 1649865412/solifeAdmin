
package com.cartmatic.estore.imports.handler.sku;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.cartmatic.estore.imports.handler.ColumnHandler;
import com.cartmatic.estore.imports.handler.basic.ColumnBasicHandler;
import com.cartmatic.estore.imports.model.Column;
import com.cartmatic.estore.imports.model.ImportModel;


public class SkuWholesalePriceHandler extends ColumnBasicHandler implements
		ColumnHandler {
	/**
	 * 分隔符，如"=",数量=价格;
	 */
	private String delimiter;
	private Logger logger = Logger.getLogger(SkuWholesalePriceHandler.class);
	

	public String getDelimiter() {
		return delimiter;
	}


	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}


	public void setProperty(ImportModel importModel,Column column) throws Exception {
		List<String> wholesalePrices=new ArrayList<String>();
		List<String> tempWholesalePrices=column.getValues();
		for (String wholesalePrice : tempWholesalePrices) {
			wholesalePrice=wholesalePrice.trim();
			if(StringUtils.isEmpty(wholesalePrice))continue;
			String tempWholesalePrice[]=wholesalePrice.split(delimiter);
			try {
				Integer quantity=new Integer(tempWholesalePrice[0]);
				BigDecimal price=new BigDecimal(tempWholesalePrice[1]);
				StringBuffer tempPrice=new StringBuffer();
				tempPrice.append(quantity);
				tempPrice.append("-");
				tempPrice.append(price);
				wholesalePrices.add(tempPrice.toString());
			} catch (Exception e) {
				logger.warn("指定的批发价不符合格式要求！");
				setDefaultResult(importModel, column);
				return;
			}
		}
		importModel.getImportTargetData().put("wholesalePrices", wholesalePrices.toArray(new String[]{}));
		importModel.setResult("1");
	}
}
