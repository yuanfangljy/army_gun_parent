package com.ybkj.gun.service;

import com.ybkj.common.entity.GunLocationVO;
import com.ybkj.gun.model.GunLocation;

import java.util.List;

/**
 * @项目名称：
 * @类名称：
 * @类描述：枪支地理位置相关接口
 * @创建人：liujiayi
 * @创建时间：2018/11/12 14:18
 * @修改时间：2018/11/12 14:18
 * @version：1.0
 */
public interface GunLocationService {
    /**
     * @Description:  功能描述（查询枪支动态位置信息）
     * @Author:       刘家义
     * @CreateDate:   2018/11/12 14:19
    */
    List<GunLocationVO> findGunDynamic(String gunId, String appName) throws Exception;
}
