package com.icloud.common.ftp;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.icloud.common.ftp.FtpUtils.DownloadStatus;
import com.icloud.common.ftp.FtpUtils.UploadStatus;

@Service
public class FtpFileService {

    
    private static final Logger logger = Logger.getLogger(FtpFileService.class);
	
    /**
     * 找到dir目录下没有用过的name名字，如果有，则加1
     * @param dir
     * @param name
     * @return
     */
    public String findUnUsedName(String dir, String name){
        FTPClient ftpClient = null;
        
        boolean status = false;
        String[] fileNames = name.split("\\.");
        String fileName = fileNames[0];
        String postFix = "";
        if(fileNames.length > 1){
            postFix = "." + fileNames[1];
        }else if (name.contains(".")) {
            postFix = ".";
        }
        try {
            ftpClient = FtpUtils.connectServer();
            for(int i=0; ; i++){
                name = fileName;
                if(i > 0){
                    name += i;
                }
                name += postFix;
                
                status = FtpUtils.existDirectory(dir + name, ftpClient);
                if(!status){
                    break;
                }
            }
        } catch (Exception e) {
            logger.error("upload file exception", e);
        } finally {
            disconectFtpServer(ftpClient);
        }
        return name;
    }
    
    /**
     * 判断文件夹或者问价是否存在
     * @param path
     * @return
     */
    public boolean existFileORDirectory(String path){
        FTPClient ftpClient = null;
        boolean status = false;
        try {
            ftpClient = FtpUtils.connectServer();
            status = FtpUtils.existDirectory(path, ftpClient);
        } catch (Exception e) {
            logger.error("upload file exception", e);
        } finally {
            disconectFtpServer(ftpClient);
        }
        return status;
    }

    /**
     * 上传文件 参数 文件 路径 （路径为tomcat下项目跟目录+自建路径）文件名
     */
    public String upload(File file, String parentDir, String saveName) throws Exception {
        FTPClient ftpClient = null;
        UploadStatus status = null;
        try {
            ftpClient = FtpUtils.connectServer();
            status = FtpUtils.upload(parentDir, saveName, file, ftpClient);
        } catch (Exception e) {
            logger.error("upload file exception", e);
        } finally {
        	FtpUtils.disconnectServer(ftpClient);
        }
        return status.name();
    }

    /**
     * 上传文件 参数 文件 路径 （路径为tomcat下项目跟目录+自建路径）文件名
     */
    public boolean upload(byte[] bytes, String parentDir, String saveName)  {
        FTPClient ftpClient = null;
        UploadStatus status = null;
        InputStream is = new ByteArrayInputStream(bytes);
        try {
            ftpClient = FtpUtils.connectServer();
            status = FtpUtils.uploadFile(parentDir, saveName, is, ftpClient);
        } catch (Exception e) {
            logger.error("upload file exception", e);
        } finally {
            disconectFtpServer(ftpClient);
        }
        return (UploadStatus.Upload_New_File_Success == status);
    }

    private void disconectFtpServer(FTPClient ftpClient) {
        try {
        	FtpUtils.disconnectServer(ftpClient);
        } catch (IOException e) {
            logger.error("disconectFtpServer file exception", e);
        }
    }

    /**
     * 下载
     */
    public String download(String remoteDir, String localDir) throws Exception {
        FTPClient ftpClient = null;
        DownloadStatus status = null;
        try {
            ftpClient = FtpUtils.connectServer();
            status = FtpUtils.download(remoteDir, localDir, ftpClient);
        } catch (Exception e) {
            logger.error("download file exception", e);
        } finally {
        	FtpUtils.disconnectServer(ftpClient);
        }
        return status.name();
    }

    public void download(String remoteDir, HttpServletResponse responseFtp) throws Exception {
        FTPClient ftpClient = null;
        try {
            ftpClient = FtpUtils.connectServer();
            FtpUtils.download(remoteDir, responseFtp, ftpClient);
        } catch (IOException ex) {
            logger.warn("download is canceled��" + remoteDir);
        } catch (Exception e) {
            logger.error("download file exception", e);
        } finally {
        	FtpUtils.disconnectServer(ftpClient);
        }
    }
    
   
    
    public boolean deleteFile(String filePath) throws Exception {
        FTPClient ftpClient = null;
        boolean status = false;
        try {
            ftpClient = FtpUtils.connectServer();
            status = FtpUtils.deleteFile(filePath, ftpClient);
        } catch (Exception e) {
            logger.error("delete file exception", e);
        } finally {
        	FtpUtils.disconnectServer(ftpClient);
        }
        return status;
    }
    /**
     * 文件上传
     * @param file
     * @param parentDir
     * @param saveName
     * @param server
     * @param port
     * @param user
     * @param password
     * @return
     * @throws Exception
     */
    public String uploadFile(File file, String parentDir, String saveName,String server,int port,String user,String password) throws Exception {
        FTPClient ftpClient = null;
        UploadStatus status = null;
        try {
            ftpClient = FtpUtils.connectServers(server, port, user, password);
            status = FtpUtils.upload(parentDir, saveName, file, ftpClient);
        } catch (Exception e) {
            logger.error("upload file exception", e);
        } finally {
        	FtpUtils.disconnectServer(ftpClient);
        }
        return status.name();
    }
   
}
