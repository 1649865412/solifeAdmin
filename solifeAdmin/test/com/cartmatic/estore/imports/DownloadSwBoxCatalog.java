package com.cartmatic.estore.imports;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
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

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.cartmatic.estore.common.helper.ConfigUtil;
import com.cartmatic.estore.imports.model.Column;

import au.com.bytecode.opencsv.CSVWriter;

public class DownloadSwBoxCatalog
{
	public static void main(String[] args) throws Exception {
		DownloadSwBoxCatalog download=new DownloadSwBoxCatalog();
//		download.getCategoryData("/apple-accessories/ca-001.html");
//		download.getCategoryData("/Samsung-Accessories.html");
//		download.getUrl();
//		download.getAllCategoryData();
		download.fixCategoryCode();
		
	}
	
	public void getUrl() throws Exception{
		String htmlContent=FileUtils.readFileToString(new File("D:/Documents/sw-box/samsung_catalog.html"), "UTF-8");
		Document doc = Jsoup.parse(htmlContent);
		Elements all_categories = doc.select("a");
		for (Element category : all_categories)
		{
			System.out.println(category.text()+","+category.attr("href"));
		}
	}
	
	public void getAllCategoryData()throws Exception{
		String title[]={"目录名称","目录编码","父目录","标题","Meta 关键字","页面Meta描述内容","描述","原URL"};
		File file = new File("D:/Documents/sw-box/all_cat_data.csv");
		FileOutputStream fos=new FileOutputStream(file);
		CSVWriter writer = new CSVWriter(new OutputStreamWriter(fos, "UTF-8"));
		writer.writeNext(title);
		
		List<String> urls=FileUtils.readLines(new File("D:/Documents/sw-box/all_categories_url.txt"));
		int i=1;
		for (String url : urls)
		{
			System.out.println(i+++"\t"+url);
			String cat_line_data[]=getCategoryData(url.split(",")[1]);
			writer.writeNext(cat_line_data);
		}
		writer.close();
		fos.close();
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
	public void fixCategoryCode() throws Exception{

		String title[]={"目录名称","目录编码","父目录","标题","Meta 关键字","页面Meta描述内容","描述","原URL"};
		File file = new File("D:/Documents/sw-box/all_cat_data_fixed.csv");
		FileOutputStream fos=new FileOutputStream(file);
		CSVWriter writer = new CSVWriter(new OutputStreamWriter(fos, "UTF-8"));
		writer.writeNext(title);
		
		
		
		Map<String,Integer> codeACount=new HashMap<String, Integer>();
		Connection conn = null;
		Statement stm = null;
		ResultSet rs=null;
		try {
			String filePath="D:/Documents/sw-box";
			Class.forName("org.relique.jdbc.csv.CsvDriver");
			Properties props = new java.util.Properties();
			props.put("charset", "UTF-8");
			conn = DriverManager.getConnection("jdbc:relique:csv:" + filePath, props);
			
			String fileName="all_cat_data";
			stm = conn.createStatement();
			rs = stm.executeQuery("SELECT * FROM " + fileName);
			
			//导入文件真正的行数（序列）
			Integer rowNum = 1;
			while (rs.next()) {
				rowNum++;
				//判断是否空白行
				if (isEmptyRow(rs)) {
					continue;
				}
				String value=rs.getString(2);
				Integer count=codeACount.get(value);
				if(count==null){
					count=0;
				}
				count++;
				codeACount.put(value, count);
				String newCode=value;
				if(count>1){
					newCode=value+"-"+count;
					System.out.println(value+"-"+count);
				}else{
					System.out.println(value+"-"+count);
				}
				writer.writeNext(new String[]{rs.getString(1),newCode,rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8)});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			closeResource(rs, stm, conn);
		}
		writer.close();
		fos.close();
	}
	
	public String[] getCategoryData(String url)throws Exception{
		String[] cat_data=new String[8];
		Document doc = getDocument(url);
		String title=doc.title();
		System.out.println(title);
		String keywords=doc.head().select("meta[name=keywords]").attr("content");
		System.out.println(keywords);
		String description=doc.head().select("meta[name=description]").attr("content");
		System.out.println(description);
		Elements parentCategories=doc.select("div.breadcrumbs-b").select("a[href!=http://www.sw-box.com/]");
		String parentCategoryPathName="";
		for (int i = 0; i < parentCategories.size(); i++)
		{
			Element parentCategory=parentCategories.get(i); 
			if(StringUtils.isNotBlank(parentCategoryPathName)){
				parentCategoryPathName+=">";
			}
			parentCategoryPathName+=parentCategory.text();
		}
		System.out.println(parentCategoryPathName);

		String categoryName=doc.select("div.breadcrumbs-b").select("strong").first().text();
		
		System.out.println(categoryName);
		String cate_describe=doc.select("#cate_describe").html();
		System.out.println(cate_describe);
		String categoryCode=categoryName.replaceAll(" ", "-").replaceAll("&", "-").replaceAll("/", "-").replaceAll("'", "-").toLowerCase();
		while (categoryCode.indexOf("--")!=-1)
		{
			categoryCode=categoryCode.replaceAll("--","-");
		}
		System.out.println(categoryCode);
		
		cat_data[0]=categoryName;
		cat_data[1]=categoryCode;
		cat_data[2]=parentCategoryPathName;
		cat_data[3]=title;
		cat_data[4]=keywords;
		cat_data[5]=description;
		cat_data[6]=cate_describe;
		cat_data[7]=url;
		return cat_data;
	}
}
