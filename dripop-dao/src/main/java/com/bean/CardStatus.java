package com.bean;

/**
 * 卡券领取状态
 * Created by liyou on 2017/9/27.
 */
public enum CardStatus {

    UN_START(1, "未开始"),

    WORKING(2, "进行中"),

    END(3, "已结束"),

    PAUSE(4, "已暂停");

    CardStatus(Integer value, String name) {
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
        CardStatus[] array = CardStatus.values();
        for (CardStatus myBankStatus : array) {
            if(myBankStatus.getValue().equals(value)) {
                return myBankStatus.getName();
            }
        }
        return null;
    }
}
