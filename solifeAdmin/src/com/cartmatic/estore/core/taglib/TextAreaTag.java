/*
 * Created on Nov 22, 2007
 * 
 */

package com.cartmatic.estore.core.taglib;

import javax.servlet.jsp.JspException;

import org.springframework.web.servlet.tags.form.TagWriter;

/**
 * 已知问题，加上HtmlEditor后实时的验证失效，原因是Ext的HtmlEditor事件不处理
 * 
 * @author Ryan
 * 
 */
public class TextAreaTag extends AbstractInputTag {
	public static final String	COLS_ATTRIBUTE		= "cols";

	public static final String	ONSELECT_ATTRIBUTE	= "onselect";

	public static final String	READONLY_ATTRIBUTE	= "readonly";

	public static final String	ROWS_ATTRIBUTE		= "rows";

	private String				cols;

	private String				onselect;

	private String				rows;

	/**
	 * Get the value of the '<code>cols</code>' attribute.
	 */
	protected String getCols() {
		return this.cols;
	}

	/**
	 * Get the value of the '<code>onselect</code>' attribute.
	 */
	protected String getOnselect() {
		return this.onselect;
	}

	/**
	 * Get the value of the '<code>rows</code>' attribute.
	 */
	protected String getRows() {
		return this.rows;
	}

	/**
	 * Set the value of the '<code>cols</code>' attribute. May be a runtime
	 * expression.
	 */
	public void setCols(String cols) {
		this.cols = cols;
	}

	/**
	 * Set the value of the '<code>onselect</code>' attribute. May be a
	 * runtime expression.
	 */
	public void setOnselect(String onselect) {
		this.onselect = onselect;
	}

	/**
	 * Set the value of the '<code>rows</code>' attribute. May be a runtime
	 * expression.
	 */
	public void setRows(String rows) {
		this.rows = rows;
	}

	@Override
	protected void writeInputContent(TagWriter tagWriter) throws JspException {
		tagWriter.startTag("textarea");
		if (isAutoValidate() || getValidConf() != null) {
			writeOptionalAttribute(tagWriter, ATTR_VALID_CONF, getValidConf());
			setOnblur("validateEventHandler();");
		}

		writeDefaultAttributes(tagWriter);
		writeOptionalAttribute(tagWriter, ROWS_ATTRIBUTE, getRows());
		writeOptionalAttribute(tagWriter, COLS_ATTRIBUTE, getCols());
		writeOptionalAttribute(tagWriter, ONSELECT_ATTRIBUTE, getOnselect());
		tagWriter.appendValue(getDisplayString(getBoundValue(),
				getPropertyEditor()));
		tagWriter.endTag();
	}
}
