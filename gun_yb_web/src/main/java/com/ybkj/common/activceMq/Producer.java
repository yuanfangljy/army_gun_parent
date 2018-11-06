package com.ybkj.common.activceMq;

import com.alibaba.fastjson.JSONObject;
import com.ybkj.common.pojo.*;
import com.ybkj.enums.IStatusMessage;
import com.ybkj.gun.mapper.GunMapper;
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
    public BaseModel sendMessageAdvanceTheDelivery(List<BindingReqMessageBody> bindingReqMessageBody) throws ParseException {

        /*for (BindingReqMessageBody info : bindingReqMessageBody) {
            System.out.println("----------------------"+info.toString());
        }*/
        log.debug("================ 开始：《预出库》 报文 05 消息推送 ================");
        BaseModel baseModel = new BaseModel();
        try {
            BindingResMessage bindingResMessage = new BindingResMessage();
            bindingResMessage.setUniqueIdentification("208POSITIONSYSTEM");//报文唯一标识：默认.208POSITIONSYSTEM 或设备IMEI
            bindingResMessage.setFormatVersion("0001");//格式版本
            bindingResMessage.setDeviceType("2");//设备类型：1.离位置报警设备 2.随行设备 3.腕表 4.定位模块
            bindingResMessage.setSerialNumber(dateTool.dateToString() + progressiveIncreaseNumber.getNumber(1));//交易流水号
            bindingResMessage.setMessageType("05");//报文类型
            bindingResMessage.setSendTime(dateTool.dateToString());//发报时间
            bindingResMessage.setSessionToken(TokenUtils.channelSessionDigest());//标识本次会话唯一标志
            bindingResMessage.setMessageBody(bindingReqMessageBody);//报文体
            String jsonString = JSONObject.toJSONString(bindingResMessage);
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
    public BaseModel sendMessageEndDelivery(Integer userId, String userName, String gun, String mac, String endTime) {
        log.debug("================ 开始：《最终出库：警员存在》 报文 07 消息推送 ================");
        BaseModel baseModel = new BaseModel();
        try {
            OutWarehouseReqMessage outWarehouseReqMessage=new OutWarehouseReqMessage();
            OutWarehouseReqMessageBody outWarehouseReqMessageBody=new OutWarehouseReqMessageBody();

            outWarehouseReqMessageBody.setGunId(String.valueOf(userId));//人员编号
            outWarehouseReqMessageBody.setUsername(userName);//人员姓名
            outWarehouseReqMessageBody.setGunId(gun);//枪号
            outWarehouseReqMessageBody.setGunMac(mac);//枪支设备蓝牙号
            outWarehouseReqMessageBody.setBegintime(dateTool.dateToString());//领用开始时间
            outWarehouseReqMessageBody.setReturnTime(endTime);//归还截止时间
            outWarehouseReqMessageBody.setReserve("");


            outWarehouseReqMessage.setUniqueIdentification("208POSITIONSYSTEM");//报文唯一标识：默认.208POSITIONSYSTEM 或设备IMEI
            outWarehouseReqMessage.setFormatVersion("0001");//格式版本
            outWarehouseReqMessage.setDeviceType("2");//设备类型：1.离位置报警设备 2.随行设备 3.腕表 4.定位模块
            outWarehouseReqMessage.setSerialNumber(dateTool.dateToString() + progressiveIncreaseNumber.getNumber(1));//交易流水号
            outWarehouseReqMessage.setMessageType("07");//报文类型
            outWarehouseReqMessage.setSendTime(dateTool.dateToString());//发报时间
            outWarehouseReqMessage.setSessionToken(TokenUtils.channelSessionDigest());//标识本次会话唯一标志
            outWarehouseReqMessage.setMessageBody(outWarehouseReqMessageBody);
            String jsonString = JSONObject.toJSONString(outWarehouseReqMessage);
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
    public BaseModel sendMessageEndDelivery(String gun, String mac, String endTime) {
        log.debug("================ 开始：《最终出库：警员不存在》 报文 07 消息推送 ================");
        BaseModel baseModel = new BaseModel();
        try {
            OutWarehouseReqMessage outWarehouseReqMessage=new OutWarehouseReqMessage();
            OutWarehouseReqMessageBody outWarehouseReqMessageBody=new OutWarehouseReqMessageBody();

            outWarehouseReqMessageBody.setGunId("");//人员编号
            outWarehouseReqMessageBody.setUsername("");//人员姓名
            outWarehouseReqMessageBody.setGunId(gun);//枪号
            outWarehouseReqMessageBody.setGunMac(mac);//枪支设备蓝牙号
            outWarehouseReqMessageBody.setBegintime(dateTool.dateToString());//领用开始时间
            outWarehouseReqMessageBody.setReturnTime(endTime);//归还截止时间
            outWarehouseReqMessageBody.setReserve("");


            outWarehouseReqMessage.setUniqueIdentification("208POSITIONSYSTEM");//报文唯一标识：默认.208POSITIONSYSTEM 或设备IMEI
            outWarehouseReqMessage.setFormatVersion("0001");//格式版本
            outWarehouseReqMessage.setDeviceType("2");//设备类型：1.离位置报警设备 2.随行设备 3.腕表 4.定位模块
            outWarehouseReqMessage.setSerialNumber(dateTool.dateToString() + progressiveIncreaseNumber.getNumber(1));//交易流水号
            outWarehouseReqMessage.setMessageType("07");//报文类型
            outWarehouseReqMessage.setSendTime(dateTool.dateToString());//发报时间
            outWarehouseReqMessage.setSessionToken(TokenUtils.channelSessionDigest());//标识本次会话唯一标志
            outWarehouseReqMessage.setMessageBody(outWarehouseReqMessageBody);
            String jsonString = JSONObject.toJSONString(outWarehouseReqMessage);
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
    public BaseModel sendMessageRevocationDelivery(String gId) {
        log.debug("================ 开始：《撤销出库》 报文 09 消息推送 ================");
        BaseModel baseModel = new BaseModel();
        try {
            CancelRecipientsGunReqMessage cancelRecipientsGunReqMessage=new CancelRecipientsGunReqMessage();
            CancelRecipientsGunReqMessageBody cancelRecipientsGunReqMessageBody=new CancelRecipientsGunReqMessageBody();

            cancelRecipientsGunReqMessageBody.setGunId(gId);
            cancelRecipientsGunReqMessageBody.setCancelTime(dateTool.dateToString());


            cancelRecipientsGunReqMessage.setUniqueIdentification("208POSITIONSYSTEM");//报文唯一标识：默认.208POSITIONSYSTEM 或设备IMEI
            cancelRecipientsGunReqMessage.setFormatVersion("0001");//格式版本
            cancelRecipientsGunReqMessage.setDeviceType("2");//设备类型：1.离位置报警设备 2.随行设备 3.腕表 4.定位模块
            cancelRecipientsGunReqMessage.setSerialNumber(dateTool.dateToString() + progressiveIncreaseNumber.getNumber(1));//交易流水号
            cancelRecipientsGunReqMessage.setMessageType("09");//报文类型
            cancelRecipientsGunReqMessage.setSendTime(dateTool.dateToString());//发报时间
            cancelRecipientsGunReqMessage.setSessionToken(TokenUtils.channelSessionDigest());//标识本次会话唯一标志
            cancelRecipientsGunReqMessage.setMessageBody(cancelRecipientsGunReqMessageBody);
            String jsonString = JSONObject.toJSONString(cancelRecipientsGunReqMessage);
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
     * @Description:  功能描述（预入库）
     * @Author:       刘家义
     * @CreateDate:   2018/11/6 19:29
    */
    public BaseModel sendMessageBeforehandStorage(String gId) {
        log.debug("================ 开始：《预入库》 报文 11 消息推送 ================");
        BaseModel baseModel = new BaseModel();
        try {
            InWarehouseReqMessage inWarehouseReqMessage=new InWarehouseReqMessage();
            InWarehouseReqMessageBody inWarehouseReqMessageBody=new InWarehouseReqMessageBody();

            inWarehouseReqMessageBody.setGunId(gId);
            inWarehouseReqMessageBody.setStage("1");//0:不入库  1：入库


            inWarehouseReqMessage.setUniqueIdentification("208POSITIONSYSTEM");//报文唯一标识：默认.208POSITIONSYSTEM 或设备IMEI
            inWarehouseReqMessage.setFormatVersion("0001");//格式版本
            inWarehouseReqMessage.setDeviceType("2");//设备类型：1.离位置报警设备 2.随行设备 3.腕表 4.定位模块
            inWarehouseReqMessage.setSerialNumber(dateTool.dateToString() + progressiveIncreaseNumber.getNumber(1));//交易流水号
            inWarehouseReqMessage.setMessageType("11");//报文类型
            inWarehouseReqMessage.setSendTime(dateTool.dateToString());//发报时间
            inWarehouseReqMessage.setSessionToken(TokenUtils.channelSessionDigest());//标识本次会话唯一标志
            inWarehouseReqMessage.setMessageBody(inWarehouseReqMessageBody);
            String jsonString = JSONObject.toJSONString(inWarehouseReqMessage);
            jmsMessagingTemplate.convertAndSend(storageQueue, jsonString);
            log.debug("************ 结束：《预入库》 报文 11 消息推送 -- 成功 **************：" + jsonString);
        }catch (Exception e){
            baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
            baseModel.setErrorMessage("预入库：报文 11 发送失败，请稍后再试");
            log.error("预入库：报文 11 发送异常，服务器连接失败" + e);
            e.printStackTrace();
        }
        return baseModel;
    }

    /**
     * @Description:  功能描述（撤销入库：腕表与原有的枪支进绑定）
     * @Author:       刘家义
     * @CreateDate:   2018/11/6 19:47
     */
    public BaseModel sendMessageRevocationStorage(String gun, String mac) throws Exception{
        log.debug("================ 开始：《撤销入库》 报文 12 消息推送 ================");
        BaseModel baseModel = new BaseModel();
        try {
            CancelInWarehouseReqMessage cancelInWarehouseReqMessage=new CancelInWarehouseReqMessage();
            CancelInWarehouseReqMessageBody cancelInWarehouseReqMessageBody=new CancelInWarehouseReqMessageBody();

            cancelInWarehouseReqMessageBody.setGunId(gun);
            cancelInWarehouseReqMessageBody.setGunMac(mac);
            cancelInWarehouseReqMessageBody.setState("0");//不入库


            cancelInWarehouseReqMessage.setUniqueIdentification("208POSITIONSYSTEM");//报文唯一标识：默认.208POSITIONSYSTEM 或设备IMEI
            cancelInWarehouseReqMessage.setFormatVersion("0001");//格式版本
            cancelInWarehouseReqMessage.setDeviceType("2");//设备类型：1.离位置报警设备 2.随行设备 3.腕表 4.定位模块
            cancelInWarehouseReqMessage.setSerialNumber(dateTool.dateToString() + progressiveIncreaseNumber.getNumber(1));//交易流水号
            cancelInWarehouseReqMessage.setMessageType("13");//报文类型
            cancelInWarehouseReqMessage.setSendTime(dateTool.dateToString());//发报时间
            cancelInWarehouseReqMessage.setSessionToken(TokenUtils.channelSessionDigest());//标识本次会话唯一标志
            cancelInWarehouseReqMessage.setMessageBody(cancelInWarehouseReqMessageBody);
            String jsonString = JSONObject.toJSONString(cancelInWarehouseReqMessage);
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
    public BaseModel sendMessageMinistrantFind(JSONObject body) throws Exception {
        BaseModel baseModel = new BaseModel();

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
    public BaseModel sendMessageOffNormalAlarmStartAndStop(String state, String bluetoothMac) throws ParseException {
        BaseModel baseModel = new BaseModel();

        return baseModel;
    }

    /**
     * 射弹数报文申请
     *
     * @param gunMac
     * @return
     */
    public BaseModel sendMessageBulletNumberApply(String gunMac) throws Exception {
        BaseModel baseModel = new BaseModel();

        return baseModel;
    }



}

