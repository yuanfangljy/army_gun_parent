package com.ybkj.gun.controller;

import com.ybkj.enums.IStatusMessage;
import com.ybkj.gun.model.AppGunUser;
import com.ybkj.gun.model.GunUser;
import com.ybkj.gun.service.AppGunUserService;
import com.ybkj.gun.service.GunUserService;
import com.ybkj.model.BaseModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @项目名称：
 * @类名称：
 * @类描述：
 * @创建人：liujiayi
 * @创建时间：2018/11/11 15:04
 * @修改时间：2018/11/11 15:04
 * @version：1.0
 */

@SuppressWarnings("all")
@RestController
@Slf4j
@Api(value = "/",description = "设备用户表")
@RequestMapping("/gunUser")
public class GunUserController {

    @Autowired
    private GunUserService gunUserService;
    @Autowired
    private AppGunUserService appGunUserService;
    /**
     * @Description: 功能描述（通过设备Id,获取gunUserId,来获取用户的相关信息）
     * @Author: 刘家义
     * @CreateDate: 2018/11/1 19:53
     */
    @ApiOperation(value = "通过设备Id,获取gunUserId,来获取用户的相关信息", notes = "获取用户的相关信息")
    @RequestMapping(value = "/readGunUser", method = RequestMethod.GET)
    public BaseModel readGunUser(@RequestParam("appId") Integer appId) {
        BaseModel baseModel = new BaseModel();
        log.debug("通过设备Id,获取gunUserId,来获取用户的相关信息--appId-" + appId);
        try {
            if (appId != null && appId > 0) {
                //通过appId，获取gunUserId
                AppGunUser appGunUser=appGunUserService.findAppGunUserByAppId(appId);
                if(appGunUser!=null){
                    baseModel = this.gunUserService.findGunUserById(appGunUser.getGunUserId());
                }
                 log.debug("获取用户的相关信息成功！-appId-" + appId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获取用户的相关信息异常！", e);
            baseModel.setStatus(IStatusMessage.SystemStatus.PARAM_ERROR.getCode());
            baseModel.setErrorMessage("获取用户的相关信息异常！");
        }
        return baseModel;
    }


    /**
     * @Description: 功能描述（查询没有和腕表绑定的用户）
     * @Author: 刘家义
     * @CreateDate: 2018/11/1 19:53
     */
    @ApiOperation(value = "查询没有和腕表绑定的用户", notes = "获取没有和腕表绑定的用户")
    @RequestMapping(value = "/readGunUserNoBinding", method = RequestMethod.GET)
    public BaseModel readGunUserNoBinding() {
        BaseModel baseModel = new BaseModel();
        log.debug("---查询没有和腕表绑定的用户---" );
        try {
                //通过appId，获取gunUserId
                List<GunUser> gunUserList=gunUserService.findGunUserNoBinding();
                baseModel.add("gunUserList",gunUserList);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获取没有和腕表绑定的用户信息异常！", e);
            baseModel.setStatus(IStatusMessage.SystemStatus.PARAM_ERROR.getCode());
            baseModel.setErrorMessage("获取没有和腕表绑定的用户信息异常！");
        }
        return baseModel;
    }

}
