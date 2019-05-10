package com.ybkj.gun.service;

import com.ybkj.gun.model.Menu;
import com.ybkj.model.BaseModel;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @项目名称：
 * @类名称：
 * @类描述：
 * @创建人：liujiayi
 * @创建时间：2018/10/16 15:24
 * @修改时间：2018/10/16 15:24
 * @version：1.0
 */
@Service
@SuppressWarnings("all")
public interface MenuService {

    /**
     * @Description:  功能描述（根据角色获取菜单资源）
     * @Author:       刘家义
     * @CreateDate:   2018/10/16 15:40
    */
    public List<Menu> findMenusByRoleId(Integer rid) throws Exception;

    /**
     * @Description:  功能描述（开通新权限菜单）
     * @Author:       刘家义
     * @CreateDate:   2018/10/21 9:10
    */
    BaseModel addMeun(Menu menu) throws Exception;

    /**
     * @Description:  功能描述（根据菜单id删除菜单信息）
     * @Author:       刘家义
     * @CreateDate:   2018/10/21 15:06
    */
    BaseModel removeMenu(Integer mid) throws Exception;

    /**
     * @Description:  功能描述（修改菜单的信息）
     * @Author:       刘家义
     * @CreateDate:   2018/10/21 17:38
    */
    BaseModel revampMenu(Menu menu) throws Exception;

    /**
     * @Description:  功能描述（根据ID查询菜单信息）
     * @Author:       刘家义
     * @CreateDate:   2018/10/21 18:20
    */
    BaseModel findMenuById(Integer mid) throws Exception;

    /**
     * @Description:  功能描述（查询全部菜单）
     * @Author:       刘家义
     * @CreateDate:   2018/10/21 18:46
    */
    List<Menu> findMenus() throws Exception;
}
