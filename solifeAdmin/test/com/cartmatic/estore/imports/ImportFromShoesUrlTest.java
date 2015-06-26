package com.cartmatic.estore.imports;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import junit.framework.TestCase;

import org.jfree.util.Log;

import au.com.bytecode.opencsv.CSVWriter;

import com.cartmatic.estore.imports.fromweb.Downloader;
import com.cartmatic.estore.imports.fromweb.Spider;
import com.cartmatic.estore.imports.fromweb.impl.DownloaderShoesImpl;
import com.cartmatic.estore.imports.fromweb.impl.ShoesReportImpl;
import com.cartmatic.estore.imports.fromweb.impl.SpiderImpl;

public class ImportFromShoesUrlTest  extends TestCase{
	private static final String[] title = {"ProductName","productCode","catalog","metaKeywords","productDesc","skuCode","image","price"};
	 
	
	//gps
	private static final String[] downloaadUrls = {
		"http://www.hotshoesus.com/giuseppe-zanotti-shoes-c-53.html",
		"http://www.hotshoesus.com/alexander-wang-c-56.html",
		"http://www.hotshoesus.com/fendi-shoes-c-61.html",
		"http://www.hotshoesus.com/miu-miu-shoes-c-57.html",
		"http://www.hotshoesus.com/roger-vivier-c-63.html"
	};
	
	
	public void testDownload()
	{
		
		
		try
		{
			ShoesReportImpl report =new ShoesReportImpl(); 
			Downloader dl = new DownloaderShoesImpl();
			dl.setBasePath("D:/Temp/testHtmlParser/Shoes/");
			File file = new File("D:/Temp/testHtmlParser/Shoes/Other-Brands.csv");
			FileOutputStream fos=new FileOutputStream(file);
			CSVWriter writer = new CSVWriter(new OutputStreamWriter(fos, "UTF-8"));
			writer.writeNext(title);
			
			dl.setCSVWriter(writer);
			Spider spider = new SpiderImpl();
			spider.setSpiderReport(report);
			spider.setDownloader(dl);
			spider.setBaseUrl("http://www.hotshoesus.com");
			
			//spider.setSinceTime(new Date());
			for (String url: downloaadUrls)
			{
				spider.addURL(url);
				//spider.addURL(url+"?page=2&sort=20a");
				//spider.addURL(url+"?page=3&sort=20a");
				//spider.addURL(url+"?page=4&sort=20a");
				//spider.addURL(url+"?page=5&sort=20a");
				//spider.addURL(url+"?page=6&sort=20a");
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
