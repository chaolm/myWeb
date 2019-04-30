package com.bean;

/**
 * 卡券使用有效期类型
 * Created by liyou on 2017/9/25.
 */
public enum CardUseTimeType {
    /**
     * 绝对时间
     */
    JD(1),
    /**
     * 相对时间
     */
    XD(2);

    CardUseTimeType(Integer value) {
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
