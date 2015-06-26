
package com.cartmatic.estore.core.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.MessageDigest;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 常用字符串操作工具类。
 */
public final class StringUtil {
	private final static Log	log	= LogFactory.getLog(StringUtil.class);

	// ~ Static fields/initializers
	// =============================================

	/**
	 * Decode a string using Base64 encoding.
	 * 
	 * @param str
	 * @return String
	 */
	public static String decodeString(String str) {
		sun.misc.BASE64Decoder dec = new sun.misc.BASE64Decoder();
		try {
			return new String(dec.decodeBuffer(str));
		} catch (IOException io) {
			throw new RuntimeException(io.getMessage(), io.getCause());
		}
	}

	// ~ Methods
	// ================================================================

	/**
	 * Encode a string using algorithm specified in web.xml and return the
	 * resulting encrypted password. If exception, the plain credentials string
	 * is returned
	 * 
	 * @param password
	 *            Password or other credentials to use in authenticating this
	 *            username
	 * @param algorithm
	 *            Algorithm used to do the digest
	 * 
	 * @return encypted password based on the algorithm.
	 */
	public static String encodePassword(String password, String algorithm) {
		if (algorithm == null) {
			return password;
		}

		byte[] unencodedPassword = password.getBytes();

		MessageDigest md = null;

		try {
			// first create an instance, given the provider
			md = MessageDigest.getInstance(algorithm);
		} catch (Exception e) {
			log.error("Exception: " + e);

			return password;
		}

		md.reset();

		// call the update method one or more times
		// (useful when you don't know the size of your data, eg. stream)
		md.update(unencodedPassword);

		// now calculate the hash
		byte[] encodedPassword = md.digest();

		StringBuffer buf = new StringBuffer();

		for (int i = 0; i < encodedPassword.length; i++) {
			if ((encodedPassword[i] & 0xff) < 0x10) {
				buf.append("0");
			}

			buf.append(Long.toString(encodedPassword[i] & 0xff, 16));
		}

		return buf.toString();
	}

	/**
	 * Encode a string using Base64 encoding. Used when storing passwords as
	 * cookies.
	 * 
	 * This is weak encoding in that anyone can use the decodeString routine to
	 * reverse the encoding.
	 * 
	 * @param str
	 * @return String
	 */
	public static String encodeString(String str) {
		sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
		return encoder.encodeBuffer(str.getBytes()).trim();
	}

	public static StringBuilder format(String msgWithFormat, Object... args) {
		return formatStr(new StringBuilder(msgWithFormat), true, args);
	}

	/**
	 * 类似C的printf的格式化，适合比较复杂的格式化。
	 * 
	 * @param format
	 * @param args
	 * @return
	 */
	public static String format2(String format, Object... args) {
		return String.format(format, args);
	}

	public static StringBuilder formatNoBracket(String msgWithFormat,
			Object... args) {
		return formatStr(new StringBuilder(msgWithFormat), false, args);
	}

	/**
	 * 简单的字符串格式化，性能较好。支持不多于10个占位符，从%1开始计算，数目可变。参数类型可以是字符串、Integer、Object，甚至int等基本类型、以及null，但只是简单的调用toString()，较复杂的情况用String.format()。每个参数可以在表达式出现多次。
	 * 
	 * @param msgWithFormat
	 * @param autoQuote
	 * @param args
	 * @return
	 */
	private static StringBuilder formatStr(CharSequence msgWithFormat,
			boolean autoQuote, Object... args) {
		StringBuilder sb = new StringBuilder(msgWithFormat);
		int argsLen = args.length;

		if (argsLen > 0) {
			boolean markFound = false;
			for (int i = 0; i < argsLen; i++) {
				String flag = "%" + (i + 1);
				int idx = sb.indexOf(flag);
				// 支持多次出现、替换的代码；但替换的内容里可能也有%1，例如hql，不能形成死循环
				while (idx >= 0) {
					markFound = true;
					String tmp = toString(args[i], autoQuote);
					sb.replace(idx, idx + 2, tmp);
					idx = sb.indexOf(flag, idx + tmp.length());
				}
			}

			if (args[argsLen - 1] instanceof Throwable) {
				StringWriter sw = new StringWriter();
				((Throwable) args[argsLen - 1])
						.printStackTrace(new PrintWriter(sw));
				sb.append("\n").append(sw.toString());
			} else if (argsLen == 1 && !markFound) {
				sb.append(args[argsLen - 1].toString());
			}
		}
		return sb;
	}
/*
	public static String getEncryptPassword(String password) {
		return encodePassword(password, "MD5");
	}
*/
	public static boolean isNumber(String value) {
		try {
			Integer d = new Integer(value);
			return true;
		} catch (Exception e) {
		}
		try {
			Double d = new Double(value);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public final static String[] toArray(String str) {
		return toArrayByDel(str, ";");
	}

	public final static String[] toArrayByDel(String str, String del) {
		String[] array = null;
		if (str != null && str.length() > 0 && del != null && del.length() > 0) {
			StringTokenizer st = new StringTokenizer(str, del);
			if (st != null && st.countTokens() > 0) {
				array = new String[st.countTokens()];
				int i = 0;
				while (st.hasMoreTokens()) {
					array[i++] = st.nextToken();
				}
			}
		}

		return array;
	}

	public static String toString(Object obj, boolean autoQuote) {
		StringBuilder sb = new StringBuilder();
		if (obj == null) {
			sb.append("NULL");
		} else {
			if (obj instanceof Object[]) {
				for (int i = 0; i < ((Object[]) obj).length; i++) {
					sb.append(((Object[]) obj)[i]).append(", ");
				}
				if (sb.length() > 0) {
					sb.delete(sb.length() - 2, sb.length());
				}
			} else {
				sb.append(obj.toString());
			}
		}
		if (autoQuote
				&& sb.length() > 0
				&& !((sb.charAt(0) == '[' && sb.charAt(sb.length() - 1) == ']') || (sb
						.charAt(0) == '{' && sb.charAt(sb.length() - 1) == '}'))) {
			sb.insert(0, "[").append("]");
		}
		return sb.toString();
	}
	
	public static String arrayToString(String[] array, String mask){
		String returnString = "";
		for(String str : array){
			returnString += str + mask;
		}
		return returnString.substring(0,returnString.length()-1);
	}

	public static boolean isEmpty(String value)
	{
		return value == null || value.length()==0;
	}
	
	private StringUtil() {
	}
}
