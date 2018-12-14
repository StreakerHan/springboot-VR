package com.streaker.utils;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
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
        file_path = FTP_ADDRESS + ":" + NGINX_PORT + "/" + originFileName;
        return file_path;
    }

    //上传模型文件夹
    public  static String uploadFileModel(File file) throws IOException{
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
            String basePath = UUID.randomUUID().toString().replace("-","");
            ftp.changeWorkingDirectory(FTP_BASEPATH);
            ftp.makeDirectory(basePath);
            ftp.changeWorkingDirectory(basePath);
            /*String[] files = file.list();
            for(int i = 0; i < files.length; i++){
                File file1 = new File(basePath + "\\" + files[i]);
                if(file1.isDirectory()){
                    uploadFileModel(file);
                    ftp.changeToParentDirectory();
                }else{
                    File file2 = new File(basePath + "\\" + files[i]);
                    FileInputStream input = new FileInputStream(file2);
                    ftp.storeFile(file.getName(),input);
                    input.close();
                }
            }*/
            ftp.logout();
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
        file_path = FTP_ADDRESS + ":" + NGINX_PORT + "/";
        return file_path;
    }
}
