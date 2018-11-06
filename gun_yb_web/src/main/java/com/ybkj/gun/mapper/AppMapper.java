package com.ybkj.gun.mapper;

import com.ybkj.gun.model.App;
import com.ybkj.gun.model.AppExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AppMapper {
    long countByExample(AppExample example);

    int deleteByExample(AppExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(App record);

    int insertSelective(App record);

    List<App> selectByExample(AppExample example);

    App selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") App record, @Param("example") AppExample example);

    int updateByExample(@Param("record") App record, @Param("example") AppExample example);

    int updateByPrimaryKeySelective(App record);

    int updateByPrimaryKey(App record);

    /**
     * @Description:  功能描述（根据腕表/手机名称，查询腕表/手机信息）
     * @Author:       刘家义
     * @CreateDate:   2018/11/1 16:21
     */
    App selectAppByName(String appName);

    /**
     * @Description:  功能描述（查询所有的设备信息）
     * @Author:       刘家义
     * @CreateDate:   2018/11/2 15:04
     */
    List<App> selectApps();
}