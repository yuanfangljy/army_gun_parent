package com.ybkj.gun.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ybkj.enums.IStatusMessage;
import com.ybkj.gun.model.Menu;
import com.ybkj.gun.model.Role;
import com.ybkj.gun.service.RoleService;
import com.ybkj.model.BaseModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
 * @类描述：角色功能模块
 * @创建人：liujiayi
 * @创建时间：2018/10/21 18:53
 * @修改时间：2018/10/21 18:53
 * @version：1.0
 */
@SuppressWarnings("all")
@RestController
@Slf4j
@Api(value = "/",description = "角色功能")
@RequestMapping("/role")
public class RoleController {


    @Autowired
    private RoleService roleService;

    /**
     * @Description:  功能描述（添加角色，并且授权）
     * @Author:       刘家义
     * @CreateDate:   2018/10/22 16:38
     * @param mids    角色对应的权限，菜单的id
     * @param role    角色对象
     * @return
    */
    @ApiOperation(value ="添加角色并且授权",notes = "添加角色授权")
    @RequestMapping(value="/insertRole",method = RequestMethod.POST)
    public BaseModel insertRole(@RequestParam("mids")String mids, Role role){
        BaseModel baseModel=new BaseModel();
        baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
        log.debug("添加角色并授权！角色数据role：" + role.toString() + "，权限数据mids：" + mids);
        try {
            //1、判断是否选择权限
            if(StringUtils.isEmpty(mids)){
                baseModel.setErrorMessage("未授权，请您给该角色授权");
                return baseModel;
            }
            //2、判断角色信息是否为空
            if(null == role){
                baseModel.setErrorMessage("请您填写完整的角色数据");
                return baseModel;
            }
            //3、添加角色并授权
            role.setInsertTime(new Date());
            baseModel=this.roleService.addRole(mids,role);

        }catch (Exception e){
            baseModel.setStatus(IStatusMessage.SystemStatus.PARAM_ERROR.getCode());
            baseModel.setErrorMessage("添加角色并授权！异常");
            e.printStackTrace();
            log.error("添加角色并授权！异常！", e);
        }
        return baseModel;
    }


    /**
     * @Description:  功能描述（删除角色已经权限）
     * @Author:       刘家义
     * @CreateDate:   2018/10/22 18:05
    */
    @ApiOperation(value ="删除角色已经权限",notes = "删除角色以及它对应的权限")
    @RequestMapping(value="/deleteRole",method = RequestMethod.DELETE)
    public BaseModel deleteRole(@RequestParam("rid") Integer rid){
        BaseModel baseModel=new BaseModel();
        log.debug("删除角色以及它对应的权限--id-" + rid);
        try {
            if(rid>0){
                baseModel=this.roleService.removeRole(rid);
            }else{
                baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
                baseModel.setErrorMessage("删除角色出错，请您稍后再试");
            }
        }catch (Exception e){
            e.printStackTrace();
            baseModel.setStatus(IStatusMessage.SystemStatus.PARAM_ERROR.getCode());
            baseModel.setErrorMessage("删除角色异常");
            log.error("删除角色异常", e);
        }
        return baseModel;
    }


    /**
     * @Description:  功能描述（修改角色并授权）
     * @Author:       刘家义
     * @CreateDate:   2018/10/22 19:43
    */
    @ApiOperation(value ="修改角色并授权",notes = "更新角色并授权")
    @RequestMapping(value="/updateRole",method = RequestMethod.PUT)
    public BaseModel updateRole(@RequestParam("rmIds")String rmIds, Role role){
        log.debug("更新角色并授权！角色数据role：" + role + "，权限数据rmId：" + rmIds);
        BaseModel baseModel=new BaseModel();
        baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
        try {
            //1、判断是否选择权限
            if(StringUtils.isEmpty(rmIds)){
                baseModel.setErrorMessage("未授权，请您给该角色授权");
                return baseModel;
            }
            //2、判断角色信息是否为空
            if(null == role){
                baseModel.setErrorMessage("请您填写完整的角色数据");
                return baseModel;
            }
            //3、对角色及权限进行更新
            role.setUpdateTime(new Date());
            baseModel=this.roleService.revampRole(role, rmIds);
        }catch (Exception e){
            e.printStackTrace();
            baseModel.setErrorMessage("删除角色异常");
            log.error("更新角色并授权！异常！", e);
        }
        return baseModel;
    }


    /**
     * @Description:  功能描述（根据角色id查询角色以及权限）
     * @Author:       刘家义
     * @CreateDate:   2018/10/22 20:02
    */
    @ApiOperation(value ="根据角色id查询限树列表",notes = "根据角色id查询限树列表")
    @RequestMapping(value="/getRole",method = RequestMethod.GET)
    public BaseModel getRoleMenu(@RequestParam("rid")Integer rid){
        log.debug("根据角色id查询限树列表！--rid---"+rid);
        BaseModel baseModel=new BaseModel();
        try {
            if(rid>0){
                baseModel=this.roleService.getRoleMenu(rid);
            }else{
                baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
                baseModel.setErrorMessage("查询角色权限出错，请您稍后再试");
            }
        }catch (Exception e){
            e.printStackTrace();
            baseModel.setStatus(IStatusMessage.SystemStatus.PARAM_ERROR.getCode());
            baseModel.setErrorMessage("查询角色权限异常");
            log.error("查询角色权限异常", e);
        }
        return baseModel;
    }

    /**
     * @Description:  功能描述（获取所有的角色列表）
     * @Author:       刘家义
     * @CreateDate:   2018/10/22 20:22
    */
    @ApiOperation(value ="获取所有的角色列表",notes = "查找所有角色")
    @RequestMapping(value="/getRoleList",method = RequestMethod.GET)
    public BaseModel getRoleList(@RequestParam(value="pn",defaultValue="1") Integer pn,@RequestParam(value = "ps",defaultValue="5")Integer ps){
        log.debug("查找所有角色!");
        BaseModel baseModel=new BaseModel();
        PageHelper.startPage(pn,ps);
        List<Role> roles = null;
        try {
            List<Role> listRoles=this.roleService.getRoles();
            PageInfo<Role> page = new PageInfo<Role>(listRoles,5);
            baseModel.setStatus(IStatusMessage.SystemStatus.SUCCESS.getCode());
            baseModel.add("pageInfo",page);
            baseModel.setErrorMessage("角色信息查询成功");
        } catch (Exception e) {
            e.printStackTrace();
            baseModel.setStatus(IStatusMessage.SystemStatus.PARAM_ERROR.getCode());
            baseModel.setErrorMessage("查找所有角色异常");
            log.error("查找所有角色异常！", e);
        }
        return baseModel;
    }

}
