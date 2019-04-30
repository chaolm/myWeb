package com.bean;

/**
 * 卡券限领类型
 * Created by liyou on 2017/9/25.
 */
public enum CardXlType {
    /**
     * 每人限领一张
     */
    CARD(1),
    /**
     * 每人每天限领一张
     */
    DAY(2);

    CardXlType(Integer value) {
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
