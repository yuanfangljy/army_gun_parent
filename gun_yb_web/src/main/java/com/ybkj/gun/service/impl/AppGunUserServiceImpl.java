package com.ybkj.gun.service.impl;

import com.ybkj.gun.mapper.AppGunUserMapper;
import com.ybkj.gun.model.AppGunUser;
import com.ybkj.gun.model.GunUser;
import com.ybkj.gun.service.AppGunUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @项目名称：
 * @类名称：
 * @类描述：设备注册，绑定的用户信息,业务逻辑具体实现
 * @创建人：liujiayi
 * @创建时间：2018/11/10 17:27
 * @修改时间：2018/11/10 17:27
 * @version：1.0
 */
@Service
@Transactional
@Slf4j
@SuppressWarnings("all")
public class AppGunUserServiceImpl implements AppGunUserService{

    @Autowired
    private AppGunUserMapper appGunUserMapper;
    /**
     * @Description:  功能描述（获取设备注册，绑定的用户信息）
     * @Author:       刘家义
     * @CreateDate:   2018/11/10 17:28
    */
    @Override
    public List<AppGunUser> findAppGunUserList() throws Exception {
        return appGunUserMapper.selectAppGunUserList();
    }

    /**
     * @Description:  功能描述（通过 appId 获取 gunUserID）
     * @Author:       刘家义
     * @CreateDate:   2018/11/11 15:14
    */
    @Override
    public AppGunUser findAppGunUserByAppId(Integer appId) throws Exception {
        return appGunUserMapper.selectAppGunUserByAppId(appId);
    }


}
