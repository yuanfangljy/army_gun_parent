package com.ybkj.gun.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ybkj.enums.IStatusMessage;
import com.ybkj.gun.model.App;
import com.ybkj.gun.model.GunUser;
import com.ybkj.gun.service.GunUserService;
import com.ybkj.model.BaseModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @项目名称：
 * @类名称：
 * @类描述：警员信息表
 * @创建人：liujiayi
 * @创建时间：2018/11/5 19:23
 * @修改时间：2018/11/5 19:23
 * @version：1.0
 */
@SuppressWarnings("all")
@RestController
@Slf4j
@Api(value = "/",description = "警员信息表")
@RequestMapping("/gunUser")
public class GunUserController {

    @Autowired
    private GunUserService gunUserService;


    /**
     * @Description: 功能描述（创建 警员信息）
     * @Author: 刘家义
     * @CreateDate: 2018/11/1 16:24
     */
    @ApiOperation(value = "创建 警员信息", notes = "警员信息")
    @RequestMapping(value = "/createGunUser", method = RequestMethod.POST)
    public BaseModel createGunUser(GunUser gunUser) throws Exception {
        log.debug("新增警员信息--gunUser-" + gunUser.toString());
        BaseModel baseModel = new BaseModel();
        try {
            if (null == gunUser) {
                baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
                baseModel.setErrorMessage("请您填写警员相关信息");
                log.debug("新增警员信息--gunUser-" + baseModel);
                return baseModel;
            }
            baseModel = gunUserService.addGunUser(gunUser);
        } catch (Exception e) {
            baseModel.setStatus(IStatusMessage.SystemStatus.PARAM_ERROR.getCode());
            baseModel.setErrorMessage("新增警员信息！异常");
            e.printStackTrace();
            log.error("新增警员信息！异常！", e);
        }
        return baseModel;
    }


    /**
     * @param wId
     * @return
     * @Description: 功能描述（删除警员信息）
     * @Author: 刘家义
     * @CreateDate: 2018/11/1 16:31
     */
    @ApiOperation(value = "删除警员信息", notes = "删除警员信息")
    @RequestMapping(value = "/deleteGunUser", method = RequestMethod.DELETE)
    public BaseModel deleteGunUser(@RequestParam("gunUserId") String gunUserId) {
        log.debug("删除警员信息--gunUserId--" + gunUserId);
        BaseModel baseModel = new BaseModel();
        try {
            if (StringUtils.isNoneBlank(gunUserId)) {
                baseModel = this.gunUserService.removeGunUser(gunUserId);
            } else {
                baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
                baseModel.setErrorMessage("删除警员信息出错，请您稍后再试");
            }
        } catch (Exception e) {
            baseModel.setStatus(IStatusMessage.SystemStatus.PARAM_ERROR.getCode());
            baseModel.setErrorMessage("删除警员信息！异常");
            e.printStackTrace();
            log.error("删除警员信息！异常！", e);
        }
        return baseModel;
    }

    /**
     * @Description: 功能描述（修改警员信息）
     * @Author: 刘家义
     * @CreateDate: 2018/11/1 19:06
     */
    @ApiOperation(value = "修改 警员信息", notes = "修改 警员信息")
    @RequestMapping(value = "/updateGunUser", method = RequestMethod.PUT)
    public BaseModel updateGunUser(GunUser gunUser) {
        log.debug("修改 警员信息--warehouse--" + gunUser.toString());
        BaseModel baseModel = new BaseModel();
        baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
        try {
            if (null == gunUser) {
                baseModel.setErrorMessage("请您填写 警员信息");
                log.debug("修改警员信息--app-" + baseModel);
                return baseModel;
            }
            baseModel = this.gunUserService.revampGunUser(gunUser);
        } catch (Exception e) {
            baseModel.setStatus(IStatusMessage.SystemStatus.PARAM_ERROR.getCode());
            baseModel.setErrorMessage("修改 警员信息！异常");
            e.printStackTrace();
            log.error("修改 警员信息！异常！", e);
        }
        return baseModel;
    }

    /**
     * @Description: 功能描述（根据警员信息）
     * @Author: 刘家义
     * @CreateDate: 2018/11/1 19:53
     */
    @ApiOperation(value = "根据警员信息", notes = "获取警员信息")
    @RequestMapping(value = "/readGunUser", method = RequestMethod.GET)
    public BaseModel readGunUser(@RequestParam("gunUserId") Integer gunUserId) {
        BaseModel baseModel = new BaseModel();
        log.debug("获取警员信息--gunUserId--" + gunUserId);
        try {
            if (gunUserId != null && gunUserId > 0) {
                baseModel = this.gunUserService.findGunUserById(gunUserId);
                log.debug("获取警员信息成功！-gunUserId-" + gunUserId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获取警员信息异常！", e);
            baseModel.setStatus(IStatusMessage.SystemStatus.PARAM_ERROR.getCode());
            baseModel.setErrorMessage("获取警员信息异常！");
        }
        return baseModel;
    }

    /**
     * @Description: 功能描述（查询警员信息列表）
     * @Author: 刘家义
     * @CreateDate: 2018/11/1 20:01
     */
    @ApiOperation(value = "查询警员信息列表", notes = "获取警员信息列表")
    @RequestMapping(value = "/readGunUserList", method = RequestMethod.GET)
    public BaseModel readGunUserList(@RequestParam(value="pn",defaultValue="1") Integer pn, @RequestParam(value = "ps",defaultValue="5")Integer ps) {
        BaseModel baseModel=new BaseModel();
        PageHelper.startPage(pn,ps);
        log.debug("--------获取警员信息列表------！");
        try {
            List<GunUser> gunUserList =this.gunUserService.findGunUsers();
            PageInfo<GunUser> page = new PageInfo<GunUser>(gunUserList,5);
            baseModel.setStatus(IStatusMessage.SystemStatus.SUCCESS.getCode());
            baseModel.setErrorMessage("获取警员信息成功！");
            baseModel.add("pageInfo",page);
        }catch (Exception e){
            e.printStackTrace();
            log.error("警员信息查询异常！", e);
            baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
            baseModel.setErrorMessage("获取警员信息异常！");
        }
        return baseModel;
    }
}
