
package com.cartmatic.estore.catalog.util;

import java.io.File;

import org.junit.Test;


public class ImageClearTest  {
//	private ProductMediaManager	productMediaManager;
//
//	public void setProductMediaManager(ProductMediaManager productMediaManager) {
//		this.productMediaManager = productMediaManager;
//	}
	@Test
	public  void testClearFiles() {
		String path = "//192.168.16.226/d$/Tomcat-trunk/webapps/StoreFront/media/product/090218";
		File dir = new File(path);
		for (File file : dir.listFiles()) {
			if (!file.getName().endsWith("_md.jpg")
					&& !file.getName().endsWith("_mn.jpg")) {
				if (file.getName().length() >= 18) {
					
//					fileName = file.getName().substring(0,file.getName().length()-4)+"_md.jpg";
//					System.out.println("delete file "+fileName+" "+new File(fileName).delete());
//					fileName = file.getName().substring(0,file.getName().length()-4)+"_mn.jpg";
//					System.out.println("delete file "+fileName+" "+new File(fileName).delete());
					System.out.println("delete file "+file.getName()+" "+file.delete());
				}else{
					System.out.println("ignore file "+file.getName()+" ");
				}
			}else{
				System.out.println("delete file "+file.getName()+" "+file.delete());
			}
			
		}
	}
	
		
	

	// @Test
	// public void testRenameFiles(){
	// File dir = new File("C:/090218/");
	// System.out.println("length=="+dir.list().length);
	// String now = DateUtil.convertDateToString(new
	// Date(),"yyyy-MM-dd-HH-mm-ss");
	// int i=1;
	// for(File file : dir.listFiles()){
	// if(file.getName().endsWith("_md.jpg")|file.getName().endsWith("_mn.jpg")){
	// //清除以mn、md结尾的文件
	// System.out.println("to delete file~~~"+file.getName());
	// }else{
	// //重命名
	// System.out.println("before file=="+file.getName());
	// File newFile = new File(file.getParent()+"\\"+now+"_"+i+".jpg");
	// while(newFile.exists()){
	// //当文件名已经存在,重新构造文件名
	// i++;
	// newFile = new File(file.getParent()+"\\"+now+"_"+i+".jpg");
	// }
	// if(file.renameTo(newFile)){
	// System.out.println("after file=="+newFile.getName());
	// }else{
	// System.out.println("!!!!!! rename fail!!!!");
	// }
	// i++;
	// }
	// }
	// }

}
