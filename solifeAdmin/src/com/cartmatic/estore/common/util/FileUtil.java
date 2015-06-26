
package com.cartmatic.estore.common.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.regex.Pattern;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.cartmatic.estore.Constants;

public class FileUtil {
	private static Logger	log	= Logger.getLogger(FileUtil.class);

	/**
	 * 会复制隐藏文件，暂时无法控制不复制这些文件。
	 * 
	 * @param srcDir
	 * @param destDir
	 * @throws IOException
	 */
	public static void copyDirectory(File srcDir, File destDir)
			throws IOException {
		FileUtils.copyDirectory(srcDir, destDir);
	}

	public static void copyDirectory(String srcDir, String destDir)
			throws IOException {
		FileUtils.copyDirectory(new File(srcDir), new File(destDir));
	}

	public static void copyFile(File srcFile, File destFile) throws IOException {
		FileUtils.copyFile(srcFile, destFile);
	}

	/*
	 * public static void cpFilesToDirectory(String srcPath, String destPath,
	 * long lastModify) throws IOException { cpFilesToDirectory(new
	 * File(srcPath), new File(destPath), lastModify); }
	 */

	/*
	 * public static void cpFilesToDirectory(File srcPath, File destPath, long
	 * lastModify) throws IOException { cpFilesToDirectory(srcPath, destPath,
	 * false, lastModify, null); }
	 */

	public static int countDirectoryLevel(String path) {
		return StringUtils.countMatches(path, "/");
	}

	/*
	 * public static void cpFilesToDirectoryWithZip(String srcPath, String
	 * destPath, long lastModify) throws IOException {
	 * cpFilesToDirectoryWithZip(new File(srcPath), new File(destPath),
	 * lastModify, null); }
	 */

	public static void cpFilesToDirectory(File srcPath, File destPath)
			throws IOException {
		cpFilesToDirectory(srcPath, destPath, false, -1, null);
	}

	/*
	 * public static void cpFilesToDirectoryWithZip(File srcPath, File destPath,
	 * long lastModify, String[] excluce) throws IOException {
	 * cpFilesToDirectory(srcPath, destPath, true, lastModify, null); }
	 */

	/**
	 * copy files to a directory. Can use zip code.
	 * 
	 * @param isZip
	 *            true is use zip.
	 * @param destPath
	 * @param srcPath
	 * @throws Exception
	 */
	public static void cpFilesToDirectory(File srcPath, File destPath,
			boolean isZip, long lastModify, String[] exclude)
			throws IOException {
		if (srcPath.isDirectory()) {
			if (!destPath.exists()) {
				destPath.mkdir();
			}
			destPath = new File(destPath, srcPath.getName());
			if (!destPath.exists()) {
				destPath.mkdir();
			}

			String files[] = srcPath.list();
			for (int i = 0; i < files.length; i++) {
				File srcFile = new File(srcPath, files[i]);
				if (matchExclude(srcFile.getPath(), exclude)) {
					continue;
				}
				if (srcFile.isDirectory()) {
					// cpFilesToDirectory(srcFile, new File(destPath, files[i]),
					// isZip, lastModify);
					cpFilesToDirectory(srcFile, destPath, isZip, lastModify,
							exclude);
				} else {
					// cpFilesToDirectory(srcFile, destPath, isZip, lastModify);
					cpFilesToDirectory(srcFile, destPath, isZip, lastModify,
							exclude);
				}
			}
		} else
		// copy file only
		{
			if (matchExclude(srcPath.getPath(), exclude)) {
				return;
			}
			if (lastModify > 0 && srcPath.lastModified() < lastModify) {
				return;
			}
			if (!srcPath.exists()) {
				if (log.isDebugEnabled()) {
					log.debug("File or directory does not exist.["
							+ srcPath.getPath() + "]");
				}
				return;
			} else {
				if (!destPath.exists()) {
					destPath.mkdir();
				}

				InputStream in = new FileInputStream(srcPath);
				OutputStream out = null;
				if (isZip) {
					out = new GZIPOutputStream(new FileOutputStream(new File(
							destPath, srcPath.getName())));
				} else {
					out = new BufferedOutputStream(new FileOutputStream(
							new File(destPath, srcPath.getName())));
				}

				// Transfer bytes from in to out
				byte[] buf = new byte[1024];
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				in.close();
				out.close();

			}
		}
		if (log.isDebugEnabled()) {
			log.debug("Directory copied.[" + srcPath.getPath() + "]");
		}
	}

	/**
	 * copy file usage:
	 * copyFilesToDirectory("D:/website/testApache/categorys_long_1_3.zip",
	 * "d:/temp"); copyFilesToDirectory("D:/website/testApache", "d:/temp");
	 * 
	 * @param destPath
	 *            dist Directory
	 * @param srcPath
	 *            srcPath can use file's path or directory's path.
	 * @throws IOException
	 */
	public static void cpFilesToDirectory(String srcPath, String destPath)
			throws IOException {
		cpFilesToDirectory(new File(srcPath), new File(destPath));
	}

	public static void cpFilesToDirectoryWithZip(File srcPath, File destPath)
			throws IOException {
		cpFilesToDirectory(srcPath, destPath, true, -1, null);
	}

	/**
	 * copy with zip encode.
	 * 
	 * @param srcPath
	 * @param destPath
	 * @throws IOException
	 */
	public static void cpFilesToDirectoryWithZip(String srcPath, String destPath)
			throws IOException {
		cpFilesToDirectoryWithZip(new File(srcPath), new File(destPath));
	}

	public static void deleteDirectory(String dir) throws IOException {
		FileUtils.deleteDirectory(new File(dir));
	}

	public static void delFile(String fileName) {
		File file = new File(formatPath(fileName));
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				delFile(files[i].getPath());
			}
			file.delete();
		} else if (file.exists()) {
			file.delete();
		}
	}
	/**
	 * 
	 * @param str
	 * @param hashPathLength 长度
	 * @param itemLength 每一个的长度
	 * @param fillStr 填充的字符
	 * @return
	 */
	public static String hashPath(String str,int hashPathLength,int itemLength,String fillStr){
		StringBuffer objIdBuff=new StringBuffer();
		//id长度不足的补零
		int addZeroCount=hashPathLength-str.length();
		for (int i = 0; i < addZeroCount; i++) {
			objIdBuff.append(fillStr);
		}
		objIdBuff.append(str);
		StringBuffer temp=new StringBuffer();
		for (int i = 0; i < objIdBuff.length(); i+=itemLength) {
			if(i+itemLength<objIdBuff.length()){
				temp.append(objIdBuff.substring(i, i+itemLength));
			}else{
				temp.append(objIdBuff.substring(i));
			}
			temp.append("/");
		}
		return temp.toString();
	}

	public static String formatPath(String path) {
		return formatPath(new StringBuilder(path)).toString();
	}

	public static StringBuilder formatPath(StringBuilder path) {
		int idx = 0;
		while ((idx = path.indexOf("//")) >= 0) {
			path.deleteCharAt(idx);
		}
		while ((idx = path.indexOf("\\")) >= 0) {
			path.replace(idx, idx + 1, "/");
		}
		while ((idx = path.indexOf("//")) >= 0) {
			path.deleteCharAt(idx);
		}
		return path;
	}

	public static List<File> listFiles(List<File> fileList, File file,
			FileFilter filter) {
		if (file.isDirectory()) {
			fileList.add(file);
			File[] subFiles = file.listFiles(filter);
			for (int i = 0; i < subFiles.length; i++) {
				File subFile = subFiles[i];
				if (subFile.isDirectory() && !subFile.isHidden()) {
					listFiles(fileList, subFile, filter);
				}
			}
			for (int i = 0; i < subFiles.length; i++) {
				File subFile = subFiles[i];
				if (subFile.isFile() && !subFile.isHidden()) {
					fileList.add(subFile);
				}
			}
		}

		return fileList;
	}

	public static void main(String[] args) {
		try {
			// copyDirectory("d:/temp/test1", "d:/temp/test2");
			deleteDirectory("d:/temp/test2");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static boolean matchExclude(String pathName, String[] exclude) {
		if (exclude != null) {
			for (int i = 0; i < exclude.length; i++) {
				if (pathName.matches(exclude[i])) {
					return true;
				}
			}
		}
		return false;
	}

	public static String readFileToString(String filePath) throws IOException {
		return FileUtils.readFileToString(new File(filePath),
				Constants.DEFAULT_ENCODING);
	}

	public static boolean validateFileName(String fileName) {
		String reg = "[^\\w\\_\\.\\-\\\\+\\=]";
		boolean result = Pattern.compile(reg).matcher(fileName).find();
		return !result;
	}

	public static void writeStringToFile(String filePath, String data)
			throws IOException {
		FileUtils.writeStringToFile(new File(filePath), data,
				Constants.DEFAULT_ENCODING);
	}
	
	/**
	 * inputStream 生成文件
	 * @param filePath
	 * @param is
	 * @throws IOException
	 */
	public static void writeInputStreamToFile(String filePath, InputStream is) throws IOException
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
	
	public static File searchOneFile(File directory,final String regex){
		File files[]=directory.listFiles(new FilenameFilter(){
			private Pattern pattern = Pattern.compile(regex);
			@Override
			public boolean accept(File dir, String name) {
				 return pattern.matcher( name).matches();
			}
		});
		if(files.length>0){
			return files[0];
		}
		File directorys[]=directory.listFiles(new FileFilter(){
			@Override
			public boolean accept(File pathname) {
				return pathname.isDirectory();
			}
		});
		for (File f : directorys) {
			File result=searchOneFile(f, regex);
			if(result!=null){
				return result;
			}
		}
		return null;
	}
}
