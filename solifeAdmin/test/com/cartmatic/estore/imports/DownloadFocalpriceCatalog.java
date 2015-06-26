package com.cartmatic.estore.imports;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
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

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.cartmatic.estore.common.helper.ConfigUtil;
import com.cartmatic.estore.imports.model.Column;

import au.com.bytecode.opencsv.CSVWriter;

public class DownloadFocalpriceCatalog
{
	static String basePath="D:/Documents/focalprice/computers-networking/";
	public static void main(String[] args) throws Exception {
		DownloadFocalpriceCatalog download=new DownloadFocalpriceCatalog();
//		download.getUrl();
//		download.getAllCategoryData();
		download.fixCategoryCode();
		
	}
	
	public void getUrl() throws Exception{
		String title[]={"目录名称","URL","最终目录"};
		File file = new File(basePath+"all_cat_urls.csv");
		file.getParentFile().mkdirs();
		FileOutputStream fos=new FileOutputStream(file);
		CSVWriter writer = new CSVWriter(new OutputStreamWriter(fos, "UTF-8"));
		writer.writeNext(title);
		
		Document doc = getDocument("http://www.focalprice.com/SiteMap");
		Elements all_categories = doc.select("#allcate").select("a");
//		System.out.println(all_categories);
		for (Element category : all_categories)
		{
			System.out.println(category.text()+","+category.attr("href"));
			
			boolean isLast=false;
			if(!category.attr("href").startsWith("sitemap/")){
				isLast=(category.siblingElements()==null||category.siblingElements().size()==0);
			}
			
			String cat_line_data[]={category.text(),category.attr("href"),isLast+""};
			writer.writeNext(cat_line_data);
		}

		writer.close();
		fos.close();
		
	}
	
	public void getAllCategoryData()throws Exception{
		boolean isok=true;
		String title[]={"目录名称","目录编码","父目录","标题","Meta 关键字","页面Meta描述内容","描述","URL","最终目录"};
		File file = new File(basePath+"all_cat_data.csv");
		FileOutputStream fos=new FileOutputStream(file);
		CSVWriter writer = new CSVWriter(new OutputStreamWriter(fos, "UTF-8"));
		writer.writeNext(title);
		
		
		Connection conn = null;
		Statement stm = null;
		ResultSet rs=null;
		try {
			String filePath=basePath;
			Class.forName("org.relique.jdbc.csv.CsvDriver");
			Properties props = new java.util.Properties();
			props.put("charset", "UTF-8");
			conn = DriverManager.getConnection("jdbc:relique:csv:" + filePath, props);
			
			String fileName="all_cat_urls";
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
				
				/*if(value.indexOf("/toothbrush-holders/ca-008013004.html")!=-1){
					isok=true;
				}*/
				if(isok){
					String cat_line_data[]=null;
					if(value.indexOf("/ca-003013001.html")==-1){
						cat_line_data=getCategoryData(value,rs.getString(3));
					}else{
						cat_line_data=new String[]{"Car Audio","car-audio","","","","","","",""};
					}
					writer.writeNext(cat_line_data);
					writer.flush();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			closeResource(rs, stm, conn);
		}
		
		writer.close();
		fos.close();
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
			}
		}
		return doc;
	}
	
	private boolean isEmptyRow(ResultSet rs) throws Exception{
		boolean isEmptyRow=true;
		for (int i = 1; i < 3; i++) {
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

		String title[]={"目录名称","目录编码","父目录","标题","Meta 关键字","页面Meta描述内容","描述","URL","最终目录"};
//		String title[]={"目录名称","目录编码","父目录","标题","Meta 关键字","页面Meta描述内容","描述"};
		File file = new File(basePath+"all_cat_data_fixed.csv");
		FileOutputStream fos=new FileOutputStream(file);
		CSVWriter writer = new CSVWriter(new OutputStreamWriter(fos, "UTF-8"));
		writer.writeNext(title);
		
		
		
		Map<String,Integer> codeACount=new HashMap<String, Integer>();
		Connection conn = null;
		Statement stm = null;
		ResultSet rs=null;
		try {
			String filePath=basePath;
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
				}
				writer.writeNext(new String[]{rs.getString(1),newCode,rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9)});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			closeResource(rs, stm, conn);
		}
		writer.close();
		fos.close();
	}
	
	public String[] getCategoryData(String url,String isLast)throws Exception{
		String[] cat_data=new String[9];
		Document doc = getDocument(url);
		String title=doc.title();
		System.out.println(title);
		String keywords=doc.head().select("meta[name=keywords]").attr("content");
		System.out.println(keywords);
		String description=doc.head().select("meta[name=description]").attr("content");
		System.out.println(description);
		Elements parentCategories=doc.select("#breadcrumbs").select("a[href!=/]");
		String parentCategoryPathName="";
		for (int i = 0; i < parentCategories.size()-1; i++)
		{
			Element parentCategory=parentCategories.get(i); 
			if(StringUtils.isNotBlank(parentCategoryPathName)){
				parentCategoryPathName+=">";
			}
			parentCategoryPathName+=parentCategory.text();
		}
		String categoryName=parentCategories.get(parentCategories.size()-1).text();
		
		System.out.println(parentCategoryPathName);
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
		cat_data[8]=isLast;
		return cat_data;
	}
}
