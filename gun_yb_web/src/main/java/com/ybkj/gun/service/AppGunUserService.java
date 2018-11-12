package com.ybkj.gun.service;

import com.ybkj.gun.model.AppGunUser;

import java.util.List;

/**
 * @项目名称：
 * @类名称：
 * @类描述：设备注册，绑定的用户信息
 * @创建人：liujiayi
 * @创建时间：2018/11/10 17:26
 * @修改时间：2018/11/10 17:26
 * @version：1.0
 */
public interface AppGunUserService {
    
    /**
     * @Description:  功能描述（获取设备注册，绑定的用户信息）
     * @Author:       刘家义
     * @CreateDate:   2018/11/10 17:27
    */
    List<AppGunUser> findAppGunUserList() throws Exception;

    /**
     * @Description:  功能描述（通过 appId 获取 gunUserID）
     * @Author:       刘家义
     * @CreateDate:   2018/11/11 15:13
    */
    AppGunUser findAppGunUserByAppId(Integer appId) throws Exception;
}
