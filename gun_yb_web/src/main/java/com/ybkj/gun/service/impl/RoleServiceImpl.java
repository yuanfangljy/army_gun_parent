package com.ybkj.gun.service.impl;

import com.ybkj.common.entity.RoleMenuVO;
import com.ybkj.enums.IStatusMessage;
import com.ybkj.gun.mapper.RoleMapper;
import com.ybkj.gun.mapper.RoleMenuMapper;
import com.ybkj.gun.model.Menu;
import com.ybkj.gun.model.RoleMenu;
import com.ybkj.gun.model.WebUser;
import com.ybkj.gun.service.RoleService;
import com.ybkj.model.BaseModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ybkj.gun.model.Role;
import java.util.List;

/**
 * @项目名称：
 * @类名称：
 * @类描述：角色功能实现业务逻辑
 * @创建人：liujiayi
 * @创建时间：2018/10/31 20:18
 * @修改时间：2018/10/31 20:18
 * @version：1.0
 */

@SuppressWarnings("all")
@Service
@Transactional
@Slf4j
public class RoleServiceImpl implements RoleService{

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RoleMenuMapper roleMenuMapper;

    /**
     * @Description:  功能描述（通过用户id，获取相应的角色）
     * @Author:       刘家义
     * @CreateDate:   2018/10/16 16:58
     */
    @Override
    public List<Role> findRoleByUser(Integer uid) {
        return roleMapper.selectRoleByUser(uid);
    }



    /**
     * @Description:  功能描述（新增角色并授权）
     * @Author:       刘家义
     * @CreateDate:   2018/10/22 17:04
     */
    @Override
    public BaseModel addRole(String mids, Role role) throws Exception {
        BaseModel baseModel=new BaseModel();
        baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
        //1、判断角色名称是否存在
        Role existRoleName = this.roleMapper.selectRoleByRoleName(role.getRoleName());
        if(null != existRoleName){
            baseModel.setErrorMessage("角色名称已存在");
            return baseModel;
        }
        //2、判断角色编码是否存在
        Role existRoleCode = this.roleMapper.selectRoleByRoleCode(role.getCode());
        if(null != existRoleCode){
            baseModel.setErrorMessage("角色编码已存在");
            return baseModel;
        }
        //3、判断当前用户是否在线
        WebUser existUser = (WebUser) SecurityUtils.getSubject().getPrincipal();
        if(null==existUser){
            baseModel.setErrorMessage("您未登录或登录超时，请您登录后再试");
            log.debug("用户登录，结果=responseResult:" + baseModel);
            return baseModel;
        }
        //4、新增角色信息
        role.setUid(existUser.getId());
        roleMapper.insertSelective(role);
        //5、新增角色权限信息
        Integer roleId=role.getRid();
        setRoleMenu(roleId,mids);
        baseModel.setStatus(IStatusMessage.SystemStatus.SUCCESS.getCode());
        baseModel.setErrorMessage("新增角色并授权成功");
        log.debug("添加角色并授权成功！");
        return baseModel;
    }

    /**
     * @Description:  功能描述（根据角色id删除角色信息及对应的权限）
     * @Author:       刘家义
     * @CreateDate:   2018/10/22 19:31
     */
    @Override
    public BaseModel removeRole(Integer rid) throws Exception {
        BaseModel baseModel=new BaseModel();
        //1.删除角色对应的权限
        batchDelRolePerms(rid);
        //2、删除角色
        int i = this.roleMapper.deleteByPrimaryKey(rid);
        if(i>0) {
            baseModel.setStatus(IStatusMessage.SystemStatus.SUCCESS.getCode());
            baseModel.setErrorMessage("角色删除成功");
        }else {
            baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
            baseModel.setErrorMessage("角色删除失败");

        }
        return baseModel;
    }

    /**
     * @Description:  功能描述（更新角色已经权限）
     * @Author:       刘家义
     * @CreateDate:   2018/10/22 19:52
     */
    @Override
    public BaseModel revampRole(Role role, String rmIds) throws Exception {
        BaseModel baseModel=new BaseModel();
        Integer roleId=role.getRid();
        //1、更新角色表数据
        int i = this.roleMapper.updateByPrimaryKeySelective(role);
        if(i>0) {
            baseModel.setStatus(IStatusMessage.SystemStatus.SUCCESS.getCode());
            baseModel.setErrorMessage("角色信息更新成功");
        }else {
            baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
            baseModel.setErrorMessage("角色信息更新失败");
        }
        //2、删除原来的角色权限数据
        batchDelRolePerms(roleId);
        //3、添加新的角色权限数据
        setRoleMenu(roleId,rmIds);
        return baseModel;
    }

    /**
     * @Description:  功能描述（查询所有的角色信息）
     * @Author:       刘家义
     * @CreateDate:   2018/10/22 20:26
     */
    @Override
    public List<Role> getRoles() throws Exception {
        return roleMapper.selectRoles();
    }

    /**
     * @Description:  功能描述（根据角色id,获取角色和权限）
     * @Author:       刘家义
     * @CreateDate:   2018/10/22 20:47
     */
    @Override
    public BaseModel getRoleMenu(Integer rid) throws Exception {
        BaseModel baseModel=new BaseModel();
        RoleMenuVO roleMenuVO= roleMapper.selectRoleAndMenuByRoleId(rid);
        if(null!=roleMenuVO){
            baseModel.setStatus(IStatusMessage.SystemStatus.SUCCESS.getCode());
            baseModel.setErrorMessage("查询角色及权限成功");
        }else{
            baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
            baseModel.setErrorMessage("查询角色及权限错误，请稍后再试");
        }
        return baseModel;
    }


    /**
     * 批量删除角色权限中间表数据
     * @param roleId
     */
    private void batchDelRolePerms(Integer roleId) {
        List<RoleMenu> rpks=this.roleMenuMapper.findByRole(roleId);
        if(null!=rpks && rpks.size()>0){
            for (RoleMenu rpk : rpks) {
                this.roleMenuMapper.deleteRoleMenuByRid(rpk.getRid());
            }
        }
    }


    /**
     * @Description:  功能描述（给角色设置权限）
     * @Author:       刘家义
     * @CreateDate:   2018/10/22 17:35
     */
    public void setRoleMenu(Integer roleId,String mids){
        String[] arrays=mids.split(",");
        log.debug("权限id =arrays="+arrays.toString());
        for (String mid : arrays) {
            RoleMenu roleMenu=new RoleMenu();
            roleMenu.setMid(Integer.valueOf(mid));
            roleMenu.setRid(roleId);
            this.roleMenuMapper.insertSelective(roleMenu);
        }
    }

}
