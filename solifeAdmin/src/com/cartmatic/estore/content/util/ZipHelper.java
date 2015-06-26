package com.cartmatic.estore.content.util;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Set;
import java.util.zip.Adler32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cartmatic.estore.common.util.FileUtil;


public class ZipHelper {
	/*****************************************************
	 * This method decompress the zip file.
	 * The basePath must a file system path at localhost.
	 * It like 'c:\\temp\\" in windows.
	 * It like '/temp/' in linux or unix.
	 * @param dataSource
	 * @param basePath
	 *****************************************************/
	private static final Log log = LogFactory.getLog(ZipHelper.class);
	
	public void decompress(String sourcePath,String directPath,boolean over)
	throws Exception{
		File file=new File(sourcePath);
		
		if(!file.exists())
			throw new Exception("File not Exists!");
		if(file.isDirectory()){
			File[] fs=file.listFiles();
			for(int i=0;i<fs.length;i++){
				if(this.isZip(fs[i])){
					FileInputStream in=new FileInputStream(fs[i]);
					
					if(!directPath.endsWith("/"))
						directPath+="/";
					if(over){
						FileUtil.delFile(directPath);
						}
					ZipHelper.decompress(in,directPath);
					fs[i].delete();
				}
					
			}
			
		}
	   	
		
	}
	
	public void decompress(String sourcePath,String directPath)
	throws Exception{
		File file=new File(sourcePath);
		if(!file.exists())
			throw new Exception("File not Exists!");
		if(file.isDirectory()){
			File[] fs=file.listFiles();
			for(int i=0;i<fs.length;i++){
				if(this.isZip(fs[i])){
					FileInputStream in=new FileInputStream(fs[i]);
					if(log.isDebugEnabled()){
						log.debug("zip Helper....."+fs[i].getName());
					}
					if(!directPath.endsWith("/"))
						directPath+="/";
					ZipHelper.decompress(in,directPath);
					File clearFile=new File(directPath+fs[i].getName());
					if(clearFile.exists())
						clearFile.delete();
				}
					
			}
			
		}
		
		
	}
	
	public static void decompress(InputStream dataSource,String basePath)
	throws Exception{
		File file=new File(basePath);
		if(!file.exists())
			file.mkdirs();
		ZipInputStream zin=new ZipInputStream(dataSource);
		if(zin.available()==0)
			return;
		ZipEntry entry;
		while((entry=zin.getNextEntry())!=null){
			
			if(!entry.isDirectory()){ //create File
				String[] nameTest=entry.getName().split("/");
				if(!FileUtil.validateFileName(entry.getName())){
					continue;
				}
				for(int j=0;j<nameTest.length-1;j++){
					file=new File(basePath+"/"+nameTest[j]);
					if(!file.exists())
						file.mkdirs();
					
				}
				byte[] buf=new byte[1024];
				if(!FileUtil.validateFileName(entry.getName())){
					continue;
				}
				file=new File(basePath+"/"+entry.getName());
				try{
					BufferedOutputStream out=new BufferedOutputStream(new FileOutputStream(file));
					int c;
					while((c=zin.read(buf))!=-1){
						out.write(buf,0,c);
					}
					out.close();
					
				}
				catch(Exception e){}
				
			}else{//create folder
				file=new File(basePath+"/"+entry.getName());
				if(!file.exists())
					file.mkdirs();
			}
		}
		zin.closeEntry();
		zin.close();
		
	}
	public void compress(String sourcePath,String zipName)
	throws Exception {
		FileOutputStream f=new FileOutputStream(zipName);
		CheckedOutputStream cs=new CheckedOutputStream(f,new Adler32());
		ZipOutputStream zos=new ZipOutputStream(cs);
	    makeZipHelper(zos,sourcePath,null);
	    zos.flush();
	    zos.finish();
		zos.close();
	}
	
	public void compress(String sourcePath,String zipName,Set disables)
	throws Exception {
		FileOutputStream f=new FileOutputStream(zipName);
		CheckedOutputStream cs=new CheckedOutputStream(f,new Adler32());
		ZipOutputStream zos=new ZipOutputStream(cs);
	    makeZipHelper(zos,sourcePath,null,disables);
	    zos.flush();
	    zos.finish();
		zos.close();
	}
	
	private void makeZipHelper(ZipOutputStream zout,String sourcePath,String path)
	throws Exception{
		File file=new File(sourcePath);
		String[] files=file.list();
		if(files!=null){
			for(int i=0;i<files.length;i++){
				
				File f=new File(file,files[i]);
				String currentPath=f.getName();
				if(path!=null&&!"".equals(path))
					currentPath=path+"/"+currentPath;
				if(f.isDirectory()){
					makeZipHelper(zout,f.getPath(),currentPath);
				}else{
					this.putEntry(zout,f,currentPath);
				}
			}
		}
		
	}
	
	
	private void makeZipHelper(ZipOutputStream zout,String sourcePath,String path,Set disables)
	throws Exception{
		File file=new File(sourcePath);
		String[] files=file.list();
		if(files!=null){
			for(int i=0;i<files.length;i++){
				
				File f=new File(file,files[i]);
				if(!f.exists())
					continue;
				String currentPath=f.getName();
				if(path!=null&&!"".equals(path))
					currentPath=path+"/"+currentPath;
				if(disables.contains(f.getName().toUpperCase()))
					continue;
				if(f.isDirectory()){
					makeZipHelper(zout,f.getPath(),currentPath,disables);
				}else{
					this.putEntry(zout,f,currentPath);
				}
			}
		}
			
	}
	
	private void putEntry(ZipOutputStream zout,File file,String entryName)
	throws Exception{
		int c;
		byte[] buf=new byte[1024];
		ZipEntry entry=new ZipEntry(entryName);
		zout.putNextEntry(entry);
		FileInputStream in=new FileInputStream(file);
		while((c=in.read(buf))!=-1){
			zout.write(buf,0,c);
		}
		zout.closeEntry();
		in.close();
	}
	/**
	 * 
	 * @param f
	 */
	private boolean isZip(File f){
		boolean result=false;
		String fileName=f.getName();
		if(fileName.toUpperCase().endsWith(".ZIP"))
			result=true;
		
		return result;
	}
	
}
