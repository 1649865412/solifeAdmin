package com.cartmatic.estore.common.model.content;

import java.io.File;
import java.io.FileFilter;

public class UploadFileFilter implements FileFilter {

	private String[] suffixConfs;


	public UploadFileFilter(String[] suffixConfs) {
		this.suffixConfs = suffixConfs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.FileFilter#accept(java.io.File)
	 */
	public boolean accept(File file) {
		
		if(file.isHidden()){
			return false;
		}
		
		if(file.isFile()&&!file.getName().endsWith(".jpg")){
			return false;
		}
		
		if(file.isFile()&&suffixConfs!=null && suffixConfs.length>0){
			for(String suffix:suffixConfs){
				String str = file.getName();
				//如果有相同的后缀名则return false,但后缀名为空字符串除外
				if(str.endsWith(suffix+".jpg") && !suffix.equals("")){
					return false;
				}
			}
		}
		return true;
	}
}