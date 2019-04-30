package com.bean;

/**
 * 售后服务类型
 * Created by liyou on 2017/9/27.
 */
public enum ServiceType {

    RETURN_GOODS(1, "退货"),

    EXCHANGE_GOODS(2, "换货"),

    REPAIR_GOODS(3, "维修"),

    REFUNDMENT(4, "退款"),

    DEPOSIT_REFUNDMENT(5, "定金退款");

    ServiceType(Integer value, String name) {
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
        ServiceType[] array = ServiceType.values();
        for (ServiceType myBankStatus : array) {
            if(myBankStatus.getValue().equals(value)) {
                return myBankStatus.getName();
            }
        }
        return null;
    }
}
