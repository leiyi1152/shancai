package com.icloud.common.ftp;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

import com.icloud.common.util.ConfigUtil;


public class FtpUtils {
	private final static Logger logger = Logger.getLogger(FtpUtils.class);
	
	public static final String FONT_ENCODING = "UTF-8";
	public static final String ENCODING_ISO88591 = "ISO-8859-1";
	public static final String SEPARATOR_CHAR = "/";
	private static String server = ConfigUtil.get("server");
	private static int port = Integer.valueOf(ConfigUtil.get("port"));
	private static String user = ConfigUtil.get("user");
	private static String password = ConfigUtil.get("password");
	private static FTPClient ftpClient;
	
	
	
	/**
	 * 
	 * @Title: connectServer
	 * @Description: 根据FTP配置连接并登录到FTP服务器
	 * @return boolean
	 * @date Aug 2, 2011 3:34:57 PM
	 */
	public static FTPClient connectServer() {
		
		ftpClient = new FTPClient();
		ftpClient.setControlEncoding(FONT_ENCODING);
		StringBuffer sb = new StringBuffer();
		sb.append("[ip:").append(server);
		sb.append(", port:").append(port);
		sb.append(", userName:").append(user);
		sb.append(", password:").append(password);
		sb.append(", workDirectory:").append(password).append("]");

		boolean connectResult = false;
		try {
			ftpClient.connect(server, port);
			if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
				connectResult = ftpClient.login(user, password);
			} else {
				ftpClient.disconnect();
				logger.info("connect FTP " + sb + " failed，return code："
						+ ftpClient.getReplyCode());
			}
		} catch (SocketException e) {
			e.printStackTrace();
			logger.warn("connect FTP " + sb + "failed，socked timeout.");
		} catch (IOException e) {
			logger.warn("connect FTP" + sb + "failed，cannot open socket");
		}
		return connectResult ? ftpClient : null;
	}

	public static FTPClient connectServers(String server, int port, String user,
			String password) {
		ftpClient = new FTPClient();
		ftpClient.setControlEncoding(FONT_ENCODING);
		StringBuffer sb = new StringBuffer();
		sb.append("[ip:").append(server);
		sb.append(", port:").append(port);
		sb.append(", userName:").append(user);
		sb.append(", password:").append(password);
		sb.append(", workDirectory:").append(password).append("]");

		boolean connectResult = false;
		try {
			ftpClient.connect(server, port);
			if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
				connectResult = ftpClient.login(user, password);
			} else {
				ftpClient.disconnect();
				logger.info("connect FTP " + sb + " failed，return code："
						+ ftpClient.getReplyCode());
			}
		} catch (SocketException e) {
			logger.warn("connect FTP " + sb + "failed，socked timeout.");
		} catch (IOException e) {
			logger.warn("connect FTP" + sb + "failed，cannot open socket");
		}
		return connectResult ? ftpClient : null;
	}

	/**
	 * 下载文件夹
	 * 
	 * @param localPath
	 *            本地路径 文件夹
	 * @param remoteFile
	 *            远程 路径 文件夹
	 * @throws IOException
	 */
	public static void downloadDirectory(String localPath, String remoteFile)
			throws IOException {
		ftpClient = connectServer();
		ftpClient.enterLocalPassiveMode();
		ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);

		String fileName = new File(remoteFile).getName();
		localPath = localPath + "/" + fileName + "/";
		File localFile = new File(localPath);
		if (!localFile.exists()) {
			localFile.mkdirs();
		}
		FTPFile[] file = ftpClient.listFiles(remoteFile);
		logger.info("file size:" + file.length);
		for (int i = 0; null != file && i < file.length; i++) {
			if (file[i].isDirectory()) {
				downloadDirectory(localPath,
						remoteFile + "/" + file[i].getName());
			} else {
				String strremoteDirectoryPath = remoteFile + "/"
						+ file[i].getName();
				download(strremoteDirectoryPath,
						localPath + file[i].getName(), ftpClient);
			}
		}
		disconnectServer(ftpClient);

	}

	/**
	 * 下载文件
	 * 
	 * @param localPath
	 *            本地路径 文件夹
	 * @param remoteFile
	 *            远程 路径 文件夹
	 * @throws IOException
	 */
	public static void downloadTxtDirectory(String localPath, String remoteFile)
			throws IOException {
		ftpClient = connectServer();
		ftpClient.enterLocalPassiveMode();
		ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);

		String fileName = new File(remoteFile).getName();
		//localPath = localPath + "/" + fileName + "/";
		
		File localFile = new File(localPath);
		if (!localFile.exists()) {
			localFile.mkdirs();
		}
		FTPFile[] file = ftpClient.listFiles(remoteFile);
		logger.info("file size:" + file.length);
		for (int i = 0; null != file && i < file.length; i++) {
			if (file[i].isDirectory()) {
				downloadDirectory(localPath,
						remoteFile + "/" + file[i].getName());
			} else {
				String strremoteDirectoryPath = remoteFile;
				download(strremoteDirectoryPath,
						localPath+"/"+fileName, ftpClient);
			}
		}
		disconnectServer(ftpClient);

	}

	/**
	 * 批量下载图片
	 * 
	 * @param localFile
	 *            本地路径 文件夹
	 * @param remotefile
	 *            远程文件的父目录
	 * @param ids
	 *            文件名称 后缀为png的二维码图片
	 * @throws IOException
	 */
	public void downLoadFiles(String localFile, String remotefile,
			String... ids) throws IOException {
		ftpClient = connectServer();
		ftpClient.enterLocalPassiveMode();
		ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
		for (int i = 0; i < ids.length; i++) {
			download(remotefile + "/" + ids[i] + ".png", localFile + "/"
					+ ids[i] + ".png", ftpClient);
		}
		disconnectServer(ftpClient);
	}

	/**
	 * 批量下载图片
	 * 
	 * @param localFile
	 *            本地路径 文件夹
	
	 * @param ids
	 *            文件名称 后缀为png的二维码图片
	 * @throws IOException
	 */
	public void downLoadFiles(String localFile,
			String... ids) throws IOException {
		ftpClient = connectServer();
		ftpClient.enterLocalPassiveMode();
		ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
		for (int i = 0; i < ids.length; i++) {
			
			download(ids[i], localFile + "/"
					+new File(ids[i]).getName(), ftpClient);
		}
		disconnectServer(ftpClient);
	}
	/**
	 * 
	 * @Title: changeWorkingDirectory
	 * @Description: TODO
	 * @param path
	 * @param ftpClient
	 * @return
	 * @date Aug 4, 2011 5:44:56 PM
	 */
	public boolean changeWorkingDirectory(String path, FTPClient ftpClient) {
		boolean result = false;
		if (ftpClient != null && path != null && path.length() > 0) {
			try {
				result = ftpClient.changeWorkingDirectory(path);
			} catch (IOException e) {
				logger.warn("改变FTP" + path + "的工作目录时发生异常，原因：I/O 异常.");
			}
		}
		return result;
	}

	/**
	 * 
	 * @Title: disconnectServer
	 * @Description: 断开与远程 FTP 服务器的连接
	 * @param ftpClient
	 *            TODO
	 * @throws IOException
	 * @date Aug 2, 2011 3:36:27 PM
	 */
	public static void disconnectServer(FTPClient ftpClient) throws IOException {
		if (ftpClient.isConnected()) {
			ftpClient.disconnect();
		}
	}

	/**
	 * 
	 * @Title: download
	 * @Description: 从FTP服务器上下载文件
	 * @param remote
	 *            远程文件路径
	 * @param ftpClient
	 *            TODO
	 * @return responseFtp
	 * @throws IOException
	 */
	public static DownloadStatus download(String remote,
			HttpServletResponse responseFtp, FTPClient ftpClient)
			throws IOException {
		String fileName = remote.substring(remote.lastIndexOf("/") + 1);
		// 设置被动模式
		ftpClient.enterLocalPassiveMode();
		// 设置以二进制方式传输
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		ftpClient.setControlEncoding(FONT_ENCODING);
		DownloadStatus result;
		// ftpClient.changeWorkingDirectory(remote);
		FTPFile[] files = ftpClient.listFiles(remote);
		boolean upNewStatus = false;
		// 检查远程文件是否存在
		if (files.length != 1) {
			logger.info("远程文件 不存在 或者不唯一 [" + remote + "]");
			// return DownloadStatus.Remote_File_Noexist;
		} else {
			// 设置响应类型及响应头
			// 防止输出文件名乱码以下两种均可
			// getResponse().setHeader( "Content-Disposition",
			// "attachment;filename=\"" + new String(
			// fileName.getBytes("gb2312"), "ISO8859-1" )+"\"");

			String encodedFileName = new String(fileName.getBytes("gbk"),
					"iso8859-1");
			responseFtp.addHeader("Content-Type",
					"APPLICATION/OCTET-STREAM; charset=utf-8");
			responseFtp.addHeader("Content-Disposition",
					"attachment;filename=\"" + encodedFileName + "\"");
			// 读取文件
			// String encodingFilePath = enCodingRemoteFilePath(remote);
			InputStream inputStream = ftpClient.retrieveFileStream(remote);
			BufferedInputStream input = new BufferedInputStream(inputStream);
			byte[] bytes = new byte[1024];
			ServletOutputStream outputStream = responseFtp.getOutputStream();
			// 该对象可以在响应中写入二进制数据
			BufferedOutputStream output = new BufferedOutputStream(outputStream);
			int readLength = 0;
			while ((readLength = input.read(bytes)) != -1) {
				output.write(bytes, 0, readLength);
				output.flush();
			}
			// 释放资源
			input.close();
			inputStream.close();
			output.close();
			outputStream.close();
			upNewStatus = ftpClient.completePendingCommand();
		}
		if (upNewStatus) {
			result = DownloadStatus.Download_New_Success;
		} else {
			result = DownloadStatus.Download_New_Failed;
		}
		return result;
	}

	/**
	 * 
	 * @Title: download
	 * @Description: 从FTP服务器上下载文件,支持断点续传，上传百分比汇报
	 * @param remote
	 *            远程文件路径
	 * @param local
	 *            本地文件路径
	 * @param ftpClient
	 *            TODO
	 * @return 上传的状态
	 * @throws IOException
	 * @date Aug 2, 2011 3:37:08 PM
	 */
	public static DownloadStatus download(String remote, String local,
			FTPClient ftpClient) throws IOException {
		// 设置被动模式
		ftpClient.enterLocalPassiveMode();
		// 设置以二进制方式传输
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		DownloadStatus result;
		// 检查远程文件是否存在
		FTPFile[] files = ftpClient.listFiles(enCodingRemoteFilePath(remote));
		if (files.length != 1) {
			logger.info("远程文件 不存在 [" + remote + "]");
			return DownloadStatus.Remote_File_Noexist;
		}

		long lRemoteSize = files[0].getSize();
		File localFile = new File(local);
		// 本地存在文件，进行断点下载
		if (localFile.exists()) {
			long localSize = localFile.length();
			// 判断本地文件大小是否大于远程文件大小
			if (localSize >= lRemoteSize) {
				logger.info("本地文件大于远程文件，下载中止");
				return DownloadStatus.Local_Bigger_Remote;
			}

			// 进行断点续传，并记录状态
			FileOutputStream out = new FileOutputStream(localFile, true);
			ftpClient.setRestartOffset(localSize);
			InputStream in = ftpClient
					.retrieveFileStream(enCodingRemoteFilePath(remote));
			byte[] bytes = new byte[1024];
			double step = lRemoteSize / 100.0;
			double process = localSize / step;
			int c;
			while ((c = in.read(bytes)) != -1) {
				out.write(bytes, 0, c);

				localSize += c;
				double nowProcess = localSize / step;
				if (nowProcess > process) {
					process = nowProcess;
					if (process % 10 == 0)
						logger.debug("文件 [" + remote + "] 的下载进度：" + process);
					// TODO 更新文件下载进度,值存放在process变量中
				}
			}
			in.close();
			out.close();
			boolean isDo = ftpClient.completePendingCommand();
			if (isDo) {
				result = DownloadStatus.Download_From_Break_Success;
			} else {
				result = DownloadStatus.Download_From_Break_Failed;
			}
		} else {
			OutputStream out = new FileOutputStream(localFile);
			InputStream in = ftpClient.retrieveFileStream(remote);
			byte[] bytes = new byte[1024];
			long step = lRemoteSize / 100;
			double process = 0;
			double localSize = 0L;
			int c;
			while ((c = in.read(bytes)) != -1) {
				out.write(bytes, 0, c);
				localSize += c;
				double nowProcess = localSize / step;
				if (nowProcess > process) {
					process = nowProcess;
					if (process % 10 == 0)
						logger.debug("下载进度：" + process);
					// 更新文件下载进度,值存放在process变量中
				}
			}
			in.close();
			out.close();
			boolean upNewStatus = ftpClient.completePendingCommand();
			if (upNewStatus) {
				result = DownloadStatus.Download_New_Success;
			} else {
				result = DownloadStatus.Download_New_Failed;
			}
		}
		return result;
	}

	/**
	 * 
	 * @Title: upload
	 * @Description: 上传文件到FTP服务器，支持断点续传
	 * @param parentFloderPath
	 *            远程文件路径，绝对路径
	 * @param fileName
	 *            文件名
	 * @param localFile
	 *            本地文件
	 * @param ftpClient
	 *            TODO
	 * @return 上传结果
	 * @throws IOException
	 * @date Aug 2, 2011 3:37:46 PM
	 */
	public static UploadStatus upload(String parentFloderPath, String fileName,
			File localFile, FTPClient ftpClient) throws IOException {
		// 设置PassiveMode传输
		ftpClient.enterLocalPassiveMode();
		// 设置以二进制流的方式传输
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		ftpClient.setControlEncoding(FONT_ENCODING);
		// 对远程目录的处理
		// 创建服务器远程目录结构，创建失败直接返回
		if (!createDirectory(parentFloderPath, ftpClient)) {
			return UploadStatus.Create_Directory_Fail;
		}
		String remoteFileFullPath = parentFloderPath;
		if (parentFloderPath.endsWith(SEPARATOR_CHAR)
				|| parentFloderPath.endsWith("\\\\")) {
			remoteFileFullPath += fileName;
		} else {
			remoteFileFullPath = remoteFileFullPath + SEPARATOR_CHAR + fileName;
		}
		// 检查远程是否存在文件

		FTPFile[] files = ftpClient
				.listFiles(enCodingRemoteFilePath(remoteFileFullPath));
		if (files.length == 1) {
			if (!ftpClient.deleteFile(remoteFileFullPath)) {
				return UploadStatus.Delete_Remote_Faild;
			}
		}
		return uploadFile(remoteFileFullPath, localFile, 0, ftpClient);
	}

	/**
	 * 
	 * @Title: uploadFile
	 * @Description: 上传文件到服务器,新上传和断点续传
	 * @param remoteFile
	 *            远程文件名，在上传之前已经将服务器工作目录做了改变
	 * @param localFile
	 *            本地文件File句柄，绝对路径
	 * @param remoteSize
	 *            断点续传时上传文件的开始点
	 * @param ftpClient
	 *            TODO
	 * @return
	 * @throws IOException
	 * @date Aug 2, 2011 3:40:59 PM
	 */
	private static UploadStatus uploadFile(String remoteFile, File localFile,
			long remoteSize, FTPClient ftpClient) throws IOException {
		UploadStatus status;
		// 显示进度的上传
		long step = localFile.length() / 100;
		long process = 0;
		long localreadbytes = 0L;
		RandomAccessFile raf = new RandomAccessFile(localFile, "r");
		OutputStream out = ftpClient.appendFileStream(remoteFile);
		// 断点续传
		if (remoteSize > 0) {
			ftpClient.setRestartOffset(remoteSize);
			// process = remoteSize / step;
			raf.seek(remoteSize);
			localreadbytes = remoteSize;
		}
		byte[] bytes = new byte[2048];
		int c = 0;
		while ((c = raf.read(bytes)) != -1) {
			out.write(bytes, 0, c);
			localreadbytes += c;
			if (localreadbytes / step != process) {
				process = localreadbytes / step;
			}
		}
		out.flush();
		raf.close();
		out.close();
		boolean result = ftpClient.completePendingCommand();
		if (remoteSize > 0) {
			status = result ? UploadStatus.Upload_From_Break_Success
					: UploadStatus.Upload_From_Break_Failed;
		} else {
			status = result ? UploadStatus.Upload_New_File_Success
					: UploadStatus.Upload_New_File_Failed;
		}
		return status;
	}

	/**
	 * 
	 * @Title: uploadFile
	 * @Description: 上传文件
	 * @param newName
	 * @param iStream
	 * @param ftpClient
	 *            TODO
	 * @return boolean
	 * @throws IOException
	 * @date Aug 2, 2011 3:42:21 PM
	 */
	public static UploadStatus uploadFile(String parentFloderPath, String newName,
			InputStream iStream, FTPClient ftpClient) throws IOException {
		boolean flag = false;
		try {
			ftpClient.enterLocalPassiveMode();
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			ftpClient.setControlEncoding(FONT_ENCODING);
			if (!createDirectory(parentFloderPath, ftpClient)) {
				return UploadStatus.Create_Directory_Fail;
			}
			logger.info(">>>>>>>>>" + parentFloderPath);
			ftpClient.changeWorkingDirectory(parentFloderPath);
			flag = ftpClient.storeFile(newName, iStream);
		} catch (IOException e) {
			e.printStackTrace();
			return UploadStatus.Upload_New_File_Failed;
		} finally {
			if (iStream != null) {
				iStream.close();
			}
		}
		return flag ? UploadStatus.Upload_New_File_Success
				: UploadStatus.Upload_New_File_Failed;
	}

	/**
	 * 
	 * @Title: createDirectory
	 * @Description: 递归创建远程 FTP 服务器目录
	 * @param directory
	 *            远程服务器目录路径, 如"/a/b" 或 "x/y/z"
	 * @param ftpClient
	 *            TODO
	 * @return 目录创建成功返回true, 否则返回false
	 * @throws IOException
	 * @date Aug 2, 2011 3:42:38 PM
	 */
	public static boolean createDirectory(String directory, FTPClient ftpClient)
			throws IOException {
		boolean status = true;
		directory = directory.replaceAll("\\\\+", SEPARATOR_CHAR).replaceAll(
				"/{2,}", SEPARATOR_CHAR);
		ftpClient.setControlEncoding("GBK");
		String workingDirectory = ftpClient.printWorkingDirectory(); // 当前的工作目录
		if (ftpClient.changeWorkingDirectory(enCodingRemoteFilePath(directory))) {
			logger.info("目录 [" + ftpClient.printWorkingDirectory() + "] 已在存在");
		} else {
			if (directory.startsWith(SEPARATOR_CHAR)) {
				ftpClient.changeWorkingDirectory(SEPARATOR_CHAR);
				directory = directory.substring(1);
			}
			if (directory.endsWith(SEPARATOR_CHAR)) {
				directory = directory.substring(0, directory.length() - 1);
			}

			String[] subDirectors = directory.split(SEPARATOR_CHAR);
			String subDirector = null;
			for (int i = 0; i < subDirectors.length; i++) {
				subDirector = enCodingRemoteFilePath(subDirectors[i]);
				if (!ftpClient.changeWorkingDirectory(subDirector)) {
					if (ftpClient.makeDirectory(subDirector)
							&& ftpClient.changeWorkingDirectory(subDirector)) {
						logger.info("创建目录[" + ftpClient.printWorkingDirectory()
								+ "]成功");
					} else {
						logger.info("创建目录[" + ftpClient.printWorkingDirectory()
								+ SEPARATOR_CHAR + subDirector + "]失败");
						status = false;
						break;
					}
				}
			}
		}
		ftpClient.changeWorkingDirectory(workingDirectory);
		return status;
	}

	/**
	 * 
	 * @Title: removeDirectory
	 * @Description: 删除目录
	 * @param path
	 * @param ftpClient
	 *            TODO
	 * @return
	 * @throws IOException
	 * @date Aug 2, 2011 3:43:19 PM
	 */
	public boolean removeDirectory(String path, FTPClient ftpClient)
			throws IOException {
		return ftpClient.removeDirectory(path);
	}

	/**
	 * 
	 * @Title: removeDirectory
	 * @Description: 删除目录(及目录下所有文件和子目录)
	 * @param path
	 * @param isAll
	 * @param ftpClient
	 *            TODO
	 * @return
	 * @throws IOException
	 * @date Aug 2, 2011 3:43:35 PM
	 */
	public boolean removeDirectory(String path, boolean isAll,
			FTPClient ftpClient) throws IOException {
		if (!isAll) {
			return removeDirectory(path, ftpClient);
		}

		FTPFile[] ftpFileArr = ftpClient.listFiles(path);
		if (ftpFileArr == null || ftpFileArr.length == 0) {
			return removeDirectory(path, ftpClient);
		}

		for (FTPFile ftpFile : ftpFileArr) {
			String name = ftpFile.getName();
			if (".".equals(name) || "..".equals(name)) {
				continue;
			}
			if (ftpFile.isDirectory()) {
				logger.info("Delete subPath [" + path + SEPARATOR_CHAR + name
						+ "]");
				removeDirectory(path + SEPARATOR_CHAR + name, true, ftpClient);
			} else if (ftpFile.isFile()) {
				logger.info("Delete file [" + path + SEPARATOR_CHAR + name
						+ "]");
				deleteFile(path + SEPARATOR_CHAR + name, ftpClient);
			} else if (ftpFile.isSymbolicLink()) {

			} else if (ftpFile.isUnknown()) {

			}
		}
		return ftpClient.removeDirectory(path);
	}

	/**
	 * 
	 * @Title: existDirectory
	 * @Description: 检查目录(或文件)是否存在
	 * @param path
	 * @param ftpClient
	 *            TODO
	 * @return
	 * @throws IOException
	 * @date Aug 2, 2011 3:49:04 PM
	 */
	public static boolean existDirectory(String path, FTPClient ftpClient)
			throws IOException {
		boolean flag = false;
		ftpClient.enterLocalPassiveMode();
		// 设置以二进制方式传输
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		FTPFile[] ftpFileArr = ftpClient.listFiles(path);
		if (ftpFileArr.length > 0) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 
	 * @Title: getFileList
	 * @Description: 得到FTP指定目录下的所有文件名列表（不包括目录）
	 * @param path
	 *            如果 path 为 null, 则列出当前所在目录的所有文件名
	 * @param ftpClient
	 *            TODO
	 * @return
	 * @throws IOException
	 * @date Aug 2, 2011 3:49:23 PM
	 */
	public static List<String> getFileList(String path, FTPClient ftpClient)
			throws IOException {
		return getFileList(path, null, ftpClient, null);
	}

	/**
	 * 
	 * @Title: getFileList
	 * @Description: 得到FTP指定目录下的符合命名规则的文件名列表（不包括目录）
	 * @param path
	 *            如果 path 为 null, 则列出当前所在目录的所有文件名
	 * @param regex
	 *            文件名的命名规则，即正则表达式, 需要注意的是：此处用正则表达式匹配文件名， 不区分大小写。<br/>
	 *            如 ".*\.(txt|xml)", 则是只取后缀名为 txt 或 xml 的文件
	 * @param ftpClient
	 *            TODO
	 * @return 如果此文件夹下没有文件，则返回一个长度为 0 的List
	 * @throws IOException
	 * @date Aug 2, 2011 3:49:48 PM
	 */
	public static List<String> getFileList(String path, String regex,
			FTPClient ftpClient, FileType fileType) throws IOException {
		FTPFile[] ftpFiles = StringUtils.isBlank(path) ? ftpClient.listFiles()
				: ftpClient.listFiles(path);
		Pattern pattern = StringUtils.isBlank(regex) ? null : Pattern.compile(
				regex, Pattern.CASE_INSENSITIVE);
		List<String> retList = new ArrayList<String>();
		if (ftpFiles == null || ftpFiles.length == 0) {
			return retList;
		}

		String fileName = null;
		for (FTPFile ftpFile : ftpFiles) {
			fileName = ftpFile.getName();
			boolean isFileTypeFit = false;
			if (fileType == FileType.File && ftpFile.isFile()) {
				isFileTypeFit = true;
			}
			if (fileType == FileType.Directory && ftpFile.isDirectory()) {
				isFileTypeFit = true;
			}

			if (fileType == null) {
				isFileTypeFit = true;
			}

			if (isFileTypeFit
					&& (pattern == null || pattern.matcher(fileName).matches())) {
				retList.add(fileName);
			}
		}
		return retList;
	}

	/**
	 * 
	 * @Title: deleteFile
	 * @Description: 删除文件
	 * @param pathName
	 * @param ftpClient
	 *            TODO
	 * @return
	 * @throws IOException
	 * @date Aug 2, 2011 3:50:46 PM
	 */
	public static boolean deleteFile(String pathName, FTPClient ftpClient)
			throws IOException {
		return ftpClient.deleteFile(pathName);
	}

	
	public static boolean isDirectoryExists(String path, FTPClient client)
			throws IOException {

		ftpClient = client;
		ftpClient.enterLocalPassiveMode();
		// 设置以二进制方式传输
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		boolean flag = false;

		FTPFile[] f = ftpClient.listFiles(path);
		if (f.length >= 1) {
			flag = true;
		}

		return flag;

	}

	/**
	 * 
	 * @Title: enCodingRemoteFilePath
	 * @Description: 远程文件路径编码(上传到ftp上的文件路径)
	 * @param remoteFilePath
	 * @return String
	 * @throws UnsupportedEncodingException
	 * @date Jul 29, 2011 4:05:14 PM
	 */
	private static String enCodingRemoteFilePath(String remoteFilePath)
			throws UnsupportedEncodingException {
		return new String(remoteFilePath.getBytes(FONT_ENCODING),
				ENCODING_ISO88591);
	}

	public static enum UploadStatus {
		Create_Directory_Fail, // 远程服务器相应目录创建失败
		Create_Directory_Success, // 远程服务器创建目录成功
		Upload_New_File_Success, // 上传新文件成功
		Upload_New_File_Failed, // 上传新文件失败
		File_Exits, // 文件已经存在
		Remote_Bigger_Local, // 远程文件大于本地文件
		Upload_From_Break_Success, // 断点续传成功
		Upload_From_Break_Failed, // 断点续传失败
		Delete_Remote_Faild; // 删除远程文件失败
	}

	public enum DownloadStatus {
		Remote_File_Noexist, // 远程文件不存在
		Local_Bigger_Remote, // 本地文件大于远程文件
		Download_From_Break_Success, // 断点下载文件成功
		Download_From_Break_Failed, // 断点下载文件失败
		Download_New_Success, // 全新下载文件成功
		Download_New_Failed; // 全新下载文件失败
	}

	public enum FileType {
		File, // 文件
		Directory, // 文件夹
	}

	
}