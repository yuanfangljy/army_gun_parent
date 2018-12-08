package com.ybkj.gun.service.impl;

import com.ybkj.common.entity.GunLocationVO;
import com.ybkj.enums.IStatusMessage;
import com.ybkj.gun.mapper.AppGunMapper;
import com.ybkj.gun.mapper.AppMapper;
import com.ybkj.gun.mapper.GunLocationMapper;
import com.ybkj.gun.mapper.GunMapper;
import com.ybkj.gun.model.App;
import com.ybkj.gun.model.AppGun;
import com.ybkj.gun.model.Gun;
import com.ybkj.gun.model.GunLocation;
import com.ybkj.gun.service.GunLocationService;
import com.ybkj.model.BaseModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @项目名称：
 * @类名称：
 * @类描述：枪支地理位置，业务逻辑具体实现
 * @创建人：liujiayi
 * @创建时间：2018/11/12 14:19
 * @修改时间：2018/11/12 14:19
 * @version：1.0
 */
@Service
@Transactional
@Slf4j
@SuppressWarnings("all")
public class GunLocationServiceImpl implements GunLocationService {

    @Autowired
    private GunLocationMapper gunLocationMapper;
    @Autowired
    private AppMapper appMapper;
    @Autowired
    private AppGunMapper appGunMapper;
    @Autowired
    private GunMapper gunMapper;

    /**
     * @param gunId
     * @param appId
     * @return
     * @throws Exception
     * @Description: 功能描述（枪支动态地理位置）
     * @Author: 刘家义
     * @CreateDate: 2018/11/12 14:20
     */
    @Override
    public List<GunLocationVO> findGunDynamic(String gunId, String appName) throws Exception {
        return gunLocationMapper.selectGunDynamic(gunId, appName);
    }


    /**
     * @Description: 功能描述（优化：查询枪支动态位置信息）
     * @Author: 刘家义
     * @CreateDate: 2018/12/5 10:37
     * 1、查询app_gun,查询所有状态为 1 的枪支
     * 2、根据获取到的枪支，获取到最新的数据，根据时间排序获取到最新的数据 limit 1
     * 3、根据相应的信息，查询对应的app和gun数据
     */
    @Override
    public BaseModel findGunDynamicOptimize(String gunId, String appName) throws Exception {
        BaseModel baseModel = new BaseModel();
        //获取到枪支的动态数据，保存到新的List里面
        List<GunLocationVO> locationVOS = new ArrayList<>();
        //第一种情况：判断是否根据gunId查询，枪支位置
        if(StringUtils.isNotEmpty(gunId)){
            //2、根据获取到的枪支，获取到最新的数据(gun_location)，根据时间排序获取到最新的数据 limit 1
            GunLocation gunLocation = gunLocationMapper.selectGunLocationByGunIdNewest(gunId);
            if (gunLocation==null){
                baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
                baseModel.setErrorMessage("暂无在线数据！");
                return baseModel;
            }
            //3、根据相应的信息，查询对应的app数据
            final App app = appMapper.selectByPrimaryKey(gunLocation.getAppId());
            //4、根据相应的信息，查询对应的gun数据
            final Gun gun = gunMapper.selectGunByGunCode(gunLocation.getGunId());
            //5、将值保存到 GunLocationVO 中
            GunLocationVO gunLocationVO = new GunLocationVO();
            gunLocationVO.setAppIMEI(app.getAppImei());
            gunLocationVO.setAppPhone(app.getAppPhone());

            gunLocationVO.setLatitude(gunLocation.getLatitude());
            gunLocationVO.setLongitude(gunLocation.getLongitude());
            gunLocationVO.setGunDeviceBatteryPower(gunLocation.getGunDeviceBatteryPower());
            gunLocationVO.setGunDeviceState(gunLocation.getGunDeviceState());

            gunLocationVO.setGunId(gun.getGunId());
            gunLocationVO.setGunMac(gun.getGunMac());
            gunLocationVO.setGunModel(gun.getGunModel());
            gunLocationVO.setGunType(gun.getGunType());
            gunLocationVO.setTotalBulletNumber(gun.getTotalBulletNumber());
            gunLocationVO.setWarehouseName(gun.getWarehouseName());
            gunLocationVO.setRealTimeState(gun.getRealTimeState());

            locationVOS.add(gunLocationVO);

            baseModel.add("gunLocations", locationVOS);
            baseModel.setStatus(IStatusMessage.SystemStatus.SUCCESS.getCode());
            baseModel.setErrorMessage("查询成功！");
        }else{
            //1、查询app_gun,查询所有状态为 1 的枪支
            List<AppGun> appGun = appGunMapper.selectAppGunState(1);
            if (appGun.size() == 0) {
                baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
                baseModel.setErrorMessage("暂无在线数据！");
                return baseModel;
            }else {
                for (AppGun appgun : appGun) {
                    //2.1、通过appgun.getGunId()，获取到枪支的蓝牙号
                    Gun g = gunMapper.selectGunByGunCode(appgun.getGunId());
                    //2、根据获取到的枪支，获取到最新的数据(gun_location)，根据时间排序获取到最新的数据 limit 1
                    GunLocation gunLocation = gunLocationMapper.selectGunLocationByGunIdNewest(g.getGunMac());
                    //3、根据相应的信息，查询对应的app数据
                    final App app = appMapper.selectByPrimaryKey(appgun.getAppId());
                    //4、根据相应的信息，查询对应的gun数据
                    final Gun gun = gunMapper.selectGunByGunCode(appgun.getGunId());
                    //5、将值保存到 GunLocationVO 中
                    GunLocationVO gunLocationVO = new GunLocationVO();
                    gunLocationVO.setAppIMEI(app.getAppImei());
                    gunLocationVO.setAppPhone(app.getAppPhone());

                    gunLocationVO.setLatitude(gunLocation.getLatitude());
                    gunLocationVO.setLongitude(gunLocation.getLongitude());
                    gunLocationVO.setGunDeviceBatteryPower(gunLocation.getGunDeviceBatteryPower());
                    gunLocationVO.setGunDeviceState(gunLocation.getGunDeviceState());

                    gunLocationVO.setGunId(gun.getGunId());
                    gunLocationVO.setGunMac(gun.getGunMac());
                    gunLocationVO.setGunModel(gun.getGunModel());
                    gunLocationVO.setGunType(gun.getGunType());
                    gunLocationVO.setTotalBulletNumber(gun.getTotalBulletNumber());
                    gunLocationVO.setWarehouseName(gun.getWarehouseName());
                    gunLocationVO.setRealTimeState(gun.getRealTimeState());

                    locationVOS.add(gunLocationVO);
                }
                baseModel.add("gunLocations", locationVOS);
                baseModel.setStatus(IStatusMessage.SystemStatus.SUCCESS.getCode());
                baseModel.setErrorMessage("查询成功！");
            }
        }
        return baseModel;
    }


    /**
     * @Description: 功能描述（查询周围的在线设备）
     * @Author: 刘家义
     * @CreateDate: 2018/11/22 19:51
     */
    @Override
    public List<GunLocation> findRoundDevice(String lng, String lat, String gunMac) throws Exception {
        //根据appImei进行去重，将新的记录保存到gunLocationList中
        List<GunLocation> gunLocations = gunLocationMapper.selectRoundDevice(lng, lat, gunMac);
       /* for (GunLocation gunLocation : gunLocations) {
            System.out.println("去重前===》"+gunLocation.getAppImei());
        }
        List<GunLocation> gunLocations1 = distinctGunLocation(gunLocations);
        for (GunLocation gunLocation : gunLocations1) {
            System.out.println("去重后===》"+gunLocation.getAppImei());
        }
        System.out.println(newGunLocationList.size());*/
        return distinctGunLocation(gunLocations);
    }

    //根据某个字段去重
    public List<GunLocation> distinctGunLocation(List<GunLocation> gunLocations) {
        Set<GunLocation> set = new TreeSet<GunLocation>(new Comparator<GunLocation>() {
            @Override
            public int compare(GunLocation o1, GunLocation o2) {
                //字符串,则按照asicc码升序排列
                return o1.getAppImei().compareTo(o2.getAppImei());
            }
        });
        set.addAll(gunLocations);
        return new ArrayList<GunLocation>(set);
    }

    /**
     * @Description: 功能描述（查询枪支的轨迹）
     * @Author: 刘家义
     * @CreateDate: 2018/11/26 19:49
     */
    @Override
    public List<GunLocation> findGunTrajectory(String appImei) throws Exception {
        return gunLocationMapper.selectGunTrajectory(appImei,new Date());
    }
}
