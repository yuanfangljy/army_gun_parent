package com.ybkj.gun.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ybkj.enums.IStatusMessage;
import com.ybkj.gun.model.AppDynamicData;
import com.ybkj.gun.model.Gun;
import com.ybkj.gun.service.AppDynamicDataService;
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
 * @类描述：手机/腕表的动态数据
 * @创建人：liujiayi
 * @创建时间：2018/11/4 15:03
 * @修改时间：2018/11/4 15:03
 * @version：1.0
 */
@SuppressWarnings("all")
@RestController
@Slf4j
@Api(value = "/",description = "腕表/手机动态数据功能")
@RequestMapping("/appDynamicData")
public class AppDynamicDataController {

    @Autowired
    private AppDynamicDataService appDynamicDataService;

    /**
     * @Description: 功能描述（根据aapId,查询app动态信息）
     * @Author: 刘家义
     * @CreateDate: 2018/11/1 19:53
     */
    @ApiOperation(value = "根据aapId,查询app动态信息", notes = "获取app动态信息")
    @RequestMapping(value = "/readAppDynamicData", method = RequestMethod.GET)
    public BaseModel readAppDynamicData(@RequestParam("appId") Integer appId) {
        BaseModel baseModel = new BaseModel();
        log.debug("获取app动态信息--appId-" + appId);
        try {
            if (appId != null && appId > 0) {
                baseModel = this.appDynamicDataService.findAppDynamicDataByAppId(appId);
                log.debug("获取app动态信息！-appId-" + appId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获取app动态信息异常！", e);
            baseModel.setStatus(IStatusMessage.SystemStatus.PARAM_ERROR.getCode());
            baseModel.setErrorMessage("获取app动态信息异常！");
        }
        return baseModel;
    }

    /**
     * @Description: 功能描述（查询app动态列表）
     * @Author: 刘家义
     * @CreateDate: 2018/11/1 20:01
     */
    @ApiOperation(value = "查询App动态列表", notes = "获取App动态列表")
    @RequestMapping(value = "/readAppDynamicDataList", method = RequestMethod.GET)
    public BaseModel readAppDynamicDataList(@RequestParam(value="pn",defaultValue="1") Integer pn, @RequestParam(value = "ps",defaultValue="5")Integer ps,@RequestParam(value = "appName",required = false)String appName) {
        BaseModel baseModel=new BaseModel();
        PageHelper.startPage(pn,ps);
        log.debug("--------获取App动态列表！");
        try {
            List<AppDynamicData> appDynamicDatas =this.appDynamicDataService.findAppDynamicDatas(appName);
            PageInfo<AppDynamicData> page = new PageInfo<AppDynamicData>(appDynamicDatas,5);
            baseModel.setStatus(IStatusMessage.SystemStatus.SUCCESS.getCode());
            baseModel.setErrorMessage("获取App动态列表成功！");
            baseModel.add("pageInfo",page);
        }catch (Exception e){
            e.printStackTrace();
            log.error("获取App动态列表查询异常！", e);
            baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
            baseModel.setErrorMessage("获取App动态列表异常！");
        }
        return baseModel;
    }
}
