package com.ybkj.gun.service;

import com.ybkj.gun.model.Menu;
import com.ybkj.gun.model.Role;
import com.ybkj.model.BaseModel;

import java.util.List;

/**
 * @项目名称：
 * @类名称：
 * @类描述：角色接口
 * @创建人：liujiayi
 * @创建时间：2018/10/31 20:17
 * @修改时间：2018/10/31 20:17
 * @version：1.0
 */
public interface RoleService {

    /**
     * @Description:  功能描述（获取用户的角色）
     * @Author:       刘家义
     * @CreateDate:   2018/10/16 15:37
     */
    public List<Role> findRoleByUser(Integer uid) throws Exception;

    /**
     * @Description:  功能描述（新增角色并授权）
     * @Author:       刘家义
     * @CreateDate:   2018/10/22 17:03
     */
    BaseModel addRole(String mids, Role role) throws Exception;

    /**
     * @Description:  功能描述（根据角色id，删除角色信息）
     * @Author:       刘家义
     * @CreateDate:   2018/10/22 19:30
     */
    BaseModel removeRole(Integer rid) throws Exception;

    /**
     * @Description:  功能描述（更新角色以及权限）
     * @Author:       刘家义
     * @CreateDate:   2018/10/22 19:51
     */
    BaseModel revampRole(Role role, String rmIds) throws Exception;

    /**
     * @Description:  功能描述（查询所有角色信息）
     * @Author:       刘家义
     * @CreateDate:   2018/10/22 20:26
     */
    List<Role> getRoles() throws Exception;

    /**
     * @Description:  功能描述（根据角色id,获取角色和权限）
     * @Author:       刘家义
     * @CreateDate:   2018/10/22 20:47
     */
    BaseModel getRoleMenu(Integer rid) throws Exception;
}
