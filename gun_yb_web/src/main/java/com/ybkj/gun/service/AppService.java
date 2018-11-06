package com.ybkj.gun.service;

import com.ybkj.gun.model.App;
import com.ybkj.model.BaseModel;

import java.util.List;

/**
 * @项目名称：
 * @类名称：
 * @类描述：腕表/手机接口
 * @创建人：liujiayi
 * @创建时间：2018/11/2 14:34
 * @修改时间：2018/11/2 14:34
 * @version：1.0
 */
public interface AppService {

    /**
     * @Description:  功能描述（新增腕表/手机）
     * @Author:       刘家义
     * @CreateDate:   2018/11/2 14:51
    */
    BaseModel addApp(App app) throws Exception;
    /**
     * @Description:  功能描述（删除 腕表/手机）
     * @Author:       刘家义
     * @CreateDate:   2018/11/2 14:51
     */
    BaseModel removeApp(String appId) throws Exception;
    /**
     * @Description:  功能描述（更新 腕表/手机）
     * @Author:       刘家义
     * @CreateDate:   2018/11/2 14:51
     */
    BaseModel revampApp(App app) throws Exception;
    /**
     * @Description:  功能描述（根据Id查询 腕表/手机）
     * @Author:       刘家义
     * @CreateDate:   2018/11/2 14:51
     */
    BaseModel findAppById(Integer appId) throws Exception;
    /**
     * @Description:  功能描述（查询 腕表/手机 列表）
     * @Author:       刘家义
     * @CreateDate:   2018/11/2 14:51
     */
    List<App> findApps() throws Exception;
}
