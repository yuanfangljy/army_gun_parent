package com.ybkj.gun.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ybkj.enums.IStatusMessage;
import com.ybkj.gun.model.App;
import com.ybkj.gun.model.Menu;
import com.ybkj.gun.service.MenuService;
import com.ybkj.model.BaseModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @项目名称：
 * @类名称：
 * @类描述：权限菜单功能
 * @创建人：liujiayi
 * @创建时间：2018/10/21 8:19
 * @修改时间：2018/10/21 8:19
 * @version：1.0
 */
@SuppressWarnings("all")
@RestController
@Slf4j
@Api(value = "/",description = "菜单权限功能")
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    /**
     * @Description:  功能描述（开通新权限菜单）
     * @Author:       刘家义
     * @CreateDate:   2018/10/21 8:26
    */
    @ApiOperation(value ="开通新权限菜单",notes = "新增权限")
    @RequestMapping(value = "/insertMenu",method = RequestMethod.POST)
    public BaseModel insertMenu(Menu menu) throws Exception {
        log.debug("新增权限--menu-" + menu.toString());
        BaseModel baseModel=new BaseModel();
        try {
            if(null == menu){
                baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
                baseModel.setErrorMessage("请您填写菜单相关信息");
                log.debug("请您填写菜单相关信息！-menu-" + menu);
                return baseModel;
            }
            menu.setInsertTime(new Date());
            baseModel=menuService.addMeun(menu);
            log.debug("新增权限成功！-menu-" + menu);
            return baseModel;
        }catch (Exception e){
            log.error("新增权限异常！", e);
            e.printStackTrace();
            baseModel.setStatus(IStatusMessage.SystemStatus.PARAM_ERROR.getCode());
            baseModel.setErrorMessage("新增菜单异常");
            return baseModel;
        }
    }


    /**
     * @Description:  功能描述（删除权限菜单）
     * @Author:       刘家义
     * @CreateDate:   2018/10/21 14:30
     * 提示：单个删除菜单比较保险；没有做全选删除
    */
    @ApiOperation(value ="删除权限菜单",notes = "删除")
    @RequestMapping(value = "deleteMenu",method = RequestMethod.DELETE)
    public BaseModel deleteMenu(@RequestParam("mid") Integer mid) throws Exception{
        BaseModel baseModel=new BaseModel();
        log.debug("删除权限菜单--mid-" + mid);
        try {
            if (null == mid) {
                baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
                baseModel.setErrorMessage("删除的菜单id为空");
                log.debug("删除的菜单id为空！-mid-" + mid);
                return baseModel;
            }
                baseModel = menuService.removeMenu(mid);
                return baseModel;
        }catch (Exception e){
            log.error("删除权限异常！", e);
            e.printStackTrace();
            baseModel.setStatus(IStatusMessage.SystemStatus.PARAM_ERROR.getCode());
            baseModel.setErrorMessage("删除权限异常");
            return baseModel;
        }
    }

    /**
     * @Description:  功能描述（修改菜单信息）
     * @Author:       刘家义
     * @CreateDate:   2018/10/21 15:14
     * @param type    [0：编辑；1：新增子节点权限]
     * @param menu
     * @return
    */
    @ApiOperation(value ="修改菜单信息",notes = "0：编辑；1：新增子节点权限")
    @RequestMapping(value = "updateMenu",method = RequestMethod.PUT)
    public BaseModel updateMenu(@RequestParam("type")Integer type, Menu menu) throws Exception {
        log.debug("设置权限--区分type-" + type + "【0：编辑；1：新增子节点权限】，权限--menu-" + menu);
        BaseModel baseModel=new BaseModel();
        try {
            if(null != menu){
                Date data=new Date();
                if(type==0){//一、编辑菜单权限
                    menu.setUpdateTime(data);
                    //编辑权限
                    baseModel=this.menuService.revampMenu(menu);
                    log.debug("编辑菜单权限成功！-menu-" + menu);
                }else if(type==1){//二、新增子节点权限
                    menu.setInsertTime(data);
                    //增加子节点权限
                    baseModel = this.menuService.addMeun(menu);
                    log.debug("增加子节点权限成功！-menu-" + menu);
                }

            }
        }catch (Exception e){
            e.printStackTrace();
            log.error("设置权限异常！", e);
            baseModel.setStatus(IStatusMessage.SystemStatus.PARAM_ERROR.getCode());
            baseModel.setErrorMessage("设置权限异常！");
        }
        return baseModel;
    }


    /**
     * @Description:  功能描述（根据菜单id,查询菜单信息）
     * @Author:       刘家义
     * @CreateDate:   2018/10/21 18:00
    */
    @ApiOperation(value ="根据菜单id,查询菜单信息",notes = "获取权限")
    @RequestMapping(value = "/getMenu",method = RequestMethod.GET)
    public BaseModel getMenu(@RequestParam("mid") Integer mid){
        BaseModel baseModel=new BaseModel();
        log.debug("获取权限--mid-" + mid);
        try {
            if(mid != null && mid>0){
                baseModel=this.menuService.findMenuById(mid);
                log.debug("获取权限成功！-mid-" + mid);
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error("获取权限异常！", e);
            baseModel.setStatus(IStatusMessage.SystemStatus.PARAM_ERROR.getCode());
            baseModel.setErrorMessage("获取权限异常！");
        }
        return baseModel;
    }


    /**
     * @Description:  功能描述（查询菜单列表）
     * @Author:       刘家义
     * @CreateDate:   2018/10/21 18:29
    */
    @ApiOperation(value ="查询菜单列表",notes = "获取权限列表")
    @RequestMapping(value = "/getMenuList",method = RequestMethod.GET)
    public BaseModel getMenuList(@RequestParam(value="pn",defaultValue="1") Integer pn,@RequestParam(value = "ps",defaultValue="5")Integer ps){
        BaseModel baseModel=new BaseModel();
        PageHelper.startPage(pn,ps);
        log.debug("--------获取权限列表！");
        try {
            List<Menu> menus =this.menuService.findMenus();
            PageInfo<Menu> page = new PageInfo<Menu>(menus,5);
            baseModel.setStatus(IStatusMessage.SystemStatus.SUCCESS.getCode());
            baseModel.add("pageInfo",page);
        }catch (Exception e){
            e.printStackTrace();
            log.error("权限查询异常！", e);
            baseModel.setStatus(IStatusMessage.SystemStatus.PARAM_ERROR.getCode());
            baseModel.setErrorMessage("获取权限异常！");
        }
        return baseModel;
    }

}
