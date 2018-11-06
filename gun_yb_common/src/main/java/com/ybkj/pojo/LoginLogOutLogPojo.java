package com.ybkj.pojo;

import lombok.Data;

/**
 * @项目名称：
 * @类名称：
 * @类描述：用户登录，登出日志实体类
 * @创建人：liujiayi
 * @创建时间：2018/11/2 17:03
 * @修改时间：2018/11/2 17:03
 * @version：1.0
 */
@Data
@SuppressWarnings("all")
public class LoginLogOutLogPojo {

    private String ip;
    private String systemName;
    private String browser;
}
