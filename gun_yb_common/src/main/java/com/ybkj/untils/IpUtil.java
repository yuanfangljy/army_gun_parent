package com.ybkj.untils;

import com.ybkj.pojo.LoginLogOutLogPojo;
import lombok.extern.slf4j.Slf4j;
import nl.bitwalker.useragentutils.Browser;
import nl.bitwalker.useragentutils.OperatingSystem;
import nl.bitwalker.useragentutils.UserAgent;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @项目名称：
 * @类名称：
 * @类描述：
 * @创建人：liujiayi
 * @创建时间：2018/11/2 16:48
 * @修改时间：2018/11/2 16:48
 * @version：1.0
 */
@SuppressWarnings("all")
public class IpUtil {


    /**
     * @Description: 功能描述（登录日志）
     * @Author: 刘家义
     * @CreateDate: 2018/11/2 16:40
     */
    public static LoginLogOutLogPojo createLoginLog(HttpServletRequest request) {
        LoginLogOutLogPojo loginLogOutLogPojo=new LoginLogOutLogPojo();
        //获取用户真实Ip
        String ip = IpUtil.getIp(request);
        //获取浏览器信息
        String ua = request.getHeader("User-Agent");
        //转成UserAgent对象
        UserAgent userAgent = UserAgent.parseUserAgentString(ua);
        //获取浏览器信息
        Browser browser = userAgent.getBrowser();
        //获取系统信息
        OperatingSystem os = userAgent.getOperatingSystem();
        //系统名称
        String system = os.getName();
        //浏览器名称
        String browserName = browser.getName();

        loginLogOutLogPojo.setIp(ip);
        loginLogOutLogPojo.setBrowser(browserName);
        loginLogOutLogPojo.setSystemName(system);
        return loginLogOutLogPojo;
    }



    /**
     * @Description:  功能描述（获取用户真实Ip）
     * @Author:       刘家义
     * @CreateDate:   2018/11/2 16:47
     */
    public static String getIp(HttpServletRequest request){
        String ipAddress = null;//登录Ip
        try {
            ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if (ipAddress.equals("127.0.0.1")) {
                    // 根据网卡取本机配置的IP
                    InetAddress inet = null;
                    try {
                        inet = InetAddress.getLocalHost();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                    ipAddress = inet.getHostAddress();
                }
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
                // = 15
                if (ipAddress.indexOf(",") > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
        } catch (Exception e) {
            ipAddress="";
        }
        return ipAddress;
    }


}
