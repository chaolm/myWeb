package com.dripop.constant;

import com.dripop.core.exception.ServiceException;

/**
 * Created by liyou on 2017/9/28.
 */
public enum RegexConstant {

    /**
     * 密码
     */
    PWD("^(?![\\d]+$)(?![a-zA-Z]+$)(?![^\\da-zA-Z]+$).{6,16}$", "密码长度为6-16位，数字、字母、特殊字符至少包含两种");

    RegexConstant(String code, String value) {
        this.code = code;
        this.value = value;
    }

    private String code;

    private String value;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /**
     * 校验是否满足正则表达式
     * @param regexConstant
     * @param val
     */
    public static void matches(RegexConstant regexConstant, String val) {
        if(!val.matches(regexConstant.getCode())) {
            throw new ServiceException(regexConstant.getValue());
        }
    }
}
