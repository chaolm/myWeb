package com.bean;

/**
 * 是否可用标识
 * Created by liyou on 2017/9/25.
 */
public enum IsUsed {
    /**
     * 可用
     */
    USED(1),
    /**
     * 不可用
     */
    NOT_USED(0);

    IsUsed(Integer value) {
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
