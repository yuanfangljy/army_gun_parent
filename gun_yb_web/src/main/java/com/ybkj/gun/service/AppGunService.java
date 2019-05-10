package com.ybkj.gun.service;

import com.ybkj.gun.model.AppGun;
import com.ybkj.gun.model.Gun;
import com.ybkj.model.BaseModel;

import java.util.List;

/**
 * @项目名称：
 * @类名称：
 * @类描述：为设备分配枪支接口
 * @创建人：liujiayi
 * @创建时间：2018/11/12 15:50
 * @修改时间：2018/11/12 15:50
 * @version：1.0
 */
public interface AppGunService {
    /**
     * @Description:  功能描述（新增分配枪支）
     * @Author:       刘家义
     * @CreateDate:   2018/11/12 15:51
    */
    BaseModel addAppGun(String gunIds, String appId, Integer gunUserId) throws Exception;

    /**
     * @Description:  功能描述（查询所有绑定的枪支的设备）
     * @Author:       刘家义
     * @CreateDate:   2018/11/12 19:09
    */
    List<AppGun> findAppGunBinding(String type)  throws Exception;

    /**
     * @Description:  功能描述（查询离位的枪支）
     * @Author:       刘家义
     * @CreateDate:   2018/12/10 16:52
    */
    List<AppGun> findOffNormalGun(String gunId) throws Exception;


    /**
     * @Description:  功能描述（枪支离位数）
     * @Author:       刘家义
     * @CreateDate:   2018/12/11 13:50
    */
    Integer findOffNormalGunCount() throws Exception;

    /**
     * @Description:  功能描述（查询在线设备）
     * @Author:       刘家义
     * @CreateDate:   2018/12/10 17:20
    */
    List<AppGun> findOnLineApp(String appName) throws Exception;

    /**
     * @Description:  功能描述（获取在线设备数）
     * @Author:       刘家义
     * @CreateDate:   2018/12/11 13:42
    */
    Integer findOnLineAppCount() throws Exception;


}
