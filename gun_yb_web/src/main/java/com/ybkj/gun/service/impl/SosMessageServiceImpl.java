package com.ybkj.gun.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ybkj.common.activceMq.Producer;
import com.ybkj.common.pojo.SearchGunReqMessageBody;
import com.ybkj.common.util.ActiveUser;
import com.ybkj.common.util.StringUtilUD;
import com.ybkj.enums.IStatusMessage;
import com.ybkj.gun.mapper.GunMapper;
import com.ybkj.gun.mapper.MissionMapper;
import com.ybkj.gun.mapper.SosMessageMapper;
import com.ybkj.gun.model.Gun;
import com.ybkj.gun.model.Mission;
import com.ybkj.gun.model.SosMessage;
import com.ybkj.gun.service.MissionService;
import com.ybkj.gun.service.SosMessageService;
import com.ybkj.model.BaseModel;
import com.ybkj.untils.DateTool;
import com.ybkj.untils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @项目名称：
 * @类名称：
 * @类描述：
 * @创建人：liujiayi
 * @创建时间：2018/11/3 16:08
 * @修改时间：2018/11/3 16:08
 * @version：1.0
 */

@SuppressWarnings("all")
@Service
@Transactional
@Slf4j
public class SosMessageServiceImpl implements SosMessageService {

    @Autowired
    private SosMessageMapper sosMessageMapper;
    @Autowired
    private GunMapper gunMapper;
    @Autowired
    private Producer producer;
    @Autowired
    private MissionService missionService;

    /**
     * @Description: 功能描述（查询警告信息列表:枪号）
     * @Author: 刘家义
     * @CreateDate: 2018/11/3 16:47
     */
    @Override
    public List<SosMessage> findSosMessages(String gunCode) throws Exception {
        return sosMessageMapper.selectSosMessageAll(gunCode);
    }

    /**
     * @Description: 功能描述（推送消息给指定的设备）
     * @Author: 刘家义
     * @CreateDate: 2018/11/24 16:22
     */
    @Override
    public BaseModel createForHelpGun(String appImei, Integer sosId,Integer type) throws Exception {
        BaseModel baseModel = new BaseModel();
        SearchGunReqMessageBody messageBody = new SearchGunReqMessageBody();
        List<SearchGunReqMessageBody.GunInfo> messageBodies = new ArrayList<>();
        //1、判断是否协助查找，还是紧急支援(我这边不需要判断，知道就行)
        SosMessage sosMessage = sosMessageMapper.selectByPrimaryKey(sosId);
        if (null != sosMessage) {
            //2、获取指定的设备
            String[] appImeis = StringUtilUD.slicerComma(appImei);
            for (String imei : appImeis) {
                SearchGunReqMessageBody.GunInfo gunInfo = new SearchGunReqMessageBody.GunInfo();
                //通过gunMac，获取到枪号
                Gun gun = gunMapper.selectGunByGunMac(sosMessage.getGunMac());
                Mission mission=new Mission();
                if (null != gun) {
                    gunInfo.setGunId(gun.getGunId());
                    gunInfo.setGunMac(sosMessage.getGunMac());
                    gunInfo.setLostTime(DateTool.dateToString(sosMessage.getSosTime()));
                    messageBodies.add(gunInfo);
                    messageBody.setLo(sosMessage.getLongitude());
                    messageBody.setLa(sosMessage.getLatitude());
                    messageBody.setReserve("");
                    messageBody.setAuthCode(TokenUtils.channelSessionDigest());
                    messageBody.setLostGunList(messageBodies);
                    //3、将数据保存到数据库：mission
                    mission.setGunMac(sosMessage.getGunMac());
                    mission.setAppImei(imei);
                    mission.setWebUserId(ActiveUser.getActiveUser().getId());
                    mission.setType(type);
                    mission.setBody(JSONObject.toJSONString(messageBody, SerializerFeature.DisableCircularReferenceDetect).toString());
                    missionService.insertMission(mission);
                    //4、进行推送：21号报文
                    baseModel = producer.sendMessageMinistrantFind(messageBody, imei);
                    baseModel.setStatus(IStatusMessage.SystemStatus.SUCCESS.getCode());
                    baseModel.setErrorMessage("推送成功！");
                } else {
                    baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
                    baseModel.setErrorMessage("推送异常！");
                    log.debug("查询出现异常，通过gunMac，查询值不存在");
                }
            }
        } else {
            baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
            baseModel.setErrorMessage("推送异常！");
            log.debug("查询出现异常，根据Id，查询值不存在");
        }
        return baseModel;
    }



}






