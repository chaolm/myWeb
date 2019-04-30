package com.bean;

/**
 * 发货操作类型
 *
 * @author dq
 * @date 2018/3/26 10:03
 */

public enum DeliveryType {
    DELIVERY(1, "已发货"),

    OUT(2, "无货"),

    UN_DELIVERY(3, "未发货"),

    VERIFY(4, "已核销"),

    UN_VERIFY(5, "未核销"),

    UN_OUTBOUND(6, "未出库"),

    REFUSE(7, "已拒收"),

    OUTBOUND(8, "已出库");


    DeliveryType(Integer value, String name) {
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
        DeliveryType[] array = DeliveryType.values();
        for (DeliveryType deliveryType : array) {
            if (deliveryType.getValue().equals(value)) {
                return deliveryType.getName();
            }
        }
        return null;
    }
}
