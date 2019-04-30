package com.bean;

/**
 * 赠品类型
 * Created by liyou on 2017/9/25.
 */
public enum GiftType {
    /**
     * 线上
     */
    ONLINE(1),
    /**
     * 线下
     */
    UN_LINE(2);

    GiftType(Integer value) {
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
