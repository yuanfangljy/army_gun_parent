package com.ybkj.gun.mapper;

import com.ybkj.common.entity.PermissionVo;
import com.ybkj.gun.model.WebUser;
import com.ybkj.gun.model.WebUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WebUserMapper {
    long countByExample(WebUserExample example);

    int deleteByExample(WebUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(WebUser record);

    int insertSelective(WebUser record);

    List<WebUser> selectByExample(WebUserExample example);

    WebUser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") WebUser record, @Param("example") WebUserExample example);

    int updateByExample(@Param("record") WebUser record, @Param("example") WebUserExample example);

    int updateByPrimaryKeySelective(WebUser record);

    int updateByPrimaryKey(WebUser record);

    /**
     * @Description:  功能描述（根据用户名查询用户信息）
     * @Author:       刘家义
     * @CreateDate:   2018/10/31 16:03
     */
    WebUser selectWebUserByUserName(@Param("userName") String userName);

    /**
     * @Description:  功能描述（根据web用户Id,查询用户权限:树）
     * @Author:       刘家义
     * @CreateDate:   2018/11/25 12:24
    */
    List<PermissionVo> selectPermissionByWebUser(@Param("userId") Integer userId,@Param("parentId") Integer parentId);

    /**
     * @Description:  功能描述（查询该节点是否存在父节点）
     * @Author:       刘家义
     * @CreateDate:   2018/11/26 14:43
    */
    List<PermissionVo> selectPermissionChildNode(Integer parentId);
}