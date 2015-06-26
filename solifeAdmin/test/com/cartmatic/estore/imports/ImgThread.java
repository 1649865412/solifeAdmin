package com.cartmatic.estore.imports;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class ImgThread implements Runnable{
	private Logger		logger					= Logger.getLogger(ImgThread.class);
	private String fileUrl;
	private String saveFilePath;
	private Integer downCount=1;
	/**
	 * 直接指定保存文件名，为null的用会原来的
	 */
	private String fileName;
	
	public ImgThread(String fileUrl,String saveFilePath,String fileName){
		this.fileUrl=fileUrl;
		this.saveFilePath=saveFilePath;
		this.fileName=fileName;
	}

	public void run() {
		URL url;
		try {
			url = new URL(fileUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			List<String>refs=new ArrayList<String>();
			connection.setRequestProperty("Referer", "http://www.sw-box.com/Hollowed-Flower-Pattern-Plating-Hard-Case-For-Samsung-Galaxy-Note-2-N7100-Red.html");  
//			refs.add("http://www.sw-box.com/Hollowed-Flower-Pattern-Plating-Hard-Case-For-Samsung-Galaxy-Note-2-N7100-Red.html");
//			connection.getHeaderFields().put("Referer", refs);
			connection.setConnectTimeout(15000);
			connection.setReadTimeout(15000); 
			InputStream is = connection.getInputStream();
			if(connection.getContentLength()<1000){
				downCount=100;
				throw new RuntimeException("down imagae less 1000s");
			}
			String path=url.getPath();
			if(fileName==null){
				fileName="";
			}else if(StringUtils.isBlank(fileName)){
				fileName=path.substring(path.lastIndexOf("/")+1);
				if(fileName.indexOf("?")!=-1){
					fileName=fileName.substring(0,fileName.indexOf("?"));
				}
			}
			writeInputStreamToFile(saveFilePath+fileName, is);
			connection.disconnect();
			url = null;
		} catch (Throwable e) {
			downCount++;
			if(e.getMessage()!=null&&e.getMessage().equals("down imagae less 1000s")){
				logger.warn("url:"+fileUrl+"__"+e.getLocalizedMessage()+"____"+e);
				throw new RuntimeException(e);
			}
			if(downCount>10){
				logger.warn("url:"+fileUrl+"__"+downCount+"___"+e.getLocalizedMessage()+"____"+e);
				e.printStackTrace();
				throw new RuntimeException("down imagae error");
			}else{
				logger.warn("url:"+fileUrl+"__"+"down:"+downCount+"___"+e.getLocalizedMessage()+"____"+e);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				run();
			}
		}
	}
	
	private static void writeInputStreamToFile(String filePath, InputStream is) throws IOException
	{
		int bufSize = 2048;
		File file = new File(filePath);
		File parent = new File(file.getParent());
		if (!parent.exists()) //检查文件目录是否存在
		{
			parent.mkdirs();
		}
		OutputStream out = new FileOutputStream(file);
		byte[] buffer = new byte[bufSize];
        int len;
        int cacheSize = 0; //cache size for CacheInputStream.
        while ((len = is.read(buffer))>0) 
        {
        	cacheSize += len;
        	out.write(buffer, 0, len);
        	if (len != bufSize)
        	{
        		byte[] tmp = new byte[len];
        		System.arraycopy(buffer, 0, tmp, 0, len);
        	}
        	else
        	{
        		buffer = new byte[bufSize];
        	}
		}
        out.close();
        is.close();
		//FileUtils.writeByteArrayToFile(file, data);
	}

}
