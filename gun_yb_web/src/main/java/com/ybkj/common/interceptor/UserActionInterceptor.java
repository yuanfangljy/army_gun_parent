package com.ybkj.common.interceptor;

import com.ybkj.common.shiro.ShiroFilterUtils;
import com.ybkj.enums.IStatusMessage;
import com.ybkj.gun.mapper.WebUserMapper;
import com.ybkj.gun.model.WebUser;
import com.ybkj.gun.service.WebUserService;
import com.ybkj.model.BaseModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @项目名称：
 * @类名称：UserActionInterceptor
 * @类描述：用于判断用户是否被后台所更好
 * @创建人：liujiayi
 * @创建时间：2018/11/1 14:48
 * @修改时间：2018/11/1 14:48
 * @version：1.0
 */
@SuppressWarnings("all")
@Slf4j
public class UserActionInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private WebUserService webUserService;
    @Autowired
    private WebUserMapper userMapper;

    private final String kickoutUrl = "/toLogin"; // 退出后重定向的地址




    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object object) throws Exception {
        // TODO Auto-generated method stub
        log.debug("请求到达后台方法之前调用（controller之前）");
        try {
            // 1. SecurityUtils获取session中的用户信息
            WebUser user = (WebUser) SecurityUtils.getSubject().getPrincipal();
            if (null != user && StringUtils.isNotEmpty(user.getWebUserName()) && null != user.getVersion()) {
                // 2. 获取数据库中的用户数据
                WebUser dataUser = webUserService.findWebUserByUserName(user.getWebUserName());
                // 3. 对比session中用户的version和数据库中的是否一致
                if (null != dataUser.getVersion() && null != dataUser && String.valueOf(dataUser.getVersion()).equals(String.valueOf(user.getVersion()))){
                    // 3.1 一样，放行
                    return true;
                }else{
                    // 3.2 不一样，这里统一做退出登录处理；
                    // TODO 使用redis缓存用户权限数据，根据用户更新、用户权限更新；做对应的处理。
                    SecurityUtils.getSubject().logout();
                    isAjaxResponse(req, resp);
                }
            }
        }catch (Exception e){
            isOnline();
            log.error("系统维护中......（用户页面缓存存在，需要用户重新登录）");
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        // TODO Auto-generated method stub
        log.debug("请求处理之后调用；在视图渲染之前，controller处理之后。");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        // TODO Auto-generated method stub
        log.debug("整个请求完成之后调用。DispatcherServlet视图渲染完成之后。（主要是用于进行资源清理工作）");
    }

    private boolean isAjaxResponse(HttpServletRequest request,
                                   HttpServletResponse response) throws IOException {
        // ajax请求
        /**
         * 判断是否已经踢出
         * 1.如果是Ajax 访问，那么给予json返回值提示。
         * 2.如果是普通请求，直接跳转到登录页
         */
        //判断是不是Ajax请求
        BaseModel baseModel = new BaseModel();
        if (ShiroFilterUtils.isAjax(request)) {
            log.debug(getClass().getName() + "，当前用户的信息或权限已变更，重新登录后生效！");
            baseModel.setStatus(IStatusMessage.SystemStatus.UPDATE.getCode());
            baseModel.setErrorMessage("您的信息或权限已变更，重新登录后生效");
            ShiroFilterUtils.out(response, baseModel);
        } else {
            // 重定向
            WebUtils.issueRedirect(request, response, kickoutUrl);
        }
        return false;
    }

    //判断用户在线，否抛出异常
    public BaseModel isOnline(){
        BaseModel baseModel=new BaseModel();
        baseModel.setStatus(IStatusMessage.SystemStatus.UPDATE.getCode());
        baseModel.setErrorMessage("系统维护中......");
        return baseModel;
    }

}
