package com.ybkj.common.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ybkj.common.shiro.ShiroFilterUtils;
import com.ybkj.enums.IStatusMessage;
import com.ybkj.gun.model.WebUser;
import com.ybkj.model.BaseModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @项目名称：
 * @类名称：
 * @类描述：自定义过滤器：基于shiro
 * @创建人：liujiayi
 * @创建时间：2018/10/31 10:58
 * @修改时间：2018/10/31 10:58
 * @version：1.0
 * 1、判断用户有没有授权，就是用户有没有登录
 * 2、获取到每一个用户的subject中的信息，session
 * 3、登录了，就用userName做成key，保存到缓存中
 * 4、判断用户sessionId是否存在，如果存在就将用户踢出，默认是踢出前者
 *
 * AccessControlFilter
 */
@SuppressWarnings("all")
@Slf4j
public class KickoutSessionFilter extends FormAuthenticationFilter {

    //将对象转换成json数据
    private final static ObjectMapper objectMapper = new ObjectMapper();
    //踢出用户的地址
    private String kickoutUrl;
    // 踢出之前登录的/之后登录的用户 默认false踢出之前登录的用户
    private boolean kickoutAfter = false;
    // 同一个帐号最大会话数 默认1
    private int maxSession = 1;
    private SessionManager sessionManager;
    private Cache<String, Deque<Serializable>> cache;


    public void setKickoutUrl(String kickoutUrl) {
        this.kickoutUrl = kickoutUrl;
    }

    public void setKickoutAfter(boolean kickoutAfter) {
        this.kickoutAfter = kickoutAfter;
    }

    public void setMaxSession(int maxSession) {
        this.maxSession = maxSession;
    }

    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public void setCacheManager(CacheManager cache) {
        this.cache = cache.getCache("shiro-activeSessionCache");
    }



    /**
     * 表示是否允许访问
     *
     * @param servletRequest
     * @param servletResponse
     * @param o
     * @return
     * @throws Exception
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {

        boolean allowed=super.isAccessAllowed(request,response,mappedValue);
        if (!allowed) {
           //判断请求上是否是options请求
            String method=WebUtils.toHttp(request).getMethod();
            if("OPTIONS".equalsIgnoreCase(method.trim())){
                return true;
            }
        }

       // return this.isAccessAllowed(request, response, mappedValue);
        return allowed;
    }

    /**
     * 表示当访问拒绝时是否已经处理了
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {

        Subject subject = SecurityUtils.getSubject();
        log.debug("过滤器》》》》用户访问是否存在------"+subject.isAuthenticated());
        //Subject subject = getSubject(request, response);
        // 没有登录授权
        if (!subject.isAuthenticated()) {
            // 如果没有登录，直接进行之后的流程
            BaseModel baseModel = new BaseModel();
            //判断是不是Ajax请求，异步请求，直接响应返回未登录
            if (ShiroFilterUtils.isAjax(request) ) {
                log.debug(getClass().getName() + "当前用户已经在其他地方登录，并且是Ajax请求！");
                baseModel.setStatus(IStatusMessage.SystemStatus.MANY_LOGINS.getCode());
                baseModel.setErrorMessage("您已在别处登录，请您修改密码或重新登录");
                out(response, baseModel);
                return false;
            } else {
                return true;
            }
        }
        // 获得用户请求的URI
        HttpServletRequest reqUrl = (HttpServletRequest) request;
        String path = reqUrl.getRequestURI();
        log.debug("===当前请求的uri：==" + path);
        String contextPath = reqUrl.getContextPath();
        log.debug("===当前请求的域名或ip+端口：==" + contextPath);
        //放行登录
        if (path.equals("/toLogin")) {
            return true;
        }
        Session session = subject.getSession();
        log.debug("==session时间设置：" + String.valueOf(session.getTimeout()) + "====");
        try {
            //当前用户
            WebUser user = (WebUser) subject.getPrincipal();
            String userName = user.getWebUserName();
            log.debug("===当前用户username：==" + userName);
            Serializable sessionId = session.getId();
            log.debug("===当前用户sessionId：==" + sessionId);
            // 读取缓存用户 没有就存入
            Deque<Serializable> deque = cache.get(userName);
            log.debug("===当前deque：==" + deque);
            if (deque == null) {
                // 初始化队列
                deque = new ArrayDeque<Serializable>();
            }
            // 如果队列里没有此sessionId，且用户没有被踢出；放入队列
            if (!deque.contains(sessionId) && session.getAttribute("kickout") == null) {
                // 将sessionId存入队列
                deque.push(sessionId);
                // 将用户的sessionId队列缓存
                cache.put(userName, deque);
            }
            // 如果队列里的sessionId数超出最大会话数，开始踢人
            while (deque.size() > maxSession) {
                log.debug("===deque队列长度：==" + deque.size());
                Serializable kickoutSessionId = null;
                // 是否踢出后来登录的，默认是false；即后者登录的用户踢出前者登录的用户；
                if (kickoutAfter) {// 如果踢出后者
                    kickoutSessionId = deque.removeFirst();
                } else {//如果踢出前者
                    kickoutSessionId = deque.removeLast();
                }
                // 踢出后再更新下缓存队列
                cache.put(userName, deque);
                try {
                    // 获取被踢出的sessionId的session对象
                    Session kickoutSession = sessionManager.getSession(new DefaultSessionKey(kickoutSessionId));
                    if(kickoutSession != null){
                        // 设置会话的kickout属性表示踢出了
                        kickoutSession.setAttribute("kickout", true);
                    }
                } catch (Exception e) {
                }
            }
            // 如果被踢出了，(前者或后者)直接退出，重定向到踢出后的地址
            if ((Boolean) session.getAttribute("kickout") != null
                    && (Boolean) session.getAttribute("kickout") == true) {
                // 会话被踢出了
                try {
                    // 退出登录
                    subject.logout();
                } catch (Exception e) { // ignore
                }
                saveRequest(request);
                log.debug("==踢出后用户重定向的路径kickoutUrl:" + kickoutUrl);
                return isAjaxResponse(request, response);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("控制用户在线数量【lyd-admin-->KickoutSessionFilter.onAccessDenied】异常！", e);
            return isAjaxResponse(request, response);
        }
    }




    /**
     * @param response
     * @param result
     * @描述：response输出json
     * @创建人：liujiayi
     * @创建时间：2018年10月31日 下午5:14:22
     */
    public static void out(ServletResponse response, BaseModel result) {
        PrintWriter out = null;
        try {
            response.setCharacterEncoding("UTF-8");//设置编码
            response.setContentType("application/json");//设置返回类型
            out = response.getWriter();
            out.println(objectMapper.writeValueAsString(result));//输出
            log.error("用户在线数量限制【army-manager-->KickoutSessionFilter.out】响应json信息成功");
        } catch (Exception e) {
            log.error("用户在线数量限制【army-manager-->KickoutSessionFilter.out】响应json信息出错", e);
        } finally {
            if (null != out) {
                out.flush();
                out.close();
            }
        }
    }

    private boolean isAjaxResponse(ServletRequest request,
                                   ServletResponse response) throws IOException {
        // ajax请求
        /**
         * 判断是否已经踢出
         * 1.如果是Ajax 访问，那么给予json返回值提示。
         * 2.如果是普通请求，直接跳转到登录页
         */
        //判断是不是Ajax请求
        BaseModel responseResult = new BaseModel();
        if (ShiroFilterUtils.isAjax(request)) {
            log.debug(getClass().getName() + "当前用户已经在其他地方登录，并且是Ajax请求！");
            responseResult.setStatus(IStatusMessage.SystemStatus.MANY_LOGINS.getCode());
            responseResult.setErrorMessage("您已在别处登录，请您修改密码或重新登录");
            out(response, responseResult);
        } else {
            // 重定向
            WebUtils.issueRedirect(request, response, kickoutUrl);
        }
        return false;
    }
}
