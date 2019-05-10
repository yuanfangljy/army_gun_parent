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



    /**
     * @Description:  功能描述（状态1/2的记录是否存在）
     * @Author:       刘家义
     * @CreateDate:   2018/11/12 16:14
     */
    AppGun selectAppGunByAppGunState(@Param(value = "gId") String gId, @Param(value = "appId") Integer appId, @Param(value = "state") Integer state);

    /**
     * @Description:  功能描述（查询所有绑定的枪支的设备）
     * @Author:       刘家义
     * @CreateDate:   2018/11/12 19:11
     */
    List<AppGun> selectAppGunBinding(@Param(value = "state") Integer state);

    /**
     * @Description:  功能描述（根据app_id,查询app_gun所有状态为 1 的相关信息）
     * @Author:       刘家义
     * @CreateDate:   2018/11/12 20:00
     */
    List<AppGun> selectAppGunByAppIdAndState(@Param(value = "appId") String appId, @Param(value = "state")Integer state);


    /**
     * @Description:  功能描述（根据状态查询对应的数据）
     * @Author:       刘家义
     * @CreateDate:   2018/11/12 20:00
     */
    List<AppGun> selectAppGunState(@Param(value = "state")Integer state);

    /**
     * @Description:  功能描述（查询所有状态，之外的状态数据）
     * @Author:       刘家义
     * @CreateDate:   2018/12/6 9:45
    */
    List<AppGun> selectGunUserNoState(@Param(value = "state")Integer state);

    /**
     * @Description:  功能描述（查询离位的枪支）
     * @Author:       刘家义
     * @CreateDate:   2018/12/10 16:54
     */
    List<AppGun> selectOffNormalGun(@Param(value = "gunId")String gunId);


    /**
     * @Description:  功能描述（查询枪支离位数）
     * @Author:       刘家义
     * @CreateDate:   2018/12/11 13:52
     */
    Integer selectOffNormalGunCount(@Param(value = "state")Integer state);


    /**
     * @Description:  功能描述（查询在线设备）
     * @Author:       刘家义
     * @CreateDate:   2018/12/10 17:20
    */
    List<AppGun> selectOnLineApp(@Param(value = "appName")String appName);

    /**
     * @Description:  功能描述（查询在线设备数）
     * @Author:       刘家义
     * @CreateDate:   2018/12/11 13:43
    */
    Integer selectOnLineAppCount(@Param(value = "state")Integer state);


}