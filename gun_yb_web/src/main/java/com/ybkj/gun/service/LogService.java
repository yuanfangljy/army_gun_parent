package com.ybkj.gun.service;

import com.ybkj.gun.model.WebUserLogin;

import java.util.List;

/**
 * @项目名称：
 * @类名称：
 * @类描述：用户日志记录接口
 * @创建人：liujiayi
 * @创建时间：2018/11/2 17:07
 * @修改时间：2018/11/2 17:07
 * @version：1.0
 */
public interface LogService {

    /**
     * @Description:  功能描述（创建用户登录登出日志）
     * @Author:       刘家义
     * @CreateDate:   2018/11/2 17:10
    */
     int addLogLoginLogOut(WebUserLogin webUserLogin) throws Exception;

    /**
     * @Description:  功能描述（根据用户名，查询用户登入出日志信息）
     * @Author:       刘家义
     * @CreateDate:   2018/11/2 17:46
    */
    List<WebUserLogin> findWebUserLogins(String userName) throws Exception;
}
