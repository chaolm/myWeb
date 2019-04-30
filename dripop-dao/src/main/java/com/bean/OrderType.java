package com.bean;

/**
 * 订单类型
 * Created by liyou on 2017/9/25.
 */
public enum OrderType {
    /**
     * 普通订单
     */
    NORMAL(1,"普通订单"),
    /**
     * 售后订单
     */
    SERVICE(7,"售后订单"),
    PRESELL(5,"预售订单");

    OrderType(Integer value, String name) {
        this.name = name;
        this.value = value;
    }

    private Integer value;
    private String name;

    public String getName() {
        return name;
    }
    public static String getName(Integer value) {
        OrderType[] array = OrderType.values();
        for (OrderType myBankStatus : array) {
            if(myBankStatus.getValue().equals(value)) {
                return myBankStatus.getName();
            }
        }
        return null;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
