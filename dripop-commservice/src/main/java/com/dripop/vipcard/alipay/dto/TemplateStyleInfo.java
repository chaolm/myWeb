package com.dripop.vipcard.alipay.dto;

import java.io.Serializable;

public class TemplateStyleInfo implements Serializable{

    private String card_show_name;

    private String logo_id;

    private String  background_id;

    private String bg_color;

    public String getCard_show_name() {
        return card_show_name;
    }

    public void setCard_show_name(String card_show_name) {
        this.card_show_name = card_show_name;
    }

    public String getLogo_id() {
        return logo_id;
    }

    public void setLogo_id(String logo_id) {
        this.logo_id = logo_id;
    }

    public String getBackground_id() {
        return background_id;
    }

    public void setBackground_id(String background_id) {
        this.background_id = background_id;
    }

    public String getBg_color() {
        return bg_color;
    }

    public void setBg_color(String bg_color) {
        this.bg_color = bg_color;
    }
}
