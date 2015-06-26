package com.cartmatic.estore.imports;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import junit.framework.TestCase;

import org.jfree.util.Log;

import au.com.bytecode.opencsv.CSVWriter;

import com.cartmatic.estore.imports.fromweb.Downloader;
import com.cartmatic.estore.imports.fromweb.Spider;
import com.cartmatic.estore.imports.fromweb.impl.DownloaderSdImpl;
import com.cartmatic.estore.imports.fromweb.impl.SdReportImpl;
import com.cartmatic.estore.imports.fromweb.impl.SpiderImpl;

public class ImportFromSdUrlTest extends TestCase{
	private static final String[] title = {"ProductName","productCode","catalog","metaKeywords","productDesc","skuCode","image","price","images"};
	 
	//Computers
	private static final String[] downloaadUrls = {
		//"http://www.sammydress.com/Wholesale-Dresses-c-2.html?odr=new"
		"http://www.sammydress.com/Wholesale-Women-b-1.html?odr=new"
		
	};
	/*
	 http://www.sammydress.com/Wholesale-Women-b-1.html  //199  //新增90
	 http://www.sammydress.com/Wholesale-Men-b-89.html  // 99   //新增19
	 http://www.sammydress.com/Wholesale-Bags-Accessories-b-44.html  //110  //新增30
	 http://www.sammydress.com/Wholesale-Watches-b-116.html //64  //新增19
	 http://www.sammydress.com/Wholesale-Sunglasses-b-115.html  //9  //新增7
	 http://www.sammydress.com/Wholesale-Sexy-Lingerie-b-79.html  //53 
	 http://www.sammydress.com/bigclass145.html  //8
	 */
	
	public void testDownload()
	{ 	
		
		try
		{
			SdReportImpl report =new SdReportImpl(); 
			Downloader dl = new DownloaderSdImpl();
			dl.setBasePath("D:/Temp/testHtmlParser/");
			File file = new File("D:/Temp/testHtmlParser/Women.csv");
			
			FileOutputStream fos=new FileOutputStream(file);
			CSVWriter writer = new CSVWriter(new OutputStreamWriter(fos, "UTF-8"));
			
			writer.writeNext(title);
			
			dl.setCSVWriter(writer);
			
			Spider spider = new SpiderImpl();
			spider.setSpiderReport(report);
			spider.setDownloader(dl);
			spider.setBaseUrl("http://www.sammydress.com");
			
			for (int i=1 ; i <= 90 ; i++)
				spider.addURL("http://www.sammydress.com/Wholesale-Women-b-1.html?odr=new&page="+i);
			for (int i=1 ; i <= 19 ; i++)
				spider.addURL("http://www.sammydress.com/Wholesale-Men-b-89.html?odr=new&page="+i);
			for (int i=1 ; i <= 30 ; i++)
				spider.addURL("http://www.sammydress.com/Wholesale-Bags-Accessories-b-44.html?odr=new&page="+i);
			for (int i=1 ; i <= 19 ; i++)
				spider.addURL("http://www.sammydress.com/Wholesale-Watches-b-116.html?odr=new&page="+i);
			for (int i=1 ; i <= 7 ; i++)
				spider.addURL("http://www.sammydress.com/Wholesale-Sunglasses-b-115.html?odr=new&page="+i);
			//	spider.addURL(url);
			
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
