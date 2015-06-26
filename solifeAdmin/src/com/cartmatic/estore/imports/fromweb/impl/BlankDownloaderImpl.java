package com.cartmatic.estore.imports.fromweb.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.zip.GZIPInputStream;

import au.com.bytecode.opencsv.CSVWriter;

import com.cartmatic.estore.imports.fromweb.Downloader;

/**
 * This downloader is do nothing.
 * @author Ryan
 *
 */
public class BlankDownloaderImpl implements Downloader
{

	public Reader download(String urlPath, InputStream is, boolean isCompressionInput) throws IOException
	{
		// TODO Auto-generated method stub
		Reader reader = null;
		if (isCompressionInput)
		{
			reader = new BufferedReader(new InputStreamReader(new GZIPInputStream(is)), 512);
		}
		else
		{
			reader = new BufferedReader(new InputStreamReader(is),512);
		}
		return reader;
	}

	public String getBasePath()
	{
		// TODO Auto-generated method stub
		return null;
	}

	

	public void setBasePath(String avalue)
	{
		// TODO Auto-generated method stub

	}

	public void setCSVWriter(CSVWriter avalue)
	{
		
	}
}
