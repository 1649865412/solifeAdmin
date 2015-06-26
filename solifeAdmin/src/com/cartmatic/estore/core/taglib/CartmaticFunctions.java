package com.cartmatic.estore.core.taglib;

import java.math.BigDecimal;
import java.util.Collection;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

public class CartmaticFunctions
{
	public CartmaticFunctions(){}
	
	public static int lastIndexOf(String value, String str) {
		if(value==null){
			return -1;
		}
        return value.lastIndexOf(str);
    }
	
	public static String lastSubstringBefore(String value, String str) {
		if(value==null){
			return value;
		}
		if(value.lastIndexOf(str)!=-1){
			value=value.substring(0, value.lastIndexOf(str));
		}
        return value;
    }
	
	public static String substring(String value, String beginStr,String endStr) {
		if(value==null){
			return value;
		}
		if(!beginStr.equals("")&&value.indexOf(beginStr)!=-1){
			value=value.substring(value.indexOf(beginStr)+beginStr.length());
		}
		if(!endStr.equals("")&&value.indexOf(endStr)!=-1){
			value=value.substring(0, value.lastIndexOf(endStr));
		}
        return value;
    }
	
	public static boolean contains(Collection<Object> collection, Object obj) {
		if(collection==null||obj==null){
			return false;
		}
        return collection.contains(obj);
    }
	
	public static boolean contains4Array(Object[] array, Object obj) {
		if(array==null||obj==null){
			return false;
		}
        return ArrayUtils.contains(array, obj);
    }
	
	public static boolean contains4Code(String codeString, String code) {
		if(StringUtils.isBlank(codeString)||StringUtils.isBlank(code)){
			return false;
		}
        return (","+codeString.trim()+",").indexOf(","+code.trim()+",")!=-1;
    }
	
	public static BigDecimal setScale(BigDecimal num, Integer newScale) {
		if(num==null){
			return num;
		}
        return num.setScale(newScale, BigDecimal.ROUND_HALF_UP);
    }
	
	public static BigDecimal divide(BigDecimal num1,BigDecimal num2, Integer scale) {
        return num1.divide(num2,scale,BigDecimal.ROUND_HALF_UP);
    }
	
	public static void main(String[] args) { 
		System.out.println(substring("abc123456789def", "","789"));
	}
}
