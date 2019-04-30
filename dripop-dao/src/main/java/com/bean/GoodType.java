package com.bean;

/**
 * 商品类型
 *Created by clm 2018/5/8
 */
public enum GoodType {
    COMMON_GOOD(1, "普通商品"),
    DEPOSIT_PRESELL_GOOD(2, "定金预售商品"),
    BESPOKE_PRESELL_GOOD(3, "预约预售商品"),
    ALLMONEY_PRESELL_GOOD(4, "全款预售商品");

    private Integer value;
    private String name;
    GoodType(Integer value, String name) {
        this.name = name;
        this.value = value;
    }
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
        GoodType[] array = GoodType.values();
        for (GoodType myBankStatus : array) {
            if(myBankStatus.getValue().equals(value)) {
                return myBankStatus.getName();
            }
        }
        return null;
    }
}
