package com.cartmatic.estore.imports.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.cartmatic.estore.imports.handler.ColumnHandler;
import com.cartmatic.estore.imports.util.ImportHelper;

@SuppressWarnings("serial")
public class Column implements Serializable{
	/**
	 * 处理的实体名称或自定义属性code
	 */
	private String attrName;
	
	/**
	 * 导入文件列头
	 */
	private String columnHeader;
	
	/**
	 * 由多个列头组成的column值的，指定列头之间的分隔符
	 */
	private String columnHeaderSplit;
	
	/**
	 * 处理本Column的Handler
	 */
	private ColumnHandler handler;
	
	/**
	 * 默认值
	 */
	private String defaultValue;
	
	/**
	 * 日期格式
	 */
	private String pattern;
	
	/**
	 * column值分隔
	 */
	private String split;
	
	/**
	 * 导入csv数据文件单元格值
	 */
	private String value;
	
	/**
	 * 导入csv数据文件单元格原值(不会再更改)
	 */
	private String originalValue;
	
	/**
	 * 由多个列头组成的column值时，
	 */
	private List<String> values=null;
	
	private List<String> originalValues=null;
	
	/**
	 * 处理顺序,越小的越优先处理
	 */
	private Integer index;
	
	/**
	 * 是否必需，即本字段不能为空
	 */
	private boolean required=false;

	private String targets;
	
	private String replacements="";
	
	private int rowNum=0;
	
	/**
	 * 是否作默认值处理
	 */
	private boolean isInitData=false;
	
	/**
	 * 大小写转换
	 */
	private String toLowerOrUpper=null;
	
	/**
	 * 文本长度限制
	 */
	private Integer maxLength=null;
	
	/**
	 * 替换处理
	 */
	private LinkedHashMap<String, String> replaces=null;
	
	/**
	 * 属性选择，如状态，销售规则等
	 */
	private LinkedHashMap<String, String> selects=null; 
	
	/**
	 * 是否支持更新
	 */
	private boolean isSupportUpdate=true;
	
	/**
	 * 前缀（value非空时会加上）
	 */
	private String prefix;
	
	/**
	 * 后缀（value非空时会加上）
	 */
	private String suffix;
	
	/**
	 * 保存当列的所有数据
	 */
	private Map<String,String> rowDataMap=null;
	
	/**
	 * 数组时，以指定字符分隔成字符串
	 */
	private String arrayAddSplitToValue;
	
	/**
	 * 数组时，以指定字符分隔成字符串 时，数组过滤掉空字符串
	 */
	private boolean arrayAddSplitToValueNotBlank=false;
	
	
	public boolean isArrayAddSplitToValueNotBlank() {
		return arrayAddSplitToValueNotBlank;
	}

	public void setArrayAddSplitToValueNotBlank(boolean arrayAddSplitToValueNotBlank) {
		this.arrayAddSplitToValueNotBlank = arrayAddSplitToValueNotBlank;
	}

	public String toString() {
		return new ToStringBuilder(this)
				.append("attrName", this.attrName) 
				.append("columnHeader", this.columnHeader) 
				.append("columnHeaderSplit", this.columnHeaderSplit) 
				.append("value", this.value) 
				.append("originalValue", this.originalValue) 
				.append("index", this.index) 
				.append("required", this.required) 
				.append("targets", this.targets) 
				.append("replacements", this.replacements) 
				.append("rowNum", this.rowNum) 
				.append("defaultValue", this.defaultValue) 
				.append("isInitData", this.isInitData) 
				.append("split", this.split) 
				.toString();
	}

	public Integer getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
	}

	public LinkedHashMap<String, String> getReplaces() {
		return replaces;
	}

	public void setReplaces(LinkedHashMap<String, String> replaces) {
		this.replaces = replaces;
	}

	public int getRowNum() {
		return rowNum;
	}

	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}

	public String getTargets() {
		return targets;
	}

	public void setTargets(String targets) {
		this.targets = targets;
	}

	public String getReplacements() {
		return replacements;
	}

	public void setReplacements(String replacements) {
		this.replacements = replacements;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value=null;
		this.originalValue=null;
		this.values=null;
		this.originalValues=null;
		if(StringUtils.isNotEmpty(value)){
			value=value.trim();
		}
		if(isInitData){
			value=defaultValue;
			originalValue=value;
			if(StringUtils.isNotEmpty(getSplit())){
				values=ImportHelper.toSplit(value, this);
				value=ImportHelper.arrayAddSplitToValue(values, this);
			}else{
				value=ImportHelper.addPrefixAndSuffix(value, this);
				values=Arrays.asList(value);
			}
		}else{
			originalValue=value;
			value=ImportHelper.toLowerOrUpper(value, this);
			value=ImportHelper.toReplace(value, this);
			if(StringUtils.isEmpty(getSplit())){
				value=ImportHelper.toSubstring(value, this);
			}
			if(getSelects()!=null){
				value=ImportHelper.toSelect(value, this);
			}else if(StringUtils.isNotEmpty(getSplit())){
				values=ImportHelper.toSplit(value, this);
				value=ImportHelper.arrayAddSplitToValue(values, this);
			}else if(StringUtils.isNotEmpty(getColumnHeaderSplit())){
				values=ImportHelper.toColumnHeaderSplit(value, this);
				value=ImportHelper.arrayAddSplitToValue(values, this);
			}else{
				value=ImportHelper.addPrefixAndSuffix(value, this);
				values=Arrays.asList(value);
			}
		}
		this.value = value;
	}

	public String getAttrName() {
		return attrName;
	}

	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}

	public String getColumnHeader() {
		return columnHeader;
	}

	public void setColumnHeader(String columnHeader) {
		this.columnHeader = columnHeader;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public String getSplit() {
		return split;
	}

	public void setSplit(String split) {
		this.split = split;
	}

	public String getColumnHeaderSplit() {
		return columnHeaderSplit;
	}

	public void setColumnHeaderSplit(String columnHeaderSplit) {
		this.columnHeaderSplit = columnHeaderSplit;
	}

	public List<String> getValues() {
		return values;
	}

	public void setValues(ArrayList<String> values) {
		originalValues=new ArrayList<String>();
		this.values=new ArrayList<String>();
		for (String value : values) {
			originalValues.add(value);
			value=ImportHelper.toLowerOrUpper(value, this);
			value=ImportHelper.toReplace(value, this);
			value=ImportHelper.toSubstring(value, this);
			this.values.add(value);
		}
	}

	public boolean isInitData() {
		return isInitData;
	}

	public void setInitData(boolean isInitData) {
		this.isInitData = isInitData;
	}

	public String getToLowerOrUpper() {
		return toLowerOrUpper;
	}

	public void setToLowerOrUpper(String toLowerOrUpper) {
		this.toLowerOrUpper = toLowerOrUpper;
	}

	public String getOriginalValue() {
		return originalValue;
	}

	public List<String> getOriginalValues() {
		return originalValues;
	}

	public Map<String, String> getRowDataMap() {
		return rowDataMap;
	}

	public void setRowDataMap(Map<String, String> rowDataMap) {
		this.rowDataMap = rowDataMap;
	}

	public ColumnHandler getHandler() {
		return handler;
	}

	public void setHandler(ColumnHandler handler) {
		this.handler = handler;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public LinkedHashMap<String, String> getSelects() {
		return selects;
	}

	public void setSelects(LinkedHashMap<String, String> selects) {
		this.selects = selects;
	}

	public boolean isSupportUpdate() {
		return isSupportUpdate;
	}

	public void setSupportUpdate(boolean isSupportUpdate) {
		this.isSupportUpdate = isSupportUpdate;
	}

	public String getArrayAddSplitToValue() {
		return arrayAddSplitToValue;
	}

	public void setArrayAddSplitToValue(String arrayAddSplitToValue) {
		this.arrayAddSplitToValue = arrayAddSplitToValue;
	}

}
