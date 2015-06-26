/*
 * Created on Nov 26, 2007
 * 
 */

package com.cartmatic.estore.core.util;

import java.awt.RenderingHints;
import java.awt.image.renderable.ParameterBlock;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.HashMap;

import javax.media.jai.BorderExtenderConstant;
import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;

import org.apache.commons.io.IOUtils;

import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageEncodeParam;
import com.sun.media.jai.codec.ImageEncoder;
import com.sun.media.jai.codec.JPEGEncodeParam;

/**
 * 测试过JPGGIF可以转JPG。
 * 目前是采用简单、系统统一的设置，如果需要不同的设置（如Upload和后台任务），可在Spring配置两套然后注入对应的manager。
 * 
 * @author Ryan
 * 
 */
public class ImageProcessorJaiImpl extends AbstractImageProcessorImpl {
	private static RenderingHints	hints	= new RenderingHints(new HashMap());

	static {
		hints.put(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		hints.put(RenderingHints.KEY_FRACTIONALMETRICS,
				RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		hints.put(RenderingHints.KEY_ALPHA_INTERPOLATION,
				RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		hints.put(RenderingHints.KEY_COLOR_RENDERING,
				RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		hints.put(RenderingHints.KEY_DITHERING,
				RenderingHints.VALUE_DITHER_ENABLE);
		hints.put(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);
	}

	public static void main(String[] args) throws IOException {
		ImageProcessorJaiImpl processor = new ImageProcessorJaiImpl();
		processor.scaleImage("D:/test.jpg", "D:/test_scaled.png", 80, 80, 2);
		processor.scaleImage("D:/test.jpg", "D:/test_scaled.jpg", 200, 200, 2);
		processor.scaleImage("D:/test.jpg", "D:/test_scaled.bmp", 200, 200, 2);
		processor.scaleImage("D:/test.jpg", "D:/test_scaled.tiff", 200, 200, 2);
		processor.scaleImage("D:/mark.gif", "D:/test_gif_scaled.png", 200, 200,
				2);
		processor.setWatermarkText("Just Demo");
		processor.setAlpha(0.3f);
		processor.addWatermark("D:/test.jpg", "D:/test_water_text.jpg");
		processor.setWatermarkImagePath("D:/logo_cartmatic.png");
		processor.addWatermark("D:/test.jpg", "D:/test_water_image.jpg");
		processor.addWatermark("D:/test_scaled.png",
				"D:/test_scaled_water_text.jpg");
	}

	private float	encodeQuality	= 0.95f;

	/**
	 * 填充图片的边缘的空白
	 * 
	 * @param scaledImage
	 * @param horizonalPad
	 * @param verticalPad
	 * @return
	 */
	private PlanarImage doImageBorderPadding(PlanarImage scaledImage,
			BigDecimal horizonalPad, BigDecimal verticalPad) {
		ParameterBlock params = new ParameterBlock();
		params.addSource(scaledImage);
		params.add((int) Math.ceil(horizonalPad.doubleValue()));
		params.add((int) Math.floor(horizonalPad.doubleValue()));
		params.add((int) Math.ceil(verticalPad.doubleValue()));
		params.add((int) Math.floor(verticalPad.doubleValue()));
		int numbands = scaledImage.getSampleModel().getNumBands();
		double fillValue[] = new double[numbands];
		for (int i = 0; i < numbands; i++) {
			fillValue[i] = 255D;
		}

		params.add(new BorderExtenderConstant(fillValue));
		params.add(fillValue);
		return JAI.create("border", params);
	}

	/**
	 * 使用SubsampleAverage而不是scale方式进行缩放，因为据说效果较好
	 * 
	 * @param srcImage
	 * @param scaleFactor
	 * @return
	 */
	private PlanarImage doImageScaling(PlanarImage srcImage, double scaleFactor) {
		ParameterBlock params = new ParameterBlock();
		params.addSource(srcImage);
		params.add(scaleFactor);
		return JAI.create("SubsampleAverage", params, hints);
	}

	/**
	 * 支持网络最常用的JPG和PNG，JAI没有GIF的encoder所以不能支持缩放，BMP和TIFF等太大。
	 * 
	 * @param imageType
	 * @return
	 */
	private ImageEncodeParam getEncodeParams(String imageType) {
		if ("jpeg".equals(imageType)) {
			JPEGEncodeParam _encodeParam = new JPEGEncodeParam();
			_encodeParam.setQuality(encodeQuality);
			return _encodeParam;
		}
		return null;
	}

	/**
	 * 载入原始图片，格式可以是任何支持的类型，包括GIF
	 * 
	 * @param imagePath
	 * @return
	 */
	private PlanarImage loadImage(String imagePath) {
		ParameterBlock pb = (new ParameterBlock()).add(imagePath);
		return JAI.create("fileload", pb);
	}

	/**
	 * 对图片进行缩放＋空白填充处理，缩放后的格式根据destFile后缀自动判断，但调用者应注意过滤不支持的格式（如GIF），推荐是JPG和PNG。如果不支持在encode时会抛异常。
	 */
	public void scaleImage(String srcFile, String destFile, int requiredWidth,
			int requiredHeight, int padding) {
		OutputStream os = null;
		try {
			// 1. 读入图片
			// is = new FileInputStream(srcFile);
			// PlanarImage srcImage = decodeImage(is, imageType);
			PlanarImage srcImage = loadImage(srcFile);
			if (srcImage == null) {
				logger.warn("Image file not found: " + srcFile);
				return;
			}

			// 2.计算宽、高、ScaleFactor（缩放比率）等
			int originalWidth = srcImage.getWidth();
			int originalHeight = srcImage.getHeight();
			int requiredWidthWithPadding = requiredWidth - padding * 2;
			int requiredHeightWithPadding = requiredHeight - padding * 2;
			BigDecimal xScaleFactor = BigDecimal.valueOf(1L);
			BigDecimal yScaleFactor = BigDecimal.valueOf(1L);
			if (requiredWidthWithPadding != 0
					&& requiredWidthWithPadding < originalWidth) {
				xScaleFactor = (new BigDecimal(requiredWidthWithPadding))
						.divide(new BigDecimal(originalWidth), 10, 0);
			}
			if (requiredHeightWithPadding != 0
					&& requiredHeightWithPadding < originalHeight) {
				yScaleFactor = (new BigDecimal(requiredHeightWithPadding))
						.divide(new BigDecimal(originalHeight), 10, 0);
			}

			// 3.对图片进行缩放
			PlanarImage scaledImage = null;
			if (xScaleFactor.compareTo(yScaleFactor) == -1) {
				scaledImage = doImageScaling(srcImage, xScaleFactor
						.doubleValue());
			} else if (xScaleFactor.compareTo(yScaleFactor) == 1) {
				scaledImage = doImageScaling(srcImage, yScaleFactor
						.doubleValue());
			} else if (xScaleFactor.compareTo(yScaleFactor) == 0
					&& xScaleFactor.compareTo(new BigDecimal("0")) == 1) {
				scaledImage = doImageScaling(srcImage, yScaleFactor
						.doubleValue());
			} else {
				scaledImage = srcImage;
			}

			// 4.处理padding/border
			if (requiredWidth > scaledImage.getWidth()
					|| requiredHeight > scaledImage.getHeight() || padding > 0) {
				BigDecimal horizontalPad = new BigDecimal("0");
				BigDecimal verticalPad = new BigDecimal("0");
				if (requiredWidth > scaledImage.getWidth()) {
					horizontalPad = (new BigDecimal(requiredWidth
							- scaledImage.getWidth())).divide(new BigDecimal(
							"2"), 2, 0);
				}
				horizontalPad = horizontalPad.add(new BigDecimal(padding));
				if (requiredHeight > scaledImage.getHeight()) {
					verticalPad = (new BigDecimal(requiredHeight
							- scaledImage.getHeight())).divide(new BigDecimal(
							"2"), 2, 0);
				}
				verticalPad = verticalPad.add(new BigDecimal(padding));
				scaledImage = doImageBorderPadding(scaledImage, horizontalPad,
						verticalPad);
			}

			// 5.保存已缩放的图片,格式由后缀自动决定，但注意不能支持GIF
			String imageType = getImageType(destFile);
			logger.debug("Saving (and encode) scaled image file: " + destFile);
			os = new FileOutputStream(destFile);
			ImageEncoder encoder = ImageCodec.createImageEncoder(imageType, os,
					getEncodeParams(imageType));
			encoder.encode(scaledImage);
		} catch (Throwable e) {
			throw new RuntimeException(
					"Unexpected error (maybe a invalid/bad jpg file) when processing image:"
							+ srcFile, e);
		} finally {
			IOUtils.closeQuietly(os);
		}
	}

	/**
	 * 设置压缩图片的质量（仅对JPEG有效），从0-1.0之间,越大质量越好但文件越大，缺省0.95f。
	 * 
	 * @param encodeQuality
	 */
	public void setEncodeQuality(float encodeQuality) {
		this.encodeQuality = encodeQuality;
	}

	@Override
	public void scaleImageGIF(String srcFile, String destFile, int requiredWidth, int requiredHeight, int padding) {
		// TODO Auto-generated method stub
		
	}

}
