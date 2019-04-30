package com.bean;

/**
 * 卡券领取方式
 * Created by liyou on 2017/9/27.
 */
public enum CardReceiveType {

    REGISTER(1, "注册领取"),

    CARD_CENTER(2, "领券中心"),

    ORDER_COMPLETE(3, "完成订单"),

    PHONE(4, "指定发放");

    CardReceiveType(Integer value, String name) {
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
        CardReceiveType[] array = CardReceiveType.values();
        for (CardReceiveType myBankStatus : array) {
            if(myBankStatus.getValue().equals(value)) {
                return myBankStatus.getName();
            }
        }
        return null;
    }
}
