package com.streaker.utils;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import org.apache.commons.lang3.StringUtils;

import java.io.*;

/**
 * linux服务器操作命令
 *
 * @author StreakerHan
 * @version 2018/12/14 13:42:57
 **/
public class LinuxShell {
    private static String DEFAULTCHART = "UTF-8";
    private static Connection conn;
    private String ip;
    private String userName;
    private String userPwd;

    public LinuxShell(String ip, String userName, String userPwd) {
        this.ip = ip;
        this.userName = userName;
        this.userPwd = userPwd;
    }

    //登录远程服务器
    public boolean loginHost() {
        boolean flg = false;
        try {
            conn = new Connection(ip);
            conn.connect();//连接
            flg = conn.authenticateWithPassword(userName, userPwd);//认证
            if (flg) {
                System.out.println("认证成功！");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flg;
    }

    //远程执行命令,命令执行完后返回的结果值
    public String execute(String cmd) {
        String result = "";
        try {
            if (loginHost()) {
                Session session = conn.openSession();//打开一个会话
                session.execCommand(cmd);//执行命令
                result = processStdout(session.getStdout(), DEFAULTCHART);
                //如果为得到标准输出为空，说明脚本执行出错了
                if (StringUtils.isBlank(result)) {
                    result = processStdout(session.getStderr(), DEFAULTCHART);
                }
                conn.close();
                session.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    //远程执行shll脚本或者命令,命令执行成功后返回的结果值，如果命令执行失败，返回空字符串，不是null
    public String executeSuccess(String cmd) {
        String result = "";
        try {
            if (loginHost()) {
                Session session = conn.openSession();//打开一个会话
                session.execCommand(cmd);//执行命令
                result = processStdout(session.getStdout(), DEFAULTCHART);
                conn.close();
                session.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    //解析脚本执行返回的结果集
    public static String processStdout(InputStream in, String charset) {
        InputStream stdout = new StreamGobbler(in);
        StringBuffer buffer = new StringBuffer();
        ;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(stdout, charset));
            String line = null;
            while ((line = br.readLine()) != null) {
                buffer.append(line + "\n");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }

    //此方法是在ftp上传完zip压缩包之后执行
    public static void UnZipAfterUpload(String fileName,String newName,String oldname, String ip, String userName, String userPwd) {
        LinuxShell rec = new LinuxShell(ip, userName, userPwd);
        try {
            if (rec.loginHost()) {
                String fileNamePrefix1 = fileName.substring(0,fileName.lastIndexOf("."));
                //System.out.println("=====第一个步骤=====");
                Session session = conn.openSession();//打开一个会话
                //TODO:多条命令
                String commandShell = "unzip -o -d /home/admin /home/admin/" + fileName + ";cd /home/admin ;mv "+oldname+" "+ newName;
                //String commandShell = "rm -rf /home/admin/abc.zip";
                session.execCommand(commandShell);//执行命令
                //System.out.println(commandShell);
                String result = processStdout(session.getStdout(), DEFAULTCHART);
                //如果为得到标准输出为空，说明脚本执行出错了
                if (StringUtils.isBlank(result)) {
                    System.out.println("脚本出错");
                    result = processStdout(session.getStderr(), DEFAULTCHART);
                }
                //System.out.println(result);
                session.close();

                conn.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
