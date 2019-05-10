package com.ybkj.gun.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ybkj.common.entity.GunLocationVO;
import com.ybkj.enums.IStatusMessage;
import com.ybkj.gun.model.GunLocation;
import com.ybkj.gun.service.GunLocationService;
import com.ybkj.model.BaseModel;
import com.ybkj.untils.DateTool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @项目名称：
 * @类名称：
 * @类描述：
 * @创建人：liujiayi
 * @创建时间：2018/11/12 14:01
 * @修改时间：2018/11/12 14:01
 * @version：1.0
 */
@SuppressWarnings("all")
@RestController
@Slf4j
@Api(value = "/",description = "查询出库的枪支信息")
@RequestMapping("/gunLocation")
public class GunLocationController {

    @Autowired
    private GunLocationService gunLocationService;

    /**
     * @Description:  功能描述（查询枪支动态信息）
     * @Author:       刘家义
     * @CreateDate:   2018/11/12 14:05
     * @param gunId   根据枪支编号查询
     * @param appName   根据腕表名称查询
     * @return
    */
    @ApiOperation(value = "查询枪支动态信息", notes = "获取枪支动态信息")
    @RequestMapping(value = "/readGunDynamic", method = RequestMethod.GET)
    public BaseModel readGunDynamic(@RequestParam(value = "gunId",defaultValue = "")String gunId
                                   ,@RequestParam(value = "appName",defaultValue = "")String appName){
        log.debug("--------获取枪支动态信息！-------gunId-------"+gunId+"-----appName-----"+appName);

        BaseModel baseModel=new BaseModel();
        List<String> locationList=new ArrayList<>();
        try {
            List<GunLocationVO> gunLocations = gunLocationService.findGunDynamic(gunId,appName);
            baseModel.add("gunLocations",gunLocations);
            baseModel.add("locationList",locationList);
            baseModel.setStatus(IStatusMessage.SystemStatus.SUCCESS.getCode());
            baseModel.setErrorMessage("查询成功！");
        }catch (Exception e){
            e.printStackTrace();
            log.error("获取枪支动态信息异常！", e);
            baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
            baseModel.setErrorMessage("获取枪支动态信息异常！");
        }
        return baseModel;
    }

    /**
     * @Description:  功能描述（优化实时枪支显示）
     * @Author:       刘家义
     * @CreateDate:   2018/12/5 10:27
     * 1、查询app_gun,查询所有状态为 1 的枪支
     * 2、根据获取到的枪支，获取到最新的数据，根据时间排序获取到最新的数据 limit 1
     * 3、根据相应的信息，查询对应的app和gun数据
    */
    @ApiOperation(value = "优化查询枪支动态信息", notes = "获取枪支动态信息")
    @RequestMapping(value = "/readGunDynamicOptimize", method = RequestMethod.GET)
    public BaseModel readGunDynamicOptimize(@RequestParam(value = "gunId",defaultValue = "")String gunId
            ,@RequestParam(value = "appName",defaultValue = "")String appName){
        log.debug("--------获取枪支动态信息！-------gunId-------"+gunId+"-----appName-----"+appName);

        BaseModel baseModel=new BaseModel();
        try {
            baseModel = gunLocationService.findGunDynamicOptimize(gunId,appName);
        }catch (Exception e){
            e.printStackTrace();
            log.error("获取枪支动态信息异常！", e);
            baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
            baseModel.setErrorMessage("获取枪支动态信息异常！");
        }
        return baseModel;
    }



    /**
     * @Description:  功能描述（查询枪支动态信息）
     * @Author:       刘家义
     * @CreateDate:   2018/11/12 14:05
     * @param gunId   根据枪支编号查询
     * @param appName   根据腕表名称查询
     * @return
     */
    @ApiOperation(value = "查询枪支动态信息列表", notes = "获取枪支动态信息列表")
    @RequestMapping(value = "/readGunDynamicList", method = RequestMethod.GET)
    public BaseModel readGunDynamicList(@RequestParam(value="pn",defaultValue="1") Integer pn, @RequestParam(value = "ps",defaultValue="5")Integer ps,@RequestParam(value = "gunId",defaultValue = "")String gunId
            ,@RequestParam(value = "appName",defaultValue = "")String appName){
        log.debug("--------获取枪支动态信息列表！-------gunId-------"+gunId+"-----appName-----"+appName);
        BaseModel baseModel=new BaseModel();
        List<String> locationList=new ArrayList<>();
        PageHelper.startPage(pn,ps);
        try {
            List<GunLocationVO> gunLocations = gunLocationService.findGunDynamic(gunId,appName);
            PageInfo<GunLocationVO> page = new PageInfo<GunLocationVO>(gunLocations,5);
            baseModel.add("pageInfo",page);
            baseModel.add("gunLocations",gunLocations);
            baseModel.add("locationList",locationList);
            baseModel.setStatus(IStatusMessage.SystemStatus.SUCCESS.getCode());
            baseModel.setErrorMessage("查询成功！");
        }catch (Exception e){
            e.printStackTrace();
            log.error("获取枪支动态信息列表异常！", e);
            baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
            baseModel.setErrorMessage("获取枪支动态信息列表异常！");
        }
        return baseModel;
    }


    /**
     * @Description:  功能描述（查找周围在线的设备）
     * @Author:       刘家义
     * @CreateDate:   2018/12/1 18:35
    */
    @ApiOperation(value = "查找周围在线的设备", notes = "获取周围在线的设备")
    @RequestMapping(value = "/readRoundDevice", method = RequestMethod.GET)
    public BaseModel readRoundDevice(@RequestParam(value = "lng",defaultValue = "")String lng,@RequestParam(value = "lat",defaultValue = "")String lat,@RequestParam(value = "gunMac",defaultValue = "")String gunMac){
        log.debug("--------查找周围在线的设备！-------lng-------"+lng+"-----lat-----"+lat+"-----gunMac-----"+gunMac);
        BaseModel baseModel=new BaseModel();
        try{
            List<GunLocation> gunRoundDevice = gunLocationService.findRoundDevice(lng,lat,gunMac);
            baseModel.add("gunRoundDevices",gunRoundDevice);
            baseModel.setStatus(IStatusMessage.SystemStatus.SUCCESS.getCode());
            baseModel.setErrorMessage("查询成功！");
        }catch (Exception e){
            e.printStackTrace();
            log.error("获取周围在线的设备异常！", e);
            baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
            baseModel.setErrorMessage("获取周围在线的设备异常！");
        }
        return baseModel;
    }


    /**
     * @Description:  功能描述（查询枪支的轨迹）
     * @Author:       刘家义
     * @CreateDate:   2018/11/26 19:45
    */
    @ApiOperation(value = "查询枪支的轨迹", notes = "查询枪支的轨迹")
    @RequestMapping(value = "/readGunTrajectory", method = RequestMethod.GET)
    public BaseModel readGunTrajectory(
            @RequestParam(value = "gunId",defaultValue = "")String gunId,
            @RequestParam(value = "appImei")String imei,
            @RequestParam(value = "imeiType")Integer imeiType,
            @RequestParam(value = "startTimes")String startTimes,
            @RequestParam(value = "endTimes")String endTimes){
        log.debug("--------查询枪支的轨迹！-------appImei-------"+imei+"-----imeiType-------"+imeiType+"-----startTimes-------"+startTimes+"-----endTimes-------"+endTimes);
        BaseModel baseModel=new BaseModel();
        try {
            if(StringUtils.isEmpty(imei) || imeiType==null){
                baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
                baseModel.setErrorMessage("查询失败！");
                return baseModel;
            }
            if(startTimes.equals("") || endTimes.equals("")){
                baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
                baseModel.setErrorMessage("请选择时间范围");
                return baseModel;
            }
          /*  Calendar beforeTime = Calendar.getInstance();
            beforeTime.add(Calendar.MINUTE, -30);// 3分钟之前的时间
            Date startTime = beforeTime.getTime();
            Date endTime = new Date();
            if(!startTimes.equals("") && !endTimes.equals("")){
                startTime= DateTool.stringToDate(startTimes);
                endTime= DateTool.stringToDate(endTimes);
            }*/
            List<GunLocation> gunTrajectory = gunLocationService.findGunTrajectory(imei,imeiType,startTimes,endTimes);
            baseModel.add("gunTrajectoryList",gunTrajectory)
                     .add("numberLocations",gunTrajectory.size());
            baseModel.setStatus(IStatusMessage.SystemStatus.SUCCESS.getCode());
            baseModel.setErrorMessage("查询成功！");
        }catch (Exception e){
            e.printStackTrace();
            log.error("查询枪支的轨迹异常！", e);
            baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
            baseModel.setErrorMessage("查询枪支的轨迹异常！");
        }
        return baseModel;
    }


    //==========================实时显示设备和枪支的位置================================

    @ApiOperation(value = "实时显示设备和枪支的位置", notes = "实时显示设备和枪支的位置")
    @RequestMapping(value = "/readAppAndGunLocation", method = RequestMethod.GET)
    public BaseModel readAppAndGunLocation(@RequestParam(value="imei",defaultValue="") String imei){
        log.debug("--------实时显示设备和枪支的位置！-------imei-------"+imei);
        BaseModel baseModel=new BaseModel();
        try {
            baseModel=gunLocationService.findAppAndGunLocation(imei);
            /*baseModel.setStatus(IStatusMessage.SystemStatus.SUCCESS.getCode());
            baseModel.setErrorMessage("查询成功！");*/
        }catch (Exception e){
            e.printStackTrace();
            log.error("实时显示设备和枪支的位置异常！", e);
            baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
            baseModel.setErrorMessage("实时显示设备和枪支的位置异常！");
        }
        return baseModel;
    }
}
