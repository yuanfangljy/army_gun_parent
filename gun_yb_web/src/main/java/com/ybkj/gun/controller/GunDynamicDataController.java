package com.ybkj.gun.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ybkj.enums.IStatusMessage;
import com.ybkj.gun.model.AppDynamicData;
import com.ybkj.gun.model.GunDynamicData;
import com.ybkj.gun.service.GunDynamicDataService;
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
 * @类描述：枪支的动态数据，其实就是定位模组的数据
 * @创建人：liujiayi
 * @创建时间：2018/11/4 16:02
 * @修改时间：2018/11/4 16:02
 * @version：1.0
 */
@SuppressWarnings("all")
@RestController
@Slf4j
@Api(value = "/",description = "枪支动态数据功能")
@RequestMapping("/gunDynamicData")
public class GunDynamicDataController {

    @Autowired
    private GunDynamicDataService gunDynamicDataService;

    /**
     * @Description: 功能描述（根据枪支编号,查询枪支动态数据）
     * @Author: 刘家义
     * @CreateDate: 2018/11/1 19:53
     * @param gunId 枪支编号
     * @return
     */
    @ApiOperation(value = "根据枪支编号,查询枪支动态数据", notes = "获取枪支动态信息")
    @RequestMapping(value = "/readGunDynamicData", method = RequestMethod.GET)
    public BaseModel readGunDynamicData(@RequestParam("gunId") Integer gunId) {
        BaseModel baseModel = new BaseModel();
        log.debug("获取枪支动态信息--gunId-" + gunId);
        try {
            if (gunId != null && gunId > 0) {
                baseModel = this.gunDynamicDataService.findGunDynamicDataByGunId(gunId);
                log.debug("获取枪支动态信息！-gunId-" + gunId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获取枪支动态信息异常！", e);
            baseModel.setStatus(IStatusMessage.SystemStatus.PARAM_ERROR.getCode());
            baseModel.setErrorMessage("获取枪支动态信息异常！");
        }
        return baseModel;
    }

    /**
     * @Description: 功能描述（查询枪支动态数据列表）
     * @Author: 刘家义
     * @CreateDate: 2018/11/1 20:01
     * @param pn 第几页
     * @param ps 每页多少条
     * @param appId 枪支编号
     * @return
     */
    @ApiOperation(value = "查询枪支动态数据列表", notes = "获取枪支动态数据列表")
    @RequestMapping(value = "/readGunDynamicDataList", method = RequestMethod.GET)
    public BaseModel readGunDynamicDataList(@RequestParam(value="pn",defaultValue="1") Integer pn, @RequestParam(value = "ps",defaultValue="5")Integer ps, @RequestParam(value = "gunId",required = false)String gunId) {
        BaseModel baseModel=new BaseModel();
        PageHelper.startPage(pn,ps);
        log.debug("--------获取枪支动态数据列表！");
        try {
            List<GunDynamicData> gunDynamicDatas =this.gunDynamicDataService.findGunDynamicDatas(gunId);
            PageInfo<GunDynamicData> page = new PageInfo<GunDynamicData>(gunDynamicDatas,5);
            baseModel.setStatus(IStatusMessage.SystemStatus.SUCCESS.getCode());
            baseModel.setErrorMessage("获取枪支动态数据列表成功！");
            baseModel.add("pageInfo",page);
        }catch (Exception e){
            e.printStackTrace();
            log.error("获取枪支动态数据列表查询异常！", e);
            baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
            baseModel.setErrorMessage("获取A枪支动态数据列表异常！");
        }
        return baseModel;
    }
}
