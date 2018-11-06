package com.ybkj.gun.service;

import com.ybkj.gun.model.Gun;
import com.ybkj.model.BaseModel;

import java.util.List;

/**
 * @项目名称：
 * @类名称：
 * @类描述：枪支管理接口
 * @创建人：liujiayi
 * @创建时间：2018/11/3 13:22
 * @修改时间：2018/11/3 13:22
 * @version：1.0
 */
public interface GunService {

    /**
     * @Description:  功能描述（新增枪支）
     * @Author:       刘家义
     * @CreateDate:   2018/11/3 13:23
    */
    BaseModel addGun(Gun gun) throws Exception;

    /**
     * @Description:  功能描述（删除枪支）
     * @Author:       刘家义
     * @CreateDate:   2018/11/3 13:24
    */
    BaseModel removeUun(String gunId,Integer type);
    /**
     * @Description:  功能描述（修改枪支）
     * @Author:       刘家义
     * @CreateDate:   2018/11/3 13:24
    */
    BaseModel revampGun(Gun gun);
    /**
     * @Description:  功能描述（根据枪支Id，查询枪支信息）
     * @Author:       刘家义
     * @CreateDate:   2018/11/3 13:24
    */
    BaseModel findGunById(Integer gunId);
    /**
     * @Description:  功能描述（查询枪支列表，进行分页）
     * @Author:       刘家义
     * @CreateDate:   2018/11/3 13:24
    */
    List<Gun> findGuns();
}
