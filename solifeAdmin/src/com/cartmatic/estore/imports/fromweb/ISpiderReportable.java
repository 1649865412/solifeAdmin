package com.cartmatic.estore.imports.fromweb;

import java.net.URL;

public interface ISpiderReportable {
	public boolean spiderFoundURL(URL base, URL url);

	public void spiderURLError(String url);

	public void spiderFoundEMail(String email);
}
