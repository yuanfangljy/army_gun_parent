package com.ybkj.common.filter;


import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.servlet4preview.GenericFilter;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @项目名称：
 * @类名称：
 * @类描述：
 * @创建人：liujiayi
 * @创建时间：2018/11/21 16:27
 * @修改时间：2018/11/21 16:27
 * @version：1.0
 */
@SuppressWarnings("all")
@Slf4j
@Component
//说明这是一个web过滤器，它拦截的url为/customFilter，过滤器名字为blogsTest
@WebFilter(filterName="sessionFilter",urlPatterns= {"/*"})
//@Order中的value越小，优先级越高。
@Order(value = 1)
public class SessionFilter extends GenericFilter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse resp = (HttpServletResponse)response;

        String origin = req.getHeader("Origin");
        if(origin == null) {
            origin = req.getHeader("Referer");
        }
        // 允许客户端携带跨域cookie，此时origin值不能为“*”，只能为指定单一域名
        resp.setHeader("Access-Control-Allow-Origin", origin);
        // 允许指定域访问跨域资源
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        //resp.setHeader("Access-Control-Allow-Headers", "X-Requested-With, accept, content-type, exception");
        //resp.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH");


        //System.out.println(RequestMethod.OPTIONS.toString()+"-----"+req.getMethod());
        //System.out.println("---------++++++=0000000000--------"+RequestMethod.OPTIONS.toString().equals(req.getMethod()));
        if(RequestMethod.OPTIONS.toString().equals(req.getMethod())) {
            String allowMethod = req.getHeader("Access-Control-Request-Method");
            String allowHeaders = req.getHeader("Access-Control-Request-Headers");
            resp.setHeader("Access-Control-Max-Age", "86400");            // 浏览器缓存预检请求结果时间,单位:秒
            resp.setHeader("Access-Control-Allow-Methods", allowMethod);  // 允许浏览器在预检请求成功之后发送的实际请求方法名
            resp.setHeader("Access-Control-Allow-Headers", allowHeaders); // 允许浏览器发送的请求消息头
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
