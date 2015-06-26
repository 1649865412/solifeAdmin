
package com.cartmatic.estore.core.taglib;

import java.util.Iterator;

import javax.servlet.jsp.JspException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.Field;
import org.apache.commons.validator.Form;
import org.apache.commons.validator.ValidatorResources;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.servlet.tags.form.AbstractHtmlInputElementTag;
import org.springframework.web.servlet.tags.form.TagWriter;
import org.springmodules.validation.commons.ValidatorFactory;

import com.cartmatic.estore.core.util.ContextUtil;
import com.cartmatic.estore.core.util.I18nUtil;
import com.cartmatic.estore.core.util.StringUtil;

/**
 * 提供一个给多个TAG共享的基类
 */
public abstract class AbstractInputTag extends AbstractHtmlInputElementTag {

	protected static final String	ATTR_VALID_CONF	= "validConf";

	protected static final String	ERR_CLASSNAME	= "errHilite";

	private boolean					addColonToLabel	= true;

	private boolean					autoValidate	= true;

	private boolean					autoWrap		= true;

	private Field					field			= null;

	private String					labelKey		= null;

	protected transient final Log	logger			= LogFactory
															.getLog(getClass());

	private boolean					showErrors		= true;

	private String					validConf		= null;

	protected void cleanup() {
		addColonToLabel = true;
		autoValidate = true;
		autoWrap = true;
		field = null;
		labelKey = null;
		showErrors = true;
		validConf = null;
	}

	@Override
	public void doFinally() {
		super.doFinally();
	}

	protected String getBindPath() throws JspException {
		return getBindStatus().getPath();
	}

	protected Field getField() throws JspException {
		if (field == null) {
			Form form = getForm();
			if (form != null) {
				field = form.getField(getPath());
			}
		}
		return field;
	}

	protected Form getForm() {
		Form form = null;
		String formName = getFormName();
		if (formName != null) {
			form = getValidatorResources().getForm(
					LocaleContextHolder.getLocale(), formName);
		}
		return form;
	}

	/**
	 * 取得Form的名称，也是commandName
	 * 
	 * @return
	 */
	protected String getFormName() {
		return (String) pageContext.getRequest().getAttribute(
				super.COMMAND_NAME_VARIABLE_NAME);
	}

	public String getLabelKey() {
		return labelKey;
	}

	/**
	 * Get the validator resources from a ValidatorFactory defined in spring
	 * context
	 * 
	 * @return ValidatorResources from a ValidatorFactory.
	 */
	private ValidatorResources getValidatorResources() {
		ValidatorFactory factory = (ValidatorFactory) ContextUtil
				.getSpringBeanById("validatorFactory");
		return factory.getValidatorResources();
	}

	/**
	 * 输出该字段的验证配置（如果有）。注意：本方法有较大的限制。包括：JS的验证框架与Commons Validator的必须兼容，
	 * 对复杂的需要参数的类型，其参数名只能是类型名（例如minlength类型的参数只能是${var:minlength}）。对mask
	 * 等复杂类型还不能支持（因为正则表达式实在太多可能性了）。
	 * 
	 * @return
	 * @throws JspException
	 */
	protected String getValidConf() throws JspException {
		if (validConf == null) {
			validConf = "";
			Field field = getField();
			if (field != null) {
				for (Iterator<String> iter = field.getDependencyList()
						.iterator(); iter.hasNext();) {
					String conf = iter.next();
					if (validConf.length() > 0) {
						validConf += ",";
					}
					validConf += conf;
					if ("maxlength".equals(conf) || "minlength".equals(conf)) {
						String arg = field.getVarValue(conf);
						if (arg != null) {
							validConf = validConf + "=" + arg;
						}
					}
				}
			}
		}
		return validConf;
	}

	public boolean isAddColonToLabel() {
		return addColonToLabel;
	}

	public boolean isAutoValidate() {
		return autoValidate;
	}

	public boolean isAutoWrap() {
		return autoWrap;
	}

	protected boolean isRequired() throws JspException {
		return getField() != null && getField().isDependency("required");
	}

	public boolean isShowErrors() {
		return showErrors;
	}

	/**
	 * 是否在Label添加冒号；缺省true。
	 * 
	 * @param addColonToLabel
	 */
	public void setAddColonToLabel(boolean addColonToLabel) {
		this.addColonToLabel = addColonToLabel;
	}

	/**
	 * 是否自动设置验证信息，如果设置了validConf，即使设置了false也会输出验证配置；缺省true。
	 * 
	 * @param autoValidate
	 */
	public void setAutoValidate(boolean autoValidate) {
		this.autoValidate = autoValidate;
	}

	/**
	 * 是否输出输入控件外层的容器（td，tr等）以及label等；缺省true。
	 * 
	 * @param autoWrap
	 */
	public void setAutoWrap(boolean autoWrap) {
		this.autoWrap = autoWrap;
	}

	/**
	 * 设置用于输出位于label内的I18N的属性名的key，缺省是使用commandName.path
	 * 
	 * @param labelKey
	 */
	public void setLabelKey(String labelKey) {
		this.labelKey = labelKey;
	}

	/**
	 * 设置是否显示错误信息，该信息与客户端JS验证的效果一致。
	 * 
	 * @param showErrors
	 */
	public void setShowErrors(boolean showErrors) {
		this.showErrors = showErrors;
	}

	/**
	 * 设置验证配置，缺省会使用validation.xml里面的配置（当autoValidate=true时）
	 * 
	 * @param validConf
	 */
	public void setValidConf(String validConf) {
		this.validConf = validConf;
	}

	/**
	 * 输出错误提示信息，兼容客户端JS验证的效果。
	 * 
	 * @param tagWriter
	 * @throws JspException
	 */
	protected void writeErrorContent(TagWriter tagWriter) throws JspException {
		StringBuilder sb = new StringBuilder();
		String[] errorMessages = getBindStatus().getErrorMessages();
		for (int i = 0; i < errorMessages.length; i++) {
			String errorMessage = errorMessages[i];
			if (i > 0) {
				sb.append("<br/>");
			}
			sb.append(errorMessage);
		}
		String fieldName = getName();
		if (fieldName == null) {
			fieldName = getId();
		}
		String imgHtmlTempl = "<img id=\"$1\" src=\"$2/images/icon_input_error.gif\" width=\"16\" height=\"15\" vaMsg=\"$3\" onmouseover=\"__fnShowVaErrMsg();\" onmouseout=\"__fnHideVaErrMsg();\">";
		tagWriter.appendValue(StringUtil.formatNoBracket(imgHtmlTempl,
				"v_err_msg_" + fieldName, getRequestContext().getContextPath(),
				sb).toString());
	}

	protected abstract void writeInputContent(TagWriter tagWriter)
			throws JspException;

	@Override
	final protected int writeTagContent(TagWriter tagWriter)
			throws JspException {
		if (isAutoWrap()) {
			writeWrapStart(tagWriter);
		}
		writeInputContent(tagWriter);
		if (getBindStatus().isError() && isShowErrors()) {
			writeErrorContent(tagWriter);
		}
		if (isAutoWrap()) {
			writeWrapEnd(tagWriter);
		}
		cleanup();
		return EVAL_PAGE;
	}

	/**
	 * Writes the '<code>value</code>' attribute to the supplied
	 * {@link TagWriter}. Subclasses may choose to override this implementation
	 * to control exactly when the value is written.
	 */
	protected void writeValue(TagWriter tagWriter) throws JspException {
		tagWriter.writeAttribute("value", getDisplayString(getBoundValue(),
				getPropertyEditor()));
	}

	protected void writeWrapEnd(TagWriter tagWriter) throws JspException {
		tagWriter.endTag();
		tagWriter.endTag();

	}

	protected void writeWrapStart(TagWriter tagWriter) throws JspException {
		tagWriter.startTag("tr");
		tagWriter.startTag("th");

		tagWriter.startTag("label");
		String labelClasses = "";
		boolean isRequired = isRequired();
		if (isRequired) {
			labelClasses += "required";
		}
		if (getBindStatus().isError() && isShowErrors()) {
			labelClasses = labelClasses
					+ (labelClasses.length() == 0 ? "" : ",") + ERR_CLASSNAME;
		}
		tagWriter.writeOptionalAttributeValue("class", labelClasses);
		// tagWriter.writeAttribute("onclick", "showElementHelp(this,'"
		// + getBindPath() + "')");
		getBindStatus().getPath();
		tagWriter.writeAttribute("for", getPath());
		tagWriter.appendValue(I18nUtil.getInstance().getMessage(
				getLabelKey() == null ? getBindPath() : getLabelKey()));
		if (isRequired) {
			tagWriter.appendValue("(*)");
		}
		tagWriter.appendValue(isAddColonToLabel() ? ":" : "");
		tagWriter.endTag();

		tagWriter.endTag();

		tagWriter.startTag("td");
	}
}
