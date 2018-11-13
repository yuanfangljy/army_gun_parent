package com.ybkj.gun.service.impl;

import com.ybkj.enums.IStatusMessage;
import com.ybkj.gun.mapper.GunUserMapper;
import com.ybkj.gun.model.GunUser;
import com.ybkj.gun.service.GunUserService;
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
 * @类描述：警员信息业务逻辑具体实现
 * @创建人：liujiayi
 * @创建时间：2018/11/5 19:32
 * @修改时间：2018/11/5 19:32
 * @version：1.0
 */

@Service
@Transactional
@Slf4j
@SuppressWarnings("all")
public class GunUserServiceImpl implements GunUserService{

    @Autowired
    private GunUserMapper gunUserMapper;

    /**
     * @Description:  功能描述（新增警员信息）
     * @Author:       刘家义
     * @CreateDate:   2018/11/5 19:33
     */
    @Override
    public BaseModel addGunUser(GunUser gunUser) {
        BaseModel baseModel = new BaseModel();
        baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
        //1、判断编号编号是否存在(数据库命名问题：gunId==gunCode)
        GunUser existGunUserUserId= gunUserMapper.selectGunUserByUserId(gunUser.getUserId());
        if (null != existGunUserUserId) {
            baseModel.setErrorMessage("警员编号已经存在，请重新填写");
            log.debug("新增编号，结果=responseResult:" + baseModel);
            return baseModel;
        }
        //2、判断电话号码是否存在
        GunUser existPhone = gunUserMapper.selectGunUserByPhone(gunUser.getUserPhone());
        if (null != existPhone) {
            baseModel.setErrorMessage("电话号码已经存在，请重新填写");
            log.debug("新增警员，结果=responseResult:" + baseModel);
            return baseModel;
        }
        //3、校验警员字段
        if (!ValidatorRequestParam.validatorRequestParam(gunUser, baseModel)) {
            log.debug("新增警员，结果=responseResult:" + baseModel);
            return baseModel;
        }
        //5、添加警员
        final int i = gunUserMapper.insertSelective(gunUser);
        if (i > 0) {
            baseModel.setStatus(IStatusMessage.SystemStatus.SUCCESS.getCode());
            baseModel.setErrorMessage("新增警员成功");
            return baseModel;
        }
        baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
        baseModel.setErrorMessage("新增警员失败");
        return baseModel;
    }

    /**
     * @Description:  功能描述（删除警员信息）
     * @Author:       刘家义
     * @CreateDate:   2018/11/5 19:33
     */
    @Override
    public BaseModel removeGunUser(String gunUserId) {
        BaseModel baseModel = new BaseModel();
        String[] arrays = gunUserId.split(",");
        log.debug("警员id =arrays=" + arrays.toString());
        for (String id : arrays) {
            GunUser gunUser = this.gunUserMapper.selectByPrimaryKey(Integer.valueOf(id));
            if(gunUser==null){
                baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
                baseModel.setErrorMessage("该警员不存在");
                return baseModel;
            }
            int i = gunUserMapper.deleteByPrimaryKey(Integer.valueOf(id));
        }
        baseModel.setStatus(IStatusMessage.SystemStatus.SUCCESS.getCode());
        baseModel.setErrorMessage("警员删除成功");
        return baseModel;
    }

    /**
     * @Description:  功能描述（更新警员信息）
     * @Author:       刘家义
     * @CreateDate:   2018/11/5 19:33
     */
    @Override
    public BaseModel revampGunUser(GunUser gunUser) {
        BaseModel baseModel = new BaseModel();
        //1、查询当前的版本号是否与开始的相同
        GunUser existGunUser = this.gunUserMapper.selectByPrimaryKey(gunUser.getUserId());
        if (null == existGunUser || null == existGunUser.getVersion() || !String.valueOf(gunUser.getVersion()).equals(String.valueOf(gunUser.getVersion()))) {
            baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
            baseModel.setErrorMessage("警员信息更新失败，请重新进去，再更新");
            log.debug("警员信息更新，结果=responseResult:" + baseModel);
            return baseModel;
        }
        //2、校验输入字符
        if(!ValidatorRequestParam.validatorRequestParam(gunUser,baseModel)){
            log.debug("警员信息更新，结果=responseResult:" + baseModel);
            return baseModel;
        }
        //3、修改警员信息
        int i = this.gunUserMapper.updateByPrimaryKeySelective(gunUser);
        if(i>0) {
            baseModel.setStatus(IStatusMessage.SystemStatus.SUCCESS.getCode());
            baseModel.setErrorMessage("警员信息更新成功");
            log.debug("警员信息更新，结果=responseResult:" + baseModel);
        }else {
            baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
            baseModel.setErrorMessage("警员信息更新失败");
            log.debug("警员信息更新，结果=responseResult:" + baseModel);
        }
        return baseModel;
    }


    /**
     * @Description:  功能描述（根据id查询警员信息）
     * @Author:       刘家义
     * @CreateDate:   2018/11/5 19:33
     */
    @Override
    public BaseModel findGunUserById(Integer gunUserId) {
        BaseModel baseModel=new BaseModel();
        GunUser gunUser = this.gunUserMapper.selectByPrimaryKey(gunUserId);
        if(null != gunUser){
            baseModel.setStatus(IStatusMessage.SystemStatus.SUCCESS.getCode());
            baseModel.add("getUser",gunUser);
            baseModel.setErrorMessage("查询成功");
            return baseModel;
        }
        baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
        baseModel.setErrorMessage("查询失败");
        return baseModel;
    }


    /**
     * @Description:  功能描述（查询警员信息列表）
     * @Author:       刘家义
     * @CreateDate:   2018/11/5 19:33
     */
    @Override
    public List<GunUser> findGunUsers() {
        return gunUserMapper.selectGunUserAll();
    }

    /**
     * @Description:  功能描述（获取没有和腕表绑定的用户）
     * @Author:       刘家义
     * @CreateDate:   2018/11/13 9:54
     */
    @Override
    public  List<GunUser>  findGunUserNoBinding() throws Exception {
        return gunUserMapper.selectGunUserNoBinding();
    }

}
