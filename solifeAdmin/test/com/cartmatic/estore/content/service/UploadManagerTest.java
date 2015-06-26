package com.cartmatic.estore.content.service;

import java.io.IOException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.cartmatic.estore.framework.test.BaseTransactionalTestCase;

public class UploadManagerTest extends BaseTransactionalTestCase{

	@Autowired
	private UploadManager uploadManager = null;
	

	//@Test
	public void processImage() throws IOException
	{ 
		uploadManager.processImageBatchJob();
	}
	
	@Test
	public void cleanUnusefulImageJob() throws IOException
	{ 
		//清理图片
		uploadManager.cleanUnusefulImageJob();
	}
}
