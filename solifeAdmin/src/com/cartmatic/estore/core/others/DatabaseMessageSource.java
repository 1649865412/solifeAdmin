
package com.cartmatic.estore.core.others;

import java.text.MessageFormat;
import java.util.Locale;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.springframework.context.support.AbstractMessageSource;

import com.cartmatic.estore.core.Constants;

/**
 * Reading of messages using data in databases. Also cache messages, allow
 * control in the xml configuration. Fallback to default locale when text for
 * the targeted locale is not found. Will try db message first, then delegate to
 * ResourceBundleMesasgeSource when db message is not found.
 * 
 * @author Ryan Peng
 */
public class DatabaseMessageSource extends AbstractMessageSource {
	private static final int	MAX_CACHE_MSG_LENGTH	= 128;
	private final static String	NULL_CACHED_OBJECT		= "~_-";

	// do not need to support cluster and put into servlet context
	private Cache				cache					= null;

	private CacheManager		cacheManager;

	private boolean				cacheMessages			= true;

	private DatabaseMessageSource() {

	}

	private String getCacheKey(String code, String localeCode) {
		return (new StringBuffer().append(code).append("#").append(localeCode))
				.toString();
	}

	public void reloadAllMessages() {
		if (!cacheMessages) {
			return;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.context.support.AbstractMessageSource#resolveCode(java.lang.String,
	 *      java.util.Locale)
	 */
	@Override
	protected MessageFormat resolveCode(String code, Locale locale) {
		String result = resolveCodeInternal(code, locale);
		return result == null ? null : new MessageFormat(result);
	}

	protected String resolveCodeFromCache(String cacheKey, String localeCode) {
		Element e = cache.get(cacheKey);
		return e != null ? (String) e.getObjectValue() : null;
	}

	protected String resolveCodeFromCacheOrDb(String code, String localeCode) {
		String result = null;
		if (cacheMessages) {
			String cacheKey = getCacheKey(code, localeCode);

			result = resolveCodeFromCache(cacheKey, localeCode);

			/**
			 * no need to care about synchronizations
			 */
			if (result == null) {
				result = resolveCodeFromDb(code, localeCode);

				if (result != null && result.length() < MAX_CACHE_MSG_LENGTH) {
					cache.put(new Element(cacheKey, result));
				}
			}
		} else {
			result = resolveCodeFromDb(code, localeCode);
		}

		if (result == null && logger.isDebugEnabled()) {
			logger.debug("*****I18n text not found, key:" + code
					+ " localeCode:" + localeCode);
		}
		return result;
	}

	/**
	 * Lookup message from db, if not found, try ResourceBundleMessageSource
	 * using getMessageFromParent, which won't activate useCodeAsDefaultMessage.
	 * 
	 * @param code
	 * @param localeCode
	 * @return
	 */
	@SuppressWarnings("deprecation")
	protected String resolveCodeFromDb(String code, String localeCode) {

		return "???";
	}

	/**
	 * Read the message associated with the given code and locale. If message
	 * for requested locale not found, try default locale.
	 * 
	 * @param code
	 *            the code of the message to solve
	 * @param locale
	 *            the locale to check against
	 * @return a String if one were found, either for the given locale or for
	 *         the default on, or null if nothing could be found
	 */
	protected String resolveCodeInternal(String code, Locale locale) {
		/**
		 * 如果I18n禁止了，满足以下条件，则直接返回code：<BR/> 1.code没有. 2.code长度超过64
		 * 说明：会有误判断，结果还是找不到，可能会放入cache
		 */

		String localeCode = locale.toString();
		String result = resolveCodeFromCacheOrDb(code, localeCode);
		if (result == null && !Constants.SYSTEM_LOCALE_CODE.equals(localeCode)) {
			result = resolveCodeFromCacheOrDb(code,
					Constants.SYSTEM_LOCALE_CODE);
		}
		/**
		 * message not found after trying all approaches, apply
		 * useCodeAsDefaultMessage usage, and cache message when caching is
		 * enabled. So no need to query db every time.
		 */
		if (result == null) {
			if (cacheMessages) {
				String cacheKey = getCacheKey(code, localeCode);
				cache.put(new Element(cacheKey, NULL_CACHED_OBJECT));
			}
			if (logger.isWarnEnabled()) {
				logger.warn("*****I18n text not found for all locales, key:"
						+ code + " localeCode:" + localeCode);
			}
		} else if (NULL_CACHED_OBJECT.equals(result)) {
			result = null;
		}

		return result;
	}

	@Override
	protected String resolveCodeWithoutArguments(String code, Locale locale) {
		return resolveCodeInternal(code, locale);
	}

	public void setCacheManager(CacheManager in_cacheManager) {
		this.cacheManager = in_cacheManager;
	}

	public void setCacheMessages(boolean cacheMessages) {
		this.cacheMessages = cacheMessages;
	}

}