/*
 * Created on Sep 27, 2007
 * 
 */

package com.cartmatic.estore.core.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

public class FtpUtil {
	interface FtpCallback {
		Object executeFtpCommand(FTPClient ftp) throws IOException;
	}

	private static Logger	logger		= Logger.getLogger(FtpUtil.class);

	/**
	 * main - Unit test program
	 * 
	 * @param args
	 *            Command line arguments
	 * 
	 */
	public static void main(String args[]) {
		try {
			FtpUtil ftpUtil = new FtpUtil();
			ftpUtil.uploadFile("d:/list.txt", "/test/test1/test2/list2.txt");
		} catch (Exception e) {
			logger.error("Error: " + e.toString());
		}

	}

	private String			ftpHost		= "192.168.16.225";

	private String			ftpPassword	= "ftptest";

	private String			ftpUserName	= "ftptest";

	protected void autoCreateDirectory(FTPClient ftp,
			final String remoteFilePath) throws IOException {
		int idx1 = 0;
		while (true) {
			int idx2 = remoteFilePath.indexOf("/", idx1 + 1);
			if (idx2 > idx1) {
				// 创建从根开始的整个路径所需的目录
				String subPath = remoteFilePath.substring(0, idx2);
				logger.info("Auto creating sub directory (if not exists): "
						+ subPath);
				ftp.makeDirectory(subPath);
				idx1 = idx2;
			} else {
				break;
			}
		}

	}

	public void downloadFile(final String localFilePath,
			final String remoteFilePath) throws IOException {
		openFtpConnection(new FtpCallback() {
			public Object executeFtpCommand(FTPClient ftp) throws IOException {
				boolean retValue = ftp.retrieveFile(remoteFilePath,
						new FileOutputStream(localFilePath));
				if (!retValue) {
					throw new IOException(
							"Downloading of remote file failed. ftp.retrieveFile() returned false."
									+ remoteFilePath);
				}
				return null;
			}
		});
	}

	public void listFtpDirectory(final String remoteFilePath)
			throws IOException {
		openFtpConnection(new FtpCallback() {
			public Object executeFtpCommand(FTPClient ftp) throws IOException {
				// Get the list of files on the remote server
				FTPFile files[] = ftp.listFiles();

				if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
					throw new IOException(
							"Unable to get list of files to dowload.");
				}

				// TODO 返回文件的更多信息
				List<String> fileList = new ArrayList<String>();
				if (files.length == 0) {
					logger
							.debug("No files are available in this remote directory: "
									+ remoteFilePath);
				} else {
					for (int i = 0; i < files.length; i++) {
						fileList.add(files[i].getName());
					}
				}

				return fileList;
			}
		});
	}

	/**
	 * TODO 实现连接池，以提高性能（不需要每次都建立连接）
	 * 
	 * @param callback
	 * @throws IOException
	 */
	public void openFtpConnection(FtpCallback callback) throws IOException {
		// Create a Jakarta Commons Net FTP Client object
		FTPClient ftp = new FTPClient();

		// A datatype to store responses from the FTP server

		// Connect to the server
		ftp.connect(ftpHost);

		// After connection attempt, you should check the reply code to verify
		// success.
		if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
			try {
				ftp.disconnect();
			} catch (Exception e) {
				logger
						.error(
								"Unable to disconnect from FTP server after server refused connection. ",
								e);
			}
			throw new IOException("FTP server refused connection.");
		}
		if (logger.isTraceEnabled()) {
			logger.trace("Connected to " + ftpHost + ". "
					+ ftp.getReplyString());
		}

		// Try to login
		if (!ftp.login(ftpUserName, ftpPassword)) {
			throw new IOException(
					"Unable to login to FTP server using username and password:"
							+ ftpUserName + "/" + ftpPassword);
		}

		if (logger.isTraceEnabled()) {
			logger.trace("Remote system is " + ftp.getSystemName() + " "
					+ ftp.getReplyString());
		}

		// Set our file transfer mode to either ASCII or Binary
		ftp.setFileType(FTP.BINARY_FILE_TYPE);

		// 执行具体的FTP命令（里面可以执行多个命令）
		callback.executeFtpCommand(ftp);

		// Disconnect from the FTP server
		try {
			ftp.disconnect();
		} catch (Exception e) {
			logger.error("Unable to disconnect from FTP server. " + e);
		}

	}

	public void removeFtpFile(final String remoteFilePath) throws IOException {
		openFtpConnection(new FtpCallback() {
			public Object executeFtpCommand(FTPClient ftp) throws IOException {
				boolean retValue = ftp.deleteFile(remoteFilePath);
				if (!retValue) {
					throw new IOException(
							"Removing of remote file failed. ftp.deleteFile() returned false."
									+ remoteFilePath);
				}
				return null;
			}
		});
	}

	public void setFtpHost(String ftpHost) {
		this.ftpHost = ftpHost;
	}

	public void setFtpPassword(String ftpPassword) {
		this.ftpPassword = ftpPassword;
	}

	public void setFtpUserName(String ftpUserName) {
		this.ftpUserName = ftpUserName;
	}

	public void uploadFile(final InputStream localFileInputStream,
			final String remoteFilePath) throws IOException {
		openFtpConnection(new FtpCallback() {
			public Object executeFtpCommand(FTPClient ftp) throws IOException {
				if (logger.isDebugEnabled()) {
					logger.debug("Uploading file as remote filename: "
							+ remoteFilePath);
				}
				boolean retValue = ftp.storeFile(remoteFilePath,
						localFileInputStream);
				if (!retValue) {
					// 可能是上级目录不存在，自动创建。TODO 可抽取出来用参数控制是否自动创建上级目录。
					autoCreateDirectory(ftp, remoteFilePath);
					// 创建所有目录后再次重试，如果还是出错就抛出异常
					retValue = ftp.storeFile(remoteFilePath,
							localFileInputStream);
					if (!retValue) {
						throw new IOException(
								"Storing of remote file failed. ftp.storeFile()"
										+ " returned false.");
					}
				}

				return null;
			}

		});
	}

	public void uploadFile(final String localFilePath,
			final String remoteFilePath) throws IOException {
		uploadFile(new FileInputStream(localFilePath), remoteFilePath);
	}
}
