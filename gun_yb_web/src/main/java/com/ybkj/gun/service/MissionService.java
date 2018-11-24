package com.ybkj.gun.service;

import com.ybkj.gun.model.Mission;
import com.ybkj.model.BaseModel;

import java.util.List;

/**
 * @项目名称：
 * @类名称：
 * @类描述：协助查找接口
 * @创建人：liujiayi
 * @创建时间：2018/11/4 15:01
 * @修改时间：2018/11/4 15:01
 * @version：1.0
 */
public interface MissionService {
    /**
     * @Description:  功能描述（查询协助查找信息）
     * @Author:       刘家义
     * @CreateDate:   2018/11/23 9:36
    */
    List<Mission> findMissions(String gunMac) throws Exception;

    /**
     * @Description:  功能描述（枪支查找启停控制）
     * @Author:       刘家义
     * @CreateDate:   2018/11/23 10:07
    */
    BaseModel restartMission(String type, String appImei) throws Exception;

    /**
     * @Description:  功能描述（新增协助查找相关信息）
     * @Author:       刘家义
     * @CreateDate:   2018/11/24 17:15
    */
    BaseModel insertMission(Mission mission) throws Exception;
}
