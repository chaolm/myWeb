package com.bean;

/**
 * 反馈类型
 *
 * @author dq
 * @date 2018/5/31 13:46
 */

public enum FeedbackType {

    GIVE_ADVICE(1, "提个意见"),

    FUNCTION(2, "功能意见"),

    INTERFACE(3, "界面意见"),

    OTHER(4, "其他问题");

    FeedbackType(Integer value, String name) {
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
        FeedbackType[] array = FeedbackType.values();
        for (FeedbackType feedbackType : array) {
            if(feedbackType.getValue().equals(value)) {
                return feedbackType.getName();
            }
        }
        return null;
    }
}
