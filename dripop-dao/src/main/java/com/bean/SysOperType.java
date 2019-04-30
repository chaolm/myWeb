package com.bean;

/**
 * 员工类型
 * Created by liyou on 2017/9/25.
 */
public enum SysOperType {
    /**
     * op管理员
     */
    ADMIN(1),
    /**
     * 店员
     */
    SELLER(2);

    SysOperType(Integer value) {
        this.value = value;
    }

    private Integer value;

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
