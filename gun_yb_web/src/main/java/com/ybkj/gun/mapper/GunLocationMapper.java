package com.ybkj.gun.mapper;

import com.ybkj.gun.model.GunLocation;
import com.ybkj.gun.model.GunLocationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GunLocationMapper {
    long countByExample(GunLocationExample example);

    int deleteByExample(GunLocationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(GunLocation record);

    int insertSelective(GunLocation record);

    List<GunLocation> selectByExample(GunLocationExample example);

    GunLocation selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") GunLocation record, @Param("example") GunLocationExample example);

    int updateByExample(@Param("record") GunLocation record, @Param("example") GunLocationExample example);

    int updateByPrimaryKeySelective(GunLocation record);

    int updateByPrimaryKey(GunLocation record);

    /**
     * @Description:  功能描述（查询枪支实时动态位置信息）
     * @Author:       刘家义
     * @CreateDate:   2018/11/12 14:23
    */
    List<GunLocation> selectGunDynamic(@Param(value = "gunId") String gunId, @Param(value = "appName")String appName);
}