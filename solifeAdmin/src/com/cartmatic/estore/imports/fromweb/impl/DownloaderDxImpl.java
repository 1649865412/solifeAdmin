
package com.cartmatic.estore.imports.fromweb.impl;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.GZIPInputStream;

import org.htmlparser.Parser;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.tags.MetaTag;
import org.htmlparser.util.NodeList;
import org.jfree.util.Log;

import au.com.bytecode.opencsv.CSVWriter;

import com.cartmatic.estore.common.util.FileUtil;
import com.cartmatic.estore.imports.fromweb.Downloader;

public class DownloaderDxImpl implements Downloader
{
	private static final int	download_buff_size	= 1024;
	private CSVWriter			writer;
	private String				basePath;

	// todo use the systemconfig
	// private boolean isCompression = true;

	/**
	 * High Performance with cached. Download the http imputStream to disk.and
	 * returns the reader.
	 */
	public Reader download(String urlPath, InputStream httpIs,
			boolean isCompressionInput) throws IOException {
		// 如果不是product url,则直接返回
		if (!Downloader.DX_PRODUCT_URL_PAT.matcher(urlPath).matches()) {
			return new BlankDownloaderImpl().download(urlPath, httpIs,
					isCompressionInput);
		}

		// 如果是product,则需要分析并下载信息
		InputStream is = null;
		if (isCompressionInput) {
			is = new GZIPInputStream(httpIs, download_buff_size);
		} else {
			is = new BufferedInputStream(httpIs, download_buff_size);
		}

		byte[] buffer = new byte[download_buff_size];
		int len;
		// int cacheSize = 0; //cache size for CacheInputStream.
		StringBuffer content = new StringBuffer();
		while ((len = is.read(buffer)) > 0) {
			// cacheSize += len;
			// out.write(buffer, 0, len);
			if (len != download_buff_size) {
				byte[] tmp = new byte[len];
				System.arraycopy(buffer, 0, tmp, 0, len);
				// cache.add(tmp);
				content.append(new String(tmp));
			} else {
				// cache.add(buffer);
				content.append(new String(buffer));
				buffer = new byte[download_buff_size];
			}
		}
		// 分析产品详细页面.
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
	 * 
	 * @param htmlContent
	 * @throws Exception
	 */
	private void downloadProduct(String htmlContent) throws Exception {
		Parser parser = Parser.createParser(htmlContent, "utf-8");
		NodeList nodes = null;
		// 产品名
		nodes = parser.extractAllNodesThatMatch(new HasAttributeFilter("id", "_ctl0_content_HeadLine1"));
		TextNode pname = (TextNode) nodes.elementAt(0).getFirstChild();
		String productName = pname.getText();
		//System.out.println("start :"+productName);
		parser.reset();
		// 产品编码 & SKU
		nodes = parser.extractAllNodesThatMatch(new TagNameFilter("font"))
				.extractAllNodesThatMatch(
						new HasAttributeFilter("face", "Arial"))
				.extractAllNodesThatMatch(new HasAttributeFilter("size", "2"));
		TextNode pcode = (TextNode) nodes.elementAt(1).getParent()
				.getNextSibling().getNextSibling().getFirstChild();
		String productCode = "SZ998" + pcode.getText().trim();
		parser.reset();
		// 主目录	
		//nodes = parser.extractAllNodesThatMatch(new HasAttributeFilter("id", "_ctl0_content_lblPrimaryCategoryName"));
		//String catalogName = nodes.elementAt(0).getChildren().elementAt(1).getFirstChild().getText();
		//catalogName = catalogName.substring(catalogName.lastIndexOf(" - ") + 3);
		String catalogName = "Networking";
		parser.reset();
		// meta关键字
		parser.reset();
		nodes = parser.extractAllNodesThatMatch(new TagNameFilter("meta"))
				.extractAllNodesThatMatch(
						new HasAttributeFilter("name", "keywords"));
		String metaKeywords = ((MetaTag) nodes.elementAt(0)).getAttribute("content");
		parser.reset();
		// 产品描述
		nodes = parser.extractAllNodesThatMatch(new HasAttributeFilter(
				"id", "tabPageOverview"));
		String productDesc = nodes.elementAt(0).getChildren().toHtml().replaceAll("http://www.dealextreme.com", "#");
		parser.reset();
		// 价格
		nodes = parser.extractAllNodesThatMatch(new HasAttributeFilter("id", "_ctl0_content_Price"));
		TextNode price = (TextNode) nodes.elementAt(0).getChildren().elementAt(2);
		BigDecimal _price = new BigDecimal(price.getText().replace("$", ""));
		// productPrice
		String productPrice = _price.multiply(new BigDecimal("0.9")).setScale(2,BigDecimal.ROUND_HALF_UP).toString();
		// costPrice
		String costPrice = _price.multiply(new BigDecimal("0.4")).setScale(2,BigDecimal.ROUND_HALF_UP).toString();
		// listPrice
		String listPrice = _price.multiply(new BigDecimal("2")).setScale(2,BigDecimal.ROUND_HALF_UP).toString();
		parser.reset();
		// 产品图片&下载图片到本地
		nodes = parser.extractAllNodesThatMatch(new HasAttributeFilter("class", "preview"));
		for (int i = 0; i < nodes.size(); i++) {
			ImageTag img = (ImageTag) nodes.elementAt(i).getChildren().elementAt(1);
			
			URL url = new URL("http:"+ img.getImageURL().replace("_small", "").replace("_thumb", ""));
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			InputStream is = connection.getInputStream();
			String imgUrl = url.getPath().substring(1);
			FileUtil.writeInputStreamToFile(basePath + imgUrl, is);
			connection.disconnect();
			url = null;
		}
		parser.reset();
		
		// 写入csv文件
		// {"ProductName","productCode","catalog","metaKeywords","productDesc",
		// "skuCode","image","price"}
		String[] csvCell = { productName, productCode, catalogName,
				metaKeywords, productDesc, productCode, costPrice, productPrice, listPrice};
		writer.writeNext(csvCell);
		writer.flush();
		//System.out.println("end :"+productName);
	}

	/**
	 * Cached inputstream.
	 * 
	 * @author Ryan
	 * 
	 */
	private class CachedInputstream extends InputStream {
		private static final int	default_buff_size	= 1024;
		private int					cache_size			= default_buff_size;
		private byte[]				cache;
		// private int length = 0;
		private int					point				= 0;

		public CachedInputstream(List icache, int cacheSize) {
			super();
			// int l = icache.size();
			cache_size = cacheSize;
			// length = l * buff_size;
			cache = new byte[cache_size];
			int index = 0;
			for (Iterator it = icache.iterator(); it.hasNext();) {
				byte[] array = (byte[]) it.next();
				System.arraycopy(array, 0, cache, index, array.length);
				index += array.length;
			}
		}

		public int read() throws IOException {
			if (point + 1 > cache_size) {
				return -1;
			}
			return cache[point++] & 0xff;
		}

		public synchronized void reset() throws IOException {
			point = 0;
		}

		public void close() throws IOException {
			cache = null;
		}
	}

	public String getBasePath() {
		return basePath;
	}

	public void setBasePath(String avalue) {
		basePath = avalue;
	}

	public void setCSVWriter(CSVWriter avalue) {
		writer = avalue;
	}
}
