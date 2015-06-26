
package com.cartmatic.estore.core.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.view.AbstractUrlBasedView;

import com.cartmatic.estore.core.util.ContextUtil;

public class DownloadView extends AbstractUrlBasedView {
	private static final Log	log	= LogFactory.getLog(DownloadView.class);

	protected void renderMergedOutputModel(Map model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String strFileName = (String) model.get("uriPath");
		if (strFileName == null || strFileName.length() == 0) {
			return;
		}
		File r;
		FileInputStream in = null;
		try {
			r = new File(ContextUtil.getServletContext().getRealPath(
					strFileName));
			in = new FileInputStream(r);
			if (!r.isFile()) {
				return;
			}
		} catch (SecurityException se) { // Be unavailable permanently
			throw (new ServletException("Servlet lacks appropriate privileges."));
		}

		String mimeType = ContextUtil.getServletContext().getMimeType(
				strFileName);
		if (log.isDebugEnabled()) {
			log.debug("Mime Type1...." + mimeType);
		}
		String downloadFileName = strFileName.substring(strFileName
				.lastIndexOf("/") + 1);
		if (mimeType == null) {
			mimeType = "text/plain";
			// downloadFileName = downloadFileName+".zip";
		}
		if (log.isDebugEnabled()) {
			log.debug("Mime Type...." + mimeType);
		}
		response.setHeader("Content-Disposition", "attachment; filename="
				+ downloadFileName);
		// response.setHeader("Content-Length", (new
		// Long(r.length())).toString());

		response.setContentType(mimeType);

		OutputStream out = response.getOutputStream();
		byte[] buf = new byte[1024];
		int count = 0;

		while ((count = in.read(buf)) >= 0) {
			out.write(buf, 0, count);
		}
		out.close();
		in.close();
	}
}
