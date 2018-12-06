package com.ybkj.common.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.*;

/**
 * 
 * 注册拦截器 
 * Created by SYSTEM on 2017/8/16. 
 */
@Configuration
@Slf4j
public class WebAppConfig extends WebMvcConfigurerAdapter {




    /**
     *
     * @描述：在Spring添加拦截器之前先创建拦截器对象，这样就能在Spring映射这个拦截器前，把拦截器中的依赖注入的对象给初始化完成了。
     * </br>避免拦截器中注入的对象为null问题。
     * @创建人：wyait
     * @创建时间：2018年5月3日 上午10:07:36
     * @return
     */
    @Bean
    public UserActionInterceptor userActionInterceptor(){
        return new UserActionInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userActionInterceptor()).excludePathPatterns("/webUser/*").excludePathPatterns("/swagger-ui.html");
        super.addInterceptors(registry);
    }


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*","http://127.0.0.1:8080/")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PUT")
                .maxAge(3600);
    }

    /**
     * 配置静态资源
     */
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /*registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/templates/**").addResourceLocations("classpath:/templates/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        */
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        super.addResourceHandlers(registry);
    }


}