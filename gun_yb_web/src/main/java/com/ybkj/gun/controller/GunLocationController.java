package com.ybkj.gun.controller;

import com.ybkj.enums.IStatusMessage;
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
    public BaseModel readGunDynamic(@RequestParam(value = "gunId")String gunId
                                   ,@RequestParam(value = "appName")String appName){
        log.debug("--------获取枪支动态信息！-------gunId-------"+gunId+"-----appName-----"+appName);
        BaseModel baseModel=new BaseModel();
        try {
            List<GunLocation> gunLocations = gunLocationService.findGunDynamic(gunId,appName);
            baseModel.add("gunLocations",gunLocations);
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
}
