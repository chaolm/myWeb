package com.dripop.vipcard.alipay.dto;

import java.io.Serializable;
import java.util.List;

public class AlipayVipCardReq implements Serializable {

    /**
     * 请求id
     */
    private String request_id;

    private String card_type;

    private String biz_no_prefix;

    private String biz_no_suffix_len;

    private String write_off_type;

    private TemplateStyleInfo template_style_info;

    private List<ColumnInfoList>  column_info_list;

    private List<CardActionList> card_action_list;

    private List<FieldRuleList> field_rule_list;

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public String getCard_type() {
        return card_type;
    }

    public void setCard_type(String card_type) {
        this.card_type = card_type;
    }

    public String getBiz_no_prefix() {
        return biz_no_prefix;
    }

    public void setBiz_no_prefix(String biz_no_prefix) {
        this.biz_no_prefix = biz_no_prefix;
    }

    public String getBiz_no_suffix_len() {
        return biz_no_suffix_len;
    }

    public void setBiz_no_suffix_len(String biz_no_suffix_len) {
        this.biz_no_suffix_len = biz_no_suffix_len;
    }

    public String getWrite_off_type() {
        return write_off_type;
    }

    public void setWrite_off_type(String write_off_type) {
        this.write_off_type = write_off_type;
    }

    public TemplateStyleInfo getTemplate_style_info() {
        return template_style_info;
    }

    public void setTemplate_style_info(TemplateStyleInfo template_style_info) {
        this.template_style_info = template_style_info;
    }

    public List<ColumnInfoList> getColumn_info_list() {
        return column_info_list;
    }

    public void setColumn_info_list(List<ColumnInfoList> column_info_list) {
        this.column_info_list = column_info_list;
    }

    public List<CardActionList> getCard_action_list() {
        return card_action_list;
    }

    public List<FieldRuleList> getField_rule_list() {
        return field_rule_list;
    }

    public void setField_rule_list(List<FieldRuleList> field_rule_list) {
        this.field_rule_list = field_rule_list;
    }

    public void setCard_action_list(List<CardActionList> card_action_list) {
        this.card_action_list = card_action_list;
    }
}
