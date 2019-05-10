package com.ybkj.gun.mapper;

import com.ybkj.common.entity.GunLocationVO;
import com.ybkj.gun.model.GunLocation;
import com.ybkj.gun.model.GunLocationExample;

import java.util.Date;
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
    List<GunLocationVO> selectGunDynamic(@Param(value = "gunId") String gunId, @Param(value = "appName")String appName);

    /**
     * @Description:  功能描述（查询周围的在线设备）
     * @Author:       刘家义
     * @CreateDate:   2018/11/22 19:51
     */
    List<GunLocation> selectRoundDevice(@Param(value = "lng")String lng, @Param(value = "lat")String lat,@Param(value = "gunMac")String gunMac);

    /**
     * @Description:  功能描述（查询枪支的轨迹）
     * @Author:       刘家义
     * @CreateDate:   2018/11/26 19:51
     */
    List<GunLocation> selectGunTrajectory(@Param(value = "imei")String imei, @Param(value = "imeiType")Integer imeiType, @Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * @Description:  功能描述（根据枪支Id和时间排序获取到最新的数据 limit 1）
     * @Author:       刘家义
     * @CreateDate:   2018/12/5 12:18
     */
    GunLocation selectGunLocationByGunIdNewest(@Param(value = "imei")String imei);

    /**
     * @Description:  功能描述（根据Imei和Imei的类型查询，最新的数据）
     * @Author:       刘家义
     * @CreateDate:   2018/12/9 16:09
     */
    GunLocation selectGunLocationByImeiAndType(@Param(value = "imei")String imei, @Param(value = "imeiType") Integer imeiType);
}