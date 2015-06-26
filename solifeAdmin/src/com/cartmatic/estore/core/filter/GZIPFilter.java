
package com.cartmatic.estore.core.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import com.cartmatic.estore.common.helper.ConfigUtil;

/**
 * Filter that compresses output with gzip (assuming that browser supports
 * gzip).
 * 
 */
public class GZIPFilter extends OncePerRequestFilter {
	private final transient Log	log	= LogFactory.getLog(GZIPFilter.class);

	public void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		if (ConfigUtil.getInstance().getIsGzipEnabled()
				&& isGZIPSupported(request)) {
			if (log.isDebugEnabled()) {
				log.debug("GZIP supported, compressing response");
			}

			GZIPResponseWrapper wrappedResponse = new GZIPResponseWrapper(
					response);

			chain.doFilter(request, wrappedResponse);
			wrappedResponse.finishResponse();

			return;
		}

		chain.doFilter(request, response);
	}

	protected void initFilterBean() throws ServletException {
	}

	/**
	 * Test for GZIP cababilities. Optimized to be used together with
	 * CacheFilter.
	 * 
	 * @param request
	 *            The current user request
	 * @return boolean indicating GZIP support
	 */
	private boolean isGZIPSupported(HttpServletRequest request) {

		// If not cached, don't zip.
		//if (request.getAttribute("__oscache_filtered__cacheFilter") == null) {
		//	return false;
		//}

		// test if browser can accept gzip encoding
		String browserEncodings = request.getHeader("accept-encoding");
		return ((browserEncodings != null) && (browserEncodings.indexOf("gzip") != -1));
	}

}
