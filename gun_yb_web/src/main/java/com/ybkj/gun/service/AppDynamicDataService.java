package com.ybkj.gun.service;

import com.ybkj.gun.model.AppDynamicData;
import com.ybkj.model.BaseModel;

import java.util.List;

/**
 * @项目名称：
 * @类名称：
 * @类描述：获取App动态数据接口
 * @创建人：liujiayi
 * @创建时间：2018/11/4 15:10
 * @修改时间：2018/11/4 15:10
 * @version：1.0
 */
public interface AppDynamicDataService {

    /**
     * @Description:  功能描述（根据AppId查询app动态信息）
     * @Author:       刘家义
     * @CreateDate:   2018/11/4 15:11
    */
    BaseModel findAppDynamicDataByAppId(Integer appId) throws Exception;

    /**
     * @Description:  功能描述（根据AppName，分页查询app动态信息）
     * @Author:       刘家义
     * @CreateDate:   2018/11/4 15:12
    */
    List<AppDynamicData> findAppDynamicDatas(String appName) throws Exception;
}
