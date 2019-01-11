package com.streaker.utils;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * FTP文件传输工具类
 *
 * @author StreakerHan
 * @version 2018/12/10 15:35:30
 **/
public class FtpUtil {
    //ftp服务器ip地址
    private static final String FTP_ADDRESS = "101.132.64.128";
    //ftp端口号
    private static final int FTP_PORT = 21;
    //nginx端口
    private static final int NGINX_PORT = 88;
    //用户名
    private static final String FTP_USERNAME = "admin";
    //密码
    private static final String FTP_PASSWORD = "admin";
    //文件路径
    private static final String FTP_BASEPATH = "/home/admin";
    //服务器名
    private static final String HOST_USERNAME = "root";
    //服务器登录密码
    private static final String HOST_PASSWORD = "HYPHYPHYPhyp123";
    //返回的文件路径
    private static String file_path = "";
    //文件上传到服务器时重命名
    //private static String originFileName = "";

    //单个文件上传
    public  static String uploadFile(String originFileName,InputStream input) throws IOException{
        FTPClient ftp = new FTPClient();
        ftp.setControlEncoding("UTF-8");
        try {
            int reply;
            ftp.connect(FTP_ADDRESS, FTP_PORT);// 连接FTP服务器
            ftp.login(FTP_USERNAME, FTP_PASSWORD);// 登录
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                throw new IOException("ftp登录失败");
            }
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            //ftp.makeDirectory(FTP_BASEPATH );
            ftp.changeWorkingDirectory(FTP_BASEPATH );
            String originFileNameUUID = UUID.randomUUID().toString().replace("-","");
            //System.out.println("####"+originFileName);
            originFileName = originFileNameUUID + originFileName;
            ftp.storeFile(originFileName,input);
            input.close();
            ftp.logout();
            //success = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        file_path = "http://" + FTP_ADDRESS + ":" + NGINX_PORT + "/" + originFileName;
        return file_path;
    }

    //上传模型文件夹
    public  static String uploadFileModel(String originFileName,InputStream input) throws IOException{
        //String newName = null;
        FTPClient ftp = new FTPClient();
        ftp.setControlEncoding("UTF-8");
        try {
            int reply;
            ftp.connect(FTP_ADDRESS, FTP_PORT);// 连接FTP服务器
            ftp.login(FTP_USERNAME, FTP_PASSWORD);// 登录
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                throw new IOException("ftp登录失败");
            }
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftp.changeWorkingDirectory(FTP_BASEPATH );
            String fileNamePrefix = originFileName.substring(0,originFileName.lastIndexOf("."));
            String fileNameSuffix = originFileName.substring(originFileName.lastIndexOf(".") + 1);
            //originFileName = originFileNameUUID;
            //上传压缩包
            //重命名压缩包，防止名字重复，上传出错
            String originFileName1 = UUID.randomUUID().toString().replace("-","");
            ftp.storeFile(originFileName1+ "." + fileNameSuffix,input);
            //执行解压操作，并将解压后的文件夹重命名
            String originFileNameUUID = UUID.randomUUID().toString().replace("-","");
            originFileName = originFileNameUUID;
            LinuxShell.UnZipAfterUpload(originFileName1 + "." + fileNameSuffix,originFileName,fileNamePrefix,FTP_ADDRESS,HOST_USERNAME,HOST_PASSWORD);
            input.close();
            ftp.logout();
            //success = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        file_path = "http://" + FTP_ADDRESS + ":" + NGINX_PORT + "/" + originFileName + "/"+"index.html";
        return file_path;
    }
}
