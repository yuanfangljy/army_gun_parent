package com.ybkj.common.entity;

import net.sf.oval.constraint.MatchPattern;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class WebUserDTO {
    private Integer id;

    private String name;

    @NotNull(message = "用户名不能为空，请您先填写用户名")
    private String webUserName;

    @NotNull(message = "密码不能为空，请您先填写密码")
    private String webUserPassword;

    @NotNull(message = "手机号不能为空，请您先填写手机号")
    @MatchPattern(pattern = "^1\\d{10}$",message = "手机号输入有误")
    private String webUserPhone;

    private Integer departmentId;

    private Boolean isDel;

    private Boolean isJob;

    private Integer insertid;

    private Date createTime;

    private Date updateTime;

    private Integer version;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getWebUserName() {
        return webUserName;
    }

    public void setWebUserName(String webUserName) {
        this.webUserName = webUserName == null ? null : webUserName.trim();
    }

    public String getWebUserPassword() {
        return webUserPassword;
    }

    public void setWebUserPassword(String webUserPassword) {
        this.webUserPassword = webUserPassword == null ? null : webUserPassword.trim();
    }

    public String getWebUserPhone() {
        return webUserPhone;
    }

    public void setWebUserPhone(String webUserPhone) {
        this.webUserPhone = webUserPhone == null ? null : webUserPhone.trim();
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Boolean getIsDel() {
        return isDel;
    }

    public void setIsDel(Boolean isDel) {
        this.isDel = isDel;
    }

    public Boolean getIsJob() {
        return isJob;
    }

    public void setIsJob(Boolean isJob) {
        this.isJob = isJob;
    }

    public Integer getInsertid() {
        return insertid;
    }

    public void setInsertid(Integer insertid) {
        this.insertid = insertid;
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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "WebUserDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", webUserName='" + webUserName + '\'' +
                ", webUserPassword='" + webUserPassword + '\'' +
                ", webUserPhone='" + webUserPhone + '\'' +
                ", departmentId=" + departmentId +
                ", isDel=" + isDel +
                ", isJob=" + isJob +
                ", insertid=" + insertid +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", version=" + version +
                '}';
    }
}