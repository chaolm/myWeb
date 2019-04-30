package com.bean;

/**
 * 支付状态
 * Created by liyou on 2017/9/27.
 */
public enum PayStatus {

    UNPAY(0),

    FINISH(1);

    PayStatus(Integer value) {
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
