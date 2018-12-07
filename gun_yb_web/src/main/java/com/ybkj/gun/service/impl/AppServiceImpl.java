package com.ybkj.gun.service.impl;

import com.ybkj.common.util.ActiveUser;
import com.ybkj.enums.IStatusMessage;
import com.ybkj.gun.mapper.AppMapper;
import com.ybkj.gun.model.App;
import com.ybkj.gun.model.WebUser;
import com.ybkj.gun.service.AppService;
import com.ybkj.model.BaseModel;
import com.ybkj.untils.ValidatorRequestParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @项目名称：
 * @类名称：
 * @类描述：腕表/手机具体业务逻辑
 * @创建人：liujiayi
 * @创建时间：2018/11/2 14:35
 * @修改时间：2018/11/2 14:35
 * @version：1.0
 */
@Service
@Transactional
@Slf4j
@SuppressWarnings("all")
public class AppServiceImpl implements AppService{

    @Autowired
    private AppMapper appMapper;

    /**
     * @Description:  功能描述（新增腕表/手机）
     * @Author:       刘家义
     * @CreateDate:   2018/11/2 14:53
    */
    @Override
    public BaseModel addApp(App app) throws Exception {
        BaseModel baseModel = new BaseModel();
        baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
        //1、判断腕表/手机名称是否存在
        App existApp = appMapper.selectAppByName(app.getAppName());
        if (null != existApp) {
            baseModel.setErrorMessage("名称已经存在，请另填写名称");
            log.debug("新增腕表/手机，结果=responseResult:" + baseModel);
            return baseModel;
        }
        //2、校验腕表/手机字段
        if (!ValidatorRequestParam.validatorRequestParam(app, baseModel)) {
            log.debug("新增腕表/手机，结果=responseResult:" + baseModel);
            return baseModel;
        }
        //3、获取当前用户
        Boolean is = ActiveUser.isActiveUser();
        if (is) {
            WebUser activeUser = ActiveUser.getActiveUser();
        } else {
            baseModel.setErrorMessage("您未登录或登录超时，请您登录后再试");
            log.debug("新增腕表/手机，结果=responseResult:" + baseModel);
            return baseModel;
        }
        //4、添加腕表/手机
        final int i = appMapper.insertSelective(app);
        if (i > 0) {
            baseModel.setStatus(IStatusMessage.SystemStatus.SUCCESS.getCode());
            baseModel.setErrorMessage("新增腕表/手机成功");
            return baseModel;
        }
        baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
        baseModel.setErrorMessage("新增腕表/手机失败");
        return baseModel;
    }
    /**
     * @Description:  功能描述（删除 腕表/手机）
     * @Author:       刘家义
     * @CreateDate:   2018/11/2 14:53
     */
    @Override
    public BaseModel removeApp(String appId) throws Exception {
        BaseModel baseModel = new BaseModel();
        String[] arrays = appId.split(",");
        log.debug("腕表/手机id =arrays=" + arrays.toString());
        for (String id : arrays) {
            App app = this.appMapper.selectByPrimaryKey(Integer.valueOf(id));
            if(app==null){
                baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
                baseModel.setErrorMessage("该设备不存在");
                return baseModel;
            }
            int i = appMapper.deleteByPrimaryKey(Integer.valueOf(id));
        }
        baseModel.setStatus(IStatusMessage.SystemStatus.SUCCESS.getCode());
        baseModel.setErrorMessage("腕表/手机删除成功");
        return baseModel;
    }
    /**
     * @Description:  功能描述（更新 腕表/手机）
     * @Author:       刘家义
     * @CreateDate:   2018/11/2 14:53
     */
    @Override
    public BaseModel revampApp(App app) throws Exception {
        BaseModel baseModel = new BaseModel();
        //1、查询当前的版本号是否与开始的相同
        App existApp = this.appMapper.selectByPrimaryKey(app.getId());
        //System.out.println(app.getVersion()+"-----------------"+existApp.getVersion());
        if (null == existApp || null == existApp.getVersion() || !String.valueOf(app.getVersion()).equals(String.valueOf(existApp.getVersion()))) {
            baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
            baseModel.setErrorMessage("腕表/手机信息更新失败，请重新进去，再更新");
            log.debug("腕表/手机信息更新，结果=responseResult:" + baseModel);
            return baseModel;
        }
        //2、校验输入字符
        if(!ValidatorRequestParam.validatorRequestParam(app,baseModel)){
            log.debug("腕表/手机信息更新，结果=responseResult:" + baseModel);
            return baseModel;
        }
        //3、修改腕表/手机信息
        int i = this.appMapper.updateByPrimaryKeySelective(app);
        if(i>0) {
            baseModel.setStatus(IStatusMessage.SystemStatus.SUCCESS.getCode());
            baseModel.setErrorMessage("腕表/手机信息更新成功");
            log.debug("腕表/手机信息更新，结果=responseResult:" + baseModel);
        }else {
            baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
            baseModel.setErrorMessage("腕表/手机信息更新失败");
            log.debug("腕表/手机信息更新，结果=responseResult:" + baseModel);
        }
        return baseModel;
    }
    /**
     * @Description:  功能描述（根据Id查询 腕表/手机）
     * @Author:       刘家义
     * @CreateDate:   2018/11/2 14:53
     */
    @Override
    public BaseModel findAppById(Integer appId) throws Exception {
        BaseModel baseModel=new BaseModel();
        App app = this.appMapper.selectByPrimaryKey(appId);
        if(null != app){
            baseModel.setStatus(IStatusMessage.SystemStatus.SUCCESS.getCode());
            baseModel.add("getapp",app);
            baseModel.setErrorMessage("查询成功");
            return baseModel;
        }
        baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
        baseModel.setErrorMessage("查询失败");
        return baseModel;
    }
    /**
     * @Description:  功能描述（查询 腕表/手机 列表）
     * @Author:       刘家义
     * @CreateDate:   2018/11/2 14:53
     */
    @Override
    public List<App> findApps() throws Exception {
        return appMapper.selectApps();
    }
}
