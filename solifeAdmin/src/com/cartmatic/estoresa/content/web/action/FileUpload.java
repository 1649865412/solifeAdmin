/*
 * Create Time 2006-6-7
 *
 */
package com.cartmatic.estoresa.content.web.action;

/**
 * @author Luo Shunkui
 *
 */
public class FileUpload {
	private byte[] file;
	private Integer mediaTypeId;
	private String mediaName;
	private String mediaDescription;
	private Integer mediaWidth;
	private Integer mediaHeight;
	private String mediaPath;
	private String mediaUrl;
	

	public byte[] getFile() {
		return file;
	}
	public void setFile(byte[] file) {
		this.file = file;
	}
	public String getMediaDescription() {
		return mediaDescription;
	}
	public void setMediaDescription(String mediaDescription) {
		this.mediaDescription = mediaDescription;
	}
	public Integer getMediaHeight() {
		return mediaHeight;
	}
	public void setMediaHeight(Integer mediaHeight) {
		this.mediaHeight = mediaHeight;
	}
	public String getMediaName() {
		return mediaName;
	}
	public void setMediaName(String mediaName) {
		this.mediaName = mediaName;
	}
	public String getMediaPath() {
		return mediaPath;
	}
	public void setMediaPath(String mediaPath) {
		this.mediaPath = mediaPath;
	}
	public Integer getMediaTypeId() {
		return mediaTypeId;
	}
	public void setMediaTypeId(Integer mediaTypeId) {
		this.mediaTypeId = mediaTypeId;
	}
	public String getMediaUrl() {
		return mediaUrl;
	}
	public void setMediaUrl(String mediaUrl) {
		this.mediaUrl = mediaUrl;
	}
	public Integer getMediaWidth() {
		return mediaWidth;
	}
	public void setMediaWidth(Integer mediaWidth) {
		this.mediaWidth = mediaWidth;
	}
}
