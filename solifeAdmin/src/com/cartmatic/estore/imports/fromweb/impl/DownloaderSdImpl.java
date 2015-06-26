package com.cartmatic.estore.imports.fromweb.impl;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.GZIPInputStream;

import org.htmlparser.Parser;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.MetaTag;
import org.htmlparser.util.NodeList;
import org.jfree.util.Log;

import au.com.bytecode.opencsv.CSVWriter;

import com.cartmatic.estore.common.util.FileUtil;
import com.cartmatic.estore.imports.fromweb.Downloader;


public class DownloaderSdImpl implements Downloader{
	private static final int download_buff_size = 1024;
	private CSVWriter writer;
	private String basePath;
	//todo use the systemconfig
	//private boolean isCompression = true;
	
	/**
	 * High Performance with cached.
	 * Download the http imputStream to disk.and returns the reader.
	 */
	public Reader download(String urlPath, InputStream httpIs, boolean isCompressionInput)throws IOException
	{
		//如果不是product url,则直接返回
		if (!Downloader.SD_PRODUCT_URL_PAT.matcher(urlPath).matches())
		{
			return new BlankDownloaderImpl().download(urlPath, httpIs, isCompressionInput);
		}
		
		//如果是product,则需要分析并下载信息
        InputStream is = null;
		if (isCompressionInput)
		{
			is = new GZIPInputStream(httpIs, download_buff_size);
		}
		else
		{
			is  = new BufferedInputStream(httpIs, download_buff_size);
		}
		
        byte[] buffer = new byte[download_buff_size];
        int len;
        //int cacheSize = 0; //cache size for CacheInputStream.
        StringBuffer content = new StringBuffer();
        while ((len = is.read(buffer))>0) 
        {
        	//cacheSize += len;
        	//out.write(buffer, 0, len);
        	if (len != download_buff_size)
        	{
        		byte[] tmp = new byte[len];
        		System.arraycopy(buffer, 0, tmp, 0, len);
        		//cache.add(tmp);
        		content.append(new String(tmp));
        	}
        	else
        	{
        		//cache.add(buffer);
        		content.append(new String(buffer));
        		buffer = new byte[download_buff_size];
        	}
		}
        //分析产品详细页面.
        try {
			downloadProduct(content.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.error(e);
			e.printStackTrace();
		}
        is.close();        
        List cache = new LinkedList();
		return new InputStreamReader(new CachedInputstream(cache, 0));
	}
	
	/**
	 * 下载产品信息
	 * @param htmlContent
	 * @throws Exception
	 */
	private void downloadProduct(String htmlContent) throws Exception
	{
		Parser parser=Parser.createParser(htmlContent, "utf-8");
		NodeList nodes = null;
		//产品名		
		nodes = parser.extractAllNodesThatMatch(new TagNameFilter("H1"));
		TextNode pname = (TextNode)nodes.elementAt(0).getFirstChild();
		String productName = pname.getText().trim();
		//产品编码 & SKU 目前只使用UUID实现.
		//String productCode = java.util.UUID.randomUUID().toString();
		parser.reset();
		nodes = parser.extractAllNodesThatMatch(new HasAttributeFilter("class","fb-like"));
		String productCode = nodes.elementAt(0).getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getText();
		productCode = productCode.replaceAll(":", "").trim();
		//主目录
		parser.reset();
		nodes = parser.extractAllNodesThatMatch(new HasAttributeFilter("class","breadcrumbs"));
		//NodeList cats = nodes.elementAt(0).getFirstChild().getFirstChild().getChildren();
		//
		NodeList cats = nodes.elementAt(0).getChildren().elementAt(1).getChildren().elementAt(1).getChildren();
		String catalogName = "";
		for (int i=1; i < cats.size(); i++)
		{
			if (cats.elementAt(i) instanceof LinkTag)
			{
				LinkTag cat = (LinkTag)cats.elementAt(i);
				if (!cat.getLinkText().equals("Home"))
				{
					if (catalogName.length() == 0)
					{
						catalogName = cat.getLinkText().trim();
					}
					else
					{
						catalogName = catalogName + ">"+cat.getLinkText().trim();
					}
				}
				//catalogName = cat.getLinkText().trim();
			}
		}
		//meta关键字
		//parser.reset();
		//nodes = parser.extractAllNodesThatMatch(new TagNameFilter("meta")).extractAllNodesThatMatch(new HasAttributeFilter("name","keywords"));
		String metaKeywords = productName;
		//产品描述
		parser.reset();
		nodes = parser.extractAllNodesThatMatch(new HasAttributeFilter("id","goods_desc"));
		String productDesc = nodes.elementAt(0).getChildren().toHtml();
		//产品描述图片下载
		URL url = null;
		HttpURLConnection connection = null;
		InputStream is = null;
		Parser parserImg = Parser.createParser(productDesc, "utf-8");
		NodeList desc_imgs = parserImg.extractAllNodesThatMatch(new TagNameFilter("img"));
		for (int i =0; i < desc_imgs.size(); i++)
		{
			ImageTag desc_img = (ImageTag) desc_imgs.elementAt(i);
			String desc_img_url = desc_img.getImageURL();
			if (!desc_img_url.startsWith("http"))
			{
				desc_img_url = "http://www.sammydress.com/" + desc_img_url;
			}
			try
			{
				url = new URL(desc_img_url);
				connection = (HttpURLConnection) url.openConnection();
				connection.setConnectTimeout(20000);
				connection.setReadTimeout(20000);
				is = connection.getInputStream();
				FileUtil.writeInputStreamToFile(basePath + "uploads_desc/" + url.getPath(), is);
			}
			catch (Exception e)
			{
				System.out.println("Time out:"+ desc_img_url );
			}
			finally
			{
				connection.disconnect();
				url = null;
			}
			//desc_img.getImageURL()
		}
		//去掉外网图片的引用.
		productDesc = productDesc.replaceAll("http://www.davismicro.com.cn:9000/", "");
		//价格
		parser.reset();
		nodes = parser.extractAllNodesThatMatch(new HasAttributeFilter("class","my_shop_price"));
		TextNode price = (TextNode)nodes.elementAt(0).getFirstChild();
		String productPrice = price.getText();
		//产品图片&下载图片到本地
		parser.reset();
		nodes = parser.extractAllNodesThatMatch(new HasAttributeFilter("class","jqzoom"));
		TagNode img = (TagNode) nodes.elementAt(0).getChildren().elementAt(1);
		String imgUrl = img.getAttribute("jqimg");
		url = new URL("http://www.sammydress.com"+imgUrl);
		connection = (HttpURLConnection) url.openConnection();
		connection.setConnectTimeout(20000);
		connection.setReadTimeout(20000);
		is = connection.getInputStream();
		FileUtil.writeInputStreamToFile(basePath + imgUrl, is);
		connection.disconnect();
		url = null;
		//更多图片
		String images = "";
		parser.reset();
		nodes = parser.extractAllNodesThatMatch(new HasAttributeFilter("class","scrollableDiv"));
		if (nodes.size() > 0)
		{
			NodeList imgs = nodes.elementAt(0).getChildren();
			for (int i = 0; i < imgs.size(); i++)
			{
				if (imgs.elementAt(i) instanceof LinkTag)
				{
					TagNode mimg = (TagNode) imgs.elementAt(i).getFirstChild().getNextSibling();
					if (mimg == null)
						continue;
					String imgsUrl = mimg.getAttribute("bigimg");
					if ( imgUrl.equals(imgsUrl))
						continue;
					try
					{
						url = new URL("http://www.sammydress.com"+imgsUrl);
						connection = (HttpURLConnection) url.openConnection();
						connection.setConnectTimeout(20000);
						connection.setReadTimeout(20000);
						is = connection.getInputStream();
						FileUtil.writeInputStreamToFile(basePath + "uploads_more/" + imgsUrl, is);
					}
					catch(Exception e)
					{
						System.out.println("Time out:"+ imgsUrl );
					}
					finally
					{
						connection.disconnect();
						url = null;
					}
					images += imgsUrl + ";";
				}
			}
		}
		//写入csv文件
		//{"ProductName","productCode","catalog","metaKeywords","productDesc","skuCode","image","price"}
		String[] csvCell = {productName, productCode, catalogName, metaKeywords, productDesc, productCode, imgUrl, productPrice, images};
		writer.writeNext(csvCell);
	}
	
	
	
	/**
	 * Cached inputstream.
	 * 
	 * @author Ryan
	 *
	 */
	private class CachedInputstream extends InputStream
	{
		private static final int default_buff_size = 1024;
		private int cache_size = default_buff_size;
		private byte[] cache;
		//private int length = 0;
		private int point = 0;
		
		public CachedInputstream(List icache, int cacheSize)
		{
			super();
			//int l = icache.size();
			cache_size = cacheSize;
			//length = l * buff_size;
			cache = new byte[cache_size];
			int index = 0;
			for(Iterator it = icache.iterator(); it.hasNext();) 
			{
				byte[] array = (byte[])it.next();
				System.arraycopy(array, 0, cache, index, array.length);
				index += array.length;
			}
		}
		
		public int read() throws IOException
		{
			if (point+1 > cache_size)
			{
				return -1;
			}
			return cache[point++] & 0xff;
		}
		
		public synchronized void reset() throws IOException 
		{
			point = 0;
		}
		
		public void close()throws IOException
		{
			cache = null;
		}
	}
	
	public String getBasePath()
	{
		return basePath;
	}

	public void setBasePath(String avalue)
	{
		basePath = avalue;
	}
	
	public void setCSVWriter(CSVWriter avalue)
	{
		writer = avalue;
	}

}
