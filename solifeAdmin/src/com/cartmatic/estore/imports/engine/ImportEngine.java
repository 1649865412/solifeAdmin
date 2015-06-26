
package com.cartmatic.estore.imports.engine;

import java.util.List;
import java.util.Map;

import com.cartmatic.estore.common.model.catalog.Catalog;
import com.cartmatic.estore.common.model.system.Store;
import com.cartmatic.estore.imports.model.Column;
import com.cartmatic.estore.imports.model.ImportMappingConfig;

public interface ImportEngine extends Runnable {

	/**
	 * 初始化导入引擎
	 * @param importType 导入类型，产品、Sku、目录等
	 * @param filePath 导入文件路径
	 * @param encoding 导入文件编辑
	 * @param importMappingConfigName 导入配置名
	 */
	public void init(String importType, String filePath, String encoding,String importMappingConfigName,Store store,Catalog catalog);

	
	/**
	 * @param filePath 
	 * @param encoding
	 * @param importMappingConfig
	 * @return
	 */
	public String startImport(String filePath,String encoding,ImportMappingConfig importMappingConfig);
	
	/**
	 * 导入处理数据文件某一行
	 * @param importMappingConfig 导入配置名
	 * @param columns 所有真正可处理的Column
	 * @param rowNum 当前处理行数（序列）
	 * @param rowDataMap 当前处理的整一行数据集
	 */
	public void doImportTarget(ImportMappingConfig importMappingConfig, List<Column> columns,Integer rowNum,Map<String,String>rowDataMap) ;
	
	
	
	/**
	 * 获取导入文件行数
	 * @param fileName
	 * @param encoding
	 * @return
	 */
	public Integer getFileRowSize(String fileName,String encoding);
	
	public List<List<String>> previewFileData(String fileName,String encoding);
}
