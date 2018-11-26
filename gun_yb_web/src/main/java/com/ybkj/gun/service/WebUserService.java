package com.ybkj.gun.service;

import com.ybkj.common.entity.PermissionVo;
import com.ybkj.common.entity.WebUserDTO;
import com.ybkj.gun.model.WebUser;
import com.ybkj.model.BaseModel;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @项目名称：
 * @类名称：
 * @类描述：web用户接口
 * @创建人：liujiayi
 * @创建时间：2018/10/30 16:08
 * @修改时间：2018/10/30 16:08
 * @version：1.0
 */
public interface WebUserService {

    /**
     * @Description:  功能描述（采用 shiro 登录）
     * @Author:       刘家义
     * @CreateDate:   2018/10/31 19:27
    */
    BaseModel shiroLogin(WebUserDTO webUserDTO, boolean rememberMe, HttpServletRequest request, HttpServletResponse response) throws Exception;

    /**
     * @Description:  功能描述（根据用户名查询用户信息）
     * @Author:       刘家义
     * @CreateDate:   2018/11/1 15:02
    */
    WebUser findWebUserByUserName(String webUserName) throws Exception;

    /**
     * @Description:  功能描述（根据web用户Id,查询用户权限）
     * @Author:       刘家义
     * @CreateDate:   2018/11/25 12:22
    */
    List<PermissionVo> findPermissionByWebUser(Integer userId,Integer parentId) throws Exception;
}
