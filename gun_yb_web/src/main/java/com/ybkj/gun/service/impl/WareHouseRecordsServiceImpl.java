package com.ybkj.gun.service.impl;

import com.ybkj.common.activceMq.Producer;
import com.ybkj.common.pojo.BindingReqMessageBody;
import com.ybkj.common.util.ActiveUser;
import com.ybkj.common.util.StringUtilUD;
import com.ybkj.enums.IStatusMessage;
import com.ybkj.gun.mapper.*;
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
    @Autowired
    private AppMapper appMapper;
    @Autowired
    private AppGunMapper appGunMapper;
    @Autowired
    private AppGunUserMapper appGunUserMapper;


    /**
     *             05
     * @Description: 功能描述（枪支预出库） 第一版
     * @Author: 刘家义
     * @CreateDate: 2018/11/5 10:16
     * 1、根据gunIds在gun表中，来获取对应库房Id和枪支编号
     * 2、获取当前web登录用户
     * 3、创建预出库记录，同时推送消息给服务器
     */
    @Override
    public BaseModel addWareHouseRecordsBeforehandDelivery(String gunIds, String appIMEI, Integer gunUserId) throws Exception {
        BaseModel baseModel = new BaseModel();
        WarehouseRecords warehouseRecords = new WarehouseRecords();
        //保存要推送的枪支信息
        List<BindingReqMessageBody> bindingReqMessageBodies = new ArrayList<>();
        List<BindingReqMessageBody.GunInfo> bindingReqMessageBodiesGunInfo = new ArrayList<>();
        BindingReqMessageBody bindingReqMessageBody = new BindingReqMessageBody();
        BindingReqMessageBody.GunInfo gunInfo = new BindingReqMessageBody.GunInfo();

        //5、将腕表或者手机的动态状态改成 1 表示改设备已经在出库中
      /*  AppDynamicData appDynamicData = appDynamicDataMapper.selectAppDynamicByAppId(appId);
        if (null == appDynamicData) {
            baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
            baseModel.setErrorMessage("预出库失败");
            log.debug("枪支预出库--查询appId可能为空-" + "---" + appId + "--" + baseModel);
            return baseModel;
        }*/

        //1、根据gunIds在gun表中，来获取对应库房Id和枪支编号
        String[] strings = StringUtilUD.slicerComma(gunIds);
        for (String gunId : strings) {
            Gun gun = gunMapper.selectGunByGunCode(gunId);
            if (gun == null) {
                baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
                baseModel.setErrorMessage("操作出现问题，请稍后再试");
                log.debug("枪支预出库--查询Id可能为空-" + baseModel + "=----" + gun.toString());
                return baseModel;
            }
            /*warehouseRecords.setWarehouseId(gun.getWarehouseId());
            warehouseRecords.setGunId(gun.getGunId());
            warehouseRecords.setAppId(appId);
            warehouseRecords.setState(0);
            warehouseRecords.setWarehouseInTime(new Date());*/
            //2、获取当前web登录用户
          /*  if (ActiveUser.isActiveUser()) {
                WebUser activeUser = ActiveUser.getActiveUser();
                warehouseRecords.setWebUserId(activeUser.getId());
            } else {
                baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
                baseModel.setErrorMessage("您未登录或登录超时，请您登录后再试");
                log.debug("枪支预出库--查询Id可能为空-" + baseModel);
                return baseModel;
            }*/
            //3、创建预出库记录
            //warehouseRecordsMapper.insertSelective(warehouseRecords);
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
        baseModel = producer.sendMessageAdvanceTheDelivery(bindingReqMessageBodies, appIMEI);
        baseModel.setStatus(IStatusMessage.SystemStatus.SUCCESS.getCode());
        baseModel.setErrorMessage("枪支预出库成功！");
        return baseModel;
    }

    /**
     *
     *   05
     * @Description: 功能描述（功能描述（枪支预出库） 第二版
     * @Author: 刘家义
     * @CreateDate: 2018/11/12 19:39
     * 1、根据app_id,查询app_gun所有状态为 1 的相关信息
     * 2、通过app_id，在app中获取到app的IMEI
     * 3、通过gun_id,在gun中查询枪支相关信息
     * 4、通过gun_id，在app_gun_user状态为 1 的用户id，（可能会没有用户信息）
     * 5、再通过gun_user_id,在gun_user中查询相关用户信息
     */
    @Override
    public BaseModel addDeviceBindingGunsBeforehandDelivery(String appId) throws Exception {
        BaseModel baseModel=new BaseModel();

        //保存要推送的枪支信息
        List<BindingReqMessageBody> bindingReqMessageBodies = new ArrayList<>();
        List<BindingReqMessageBody.GunInfo> bindingReqMessageBodiesGunInfo = new ArrayList<>();
        BindingReqMessageBody bindingReqMessageBody = new BindingReqMessageBody();
        BindingReqMessageBody.GunInfo gunInfo = new BindingReqMessageBody.GunInfo();

        List<Gun> guns=new ArrayList<>();
        String appIMEI="";
        //1、根据app_id,查询app_gun所有状态为 1 的相关信息
        List<AppGun> appGuns=appGunMapper.selectAppGunByAppIdAndState(appId,2);
        System.out.println("-----------"+appGuns.size());
        for (AppGun appGun : appGuns) {
            //2、通过app_id，在app中获取到app的IMEI
            App app = appMapper.selectByPrimaryKey(appGun.getAppId());
            if(app==null){
                baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
                baseModel.setErrorMessage("查询出错！");
                log.debug("----App ID ----为"+appGun.getAppId()+"不存在");
                return baseModel;
            }
            //获取app的IMEI
            appIMEI=app.getAppImei();
            //3、通过gun_id,在gun中查询枪支相关信息
            Gun gun = gunMapper.selectGunByGunCode(String.valueOf(appGun.getGunId()));
            if(gun==null){
                baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
                baseModel.setErrorMessage("查询出错！");
                log.debug("----枪支 ID ----为"+appGun.getGunId()+"不存在");
                return baseModel;
            }
            //保存相关枪支信息
            guns.add(gun);
            //4、通过gun_id，在app_gun_user状态为 1 的用户id，（可能会没有用户信息）
            AppGunUser appGunUser=appGunUserMapper.selectAppGunUserByGunIdAndState(Integer.valueOf(appGun.getGunId()),1);
            if(appGunUser!=null){
                //5、再通过gun_user_id,在gun_user中查询相关用户信息
                GunUser gunUser = gunUserMapper.selectByPrimaryKey(appGunUser.getGunUserId());
                bindingReqMessageBody.setUserId(String.valueOf(gunUser.getUserName()));
            }else{
                bindingReqMessageBody.setUserId("");
            }
            //6、临时保存将要预出库发送给服务器的临时消息
            gunInfo.setGunId(gun.getGunId());
            gunInfo.setGunMac(gun.getGunMac());
            gunInfo.setGunModel(gun.getGunModel());
            gunInfo.setGunType(gun.getGunType());
            bindingReqMessageBodiesGunInfo.add(gunInfo);

        }
        bindingReqMessageBody.setGunList(bindingReqMessageBodiesGunInfo);
        bindingReqMessageBodies.add(bindingReqMessageBody);
        //7、发送05报文
        baseModel = producer.sendMessageAdvanceTheDelivery(bindingReqMessageBodies, appIMEI);
        baseModel.setStatus(IStatusMessage.SystemStatus.SUCCESS.getCode());
        baseModel.setErrorMessage("枪支预出库成功！");
        return baseModel;
    }


    /**
     *     07
     * @Description: 功能描述（枪支最终出库） 警员信息存在    第二版
     * @Author: 刘家义
     * @CreateDate: 2018/11/6 14:44
     */
    @Override
    public BaseModel endWareHouseRecordsDelivery(String appId, String endTime) throws Exception {
        BaseModel baseModel = new BaseModel();
        List<Gun> guns=new ArrayList<>();
        String appIMEI="";
        //1、根据app_id,查询app_gun所有状态为 1 的相关信息
        List<AppGun> appGuns=appGunMapper.selectAppGunByAppIdAndState(appId,2);
        for (AppGun appGun : appGuns) {
            //2、通过app_id，在app中获取到app的IMEI
            App app = appMapper.selectByPrimaryKey(appGun.getAppId());
            if(app==null){
                baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
                baseModel.setErrorMessage("查询出错！");
                log.debug("----App ID ----为"+appGun.getAppId()+"不存在");
                return baseModel;
            }
            //获取app的IMEI
            appIMEI=app.getAppImei();
            //3、通过gun_id,在gun中查询枪支相关信息
            Gun gun = gunMapper.selectGunByGunCode(String.valueOf(appGun.getGunId()));
            if(gun==null){
                baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
                baseModel.setErrorMessage("查询出错！");
                log.debug("----枪支 ID ----为"+appGun.getGunId()+"不存在");
                return baseModel;
            }
            //保存相关枪支信息
            guns.add(gun);
            //4、通过gun_id，在app_gun_user状态为 1 的用户id，（可能会没有用户信息）
            AppGunUser appGunUser=appGunUserMapper.selectAppGunUserByGunIdAndState(Integer.valueOf(appGun.getGunId()),1);
            if(appGunUser!=null){
                //5、再通过gun_user_id,在gun_user中查询相关用户信息
                GunUser gunUser = gunUserMapper.selectByPrimaryKey(appGunUser.getGunUserId());
                //6.1、用户存在的时候 发送 07号报文
                baseModel = producer.sendMessageEndDelivery(gunUser.getUserId(), gunUser.getUserName(), gun.getGunId(), gun.getGunMac(), endTime, appIMEI);
            }
           //6.2 用户不存在的时候 发送 07号报文
            baseModel = producer.sendMessageEndDelivery(gun.getGunId(), gun.getGunMac(), endTime, appIMEI);
        }
        return baseModel;
    }


    /**
     *     07
     * @Description: 功能描述（枪支最终出库） 警员信息存在    第一版
     * @Author: 刘家义
     * @CreateDate: 2018/11/6 14:44
     */
    @Override
    public BaseModel endWareHouseRecordsDelivery(Integer userId, String gunId, String gunMac, String endTime, String appId) throws Exception {
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
            String gun = gunIds[i];
            baseModel = producer.sendMessageEndDelivery(userId, gunUser.getUserName(), gun, mac, endTime, appId);
        }
        baseModel.setStatus(IStatusMessage.SystemStatus.SUCCESS.getCode());
        baseModel.setErrorMessage("最终出库成功！");
        return baseModel;
    }



    /**
     * @Description: 功能描述（枪支最终出库）警员信息不存在 第一版
     * @Author: 刘家义
     * @CreateDate: 2018/11/6 14:44
     */
    @Override
    public BaseModel endWareHouseRecordsDelivery(String gunId, String gunMac, String endTime, String appId) throws Exception {
        BaseModel baseModel = new BaseModel();
        //1、对gunId，gunMmc以“，”分割进行解析
        String[] gunIds = StringUtilUD.slicerComma(gunId);
        String[] gunMacs = StringUtilUD.slicerComma(gunMac);
        for (int i = 0; i < gunMacs.length; i++) {
            //2、下发07报文给服务器，进行最终出库操作
            String mac = gunMacs[i];
            String gun = gunMacs[i];
            baseModel = producer.sendMessageEndDelivery(gun, mac, endTime, appId);
        }
        baseModel.setStatus(IStatusMessage.SystemStatus.SUCCESS.getCode());
        baseModel.setErrorMessage("最终出库成功！");
        return baseModel;
    }

    /**
     * @Description: 功能描述（撤销出库：09）第一版
     * @Author: 刘家义
     * @CreateDate: 2018/11/6 16:19
     */
    @Override
    public BaseModel revocationWareHouseRecordsDelivery(String gunId, Integer appId) throws Exception {
        BaseModel baseModel = new BaseModel();
        App app = appMapper.selectByPrimaryKey(Integer.valueOf(appId));
        //1、对gunId“，”分割进行解析
        String[] gunIds = StringUtilUD.slicerComma(gunId);
        for (String gId : gunIds) {
            //2、下发09报文给服务器，进行出库撤销操作
            baseModel = producer.sendMessageRevocationDelivery(gId, app.getAppImei());
        }
        baseModel.setStatus(IStatusMessage.SystemStatus.SUCCESS.getCode());
        baseModel.setErrorMessage("撤销出库成功！");
        return baseModel;
    }

    /**
     * @Description:  功能描述（撤销出库：09）第二版
     * @Author:       刘家义
     * @CreateDate:   2018/11/13 14:47
    */
    @Override
    public BaseModel revocationWareHouseRecordsDelivery(Integer appId) throws Exception {
        BaseModel baseModel=new BaseModel();
        //1、通过appId，查询对应的appIMEI
        App app = appMapper.selectByPrimaryKey(appId);
        if(null==app){
            baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
            baseModel.setErrorMessage("撤销失败！");
            log.debug("----App ID ----为"+appId+"不存在");
            return baseModel;
        }
        String IMEI=app.getAppImei();
        //2、根据app_id,查询app_gun所有状态为 1 的相关信息
        List<AppGun> appGuns=appGunMapper.selectAppGunByAppIdAndState(String.valueOf(appId),1);
        for (AppGun appGun : appGuns) {
            //3、获取到相关枪支Id,通过枪支Id，查询枪支编号
            Gun gun = gunMapper.selectGunByGunCode(String.valueOf(appGun.getGunId()));
            //4、下发09报文给服务器，进行出库撤销操作
            baseModel = producer.sendMessageRevocationDelivery(gun.getGunId(), IMEI);
        }
        baseModel.setStatus(IStatusMessage.SystemStatus.SUCCESS.getCode());
        baseModel.setErrorMessage("撤销出库成功！");
        return baseModel;
    }

    /**
     * @Description: 功能描述（枪支预入库）
     * @Author: 刘家义
     * @CreateDate: 2018/11/6 17:28
     */
    @Override
    public BaseModel addWareHouseRecordsBeforehandStorage(String gunId, String appImei) throws Exception {
        BaseModel baseModel = new BaseModel();
        //1、对gunId“，”分割进行解析
        String[] gunIds = StringUtilUD.slicerComma(gunId);
        String[] appImeis = StringUtilUD.slicerComma(appImei);
        for (int i = 0; i < gunIds.length; i++) {
            //2、下发09报文给服务器，进行预入库
            baseModel = producer.sendMessageBeforehandStorage(gunIds[i], appImeis[i]);
        }

        baseModel.setStatus(IStatusMessage.SystemStatus.SUCCESS.getCode());
        baseModel.setErrorMessage("预入库成功！");
        return baseModel;
    }


    /**
     * @Description: 功能描述（撤销入库）
     * @Author: 刘家义
     * @CreateDate: 2018/11/6 19:45
     */
    @Override
    public BaseModel revocationWareHouseRecordsStorage(String gunId, String gunMac, String appId, String state) throws Exception {
        BaseModel baseModel = new BaseModel();
        //1、对gunId，gunMmc以“，”分割进行解析
        String[] gunIds = StringUtilUD.slicerComma(gunId);
        String[] gunMacs = StringUtilUD.slicerComma(gunMac);
        for (int i = 0; i < gunMacs.length; i++) {
            //2、下发07报文给服务器，进行最终出库操作
            String mac = gunMacs[i];
            String gun = gunIds[i];
            baseModel = producer.sendMessageRevocationStorage(gun, mac, appId, state);
        }
        baseModel.setStatus(IStatusMessage.SystemStatus.SUCCESS.getCode());
        if (state.equals(String.valueOf(1))) {
            baseModel.setErrorMessage("入库成功！");
        } else {
            baseModel.setErrorMessage("入库撤销成功！");
        }

        return baseModel;
    }

    /**
     * @Description: 功能描述（查询库存记录表）
     * @Author: 刘家义
     * @CreateDate: 2018/11/10 12:15
     */
    @Override
    public List<WarehouseRecords> findWareHouseRecords(Integer type) throws Exception {
        return warehouseRecordsMapper.selectWareHouseRecords(type);
    }
}
