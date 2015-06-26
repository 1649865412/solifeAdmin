package com.cartmatic.estore.system.payment.alipay;
import java.net.*;
import java.io.*;
public class CheckURL {
	public static String check(String urlvalue ) {
		  String inputLine="";
		  
			try{
					URL url = new URL(urlvalue);
					
					HttpURLConnection urlConnection  = (HttpURLConnection)url.openConnection();
					
					BufferedReader in  = new BufferedReader(
				            new InputStreamReader(
				            		urlConnection.getInputStream()));
				
					inputLine = in.readLine().toString();
				}catch(Exception e){
					e.printStackTrace();
				}
		    return inputLine;
	  }
}
