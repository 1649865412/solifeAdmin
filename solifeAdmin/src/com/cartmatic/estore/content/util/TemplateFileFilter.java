/**
 * 
 */
package com.cartmatic.estore.content.util;

import java.io.File;
import java.io.FileFilter;
import java.util.regex.Pattern;

/**
 * @author Ryan
 * 
 */
public class TemplateFileFilter implements FileFilter {

	private String suffix;
	
	/** 模板类型 － 邮件模板*/
	public final static String TEMPLATE_TYPE_VELOCITY = "velocity"; 
	/**
	 * 路径包含有 admin或system的都不能显示
	 */
	private final static Pattern P_ADMIN = Pattern.compile(".*admin.*");
	private final static Pattern P_SYSTEM = Pattern.compile(".*system.*");
	/**
	 * 
	 */
	public TemplateFileFilter(String templateType) {
		if ("jsp".equals(templateType)) {
			this.suffix = null;
		} else if ("jspf".equals(templateType)) {
			this.suffix = ".jspf";
		} else if (TEMPLATE_TYPE_VELOCITY.equals(templateType)) {
			this.suffix = ".vm";
		} else if ("scripts".equals(templateType)) {
			this.suffix = ".js";
		} else if ("styles".equals(templateType)) {
			this.suffix = ".css";
		} else {
			this.suffix = null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.FileFilter#accept(java.io.File)
	 */
	public boolean accept(File file) {
		if (file.isHidden() || file.getName().startsWith("."))
			return false;
		if (P_ADMIN.matcher(file.getPath()).matches() || P_SYSTEM.matcher(file.getPath()).matches())
			return false;
		return file.isDirectory() || this.suffix == null || (this.suffix != null && file.getName().endsWith(this.suffix));
	}
}
