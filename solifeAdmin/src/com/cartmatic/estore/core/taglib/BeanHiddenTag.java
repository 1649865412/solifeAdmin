
package com.cartmatic.estore.core.taglib;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.Field;
import org.apache.commons.validator.Form;
import org.apache.commons.validator.ValidatorResources;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.Errors;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.RequestContext;
import org.springmodules.validation.commons.ValidatorFactory;

import com.cartmatic.estore.core.util.I18nUtil;


public class BeanHiddenTag extends TagSupport {
	private Object bean;
	private String id_Prefix;
	private String name_Prefix;
	private String excludeProperty;
	private String includeProperty;
	private boolean includeEmpty;
	

	protected boolean				colon		= false;

	protected String				errorClass	= null;

	protected String				key			= null;

	protected transient final Log	log			= LogFactory
														.getLog(BeanHiddenTag.class);

	protected RequestContext		requestContext;

	protected String				styleClass	= null;

	@Override
	public int doStartTag() throws JspException {
		Class clazz=bean.getClass();
		Method methods[] = clazz.getMethods();
		for (Method method : methods) {
			String methodName=method.getName();
			if(methodName.startsWith("get")){
				if((StringUtils.isNotEmpty(excludeProperty)&&excludeProperty.indexOf(methodName)==-1)||(StringUtils.isNotEmpty(includeProperty)&&includeProperty.indexOf(methodName)==-1)){
					
				}
			}
		}
		try {
			this.requestContext = new RequestContext(
					(HttpServletRequest) this.pageContext.getRequest());
		} catch (RuntimeException ex) {
			throw ex;
		} catch (Exception ex) {
			pageContext.getServletContext().log("Exception in custom tag", ex);
		}

		// Look up this key to see if its a field of the current form
		boolean requiredField = false;
		boolean validationError = false;

		ValidatorResources resources = getValidatorResources();

		// get the name of the bean from the key
		String formName = key.substring(0, key.indexOf('.'));
		String fieldName = key.substring(formName.length() + 1);

		if (resources != null) {
			Form form = resources.getForm(LocaleContextHolder.getLocale(),
					formName);

			if (form != null) {
				Field field = form.getField(fieldName);

				if (field != null) {
					if (field.isDependency("required")
							|| field.isDependency("validwhen")) {
						requiredField = true;
					}
				}
			}
		}

		Errors errors = requestContext.getErrors(formName, false);
		List fes = null;
		if (errors != null) {
			fes = errors.getFieldErrors(fieldName);
		}

		// Retrieve the message string we are looking for
		String message = I18nUtil.getInstance().getMessage(key);

		String cssClass = null;
		if (styleClass != null) {
			cssClass = styleClass;
		} else if (requiredField) {
			cssClass = "required";
		}

		String cssErrorClass = (errorClass != null) ? errorClass : "error";
		StringBuffer label = new StringBuffer();

		if ((message == null) || "".equals(message.trim())) {
			label.append("");
		} else {
			// label.append("<label for=\"").append(fieldName).append(
			// "\" onclick=\"showElementHelp(this,'").append(key).append(
			// "');\"");
			label.append("<label for=\"").append(fieldName).append("\"");

			if (validationError) {
				label.append(" class=\"").append(cssErrorClass).append("\"");
			} else if (cssClass != null) {
				label.append(" class=\"").append(cssClass).append("\"");
			}

			label.append(" >").append(message);
			// --------------------
			label.append((requiredField) ? " <span class=\"req\">(*)</span>"
					: "");
			label.append((colon) ? ":" : "");
			label.append("</label>");

			if (fes != null && fes.size() > 0) {
				label.append("<img class=\"validationWarning\" alt=\"");
				label.append(I18nUtil.getInstance().getMessage("icon.warning"));
				label.append("\"");

				String context = ((HttpServletRequest) pageContext.getRequest())
						.getContextPath();

				label.append("src=\"" + context);
				label.append(I18nUtil.getInstance().getMessage(
						"icon.warning.img"));
				label.append("\" />");
			}
		}

		// Print the retrieved message to our output writer
		try {
			writeMessage(label.toString());
		} catch (IOException io) {
			io.printStackTrace();
			throw new JspException("Error writing label: " + io.getMessage());
		}

		// Continue processing this page
		return (SKIP_BODY);
	}

	/**
	 * Get the validator resources from a ValidatorFactory defined in the web
	 * application context or one of its parent contexts. The bean is resolved
	 * by type (org.springframework.validation.commons.ValidatorFactory).
	 * 
	 * @return ValidatorResources from a ValidatorFactory.
	 */
	private ValidatorResources getValidatorResources() {
		// look in servlet beans definition (i.e. action-servlet.xml)
		WebApplicationContext ctx = (WebApplicationContext) pageContext
				.getRequest().getAttribute(
						DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE);
		ValidatorFactory factory = null;
		try {
			factory = (ValidatorFactory) BeanFactoryUtils
					.beanOfTypeIncludingAncestors(ctx, ValidatorFactory.class,
							true, true);
		} catch (NoSuchBeanDefinitionException e) {
			// look in main application context (i.e. applicationContext.xml)
			ctx = WebApplicationContextUtils
					.getRequiredWebApplicationContext(pageContext
							.getServletContext());
			factory = (ValidatorFactory) BeanFactoryUtils
					.beanOfTypeIncludingAncestors(ctx, ValidatorFactory.class,
							true, true);
		}
		return factory.getValidatorResources();
	}

	/**
	 * Release all allocated resources.
	 */
	@Override
	public void release() {
		super.release();
		key = null;
		colon = false;
		styleClass = null;
		errorClass = null;
	}

	/**
	 * Setter for specifying whether to include colon
	 * 
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setColon(boolean colon) {
		this.colon = colon;
	}

	/**
	 * Setter for assigning a CSS class when errors occur, defaults to
	 * labelError.
	 * 
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setErrorClass(String errorClass) {
		this.errorClass = errorClass;
	}

	/**
	 * @jsp.attribute required="true" rtexprvalue="true"
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * Setter for assigning a CSS class, default is label.
	 * 
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	/**
	 * Write the message to the page.
	 * 
	 * @param msg
	 *            the message to write
	 * @throws IOException
	 *             if writing failed
	 */
	protected void writeMessage(String msg) throws IOException {
		pageContext.getOut().write(msg);
	}

}
