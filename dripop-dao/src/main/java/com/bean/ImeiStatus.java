package com.bean;

/**
 * 商品串号状态
 * Created by liyou on 2017/9/27.
 */
public enum ImeiStatus {

    ZT(1, "在途"),

    ZK(2, "在库"),

    SOLD(3, "已售"),

    SERVICE(4, "售后");

    ImeiStatus(Integer value, String name) {
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
        ImeiStatus[] array = ImeiStatus.values();
        for (ImeiStatus myBankStatus : array) {
            if(myBankStatus.getValue() == value) {
                return myBankStatus.getName();
            }
        }
        return null;
    }
}
