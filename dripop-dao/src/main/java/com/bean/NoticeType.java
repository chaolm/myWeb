package com.bean;

public enum NoticeType {
    ARRIVAL_NOTICE(1, "到货通知"),

    PREE_NOTICE(2, "预约通知");

    private Integer value;

    private String name;

    NoticeType(Integer value, String name) {
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
        NoticeType[] array = NoticeType.values();
        for (NoticeType myBankStatus : array) {
            if (myBankStatus.getValue().equals(value)) {
                return myBankStatus.getName();
            }
        }
        return null;
    }
}
