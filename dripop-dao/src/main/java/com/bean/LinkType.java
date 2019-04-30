package com.bean;

/**
 * 链接类型
 * Created by liyou on 2017/9/25.
 */
public enum LinkType {
    /**
     * 无
     */
    NONE(1),
    /**
     * 可用
     */
    GOODS(2),
    /**
     * 不可用
     */
    H5(3);

    LinkType(Integer value) {
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
