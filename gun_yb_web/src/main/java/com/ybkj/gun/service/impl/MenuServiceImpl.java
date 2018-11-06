package com.ybkj.gun.service.impl;

import com.ybkj.enums.IStatusMessage;
import com.ybkj.gun.mapper.MenuMapper;
import com.ybkj.gun.model.Menu;
import com.ybkj.gun.service.MenuService;
import com.ybkj.model.BaseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * @项目名称：
 * @类名称：
 * @类描述：菜单权限功能实现业务逻辑
 * @创建人：liujiayi
 * @创建时间：2018/10/16 15:25
 * @修改时间：2018/10/16 15:25
 * @version：1.0
 */
@Service
@Transactional
@Slf4j
@SuppressWarnings("all")
public class MenuServiceImpl implements MenuService {
    
    @Autowired
    MenuMapper menuMapper;
    
    /**
     * @Description:  功能描述（根据角色id来获取最新权限树）
     * @Author:       刘家义
     * @CreateDate:   2018/10/16 16:59
    */
    @Override
    public List<Menu> findMenusByRoleId(Integer rid) {
        return menuMapper.selectMenusByRoleId(rid);
    }


    /**
     * @Description:  功能描述（开通新权限菜单）
     * @Author:       刘家义
     * @CreateDate:   2018/10/21 9:19
    */
    @Override
    public BaseModel addMeun(Menu menu) throws Exception {
        BaseModel baseModel=new BaseModel();
            final int i=menuMapper.insertSelective(menu);
            if(i>0){
                baseModel.setStatus(IStatusMessage.SystemStatus.SUCCESS.getCode());
                baseModel.setErrorMessage("新增菜单成功");
                return baseModel;
            }
            baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
            baseModel.setErrorMessage("新增菜单失败");
            return baseModel;
    }

    /**
     * @Description:  功能描述（根据菜单id，删除菜单信息）
     * @Author:       刘家义
     * @CreateDate:   2018/10/21 15:07
    */
    @Override
    public BaseModel removeMenu(Integer mid) throws Exception {
        BaseModel baseModel=new BaseModel();
        final  int i = menuMapper.deleteByPrimaryKey(mid);
        if(i>0){
            baseModel.setStatus(IStatusMessage.SystemStatus.SUCCESS.getCode());
            baseModel.setErrorMessage("删除菜单成功");
            return baseModel;
        }
        baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
        baseModel.setErrorMessage("删除菜单失败");
        return baseModel;
    }


    /**
     * @Description:  功能描述（修改菜单信息）
     * @Author:       刘家义
     * @CreateDate:   2018/10/21 17:42
    */
    @Override
    public BaseModel revampMenu(Menu menu) throws Exception {
        BaseModel baseModel=new BaseModel();
        final int i=menuMapper.updateByPrimaryKeySelective(menu);
        if(i>0){
            baseModel.setStatus(IStatusMessage.SystemStatus.SUCCESS.getCode());
            baseModel.setErrorMessage("修改菜单成功");
            return baseModel;
        }
        baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
        baseModel.setErrorMessage("修改菜单失败");
        return baseModel;
    }


    /**
     * @Description:  功能描述（根据ID查询菜单信息）
     * @Author:       刘家义
     * @CreateDate:   2018/10/21 18:21
    */
    @Override
    public BaseModel findMenuById(Integer mid) throws Exception {
        BaseModel baseModel=new BaseModel();
        Menu menu = this.menuMapper.selectByPrimaryKey(mid);
        if(null != menu){
            baseModel.setStatus(IStatusMessage.SystemStatus.SUCCESS.getCode());
            baseModel.add("getMenu",menu);
            baseModel.setErrorMessage("查询成功");
            return baseModel;
        }
        baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
        baseModel.setErrorMessage("查询失败");
        return baseModel;
    }


    /**
     * @Description:  功能描述（查询全部菜单）
     * @Author:       刘家义
     * @CreateDate:   2018/10/21 18:47
    */
    @Override
    public List<Menu> findMenus() throws Exception {
        return menuMapper.selectMenus();
    }
}
