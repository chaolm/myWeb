package com.bean;

/**
 * 类名及用途
 *
 * @author dq
 * @date 2018/5/31 13:52
 */

public enum SourceChannel {
    APP(1, "APP"),

    PC(2, "PC");

    SourceChannel(Integer value, String name) {
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
        SourceChannel[] array = SourceChannel.values();
        for (SourceChannel sourceChannel : array) {
            if(sourceChannel.getValue().equals(value)) {
                return sourceChannel.getName();
            }
        }
        return null;
    }
}
