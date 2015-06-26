/**
 * 
 */
package com.cartmatic.estore.order.util;

import java.util.Map;

/**
 * 订单模块工具类
 * @author pengzhirong
 */
public class OrderUtil {
	/**
	 * 获取request中的参数值
	 * @param params request.getParameterMap()
	 * @param key 参数名
	 * @return
	 */
	public static String getParameter(Map params, String key){
		String[] valueArray = (String[])params.get(key);
		if(valueArray!=null && valueArray.length>0){
			return valueArray[0];
		}
		
		return null;
	}
}
