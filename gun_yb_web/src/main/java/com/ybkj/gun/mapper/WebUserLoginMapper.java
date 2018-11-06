package com.ybkj.gun.mapper;

import com.ybkj.gun.model.WebUserLogin;
import com.ybkj.gun.model.WebUserLoginExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WebUserLoginMapper {
    long countByExample(WebUserLoginExample example);

    int deleteByExample(WebUserLoginExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(WebUserLogin record);

    int insertSelective(WebUserLogin record);

    List<WebUserLogin> selectByExample(WebUserLoginExample example);

    WebUserLogin selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") WebUserLogin record, @Param("example") WebUserLoginExample example);

    int updateByExample(@Param("record") WebUserLogin record, @Param("example") WebUserLoginExample example);

    int updateByPrimaryKeySelective(WebUserLogin record);

    int updateByPrimaryKey(WebUserLogin record);

    /**
     * @Description:  功能描述（根据用户名，查询用户登入出日志信息）
     * @Author:       刘家义
     * @CreateDate:   2018/11/2 17:48
     */
    List<WebUserLogin> selectWebUserLoginByUserName(@Param(value = "userName") String userName);
}