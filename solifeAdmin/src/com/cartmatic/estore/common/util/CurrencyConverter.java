
package com.cartmatic.estore.common.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cartmatic.estore.common.helper.ConfigUtil;

/**
 * This class is converts a Double to a double-digit String (and vise-versa) by
 * BeanUtils when copying properties. Registered for use in BaseAction.
 * 
 * <p>
 * <a href="CurrencyConverter.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public class CurrencyConverter implements Converter {
	protected final DecimalFormat	formatter	= new DecimalFormat("###,##0.00");
	protected final Log				log			= LogFactory.getLog(CurrencyConverter.class);

	/**
	 * Convert a String to a Double and a Double to a String
	 * 
	 * @param type
	 *            the class type to output
	 * @param value
	 *            the object to convert
	 * @return object the converted object (Double or String)
	 */
	public final Object convert(final Class type, final Object value) {
		// for a null value, return null
		if (value == null) {
			return formatter.format((double) 0);
		} else {
			if (value instanceof String) {
				if (log.isDebugEnabled()) {
					log.debug("value (" + value + ") instance of String");
				}

				try {
					if (StringUtils.isBlank(String.valueOf(value))) {
						return formatter.format((double) 0);
					}

					if (log.isDebugEnabled()) {
						log.debug("converting '" + value + "' to a decimal");
					}

					// formatter.setDecimalSeparatorAlwaysShown(true);
					Number num = formatter.parse(String.valueOf(value));

					return new Double(num.doubleValue());
				} catch (ParseException pe) {
					pe.printStackTrace();
				}
			} else if (value instanceof Double) {
				if (log.isDebugEnabled()) {
					log.debug("value (" + value + ") instance of Double");
					log.debug("returning double: " + formatter.format(value));
				}

				return formatter.format(value);
			} else if (value instanceof java.math.BigDecimal) {
				return formatter.format(((java.math.BigDecimal) value)
						.doubleValue());
			}
		}

		throw new ConversionException("Could not convert " + value + " to "
				+ type.getName() + "!");
	}

	/**
	 * 专为邮件模板提供
	 * @param value
	 * @return
	 */
	public String formatBigDecimal(final java.math.BigDecimal value) {
		return ConfigUtil.getInstance().getDefaultCurrencySymbol() + this.convert(java.math.BigDecimal.class, value).toString();
	}
	
	
	public String formatBigDecimal(final java.math.BigDecimal value,String pattern) {
		NumberFormat nf=new DecimalFormat(pattern);
		return nf.format(value);
	}
}
