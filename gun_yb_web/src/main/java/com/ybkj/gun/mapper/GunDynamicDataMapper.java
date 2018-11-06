package com.ybkj.gun.mapper;

import com.ybkj.gun.model.GunDynamicData;
import com.ybkj.gun.model.GunDynamicDataExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GunDynamicDataMapper {
    long countByExample(GunDynamicDataExample example);

    int deleteByExample(GunDynamicDataExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(GunDynamicData record);

    int insertSelective(GunDynamicData record);

    List<GunDynamicData> selectByExample(GunDynamicDataExample example);

    GunDynamicData selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") GunDynamicData record, @Param("example") GunDynamicDataExample example);

    int updateByExample(@Param("record") GunDynamicData record, @Param("example") GunDynamicDataExample example);

    int updateByPrimaryKeySelective(GunDynamicData record);

    int updateByPrimaryKey(GunDynamicData record);

    /**
     * @Description:  功能描述（根据枪支编号查询枪支动态信息）
     * @Author:       刘家义
     * @CreateDate:   2018/11/4 16:41
     */
    GunDynamicData selectAppDynamicByGunId(Integer gunId);

    /**
     * @Description:  功能描述（根据枪支编号，查询枪支动态数据列表）
     * @Author:       刘家义
     * @CreateDate:   2018/11/4 16:41
     */
    List<GunDynamicData> selectGunDynamicDataAll(@Param(value = "gunId") String gunId);
}