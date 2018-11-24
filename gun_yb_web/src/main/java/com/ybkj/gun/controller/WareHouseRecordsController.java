package com.ybkj.gun.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ybkj.enums.IStatusMessage;
import com.ybkj.gun.model.WarehouseRecords;
import com.ybkj.gun.service.WareHouseRecordsService;
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

import java.util.List;

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
     * @param appIMEI          imei
     * @param gunUserId        人员编号
     * @return 1、直接Gun表中选择对应的枪支，可以进行多选（要获取在枪支是哪一个库室中的）
     * 2、要选择对应的腕表
     * 3、获取是哪一个管理员操作的
     * 4、推送消息给服务器
     * @Description: 功能描述（创建出库记录）
     * @Author: 刘家义
     * @CreateDate: 2018/11/5 9:49
     */
    @ApiOperation(value = "第一版：（暂不用）05号报文：枪支预出库", notes = "05号：枪支预出库")
    @RequestMapping(value = "/createWareHouseRecordsBeforehandDelivery", method = RequestMethod.POST)
    public BaseModel createWareHouseRecordsBeforehandDelivery(@RequestParam(value = "gunIds", required = false) String gunIds, @RequestParam(value = "appIMEI", defaultValue = "") String appIMEI, @RequestParam(value = "gunUserId", defaultValue = "") Integer gunUserId) {
        log.debug("05：枪支预出库------WareHouseRecords：------gunIds-" + gunIds + "---appIMEI--" + appIMEI + "---gunUserId--" + gunUserId);
        BaseModel baseModel = new BaseModel();
        try {
            if (StringUtils.isEmpty(gunIds)) {
                baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
                baseModel.setErrorMessage("请选择出库的枪支");
                log.debug("枪支预出库--WareHouseRecords-" + baseModel);
                return baseModel;
            }
            /*if (null == appId) {
                baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
                baseModel.setErrorMessage("请选择设备");
                log.debug("枪支预出库--WareHouseRecords-" + baseModel);
                return baseModel;
            }*/
            baseModel = wareHouseRecordsService.addWareHouseRecordsBeforehandDelivery(gunIds, appIMEI, gunUserId);

        } catch (Exception e) {
            baseModel.setStatus(IStatusMessage.SystemStatus.PARAM_ERROR.getCode());
            baseModel.setErrorMessage("枪支预出库！异常");
            e.printStackTrace();
            log.error("枪支预出库！异常！", e);
        }
        return baseModel;
    }



    /**
     * @Description:  功能描述（05 -- 07 :预出库--出库）
     * @Author:       刘家义
     * @CreateDate:   2018/11/12 20:12
     * @param appId
     * @param type    5:表示  预出库   7：表示  军械员最终出库
     * @return
    */
    @ApiOperation(value = "第二版：05 -- 07 :预出库--出库", notes = "05号-07号：预出库--出库")
    @RequestMapping(value = "/createDeviceBindingGunsBeforehandDelivery", method = RequestMethod.POST)
    public BaseModel createDeviceBindingGunsBeforehandDelivery(@RequestParam(value = "appId", required = false) String appId,@RequestParam(value = "type", defaultValue = "5") String type,@RequestParam(value = "endTime", defaultValue = "") String endTime){
        BaseModel baseModel=new BaseModel();
        if(type.equals("5")){
            log.debug("05：枪支预出库------DeviceBindingGuns：--appId-"+appId );
            try {
                baseModel = wareHouseRecordsService.addDeviceBindingGunsBeforehandDelivery(appId);
            }catch (Exception e){
                baseModel.setStatus(IStatusMessage.SystemStatus.PARAM_ERROR.getCode());
                baseModel.setErrorMessage("枪支预出库！异常");
                e.printStackTrace();
                log.error("枪支预出库！异常！", e);
            }
        }else if(type.equals("7")){
            log.debug("07：枪支出库------DeviceBindingGuns：--appId-"+appId );
            try {
                baseModel = wareHouseRecordsService.endWareHouseRecordsDelivery(appId,endTime);
            }catch (Exception e){
                baseModel.setStatus(IStatusMessage.SystemStatus.PARAM_ERROR.getCode());
                baseModel.setErrorMessage("枪支出库！异常");
                e.printStackTrace();
                log.error("枪支出库！异常！", e);
            }
        }else{
            baseModel.setStatus(IStatusMessage.SystemStatus.PARAM_ERROR.getCode());
            baseModel.setErrorMessage("枪支失败");
        }
        return baseModel;
    }



    /**
     * @param userId     警员编号
     * @param gunId      枪支编号（多个）
     * @param gunMac     枪支mac （多个）
     * @param returnTime 归还时间
     * @param appId      设备Id
     * @return 注意：
     * 可以一把一把的点击最终出库
     * 也可以全选最终出库
     * <p>
     * 1、通过userId警员编号来获取userName警员姓名
     * 2、获取到枪号和枪支蓝牙号
     * @Description: 功能描述（最终确认出库指令）
     * @Author: 刘家义
     * @CreateDate: 2018/11/6 9:47
     */
    @ApiOperation(value = "07号报文：最终出库", notes = "07：最终出库")
    @RequestMapping(value = "/endWareHouseRecordsDelivery", method = RequestMethod.PUT)
    public BaseModel endWareHouseRecordsDelivery(@RequestParam(value = "userId") Integer userId, @RequestParam(value = "gunId") String gunId, @RequestParam(value = "gunMac") String gunMac, @RequestParam(value = "returnTime") String returnTime
            , @RequestParam(value = "appId") String appId) {
        log.debug("07号报文：最终出库----userId----" + userId + "gunId----" + gunId + "----" + "----gunMac---" + gunMac+ "----appId---" + appId);
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
            if (null == userId) {
                baseModel = wareHouseRecordsService.endWareHouseRecordsDelivery(gunId, gunMac, endTime,appId);
            } else {
                baseModel = wareHouseRecordsService.endWareHouseRecordsDelivery(userId, gunId, gunMac, endTime,appId);
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
     * @param gunId 枪支编号（多个）
     * @Description: 功能描述（撤销出库指令：09，设备与枪支进行解绑）  第二版
     * @Author: 刘家义
     * @CreateDate: 2018/11/6 16:12
     */
    @ApiOperation(value = "09号报文：撤销出库", notes = "07：撤销出库")
    @RequestMapping(value = "/revocationWareHouseRecordsDelivery_2", method = RequestMethod.DELETE)
    public BaseModel revocationWareHouseRecordsDelivery_2(@RequestParam(value = "appId",required = false) Integer appId) {
        log.debug("09号报文：撤销出库----appId----" + appId);
        BaseModel baseModel = new BaseModel();
        try {
            if (null==appId) {
                baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
                baseModel.setErrorMessage("枪支撤销失败！");
                log.debug("枪支撤销失败！--appId为空---" + appId + "----" + baseModel);
                return baseModel;
            }

            baseModel = wareHouseRecordsService.revocationWareHouseRecordsDelivery(appId);
        } catch (Exception e) {
            baseModel.setStatus(IStatusMessage.SystemStatus.PARAM_ERROR.getCode());
            baseModel.setErrorMessage("枪支出库撤销！异常");
            e.printStackTrace();
            log.error("枪支出库撤销！异常！", e);
        }
        return baseModel;
    }



    /**
     * @param gunId 枪支编号（多个）
     * @Description: 功能描述（撤销出库指令：09，设备与枪支进行解绑）  第一版
     * @Author: 刘家义
     * @CreateDate: 2018/11/6 16:12
     */
    @ApiOperation(value = "09号报文：撤销出库", notes = "07：撤销出库")
    @RequestMapping(value = "/revocationWareHouseRecordsDelivery", method = RequestMethod.DELETE)
    public BaseModel revocationWareHouseRecordsDelivery(@RequestParam(value = "gunId") String gunId , @RequestParam(value = "appId") Integer appId) {
        log.debug("09号报文：撤销出库----gunId----" + gunId);
        BaseModel baseModel = new BaseModel();
        try {
            if (StringUtils.isEmpty(gunId)) {
                baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
                baseModel.setErrorMessage("枪支撤销失败！");
                log.debug("枪支撤销失败！--gunId为空---" + gunId + "----" + baseModel);
                return baseModel;
            }

            baseModel = wareHouseRecordsService.revocationWareHouseRecordsDelivery(gunId,appId);
        } catch (Exception e) {
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
     * @Description: 功能描述（预入库）
     * @Author: 刘家义
     * @CreateDate: 2018/11/6 17:24
     */
    @ApiOperation(value = "11号报文：枪支预入库", notes = "07：枪支预入库")
    @RequestMapping(value = "/createWareHouseRecordsBeforehandStorage", method = RequestMethod.POST)
    public BaseModel createWareHouseRecordsBeforehandStorage(@RequestParam(value = "gunId") String gunId,@RequestParam(value = "appImei") String appImei) {
        log.debug("11号报文：枪支预入库----gunId----" + gunId+"-----appImei----" + appImei);
        BaseModel baseModel = new BaseModel();
        try {
            if (StringUtils.isEmpty(gunId) || StringUtils.isEmpty(appImei)) {
                baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
                baseModel.setErrorMessage("枪支入库失败！");
                log.debug("枪支预入库失败！--gunId为空---" + gunId + "----" + baseModel);
                return baseModel;
            }
            baseModel = wareHouseRecordsService.addWareHouseRecordsBeforehandStorage(gunId,appImei);
        } catch (Exception e) {
            baseModel.setStatus(IStatusMessage.SystemStatus.PARAM_ERROR.getCode());
            baseModel.setErrorMessage("枪支预入库撤销！异常");
            e.printStackTrace();
            log.error("枪支预入库撤销！异常！", e);
        }
        return baseModel;
    }


    /**
     * @Description: 功能描述（是否入库撤销：枪支与原有的枪支进行绑定）
     * @Author: 刘家义
     * @CreateDate: 2018/11/6 19:39
     * 0：撤销
     * 1：入库
     */
    @ApiOperation(value = "13号报文：是否撤销入库 0：撤销 1：入库", notes = "13：是否撤销入库")
    @RequestMapping(value = "/revocationWareHouseRecordsStorage", method = RequestMethod.DELETE)
    public BaseModel revocationWareHouseRecordsStorage(@RequestParam(value = "gunId") String gunId, @RequestParam(value = "gunMac") String gunMac, @RequestParam(value = "appId") String appId,@RequestParam(value = "state") String state) {
        log.debug("13号报文：是否撤销入库----gunId----" + gunId + "----" + "----gunMac---" + gunMac + "----appId---" + appId + "----state---" + state);
        BaseModel baseModel = new BaseModel();
        try {
            if (StringUtils.isEmpty(gunMac) || StringUtils.isEmpty(gunId)) {
                baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
                if(state.equals(String.valueOf(1))){
                    baseModel.setErrorMessage("枪支入库，响应异常！");
                    log.debug("枪支入库，响应异常！--revocationWareHouseRecordsStorage-" + baseModel);
                }else{
                    baseModel.setErrorMessage("枪支撤销入库，响应异常！");
                    log.debug("枪支撤销入库，响应异常！--revocationWareHouseRecordsStorage-" + baseModel);
                }
                return baseModel;
            }
            baseModel = wareHouseRecordsService.revocationWareHouseRecordsStorage(gunId, gunMac,appId,state);
        } catch (Exception e) {
            baseModel.setStatus(IStatusMessage.SystemStatus.PARAM_ERROR.getCode());
            if(state.equals(String.valueOf(1))){
                baseModel.setErrorMessage("枪支入库！异常");
                log.error("枪支入库！异常！", e);
            }else{
                baseModel.setErrorMessage("枪支撤销入库，异常！");
                log.error("枪支撤销入库！异常！", e);
            }
            e.printStackTrace();

        }
        return baseModel;
    }

    //****** END   *************************** 入库 ***************************  END  ******

    /**
     * @Description: 功能描述（查询库存记录表）
     * @Author: 刘家义
     * @CreateDate: 2018/11/10 11:10
     * 1、默认是查询，入库中的枪支信息   出入库状态（1:出库中 2：已出库 3：入库中 4：已入库 ）
     */
    @ApiOperation(value = "查询库存记录表，根据出入库状态", notes = "查询库存记录表")
    @RequestMapping(value = "/readWareHouseRecords", method = RequestMethod.GET)
    public BaseModel readWareHouseRecords(@RequestParam(value = "pn", defaultValue = "1") Integer pn, @RequestParam(value = "ps", defaultValue = "5") Integer ps, @RequestParam(value = "typeState", defaultValue = "3") Integer type) throws Exception {

       /* List<WarehouseRecords> w = this.wareHouseRecordsService.findWareHouseRecords(type);
        System.out.println(w.size());*/

        BaseModel baseModel = new BaseModel();
        PageHelper.startPage(pn, ps);
        log.debug("--------查询  【" + ((type == 1) ? "出库中" : (type == 2) ? "已出库" : (type == 3) ? "入库中" : "已入库") + "】！" + "库存记录");
        try {
            List<WarehouseRecords> warehouseRecords = this.wareHouseRecordsService.findWareHouseRecords(type);
            System.out.println(warehouseRecords.size());
            PageInfo<WarehouseRecords> page = new PageInfo<WarehouseRecords>(warehouseRecords, 5);
            baseModel.setStatus(IStatusMessage.SystemStatus.SUCCESS.getCode());
            baseModel.setErrorMessage("查询成功！");
            baseModel.add("pageInfo", page);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询库存记录表列表异常！", e);
            baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
            baseModel.setErrorMessage("查询库存记录表列表异常！");
        }
        return baseModel;
    }



}
