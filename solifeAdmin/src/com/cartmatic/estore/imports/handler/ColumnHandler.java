package com.cartmatic.estore.imports.handler;

import com.cartmatic.estore.imports.model.Column;
import com.cartmatic.estore.imports.model.ImportModel;

public interface ColumnHandler {
	/**
	 * 将获取到本单元格的数据，设置在要处理的对象中
	 * @param importModel
	 * @param column
	 * @throws Exception
	 */
	public void setProperty(ImportModel importModel,Column column)throws Exception;
}
