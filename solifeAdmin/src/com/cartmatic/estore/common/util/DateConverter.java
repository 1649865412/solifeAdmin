
package com.cartmatic.estore.common.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang.StringUtils;


/**
 * This class is converts a java.util.Date to a String and a String to a
 * java.util.Date.
 * 
 * <p>
 * <a href="DateConverter.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public class DateConverter implements Converter {

	public Object convert(Class type, Object value) {
		if (value == null) {
			return null;
		} else if (type == Timestamp.class) {
			return convertToDate(type, value, DateUtil.getDateTimePattern());
		} else if (type == Date.class) {
			return convertToDate(type, value, DateUtil.getDatePattern());
		} else if (type == String.class) {
			return convertToString(type, value);
		}

		throw new ConversionException("Could not convert "
				+ value.getClass().getName() + " to " + type.getName());
	}

	public String convertDateToString(Date date) {
		if (date != null)
			return this.convertToString(String.class, date).toString();
		else
			return "";
	}
	
	public String convertDateToString(Date date,String pattern) {
		if (date != null)
			return this.convertToString(String.class, date, pattern).toString();
		else
			return "";
	}

	protected Object convertToDate(Class type, Object value, String pattern) {
		DateFormat df = new SimpleDateFormat(pattern);
		if (value instanceof String) {
			try {
				if (StringUtils.isEmpty(value.toString())) {
					return null;
				}

				Date date = df.parse((String) value);
				if (type.equals(Timestamp.class)) {
					return new Timestamp(date.getTime());
				}
				return date;
			} catch (Exception pe) {
				pe.printStackTrace();
				throw new ConversionException("Error converting String to Date");
			}
		}

		throw new ConversionException("Could not convert "
				+ value.getClass().getName() + " to " + type.getName());
	}

	protected Object convertToString(Class type, Object value) {

		if (value instanceof Date) {
			DateFormat df = new SimpleDateFormat(DateUtil.getDatePattern());
			if (value instanceof Timestamp) {
				df = new SimpleDateFormat(DateUtil.getDateTimePattern());
			}

			try {
				return df.format(value);
			} catch (Exception e) {
				e.printStackTrace();
				throw new ConversionException("Error converting Date to String");
			}
		} else {
			return value.toString();
		}
	}
	
	
	protected Object convertToString(Class type, Object value,String pattern) {

		if (value instanceof Date) {
			DateFormat df = new SimpleDateFormat(pattern);
			if (value instanceof Timestamp) {
				df = new SimpleDateFormat(pattern);
			}

			try {
				return df.format(value);
			} catch (Exception e) {
				e.printStackTrace();
				throw new ConversionException("Error converting Date to String");
			}
		} else {
			return value.toString();
		}
	}
}
