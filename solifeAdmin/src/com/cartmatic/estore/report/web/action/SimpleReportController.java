/**
 * 
 */

package com.cartmatic.estore.report.web.action;

import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import com.cartmatic.estore.core.controller.BaseController;
import com.cartmatic.estore.core.util.UrlUtil;
import com.cartmatic.estore.report.dao.impl.SimpleReportDaoImpl;
import com.cartmatic.estore.webapp.util.RequestUtil;

/**
 * 跳过Manager，业务处理很简单，也不需要事务。Dao不使用接口，因为只是为这个Controller服务，其他功能不会用到。
 * 
 * @author Ryan
 * 
 */
public class SimpleReportController extends BaseController {
	SimpleReportDaoImpl	dao;

	public ModelAndView defaultAction(HttpServletRequest request, HttpServletResponse resp) throws ServletException 
	{
		String reportName = UrlUtil.parsePageName(request.getRequestURI());
		String methodName = "get" + StringUtils.capitalize(reportName) + "Model";

		Map model = null;
		Method method = null;

		try {
			method = SimpleReportDaoImpl.class.getMethod(methodName, Map.class);
		} catch (Throwable e) {
			logger
					.error(formatMsg(
							"Cannot find method: %1 for report: %2. Error message: %3.",
							methodName, reportName, e));
			return getErrorModelAndView();
		}

		try {
			// 传给报表的参数
			Map params = RequestUtil.getRequestMap(request);
			model = (Map) method.invoke(dao, params);
		} catch (Throwable e) {
			logger.error(formatMsg(
					"Build report: %1 data error. Error message: %2.",
					reportName, e));
			return getErrorModelAndView();
		}

		String viewName = "report/summary/" + reportName;
		return super.getModelAndView(viewName, model);
	}

	protected ModelAndView getErrorModelAndView() {
		String viewName = "report/summary/showReportError";
		return super.getModelAndView(viewName, null);
	}

	/**
	 * @param dao
	 *            the dao to set
	 */
	public void setSimpleReportDao(SimpleReportDaoImpl dao) {
		this.dao = dao;
	}

	@Override
	protected Map<Integer, Map<String, Object>> getMultiSaveModel(
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void initController() throws Exception {
		// TODO Auto-generated method stub
		
	}

}
