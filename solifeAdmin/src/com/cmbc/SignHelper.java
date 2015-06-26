package com.cmbc;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

public class SignHelper {
	
    public static String sign(Map<String, String> params, String key) {
        if (null != params && StringUtils.isNotBlank(key)) {
            Properties properties = new Properties();
            for (Iterator<String> iter = params.keySet().iterator(); iter.hasNext();) {
                String name = (String) iter.next();
                String value = params.get(name);
                if (StringUtils.isBlank(name) || StringUtils.isBlank(value)
                    || StringUtils.equalsIgnoreCase("sign", name)) {
                    continue;
                }
                properties.setProperty(name, StringUtils.trim(value));
            }
            String content = getSignatureContent(properties);
            return sign(content, key);
        }
        return null;
    }

    public static boolean checkSign(Map<String, String> params, String key, String sign) {
        if (null != params && StringUtils.isNotBlank(key) && StringUtils.isNotBlank(sign)) {

            String signed = sign(params, key);
            if (StringUtils.equalsIgnoreCase(signed, sign)) {
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
	private static String getSignatureContent(Properties properties) {
        StringBuffer content = new StringBuffer();
        List keys = new ArrayList(properties.keySet());
        Collections.sort(keys);
        for (int i = 0; i < keys.size(); i++) {
            String key = (String) keys.get(i);
            String value = properties.getProperty(key);
            content.append((i == 0 ? "" : "&") + key + "=" + value);
        }
        return content.toString();
    }

    public static String sign(String content, String privateKey) {
        if (StringUtils.isBlank(privateKey)) {
            return null;
        }
        String signBefore = content + privateKey;
        return Md5Encrypt.md5(signBefore);
    }

    public static String mapToUrl(Map<String, String> map) {
        Iterator<Entry<String, String>> it = map.entrySet().iterator();
        StringBuffer sb = new StringBuffer();
        while (it != null && it.hasNext()) {
            Entry<String, String> entry = it.next();
            if (StringUtils.isNotBlank(entry.getKey())) {
                sb.append(entry.getKey());
                sb.append("=");
                if (entry.getKey().equals("out_trade_no")) {
                	try {
						sb.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
						sb.append("&");
						continue;
					} catch (UnsupportedEncodingException e) {
					}
				}
                sb.append(entry.getValue());
                sb.append("&");
            }
        }
        String urlTemp = sb.toString();
        if (StringUtils.isNotBlank(urlTemp)) {
            return StringUtils.substring(urlTemp, 0, urlTemp.length() - 1);
        }

        return null;
    }

}
