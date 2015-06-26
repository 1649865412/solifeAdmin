package com.cartmatic.estore.order.service.impl;



import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang.StringUtils;

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
public class GeoIpCollectorIpAdressAPIImpl extends GoeIpCollectorAbstract{
	private HttpClient httpclient = new HttpClient();

	public GeoIpCollectorIpAdressAPIImpl(){
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
		//http://api.ip-adress.com/?u=f619c94ca8d128746a653ddb18fdd78cc042b&h=<host or ip address>
		String u=getUser();
		GetMethod getMethod=new GetMethod("http://api.ip-adress.com/?u="+u+"&h="+ip);
		String content="";
		try {
			httpclient.executeMethod(getMethod);
//			content=getMethod.getResponseBodyAsString();
			
			boolean isCompressionInput = false;
			if (getMethod.getResponseHeader("Content-Encoding")!=null&&"gzip".equals(getMethod.getResponseHeader("Content-Encoding").getValue())){
				isCompressionInput = true;
			}
			int download_buff_size = 1024;
			//如果是product,则需要分析并下载信息
	        InputStream is = null;
			if (isCompressionInput)
			{
				is = new GZIPInputStream(getMethod.getResponseBodyAsStream(), download_buff_size);
			}
			else
			{
				is  = new BufferedInputStream(getMethod.getResponseBodyAsStream(), download_buff_size);
			}
			
	        byte[] buffer = new byte[download_buff_size];
	        int len;
	        StringBuffer contentBuffer = new StringBuffer();
	        while ((len = is.read(buffer))>0) 
	        {
	        	if (len != download_buff_size)
	        	{
	        		byte[] tmp = new byte[len];
	        		System.arraycopy(buffer, 0, tmp, 0, len);
	        		contentBuffer.append(new String(tmp));
	        	}
	        	else
	        	{
	        		contentBuffer.append(new String(buffer));
	        		buffer = new byte[download_buff_size];
	        	}
	        	content=contentBuffer.toString();
			}
	        
		} catch (Exception e) {
//			throw new Exception("请求查询IP访问页面失败。",e);
			throw new IPTracerException(IPTracerException.TYPE_1,"请求查询IP访问页面失败.",e);
		}finally{
			getMethod.releaseConnection();
		}
		if(StringUtils.isBlank(content)){
			throw new IPTracerException(IPTracerException.TYPE_4,"系统配置IP-Address.com账号没有配置好");
		}
		return content;
	}
	
	public static Map<String,String> parseHTML(String content)throws Exception{
		Map<String, String>data=new HashMap<String, String>(); 
		try {
			content=content.replaceAll("\"","");
			String tempDatas[]=content.split(",");
			String country=tempDatas[3];
			String state=tempDatas[5];
			String city=tempDatas[6];
			String latitude=tempDatas[8];
			String longitude=tempDatas[9];
			data.put("country", country);
			data.put("state", state);
			data.put("city", city);
			data.put("latitude", latitude);
			data.put("longitude", longitude);
			return data;
		} catch (Exception e) {
//			e.printStackTrace();
			if(e instanceof IPTracerException){
				throw e;
			}
//			throw new Exception("解析页面获取IP数据失败",e);
			throw new IPTracerException(IPTracerException.TYPE_2,"解析页面获取IP数据失败.",e);
		}
	}
	
	public static void main(String[] args) throws Exception{
		test();
	}
	public static void test() throws Exception{
		GeoIpCollectorIpAdressAPIImpl geoIpCollectorIpAdressImpl=new GeoIpCollectorIpAdressAPIImpl();
		String content=geoIpCollectorIpAdressImpl.getContent("174.123.41.2");
		Map<String, String>data=parseHTML(content);
		System.out.println(data.size());
		System.out.println(data);
	}
	
	public static void test2(){
		
	}

	public String getUser() {
		String u="";
		List<String[]> users=ConfigUtil.getInstance().getIpAdressUserInfo();
		if(users.size()>0&&users.get(0).length>0){
			u=users.get(0)[0];
		}
		//u="f619c94ca8d128746a653ddb18fdd78cc042b";
		return u;
	}
	
}
