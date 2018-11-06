package com.ybkj.gun.controller;

import com.ybkj.common.entity.WebUserDTO;
import com.ybkj.common.util.ActiveUser;
import com.ybkj.gun.model.WebUser;
import com.ybkj.gun.model.WebUserLogin;
import com.ybkj.gun.service.LogService;
import com.ybkj.gun.service.impl.WebUserServiceImpl;
import com.ybkj.model.BaseModel;
import com.ybkj.pojo.LoginLogOutLogPojo;
import com.ybkj.untils.IpUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

/**
 * @项目名称：
 * @类名称：
 * @类描述：web用户功能
 * @创建人：liujiayi
 * @创建时间：2018/10/30 16:20
 * @修改时间：2018/10/30 16:20
 * @version：1.0
 */
@SuppressWarnings("all")
@RestController
@RequestMapping("/webUser")
@Slf4j
@Api(value = "/", description = "web用户功能")
public class WebUserController {

    @Autowired
    private WebUserServiceImpl webUserService;
    @Autowired
    private LogService logService;

    @ApiOperation(value = "用户登录", notes = "用户名和密码")
    @RequestMapping(value = "/loginWeb", method = RequestMethod.POST)
    public BaseModel loginWeb(WebUserDTO webUserDTO,
                              @RequestParam(value = "rememberMe", required = false) boolean rememberMe, HttpServletRequest request) throws Exception {
        BaseModel baseModel = webUserService.shiroLogin(webUserDTO, rememberMe);
        //记录日志
        LoginLogOutLogPojo loginLog = IpUtil.createLoginLog(request);
        WebUserLogin webUserLogin=new WebUserLogin();
        webUserLogin.setWebIp(loginLog.getIp());
        webUserLogin.setBrowser(loginLog.getBrowser());
        webUserLogin.setSystemName(loginLog.getSystemName());
        webUserLogin.setState(0);
        webUserLogin.setLogintime(new Date());
        webUserLogin.setUid(ActiveUser.getActiveUser().getId());
        webUserLogin.setUserName(ActiveUser.getActiveUser().getName());
        logService.addLogLoginLogOut(webUserLogin);
        return baseModel;
    }
}
