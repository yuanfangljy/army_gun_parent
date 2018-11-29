package com.ybkj.gun.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ybkj.common.entity.GunLocationVO;
import com.ybkj.enums.IStatusMessage;
import com.ybkj.gun.model.Gun;
import com.ybkj.gun.model.GunLocation;
import com.ybkj.gun.service.GunLocationService;
import com.ybkj.model.BaseModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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



    @ApiOperation(value = "查找周围在线的设备", notes = "获取周围在线的设备")
    @RequestMapping(value = "/readRoundDevice", method = RequestMethod.GET)
    public BaseModel readRoundDevice(@RequestParam(value = "lng",defaultValue = "")String lng,@RequestParam(value = "lat",defaultValue = "")String lat){
        log.debug("--------查找周围在线的设备！-------lng-------"+lng+"-----lat-----"+lat);
        BaseModel baseModel=new BaseModel();
        try{
            List<GunLocation> gunRoundDevice = gunLocationService.findRoundDevice(lng,lat);
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
    public BaseModel readGunTrajectory(@RequestParam(value = "gunId",defaultValue = "")String gunId,@RequestParam(value = "appImei",defaultValue = "")String appImei){
        log.debug("--------查询枪支的轨迹！-------gunId-------"+gunId);
        BaseModel baseModel=new BaseModel();
        try {
            List<GunLocation> gunTrajectory = gunLocationService.findGunTrajectory(appImei);
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

}
