
package com.cartmatic.estore.imports.engine.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.cartmatic.estore.common.helper.ConfigUtil;
import com.cartmatic.estore.common.model.catalog.Catalog;
import com.cartmatic.estore.common.model.system.Store;
import com.cartmatic.estore.common.util.DateUtil;
import com.cartmatic.estore.core.util.ContextUtil;
import com.cartmatic.estore.core.util.I18nUtil;
import com.cartmatic.estore.imports.engine.ImportEngine;
import com.cartmatic.estore.imports.handler.ColumnHandler;
import com.cartmatic.estore.imports.handler.ImportClause;
import com.cartmatic.estore.imports.handler.PersistenceHandler;
import com.cartmatic.estore.imports.model.Column;
import com.cartmatic.estore.imports.model.ImportInfo;
import com.cartmatic.estore.imports.model.ImportMappingConfig;
import com.cartmatic.estore.imports.model.ImportModel;
import com.cartmatic.estore.imports.service.impl.ImportManagerImpl;

public class ImportEngineImpl implements ImportEngine {
	/**
	 * 导入的数据文件路径
	 */
	private String		filePath					= null;
	/**
	 * 导入的数据文件编码
	 */
	private String		encoding				= null;
	/**
	 * 导入配置模板名
	 */
	private String		importMappingConfigName	= null;
	/**
	 * 导入的数据类型（产品，sku,目录等）
	 */
	private String		importType				= null;
	
	private ImportInfo	importInfo				= ImportInfo.getInstance();
	private Logger		logger					= Logger.getLogger(ImportManagerImpl.class);
	private Store store=null;
	private Catalog catalog=null;

	/**
	 * 初始化导入引擎
	 * @param importType 导入类型，产品、Sku、目录等
	 * @param filePath 导入文件路径
	 * @param encoding 导入文件编辑
	 * @param importMappingConfigName 导入配置名
	 */
	public void init(String importType, String filePath, String encoding,String importMappingConfigName,Store store,Catalog catalog){
		this.importType = importType;
		this.filePath = filePath;
		this.encoding = encoding;
		this.importMappingConfigName = importMappingConfigName;
		this.store = store;
		this.catalog = catalog;
	}

	public void run() {
		try {
			importInfo.init();
			ImportMappingConfig importMappingConfig = (ImportMappingConfig) ContextUtil.getSpringBeanById(importMappingConfigName);
			if (logger.isInfoEnabled()) {
				String msg = I18nUtil.getInstance().getMessage("import.file.data",new Object[] { importType, encoding, filePath });
				logger.info("*************开始导入数据，" + msg);
			}
			startImport(filePath, encoding, importMappingConfig);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (logger.isInfoEnabled()) {
			StringBuffer msg = new StringBuffer("*************导入数据结束!");
			msg.append("开始时间：");
			msg.append(DateUtil.convertDateToString(importInfo.getStartImportTime()));
			msg.append("，结束时间：");
			msg.append(DateUtil.convertDateToString(importInfo.getEndImportTime()));
			msg.append("，添加成功数量：");
			msg.append(importInfo.getSuccessInsertCount());
			msg.append("，更新成功数量：");
			msg.append(importInfo.getSuccessUpdateCount());
			msg.append("，添加失败数量：");
			msg.append(importInfo.getFailInsertCount());
			msg.append("，更新失败数量：");
			msg.append(importInfo.getFailUpdateCount());
			msg.append("，忽略处理数量：");
			msg.append(importInfo.getIgnoreCount());
			msg.append("。：");
			logger.info(msg.toString());
			logger.info("错误记录(行)：" + importInfo.getFailData());
		}
		importInfo.setEndImportTime(new Date());
		importInfo.setRunning(false);
	}
	
	
	/**
	 * @param filePath 
	 * @param encoding
	 * @param importMappingConfig
	 * @return
	 */
	public String startImport(String filePath,String encoding,ImportMappingConfig importMappingConfig) {
		Connection conn = null;
		Statement stm = null;
		ResultSet rs=null;
		try {
			conn = getConnectionFromCSV(filePath,encoding);
			String fileName=getFileName(filePath);
			stm = conn.createStatement();
			rs = stm.executeQuery("SELECT * FROM " + fileName);
			ResultSetMetaData metaData = rs.getMetaData();
			List<String> realHeaders=getAllHeaders(metaData);
			importMappingConfig.setRealHeaders(realHeaders);
			List<Column> columns = importMappingConfig.getAllColumns();
			//导入文件真正的行数（序列）
			Integer rowNum = 1;
			int continuouslyFailCountStopImport=ConfigUtil.getInstance().getContinuouslyFailCountStopImport();
			while (rs.next()) {
				rowNum++;
				//判断是否空白行
				if (isEmptyRow(rs)) {
					continue;
				}
				Map<String,String> rowDataMap=getRowDataMap(metaData, rs);
				doImportTarget(importMappingConfig, columns, rowNum, rowDataMap);
				if (importInfo.isStop() || importInfo.getContinuouslyFailCount() > continuouslyFailCountStopImport) {
					importInfo.setStop(true);
					break;
				}
			}
			logger.info(importInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			closeResource(rs, stm, conn);
		}
		return null;
	}
	
	/**
	 * 导入处理数据文件某一行
	 * @param importMappingConfig 导入配置名
	 * @param columns 所有真正可处理的Column
	 * @param rowNum 当前处理行数（序列）
	 * @param rowDataMap 当前处理的整一行数据集
	 */
	public void doImportTarget(ImportMappingConfig importMappingConfig, List<Column> columns,Integer rowNum,Map<String,String>rowDataMap) {
		try {
			if(logger.isInfoEnabled()){
				logger.info("开始导入第"+rowNum+"条数据:"+rowDataMap);
			}
			ImportClause importClause=importMappingConfig.getImportClause();
			if(importClause!=null&&!importClause.check(rowDataMap)){
				importInfo.addIgnoreCount();
				return;
			}
			//target导入处理的对象
			Object target=Class.forName(importMappingConfig.getTarget()).newInstance();
			ImportModel importModel=new ImportModel();
			importModel.setStore(store);
			importModel.setCatalog(catalog);
			importModel.setUpdate(false);
			importModel.setTarget(target);
			//realHeaders导入的数据文件真实的列头
			List<String> realHeaders=importMappingConfig.getRealHeaders();
			nextRow: for (Column column : columns) {
				column.setRowDataMap(rowDataMap);
				column.setRowNum(rowNum);
				for (int i = 0; i < realHeaders.size(); i++) {
					//当配置的列与导入文件列头一致的就处理该数据
					if (realHeaders.get(i).equals(column.getColumnHeader())|| column.isInitData()|| (StringUtils.isNotEmpty(column.getColumnHeaderSplit())&&ArrayUtils.indexOf(column.getColumnHeader().split(column.getColumnHeaderSplit()), realHeaders.get(i))==0)) {
						ColumnHandler handler = column.getHandler();
						//设置获取当前单元格值
						column.setValue(rowDataMap.get(realHeaders.get(i)));
						if(handler!=null){
							try {
								handler.setProperty(importModel,column);
							} catch (Exception e) {
								e.printStackTrace();
								handler.setProperty(importModel, column);
								logger.warn("错误信息："+e.getMessage()+">>>"+e);
							}
							target=importModel.getTarget();
							if (importModel.getResult().equals("-1")) {
								break nextRow;
							}
							if (column.isInitData()) {
								break;
							}
						}
					}
				}
			}
			if (!importModel.getResult().equals("-1")) {
				try {
					PersistenceHandler persistenceHandler=importMappingConfig.getPersistenceHandler();
					persistenceHandler.validate(importModel);
					if(!importModel.getResult().equals("-1")){
						persistenceHandler.saveOrUpdate(importModel);
					}
					if(!importModel.getResult().equals("-1")){
						importInfo.addSuccessCount(importModel.isUpdate());
						logger.info("导入处理成功，保存更新对象为：" + target);
					}
				} catch (Exception e) {
					logger.warn("错误信息："+e.getMessage()+">>>"+e);
					importModel.setResult("-1");
					e.printStackTrace();
				}
			}
			if(importModel.getResult().equals("-1")){
				logger.warn("导入处理失败，保存更新对象为：" + target);
				importInfo.addFailCount(importModel.isUpdate(),rowNum);
			}
			if(logger.isInfoEnabled()){
				logger.info("导入处理第"+rowNum+"条数据结束。");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("错误信息："+e.getMessage()+">>>"+e);
			logger.error("导入处理失败，强制退出！");
		}
		
	}
	
	
	private Map<String,String> getRowDataMap(ResultSetMetaData metaData,ResultSet rs) throws Exception{
		Map<String,String> rowDataMap=new HashMap<String, String>();
		for(int i=1;i<=metaData.getColumnCount();i++){
			String columnName=metaData.getColumnName(i);
			String value=rs.getString(i);
			rowDataMap.put(columnName, value);
		}
		return rowDataMap;
	}
	
	private boolean isEmptyRow(ResultSet rs) throws Exception{
		boolean isEmptyRow=true;
		for (int i = 1; i < 8; i++) {
			if(StringUtils.isNotBlank(rs.getString(i))){
				isEmptyRow=false;
				break;
			}
		}
		return isEmptyRow;
	}
	
	
	
	private String getFileName(String filePath){
		String fileName=filePath.substring(filePath.lastIndexOf("/")+1,filePath.indexOf("."));
		return fileName;
	}
	
	/**
	 * 获取导入文件真实的列头字段
	 * @param metaData
	 * @return
	 * @throws Exception
	 */
	private List<String> getAllHeaders(ResultSetMetaData metaData) throws Exception{
		List<String> headers=new ArrayList<String>();
		for(int i=1;i<=metaData.getColumnCount();i++){
			String columnName=metaData.getColumnName(i);
			headers.add(columnName);
		}
		return headers;
	}
	
	private Connection getConnectionFromCSV(String filePath,String encoding) throws Exception {
		filePath=filePath.substring(0,filePath.lastIndexOf("/"));
		filePath=ConfigUtil.getInstance().getMediaStorePath()+"/other/"+filePath;
		Class.forName("org.relique.jdbc.csv.CsvDriver");
		Properties props = new java.util.Properties();
		props.put("charset", encoding);
		Connection conn = DriverManager.getConnection("jdbc:relique:csv:" + filePath, props);
		return conn;
	}
	
	private void closeResource(ResultSet rs, Statement stmt, Connection conn) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	
	public Integer getFileRowSize(String fileName,String encoding) {
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		Integer rowCount = 0;
		try {
			conn = getConnectionFromCSV(fileName,encoding);
			fileName=getFileName(fileName);
			stm = conn.createStatement();
			rs = stm.executeQuery("SELECT * FROM " + fileName);
			while (rs.next()) {
				//判断是否空白行
				if (isEmptyRow(rs)) {
					continue;
				}
				rowCount++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeResource(rs, stm, conn);
		}
		return rowCount;
	}
	
	public List<List<String>> previewFileData(String fileName,String encoding) {
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		Integer rowIndex = 0;
		List<List<String>> result=new ArrayList<List<String>>();
		try {
			conn = getConnectionFromCSV(fileName,encoding);
			fileName=getFileName(fileName);
			stm = conn.createStatement();
			rs = stm.executeQuery("SELECT * FROM " + fileName);
			ResultSetMetaData metaData = rs.getMetaData();
			List<String> rowColumnData=new ArrayList<String>();
			Integer rowNum=1;
			rowColumnData.add(rowNum.toString());
			for(int i=1;i<=metaData.getColumnCount();i++){
				String columnName=metaData.getColumnName(i);
				rowColumnData.add(columnName);
			}
			result.add(rowColumnData);
			while (rs.next()) {
				rowNum++;
				//判断是否空白行
				if (isEmptyRow(rs)) {
					continue;
				}
				List<String> rowData=new ArrayList<String>();
				rowData.add(rowNum.toString());
				for(int i=1;i<=metaData.getColumnCount();i++){
					rowData.add(rs.getString(i));
				}
				rowIndex++;
				result.add(rowData);
				if(rowIndex>10){
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeResource(rs, stm, conn);
		}
		return result;
	}
}
