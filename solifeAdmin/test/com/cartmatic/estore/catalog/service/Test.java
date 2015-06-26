package com.cartmatic.estore.catalog.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.quartz.simpl.RAMJobStore;

import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.common.model.catalog.ProductSku;
import com.cartmatic.estore.common.model.system.ShippingRate;
import com.cartmatic.estore.common.util.DateUtil;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

public class Test {
	public static void main(String[] args) throws Exception {
		Document document=Jsoup.parse(new URL("http://192.168.1.123"), 2000);
		System.out.println(document.html());
	}
	
	public static String getImageCat(String image){
		String cat="Lg";
		if(StringUtils.isNotBlank(image)&&image.indexOf("/images/")!=-1&&image.indexOf("/skus/")!=-1){
			cat=image.substring(image.indexOf("/images/")+"/images/".length(), image.indexOf("/skus/"));
		}
		System.out.println(cat);
		return cat;
	}
	
	public static String durl(String url){
		if(url.indexOf("?")==-1){
			return url;
		}
		String uri=url.substring(0,url.indexOf("?"));
		String queryString=url.substring(url.indexOf("?")+1,url.length());
		String params[]=queryString.split("&");
		List<String> paramList=new ArrayList<String>();
		for (String param : params) {
			if(!paramList.contains(param)){
				paramList.add(param);
			}
		}
		String newQueryString="";
		for (int i = 0; i < paramList.size(); i++) {
			if(i==0){
				newQueryString+="?";
			}else if(i<paramList.size()){
				newQueryString+="&";
			}
			newQueryString+=paramList.get(i);
		}
		url=uri+newQueryString;
		return url;
	}
	
	public void test8()throws Exception{
		String s1="phone,mp3,led,apple,wholesale,gps,Dresses,Bluetooth,Straight Hair,Notebook,Protective Skin Sticker,Wedding Dresses"
			+","+"Wedding Dresses, Evening Dresses, HiPhone, Cheap Bridesmaid Dress, Versio Aquarius 600, Homecoming Dresses, Cheap Wedding Dresses, Beach Wedding Dresses, Womens Dresses"
			+","+"Dress, Dresses, Earring, Earrings, Shoes, Wedding, Wedding Earrings, Weddings, Shoes Wedding, Wedding Shoes"
			+","+"Phone, Phones, Cell Phone, Cell Phones, Dual Phones, Bar Phone, Bar Phones, Cheap Iphone, Cases, Phone Cases"
			+","+"Camera Camcorder, Camcorder, Batteries, Camera, Battery, Electronic, Batterys, Camera And Camcorder, Electronics, Electronic Gadgets"
			+","+"Rc Control, Rc Toy, Rc Toys, Radio Control, Radio, Toys And Hobbies, Control, Tool, Tools, Rc Wholesale"
			+","+"Car, Cars, Dvd, Dvds, Car Dvd, Car Dvds, Car Accessories, Dvd Cars, Car Accessory, Car Electronics Wholesale";
//			System.out.println(s1);
			Set<String>set=new HashSet<String>();
			
			String array[]=s1.split(",");
			for (String string : array) {
				set.add(string.trim());
			}
			for (String string : set) {
				System.out.println(URLEncoder.encode(string,"UTF-8"));
			}
			System.out.println(Arrays.toString(set.toArray(new String[]{})));
	}
	
	public void test7(){
		NumberFormat nf=new DecimalFormat("00000");
		for (int i = 1; i <= 1000; i++) {
			System.out.println("test_a_"+nf.format(i));
		}
	}
	public void test6(){
		String value="Max Offer China Wholesale Products! Wholesale & Dropship: Elect<ronics, Cell Phones, Mp4, PC Acc>essories, Security Prod'ucts, Gadg\"ets,   Handbags, Wedding Dresses. Buy China Wholesale Now!";
		value=value.replaceAll("<","&lt;");
		value=value.replaceAll(">","&gt;");
		value=value.replaceAll("&","&amp;");
		value=value.replaceAll("\"","&quot;");
		System.out.println(value);
	}
	public void test5() throws Exception{
		System.out.println("http://192.168.1.123:8005/".endsWith("/"));
		String command="D:/ImageMagick-6.6.0-Q16/convert -sharpen 30 -resize 200x200 -extent 200x200 -gravity center F:/image_test/m50209i.jpg F:/image_test/test_3.jpg";;
		Process process=Runtime.getRuntime().exec(command);
		System.out.println("ok");
		BufferedReader in = new BufferedReader(new InputStreamReader(process.getErrorStream()));
		String errorMsg=null;
		while((errorMsg = in.readLine()) != null)
		    System.out.println(errorMsg);
//		System.out.println(process.waitFor());
		System.out.println("ok2");
	}
	
	public static void testThead() throws Exception {
		ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(2);
		 List<Future> list=new ArrayList<Future>();
		    for( int index = 0; index < 50; index++) {
		    	System.out.println(index);
		      Runnable run = new Runnable() {
		        public void run() {
		          long time = (long) (Math.random() * 1000);
		          System.out.println("Sleeping " + time + "ms");
		            try {
		              Thread.sleep(time);
		            } catch (InterruptedException e) {
		            }
		        }
		      };
		     Future f=exec.submit(run);
		     list.add(f);
		    }
		    // must shutdown
		    exec.shutdown();
		    System.out.println("...............");
		    
		    while (true) {
		    	for (Future future : list) {
					System.out.println(future.isDone()+"___"+exec.getActiveCount()+"__"+exec.getCompletedTaskCount()+"__"+exec.getCorePoolSize());
					if(future.isDone()){
						future.cancel(true);
					}
					BlockingQueue<Runnable> rr=exec.getQueue();
					System.out.println(rr.size());
				}
		    	Thread.sleep(1000);
			}

	}
	
	public static void test4(){
		List<Object>list=new ArrayList<Object>();
		list.add("s1");
		list.add("s2");
		list.add("s3");
		list.add("s4");
		list.add(0,list.remove(2));
		System.out.println(list.toString());
		System.out.println("s d#-f d_s,f?sss323a".replaceAll("[^\\w]+", "-"));
		String s1="kedou";
		String s2="kedou";
		Pattern pattern=Pattern.compile("^"+s1+"\\d{5}"+s2+".*");
		System.out.println(pattern.matcher(""+s1+DateUtil.fmtTodayToFive()+s2+"4545212452").matches());
		System.out.println(DateUtil.fmtTodayToFive());
		Integer iii=-5;
		iii=Math.abs(iii);
		System.out.println(iii);
		String ss[]=new String[]{"2","aa"};
		if(ss.length<4){
			for (int i = ss.length; i <4; i++) {
				ss=(String[])ArrayUtils.add(ss, "");
			}
		}
		System.out.println(ss.length);
		System.out.println(Arrays.toString(ss));
	}
	
	public void test3(){
		List<ShippingRate>shippingRateList=new ArrayList<ShippingRate>();
		ShippingRate shippingRate=new ShippingRate();
		shippingRate.setShippingRateId(1);
		shippingRateList.add(shippingRate);
		
		shippingRate=new ShippingRate();
		shippingRate.setShippingRateId(2);
		shippingRateList.add(shippingRate);
		
		shippingRate=new ShippingRate();
		shippingRate.setShippingRateId(3);
		shippingRateList.add(shippingRate);
		
		
		shippingRate=new ShippingRate();
		shippingRate.setShippingRateId(4);
		shippingRateList.add(shippingRate);
		
		shippingRate=new ShippingRate();
		shippingRate.setShippingRateId(5);
		shippingRateList.add(shippingRate);

		System.out.println(shippingRateList.size());
		for (int i = 0; i < shippingRateList.size(); i++) {
			if(shippingRateList.get(i).getShippingRateId().intValue()==2||shippingRateList.get(i).getShippingRateId().intValue()==3){
				shippingRateList.remove(i);
//				System.out.println(i);
			}
		}
		System.out.println(shippingRateList.size());
	}
	
	public void test2()throws Exception{
		testReadRss();
	}
	
	public void testReadRss()throws Exception{
		List<Map<String,String>>wholeSaleNews=new ArrayList<Map<String,String>>();
		try {
			URL feedUrl = new URL("http://www.cnblogs.com/penny/rss"); 
			SyndFeedInput input = new SyndFeedInput(); 
			Reader read=new XmlReader(feedUrl);
			SyndFeed synfeed =input.build(read);
	        List entries = synfeed.getEntries();
	        for (int i = 0; i < entries.size(); i++) {
	            SyndEntry entry = (SyndEntry) entries.get(i);   
	        	Map<String,String> wholeSaleNew=new HashMap<String, String>();
	        	wholeSaleNew.put("link",entry.getLink().trim());
	        	wholeSaleNew.put("title",entry.getTitle().trim());
	        	wholeSaleNews.add(wholeSaleNew);
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (Map<String, String> map : wholeSaleNews) {
			System.out.println(map);
		}
		
	}
	
	public void test1()throws Exception{
		Product p1=new Product();
		p1.setProductName("1234");
		JSONObject jo=JSONObject.fromObject(p1);
		
		String orderPrefix="RE";
		Pattern pattern = Pattern.compile(".*"+orderPrefix+"[0-9]{9}.*");
		  Matcher matcher = pattern.matcher("aaaaRE083040002 sds");
		  boolean b5= matcher.matches();
		  //当条件满足时，将返回true，否则返回false
		  System.out.println(b5);

		String []ss2343=new String[]{"123"};
		ss2343=(String [])ArrayUtils.add(ss2343, "789");
		System.out.println(Arrays.toString(ss2343));
		String sss22="abc";
		System.out.println(Arrays.toString(StringUtils.split(sss22,"")));
		List<String> sl=new ArrayList<String>();
		sl.add("a");
		sl.add("b");
		System.out.println(Arrays.toString(sl.toArray(new String[]{"cc"})));
		StringBuffer ssssss=new StringBuffer("123");
		ssssss.append("741");
		System.out.println(ssssss.toString());
		Map<String,Object> mm=new HashMap<String, Object>();
		mm.put("aaa",new Product());
		System.out.println("ss"+mm);
		System.out.println(new BigDecimal("12.3").toString());
		ConvertUtilsBean cub=new ConvertUtilsBean();
		cub.register(new BigDecimalConverter(null), BigDecimal.class);
		cub.register(new Converter(){
			public Object convert(Class arg0, Object arg1) {
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				try {
					if(arg1!=null){
						return df.parse(arg1.toString());
					}
				} catch (ParseException e) {
				}
				return null;
			}},Date.class);
		BeanUtilsBean b=new BeanUtilsBean(cub,new PropertyUtilsBean());
		Product product3=new Product();
		product3.setDefaultProductSku(new ProductSku());
		Method m=MethodUtils.getAccessibleMethod(product3.getClass(), "getDefaultProductSku",new Class[]{});
		System.out.println(m.getReturnType().newInstance());
//		BeanUtils.setProperty(product3, "defaultProductSku", new Object());
		b.setProperty(product3, "publishTime", "2007-01-18");
//		BeanUtilsBean.getInstance().setProperty(product3, "defaultProductSku.productSkuId", "11");
		BeanUtilsBean.getInstance().getProperty(product3, "defaultProductSku");
		System.out.println(product3.getPublishTime());
		StringBuffer ssbb=new StringBuffer("aaa");
		ssbb.insert(1,"bbb");
		System.out.println(ssbb.toString());
		String sss=URLEncoder.encode("Blue hills_122950306_mn.jpg","utf-8");
		System.out.println(sss);
		sss=URLDecoder.decode(sss, "utf-8");
		System.out.println(sss);
		String ssss="/eStore/name/N001/22/sdfasfdsadf/prod209.html";
		ssss.split("/");
		Map<Integer ,String>map=new HashMap<Integer, String>();
		map.put(2,"b");
		map.put(1,"a");
		map.put(2,"b");
		Set<Integer>iii=map.keySet();
		for (Integer integer : iii) {
			System.out.println(integer);
		}
		
		String defaultproductReviewGivenPoints="1,2,3a,5";
		System.out.println("sssssssss_____"+Arrays.binarySearch(defaultproductReviewGivenPoints.split(","),"1"));
		
		int i[]=(int[])ConvertUtils.convert(defaultproductReviewGivenPoints.split(","), int.class);
		System.out.println(NumberUtils.min(i));
		System.out.println(Arrays.toString(i));
		System.out.println("copy1227495389921".length());
		Product product=new Product();
		product.setProductName("aaa");
		ProductSku productSku=new ProductSku();
		productSku.setProductSkuCode("bbb");
		product.setDefaultProductSku(productSku);
		Product product2=new Product();
//		product2=(Product)BeanUtils.cloneBean(product);
//		BeanUtilsBean.getInstance().copyProperties(product2, product);
		/*ConvertUtilsBean cub=new ConvertUtilsBean();
		cub.register(new BigDecimalConverter(null), BigDecimal.class);
		cub.register(new Converter(){

			public Object convert(Class arg0, Object arg1) {
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				try {
					return df.parse(arg1.toString());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					return null;
				}
			}},Date.class);
		BeanUtilsBean b=new BeanUtilsBean(cub,new PropertyUtilsBean());*/
		b.copyProperties(product2, product);
		System.out.println(product2==product);
		System.out.println(product2.getDefaultProductSku());
		System.out.println(product.getDefaultProductSku());
		System.out.println(product2.getDefaultProductSku()==product.getDefaultProductSku());
//		ProductSku productSku2=(ProductSku)BeanUtils.cloneBean(productSku);
//		System.out.println(productSku2);
	}
}
