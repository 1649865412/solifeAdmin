
package com.cartmatic.estore.imports.handler.basic;


import org.apache.log4j.Logger;

import com.cartmatic.estore.imports.model.Column;
import com.cartmatic.estore.imports.model.ImportModel;

public class ColumnBasicHandler {
	private Logger	logger	= Logger.getLogger(ColumnBasicHandler.class);

	/**
	 * 设置默认的结果处理
	 * @param importModel
	 * @param column
	 */
	protected void setDefaultResult(ImportModel importModel,Column column){
		if(column.isRequired()){
			logger.warn("处理失败数据："+column);
			importModel.setResult("-1");
		}else{
			importModel.setResult("0");
		}
	}
}