package com.cartmatic.estore.imports;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.util.Date;

import org.jfree.util.Log;

import junit.framework.TestCase;

import au.com.bytecode.opencsv.CSVWriter;

import com.cartmatic.estore.imports.fromweb.Downloader;
import com.cartmatic.estore.imports.fromweb.Spider;
import com.cartmatic.estore.imports.fromweb.impl.DefaultReportImpl;
import com.cartmatic.estore.imports.fromweb.impl.DownloaderImpl;
import com.cartmatic.estore.imports.fromweb.impl.SpiderImpl;

public class ImportFromUrlTest extends TestCase{

	private static final String[] title = {"ProductName","productCode","catalog","metaKeywords","productDesc","skuCode","image","price"};
	 
	//cellPhone目录
	/*private static final String[] downloaadUrls = {
		"http://www.lightinthebox.com/wholesale-HiPhone_c1498",
		"http://www.lightinthebox.com/wholesale-Multi-Function-Cell-Phones_c209",
		"http://www.lightinthebox.com/wholesale-Bar-Phone_c2204",
		"http://www.lightinthebox.com/wholesale-Slide-Phone_c2205",
		"http://www.lightinthebox.com/wholesale-Flip-Phone_c2206",
		"http://www.lightinthebox.com/wholesale-Watch-Style-Cell-Phone_c1298",
		"http://www.lightinthebox.com/wholesale-New-Cell-Phones_c2706",
		"http://www.lightinthebox.com/wholesale-Cheap-Cell-Phones_c2740",
		"http://www.lightinthebox.com/wholesale-Brand-Cell-Phones_c207",
		"http://www.lightinthebox.com/wholesale-Other-Cell-Phone_c2037",
		"http://www.lightinthebox.com/wholesale-Bluetooth_c208",
		"http://www.lightinthebox.com/wholesale-Cell-Phone-Accessories_c742"		
	};*/
	
	//Electronics
	/*private static final String[] downloaadUrls = {
		"http://www.lightinthebox.com/wholesale-Security-Systems_c797",
		"http://www.lightinthebox.com/wholesale-Car-Electronics_c2623",
		"http://www.lightinthebox.com/wholesale-Home-Video-and-Theater_c197",
		"http://www.lightinthebox.com/wholesale-MP3-and-Media-Player_c1746",
		"http://www.lightinthebox.com/wholesale-Headphones_c809",
		"http://www.lightinthebox.com/wholesale-Electronics-Gadgets_c2624",
		"http://www.lightinthebox.com/wholesale-Camera-and-Camcorder_c199",
		"http://www.lightinthebox.com/wholesale-HDMI-and-AV-Cables_c2722",
		"http://www.lightinthebox.com/wholesale-PDAs-and-Handheld-PCs_c2622",
		"http://www.lightinthebox.com/wholesale-Telephones-and-Pagers_c2621"				
	};*/
	//Computers
	private static final String[] downloaadUrls = {
		"http://www.lightinthebox.com/wholesale-Laptops--Notebooks_c1386",
		"http://www.lightinthebox.com/wholesale-Computer-Accessories_c528",
		"http://www.lightinthebox.com/wholesale-Drives-and-Storage_c1988",
		"http://www.lightinthebox.com/wholesale-Input-Devices_c639",
		"http://www.lightinthebox.com/wholesale-Networking_c855",
		"http://www.lightinthebox.com/wholesale-Components_c851",
		"http://www.lightinthebox.com/wholesale-Cartridges_c529",
		"http://www.lightinthebox.com/wholesale-Projector_c2378"				
	};
	// Health and Beauty
	/*private static final String[] downloaadUrls = {
		"http://www.lightinthebox.com/wholesale-Face-Makeup_c2744",
		"http://www.lightinthebox.com/wholesale-Eye-Makeup_c2745",
		"http://www.lightinthebox.com/wholesale-Lip-Makeup_c2746",
		"http://www.lightinthebox.com/wholesale-Cosmetics-Brushes_c1176",
		"http://www.lightinthebox.com/wholesale-Makeup-Accessories_c2747",
		"http://www.lightinthebox.com/wholesale-Short-Hair-Wigs_c2695",
		"http://www.lightinthebox.com/wholesale-Custom-Wigs_c2893",
		"http://www.lightinthebox.com/wholesale-Long-Hair-Wigs_c2694",
		"http://www.lightinthebox.com/wholesale-Mens-Wigs_c2822",
		"http://www.lightinthebox.com/wholesale-Party-Wigs_c2757",
		"http://www.lightinthebox.com/wholesale-Tattoo-Machines-and-Guns_c2196",
		"http://www.lightinthebox.com/wholesale-Tattoo-Ink_c2372",
		"http://www.lightinthebox.com/wholesale-Tattoo-Clothing_c2837",
		"http://www.lightinthebox.com/wholesale-Tattoo-Shoes_c2838",
		"http://www.lightinthebox.com/wholesale-Electronic-Cigarette_c1285",
		"http://www.lightinthebox.com/wholesale-Blood-Pressure-Monitors_c1286",
		"http://www.lightinthebox.com/wholesale-Heart-Rate-Monitors_c1289",
		"http://www.lightinthebox.com/wholesale-Nutrition-and-Fitness_c2835",
		"http://www.lightinthebox.com/wholesale-Scales-and-Body-Fat-Monitors_c1288",
		"http://www.lightinthebox.com/wholesale-Hair-Care_c523",
		"http://www.lightinthebox.com/wholesale-Body-care-Kits-and-Accessories_c520",
		"http://www.lightinthebox.com/wholesale-Nails-Care_c519",
		"http://www.lightinthebox.com/wholesale-Eyes-Care_c518",
		"http://www.lightinthebox.com/wholesale-Oral-Hygiene_c2324",
		"http://www.lightinthebox.com/wholesale-Eyeglasses_c2870",
		"http://www.lightinthebox.com/wholesale-Ionic-Foot-Bath_c2662"
	};*/
	//Home and Garden
	/*private static final String[] downloaadUrls = {
		"http://www.lightinthebox.com/wholesale-Museum-Masters-Paintings_c2027",
		"http://www.lightinthebox.com/wholesale-Abstract-Paintings_c2026",
		"http://www.lightinthebox.com/wholesale-Landscapes-Paintings_c2030",
		"http://www.lightinthebox.com/wholesale-Animals-Paintings_c2028",
		"http://www.lightinthebox.com/wholesale-Floral-Paintings_c2029",
		"http://www.lightinthebox.com/wholesale-People-Paintings_c2031",
		"http://www.lightinthebox.com/wholesale-Still-Life-Paintings_c2032",
		"http://www.lightinthebox.com/wholesale-Chinese-Paintings_c2072",
		"http://www.lightinthebox.com/wholesale-Bedding_c2815",
		"http://www.lightinthebox.com/wholesale-Faucets_c2857",
		"http://www.lightinthebox.com/wholesale-Home-Decor_c240",
		"http://www.lightinthebox.com/wholesale-Pet-Supply_c568",
		"http://www.lightinthebox.com/wholesale-Tools_c241",
		"http://www.lightinthebox.com/wholesale-Gadgets_c847",
		"http://www.lightinthebox.com/wholesale-Kitchen_c238",
		"http://www.lightinthebox.com/wholesale-Home-Appliances_c235"		
	};*/
	//Jewelry
	/*private static final String[] downloaadUrls = {
		"http://www.lightinthebox.com/wholesale-Cubic-Zirconia-Jewelry_c294",
		"http://www.lightinthebox.com/wholesale-Sterling-Silver-Jewelry_c292",
		"http://www.lightinthebox.com/wholesale-Landscapes-Paintings_c2030",
		"http://www.lightinthebox.com/wholesale-Pearl-Jewelry_c1664",
		"http://www.lightinthebox.com/wholesale-Fashion-Jewelry_c2804",
		"http://www.lightinthebox.com/wholesale-Watch_c224",
		"http://www.lightinthebox.com/wholesale-Chains_c2210"				
	};*/
	//Sports and Outdoor
	/*private static final String[] downloaadUrls = {
		"http://www.lightinthebox.com/wholesale-Soccer-Jersey_c957",
		"http://www.lightinthebox.com/wholesale-Sports-Clothing_c2793",
		"http://www.lightinthebox.com/wholesale-Motorcross_c2814",
		"http://www.lightinthebox.com/wholesale-Cycling-Jersey_c2413",
		"http://www.lightinthebox.com/wholesale-Cycling-Shorts_c2543",
		"http://www.lightinthebox.com/wholesale-Bicycle-Parts_c2177",
		"http://www.lightinthebox.com/wholesale-Water-Sports_c2665",
		"http://www.lightinthebox.com/wholesale-Sports-Clothing_c2741",
		"http://www.lightinthebox.com/wholesale-Sneakers_c2475",
		"http://www.lightinthebox.com/wholesale-Motorcross_c2380",
		"http://www.lightinthebox.com/wholesale-Hunting_c1401",
		"http://www.lightinthebox.com/wholesale-Golf_c2441",
		"http://www.lightinthebox.com/wholesale-Camping-Gear-and-Hiking-Gear_c368",
		"http://www.lightinthebox.com/wholesale-Skating_c360",
		"http://www.lightinthebox.com/wholesale-Flashlights_c764",
		"http://www.lightinthebox.com/wholesale-Running_c611"
	};*/
	//Toys and Hobbies 
	/*private static final String[] downloaadUrls = {
		"http://www.lightinthebox.com/wholesale-Radio-Control_c2283",
		"http://www.lightinthebox.com/wholesale-Cosplay-and-Costumes_c2284"
	};*/
	//Video Game 
	/*private static final String[] downloaadUrls = {
		"http://www.lightinthebox.com/wholesale-PS3_c204",
		"http://www.lightinthebox.com/wholesale-Nintendo-Ds_c648",
		"http://www.lightinthebox.com/wholesale-PSP_c203",
		"http://www.lightinthebox.com/wholesale-Xbox-360_c501",
		"http://www.lightinthebox.com/wholesale-PS2_c504",
		"http://www.lightinthebox.com/wholesale-China-Game-Console_c2221"
	};*/
	
	public void testDownload()
	{
		
		try
		{
			DefaultReportImpl report =new DefaultReportImpl(); 
			Downloader dl = new DownloaderImpl();
			dl.setBasePath("D:/Temp/testHtmlParser/");
			File file = new File("D:/Temp/testHtmlParser/Computers.csv");
//			CSVWriter writer = new CSVWriter(new FileWriter(file));
			FileOutputStream fos=new FileOutputStream(file);
			CSVWriter writer = new CSVWriter(new OutputStreamWriter(fos, "UTF-8"));
			
			writer.writeNext(title);
			
			dl.setCSVWriter(writer);
			Spider spider = new SpiderImpl();
			spider.setSpiderReport(report);
			spider.setDownloader(dl);
			spider.setBaseUrl("http://www.lightinthebox.com");
			
			//spider.setSinceTime(new Date());
			for (String url: downloaadUrls)
			{
				spider.addURL(url+"/1.html");
				spider.addURL(url+"/2.html");
				spider.addURL(url+"/3.html");
				//spider.addURL(url+"/4.html");
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
