package com.cartmatic.estore.system.payment;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.cartmatic.estore.common.model.order.SalesOrder;
import com.cartmatic.estore.framework.test.BaseTransactionalTestCase;
import com.cartmatic.estore.order.OrderConstants;
import com.cartmatic.estore.order.service.SalesOrderManager;

public class CtopayTest extends BaseTransactionalTestCase{
	@Autowired
	private SalesOrderManager salesOrderManager;
	private static final int download_buff_size = 1024;	
	private static String ctopay_strUrl = "https://192.168.1.123/system/payment/ctopay_response.html";
	private static String successFlag = "1";
	private static String md5Key = "wcaTD`YL";
	
	private String orderNo = "kedou101100002";
	
	@Test
	@Rollback
	public void testPay()
	{
		SalesOrder salesOrder = salesOrderManager.getSalesOrderByOrderNo(orderNo);
		Assert.assertNotNull(salesOrder);
		//确保订单未支付完
		Assert.assertNotSame(salesOrder.getPaymentStatus(), OrderConstants.PAYMENT_STATUS_PAID);
		//paymentGatewayManager.getByPaymentGatewayCode(paymentGatewayCode)
		try
		{
			runResponse(orderNo, salesOrder.getShouldPay());
		}catch (Exception e)
		{
			e.printStackTrace();
		}
		//salesOrderManager.evict(salesOrder);
		//this.setComplete();
	}
	
	private void runResponse(String orderNo, BigDecimal amount) throws Exception
	{
		String md5Info = com.cartmatic.estore.system.util.TranslateService.md5Translate(orderNo + "10" + amount + successFlag + md5Key );
		String strUrl = ctopay_strUrl + "?BillNo="+orderNo+
		"&Currency=10&Amount="+amount+
		"&Succeed=1&Result=pay_result&MD5info="+md5Info;
		
		URL url = new URL(strUrl);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		boolean hasError = false;
		try
		{
			String host = url.getHost();
			int port = url.getPort();
			if (port != -1)
			{
				host=host+":"+port;
			}
			connection.setRequestProperty("Host", host);
			connection.setRequestProperty("Connection", "close");
			connection.setRequestProperty("Accept", "text/xml,application/xml,application/xhtml+xml,text/html;q=0.9,text/plain;q=0.8,image/png,*/*;q=0.5");
			connection.setRequestProperty("Accept-Encoding", "gzip");			
			connection.setRequestProperty("Accept-Charset", "gb2312,utf-8;q=0.7,*;q=0.7");
			connection.setUseCaches(false);
			// check the redirect, if has redirect, the url will be change.
			if (!strUrl.equals(connection.getURL().toExternalForm()))
			{
				String redirUrl = connection.getURL().toExternalForm();				
				url = connection.getURL(); //redirect url;
			}
			// read the URL
			InputStream is = connection.getInputStream();
			//连接的响应头必需是200,否则证明有问题.
			Assert.assertEquals(connection.getResponseCode(), 200); 
			// download
			byte[] buffer = new byte[download_buff_size];
			int len;
	        //int cacheSize = 0;
			while ((len = is.read(buffer))>0) 
	        {
	        	//cacheSize += len;
	        	System.out.print(buffer);
	        	//out.write(buffer, 0, len);	        	
			}
		} 
		catch (IOException e)
		{
			hasError = true;
		}
		finally
		{
			connection.disconnect();			
			connection=null;
			url=null;
		}
		if (hasError)
		{
			System.out.println("Error: " + strUrl);
			return;
		}
		// mark URL as complete
		System.out.println("Complete: " + strUrl);
	}
	
	
}
