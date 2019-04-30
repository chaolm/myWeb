package com.bean;

/**
 * 售后订单状态
 * Created by liyou on 2017/9/27.
 */
public enum OrderCancelStatus {

    SUBMIT(1, "待审核"),

    AGREE(2, "审核通过"),

    REFUSE(3, "审核拒绝"),

    CANCEL(4, "取消申请"),

    AUDIT_SUCCESS(5, "售后成功"),

    AUDIT_REFUSE(6, "售后拒绝");

    OrderCancelStatus(Integer value, String name) {
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
        OrderCancelStatus[] array = OrderCancelStatus.values();
        for (OrderCancelStatus myBankStatus : array) {
            if(myBankStatus.getValue().equals(value)) {
                return myBankStatus.getName();
            }
        }
        return null;
    }
}
