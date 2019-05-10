package com.ybkj.gun.service.impl;

import com.ybkj.gun.mapper.WebUserLoginMapper;
import com.ybkj.gun.model.WebUserLogin;
import com.ybkj.gun.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @项目名称：
 * @类名称：
 * @类描述：
 * @创建人：liujiayi
 * @创建时间：2018/11/2 17:11
 * @修改时间：2018/11/2 17:11
 * @version：1.0
 */
@Service
@Transactional
@Slf4j
@SuppressWarnings("all")
public class LogServiceImpl implements LogService{

    @Autowired
    private WebUserLoginMapper webUserLoginMapper;
    /**
     * @Description:  功能描述（创建用户登录登出日志）
     * @Author:       刘家义
     * @CreateDate:   2018/11/2 17:11
    */
    @Override
    public int addLogLoginLogOut(WebUserLogin webUserLogin) throws Exception {
        return webUserLoginMapper.insertSelective(webUserLogin);
    }

    /**
     * @Description:  功能描述（根据用户名，查询用户登入出日志信息）
     * @Author:       刘家义
     * @CreateDate:   2018/11/2 17:46
     */
    @Override
    public List<WebUserLogin> findWebUserLogins(String userName) throws Exception {
        return webUserLoginMapper.selectWebUserLoginByUserName(userName);
    }
}
