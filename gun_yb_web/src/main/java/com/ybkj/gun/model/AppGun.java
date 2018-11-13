package com.ybkj.gun.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class AppGun {
    private Integer id;

    private Integer appId;

    private Integer gunId;

    private Date createTime;

    private Integer allotState;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public Integer getGunId() {
        return gunId;
    }

    public void setGunId(Integer gunId) {
        this.gunId = gunId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getAllotState() {
        return allotState;
    }

    public void setAllotState(Integer allotState) {
        this.allotState = allotState;
    }


    @Setter
    @Getter
    private String appName;
    @Setter
    @Getter
    private Integer counts;
}