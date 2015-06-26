package com.cartmatic.estore.supplier.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.common.helper.ConfigUtil;
import com.cartmatic.estore.common.model.supplier.Supplier;
import com.cartmatic.estore.common.model.supplier.SupplierProduct;
import com.cartmatic.estore.common.model.supplier.TbCatPropValueRefer;
import com.cartmatic.estore.common.model.supplier.TbCategoryRefer;
import com.cartmatic.estore.common.util.DateUtil;
import com.cartmatic.estore.common.util.FileUtil;
import com.cartmatic.estore.content.service.UploadManager;
import com.cartmatic.estore.supplier.service.ImportSupplierProductManager;
import com.cartmatic.estore.supplier.service.SupplierProductManager;
import com.cartmatic.estore.supplier.service.TbCatPropReferManager;
import com.cartmatic.estore.supplier.service.TbCategoryReferManager;
import com.cartmatic.extend.supplier.util.TaoBaoApiHelper;

public class ImportSupplierProductManagerImpl implements ImportSupplierProductManager {
	private static final Log logger	= LogFactory.getLog(ImportSupplierProductManagerImpl.class);
	private SupplierProductManager supplierProductManager;
	
	private TbCatPropReferManager tbCatPropReferManager;
	
	private TbCategoryReferManager tbCategoryReferManager;
	
	private UploadManager uploadManager;
	
	public void setTbCatPropReferManager(TbCatPropReferManager tbCatPropReferManager) {
		this.tbCatPropReferManager = tbCatPropReferManager;
	}

	public void setTbCategoryReferManager(
			TbCategoryReferManager tbCategoryReferManager) {
		this.tbCategoryReferManager = tbCategoryReferManager;
	}

	public void setUploadManager(UploadManager uploadManager) {
		this.uploadManager = uploadManager;
	}
	
	public void setSupplierProductManager(
			SupplierProductManager supplierProductManager) {
		this.supplierProductManager = supplierProductManager;
	}
	
	private String localizeDescriptionImages(Supplier supplier,String productCode,String description) {
		Document document=Jsoup.parse(description);
		Elements image_elements=document.select("img");
		String date=DateUtil.convertDateToString(new Date(), "yyMMdd");
		//String storeFrontInstallationPath=ConfigUtil.getInstance().getStoreFrontInstallationPath();
		String mediaPath=ConfigUtil.getInstance().getMediaStorePath();
		for (Element image_element : image_elements) {
			String imageUrl=image_element.attr("src");
			System.out.println(imageUrl);
			String fileName=imageUrl.substring(imageUrl.lastIndexOf("/")+1);
			if(fileName.indexOf("?")!=-1){
				fileName=fileName.substring(0,fileName.indexOf("?"));
			}
			String path=mediaPath+"/other/"+date+"/"+supplier.getSupplierCode()+"/"+productCode+"/"+fileName;
			try {
				URL url = new URL(imageUrl);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setConnectTimeout(10000);
				connection.setReadTimeout(10000);
				InputStream is = connection.getInputStream();
				FileUtil.writeInputStreamToFile(path, is);
				connection.disconnect();
				url = null;
				image_element.attr("src", path);
			} catch (Exception e) {
				logger.warn(e.getMessage());
			}
		}
		return document.body().html();
	}
	
	private String localizeProductImages(String importImageDirectoryPath,Supplier supplier,String productCode,String images) {
		System.out.println("============="+images);
		if(images.startsWith("cf4847de1cf1e266a523c5bf9f15bda2")){
			System.out.println("");
		}
		List<String>imageList=new ArrayList<String>();
		//String storeFrontInstallationPath=ConfigUtil.getInstance().getStoreFrontInstallationPath();
		String mediaPath=ConfigUtil.getInstance().getMediaStorePath();
		String date=DateUtil.convertDateToString(new Date(), "yyMMdd");
		String temp_images[]=images.split(";");
		for (int i = 0; i < temp_images.length; i++) {
			String temp_1p_image=temp_images[i];
			String temp_1p_image_s[]=temp_1p_image.split("\\|");
			String temp_p_image1=temp_1p_image_s[0].split(":")[0];
			String imagePath="";
			if(temp_1p_image_s.length>1){
				String temp_p_image2=temp_1p_image_s[1];
				String fileName=temp_p_image2.substring(temp_p_image2.lastIndexOf("/")+1);
				if(fileName.indexOf("?")!=-1){
					fileName=fileName.substring(0,fileName.indexOf("?"));
				}
				imagePath=date+"/"+supplier.getSupplierCode()+"/"+productCode+"/"+fileName;
				try {
					URL url = new URL(temp_p_image2);
					HttpURLConnection connection = (HttpURLConnection) url.openConnection();
					connection.setConnectTimeout(10000);
					connection.setReadTimeout(10000);
					InputStream is = connection.getInputStream();
					FileUtil.writeInputStreamToFile(mediaPath+"/supplierProduct/v/"+imagePath, is);
					connection.disconnect();
					url = null;
					imageList.add(imagePath);
				} catch (Exception e) {
					logger.warn(e.getMessage());
				}
			}
			if(StringUtils.isBlank(imagePath)){
				File imageFile=FileUtil.searchOneFile(new File(importImageDirectoryPath), temp_p_image1+".tbi");
				if(imageFile!=null){
					imagePath=date+"/"+supplier.getSupplierCode()+"/"+productCode+"/"+temp_p_image1+".jpg";
					File imageFile2=new File(mediaPath+"/supplierProduct/v/"+imagePath);
					try {
						FileUtils.copyFile(imageFile, imageFile2);
					} catch (IOException e) {
						e.printStackTrace();
					}
					imageList.add(imagePath);
				}
			}
			if(StringUtils.isNotBlank(imagePath)){
				try {
					uploadManager.processImage(imagePath, "supplierProduct");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return StringUtils.join(imageList.toArray(new String[]{}),",")+",";
	}

	@Override
	public void doImportSupplierProduct(Supplier supplier, String importFilePath,String productName,String productCode,Long tb_cid, String price, String description,String image,String tb_catProps, String tb_sell_catProps, Long tb_id) {
		// TODO Auto-generated method stub
		if(supplier==null)return ;
		SupplierProduct supplierProduct=new SupplierProduct();
		boolean isNew=false;
		supplierProduct.setProductCode(supplier.getSupplierCode()+productCode);
		SupplierProduct temp_supplierProduct=supplierProductManager.getSupplierProductBySupplierIdAndProductCode(supplier.getSupplierId(), supplierProduct.getProductCode());
		if(temp_supplierProduct!=null){
			supplierProduct=temp_supplierProduct;
		}else{
			isNew=true;
		}
		supplierProduct.setProductName(productName);
		supplierProduct.setTbCId(tb_cid);
		supplierProduct.setWholesalePrice("1:"+price+",");
		
		description=localizeDescriptionImages(supplier,productCode,description);
		
		supplierProduct.setProductDesc(description);
		image=localizeProductImages(importFilePath,supplier, productCode, image);
		supplierProduct.setMediaUrl(image);
		supplierProduct.setTbCatProps(tb_catProps);
		supplierProduct.setTbSellCatProps(tb_sell_catProps);
		supplierProduct.setTbId(tb_id);
		supplierProduct.setSupplier(supplier);
		supplierProduct.setStatus(Constants.STATUS_NOT_PUBLISHED);
		
		System.out.println(supplierProduct.getMediaUrl());
		System.out.println(supplierProduct.getTbSellCatProps());
		supplierProductManager.save(supplierProduct);
		
		
		TbCategoryRefer tbCategoryRefer=tbCategoryReferManager.addTbCategoryRefer(tb_cid);
		
		List<TbCatPropValueRefer> tbCatPropValueReferList=TaoBaoApiHelper.getInstance().strToTbCatPropValueRefer(tb_sell_catProps);
		
		//tb_sell_catProps 淘宝销售属性组合,机选择性选项属性
		tbCatPropReferManager.addTbCatPropRefers(tb_cid, tbCatPropValueReferList);
		
		if(!isNew){
			supplierProductManager.setUploadLogs(supplierProduct);
		}
	}
	
}
