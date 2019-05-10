package com.ybkj.gun.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

public class Gun {
    private Integer id;

    private Integer uid;

    private String gunId;

    private String gunModel;

    @Getter
    @Setter
    private String gunImei;

    private String gunType;

    private String gunMac;

    private Byte isDel;

    private Date createTime;

    private Date updateTime;

    private Integer warehouseId;

    private String warehouseName;

    private Integer version;

    private Integer totalBulletNumber;

    private Integer realTimeState;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getGunId() {
        return gunId;
    }

    public void setGunId(String gunId) {
        this.gunId = gunId == null ? null : gunId.trim();
    }

    public String getGunModel() {
        return gunModel;
    }

    public void setGunModel(String gunModel) {
        this.gunModel = gunModel == null ? null : gunModel.trim();
    }

    public String getGunType() {
        return gunType;
    }

    public void setGunType(String gunType) {
        this.gunType = gunType == null ? null : gunType.trim();
    }

    public String getGunMac() {
        return gunMac;
    }

    public void setGunMac(String gunMac) {
        this.gunMac = gunMac == null ? null : gunMac.trim();
    }

    public Byte getIsDel() {
        return isDel;
    }

    public void setIsDel(Byte isDel) {
        this.isDel = isDel;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName == null ? null : warehouseName.trim();
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getTotalBulletNumber() {
        return totalBulletNumber;
    }

    public void setTotalBulletNumber(Integer totalBulletNumber) {
        this.totalBulletNumber = totalBulletNumber;
    }

    public Integer getRealTimeState() {
        return realTimeState;
    }

    public void setRealTimeState(Integer realTimeState) {
        this.realTimeState = realTimeState;
    }

    @Override
    public String toString() {
        return "Gun{" +
                "id=" + id +
                ", uid=" + uid +
                ", gunId='" + gunId + '\'' +
                ", gunModel='" + gunModel + '\'' +
                ", gunType='" + gunType + '\'' +
                ", gunMac='" + gunMac + '\'' +
                ", isDel=" + isDel +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", warehouseId=" + warehouseId +
                ", warehouseName='" + warehouseName + '\'' +
                ", version=" + version +
                ", totalBulletNumber=" + totalBulletNumber +
                ", realTimeState=" + realTimeState +
                ", apps=" + apps +
                '}';
    }

    @Setter
    @Getter
    private App apps;


}