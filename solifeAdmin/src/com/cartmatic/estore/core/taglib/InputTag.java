/*
 * Created on Nov 22, 2007
 * 
 */

package com.cartmatic.estore.core.taglib;

import javax.servlet.jsp.JspException;

import org.springframework.web.servlet.tags.form.TagWriter;

/**
 * <p>
 * 主要功能：显示输入框、属性名（包括点击显示元素的帮助信息）、是否必须标记(*)、是否有错误、绑定输入验证配置、如果没有设置自动判断maxlength和调整size、CSS等。
 * 相当于label+input+errors+validator+外层的包装，并可控制每个部分是否使用（labelKey、validConf、size、maxlength等）。
 * <p>
 * 最常用的用法（label,input等都使用相同的css设置）：
 * 
 * <pre>
 * &lt;form:form commandName=&quot;appMessage&quot; ... &gt;
 * &lt;table ... &gt;
 * &lt;tag:input path=&quot;subject&quot;/&gt;
 * </pre>
 * 
 * @jsp.tag name="input" bodycontent="empty"
 * 
 * @author Ryan
 * 
 */
public class InputTag extends AbstractInputTag {
	public static final String	ALT_ATTRIBUTE			= "alt";

	public static final String	AUTOCOMPLETE_ATTRIBUTE	= "autocomplete";
	public static final String	MAXLENGTH_ATTRIBUTE		= "maxlength";

	public static final String	ONSELECT_ATTRIBUTE		= "onselect";

	public static final String	READONLY_ATTRIBUTE		= "readonly";

	private static final long	serialVersionUID		= -2724266210280641402L;

	public static final String	SIZE_ATTRIBUTE			= "size";

	private String				alt;

	private String				autocomplete;

	private String				maxlength;

	private String				onselect;

	private String				size;

	/**
	 * Get the value of the '<code>alt</code>' attribute.
	 */
	protected String getAlt() {
		return this.alt;
	}

	/**
	 * Get the value of the '<code>autocomplete</code>' attribute.
	 */
	protected String getAutocomplete() {
		return this.autocomplete;
	}

	/**
	 * 覆盖以提供缺省设置：从validation.xml读最大长度
	 */
	protected String getMaxlength() {
		if (maxlength == null) {
			try {
				if (getField() != null) {
					maxlength = getField().getVarValue("maxlength");
				}
			} catch (JspException e) {
				e.printStackTrace();
			}
		}
		return maxlength;
	}

	/**
	 * Get the value of the '<code>onselect</code>' attribute.
	 */
	protected String getOnselect() {
		return this.onselect;
	}

	/**
	 * 覆盖以提供缺省设置：根据maxlength自动调整比较合适的size
	 */
	protected String getSize() {
		if (size == null && getMaxlength() != null) {
			int length = Integer.valueOf(getMaxlength());
			size = ""
					+ (length <= 16 ? 16 : (length <= 32 ? 32
							: (length <= 64 ? 48 : 64)));
		}
		return size;
	}

	/**
	 * Get the value of the '<code>type</code>' attribute. Subclasses can
	 * override this to change the type of '<code>input</code>' element
	 * rendered. Default value is '<code>text</code>'.
	 */
	protected String getType() {
		return "text";
	}

	/**
	 * Set the value of the '<code>alt</code>' attribute. May be a runtime
	 * expression.
	 */
	public void setAlt(String alt) {
		this.alt = alt;
	}

	/**
	 * Set the value of the '<code>autocomplete</code>' attribute. May be a
	 * runtime expression.
	 */
	public void setAutocomplete(String autocomplete) {
		this.autocomplete = autocomplete;
	}

	/**
	 * Set the value of the '<code>maxlength</code>' attribute. May be a
	 * runtime expression. 可选。缺省处理：从validation.xml读最大长度。
	 */
	public void setMaxlength(String maxlength) {
		this.maxlength = maxlength;
	}

	/**
	 * Set the value of the '<code>onselect</code>' attribute. May be a
	 * runtime expression.
	 */
	public void setOnselect(String onselect) {
		this.onselect = onselect;
	}

	/**
	 * Set the value of the '<code>size</code>' attribute. May be a runtime
	 * expression. 可选。缺省处理：根据maxlength自动调整比较合适的size。
	 */
	public void setSize(String size) {
		this.size = size;
	}

	/**
	 * 除输出Spring的InputTag的功能外，增加输出validConf、onblur事件、错误提示等。
	 * 
	 * @param tagWriter
	 * @throws JspException
	 */
	@Override
	protected void writeInputContent(TagWriter tagWriter) throws JspException {
		if (getCssClass() == null) {
			setCssClass(isReadonly() ? "form-inputbox-readonly"
					: "form-inputbox");
		}
		setCssErrorClass(ERR_CLASSNAME);

		tagWriter.startTag("input");

		if (isAutoValidate() || getValidConf() != null) {
			writeOptionalAttribute(tagWriter, ATTR_VALID_CONF, getValidConf());
			setOnblur("validateEventHandler();");
		}

		writeDefaultAttributes(tagWriter);
		tagWriter.writeAttribute("type", getType());
		writeValue(tagWriter);

		// custom optional attributes
		writeOptionalAttribute(tagWriter, SIZE_ATTRIBUTE, getSize());
		writeOptionalAttribute(tagWriter, MAXLENGTH_ATTRIBUTE, getMaxlength());
		writeOptionalAttribute(tagWriter, ALT_ATTRIBUTE, getAlt());
		writeOptionalAttribute(tagWriter, ONSELECT_ATTRIBUTE, getOnselect());
		writeOptionalAttribute(tagWriter, AUTOCOMPLETE_ATTRIBUTE,
				getAutocomplete());

		tagWriter.endTag();

	}

}
