package com.ybkj.gun.service.impl;

import com.ybkj.common.entity.WebUserDTO;
import com.ybkj.enums.IStatusMessage;
import com.ybkj.gun.mapper.WebUserMapper;
import com.ybkj.gun.model.WebUser;
import com.ybkj.gun.service.WebUserService;
import com.ybkj.model.BaseModel;
import com.ybkj.untils.ValidatorRequestParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.BadBinaryOpValueExpException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @项目名称：
 * @类名称：
 * @类描述：web用户功能实现业务逻辑
 * @创建人：liujiayi
 * @创建时间：2018/10/30 16:43
 * @修改时间：2018/10/30 16:43
 * @version：1.0
 */
@Service
@Transactional
@Slf4j
public class WebUserServiceImpl implements WebUserService{

    @Autowired
    private WebUserMapper userMapper;
    @Autowired
    private EhCacheManager ecm;

     /**
     * @param user
     * @return
     * @throws Exception
     * @Description: 功能描述（用户登录实现）
     * @Author: 刘家义
     * @CreateDate: 2018/10/16 9:53
     */

    @Override
    public BaseModel shiroLogin(WebUserDTO user,boolean rememberMe) throws Exception {
        log.debug("用户登录，请求参数=user:" + user);
        BaseModel baseModel = new BaseModel();
        baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
        //1、判断user是否为空
        if (null == user) {
            baseModel.setStatus(IStatusMessage.SystemStatus.PARAM_ERROR
                    .getCode());
            baseModel.setErrorMessage("请求参数有误，请您稍后再试");
            log.debug("用户登录，结果=responseResult:" + baseModel);
            return baseModel;
        }
        //2、后台校验输入数据
        if (!ValidatorRequestParam.validatorRequestParam(user, baseModel)) {
            log.debug("用户登录，结果=responseResult:" + baseModel);
            return baseModel;
        }
        //3、判断用户是否存在
        WebUser existUser = userMapper.selectWebUserByUserName(user.getWebUserName());
        if (null == existUser) {
            baseModel.setErrorMessage("该用户不存在，请您联系管理员");
            log.debug("用户登录，结果=responseResult:" + baseModel);
            return baseModel;
        } else {
            //4、判断用户是否离职
            if (existUser.getIsJob()) {//1:true
                baseModel.setErrorMessage("登录用户已离职，请您联系管理员");
                log.debug("用户登录，结果=responseResult:" + baseModel);
                return baseModel;
            }
        }
        //5、用户登录：使用shiro
        try {
            // 1、 封装用户名、密码、是否记住我到token令牌对象 [支持记住我]
            AuthenticationToken token = new UsernamePasswordToken(
                    user.getWebUserName(), DigestUtils.md5Hex(user.getWebUserPassword()),rememberMe);
            // 2、 Subject调用login
            Subject subject = SecurityUtils.getSubject();
            // 在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
            // 每个Realm都能在必要时对提交的AuthenticationTokens作出反应
            // 所以这一步在调用login(token)方法时,它会走到MyRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法
            log.debug("用户登录，用户验证开始！user=" + user.getWebUserName());
            subject.login(token);
            baseModel.setStatus(IStatusMessage.SystemStatus.SUCCESS.getCode());
            baseModel.setErrorMessage("用户登录成功");
            log.info("用户登录，用户验证通过！user=" + user.getWebUserName());
        } catch (UnknownAccountException uae) {
            log.error("用户登录，用户验证未通过：未知用户！user=" + user.getWebUserName(), uae);
            baseModel.setErrorMessage("该用户不存在，请您联系管理员");
        } catch (IncorrectCredentialsException ice) {
            // 获取输错次数
            log.error("用户登录，用户验证未通过：错误的凭证，密码输入错误！user=" + user.getWebUserName(),
                    ice);
            baseModel.setErrorMessage("用户名或密码不正确");
        } catch (LockedAccountException lae) {
            log.error("用户登录，用户验证未通过：账户已锁定！user=" + user.getWebUserName(), lae);
            baseModel.setErrorMessage("账户已锁定");
        } catch (ExcessiveAttemptsException eae) {
            log.error(
                    "用户登录，用户验证未通过：错误次数大于5次,账户已锁定！user=.getMobile()" + user, eae);
            baseModel
                    .setErrorMessage("用户名或密码错误次数大于5次,账户已锁定!</br><span style='color:red;font-weight:bold; '>2分钟后可再次登录，或联系管理员解锁</span>");
            // 这里结合了，另一种密码输错限制的实现，基于redis或mysql的实现；也可以直接使用RetryLimitHashedCredentialsMatcher限制5次
        } /*catch (DisabledAccountException sae){
                     logger.error("用户登录，用户验证未通过：帐号已经禁止登录！user=" +
		 user.getMobile(),sae);
		 responseResult.setCode(IStatusMessage.SystemStatus.ERROR.getCode());
		 responseResult.setMessage("帐号已经禁止登录");
		}*/ catch (AuthenticationException ae) {
            // 通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
            log.error("用户登录，用户验证未通过：认证异常，异常信息如下！user=" + user.getWebUserName(),
                    ae);
            baseModel.setErrorMessage("用户名或密码不正确");
        } catch (Exception e) {
            log.error("用户登录，用户验证未通过：操作异常，异常信息如下！user=" + user.getWebUserName(), e);
            baseModel.setErrorMessage("用户登录失败，请您稍后再试");
        }
        Cache<String, AtomicInteger> passwordRetryCache = ecm.getCache("passwordRetryCache");
        if (null != passwordRetryCache) {
            int retryNum = (passwordRetryCache.get(existUser.getWebUserName()) == null ? 0 : passwordRetryCache.get(existUser.getWebUserName())).intValue();
            log.debug("输错次数：" + retryNum);
            if (retryNum > 0 && retryNum < 6) {
                baseModel.setErrorMessage("用户名或密码错误" + retryNum + "次,再输错"
                        + (6 - retryNum) + "次账号将锁定");
            }
        }
        log.debug("用户登录，user=" + user.getWebUserName() + ",登录结果=responseResult:"
                + baseModel);
        return baseModel;
    }

    /**
     * @Description:  功能描述（根据用户名查询用户信息）
     * @Author:       刘家义
     * @CreateDate:   2018/11/1 15:03
    */
    @Override
    public WebUser findWebUserByUserName(String webUserName) {

        return userMapper.selectWebUserByUserName(webUserName);
    }
}
