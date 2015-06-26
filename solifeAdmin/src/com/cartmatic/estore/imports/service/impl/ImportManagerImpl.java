
package com.cartmatic.estore.imports.service.impl;

import java.io.File;
import java.io.FileWriter;
import java.util.List;
import org.apache.log4j.Logger;

import au.com.bytecode.opencsv.CSVWriter;

import com.cartmatic.estore.common.helper.ConfigUtil;
import com.cartmatic.estore.common.model.catalog.Catalog;
import com.cartmatic.estore.common.model.system.Store;
import com.cartmatic.estore.core.util.ContextUtil;
import com.cartmatic.estore.imports.engine.ImportEngine;
import com.cartmatic.estore.imports.fromweb.Downloader;
import com.cartmatic.estore.imports.fromweb.Spider;
import com.cartmatic.estore.imports.fromweb.impl.DefaultReportImpl;
import com.cartmatic.estore.imports.fromweb.impl.DownloaderImpl;
import com.cartmatic.estore.imports.fromweb.impl.SpiderImpl;
import com.cartmatic.estore.imports.model.ImportInfo;
import com.cartmatic.estore.imports.service.ImportManager;


public class ImportManagerImpl implements ImportManager {
	private Logger					logger			= Logger.getLogger(ImportManagerImpl.class);
	private ImportInfo				importInfo		= ImportInfo.getInstance();
	
	private static String start_key="start_key"; 
	private static String stop_key="stop_key"; 

	
	public void startImport(String importType,String importMappingConfigName,String filePath,String encoding,Store store,Catalog catalog){
		synchronized (start_key) {
			if(!importInfo.isRunning()){
				ImportEngine importEngine = (ImportEngine) ContextUtil.getSpringBeanById("importEngine");
				importEngine.init(importType, filePath, encoding, importMappingConfigName,store,catalog);
				Thread importEngineRunningThread = new Thread(importEngine);
				importEngineRunningThread.start();
			}
		}
	}

	public void stopImport() {
		synchronized (stop_key) {
			if (!importInfo.isStop()) {
				importInfo.setStop(true);
			}
		}
	}
	
	public List<List<String>> previewCsv(String fileName,String encoding) {
		ImportEngine importEngine = (ImportEngine) ContextUtil.getSpringBeanById("importEngine");
		List<List<String>> result=importEngine.previewFileData(fileName, encoding);
		return result;
	}

	public void setImportInfo(ImportInfo importInfo) {
		this.importInfo = importInfo;
	}

	public Integer checkCsvSize(String fileName, String encoding) {
		ImportEngine importEngine = (ImportEngine) ContextUtil.getSpringBeanById("importEngine");
		Integer rowCount = importEngine.getFileRowSize(fileName, encoding);
		return rowCount;
	}

	/**
	 * 根据url下载产品数据到CSV文件
	 * @param productListUrl 产品目录的url
	 * @param csvPath 下载的csv文件路径
	 */
	public void startImportFromUrl(String productListUrl, String csvPath)
	{
		String[] csvTitle = {"ProductName","productCode","catalog","metaKeywords","productDesc","skuCode","image","price"};
		String baseImgPath = ConfigUtil.getInstance().getMediaStorePath()+"/product/v/";
		DefaultReportImpl report =new DefaultReportImpl(); 
		Downloader dl = new DownloaderImpl();
		dl.setBasePath(baseImgPath);
		Spider spider = new SpiderImpl();
		spider.setSpiderReport(report);
		spider.setDownloader(dl);
		spider.setBaseUrl("http://www.lightinthebox.com");
		try
		{
			//指定页数
			if (productListUrl.endsWith(".html"))
			{
				spider.addURL(productListUrl);
			}
			else  //没有指定页数的，最多只爬4页
			{
				spider.addURL(productListUrl+"/1.html");
				spider.addURL(productListUrl+"/2.html");
				spider.addURL(productListUrl+"/3.html");
				spider.addURL(productListUrl+"/4.html");
			}
			//CSV writer
			File file = new File(csvPath);
			CSVWriter writer = new CSVWriter(new FileWriter(file));
			writer.writeNext(csvTitle);
			dl.setCSVWriter(writer);
			
			spider.begin();
			if (spider != null)
			{
				spider.clear();
				spider = null;
			}
		}
		catch(Exception e)
		{
			//log.error(e);
			e.printStackTrace();
		}
	}
	
	

}
