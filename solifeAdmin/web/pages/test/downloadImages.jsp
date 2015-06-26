<%@page import="java.io.OutputStream"%>
<%@page import="java.io.IOException"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.io.FileOutputStream"%>
<%@page import="java.net.HttpURLConnection"%>
<%@page import="java.net.URL"%>
<%@page import="org.apache.commons.io.FileUtils"%>
<%@page import="java.util.concurrent.ScheduledThreadPoolExecutor"%>
<%@page import="java.util.List"%>
<%@page import="common.Logger"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="java.io.File"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="cartmatic" tagdir="/WEB-INF/tags/cartmatic"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Testing page</title>
		<%@ include file="/decorators/include/styles.jspf"%>
		<%@ include file="/decorators/include/javascripts.jspf"%>
	</head>
	<body>
		<form action="">
			<input type="text" name="filePath" value="" style="width: 450px;">
			<input type="submit" value="提交">
		</form>
		<%!
class ImgThread implements Runnable{
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
	
	private void writeInputStreamToFile(String filePath, InputStream is) throws IOException
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
		 %>
		<%!
		class DownloadThankoProduct
{
	Logger log=Logger.getLogger(DownloadThankoProduct.class);

    ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(8);
	public void downloadImage(String imageUrl,String saveFilePath,String fileName) throws Exception{
		exec.submit(new ImgThread(imageUrl, saveFilePath, fileName));
		while (true)
		{
			if (exec.getCorePoolSize() - exec.getActiveCount() == 0)
			{
				Thread.sleep(1000);
			}
			else
			{
				break;
			}
		}
	}
	
	public void downloadImages(File imageUrlFile) throws Exception{
		exec.setContinueExistingPeriodicTasksAfterShutdownPolicy(true);
		exec.setExecuteExistingDelayedTasksAfterShutdownPolicy(true);
		List<String>urls=FileUtils.readLines(imageUrlFile); 
		exec.getCorePoolSize();
		String path=imageUrlFile.getParentFile().getAbsolutePath();
		for (String imageUrl : urls)
		{
			if(StringUtils.isNotBlank(imageUrl)){
				URL url = new URL(imageUrl);
				String imgUrl = url.getPath();
				//String imgUrl = url.getPath().substring(1);
				//imgUrl=imgUrl.substring(imgUrl.indexOf("/")+1);
				File file=new File(path+"/images/"+imgUrl);
				log.warn(imageUrl+"\t"+file.exists());
				//System.out.println(imageUrl+"\t"+file.exists());
				if((!file.exists())){
					downloadImage(imageUrl, path+"/images/"+imgUrl, null);
				}
			}
		}

		log.warn("finish download.........................................................");
//		System.out.println("end..............................................................");
		exec.shutdown(); //关闭后不能加入新线程，队列中的线程则依次执行完
		while(exec.getPoolSize()!=0){
			Thread.sleep(500);
		}
		log.warn("main thread end!");
//	    System.out.println("main thread end!");
		try
		{
			Thread.sleep(30000);
		}
		catch (Exception e1)
		{
			e1.printStackTrace();
		}
		exec.getCorePoolSize();
		System.out.println("ok........................................");
	}
	
}
		
		 %>
		test test
		<%
			final String filePath=request.getParameter("filePath");
			System.out.println("filePath:"+filePath);
			if(StringUtils.isNotBlank(filePath)){
				Thread dt=new Thread(new Runnable()
				{
					public void run() {
						File imageUrlFile=new File(filePath);
						if(imageUrlFile.exists()&&imageUrlFile.isFile()){
							DownloadThankoProduct download=new DownloadThankoProduct();
							try
							{
								System.out.println("start download...");
								download.downloadImages(imageUrlFile);
							}
							catch (Exception e)
							{
								e.printStackTrace();
							}
						}
					}
				});
				dt.start();
			}
		%>
	    <%@ include file="/decorators/include/uiResource.jspf"%>
	</body>
</html>