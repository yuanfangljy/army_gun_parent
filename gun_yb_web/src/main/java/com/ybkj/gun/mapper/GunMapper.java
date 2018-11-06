package com.ybkj.gun.mapper;

import com.ybkj.gun.model.Gun;
import com.ybkj.gun.model.GunExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GunMapper {
    long countByExample(GunExample example);

    int deleteByExample(GunExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Gun record);

    int insertSelective(Gun record);

    List<Gun> selectByExample(GunExample example);

    Gun selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Gun record, @Param("example") GunExample example);

    int updateByExample(@Param("record") Gun record, @Param("example") GunExample example);

    int updateByPrimaryKeySelective(Gun record);

    int updateByPrimaryKey(Gun record);

    /**
     * @Description:  功能描述（根据枪支编号，查询枪支信息）
     * @Author:       刘家义
     * @CreateDate:   2018/11/3 13:58
     */
    Gun selectGunByGunCode(String gunId);
    /**
     * @Description:  功能描述（根据枪支蓝牙号，查询枪支信息）
     * @Author:       刘家义
     * @CreateDate:   2018/11/3 13:58
     */
    Gun selectGunByName(String bluetoothMac);
    /**
     * @Description:  功能描述（查询枪支信息列表）
     * @Author:       刘家义
     * @CreateDate:   2018/11/3 13:58
     */
    List<Gun> selectGuns();
}