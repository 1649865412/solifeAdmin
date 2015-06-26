
package com.cartmatic.estore.webapp.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.navigator.menu.MenuComponent;
import net.sf.navigator.menu.MenuRepository;

import org.springframework.web.filter.OncePerRequestFilter;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.common.helper.MenuHelper;
import com.cartmatic.estore.common.model.system.SystemVersion;
import com.cartmatic.estore.core.util.ContextUtil;
import com.cartmatic.estore.system.service.SystemVersionManager;
import com.cartmatic.estore.webapp.util.RequestContext;
import com.cartmatic.estore.webapp.util.RequestUtil;

/**
 * 主要是设置基于Request的上下文环境，以及设置菜单上下文环境等。
 */
public class ActionFilter extends OncePerRequestFilter {

	private ServletContext	context;

	private boolean			isFront;

	public void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		if (isFront) {
			// will be used in eStore paging. Don't remove.
			request.setAttribute("OriginalRequestURL", request.getRequestURL().toString());
			request.setAttribute("OriginalRequestURI", request.getRequestURI().toString());
			request.setAttribute("OriginalQueryString", request.getQueryString()==null?null:request.getQueryString().toString());
			if (request.getParameter("_query_cartmatic_version_info") != null) {
				Object ver_obj = ContextUtil.getSpringBeanById("systemVersionManager");
				if (ver_obj != null) {
					SystemVersionManager v_mgr = (SystemVersionManager) ver_obj;
					SystemVersion sv = v_mgr.getSystemVersion();
					response.setHeader("reg_copyright", "domain["
							+ sv.getDomainName() + "] license["
							+ sv.getLicenseKey() + "] ver["
							+ sv.getSysVersion() + "] Cartmatic Software");
				}
			}
		}

		long startProcessTime = System.currentTimeMillis();

		

		RequestContext.setCurrentHttpRequest(request);

		/**
		 * target to filter the main page request only, which don't have a
		 * decorator in url
		 */
		if (!isFront && (request.getParameter("decorator") == null)) {
			// menu handling, front-end and back-end use different
			// implementations
			request.setAttribute("OriginalRequestURI", request.getRequestURI().toString());
			MenuRepository menuRepository = (MenuRepository) context.getAttribute(MenuRepository.MENU_REPOSITORY_KEY);
			List<MenuComponent> menuContext = menuRepository.getTopMenus();
			
			String url = request.getRequestURI().substring(RequestUtil.getUrlPrefixIdx(request));
			List<String> menuNav = MenuHelper.getSelectedNavMenu(menuContext, url);
			request.setAttribute(Constants.KEY_MENU_CONTEXT, menuContext);
			request.setAttribute(Constants.KEY_MENU_NAV, menuNav);
			MenuComponent currentMenu=MenuHelper.getCurrentMenu(menuContext, url);
			request.setAttribute(Constants.KEY_CURRENT_MENU, currentMenu);
			List<MenuComponent> menuList=MenuHelper.getMenuList(menuContext);
			request.setAttribute(Constants.KEY_MENU_CONTEXT_LIST, menuList);
		}

		// if (logger.isDebugEnabled()) {
		// //可用于帮助分析多线程多用户的时候的问题
		// NDC.push(request.getRemoteUser());
		// }
		try {
			chain.doFilter(request, response);
		} finally {
			RequestContext.clearCurrentHttpRequest();
		}

		if (logger.isDebugEnabled()) {
			// NDC.pop();
			// performance info logging
			String requestUrl = RequestUtil.getFullRequestUrl(request).toString();
			StringBuilder sb = new StringBuilder();
			sb.append("Web Request Performance Monitor - [URL: ").append(
					requestUrl).append("], [Process time: ");
			sb.append(System.currentTimeMillis() - startProcessTime).append(
					" ms].");

			if (logger.isTraceEnabled()
					&& (request.getParameter("decorator") == null)) {
				sb.append("\n").append(RequestUtil.getRequestInfo(request));
			}
			logger.debug(sb.toString());
		}
	}

	protected void initFilterBean() throws ServletException {
		this.isFront = ContextUtil.isStoreFront();
		context = getServletContext();
	}
}
