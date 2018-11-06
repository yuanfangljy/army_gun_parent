package com.ybkj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @项目名称：
 * @类名称：
 * @类描述：
 * @创建人：liujiayi
 * @创建时间：2018/10/12 17:07
 * @修改时间：2018/10/12 17:07
 * @version：1.0
 */

@SpringBootApplication
@MapperScan(basePackages = {"com.ybkj.gun.mapper"})
@EnableSwagger2
public class App {

    public static void main(String[] args) {
        SpringApplication  sa=new SpringApplication(App.class);
        sa.run(args);
    }
}

