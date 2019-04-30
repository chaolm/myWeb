package com.bean;

/**
 * 是否可用标识
 * Created by liyou on 2017/9/25.
 */
public enum ThirdPlatType {
    /**
     * QQ
     */
    QQ(1),
    /**
     * 微信
     */
    WECHAT(2),
    /**
     * 支付宝
     */
    ALIPAY(3);

    ThirdPlatType(Integer value) {
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
