package com.cartmatic.estore.catalog.util;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.cartmatic.estore.common.helper.ConfigUtil;

public class HtmlConverter {
	public static String converProductDescription(String htmlContent){
		Document document=Jsoup.parse(htmlContent);
		Elements imageElements= document.select("img");
		ConfigUtil config=ConfigUtil.getInstance();
		String mediaUrlPath=config.getStore().getMediaUrlPath();
		String frontSiteMediaUrlPath = config.getStore().getSiteUrl()+config.getMediaPath()+"/";
		for (int i = 0; i < imageElements.size(); i++) {
			Element imageElement=imageElements.get(i);
			String src=imageElement.attr("src");
			if(src.startsWith("/media/")){
				src=mediaUrlPath+src.substring("/media/".length());
			}else if(src.startsWith("/")){
				src=mediaUrlPath+src.substring(1);
			}else if(src.toLowerCase().startsWith("http")){
				src=src.replaceAll(frontSiteMediaUrlPath, mediaUrlPath);
			}
			imageElement.attr("src","/images/img/empty.gif");
			imageElement.attr("original",src);
			imageElement.attr("class","photo-dh");
		}
		Elements scriptElements= document.select("script");
		scriptElements.remove();
		return document.body().html();
	}
	
	public  static void test()throws Exception {
		String htmlContent=FileUtils.readFileToString(new File("D:\\Desktop\\H_Noname4.html"), "UTF-8");
		Document document=Jsoup.parse(htmlContent);
		Elements elements= document.select("img");
		for (int i = 0; i < elements.size(); i++) {
			System.out.println(elements.get(i).attr("src"));;
		}
		System.out.println("================================================");
		elements.get(0).attr("src","http://192.168.1.123/media/other/uploads/201109/heditor/201109011546547212.jpg");
		for (int i = 0; i < elements.size(); i++) {
			System.out.println(elements.get(i).attr("src"));;
		}
		System.out.println("================================================");
		System.out.println(document.html());
	}
	
	public static void main(String[] args) throws Exception {
		String htmlContent=FileUtils.readFileToString(new File("D:\\Desktop\\H_Noname4.html"), "UTF-8");
		String content=converProductDescription(htmlContent);
		System.out.println("-----------------------------------------");
		System.out.println(content);
	}
}
