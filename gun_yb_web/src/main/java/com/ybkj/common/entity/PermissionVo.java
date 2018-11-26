package com.ybkj.common.entity;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.List;

/**
 * @项目名称：
 * @类名称：
 * @类描述：用户权限保存类
 * @创建人：liujiayi
 * @创建时间：2018/11/25 10:52
 * @修改时间：2018/11/25 10:52
 * @version：1.0
 */
@Data
@Slf4j
@SuppressWarnings("ALL")
public class PermissionVo implements Serializable {
    private static final long serialVersionUID= -2783081162690878303L;

    //------------- Start 角色相关信息  --------------
  /*  private Integer rid;
    private String roleName;
    private String rdescpt;
    private String rcode;*/
    //------------- End 角色相关信息  --------------


    //------------- Start 菜单相关信息  --------------
    private Integer mid;
    private String name;
    private Integer parentId;
    private Integer zindex;
    private Integer istype;
    private String descpt;
    private String code;
    private String icon;
    private String page;
    private Integer enabled;
    private List<PermissionVo> children;

    //------------- End 菜单相关信息  --------------

    //------------- Start 用户相关信息  --------------
   /* private String webUserName;
    private String webUserPhone;
    private Integer departmentId;
    private Boolean isDel;
    private Boolean isJob;*/
    //------------- End 用户相关信息  --------------
}
