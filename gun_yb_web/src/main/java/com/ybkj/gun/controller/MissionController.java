package com.ybkj.gun.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ybkj.enums.IStatusMessage;
import com.ybkj.gun.model.Gun;
import com.ybkj.gun.model.Mission;
import com.ybkj.gun.service.MissionService;
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
 * @类描述：协助查找功能模块
 * @创建人：liujiayi
 * @创建时间：2018/11/4 14:35
 * @修改时间：2018/11/4 14:35
 * @version：1.0
 */
@SuppressWarnings("all")
@RestController
@Slf4j
@Api(value = "/",description = "协助查找功能")
@RequestMapping("/mission")
public class MissionController {

    @Autowired
    private MissionService missionService;

    /**
     * 问题：需求不明确
     *     1、是怎么样发送，协助查询，不需要指定发送给某一个腕表吗？
     *
     * @Description:  功能描述（协助查找）
     * @Author:       刘家义
     * @CreateDate:   2018/11/4 14:44
     * 1、判断丢失的枪，是否已经连接上
     * 2、再将创建协助查找消息，进行推送消息
    */
    public BaseModel insertMission(){

        return null;
    }


    /**
     * @Description:  功能描述（查询协助查找信息）
     * @Author:       刘家义
     * @CreateDate:   2018/11/23 9:27
     * @param  gunMac:设备号
    */
    @ApiOperation(value = "查询协助查找信息", notes = "查询协助查找信息")
    @RequestMapping(value = "/readMission", method = RequestMethod.GET)
    public BaseModel readMission(@RequestParam(value="pn",defaultValue="1") Integer pn, @RequestParam(value = "ps",defaultValue="5")Integer ps,@RequestParam(value="gunMac",defaultValue = "")String gunMac){
        BaseModel baseModel=new BaseModel();
        PageHelper.startPage(pn,ps);
        log.debug("--------获取协助查找信息列表！");
        try {
            List<Mission> missions =this.missionService.findMissions(gunMac);
            PageInfo<Mission> page = new PageInfo<Mission>(missions,5);
            baseModel.setStatus(IStatusMessage.SystemStatus.SUCCESS.getCode());
            baseModel.setErrorMessage("查询成功！");
            baseModel.add("pageInfo",page);
        }catch (Exception e){
            e.printStackTrace();
            log.error("查询协助查找信息列表异常！", e);
            baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
            baseModel.setErrorMessage("查询协助查找信息列表异常！");
        }
        return baseModel;
    }

    
    /**
     * @Description:  功能描述（枪支查找启停控制）
     * @Author:       刘家义
     * @CreateDate:   2018/11/23 9:48
     * @param type:   1——停止/0——重启
    */
    @ApiOperation(value = "枪支查找启停控制", notes = "枪支查找启停控制")
    @RequestMapping(value = "/restartMission", method = RequestMethod.PUT)
    public BaseModel restartMission(@RequestParam(value="type",required = false) String type,@RequestParam(value="appImei",required = false) String appImei){
        BaseModel baseModel=new BaseModel();
        log.debug("--------枪支查找启停控制！");
        try {
            if(StringUtils.isEmpty(type) || StringUtils.isEmpty(appImei)){
                baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
                baseModel.setErrorMessage("操作失败！");
                log.error("操作失败，暴力操作，使值为空！");
                return baseModel;
            }else{
                baseModel=missionService.restartMission(type,appImei);
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error("枪支查找启停控制异常！", e);
            baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
            baseModel.setErrorMessage("枪支查找启停控制异常！");
        }
        return baseModel;
    }
}
