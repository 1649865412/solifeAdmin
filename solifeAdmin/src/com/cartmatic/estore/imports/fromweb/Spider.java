package com.cartmatic.estore.imports.fromweb;

import java.util.Date;

public interface Spider {
	public void setSpiderReport(ISpiderReportable avalue);
	public void setDownloader(Downloader avalue);
	public void setSinceTime(Date since);
	public void setBaseUrl(String avalue);
	public void addURL(String url);
	public void begin();
	public void cancel();
	public void clear();
}
