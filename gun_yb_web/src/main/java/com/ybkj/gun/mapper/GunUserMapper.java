package com.ybkj.gun.mapper;

import com.ybkj.gun.model.GunUser;
import com.ybkj.gun.model.GunUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GunUserMapper {
    long countByExample(GunUserExample example);

    int deleteByExample(GunUserExample example);

    int deleteByPrimaryKey(Integer userId);

    int insert(GunUser record);

    int insertSelective(GunUser record);

    List<GunUser> selectByExample(GunUserExample example);

    GunUser selectByPrimaryKey(Integer userId);

    int updateByExampleSelective(@Param("record") GunUser record, @Param("example") GunUserExample example);

    int updateByExample(@Param("record") GunUser record, @Param("example") GunUserExample example);

    int updateByPrimaryKeySelective(GunUser record);

    int updateByPrimaryKey(GunUser record);

    /**
     * @Description:  功能描述（根据警员Id,查询警员信息）
     * @Author:       刘家义
     * @CreateDate:   2018/11/5 19:47
     */
    GunUser selectGunUserByUserId(Integer userId);
    /**
     * @Description:  功能描述（根据警员电话号码,查询警员信息）
     * @Author:       刘家义
     * @CreateDate:   2018/11/5 19:47
     */
    GunUser selectGunUserByPhone(String userPhone);

    /**
     * @Description:  功能描述（查询警员信息列表）
     * @Author:       刘家义
     * @CreateDate:   2018/11/5 19:47
     */
    List<GunUser> selectGunUserAll();


    /**
     * @Description:  功能描述（获取没有和腕表绑定的用户）
     * @Author:       刘家义
     * @CreateDate:   2018/11/13 9:55
     */
    List<GunUser> selectGunUserNoBinding();
}