/**
 * 
 */

package com.cartmatic.estore.core.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

import com.cartmatic.estore.core.util.ContextUtil;
import com.cartmatic.estore.webapp.util.RequestContext;

/**
 * </li>
 * </ul>
 * 
 * @author Ryan
 * 
 */
public class BaseStoreFrontController extends BaseController {

	protected String	viewPrefix	= "/pages/";

	/**
	 * 简化版本， 适用于不支持动态配置模版并且不需要传入Model的时候用。需要保证templatePath存在。
	 * 
	 * @param templatePath
	 * @return
	 */
	protected ModelAndView getModelAndView(final String templatePath) {
		return getModelAndView(templatePath, null, null);
	}
	
	protected final String getMessage(String msgKey) {
		return super.getMessageSourceAccessor().getMessage(msgKey);
	}

		

	/**
	 * TODO,未完善，未测试，性能优化（如缓存）
	 * 
	 * @param templatePath
	 *            不能用/开头
	 * @param defaultTemplatePath
	 *            没有指定模版，或指定的模版找不到的时候，使用的缺省模版
	 * @param model
	 * @return
	 */
	protected ModelAndView getModelAndView(final String in_templatePath,
			final String defaultTemplatePath, final Map model) {
		String templatePath = getTemplatePath(defaultTemplatePath,
				in_templatePath);
		return templatePath == null ? null : new ModelAndView(templatePath,
				model);
	}

	/**
	 * TODO,未完善，未测试，性能优化（如缓存）
	 * 
	 * @param defaultTemplate
	 *            没有指定模版，或指定的模版找不到的时候，使用的缺省模版
	 * @param templatePath
	 * @return
	 */
	protected String getTemplatePath(String defaultTemplate, String templatePath) {
		// TODO 这个是否存在的检查应该缓存；另外，这样ViewResolver不再用检查是否存在了？
		if (empty(templatePath) || !isTemplateExists(templatePath)) {
			if (logger.isInfoEnabled()) {
				logger.info("Template not found for page: [" + templatePath
						+ "].");
			}
			return defaultTemplate;
		}
		return templatePath;
	}

	protected boolean isTemplateExists(String templatePath) {
		// return ContextUtil.getInstance().isFileExists(
		// new StringBuilder(viewPrefix).append(templatePath).append(
		// ".jsp").toString());
		return ContextUtil.getInstance().isFileExists(
				RequestContext.getRelativeTemplatePath(templatePath));
	}

	public void setViewPrefix(String viewPrefix) {
		this.viewPrefix = viewPrefix;
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
