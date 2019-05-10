package com.ybkj.gun.service.impl;

import com.ybkj.common.util.ActiveUser;
import com.ybkj.enums.IStatusMessage;
import com.ybkj.gun.mapper.WarehouseMapper;
import com.ybkj.gun.model.App;
import com.ybkj.gun.model.Warehouse;
import com.ybkj.gun.model.WebUser;
import com.ybkj.gun.service.WareHouseService;
import com.ybkj.model.BaseModel;
import com.ybkj.untils.ValidatorRequestParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.core.jackson.Log4jYamlObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @项目名称：
 * @类名称：
 * @类描述：库室具体业务逻辑
 * @创建人：liujiayi
 * @创建时间：2018/11/1 15:20
 * @修改时间：2018/11/1 15:20
 * @version：1.0
 */
@Service
@Transactional
@Slf4j
@SuppressWarnings("all")
public class WareHouseServiceImpl implements WareHouseService {

    @Autowired
    private WarehouseMapper warehouseMapper;

    /**
     * @Description: 功能描述（新增库室逻辑）
     * @Author: 刘家义
     * @CreateDate: 2018/11/1 15:44
     */
    @Override
    public BaseModel addWareHouse(Warehouse warehouse) throws Exception {
        BaseModel baseModel = new BaseModel();
        baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
        //1、判断库室名称是否存在
        Warehouse existWareHouse = warehouseMapper.selectWareHouseByName(warehouse.getName());
        if (null != existWareHouse) {
            baseModel.setErrorMessage("库室名已经存在，请重新填写");
            log.debug("新增库室，结果=responseResult:" + baseModel);
            return baseModel;
        }
        //2、校验库室字段
        if (!ValidatorRequestParam.validatorRequestParam(warehouse, baseModel)) {
            log.debug("新增库室，结果=responseResult:" + baseModel);
            return baseModel;
        }
        //3、获取当前用户
        Boolean is = ActiveUser.isActiveUser();
        if (is) {
            WebUser activeUser = ActiveUser.getActiveUser();
            warehouse.setUid(activeUser.getId());
        } else {
            baseModel.setErrorMessage("您未登录或登录超时，请您登录后再试");
            log.debug("新增库室，结果=responseResult:" + baseModel);
            return baseModel;
        }
        //4、添加库室
        final int i = warehouseMapper.insertSelective(warehouse);
        if (i > 0) {
            baseModel.setStatus(IStatusMessage.SystemStatus.SUCCESS.getCode());
            baseModel.setErrorMessage("新增库室成功");
            return baseModel;
        }
        baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
        baseModel.setErrorMessage("新增库室失败");
        return baseModel;
    }

    /**
     * @Description: 功能描述（删除库室）
     * @Author: 刘家义
     * @CreateDate: 2018/11/1 17:26
     */
    @Override
    public BaseModel removeWareHourse(String wId) throws Exception{
        BaseModel baseModel = new BaseModel();
        String[] arrays = wId.split(",");
        log.debug("库室id =arrays=" + arrays.toString());
        for (String id : arrays) {
            Warehouse warehouse = this.warehouseMapper.selectByPrimaryKey(Integer.valueOf(id));
            if(warehouse==null){
                baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
                baseModel.setErrorMessage("该库室不存在");
                return baseModel;
            }
            int i = warehouseMapper.deleteByPrimaryKey(Integer.valueOf(id));
        }
        baseModel.setStatus(IStatusMessage.SystemStatus.SUCCESS.getCode());
        baseModel.setErrorMessage("库室删除成功");
        return baseModel;
    }

    /**
     * @Description: 功能描述（修改库室信息）
     * @Author: 刘家义
     * @CreateDate: 2018/11/1 19:38
     */
    @Override
    public BaseModel revampWareHouse(Warehouse warehouse) throws Exception{
        BaseModel baseModel = new BaseModel();
        //1、查询当前的版本号是否与开始的相同
        Warehouse existWareHouse = this.warehouseMapper.selectByPrimaryKey(warehouse.getId());
        if (null == existWareHouse || null == existWareHouse.getVersion() || !String.valueOf(warehouse.getVersion()).equals(String.valueOf(existWareHouse.getVersion()))) {
            baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
            baseModel.setErrorMessage("库室信息更新失败，请重新进去，再更新");
            log.debug("库室信息更新，结果=responseResult:" + baseModel);
            return baseModel;
        }
        //2、校验输入字符
        if(!ValidatorRequestParam.validatorRequestParam(warehouse,baseModel)){
            log.debug("库室信息更新，结果=responseResult:" + baseModel);
            return baseModel;
        }
        //3、修改库室信息
        int i = this.warehouseMapper.updateByPrimaryKeySelective(warehouse);
        if(i>0) {
            baseModel.setStatus(IStatusMessage.SystemStatus.SUCCESS.getCode());
            baseModel.setErrorMessage("库室信息更新成功");
            log.debug("库室信息更新，结果=responseResult:" + baseModel);
        }else {
            baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
            baseModel.setErrorMessage("库室信息更新失败");
            log.debug("库室信息更新，结果=responseResult:" + baseModel);
        }
        return baseModel;
    }


    /**
     * @Description:  功能描述（根据库室id,查询库室信息）
     * @Author:       刘家义
     * @CreateDate:   2018/11/1 19:58
    */
    @Override
    public BaseModel findWareHouseById(Integer wId) throws Exception {
        BaseModel baseModel=new BaseModel();
        Warehouse warehouse = this.warehouseMapper.selectByPrimaryKey(wId);
        if(null != warehouse){
            baseModel.setStatus(IStatusMessage.SystemStatus.SUCCESS.getCode());
            baseModel.add("getWarehouse",warehouse);
            baseModel.setErrorMessage("查询成功");
            return baseModel;
        }
        baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
        baseModel.setErrorMessage("查询失败");
        return baseModel;
    }


    /**
     * @Description:  功能描述（查询库室列表）
     * @Author:       刘家义
     * @CreateDate:   2018/11/1 20:03
    */
    @Override
    public List<Warehouse> findWarehouses() throws Exception {
        return warehouseMapper.selectWareHouses();
    }


}
