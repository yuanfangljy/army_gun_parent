package com.ybkj.gun.service;

import com.ybkj.model.BaseModel;

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
     * @Description: 功能描述（枪支预出库）
     * @Author: 刘家义
     * @CreateDate: 2018/11/5 10:16
     */
    BaseModel addWareHouseRecordsBeforehandDelivery(String gunIds, Integer appId, Integer gunUserId) throws Exception;


    /**
     * @Description: 功能描述（枪支最终出库）警员信息存在
     * @Author: 刘家义
     * @CreateDate: 2018/11/6 14:44
     */
    BaseModel endWareHouseRecordsDelivery(Integer userId, String gunId, String gunMac, String endTime) throws Exception;

    /**
     * @Description: 功能描述（枪支最终出库）警员信息存在s
     * @Author: 刘家义
     * @CreateDate: 2018/11/6 15:04
     */
    BaseModel endWareHouseRecordsDelivery(String gunId, String gunMac, String endTime) throws Exception;

    /**
     * @Description: 功能描述（撤销出库）
     * @Author: 刘家义
     * @CreateDate: 2018/11/6 16:19
     */
    BaseModel revocationWareHouseRecordsDelivery(String gunId) throws Exception;

    /**
     * @Description: 功能描述（枪支预入库）
     * @Author: 刘家义
     * @CreateDate: 2018/11/6 17:27
     */
    BaseModel addWareHouseRecordsBeforehandStorage(String gunId) throws Exception;

    /**
     * @Description: 功能描述（撤销入库）
     * @Author: 刘家义
     * @CreateDate: 2018/11/6 19:44
     */
    BaseModel revocationWareHouseRecordsStorage(String gunId, String gunMac) throws Exception;
}
