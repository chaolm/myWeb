package com.bean;

/**
 * 商品处理类型
 *
 * @author dq
 * @date 2018/4/10 16:22
 */

public enum OperServiceType {


    ORDER(1, "订单"),

    CANCEL_REFUSE(2, "售后退货"),

    CANCEL_EXCHANGE(3, "售后换货"),

    CANCEL_DELIVERY(4, "售后发货");

    OperServiceType(Integer value, String name) {
        this.name = name;
        this.value = value;
    }

    private Integer value;

    private String name;

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static String getName(Integer value) {
        OperServiceType[] array = OperServiceType.values();
        for (OperServiceType myBankStatus : array) {
            if (myBankStatus.getValue().equals(value)) {
                return myBankStatus.getName();
            }
        }
        return null;
    }
}
