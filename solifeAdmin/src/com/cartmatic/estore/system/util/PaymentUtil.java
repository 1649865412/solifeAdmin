package com.cartmatic.estore.system.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * CreatTime: 2006-7-31
 * Decription: The class provide common method for payment
 * Author: chenshangxuan
 */
public class PaymentUtil {
    
	public static String getParamStr(String params, ServletRequest request, String param)
	{
		if (request.getParameter(param)!=null)
		{
			if (params != null && params.length() > 0)
			{
				return "&" + param+"=" + request.getParameter(param);
			}
			else
			{
				return param+"=" + request.getParameter(param);
			}
		}
		return "";
	}
	
    /**
     * submit data to url by https connection
     * @param httpsUrl
     * @param data
     * @param contentType such as "text/xml"
     * @return return response text
     */
    public static String submitDataFromHttpsUrl(String httpsUrl,String data,String contentType){
        
        HttpsURLConnection conn=null;
        
        StringBuffer sb = new StringBuffer();
        
        try{
            
            URL url = new URL(httpsUrl);
            
            conn = (HttpsURLConnection) url.openConnection();
            
            conn.setRequestMethod("POST");
            
            if(contentType!=null){
                conn.setRequestProperty("Content-type", contentType);
            }
            
            conn.setDoInput(true); 
            conn.setDoOutput(true); 
            
            OutputStream out = conn.getOutputStream();
            
            out.write(data.getBytes());
            
            out.flush();
            
            InputStream in = conn.getInputStream();
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            
            String line=reader.readLine();
			while(line!=null){ 
				sb.append(line);
				line=reader.readLine();
			} 
			
        }catch (MalformedURLException e){
            System.out.println("HTTPS_URL MalformedURLException:" + e);
        }catch (IOException e) {
            System.out.println("HTTPS_URL IOException:" + e);
        }
        finally{
          if(conn!=null)conn.disconnect(); 
        }
       
        return sb.toString();
    } 
    
    public static Map getParseData(String xmlData){
        
        Map responseMap=new HashMap();
        
        SAXReader reader = new SAXReader();
        try{
            ByteArrayInputStream stream = new ByteArrayInputStream(xmlData.getBytes("UTF-8"));
            Document document=reader.read(stream);
            
            Element root = document.getRootElement();
            
            for (Iterator i = root.elementIterator(); i.hasNext();) {
             Element temp = (Element) i.next();
             responseMap.put(temp.getName(),temp.getText());
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
          return responseMap;
    }
    /**
     *  write the data received from the the payment gateway to a file, build to html format. 
     * @param request
     * @param fullFileName
     */
    public static void writeReceiveDataToFile(HttpServletRequest request,String fullFileName){
    	java.io.FileWriter fw=null;
    	try{
    		fw=new java.io.FileWriter(fullFileName);
    		java.io.PrintWriter pw=new java.io.PrintWriter(fw);
    		pw.println("<form method='post'>");
    		Map map=request.getParameterMap();
    		Iterator i=map.entrySet().iterator();
    		while(i.hasNext())
    		{
				Map.Entry en=(Map.Entry)i.next();
				try{
					String[]value=(String[])en.getValue();
					pw.println("<input type=\"hidden\" Name=\"" + en.getKey() + "\" Value=\"" + value[0] + "\"/>");
				}catch(Exception rrrrx){}
    		}
    		pw.println("</form>");
    		pw.close();
    		fw.close();
    	}catch(Exception ex){
    		try{
    			ex.printStackTrace();
    		}catch(Exception eee){}
    	}
    }

}
