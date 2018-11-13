package com.ybkj.gun.service.impl;

import com.ybkj.common.util.StringUtilUD;
import com.ybkj.enums.IStatusMessage;
import com.ybkj.gun.mapper.AppGunMapper;
import com.ybkj.gun.mapper.AppGunUserMapper;
import com.ybkj.gun.mapper.AppMapper;
import com.ybkj.gun.mapper.GunMapper;
import com.ybkj.gun.model.App;
import com.ybkj.gun.model.AppGun;
import com.ybkj.gun.model.AppGunUser;
import com.ybkj.gun.model.Gun;
import com.ybkj.gun.service.AppGunService;
import com.ybkj.model.BaseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @项目名称：
 * @类名称：
 * @类描述：枪支分配具体业务逻辑
 * @创建人：liujiayi
 * @创建时间：2018/11/12 15:51
 * @修改时间：2018/11/12 15:51
 * @version：1.0
 */
@Service
@Transactional
@Slf4j
@SuppressWarnings("all")
public class AppGunServiceImpl implements AppGunService {

    @Autowired
    private AppGunMapper appGunMapper;
    @Autowired
    private AppGunUserMapper appGunUserMapper;
    @Autowired
    private GunMapper gunMapper;
    @Autowired
    private AppMapper appMapper;


    /**
     * @Description: 功能描述（新增分配枪支）
     * @Author: 刘家义
     * @CreateDate: 2018/11/12 15:52
     * 1、在app_gun表中，插入相关信息；给设备分配枪支
     * 2、在app_gun_user表中，插入相关数据，给设备分配用户
     */
    @Override
    public BaseModel addAppGun(String gunIds, String appId, Integer gunUserId) throws Exception {
        BaseModel baseModel = new BaseModel();
        baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
        AppGunUser appGunUser = new AppGunUser();
        AppGun appGun = new AppGun();
        appGun.setAppId(Integer.valueOf(appId));


        String[] gunId = StringUtilUD.slicerComma(gunIds);
        System.out.println("---------"+gunIds);
        for (String gId : gunId) {
            appGun.setGunId(Integer.valueOf(gId));
            //一、在app_gun表中，插入相关信息；给设备分配枪支
            //1、再插入的时候，绑定这条记录，状态是否不等于0的记录存在；如果有就代表这条记录以及存在，在预出库中
            AppGun appGun1 = appGunMapper.selectAppGunByAppGunState(Integer.valueOf(gId), Integer.valueOf(appId), 0);
            if (null != appGun1) {
                Gun gun = gunMapper.selectByPrimaryKey(Integer.valueOf(gId));
                if (null == gun) {
                    baseModel.setErrorMessage("您选择的枪支不存在");
                    log.debug("枪支分配-----appGun-----" + baseModel + "枪支可能被删除");
                }
                baseModel.setErrorMessage("您选择编号【" + gun.getGunId() + "】的枪支，已经被分配，请刷新列表");
                log.debug("枪支分配--appGun-" + baseModel);
            } else {
                //二、在app_gun_user表中，插入相关数据，给设备分配用户
                //1、判断该腕表是否与用户已经进行绑定
                AppGunUser appGunUser1 = appGunUserMapper.selectAppGunUserByAPPUserState(gunUserId, Integer.valueOf(appId), 1);
                if(null!=appGunUser1){
                    App app = appMapper.selectByPrimaryKey(Integer.valueOf(appId));
                    if(null==app){
                        baseModel.setErrorMessage("您选择的设备不存在");
                        log.debug("枪支分配-----appGun-----" + baseModel + "设备可能被删除");
                    }
                    baseModel.setErrorMessage("您选择设备【" + app.getAppName() + "】，已经被分配，请刷新列表");
                    log.debug("枪支分配--appGun-" + baseModel);
                }
                //判断该用户和设备绑定是否存在，且状态是 1；不存在就插入，反之
                int ag = appGunMapper.insertSelective(appGun);
                if ( ag<1) {
                    baseModel.setErrorMessage("分配失败");
                    log.debug("枪支分配--appGun-" + baseModel);
                }
            }
        }
        //判断是否匹配了用户
        if(null!=gunUserId){
            appGunUser.setGunUserId(Integer.valueOf(gunUserId));
            appGunUser.setAppId(Integer.valueOf(appId));
            int agu = appGunUserMapper.insertSelective(appGunUser);
            if(agu<1){
                baseModel.setErrorMessage("分配失败");
                log.debug("枪支分配--appGun-" + baseModel);
            }else{
                baseModel.setStatus(IStatusMessage.SystemStatus.SUCCESS.getCode());
                baseModel.setErrorMessage("分配成功");
                log.debug("枪支分配--appGun-" + baseModel);
            }
        }else{
            baseModel.setStatus(IStatusMessage.SystemStatus.SUCCESS.getCode());
            baseModel.setErrorMessage("分配成功");
            log.debug("枪支分配--appGun-" + baseModel);
        }
        return baseModel;
    }


    /**
     * @Description:  功能描述（查询所有绑定的枪支的设备）
     * @Author:       刘家义
     * @CreateDate:   2018/11/12 19:09
     */
    @Override
    public List<AppGun> findAppGunBinding() throws Exception {
        return appGunMapper.selectAppGunBinding(2);
    }
}
