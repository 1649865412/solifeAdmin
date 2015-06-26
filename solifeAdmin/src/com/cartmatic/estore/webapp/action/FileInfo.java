package com.cartmatic.estore.webapp.action;

import java.util.UUID;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.cartmatic.estore.common.util.DateUtil;


public class FileInfo {
	/**
	 * 媒体根目录
	 */
	private String rootCategory;
	/**
	 * 媒体规格目录
	 */
	private String	category;
	
	/**
	 * 媒体名
	 */
	private String				fileName;
	
	/**
	 * 保存图片Path
	 */
	private String hashPath;
	
	
	private String				fileNameOnly;

	private String				suffix				= null;

	private final static int hashPathLength=6;

	public FileInfo(String rootCategory,String category, String fileName,String objId) {
		this.rootCategory=rootCategory;
		this.category=category;
		this.fileName=fileName;
		int idx = fileName.lastIndexOf(".");
		if (idx > 0 && idx != fileName.length()) {
			suffix = fileName.substring(idx + 1, fileName.length())
					.toLowerCase();
			this.fileNameOnly = fileName.substring(0, idx);
		} else {
			this.fileNameOnly = fileName;
		}
		fileNameOnly=fileNameOnly.replaceAll("[^\\w]+", "-");
		
		if(StringUtils.isNotBlank(objId)){
			StringBuffer objIdBuff=new StringBuffer();
			//id长度不足的补零
			int addZeroCount=hashPathLength-objId.length();
			for (int i = 0; i < addZeroCount; i++) {
				objIdBuff.append("0");
			}
			objIdBuff.append(objId);
			StringBuffer temp=new StringBuffer();
			for (int i = 0; i < objIdBuff.length(); i+=2) {
				if(i+2<objIdBuff.length()){
					temp.append(objIdBuff.substring(i, i+2));
				}else{
					temp.append(objIdBuff.substring(i));
				}
				temp.append("/");
			}
			hashPath=temp.toString();
		}else{
			//没有指定Id时使用时间作为path
			hashPath=new StringBuffer(DateUtil.getShortDateString()).append("/").toString();
		}
	}
	
	public void autoRename() {
		fileNameOnly = fileNameOnly + "_"+ Math.abs(UUID.randomUUID().getLeastSignificantBits());
	}
	
	public void uuidRename()
	{
		fileNameOnly = UUID.randomUUID().toString();
	}

	public String getFileName() {
		return new StringBuilder(fileNameOnly).append(".").append(suffix).toString();
	}
	public String getMediaPath() {
		 return getMediaPath(category);
	}

	public String getMediaPath(String category) {
		// 可能可以考虑加上用户的id进一步减少一级目录下的文件数
		StringBuffer path=new StringBuffer();
		if(StringUtils.isNotBlank(rootCategory)){
			path.append(rootCategory);
			path.append("/");
		}
		if(StringUtils.isNotBlank(category)){
			path.append(category);
			path.append("/");
		}
		path.append(getSampleMediaPath());
		return path.toString();
	}
	
	public String getSampleMediaPath(){
		StringBuffer path=new StringBuffer();
		if(StringUtils.isNotBlank(hashPath)){
			path.append(hashPath);
		}
		path.append(getFileName());
		return path.toString().toLowerCase();
	}

	public String getSuffix() {
		return suffix;
	}

	
}
