package com.bean;

/**
 * 首页专区数据类型
 * Created by liyou on 2017/9/25.
 */
public enum IndexAreaType {
    /**
     * 首页
     */
    INDEX(1),
    /**
     * 专区
     */
    AREA(2),
    /**
     * BANNER
     */
    BANNER(3),
    /**
     * 区块
     */
    BLOCK(4),
    /**
     * 商品位
     */
    GOODS(5),
    /**
     * 通栏
     */
    CHANNEL(6);

    IndexAreaType(Integer value) {
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
