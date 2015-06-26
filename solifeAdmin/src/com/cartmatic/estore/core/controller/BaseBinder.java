/*
 * create 2006-5-19
 * 
 * 
 */

package com.cartmatic.estore.core.controller;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.validation.BindingErrorProcessor;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.ServletRequestParameterPropertyValues;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import com.cartmatic.estore.common.util.DateUtil;

/**
 * @author Ryan
 * 
 * 
 */
public class BaseBinder {
	// Default number format for from.jsp. use for BigDecimal data type.
	public final static String		DEFAULT_BINDER_NUMBERFORMAT	= "#.##";

	private BindingErrorProcessor	bindingErrorProcessor;
	private boolean					hasErros					= false;

	private MessageCodesResolver	messageCodesResolver;
	private Map						model;
	private Validator				validater;

	/**
	 * 
	 * @param request
	 * @param command
	 * @param commandName
	 */
	public void bind(HttpServletRequest request, Object command,
			String commandName) {
		ServletRequestDataBinder binder = createBinder(request, command,
				commandName);
		binder.bind(request);
		validate(command, binder);
		if (binder.getBindingResult().hasErrors()) {
			hasErros = true;
		}
		if (model != null)
			model.putAll(binder.getBindingResult().getModel());
		else
			model = binder.getBindingResult().getModel();
	}

	/**
	 * When bind a command from request and use the prefix to partition
	 * difference VO.<br/> Html code:&lt;input type=&quot;hidden&quot;
	 * name=&quot;prefix_promotionId&quot; &gt;<br/> Only for bind object from
	 * request.
	 * 
	 * @param request
	 * @param command
	 * @param commandName
	 * @param prefix
	 */
	public void bind(HttpServletRequest request, Object command,
			String commandName, String prefix) {
		ServletRequestDataBinder binder = createBinder(request, command,
				commandName);
		MutablePropertyValues mpvs = new ServletRequestParameterPropertyValues(
				request, prefix);
		binder.bind(mpvs);
		validate(command, binder);
		if (binder.getBindingResult().hasErrors()) {
			hasErros = true;
		}
		if (model != null)
			model.putAll(binder.getBindingResult().getModel());
		else
			model = binder.getBindingResult().getModel();
	}

	/**
	 * Create a binder.
	 * 
	 * @param request
	 * @param command
	 * @param commandName
	 * @return
	 * @throws Exception
	 */
	protected ServletRequestDataBinder createBinder(HttpServletRequest request,
			Object command, String commandName) {
		ServletRequestDataBinder binder = new ServletRequestDataBinder(command,
				commandName);
		if (this.messageCodesResolver != null) {
			binder.setMessageCodesResolver(this.messageCodesResolver);
		}
		if (this.bindingErrorProcessor != null) {
			binder.setBindingErrorProcessor(this.bindingErrorProcessor);
		}
		initBinder(request, binder);
		return binder;
	}

	public Map getModel() {
		return model;
	}

	public boolean hasErrors() {
		return hasErros;
	}

	/**
	 * set the Editors into binder
	 * 
	 * @param binder
	 */
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) {
		NumberFormat nf = NumberFormat.getNumberInstance();
		SimpleDateFormat dtf = new SimpleDateFormat(DateUtil.getDatePattern());
		dtf.setLenient(false);
		// NumberFormat dcf = DecimalFormat.getNumberInstance();
		NumberFormat dcf = new DecimalFormat(DEFAULT_BINDER_NUMBERFORMAT);
		binder.registerCustomEditor(BigDecimal.class, null,
				new CustomNumberEditor(BigDecimal.class, dcf, true));
		binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dtf,
				true));
		binder.registerCustomEditor(Integer.class, null,
				new CustomNumberEditor(Integer.class, nf, true));
		binder.registerCustomEditor(Long.class, null, new CustomNumberEditor(
				Long.class, nf, true));
		binder.registerCustomEditor(byte[].class,
				new ByteArrayMultipartFileEditor());
	}

	public void setBindingErrorProcessor(
			BindingErrorProcessor bindingErrorProcessor) {
		this.bindingErrorProcessor = bindingErrorProcessor;
	}

	public void setMessageCodesResolver(
			MessageCodesResolver messageCodesResolver) {
		this.messageCodesResolver = messageCodesResolver;
	}

	public void setValidater(Validator validater) {
		this.validater = validater;
	}

	/**
	 * validate by validater
	 * 
	 * @param command
	 * @param binder
	 */
	private void validate(Object command, ServletRequestDataBinder binder) {
		if (validater != null) {
			ValidationUtils.invokeValidator(validater, command, binder
					.getBindingResult());
		}
	}
}
