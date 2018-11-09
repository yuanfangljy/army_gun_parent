package com.ybkj.gun.mapper;

import com.ybkj.gun.model.AppGun;
import com.ybkj.gun.model.AppGunExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AppGunMapper {
    long countByExample(AppGunExample example);

    int deleteByExample(AppGunExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AppGun record);

    int insertSelective(AppGun record);

    List<AppGun> selectByExample(AppGunExample example);

    AppGun selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AppGun record, @Param("example") AppGunExample example);

    int updateByExample(@Param("record") AppGun record, @Param("example") AppGunExample example);

    int updateByPrimaryKeySelective(AppGun record);

    int updateByPrimaryKey(AppGun record);



}