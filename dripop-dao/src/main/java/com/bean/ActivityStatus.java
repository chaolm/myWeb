package com.bean;

/**
 * 活动、营销状态
 * Created by liyou on 2017/9/27.
 */
public enum ActivityStatus {

    UN_START(1, "未开始"),

    WORKING(2, "进行中"),

    SUSPEND(3, "已暂停"),

    FINISH(4, "已结束");

    ActivityStatus(Integer value, String name) {
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
        ActivityStatus[] array = ActivityStatus.values();
        for (ActivityStatus myBankStatus : array) {
            if(myBankStatus.getValue().equals(value)) {
                return myBankStatus.getName();
            }
        }
        return null;
    }
}
