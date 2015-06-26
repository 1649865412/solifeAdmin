package com.cartmatic.estore.imports;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import junit.framework.TestCase;

import org.jfree.util.Log;

import au.com.bytecode.opencsv.CSVWriter;

import com.cartmatic.estore.imports.fromweb.Downloader;
import com.cartmatic.estore.imports.fromweb.Spider;
import com.cartmatic.estore.imports.fromweb.impl.DownloaderDxImpl;
import com.cartmatic.estore.imports.fromweb.impl.DxReportImpl;
import com.cartmatic.estore.imports.fromweb.impl.SpiderImpl;

public class ImportFromDxUrlTest  extends TestCase{
	private static final String[] title = {"ProductName","productCode","catalog","metaKeywords","productDesc","skuCode","costPrice","price","listPrice"};
	 
	
	//gps
	private static final String[] downloaadUrls = {
		"http://www.dealextreme.com/products.dx/category.314"
						
	};
	
	
	public void testDownload()
	{
		
		try
		{
			DxReportImpl report =new DxReportImpl(); 
			Downloader dl = new DownloaderDxImpl();
			dl.setBasePath("D:/Temp/testHtmlParser/DX/");
			File file = new File("D:/Temp/testHtmlParser/DX/Networking.csv");
			FileOutputStream fos=new FileOutputStream(file);
			CSVWriter writer = new CSVWriter(new OutputStreamWriter(fos, "UTF-8"));
			writer.writeNext(title);
			
			dl.setCSVWriter(writer);
			Spider spider = new SpiderImpl();
			spider.setSpiderReport(report);
			spider.setDownloader(dl);
			spider.setBaseUrl("http://www.dealextreme.com");
			
			//spider.setSinceTime(new Date());
			for (String url: downloaadUrls)
			{
				spider.addURL(url);
				spider.addURL(url+"~page.2");
				spider.addURL(url+"~page.3");
				spider.addURL(url+"~page.4");
				//spider.addURL(url+"~page.5");
				//spider.addURL(url+"~page.6");
			}
			Log.debug("Start.....");
			spider.begin();
			if (spider != null)
			{
				spider.clear();
				spider = null;
			}
			
			writer.close();
			fos.close();
		}
		catch(Exception e)
		{
			//log.error(e);
			e.printStackTrace();
		}
	}
}
