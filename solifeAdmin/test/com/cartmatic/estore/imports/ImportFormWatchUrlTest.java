package com.cartmatic.estore.imports;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import junit.framework.TestCase;

import org.jfree.util.Log;

import au.com.bytecode.opencsv.CSVWriter;

import com.cartmatic.estore.imports.fromweb.Downloader;
import com.cartmatic.estore.imports.fromweb.Spider;
import com.cartmatic.estore.imports.fromweb.impl.DownloaderWatchImpl;
import com.cartmatic.estore.imports.fromweb.impl.SpiderImpl;
import com.cartmatic.estore.imports.fromweb.impl.WatchReportImpl;

public class ImportFormWatchUrlTest extends TestCase{
private static final String[] title = {"ProductName","productCode","catalog","productDesc","skuCode","image","price","moreImages"};
	 
	
	//gps
	private static final String[] downloaadUrls = {
		"http://www.watch-replica.net/replica_watches_breitling.html"		
	};
	
	
	public void testDownload()
	{
		
		
		try
		{
			WatchReportImpl report =new WatchReportImpl(); 
			Downloader dl = new DownloaderWatchImpl();
			dl.setBasePath("D:/Temp/testHtmlParser/watch/");
			File file = new File("D:/Temp/testHtmlParser/watch/iwc.csv");
			FileOutputStream fos=new FileOutputStream(file);
			CSVWriter writer = new CSVWriter(new OutputStreamWriter(fos, "UTF-8"));
			writer.writeNext(title);
			
			dl.setCSVWriter(writer);
			Spider spider = new SpiderImpl();
			spider.setSpiderReport(report);
			spider.setDownloader(dl);
			spider.setBaseUrl("http://www.watch-replica.net");
			
			//spider.setSinceTime(new Date());
			for (int i=0; i< 21; i++)
			{
				spider.addURL("http://www.watch-replica.net/replica_watches_iwc/replica_watches_iwc"+i+".html");
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
