package com.bean;

/**
 * 支付方式
 * Created by liyou on 2017/9/27.
 */
public enum PayModel {

    CASH(1, "现金"),

    WECHAT(2, "微信"),

    ALIPAY(3, "支付宝"),

    STAGING_PAY(5, "花呗分期");

    PayModel(Integer value, String name) {
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
        PayModel[] array = PayModel.values();
        for (PayModel myBankStatus : array) {
            if(myBankStatus.getValue().equals(value)) {
                return myBankStatus.getName();
            }
        }
        return null;
    }

    public static String getName(Integer value, Integer fqNum, Integer sellerPoundage) {
        PayModel[] array = PayModel.values();
        for (PayModel myBankStatus : array) {
            if(PayModel.STAGING_PAY.getValue().equals(value)) {
                return (sellerPoundage != null && sellerPoundage == 0) ? "花呗"+fqNum+"期"+"(免息)" : "花呗"+fqNum+"期"+"(含息)";
            }else if(myBankStatus.getValue().equals(value)) {
                return myBankStatus.getName();
            }
        }
        return null;
    }
}
