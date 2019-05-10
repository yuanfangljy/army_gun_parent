package com.ybkj.gun.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class AppGunUser {
    private Integer id;

    private Integer appId;

    private Integer gunUserId;

    private Integer bindingState;

    private Date createTime;

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

    public Integer getGunUserId() {
        return gunUserId;
    }

    public void setGunUserId(Integer gunUserId) {
        this.gunUserId = gunUserId;
    }

    public Integer getBindingState() {
        return bindingState;
    }

    public void setBindingState(Integer bindingState) {
        this.bindingState = bindingState;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }



    @Setter
    @Getter
    private App apps;
    @Setter
    @Getter
    private GunUser gunUser;
}