package com.bean;

/**
 * 消息通知撤销类型
 *
 * @author dq
 * @date 2018/3/19 15:14
 */

public enum IsRepeal {

    /**
     * 未撤销
     */
    UN_REPEAL(1),
    /**
     * 撤销
     */
    REPEAL(2);

    IsRepeal(Integer value) {
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
