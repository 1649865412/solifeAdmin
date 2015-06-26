package com.cartmatic.estore.imports.service;

import java.util.List;

import com.cartmatic.estore.common.model.catalog.Catalog;
import com.cartmatic.estore.common.model.system.Store;

public interface ImportManager {
	
	public Integer checkCsvSize(String fileName,String encoding);
	
	/**
	 * 开始导入文件
	 * @param importType 导入数据类型
	 * @param importMappingConfigName 需装载的配置文件
	 * @param fileName 导入数据文件
	 * @param encoding 导入数据文件编码
	 */
	public void startImport(String importType,String importMappingConfigName,String fileName,String encoding,Store store,Catalog catalog);
	
	public void stopImport();
	
	public List<List<String>> previewCsv(String fileName,String encoding);
	
}
 