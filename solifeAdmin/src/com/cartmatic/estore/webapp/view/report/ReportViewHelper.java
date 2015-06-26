/*
 * create 2006-9-21
 * 
 * 
 */

package com.cartmatic.estore.webapp.view.report;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cartmatic.estore.common.service.CategoryService;
import com.cartmatic.estore.common.util.DateUtil;
import com.cartmatic.estore.core.util.I18nUtil;

/**
 * @author Ryan
 * 
 * 
 */
public class ReportViewHelper {
	private CategoryService categoryService=null;
	private static ReportViewHelper	reportViewHelper	= null;
	public static final String		SCALE_DAY			= "day";
	public static final String		SCALE_MONTH			= "month";

	public static final String		SCALE_WEEK			= "week";

	public static ReportViewHelper getInstance() {
		if (reportViewHelper == null) {
			reportViewHelper = new ReportViewHelper();
		}
		return reportViewHelper;
	}


	private I18nUtil	i18nUtil	= I18nUtil.getInstance();

	protected final Log	logger		= LogFactory.getLog(getClass());

	private ReportViewHelper() {
	}

	public Date dateTime2date(Date date) {
		return DateUtil.convertDateTimeToDate(date);
	}

	/**
	 * Format Date
	 * 
	 * @param date
	 * @return
	 */
	public String formatDate(Date date) {
		return DateUtil.convertDateToString(date);
	}

	/**
	 * Format date by scale month:yyyy-MM week:xx day:reference formatDate()
	 * 
	 * @param date
	 * @param scale
	 * @return
	 */
	public String formatDateByScale(Date date, String scale) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		String rs = null;
		if (SCALE_DAY.equals(scale)) {
			rs = formatDate(date);
		} else if (SCALE_WEEK.equals(scale)) {
			rs = cal.get(Calendar.WEEK_OF_YEAR) + "";
		} else {
			rs = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1);
		}
		return rs;
	}

	// for dates
	/**
	 * For days
	 */
	public Date getEndOfThisDay() {
		return DateUtil.getEndOfThisDay();
	}

	public Date getEndOfThisMonth() {
		return DateUtil.getEndOfThisMonth();
	}

	public Date getEndOfThisMonth(String year, String month) {
		Date date = DateUtil.combineDateStart(year, month, "1");
		return DateUtil.getEndOfThisMonth(date);
	}

	public Date getEndOfThisWeek() {
		return DateUtil.getEndOfThisWeek();
	}

	public Date getEndOfThisYear() {
		return DateUtil.getEndOfThisYear();
	}

	/**
	 * For months
	 * 
	 * @return
	 */
	public Date getStartOfThisMonth() {
		return DateUtil.getStartOfThisMonth();
	}

	public Date getStartOfThisMonth(String year, String month) {
		Date date = DateUtil.combineDateStart(year, month, "1");
		return DateUtil.getStartOfThisMonth(date);
	}

	/**
	 * For week
	 */
	public Date getStartOfThisWeek() {
		return DateUtil.getStartOfThisWeek();
	}

	/**
	 * For years
	 * 
	 * @return
	 */
	public Date getStartOfThisYear() {
		return DateUtil.getStartOfThisYear();
	}

	/**
	 * Returns i18nText value by i18nKey
	 * 
	 * @param key
	 *            i18nkey
	 * @return
	 */
	public String i18n(String key) {
		if (StringUtils.isEmpty(key)) {
			return "";
		}
		return i18nUtil.getMessage(key);
	}

	/**
	 * Returns i18nText value by i18nKey and limit the length
	 * 
	 * @param key
	 *            i18nKey
	 * @param limit
	 *            limit the value length
	 * @return
	 */
	public String i18n(String key, int limit) {
		if (StringUtils.isEmpty(key)) {
			return "";
		}
		String value = i18nUtil.getMessage(key);
		if (value.length() > limit) {
			value = value.substring(0, limit) + "...";
		}
		return value;
	}

	/**
	 * Return catalog name by categoryId
	 * 
	 * @param categoryId
	 * @return
	 */
	public String i18nCatalog(String categoryId) {
		if (StringUtils.isEmpty(categoryId)) {
			return "";
		}
		String name =categoryService.getCategoryById(new Integer(categoryId)).getCategoryName();
		return name;
	}

	/**
	 * Return catalog name by path
	 * 
	 * @param path
	 *            categoryPath
	 * @return
	 */
	public String i18nCatalogByPath(String path) {
		if (StringUtils.isEmpty(path)) {
			return "";
		}
		String id = path.substring(path.lastIndexOf(".") + 1);
		return i18nCatalog(id);
	}

	/**
	 * Returns i18n category path by categoryId
	 * 
	 * @param categoryId
	 * @return
	 */
	public String i18nPath(String categoryId) {
		if (StringUtils.isEmpty(categoryId)) {
			return "";
		}
		String i18nPath = categoryService.getCategoryById(new Integer(categoryId)).getCategoryPathName();
		
		int length = i18nPath.length();
		if (length > 1) {
			i18nPath = i18nPath.substring(0, length - 1);
		}
		return i18nPath;
	}

	// use log4j
	public void logDebug(String v) {
		if (logger.isDebugEnabled()) {
			logger.debug(v);
		}
	}

	public void logError(String v) {
		if (logger.isErrorEnabled()) {
			logger.error(v);
		}
	}

	public void logInfo(String v) {
		if (logger.isInfoEnabled()) {
			logger.info(v);
		}
	}

	public void logWarn(String v) {
		if (logger.isWarnEnabled()) {
			logger.warn(v);
		}
	}

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
}
