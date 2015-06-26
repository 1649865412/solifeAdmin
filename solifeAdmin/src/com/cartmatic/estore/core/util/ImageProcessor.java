/*
 * Created on Nov 26, 2007
 * 
 */

package com.cartmatic.estore.core.util;

import java.io.IOException;

public interface ImageProcessor {
	/**
	 * 对图片增加水印，支持图片或文字的，通过conf配置。采用Properties配置是为方便以后扩展以提供更多配置项，如文字的位置等。
	 * 
	 * @param srcImagePath
	 * @param destImagePath
	 * @throws IOException
	 */
	public void addWatermark(String srcImagePath, String destImagePath)
			throws IOException;

	/**
	 * 对图片进行缩放＋空白填充处理然后保存到destFile，缩放后的格式根据destFile后缀自动判断，但调用者应注意过滤不支持的格式（如GIF），推荐是JPG和PNG。如果不支持在encode时会抛异常。
	 * 
	 * @param srcFile
	 * @param destFile
	 * @param requiredWidth
	 * @param requiredHeight
	 * @param padding
	 */
	void scaleImage(String srcFile, String destFile, int requiredWidth,
			int requiredHeight, int padding);
	
	void scaleImageGIF(String srcFile, String destFile, int requiredWidth,
			int requiredHeight, int padding);
}
