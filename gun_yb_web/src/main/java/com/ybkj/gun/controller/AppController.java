package com.ybkj.gun.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ybkj.enums.IStatusMessage;
import com.ybkj.gun.model.App;
import com.ybkj.gun.model.Gun;
import com.ybkj.gun.service.AppService;
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
 * @类描述：腕表/手机功能
 * @创建人：liujiayi
 * @创建时间：2018/11/2 14:31
 * @修改时间：2018/11/2 14:31
 * @version：1.0
 */
@SuppressWarnings("all")
@RestController
@Slf4j
@Api(value = "/",description = "腕表/手机功能")
@RequestMapping("/app")
public class AppController {

    @Autowired
    private AppService appService;

    /**
     * @Description: 功能描述（创建 腕表/手机 账号）
     * @Author: 刘家义
     * @CreateDate: 2018/11/1 16:24
     */
    @ApiOperation(value = "创建 腕表/手机 账号", notes = "腕表/手机")
    @RequestMapping(value = "/createApp", method = RequestMethod.POST)
    public BaseModel createApp(App app) throws Exception {
        log.debug("新增腕表/手机--app-" + app.toString());
        BaseModel baseModel = new BaseModel();
        try {
            if (null == app) {
                baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
                baseModel.setErrorMessage("请您填写设备相关信息");
                log.debug("新增腕表/手机--app-" + baseModel);
                return baseModel;
            }
            baseModel = appService.addApp(app);
        } catch (Exception e) {
            baseModel.setStatus(IStatusMessage.SystemStatus.PARAM_ERROR.getCode());
            baseModel.setErrorMessage("新增腕表/手机！异常");
            e.printStackTrace();
            log.error("新增腕表/手机！异常！", e);
        }
        return baseModel;
    }


    /**
     * @param wId
     * @return
     * @Description: 功能描述（删除腕表/手机）
     * @Author: 刘家义
     * @CreateDate: 2018/11/1 16:31
     */
    @ApiOperation(value = "删除腕表/手机", notes = "删除腕表/手机")
    @RequestMapping(value = "/deleteApp", method = RequestMethod.DELETE)
    public BaseModel deleteApp(@RequestParam("appId") String appId) {
        log.debug("删除腕表/手机--app--删除的腕表/手机" + appId);
        BaseModel baseModel = new BaseModel();
        try {
            if (StringUtils.isNoneBlank(appId)) {
                baseModel = this.appService.removeApp(appId);
            } else {
                baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
                baseModel.setErrorMessage("删除腕表/手机出错，请您稍后再试");
            }
        } catch (Exception e) {
            baseModel.setStatus(IStatusMessage.SystemStatus.PARAM_ERROR.getCode());
            baseModel.setErrorMessage("删除腕表/手机！异常");
            e.printStackTrace();
            log.error("删除腕表/手机！异常！", e);
        }
        return baseModel;
    }

    /**
     * @Description: 功能描述（修改腕表/手机信息）
     * @Author: 刘家义
     * @CreateDate: 2018/11/1 19:06
     */
    @ApiOperation(value = "修改 腕表/手机 信息", notes = "修改 腕表/手机 信息")
    @RequestMapping(value = "updateApp", method = RequestMethod.PUT)
    public BaseModel updateApp(App app) {
        log.debug("修改 腕表/手机 信息--warehouse--" + app.toString());
        BaseModel baseModel = new BaseModel();
        baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
        try {
            if (null == app) {
                baseModel.setErrorMessage("请您填写 腕表/手机 相关信息");
                log.debug("修改腕表/手机--app-" + baseModel);
                return baseModel;
            }
            baseModel = this.appService.revampApp(app);
        } catch (Exception e) {
            baseModel.setStatus(IStatusMessage.SystemStatus.PARAM_ERROR.getCode());
            baseModel.setErrorMessage("修改 腕表/手机！异常");
            e.printStackTrace();
            log.error("修改 腕表/手机！异常！", e);
        }
        return baseModel;
    }

    /**
     * @Description: 功能描述（根据库室id,查询腕表/手机信息）
     * @Author: 刘家义
     * @CreateDate: 2018/11/1 19:53
     */
    @ApiOperation(value = "根据腕表/手机id,查询库室信息", notes = "获取腕表/手机信息")
    @RequestMapping(value = "/readApp", method = RequestMethod.GET)
    public BaseModel readApp(@RequestParam("appId") Integer appId) {
        BaseModel baseModel = new BaseModel();
        log.debug("获取腕表/手机信息--appId--" + appId);
        try {
            if (appId != null && appId > 0) {
                baseModel = this.appService.findAppById(appId);
                log.debug("获取腕表/手机信息成功！-appId-" + appId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获取腕表/手机信息异常！", e);
            baseModel.setStatus(IStatusMessage.SystemStatus.PARAM_ERROR.getCode());
            baseModel.setErrorMessage("获取腕表/手机信息异常！");
        }
        return baseModel;
    }

    /**
     * @Description: 功能描述（查询腕表/手机列表）
     * @Author: 刘家义
     * @CreateDate: 2018/11/1 20:01
     */
    @ApiOperation(value = "查询腕表/手机列表", notes = "获取腕表/手机列表")
    @RequestMapping(value = "/readAppList", method = RequestMethod.GET)
    public BaseModel readAppList(@RequestParam(value="pn",defaultValue="1") Integer pn,@RequestParam(value = "ps",defaultValue="5")Integer ps) {
        BaseModel baseModel=new BaseModel();
        PageHelper.startPage(pn,ps);
        log.debug("--------获取腕表/手机列表------！");
        try {
            List<App> appList =this.appService.findApps();
            PageInfo<App> page = new PageInfo<App>(appList,5);
            baseModel.setStatus(IStatusMessage.SystemStatus.SUCCESS.getCode());
            baseModel.setErrorMessage("获取腕表/手机成功！");
            baseModel.add("pageInfo",page);
        }catch (Exception e){
            e.printStackTrace();
            log.error("腕表/手机查询异常！", e);
            baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
            baseModel.setErrorMessage("获取腕表/手机异常！");
        }
        return baseModel;
    }
}
