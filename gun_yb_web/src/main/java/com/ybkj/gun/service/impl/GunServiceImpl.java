package com.ybkj.gun.service.impl;

import com.ybkj.common.util.ActiveUser;
import com.ybkj.enums.IStatusMessage;
import com.ybkj.gun.mapper.GunMapper;
import com.ybkj.gun.model.Gun;
import com.ybkj.gun.model.Warehouse;
import com.ybkj.gun.model.WebUser;
import com.ybkj.gun.service.GunService;
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
 * @类描述：
 * @创建人：liujiayi
 * @创建时间：2018/11/3 13:23
 * @修改时间：2018/11/3 13:23
 * @version：1.0
 */
@Service
@Transactional
@Slf4j
@SuppressWarnings("all")
public class GunServiceImpl implements GunService{

    @Autowired
    private GunMapper gunMapper;

    /**
     * @Description:  功能描述（新增枪支）
     * @Author:       刘家义
     * @CreateDate:   2018/11/3 13:23
     */
    @Override
    public BaseModel addGun(Gun gun) throws Exception {
        BaseModel baseModel = new BaseModel();
        baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
        //1、判断枪支编号是否存在(数据库命名问题：gunId==gunCode)
        Gun existGunCode= gunMapper.selectGunByGunCode(gun.getGunId());
        if (null != existGunCode) {
            baseModel.setErrorMessage("枪支编号已经存在，请重新填写");
            log.debug("新增枪支，结果=responseResult:" + baseModel);
            return baseModel;
        }
        //2、判断枪支绑定的蓝牙号是否存在
        Gun existGunMac = gunMapper.selectGunByGunMac(gun.getGunMac());
        if (null != existGunMac) {
            baseModel.setErrorMessage("蓝牙编号已经存在，请重新填写");
            log.debug("新增枪支，结果=responseResult:" + baseModel);
            return baseModel;
        }
        //3、校验枪支字段
        if (!ValidatorRequestParam.validatorRequestParam(gun, baseModel)) {
            log.debug("新增枪支，结果=responseResult:" + baseModel);
            return baseModel;
        }
        //4、获取当前用户
       /* Boolean is = ActiveUser.isActiveUser();
        if (is) {
            WebUser activeUser = ActiveUser.getActiveUser();
            gun.setUid(activeUser.getId());
        } else {
            baseModel.setErrorMessage("您未登录或登录超时，请您登录后再试");
            log.debug("新增库室，结果=responseResult:" + baseModel);
            return baseModel;
        }*/
        //5、添加库室
        final int i = gunMapper.insertSelective(gun);
        if (i > 0) {
            baseModel.setStatus(IStatusMessage.SystemStatus.SUCCESS.getCode());
            baseModel.setErrorMessage("新增枪支成功");
            return baseModel;
        }
        baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
        baseModel.setErrorMessage("新增枪支失败");
        return baseModel;
    }

    /**
     * @Description:  功能描述（删除枪支）
     * @Author:       刘家义
     * @CreateDate:   2018/11/3 13:24
     * 注意：
     *     1、并不是真正的删除，只是改变 is_del 状态
     */
    @Override
    public BaseModel removeUun(String gunId,Integer type) {
        BaseModel baseModel = new BaseModel();
        String[] arrays = gunId.split(",");
        log.debug("枪支id =arrays=" + arrays.toString());
        for (String id : arrays) {
            Gun gun = this.gunMapper.selectByPrimaryKey(Integer.valueOf(id));
            if(gun==null){
                baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
                baseModel.setErrorMessage("该枪支不存在");
                return baseModel;
            }
            if(type==0){//0:恢复
                gun.setIsDel((byte)0);
                int i = gunMapper.updateByPrimaryKeySelective(gun);
                baseModel.setStatus(IStatusMessage.SystemStatus.SUCCESS.getCode());
                baseModel.setErrorMessage("枪支恢复成功");
            }else {//1：删除
                gun.setIsDel((byte)1);
                int i = gunMapper.updateByPrimaryKeySelective(gun);
                baseModel.setStatus(IStatusMessage.SystemStatus.SUCCESS.getCode());
                baseModel.setErrorMessage("枪支删除成功");
            }
        }
        return baseModel;
    }

    /**
     * @Description:  功能描述（修改枪支）
     * @Author:       刘家义
     * @CreateDate:   2018/11/3 13:24
     */
    @Override
    public BaseModel revampGun(Gun gun) {
        BaseModel baseModel = new BaseModel();
        //1、查询当前的版本号是否与开始的相同
        Gun existGun = this.gunMapper.selectByPrimaryKey(gun.getId());
       // System.out.println("----------------------------"+gun.getId());
        if (null == existGun || null == existGun.getVersion() || !String.valueOf(existGun.getVersion()).equals(String.valueOf(gun.getVersion()))) {
            baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
            baseModel.setErrorMessage("枪支信息更新失败，请重新进去，再更新");
            log.debug("枪支信息更新，结果=responseResult:" + baseModel);
            return baseModel;
        }
        //2、校验输入字符
       /* if(!ValidatorRequestParam.validatorRequestParam(gun,baseModel)){
            log.debug("枪支信息更新，结果=responseResult:" + baseModel);
            return baseModel;
        }*/
        //3、修改库室信息
        int i = this.gunMapper.updateByPrimaryKeySelective(gun);
        if(i>0) {
            baseModel.setStatus(IStatusMessage.SystemStatus.SUCCESS.getCode());
            baseModel.setErrorMessage("枪支信息更新成功");
            log.debug("枪支信息更新，结果=responseResult:" + baseModel);
        }else {
            baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
            baseModel.setErrorMessage("枪支信息更新失败");
            log.debug("枪支信息更新，结果=responseResult:" + baseModel);
        }
        return baseModel;
    }

    /**
     * @Description:  功能描述（根据枪支Id，查询枪支信息）
     * @Author:       刘家义
     * @CreateDate:   2018/11/3 13:24
     */
    @Override
    public BaseModel findGunById(Integer gunId) {
        BaseModel baseModel=new BaseModel();
        Gun gun = this.gunMapper.selectByPrimaryKey(gunId);
        if(null != gun){
            baseModel.setStatus(IStatusMessage.SystemStatus.SUCCESS.getCode());
            baseModel.add("getGun",gun);
            baseModel.setErrorMessage("查询成功");
            return baseModel;
        }
        baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
        baseModel.setErrorMessage("查询失败");
        return baseModel;
    }

    /**
     * @Description:  功能描述（查询枪支列表，进行分页）
     * @Author:       刘家义
     * @CreateDate:   2018/11/3 13:24
     */
    @Override
    public List<Gun> findGuns() {
        return gunMapper.selectGuns();
    }

    /**
     * @Description:  功能描述（查询没有被预选的枪支列表）
     * @Author:       刘家义
     * @CreateDate:   2018/11/9 20:19
    */
    @Override
    public List<Gun> findGunsNotPreselected() throws Exception {
        return gunMapper.selectGunsNotPreselected();
    }

    /**
     * @Description:  功能描述（查询被预选的枪支列表）
     * @Author:       刘家义
     * @CreateDate:   2018/11/10 8:37
    */
    @Override
    public List<Gun> findGunsPreselected() throws Exception {
        return gunMapper.selectGunsPreselected();
    }


}
