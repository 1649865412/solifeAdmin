package com.cartmatic.estore.imports;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.cartmatic.estore.common.helper.ConfigUtil;
import com.cartmatic.estore.common.util.FileUtil;
import com.cartmatic.estore.imports.model.Column;
import common.Logger;

import au.com.bytecode.opencsv.CSVWriter;

public class DownloadThankoProduct
{
	Logger log=Logger.getLogger(DownloadThankoProduct.class);
	private List<String> productImageList=new ArrayList<String>();
	private List<String> productDescImageList=new ArrayList<String>();
	private List<String> productMoreImageList=new ArrayList<String>();
	private List<String> productUrlList=new ArrayList<String>();
	
	private File productImageFile = new File(basePath+"product_images.txt");
	private File productMoreImageFile = new File(basePath+"product_more_images.txt");
	private File productDescImageFile = new File(basePath+"product_desc_images.txt");
	private File productUrlFile = new File(basePath+"product_urls.txt");

	static String basePath="D:/Documents/thanko/";
	public static void main(String[] args) throws Exception {
		DownloadThankoProduct download=new DownloadThankoProduct();
		
		download.downloadProduct();
//		download.downloadProductNotLast();
		
//		download.downloadImage();
//		download.downloadMoreImage();
		
		
	}
	
	public DownloadThankoProduct(){
		try
		{
			if(productImageFile.exists()){
				productImageList=FileUtils.readLines(productImageFile);
			}
			if(productDescImageFile.exists()){
				productDescImageList=FileUtils.readLines(productDescImageFile);
			}
			if(productMoreImageFile.exists()){
				productMoreImageList=FileUtils.readLines(productMoreImageFile);
			}
			if(productUrlFile.exists()){
				productUrlList=FileUtils.readLines(productUrlFile);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	public void downloadProduct() throws Exception{
		boolean isok=false;
		CSVWriter writer =null;
		FileOutputStream fos =null;
		Connection conn = null;
		Statement stm = null;
		ResultSet rs=null;
		try {
			String filePath=basePath;
			Class.forName("org.relique.jdbc.csv.CsvDriver");
			Properties props = new java.util.Properties();
			props.put("charset", "UTF-8");
			conn = DriverManager.getConnection("jdbc:relique:csv:" + filePath, props);
			
			String fileName="all_cat_data_fixed";
			stm = conn.createStatement();
			rs = stm.executeQuery("SELECT * FROM " + fileName);
			
				
			File file = new File(basePath+"/product_data.csv");
			
			if(!file.getParentFile().exists()){
				file.getParentFile().mkdir();
			}

			String title[]={"产品编码","产品名","目录","价格","图片","重量","描述","images"};
			fos=new FileOutputStream(file);
			writer = new CSVWriter(new OutputStreamWriter(fos, "UTF-8"));
			writer.writeNext(title);
			
			
			//导入文件真正的行数（序列）
			Integer rowNum = 0;
			while (rs.next()) {
				rowNum++;
				//判断是否空白行
				if (isEmptyRow(rs)) {
					continue;
				}

				//String title[]={"目录名称","目录编码","父目录","标题","Meta 关键字","页面Meta描述内容","描述","URL","最终目录"};
				String  cateogryCode=rs.getString(2);
				String  productCateogry=rs.getString(3);
				String  categoryUrl=rs.getString(8);
				Boolean  isLastCat=new Boolean(rs.getString(9));
				System.out.println("category count:"+rowNum);

				System.out.println("获取目录产品:"+cateogryCode);
				
				
				isok=true;
				if(categoryUrl.indexOf("/android-phones/ca-004001.html")!=-1){
					isok=true;
				}
				if(isLastCat){
					///car-solutions/ca-001010003.html
					if(isok){
						getCategoryProduct(writer,categoryUrl,cateogryCode,isLastCat);
					}
					
					if(categoryUrl.indexOf("/samsung-galaxy-s-cases/ca-004014002.html")!=-1){
						return;
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			closeResource(rs, stm, conn);
		}
	}
	
	public void downloadProductNotLast() throws Exception{
		//basePath="D:/Documents/thankoNotLast/";
		boolean isok=false;
		CSVWriter writer =null;
		FileOutputStream fos =null;
		Connection conn = null;
		Statement stm = null;
		ResultSet rs=null;
		try {
			String filePath=basePath;
			Class.forName("org.relique.jdbc.csv.CsvDriver");
			Properties props = new java.util.Properties();
			props.put("charset", "UTF-8");
			conn = DriverManager.getConnection("jdbc:relique:csv:" + filePath, props);
			
			String fileName="all_cat_data_fixed";
			stm = conn.createStatement();
			rs = stm.executeQuery("SELECT * FROM " + fileName);
			
				
			File file = new File(basePath+"/product_data_not_last.csv");
			
			if(!file.getParentFile().exists()){
				file.getParentFile().mkdir();
			}

			String title[]={"产品编码","产品名","目录","价格","图片","重量","描述","images"};
			fos=new FileOutputStream(file);
			writer = new CSVWriter(new OutputStreamWriter(fos, "UTF-8"));
			writer.writeNext(title);
			
			
			//导入文件真正的行数（序列）
			Integer rowNum = 0;
			while (rs.next()) {
				rowNum++;
				//判断是否空白行
				if (isEmptyRow(rs)) {
					continue;
				}

				//String title[]={"目录名称","目录编码","父目录","标题","Meta 关键字","页面Meta描述内容","描述","URL","最终目录"};
				String  cateogryCode=rs.getString(2);
				String  productCateogry=rs.getString(3);
				String  categoryUrl=rs.getString(8);
				Boolean  isLastCat=new Boolean(rs.getString(9));
				System.out.println("category count:"+rowNum);

				System.out.println("获取目录产品:"+cateogryCode);
				
				
				isok=true;
				if(categoryUrl.indexOf("/android-phones/ca-004001.html")!=-1){
					isok=true;
				}
				if(!isLastCat){
					///car-solutions/ca-001010003.html
					if(isok){
						getCategoryProduct2(writer,categoryUrl,cateogryCode,isLastCat);
					}
					
					if(categoryUrl.indexOf("/samsung-galaxy-s-cases/ca-004014002.html")!=-1){
						return;
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			closeResource(rs, stm, conn);
		}
	}
	
	public void getCategoryProduct(CSVWriter writer,String url,String cateogryCode,Boolean  isLastCat) throws Exception{
		//url="http://www.sw-box.com/Samsung-Galaxy-S3-Hard-Cases.html";
 		System.out.println("category:"+url);
		Document doc = getDocument(url);
		
		//判断本目录是否产品目录
		Elements elements_categoryProductList=doc.select("#item_list");
		if(elements_categoryProductList.size()==0){
			return;
		}
		
		List<String> categoryUrlList=new ArrayList<String>();
		categoryUrlList.add(url);
		//获取分页
		Elements elements_categoryProductListPaging=doc.select("div.pager-category");
		if(elements_categoryProductListPaging.size()>0){
			elements_categoryProductListPaging=doc.select("div.pager-category").first().select("a.link_page");
		}
		
		if(elements_categoryProductListPaging.size()>=1){
			for (Element element : elements_categoryProductListPaging)
			{
				String categoryPageUrl=element.attr("href");
				if(!categoryUrlList.contains(categoryPageUrl)){
					categoryUrlList.add(categoryPageUrl);
				}
			}
		}
		
		Elements elements_productUrls=doc.select("#item_list").select("h3").select("a");
	
		for (int i = 0; i < categoryUrlList.size(); i++)
		{
			if(i!=0){
				System.out.println("category:"+url);
				doc = getDocument(categoryUrlList.get(i));
				elements_productUrls= doc.select("#item_list").select("h3").select("a");
			}
			
			for (Element elements_productUrl : elements_productUrls)
			{
				
				int readcount=0;
				while (readcount<100)
				{
					readcount++;
					try
					{
						int productCount=productImageList.size();
						String productUrl=elements_productUrl.attr("href");
						System.out.println("product:"+productUrl+"\t"+productCount);
						
						List<String[]>productList=getProductDetail(cateogryCode,productUrl);
						for (String[] line_data : productList)
						{
							writer.writeNext(line_data);
							writer.flush();
						}
						if(!productUrlList.contains(productUrl)){
							productUrlList.add(productUrl);
							FileUtils.writeLines(productUrlFile, productUrlList);
						}
						
						break;
					}
					catch (Exception e)
					{
						e.printStackTrace();
						Thread.sleep(2000*readcount);
					}
				}
				
			}
		}
	}
	
	
	public void getCategoryProduct2(CSVWriter writer,String url,String cateogryCode,Boolean  isLastCat) throws Exception{
		//url="http://www.sw-box.com/Samsung-Galaxy-S3-Hard-Cases.html";
 		System.out.println("category:"+url);
		Document doc = getDocument(url);
		
		//判断本目录是否产品目录
		Elements elements_categoryProductList=doc.select("#item_list");
		if(elements_categoryProductList.size()==0){
			return;
		}
		
		List<String> categoryUrlList=new ArrayList<String>();
		categoryUrlList.add(url);
		//获取分页
		Elements elements_categoryProductListPaging=doc.select("div.pager-category");
		if(elements_categoryProductListPaging.size()>0){
			elements_categoryProductListPaging=doc.select("div.pager-category").first().select("a.link_page");
		}
		
		if(elements_categoryProductListPaging.size()>=1){
			for (Element element : elements_categoryProductListPaging)
			{
				String categoryPageUrl=element.attr("href");
				if(!categoryUrlList.contains(categoryPageUrl)){
					categoryUrlList.add(categoryPageUrl);
				}
			}
		}
		
		Elements elements_productUrls=doc.select("#item_list").select("h3").select("a");
	
		for (int i = 0; i < categoryUrlList.size(); i++)
		{
			if(i!=0){
				System.out.println("category:"+url);
				doc = getDocument(categoryUrlList.get(i));
				elements_productUrls= doc.select("#item_list").select("h3").select("a");
			}
			
			for (Element elements_productUrl : elements_productUrls)
			{
				
				int readcount=0;
				while (readcount<100)
				{
					readcount++;
					try
					{
						int productCount=productImageList.size();
						String productUrl=elements_productUrl.attr("href");
						if(!productUrlList.contains(productUrl)){
							System.out.println("product:"+productUrl+"\t"+productCount);
							
							
							List<String[]>productList=getProductDetail(cateogryCode,productUrl);
							for (String[] line_data : productList)
							{
								writer.writeNext(line_data);
								writer.flush();
							}
							if(!productUrlList.contains(productUrl)){
								productUrlList.add(productUrl);
								FileUtils.writeLines(productUrlFile, productUrlList);
							}
							
						}else{
							System.out.println("product:"+productUrl+"\t已存在!");
						}
						break;
					}
					catch (Exception e)
					{
						e.printStackTrace();
						Thread.sleep(2000*readcount);
					}
				}
				
			}
		}
	}

    ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(8);
	public void downloadImage(String imageUrl,String saveFilePath,String fileName) throws Exception{
		exec.submit(new ImgThread(imageUrl, saveFilePath, fileName));
		while (true)
		{
			if (exec.getCorePoolSize() - exec.getActiveCount() == 0)
			{
				Thread.sleep(1000);
			}
			else
			{
				break;
			}
		}
	}
	
	public void downloadProductImage() throws Exception{
		exec.setContinueExistingPeriodicTasksAfterShutdownPolicy(true);
		exec.setExecuteExistingDelayedTasksAfterShutdownPolicy(true);
		List<String>urls=FileUtils.readLines(productImageFile); 
		exec.getCorePoolSize();
		for (String imageUrl : urls)
		{
			if(StringUtils.isNotBlank(imageUrl)){
				URL url = new URL(imageUrl);
				String imgUrl = url.getPath().substring(1);
				imgUrl=imgUrl.substring(imgUrl.indexOf("/")+1);
				File file=new File(basePath+"image/"+imgUrl);
				System.out.println(imageUrl+"\t"+file.exists());
				if((!file.exists())){
					downloadImage(imageUrl, basePath+"image/"+imgUrl, null);
				}
			}
		}
		
		System.out.println("end..............................................................");
		exec.shutdown(); //关闭后不能加入新线程，队列中的线程则依次执行完
		while(exec.getPoolSize()!=0){
			Thread.sleep(500);
		}
	    System.out.println("main thread end!");
		try
		{
			Thread.sleep(30000);
		}
		catch (Exception e1)
		{
			e1.printStackTrace();
		}
		exec.getCorePoolSize();
		System.out.println("ok........................................");
	}
	
	public void downloadMoreImage() throws Exception{
		exec.setContinueExistingPeriodicTasksAfterShutdownPolicy(true);
		exec.setExecuteExistingDelayedTasksAfterShutdownPolicy(true);
		List<String>urls=FileUtils.readLines(productMoreImageFile); 
		exec.getCorePoolSize();
		for (String imageUrl : urls)
		{
			if(StringUtils.isNotBlank(imageUrl)){
				URL url = new URL(imageUrl);
				String imgUrl = url.getPath().substring(1);
				imgUrl=imgUrl.substring(imgUrl.indexOf("/")+1);
				File file=new File(basePath+"moreImage/"+imgUrl);
				System.out.println(imageUrl+"\t"+file.exists());
				if((!file.exists())){
					downloadImage(imageUrl, basePath+"moreImage/"+imgUrl, null);
				}
			}
		}
		
		System.out.println("end..............................................................");
		exec.shutdown(); //关闭后不能加入新线程，队列中的线程则依次执行完
		while(exec.getPoolSize()!=0){
			Thread.sleep(500);
		}
	    System.out.println("main thread end!");
		try
		{
			Thread.sleep(30000);
		}
		catch (Exception e1)
		{
			e1.printStackTrace();
		}
		exec.getCorePoolSize();
		System.out.println("ok........................................");
	}
	
	public void downloadDescImage() throws Exception{
		exec.setContinueExistingPeriodicTasksAfterShutdownPolicy(true);
		exec.setExecuteExistingDelayedTasksAfterShutdownPolicy(true);
		List<String>urls=FileUtils.readLines(productDescImageFile); 
		exec.getCorePoolSize();
		for (String imageUrl : urls)
		{
			if(StringUtils.isNotBlank(imageUrl)){
				URL url = new URL(imageUrl);
				String imgUrl = url.getPath().substring(1);
				imgUrl=imgUrl.substring(imgUrl.indexOf("/")+1);
				File file=new File(basePath+"descImage/"+imgUrl);
				System.out.println(imageUrl+"\t"+file.exists());
				if((!file.exists())){
					downloadImage(imageUrl, basePath+"descImage/"+imgUrl, null);
				}
			}
		}
		
		System.out.println("end..............................................................");
		exec.shutdown(); //关闭后不能加入新线程，队列中的线程则依次执行完
		while(exec.getPoolSize()!=0){
			Thread.sleep(500);
		}
	    System.out.println("main thread end!");
		try
		{
			Thread.sleep(30000);
		}
		catch (Exception e1)
		{
			e1.printStackTrace();
		}
		exec.getCorePoolSize();
		System.out.println("ok........................................");
	}
	
	public void downloadImages(File imageUrlFile) throws Exception{
		exec.setContinueExistingPeriodicTasksAfterShutdownPolicy(true);
		exec.setExecuteExistingDelayedTasksAfterShutdownPolicy(true);
		List<String>urls=FileUtils.readLines(imageUrlFile); 
		exec.getCorePoolSize();
		String path=imageUrlFile.getParentFile().getAbsolutePath();
		for (String imageUrl : urls)
		{
			if(StringUtils.isNotBlank(imageUrl)){
				URL url = new URL(imageUrl);
				String imgUrl = url.getPath().substring(1);
				imgUrl=imgUrl.substring(imgUrl.indexOf("/")+1);
				File file=new File(path+"/images/"+imgUrl);
				log.warn(imageUrl+"\t"+file.exists());
				//System.out.println(imageUrl+"\t"+file.exists());
				if((!file.exists())){
					downloadImage(imageUrl, path+"/images/"+imgUrl, null);
				}
			}
		}

		log.warn("end..............................................................");
//		System.out.println("end..............................................................");
		exec.shutdown(); //关闭后不能加入新线程，队列中的线程则依次执行完
		while(exec.getPoolSize()!=0){
			Thread.sleep(500);
		}
		log.warn("main thread end!");
//	    System.out.println("main thread end!");
		try
		{
			Thread.sleep(30000);
		}
		catch (Exception e1)
		{
			e1.printStackTrace();
		}
		exec.getCorePoolSize();
		System.out.println("ok........................................");
	}
	public List<String[]> getProductDetail(String cateogryCode,String url) throws Exception{
		Document doc = getDocument(url);
		return getProductDetail2(cateogryCode, url,doc);
	}
	
	public List<String[]> getProductDetail2(String cateogryCode,String url,Document doc) throws Exception{
		List<String[]> productList=new ArrayList<String[]>();
		String productData[]=new String[8];
		
		productData[2]=cateogryCode;
		System.out.println("cateogryCode:"+cateogryCode);
		
		Elements elements_imgs=doc.select("#gallery").select("a[rel=photo]");

		List<String> productImageUrlList=new ArrayList<String>();
		if(elements_imgs.size()>0){
			for (Element elements_img : elements_imgs)
			{
				String imageUrl=elements_img.attr("href");
				productImageUrlList.add(imageUrl);
			}
		}
		/*List<String>imageList=new ArrayList<String>();
		List<String>bigImageList=new ArrayList<String>();
		Elements elements_imgs=doc.select("#imgs").select("img[jqimg2]");
		System.out.println("images:");
		for (Element element : elements_imgs)
		{
			String bigImageUrl=element.attr("jqimg2");
			bigImageList.add(bigImageUrl);
			String imageUrl=element.attr("jqimg");
			bigImageList.add(imageUrl);
			System.out.println(imageUrl);
		}*/
		String imagePath="";
		String moreImagePaht="";
		for (int i = 0; i < productImageUrlList.size(); i++)
		{
			String productImageURL=productImageUrlList.get(i);
			if(productImageURL.indexOf("http://www.thanko.jp")==-1){
				productImageURL="http://www.thanko.jp"+productImageURL;
			}
			if(i==0){
				imagePath=downLoadImage(productImageURL);
				productImageList.add(productImageURL);
				FileUtils.writeLines(productImageFile, productImageList);
			}else{
				if(StringUtils.isNotBlank(moreImagePaht)){
					moreImagePaht+=";";
				}
				moreImagePaht+=downLoadImage(productImageURL);
				productMoreImageList.add(productImageURL);
				FileUtils.writeLines(productMoreImageFile, productMoreImageList);
			}
		}
		
		productData[4]=imagePath;
		System.out.println(imagePath);
		productData[7]=moreImagePaht;
		System.out.println(moreImagePaht);
	
		String fullDescription=getDescription(doc);
		productData[6]=fullDescription;
		System.out.println("fullDescription");
//		System.out.println(fullDescription);
		
		
		Elements elements_productItems=doc.select("#cart").select("div.cart-wrapper2");
		
		for (Element elements_productItem : elements_productItems)
		{
			Elements checkIsOtherProduct=elements_productItem.select("p.cart-details").select("a");
			if(checkIsOtherProduct.size()==0){
				String productName=elements_productItem.select("p.cart-name").text();
				productData[1]=productName;
				System.out.println("productName:"+productName);
				
				String productSkuCode=elements_productItem.select("p.cart-number").text();
				productData[0]=productSkuCode;
				System.out.println("productSkuCode:"+productSkuCode);
				
				String priceStr=elements_productItem.select("p.cart-price").html();
				if(priceStr.indexOf(">")!=-1){
					priceStr=priceStr.substring(priceStr.lastIndexOf(">")+1);
				}
				priceStr=priceStr.replaceAll("円\\(税込\\)","").replaceAll("！","").replaceAll(",", "").trim();
				BigDecimal price=new BigDecimal(priceStr);
				productData[3]=price.toString();
				System.out.println("price:"+price);
				
				String productWeight="";
				productData[5]=productWeight;
				System.out.println("productWeight:"+productWeight);
				if(price.compareTo(BigDecimal.ZERO)>0){
					productList.add(Arrays.copyOf(productData, productData.length));
				}else{
					System.out.println("售完:"+productSkuCode);
				}
			}
		}
		
		
		/*
		
		
		String productName=doc.select("p.cart-name").text();
		productData[1]=productName;
		System.out.println("productName:"+productName);
		String productSkuCode=doc.select("#sku").text();
		productData[0]=productSkuCode;
		System.out.println("productSkuCode:"+productSkuCode);
		String priceStr=doc.select("#nowprice").text();
		BigDecimal price=new BigDecimal(priceStr);
		productData[3]=price.toString();
		System.out.println("price:"+price);
		BigDecimal productWeight=null;
		Element element_productWeight=doc.select("#productWeight").first();
		if(element_productWeight!=null){
			String productWeightStr=element_productWeight.select("label").text();
			productWeight=new BigDecimal(productWeightStr);
			if(element_productWeight.text().toLowerCase().indexOf("kg")!=-1){
				productWeight=productWeight.multiply(new BigDecimal("1000"));
			}
			productData[5]=productWeight.toString();
			System.out.println("productWeight:"+productWeight);
		}
		*/

		
		return productList;
	}
	
	private String getDescription(Document doc){
		String fullDescription="";
		Elements elementDescription=doc.select("#useRakutenMain");
		if(elementDescription.size()>0){
			Elements elements_a=elementDescription.select("a").attr("href", "#").removeAttr("target");
			for (Element element_a : elements_a)
			{
				if(StringUtils.isBlank(element_a.html().trim())){
					element_a.remove();
				}
			}
			fullDescription+=elementDescription.outerHtml();

			Elements elements_descImages=elementDescription.select("img");
			for (Element elements_descImage : elements_descImages)
			{
				String imgUrl=elements_descImage.attr("src");
				if(imgUrl.indexOf("http://www.thanko.jp")==-1){
					imgUrl="http://www.thanko.jp"+imgUrl;
				}
				if(StringUtils.isNotBlank(imgUrl)&&!productDescImageList.contains(imgUrl)){
					productDescImageList.add(imgUrl);
				}
			}
		}
		
		
		Elements elementUseRakutenIntroduction=doc.select("#useRakutenIntroduction");
		if(elementUseRakutenIntroduction.size()>0){
			Elements elements_a=elementUseRakutenIntroduction.select("a").attr("href", "#").removeAttr("target");
			for (Element element_a : elements_a)
			{
				if(StringUtils.isBlank(element_a.html().trim())){
					element_a.remove();
				}
			}
			fullDescription+=elementUseRakutenIntroduction.outerHtml();
			
			Elements elements_descImages=elementUseRakutenIntroduction.select("img");
			for (Element elements_descImage : elements_descImages)
			{
				String imgUrl=elements_descImage.attr("src");
				if(imgUrl.indexOf("http://www.thanko.jp")==-1){
					imgUrl="http://www.thanko.jp"+imgUrl;
				}
				if(StringUtils.isNotBlank(imgUrl)&&!productDescImageList.contains(imgUrl)){
					productDescImageList.add(imgUrl);
				}
			}
		}
		
		Elements elementUseRakutenSpec=doc.select("#useRakutenSpec");
		if(elementUseRakutenSpec.size()>0){
			Elements elements_a=elementUseRakutenSpec.select("a").attr("href", "#").removeAttr("target");
			for (Element element_a : elements_a)
			{
				if(StringUtils.isBlank(element_a.html().trim())){
					element_a.remove();
				}
			}
			fullDescription+=elementUseRakutenSpec.outerHtml();
			
			Elements elements_descImages=elementUseRakutenSpec.select("img");
			for (Element elements_descImage : elements_descImages)
			{
				String imgUrl=elements_descImage.attr("src");
				if(imgUrl.indexOf("http://www.thanko.jp")==-1){
					imgUrl="http://www.thanko.jp"+imgUrl;
				}
				if(StringUtils.isNotBlank(imgUrl)&&!productDescImageList.contains(imgUrl)){
					productDescImageList.add(imgUrl);
				}
			}
		}
		try
		{
			FileUtils.writeLines(productDescImageFile, productDescImageList);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		System.out.println(fullDescription);
		return fullDescription;
	}
	
	public String downLoadImage(String url_str) throws Exception{
		URL url = new URL(url_str);
		String imgUrl = url.getPath().substring(1);
		imgUrl=imgUrl.substring(imgUrl.indexOf("/")+1);
		File file=new File(basePath+"image/"+imgUrl);
		if(!file.exists()&&1==2){
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			InputStream is = connection.getInputStream();
			FileUtil.writeInputStreamToFile(file.getAbsolutePath(), is);
			connection.disconnect();
		}
		url = null;
		return imgUrl;
	}
	
	
	public Document getDocument(String url)throws Exception{
		if(url.indexOf("http://www.thanko.jp")==-1){
			url="http://www.thanko.jp"+url;
		}
		URL urlo=new URL(url);
		String ss[]=urlo.getPath().split("/");
		StringBuffer sb=new StringBuffer();
		for (String s : ss)
		{
			if(StringUtils.isNotBlank(s)){
				sb.append("/");
				sb.append(s.replaceAll(" ", "%20").replaceAll("é", "%C3%A9"));
			}
		}
		if(StringUtils.isNotBlank(urlo.getQuery())){
			sb.append("?");
			sb.append(urlo.getQuery());
		}
		url=sb.toString();
		if(url.indexOf("http://www.thanko.jp")==-1){
			url="http://www.thanko.jp"+url;
		}
		
		Document doc = null;
		for (int i = 1; i < 100; i++)
		{
			try
			{
				if(i>1){
					Thread.sleep(1000*i);
				}
				doc = Jsoup.connect(url).timeout(10000).get();
				return doc;
			}
			catch (Exception e)
			{
				System.out.println(url+"\t"+i);
				e.printStackTrace();
			}
		}
		return doc;
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
	
	
}
