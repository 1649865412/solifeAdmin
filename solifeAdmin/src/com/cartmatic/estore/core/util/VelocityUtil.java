/**
 * 
 */

package com.cartmatic.estore.core.util;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.servlet.view.AbstractTemplateView;

import com.cartmatic.estore.core.view.MessageContext;

/**
 * @author Ryan
 * 
 */
public class VelocityUtil implements InitializingBean 
{
	
	protected final transient Log	logger	= LogFactory.getLog(getClass());

	private MessageContext			messageContext;

	private VelocityEngine			velocityEngine;

	/**
	 * 
	 */
	public VelocityUtil() {

	}

	public void afterPropertiesSet() throws Exception {
	}

	/**
	 * 支持模板包，获取Velocity模版的路径
	 * 
	 * @param templatePath
	 * @return
	 */
	private String getVelocityTemplatePath(String templatePath, String storeCode)
	{
		//if (templatePath.indexOf("share/") >= 0) {
		//	return templatePath;
		//}

		if (templatePath.indexOf("!") == 0) {
			return templatePath.substring(1);
		}

		return UrlUtil.formatUrl(
				new StringBuilder("/").append(storeCode).append("/email/").append(templatePath))
				.toString();
	}

	public String mergeTemplateIntoString(String templateLocation, Map<String, Object> model, String storeCode) throws VelocityException 
	{
		/**
		 * Set the same name as
		 * AbstractTemplateView.SPRING_MACRO_REQUEST_CONTEXT_ATTRIBUTE so can
		 * reuse spring.vm macros
		 */
		model.put(AbstractTemplateView.SPRING_MACRO_REQUEST_CONTEXT_ATTRIBUTE, messageContext);
		return VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, getVelocityTemplatePath(templateLocation, storeCode), model);
	}

	/*public String mergeTemplateIntoString(VelocityEngine velocityEngine, String templateLocation, Map model) throws VelocityException 
	{
		return VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, getVelocityTemplatePath(templateLocation), model);
	}*/

	public void setMessageContext(MessageContext messageContext) {
		this.messageContext = messageContext;
	}

	/**
	 * @param velocityEngine
	 *            the velocityEngine to set
	 */
	public void setVelocityEngine(VelocityEngine in_velocityEngine) {
		velocityEngine = in_velocityEngine;
	}
}
