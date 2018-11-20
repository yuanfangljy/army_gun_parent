package com.ybkj.gun.controller;

import com.ybkj.common.entity.WebUserDTO;
import com.ybkj.common.shiro.ShiroRealm;
import com.ybkj.common.util.ActiveUser;
import com.ybkj.enums.IStatusMessage;
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
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
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
                              @RequestParam(value = "rememberMe", required = false) boolean rememberMe, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String origin = request.getHeader("Origin");
        if(origin == null) {
            origin = request.getHeader("Referer");
        }

        response.setHeader("Access-Control-Allow-Origin", origin);
        response.setHeader("Access-Control-Allow-Credentials", "true");
        //response.setHeader("Access-Control-Allow-Origin", "*");
        BaseModel baseModel = webUserService.shiroLogin(webUserDTO, rememberMe,request,response);
        if(baseModel.getStatus()=="1000"){
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
        }
        return baseModel;
    }

    @ApiOperation(value = "用户登出", notes = "用户登出")
    @RequestMapping(value = "/loginOut", method = RequestMethod.POST)
    public BaseModel loginOut(HttpServletRequest request, HttpServletResponse response){
        String origin = request.getHeader("Origin");
        if(origin == null) {
            origin = request.getHeader("Referer");
        }

        response.setHeader("Access-Control-Allow-Origin", origin);
        response.setHeader("Access-Control-Allow-Credentials", "true");
        BaseModel baseModel=new BaseModel();
        try {

            RealmSecurityManager rsm = (RealmSecurityManager) SecurityUtils.getSecurityManager();
            ShiroRealm authRealm = (ShiroRealm) rsm.getRealms().iterator().next();
            authRealm.clearCachedAuth();
            SecurityUtils.getSubject().logout();
            baseModel.setStatus(IStatusMessage.SystemStatus.SUCCESS.getCode());
            baseModel.setErrorMessage("退出成功");
            log.debug("用户退出登录");
        }catch (Exception e){
            baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
            baseModel.setErrorMessage("退出异常");
            e.printStackTrace();
            log.debug("用户退出登录异常");
        }
        return baseModel;
    }

    public static void main(String[] args) {
        String s = DigestUtils.md5Hex("123456");
        System.out.println(s);
    }
 /*   @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public String login( @RequestParam(required = false) String username, @RequestParam(required = false) String password ){
    JSONObject jsonObject = new JSONObject();
    Subject subject = SecurityUtils.getSubject();
    //password = MD5Tools.MD5(password);
    UsernamePasswordToken token = new UsernamePasswordToken(username, password);
    try {
        // 登录，即身份验证
        subject.login(token);
        onlineSessionManager.addOnlineSession(subject.getSession().getId());
        User user = userService.getUserByLoginName(token.getUsername());
        // 在session中存放用户信息
        subject.getSession().setAttribute("userLogin", user);
        jsonObject.put("error", 0);
        jsonObject.put("msg", "登录成功");
        // 返回sessionId作为token
        jsonObject.put("token",subject.getSession().getId());
    } catch (IncorrectCredentialsException e) {
        throw new JsonException("用户名或密码错误", 405);
    } catch (LockedAccountException e) {
        throw new JsonException("登录失败，该用户已被冻结", 405);
    } catch (AuthenticationException e) {
        throw new JsonException("用户名或密码错误", 405);
      } return jsonObject.toString(); }
    }*/

}
