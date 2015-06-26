package com.cartmatic.estore.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
public class DecompressionUtil {
	 /** 
     * 解压zip格式的压缩文件到指定位置 
     * @param zipFileName 压缩文件 
     * @param extPlace 解压目录 
     * @throws Exception 
     */  
    @SuppressWarnings("unchecked")  
    public static void unzip(String zipFileName, String extPlace) throws Exception {  
        try {  
            (new File(extPlace)).mkdirs();  
            File f = new File(zipFileName);  
            ZipFile zipFile = new ZipFile(zipFileName);  
            if((!f.exists()) && (f.length() <= 0)) {  
                throw new Exception("要解压的文件不存在!");  
            }  
            String strPath, gbkPath, strtemp;  
            File tempFile = new File(extPlace);  
            strPath = tempFile.getAbsolutePath();  
            java.util.Enumeration e = zipFile.getEntries();  
            while(e.hasMoreElements()){  
                org.apache.tools.zip.ZipEntry zipEnt = (ZipEntry) e.nextElement();  
                gbkPath=zipEnt.getName();  
                if(zipEnt.isDirectory()){  
                    strtemp = strPath + File.separator + gbkPath;  
                    File dir = new File(strtemp);  
                    dir.mkdirs();  
                    continue;  
                } else {  
                    //读写文件  
                    InputStream is = zipFile.getInputStream(zipEnt);  
                    BufferedInputStream bis = new BufferedInputStream(is);  
                    gbkPath=zipEnt.getName();  
                    strtemp = strPath + File.separator + gbkPath;  
                  
                    //建目录  
                    String strsubdir = gbkPath;  
                    for(int i = 0; i < strsubdir.length(); i++) {  
                        if(strsubdir.substring(i, i + 1).equalsIgnoreCase("/")) {  
                            String temp = strPath + File.separator + strsubdir.substring(0, i);  
                            File subdir = new File(temp);  
                            if(!subdir.exists())  
                            subdir.mkdir();  
                        }  
                    }  
                    FileOutputStream fos = new FileOutputStream(strtemp);  
                    BufferedOutputStream bos = new BufferedOutputStream(fos);  
                    int c;  
                    while((c = bis.read()) != -1) {  
                        bos.write((byte) c);  
                    }  
                    bos.close();  
                    fos.close();  
                }  
            }  
        } catch(Exception e) {  
            e.printStackTrace();  
            throw e;
        }  
    }
}
