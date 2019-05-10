package com.ybkj.gun.mapper;

import com.ybkj.gun.model.Mission;
import com.ybkj.gun.model.MissionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MissionMapper {
    long countByExample(MissionExample example);

    int deleteByExample(MissionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Mission record);

    int insertSelective(Mission record);

    List<Mission> selectByExample(MissionExample example);

    Mission selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Mission record, @Param("example") MissionExample example);

    int updateByExample(@Param("record") Mission record, @Param("example") MissionExample example);

    int updateByPrimaryKeySelective(Mission record);

    int updateByPrimaryKey(Mission record);


    /**
     * @Description:  功能描述（查询协助查找信息）
     * @Author:       刘家义
     * @CreateDate:   2018/11/23 9:37
     */
    List<Mission> selectMissions(@Param(value = "gunMac") String gunMac);

    /**
     * @Description:  功能描述（通过appImei,在mission中查询状态不为0的信息）
     * @Author:       刘家义
     * @CreateDate:   2018/11/23 10:18
     */
    List<Mission> selectMissionsByAppImei(@Param(value = "appImei") String appImei);
}