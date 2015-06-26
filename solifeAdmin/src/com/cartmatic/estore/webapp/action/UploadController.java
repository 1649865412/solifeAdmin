/*
 * Created on Oct 25, 2007
 * 
 */

package com.cartmatic.estore.webapp.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import com.cartmatic.estore.common.helper.ConfigUtil;
import com.cartmatic.estore.content.service.UploadManager;
import com.cartmatic.estore.core.controller.BaseController;
import com.cartmatic.estore.webapp.util.RequestUtil;

public class UploadController extends BaseController {
	
	private UploadManager uploadManager;

	private long							maxImageSize		= 100 * 1024;

	private long							maxUploadSize		= 20 * 1024 * 1024;

	private MultipartResolver				multipartResolver;

	private boolean isUuidRename = false;

	/**
	 * 保存上传的文件并返回资源的URL。可支持多个，但目前的使用方式都是一个。可支持中文文件名。可支持分类别存放。类别下会再按日期存放。
	 * 如果同一天内同名文件已经存在，会自动改名。
	 * 
	 * 配合ocupload.js暂时只能支持每一次upload上传一个文件
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView defaultAction(HttpServletRequest request,
			HttpServletResponse response) {
		String uploadWidget=request.getParameter("uploadWidget");
		if(StringUtils.equalsIgnoreCase(uploadWidget,"SWFUpload")){
			return uploadFileBySwfUpload(request, response);
		}else{
			return uploadFileByIframeUpload(request, response);
		}
	}
	
	
	private ModelAndView uploadFileBySwfUpload(HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> model = new HashMap<String, Object>();
		List<String> uploadedFiles = new ArrayList<String>();
		String uploadMsg = "";
		String filePath = null;
		String mediaType = null;
		if (multipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest fileRequest = null;
			try {
				fileRequest = multipartResolver.resolveMultipart(request);
				Iterator<String> iter = fileRequest.getFileNames();
				mediaType = getParameter(request,"mediaType","other");
				String objId = getParameter(request,"objId", null);
				while (iter.hasNext()) {
					String prmName = iter.next();
					MultipartFile file = fileRequest.getFile(prmName);
					if (file.isEmpty()) {
						continue;
					}
					if (file.getSize() > maxUploadSize) {
						uploadMsg = "err001";
						continue;
					}
					ImageConf imageConf=uploadManager.getImageConf(mediaType);
					if(imageConf==null){
						imageConf=uploadManager.getImageConf("other");
					}
					String fileName = file.getOriginalFilename();
					String fileImageSize = getParameter(request, "fileImageSize", null);
					FileInfo fileInfo = null;
					if(fileImageSize == null || mediaType.equals("other") || mediaType.equals("a_and_d")){
						fileInfo = new FileInfo(imageConf.getMediaType(), imageConf.getOriginalImageCategory(), fileName,objId);
					} else {
						fileInfo = new FileInfo(imageConf.getMediaType(), fileImageSize, fileName,objId);
					}
					File pfile = null;
					boolean fileExists = false;
					if (isUuidRename){
						fileInfo.uuidRename();
					}
					do {
						filePath = getFullFilePath(fileInfo.getMediaPath());
						pfile = new File(filePath);
						fileExists = pfile.exists();
						if (fileExists) {
							if (isUuidRename){
								fileInfo.uuidRename();
							}else{
								logger.info(formatMsg("File already exists: %1, auto rename...",filePath));
								fileInfo.autoRename();
							}
						}
					} while (fileExists);
					pfile.mkdirs();
					file.transferTo(pfile);
					// 处理缩放和水印，并删除原文件
					uploadManager.processImage(pfile, imageConf,fileInfo) ;
					// 不管什么情况下，都只返回原图的url就可以。
					uploadedFiles.add(fileInfo.getSampleMediaPath());
				}
			} catch (Throwable ex) {
			    uploadMsg = "err002";
				logger.error(formatMsg("Unexpected error when handling uploaded file %1, cause is: %2.",
		                        filePath, ex.getMessage()), ex);
			} finally {
				if (fileRequest != null) {
					multipartResolver.cleanupMultipart(fileRequest);
				}
			}
		} else {
		    uploadMsg = "err002";
			logger.warn("Invalid request, MultipartHttpServletRequest expected, found: " + request.getClass().getName() + ".\n"+ RequestUtil.getRequestInfo(request));
		}
		
		model.put("url", uploadedFiles.get(0));
		model.put("mediaType", mediaType);
		model.put("msg", uploadMsg);
		JSONObject jsonMap = JSONObject.fromObject(model);
		try {
			response.getWriter().print(jsonMap.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}


	private ModelAndView uploadFileByIframeUpload(HttpServletRequest request,HttpServletResponse response) {

		Map<String, Object> model = new HashMap<String, Object>();
		List<String> uploadedFiles = new ArrayList<String>();
		
		String uploadMsg = "";
		String filePath = null;
		if (multipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest fileRequest = null;
			try {
				fileRequest = multipartResolver.resolveMultipart(request);
				Iterator<String> iter = fileRequest.getFileNames();
				String mediaType = getParameter(request,"mediaType","other");
				String objId = getParameter(request,"objId", null);
				while (iter.hasNext()) {
					String prmName = iter.next();
					MultipartFile file = fileRequest.getFile(prmName);
					if (file.isEmpty()) {
						continue;
					}
					if (file.getSize() > maxUploadSize) {
						uploadMsg = "err001";
						continue;
					}
					ImageConf imageConf=uploadManager.getImageConf(mediaType);
					if(imageConf==null){
						imageConf=uploadManager.getImageConf("other");
					}
					String fileName = file.getOriginalFilename();
					FileInfo fileInfo = new FileInfo(imageConf.getMediaType(), imageConf.getOriginalImageCategory(),fileName,objId);
					File pfile = null;
					boolean fileExists = false;
					do {
						filePath = getFullFilePath(fileInfo.getMediaPath());
						pfile = new File(filePath);
						fileExists = pfile.exists();
						if (isUuidRename)
						{
							fileInfo.uuidRename();
						}
						else if (pfile.exists()) {
							logger.info(formatMsg("File already exists: %1, auto rename...",filePath));
							fileInfo.autoRename();
						}
					} while (fileExists);
					pfile.mkdirs();
					file.transferTo(pfile);
					// 处理缩放和水印，并删除原文件
					uploadManager.processImage(pfile, imageConf,fileInfo) ;
					// 不管什么情况下，都只返回原图的url就可以。
					uploadedFiles.add(fileInfo.getSampleMediaPath());
				}
			} catch (Throwable ex) {
			    uploadMsg = "err002";
				logger.error(formatMsg("Unexpected error when handling uploaded file %1, cause is: %2.",
		                        filePath, ex.getMessage()), ex);
			} finally {
				if (fileRequest != null) {
					multipartResolver.cleanupMultipart(fileRequest);
				}
			}
		} else {
		    uploadMsg = "err002";
			logger.warn("Invalid request, MultipartHttpServletRequest expected, found: "
		                    + request.getClass().getName()
		                    + ".\n"+ RequestUtil.getRequestInfo(request));
		}
		model.put("fileInputId",request.getParameter("fileInputId"));
		model.put("uploadedFiles", uploadedFiles);
		model.put("uploadMsg", uploadMsg);
		model.put("onCompleteHandler",request.getParameter("onCompleteHandler"));
		return new ModelAndView("content/uploadResults", model);
	}


	protected String getFullFilePath(String relativePath) {
		return new StringBuilder(getMediaStorePath()).append("/").append(relativePath).toString();
	}

	

	protected String getMediaStorePath() {
		return ConfigUtil.getInstance().getMediaStorePath();
	}



	
	public void setIsUuidRename(boolean avalue)
	{
		isUuidRename = avalue;
	}
	
	public void setMultipartResolver(MultipartResolver multipartResolver) {
		this.multipartResolver = multipartResolver;
	}
	
	public void setUploadManager(UploadManager avalue)
	{
		uploadManager = avalue;
	}


	@Override
	protected Map<Integer, Map<String, Object>> getMultiSaveModel(
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	protected void initController() throws Exception {
		// TODO Auto-generated method stub
	}
	
	public ModelAndView test(HttpServletRequest request,HttpServletResponse response) {
		uploadManager.cleanUnusefulImageJob();
		return null;
	}
}