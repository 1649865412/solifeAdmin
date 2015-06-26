/*
 * Created on Dec 29, 2004
 * 
 */

package com.cartmatic.estore.core.decorator;

import org.displaytag.decorator.DisplaytagColumnDecorator;
import org.displaytag.exception.DecoratorException;
import org.displaytag.properties.MediaTypeEnum;

import com.cartmatic.estore.core.util.I18nUtil;

/**
 * 
 */
public class AuditInfoTblColumnDecorator implements DisplaytagColumnDecorator {
	public Object decorate(java.lang.Object columnValue,
			javax.servlet.jsp.PageContext pageContext, MediaTypeEnum media)
			throws DecoratorException {
		if (columnValue == null || "".equals(columnValue)) {
			return "";
		}

		String strValue = I18nUtil.getInstance().evalMessage(
				columnValue.toString());

		// generate html code
		StringBuilder htmlBuf = new StringBuilder();
		htmlBuf.append("<span title=\"").append(strValue).append("\">");
		if (strValue.length() > 64) {
			htmlBuf.append(strValue.substring(0, 64)).append("...");
		} else {
			htmlBuf.append(strValue);
		}
		htmlBuf.append("</span>");
		return htmlBuf.toString();
	}
}