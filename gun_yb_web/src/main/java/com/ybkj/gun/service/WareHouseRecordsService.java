package com.ybkj.gun.service;

import com.ybkj.gun.model.WarehouseRecords;
import com.ybkj.model.BaseModel;

import java.util.List;

/**
 * @项目名称：
 * @类名称：
 * @类描述：枪支出入库记录接口
 * @创建人：liujiayi
 * @创建时间：2018/11/5 10:14
 * @修改时间：2018/11/5 10:14
 * @version：1.0
 */
public interface WareHouseRecordsService {

    /**
     * @Description: 功能描述（枪支预出库） 第一版
     * @Author: 刘家义
     * @CreateDate: 2018/11/5 10:16
     */
    BaseModel addWareHouseRecordsBeforehandDelivery(String gunIds, String appIMEI, Integer gunUserId) throws Exception;

    /**
     * @Description:  功能描述（枪支预出库） 第二版
     * @Author:       刘家义
     * @CreateDate:   2018/11/12 19:38
    */
    BaseModel addDeviceBindingGunsBeforehandDelivery(String appId) throws Exception;

    /**
     * @Description: 功能描述（枪支最终出库）警员信息存在 第一版
     * @Author: 刘家义
     * @CreateDate: 2018/11/6 14:44
     */
    BaseModel endWareHouseRecordsDelivery(Integer userId, String gunId, String gunMac, String endTime,String appId) throws Exception;


    /**
     * @Description: 功能描述（枪支最终出库）警员信息存在 第二版
     * @Author: 刘家义
     * @CreateDate: 2018/11/6 14:44
     */
    BaseModel endWareHouseRecordsDelivery(String appId, String endTime) throws Exception;

    /**
     * @Description: 功能描述（枪支最终出库）警员信息不存在s
     * @Author: 刘家义
     * @CreateDate: 2018/11/6 15:04
     */
    BaseModel endWareHouseRecordsDelivery(String gunId, String gunMac, String endTime ,String appId) throws Exception;

    /**
     * @Description: 功能描述（撤销出库）
     * @Author: 刘家义
     * @CreateDate: 2018/11/6 16:19
     */
    BaseModel revocationWareHouseRecordsDelivery(String gunId,Integer appId) throws Exception;


    /**
     * @Description: 功能描述（撤销出库）
     * @Author: 刘家义
     * @CreateDate: 2018/11/6 16:19
     */
    BaseModel revocationWareHouseRecordsDelivery(Integer appId) throws Exception;


    /**
     * @Description: 功能描述（枪支入库）
     * @Author: 刘家义
     * @CreateDate: 2018/11/6 17:27
     */
    BaseModel addWareHouseRecordsBeforehandStorage(String gunId, String appImei) throws Exception;

    /**
     * @Description: 功能描述（撤销入库）
     * @Author: 刘家义
     * @CreateDate: 2018/11/6 19:44
     */
    BaseModel revocationWareHouseRecordsStorage(String gunId, String gunMac,String appId) throws Exception;

    /**
     * @Description:  功能描述（查询库存记录表）
     * @Author:       刘家义
     * @CreateDate:   2018/11/10 12:15
    */
    List<WarehouseRecords> findWareHouseRecords(Integer type) throws Exception;


    /**
     * @Description:  功能描述（读取累计射弹计数的申请数据:25）
     * @Author:       刘家义
     * @CreateDate:   2018/12/1 17:06
    */
    BaseModel theProjectileBase(Integer appId, String gunId) throws Exception;
}
