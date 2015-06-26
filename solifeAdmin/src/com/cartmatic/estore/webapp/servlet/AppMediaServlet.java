
package com.cartmatic.estore.webapp.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.common.helper.ConfigUtil;

public class AppMediaServlet extends HttpServlet implements Servlet {
	protected final transient Log	logger	= LogFactory
													.getLog(AppMediaServlet.class);

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public AppMediaServlet() {
		super();
	}

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest arg0,
	 *      HttpServletResponse arg1)
	 */
	@Override
	protected void doGet(HttpServletRequest arg0, HttpServletResponse arg1)
			throws ServletException, IOException {
		doPost(arg0, arg1);
		// TODO Auto-generated method stub
	}

	/*
	 * (non-Java-doc)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		File file;
		FileInputStream in = null;
		String path = ConfigUtil.getInstance().getMediaStorePath();
		String reqPath = URLDecoder.decode(request.getRequestURI(),
				Constants.DEFAULT_ENCODING);
		if (!path.endsWith("/")) {
			path += "/";
		}
		String filePath = path
				+ reqPath.substring(reqPath.indexOf("media/") + 6);
		// System.out.println("filePath:"+filePath);
		if (logger.isDebugEnabled()) {
			logger.debug("filePath:" + filePath);
		}
		try {
			file = new File(filePath);
			if (!file.exists() || !file.isFile()) { // Must be a file
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				return;
			}
			in = new FileInputStream(file);
		} 
		catch (FileNotFoundException e) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		catch (SecurityException se) { // Be unavailable permanently
			//throw (new ServletException("Servlet lacks appropriate privileges."));
			response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
			return;
		}

		String mimeType = getServletContext().getMimeType(filePath);

		// response.setContentType("text/html");
		response.setContentType(mimeType);
		OutputStream out = response.getOutputStream();
		byte[] buf = new byte[1024];
		int count = 0;
		while ((count = in.read(buf)) >= 0) {
			out.write(buf, 0, count);
		}
		in.close();
		out.close();
		// TODO Auto-generated method stub
	}
}