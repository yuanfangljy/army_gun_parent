package com.ybkj.common.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @项目名称：
 * @类名称：
 * @类描述：
 * @创建人：liujiayi
 * @创建时间：2018/12/9 16:24
 * @修改时间：2018/12/9 16:24
 * @version：1.0
 */
@Data
public class AppAndGunLocationVO implements Serializable{
    private static final long serialVersionUID= -2783081162694130311L;

    private String longitude;//经度
    private String latitude;//纬度
    private String imei;//IMEI号
    private Integer typeImei;//imei类型
    private Integer gunDeviceState;//枪支定位设备是否在位(没有被拆 0:在位 1：不在位)
    private String appPhone;//设备电话
    private String gunId;//枪支编号
    private String appName;//设备名称
    private String gunIds;//绑定的枪支数
    private String gunType;//枪支类型
    private String gunModel;//枪支型号

}
