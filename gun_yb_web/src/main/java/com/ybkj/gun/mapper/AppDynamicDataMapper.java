package com.ybkj.gun.mapper;

import com.ybkj.gun.model.AppDynamicData;
import com.ybkj.gun.model.AppDynamicDataExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AppDynamicDataMapper {
    long countByExample(AppDynamicDataExample example);

    int deleteByExample(AppDynamicDataExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AppDynamicData record);

    int insertSelective(AppDynamicData record);

    List<AppDynamicData> selectByExample(AppDynamicDataExample example);

    AppDynamicData selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AppDynamicData record, @Param("example") AppDynamicDataExample example);

    int updateByExample(@Param("record") AppDynamicData record, @Param("example") AppDynamicDataExample example);

    int updateByPrimaryKeySelective(AppDynamicData record);

    int updateByPrimaryKey(AppDynamicData record);


    /**
     * @Description:  功能描述（根据appId，查询app动态数据）
     * @Author:       刘家义
     * @CreateDate:   2018/11/4 15:27
     */
    AppDynamicData selectAppDynamicByAppId(Integer appId);

    /**
     * @Description:  功能描述（根据app的名称，查询所有的app动态数据）
     * @Author:       刘家义
     * @CreateDate:   2018/11/4 15:27
     */
    List<AppDynamicData> selectAppDynamicDataAll(@Param(value = "appName") String appName);
}