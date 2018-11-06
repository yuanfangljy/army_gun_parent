package com.ybkj.gun.service;

import com.ybkj.gun.model.Warehouse;
import com.ybkj.model.BaseModel;

import java.util.List;

/**
 * @项目名称：
 * @类名称：
 * @类描述：库室接口
 * @创建人：liujiayi
 * @创建时间：2018/11/1 15:18
 * @修改时间：2018/11/1 15:18
 * @version：1.0
 */
public interface WareHouseService {

    /**
     * @Description:  功能描述（新增库室接口）
     * @Author:       刘家义
     * @CreateDate:   2018/11/1 15:43
    */
    BaseModel addWareHouse(Warehouse warehouse) throws Exception;

    /**
     * @Description:  功能描述（删除库室）
     * @Author:       刘家义
     * @CreateDate:   2018/11/1 17:26
    */
    BaseModel removeWareHourse(String wId) throws Exception;

    /**
     * @Description:  功能描述（修改库室）
     * @Author:       刘家义
     * @CreateDate:   2018/11/1 19:38
    */
    BaseModel revampWareHouse(Warehouse warehouse) throws Exception;

    /**
     * @Description:  功能描述（根据库室id,查询库室信息）
     * @Author:       刘家义
     * @CreateDate:   2018/11/1 19:57
    */
    BaseModel findWareHouseById(Integer wId) throws Exception;

    /**
     * @Description:  功能描述（查询库室列表）
     * @Author:       刘家义
     * @CreateDate:   2018/11/1 20:03
    */
    List<Warehouse> findWarehouses() throws Exception;
}
