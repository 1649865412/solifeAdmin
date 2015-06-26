package com.cartmatic.estore.order.service.impl;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.htmlparser.Parser;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.StringFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.util.NodeList;

import com.cartmatic.estore.common.helper.ConfigUtil;
import com.cartmatic.estore.common.model.order.SalesOrderGeoip;
import com.cartmatic.estore.exception.IPTracerException;

/**
 * @author kedou
 *
 */
/**
 * @author kedou
 *
 */
public class GeoIpCollectorIpAdressImpl extends GoeIpCollectorAbstract{
	private HttpClient httpclient = new HttpClient();
	private int userIndex=0;
	/**
	 * 整批切换用户登录次数
	 */
	private int autoLoginTime=0;

	/**
	 * 每天将（整批切换用户登录次数）清零
	 */
	private int reSetLoginTimeDay=Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_YEAR); 
	public GeoIpCollectorIpAdressImpl(){
		List<Header> headers = new ArrayList<Header>();
        headers.add(new Header("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.0; zh-CN; rv:1.9.1.3) Gecko/20090824 Firefox/3.5.3 (.NET CLR 3.5.30729)"));
        headers.add(new Header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"));
        headers.add(new Header("Accept-Language", "zh-cn,zh;q=0.5"));
        headers.add(new Header("Accept-Encoding", "gzip,deflate"));
        headers.add(new Header("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7"));
        headers.add(new Header("Keep-Alive", "300"));
        headers.add(new Header("Connection", "keep-alive"));
        headers.add(new Header("Content-Type", "application/x-www-form-urlencoded"));
        httpclient.getHostConfiguration().getParams().setParameter("http.default-headers", headers);
	}
	public SalesOrderGeoip getGeoIpByIp(String ip) throws Exception{
		SalesOrderGeoip orderGeoip=new SalesOrderGeoip();
		autoLogin();
		String content=getContent(ip);
		Map<String,String> data=parseHTML(content);
		orderGeoip.setCountry(data.get("country"));
		orderGeoip.setState(data.get("state"));
		orderGeoip.setCity(data.get("city"));
		orderGeoip.setCustomerIp(ip);
		orderGeoip.setLat(data.get("latitude"));
		orderGeoip.setLon(data.get("longitude"));
		return orderGeoip;
	}
	public String getContent(String ip)throws Exception{
		GetMethod getMethod=new GetMethod("http://www.ip-adress.com/ip_tracer/"+ip);
		String content="";
		try {
			httpclient.executeMethod(getMethod);
			content=getMethod.getResponseBodyAsString();
		} catch (Exception e) {
//			throw new Exception("请求查询IP访问页面失败。",e);
			throw new IPTracerException(IPTracerException.TYPE_1,"请求查询IP访问页面失败.",e);
		}finally{
			getMethod.releaseConnection();
		}
		return content;
	}
	
	public static Map<String,String> parseHTML(String content)throws Exception{
		Map<String, String>data=new HashMap<String, String>(); 
		try {
			//检查Ip是否无法解释，
			Parser checkResolved_parser=Parser.createParser(content, "utf-8");
			NodeList notResolvedNodes=checkResolved_parser.extractAllNodesThatMatch(new StringFilter("This IP address could not be resolved to its location"));
			if(notResolvedNodes.size()>0){
//				throw new Exception("This IP address could not be resolved to its location.");
				throw new IPTracerException(IPTracerException.TYPE_3,"This IP address could not be resolved to its location.");
			}
			Parser parser=Parser.createParser(content, "utf-8");
			NodeList nodes = parser.extractAllNodesThatMatch(new HasAttributeFilter("id","ipinfo"));
			//取Country
			NodeList countryNodes = nodes.extractAllNodesThatMatch(new StringFilter("IP address country"),true);
			TextNode countryTextNode=(TextNode)countryNodes.elementAt(0);
			String countryValue=countryTextNode.getParent().getNextSibling().getNextSibling().toPlainTextString().trim();
			data.put("country", countryValue);
			//取state
			NodeList stateNodes = nodes.extractAllNodesThatMatch(new StringFilter("IP address state"),true);
			TextNode stateTextNode=(TextNode)stateNodes.elementAt(0);
			String stateValue=stateTextNode.getParent().getNextSibling().getNextSibling().toPlainTextString().trim();
			data.put("state", stateValue);
			//取city
			NodeList cityNodes = nodes.extractAllNodesThatMatch(new StringFilter("IP address city"),true);
			TextNode cityTextNode=(TextNode)cityNodes.elementAt(0);
			String cityValue=cityTextNode.getParent().getNextSibling().getNextSibling().toPlainTextString().trim();
			data.put("city", cityValue);
			//取纬度
			NodeList latitudeNodes = nodes.extractAllNodesThatMatch(new StringFilter("IP address latitude"),true);
			TextNode latitudeTextNode=(TextNode)latitudeNodes.elementAt(0);
			String latitudeValue=latitudeTextNode.getParent().getNextSibling().getNextSibling().toPlainTextString().trim();
			data.put("latitude", latitudeValue);
			//取经度
			NodeList longitudeNodes = nodes.extractAllNodesThatMatch(new StringFilter("IP address longitude"),true);
			TextNode longitudeTextNode=(TextNode)longitudeNodes.elementAt(0);
			String longitudeValue=longitudeTextNode.getParent().getNextSibling().getNextSibling().toPlainTextString().trim();
			data.put("longitude", longitudeValue);
			return data;
		} catch (Exception e) {
			e.printStackTrace();
			if(e instanceof IPTracerException){
				throw e;
			}
//			throw new Exception("解析页面获取IP数据失败",e);
			throw new IPTracerException(IPTracerException.TYPE_2,"解析页面获取IP数据失败.",e);
		}
	}
	
	
	/**
	 * 切换至下一用户登录
	 */
	private void switchToNextUser(){
		userIndex++;
		if(userIndex>=getUsers().size()){
			userIndex=0;
		}
		autoLoginTime++;
	}
	
	private String []getIpAddressUser()throws Exception{
		List<String[]>users=getUsers();
		if(users.size()==0){
			throw new IPTracerException(IPTracerException.TYPE_4,"系统配置IP-Address.com账号没有配置好");
		}
		return users.get(userIndex);
	}
	
	public boolean isLogin(String contentBoy)throws Exception{
		Parser parser=Parser.createParser(contentBoy, "utf-8");
		NodeList nodes = parser.extractAllNodesThatMatch(new StringFilter("Last login"));
		return nodes.size()>0;
	}
	
	public Integer getRemainingFreeLookupsToday(String contentBoy)throws Exception{
		Parser parser=Parser.createParser(contentBoy, "utf-8");
		NodeList nodes = parser.extractAllNodesThatMatch(new StringFilter("Remaining:"));
		if(nodes.size()>0){
			TextNode textNode=(TextNode)nodes.elementAt(0);
			/*NodeList spanNodes=textNode.getParent().getChildren().extractAllNodesThatMatch(new TagNameFilter("span"));
			spanNodes.elementAt(0).getText();
			String temp=spanNodes.elementAt(0).toPlainTextString().trim();*/
			Pattern pattern= Pattern.compile("\\d+");
			Matcher matcher=pattern.matcher(textNode.getText());
			matcher.find();
			return Integer.parseInt(matcher.group());
		}else{
			//登录失败、或页面调整了,解释页面失败
			throw new IPTracerException(IPTracerException.TYPE_2,"解析页面数据失败");
		}
//		return 0;
	}
	
	public void autoLogin() throws Exception{
		String user[]=getIpAddressUser();
		String mail=user[0];
		String password=user[1];
		//登录
		PostMethod postMethod=new PostMethod("http://www.ip-adress.com/login/");
		postMethod.addParameter("login", mail);
		postMethod.addParameter("password", password);
		postMethod.addParameter("submit", "Login");
		httpclient.executeMethod(postMethod);
		
		GetMethod getMethod=new GetMethod("http://www.ip-adress.com/ip_tracer/");
		httpclient.executeMethod(getMethod);
		//检查是否已经登录
		boolean isLogin=isLogin(getMethod.getResponseBodyAsString());
		int currentDayOfYear=Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
		if(reSetLoginTimeDay<currentDayOfYear){
			reSetLoginTimeDay=currentDayOfYear;
			autoLoginTime=0;
		}
		if(autoLoginTime>10){
			throw new IPTracerException(IPTracerException.TYPE_0,"登录失败");
		}
		if(!isLogin){
			//登录失败,切换用户登录
			switchToNextUser();
			autoLogin();
		}else{
			Integer remainingFree =getRemainingFreeLookupsToday(getMethod.getResponseBodyAsString());
			//检查用户可查询次数，少于0的切换用户登录 
			if(remainingFree.intValue()<=0){
				switchToNextUser();
				autoLogin();
			}
		}
	}
	
	
	
	public static void main(String[] args) throws Exception{
		test();
	}
	public static void test() throws Exception{
		GeoIpCollectorIpAdressImpl geoIpCollectorIpAdressImpl=new GeoIpCollectorIpAdressImpl();
		geoIpCollectorIpAdressImpl.autoLogin();
		String content=geoIpCollectorIpAdressImpl.getContent("202.96.128.86");
		Map<String, String>data=parseHTML(content);
		System.out.println(data.size());
	}
	
	public static void test2(){
		
	}

	public List<String[]> getUsers() {
		/*
		List<String[]>users=new ArrayList<String[]>();
		users.add(new String[]{"kedou20@hotmail.com","111111"});
		users.add(new String[]{"kedou20@gmail.com","111111"});
		return users;*/
		return ConfigUtil.getInstance().getIpAdressUserInfo();
	}
	
	

	
}
