package com.bean;

/**
 * 逻辑删除标识
 * Created by liyou on 2017/9/25.
 */
public enum LogicDelete {
    /**
     * 已删除
     */
    DELETE(1),
    /**
     * 未删除
     */
    NOT_DELETE(0);

    LogicDelete(Integer value) {
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
