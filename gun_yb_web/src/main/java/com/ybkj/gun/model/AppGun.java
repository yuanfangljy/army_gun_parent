package com.ybkj.gun.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

public class AppGun {
    private Integer id;

    private Integer appId;

    private String gunId;

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

    public String getGunId() {
        return gunId;
    }

    public void setGunId(String gunId) {
        this.gunId = gunId == null ? null : gunId.trim();
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

    @Setter
    @Getter
    private App apps;
    @Setter
    @Getter
    private Gun guns;

}