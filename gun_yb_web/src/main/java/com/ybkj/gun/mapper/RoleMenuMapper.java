package com.ybkj.gun.mapper;

import com.ybkj.gun.model.RoleMenu;
import com.ybkj.gun.model.RoleMenuExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RoleMenuMapper {
    long countByExample(RoleMenuExample example);

    int deleteByExample(RoleMenuExample example);

    int deleteByPrimaryKey(Integer mrid);

    int insert(RoleMenu record);

    int insertSelective(RoleMenu record);

    List<RoleMenu> selectByExample(RoleMenuExample example);

    RoleMenu selectByPrimaryKey(Integer mrid);

    int updateByExampleSelective(@Param("record") RoleMenu record, @Param("example") RoleMenuExample example);

    int updateByExample(@Param("record") RoleMenu record, @Param("example") RoleMenuExample example);

    int updateByPrimaryKeySelective(RoleMenu record);

    int updateByPrimaryKey(RoleMenu record);


    /**
     * @Description:  功能描述（根据roleid查询所有的中间表中角色）
     * @Author:       刘家义
     * @CreateDate:   2018/10/22 19:40
     */
    List<RoleMenu> findByRole(int roleId);

    /**
     * @Description:  功能描述（根据roleid删除中间表角色的权限）
     * @Author:       刘家义
     * @CreateDate:   2018/11/1 9:30
     */
    int deleteRoleMenuByRid(Integer rid);
}