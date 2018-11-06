package com.ybkj.gun.controller;

import com.ybkj.enums.IStatusMessage;
import com.ybkj.gun.model.WarehouseRecords;
import com.ybkj.gun.service.WareHouseRecordsService;
import com.ybkj.model.BaseModel;
import com.ybkj.untils.DateTool;
import com.ybkj.untils.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @项目名称：
 * @类名称：
 * @类描述：枪支出入库预出库、入库--->出入库绑定
 * @创建人：liujiayi
 * @创建时间：2018/11/5 9:20
 * @修改时间：2018/11/5 9:20
 * @version：1.0
 */
@RestController
@Slf4j
@SuppressWarnings("ALL")
@Api(value = "/", description = "枪支出入库")
@RequestMapping("/wareHouseRecords")
public class WareHouseRecordsController {

    @Autowired
    private WareHouseRecordsService wareHouseRecordsService;


    //****** START *************************** 出库 *************************** START ******
    /**
     * @param warehouseRecords
     * @param gunIds           枪支Id
     * @param appId            腕表/手机Id
     * @param gunUserId        人员编号
     * @return 1、直接Gun表中选择对应的枪支，可以进行多选（要获取在枪支是哪一个库室中的）
     * 2、要选择对应的腕表
     * 3、获取是哪一个管理员操作的
     * 4、推送消息给服务器
     * @Description: 功能描述（创建出库记录）
     * @Author: 刘家义
     * @CreateDate: 2018/11/5 9:49
     */
    @ApiOperation(value = "05号报文：枪支预出库", notes = "05号：枪支预出库")
    @RequestMapping(value = "/createWareHouseRecordsBeforehandDelivery", method = RequestMethod.POST)
    public BaseModel createWareHouseRecordsBeforehandDelivery(@RequestParam(value = "gunIds", required = false) String gunIds, @RequestParam(value = "appId", required = false) Integer appId, @RequestParam(value = "gunUserId", required = false, defaultValue = "") Integer gunUserId) {
        log.debug("05：枪支预出库------WareHouseRecords：------gunIds-" + gunIds + "---appId--" + appId + "---gunUserId--" + gunUserId);
        BaseModel baseModel = new BaseModel();
        try {
            if (StringUtils.isEmpty(gunIds)) {
                baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
                baseModel.setErrorMessage("请选择出库的枪支");
                log.debug("枪支预出库--WareHouseRecords-" + baseModel);
                return baseModel;
            }
            if (null == appId) {
                baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
                baseModel.setErrorMessage("请选择设备");
                log.debug("枪支预出库--WareHouseRecords-" + baseModel);
                return baseModel;
            }
            baseModel = wareHouseRecordsService.addWareHouseRecordsBeforehandDelivery(gunIds, appId, gunUserId);

        } catch (Exception e) {
            baseModel.setStatus(IStatusMessage.SystemStatus.PARAM_ERROR.getCode());
            baseModel.setErrorMessage("枪支预出库！异常");
            e.printStackTrace();
            log.error("枪支预出库！异常！", e);
}
        return baseModel;
                }

/**
 * @Description: 功能描述（最终确认出库指令）
 * @Author: 刘家义
 * @CreateDate: 2018/11/6 9:47
 * @param userId 警员编号
 * @param gunId  枪支编号（多个）
 * @param gunMac 枪支mac （多个）
 * @param returnTime  归还时间
 * @return 注意：
 * 可以一把一把的点击最终出库
 * 也可以全选最终出库
 * <p>
 * 1、通过userId警员编号来获取userName警员姓名
 * 2、获取到枪号和枪支蓝牙号
 */
@ApiOperation(value = "07号报文：最终出库", notes = "07：最终出库")
@RequestMapping(value = "/endWareHouseRecordsDelivery", method = RequestMethod.PUT)
public BaseModel endWareHouseRecordsDelivery(@RequestParam(value = "userId") Integer userId, @RequestParam(value = "gunId") String gunId, @RequestParam(value = "gunMac") String gunMac, @RequestParam(value = "returnTime")String returnTime) {
        log.debug("07号报文：最终出库----userId----" + userId + "gunId----" + gunId + "----" + "----gunMac---" + gunMac);
        BaseModel baseModel = new BaseModel();
        try {
        if (StringUtils.isEmpty(gunMac) || StringUtils.isEmpty(gunId) || StringUtils
        .isEmpty(returnTime)) {
        baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
        baseModel.setErrorMessage("枪支预出库，响应异常！");
        log.debug("枪支出库，响应异常！--endWareHouseRecordsDelivery-" + baseModel);
        return baseModel;
        }
        String endTime = DateTool.cutToString(returnTime);
        if(null==userId){
        baseModel = wareHouseRecordsService.endWareHouseRecordsDelivery(gunId, gunMac,endTime);
        }else{
        baseModel = wareHouseRecordsService.endWareHouseRecordsDelivery(userId, gunId, gunMac,endTime);
        }
        } catch (Exception e) {
        baseModel.setStatus(IStatusMessage.SystemStatus.PARAM_ERROR.getCode());
        baseModel.setErrorMessage("枪支出库！异常");
        e.printStackTrace();
        log.error("枪支出库！异常！", e);
        }
        return baseModel;
        }

/**
 * @Description:  功能描述（撤销出库指令：09，设备与枪支进行解绑）
 * @Author:       刘家义
 * @CreateDate:   2018/11/6 16:12
 * @param gunId  枪支编号（多个）
 */
@ApiOperation(value = "09号报文：撤销出库", notes = "07：撤销出库")
@RequestMapping(value = "/revocationWareHouseRecordsDelivery", method = RequestMethod.DELETE)
public BaseModel revocationWareHouseRecordsDelivery(@RequestParam(value = "gunId")String gunId){
        log.debug("09号报文：撤销出库----gunId----"+gunId);
        BaseModel baseModel=new BaseModel();
        try {
        if(StringUtils.isEmpty(gunId)){
        baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
        baseModel.setErrorMessage("枪支撤销失败！");
        log.debug("枪支撤销失败！--gunId为空---"+gunId+"----"+ baseModel);
        return baseModel;
        }

        baseModel = wareHouseRecordsService.revocationWareHouseRecordsDelivery(gunId);
        }catch (Exception e){
        baseModel.setStatus(IStatusMessage.SystemStatus.PARAM_ERROR.getCode());
        baseModel.setErrorMessage("枪支出库撤销！异常");
        e.printStackTrace();
        log.error("枪支出库撤销！异常！", e);
        }
        return baseModel;
        }
//****** END *************************** 出库 *************************** END ******


//=====================================================================================================


//****** START *************************** 入库 *************************** START ******

    /**
     * @Description:  功能描述（预入库）
     * @Author:       刘家义
     * @CreateDate:   2018/11/6 17:24
    */
    @ApiOperation(value = "11号报文：枪支预入库", notes = "07：枪支预入库")
    @RequestMapping(value = "/createWareHouseRecordsBeforehandStorage", method = RequestMethod.POST)
    public BaseModel createWareHouseRecordsBeforehandStorage(@RequestParam(value = "gunId")String gunId){
        log.debug("11号报文：枪支预入库----gunId----"+gunId);
        BaseModel baseModel=new BaseModel();
        try {
            if(StringUtils.isEmpty(gunId)){
                baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
                baseModel.setErrorMessage("枪支撤销失败！");
                log.debug("枪支预入库失败！--gunId为空---"+gunId+"----"+ baseModel);
                return baseModel;
            }
            baseModel = wareHouseRecordsService.addWareHouseRecordsBeforehandStorage(gunId);
        }catch (Exception e){
            baseModel.setStatus(IStatusMessage.SystemStatus.PARAM_ERROR.getCode());
            baseModel.setErrorMessage("枪支预入库撤销！异常");
            e.printStackTrace();
            log.error("枪支预入库撤销！异常！", e);
        }
        return baseModel;
    }


    /**
     * @Description:  功能描述（入库撤销：枪支与原有的枪支进行绑定）
     * @Author:       刘家义
     * @CreateDate:   2018/11/6 19:39
    */
    @ApiOperation(value = "13号报文：撤销入库", notes = "13：撤销入库")
    @RequestMapping(value = "/revocationWareHouseRecordsStorage", method = RequestMethod.DELETE)
    public BaseModel revocationWareHouseRecordsStorage(@RequestParam(value = "gunId") String gunId, @RequestParam(value = "gunMac") String gunMac){
        log.debug("13号报文：撤销入库----gunId----" + gunId + "----" + "----gunMac---" + gunMac);
        BaseModel baseModel = new BaseModel();
        try {
            if (StringUtils.isEmpty(gunMac) || StringUtils.isEmpty(gunId)) {
                baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
                baseModel.setErrorMessage("枪支撤销入库，响应异常！");
                log.debug("枪支撤销入库，响应异常！--revocationWareHouseRecordsStorage-" + baseModel);
                return baseModel;
            }
            baseModel = wareHouseRecordsService.revocationWareHouseRecordsStorage(gunId,gunMac);
        }catch (Exception e){
            baseModel.setStatus(IStatusMessage.SystemStatus.PARAM_ERROR.getCode());
            baseModel.setErrorMessage("枪支撤销入库！异常");
            e.printStackTrace();
            log.error("枪支撤销入库！异常！", e);
        }
        return baseModel;
    }

    //****** END   *************************** 入库 ***************************  END  ******

}
