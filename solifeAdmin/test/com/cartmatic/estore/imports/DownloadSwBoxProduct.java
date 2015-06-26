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
import java.util.HashMap;
import java.util.Iterator;
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

import au.com.bytecode.opencsv.CSVWriter;

public class DownloadSwBoxProduct
{
	

	private Integer productCount=0;
	static String basePath="D:/Documents/sw-box/";
	public static void main(String[] args) throws Exception {
		DownloadSwBoxProduct download=new DownloadSwBoxProduct();

//		download.downloadProduct();
//		download.getProductDetail("sadsa","http://www.sw-box.com/Candy-Color-Stylish-Hard-Plastic-Case-With-Stand-For-Samsung-Galaxy-S3-i9300-Black.html");
		
		download.downloadImage();
	}
	
	public void downloadProduct() throws Exception{
		basePath="D:/Documents/sw-box/Samsung-Accessories/";
		File file = new File(basePath+"product_data.csv");
		List<String> imageList=new ArrayList<String>();
		
		if(!file.getParentFile().exists()){
			file.getParentFile().mkdir();
		}

		String title[]={"产品编码","产品名","目录","价格","图片","重量","描述","图片Url"};
		FileOutputStream fos=new FileOutputStream(file);
		CSVWriter writer = new CSVWriter(new OutputStreamWriter(fos, "UTF-8"));
		writer.writeNext(title);
		
		
		
		
		
		Connection conn = null;
		Statement stm = null;
		ResultSet rs=null;
		try {
			String filePath="D:/Documents/sw-box";
			Class.forName("org.relique.jdbc.csv.CsvDriver");
			Properties props = new java.util.Properties();
			props.put("charset", "UTF-8");
			conn = DriverManager.getConnection("jdbc:relique:csv:" + filePath, props);
			
			String fileName="all_cat_data_fixed";
			stm = conn.createStatement();
			rs = stm.executeQuery("SELECT * FROM " + fileName);
			
			//导入文件真正的行数（序列）
			Integer rowNum = 0;
			while (rs.next()) {
				rowNum++;
				//判断是否空白行
				if (isEmptyRow(rs)) {
					continue;
				}
				
				String  cateogryCode=rs.getString(2);
				String  categoryUrl=rs.getString(8);
				System.out.println("category count:"+rowNum);
				getCategoryProduct(writer,imageList,categoryUrl,cateogryCode);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			closeResource(rs, stm, conn);
		}
		
		writer.close();
		fos.close();
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
	
	public void downloadImage() throws Exception{
		exec.setContinueExistingPeriodicTasksAfterShutdownPolicy(true);
		exec.setExecuteExistingDelayedTasksAfterShutdownPolicy(true);
		List<String>urls=FileUtils.readLines(new File("D:/Documents/sw-box/Samsung-Accessories/product_images.txt")); 
		exec.getCorePoolSize();
		basePath="D:/Documents/sw-box/Samsung-Accessories/";
		for (String imageUrl : urls)
		{
			URL url = new URL(imageUrl);
			String imgUrl = url.getPath().substring(1);
			imgUrl=imgUrl.substring(imgUrl.indexOf("/")+1);
			File file=new File(basePath+"image/"+imgUrl);
			System.out.println(imageUrl+"\t"+file.exists());
			if((!file.exists())){
				downloadImage(imageUrl, basePath+"image/"+imgUrl, null);
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
	
	public void getCategoryProduct(CSVWriter writer,List<String> imageList,String url,String cateogryCode) throws Exception{

		File imageFile = new File(basePath+"product_images.txt");
		//url="http://www.sw-box.com/Samsung-Galaxy-S3-Hard-Cases.html";

		System.out.println("category:"+url);
		Document doc = getDocument(url);
		
		//判断本目录是否产品目录
		Elements elements_categoryProductList=doc.select("div.category-products");
		if(elements_categoryProductList.size()==0){
			return;
		}
		
		List<String> categoryUrlList=new ArrayList<String>();
		categoryUrlList.add(url);
		//获取分页
		Elements elements_categoryProductListPaging=elements_categoryProductList.select("div.pages");
		if(elements_categoryProductListPaging.size()>0){
			Elements elements_endCategoryPage=elements_categoryProductListPaging.first().select("li.next-icon").first().siblingElements();
			int pageCount=0;
			if(elements_endCategoryPage!=null){
				pageCount=new Integer(elements_endCategoryPage.get(elements_endCategoryPage.size()-1).text());
			}
			for (int i = 2; i <= pageCount; i++)
			{
				String categoryPageUrl=url+"?p="+i;
				if(!categoryUrlList.contains(categoryPageUrl)){
					categoryUrlList.add(categoryPageUrl);
				}
			}
		}
		for (int i = 0; i < categoryUrlList.size(); i++)
		{
			if(i!=0){
				System.out.println("category:"+url);
				doc = getDocument(categoryUrlList.get(i));
				elements_categoryProductList=doc.select("div.category-products");
			}
			Elements elements_productUrls=elements_categoryProductList.select("a.product-image");
			for (Element elements_productUrl : elements_productUrls)
			{
				
				int readcount=0;
				while (readcount<100)
				{
					readcount++;
					try
					{
						productCount++;
						String productUrl=elements_productUrl.attr("href");
						System.out.println("product:"+productUrl+"\t"+productCount);
						
						String line_data[]=getProductDetail(cateogryCode,productUrl);
						if(line_data==null){
							System.out.println("缺货产品!");
						}else{
							writer.writeNext(line_data);
							imageList.add(line_data[line_data.length-1]);
							FileUtils.writeLines(imageFile, imageList);
							writer.flush();
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
	
	public String[] getProductDetail(String cateogryCode,String url) throws Exception{
		String productData[]=new String[8];
		Document doc = getDocument(url);
		
		//检查库存状态
		Elements elements_availability=doc.select("span[itemprop=availability][content=in_stock]");
		if(elements_availability==null||elements_availability.size()==0){
			return null;
		}
		
		productData[2]=cateogryCode;
		System.out.println("categoryCode:"+cateogryCode);
		
		String productName=doc.select("div.product-name").text();
		productData[1]=productName;
		System.out.println("productName:"+productName);
		String productSkuCode=doc.select("div.rss-code-stock").select("span[itemprop=identifier]").text();
		productData[0]=productSkuCode;
		System.out.println("productSkuCode:"+productSkuCode);
		String priceStr=doc.select("#total-qty-price-total").text();
		priceStr=priceStr.replaceAll("\\$", "");
		BigDecimal price=new BigDecimal(priceStr);
		productData[3]=price.toString();
		System.out.println("price:"+price);
		BigDecimal productWeight=null;
		Element element_productWeight=doc.select("#ex_content_shipping_info").select("li:contains(Volume weight)").first();
		if(element_productWeight!=null){
			String productWeightStr=element_productWeight.text().toLowerCase();
			String temp_productWeightStr=productWeightStr.replaceAll("volume weight", "").replaceAll(":", "").replaceAll("kg", "").replaceAll("g", "").trim();
			productWeight=new BigDecimal(temp_productWeightStr);
			if(productWeightStr.indexOf("kg")!=-1){
				productWeight=productWeight.multiply(new BigDecimal("1000"));
			}
			productData[5]=productWeight.toString();
			System.out.println("productWeight:"+productWeight);
		}
		
		String fullDescription=getDescription(doc);
		productData[6]=fullDescription;
		System.out.println("fullDescription");
//		System.out.println(fullDescription);

		String productImageURL=doc.select("div.highslide-gallery").select("a").attr("href");
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
		
		String imagePath=downLoadImage(productImageURL);
		productData[4]=imagePath;
		System.out.println(imagePath);
		productData[7]=productImageURL;
		System.out.println(productImageURL);
		return productData;
	}
	
	private String getDescription(Document doc){
		Element elementDescription=doc.select("div.product-collateral-left").select("div.std").first();
		
		Elements element_img_ul=elementDescription.select("ul:has(img)");
		if(element_img_ul!=null){
			element_img_ul.remove();
		}
		String fullDescription=elementDescription.html();
//		System.out.println(fullDescription);
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
		Document doc = null;
		for (int i = 1; i < 100; i++)
		{
			try
			{
				if(i>1){
					Thread.sleep(1000*i);
				}
				if(url.indexOf("http://www.sw-box.com")==-1){
					url="http://www.sw-box.com"+url;
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
