package com.ybkj.common.entity;

import lombok.Data;

/**
 * @项目名称：
 * @类名称：
 * @类描述：封装枪支位置信息
 * @创建人：liujiayi
 * @创建时间：2018/11/12 14:33
 * @修改时间：2018/11/12 14:33
 * @version：1.0
 */
@Data
@SuppressWarnings("all")
public class GunLocationVO {

    private String gunId;//枪支编号
    private String gunModel;//枪支型号
    private String gunType;//枪支类型
    private String gunMac;//枪支蓝牙
    private String longitude;//经度
    private String latitude;//纬度
    private String gunDeviceBatteryPower;//枪支蓝牙电量
    private Integer gunDeviceState;//枪支定位设备是否在位(没有被拆 0:在位 1：不在位)
    private Integer bulletNumber;//枪支射弹数
    private Integer totalBulletNumber;//枪支总射弹数
    private String warehouseName;//库房名称
    private String appIMEI;//IMEI号
    private String appPhone;//腕表/手机app绑定的电话号码
    private String appBatteryPower;//随行设备的电量
    private Integer realTimeState;//枪支状态

}
