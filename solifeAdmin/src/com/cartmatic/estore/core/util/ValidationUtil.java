
package com.cartmatic.estore.core.util;

import java.util.Date;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.Field;
import org.apache.commons.validator.GenericValidator;
import org.apache.commons.validator.ValidatorAction;
import org.apache.commons.validator.util.ValidatorUtils;
import org.springframework.validation.Errors;
import org.springmodules.validation.commons.FieldChecks;

/**
 * ValidationUtil Helper class for performing custom validations that aren't
 * already included in the core Commons Validator.
 * <p>
 * <a href="ValidationUtil.java.html"> <i>View Source </i> </a>
 * </p>
 * 
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible </a>
 */
public class ValidationUtil {
	// ~ Methods
	// ================================================================

	private static final Log	logger	= LogFactory.getLog(FieldChecks.class);

	/**
	 * Extracts the value of the given bean. If the bean is <code>null</code>,
	 * the returned value is also <code>null</code>. If the bean is a
	 * <code>String</code> then the bean itself is returned. In all other
	 * cases, the <code>ValidatorUtils</code> class is used to extract the
	 * bean value using the <code>Field</code> object supplied.
	 * 
	 * @see ValidatorUtils#getValueAsString(Object, String)
	 */
	protected static String extractValue(Object bean, Field field) {
		String value = null;

		if (bean == null) {
			return null;
		} else if (bean instanceof String) {
			value = (String) bean;
		} else {
			value = ValidatorUtils.getValueAsString(bean, field.getProperty());
		}

		return value;
	}

	/**
	 * Checks if the field is a valid code string. A word character:
	 * [a-zA-Z_0-9]
	 * 
	 * @param bean
	 *            The bean validation is being performed on.
	 * @param va
	 *            The <code>ValidatorAction</code> that is currently being
	 *            performed.
	 * @param field
	 *            The <code>Field</code> object associated with the current
	 *            field being validated.
	 * @param errors
	 *            The <code>Errors</code> object to add errors to if any
	 *            validation errors occur. -param request Current request
	 *            object.
	 * @return A Date if valid, a null if blank or invalid.
	 */
	public static boolean validateCode(Object bean, ValidatorAction va,
			Field field, Errors errors) {
		String value = ValidationUtil.extractValue(bean, field);
		//简单处理使其支持中划线
		//value=value.replace("-","_");
		String regex = "[\\w-#]*";
		if (value != null && (!Pattern.matches(regex, value))) {
			FieldChecks.rejectValue(errors, field, va);
			return false;
		}

		return true;
	}

	/**
	 * Checks if the field is a valid date. If the field has a datePattern
	 * variable, that will be used to format
	 * <code>java.text.SimpleDateFormat</code>. If the field has a
	 * datePatternStrict variable, that will be used to format
	 * <code>java.text.SimpleDateFormat</code> and the length will be checked
	 * so '2/12/1999' will not pass validation with the format 'MM/dd/yyyy'
	 * because the month isn't two digits. If no datePattern variable is
	 * specified, then the field gets the DateFormat.SHORT format for the
	 * locale. The setLenient method is set to <code>false</code> for all
	 * variations.
	 * 
	 * @param bean
	 *            The bean validation is being performed on.
	 * @param va
	 *            The <code>ValidatorAction</code> that is currently being
	 *            performed.
	 * @param field
	 *            The <code>Field</code> object associated with the current
	 *            field being validated.
	 * @param errors
	 *            The <code>Errors</code> object to add errors to if any
	 *            validation errors occur. -param request Current request
	 *            object.
	 * @return A Date if valid, a null if blank or invalid.
	 */
	public static Date validateDate(Object bean, ValidatorAction va,
			Field field, Errors errors) {

		return null;
	}

	public static boolean validateDouble4(Object bean, ValidatorAction va,
			Field field, Errors errors) {
		String value = ValidationUtil.extractValue(bean, field);
		String regex = "^\\d{0,10}\\.?\\d{0,4}$";
		if (value != null&&!Pattern.matches(regex, value)) {
			FieldChecks.rejectValue(errors, field, va);
			return false;
		}

		return true;
	}

	/**
	 * 只是简单检查是否含有HTML标记
	 * 
	 * @param bean
	 * @param va
	 * @param field
	 * @param errors
	 * @return
	 */
	public static boolean validateNoHtml(Object bean, ValidatorAction va,
			Field field, Errors errors) {

		String value = ValidationUtil.extractValue(bean, field);
		if (value != null
				&& (value.indexOf("<") != -1 || value.indexOf(">") != -1)) {
			return false;
		}

		// String regex = ".*<(\\w+)\\b[^>]*>.*?</\\1>.*|.*<(\\w+)\\b[^>]*/>.*";
		//
		// if (Pattern.matches(regex, value)) {
		// FieldChecks.rejectValue(errors, field, va);
		// return false;
		// }

		return true;
	}

	public static boolean validatePositiveInteger(Object bean,
			ValidatorAction va, Field field, Errors errors) {
		String value = ValidationUtil.extractValue(bean, field);
		String regex = "^\\+?[1-9]\\d*$";
		if (value != null && !Pattern.matches(regex, value)) {
			FieldChecks.rejectValue(errors, field, va);
			return false;
		}

		return true;
	}

	public static boolean validatePrice(Object bean, ValidatorAction va,
			Field field, Errors errors) {
		String value = ValidationUtil.extractValue(bean, field);
		String regex = "^\\d{0,10}\\.?\\d{0,2}$";
		if (value != null && !Pattern.matches(regex, value)) {
			FieldChecks.rejectValue(errors, field, va);
			return false;
		}

		return true;
	}

	/**
	 * 简单的检查是否有非法字符。
	 * 
	 * @param bean
	 * @param va
	 * @param field
	 * @param errors
	 * @return
	 */
	public static boolean validateText(Object bean, ValidatorAction va,
			Field field, Errors errors) {
		String value = ValidationUtil.extractValue(bean, field);
		String regex = ".*\\p{Punct}.*";

		if (Pattern.matches(regex, value)) {
			FieldChecks.rejectValue(errors, field, va);
			return false;
		}

		return true;
	}

	/**
	 * Validates that two fields match.
	 * 
	 * @param bean
	 * @param va
	 * @param field
	 * @param errors
	 */
	public static boolean validateTwoFields(Object bean, ValidatorAction va,
			Field field, Errors errors) {
		String value = ValidatorUtils.getValueAsString(bean, field
				.getProperty());
		String sProperty2 = field.getVarValue("equalTo");
		String value2 = ValidatorUtils.getValueAsString(bean, sProperty2);
		if(value==null&&value2==null){
			return true;
		}else if(value!=null&&value2==null){
			return false;
		}else if(value==null&&value2!=null){
			return false;
		}
		try {
			if (!value.equals(value2)) {
				FieldChecks.rejectValue(errors, field, va);
				return false;
			}
		} catch (Exception e) {
			FieldChecks.rejectValue(errors, field, va);
			return false;
		}

		return true;
	}
}
