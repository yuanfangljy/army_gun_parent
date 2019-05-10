package com.ybkj.common.entity;

import lombok.Data;

@Data
public class RoleMenuVO {

    private Integer rid;

    private String roleName;

    private Integer uid;

    private Integer mid;

    private String name;

    private Integer parentid;

    private Integer zindex;

    private Integer istype;

    private String descpt;

    private String code;

    private String icon;

    private String page;

    private Integer enabled;

}