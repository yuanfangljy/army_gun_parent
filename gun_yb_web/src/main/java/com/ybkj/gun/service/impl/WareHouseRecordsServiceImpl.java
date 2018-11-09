package com.ybkj.gun.service.impl;

import com.ybkj.common.activceMq.Producer;
import com.ybkj.common.pojo.BindingReqMessageBody;
import com.ybkj.common.util.ActiveUser;
import com.ybkj.common.util.StringUtilUD;
import com.ybkj.enums.IStatusMessage;
import com.ybkj.gun.mapper.AppDynamicDataMapper;
import com.ybkj.gun.mapper.GunMapper;
import com.ybkj.gun.mapper.GunUserMapper;
import com.ybkj.gun.mapper.WarehouseRecordsMapper;
import com.ybkj.gun.model.*;
import com.ybkj.gun.service.WareHouseRecordsService;
import com.ybkj.model.BaseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.Exception;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @项目名称：
 * @类名称：
 * @类描述：枪支出入库业务逻辑具体实现
 * @创建人：liujiayi
 * @创建时间：2018/11/5 10:15
 * @修改时间：2018/11/5 10:15
 * @version：1.0
 */
@SuppressWarnings("all")
@Service
@Transactional
@Slf4j
public class WareHouseRecordsServiceImpl implements WareHouseRecordsService {

    @Autowired
    private WarehouseRecordsMapper warehouseRecordsMapper;
    @Autowired
    private GunMapper gunMapper;
    @Autowired
    private Producer producer;
    @Autowired
    private AppDynamicDataMapper appDynamicDataMapper;
    @Autowired
    private GunUserMapper gunUserMapper;


    /**
     * @Description: 功能描述（枪支预出库）
     * @Author: 刘家义
     * @CreateDate: 2018/11/5 10:16
     * 1、根据gunIds在gun表中，来获取对应库房Id和枪支编号
     * 2、获取当前web登录用户
     * 3、创建预出库记录，同时推送消息给服务器
     */
    @Override
    public BaseModel addWareHouseRecordsBeforehandDelivery(String gunIds, Integer appId, Integer gunUserId) throws Exception {
        BaseModel baseModel = new BaseModel();
        WarehouseRecords warehouseRecords = new WarehouseRecords();
        //保存要推送的枪支信息
        List<BindingReqMessageBody> bindingReqMessageBodies = new ArrayList<>();
        List<BindingReqMessageBody.GunInfo> bindingReqMessageBodiesGunInfo = new ArrayList<>();
        BindingReqMessageBody bindingReqMessageBody = new BindingReqMessageBody();
        BindingReqMessageBody.GunInfo gunInfo = new BindingReqMessageBody.GunInfo();

        //5、将腕表或者手机的动态状态改成 1 表示改设备已经在出库中
        AppDynamicData appDynamicData = appDynamicDataMapper.selectAppDynamicByAppId(appId);
        if (null == appDynamicData) {
            baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
            baseModel.setErrorMessage("预出库失败");
            log.debug("枪支预出库--查询appId可能为空-" + "---" + appId + "--" + baseModel);
            return baseModel;
        }

        //1、根据gunIds在gun表中，来获取对应库房Id和枪支编号
        String[] strings = StringUtilUD.slicerComma(gunIds);
        for (String gunId : strings) {
            Gun gun = gunMapper.selectByPrimaryKey(Integer.valueOf(gunId));
            if (gun == null) {
                baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
                baseModel.setErrorMessage("操作出现问题，请稍后再试");
                log.debug("枪支预出库--查询Id可能为空-" + baseModel + "=----" + gun.toString());
                return baseModel;
            }
            warehouseRecords.setWarehouseId(gun.getWarehouseId());
            warehouseRecords.setGunId(gun.getGunId());
            warehouseRecords.setAppId(appId);
            warehouseRecords.setState(0);
            warehouseRecords.setWarehouseInTime(new Date());
            //2、获取当前web登录用户
            if (ActiveUser.isActiveUser()) {
                WebUser activeUser = ActiveUser.getActiveUser();
                warehouseRecords.setWebUserId(activeUser.getId());
            } else {
                baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
                baseModel.setErrorMessage("您未登录或登录超时，请您登录后再试");
                log.debug("枪支预出库--查询Id可能为空-" + baseModel);
                return baseModel;
            }
            //3、创建预出库记录
            warehouseRecordsMapper.insertSelective(warehouseRecords);
            //4、临时保存将要预出库发送给服务器的临时消息
            bindingReqMessageBody.setUserId(String.valueOf(gunUserId));
            gunInfo.setGunId(gun.getGunId());
            gunInfo.setGunMac(gun.getGunMac());
            gunInfo.setGunModel(gun.getGunModel());
            gunInfo.setGunType(gun.getGunType());
            bindingReqMessageBodiesGunInfo.add(gunInfo);

        }
        bindingReqMessageBody.setGunList(bindingReqMessageBodiesGunInfo);
        bindingReqMessageBodies.add(bindingReqMessageBody);

        //appDynamicData.setState(1);//设置腕表已出库中
        //6、推送消息给服务器
        baseModel = producer.sendMessageAdvanceTheDelivery(bindingReqMessageBodies);
        baseModel.setStatus(IStatusMessage.SystemStatus.SUCCESS.getCode());
        baseModel.setErrorMessage("枪支预出库成功！");
        return baseModel;
    }

    /**
     * @Description: 功能描述（枪支最终出库） 警员信息存在
     * @Author: 刘家义
     * @CreateDate: 2018/11/6 14:44
     */
    @Override
    public BaseModel endWareHouseRecordsDelivery(Integer userId, String gunId, String gunMac, String endTime) throws Exception {
        BaseModel baseModel = new BaseModel();
        //1、通过userId，查询userName获取到警员的相关信息
        GunUser gunUser = gunUserMapper.selectByPrimaryKey(userId);
        if (null == gunUser) {
            baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
            baseModel.setErrorMessage("人员【" + userId + "】不存在，请联系管理员");
            log.debug("人员【" + userId + "】不存在-" + baseModel);
            return baseModel;
        }
        //2、对gunId，gunMmc以“，”分割进行解析
        String[] gunIds = StringUtilUD.slicerComma(gunId);
        String[] gunMacs = StringUtilUD.slicerComma(gunMac);
        for (int i = 0; i < gunMacs.length; i++) {
            //3、下发07报文给服务器，进行最终出库操作
            String mac = gunMacs[i];
            String gun = gunMacs[i];
            baseModel = producer.sendMessageEndDelivery(userId, gunUser.getUserName(), gun, mac, endTime);
        }
        baseModel.setStatus(IStatusMessage.SystemStatus.SUCCESS.getCode());
        baseModel.setErrorMessage("最终出库成功！");
        return baseModel;
    }


    /**
     * @Description: 功能描述（枪支最终出库）警员信息不存在
     * @Author: 刘家义
     * @CreateDate: 2018/11/6 14:44
     */
    @Override
    public BaseModel endWareHouseRecordsDelivery(String gunId, String gunMac, String endTime) throws Exception {
        BaseModel baseModel = new BaseModel();
        //1、对gunId，gunMmc以“，”分割进行解析
        String[] gunIds = StringUtilUD.slicerComma(gunId);
        String[] gunMacs = StringUtilUD.slicerComma(gunMac);
        for (int i = 0; i < gunMacs.length; i++) {
            //2、下发07报文给服务器，进行最终出库操作
            String mac = gunMacs[i];
            String gun = gunMacs[i];
            baseModel = producer.sendMessageEndDelivery(gun, mac, endTime);
        }
        baseModel.setStatus(IStatusMessage.SystemStatus.SUCCESS.getCode());
        baseModel.setErrorMessage("最终出库成功！");
        return baseModel;
    }

    /**
     * @Description: 功能描述（撤销出库：09）
     * @Author: 刘家义
     * @CreateDate: 2018/11/6 16:19
     */
    @Override
    public BaseModel revocationWareHouseRecordsDelivery(String gunId) throws Exception {
        BaseModel baseModel = new BaseModel();
        //1、对gunId“，”分割进行解析
        String[] gunIds = StringUtilUD.slicerComma(gunId);
        for (String gId : gunIds) {
            //2、下发09报文给服务器，进行出库撤销操作
            baseModel = producer.sendMessageRevocationDelivery(gId);
        }
        baseModel.setStatus(IStatusMessage.SystemStatus.SUCCESS.getCode());
        baseModel.setErrorMessage("撤销出库成功！");
        return baseModel;
    }

    /**
     * @Description:  功能描述（枪支预入库）
     * @Author:       刘家义
     * @CreateDate:   2018/11/6 17:28
    */
    @Override
    public BaseModel addWareHouseRecordsBeforehandStorage(String gunId) throws Exception {
        BaseModel baseModel = new BaseModel();
        //1、对gunId“，”分割进行解析
        String[] gunIds = StringUtilUD.slicerComma(gunId);
        for (String gId : gunIds) {
            //2、下发09报文给服务器，进行预入库
            baseModel = producer.sendMessageBeforehandStorage(gId);
        }
        baseModel.setStatus(IStatusMessage.SystemStatus.SUCCESS.getCode());
        baseModel.setErrorMessage("预入库成功！");
        return baseModel;
    }


    /**
     * @Description:  功能描述（撤销入库）
     * @Author:       刘家义
     * @CreateDate:   2018/11/6 19:45
    */
    @Override
    public BaseModel revocationWareHouseRecordsStorage(String gunId, String gunMac) throws Exception {
        BaseModel baseModel = new BaseModel();
        //1、对gunId，gunMmc以“，”分割进行解析
        String[] gunIds = StringUtilUD.slicerComma(gunId);
        String[] gunMacs = StringUtilUD.slicerComma(gunMac);
        for (int i = 0; i < gunMacs.length; i++) {
            //2、下发07报文给服务器，进行最终出库操作
            String mac = gunMacs[i];
            String gun = gunMacs[i];
            baseModel = producer.sendMessageRevocationStorage(gun,mac);
        }
        baseModel.setStatus(IStatusMessage.SystemStatus.SUCCESS.getCode());
        baseModel.setErrorMessage("入库撤销成功！");
        return baseModel;
    }
}
