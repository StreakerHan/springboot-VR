package com.streaker.springboot;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Ftp工具测试类
 *
 * @author StreakerHan
 * @version 2018/12/10 14:02:19
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class FtpUtilTest {
    @Test
    public void testFtpClient() throws IOException {
        FTPClient ftpClient = new FTPClient();
        //System.out.println("#########");
        ftpClient.connect("106.15.204.11",21);//服务器地址和端口
        ftpClient.login("root1234","root1234");//登录的用户名和密码
        //读取本地文件，给出的是本地文件地址
        FileInputStream inputStream = new FileInputStream(new File("G:\\slide_3.jpg"));
        ftpClient.enterLocalActiveMode();
        //设置上传路径
        ftpClient.changeWorkingDirectory("/home/images");
        //设置文件类型
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        //1.服务器端保存的文件名，2.上传文件的inputstream
        ftpClient.storeFile("test.jpg",inputStream);
        ftpClient.logout();
    }

}
