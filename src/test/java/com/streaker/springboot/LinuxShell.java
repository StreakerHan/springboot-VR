package com.streaker.springboot;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import org.apache.commons.lang3.StringUtils;

import java.io.*;

/**
 * 远程操作linux
 *
 * @author StreakerHan
 * @version 2018/12/14 08:54:27
 **/
public class LinuxShell {
    //字符编码默认是utf-8
    private static String  DEFAULTCHART="UTF-8";
    private static Connection conn;
    private String ip;
    private String userName;
    private String userPwd;

    public LinuxShell(String ip, String userName, String userPwd) {
        this.ip = ip;
        this.userName = userName;
        this.userPwd = userPwd;
    }

    public LinuxShell() {

    }

    /**
     * 远程登录linux的主机
     * @since  V0.1
     * @return
     *      登录成功返回true，否则返回false
     */
    public Boolean login(){
        boolean flg=false;
        try {
            conn = new Connection(ip);
            conn.connect();//连接
            flg=conn.authenticateWithPassword(userName, userPwd);//认证
            if (flg){
                System.out.println("认证成功！");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flg;
    }
    /**
     * 远程执行shll脚本或者命令
     * @param cmd
     *      即将执行的命令
     * @return
     *      命令执行完后返回的结果值
     * @since V0.1
     */
    public String execute(String cmd){
        String result="";
        try {
            if(login()){
                Session session= conn.openSession();//打开一个会话
                session.execCommand(cmd);//执行命令
                result=processStdout(session.getStdout(),DEFAULTCHART);
                //如果为得到标准输出为空，说明脚本执行出错了
                if(StringUtils.isBlank(result)){
                    result=processStdout(session.getStderr(),DEFAULTCHART);
                }
                conn.close();
                session.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 远程执行shll脚本或者命令
     * @param cmd
     *      即将执行的命令
     * @return
     *      命令执行成功后返回的结果值，如果命令执行失败，返回空字符串，不是null
     * @since V0.1
     */
    public String executeSuccess(String cmd){
        String result="";
        try {
            if(login()){
                Session session= conn.openSession();//打开一个会话
                session.execCommand(cmd);//执行命令
                result=processStdout(session.getStdout(),DEFAULTCHART);
                conn.close();
                session.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 解析脚本执行返回的结果集
     * @param in 输入流对象
     * @param charset 编码
     * @since V0.1
     * @return
     *       以纯文本的格式返回
     */
    public static String processStdout(InputStream in, String charset){
        InputStream    stdout = new StreamGobbler(in);
        StringBuffer buffer = new StringBuffer();;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(stdout,charset));
            String line=null;
            while((line=br.readLine()) != null){
                buffer.append(line+"\n");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }



    public static void main(String[] args) {

        LinuxShell rec=new LinuxShell("101.132.64.128", "root","HYPHYPHYPhyp123");
        //执行命令
        try {
            if(rec.login()){

                System.out.println("=====第一个步骤=====");
                Session session= conn.openSession();//打开一个会话
                //TODO:多条命令
                session.execCommand("");//执行命令
                String result=processStdout(session.getStdout(),DEFAULTCHART);
                //如果为得到标准输出为空，说明脚本执行出错了
                if(StringUtils.isBlank(result)){
                    System.out.println("脚本出错");
                    result=processStdout(session.getStderr(),DEFAULTCHART);
                }
                System.out.println(result);
                session.close();

                System.out.println("=====第二个步骤=====");
                Session session2= conn.openSession();//打开一个会话
                //TODO:多条命令
                session2.execCommand("");//执行命令
                String result2=processStdout(session2.getStdout(),DEFAULTCHART);
                //如果为得到标准输出为空，说明脚本执行出错了
                if(StringUtils.isBlank(result2)){
                    System.out.println("脚本出错");
                    result2=processStdout(session2.getStderr(),DEFAULTCHART);
                }
                System.out.println(result2);
                session2.close();


                conn.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    public static void setCharset(String charset) {
        DEFAULTCHART = charset;
    }
    public Connection getConn() {
        return conn;
    }
    public void setConn(Connection conn) {
        this.conn = conn;
    }
    public String getIp() {
        return ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserPwd() {
        return userPwd;
    }
    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }
}
