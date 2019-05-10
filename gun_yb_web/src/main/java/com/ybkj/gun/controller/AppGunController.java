package com.ybkj.gun.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ybkj.enums.IStatusMessage;
import com.ybkj.gun.model.App;
import com.ybkj.gun.model.AppGun;
import com.ybkj.gun.model.Gun;
import com.ybkj.gun.service.AppGunService;
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
 * @类描述：为设备分配枪支，指定用户（可无）
 * @创建人：liujiayi
 * @创建时间：2018/11/12 15:37
 * @修改时间：2018/11/12 15:37
 * @version：1.0
 */
@SuppressWarnings("all")
@RestController
@Slf4j
@Api(value = "/", description = "为设备分配枪支功能")
@RequestMapping("/appGun")
public class AppGunController {

    @Autowired
    private AppGunService appGunService;


    /**
     * @param gunIds    枪支id
     * @param appId     设备id
     * @param gunUserId 用户id
     * @return
     * @Description: 功能描述（新增分配枪支）
     * @Author: 刘家义
     * @CreateDate: 2018/11/12 15:41
     */
    @ApiOperation(value = "新增分配枪支", notes = "分配枪支")
    @RequestMapping(value = "/createAppGun", method = RequestMethod.POST)
    public BaseModel createAppGun(@RequestParam(value = "gunIds", required = false) String gunIds,
                                  @RequestParam(value = "appId", required = false) String appId,
                                  @RequestParam(value = "gunUserId", defaultValue = "") Integer gunUserId) {
        log.debug("新增分配枪支------appGun：------gunIds-" + gunIds + "---appId--" + appId + "---gunUserId--" + gunUserId);
        BaseModel baseModel = new BaseModel();
        try {
            if (StringUtils.isEmpty(gunIds)) {
                baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
                baseModel.setErrorMessage("请选择出库的枪支");
                log.debug("枪支分配--appGun-" + baseModel);
                return baseModel;
            }
            if (null == appId) {
                baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
                baseModel.setErrorMessage("请选择设备");
                log.debug("枪支分配--appGun-" + baseModel);
                return baseModel;
            }
            baseModel=appGunService.addAppGun(gunIds, appId, gunUserId);
        } catch (Exception e) {
            baseModel.setStatus(IStatusMessage.SystemStatus.PARAM_ERROR.getCode());
            baseModel.setErrorMessage("枪支分配！异常");
            e.printStackTrace();
            log.error("枪支分配！异常！", e);
        }
        return baseModel;
    }


    /**
     * @Description:  功能描述（查询所有绑定的枪支的设备）
     * @Author:       刘家义
     * @CreateDate:   2018/11/12 19:06
     * @param type    0:预出库   1：军械员操作出库
    */
    @ApiOperation(value = "查询所有绑定的枪支的设备", notes = "枪支绑定设备")
    @RequestMapping(value = "/readAppGunBinding", method = RequestMethod.GET)
    public BaseModel readAppGunBinding(@RequestParam(value="pn",defaultValue="1") Integer pn,@RequestParam(value = "ps",defaultValue="5")Integer ps,@RequestParam(value = "type",defaultValue="",required = false)String type){
        log.debug("----------------新查询所有绑定的枪支的设备---------------");
        BaseModel baseModel=new BaseModel();
        PageHelper.startPage(pn,ps);
        try {
            List<AppGun> appGuns= appGunService.findAppGunBinding(type);
            PageInfo<AppGun> page = new PageInfo<AppGun>(appGuns,5);
            baseModel.setStatus(IStatusMessage.SystemStatus.SUCCESS.getCode());
            baseModel.setErrorMessage("查询成功！");
            baseModel.add("pageInfo",page);
        }catch (Exception e){
            baseModel.setStatus(IStatusMessage.SystemStatus.PARAM_ERROR.getCode());
            baseModel.setErrorMessage("查询枪支绑定设备！异常");
            e.printStackTrace();
            log.error("查询枪支绑定设备！异常！", e);
        }
        return baseModel;
    }

    /**
     * @Description:  功能描述（查询离位的枪支）
     * @Author:       刘家义
     * @CreateDate:   2018/12/10 16:40
     */
    @ApiOperation(value = "查询离位的枪支", notes = "获取离位的枪支")
    @RequestMapping(value = "/readOffNormalGun", method = RequestMethod.GET)
    public BaseModel readOffNormalGun(@RequestParam(value="pn",defaultValue="1") Integer pn, @RequestParam(value = "ps",defaultValue="5")Integer ps,@RequestParam(value="gunId",defaultValue="") String gunId){
        BaseModel baseModel=new BaseModel();
        PageHelper.startPage(pn,ps);
        log.debug("--------获取离位的枪支列表---------！");
        try {
            List<AppGun> guns =this.appGunService.findOffNormalGun(gunId);
            PageInfo<AppGun> page = new PageInfo<AppGun>(guns,5);
            baseModel.setStatus(IStatusMessage.SystemStatus.SUCCESS.getCode());
            baseModel.setErrorMessage("查询成功！");
            baseModel.add("pageInfo",page);
        }catch (Exception e){
            e.printStackTrace();
            log.error("查询离位的枪支列表异常！", e);
            baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
            baseModel.setErrorMessage("查询离位的枪支列表异常！");
        }
        return baseModel;
    }



    /**
     * @Description:  功能描述（查询离位的枪支数）
     * @Author:       刘家义
     * @CreateDate:   2018/12/10 16:40
     */
    @ApiOperation(value = "查询离位的枪支数", notes = "获取离位的枪支数")
    @RequestMapping(value = "/readOffNormalGunCount", method = RequestMethod.GET)
    public BaseModel readOffNormalGunCount(){
        BaseModel baseModel=new BaseModel();
        log.debug("--------获取离位的枪支数---------！");
        try {
            Integer gunCount=this.appGunService.findOffNormalGunCount();
            baseModel.add("gunCount",gunCount);
            baseModel.setStatus(IStatusMessage.SystemStatus.SUCCESS.getCode());
            baseModel.setErrorMessage("查询成功！");
        }catch (Exception e){
            e.printStackTrace();
            log.error("获取离位的枪支数异常！", e);
            baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
            baseModel.setErrorMessage("获取离位的枪支数异常！");
        }
        return baseModel;
    }


    /**
     * @Description:  功能描述（查询在线设备）
     * @Author:       刘家义
     * @CreateDate:   2018/12/10 16:40
     */
    @ApiOperation(value = "查询在线设备", notes = "获取在线设备")
    @RequestMapping(value = "/readOnLineApp", method = RequestMethod.GET)
    public BaseModel readOnLineApp(@RequestParam(value="pn",defaultValue="1") Integer pn, @RequestParam(value = "ps",defaultValue="5")Integer ps,@RequestParam(value="appName",defaultValue="") String appName){
        BaseModel baseModel=new BaseModel();
        PageHelper.startPage(pn,ps);
        log.debug("--------获取在线设备列表---------！");
        try {
            List<AppGun> guns =this.appGunService.findOnLineApp(appName);
            PageInfo<AppGun> page = new PageInfo<AppGun>(guns,5);
            baseModel.setStatus(IStatusMessage.SystemStatus.SUCCESS.getCode());
            baseModel.setErrorMessage("查询成功！");
            baseModel.add("pageInfo",page);
        }catch (Exception e){
            e.printStackTrace();
            log.error("查询在线设备列表异常！", e);
            baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
            baseModel.setErrorMessage("查询在线设备列表异常！");
        }
        return baseModel;
    }


    /**
     * @Description:  功能描述（查询在线设备数）
     * @Author:       刘家义
     * @CreateDate:   2018/12/11 13:39
    */
    @ApiOperation(value = "查询在线设备数", notes = "获取在线设备数")
    @RequestMapping(value = "/readOnLineAppCount", method = RequestMethod.GET)
    public BaseModel readOnLineAppCount(){
        BaseModel baseModel=new BaseModel();
        log.debug("--------获取在线设备数---------！");
        try {
            Integer countApp =this.appGunService.findOnLineAppCount();
            baseModel.add("countApp",countApp);
            baseModel.setStatus(IStatusMessage.SystemStatus.SUCCESS.getCode());
            baseModel.setErrorMessage("查询成功！");
        }catch (Exception e){
            e.printStackTrace();
            log.error("获取在线设备数异常！", e);
            baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
            baseModel.setErrorMessage("获取在线设备数异常！");
        }
        return baseModel;
    }
}
