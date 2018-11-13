package com.ybkj.gun.service;

import com.ybkj.gun.model.GunUser;
import com.ybkj.model.BaseModel;

import java.util.List;

/**
 * @项目名称：
 * @类名称：
 * @类描述：警员信息接口
 * @创建人：liujiayi
 * @创建时间：2018/11/5 19:32
 * @修改时间：2018/11/5 19:32
 * @version：1.0
 */
public interface GunUserService {

    /**
     * @Description:  功能描述（新增警员信息）
     * @Author:       刘家义
     * @CreateDate:   2018/11/5 19:33
    */
    BaseModel addGunUser(GunUser gunUser);
    /**
     * @Description:  功能描述（删除警员信息）
     * @Author:       刘家义
     * @CreateDate:   2018/11/5 19:33
     */
    BaseModel removeGunUser(String gunUserId);
    /**
     * @Description:  功能描述（更新警员信息）
     * @Author:       刘家义
     * @CreateDate:   2018/11/5 19:33
     */
    BaseModel revampGunUser(GunUser gunUser);
    /**
     * @Description:  功能描述（根据id查询警员信息）
     * @Author:       刘家义
     * @CreateDate:   2018/11/5 19:33
     */
    BaseModel findGunUserById(Integer gunUserId);
    /**
     * @Description:  功能描述（查询警员信息列表）
     * @Author:       刘家义
     * @CreateDate:   2018/11/5 19:33
     */
    List<GunUser> findGunUsers();

    /**
     * @Description:  功能描述（获取没有和腕表绑定的用户）
     * @Author:       刘家义
     * @CreateDate:   2018/11/13 9:54
     */
    List<GunUser> findGunUserNoBinding() throws Exception;


}
