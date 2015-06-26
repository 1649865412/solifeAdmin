/*
 * Created on Nov 27, 2007
 * 
 */

package com.cartmatic.estore.webapp.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;

public class ImageConf implements InitializingBean
{
	private boolean	autoScaleImage	= true;
	private boolean	autoWatermark	= false;
	private int		height;
	private int		padding;
	private int		width;
	/**
	 * 为图片所在目录
	 */
	private String mediaType;
	private String originalImageCategory;
	private boolean keepOriginalImage=true;
	/**
	 * all imageProcessorConf
	 */
	private Map<String, ImageProcessorConf> imageProcessorConfMap;
	private List<ImageProcessorConf> imageProcessorConfigs=new ArrayList<ImageProcessorConf>();
	/**
	 * current configures which use ',' to split.
	 */
	private String imageProcessorConfs;
	
	public void setImageProcessorConfMap(Map<String, ImageProcessorConf> avalue)
	{
		imageProcessorConfMap = avalue;
	}
	
	public void setImageProcessorConfs(String avalue)
	{
		this.imageProcessorConfs = avalue;
	}
	
	public boolean isKeepOriginalImage() {
		return keepOriginalImage;
	}

	public void setKeepOriginalImage(boolean keepOriginalImage) {
		this.keepOriginalImage = keepOriginalImage;
	}

	
	
	public String getOriginalImageCategory() {
		return originalImageCategory;
	}

	public void setOriginalImageCategory(String originalImageCategory) {
		this.originalImageCategory = originalImageCategory;
	}

	public List<ImageProcessorConf> getImageProcessorConfigs() {
		return imageProcessorConfigs;
	}

	/*public void setImageProcessorConfigs(
			List<ImageProcessorConf> imageProcessorConfigs) {
		this.imageProcessorConfigs = imageProcessorConfigs;
	}
*/

	public int getHeight() {
		return height;
	}

	public int getPadding() {
		return padding;
	}

	public int getWidth() {
		return width;
	}

	public boolean isAutoScaleImage() {
		return autoScaleImage;
	}

	public boolean isAutoWatermark() {
		return autoWatermark;
	}

	public void setAutoScaleImage(boolean autoScaleImage) {
		this.autoScaleImage = autoScaleImage;
	}

	/**
	 * 目前只能设置是否使用水印功能，不能很具体的控制用什么图片和文字及大小等。可以做到但比较难做好，而且用户很容易配置错误，所以最好是实施的时候设置正确不让客户改。
	 * 小图建议不要加水印，否则用文字只能看到部分；用图片会抛错。
	 * 
	 * @param autoWatermark
	 */
	public void setAutoWatermark(boolean autoWatermark) {
		this.autoWatermark = autoWatermark;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setPadding(int padding) {
		this.padding = padding;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * 为图片所在目录
	 * @return
	 */
	public String getMediaType() {
		return mediaType;
	}

	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		if (imageProcessorConfs != null)
		{
			String[] beans = imageProcessorConfs.split(",");
			for (String beanId: beans)
			{				 
				ImageProcessorConf e = imageProcessorConfMap.get(beanId);
				if(e!=null){
					imageProcessorConfigs.add(e);
				}
			}
		}
	}


}
