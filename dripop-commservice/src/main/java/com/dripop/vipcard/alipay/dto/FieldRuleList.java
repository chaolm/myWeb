package com.dripop.vipcard.alipay.dto;

import java.io.Serializable;

public class FieldRuleList implements Serializable{

    private String  field_name;

    private String rule_name;

    private String rule_value;

    public String getField_name() {
        return field_name;
    }

    public void setField_name(String field_name) {
        this.field_name = field_name;
    }

    public String getRule_name() {
        return rule_name;
    }

    public void setRule_name(String rule_name) {
        this.rule_name = rule_name;
    }

    public String getRule_value() {
        return rule_value;
    }

    public void setRule_value(String rule_value) {
        this.rule_value = rule_value;
    }
}
