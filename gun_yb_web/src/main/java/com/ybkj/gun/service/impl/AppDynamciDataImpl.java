package com.ybkj.gun.service.impl;

import com.ybkj.enums.IStatusMessage;
import com.ybkj.gun.mapper.AppDynamicDataMapper;
import com.ybkj.gun.model.App;
import com.ybkj.gun.model.AppDynamicData;
import com.ybkj.gun.service.AppDynamicDataService;
import com.ybkj.model.BaseModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @项目名称：
 * @类名称：
 * @类描述：App动态数据业务逻辑的实现
 * @创建人：liujiayi
 * @创建时间：2018/11/4 15:11
 * @修改时间：2018/11/4 15:11
 * @version：1.0
 */
@Service
@Transactional
@Slf4j
@SuppressWarnings("all")
public class AppDynamciDataImpl implements AppDynamicDataService{

    @Autowired
    private AppDynamicDataMapper appDynamicDataMapper;
    /**
     * @Description:  功能描述（根据AppId查询app动态信息）
     * @Author:       刘家义
     * @CreateDate:   2018/11/4 15:11
     */
    @Override
    public BaseModel findAppDynamicDataByAppId(Integer appId) throws Exception {
        BaseModel baseModel=new BaseModel();
        AppDynamicData appDynamicData = this.appDynamicDataMapper.selectAppDynamicByAppId(appId);
        if(null != appDynamicData){
            baseModel.setStatus(IStatusMessage.SystemStatus.SUCCESS.getCode());
            baseModel.add("getAppDynamicData",appDynamicData);
            baseModel.setErrorMessage("查询成功");
            return baseModel;
        }
        baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
        baseModel.setErrorMessage("查询失败");
        return baseModel;
    }


    /**
     * @Description:  功能描述（根据AppName，分页查询app动态信息）
     * @Author:       刘家义
     * @CreateDate:   2018/11/4 15:12
     */
    @Override
    public List<AppDynamicData> findAppDynamicDatas(String appName) throws Exception {
        return appDynamicDataMapper.selectAppDynamicDataAll(appName);
    }





}
