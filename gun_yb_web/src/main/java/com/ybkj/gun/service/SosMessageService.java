package com.ybkj.gun.service;

import com.ybkj.gun.model.SosMessage;
import com.ybkj.model.BaseModel;

import java.util.List;

/**
 * @项目名称：
 * @类名称：
 * @类描述：警告信息接口
 * @创建人：liujiayi
 * @创建时间：2018/11/3 16:07
 * @修改时间：2018/11/3 16:07
 * @version：1.0
 */
public interface SosMessageService {
    /**
     * @Description:  功能描述（查询警告信息列表:枪号）
     * @Author:       刘家义
     * @CreateDate:   2018/11/3 16:46
    */
    List<SosMessage> findSosMessages(String gunCode) throws Exception;

    /**
     * @Description:  功能描述（推送消息给指定的设备）
     * @Author:       刘家义
     * @CreateDate:   2018/11/24 16:22
    */
    BaseModel createForHelpGun(String appImei, Integer sosId,Integer type)  throws Exception;
}
