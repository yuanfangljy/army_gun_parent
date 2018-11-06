package com.ybkj.gun.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ybkj.enums.IStatusMessage;
import com.ybkj.gun.model.Warehouse;
import com.ybkj.gun.service.WareHouseService;
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
 * @类描述：库室功能模块
 * @创建人：liujiayi
 * @创建时间：2018/11/1 15:15
 * @修改时间：2018/11/1 15:15
 * @version：1.0
 */
@RestController
@Slf4j
@SuppressWarnings("ALL")
@Api(value = "/", description = "库室模块")
@RequestMapping("/warehouse")
public class WareHouseController {

    @Autowired
    private WareHouseService wareHouseService;

    /**
     * @Description: 功能描述（创建库室）
     * @Author: 刘家义
     * @CreateDate: 2018/11/1 16:24
     */
    @ApiOperation(value = "创建库室", notes = "添加库室")
    @RequestMapping(value = "/createWareHouse", method = RequestMethod.POST)
    public BaseModel createWareHouse(Warehouse warehouse) throws Exception {
        log.debug("新增库室--warehouse-" + warehouse.toString());
        BaseModel baseModel = new BaseModel();
        try {
            if (null == warehouse) {
                baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
                baseModel.setErrorMessage("请您填写库室相关信息");
                log.debug("新增库室--warehouse-" + baseModel);
                return baseModel;
            }
            warehouse.setInserttime(new Date());
            baseModel = wareHouseService.addWareHouse(warehouse);
        } catch (Exception e) {
            baseModel.setStatus(IStatusMessage.SystemStatus.PARAM_ERROR.getCode());
            baseModel.setErrorMessage("新增库室！异常");
            e.printStackTrace();
            log.error("新增库室！异常！", e);
        }
        return baseModel;
    }


    /**
     * @param wId
     * @return
     * @Description: 功能描述（删除库室）
     * @Author: 刘家义
     * @CreateDate: 2018/11/1 16:31
     */
    @ApiOperation(value = "删除库室", notes = "删除库室")
    @RequestMapping(value = "/deleteWareHouse", method = RequestMethod.DELETE)
    public BaseModel deleteWareHouse(@RequestParam("wId") String wId) {
        log.debug("删除库室--warehouse--删除的id" + wId);
        BaseModel baseModel = new BaseModel();
        try {
            if (StringUtils.isNoneBlank(wId)) {
                baseModel = this.wareHouseService.removeWareHourse(wId);
            } else {
                baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
                baseModel.setErrorMessage("删除库室出错，请您稍后再试");
            }
        } catch (Exception e) {
            baseModel.setStatus(IStatusMessage.SystemStatus.PARAM_ERROR.getCode());
            baseModel.setErrorMessage("删除库室！异常");
            e.printStackTrace();
            log.error("删除库室！异常！", e);
        }
        return baseModel;
    }

    /**
     * @Description: 功能描述（修改库室信息）
     * @Author: 刘家义
     * @CreateDate: 2018/11/1 19:06
     */
    @ApiOperation(value = "修改库室信息", notes = "修改库室信息")
    @RequestMapping(value = "updateWareHouse", method = RequestMethod.PUT)
    public BaseModel updateWareHouse(Warehouse warehouse) {
        log.debug("修改库室信息--warehouse--" + warehouse.toString());
        BaseModel baseModel = new BaseModel();
        baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
        try {
            if (null == warehouse) {
                baseModel.setErrorMessage("请您填写库室相关信息");
                log.debug("修改库室--warehouse-" + baseModel);
                return baseModel;
            }
            warehouse.setUpdatetime(new Date());
            baseModel = this.wareHouseService.revampWareHouse(warehouse);
        } catch (Exception e) {
            baseModel.setStatus(IStatusMessage.SystemStatus.PARAM_ERROR.getCode());
            baseModel.setErrorMessage("修改库室！异常");
            e.printStackTrace();
            log.error("修改库室！异常！", e);
        }
        return baseModel;
    }

    /**
     * @Description: 功能描述（根据库室id,查询库室信息）
     * @Author: 刘家义
     * @CreateDate: 2018/11/1 19:53
     */
    @ApiOperation(value = "根据库室id,查询库室信息", notes = "获取库室信息")
    @RequestMapping(value = "/readWareHouse", method = RequestMethod.GET)
    public BaseModel readWareHouse(@RequestParam("wId") Integer wId) {
        BaseModel baseModel = new BaseModel();
        log.debug("获取库室信息--mid-" + wId);
        try {
            if (wId != null && wId > 0) {
                baseModel = this.wareHouseService.findWareHouseById(wId);
                log.debug("获取库室信息成功！-mid-" + wId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获取库室信息异常！", e);
            baseModel.setStatus(IStatusMessage.SystemStatus.PARAM_ERROR.getCode());
            baseModel.setErrorMessage("获取库室信息异常！");
        }
        return baseModel;
    }

    /**
     * @Description: 功能描述（查询库室列表）
     * @Author: 刘家义
     * @CreateDate: 2018/11/1 20:01
     */
    @ApiOperation(value = "查询库室列表", notes = "获取库室列表")
    @RequestMapping(value = "/getMenuList", method = RequestMethod.GET)
    public BaseModel readWareHouseList(@RequestParam(value="pn",defaultValue="1") Integer pn,@RequestParam(value = "ps",defaultValue="5")Integer ps) {
        BaseModel baseModel=new BaseModel();
        PageHelper.startPage(pn,ps);
        log.debug("--------获取库室列表！");
        try {
            List<Warehouse> warehouses =this.wareHouseService.findWarehouses();
            PageInfo<Warehouse> page = new PageInfo<Warehouse>(warehouses,5);
            baseModel.setStatus(IStatusMessage.SystemStatus.SUCCESS.getCode());
            baseModel.setErrorMessage("获取库室成功！");
            baseModel.add("pageInfo",page);
        }catch (Exception e){
            e.printStackTrace();
            log.error("库室查询异常！", e);
            baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
            baseModel.setErrorMessage("获取库室异常！");
        }
        return baseModel;
    }
}
