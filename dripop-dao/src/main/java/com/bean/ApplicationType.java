package com.bean;

/**
 * 帮助问题展示位置类型
 *
 * @author dq
 * @date 2018/5/31 13:40
 */

public enum ApplicationType {

    BLANK(0, ""),

    ORDER(1, "我的订单"),

    SERVER(2, "售后申请"),

    PRESELL(3, "商品预售"),

    FLASH(4, "闪购问题");

    ApplicationType(Integer value, String name) {
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
        ApplicationType[] array = ApplicationType.values();
        for (ApplicationType applicationType : array) {
            if(applicationType.getValue().equals(value)) {
                return applicationType.getName();
            }
        }
        return null;
    }
}
