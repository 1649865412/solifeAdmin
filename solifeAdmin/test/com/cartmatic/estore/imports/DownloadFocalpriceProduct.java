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
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.cartmatic.estore.common.helper.ConfigUtil;
import com.cartmatic.estore.common.util.FileUtil;
import com.cartmatic.estore.imports.model.Column;

import au.com.bytecode.opencsv.CSVWriter;

public class DownloadFocalpriceProduct
{
	

	static String basePath="D:/Documents/focalprice/computers-networking/";
	public static void main(String[] args) throws Exception {
		DownloadFocalpriceProduct download=new DownloadFocalpriceProduct();
		
		download.downloadProduct();
		
//		download.downloadImage();
	}
	
	public void downloadProduct() throws Exception{
		File imageFile = new File(basePath+"product_images.txt");
		List<String> imageList=new ArrayList<String>();
		if(imageFile.exists()){
			imageList=FileUtils.readLines(imageFile);
		}
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
				if(StringUtils.isBlank(productCateogry)){
					if(writer!=null){
						writer.flush();
						writer.close();
					}
					if(fos!=null){
						fos.close();
					}
					
					File file = new File(basePath+cateogryCode+"/product_data.csv");
					System.out.println("获取目录产品:"+cateogryCode);
					System.out.println("产品数据文件:"+file.getAbsolutePath());
					
					if(!file.getParentFile().exists()){
						file.getParentFile().mkdir();
					}

					String title[]={"产品编码","产品名","目录","价格","图片","重量","描述","图片Url"};
					fos=new FileOutputStream(file);
					writer = new CSVWriter(new OutputStreamWriter(fos, "UTF-8"));
					writer.writeNext(title);
				}
				if(categoryUrl.indexOf("/car-alarms-security/ca-003001.html")!=-1){
					isok=true;
				}
				if(isLastCat){
					///car-solutions/ca-001010003.html
					if(isok){
						try
						{

							getCategoryProduct(writer,imageList,categoryUrl,cateogryCode);
						}
						catch (Exception e)
						{
							if(e instanceof HttpStatusException){
								HttpStatusException htppStatus=(HttpStatusException)e;
								if(htppStatus.getStatusCode()==404){
									System.out.println("目录已不存在!(404)");
									continue;
								}
							}
						}
					}
					if(categoryUrl.indexOf("/goggles/ca-003012005.html")!=-1){
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
	
	public void getCategoryProduct(CSVWriter writer,List<String> imageList,String url,String cateogryCode) throws Exception{
		//url="http://www.sw-box.com/Samsung-Galaxy-S3-Hard-Cases.html";
		File imageFile = new File(basePath+"product_images.txt");

 		System.out.println("category:"+url);
		Document doc = getDocument(url);
		
		//判断本目录是否产品目录
		Elements elements_categoryProductList=doc.select("#list_content").select(">div");
		if(elements_categoryProductList.size()==0){
			return;
		}
		
		List<String> categoryUrlList=new ArrayList<String>();
		categoryUrlList.add(url);
		//获取分页
		Elements elements_categoryProductListPaging=doc.select("div.pages").select("a");
		Elements elements_productUrls= doc.select("#list_content").select("li.proImg").select("a");
		if(elements_categoryProductListPaging.size()>=3){
			Element elements_endCategoryPage=elements_categoryProductListPaging.get(elements_categoryProductListPaging.size()-3);
			int pageCount=0;
			if(elements_endCategoryPage!=null){
				pageCount=new Integer(elements_endCategoryPage.text());
			}
			for (int i = 2; i <= pageCount; i++)
			{
				String categoryPageUrl=url.replaceAll(".html", "-"+i+".html");
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
				elements_productUrls= doc.select("#list_content").select("li.proImg").select("a");
			}
			
			for (Element elements_productUrl : elements_productUrls)
			{
				
				int readcount=0;
				while (readcount<100)
				{
					readcount++;
					try
					{
						int productCount=imageList.size();
						String productUrl=elements_productUrl.attr("href");
						System.out.println("product:"+productUrl+"\t"+productCount);
						String line_data[]=null;
						try
						{

							line_data=getProductDetail(cateogryCode,productUrl);
						}
						catch (Exception e)
						{
							if(e instanceof HttpStatusException){
								HttpStatusException htppStatus=(HttpStatusException)e;
								if(htppStatus.getStatusCode()==404){
									System.out.println("缺货产品!(404)");
									break;
								}
							}
						}
						if(line_data==null){
							System.out.println("缺货产品!");
						}else{
							writer.writeNext(line_data);
							
							String imgUrl=line_data[line_data.length-1];
							if(StringUtils.isNotBlank(imgUrl)&&!imageList.contains(imgUrl)){
								imageList.add(imgUrl);
								FileUtils.writeLines(imageFile, imageList);
							}
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
		List<String>urls=FileUtils.readLines(new File("D:/Documents/focalprice/computers-networking/consumer-electronics/product_images.txt")); 
		exec.getCorePoolSize();
		basePath="D:/Documents/focalprice/computers-networking/consumer-electronics/";
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
	
	public void getCategoryProduct(String url,Integer startPage,Integer pageCount) throws Exception{
		/*boolean isOk=false;
		String title[]={"产品编码","产品名","目录","价格","图片","重量","描述","图片Url"};

		File file = new File(basePath+"product_data-"+startPage+"-"+pageCount+".csv");
		File imageFile = new File(basePath+"product_images-"+startPage+"-"+pageCount+".txt");
		if(!file.getParentFile().exists()){
			file.getParentFile().mkdir();
		}
		if(!imageFile.getParentFile().exists()){
			imageFile.getParentFile().mkdir();
		}
		FileOutputStream fos=new FileOutputStream(file);
		CSVWriter writer = new CSVWriter(new OutputStreamWriter(fos, "UTF-8"));
		writer.writeNext(title);
		
		List<String> imageList=new ArrayList<String>();
		
		int count=0;
		for (int i = startPage; i <=pageCount; i++)
		{
			
			String tempUrl=url;
			if(i>1){
				tempUrl+="-"+i;
			}
			tempUrl+=".html";
			System.out.println("category:\t"+url+"\t"+i);
			Document doc = getDocument(tempUrl);
			Elements all_products = doc.select("#list_content").select("li.proImg").select("a");
			for (Element product : all_products)
			{
				count++;
				int readcount=0;
				while (readcount<100)
				{
					readcount++;
					try
					{
						System.out.println("product:\t"+product.attr("href")+"\tc:"+i+"\tp:"+count+"\t\t"+readcount);
						String productUrl=product.attr("href");
						String line_data[]=getProductDetail(product.attr("href"));
						writer.writeNext(line_data);
						imageList.add(line_data[line_data.length-1]);
						FileUtils.writeLines(imageFile, imageList);
						writer.flush();
						break;
					}
					catch (Exception e)
					{
						e.printStackTrace();
						Thread.sleep(2000*readcount);
					}
				}
				
			}
			//break;
		}
		
		writer.close();
		fos.close();
		*/
	}
	
	public String[] getProductDetail(String cateogryCode,String url) throws Exception{
		String productData[]=new String[8];
		Document doc = getDocument(url);
	
	
		productData[2]=cateogryCode;
		System.out.println("cateogryCode:"+cateogryCode);
		
		String productName=doc.select("#productName").text();
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
		
		String fullDescription=getDescription(doc);
		productData[6]=fullDescription;
		System.out.println("fullDescription");
//		System.out.println(fullDescription);

		String productImageURL=doc.select("#spec_n1").select("img").attr("src");
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
		Element elementDescription=doc.select("#Description").select("div.description_m").first();
		
		Element element_ProductOverview=elementDescription.select("img[src=http://img-cloud.com/JPWA/brand/Product%20Overview.jpg]").first();
		if(element_ProductOverview!=null){
			element_ProductOverview.parent().html("<span class=\"f14\">Product Overview</span>");
		}
		Element element_specifications=elementDescription.select("img[src=http://img-cloud.com/JPWA/brand/Specifications.jpg]").first();
		if(element_specifications!=null){
			element_specifications.parent().html("<span class=\"f14\">Specifications</span>");
		}
		//elementDescription.select("img[src=http://img-cloud.com/JPWA/brand/Product%20Pictures.jpg]").first().parent().html("<span class=\"f14\">Product Pictures</span>");
		Element element_Product_Pictures=elementDescription.select("img[src=http://img-cloud.com/JPWA/brand/Product%20Pictures.jpg]").first();
		if(element_Product_Pictures!=null){
			element_Product_Pictures.parent().remove();
		}
		Elements element_desc_images=elementDescription.select("img[src^=http://img.focalprice.com/860x666]");
		for (Element element : element_desc_images)
		{
			element.parent().remove();
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
		if(url.indexOf("http://www.focalprice.com")==-1){
			url="http://www.focalprice.com"+url;
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
		if(url.indexOf("http://www.focalprice.com")==-1){
			url="http://www.focalprice.com"+url;
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
				if(e instanceof HttpStatusException){
					HttpStatusException htppStatus=(HttpStatusException)e;
					if(htppStatus.getStatusCode()==404){
						throw e;
					}
				}
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
