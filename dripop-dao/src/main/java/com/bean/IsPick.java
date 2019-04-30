package com.bean;

/**
 * 门店是否支持自提
 *
 * @author dq
 * @date 2018/6/1 9:34
 */

public enum IsPick  {

    /**
     * 可用
     */
    PICK(1,"支持"),
    /**
     * 不可用
     */
    UN_PICK(0,"不支持");

    IsPick(Integer value, String name) {
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
        IsPick[] array = IsPick.values();
        for (IsPick myBankStatus : array) {
            if(myBankStatus.getValue().equals(value)) {
                return myBankStatus.getName();
            }
        }
        return null;
    }
}
