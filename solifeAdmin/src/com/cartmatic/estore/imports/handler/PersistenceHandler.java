package com.cartmatic.estore.imports.handler;

import com.cartmatic.estore.imports.model.ImportModel;

public interface PersistenceHandler {
	/**
	 * 保存更新导入实体
	 * @param importModel
	 */
	public void saveOrUpdate(ImportModel importModel);
	
	/**
	 * 检查数据
	 * @param importModel
	 */
	public void validate(ImportModel importModel);
}
