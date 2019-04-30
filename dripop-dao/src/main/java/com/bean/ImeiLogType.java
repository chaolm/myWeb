package com.bean;

public enum ImeiLogType {
    IN_WAREHOUSE(1, "入库"),

    OUT_WAREHOUSE(2, "出库"),

    MOVE_WAREHOUSE(3, "移库"),

    SALE(4, "卖出"),

    CHANGE_SHOP(5, "换货"),

    IMEIS_CHAGNGE(6, "串号更换"),

    IMEIS_RECOVER(7, "串号恢复"),

    OUT_SHOP(8, "退货");


    ImeiLogType(Integer value, String name) {
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
        ImeiLogType[] array = ImeiLogType.values();
        for (ImeiLogType myBankStatus : array) {
            if(myBankStatus.getValue().equals(value)) {
                return myBankStatus.getName();
            }
        }
        return null;
    }
}
