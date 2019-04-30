package com.bean;

/**
 * 提货方式
 * Created by liyou on 2017/9/27.
 */
public enum ShoppingModel {

    DELIVERY(2, "送货上门"),

    SELF_PICK_UP(3, "到店自提");

    ShoppingModel(Integer value, String name) {
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
        ShoppingModel[] array = ShoppingModel.values();
        for (ShoppingModel myBankStatus : array) {
            if(myBankStatus.getValue().equals(value)) {
                return myBankStatus.getName();
            }
        }
        return null;
    }
}
