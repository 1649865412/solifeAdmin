package com.cartmatic.estore.imports.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.cartmatic.estore.imports.model.Column;

public class ImportHelper {
	/**
	 * 
	 * @param value
	 * @param replaces key为CoNtInUe|开始的递归连续替换
	 * @return
	 */
	public static String toReplace(String value,LinkedHashMap<String, String> replaces) {
		if (value != null && replaces!= null
				&& replaces.size() > 0) {
			Set<String> regexs = replaces.keySet();
			for (String regex : regexs) {
				String replacement = replaces.get(regex);
				if(regex.startsWith("CoNtInUe|")){
					//递归连续替换
					regex=regex.substring("CoNtInUe|".length());
					while(value.indexOf(regex)!=-1){
						value=value.replaceAll(regex,replacement);
						if(regex==""){
							break;
						}
					}
				}else{
					value = value.replaceAll(regex, replacement);
				}
			}
		}
		return value;
	}
	public static void main(String[] args) {
		String value="aaa#%-s#d$fd%43&&**dg--d";
		LinkedHashMap<String, String>replaces=new LinkedHashMap<String, String>();
		replaces.put("[^\\w]", "-");
		replaces.put("CoNtInUe|--", "-");
		value=ImportHelper.toReplace(value, replaces);
		System.out.println(value);
	}
	public static String toReplace(String value, Column column){
		return toReplace(value, column.getReplaces());
	}

	public static String toLowerOrUpper(String value, String toLowerOrUpper) {
		if (StringUtils.isNotEmpty(value)&&StringUtils.isNotEmpty(toLowerOrUpper)) {
			if (toLowerOrUpper.equalsIgnoreCase("Lower")) {
				value = String.valueOf(value).toLowerCase();
			} else if (toLowerOrUpper.equalsIgnoreCase("Upper")) {
				value = String.valueOf(value).toUpperCase();
			} 
		}
		return value;
	}
	public static String toLowerOrUpper(String value, Column column) {
		return toLowerOrUpper(value, column.getToLowerOrUpper());
	}
	
	public static String toSubstring(String value, Integer  maxLength){
		if (StringUtils.isNotEmpty(value) && maxLength != null
				&& maxLength > 0) {
			if (value.length() > maxLength) {
				value = value.substring(0, maxLength);
			}
		}
		return value;
	}

	public static String toSubstring(String value, Column column) {
		return toSubstring(value, column.getMaxLength());
	}
	public static List<String> toSplit(String value, Column column) {
		ArrayList<String> values = new ArrayList<String>();
		if(StringUtils.isNotEmpty(column.getSplit())){
			String tempValues[]=value.split(column.getSplit());
			for (String tempValue : tempValues) {
				if(StringUtils.isNotBlank(tempValue)){
					values.add(toSubstring(addPrefixAndSuffix(tempValue.trim(), column),column));
				}
			}
			if(values.size()>0){
				return values;
			}
			
		}
		values.add(toSubstring(addPrefixAndSuffix(value.trim(), column), column));
		return values;
	}
	public static List<String> toColumnHeaderSplit(String value, Column column) {
		ArrayList<String> values = new ArrayList<String>();
		if (StringUtils.isNotEmpty(column.getColumnHeaderSplit())) {
			String columnHeaders[] = column.getColumnHeader().split(column.getColumnHeaderSplit());
			for (String columnHeader : columnHeaders) {
				String tempValue=column.getRowDataMap().get(columnHeader);
				values.add(addPrefixAndSuffix(tempValue.trim(), column));
			}
		}else{
			String tempValue=column.getRowDataMap().get(column.getColumnHeader());
			values.add(addPrefixAndSuffix(tempValue.trim(), column));
		}
		return values;
	}
	
	public static String toSelect(String value, Column column) {
		LinkedHashMap<String, String> selects=column.getSelects();
		if (value != null&&StringUtils.isNotBlank(value)&& selects!= null&& selects.size() > 0) {
			value=selects.get(value);
			if(value==null&&StringUtils.isEmpty(column.getDefaultValue())){
				value=selects.entrySet().iterator().next().getValue();
			}
		}
		return value;
	}
	
	public static String addPrefixAndSuffix(String value, Column column) {
		if(StringUtils.isNotEmpty(value)){
			StringBuffer tempValue=new StringBuffer(value);
			if(StringUtils.isNotEmpty(column.getPrefix())){
				tempValue.insert(0,column.getPrefix());
			}
			if(StringUtils.isNotEmpty(column.getSuffix())){
				tempValue.append(column.getSuffix());
			}
			return tempValue.toString();
		}
		return value;
	}
	public static String arrayAddSplitToValue(List<String>values,Column column){
		List<String>tempValues=null;
		if(column.isArrayAddSplitToValueNotBlank()){
			tempValues=new ArrayList<String>();
			for (String value : values) {
				if(StringUtils.isNotBlank(value)){
					tempValues.add(value);
				}
			}
		}else{
			tempValues=values;
		}
		
		if(tempValues.size()>1&&StringUtils.isNotBlank(column.getArrayAddSplitToValue())){
			StringBuffer value=new StringBuffer();
			int index=0;
			for (String tempValue : tempValues) {
				index++;
				value.append(tempValue);
				if(index<tempValues.size())
					value.append(column.getArrayAddSplitToValue());
			}
			return value.toString();
		}
		return tempValues.get(0);
		
	}
}
