package com.ybkj.gun.mapper;

import com.ybkj.gun.model.AppGun;
import com.ybkj.gun.model.AppGunUser;
import com.ybkj.gun.model.AppGunUserExample;
import java.util.List;

import com.ybkj.gun.model.GunUser;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;

public interface AppGunUserMapper {
    long countByExample(AppGunUserExample example);

    int deleteByExample(AppGunUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AppGunUser record);

    int insertSelective(AppGunUser record);

    List<AppGunUser> selectByExample(AppGunUserExample example);

    AppGunUser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AppGunUser record, @Param("example") AppGunUserExample example);

    int updateByExample(@Param("record") AppGunUser record, @Param("example") AppGunUserExample example);

    int updateByPrimaryKeySelective(AppGunUser record);

    int updateByPrimaryKey(AppGunUser record);

    /**
     * @Description:  功能描述（获取设备注册，绑定的用户信息）
     * @Author:       刘家义
     * @CreateDate:   2018/11/10 17:29
     */
    List<AppGunUser> selectAppGunUserList();

    /**
     * @Description:  功能描述（通过 appId 获取 gunUserID）
     * @Author:       刘家义
     * @CreateDate:   2018/11/11 15:14
     */
    AppGunUser selectAppGunUserByAppId(Integer appId);

    /**
     * @Description:  功能描述（判断用户和设备是否已经绑定）
     * @Author:       刘家义
     * @CreateDate:   2018/11/12 16:31
    */
    AppGunUser selectAppGunUserByAPPUserState(@Param(value = "gunUserId") Integer gunUserId, @Param(value = "appId")Integer appId, @Param(value = "state")Integer state);

    /**
     * @Description:  功能描述（通过gun_id，在app_gun_user状态为 1 的用户id）
     * @Author:       刘家义
     * @CreateDate:   2018/11/12 20:02
    */
    AppGunUser selectAppGunUserByGunIdAndState(@Param(value = "gunId") Integer gunId, @Param(value = "state")Integer state);


}