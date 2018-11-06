package com.ybkj.gun.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ybkj.enums.IStatusMessage;
import com.ybkj.gun.model.App;
import com.ybkj.gun.model.WebUserLogin;
import com.ybkj.gun.service.LogService;
import com.ybkj.model.BaseModel;
import com.ybkj.untils.IpUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import nl.bitwalker.useragentutils.Browser;
import nl.bitwalker.useragentutils.OperatingSystem;
import nl.bitwalker.useragentutils.UserAgent;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

/**
 * @项目名称：
 * @类名称：
 * @类描述：用户日志操作功能
 * @创建人：liujiayi
 * @创建时间：2018/11/2 16:18
 * @修改时间：2018/11/2 16:18
 * @version：1.0
 */
@SuppressWarnings("all")
@RestController
@Slf4j
@Api(value = "/", description = "用户日志操作")
@RequestMapping("/log")
public class LogController {

    @Autowired
    private LogService logService;

    /**
     * @Description: 功能描述（查看用户登入,登出日志）
     * @Author: 刘家义
     * @CreateDate: 2018/11/1 20:01
     */
    @ApiOperation(value = "查询用户登入,登出日志", notes = "获取用户登入,登出日志列表")
    @RequestMapping(value = "/readUserLogList", method = RequestMethod.GET)
    public BaseModel readUserLogList(@RequestParam(value="pn",defaultValue="1") Integer pn, @RequestParam(value = "ps",defaultValue="5")Integer ps,@RequestParam(value="userName",required=false)String userName) {
        BaseModel baseModel=new BaseModel();
        PageHelper.startPage(pn,ps);
        log.debug("--------获取用户登入,登出日志列表------！"+userName);
        try {
            List<WebUserLogin> appList =this.logService.findWebUserLogins(userName);
            PageInfo<WebUserLogin> page = new PageInfo<WebUserLogin>(appList,5);
            baseModel.setStatus(IStatusMessage.SystemStatus.SUCCESS.getCode());
            baseModel.setErrorMessage("获取用户登入,登出日志成功！");
            baseModel.add("pageInfo",page);
        }catch (Exception e){
            e.printStackTrace();
            log.error("用户登入,登出日志查询异常！", e);
            baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
            baseModel.setErrorMessage("获取用户登入,登出日志异常！");
        }
        return baseModel;
    }


}
