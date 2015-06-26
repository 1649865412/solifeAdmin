/*
 * Created on Dec 29, 2004
 * 
 */

package com.cartmatic.estore.core.decorator;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.displaytag.decorator.DisplaytagColumnDecorator;
import org.displaytag.exception.DecoratorException;
import org.displaytag.properties.MediaTypeEnum;

import com.cartmatic.estore.common.util.DateUtil;

/**
 * @author chris
 * 
 */
public class TblColumnDecorator implements DisplaytagColumnDecorator {
	private final static Log	logger		= LogFactory
													.getLog(TblColumnDecorator.class);

	private SimpleDateFormat	datefmt		= null;

	private DecimalFormat		moneyfmt	= null;

	/**
	 * 
	 */
	public TblColumnDecorator() {
		super();
		this.datefmt = new SimpleDateFormat(DateUtil.getDatePattern());
		this.moneyfmt = new DecimalFormat("##,###,###,###.00");

	}

	/**
	 * 
	 * @see org.displaytag.decorator.DisplaytagColumnDecorator#decorate(java.lang.Object,
	 *      javax.servlet.jsp.PageContext,
	 *      org.displaytag.properties.MediaTypeEnum)
	 */
	public Object decorate(java.lang.Object columnValue,
			javax.servlet.jsp.PageContext pageContext, MediaTypeEnum media)
			throws DecoratorException {
		if (columnValue == null || columnValue.equals("")) {
			return "html".equals(media.getName()) ? "&nbsp;" : "";
		}
		if (columnValue instanceof BigDecimal || columnValue instanceof Double
				|| columnValue instanceof Float) {
			return getMoney(columnValue);
		} else if (columnValue instanceof Date) {
			return getDate(columnValue);
		}

		String strValue = columnValue.toString();
		if (!"html".equals(media.getName())) {
			return parseHtmlValue(strValue);
		}
		return strValue;
	}

	/**
	 * Returns the date as a String in MM/dd/yy format
	 */

	public String getDate(Object anObject) {
		return this.datefmt.format(anObject);
	}

	/**
	 * Returns the decimal as a String in ##,###,###,###.00 format
	 */

	public String getMoney(Object anObject) {
		return this.moneyfmt.format(anObject);
	}

	/**
	 * <P>
	 * Use for export only.
	 * </P>
	 * <P>
	 * Note, can parse very simple, very standard html only. Only support lower
	 * case html tag for now.
	 * </P>
	 * 
	 * @param htmlStr
	 * @return
	 */
	protected String parseHtmlValue(String htmlStr) {
		String str = StringUtils.stripToEmpty(htmlStr);
		if ("&nbsp;".equals(str)) {
			return "";
		}
		if (!str.startsWith("<")) {
			// is no more a html tag
			return str;
		}
		if (str.startsWith("<input ")) {
			int idxOfEndTag = str.indexOf("/>");
			if (str.indexOf("hidden") < idxOfEndTag) {
				// is a hidden input, skip, try the remained part
				return parseHtmlValue(str.substring(idxOfEndTag + 2));
			}
			// If is a checkbox
			if (str.indexOf("checkbox") < idxOfEndTag) {
				// If is checked, try the remained part
				if (str.indexOf("checked") < idxOfEndTag) {
					return parseHtmlValue(str.substring(idxOfEndTag + 2));
				}
				// Not checked return empty string.
				return "";
			}
			// is a normal input, locate value and return
			int idxOfValueStart = str.indexOf("value=", str.indexOf("<input "));
			if (idxOfValueStart > 0 && idxOfValueStart < idxOfEndTag) {
				// size of value= is 6
				String mark = str.substring(idxOfValueStart + 6,
						idxOfValueStart + 7);
				int idxOfValueEnd = str.indexOf(mark, idxOfValueStart + 7);
				// no more tag need to be parsed
				return str.substring(idxOfValueStart + 7, idxOfValueEnd);
			}
		}

		if (str.startsWith("<label ")) {
			int idxOfEndTag = str.indexOf("</label>");
			int firstTagEnd = str.indexOf(">");
			if (idxOfEndTag > 0) {
				String subStr = str.substring(firstTagEnd + 1, idxOfEndTag);
				return parseHtmlValue(subStr);
			}
		}

		if (str.startsWith("<font ")) {
			int idxOfEndTag = str.indexOf("</font>");
			int firstTagEnd = str.indexOf(">");
			if (idxOfEndTag > 0) {
				String subStr = str.substring(firstTagEnd + 1, idxOfEndTag);
				return parseHtmlValue(subStr);
			}
		}

		if (str.startsWith("<a ")) {
			int idxOfEndTag = str.indexOf("</a>");
			int firstTagEnd = str.indexOf(">");
			if (idxOfEndTag > 0) {
				String subStr = str.substring(firstTagEnd + 1, idxOfEndTag);
				return parseHtmlValue(subStr);
			}
		}

		if (str.startsWith("<span ")) {
			int idxOfEndTag = str.indexOf("</span>");
			int firstTagEnd = str.indexOf(">");
			if (idxOfEndTag > 0) {
				String subStr = str.substring(firstTagEnd + 1, idxOfEndTag);
				return parseHtmlValue(subStr);
			}
		}

		if (str.startsWith("<select ")) {
			int idxOfEndTag = str.indexOf("</select>");
			if (idxOfEndTag > 0) {
				int idxOfSelectedStart = str.lastIndexOf("selected");
				if (idxOfSelectedStart > 0) {
					int idxOfSelectedStart2 = str.indexOf(">",
							idxOfSelectedStart);
					int idxOfSelectedEnd = str.indexOf("</option>",
							idxOfSelectedStart2);
					return parseHtmlValue(str.substring(
							idxOfSelectedStart2 + 1, idxOfSelectedEnd));
				}
			}
		}

		// if goes here, indicates something is not parsed correctly.
		logger.debug("Something not parsable: " + str);
		return str;
	}

	protected String parseHtmlValue2(String htmlStr) {
		if (!htmlStr.startsWith("<")) {
			return htmlStr;
		}
		String parsedValue = null;

		return "";
	}
}