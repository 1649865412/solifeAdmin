
package com.cartmatic.estore.system.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.util.EncryptionUtils;

public class TranslateService {
	private final static Log log=LogFactory.getLog(TranslateService.class);
	/**
	 * hmac-md5 加密算法的缺省实现
	 * @param crackText 要加密的内容
	 * @param key 双方的私钥
	 * @return 加密后的数字指纹
	 */
	public static String hmacMd5Transalte(String crackText,String key) {
		String result="";
		try
		{
			byte[] plainText = crackText.getBytes("UTF8");
			byte[] keyBytes=key.getBytes("UTF8");
			 
		    SecretKey MD5key = new SecretKeySpec(keyBytes, "HmacMD5"); 
	
		    Mac mac = Mac.getInstance("HmacMD5");
		   
		    mac.init(MD5key);
	
		    mac.update(plainText);
		    	    
		   byte[]re= mac.doFinal();
		  
		   for(int i=0;i<re.length;i++){ 
		   	result += Integer.toHexString((0x000000ff&re[i])|0xffffff00).substring(6); 
		   }
		}
		catch(Exception ex){
			if(log.isDebugEnabled()){
				log.debug("hmacMd5Transalte" + ex.getMessage());
			}
		}
		return result;
	}

	/**
	 * md5 crack
	 * @param text
	 */
	public static String md5Translate(String text) {
		
		MessageDigest msgDigest = null;

        try {
            msgDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("System doesn't support MD5 algorithm.");
        }

        msgDigest.update(text.getBytes());

        byte[] bytes = msgDigest.digest();

        byte   tb;
        char   low;
        char   high;
        char   tmpChar;

        String md5Str = new String();

        for (int i = 0; i < bytes.length; i++) {
            tb = bytes[i];

            tmpChar = (char) ((tb >>> 4) & 0x000f);

            if (tmpChar >= 10) {
                high = (char) (('a' + tmpChar) - 10);
            } else {
                high = (char) ('0' + tmpChar);
            }

            md5Str += high;
            tmpChar = (char) (tb & 0x000f);

            if (tmpChar >= 10) {
                low = (char) (('a' + tmpChar) - 10);
            } else {
                low = (char) ('0' + tmpChar);
            }

            md5Str += low;
        }

        return md5Str;
	}

	
	/**
	 * Format the type like style
	 * @param orderDate 2005/05/02 20:30:10
	 * @param style yyyy-MM-dd hh:mm:ss
	 * @return
	 */
	public static String orderDateTranslate(String orderDate, String style) {
		
		GregorianCalendar cal=new GregorianCalendar();//默认为当前的时间
		
		try{
			int year=Integer.parseInt(orderDate.substring(0,4));
			int month=Integer.parseInt(orderDate.substring(5,7));
			int day=Integer.parseInt(orderDate.substring(8,10));
			
			int hour=Integer.parseInt(orderDate.substring(11,13));
			int minute=Integer.parseInt(orderDate.substring(14,16));
			int second=Integer.parseInt(orderDate.substring(17,19));
			
			cal.set(year,month,day,hour,minute,second);
		}
		catch(Exception ex){
			if(log.isDebugEnabled()){
				log.debug("Date convert has error: " + ex.getMessage());
			}
		}
		
		if(style.toUpperCase().trim().equals("TIMESTAMP")){//return timestamp from 1970-1-1 to now millisecond
			return String.valueOf(cal.getTimeInMillis());
		}
		SimpleDateFormat sdf=new SimpleDateFormat(style);
		return sdf.format(cal.getTime());
	}
	
	/**
	 * 加上定单的前缀
	 * @param orderNo
	 * @param pre
	 * @return
	 */
	public static String orderNoTranslate(String orderNo, String pre) {
		
		return pre+orderNo;
	}
	
	/**
	 * 
	 * @param httpUrl
	 * @return
	 */
	public static String getResponseFromHttpUrl(String httpUrl){
	    String result="";
	    try{
		    
	        URL url=new URL(httpUrl);
			HttpURLConnection con=(HttpURLConnection)url.openConnection();
			InputStream in=con.getInputStream();
			BufferedReader reader=new BufferedReader(new InputStreamReader(in,"GBK"));
			String temp=reader.readLine();
			
		while(temp!=null){ 
			result+=temp;
			temp=reader.readLine();
			} 
	    }catch(Exception ex){}
		return result.toString();
	}
	
	/**
	 * get the data from request,and send the data to url to validate.
	 * @param httpsUrl
	 * @param request request which can get data .
	 * @return
	 */
	public static String getResponseFromHttpsUrl(String httpsUrl,Map paramMap){
        
	    HttpsURLConnection conn=null;
        
        StringBuffer sb = new StringBuffer();
        //get data from request.
        
		StringBuffer sbuilder=new StringBuffer();
		
		Iterator i=paramMap.entrySet().iterator();

		if(i.hasNext()){
			Map.Entry en=(Map.Entry)i.next();
			sbuilder.append(en.getKey()+"="+en.getValue());
		}
		while(i.hasNext()){
			Map.Entry en=(Map.Entry)i.next();
			sbuilder.append("&" + en.getKey()+"="+en.getValue());
		}
		
		String params=sbuilder.toString();
		 
        try{
            URL url = new URL(httpsUrl);
            conn = (HttpsURLConnection) url.openConnection();
            
			conn.setRequestMethod("POST");            
            conn.setDoInput(true); 
            conn.setDoOutput(true); 
            //request paramters
            OutputStream out = conn.getOutputStream();
            out.write(params.getBytes());
            out.flush();
            
            InputStream in = conn.getInputStream();
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            
            String line=reader.readLine();
			while(line!=null){ 
				sb.append(line);
				line=reader.readLine();
			} 
        }catch (MalformedURLException e){
        	if(log.isDebugEnabled()){
        		log.debug("HTTPS_URL  MalformedURLException:" + e);
        	}
        }catch (IOException e) {
            if(log.isDebugEnabled()){
            	log.debug("HTTPS_URL  IOException:" + e);
            }
        }
        finally{
          if(conn!=null)conn.disconnect(); 
        }
        //System.out.println("content:[" + sb.toString() + "]");
        return sb.toString();
    }
	
	public static String md5TranslateUpperCase(String text)
	{
		return md5Translate(text).toUpperCase();
	}
	
	public static String sha256Encode(String message)
	{
		MessageDigest md;  
	    String encode = null;  
	    try {  
	        md = MessageDigest.getInstance("SHA-256");  
	        encode = byteArrayToHexString(md.digest(message.getBytes()));  
	    } catch (NoSuchAlgorithmException e) {  
	        e.printStackTrace();  
	    }  
	    return encode;
	}
	
	public static String byteArrayToHexString(byte b[]) {
        StringBuffer s = new StringBuffer();
        
        for (int j = 0; j < b.length; j++) {
            s.append(Integer.toHexString((int)((b[j]>>4)&0x0f)));
            s.append(Integer.toHexString((int)(b[j]&0x0f)));
        }
        
        return new String(s);
    }
}
