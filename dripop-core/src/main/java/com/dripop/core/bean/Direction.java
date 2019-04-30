//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.dripop.core.bean;

public enum Direction {
    asc,
    desc;

    private Direction() {
    }

    public static Direction fromString(String value) {
        return valueOf(value.toLowerCase());
    }
}
