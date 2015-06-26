/*
 * Created on Apr 30, 2006
 * Updated on Mar 22, 2011. Refactor the Locale's strategy, this i18nUtil only for cached.
 */

package com.cartmatic.estore.core.util;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * <P>
 * Note:keys startWith "_" will be cached in message source.
 * </P>
 * 
 * @author Ryan TODO: performance tuning
 */
public final class I18nUtil
{
	
	private final static Map<String,Locale> cachedLocaleMap = new HashMap<String,Locale>();
	
	private final static I18nUtil	i18nUtil						= new I18nUtil();

	public static I18nUtil getInstance() {
		return i18nUtil;
	}


	public static Locale getLocaleByCode(String localeCode) {

		Locale locale = (Locale) cachedLocaleMap.get(localeCode);
		if (locale == null && localeCode != null) {
			if (localeCode.indexOf('_') > 0) {
				String[] tempArr = localeCode.split("[_]");
				locale = new Locale(tempArr[0], tempArr[1]);
			} else {
				locale = new Locale(localeCode);
			}
			cachedLocaleMap.put(localeCode, locale);
		}

		return locale;
	}	

	private boolean			isI18nEnabled	= true;

	private MessageSource	messageSource;

	private I18nUtil() {
	}

	/**
	 * 提供方便地构造复杂的I18n信息的方法。
	 * <P>
	 * 使用类似于JSP的EL的语法，如"$[common.msg.updated] $[product.productNameKey]:
	 * $[product-i18nTextKey]."可以生成"更新了产品:相机1"，这样复杂的信息，而编写要比自己凑字符串简单得多。
	 * 示例里面的前两段都是Properties的key，最后是数据库里面的key。 为了和JSP的EL区分，所以用$[]代替${}。
	 * <P>
	 * 自动使用当前用户的语言。支持Properties文件和数据库里面的信息的混合。还可以多层嵌套。即一个I18n信息里面还可以有I18n信息。
	 * 
	 * @param strI18nEl
	 *            如果某个code找不到，用???+code+???代替
	 * @return
	 */
	public String evalMessage(final String strI18nEl) {
		int i18nMarkStartIdx = strI18nEl.indexOf("$[");
		if (i18nMarkStartIdx == -1) {
			return getMessage(strI18nEl);
		}

		StringBuilder sb = new StringBuilder(strI18nEl);
		// replace all i18n code with concrete message
		do {
			int i18nMarkEndIdx = sb.indexOf("]", i18nMarkStartIdx);
			String code = sb.substring(i18nMarkStartIdx + 2, i18nMarkEndIdx);
			boolean isDynamicMsg = code.charAt(0) == '[';
			String finalCode = isDynamicMsg ? code.substring(1) : code;
			// 如果I18N启用，或虽然禁止了，但code代表的是一个系统的信息，就求值；否则直接返回code，因为在I18N禁止情况下，code就是value。
			// TODO:开头的符号的处理还没有完成；搬到getMessage里面是不是更好更一致？使用[来对静态的I18n信息（系统信息）进行强制求值
			// textValue里面有不少]的情况；不过都是从start标记之后进行搜索]，应该不会影响结果
			String i18nMsg = null;
			if (!isI18nEnabled) {
				i18nMsg = isDynamicMsg ? code : getMessage(finalCode);
			} else {
				i18nMsg = getMessage(finalCode);
			}
			sb.replace(i18nMarkStartIdx, i18nMarkEndIdx + 1, i18nMsg);
			// find next mark
			i18nMarkStartIdx = sb.indexOf("$[", i18nMarkStartIdx);
		} while (i18nMarkStartIdx >= 0);
		return sb.toString();
	}

	public String getMessage(String code) {
		return getMessage(code, null, null, null);
	}

	public String getMessage(String code, Object[] args) {
		return getMessage(code, args, null, null);
	}

	// TODO:根据是否启用I18n判断处理，包括其他的方法也需要修改一下
	public String getMessage(String code, Object[] args, String defaultMessage,
			String localeCode) {
		String msg = messageSource.getMessage(code, args, defaultMessage,
				localeCode == null ? LocaleContextHolder.getLocale()
						: getLocaleByCode(localeCode));
		if (msg == null) {
			msg = "???" + code + "???";
		}
		return msg;
	}

	public String getMessage(String code, String defaultMessage) {
		return getMessage(code, null, defaultMessage, null);
	}

	public String getMessage(String code, String defaultMessage,
			String localeCode) {
		return getMessage(code, null, defaultMessage, localeCode);
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
}
