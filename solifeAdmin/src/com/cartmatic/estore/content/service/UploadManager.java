package com.cartmatic.estore.content.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.cartmatic.estore.webapp.action.FileInfo;
import com.cartmatic.estore.webapp.action.ImageConf;

public interface UploadManager {
	public void processImage(File pfile, ImageConf imageConf,FileInfo fileInfo)	throws IOException;

	
	/**
	 * 根据图片path,删除相应的图片,包括处理生成的图片
	 * @param imageUrl(/media/product/080303/07-02.jpg)
	 * @return
	 */
	public List<String> deleteImage(String imageUrl);
	
	public void processImageBatchJob() throws IOException;
	
	/**
	 * 获取图片处理配置
	 * @param imageType
	 * @return
	 */
	public ImageConf getImageConf(String imageType);
	
	/**
	 * 处理单张图片
	 * @param imagePath 图片路径,相对路径
	 * @param mediaType
	 * @return
	 * @throws IOException
	 */
	public boolean processImage(String imagePath,String mediaType) throws IOException;
	
	/**
	 * 清理没有使用的图片(产品,产品媒体)
	 */
	public void cleanUnusefulImageJob();
}
