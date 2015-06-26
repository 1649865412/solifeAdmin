package com.cartmatic.estore.imports.fromweb;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.regex.Pattern;

import au.com.bytecode.opencsv.CSVWriter;

public interface Downloader {
	public final Pattern LIB_PRODUCT_URL_PAT = Pattern.compile("^(.+)_p(\\d+).html$");
	public final Pattern DX_PRODUCT_URL_PAT = Pattern.compile("^(.+)details.dx/sku.(\\d+)$");
	public final Pattern SHOES_PRODUCT_URL_PAT = Pattern.compile("^(.+)-p-(\\d+).html$");
	public final Pattern WATCH_PRODUCT_URL_PAT = Pattern.compile("^(.+)_(\\d+).html$");
	public final Pattern SD_PRODUCT_URL_PAT = Pattern.compile("^(.+)product(\\d+).html$");
	
	public String getBasePath();
	/**
	 * set the download path
	 *
	 */
	public void setBasePath(String avalue);
	public void setCSVWriter(CSVWriter avalue);
	public Reader download(String urlPath, InputStream is, boolean isCompressionInput) throws IOException;
}
