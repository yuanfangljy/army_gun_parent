package com.ybkj.gun.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ybkj.enums.IStatusMessage;
import com.ybkj.gun.model.App;
import com.ybkj.gun.model.SosMessage;
import com.ybkj.gun.service.SosMessageService;
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
 * @类描述：警告信息功能模块
 * @创建人：liujiayi
 * @创建时间：2018/11/3 15:53
 * @修改时间：2018/11/3 15:53
 * @version：1.0
 */

@SuppressWarnings("all")
@RestController
@Slf4j
@Api(value = "/",description = "警告信息功能")
@RequestMapping("/sosMessage")
public class SosMessageController {

    @Autowired
    private SosMessageService sosMessageService;

    /**
     * @Description:  功能描述（查询警告信息列表:枪号）
     * @Author:       刘家义
     * @CreateDate:   2018/11/3 16:45
    */
    @ApiOperation(value = "查询警告信息列表", notes = "获取警告信息列表")
    @RequestMapping(value = "/readSosMessageList", method = RequestMethod.GET)
    public BaseModel readSosMessageList(@RequestParam(value="pn",defaultValue="1") Integer pn, @RequestParam(value = "ps",defaultValue="5")Integer ps,@RequestParam(value = "gunCode",required=false)String gunCode,@RequestParam(value = "appCode",required=false)String appCode){

        BaseModel baseModel=new BaseModel();
        PageHelper.startPage(pn,ps);
        log.debug("--------获取警告信息列表------！");
        try {
            List<SosMessage> sosMessagesList =this.sosMessageService.findSosMessages(gunCode);
           /* for (SosMessage sosMessage : sosMessagesList) {
                System.out.println("ssss---"+sosMessage);
            }*/
            PageInfo<SosMessage> page = new PageInfo<SosMessage>(sosMessagesList,5);
            baseModel.setStatus(IStatusMessage.SystemStatus.SUCCESS.getCode());
            baseModel.setErrorMessage("获取警告信息成功！");
            baseModel.add("pageInfo",page);
            log.debug("--------获取警告信息成功------！");
        }catch (Exception e){
            e.printStackTrace();
            log.error("警告信息查询异常！", e);
            baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
            baseModel.setErrorMessage("获取警告信息异常！");
        }
        return baseModel;
    }

    /**
     * @Description:  功能描述（推送消息给指定的设备：）
     * @Author:       刘家义
     * @CreateDate:   2018/11/24 16:12
     * 如果枪支MAC枪号和丢失时间为空时，表示是紧急支援事件，如果不为空，则是协助查找事件。
     * @param appImei 多个，可发送给多个app设备
     * @param sosId   离位信息的ID
     * @param type    任务类型（0.协助查找 1：紧急支援）
     * @return
     */
    @ApiOperation(value = "推送消息给指定的设备", notes = "推送消息给指定的设备")
    @RequestMapping(value = "/createForHelpGun", method = RequestMethod.POST)
    public BaseModel createForHelpGun(@RequestParam(value = "appImei")String appImei,@RequestParam(value = "sosId")Integer sosId,@RequestParam(value = "type")Integer type){
        log.debug("--------推送消息给指定的设备----appImei----"+appImei+"---sosId---"+sosId);
        BaseModel baseModel=new BaseModel();
        try {
            if(StringUtils.isEmpty(appImei) || sosId==null || type==null){
                baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
                baseModel.setErrorMessage("操作失败！");
                return baseModel;
            }else{
                baseModel=sosMessageService.createForHelpGun(appImei,sosId,type);
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error("推送消息给指定的设备异常！", e);
            baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
            baseModel.setErrorMessage("推送消息给指定的设备异常！");
        }
        return baseModel;
    }
}
