/*
 * Created on Nov 26, 2007
 * 
 */

package com.cartmatic.estore.core.util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageProcessorAwtImpl extends AbstractImageProcessorImpl {
	public static void main(String[] args) {
		String[] formats = ImageIO.getWriterFormatNames();
		for (int i = 0; i < formats.length; i++) {
			System.out.println(formats[i]);
		}

		ImageProcessorAwtImpl impl = new ImageProcessorAwtImpl();
		impl.generateThumbnail("d:/test.jpg", 200, 200);

	}

	public void generateThumbnail(String srcFile, int requiredWidth,
			int requiredHeight) {
		scaleImage(srcFile, srcFile.substring(0, srcFile.lastIndexOf('.'))
				+ "_thumb" + srcFile.substring(srcFile.lastIndexOf('.')),
				requiredWidth, requiredHeight, 0);
	}

	public void scaleImage(String srcFile, String destFile, int requiredWidth,
			int requiredHeight, int padding) {

		BufferedImage bis = null;
		try {
			bis = ImageIO.read(new File(srcFile));
			if (bis == null) {
				throw new RuntimeException("Invalid source image file: "
						+ srcFile);
			}
		} catch (IOException ioe) {
			throw new RuntimeException("Error reading source image file: "
					+ srcFile);
		}

		// resize
		int thumbWidth = requiredWidth;
		int thumbHeight = requiredHeight;
		double thumbRatio = (double) thumbWidth / (double) thumbHeight;
		int imageWidth = bis.getWidth(null);
		int imageHeight = bis.getHeight(null);
		double imageRatio = (double) imageWidth / (double) imageHeight;
		if (thumbRatio < imageRatio) {
			thumbHeight = (int) (thumbWidth / imageRatio);
		} else {
			thumbWidth = (int) (thumbHeight * imageRatio);
		}

		try {
			BufferedImage bid = new BufferedImage(thumbWidth, thumbHeight,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D g = bid.createGraphics();
			g.drawImage(bis, 0, 0, thumbWidth, thumbHeight, null);
			g.dispose();
			ImageIO.write(bid, getImageType(destFile), new File(destFile));
		} catch (Exception e) {
			throw new RuntimeException(
					"Error generating thumbnail for source image file: "
							+ srcFile);
		}
	}

	@Override
	public void scaleImageGIF(String srcFile, String destFile, int requiredWidth, int requiredHeight, int padding) {
		// TODO Auto-generated method stub
		
	}
}
