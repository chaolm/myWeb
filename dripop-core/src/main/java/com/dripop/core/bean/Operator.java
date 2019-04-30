//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.dripop.core.bean;

public enum Operator {
    eq,
    ne,
    gt,
    lt,
    ge,
    le,
    like,
    in,
    isNull,
    isNotNull;

    private Operator() {
    }

    public static Operator fromString(String value) {
        return valueOf(value.toLowerCase());
    }
}
