package com.ybkj.gun.controller;

import com.ybkj.enums.IStatusMessage;
import com.ybkj.gun.model.AppGunUser;
import com.ybkj.gun.service.AppGunUserService;
import com.ybkj.model.BaseModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @项目名称：
 * @类名称：
 * @类描述：设备注册，绑定的用户信息
 * @创建人：liujiayi
 * @创建时间：2018/11/10 17:20
 * @修改时间：2018/11/10 17:20
 * @version：1.0
 */
@SuppressWarnings("all")
@RestController
@Slf4j
@Api(value = "/",description = "设备注册，绑定的用户信息")
@RequestMapping("/appGunUser")
public class AppGunUserController {

    @Autowired
    private AppGunUserService appGunUserService;

    /**
     * @Description: 功能描述（查询腕表/手机列表）
     * @Author: 刘家义
     * @CreateDate: 2018/11/1 20:01
     */
    @ApiOperation(value = "查询设备注册，绑定的用户信息", notes = "获取设备注册，绑定的用户信息")
    @RequestMapping(value = "/readAppGunUserList", method = RequestMethod.GET)
    public BaseModel readAppGunUserList() {
        BaseModel baseModel=new BaseModel();
        log.debug("--------设备注册，绑定的用户信息列表------！");
        try {
            List<AppGunUser> appGunUsers =this.appGunUserService.findAppGunUserList();
            baseModel.setStatus(IStatusMessage.SystemStatus.SUCCESS.getCode());
            baseModel.setErrorMessage("获取设备注册，绑定的用户信息列表！");
            baseModel.add("pageInfo",appGunUsers);
        }catch (Exception e){
            e.printStackTrace();
            log.error("设备注册，绑定的用户信息列表查询异常！", e);
            baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
            baseModel.setErrorMessage("获取设备注册，绑定的用户信息列表异常！");
        }
        return baseModel;
    }



}
