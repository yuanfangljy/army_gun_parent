package com.ybkj.gun.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ybkj.enums.IStatusMessage;
import com.ybkj.gun.model.Gun;
import com.ybkj.gun.service.GunService;
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

import java.util.Date;
import java.util.List;

/**
 * @项目名称：
 * @类名称：
 * @类描述：枪支功能
 * @创建人：liujiayi
 * @创建时间：2018/11/3 12:16
 * @修改时间：2018/11/3 12:16
 * @version：1.0
 */
@SuppressWarnings("all")
@RestController
@Slf4j
@Api(value = "/",description = "枪支功能")
@RequestMapping("/gun")
public class GunController {

    @Autowired
    private GunService gunService;

    /**
     * @Description:  功能描述（查询没有被预选的枪支）
     * @Author:       刘家义
     * @CreateDate:   2018/11/9 20:15
    */
    @ApiOperation(value = "查询没有被预选的枪支", notes = "获取没有被预选的枪支")
    @RequestMapping(value = "/readGunsNotPreselected", method = RequestMethod.GET)
    public BaseModel readGunsNotPreselected(@RequestParam(value="pn",defaultValue="1") Integer pn, @RequestParam(value = "ps",defaultValue="5")Integer ps){
        BaseModel baseModel=new BaseModel();
        PageHelper.startPage(pn,ps);
        log.debug("--------获取没有被预选的枪支列表！");
        try {
            List<Gun> guns =this.gunService.findGunsNotPreselected();
            PageInfo<Gun> page = new PageInfo<Gun>(guns,5);
            baseModel.setStatus(IStatusMessage.SystemStatus.SUCCESS.getCode());
            baseModel.setErrorMessage("查询成功！");
            baseModel.add("pageInfo",page);
        }catch (Exception e){
            e.printStackTrace();
            log.error("查询没有被预选的枪支列表异常！", e);
            baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
            baseModel.setErrorMessage("查询没有被预选的枪支列表异常！");
        }
        return baseModel;
    }

    /**
     * @Description:  功能描述（查询被预选的枪支列表）
     * @Author:       刘家义
     * @CreateDate:   2018/11/9 20:15
     */
    @ApiOperation(value = "查询被预选的枪支列表", notes = "获取被预选的枪支列表")
    @RequestMapping(value = "/readGunsPreselected", method = RequestMethod.GET)
    public BaseModel readGunsPreselected(@RequestParam(value="pn",defaultValue="1") Integer pn, @RequestParam(value = "ps",defaultValue="5")Integer ps){
        BaseModel baseModel=new BaseModel();
        PageHelper.startPage(pn,ps);
        log.debug("--------获取被预选的枪支列表！");
        try {
            List<Gun> guns =this.gunService.findGunsPreselected();
            PageInfo<Gun> page = new PageInfo<Gun>(guns,5);
            baseModel.setStatus(IStatusMessage.SystemStatus.SUCCESS.getCode());
            baseModel.setErrorMessage("查询成功！");
            baseModel.add("pageInfo",page);
        }catch (Exception e){
            e.printStackTrace();
            log.error("查询被预选的枪支列表异常！", e);
            baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
            baseModel.setErrorMessage("查询被预选的枪支列表异常！");
        }
        return baseModel;
    }




    /**
     * @Description: 功能描述（新增枪支）
     * @Author: 刘家义
     * @CreateDate: 2018/11/1 16:24
     * @param gun
     * @param wId   库室id
     * @param wName 库室名称
     */
    @ApiOperation(value = "新增枪支", notes = "添加枪支")
    @RequestMapping(value = "/createGun", method = RequestMethod.POST)
    public BaseModel createGun(Gun gun) throws Exception {
        log.debug("新增枪支--gun-" + gun.toString());
        BaseModel baseModel = new BaseModel();
        try {
            if (null == gun) {
                baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
                baseModel.setErrorMessage("请您填写枪支相关信息");
                log.debug("新增枪支--gun-" + baseModel);
                return baseModel;
            }
            if(null==gun.getWarehouseId() || null==gun.getWarehouseName()){
                baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
                baseModel.setErrorMessage("请选择库室");
                log.debug("新增枪支--gun-" + baseModel);
                return baseModel;
            }
            gun.setWarehouseId(gun.getWarehouseId());
            gun.setWarehouseName(gun.getWarehouseName());
            gun.setCreateTime(new Date());
            baseModel = gunService.addGun(gun);
        } catch (Exception e) {
            baseModel.setStatus(IStatusMessage.SystemStatus.PARAM_ERROR.getCode());
            baseModel.setErrorMessage("新增枪支！异常");
            e.printStackTrace();
            log.error("新增枪支！异常！", e);
        }
        return baseModel;
    }


    /**
     * @param wId
     * @return
     * @Description: 功能描述（删除枪支）
     * @Author: 刘家义
     * @CreateDate: 2018/11/1 16:31
     * @param gunId
     * @param type  0:正常  1：删除
     * @return
     * 逻辑删除
     */
    @ApiOperation(value = "删除枪支", notes = "删除枪支")
    @RequestMapping(value = "/deleteGun", method = RequestMethod.DELETE)
    public BaseModel deleteGun(@RequestParam("gunId") String gunId,@RequestParam("type")Integer type) {
        log.debug("删除枪支--gun--删除的id" + gunId);
        BaseModel baseModel = new BaseModel();
        try {
            if (StringUtils.isNoneBlank(gunId) || null==type) {
                baseModel = this.gunService.removeUun(gunId,type);
            } else {
                baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
                baseModel.setErrorMessage("删除枪支出错，请您稍后再试");
            }
        } catch (Exception e) {
            baseModel.setStatus(IStatusMessage.SystemStatus.PARAM_ERROR.getCode());
            baseModel.setErrorMessage("删除枪支！异常");
            e.printStackTrace();
            log.error("删除枪支！异常！", e);
        }
        return baseModel;
    }

    /**
     * @Description: 功能描述（修改枪支信息）
     * @Author: 刘家义
     * @CreateDate: 2018/11/1 19:06
     */
    @ApiOperation(value = "修改枪支信息", notes = "修改枪支信息")
    @RequestMapping(value = "updateGun", method = RequestMethod.PUT)
    public BaseModel updateGun(Gun gun) {

        log.debug("修改枪支信息--gun--" + gun.toString());
        BaseModel baseModel = new BaseModel();
        baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
        try {
            if (null == gun) {
                baseModel.setErrorMessage("请您填写枪支相关信息");
                log.debug("修改枪支--gun-" + baseModel);
                return baseModel;
            }
            gun.setUpdateTime(new Date());
            baseModel = this.gunService.revampGun(gun);
        } catch (Exception e) {
            baseModel.setStatus(IStatusMessage.SystemStatus.PARAM_ERROR.getCode());
            baseModel.setErrorMessage("修改枪支！异常");
            e.printStackTrace();
            log.error("修改枪支！异常！", e);
        }
        return baseModel;
    }

    /**
     * @Description: 功能描述（根据枪支id,查询库室信息）
     * @Author: 刘家义
     * @CreateDate: 2018/11/1 19:53
     */
    @ApiOperation(value = "根据枪号id,查询库室信息", notes = "获取枪支信息")
    @RequestMapping(value = "/readGun", method = RequestMethod.GET)
    public BaseModel readGun(@RequestParam("gunId") Integer gunId) {
        BaseModel baseModel = new BaseModel();
        log.debug("获取枪支信息--gunId-" + gunId);
        try {
            if (gunId != null && gunId > 0) {
                baseModel = this.gunService.findGunById(gunId);
                log.debug("获取枪支信息成功！-gunId-" + gunId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获取枪支信息异常！", e);
            baseModel.setStatus(IStatusMessage.SystemStatus.PARAM_ERROR.getCode());
            baseModel.setErrorMessage("获取枪支信息异常！");
        }
        return baseModel;
    }

    /**
     * @Description: 功能描述（查询枪支列表）
     * @Author: 刘家义
     * @CreateDate: 2018/11/1 20:01
     */
    @ApiOperation(value = "查询枪支列表", notes = "获取枪支列表")
    @RequestMapping(value = "/readGunList", method = RequestMethod.GET)
    public BaseModel readGunList(@RequestParam(value="pn",defaultValue="1") Integer pn, @RequestParam(value = "ps",defaultValue="5")Integer ps) {
        BaseModel baseModel=new BaseModel();
        PageHelper.startPage(pn,ps);
        log.debug("--------获取枪支列表！");
        try {
            List<Gun> guns =this.gunService.findGuns();
            PageInfo<Gun> page = new PageInfo<Gun>(guns,5);
            baseModel.setStatus(IStatusMessage.SystemStatus.SUCCESS.getCode());
            baseModel.setErrorMessage("获取枪支成功！");
            baseModel.add("pageInfo",page);
        }catch (Exception e){
            e.printStackTrace();
            log.error("枪支查询异常！", e);
            baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
            baseModel.setErrorMessage("枪支库室异常！");
        }
        return baseModel;
    }



}
