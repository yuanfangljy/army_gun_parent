package com.ybkj.gun.service.impl;

import com.ybkj.common.activceMq.Producer;
import com.ybkj.common.pojo.StartStopSearchGunReqMessageBody;
import com.ybkj.enums.IStatusMessage;
import com.ybkj.gun.mapper.MissionMapper;
import com.ybkj.gun.model.Mission;
import com.ybkj.gun.service.MissionService;
import com.ybkj.model.BaseModel;
import com.ybkj.untils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @项目名称：
 * @类名称：
 * @类描述：实现协助查找业务逻辑
 * @创建人：liujiayi
 * @创建时间：2018/11/4 15:02
 * @修改时间：2018/11/4 15:02
 * @version：1.0
 */
@Service
@Transactional
@Slf4j
@SuppressWarnings("all")
public class MissionServiceImpl implements MissionService{

    @Autowired
    private MissionMapper missionMapper;
    @Autowired
    private Producer producer;

    /**
     * @Description:  功能描述（查询协助查找信息）
     * @Author:       刘家义
     * @CreateDate:   2018/11/23 9:36
    */
    @Override
    public List<Mission> findMissions(String gunMac) throws Exception {
        return missionMapper.selectMissions(gunMac);
    }

    /**
     * @Description:  功能描述（枪支查找启停控制）19报文
     * @Author:       刘家义
     * @CreateDate:   2018/11/23 10:07
     * 1、通过appImei,在mission中查询状态不为0的信息
     * 2、获取所有的gunMac，进行保存
     * 3、发送19报文
    */
    @Override
    public BaseModel restartMission(String type, String appImei) throws Exception {
        BaseModel baseModel=new BaseModel();
        StartStopSearchGunReqMessageBody messageBody=new StartStopSearchGunReqMessageBody();

        List<StartStopSearchGunReqMessageBody.GunInfo> bodiesGunInfo = new ArrayList<>();
       // List<StartStopSearchGunReqMessageBody> bodies = new ArrayList<>();

        //1、通过appImei,在mission中查询状态不为0的信息
        List<Mission> missions=missionMapper.selectMissionsByAppImei(appImei);
        //2、获取所有的gunMac，进行保存
        for (Mission mission : missions) {
            //防止往ArrayList添加值的时候进行覆盖，让每次都是一次新的new
            StartStopSearchGunReqMessageBody.GunInfo gunInfo=new StartStopSearchGunReqMessageBody.GunInfo();
            gunInfo.setGunMac(mission.getGunMac());
            bodiesGunInfo.add(gunInfo);
        }
        messageBody.setGunList(bodiesGunInfo);
        messageBody.setCommand(type);
        messageBody.setAuthCode(TokenUtils.channelSessionDigest());

       // bodies.add(messageBody);

        //3、发送19报文
        baseModel = producer.sendMessageOffNormalAlarmStartAndStop(messageBody, appImei);
        baseModel.setStatus(IStatusMessage.SystemStatus.SUCCESS.getCode());
        baseModel.setErrorMessage("枪支预出库成功！");
        return baseModel;
    }

    /**
     * @Description:  功能描述（新增协助查找相关信息）
     * @Author:       刘家义
     * @CreateDate:   2018/11/24 17:16
    */
    @Override
    public BaseModel insertMission(Mission mission) throws Exception {
        BaseModel baseModel=new BaseModel();
        log.debug("-----------新增协助查找相关信息-----------");
        int i = missionMapper.insertSelective(mission);
        if(i<0){
            baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
            baseModel.setErrorMessage("新增失败！");
        }
        return baseModel;
    }


}
