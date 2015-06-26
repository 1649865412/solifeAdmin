package com.cartmatic.estore.core.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cartmatic.estore.common.helper.ConfigUtil;


public class ImageProcessorImageMagickImpl implements ImageProcessor {
	private String quality;
	private String watermarkFilePath = ConfigUtil.getInstance().getMediaStorePath()+"/default/watermark.png";
	private String imageMagickCommand="";
	private static final Log logger	= LogFactory.getLog(ImageProcessorImageMagickImpl.class);
	
	public void addWatermark(String srcImagePath, String destImagePath)
			throws IOException {
		// command$>composite -gravity center HMD_600.png m93199-01.jpg m93199-01.jpg 
		String command = imageMagickCommand.replaceAll("convert", "composite -gravity center ");
		command += watermarkFilePath + " " +srcImagePath +" " + destImagePath;
		
		try {
			Process process=Runtime.getRuntime().exec(command);
			BufferedReader in = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			String errorMsg=null;
			int errorIndex=0;
			while((errorMsg = in.readLine()) != null){
				if(errorIndex==0){
					logger.warn(command);
				}
				errorIndex++;
				logger.warn(errorMsg);
			}
				
		} catch (IOException e) {
	           logger.error("ImageMagick convert image error."+command);
	           logger.error(e.getMessage());
	          //e.printStackTrace();
	    }
	}

	public void scaleImage(String srcFile, String destFile, int requiredWidth,
			int requiredHeight, int padding) {
		StringBuffer command =new StringBuffer(imageMagickCommand);
		//C:/Program Files (x86)/ImageMagick-6.6.0-Q16/convert -sharpen 1 -resize 165X165  -quality 80 d:/temp/100319/m40047_00.jpg d:/temp/100319/m40047_23.jpg
		///usr/local/bin/convert -sharpen 1 -resize 200x200 -extent 200x200 -gravity center test.jpg test_4.jpg
		//convert org.jpg -thumbnail "350x350>" -gravity center -extent 350x350 -compress JPEG -quality 75 t001.jpg
		//convert -thumbnail "350x350>" -gravity center -extent 350x350 -compress JPEG -quality 75  D:/Pictures/test/Chrysanthemum.jpg D:/Pictures/test/Chrysanthemum4.jpg
		command.append(" -thumbnail ");
		command.append(requiredWidth);
		command.append("x");
		command.append(requiredHeight);
		command.append("> -gravity center -extent ");
		command.append(requiredWidth);
		command.append("x");
		command.append(requiredHeight);
		command.append(" -compress JPEG ");
		 
		if(StringUtils.isNotBlank(quality)){
			command.append("-quality ");
			command.append(quality);
			command.append(" ");
		}
		command.append(srcFile);
		command.append(" ");
		command.append(destFile);
		try {
				logger.debug(command.toString());
				Process process=Runtime.getRuntime().exec(command.toString());
				BufferedReader in = new BufferedReader(new InputStreamReader(process.getErrorStream()));
				String errorMsg=null;
				while((errorMsg = in.readLine()) != null)
					logger.warn(errorMsg);
//				process.waitFor();
	       } catch (IOException e) {
	           logger.error("ImageMagick convert image error."+command.toString());
	           logger.error(e.getMessage());
	           //e.printStackTrace();
	       }
	}
	
	public void scaleImageGIF(String srcFile, String destFile, int requiredWidth,
			int requiredHeight, int padding) {
		StringBuffer command =new StringBuffer(imageMagickCommand);

		//convert 

		//D:\Repositories\workspace\asset\assets\media\productMedia\v\00\05\37\111.gif 

		//-coalesce 

		//-thumbnail 50x50 -layers optimize 

		//D:/Repositories/workspace/asset/assets/media/productMedia/a/00/05/37/111.gif
		
		command.append(" " + srcFile + " ");
		command.append("-coalesce ");
		command.append("-resize ");
		command.append(requiredWidth);
		command.append("x");
		command.append(requiredHeight);
		command.append(" ");
		command.append(destFile);
		
		try {
				logger.debug(command.toString());
				Process process=Runtime.getRuntime().exec(command.toString());
				BufferedReader in = new BufferedReader(new InputStreamReader(process.getErrorStream()));
				String errorMsg=null;
				while((errorMsg = in.readLine()) != null)
					logger.warn(errorMsg);
//				process.waitFor();
	       } catch (IOException e) {
	           logger.error("ImageMagick convert image error."+command.toString());
	           logger.error(e.getMessage());
	           //e.printStackTrace();
	       }
	}

	
	public String getQuality() {
		return quality;
	}

	public void setQuality(String quality) {
		this.quality = quality;
	}
	
	public void setImageMagickCommand(String imageMagickCommand) {
		logger.info("config imageMagickCommand:" +imageMagickCommand);
		this.imageMagickCommand = imageMagickCommand;
	}

}
