package com.bean;

/**
 * 订单状态
 * Created by liyou on 2017/9/27.
 */
public enum OrderStatus {

    WAIT_PAY(1, "待支付"),

    WAIT_DELIVERY(2, "待收货"),

//    DELIVERIED(3, "已结束"),

    FINISH(3, "已完成"),

    CLOSE(4, "已取消");

    OrderStatus(Integer value, String name) {
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
        OrderStatus[] array = OrderStatus.values();
        for (OrderStatus myBankStatus : array) {
            if(myBankStatus.getValue().equals(value)) {
                return myBankStatus.getName();
            }
        }
        return null;
    }
}
