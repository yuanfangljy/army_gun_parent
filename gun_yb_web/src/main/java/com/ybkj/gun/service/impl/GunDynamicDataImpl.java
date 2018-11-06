package com.ybkj.gun.service.impl;

import com.ybkj.enums.IStatusMessage;
import com.ybkj.gun.mapper.GunDynamicDataMapper;
import com.ybkj.gun.model.AppDynamicData;
import com.ybkj.gun.model.GunDynamicData;
import com.ybkj.gun.service.GunDynamicDataService;
import com.ybkj.model.BaseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @项目名称：
 * @类名称：
 * @类描述：
 * @创建人：liujiayi
 * @创建时间：2018/11/4 16:35
 * @修改时间：2018/11/4 16:35
 * @version：1.0
 */
@Service
@Transactional
@Slf4j
@SuppressWarnings("all")
public class GunDynamicDataImpl implements GunDynamicDataService {

    @Autowired
    private GunDynamicDataMapper gunDynamicDataMapper;

    /**
     * @Description: 功能描述（根据枪支编号查询枪支动态信息）
     * @Author: 刘家义
     * @CreateDate: 2018/11/4 16:37
     */
    @Override
    public BaseModel findGunDynamicDataByGunId(Integer gunId) throws Exception {
        BaseModel baseModel=new BaseModel();
        GunDynamicData gunDynamicData = this.gunDynamicDataMapper.selectAppDynamicByGunId(gunId);
        if(null != gunDynamicData){
            baseModel.setStatus(IStatusMessage.SystemStatus.SUCCESS.getCode());
            baseModel.add("getGunDynamicData",gunDynamicData);
            baseModel.setErrorMessage("查询成功");
            return baseModel;
        }
        baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
        baseModel.setErrorMessage("查询失败");
        return baseModel;
    }

    /**
     * @Description: 功能描述（根据枪支编号，查询枪支动态数据列表）
     * @Author: 刘家义
     * @CreateDate: 2018/11/4 16:37
     */
    @Override
    public List<GunDynamicData> findGunDynamicDatas(String gunId) throws Exception {
        return gunDynamicDataMapper.selectGunDynamicDataAll(gunId);
    }
}
