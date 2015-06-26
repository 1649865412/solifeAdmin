package com.cartmatic.estore.order.service.impl;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.Tag;
import org.htmlparser.util.NodeList;

import com.cartmatic.estore.common.model.order.SalesOrderGeoip;

public class GeoIpCollectorGeobytesImpl extends GoeIpCollectorAbstract{

	public SalesOrderGeoip getGeoIpByIp(String ip) {
		SalesOrderGeoip orderGeoip=new SalesOrderGeoip();
		String content=getContent(ip);
		Map<String,String> data=parseHTML(content);
		orderGeoip.setCountry(data.get("country"));
		orderGeoip.setState(data.get("region"));
		orderGeoip.setCity(data.get("city"));
		orderGeoip.setCustomerIp(ip);
		orderGeoip.setLat(data.get("latitude"));
		orderGeoip.setLon(data.get("longitude"));
		return orderGeoip;
	}
	public static SalesOrderGeoip getGeoIpByIp2(String ip) {
		SalesOrderGeoip orderGeoip=new SalesOrderGeoip();
		String content=getContent(ip);
		Map<String,String> data=parseHTML(content);
		orderGeoip.setCountry(data.get("country"));
		orderGeoip.setState(data.get("region"));
		orderGeoip.setCity(data.get("city"));
		orderGeoip.setCustomerIp(ip);
		orderGeoip.setLat(data.get("latitude"));
		orderGeoip.setLon(data.get("longitude"));
		return orderGeoip;
	}
	public static String getContent(String ip){
		HttpClient httpclient = new HttpClient();
		GetMethod getMethod=new GetMethod("http://www.geobytes.com/IpLocator.htm?GetLocation&template=php3.txt&IpAddress="+ip);
		String content="";
		try {
			httpclient.executeMethod(getMethod);
			getMethod.getResponseBodyAsString();
			content=getMethod.getResponseBodyAsString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		getMethod.releaseConnection();
		return content;
	}
	
	public static Map<String,String> parseHTML(String content){
		Parser parser=Parser.createParser(content, "utf-8");
		try {
			NodeList nodes = parser.extractAllNodesThatMatch(new NodeFilter() {
	            public boolean accept(Node node) {
	            	if(node instanceof Tag){
	            		Tag tag=(Tag)node;
		            	return tag.getTagName().equals("META");
	            	}
	            	return false;
	            }
	        });
			Map<String, String>data=new HashMap<String, String>(); 
			for (int i = 0; i < nodes.size(); i++) {
				Node node = nodes.elementAt(i);
				Tag tag=(Tag)node;
	    		data.put(tag.getAttribute("name"), tag.getAttribute("content"));
			}
			return data;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) throws Exception{
		String content="<html><head><meta name=\"known\" content=\"true\"><meta name=\"locationcode\" content=\"CNBJBEIJ\"><meta name=\"fips104\" content=\"CH\"><meta name=\"iso2\" content=\"CN\"><meta name=\"iso3\" content=\"CHN\"><meta name=\"ison\" content=\"156\"><meta name=\"internet\" content=\"CN\"><meta name=\"countryid\" content=\"49\"><meta name=\"country\" content=\"China\"><meta name=\"regionid\" content=\"1353\"><meta name=\"region\" content=\"Beijing\"><meta name=\"regioncode\" content=\"BJ\"><meta name=\"adm1code\" content=\"CH22\"><meta name=\"cityid\" content=\"3518\"><meta name=\"city\" content=\"Beijing\"><meta name=\"latitude\" content=\"39.9000\"><meta name=\"longitude\" content=\"116.4130\"><meta name=\"timezone\" content=\"+08:00\"><meta name=\"certainty\" content=\"66\"><title>PHP2 Template</title></head><body></body></html>";
		content="<html><head></head><body>The IP Address that you are currently using: 59.42.228.80 has exceeded it's reasonable usage limit, and has been temporarily blocked from accessing this service. Please try back again later.<p>For unlimited access, please purchase some Mapbytes from <a href=\"https://secure.geobytes.com/buy.htm\">https://secure.geobytes.com/buy.htm</a> or send an email to <a href=\"mailto:info@geobytes.com\">info@geobytes.com</a><p>Unlimited Access is charged at a tenth of a cent per lookup - (1 Mapbyte per lookup).</p></body></html>";
//		parseHTML(content);
		getGeoIpByIp2("59.42.228.80");
	}
	public static void test(String content) throws Exception{
		Parser parser=Parser.createParser(content.toString(), "utf-8");
		NodeList nodes = parser.extractAllNodesThatMatch(new NodeFilter() {
            public boolean accept(Node node) {
            	if(node instanceof Tag){
            		Tag tag=(Tag)node;
	            	if(tag.getTagName().equals("META")){
	            		Tag parentTag=(Tag)tag.getParent();
//	            		System.out.println(parentTag.getAttribute("ID"));
	            		System.out.println(tag.toHtml());
	            		return true;
	            	}
	                return false;
            	}
            	return false;
            }
        });
		System.out.println("====================");
		Map<String, String>data=new HashMap<String, String>(); 
		for (int i = 0; i < nodes.size(); i++) {
			Node node = nodes.elementAt(i);
//			System.out.println(i+"-----------"+node.toPlainTextString().trim());
			if(node instanceof Tag){
        		Tag tag=(Tag)node;
        		data.put(tag.getAttribute("name"), tag.getAttribute("content"));
    			System.out.println(i+"-----------"+node.toHtml());
			}
		}
		Set<String> keys=data.keySet();
		for (String key : keys) {
			System.out.println(key+"="+data.get(key));
		}
	}
	
	public static void test2(){
		HttpClient httpclient = new HttpClient();
		GetMethod getMethod=new GetMethod("http://www.geobytes.com/IpLocator.htm?GetLocation&template=php3.txt&IpAddress=202.96.128.86");
		try {
			httpclient.executeMethod(getMethod);
			Header headers[]=getMethod.getRequestHeaders();
			for (Header header : headers) {
				System.out.println(header.getName()+":"+header.getValue());
			}
			System.out.println("====================================");
			Header headers2[]=getMethod.getResponseHeaders();
			for (Header header : headers2) {
				System.out.println(header.getName()+":"+header.getValue());
			}
			System.out.println("====================================");
			System.out.println(getMethod.getResponseBodyAsString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		getMethod.releaseConnection();
	}

	
}
