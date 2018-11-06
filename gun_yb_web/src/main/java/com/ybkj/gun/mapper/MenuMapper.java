package com.ybkj.gun.mapper;

import com.ybkj.gun.model.Menu;
import com.ybkj.gun.model.MenuExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MenuMapper {
    long countByExample(MenuExample example);

    int deleteByExample(MenuExample example);

    int deleteByPrimaryKey(Integer mid);

    int insert(Menu record);

    int insertSelective(Menu record);

    List<Menu> selectByExample(MenuExample example);

    Menu selectByPrimaryKey(Integer mid);

    int updateByExampleSelective(@Param("record") Menu record, @Param("example") MenuExample example);

    int updateByExample(@Param("record") Menu record, @Param("example") MenuExample example);

    int updateByPrimaryKeySelective(Menu record);

    int updateByPrimaryKey(Menu record);

    /**
     * @Description:  功能描述（根据角色ID查找权限树列表）
     * @Author:       刘家义
     * @CreateDate:   2018/10/19 15:28
     */
    List<Menu> selectMenusByRoleId(Integer roleId);

    /**
     * @Description:  功能描述（查询所有的菜单）
     * @Author:       刘家义
     * @CreateDate:   2018/10/21 18:49
     */
    List<Menu> selectMenus();
}