package com.bean;

/**
 * 类名及用途
 *
 * @author dq
 * @date 2018/3/17 9:40
 */

public enum VisitType {

    /**
     * 游客
     */
    VISITORS(1),
    /**
     * 会员
     */
    CUSTOMER(2);


    VisitType(Integer value) {
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
