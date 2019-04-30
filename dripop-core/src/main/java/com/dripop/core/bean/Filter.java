package com.dripop.core.bean;

import java.io.Serializable;

public class Filter implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final boolean DEFAULT_IGNORE_CASE = false;
    private String property;
    private Operator operator;
    private Object value;
    private Boolean ignoreCase = Boolean.valueOf(false);

    private Filter() {
    }

    public Filter(String property, Operator operator, Object value) {
        this.property = property;
        this.operator = operator;
        this.value = value;
    }

    public Filter(String property, Operator operator, Object value, boolean ignoreCase) {
        this.property = property;
        this.operator = operator;
        this.value = value;
        this.ignoreCase = Boolean.valueOf(ignoreCase);
    }

    public static Filter eq(String property, Object value) {
        return new Filter(property, Operator.eq, value);
    }

    public static Filter eq(String property, Object value, boolean ignoreCase) {
        return new Filter(property, Operator.eq, value, ignoreCase);
    }

    public static Filter ne(String property, Object value) {
        return new Filter(property, Operator.ne, value);
    }

    public static Filter ne(String property, Object value, boolean ignoreCase) {
        return new Filter(property, Operator.ne, value, ignoreCase);
    }

    public static Filter gt(String property, Object value) {
        return new Filter(property, Operator.gt, value);
    }

    public static Filter lt(String property, Object value) {
        return new Filter(property, Operator.lt, value);
    }

    public static Filter ge(String property, Object value) {
        return new Filter(property, Operator.ge, value);
    }

    public static Filter le(String property, Object value) {
        return new Filter(property, Operator.le, value);
    }

    public static Filter like(String property, Object value) {
        return new Filter(property, Operator.like, value);
    }

    public static Filter in(String property, Object value) {
        return new Filter(property, Operator.in, value);
    }

    public static Filter isNull(String property) {
        return new Filter(property, Operator.isNull, (Object)null);
    }

    public static Filter isNotNull(String property) {
        return new Filter(property, Operator.isNotNull, (Object)null);
    }

    public Filter ignoreCase() {
        this.ignoreCase = Boolean.valueOf(true);
        return this;
    }

    public String getProperty() {
        return this.property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public Operator getOperator() {
        return this.operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public Object getValue() {
        return this.value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Boolean getIgnoreCase() {
        return this.ignoreCase;
    }

    public void setIgnoreCase(Boolean ignoreCase) {
        this.ignoreCase = ignoreCase;
    }
}
