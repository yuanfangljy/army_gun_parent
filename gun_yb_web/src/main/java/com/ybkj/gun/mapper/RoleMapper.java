package com.ybkj.gun.mapper;

import com.ybkj.common.entity.RoleMenuVO;
import com.ybkj.gun.model.Role;
import com.ybkj.gun.model.RoleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RoleMapper {
    long countByExample(RoleExample example);

    int deleteByExample(RoleExample example);

    int deleteByPrimaryKey(Integer rid);

    int insert(Role record);

    int insertSelective(Role record);

    List<Role> selectByExample(RoleExample example);

    Role selectByPrimaryKey(Integer rid);

    int updateByExampleSelective(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByExample(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    /**
     * @Description:  功能描述（根据用户id查询角色数据）
     * @Author:       刘家义
     * @CreateDate:   2018/10/19 15:26
     */
    List<Role> selectRoleByUser(Integer userId);

    /**
     * @Description:  功能描述（根据角色名称获取角色信息）
     * @Author:       刘家义
     * @CreateDate:   2018/10/22 17:18
     */
    Role selectRoleByRoleName(String roleName);

    /**
     * @Description:  功能描述（根据角色编码获取角色信息）
     * @Author:       刘家义
     * @CreateDate:   2018/10/22 17:28
     */
    Role selectRoleByRoleCode(String code);

    /**
     * @Description:  功能描述（查询所有的角色信息）
     * @Author:       刘家义
     * @CreateDate:   2018/10/22 20:30
     */
    List<Role> selectRoles();


    /**
     * @Description:  功能描述（根据角色id,获取角色和权限）
     * @Author:       刘家义
     * @CreateDate:   2018/10/22 20:54
     */
    RoleMenuVO selectRoleAndMenuByRoleId(Integer rid);
}