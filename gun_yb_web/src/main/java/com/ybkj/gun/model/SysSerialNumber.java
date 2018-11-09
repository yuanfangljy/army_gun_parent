package com.ybkj.gun.model;

public class SysSerialNumber {
    private String id;

    private String moduleName;

    private String moduleCode;

    private String configTemplet;

    private String maxSerial;

    private String preMaxNum;

    private String isAutoIncrement;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName == null ? null : moduleName.trim();
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode == null ? null : moduleCode.trim();
    }

    public String getConfigTemplet() {
        return configTemplet;
    }

    public void setConfigTemplet(String configTemplet) {
        this.configTemplet = configTemplet == null ? null : configTemplet.trim();
    }

    public String getMaxSerial() {
        return maxSerial;
    }

    public void setMaxSerial(String maxSerial) {
        this.maxSerial = maxSerial == null ? null : maxSerial.trim();
    }

    public String getPreMaxNum() {
        return preMaxNum;
    }

    public void setPreMaxNum(String preMaxNum) {
        this.preMaxNum = preMaxNum == null ? null : preMaxNum.trim();
    }

    public String getIsAutoIncrement() {
        return isAutoIncrement;
    }

    public void setIsAutoIncrement(String isAutoIncrement) {
        this.isAutoIncrement = isAutoIncrement == null ? null : isAutoIncrement.trim();
    }
}