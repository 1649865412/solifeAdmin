/*
 * Created on Nov 27, 2007
 * 
 */

package com.cartmatic.estore.webapp.action;


public class ImageProcessorConf {
	private boolean	autoScaleImage	= true;
	private boolean	autoWatermark	= false;
	private String	category;
	private String	height;
	private String padding;
	private String width;
	private int heightValue;
	private int widthValue;
	private int paddingValue;

	
	
	public int getHeightValue() {
		return heightValue;
	}

	public int getWidthValue() {
		return widthValue;
	}

	public int getPaddingValue() {
		return paddingValue;
	}

	
	public String getCategory() {
		return category;
	}

	public String getHeight() {
		return height;
	}

	public String getPadding() {
		return padding;
	}

	public String getWidth() {
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

	public void setCategory(String category) {
		this.category = category;
	}

	public void setHeight(String height) {
		this.height = height;
		if (height != null && !height.startsWith("$"))
			this.heightValue = new Integer(height);
	}

	public void setPadding(String padding) {
		this.padding = padding;
		if (padding != null && !padding.startsWith("$"))
			this.paddingValue = new Integer(padding);
	}
	
	public void setWidth(String width) {
		this.width = width;
		if (width != null && !width.startsWith("$"))
			this.widthValue = new Integer(width);
	}
}
