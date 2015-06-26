package com.cartmatic.estore.imports.fromweb.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;

import org.apache.log4j.Logger;

import com.cartmatic.estore.imports.fromweb.Downloader;
import com.cartmatic.estore.imports.fromweb.ISpiderReportable;
import com.cartmatic.estore.imports.fromweb.Spider;

public class SpiderImpl implements Spider
{

	private Logger logger = Logger.getLogger(SpiderImpl.class);
	private String baseUrl = null;
	private Downloader downloader = null;
	private String sinceTime = null;
	private String cookie = null;
	/**
	 * A collection of URLs that resulted in an error
	 */
	protected Set workloadError = new HashSet();

	/**
	 * A collection of URLs that are waiting to be processed
	 */
	protected Set workloadWaiting = new HashSet();

	/**
	 * A collection of URLs that were processed
	 */
	protected Set workloadProcessed = new HashSet();

	/**
	 * The class that the spider should report its URLs to
	 */
	protected ISpiderReportable report;
	
	/**
	 * A flag that indicates whether this process should be canceled
	 */
	protected boolean cancel = false;

	/**
	 * The constructor
	 * 
	 * @param report A class that implements the ISpiderReportable interface, 
	 * that will receive information that the spider finds.
	 */
	public SpiderImpl()
	{	}

	/**
	 * Get the URLs that resulted in an error.
	 * 
	 * @return A collection of URL's.
	 */
	private Collection getWorkloadError()
	{
		return workloadError;
	}

	/**
	 * Get the URLs that were waiting to be processed. You should add one URL to
	 * this collection to begin the spider.
	 * 
	 * @return A collection of URLs.
	 */
	private Collection getWorkloadWaiting()
	{
		return workloadWaiting;
	}

	/**
	 * Get the URLs that were processed by this spider.
	 * 
	 * @return A collection of URLs.
	 */
	private Collection getWorkloadProcessed()
	{
		return workloadProcessed;
	}

	/**
	 * Clear all of the workloads.
	 */
	public void clear()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("ErrorSize["+getWorkloadError().size()+"] waitSize[" + getWorkloadWaiting().size() +"] processedSize["+ getWorkloadProcessed().size()+"]");
		}
		getWorkloadError().clear();
		getWorkloadWaiting().clear();
		getWorkloadProcessed().clear();
		baseUrl = null;
	}

	/**
	 * Set a flag that will cause the begin method to return before it is done.
	 */
	public void cancel()
	{
		cancel = true;
	}

	/**
	 * Add a URL for processing.
	 * 
	 * @param url
	 */
	public void addURL(String url)
	{
		if (baseUrl == null)
		{
			baseUrl = url;
			// get the base url.
			if (baseUrl.lastIndexOf("/") > 7)
			{
				baseUrl = baseUrl.substring(0, baseUrl.lastIndexOf("/") + 1);
				logger.debug("baseUrl[" + baseUrl + "]");
			}
		}
		else if (!url.startsWith(baseUrl)) // check the scope, must inside the baseUrl.
		{
			return;
		}
		
		if (url.indexOf(";")!= -1) //remove the ";jsessionid" in url.
		{
			url = url.substring(0,url.indexOf(";"));
		}
		if (containsInWorkloadWaiting(url))
			return;
		if (containsInWorkloadError(url))
			return;
		if (containsInWorkloadProcessed(url))
			return;

		logger.debug("Adding to workload: " + url);
		addToWorkloadWaiting(url);
	}
	
	private boolean containsInWorkloadWaiting(String url)
	{
		return getWorkloadWaiting().contains(url);
	}
	private void addToWorkloadWaiting(String url)
	{
		getWorkloadWaiting().add(url);
	}
	private void removeWorkloadWaiting(String url)
	{
		getWorkloadWaiting().remove(url);
	}
	
	private boolean containsInWorkloadError(String url)
	{
		return getWorkloadError().contains(url);
	}
	private void addToWorkloadError(String url)
	{
		getWorkloadError().add(url);
	}
	
	private boolean containsInWorkloadProcessed(String url)
	{
		return getWorkloadProcessed().contains(url);
	}
	private void addToWorkloadProcessed(String url)
	{
		getWorkloadProcessed().add(url);
	}
	
	/**
	 * Called internally to process a URL
	 * 
	 * @param url The URL to be processed.
	 */
	private void processURL(String strUrl) throws MalformedURLException, IOException
	{
		URL url = new URL(strUrl);
		
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		boolean hasError = false;
		try
		{
			logger.debug("Processing: " + strUrl);
			String host = url.getHost();
			int port = url.getPort();
			if (port != -1)
			{
				host=host+":"+port;
			}
			connection.setRequestProperty("Host", host);
			connection.setRequestProperty("Connection", "close");
			//connection.setRequestProperty("Connection", "keep-alive");
			//connection.setRequestProperty("Keep-Alive", "300");
			connection.setRequestProperty("Accept", "text/xml,application/xml,application/xhtml+xml,text/html;q=0.9,text/plain;q=0.8,image/png,*/*;q=0.5");
			connection.setRequestProperty("Accept-Encoding", "gzip");			
			connection.setRequestProperty("Accept-Charset", "utf-8;q=0.7,*;q=0.7");
			connection.setUseCaches(false);
			if (cookie != null)
				connection.setRequestProperty("Cookie", cookie);
			
			if (sinceTime != null)
			{
				connection.setRequestProperty("If-Modified-Since", sinceTime);
			}
			// Not modified.
			if (connection.getResponseCode()== connection.HTTP_NOT_MODIFIED)
			{
				removeWorkloadWaiting(strUrl);
				addToWorkloadProcessed(strUrl);
				connection.disconnect();
				logger.debug("HTTP_NOT_MODIFIED: " + strUrl);
				return;
			}
			if ((connection.getContentType() != null)
					&& !connection.getContentType().toLowerCase().startsWith("text/"))
			{
				removeWorkloadWaiting(strUrl);
				addToWorkloadProcessed(strUrl);
				logger.debug("Not processing because content type is: "
						+ connection.getContentType());
				connection.disconnect();
				return;
			}			
			// check the redirect, if has redirect, the url will be change.
			if (!strUrl.equals(connection.getURL().toExternalForm()))
			{
				String redirUrl = connection.getURL().toExternalForm();
				if (containsInWorkloadWaiting(redirUrl))
				{
					connection.disconnect();
					return;
				}
				if (containsInWorkloadError(redirUrl))
				{
					connection.disconnect();
					return;
				}
				if (containsInWorkloadProcessed(redirUrl))
				{
					connection.disconnect();
					return;
				}
				url = connection.getURL(); //redirect url;
			}
			boolean isCompressionInput = false;
			if ("gzip".equals(connection.getContentEncoding()))
			{
				isCompressionInput = true;
			}
			// read the URL
			InputStream is = connection.getInputStream();
			String setCookie = connection.getHeaderField("Set-Cookie");
			if (logger.isDebugEnabled())
				logger.debug("setCookie:"+setCookie);
			// download
			Reader r = downloader.download(url.getPath(), is, isCompressionInput);
			// parse the URL
			HTMLEditorKit.Parser parse = new HTMLParse().getParser();
			parse.parse(r, new Parser(url), true);
			r.close();
		} 
		catch (IOException e)
		{
			//e.printStackTrace();
			//logger.error(e);
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
			removeWorkloadWaiting(strUrl);
			addToWorkloadError(strUrl);			
			logger.info("Error: " + strUrl);
			report.spiderURLError(strUrl);
			return;
		}
		// mark URL as complete
		removeWorkloadWaiting(strUrl);
		addToWorkloadProcessed(strUrl);
		logger.debug("Complete: " + strUrl);
	}

	/**
	 * Called to start the spider
	 */
	public void begin()
	{
		cancel = false;
		while (!getWorkloadWaiting().isEmpty() && !cancel)
		{
			Object list[] = getWorkloadWaiting().toArray();
			for (int i = 0; (i < list.length) && !cancel; i++)
			{
				try
				{
					processURL((String) list[i]);
				}
				catch (Exception e)
				{
					e.printStackTrace();
					logger.error( list[i]+" error="+e.getMessage());
				}
			}
		}
	}

	/**
	 * A HTML parser callback used by this class to detect links
	 * 
	 * @author Jeff Heaton
	 * @version 1.0
	 */
	protected class Parser extends HTMLEditorKit.ParserCallback
	{
		protected URL base;

		public Parser(URL base)
		{
			this.base = base;
		}

		public void handleSimpleTag(HTML.Tag t, MutableAttributeSet a, int pos)
		{
			String href = (String) a.getAttribute(HTML.Attribute.HREF);

			if ((href == null) && (t == HTML.Tag.FRAME))
			{
				href = (String) a.getAttribute(HTML.Attribute.SRC);
			}

			if (href == null)
			{
				return;
			}
			if (href.contains("javascript:"))
			{
				return;
			}
			int i = href.indexOf('#');
			if (i != -1)
				href = href.substring(0, i);

			if (href.toLowerCase().startsWith("mailto:"))
			{
				report.spiderFoundEMail(href);
				return;
			}

			handleLink(base, href);
		}

		public void handleStartTag(HTML.Tag t, MutableAttributeSet a, int pos)
		{
			handleSimpleTag(t, a, pos); // handle the same way

		}

		protected void handleLink(URL base, String str)
		{
			try
			{
				URL url = new URL(base, str);
				if (report.spiderFoundURL(base, url))
				{
					addURL(url.toString());
				}
				//close url;
				base=null;
				url=null;
			}
			catch (MalformedURLException e)
			{
				logger.debug("Found malformed URL: " + str);
			}
		}
	}
	
	public void setBaseUrl(String avalue)
	{
		baseUrl = avalue;
	}
	
	public void setDownloader(Downloader downloader)
	{
		this.downloader = downloader;
	}

	public void setSpiderReport(ISpiderReportable avalue)
	{
		this.report = avalue;
	}

	public void setSinceTime(Date sinceTime)
	{
		if (sinceTime == null)
		{
			return;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		this.sinceTime = sdf.format(sinceTime);
	}


}
