package com.cartmatic.estore.imports.fromweb.impl;

import java.net.URL;

import org.apache.log4j.Logger;

import com.cartmatic.estore.imports.fromweb.Downloader;
import com.cartmatic.estore.imports.fromweb.ISpiderReportable;

public class DxReportImpl  implements ISpiderReportable{
	
	private Logger logger = Logger.getLogger(DxReportImpl.class);
	
	public boolean spiderFoundURL(URL base, URL url)
	{
		String strBase = base.toExternalForm();
		String strUrl = url.toExternalForm();
		if (excludeKeyWord(strBase) || excludeKeyWord(strUrl) )
		{
			return false;
		}
		//only parse *.html;
		if (Downloader.DX_PRODUCT_URL_PAT.matcher(strUrl).matches())
		{
			return true;
		}
		return false;
	}
	
	private boolean excludeKeyWord(String url)
	{
		return (url.contains("'") || 
				url.contains("+") || 
				url.contains("?") || 
				url.contains("/cart/") ||
				url.contains("/customer/") ||
				url.contains("/textsearch/") ||
				url.contains("_dy.html"));
	}
	
	public void spiderURLError(String url)
	{
		logger.warn("Error url="+url);
	}

	public void spiderFoundEMail(String email)
	{
		logger.info("email="+email);
	}
}
