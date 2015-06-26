
package com.cartmatic.estore.content.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cartmatic.estore.catalog.service.ProductMediaManager;
import com.cartmatic.estore.catalog.service.ProductSkuManager;
import com.cartmatic.estore.common.helper.ConfigUtil;
import com.cartmatic.estore.common.util.FileUtil;
import com.cartmatic.estore.content.service.UploadManager;
import com.cartmatic.estore.core.util.ImageProcessor;
import com.cartmatic.estore.webapp.action.FileInfo;
import com.cartmatic.estore.webapp.action.ImageConf;
import com.cartmatic.estore.webapp.action.ImageProcessorConf;


public class UploadManagerImpl implements UploadManager 
{

	protected final transient Log logger = LogFactory.getLog(getClass());
	private Map<String, ImageConf>	categoryImageConfs;

	private ImageProcessor					imageProcessor;
	
	/**
	 * key 表示要处理的图片.特定的,ProductImage,ProductMediaImage
	 * value图片类型
	 */
	private Map<String,String>	cleanUnusefulImageTypes;
	
	private ProductSkuManager productSkuManager=null;
	
	private ProductMediaManager productMediaManager=null;
	
	public void setProductMediaManager(ProductMediaManager productMediaManager) {
		this.productMediaManager = productMediaManager;
	}

	public void setProductSkuManager(ProductSkuManager productSkuManager) {
		this.productSkuManager = productSkuManager;
	}

	public void setCleanUnusefulImageTypes(Map<String, String> cleanUnusefulImageTypes) {
		this.cleanUnusefulImageTypes = cleanUnusefulImageTypes;
	}

	public ImageConf getImageConf(String imageType){
		return categoryImageConfs.get(imageType);
	}

	/*
	 * 处理图片
	 */
	public void processImage(File pfile, ImageConf imageConf,FileInfo fileInfo)throws IOException {
		String filePath = pfile.getAbsolutePath();
		if("jpg".equals(fileInfo.getSuffix())){
			List<ImageProcessorConf>imageProcessorConfList=imageConf.getImageProcessorConfigs();
			if(imageProcessorConfList!=null&&imageProcessorConfList.size()>0){
				for (ImageProcessorConf imageProcessorConf : imageProcessorConfList) {
					String destFile = fileInfo.getMediaPath(imageProcessorConf.getCategory());
					destFile=getFullFilePath(destFile);
					checkImageExists(destFile);
					if(imageProcessorConf.isAutoScaleImage()){
						imageProcessor.scaleImage(filePath, destFile, imageProcessorConf.getWidthValue(), imageProcessorConf.getHeightValue(), imageProcessorConf.getPaddingValue());
					}
					if (imageProcessorConf.isAutoWatermark()) {
						imageProcessor.addWatermark(destFile, destFile);
					}
				}
				//TODO 为可以立刻删除文件
				System.gc();
			}
		}
		if("gif".equals(fileInfo.getSuffix())){
			List<ImageProcessorConf>imageProcessorConfList=imageConf.getImageProcessorConfigs();
			if(imageProcessorConfList!=null&&imageProcessorConfList.size()>0){
				for (ImageProcessorConf imageProcessorConf : imageProcessorConfList) {
					String destFile = fileInfo.getMediaPath(imageProcessorConf.getCategory());
					destFile=getFullFilePath(destFile);
					checkImageExists(destFile);
					if(imageProcessorConf.isAutoScaleImage()){
						imageProcessor.scaleImageGIF(filePath, destFile, imageProcessorConf.getWidthValue(), imageProcessorConf.getHeightValue(), imageProcessorConf.getPaddingValue());
					}
					if (imageProcessorConf.isAutoWatermark()) {
						imageProcessor.addWatermark(destFile, destFile);
					}
				}
				//TODO 为可以立刻删除文件
				System.gc();
			}
		}
	}
	
	private boolean checkImageExists(String filePath){
		File tempfile = new File(filePath);
		boolean exists=tempfile.exists();
		if(!exists){
			File mkdirFile=new File(filePath.substring(0, filePath.lastIndexOf("/")));
			if(!mkdirFile.exists())
				mkdirFile.mkdirs();
		}
		return exists;
	}
	
	private String getFullFilePath(String relativePath) {
		return new StringBuilder(ConfigUtil.getInstance().getMediaStorePath()).append("/").append(relativePath).toString();
	}
	

	/**
	 * 设置图片处理的配置列表，使用ImageConf，一个类别（如product）可配置多次处理。
	 * @param imageConfs
	 */
	public void setImageConfs(List<ImageConf> imageConfs) {
		categoryImageConfs = new HashMap<String, ImageConf>();
		for (Iterator<ImageConf> iter = imageConfs.iterator(); iter.hasNext();) {
			ImageConf conf = iter.next();
			categoryImageConfs.put(conf.getMediaType(), conf);
		}
	}


	public void setImageProcessor(ImageProcessor imageProcessor) {
		this.imageProcessor = imageProcessor;
	}


	public void processImageBatchJob() throws IOException {
		String mediaStorePath=ConfigUtil.getInstance().getMediaStorePath();
		if(this.categoryImageConfs!=null && this.categoryImageConfs.size()>0){
			Set<String> mediaTypes=categoryImageConfs.keySet();
			for (String mediaType : mediaTypes) {
				ImageConf imageConf=categoryImageConfs.get(mediaType);
				List<ImageProcessorConf> imageProcessorConfList=imageConf.getImageProcessorConfigs();
				String originalImageCategory=imageConf.getOriginalImageCategory();
				if(StringUtils.isNotBlank(originalImageCategory)&&imageProcessorConfList!=null&&imageProcessorConfList.size()>0){
					String categoryPath =mediaStorePath+"/"+imageConf.getMediaType()+"/"+originalImageCategory+"/";
					File file = new File(categoryPath);
					listFiles(file, imageConf);
				}
			}
		}
	}
	
	
	private void listFiles(File file,ImageConf imageConf) throws IOException 
	{
		if (file.isDirectory()) 
		{
			File[] subFiles = file.listFiles();
			if(subFiles!=null){
				for (int i = 0; i < subFiles.length; i++) {
					File subFile = subFiles[i];
					listFiles(subFile,imageConf);
				}
			}
		}
		else
		{
			if(file.getAbsoluteFile()!=null&&file.getAbsolutePath().toLowerCase().endsWith(".jpg")){
				listProcessImage(file, imageConf);
			}
		}
	}


	private void listProcessImage(File file,ImageConf imageConf) throws IOException {
		String mediaStorePath=ConfigUtil.getInstance().getMediaStorePath();
		String filePath =mediaStorePath+"/"+imageConf.getMediaType()+"/"+imageConf.getOriginalImageCategory()+"/";
		List<ImageProcessorConf> imageProcessorConfList=imageConf.getImageProcessorConfigs();
		for (ImageProcessorConf imageProcessorConf : imageProcessorConfList) {
			String destFilePath =mediaStorePath+"/"+imageConf.getMediaType()+"/"+imageProcessorConf.getCategory()+"/";
			destFilePath=FileUtil.formatPath(destFilePath);
			String destFile=FileUtil.formatPath(file.getAbsolutePath()).replaceAll(filePath, destFilePath);
			checkImageExists(destFile);
			if(imageProcessorConf.isAutoScaleImage()){
				imageProcessor.scaleImage(file.getAbsolutePath(), destFile, imageProcessorConf.getWidthValue(), imageProcessorConf.getHeightValue(), imageProcessorConf.getPaddingValue());
			}
			if (imageProcessorConf.isAutoWatermark()) {
				imageProcessor.addWatermark(destFile, destFile);
			}
		}
	}
	
	public boolean processImage(String imagePath,String mediaType) throws IOException{
		boolean result=false;
		if(this.categoryImageConfs!=null && this.categoryImageConfs.size()>0){
			ImageConf imageConf=categoryImageConfs.get(mediaType);
			if(imageConf!=null){
				String mediaStorePath=ConfigUtil.getInstance().getMediaStorePath();
				String filePath =mediaStorePath+"/"+imageConf.getMediaType()+"/"+imageConf.getOriginalImageCategory()+"/"+imagePath;
				File imageFile=new File(filePath);
				if(imageFile.exists()){
					listProcessImage(imageFile, imageConf);
					return true;
				}
			}
		}
		return result;
	}

	public List<String> deleteImage(String imageUrl) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void cleanUnusefulImageJob() {
		if (logger.isDebugEnabled()){
			logger.debug("cleanUnusefulImageJob begin...");
			logger.debug("cleanUnusefulImageTypes :"+cleanUnusefulImageTypes);
		}
		if(cleanUnusefulImageTypes!=null){
			String mediaStorePath=ConfigUtil.getInstance().getMediaStorePath();
			if (logger.isDebugEnabled()){
				logger.debug("mediaStorePath:"+mediaStorePath);
			}
			//清理产品图片(主图)
			String imageType_product=cleanUnusefulImageTypes.get("ProductImage");
			if(StringUtils.isNotBlank(imageType_product)){
				ImageConf imageConf=categoryImageConfs.get(imageType_product);
				//以原图为基准
				String filePath =mediaStorePath+"/"+imageConf.getMediaType()+"/"+imageConf.getOriginalImageCategory()+"/";
				cleanImageFiles("ProductImage",new File(filePath),imageConf);
			}

			//清理更多媒体图片(其他图片)
			String imageType_productMedia=cleanUnusefulImageTypes.get("ProductMediaImage");
			if(StringUtils.isNotBlank(imageType_product)){
				ImageConf imageConf=categoryImageConfs.get(imageType_productMedia);
				//以原图为基准
				String filePath =mediaStorePath+"/"+imageConf.getMediaType()+"/"+imageConf.getOriginalImageCategory()+"/";
				cleanImageFiles("ProductMediaImage",new File(filePath),imageConf);
			}
		}
	}
	/**
	 * 检查图片是否被引用
	 * @param imageType
	 * @param saveFilePath
	 * @return
	 */
	private boolean existImageReference(String imageType,String saveFilePath){
		boolean exist=true;
		if(imageType.equals("ProductImage")){
			exist=productSkuManager.existImageReference(saveFilePath);
		}else if(imageType.equals("ProductMediaImage")){
			exist=productMediaManager.existImageReference(saveFilePath);
		}
		return exist;
	}
	private void cleanImageFiles(String imageType,File file,ImageConf imageConf){
		if (logger.isDebugEnabled()){
			if(file!=null){
				logger.debug("file:"+file.getAbsolutePath()+"\t"+file.exists());
			}
		}
		if(file!=null&&file.exists()){
			if(file.isDirectory()){
				File[] subFiles = file.listFiles();
				if(subFiles!=null){
					for (int i = 0; i < subFiles.length; i++) {
						File subFile = subFiles[i];
						cleanImageFiles(imageType,subFile,imageConf);
					}
				}
				deleteImageDirector(file, imageConf);
			}else{
				//只处理jpg的
				if(file.getAbsoluteFile()!=null&&file.getAbsolutePath().toLowerCase().endsWith(".jpg")){
					String mediaStorePath=ConfigUtil.getInstance().getMediaStorePath();
					String filePath =mediaStorePath+"/"+imageConf.getMediaType()+"/"+imageConf.getOriginalImageCategory()+"/";
					filePath=FileUtil.formatPath(filePath);
					String saveFilePath=FileUtil.formatPath(file.getAbsolutePath()).replaceAll(filePath, "");
					boolean exist=existImageReference(imageType,saveFilePath);
					if(!exist){
						List<ImageProcessorConf> imageProcessorConfigs=imageConf.getImageProcessorConfigs();
						//删除其他尺寸的图片
						for (ImageProcessorConf imageProcessorConf : imageProcessorConfigs){
							String otherFilePath =mediaStorePath+"/"+imageConf.getMediaType()+"/"+imageProcessorConf.getCategory()+"/"+saveFilePath;
							File otherFile=new File(otherFilePath);
							if(otherFile.exists()&&otherFile.isFile()){
								otherFile.delete();
							}
						}
						//删除原图
						file.delete();
						if (logger.isDebugEnabled()){
							logger.debug("deleted...");
						}
					}
				}
			}
		}
	}
	
	/**
	 * 清理空文件夹
	 * @param file
	 * @param imageConf
	 */
	private void deleteImageDirector(File file,ImageConf imageConf){
		File imageDirector=new File(file.getAbsolutePath());
		if(imageDirector!=null&&imageDirector.isDirectory()){
			//当图片文件夹为空的，文件夹也删除（原图）
			File[] subFiles=imageDirector.listFiles();
			if(subFiles==null||subFiles.length==0){
				imageDirector.delete();
				String mediaStorePath=ConfigUtil.getInstance().getMediaStorePath();
				String filePath=FileUtil.formatPath(file.getAbsolutePath());
				//其他尺寸的图片文件夹也直接删除
				List<ImageProcessorConf> imageProcessorConfigs=imageConf.getImageProcessorConfigs();
				for (ImageProcessorConf imageProcessorConf : imageProcessorConfigs){
					String imageDirectorPath =mediaStorePath+"/"+imageConf.getMediaType()+"/"+imageConf.getOriginalImageCategory();
					String otherImageDirectorPath_temp =mediaStorePath+"/"+imageConf.getMediaType()+"/"+imageProcessorConf.getCategory();
					String otherImageDirectorPath=filePath.replaceAll(imageDirectorPath, otherImageDirectorPath_temp);
					
					File otherImageDirector=new File(otherImageDirectorPath);
					if(otherImageDirector!=null&&otherImageDirector.isDirectory()){
						otherImageDirector.delete();
					}
				}
			} 
		}
		
	}
	
}
