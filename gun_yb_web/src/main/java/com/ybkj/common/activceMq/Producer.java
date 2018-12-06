package com.ybkj.common.activceMq;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ybkj.common.pojo.*;
import com.ybkj.enums.IStatusMessage;
import com.ybkj.gun.mapper.AppMapper;
import com.ybkj.gun.mapper.GunMapper;
import com.ybkj.gun.model.App;
import com.ybkj.model.BaseModel;
import com.ybkj.untils.DateTool;
import com.ybkj.untils.ProgressiveIncreaseNumber;
import com.ybkj.untils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.MessageEOFException;
import javax.jms.Queue;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.List;

@SuppressWarnings("all")
@Slf4j
@Component
@EnableScheduling
@Transactional
public class Producer {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;
    @Autowired
    private Queue storageQueue;
    @Autowired
    private Queue deliveryQueue;
    @Autowired
    private Queue helpFindQueue;
    @Autowired
    private Queue startAndStopQueue;
    @Autowired
    private DateTool dateTool;
    @Autowired
    private ProgressiveIncreaseNumber progressiveIncreaseNumber;
    @Autowired
    HttpServletRequest requests;
    @Autowired
    GunMapper gunMapper;
    @Autowired
    AppMapper appMapper;
   /* @Value("${powerAlarmLevel}")
    private String powerAlarmLevel;
    @Value("${transmittingPower}")
    private String transmittingPower;
    @Value("${broadcastInterval}")
    private String broadcastInterval;
    @Value("${connectionInterval}")
    private String connectionInterval;
    @Value("${connectionTimeout}")
    private String connectionTimeout;
    @Value("${softwareversion}")
    private String softwareversion;
    @Value("${heartbeat}")
    private String heartbeat;
    @Value("${powerSampling}")
    private String powerSampling;*/

    /*HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();*/

    //@Scheduled(fixedDelay=5000)

    /**
     * 预出库报文消息：05报文
     *
     * @param bluetoothMac:枪支蓝牙
     * @param gunTag：枪编码
     * @param applyTime：领用时间
     * @param deadlineTime：归还时间
     * @param bindingReqMessageBody
     * @throws MessageEOFException
     */
    public BaseModel sendMessageAdvanceTheDelivery(BindingReqMessageBody bindingReqMessageBody,String appId) throws ParseException {

       /* for (BindingReqMessageBody info : bindingReqMessageBody) {
            System.out.println("----------------------"+info.toString());
        }*/
        log.debug("================ 开始：《预出库》 报文 05 消息推送 ================");
        BaseModel baseModel = new BaseModel();
        try {
            BindingResMessage bindingResMessage = new BindingResMessage();
            bindingResMessage.setUniqueIdentification(appId);//报文唯一标识：默认.208POSITIONSYSTEM 或设备IMEI
            bindingResMessage.setFormatVersion("V1");//格式版本
            bindingResMessage.setDeviceType("2");//设备类型：1.离位置报警设备 2.随行设备 3.腕表 4.定位模块
            bindingResMessage.setSerialNumber(dateTool.dateToString() + progressiveIncreaseNumber.getRandomNumber());//交易流水号
            bindingResMessage.setMessageType("05");//报文类型
            bindingResMessage.setSendTime(dateTool.dateToString());//发报时间
            bindingResMessage.setSessionToken(TokenUtils.channelSessionDigest());//标识本次会话唯一标志
            bindingResMessage.setMessageBody(bindingReqMessageBody);//报文体
            String jsonString = JSONObject.toJSONString(bindingResMessage, SerializerFeature.DisableCircularReferenceDetect);
            jmsMessagingTemplate.convertAndSend(storageQueue, jsonString);
            log.debug("************ 结束：《预出库》 报文 05 消息推送 -- 成功 **************：" + jsonString);
        } catch (Exception e) {
            baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
            baseModel.setErrorMessage("预出库报文发送失败，请稍后再试");
            log.error("预出库报文发送异常，服务器连接失败" + e);
            e.printStackTrace();
        }
        return baseModel;
    }

    /**
     * @param userId   警员编号
     * @param userName 警员姓名
     * @param gun      枪支编号
     * @param mac      枪支mac
     * @param endTime  归还时间
     * @return
     * @Description: 功能描述（最终出库：警员存在）
     * @Author: 刘家义
     * @CreateDate: 2018/11/6 15:43
     */
    public BaseModel sendMessageEndDelivery(Integer userId, String userName, String gun, String mac, String endTime,String appImei) {
        log.debug("================ 开始：《最终出库：警员存在》 报文 07 消息推送 ================");
        BaseModel baseModel = new BaseModel();
        try {
            //获取IMEI号
           // App app = appMapper.selectByPrimaryKey(Integer.valueOf(appId));

            OutWarehouseReqMessage message=new OutWarehouseReqMessage();
            OutWarehouseReqMessageBody messageBody=new OutWarehouseReqMessageBody();

            messageBody.setGunId(String.valueOf(userId));//人员编号
            messageBody.setUsername(userName);//人员姓名
            messageBody.setUserId(String.valueOf(userId));
            messageBody.setGunId(gun);//枪号
            messageBody.setGunMac(mac);//枪支设备蓝牙号
            messageBody.setBegintime(dateTool.dateToString());//领用开始时间
            messageBody.setReturnTime(dateTool.cutToString(endTime));//归还截止时间
            messageBody.setReserve("");


            message.setUniqueIdentification(appImei);//报文唯一标识：默认.208POSITIONSYSTEM 或设备IMEI
            message.setFormatVersion("V1");//格式版本
            message.setDeviceType("2");//设备类型：1.离位置报警设备 2.随行设备 3.腕表 4.定位模块
            message.setSerialNumber(dateTool.dateToString() + progressiveIncreaseNumber.getRandomNumber());//交易流水号
            message.setMessageType("07");//报文类型
            message.setSendTime(dateTool.dateToString());//发报时间
            message.setSessionToken(TokenUtils.channelSessionDigest());//标识本次会话唯一标志
            message.setMessageBody(messageBody);
            String jsonString = JSONObject.toJSONString(message, SerializerFeature.DisableCircularReferenceDetect);
            jmsMessagingTemplate.convertAndSend(storageQueue, jsonString);
            log.debug("************ 结束：《最终出库：警员存在》 报文 07 消息推送 -- 成功 **************：" + jsonString);
        }catch (Exception e){
            baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
            baseModel.setErrorMessage("最终出库：警员存在 报文 07 发送失败，请稍后再试");
            log.error("最终出库：警员存在 报文 07 发送异常，服务器连接失败" + e);
            e.printStackTrace();
        }
        return baseModel;
    }


    /**
     * @param gun     枪支编号
     * @param mac     枪支mac
     * @param endTime 归还时间
     * @return
     * @Description: 功能描述（最终出库：警员不存在）
     * @Author: 刘家义
     * @CreateDate: 2018/11/6 15:43
     */
    public BaseModel sendMessageEndDelivery(String gun, String mac, String endTime,String appImei) {
        log.debug("================ 开始：《最终出库：警员不存在》 报文 07 消息推送 ================");
        BaseModel baseModel = new BaseModel();
        try {
            //获取IMEI号
            //App app = appMapper.selectByPrimaryKey(Integer.valueOf(appId));

            OutWarehouseReqMessage message=new OutWarehouseReqMessage();
            OutWarehouseReqMessageBody messageBody=new OutWarehouseReqMessageBody();

            messageBody.setUserId("");//人员编号
            messageBody.setUsername("");//人员姓名
            messageBody.setGunId(gun);//枪号
            messageBody.setGunMac(mac);//枪支设备蓝牙号
            messageBody.setBegintime(dateTool.dateToString());//领用开始时间
            messageBody.setReturnTime(dateTool.cutToString(endTime));//归还截止时间
            messageBody.setReserve("");


            message.setUniqueIdentification(appImei);//报文唯一标识：默认.208POSITIONSYSTEM 或设备IMEI
            message.setFormatVersion("V1");//格式版本
            message.setDeviceType("2");//设备类型：1.离位置报警设备 2.随行设备 3.腕表 4.定位模块
            message.setSerialNumber(dateTool.dateToString() + progressiveIncreaseNumber.getRandomNumber());//交易流水号
            message.setMessageType("07");//报文类型
            message.setSendTime(dateTool.dateToString());//发报时间
            message.setSessionToken(TokenUtils.channelSessionDigest());//标识本次会话唯一标志
            message.setMessageBody(messageBody);
            String jsonString = JSONObject.toJSONString(message, SerializerFeature.DisableCircularReferenceDetect);
            jmsMessagingTemplate.convertAndSend(storageQueue, jsonString);
            log.debug("************ 结束：《最终出库：警员不存在》 报文 07 消息推送 -- 成功 **************：" + jsonString);
        }catch (Exception e){
            baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
            baseModel.setErrorMessage("最终出库：警员不存在 报文 07 发送失败，请稍后再试");
            log.error("最终出库：警员不存在 报文 07 发送异常，服务器连接失败" + e);
            e.printStackTrace();
        }
        return baseModel;
    }

    /**
     * @Description:  功能描述（撤销出库）
     * @Author:       刘家义
     * @CreateDate:   2018/11/6 16:25
    */
    public BaseModel sendMessageRevocationDelivery(String gId,String  IMEI) {
        log.debug("================ 开始：《撤销出库》 报文 09 消息推送 ================");
        BaseModel baseModel = new BaseModel();
        try {
            //获取IMEI号
            //App app = appMapper.selectByPrimaryKey(Integer.valueOf(appId));

            CancelRecipientsGunReqMessage message=new CancelRecipientsGunReqMessage();
            CancelRecipientsGunReqMessageBody messageBody=new CancelRecipientsGunReqMessageBody();

            messageBody.setGunId(gId);
            messageBody.setCancelTime(dateTool.dateToString());


            message.setUniqueIdentification(IMEI);//报文唯一标识：默认.208POSITIONSYSTEM 或设备IMEI
            message.setFormatVersion("V1");//格式版本
            message.setDeviceType("2");//设备类型：1.离位置报警设备 2.随行设备 3.腕表 4.定位模块
            message.setSerialNumber(dateTool.dateToString() + progressiveIncreaseNumber.getRandomNumber());//交易流水号
            message.setMessageType("09");//报文类型
            message.setSendTime(dateTool.dateToString());//发报时间
            message.setSessionToken(TokenUtils.channelSessionDigest());//标识本次会话唯一标志
            message.setMessageBody(messageBody);
            String jsonString = JSONObject.toJSONString(message, SerializerFeature.DisableCircularReferenceDetect);
            jmsMessagingTemplate.convertAndSend(storageQueue, jsonString);
            log.debug("************ 结束：《撤销出库》 报文 09 消息推送 -- 成功 **************：" + jsonString);
        }catch (Exception e){
            baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
            baseModel.setErrorMessage("撤销出库：报文 09 发送失败，请稍后再试");
            log.error("撤销出库：报文 09 发送异常，服务器连接失败" + e);
            e.printStackTrace();
        }
        return baseModel;
    }

    /**
     * @Description:  功能描述（入库）
     * @Author:       刘家义
     * @CreateDate:   2018/11/6 19:29
    */
    public BaseModel sendMessageBeforehandStorage(String gId  ,String appImei) {
        log.debug("================ 开始：《入库》 报文 11 消息推送 ================");
        BaseModel baseModel = new BaseModel();
        try {
            InWarehouseReqMessage message=new InWarehouseReqMessage();
            InWarehouseReqMessageBody messageBody=new InWarehouseReqMessageBody();

            messageBody.setGunId(gId);
            messageBody.setStage("1");//0:不入库  1：入库


            message.setUniqueIdentification(appImei);//报文唯一标识：默认.208POSITIONSYSTEM 或设备IMEI
            message.setFormatVersion("V1");//格式版本
            message.setDeviceType("2");//设备类型：1.离位置报警设备 2.随行设备 3.腕表 4.定位模块
            message.setSerialNumber(dateTool.dateToString() + progressiveIncreaseNumber.getRandomNumber());//交易流水号
            message.setMessageType("11");//报文类型
            message.setSendTime(dateTool.dateToString());//发报时间
            message.setSessionToken(TokenUtils.channelSessionDigest());//标识本次会话唯一标志
            message.setMessageBody(messageBody);
            String jsonString = JSONObject.toJSONString(message, SerializerFeature.DisableCircularReferenceDetect);
            jmsMessagingTemplate.convertAndSend(storageQueue, jsonString);
            log.debug("************ 结束：《入库》 报文 11 消息推送 -- 成功 **************：" + jsonString);
        }catch (Exception e){
            baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
            baseModel.setErrorMessage("入库：报文 11 发送失败，请稍后再试");
            log.error("入库：报文 11 发送异常，服务器连接失败" + e);
            e.printStackTrace();
        }
        return baseModel;
    }

    /**
     * @Description:  功能描述（撤销入库：腕表与原有的枪支进绑定）
     * @Author:       刘家义
     * @CreateDate:   2018/11/6 19:47
     */
    public BaseModel sendMessageRevocationStorage(String gun, String mac,String appId) throws Exception{
            log.debug("================ 开始：《撤销入库》 报文 12 消息推送 ================");
        BaseModel baseModel = new BaseModel();
        try {
            CancelInWarehouseReqMessage message=new CancelInWarehouseReqMessage();
            CancelInWarehouseReqMessageBody messageBody=new CancelInWarehouseReqMessageBody();

            messageBody.setGunId(gun);
            messageBody.setGunMac(mac);
            messageBody.setState("0");//不入库  0：撤销


            message.setUniqueIdentification(appId);//报文唯一标识：默认.208POSITIONSYSTEM 或设备IMEI
            message.setFormatVersion("V1");//格式版本
            message.setDeviceType("2");//设备类型：1.离位置报警设备 2.随行设备 3.腕表 4.定位模块
            message.setSerialNumber(dateTool.dateToString() + progressiveIncreaseNumber.getRandomNumber());//交易流水号
            message.setMessageType("13");//报文类型
            message.setSendTime(dateTool.dateToString());//发报时间
            message.setSessionToken(TokenUtils.channelSessionDigest());//标识本次会话唯一标志
            message.setMessageBody(messageBody);
            String jsonString = JSONObject.toJSONString(message, SerializerFeature.DisableCircularReferenceDetect);
            jmsMessagingTemplate.convertAndSend(storageQueue, jsonString);

                log.debug("************ 结束：《撤销入库》 报文 13 消息推送 -- 成功 **************：" + jsonString);
        }catch (Exception e){
            baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
                baseModel.setErrorMessage("撤销入库：报文 13 发送失败，请稍后再试");
                log.error("撤销入库：报文 13 发送异常，服务器连接失败" + e);
            e.printStackTrace();
        }
        return baseModel;
    }




    /**
     * 入库信息报文
     *
     * @param bluetoothMac:枪支ID
     * @param authCode:授权码
     */
    public BaseModel sendMessageStorage() throws MessageEOFException, ParseException {
        HttpSession session = requests.getSession();
        BaseModel baseModel = new BaseModel();

        return baseModel;
    }


    /**
     * 协助查找报文消息
     *
     * @param body
     * @return
     */
    public BaseModel sendMessageMinistrantFind(SearchGunReqMessageBody messageBody,String appImei) throws Exception {
        BaseModel baseModel = new BaseModel();
        log.debug("================ 开始：《协助查找/紧急支援》 报文 21 消息推送 ================");
        try {
            SearchGunReqMessage message=new SearchGunReqMessage();
            SearchGunReqMessageBody messageBody1=new SearchGunReqMessageBody();
            message.setUniqueIdentification(appImei);//报文唯一标识：默认.208POSITIONSYSTEM 或设备IMEI
            message.setFormatVersion("V1");//格式版本
            message.setDeviceType("2");//设备类型：1.离位置报警设备 2.随行设备 3.腕表 4.定位模块
            message.setSerialNumber(dateTool.dateToString() + progressiveIncreaseNumber.getRandomNumber());//交易流水号
            message.setMessageType("21");//报文类型
            message.setSendTime(dateTool.dateToString());//发报时间
            message.setSessionToken(TokenUtils.channelSessionDigest());//标识本次会话唯一标志
            message.setMessageBody(messageBody);
            String jsonString = JSONObject.toJSONString(message, SerializerFeature.DisableCircularReferenceDetect);
            jmsMessagingTemplate.convertAndSend(storageQueue, jsonString);
            log.debug("================ 结束：《协助查找/紧急支援》 报文 21 消息推送 ================"+jsonString.toString());
        }catch (Exception e){
            baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
            baseModel.setErrorMessage("协助查找/紧急支援：报文 21 发送失败，请稍后再试");
            log.error("协助查找/紧急支援：报文 21 发送异常，服务器连接失败" + e);
        }
        return baseModel;
    }


    /**
     * 离位报警启停操作
     *
     * @param state        启停状态：[1/0（停止/重启）
     * @param bluetoothMac
     * @return
     * @throws ParseException
     */
    public BaseModel sendMessageOffNormalAlarmStartAndStop(StartStopSearchGunReqMessageBody messageBody,String appImei) throws ParseException {
        System.out.println("************"+messageBody.toString());
        BaseModel baseModel = new BaseModel();
        try {
            log.debug("================ 开始：《离位报警启停操作》 报文 19 消息推送 ================");
            StartStopSearchGunReqMessage message = new StartStopSearchGunReqMessage();

            message.setUniqueIdentification(appImei);//报文唯一标识：默认.208POSITIONSYSTEM 或设备IMEI
            message.setFormatVersion("V1");//格式版本
            message.setDeviceType("2");//设备类型：1.离位置报警设备 2.随行设备 3.腕表 4.定位模块
            message.setSerialNumber(dateTool.dateToString() + progressiveIncreaseNumber.getRandomNumber());//交易流水号
            message.setMessageType("19");//报文类型
            message.setSendTime(dateTool.dateToString());//发报时间
            message.setSessionToken(TokenUtils.channelSessionDigest());//标识本次会话唯一标志
            message.setMessageBody(messageBody);
            String jsonString = JSONObject.toJSONString(message, SerializerFeature.DisableCircularReferenceDetect);
            jmsMessagingTemplate.convertAndSend(storageQueue, jsonString);
            log.debug("================ 结束：《离位报警启停操作》 报文 19 消息推送 ================"+jsonString);
        }catch (Exception e){
            baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
            baseModel.setErrorMessage("离位报警启停操作：报文 19 发送失败，请稍后再试");
            log.error("离位报警启停操作：报文 19 发送失败，请稍后再试" + e);
        }
        return baseModel;
    }

    /**
     * 射弹数报文申请:25
     *
     * @param gunMac
     * @return
     */
    public BaseModel sendMessageBulletNumberApply(String appImei, String gunMac) throws Exception {
        BaseModel baseModel = new BaseModel();
        try {
            log.debug("================ 开始：《射弹数报文申请》 报文 25 消息推送 ================");
            GetBulletNumberReqMessage message = new GetBulletNumberReqMessage();
            GetBulletNumberReqMessageBody messageBody=new GetBulletNumberReqMessageBody();

            message.setUniqueIdentification(appImei);//报文唯一标识：默认.208POSITIONSYSTEM 或设备IMEI
            message.setFormatVersion("V1");//格式版本
            message.setDeviceType("2");//设备类型：1.离位置报警设备 2.随行设备 3.腕表 4.定位模块
            message.setSerialNumber(dateTool.dateToString() + progressiveIncreaseNumber.getRandomNumber());//交易流水号
            message.setMessageType("25");//报文类型
            message.setSendTime(dateTool.dateToString());//发报时间
            message.setSessionToken(TokenUtils.channelSessionDigest());//标识本次会话唯一标志

            messageBody.setGunMac(gunMac);
            messageBody.setAuthCode(TokenUtils.channelSessionDigest());
            message.setMessageBody(messageBody);
            String jsonString = JSONObject.toJSONString(message, SerializerFeature.DisableCircularReferenceDetect);
            jmsMessagingTemplate.convertAndSend(storageQueue, jsonString);
            log.debug("================ 结束：《射弹数报文申请》 报文 25 消息推送 ================"+jsonString);
        }catch (Exception e){
            baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
            baseModel.setErrorMessage("射弹数报文申请：报文 25 发送失败，请稍后再试");
            log.error("离位报警启停操作：报文 25 发送失败，请稍后再试" + e);
        }
        return baseModel;
    }

    /**
     * 27：服务器向随行设备或腕表推送参数设置信息
     *
     * @param gunMac
     * @return
     */
    public BaseModel sendMessageAppSetUpParameters(String appImei) throws Exception {
        BaseModel baseModel = new BaseModel();
        try {
            log.debug("================ 开始：《服务器向随行设备或腕表推送参数设置信息》 报文 27 消息推送 ================");
            ParamSettingReqMessage message = new ParamSettingReqMessage();
            ParamSettingMessageBody messageBody=new ParamSettingMessageBody();

            message.setUniqueIdentification(appImei);//报文唯一标识：默认.208POSITIONSYSTEM 或设备IMEI
            message.setFormatVersion("V1");//格式版本
            message.setDeviceType("2");//设备类型：1.离位置报警设备 2.随行设备 3.腕表 4.定位模块
            message.setSerialNumber(dateTool.dateToString() + progressiveIncreaseNumber.getRandomNumber());//交易流水号
            message.setMessageType("21");//报文类型
            message.setSendTime(dateTool.dateToString());//发报时间
            message.setSessionToken(TokenUtils.channelSessionDigest());//标识本次会话唯一标志

            messageBody.setBroadcastInterval("1");
            messageBody.setConnectionInterval("1");
            messageBody.setConnectionTimeout("1");
            messageBody.setHeartbeat("1");
            messageBody.setMatchTime("1");
            messageBody.setPositioningInterval("1");
            messageBody.setPowerAlarmLevel("1");
            messageBody.setPowerSampling("1");
            messageBody.setSafeCode("1");
            messageBody.setSoftwareDeviceVersion("1");
            messageBody.setSystemTime("1");
            messageBody.setTransmittingPower("1");
            messageBody.setAuthCode(TokenUtils.channelSessionDigest());
            message.setMessageBody(messageBody);
            String jsonString = JSONObject.toJSONString(message, SerializerFeature.DisableCircularReferenceDetect);
            jmsMessagingTemplate.convertAndSend(storageQueue, jsonString);
            log.debug("================ 结束：《服务器向随行设备或腕表推送参数设置信息》 报文 27 消息推送 ================"+jsonString);
        }catch (Exception e){
            baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
            baseModel.setErrorMessage("服务器向随行设备或腕表推送参数设置信息：报文 27 发送失败，请稍后再试");
            log.error("服务器向随行设备或腕表推送参数设置信息：报文 27 发送失败，请稍后再试" + e);
        }
        return baseModel;
    }



}

