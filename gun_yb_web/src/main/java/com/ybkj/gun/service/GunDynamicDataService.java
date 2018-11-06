package com.ybkj.gun.service;

import com.ybkj.gun.model.GunDynamicData;
import com.ybkj.model.BaseModel;

import java.util.List;

/**
 * @项目名称：
 * @类名称：
 * @类描述：枪支动态数据接口
 * @创建人：liujiayi
 * @创建时间：2018/11/4 16:35
 * @修改时间：2018/11/4 16:35
 * @version：1.0
 */
public interface GunDynamicDataService {
    /**
     * @Description:  功能描述（根据枪支编号查询枪支动态信息）
     * @Author:       刘家义
     * @CreateDate:   2018/11/4 16:37
    */
    BaseModel findGunDynamicDataByGunId(Integer gunId) throws Exception;

    /**
     * @Description:  功能描述（根据枪支编号，查询枪支动态数据列表）
     * @Author:       刘家义
     * @CreateDate:   2018/11/4 16:37
    */
    List<GunDynamicData> findGunDynamicDatas(String gunId) throws Exception;
}
