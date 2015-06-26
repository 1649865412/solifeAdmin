
package com.cartmatic.estore.core.security;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlMatcher {
	public static boolean matches(String url, String urlPattern) {
		StringBuffer buff = new StringBuffer();
		buff.append('^');
		buff.append(urlPattern.replaceAll("\\.", "\\\\.").replaceAll("\\?",
				"\\\\?").replaceAll("\\*", ".*"));
		Pattern pattern = Pattern.compile(buff.toString());
		Matcher matcher = pattern.matcher(url);
		boolean found = matcher.find();
		return found && matcher.group().equals(url);
	}
}