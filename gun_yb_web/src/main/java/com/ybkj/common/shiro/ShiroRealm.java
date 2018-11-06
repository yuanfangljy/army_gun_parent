package com.ybkj.common.shiro;

import com.ybkj.gun.mapper.MenuMapper;
import com.ybkj.gun.mapper.RoleMapper;
import com.ybkj.gun.mapper.WebUserMapper;
import com.ybkj.gun.model.Menu;
import com.ybkj.gun.model.Role;
import com.ybkj.gun.model.WebUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @项目名称：
 * @类名称：
 * @类描述：对登录用户进行认证和授权
 * @创建人：liujiayi
 * @创建时间：2018/10/31 15:44
 * @修改时间：2018/10/31 15:44
 * @version：1.0
 */
@Component
@Slf4j
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private WebUserMapper webUserMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private MenuMapper menuMapper;

    /**
     * @Description:  功能描述（权限验证）
     * @Author:       刘家义
     * @CreateDate:   2018/10/31 15:50
    */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //授权
        log.debug("授予角色和权限");
        // 添加权限 和 角色信息
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        // 获取当前登陆用户
        Subject subject = SecurityUtils.getSubject();
        WebUser user = (WebUser) subject.getPrincipal();
        if (user.getWebUserName().equals("远方科技")) {
            // 超级管理员，添加所有角色、添加所有权限
            authorizationInfo.addRole("*");
            authorizationInfo.addStringPermission("*");
            log.debug("【远方科技】-- 超级管理员授权成功");
        }else{
            // 普通用户，查询用户的角色，根据角色查询权限
            Integer userId = user.getId();
            List<Role> roles = this.roleMapper.selectRoleByUser(userId);
            if (null != roles && roles.size() > 0) {
                for (Role role : roles) {
                    authorizationInfo.addRole(role.getCode());
                    // 角色对应的权限数据
                    List<Menu> perms = this.menuMapper.selectMenusByRoleId(role
                            .getRid());
                    if (null != perms && perms.size() > 0) {
                        // 授权角色下所有权限
                        for (Menu perm : perms) {
                            authorizationInfo.addStringPermission(perm
                                    .getCode());
                        }
                    }
                }
            }
            log.debug('【'+user.getWebUserName()+'】'+"管理员授权成功");
        }
        return authorizationInfo;
    }

    /**
     * @Description:  功能描述（登陆验证）
     * @Author:       刘家义
     * @CreateDate:   2018/10/31 15:50
    */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        //UsernamePasswordToken用于存放提交的登录信息
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        log.info("用户登录认证：验证当前Subject时获取到token为：" + ReflectionToStringBuilder
                .toString(token, ToStringStyle.MULTI_LINE_STYLE));
        String userName = token.getUsername();
        //查询用户是否存在
        WebUser user= webUserMapper.selectWebUserByUserName(userName);
        log.debug("用户登录认证！用户信息user：" + user);
        if(null == user){
            log.debug("登录用户不存在！");
            return null;
        }else{
            // 密码存在
            // 第一个参数 ，登陆后，需要在session保存数据
            // 第二个参数，查询到密码(加密规则要和自定义的HashedCredentialsMatcher中的HashAlgorithmName散列算法一致)
            // 第三个参数 ，realm名字
            return new SimpleAuthenticationInfo(user, DigestUtils.md5Hex(user.getWebUserPassword()),getName());
        }
    }

    /**
     * 清除所有缓存【实测无效】
     */
    public void clearCachedAuth() {
        this.clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
    }
}
