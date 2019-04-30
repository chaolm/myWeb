package com.bean;

/**
 * 消息类型
 *
 * @author dq
 * @date 2018/3/19 11:06
 */

public enum MsgServiceType {
    /*无*/
    NONE(1),
    /*商品*/
    GOODS(2),
    /*聚合页*/
    H5(3),
    /*订单*/
    MSG_ORDER(4),
    /*售后*/
    MSG_SERVICE(5),
    /*卡券中心*/
    MSG_COUPON_CENTRE(6),
    /*我的卡券*/
    MSG_COUPON(7);


    MsgServiceType(Integer value) {
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
