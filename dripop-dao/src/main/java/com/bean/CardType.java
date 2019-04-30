package com.bean;

/**
 * 卡券类型
 * Created by liyou on 2017/9/25.
 */
public enum CardType {
    /**
     * 满减券
     */
    FULL_CUT(1),
    /**
     * 折扣券
     */
    DISCOUNT(2);

    CardType(Integer value) {
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
